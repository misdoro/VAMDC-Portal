package org.vamdc.portal.session.preview;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
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

	public void initiate(){
		if (nodeFutureResponses.size()>0)
			return;

		Collection<String> activeNodes = nodeTree.getActiveNodes();

		ExecutorService executor = Executors.newFixedThreadPool(activeNodes.size());

		for (String ivoaID:activeNodes){
			try{
				nodeFutureResponses.add(executor.submit(new PreviewThread(ivoaID,getQuery(ivoaID))));
			}catch (IllegalArgumentException e){
			}
		}
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
		TreeSet<HttpHeadResponse> nodes = new TreeSet<HttpHeadResponse>(new HttpHeadResponseComparator());
		for (Future<HttpHeadResponse> task:nodeFutureResponses){
			if (task.isDone()&& !task.isCancelled()){
				try {
					HttpHeadResponse response = task.get();
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
		return new ArrayList<HttpHeadResponse>(nodes);
	}

	private class HttpHeadResponseComparator implements Comparator<HttpHeadResponse>{

		public int compare(HttpHeadResponse o1, HttpHeadResponse o2) {
			if (o1==null || o2==null)
				return 0;
			Integer value1 = Integer.valueOf(o1.getStatus().ordinal());
			Integer value2 = Integer.valueOf(o2.getStatus().ordinal());
			int compare = value1.compareTo(value2);
				if (compare!=0)
						return compare;
				else if(o1.getProcesses()!=o2.getProcesses())
					return o2.getProcesses()-o1.getProcesses();
				else
					return o1.getIvoaID().compareTo(o2.getIvoaID());
				
		}
		

		
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
	
	public void clear(){
		cancel();
		nodeFutureResponses=new ArrayList<Future<HttpHeadResponse>>();
	}
	
	public String refine(){
		clear();
		return RedirectPage.QUERY;
	}

}
