package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;

public class VamdcNode implements TreeNode<TreeNodeElement>,TreeNodeElement{

	private final String ivoaID;
	private final TreeNode<TreeNodeElement> root;
	private final String description;
	private final Map<Object,TreeNode<TreeNodeElement>> restrictables = new HashMap<Object,TreeNode<TreeNodeElement>>();

	public VamdcNode(TreeNode<TreeNodeElement> root, Registry registry, String id){
		this.ivoaID = id;
		this.root = root;
		
		String desc="";
		try {
			desc = registry.getResourceMetadata(id).getContent().getDescription();
			for (Restrictable key:registry.getRestrictables(ivoaID)){
				restrictables.put(key.name(), new RestrictableNode(key,this));
			}
		}catch (RegistryCommunicationException e) { }
		
		this.description = desc;
		
	}

	private static final long serialVersionUID = 7577402632944052661L;


	public TreeNode<TreeNodeElement> getChild(Object arg0) { return restrictables.get(arg0); }

	public Iterator<Entry<Object, TreeNode<TreeNodeElement>>> getChildren() { return restrictables.entrySet().iterator(); }

	public TreeNodeElement getData() { return this; }

	public String getDescription(){ return description; }

	public String getType(){ return "vamdcNode"; }

	public String getName(){ return ivoaID; }

	public TreeNode<TreeNodeElement> getParent() { return root; }

	public boolean isLeaf() { return false; }

	public void addChild(Object arg0, TreeNode<TreeNodeElement> arg1) {}
	public void removeChild(Object arg0) {}
	public void setParent(TreeNode<TreeNodeElement> arg0) {}
	public void setData(TreeNodeElement arg0) {}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isMissing() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().trim().length()>0);
	}
}
