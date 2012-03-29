package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;

import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.portal.session.queryBuilder.QueryData;


public class VamdcNode extends TreeNodeImpl<TreeNodeElement> implements TreeNodeElement{

	private static final long serialVersionUID = 7577402632944052661L;
	
	
	private final String ivoaID;
	private final RegistryFacade registry;
	private final boolean active;
	
	private String name;
	private String description;

	public class RestrictableComparator implements Comparator<Restrictable>{

		@Override
		public int compare(org.vamdc.dictionary.Restrictable o1,
				org.vamdc.dictionary.Restrictable o2) {
			return o1.name().compareTo(o2.name());
		}
		
	}

	public VamdcNode(RegistryFacade registryFacade, String id, QueryData query){
		this.ivoaID = id;
		this.registry=registryFacade;
		this.setData(this);
		
		
		Collection<Restrictable> queryKeywords = query.getActiveKeywords();
		
		Set<Restrictable> missingKeywords;
		if (queryKeywords.size()>0){
			missingKeywords = EnumSet.copyOf(queryKeywords);
		}else{
			missingKeywords = EnumSet.noneOf(Restrictable.class);
		}
		
		Set<Restrictable> keys = new TreeSet<Restrictable>();
		keys.addAll(registry.getRestrictables(ivoaID));
		missingKeywords.removeAll(keys);
		
		this.active = (keys!=null && queryKeywords!=null && queryKeywords.size()>0 && keys.containsAll(queryKeywords));
		
		for (Restrictable key:keys){
			boolean keyIsActive = queryKeywords.contains(key);
			this.addChild(key, new RestrictableNode(key,keyIsActive,false));
		}
		
		for (Restrictable key:missingKeywords)
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
}
