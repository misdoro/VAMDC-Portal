package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;


public class VamdcNode implements TreeNode<TreeNodeElement>,TreeNodeElement{

	private QueryData queryData;

	private final String ivoaID;
	private final TreeNode<TreeNodeElement> root;
	private final Map<Object,TreeNode<TreeNodeElement>> restrictables = new HashMap<Object,TreeNode<TreeNodeElement>>();
	private final Registry registry;

	
	public VamdcNode(TreeNode<TreeNodeElement> root, Registry registry, String id, QueryData queryData){
		this.ivoaID = id;
		this.root = root;
		this.registry=registry;
		this.queryData = queryData;
		
		EnumSet<Restrictable> missingKeywords = EnumSet.copyOf(queryData.getKeywords());
		
		try {
			for (Restrictable key:registry.getRestrictables(ivoaID)){
				restrictables.put(key, new RestrictableNode(key,this, queryData));
				missingKeywords.remove(key);
			}
		}catch (RegistryCommunicationException e) { }

		for (Restrictable key:missingKeywords){
			restrictables.put(key, new RestrictableNode(key,this,null));
		}
		
	}

	private static final long serialVersionUID = 7577402632944052661L;


	public TreeNode<TreeNodeElement> getChild(Object key) { return restrictables.get(key); }

	public Iterator<Entry<Object, TreeNode<TreeNodeElement>>> getChildren() { return restrictables.entrySet().iterator(); }

	public TreeNodeElement getData() { return this; }

	public String getDescription(){ 
		try {
			return registry.getResourceMetadata(ivoaID).getContent().getDescription();
		} catch (RegistryCommunicationException e) {
			return "";
		} 
	}

	public String getType(){ return "vamdcNode"; }

	public String getName(){ return ivoaID; }

	public TreeNode<TreeNodeElement> getParent() { 
		System.out.println("getParent called for node"+ivoaID);
		return root; }

	public boolean isLeaf() { return false; }

	public void addChild(Object arg0, TreeNode<TreeNodeElement> arg1) {}
	public void removeChild(Object arg0) {}
	public void setParent(TreeNode<TreeNodeElement> arg0) {}
	public void setData(TreeNodeElement arg0) {}

	public boolean isActive() {
		try {
			Collection<Restrictable> keywords = queryData.getKeywords();
			return registry.getRestrictables(ivoaID).containsAll(keywords);
		} catch (RegistryCommunicationException e) {
			return false;
		}
	}

	public boolean isMissing() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().trim().length()>0);
	}
}
