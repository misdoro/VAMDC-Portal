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
	private SearchMode selectedMode;
	private QueryTreeInterface tree;
	private Boolean queryable=true;

	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){
	private static final long serialVersionUID = -8406069388034760380L;

	{
		add(new SelectItem(SearchMode.collision,"For collisional process"));
		add(new SelectItem(SearchMode.radiative,"For radiative process"));
		add(new SelectItem(SearchMode.species,"By species"));
	}
	};
	
	public RootForm(QueryTreeInterface tree) {
		//super(tree);
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
		
		tree.setSelectionMode(selectedMode);
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return Order.GuidedRoot;
	}

	public Integer getPosition() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Boolean getQueryable() {
		// TODO Auto-generated method stub
		return this.queryable;
	}
	
	public void setQueryable(Boolean queryable) {
		// TODO Auto-generated method stub
		this.queryable = queryable;
	}

}
