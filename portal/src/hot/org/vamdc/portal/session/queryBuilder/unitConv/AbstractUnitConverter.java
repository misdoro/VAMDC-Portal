package org.vamdc.portal.session.queryBuilder.unitConv;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public abstract class AbstractUnitConverter implements UnitConverter{
	
	private static final long serialVersionUID = -7732846155302824698L;

	protected interface EnumConverter{
	
		public String getDisplay();
		public Double convert(Double value);
		public EnumConverter[] getValues();
	}
	
	protected EnumConverter converter;
	
	protected List<SelectItem> options;
	public AbstractUnitConverter(EnumConverter converter){
		this.converter=converter;
		this.options = new ArrayList<SelectItem>();
		for (EnumConverter conv:converter.getValues()){
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
		converter=valueOfShort(option);
	}
	
	private EnumConverter valueOfShort(String value){
		for (EnumConverter opt:converter.getValues()){
			if (opt.getDisplay().equals(value))
					return opt;
		}
		return null;
	}

	public Double convert (Double value) {
		if (converter!=null && value!=null)
			return converter.convert(value);
		return Double.MIN_NORMAL;
	}
	
}
