package org.vamdc.portal.session.queryLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.vamdc.portal.Settings;
import org.vamdc.portal.entity.security.User;
import org.vamdc.portal.session.security.UserInfo;

public class QueryStoreRequest implements Callable<QueryStoreResponse>{
	
	@Logger
	private Log log = Logging.getLog(QueryStoreRequest.class);

	private UserInfo auth;	
	private String token;
	private String userIp;		

	/**
	 * parameters of request to query store 
	 * @author nmoreau
	 *
	 */
	enum Parameter{

		QUERYTOKEN("queryToken"),
		EMAIL("email"),
		USERIP("userIp"),
		USEDCLIENT("usedClient");		

		private String text;

		Parameter(String text){
			this.text = text;
		}

		public String getText(){
			return this.text;
		}
	}


	public QueryStoreRequest(String token,  String userIp){
		auth = (UserInfo) Component.getInstance("auth");
		this.token = token;
		this.userIp = userIp;
	}

	@Override
	public QueryStoreResponse call() throws Exception {
		return this.associateRequest(token, userIp);
	}

	/**
	 * Ask a permanent identifier for the request identified by the token string
	 * 
	 * @param token  token of a head request
	 */
	private QueryStoreResponse associateRequest(String token, String userIp) {
		QueryStoreResponse result = null;
		Integer count = 0;
		try {
			String request = this.getRequest(token, this.getUserEmail(),
					this.userIp);

			// send request while no result or result is empty
			while ((result == null || QueryStoreResponse.STATUS_EMPTY.equals(result.getStatus())) 
					&& count < Settings.QUERYSTORE_MAX_RETRY.getInt()) {
				result = this.doRequest(request);
				Thread.sleep(Settings.QUERYSTORE_RETRY_TIMER.getInt());
				count++;
			}

		} catch (Exception e) {		
			log.debug(e);
			new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "", "Error while querying query store");

		}		
		return  result;
	}


	/**
	 * Send an HTTP request to the query store to get a UUID corresponding to a
	 * head token
	 * 
	 * @param requestString  get request string
	 * @return QueryStoreResponse
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

		//SSLHandShakeException (IOException) occurs if missing certificate
		HttpResponse response = httpClient.execute(request);
		Integer statusCode = response.getStatusLine().getStatusCode();
		
		if ( statusCode == 200) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String uuid;
			while ((uuid = rd.readLine()) != null) {
				result.append(uuid);
			}
			rd.close();

		} else {
			// empty response
			if (statusCode == 204) {
				return new QueryStoreResponse(QueryStoreResponse.STATUS_EMPTY, "", "");
			}
			// server error
			else if (statusCode >= 500) {
				return new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "", response.getStatusLine().getReasonPhrase());
			}
			// client error
			else if (statusCode >= 400 && statusCode < 500) {
				return new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "",  response.getStatusLine().getReasonPhrase());
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
		result = result + Parameter.QUERYTOKEN.getText() +"=" + token 
				+ "&"+ Parameter.EMAIL.getText() +"=" + email
				+ "&" + Parameter.USERIP.getText() + "=" + userIp
				+ "&" + Parameter.USEDCLIENT.getText() + "=" + 
				URLEncoder.encode(
						Settings.PORTAL_USER_AGENT.get()
						+"-"+Settings.PORTAL_VERSION.get(), "UTF-8");
		return result;
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
			return Settings.DEFAULT_USER_MAIL.get();
	}	
}
