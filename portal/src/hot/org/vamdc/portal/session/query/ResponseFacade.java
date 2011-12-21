package org.vamdc.portal.session.query;

import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.query.RespondedNode;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.RegistryCommunicationException;

public class ResponseFacade {

	private RespondedNode response;
	private Query query;
	
	public ResponseFacade (RespondedNode response, Query parent){
		this.response = response;
		this.query = parent;
	}
	
	public String getNode(){
		try {
			return Client.INSTANCE.get().getResourceMetadata(response.getNodeIvoaID()).getTitle();
		} catch (RegistryCommunicationException e) {
			return "";
		}
	}
	
	public String getStats(){
		return response.getRecordID().toString();
	}
	
	public String getXsamsURL(){
		try {
			String result = Client.INSTANCE.get().getVamdcTapURL(response.getNodeIvoaID()).toString();
			result += query.getQueryString();
			return result;
		} catch (RegistryCommunicationException e) {
			return "";
		}
	}
	
}
