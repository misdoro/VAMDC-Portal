package org.vamdc.portal.session.preview;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.vamdc.portal.Settings;
import org.vamdc.portal.entity.query.HttpHeadResponse;


public class PreviewThread implements Callable<HttpHeadResponse>{

	private String ivoaID;
	private String query;
	private Collection<URL> mirrors;
	
	public PreviewThread(String ivoaID, Collection<URL> mirrors, String query){
		this.ivoaID = ivoaID;
		this.query = query;
		this.mirrors = mirrors;		
	}
	
	private URL getQuery(URL mirror) {
		URL result=null;
		try {
			result = new URL(mirror+"sync?LANG=VSS2&REQUEST=doQuery&FORMAT=XSAMS&QUERY="+URLEncoder.encode(query,"UTF-8"));
		} catch (MalformedURLException e) {
		} catch (UnsupportedEncodingException e) {
		}

		return result;
	}
	
	@Override
	public HttpHeadResponse call() throws Exception {
		HttpHeadResponse response =null;
		for (URL mirror:mirrors){
			URL queryURL = getQuery(mirror);
			HttpURLConnection connection = (HttpURLConnection) queryURL.openConnection();
		
			connection.setRequestMethod("HEAD");
			connection.setReadTimeout(Settings.HTTP_HEAD_TIMEOUT.getInt());
		
			response = new HttpHeadResponse(ivoaID,connection);
			if (response.isOk())
				return response;
		}
		return response;
	}

}
