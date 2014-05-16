package org.vamdc.portal.exception;

import org.apache.http.HttpException;

public class PortalHttpException extends HttpException{

	private static final long serialVersionUID = 1L;
	private Integer code;
	
	public PortalHttpException (String message) {
		super(message);
	}
	
	public PortalHttpException (String message, Integer code) {
		super(message);
		this.code = code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	
}
