package org.vamdc.portal.session.queryLog;

import java.util.UUID;

import org.vamdc.portal.entity.query.HttpHeadResponse;

public class ResponseFacade implements ResponseInterface {
	private HttpHeadResponse response;
	
	public ResponseFacade (HttpHeadResponse node){
		this.response=node;
		if (response==null)
			throw new IllegalArgumentException("Response is null!");
	}

	@Override
	public String getNodeIVOAId(){
		return response.getIvoaID();
	}
	
	@Override
	public String getStatsString(){
		if (response!=null)
			return "Sp: "+response.getSpecies()+" -st:"+response.getStates()+" - Pr:"+response.getProcesses();
		return "?";
	}
	
	@Override
	public String getFullQueryURL(){
		return response.getFullQueryURL();
	}
	
	@Override
	public String getId(){
		String result=null;
		if (response!=null && response.getRecordID()!=null){
			result = response.getRecordID().toString();
		}
		
		if (result==null || result.length()==0)
			result = UUID.randomUUID().toString();
		return result;
	}

	@Override
	public String getStatus() {
		return response.getStatus().toString();
	}
	
}
