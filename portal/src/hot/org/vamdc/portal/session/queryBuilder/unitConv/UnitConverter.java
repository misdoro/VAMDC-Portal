package org.vamdc.portal.session.queryBuilder.unitConv;

import java.util.List;

import javax.faces.model.SelectItem;

public interface UnitConverter {

	public List<SelectItem> getOptions();
	public String getSelectedOption();
	public void setSelectedOption(String option);
	public Double getConvertedValue(Double value);
}
