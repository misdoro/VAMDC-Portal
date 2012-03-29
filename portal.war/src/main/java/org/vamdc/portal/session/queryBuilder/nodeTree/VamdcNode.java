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
	
	private QueryData queryData;
	
	private final String ivoaID;
	private final RegistryFacade registry;

	public class RestrictableComparator implements Comparator<Restrictable>{

		@Override
		public int compare(org.vamdc.dictionary.Restrictable o1,
				org.vamdc.dictionary.Restrictable o2) {
			return o1.name().compareTo(o2.name());
		}
		
	}

	public VamdcNode(RegistryFacade registryFacade, String id, QueryData queryData){
		this.ivoaID = id;
		this.registry=registryFacade;
		this.queryData = queryData;
		this.setData(this);
		
		
		Set<Restrictable> missingKeywords;
		if (queryData.getKeywords().size()>0){
			missingKeywords = EnumSet.copyOf(queryData.getKeywords());
		}else{
			missingKeywords = EnumSet.noneOf(Restrictable.class);
		}
		
		Set<Restrictable> keys = new TreeSet<Restrictable>(new RestrictableComparator());
		keys.addAll(registry.getRestrictables(ivoaID));
		missingKeywords.removeAll(keys);
		
		for (Restrictable key:keys)
			this.addChild(key, new RestrictableNode(key,queryData));
		
		for (Restrictable key:missingKeywords)
			this.addChild(key, new RestrictableNode(key,null));
	}



	public String getDescription(){ return registry.getResourceDescription(ivoaID); }

	public String getName(){ return registry.getResourceTitle(ivoaID); }

	public boolean isActive() {
		Collection<Restrictable> keywords = queryData.getKeywords();
		return (keywords.size()>0 && registry.getRestrictables(ivoaID).containsAll(keywords));
	}

	public boolean isMissing() {
		return false;
	}

	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().trim().length()>0);
	}

	@Override
	public String getType() {
		return TreeNodeElement.VAMDCNode;
	}
}
