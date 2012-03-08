package org.vamdc.portal.session.queryLog;

import java.util.ArrayList;
import java.util.List;

import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;

public class QueryFacade {

	private final Query query; 
	
	private final String idPrefix;
	
	public QueryFacade(Query query, String idPrefix){
		this.query = query;
		
		this.idPrefix = idPrefix;
	}
	
	public String getQueryString(){
		return query.getQueryString();
	}
	
	public String getComments(){
		return query.getComments();
	}
	
	public  List<ResponseInterface> getResponses(){
		List<ResponseInterface> respondedNodes = new ArrayList<ResponseInterface>();
		for (HttpHeadResponse node:query.getResponses()){	
			respondedNodes.add(new ResponseFacade(node));
		}
		if (respondedNodes.size()==0)
			respondedNodes.add(new EmptyResponse());
		return respondedNodes;
	}
	
	public String getId(){
		if (query.getQueryID()!=null)
			return idPrefix+query.getQueryID().toString();
		else return "";
	}
	
}
