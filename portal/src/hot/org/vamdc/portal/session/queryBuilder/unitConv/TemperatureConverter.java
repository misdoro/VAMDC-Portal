package org.vamdc.portal.session.queryBuilder.unitConv;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class TemperatureConverter implements UnitConverter{

	enum Convert{
		KELVIN("K",javax.measure.unit.SI.KELVIN.getConverterTo(javax.measure.unit.SI.KELVIN)),
		CELSIUS("C",javax.measure.unit.SI.CELSIUS.getConverterTo(javax.measure.unit.SI.KELVIN)),
		FARENHEIT("F",javax.measure.unit.NonSI.FAHRENHEIT.getConverterTo(javax.measure.unit.SI.KELVIN)),
		;
		
		private String display;
		private javax.measure.converter.UnitConverter convert;
		
		Convert(String display,javax.measure.converter.UnitConverter converter){
			this.display = display;
			this.convert = converter;
		}
		
		public String getDisplay(){
			return this.display;
		}
		
		public static Convert valueOfShort(String value){
			for (Convert opt:Convert.values()){
				if (opt.display.equals(value))
						return opt;
			}
			return null;
		}
		
		public Double convert(Double value){
			if (convert == null || value==null)
				return value;
			return convert.convert(value);
		}
		
	}
	
	private Convert converter;
	private List<SelectItem> options;
	
	public TemperatureConverter(){
		this.options = new ArrayList<SelectItem>();
		for (Convert converter:Convert.values()){
			options.add(new SelectItem(converter.getDisplay()));
		}
		this.converter = Convert.KELVIN;
	}
	
	public List<SelectItem> getOptions() {
		return options;
	}

	public String getSelectedOption() {
		return converter.getDisplay();
	}

	public void setSelectedOption(String option) {
		converter=Convert.valueOfShort(option);
	}

	public Double getConvertedValue(Double value) {
		if (converter!=null && value!=null)
			return converter.convert(value);
		return Double.MIN_NORMAL;
	}
	
}
