package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

public class CommentsForm extends AbstractForm implements Form{

	public String getTitle() { return "Query comments"; }
	public Integer getOrder() { return Order.Comments; }
	public String getView() { return "/xhtml/query/forms/editorForm.xhtml"; }
	
	public CommentsForm(QueryData queryData){
		super(queryData);
		fields = new ArrayList<AbstractField>();
	}
	
	public String getValue(){
		return queryData.getComments();
	}
	
	public void setValue(String comments){
		queryData.setComments(comments);
	}
	
}
