package org.vamdc.portal.session.queryBuilder.branches;

import java.util.Iterator;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.DataType;
import org.vamdc.dictionary.Keyword;

public class RequestNode extends TreeNodeImpl<RequestNode> implements Keyword{
	
	private static final long serialVersionUID = 7595270306255586808L;
	private boolean active = true;
	private Keyword keyword;
	private String label;
	
	public RequestNode(Keyword key, String label){
		super();
		this.keyword = key;
		setData(this);
		
	}
	
	
	public boolean isActive(){
		return ((active && getData()!=null) || hasActiveChild());
	}
	
	/**
	 * @return true if node has active child nodes
	 */
	public boolean hasActiveChild(){
		Iterator<Entry<Object,TreeNode<RequestNode>>> children = this.getChildren();
		while (children.hasNext()){
			RequestNode myChild = (RequestNode) children.next().getValue();
			if (myChild.isActive())
				return true;
		}
		return false;
	}
	
	/**
	 * Enable element and all parents
	 */
	public void enable(){
		this.active=true;
		RequestNode parent = (RequestNode) this.getParent();
		if (parent!=null)
			parent.enable();
	}
	
	/**
	 * Disable element and all children
	 */
	public void disable(){
		this.active=false;
		disableChildren();
	}
	
	/**
	 * enable children non-recursively
	 */
	private void enableChildren(){
		Iterator<Entry<Object,TreeNode<RequestNode>>> children = this.getChildren();
		while (children.hasNext()){
			RequestNode myChild = (RequestNode) children.next().getValue();
			myChild.enable();
		}
	}
	
	/**
	 * Recursively disable children
	 */
	private void disableChildren(){
		Iterator<Entry<Object,TreeNode<RequestNode>>> children = this.getChildren();
		while (children.hasNext()){
			RequestNode myChild = (RequestNode) children.next().getValue();
			myChild.disable();
		}
	}


	@Override
	public DataType getDataType() {
		return null;
	}


	@Override
	public String getDescription() {
		if (keyword!=null)
			return keyword.getDescription();
		return "";
	}


	@Override
	public String getInfo() {
		if (keyword!=null)
			return keyword.getInfo();
		return "";
	}


	@Override
	public String getUnits() {
		return "";
	}


	@Override
	public String name() {
		if (keyword!=null)
			return keyword.name();
		return "";
	}


	public String getLabel() {
		return label;
	}
	
}
