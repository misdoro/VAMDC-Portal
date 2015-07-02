package org.vamdc.portal.session.consumers;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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
public class Consumers implements Serializable{

	private static final long serialVersionUID = -4206391044359168710L;

	@In(create=true) RegistryFacade registryFacade;

	private String selectedIvoaID = null;
	private Boolean consumerSelected = false;
	
	//selected nodes requests 
	private Map<String,Boolean> queries = new HashMap<String,Boolean>();
	
	// selected nodes ids
	private Map<String, Boolean> selectedNodes = new HashMap<String, Boolean>();
	
	private Future<URL> consumerLocation;
	
	//list of processors
	private List<SelectItem> consumers = new ArrayList<SelectItem>();
	
	
	public List<SelectItem> getConsumers(){
		return consumers;
	}	
	
	public Integer getConsumersCount(){
		return consumers.size();
	}
	
	/**
	 * update list of processors and returns its size
	 * @return
	 */
    public Integer getConsumersUpdatedCount(){
    	return getConsumersUpdated().size();
    }
    
    private void clearConsumerSelection(){
    	consumerSelected = false;
    	consumers.clear();
    	consumerLocation = null;
    	selectedIvoaID = null;
    }    
	
    /**
     * update list of processors for the currently selected nodes
     * @return
     */
	public List<SelectItem> getConsumersUpdated(){

		List<SelectItem> result = new ArrayList<SelectItem>();
		if(getSelectedNodesCount() > 0){	
			//search processors for selected nodes	
			Collection<String> visibleConsumers = new ArrayList<String>(registryFacade.getConsumerIvoaIDs());
			for (Map.Entry<String,Boolean> node : selectedNodes.entrySet()){
				if (node.getValue()){
					//Retain available consumers	
					visibleConsumers.retainAll(registryFacade.getNodeConsumers(node.getKey()));
				}
			}
			
			//recommended consumers
			for (String ivoaID:visibleConsumers){
				result.add(new SelectItem(ivoaID," ** "+registryFacade.getResourceTitle(ivoaID)));
			}
			
			//other consumers
			for(String ivoaID : registryFacade.getConsumerIvoaIDs()){
				if(visibleConsumers.contains(ivoaID) == false){
					result.add(new SelectItem(ivoaID,registryFacade.getResourceTitle(ivoaID)));
				}
			}
		}	
		this.consumers = result;
		return this.consumers;
	}
    
    public Integer getSelectedNodesCount(){
        int result = 0;
        for (Map.Entry<String, Boolean> entry : this.queries.entrySet()){
            if((Boolean)entry.getValue())
                result++;
        }
        return result;
    }
    
    public String getSelectedNodes(){
    	StringBuilder ret=new StringBuilder();
    	for (Map.Entry<String, Boolean> entry : this.queries.entrySet()){
            if((Boolean)entry.getValue())
                ret.append(entry.getKey()+";");
        }
    	return ret.toString();
    }
    
    public boolean getConsumerSelected(){
    	return this.consumerSelected;
    }
    
	public void setSelectedConsumer(String ivoaID){
		this.consumerSelected = true;
		this.selectedIvoaID = ivoaID;
	}

	public String getSelectedConsumer(){
		return selectedIvoaID;
	}
	
	
	public Integer getSelectedConsumerNumberOfInput(){
		return registryFacade.getConsumerNumberOfInputs(selectedIvoaID);
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
		
		URL consumer = registryFacade.getConsumerServiceURL(selectedIvoaID);
		
		if (nodes.size()>0 && consumer!=null){
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
	
	public void updateNodeIds(String text){
		clearConsumerSelection();
		if(selectedNodes.containsKey(text)){
			selectedNodes.put(text, !selectedNodes.get(text));
		}else{
			selectedNodes.put(text, true);
		}		
	}
	
}
