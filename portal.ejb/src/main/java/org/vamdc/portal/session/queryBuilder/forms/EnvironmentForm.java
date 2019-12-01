package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.PressureUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.TemperatureConverter;

public class EnvironmentForm extends AbstractForm implements Form{


	private static final long serialVersionUID = -2779854273259772288L;
	@Override
	public String getTitle() { return "Environment"; }
	@Override
	public Integer getOrder() { return Order.Environment; }
	@Override
	public String getView() { return "/xhtml/query/forms/environmentForm.xhtml"; }
	
	public EnvironmentForm(){
		addField(
				new UnitConvRangeField(Restrictable.EnvironmentTemperature,"Temperature",	new TemperatureConverter()));
		addField(
				new UnitConvRangeField(Restrictable.EnvironmentTotalPressure,"Pressure",new PressureUnitConverter()));
		addField(
				new RangeField(Restrictable.EnvironmentTotalNumberDensity,"Number Density"));
	}


}
