package org.vamdc.portal.session.queryBuilder.forms;

public class CommentsForm extends AbstractForm implements Form{

	public String getTitle() { return "Query comments"; }
	public Integer getOrder() { return Order.Comments; }
	public String getView() { return "/xhtml/query/forms/editorForm.xhtml"; }
	
	public CommentsForm(){
	}
	
	public String getValue(){
		return queryData.getComments();
	}
	
	public void setValue(String comments){
		queryData.setComments(comments);
	}
	
	@Override
	public void clear(){
		queryData.setComments("");
	}
	
}
