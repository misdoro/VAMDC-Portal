package org.vamdc.portal.registry;

import java.net.MalformedURLException;
import java.net.URL;
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


@Name("registryFacade")
@Scope(ScopeType.STATELESS)
/**
 * Registry facade, wrapping and logging error messages from the registry client calls
 * @author doronin
 *
 */
public class RegistryFacade {


	private Registry registry = Client.INSTANCE.get();

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

	public URL getVamdcTapURL(String ivoaID) {
		URL result = registry.getVamdcTapURL(ivoaID);
		if (result!=null)
			return result;
		try {
			return new URL("http://vamdc.org/");
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public Collection<String> getConsumerIvoaIDs() {
		return Collections.unmodifiableCollection(registry.getIVOAIDs(Service.CONSUMER));
		
	}

	public Resource getResource(String ivoaID) {
		return registry.getResourceMetadata(ivoaID);
	}
	
	public URL getConsumerService(String ivoaID){
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
}