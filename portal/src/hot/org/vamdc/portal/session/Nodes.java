package org.vamdc.portal.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ivoa.xml.voresource.v1.Resource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;

@Name("nodes")
@Scope(ScopeType.STATELESS)
public class Nodes
{

    private Registry registry;

    public Nodes(){
    	this.registry=Client.INSTANCE.get();
    }
    
    public final class Node{
    	private final String ivoaID;
    	private Resource node;
    	
    	private Node(String id){
    		this.ivoaID = id;
    		try {
				this.node=registry.getResourceMetadata(ivoaID);
			} catch (RegistryCommunicationException e) {
				this.node=null;
			}
    	}
    	
    	public String getName(){
    		if (node!=null)
				return node.getTitle();
    		return ivoaID;
    	}
    	
    	public String getSite(){
    		if (node!=null && node.getContent()!=null)
    			return node.getContent().getReferenceURL();
    		return ivoaID;
    	}
    	
    	public String getDescription(){
    		if (node!=null && node.getContent()!=null)
				return node.getContent().getDescription();
    		return ivoaID;
    	}
    	
    	public String getMaintainer(){
    		if (node!=null && node.getCuration()!=null && node.getCuration().getContact()!=null)
				return node.getCuration().getContact().get(0).getEmail();
			return ivoaID;
    	}
    	
    	public String getStatus(){
    		return "Unknown";
    	}
    	
    }

    public List<Node> getList(){
    	List<Node> result = new ArrayList<Node>();
    	
    	if (registry==null)
    		return result;
    	
    	Collection<String> ivoaIDs=null;
		try {
			ivoaIDs = registry.getIVOAIDs(Service.VAMDC_TAP);
		} catch (RegistryCommunicationException e) {
		}
    	
		if (ivoaIDs==null)
    		return result;
    	
		for (String id:ivoaIDs){
    		result.add(new Node(id));
    	}
    	
		return result;
    }
  
}
