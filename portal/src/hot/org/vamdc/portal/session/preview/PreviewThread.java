package org.vamdc.portal.session.preview;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.vamdc.portal.Settings;


public class PreviewThread implements Callable<HttpHeadResponse>{

	private String ivoaID;
	private URL queryURL;
	
	public PreviewThread(String ivoaID, URL queryURL){
		this.ivoaID = ivoaID;
		this.queryURL = queryURL;
		if (queryURL==null)
			throw new IllegalArgumentException("Query URL cannot be null for the preview thread");
		if (ivoaID==null)
			throw new IllegalArgumentException("IVOA ID cannot be null for the preview thread");
		
	}
	
	public HttpHeadResponse call() throws Exception {
		
		HttpURLConnection connection = (HttpURLConnection) queryURL.openConnection();
		
		connection.setRequestMethod("HEAD");
		connection.setReadTimeout(Settings.HTTP_HEAD_TIMEOUT.getInt());
		
		HttpHeadResponse request = new HttpHeadResponse(ivoaID,connection);
		
		return request;
		
	}

}
