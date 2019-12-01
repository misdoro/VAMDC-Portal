package org.vamdc.portal.session.queryLog;

public class EmptyResponse implements ResponseInterface{

	public final static String IVOA_ID = "ivo://vamdc/none";
	
	@Override
	public String getNodeIVOAId() {
		return IVOA_ID;
	}

	@Override
	public String getStatsString() {
		return "No node responded";
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
