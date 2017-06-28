package org.vamdc.portal.session.queryLog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public String getDate(){
		Date queryDate = query.getDate(); 
		if (queryDate!=null)
			return DateFormat.getDateInstance(DateFormat.MEDIUM).format(queryDate);
		return "";
	}
	
	public  List<ResponseInterface> getResponses(){
		List<ResponseInterface> respondedNodes = new ArrayList<ResponseInterface>();
		for (HttpHeadResponse node:query.getResponses()){	
			respondedNodes.add(new ResponseFacade(node));
		}
		if (respondedNodes.isEmpty())
			respondedNodes.add(new EmptyResponse());
		return respondedNodes;
	}
	
	public String getId(){
		if (query.getQueryID()!=null)
			return idPrefix+query.getQueryID().toString();
		else return "";
	}
	
}
