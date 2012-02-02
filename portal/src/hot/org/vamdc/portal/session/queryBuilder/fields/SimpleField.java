package org.vamdc.portal.session.queryBuilder.fields;

import org.vamdc.dictionary.Restrictable;

public class SimpleField extends AbstractField{
	
	public SimpleField(Restrictable keyword, String title) {
		super(keyword, title);
	}

	@Override
	public String getView() { return "/xhtml/query/fields/simpleField.xhtml"; }
	
}
