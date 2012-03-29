package org.vamdc.portal.session.queryBuilder.nodeTree;

public interface TreeNodeElement {
	public final static String VAMDCNode="VAMDCNode";
	public final static String Restrictable="Restrictable";
	
	public String getName();
	public String getType();
	public String getDescription();
	public boolean hasDescription();
}
