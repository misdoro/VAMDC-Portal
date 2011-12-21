package org.vamdc.portal.session.queryLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.query.RespondedNode;

public class QueryFacade {

	private final Query query; 
	
	private final List<ResponseFacade> respondedNodes ;
	
	
	public QueryFacade(Query query){
		this.query = query;
		respondedNodes = new ArrayList<ResponseFacade>();
		for (RespondedNode node:query.getResponses()){	
			respondedNodes.add(new ResponseFacade(node,query));
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
