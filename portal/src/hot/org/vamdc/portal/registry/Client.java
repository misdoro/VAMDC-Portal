package org.vamdc.portal.registry;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.vamdc.portal.Settings;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;
import org.vamdc.registry.client.RegistryFactory;

public enum Client {
	INSTANCE,
	;
	
	private Registry registry;
	
	Client(){	
		System.out.println("Starting the registry client thread");
		Executor exec = Executors.newSingleThreadExecutor();
		exec.execute(new RegistryThread());
	}

	public Registry get(){
		return registry;
	}
	
	void set(Registry newInstance){
		this.registry=newInstance;
	}
	
	private class RegistryThread implements Runnable{

		public void run() {
			System.out.println("Started the registry client thread");
			while(!Thread.interrupted()){
				try {
					System.out.println("Updating the registry");
					Registry newInstance = RegistryFactory.getClient(Settings.REGISTRY_URL.get());
					if (newInstance!=null && newInstance.getIVOAIDs(Service.VAMDC_TAP).size()>0)
						Client.INSTANCE.set(newInstance);
					Thread.sleep(Settings.REGISTRY_UPDATE_INTERVAL.getInt());
				} catch (RegistryCommunicationException e) {
				} catch (InterruptedException e) {
				}
				
			}
			System.out.println("registry client thread interrupted");
		}
		
	}
	
}
