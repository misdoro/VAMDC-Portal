package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.List;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;


public class AllStatesEnergyTreeForm extends AbstractForm implements FormForFields, TreeFormInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1777979203251373978L;
	private Boolean queryable = true;
	private QueryTreeInterface tree;

	public AllStatesEnergyTreeForm(QueryTreeInterface tree){
		this.tree = tree;
		init();
	}
	
	
	private void init(){
		AbstractField field = new UnitConvRangeField(Restrictable.StateEnergy, "Upper state energy", new EnergyUnitConverter());
		field.setPrefix("upper");
		addField(field);
		field = new UnitConvRangeField(Restrictable.StateEnergy, "Lower state energy", new EnergyUnitConverter());
		field.setPrefix("lower");
		addField(field);		
		addField(new RangeField(Restrictable.RadTransProbabilityA,"Probability, A"));
	}
	
	@Override
	public String getTitle() {
		return "Search by state energy";
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return Order.GuidedStates;
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/allStatesEnergyForm.xhtml";
	}

	@Override
	public Boolean getQueryable() {
		// TODO Auto-generated method stub
		return this.queryable;
	}

	@Override
	public void validate() {
		tree.addForm(new SpeciesSelectionForm(tree));
	}

}
