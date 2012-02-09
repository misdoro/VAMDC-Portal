package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class MoleculesForm extends AbstractForm implements Form{

	public String getTitle() { return "Molecules"; }
	public Integer getOrder() { return Order.Molecules; }
	public String getView() { return "/xhtml/query/forms/standardForm.xhtml"; }

	public MoleculesForm(){
		
		addField(new SimpleField(Restrictable.MoleculeChemicalName,"Chemical name"));
		addField(new SimpleField(Restrictable.MoleculeStoichiometricFormula,"Stoichiometric formula"));
		addField(new RangeField(Restrictable.IonCharge,"Ion charge"));
		addField(new SimpleField(Restrictable.InchiKey,"InChIKey"));
	}

	
	
}
