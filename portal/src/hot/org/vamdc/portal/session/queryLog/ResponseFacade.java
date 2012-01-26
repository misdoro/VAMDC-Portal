package org.vamdc.portal.session.queryLog;

import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.query.RespondedNode;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.RegistryCommunicationException;

public class ResponseFacade {

	private HttpHeadResponse response;
	private Query query;
	
	public ResponseFacade (HttpHeadResponse node, Query parent){
		this.response = node;
		this.query = parent;
	}

	public String getNode(){
		try {
			return Client.INSTANCE.get().getResourceMetadata(response.getIvoaID()).getTitle();
		} catch (RegistryCommunicationException e) {
			return "";
		}
	}
	
	public String getStats(){
		return response.getRecordID().toString();
	}
	
	public String getXsamsURL(){
		return response.getFullQueryURL();
	}
	
}
