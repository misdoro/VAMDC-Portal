package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;

public class AtomsForm extends AbstractForm implements Form {

	public String getTitle() { return "Atoms"; }
	public Integer getOrder() { return Order.Atoms; }
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }
	
	public AtomsForm(){
		super();
		
		addField(new SimpleField(Restrictable.AtomSymbol,"Atom symbol"));
		addField(new SimpleField(Restrictable.InchiKey,"InChIKey"));
		addField(new RangeField(Restrictable.AtomMassNumber,"Mass number"));
		addField(new RangeField(Restrictable.AtomNuclearCharge,"Nuclear charge"));
		addField(new RangeField(Restrictable.IonCharge,"Ion charge"));
		addField(new UnitConvRangeField(
				Restrictable.StateEnergy, "State energy", new EnergyUnitConverter()));
	}

}
