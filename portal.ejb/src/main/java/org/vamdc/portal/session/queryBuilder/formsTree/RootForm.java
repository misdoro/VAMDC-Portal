package org.vamdc.portal.session.queryBuilder.formsTree;

import java.util.ArrayList;
import java.util.List;

public class RootForm extends TreeForm{

	private String selectedMode;
	
	List<String> modeOptions = new ArrayList<String>(){{
		add("For collisional process");
		add("For radiative process");
		add("By species");
	}};
	
	@Override
	public void showNext() {
		
	}

	public String getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(String selectedMode) {
		this.selectedMode = selectedMode;
		System.out.println(selectedMode);
	}

}
