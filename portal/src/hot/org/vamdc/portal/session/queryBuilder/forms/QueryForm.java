package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

/**
 * Interface used to build forms
 * @author doronin
 */
public interface QueryForm {
	
	public String getTitle();
	public String getView();
	public String getId();
	public Collection<AbstractField> getFields();
	
	public String getQueryPart();
	public Collection<Restrictable> getKeywords();

}
