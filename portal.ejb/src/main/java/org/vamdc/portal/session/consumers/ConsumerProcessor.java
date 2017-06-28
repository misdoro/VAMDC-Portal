package org.vamdc.portal.session.consumers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.seam.Component;
import org.vamdc.portal.async.FutureTask;
import org.vamdc.portal.registry.RegistryFacade;

public class ConsumerProcessor implements Consumer, FutureTask{
	
	RegistryFacade registryFacade;	
	private URL consumer;
	private URL data;
	private Future<URL> location;
	
	
	public ConsumerProcessor(String consumer, String data){
		registryFacade = (RegistryFacade) Component.getInstance("registryFacade");
		try {
			this.consumer = registryFacade.getConsumerServiceURL(consumer);
			this.data = new URL(data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * execute consumer
	 */
	public void process() {	
		List<URL> nodes = new ArrayList<URL>();
		nodes.add(data);		
		if (consumer != null) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			location = executor
					.submit(new PostRequest(consumer, nodes));
			executor.shutdown();
		}
	}
	
	/**
	 * return consumer result url as a string
	 */
	public String getLocation(){
		String result = "";
		try {
			result = this.location.get().toExternalForm();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean isDone() {
		return (location != null && location.isDone() && !location
				.isCancelled());
	}
	
	public boolean isProcessing() {
		return (location != null && !location.isDone());
	}
	
	public boolean isOk() {
		return (isDone() && !isErrorHappened());
	}

	public boolean isErrorHappened() {
		if (isDone()) {
			try {
				location.get();
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	public String getError() {
		if (isDone()) {
			try {
				location.get();
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		return "";
	}
}
