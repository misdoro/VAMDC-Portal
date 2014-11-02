package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

public interface FormForFields {
	/**
	 * Is called by fields when any of them is updated
	 */
	public void fieldUpdated(AbstractField field);
}
