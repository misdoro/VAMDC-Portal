package org.vamdc.portal.session.preview;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.vamdc.portal.Settings;
import org.vamdc.portal.entity.query.HttpHeadResponse;

public class PreviewThread implements Callable<HttpHeadResponse> {
	

	@Logger
	Log log;

	private String ivoaID;
	private String query;
	private Collection<URL> mirrors;

	public PreviewThread(String ivoaID, Collection<URL> mirrors, String query) {
		this.ivoaID = ivoaID;
		this.query = query;
		this.mirrors = mirrors;
	}

	private URL getQuery(URL mirror) {
		URL result = null;
		try {
			result = new URL(mirror
					+ "sync?LANG=VSS2&REQUEST=doQuery&FORMAT=XSAMS&QUERY="
					+ URLEncoder.encode(query, "UTF-8"));
		} catch (MalformedURLException e) {
			log.info(e);
		} catch (UnsupportedEncodingException e) {
			log.info(e);
		}

		return result;
	}
	
	private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		HttpURLConnection connection = null;

		
		//http request
		if (!url.getProtocol().equals("https")) {
			connection = (HttpURLConnection) url.openConnection();
		} 
		//https request
		else {
			connection = (HttpsURLConnection) url.openConnection();
		}		
		
		return connection;
	}

	/**
	 * Send a HEAD request to a node by testing its mirrors
	 */
	@Override
	public HttpHeadResponse call() throws Exception {
		HttpHeadResponse response = null;
		for (URL mirror : mirrors) {
			HttpURLConnection connection = null;
			connection = this.getHttpURLConnection(getQuery(mirror));
			connection.setRequestMethod("HEAD");
			connection.setReadTimeout(Settings.HTTP_HEAD_TIMEOUT.getInt());
			/*if(connection.getResponseCode() == 301 || connection.getResponseCode() == 302) {
				connection.disconnect();
				String newUrl = connection.getHeaderField("Location");  	
				System.out.println("### try new url : " + newUrl);
				connection = this.getHttpURLConnection(new URL(newUrl));
				connection.setRequestMethod("HEAD");
				connection.setReadTimeout(Settings.HTTP_HEAD_TIMEOUT.getInt());
			}*/
			response = new HttpHeadResponse(ivoaID, connection);
			
			if (response.isOk())
				return response;
		}

		return response;
	}

}
