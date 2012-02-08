package org.vamdc.portal.session.queryBuilder.unitConv;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public abstract class AbstractUnitConverter implements UnitConverter{
	
	protected interface Convert{
	
		public String getDisplay();
		public Double convert(Double value);
		public Convert[] getValues();
		public Convert valueOfShort(String value);
	}
	
	protected Convert converter;
	
	protected List<SelectItem> options;
	public AbstractUnitConverter(Convert converter){
		this.converter=converter;
		this.options = new ArrayList<SelectItem>();
		for (Convert conv:converter.getValues()){
			options.add(new SelectItem(conv.getDisplay()));
		}
	}
	
	public List<SelectItem> getOptions() {
		return options;
	}

	public String getSelectedOption() {
		return converter.getDisplay();
	}
	
	public void setSelectedOption(String option) {
		converter=converter.valueOfShort(option);
	}

	public Double getConvertedValue(Double value) {
		if (converter!=null && value!=null)
			return converter.convert(value);
		return Double.MIN_NORMAL;
	}
	
}
