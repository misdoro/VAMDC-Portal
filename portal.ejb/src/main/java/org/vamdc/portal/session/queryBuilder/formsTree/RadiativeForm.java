package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.ProxyRangeField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.unitConv.CustomConverters;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.FrequencyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavelengthUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavenumberUnitConverter;

public class RadiativeForm extends AbstractForm implements FormForFields, TreeFormInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = -621884685195400242L;
	private Boolean queryable = true;
	private NextForm selectedMode = NextForm.anyEnergyRange;
	private QueryTreeInterface tree;
	private final String defaultSubmitButtonValue = "Select species";
	private String submitButtonValue = defaultSubmitButtonValue;
		
	private enum NextForm{
		upperLowerEnergyRange,
		stateEnergyRange,
		anyEnergyRange
	}
	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){
		private static final long serialVersionUID = 1L;
		{
			add(new SelectItem(NextForm.upperLowerEnergyRange,"Transition from an energy range to another one"));
			add(new SelectItem(NextForm.anyEnergyRange,"Any transition"));
		}
	};
	
	public RadiativeForm(QueryTreeInterface tree) {
		//super(tree);
		this.tree = tree;
		this.fields = new ArrayList<AbstractField>();
		ProxyRangeField wlField = setupWLField();
		fields.add(wlField);
	}
	
	public Collection<AbstractField> getFields() { return fields; }

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/radiativeForm.xhtml";
	}

	@Override
	public void validate() {
		if (this.selectedMode == null)
			return;
			
		switch(this.selectedMode){
		case upperLowerEnergyRange:
			tree.addForm(new AllStatesEnergyTreeForm(tree));
			break;
		case stateEnergyRange:
			tree.addForm(new OneStateEnergyTreeForm(tree));
			break;
		case anyEnergyRange:	
			tree.addForm(new SpeciesSelectionForm(tree));
			break;
		}
		this.queryable = false;		
	}

	static ProxyRangeField setupWLField() {
		ProxyRangeField wlField = new ProxyRangeField(Restrictable.RadTransWavelength,"Wavelength", new WavelengthUnitConverter());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransFrequency,"Frequency",new FrequencyUnitConverter()), CustomConverters.MHzToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransWavenumber,"Wavenumber",new WavenumberUnitConverter()), CustomConverters.WnToWl());
		wlField.addProxyField(new UnitConvRangeField(Restrictable.RadTransEnergy,"Energy",new EnergyUnitConverter()), CustomConverters.WnToWl());
		return wlField;
	}
	
	public List<SelectItem> getNextOptions(){
		return nextOptions;
	}	
	
	public NextForm getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(NextForm selectedMode) {
		this.selectedMode = selectedMode;	//null if nothing selected
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}

	@Override
	public String getTitle() {
		return "Define radiative configuration";
	}

	@Override
	public Integer getOrder() {
		return Order.GuidedRadiative;
	}

	@Override
	public Boolean getQueryable() {
		return this.queryable;
	}
	
	public String getSubmitButtonValue(){
		return this.submitButtonValue;
	}
	
	public void changeTransitionType(ValueChangeEvent e){
		NextForm value = (NextForm)e.getNewValue();
		String submitButton = "Select range";
		switch(value){
		case upperLowerEnergyRange:
			this.submitButtonValue = submitButton;
			break;
		case stateEnergyRange:
			this.submitButtonValue = submitButton;
			break;
		case anyEnergyRange:	
			this.submitButtonValue = this.defaultSubmitButtonValue;
			break;
		}
	}
	
}
