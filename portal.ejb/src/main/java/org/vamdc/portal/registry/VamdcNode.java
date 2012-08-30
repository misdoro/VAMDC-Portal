package org.vamdc.portal.registry;

import net.ivoa.xml.voresource.v1.Resource;

import org.vamdc.registry.client.Registry;

/**
 * Class providing getters for some of node registration points of interest
 * @author doronin
 *
 */
public class VamdcNode {
	private final String ivoaID;
	private Resource node;
	private Registry registry;
	
	public VamdcNode(String id){
		this.registry=Client.INSTANCE.get();
		
		this.ivoaID = id;
		
		this.node=registry.getResourceMetadata(ivoaID);
		
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
		return "OK";
	}
	
}
