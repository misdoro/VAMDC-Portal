package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class AsyncForm extends AbstractForm{

	private static final long serialVersionUID = 604009831586236633L;

	@Override
	public String getTitle() { return "Async"; }

	@Override
	public String getView() { return "/xhtml/query/forms/asyncForm.xhtml"; }

	@Override
	public Integer getOrder() {	return Order.Async; }

	public AsyncForm(){
		super();
		
		addField(new SimpleField(null, "Email"));		
	}
	
}