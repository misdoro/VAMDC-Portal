package org.vamdc.portal.session.queryBuilder.nodeTree;

public interface TreeNodeElement {
	public String getName();
	public String getType();
	public String getDescription();
	public boolean hasDescription();
	public boolean isActive();
	public boolean isMissing();
}
