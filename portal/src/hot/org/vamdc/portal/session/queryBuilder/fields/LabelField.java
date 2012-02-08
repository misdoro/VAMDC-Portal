package org.vamdc.portal.session.queryBuilder.fields;

public class LabelField extends AbstractField{

	public LabelField(String title) {
		super(null, title);
	}

	@Override
	public String getView() {
		return "/xhtml/query/fields/labelField.xhtml";
	}

}
