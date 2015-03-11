package org.vamdc.portal.session.queryBuilder.formsTree;

public interface TreeFormInterface {
	public String getView();
	public Boolean getQueryable();
	/**
	 * Action for the "validate and continue" button 
	 */
	void validate();
}
