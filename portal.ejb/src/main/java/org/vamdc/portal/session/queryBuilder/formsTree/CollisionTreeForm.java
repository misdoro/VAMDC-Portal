package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.LabelField;
import org.vamdc.portal.session.queryBuilder.fields.ProxyRangeField;
import org.vamdc.portal.session.queryBuilder.fields.SuggestionField;
import org.vamdc.portal.session.queryBuilder.fields.UnitConvRangeField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm.IaeaCodeSuggest;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm.ProcessNameSuggest;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm.XsamsCodeSuggest;
import org.vamdc.portal.session.queryBuilder.unitConv.CustomConverters;
import org.vamdc.portal.session.queryBuilder.unitConv.EnergyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.FrequencyUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavelengthUnitConverter;
import org.vamdc.portal.session.queryBuilder.unitConv.WavenumberUnitConverter;

public class CollisionTreeForm extends CollisionsForm implements FormForFields, TreeFormInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = -621884685195400242L;
	private Boolean queryable = true;
	private SearchMode selectedMode = SearchMode.withoutReaction;
	private QueryTreeInterface tree;
	private String defaultSubmitButtonValue = "Select targets and colliders";
	private String submitButtonValue = defaultSubmitButtonValue;
	
		
	private enum SearchMode{
		withoutReaction,
		withReaction
	}
	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){
		private static final long serialVersionUID = 1L;
		{
			add(new SelectItem(SearchMode.withoutReaction,"Without reaction (Elastic and Inelastic)"));
			add(new SelectItem(SearchMode.withReaction,"With reaction"));
		}
	};
	
	public CollisionTreeForm(QueryTreeInterface tree) {
		this.tree = tree;
		init();
	}
	
	protected void init(){
		this.fields = new ArrayList<AbstractField>();		
		processName = new SuggestionField(null,"Process name",new ProcessNameSuggest());
		addField(processName);
		
		processDescription = new LabelField("Process description");
		addField(processDescription);
		
		xsamsProcCode = new SuggestionField(Restrictable.CollisionCode,"Process code",new XsamsCodeSuggest());
		addField(xsamsProcCode);	
		
	}
	
	public Collection<AbstractField> getFields() { return fields; }

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/collisionTreeForm.xhtml";
	}

	@Override
	public void validate() {
		if (this.selectedMode == null)
			return;
		
		switch(this.selectedMode){
			case withoutReaction :
				tree.addForm(new SineReactionCollisionForm(tree));
				break;
				
			case withReaction:
				tree.addForm(new WithReactionCollisionForm(tree));
				break;		
		}
		
		this.queryable = false;		
	}

	public List<SelectItem> getNextOptions(){
		return nextOptions;
	}	
	
	public SearchMode getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(SearchMode selectedMode) {
		this.selectedMode = selectedMode;	//null if nothing selected
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}

	@Override
	public String getTitle() {
		return "Define collision type";
	}

	@Override
	public Integer getOrder() {
		return Order.GuidedCollision;
	}

	@Override
	public Boolean getQueryable() {
		return this.queryable;
	}
	
	public String getSubmitButtonValue(){
		return this.submitButtonValue;
	}
	
	public void changeCollisionType(ValueChangeEvent e){
		final SearchMode value = (SearchMode)e.getNewValue();
		switch(value){
		case withoutReaction :
			this.submitButtonValue = this.defaultSubmitButtonValue;
			break;
			
		case withReaction:
			this.submitButtonValue = "Select reactants and products";
			break;				
		}
		
	}
	
}
