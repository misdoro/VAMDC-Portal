package org.vamdc.portal.session.consumers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.selection.SimpleSelection;
import org.vamdc.portal.registry.RegistryFacade;


@Name("consumers")
@Scope(ScopeType.CONVERSATION)
public class Consumers {

	@In(create=true) RegistryFacade registryFacade;

	private String selectedIvoaID;

	private class SelectedResponse {
		private String queryID;
		private String nodeIvoaID;
		
		SelectedResponse(String queryID,String nodeIvoaID){
			this.queryID = queryID;
			this.nodeIvoaID = nodeIvoaID;
		}
	}
	
	private List<SelectedResponse> selected = new ArrayList<SelectedResponse>();

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

	@End
	public void process(){
		System.out.println("selected" +selectedIvoaID);
		System.out.println("nodes");
		for (SelectedResponse resp:selected){
			System.out.println(resp.queryID+resp.nodeIvoaID);
		}
	}
	
	public boolean isSelected(String queryID,String nodeIvoaID){
		for (SelectedResponse response:selected){
			if (response.nodeIvoaID.equals(nodeIvoaID)&&response.queryID.equals(queryID))
				return true;
		}
		return false;
	}
	
	public void deselect(String queryID,String nodeIvoaID){
		for (int i=0;i<selected.size();i++){
			SelectedResponse response = selected.get(i);
			if (response.nodeIvoaID.equals(nodeIvoaID)&&response.queryID.equals(queryID))
				selected.remove(i);
		}
	}
	
	@Begin(join=true)
	public void select(String queryID, String nodeIvoaID){
		if (!isSelected(queryID,nodeIvoaID))
			selected.add(new SelectedResponse(queryID, nodeIvoaID));
	}
	
}
