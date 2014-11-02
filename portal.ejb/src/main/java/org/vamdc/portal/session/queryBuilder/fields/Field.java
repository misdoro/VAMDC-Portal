package org.vamdc.portal.session.queryBuilder.fields;

import org.vamdc.portal.session.queryBuilder.forms.FormForFields;

/**
 * Field interface as seen by owning form
 * @author doronin
 */
public interface Field {
	
	/**
	 * Get query part corresponding to the field
	 * @return
	 */
	public String getQuery();
	
	/**
	 *
	 * @return true if the form has query keyword and value
	 */
	public boolean hasValue();
	
	/**
	 * Set parent for for updated callback
	 * @param parent
	 */
	public void setParentForm(FormForFields parent);
}
