package org.vamdc.portal.session.queryBuilder.fields;

import org.vamdc.dictionary.Restrictable;

public class TextField extends AbstractField{
	
	private static final long serialVersionUID = 2730916891512241185L;

	public TextField(Restrictable keyword, String title) {
		super(keyword, title);
	}

	@Override
	public String getView() { return "/xhtml/query/fields/textField.xhtml"; }
	
}
