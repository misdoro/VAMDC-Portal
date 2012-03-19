package org.vamdc.portal.session.queryBuilder.forms;

public class CommentsForm extends AbstractForm implements Form{

	private static final long serialVersionUID = 1624151709277914531L;
	@Override
	public String getTitle() { return "Query comments"; }
	@Override
	public Integer getOrder() { return Order.Comments; }
	@Override
	public String getView() { return "/xhtml/query/forms/editorForm.xhtml"; }
	
	public CommentsForm(){
	}
	
	@Override
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
