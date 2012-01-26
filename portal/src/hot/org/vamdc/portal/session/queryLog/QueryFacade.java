package org.vamdc.portal.session.queryLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;

public class QueryFacade {

	private final Query query; 
	
	private final List<ResponseFacade> respondedNodes ;
	
	
	public QueryFacade(Query query){
		this.query = query;
		respondedNodes = new ArrayList<ResponseFacade>();
		for (HttpHeadResponse node:query.getResponses()){	
			respondedNodes.add(new ResponseFacade(node));
		}
		
	}
	
	public String getQueryString(){
		return query.getQueryString();
	}
	
	public String getComments(){
		return query.getComments();
	}
	
	public  List<ResponseFacade> getResponses(){
		return Collections.unmodifiableList(respondedNodes);
	}
	
}
