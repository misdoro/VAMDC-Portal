package org.vamdc.portal.session.queryLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.web.ServletContexts;
import org.vamdc.portal.Settings;
import org.vamdc.portal.entity.security.User;
import org.vamdc.portal.session.security.UserInfo;

@Name("querystore")
@Scope(ScopeType.PAGE)
public class QueryStore {

	/**
	 * Map storing last uuid for a given node key = node id
	 */
	private Map<String, QueryStoreResponse> uuids = new HashMap<String, QueryStoreResponse>(
			5);

	/**
	 * number of requests sent to querystore
	 */
	private Integer retry = 10;		

	/**
	 * time between two request to the querystore, in milliseconds
	 */
	private Integer retryInterval = 3000;

	@In
	private UserInfo auth;
	
	@Logger
	transient private Log log;
	
	private String processing = "";

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	/**
	 * return result of querystore for a request
	 * 
	 * @return
	 **/	
	public QueryStoreResponse getNodeUuid(String node) {
		// not requested yet
		if (this.uuids.containsKey(node) == false)
			return new QueryStoreResponse(QueryStoreResponse.STATUS_UNKNOWN,
					"", "");

		return this.uuids.get(node);
	}		

	/**
	 * Ask a permanent identifier for the request identified by the token string
	 * 
	 * @param token  token of a head request
	 */
	//@Asynchronous
	public void associateRequest(String token, String node) {
		this.processing = "Starting process";
		QueryStoreResponse result = null;
		Integer count = 0;
		try {
			String request = this.getRequest(token, this.getUserEmail(),
					this.getIpAdress());
						// not tried yet or previous try failed
			if (!uuids.containsKey(node)
					|| !uuids.get(node).getStatus()
							.equals(QueryStoreResponse.STATUS_SUCCESS)) {	
				while ((result == null || QueryStoreResponse.STATUS_EMPTY.equals(result.getStatus())) && count < this.retry) {
					result = this.doRequest(request);
					Thread.sleep(this.retryInterval);
					count++;
				}
				this.uuids.put(node, result);
			}
		} catch (Exception e) {
			log.debug(e);
		}
		this.processing = "INFO : A  request was sent to the query store to get a citation link. Request status :  " + uuids.get(node).getStatus();
	}


	/**
	 * Send an HTTP request to the query store to get a UUID corresponding to a
	 * head token
	 * 
	 * @param token
	 *            token of a head request
	 * @param email
	 *            email of registered user
	 * @param userIp
	 *            ip of user asking
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpException
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	private QueryStoreResponse doRequest(String requestString)
			throws IOException, HttpException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(3 * 1000).build();		
		
		HttpClient httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig).build();
				
		HttpGet request = new HttpGet(requestString);	
		request.addHeader("User-Agent", Settings.PORTAL_USER_AGENT.get());
		StringBuilder result = new StringBuilder();			
		
		//SSLHandShakeException (IOException) if missing certificate
		HttpResponse response = httpClient.execute(request);	
		
		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String uuid;
			while ((uuid = rd.readLine()) != null) {
				result.append(uuid);
			}
			rd.close();
			
		} else {
			// empty response
			if (response.getStatusLine().getStatusCode() == 204) {
				return new QueryStoreResponse(QueryStoreResponse.STATUS_EMPTY, "", "");
			}
			// server error
			else if (response.getStatusLine().getStatusCode() >= 500) {
				throw new HttpException("Server error : "
						+ response.getStatusLine().getStatusCode());
			}
			// client error
			else if (response.getStatusLine().getStatusCode() >= 400
					&& response.getStatusLine().getStatusCode() < 500) {
				throw new HttpException("Client error : "
						+ response.getStatusLine().getStatusCode());
			}
		}
		
		// extract uuid from json response
		return QueryStoreResponseReader.parseResponse(result.toString());
	}

	/**
	 * Build the URL of the HTTP request sent to the query store
	 * 
	 * @param token
	 * @param email
	 * @param userIp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRequest(String token, String email, String userIp) throws UnsupportedEncodingException {
		String result = Settings.QUERYSTORE_ASSOCIATION_URL.get();
		result = result + "queryToken=" + token + "&email=" + email
				+ "&userIp=" + userIp + "&usedClient=" + URLEncoder.encode(
						Settings.PORTAL_USER_AGENT.get()+"-"+Settings.PORTAL_VERSION.get(), "UTF-8");
		return result;
	}

	/**
	 * Get IP address of the user that is asking for the association
	 * 
	 * @return
	 */
	private String getIpAdress() {
		String ipAddress = ServletContexts.instance().getRequest()
				.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = ServletContexts.instance().getRequest().getRemoteAddr();
		}
		return ipAddress;
	}

	/**
	 * Return user email if he is logged in, empty string if not
	 * 
	 * @return
	 */
	private String getUserEmail() {
		User u = auth.getUser();
		if (u != null)
			return u.getEmail();
		else
			return "unregistered@portal.vamdc.eu";
	}

}
