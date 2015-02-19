package org.vamdc.portal.async;

import java.io.Serializable;

public class PdlResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4918209555478063181L;

	private String message;
	
	/**
	 * True when result can be displayed
	 */
	private Boolean ready = false;
	
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean isReady(){
		return ready;
	}
	
	public void setReady(Boolean ready){
		this.ready = ready;
	}
}
