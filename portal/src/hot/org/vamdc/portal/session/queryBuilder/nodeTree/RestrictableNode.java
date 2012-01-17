package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Iterator;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;

public class RestrictableNode implements TreeNode<TreeNodeElement>,TreeNodeElement {

	private static final long serialVersionUID = 8930608280461610995L;

	private final Restrictable key;
	private final VamdcNode parent;
	
	public RestrictableNode(Restrictable key,VamdcNode parent) {
		this.key = key;
		this.parent = parent;
	}
	

	public TreeNodeElement getData() { return this; }
	public String getDescription() { return key.getDescription(); };
	public TreeNode<TreeNodeElement> getParent() { return parent; }
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
	public void addChild(Object arg0, TreeNode<TreeNodeElement> arg1) {}
	public TreeNode<TreeNodeElement> getChild(Object arg0) {	return null; }
	public Iterator<Entry<Object, TreeNode<TreeNodeElement>>> getChildren() { return null; }
	public void removeChild(Object arg0) {}
	public void setData(TreeNodeElement arg0) {}
	public void setParent(TreeNode<TreeNodeElement> arg0) {}


	public boolean isActive() {
		return false;
	}


	public boolean isMissing() {
		// TODO Auto-generated method stub
		return false;
	}

}
