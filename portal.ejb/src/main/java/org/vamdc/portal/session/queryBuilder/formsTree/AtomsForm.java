package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.List;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.SimpleField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;

public class AtomsForm extends TreeForm implements FormForFields{
	protected List<AbstractField> fields;
	protected Integer position; 
	public AtomsForm(QueryTreeInterface tree) {
		super(tree);
		position = tree.getFormCount();
		System.out.println("### position : "+position);
		this.fields = new ArrayList<AbstractField>();
		
		AbstractField field=new SimpleField(Restrictable.AtomSymbol,"Atom symbol");
		fields.add(field);
		field.setParentForm(this);
		field=new RangeField(Restrictable.AtomMassNumber,"Mass number");
		fields.add(field);
		field.setParentForm(this);
		field=new RangeField(Restrictable.AtomNuclearCharge,"Nuclear charge");
		fields.add(field);
		field.setParentForm(this);		
		field=new RangeField(Restrictable.IonCharge,"Ion charge");
		fields.add(field);
		field.setParentForm(this);	
		field=new RangeField(Restrictable.InchiKey,"InChIKey");
		fields.add(field);
		field.setParentForm(this);	
		field=new UnitConvRangeField(Restrictable.StateEnergy, "State energy", new EnergyUnitConverter());
		fields.add(field);
		field.setParentForm(this);	
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/atomsForm.xhtml";
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
		// TODO Auto-generated method stub
		System.out.println("Field "+field.getTitle()+" was updated!");
	}
	
	public List<AbstractField> getFields() {
		return fields;
	}

	public void setFields(List<AbstractField> fields) {
		this.fields = fields;
	}
	public Integer getPosition(){
		return position;
	}

}
