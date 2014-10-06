package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

public class RootForm extends TreeForm{

	
	private NextForm selectedMode;
	private enum NextForm{
		collision,
		radiative,
		species
	}
	
	private List<SelectItem> nextOptions = new ArrayList<SelectItem>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add(new SelectItem(NextForm.collision,"For collisional process"));
		add(new SelectItem(NextForm.radiative,"For radiative process"));
		add(new SelectItem(NextForm.species,"By species"));
	}
	};
	
	@Override
	public void showNext() {
		
	}

	public List<SelectItem> getNextOptions(){
		return nextOptions;
	}	
	
	public NextForm getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(NextForm selectedMode) {
		this.selectedMode = selectedMode;
		System.out.println(selectedMode);
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/rootForm.xhtml";
	}

}
