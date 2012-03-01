package org.vamdc.portal.session.queryBuilder.unitConv;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.SelectItem;

public interface UnitConverter extends Converter,Serializable{

	public List<SelectItem> getOptions();
	public String getSelectedOption();
	public void setSelectedOption(String option);
}
