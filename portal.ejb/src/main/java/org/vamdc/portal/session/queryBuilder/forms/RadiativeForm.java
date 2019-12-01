package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.ProxyRangeField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.CustomConverters;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.FrequencyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavelengthUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavenumberUnitConverter;

public class RadiativeForm extends AbstractForm implements Form{

	private static final long serialVersionUID = 3472732538919812959L;
	@Override
	public String getTitle() { return "Radiative"; }
	@Override
	public Integer getOrder() { return Order.Process; }
	@Override
	public String getView() { return "/xhtml/query/forms/radiativeForm.xhtml"; }
	
	public RadiativeForm(){
		super();
		ProxyRangeField wlField = setupWLField();
		
		addField(wlField);
		AbstractField field = new UnitConvRangeField(Restrictable.StateEnergy, "Upper state energy", new EnergyUnitConverter());
		field.setPrefix("upper");
		addField(field);
		//addField(new RangeField("upper",Restrictable.StateEnergy,"Upper state energy"));
		field = new UnitConvRangeField(Restrictable.StateEnergy, "Lower state energy", new EnergyUnitConverter());
		field.setPrefix("lower");
		addField(field);
		//addField(new RangeField("lower",Restrictable.StateEnergy,"Lower state energy"));
		addField(new RangeField(Restrictable.RadTransProbabilityA,"Probability, A"));
	}
	
	static ProxyRangeField setupWLField() {
		ProxyRangeField wlField = new ProxyRangeField(Restrictable.RadTransWavelength,"Wavelength", new WavelengthUnitConverter());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransFrequency,"Frequency",new FrequencyUnitConverter()), CustomConverters.MHzToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransWavenumber,"Wavenumber",new WavenumberUnitConverter()), CustomConverters.WnToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransEnergy,"Energy",new EnergyUnitConverter()), CustomConverters.WnToWl());
		return wlField;
	}

}
