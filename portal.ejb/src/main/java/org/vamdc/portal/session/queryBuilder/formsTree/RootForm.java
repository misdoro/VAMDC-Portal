package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;

public class RootForm extends TreeForm{

	private NextForm selectedMode;
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
		super(tree);
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
			break;
		}
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/rootForm.xhtml";
	}

}
