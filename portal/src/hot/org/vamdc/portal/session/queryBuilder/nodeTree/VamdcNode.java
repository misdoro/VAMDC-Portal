package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;

public class VamdcNode implements TreeNode<String>{

	private final String ivoaID;
	private final TreeNode<String> root;
	
	private final Map<Object,TreeNode<String>> restrictables = new HashMap<Object,TreeNode<String>>();
	
	public VamdcNode(TreeNode<String> root, Registry registry, String id){
		this.ivoaID = id;
		this.root = root;
		try {
			for (Restrictable key:registry.getRestrictables(ivoaID)){
				restrictables.put(key.name(), new RestrictableNode(key,this));
			}
		} catch (RegistryCommunicationException e) {
		}
	}
	
	private static final long serialVersionUID = 7577402632944052661L;

	
	public TreeNode<String> getChild(Object arg0) {
		return restrictables.get(arg0);
	}

	public Iterator<Entry<Object, TreeNode<String>>> getChildren() {
		return restrictables.entrySet().iterator();
	}

	public String getData() {
		return ivoaID;
	}
	
	public String getDescription(){
		return "description";
	}
	
	public String getType(){
		return "vamdcNode";
	}
	
	public String getName(){
		return ivoaID;
	}

	public TreeNode<String> getParent() {
		return root;
	}

	public boolean isLeaf() {
		return false;
	}

	public void addChild(Object arg0, TreeNode<String> arg1) {}
	public void removeChild(Object arg0) {}
	public void setParent(TreeNode<String> arg0) {}
	public void setData(String arg0) {}
}
