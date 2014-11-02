package org.vamdc.portal.session.queryBuilder.fields;


public class LabelField extends AbstractField{

	private static final long serialVersionUID = 1411385334362934390L;

	public LabelField(String title) {
		super(null, title);
	}

	@Override
	public String getView() {
		return "/xhtml/query/fields/labelField.xhtml";
	}

}
