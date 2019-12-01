package org.vamdc.portal.session.queryLog;

public class QueryStoreResponse {
	
	private String status;
	private String uuid;
	private String errorMessage;
	public static final String STATUS_ERROR = "error";
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_UNKNOWN = "unknown";
	public static final String STATUS_EMPTY = "empty";

	public QueryStoreResponse(String status, String uuid, String errorMessage){
		this.status = status;
		this.uuid = uuid;
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
