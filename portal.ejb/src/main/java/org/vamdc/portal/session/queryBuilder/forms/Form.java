package org.vamdc.portal.session.queryBuilder.forms;

import java.io.Serializable;
import java.util.Collection;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.tapservice.vss2.LogicNode;

/**
 * Interface used to build forms
 * @author doronin
 */
public interface Form extends Serializable{
	public String getTitle();
	public String getView();
	public String getSummary();
	public String getId();
	public Collection<AbstractField> getFields();
	
	public String getQueryPart();
	public Collection<Restrictable> getKeywords();
	public Collection<Restrictable> getSupportedKeywords();

	/**
	 * Set queryData when adding form to the querydata structure
	 * @param data
	 */
	public void setQueryData(QueryData data);

	public void clear();
	public void delete();
	public void setPrefix(String prefix);
	public String getPrefix();
	
	public Integer getOrder();
	public Integer getPosition();
	public void decreasePosition();
	public void setPrefixIndex(Integer integer);
	
	public int loadFromQuery(LogicNode branch);
	
}
