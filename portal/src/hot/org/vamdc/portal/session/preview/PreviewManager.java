package org.vamdc.portal.session.preview;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.registry.Client;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.nodeTree.NodeTree;
import org.vamdc.registry.client.RegistryCommunicationException;

@Name("preview")
@Scope(ScopeType.CONVERSATION)
public class PreviewManager {

	@Logger
	Log log;
	
	@In(create=true) NodeTree nodeTree;
	@In QueryData queryData;
	
	private Collection<Future<HttpHeadResponse>> nodeFutureResponses = new ArrayList<Future<HttpHeadResponse>>();
	
	public String initiate(){
		if (!queryData.isValid())
			return RedirectPage.QUERY;
		
		if (nodeFutureResponses.size()>0)
			return RedirectPage.PREVIEW;
		
		Collection<String> activeNodes = nodeTree.getActiveNodes();
		
		ExecutorService executor = Executors.newFixedThreadPool(activeNodes.size());
		
		for (String ivoaID:activeNodes){
			try{
				nodeFutureResponses.add(executor.submit(new PreviewThread(ivoaID,getQuery(ivoaID))));
				log.info("Submit preview thread to "+ivoaID);
			}catch (IllegalArgumentException e){
			}
		}
		
		log.info(""+nodeFutureResponses.size()+"head requests queued to be submitted for nodes");
		
		if (nodeFutureResponses.size()>0)
			return RedirectPage.PREVIEW;
		
		return RedirectPage.QUERY;
	}

	
	
	private URL getQuery(String ivoaID) {
		String query = queryData.getQueryString();
		URL baseURL;
		URL queryURL=null;
		try {
			baseURL = Client.INSTANCE.get().getVamdcTapURL(ivoaID);
			queryURL = new URL(baseURL+"sync?LANG=VSS2&REQUEST=doQuery&FORMAT=XSAMS&QUERY="+URLEncoder.encode(query,"UTF-8"));
			
		} catch (RegistryCommunicationException e) {
		} catch (MalformedURLException e) {
		} catch (UnsupportedEncodingException e) {
		}
		
		return queryURL;
	}
	
	
	public Collection<HttpHeadResponse> getNodes(){
		ArrayList<HttpHeadResponse> nodes = new ArrayList<HttpHeadResponse>();
		for (Future<HttpHeadResponse> task:nodeFutureResponses){
			if (task.isDone()&& !task.isCancelled()){
				try {
					HttpHeadResponse response = task.get();
					log.info("we have a response for"+response.getIvoaID());
					nodes.add(response);
				} catch (InterruptedException e) {
					log.info("interruptedException");
					e.printStackTrace();
				} catch (ExecutionException e) {
					log.info("ExecutionException");
					e.printStackTrace();
				}
			}
		}
		return Collections.unmodifiableList(nodes);
	}
	
	public boolean isDone(){
		for (Future<HttpHeadResponse> task:nodeFutureResponses){
			if (!task.isDone())
				return false;
		}
		return true;
	}
	
	public void cancel(){
		for (Future<HttpHeadResponse> task:nodeFutureResponses){
			if (!task.isDone())
				task.cancel(true);
		}
	}
	
}
