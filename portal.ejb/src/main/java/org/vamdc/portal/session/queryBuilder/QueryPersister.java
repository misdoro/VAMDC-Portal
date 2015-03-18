package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.query.HttpHeadResponse.Response;
import org.vamdc.portal.session.preview.PersistableQueryInterface;


public class QueryPersister {
	private PersistableQueryInterface queryManager;
	
	public QueryPersister(PersistableQueryInterface queryManager){
		this.queryManager = queryManager;
	}
	
	public Query constructQuery(){
		Query result=null;
		if (queryManager.getQueryData().getEditQueryId() !=null)
			result = queryManager.getQueryLog().getQuery(queryManager.getQueryData().getEditQueryId());
		else
			result = new Query();
		result.setComments(queryManager.getQueryData().getComments());
		result.setQueryString(queryManager.getQueryData().getQueryString());
		result.setResponses(selectRespondedNodes());
		result.setUser(queryManager.getUser());
		result.setDate(new Date());
		return result;
	}
	
	private List<HttpHeadResponse> selectRespondedNodes() {
		ArrayList<HttpHeadResponse> responses = new ArrayList<HttpHeadResponse>();
		
		for (HttpHeadResponse response:queryManager.getNodesResponse()){
			if (response.getStatus()==Response.OK)
				responses.add(response);
		}
		return responses;
	}
	
	
}
