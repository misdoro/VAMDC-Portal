package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;

public class MoleculesForm extends AbstractForm implements QueryForm{

	public String getTitle() { return "Molecules"; }

	public String getView() { return "/xhtml/query/forms/moleculesForm.xhtml"; }

	public String getId() { return "2"; }

	public MoleculesForm(){
		fields = new ArrayList<AbstractField>();
		fields.add(new SimpleField(Restrictable.MoleculeChemicalName,"Chemical name"));
		fields.add(new SimpleField(Restrictable.MoleculeStoichiometricFormula,"Stoichiometric formula"));
		fields.add(new RangeField(Restrictable.IonCharge,"Ion charge"));
		fields.add(new SimpleField(Restrictable.InchiKey,"InChIKey"));
	}
	
}
