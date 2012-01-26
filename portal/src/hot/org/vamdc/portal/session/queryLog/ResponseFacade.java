package org.vamdc.portal.session.queryLog;

import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.RegistryCommunicationException;

public class ResponseFacade {

	private HttpHeadResponse response;
	
	public ResponseFacade (HttpHeadResponse node){
		this.response = node;
	}

	public String getNode(){
		try {
			return Client.INSTANCE.get().getResourceMetadata(response.getIvoaID()).getTitle();
		} catch (RegistryCommunicationException e) {
			return "";
		}
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
