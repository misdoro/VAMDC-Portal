package org.vamdc.portal.registry;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.vamdc.portal.Settings;
import org.vamdc.registry.client.RegistryCommunicationException;
import org.vamdc.registry.client.Registry.Service;

public enum  AvailabilityMonitor {
	INSTANCE;

	private MonitorThread thread;
	private Map<String,Status> nodeStatus;

	private ArrayList<Future<Status>> nodeStatusResponse;

	public enum Status{
		OK,
		FAIL,
		TIMEOUT
	}

	public Status getStatus(String ivoaID){
		Status result = null;
		if (nodeStatus!=null)
			result = nodeStatus.get(ivoaID);
		if (result!=null)
			return result;
		return Status.FAIL;
	}

	public Map<String,Status> getStatusMap(){
		return nodeStatus;
	}

	private AvailabilityMonitor(){
		Executor exec = Executors.newSingleThreadExecutor();
		thread = new MonitorThread();
		exec.execute(thread);
	}

	private class MonitorThread implements Runnable{

		private volatile boolean running=true;

		public void run() {
			while(running){
				try {

					List<String> ivoaIDs;
					try {
						ivoaIDs = new ArrayList<String>(Client.INSTANCE.get().getIVOAIDs(Service.VAMDC_TAP));
					} catch (RegistryCommunicationException e) {
						ivoaIDs = Collections.emptyList();
					}

					if (ivoaIDs.size()==0)
						Thread.sleep(2000);
					
					if (ivoaIDs.size()>0){

						ExecutorService executor = Executors.newFixedThreadPool(ivoaIDs.size());
						for (String ivoaID:ivoaIDs){
							URL endpoint;
							try {
								endpoint = Client.INSTANCE.get().getAvailabilityURL(ivoaID);
								nodeStatusResponse.add(executor.submit(new AvailabilityClient(endpoint)));
							}catch (IllegalArgumentException e){
							}catch (RegistryCommunicationException e){
							}
						}

						boolean done=false;
						while (!done){
							done=true;
							Thread.sleep(1000);

							for (Future<Status> status:nodeStatusResponse){
								if (!status.isDone())
									done=false;
							}
						}

						Map<String,Status> resultMap=new HashMap<String,Status>(); 

						for (int i=0;i<ivoaIDs.size();i++){
							Future<Status> status = nodeStatusResponse.get(i);
							Status result;
							try {
								result = status.get();
							} catch (ExecutionException e) {
								result = Status.FAIL;
							}
							String ivoaID=ivoaIDs.get(i);
							if (status.isDone() && result!=null){
								resultMap.put(ivoaID, result);
							}
						}
						nodeStatus= Collections.unmodifiableMap(resultMap);
					}
					Thread.sleep(Settings.AVAILABILITY_MONITOR_INTERVAL.getInt());
				} catch (InterruptedException e) {
				}
			}
			System.out.println("registry client thread interrupted");
		}

		public void stop(){
			running=false;
		}

	}

	public void stop() {
		thread.stop();
	}

}
