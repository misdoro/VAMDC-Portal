package org.vamdc.portal.session.consumers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.faces.model.SelectItem;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.registry.RegistryFacade;


@Name("consumers")
@Scope(ScopeType.PAGE)
public class Consumers {

	@In(create=true) RegistryFacade registryFacade;

	private String selectedIvoaID;
	
	private Map<String,Boolean> queries = new HashMap<String,Boolean>();
	
	private Future<URL> consumerLocation;

	public List<SelectItem> getConsumers(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for (String ivoaID:registryFacade.getConsumerIvoaIDs()){
			result.add(new SelectItem(ivoaID,registryFacade.getResourceTitle(ivoaID)));
		}
		return result;
	}

	public void setSelectedConsumer(String ivoaID){
		this.selectedIvoaID = ivoaID;
	}

	public String getSelectedConsumer(){
		return selectedIvoaID;
	}

	public Map<String,Boolean> getQueries() {
		return queries;
	}

	public void setQueries(Map<String,Boolean> queries) {
		this.queries = queries;
	}

	public void process(){
		List<URL> nodes = new ArrayList<URL>();
		for (String req:queries.keySet()){
			if (queries.get(req)){
				try {
					nodes.add(new URL(req));
				} catch (MalformedURLException e) {}
			}
		}
		
		URL consumer = registryFacade.getConsumerService(selectedIvoaID);
		
		if (nodes.size()>0 && consumer!=null){
			for(URL node:nodes)
				System.out.println(node+" to "+consumer);
			ExecutorService executor = Executors.newSingleThreadExecutor();
			consumerLocation = executor.submit(new PostRequest(consumer,nodes));
			executor.shutdown();
		}
	}
	
	public boolean isDone(){
		return (consumerLocation!=null && consumerLocation.isDone() && !consumerLocation.isCancelled());
	}
	
	public boolean isProcessing(){
		return ((consumerLocation!=null && !consumerLocation.isDone()));
	}
	
	public boolean isOk(){
		return (isDone() && !isErrorHappened());
	}
	
	public boolean isErrorHappened(){
		if (isDone()){
			try{
				consumerLocation.get();
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}
	
	public String getError(){
		if (isDone()){
			try{
				consumerLocation.get();
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		return "";
	}
	
	public String getLocation(){
		URL result = null;
		if (isDone())
			try {
				result= consumerLocation.get();
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		if (result!=null)
			return result.toExternalForm();
		return "";
	}
	
}
