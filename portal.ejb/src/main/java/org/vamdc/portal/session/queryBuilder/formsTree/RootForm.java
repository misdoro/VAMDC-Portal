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
	private NextForm selectedMode;
	private QueryTreeInterface tree;
	private Boolean queryable=true;
	private enum NextForm{
		collision,
		radiative,
		species
	}
	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){
	private static final long serialVersionUID = -8406069388034760380L;

	{
		add(new SelectItem(NextForm.collision,"For collisional process"));
		add(new SelectItem(NextForm.radiative,"For radiative process"));
		add(new SelectItem(NextForm.species,"By species"));
	}
	};
	
	public RootForm(QueryTreeInterface tree) {
		//super(tree);
		this.tree = tree;		
	}

	public List<SelectItem> getNextOptions(){
		return nextOptions;
	}	
	
	public NextForm getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(NextForm selectedMode) {
		System.err.println("Set form "+selectedMode.name());
		this.selectedMode = selectedMode;
	}
	
	@Override
	public void validate(){		
		if (this.selectedMode == null)
			return;
		
		System.err.println("Adding next form "+selectedMode.name());
		
		switch(this.selectedMode){
		case collision:
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
