package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.PressureUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.TemperatureConverter;

public class EnvironmentForm extends AbstractForm implements Form{
	public String getTitle() { return "Environment"; }
	public Integer getOrder() { return Order.Environment; }
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	
	public EnvironmentForm(QueryData queryData){
		super(queryData);
		fields = new ArrayList<AbstractField>();
		fields.add(new UnitConvRangeField(
				Restrictable.Temperature,
				"Temperature",
				new TemperatureConverter()));
		fields.add(new UnitConvRangeField(
				Restrictable.Pressure,
				"Pressure",
				new PressureUnitConverter()));
	}


}
