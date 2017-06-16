package org.vamdc.portal.session.consumers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.component.UIOutput;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.portal.session.queryLog.EmptyResponse;

@Name("consumers")
@Scope(ScopeType.PAGE)
public class ConsumerRequestRegistry implements Serializable {

	private static final long serialVersionUID = -4206391044359168710L;

	@In(create = true)
	RegistryFacade registryFacade;

	/**
	 * map of consumers for each node	
	 */
	private Map<String, List<SelectItem>> nodeConsumers;

	/**
	 * Map of the last called consumer for each node
	 */
	private Map<String, Consumer> processedConsumers = new ConcurrentHashMap<String, Consumer>();
	
	/**
	 * selected consumer ivoaid
	 */
	private String selectedConsumer = null;

	/**
	 * Returns map of all consumers for each node
	 * @return
	 */
	public Map<String, List<SelectItem>> getNodeConsumers(){
		if(this.nodeConsumers == null){
			initNodeConsumers();
		}
		return this.nodeConsumers;
	}	

	/**
	 * populates nodeConsumers Map
	 * 
	 * @return
	 */
	private void initNodeConsumers() {
		Collection<String> nodes = registryFacade.getTapIvoaIDs();
		this.nodeConsumers = new HashMap<String, List<SelectItem>>();
		this.nodeConsumers.put(EmptyResponse.IVOA_ID, new ArrayList<SelectItem>());
		for (String node : nodes) {			
			List<SelectItem> consumers = new ArrayList<SelectItem>();

			// search processors for selected nodes
			Collection<String> visibleConsumers = new ArrayList<String>(
					registryFacade.getConsumerIvoaIDs());
			// Retain available consumers
			visibleConsumers.retainAll(registryFacade.getNodeConsumers(node));

			// recommended consumers
			for (String ivoaID : visibleConsumers) {
				consumers.add(new SelectItem(ivoaID, " ** "
						+ registryFacade.getResourceTitle(ivoaID)));
			}

			// other consumers
			for (String ivoaID : registryFacade.getConsumerIvoaIDs()) {
				if (visibleConsumers.contains(ivoaID) == false) {
					consumers.add(new SelectItem(ivoaID, registryFacade
							.getResourceTitle(ivoaID)));
				}
			}
			this.nodeConsumers.put(node, consumers);

		}
	}

	/**
	 * changes the currently selected consumer when user change selection in list
	 * @param event
	 */
	public void changeConsumerSelected(ActionEvent event) {
		if (event.getComponent().getParent() != null) {
			UIOutput el = (UIOutput) event.getComponent().getParent();
			this.setSelectedConsumer((String) el.getValue());
		}
	}

	
	public void setSelectedConsumer(String ivoaID) {
		this.selectedConsumer = ivoaID;
	}

	public String getSelectedConsumer() {
		return selectedConsumer;
	}

	/**
	 * ask consumer execution for nodeIvoaID on data at nodeQueryUrl
	 * @param nodeQueryUrl
	 * @param nodeIvoaID
	 */
	public void process(String nodeQueryUrl, String nodeIvoaID) {
		Consumer consumer = new ConsumerProcessor(selectedConsumer, nodeQueryUrl);
		this.processedConsumers.put(nodeIvoaID,  consumer);
		this.processedConsumers.get(nodeIvoaID).process();

	}
	
	public Map<String, Consumer> getConsumersByNode(){
		return this.processedConsumers;				
	}
}
