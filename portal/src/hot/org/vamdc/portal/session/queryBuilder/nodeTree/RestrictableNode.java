package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Iterator;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;

public class RestrictableNode implements TreeNode<String> {

	private static final long serialVersionUID = 8930608280461610995L;

	private final Restrictable key;
	private final VamdcNode parent;
	
	public RestrictableNode(Restrictable key,VamdcNode parent) {
		this.key = key;
		this.parent = parent;
	}
	

	public String getData() { return key.name(); }
	public String getDescription() { return key.getDescription(); };
	public TreeNode<String> getParent() { return parent; }
	public boolean isLeaf() { return true; }
	public String getType(){
		return "restrictable";
	}
	
	public String getName(){
		return key.name();
	}
	
	/**
	 * Unused setters and getters
	 */
	public void addChild(Object arg0, TreeNode<String> arg1) {}
	public TreeNode<String> getChild(Object arg0) {	return null; }
	public Iterator<Entry<Object, TreeNode<String>>> getChildren() { return null; }
	public void removeChild(Object arg0) {}
	public void setData(String arg0) {}
	public void setParent(TreeNode<String> arg0) {}

}
