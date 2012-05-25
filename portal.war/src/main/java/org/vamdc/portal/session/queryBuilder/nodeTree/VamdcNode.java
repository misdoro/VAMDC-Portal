package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.portal.session.queryBuilder.QueryData;


public class VamdcNode extends TreeNodeImpl<TreeNodeElement> implements TreeNodeElement{

	private static final long serialVersionUID = 7577402632944052661L;
	
	
	private final String ivoaID;
	private final RegistryFacade registry;
	private final boolean active;
	
	private final Collection<Restrictable> queryKeywords;
	private final Set<Restrictable> nodeKeywords;
	private final Set<Restrictable> missing;
	
	private String name;
	private String description;

	public VamdcNode(RegistryFacade registryFacade, String id, QueryData query){
		this.ivoaID = id;
		this.registry=registryFacade;
		this.setData(this);
		
		nodeKeywords = new TreeSet<Restrictable>();
		nodeKeywords.addAll(registry.getRestrictables(ivoaID));
		
		queryKeywords = query.getActiveKeywords();
		
		if (queryKeywords.size()>0){
			missing = EnumSet.copyOf(queryKeywords);
		}else{
			missing = EnumSet.noneOf(Restrictable.class);
		}
		
		missing.removeAll(nodeKeywords);
		
		this.active = (nodeKeywords!=null && queryKeywords!=null && queryKeywords.size()>0 && nodeKeywords.containsAll(queryKeywords));
	}

	private void initChildren() {
		for (Restrictable key:nodeKeywords){
			boolean keyIsActive = queryKeywords.contains(key);
			this.addChild(key, new RestrictableNode(key,keyIsActive,false));
		}
		
		for (Restrictable key:missing)
			this.addChild(key, new RestrictableNode(key,false,true));
	}

	public String getDescription(){ 
		if (description==null) 
			description = registry.getResourceDescription(ivoaID); 
		return description;
	}

	public String getName(){ 
		if (name==null)
			name= registry.getResourceTitle(ivoaID);
		return name;
	}
	
	public String getIvoaId(){ return this.ivoaID; }

	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().length()>0);
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isMissing(){
		return false;
	}

	@Override
	public String getType() {
		return TreeNodeElement.VAMDCNode;
	}
	
	@Override
	public boolean isLeaf(){
		return false;
	}
	
	@Override
	public Iterator<Map.Entry<Object, TreeNode<TreeNodeElement>>> getChildren() {
		initChildren();
		return super.getChildren();
	}
	
		
}
