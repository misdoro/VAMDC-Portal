package org.vamdc.portal.registry;

public class SpeciesResult {
	private String message;
	private String formattedResult;
	
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
	public String getFormattedResult() {
		return formattedResult;
	}
	public void setFormattedResult(String formattedResult) {
		this.formattedResult = formattedResult;
	}
	
	public Boolean isReady(){
		return ready;
	}
	
	public void setReady(Boolean ready){
		this.ready = ready;
	}
}
