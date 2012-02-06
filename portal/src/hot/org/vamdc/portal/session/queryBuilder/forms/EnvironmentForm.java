package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;

public class EnvironmentForm extends AbstractForm implements QueryForm{
	public String getTitle() { return "Environment"; }

	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public String getId() {	return "6"; }
	
	public EnvironmentForm(QueryData queryData){
		super(queryData);
		fields = new ArrayList<AbstractField>();
		fields.add(new RangeField(Restrictable.Temperature,"Temperature"));
		fields.add(new RangeField(Restrictable.Pressure,"Pressure"));
	}
}
