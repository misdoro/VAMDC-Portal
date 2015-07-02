package org.vamdc.portal.registry;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.ivoa.xml.voresource.v1.Capability;
import net.ivoa.xml.voresource.v1.Interface;
import net.ivoa.xml.voresource.v1.Resource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.VamdcTapService;
import org.vamdc.xml.xsams_consumer.v1.XsamsConsumer;


@Name("registryFacade")
@Scope(ScopeType.STATELESS)
/**
 * Registry facade, wrapping and logging error messages from the registry client calls
 * @author doronin
 *
 */
public class RegistryFacade {


	private Registry registry = Client.INSTANCE.get();
	
	/**
	 * Used only for test purposes
	 */
	void forceUpdate(){
		Client.INSTANCE.forceUpdate();
		registry=Client.INSTANCE.get();
	}

	public Collection<String> getTapIvoaIDs(){
		return Collections.unmodifiableCollection(registry.getIVOAIDs(Service.VAMDC_TAP));
	}

	public Collection<Restrictable> getRestrictables(String ivoaID){
		return Collections.unmodifiableSet(registry.getRestrictables(ivoaID));
	}


	public String getResourceTitle(String ivoaID){
		Resource res = registry.getResourceMetadata(ivoaID);
		if (res!=null)
			return res.getTitle();
		return "";
	}

	public String getResourceDescription(String ivoaID){
		Resource res = registry.getResourceMetadata(ivoaID);
		if (res!=null && res.getContent()!=null)
			return res.getContent().getDescription();
		return "";
	}

	public Collection<String> getConsumerIvoaIDs() {
		return Collections.unmodifiableCollection(registry.getIVOAIDs(Service.CONSUMER));

	}

	public Resource getResource(String ivoaID) {
		return registry.getResourceMetadata(ivoaID);
	}

	public Collection<String> getNodeConsumers(String ivoaId){
		return registry.getProcessors(ivoaId);		
	}
	
	public Integer getConsumerNumberOfInputs(String consumerId){
		Integer result=null;
		net.ivoa.xml.voresource.v1.Service consumer = (net.ivoa.xml.voresource.v1.Service) getResource(consumerId);
		for (Capability cap:consumer.getCapability()){
			if (cap!= null && cap.getStandardID()!=null && cap.getStandardID().equalsIgnoreCase(
					Registry.Service.CONSUMER.getStandardID())){
				XsamsConsumer thisConsumer=(XsamsConsumer) cap;
				try{
					result=Integer.valueOf(thisConsumer.getNumberOfInputs());
				}catch(NumberFormatException e){
					result=1;
				}
			}
		}
		
		return result;
	}

	public URL getConsumerServiceURL(String ivoaID){
		URL result = null;
		net.ivoa.xml.voresource.v1.Service consumer = (net.ivoa.xml.voresource.v1.Service) getResource(ivoaID);
		if (ivoaID==null || ivoaID.length()==0 || consumer==null)
			return null;
		for (Capability cap:consumer.getCapability()){
			if (cap!= null && cap.getStandardID()!=null && cap.getStandardID().equalsIgnoreCase(
					Registry.Service.CONSUMER.getStandardID())){
				for (Interface interf:cap.getInterface()){
					if (interf instanceof net.ivoa.xml.vodataservice.v1.ParamHTTP){
						try {
							result= new URL(interf.getAccessURL().get(0).getValue());
						} catch (MalformedURLException e) {
						}
					}
				}
			}
		}
		return result;

	}

	public Collection<URL> getVamdcTapMirrors(String ivoaID) {
		if (ivoaID==null)
			return Collections.emptyList();
		Collection<VamdcTapService> mirrors = registry.getMirrors(ivoaID);
		Collection<URL> result = new ArrayList<URL>(mirrors.size());
		for (VamdcTapService mirror:mirrors){
			result.add(mirror.TAPEndpoint);
		}
		return result;
	}
}