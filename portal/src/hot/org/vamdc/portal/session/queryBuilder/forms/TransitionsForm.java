package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavelengthUnitConverter;

public class TransitionsForm extends AbstractForm implements Form{

	private static final long serialVersionUID = 3472732538919812959L;
	public String getTitle() { return "Transitions"; }
	public Integer getOrder() { return Order.Process; }
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	
	public TransitionsForm(){
		super();
		addField(new UnitConvRangeField(Restrictable.RadTransWavelength,"Wavelength", new WavelengthUnitConverter()));
		AbstractField field = new UnitConvRangeField(Restrictable.StateEnergy, "Upper state energy", new EnergyUnitConverter());
		field.setPrefix("upper.");
		addField(field);
		//addField(new RangeField("upper",Restrictable.StateEnergy,"Upper state energy"));
		field = new UnitConvRangeField(Restrictable.StateEnergy, "Lower state energy", new EnergyUnitConverter());
		field.setPrefix("lower.");
		addField(field);
		//addField(new RangeField("lower",Restrictable.StateEnergy,"Lower state energy"));
		addField(new RangeField(Restrictable.RadTransProbabilityA,"Probability, A"));
	}

}
