package org.vamdc.portal.session.queryLog;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.vamdc.portal.async.FutureTask;

public class QueryStoreProcessor implements FutureTask{	
	
	private Future<QueryStoreResponse> response;
	
	@Logger
	private Log log = Logging.getLog(QueryStoreProcessor.class);;
	
	private String token;	
	
	public QueryStoreProcessor(String token ){
		this.token = token;
	}	
	
	@Override
	public void process(){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		String ipaddress = ((QueryStoreRequestRegistry) Component.getInstance("querystore")).getIpAdress();
		response = executor				
				.submit(new QueryStoreRequest(token, ipaddress));
		executor.shutdown();	
	}

	/**
	 * return result of querystore for a request
	 * 
	 * @return
	 **/	
	public QueryStoreResponse getResponse() {
		QueryStoreResponse result;

		try {
			result =  this.response.get();
		} catch (InterruptedException e) {
			log.debug(e);
			result = new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "", e.getMessage());
		} catch (ExecutionException e) {			
			e.printStackTrace();
			log.debug(e);
			result = new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "", e.getMessage());
		}catch (NullPointerException e) {
			log.debug(e);
			result = new QueryStoreResponse(QueryStoreResponse.STATUS_ERROR, "", e.getMessage());
		}

		return result;
	}		
	
	@Override
	public boolean isDone() {
		return (response != null && response.isDone() && !response.isCancelled());
	}
	
	@Override
	public boolean isProcessing() {
		return (response != null && !response.isDone());
	}
	
	@Override
	public boolean isOk() {
		return (isDone() && !isErrorHappened());
	}
	
	@Override
	public boolean isErrorHappened() {
		if (isDone()) {
			try {
				response.get();
			} catch (Exception e) {
				log.debug(e);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getError() {
		if (isDone()) {
			try {
				response.get();
			} catch (Exception e) {
				log.debug(e);
				return e.getMessage();
			}
		}
		return "";
	}
}
