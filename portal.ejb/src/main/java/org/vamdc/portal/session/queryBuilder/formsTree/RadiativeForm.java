package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.ProxyRangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.unitConv.CustomConverters;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.FrequencyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavelengthUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavenumberUnitConverter;

public class RadiativeForm extends TreeForm implements FormForFields{

	/**
	 * 
	 */
	private static final long serialVersionUID = -621884685195400242L;
	protected List<AbstractField> fields;
	public RadiativeForm(QueryTreeInterface tree) {
		super(tree);
		this.fields = new ArrayList<AbstractField>();
		//ProxyRangeField wlField = setupWLField();
		//fields.add(wlField);
		
		//AbstractField field = new UnitConvRangeField(Restrictable.StateEnergy, "Lower state energy", new EnergyUnitConverter());
		//field.setPrefix("lower");
		AbstractField field=new SimpleField(Restrictable.AtomSymbol,"Test field");
		fields.add(field);
		field.setParentForm(this);
		field=new SimpleField(Restrictable.AtomMass,"massatom");
		fields.add(field);
		field.setParentForm(this);
	}
	
	public Collection<AbstractField> getFields() { return fields; }

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/radiativeForm.xhtml";
	}

	@Override
	public void validate() {
		
	}

	static ProxyRangeField setupWLField() {
		ProxyRangeField wlField = new ProxyRangeField(Restrictable.RadTransWavelength,"Wavelength", new WavelengthUnitConverter());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransFrequency,"Frequency",new FrequencyUnitConverter()), CustomConverters.MHzToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransWavenumber,"Wavenumber",new WavenumberUnitConverter()), CustomConverters.WnToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransEnergy,"Energy",new EnergyUnitConverter()), CustomConverters.WnToWl());
		return wlField;
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return Order.Process;
	}
	
}
