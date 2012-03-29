package org.vamdc.portal.session.queryBuilder.nodeTree;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;

/**
 * Tree node corresponding to a restrictable keyword
 * 
 */
@Scope(ScopeType.STATELESS)
public class RestrictableNode extends TreeNodeImpl<TreeNodeElement> implements TreeNodeElement {

	private static final long serialVersionUID = 8930608280461610995L;

	private final Restrictable key;
	private final boolean isMissing;
	
	/**
	 * 
	 * @param key
	 * @param parent
	 * @param queryData null means that this keyword is actually missing from the node but is expected by the query
	 */
	public RestrictableNode(Restrictable key, boolean missing) {
		this.key = key;
		this.isMissing = missing;
		this.setData(this);
	}
	
	public String getDescription() { return key.getDescription(); };
	public String getType(){
		return TreeNodeElement.Restrictable;
	}
	
	public String getName(){
		return key.name();
	}

	public boolean hasDescription() {
		return (getDescription()!=null && getDescription().trim().length()>0);
	}

	public boolean isMissing() {
		return isMissing;
	}

}
