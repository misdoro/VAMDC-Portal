package org.vamdc.portal.session.queryLog;

import org.vamdc.portal.entity.query.HttpHeadResponse;

public class ResponseFacade {
	private HttpHeadResponse response;
	
	public ResponseFacade (HttpHeadResponse node){
		this.response=node;
		if (response==null)
			throw new IllegalArgumentException("Response is null!");
	}

	public String getNode(){
		return response.getIvoaID();
	}
	
	public String getStats(){
		if (response!=null)
			return "Sp: "+response.getSpecies()+" -st:"+response.getStates()+" - Pr:"+response.getProcesses();
		return "?";
	}
	
	public String getFullQueryURL(){
		return response.getFullQueryURL();
	}
	
	
}
