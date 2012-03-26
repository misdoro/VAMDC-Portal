package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Iterator;
import java.util.Map.Entry;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.DataType;
import org.vamdc.dictionary.Keyword;
import org.vamdc.dictionary.Requestable;

public class BranchesForm extends AbstractForm implements Form{

	private static final long serialVersionUID = 8345255113788420868L;
	private RequestNode root;
	
	@Override
	public String getTitle() { return "Request branches"; }
	@Override
	public Integer getOrder() { return Order.Branches; }
	@Override
	public String getView() { return "/xhtml/query/forms/branchesForm.xhtml"; }
	
	
	public class RequestNode extends TreeNodeImpl<RequestNode>{
		
		private static final long serialVersionUID = 7595270306255586808L;
		private boolean active = true;
		
		public RequestNode(Keyword key){
			super();
			setData(key);
			
		}
		
		
		public boolean isActive(){
			return ((active && getData()!=null) || hasActiveChild());
		}
		
		/**
		 * @return true if node has active child nodes
		 */
		public boolean hasActiveChild(){
			Iterator<Entry<Object,TreeNode<Keyword>>> children = this.getChildren();
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
		private void enable(){
			this.active=true;
			RequestNode parent = (RequestNode) this.getParent();
			if (parent!=null)
				parent.enable();
		}
		
		/**
		 * Disable element and all children
		 */
		private void disable(){
			this.active=false;
			disableChildren();
		}
		
		/**
		 * enable children non-recursively
		 */
		private void enableChildren(){
			Iterator<Entry<Object,TreeNode<Keyword>>> children = this.getChildren();
			while (children.hasNext()){
				RequestNode myChild = (RequestNode) children.next().getValue();
				myChild.enable();
			}
		}
		
		/**
		 * Recursively disable children
		 */
		private void disableChildren(){
			Iterator<Entry<Object,TreeNode<Keyword>>> children = this.getChildren();
			while (children.hasNext()){
				RequestNode myChild = (RequestNode) children.next().getValue();
				myChild.disable();
			}
		}
		
	}
	
	public class RootKeyword implements Keyword{
		@Override
		public DataType getDataType() { return DataType.String; }
		@Override
		public String getDescription() { return "Root of the tree"; }
		@Override
		public String getInfo() { return "Root"; }
		@Override
		public String getUnits() { return ""; }
		@Override
		public String name() { return ""; }
		
	}
	
	public BranchesForm(){
		super();
		root = new RequestNode(new RootKeyword());
		root.addChild(0, new RequestNode(Requestable.Species));
		root.addChild(1, new RequestNode(Requestable.Processes));
		
	}
	
	public TreeNode<Keyword> getTree(){
		return root;
	}
	
}
