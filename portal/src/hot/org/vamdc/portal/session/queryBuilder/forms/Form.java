package org.vamdc.portal.session.queryBuilder.forms;

import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

/**
 * Interface used to build forms
 * @author doronin
 */
public interface Form {
	public String getFullTitle();
	public String getTitle();
	public String getView();
	public String getId();
	public Collection<AbstractField> getFields();
	
	public String getQueryPart();
	public Collection<Restrictable> getKeywords();

	public void setQueryData(QueryData data);
	
	public void clear();
	public void delete();
	public void setPrefix(String prefix);
	public String getPrefix();
	
	public Integer getOrder();
	public void setPrefixIndex(Integer integer);
}