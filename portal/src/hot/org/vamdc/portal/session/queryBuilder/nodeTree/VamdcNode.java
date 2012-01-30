package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.portal.session.queryBuilder.QueryData;


public class VamdcNode implements TreeNode<TreeNodeElement>,TreeNodeElement{

	private QueryData queryData;

	private final String ivoaID;
	private final TreeNode<TreeNodeElement> root;
	private final Map<Object,TreeNode<TreeNodeElement>> restrictables = new HashMap<Object,TreeNode<TreeNodeElement>>();
	private final RegistryFacade registry;


	public VamdcNode(TreeNode<TreeNodeElement> root, RegistryFacade registryFacade, String id, QueryData queryData){
		this.ivoaID = id;
		this.root = root;
		this.registry=registryFacade;
		this.queryData = queryData;

		EnumSet<Restrictable> missingKeywords;
		if (queryData.getKeywords().size()>0){
			missingKeywords = EnumSet.copyOf(queryData.getKeywords());
		}else{
			missingKeywords = EnumSet.noneOf(Restrictable.class);
		}

		for (Restrictable key:registry.getRestrictables(ivoaID)){
			restrictables.put(key, new RestrictableNode(key,this, queryData));
			missingKeywords.remove(key);
		}

		for (Restrictable key:missingKeywords){
			restrictables.put(key, new RestrictableNode(key,this,null));
		}

	}

	private static final long serialVersionUID = 7577402632944052661L;


	public TreeNode<TreeNodeElement> getChild(Object key) { return restrictables.get(key); }

	public Iterator<Entry<Object, TreeNode<TreeNodeElement>>> getChildren() { return restrictables.entrySet().iterator(); }

	public TreeNodeElement getData() { return this; }

	public String getDescription(){ 
		return registry.getResourceDescription(ivoaID); 
	}

	public String getType(){ return "vamdcNode"; }

	public String getName(){ return registry.getResourceTitle(ivoaID); }

	public TreeNode<TreeNodeElement> getParent() { 
		System.out.println("getParent called for node"+ivoaID);
		return root; }

	public boolean isLeaf() { return false; }

	public void addChild(Object arg0, TreeNode<TreeNodeElement> arg1) {}
	public void removeChild(Object arg0) {}
	public void setParent(TreeNode<TreeNodeElement> arg0) {}
	public void setData(TreeNodeElement arg0) {}

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
}
