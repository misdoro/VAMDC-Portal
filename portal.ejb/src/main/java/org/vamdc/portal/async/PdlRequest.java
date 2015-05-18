package org.vamdc.portal.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.vamdc.portal.Settings;

@Name("pdlRequest")
@Scope(ScopeType.CONVERSATION)
public class PdlRequest {
	/**
	 * VAMDC-TAP request
	 */
	private String query= new String();
	
	/**
	 * Email address from user for result notifications
	 */
	private String email = new String();
	
	/**
	 * pdl server does not send email notifications if false 
	 */
	private Boolean hasEmail = true;
	
	/**
	 * connections timeouts 
	 */
	private Integer CONNECT_TIMEOUT = 3000;
	private Integer READ_TIMEOUT = 3000;
	
	
	private String getQuery(String query) throws UnsupportedEncodingException{
		return "query="+URLEncoder.encode(query, "utf-8");
	}
	
	private String getEmail(String email) throws UnsupportedEncodingException{
		return "mail="+URLEncoder.encode(email, "utf-8");
	}
	
	private URL buildRequest() throws MalformedURLException, UnsupportedEncodingException{
		String result = Settings.PDL_SERVER_URL.get()+getQuery(this.query)+"&"+getEmail(this.email);
		if(hasEmail)
			return new URL(result);
		else
			return new URL(result+"&MailRequested=false");

	}
	
	
	@Asynchronous 
	public void execRequest(String query, String email, PdlResult resultBean){	
		String result = null;
		this.query = query;
		this.email = email;
        URL pdl;
		try {
			pdl = buildRequest() ;
			URLConnection yc = pdl.openConnection();
			yc.setConnectTimeout(CONNECT_TIMEOUT);
			yc.setReadTimeout(READ_TIMEOUT);

	        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    yc.getInputStream()));
			String inputLine;
			StringBuffer sbuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null){
				sbuffer.append(inputLine);
			}
			
			in.close();	
			
			result = sbuffer.toString();	

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();	
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			result = e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();
		} finally {
			resultBean.setMessage(result);
			resultBean.setReady(true);
		}
	}

}
