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


public class OneStateEnergyTreeForm extends AbstractForm implements FormForFields, TreeFormInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1777979203251373978L;
	private Boolean queryable = true;
	private QueryTreeInterface tree;

	
	public OneStateEnergyTreeForm(QueryTreeInterface tree){
		this.tree = tree;
		init();
	}
	

	
	private void init(){			
		addField(new UnitConvRangeField(Restrictable.StateEnergy, "State energy", new EnergyUnitConverter()));			
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
		return "/xhtml/query/queryTree/oneStateEnergyForm.xhtml";
	}

	@Override
	public Boolean getQueryable() {
		// TODO Auto-generated method stub
		return this.queryable;
	}

	@Override
	public void validate() {
		tree.addForm(new SpeciesSelectionForm(tree));
		this.queryable = false;
	}

}
