package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class RootForm extends AbstractForm implements TreeFormInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 48058013878512872L;
	private SearchMode selectedMode = SearchMode.species;
	private QueryTreeInterface tree;
	private Boolean queryable=true;

	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){
		private static final long serialVersionUID = -8406069388034760380L;
	
		{
			add(new SelectItem(SearchMode.species,"By species"));
			add(new SelectItem(SearchMode.radiative,"For radiative process"));
			add(new SelectItem(SearchMode.collision,"For collisional process"));
		}
	};
	
	public RootForm(QueryTreeInterface tree) {
		this.tree = tree;		
	}

	public List<SelectItem> getNextOptions(){
		return nextOptions;
	}	
	
	public SearchMode getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(SearchMode selectedMode) {
		this.selectedMode = selectedMode;
	}
	
	@Override
	public void validate(){		
		if (this.selectedMode == null)
			return;
		
		switch(this.selectedMode){
		case collision:
			tree.addForm(new CollisionTreeForm(tree));
			break;
		case radiative:
			tree.addForm(new RadiativeForm(tree));
			break;
		case species:
			tree.addForm(new SpeciesSelectionForm(tree));
			break;
		}
		this.queryable = false;
		
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/rootForm.xhtml";
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public Integer getOrder() {
		return Order.GuidedRoot;
	}
	
	@Override
	public Integer getPosition() {
		return 1;
	}

	@Override
	public Boolean getQueryable() {
		return this.queryable;
	}
	
	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

}
