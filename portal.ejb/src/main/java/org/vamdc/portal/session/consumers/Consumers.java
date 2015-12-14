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
import org.vamdc.portal.session.queryLog.EmptyResponse;

@Name("consumers")
@Scope(ScopeType.PAGE)
public class Consumers implements Serializable {

	private static final long serialVersionUID = -4206391044359168710L;

	@In(create = true)
	RegistryFacade registryFacade;

	private Map<String, List<SelectItem>> nodeConsumers;

	/**
	 * selected consumer ivoaid
	 */
	private String selectedConsumer = null;

	/**
	 * selected node ivoaid
	 */
	private String selectedNode = null;


	private Future<URL> consumerLocation;


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

	public String getSelectedNode() {
		return this.selectedNode;
	}

	public void setSelectedNode(String nodeId) {
		this.selectedNode = nodeId;
	}

	/**
	 * populates Map
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


	public boolean getConsumerSelected() {
		if (this.selectedConsumer == null)
			return false;
		return true;
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

	public void process(String value) {
		URL consumer = registryFacade.getConsumerServiceURL(selectedConsumer);

		List<URL> nodes = new ArrayList<URL>();

		try {
			nodes.add(new URL(value));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (consumer != null) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			consumerLocation = executor
					.submit(new PostRequest(consumer, nodes));
			executor.shutdown();
		}

	}

	public boolean isDone() {
		return (consumerLocation != null && consumerLocation.isDone() && !consumerLocation
				.isCancelled());
	}

	public boolean isProcessing() {
		return ((consumerLocation != null && !consumerLocation.isDone()));
	}

	public boolean isOk() {
		return (isDone() && !isErrorHappened());
	}

	public boolean isErrorHappened() {
		if (isDone()) {
			try {
				consumerLocation.get();
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	public String getError() {
		if (isDone()) {
			try {
				consumerLocation.get();
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		return "";
	}

	public String getLocation() {
		URL result = null;
		if (isDone())
			try {
				result = consumerLocation.get();

			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		if (result != null)
			return result.toExternalForm();
		return "";
	}
}
