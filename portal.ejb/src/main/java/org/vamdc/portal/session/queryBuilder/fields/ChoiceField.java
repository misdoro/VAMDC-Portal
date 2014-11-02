package org.vamdc.portal.session.queryBuilder.fields;

import org.vamdc.dictionary.Restrictable;

public class ChoiceField extends AbstractField{
	
	public ChoiceField(Restrictable keyword, String title) {
		super(keyword, title);
	}

	private static final long serialVersionUID = 2674522952002721167L;

	@Override
	public String getView() {
		return "/xhtml/query/fields/choiceField.xhtml";
	}

}
