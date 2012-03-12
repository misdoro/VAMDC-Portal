package org.vamdc.portal.session.queryLog;

public class EmptyResponse implements ResponseInterface{

	public String getNodeIVOAId() {
		return "ivo://vamdc/none";
	}

	public String getStatsString() {
		return "No nodes responded";
	}

	public String getFullQueryURL() {
		return "";
	}

	public String getId() {
		return "";
	}

}
