package org.vamdc.portal.entity.query;

public enum CountHeader {
	SPECIES("VAMDC-COUNT-SPECIES"),
	STATES("VAMDC-COUNT-STATES"),
	PROCESSES(""),
	RADIATIVE("VAMDC-COUNT-RADIATIVE"),
	NONRADIATIVE("VAMDC-COUNT-NONRADIATIVE"),
	COLLISIONS("VAMDC-COUNT-COLLISIONS"),
	;
	private final String headerName;
	
	private CountHeader(String headerName){
		this.headerName = headerName;
	}

	public String getHeaderName() {
		return headerName;
	}
	
}
