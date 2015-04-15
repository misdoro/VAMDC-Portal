package org.vamdc.portal.session.queryLog;

public class EmptyResponse implements ResponseInterface{

	@Override
	public String getNodeIVOAId() {
		return "ivo://vamdc/none";
	}

	@Override
	public String getStatsString() {
		return "No nodes responded";
	}

	@Override
	public String getFullQueryURL() {
		return "";
	}

	@Override
	public String getId() {
		return "";
	}

	@Override
	public String getStatus() {
		return "";
	}

}
