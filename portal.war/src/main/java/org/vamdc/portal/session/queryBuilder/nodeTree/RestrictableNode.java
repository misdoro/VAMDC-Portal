package org.vamdc.portal.session.queryBuilder.nodeTree;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;

/**
 * Tree node corresponding to a restrictable keyword
 * 
 */
@Scope(ScopeType.STATELESS)
public class RestrictableNode extends TreeNodeImpl<TreeNodeElement> implements TreeNodeElement {

	private static final long serialVersionUID = 8930608280461610995L;

	private final Restrictable key;
	private final QueryData queryData;
	
	/**
	 * 
	 * @param key
	 * @param parent
	 * @param queryData null means that this keyword is actually missing from the node but is expected by the query
	 */
	public RestrictableNode(Restrictable key, QueryData queryData) {
		this.key = key;
		this.queryData = queryData;
		this.setData(this);
	}
	
	public String getDescription() { return key.getDescription(); };
	public String getType(){
		return TreeNodeElement.Restrictable;
	}
	
	public String getName(){
		return key.name();
	}
	
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
