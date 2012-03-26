package org.vamdc.portal.session.queryBuilder.forms;

public class QueryEditForm extends AbstractForm implements Form{

	private static final long serialVersionUID = 1038458921184436210L;
	@Override
	public String getTitle() { return "Query editor"; }
	@Override
	public Integer getOrder() { return Order.Query; }
	@Override
	public String getView() { return "/xhtml/query/forms/editorForm.xhtml"; }
	
	public String getValue(){
		if (queryData!=null)
			return queryData.getQueryString();
		return "";
	}
	
	public void setValue(String newQueryString){
		if (queryData!=null && !newQueryString.equals(queryData.getQueryString()))
			queryData.setCustomQueryString(newQueryString);
	}
	
	@Override
	public void clear(){
		super.clear();
		queryData.setCustomQueryString("");
	}
	
}
