package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.Iterator;
import java.util.Map.Entry;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.TreeNode;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;

/**
 * 
 * @author doronin
 * 
 */
@Scope(ScopeType.STATELESS)
public class RestrictableNode implements TreeNode<TreeNodeElement>,TreeNodeElement {

	private static final long serialVersionUID = 8930608280461610995L;

	private final Restrictable key;
	private final VamdcNode parent;
	private final QueryData queryData;
	
	/**
	 * 
	 * @param key
	 * @param parent
	 * @param queryData null means that this keyword is actually missing from the node but is expected by the query
	 */
	public RestrictableNode(Restrictable key,VamdcNode parent, QueryData queryData) {
		this.key = key;
		this.parent = parent;
		this.queryData = queryData;
	}
	

	public TreeNodeElement getData() { return this; }
	public String getDescription() { return key.getDescription(); };
	public TreeNode<TreeNodeElement> getParent() { return parent; }
	public boolean isLeaf() { return true; }
	public String getType(){
		return "keyword";
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
		return queryData!=null && queryData.getKeywords().contains(key);
	}


	public boolean isMissing() {
		return queryData==null;
	}


	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().trim().length()>0);
	}

}
