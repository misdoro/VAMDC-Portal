package org.vamdc.portal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vamdc.portal.Settings;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;
import org.vamdc.registry.client.RegistryFactory;
import org.vamdc.registry.client.Registry.Service;

class RegistryThread implements Runnable{

	private volatile boolean running=true;
	
	@Override
	public void run() {
		while(running){
			try {
				reloadRegistry();
				Thread.sleep(Settings.REGISTRY_UPDATE_INTERVAL.getInt());
			} catch (RegistryCommunicationException e) {
				System.err.println("Registry communication problem"+e.getMessage());
				e.printStackTrace();
				try {
					Thread.sleep(Settings.REGISTRY_RETRY_INTERVAL.getInt());
				} catch (InterruptedException e1) {
				}
			} catch (InterruptedException e) {
			}
		}
		System.out.println("registry client thread interrupted");
	}

	public void stop(){
		running=false;
	}
	
	void reloadRegistry() throws RegistryCommunicationException {
		try{
			Registry newInstance = getRegistry();
			if (verifyRegistry(newInstance))
				Client.INSTANCE.set(newInstance);
		}catch (Exception e){
			if (!(e instanceof RegistryCommunicationException)){
				e.printStackTrace();
				throw new RegistryCommunicationException(e.getMessage());
			}
			else 
				throw (RegistryCommunicationException)e;
		}
	}
	
	private Registry getRegistry() throws Exception{
		Registry result = null;
		Exception err = null;
		for (String registryUrl:getRegistryMirrors()){
			try{
				result = RegistryFactory.getClient(registryUrl);
			}catch (Exception e){
				err=e;
			}
			if (verifyRegistry(result))
				return result;
		}
		if (err==null)
			throw new RegistryCommunicationException("There was some problem to connect to the registry");
		throw err;
	}

	private boolean verifyRegistry(Registry newInstance) {
		return newInstance!=null && newInstance.getIVOAIDs(Service.VAMDC_TAP).size()>0;
	}
	
	private Collection<String> getRegistryMirrors(){
		List<String> result = new ArrayList<String>();
		String line = Settings.REGISTRY_URL.get();
		
		for (String url:line.split(";")){
			String tmp = url.trim();
			if (!tmp.isEmpty())
				result.add(tmp);
		}
		return result;
	}
	
}