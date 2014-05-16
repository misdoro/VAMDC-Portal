package org.vamdc.portal.species;

public class SpeciesResult {
	private String message;
	private String formattedResult;
	private Integer mirrorIndex=0;
	
	/**
	 * True when result can be displayed
	 */
	private Boolean ready = false;
	private Integer mirrorCount;
	
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
	public Integer getMirrorIndex(){
		return this.mirrorIndex;
	}
	
	public Integer getMirrorCount(){
		return this.mirrorCount;
	}
	public void setMirrorCount(Integer count){
		this.mirrorCount=count;
	}
	public void nextMirror(){
		this.mirrorIndex++;
	}
	
	public Boolean isReady(){
		return ready;
	}
	
	public void setReady(Boolean ready){
		this.ready = ready;
	}
}
