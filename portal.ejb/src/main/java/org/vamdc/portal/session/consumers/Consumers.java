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

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.component.UIOutput;

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

	public String selectedItem;

	/**
	 * selected consumer ivoaid
	 */
	private String selectedConsumer = null;
	//private Boolean isConsumerSelected = false;

	//selected nodes requests 
	//private Map<String,Boolean> queries = new HashMap<String,Boolean>();
	private String selectedNode = null;

	// selected nodes ids
	private Map<String, Boolean> selectedNodes = new HashMap<String, Boolean>();

	private Future<URL> consumerLocation;

	//list of processors
	private List<SelectItem> consumers = new ArrayList<SelectItem>();

	public String getSelectedItem(){
		return this.selectedItem;
	}

	public void setSelectedItem(String selectedItem){
		this.selectedItem = selectedItem;
	}


	public List<SelectItem> getConsumers(){
		return consumers;
	}	

	public String getSelectedNode(){
		return this.selectedNode;
	}

	public void setSelectedNode(String nodeId){
		this.selectedNode = nodeId;
	}

	/**
	 * returns a list of all available consumers
	 * parameters are not accepted with jsf1.2 so it is not possible to restrict to consumers related to a
	 * given node yet
	 * @return
	 */
	public List<SelectItem> getAllConsumers(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		Collection<String> visibleConsumers = new ArrayList<String>(registryFacade.getConsumerIvoaIDs());
		//recommended consumers
		for (String ivoaID:visibleConsumers){
			result.add(new SelectItem(ivoaID, registryFacade.getResourceTitle(ivoaID)));
		}
		return result;
	}

	public Integer getConsumersCount(){
		return consumers.size();
	}

	/**
	 * update list of processors and returns its size
	 * @return
	 */
	/* public Integer getConsumersUpdatedCount(){
    	return getConsumersUpdated().size();
    }
	 */
	private void clearConsumerSelection(){
		//isConsumerSelected = false;
		consumers.clear();
		consumerLocation = null;
		selectedConsumer = null;
		selectedNode = null;
	}    

	/**
	 * update list of processors for the currently selected nodes
	 * @return
	 */
	/*
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
	}*/
	/*  
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
	 */
	public boolean getConsumerSelected(){
		if(this.selectedConsumer == null)
			return false;
		return true;
		//return this.isConsumerSelected;
	}

	public void changeConsumerSelected(ActionEvent event){
		System.out.println("### listener");
		if( event.getComponent().getParent() != null ){
			UIOutput el = (UIOutput)event.getComponent().getParent();
			this.setSelectedConsumer((String)el.getValue());
			System.out.println("### set value : "+this.selectedConsumer);
		}	   
	}   


	public void setSelectedConsumer(String ivoaID){
		//this.isConsumerSelected = true;
		this.selectedConsumer = ivoaID;
	}

	public String getSelectedConsumer(){
		return selectedConsumer;
	}


	public Integer getSelectedConsumerNumberOfInput(){
		return registryFacade.getConsumerNumberOfInputs(selectedConsumer);
	}
	/*
	public Map<String,Boolean> getQueries() {
		return queries;
	}

	public void setQueries(Map<String,Boolean> queries) {
		this.queries = queries;
	}
	 */
	public void process(String value){	
		URL consumer = registryFacade.getConsumerServiceURL(selectedConsumer);
		
		List<URL> nodes = new ArrayList<URL>();

		try {
			nodes.add(new URL(value));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (consumer!=null){
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
