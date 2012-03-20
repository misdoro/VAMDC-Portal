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
	public String getTitle();//Form title
	public String getView();//Form view
	public String getExtraView();//Extra include
	public String getId();//UUID
	public Collection<AbstractField> getFields();//All form fields
	
	public String getQueryPart();//Query string
	public Collection<Restrictable> getKeywords();//Used keywords
	public Collection<Restrictable> getSupportedKeywords();//All possible keywords

	public void setQueryData(QueryData data);//Attach form, called when the form is added to the queryData
	public Integer getInsertOrder();//Increasing integer, unique form number
	public void setInsertOrder(Integer insertOrder);//Setter
	
	
	public void clear();//Clear all form fields
	public void delete();//Delete form
	
	public void setPrefix(String prefix);//Set form prefix
	public String getPrefix();//Get form prefix
	public void setPrefixIndex(Integer integer);//Set prefix index, for reactants and products
	
	
	public Integer getOrder();//Get form order, as it should be. The higher the number, the lower form will be on the page
	
	public int loadFromQuery(LogicNode branch);
	
	public int getListOrder();
	public void setListOrder(int order);
}
