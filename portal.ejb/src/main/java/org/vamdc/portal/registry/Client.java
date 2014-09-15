package org.vamdc.portal.registry;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;

public enum Client {
	INSTANCE;
	
	private volatile Registry registry;
	private RegistryThread thread;
	
	Client(){
		registry = new EmptyRegistry();
		Executor exec = Executors.newSingleThreadExecutor();
		thread = new RegistryThread();
		exec.execute(thread);
	}
	
	public void forceUpdate(){
		try {
			RegistryThread thread2=new RegistryThread();
			thread2.reloadRegistry();
		} catch (RegistryCommunicationException e) {
			e.printStackTrace();
		}
	}

	public Registry get(){
		return registry;
	}
	
	void set(Registry newInstance){
		this.registry=newInstance;
	}
	
	void stopUpdates(){
		thread.stop();
	}
	
}
