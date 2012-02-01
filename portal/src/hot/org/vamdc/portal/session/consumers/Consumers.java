package org.vamdc.portal.session.consumers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.registry.RegistryFacade;


@Name("consumers")
@Scope(ScopeType.EVENT)
public class Consumers {

	@In(create=true) RegistryFacade registryFacade;

	private String selectedIvoaID;
	
	private Map<String,Boolean> queries = new HashMap<String,Boolean>();

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
		for (String req:queries.keySet()){
			if (queries.get(req))
				System.out.println(req);
		}
	}
	
}
