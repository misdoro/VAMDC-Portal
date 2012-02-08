package org.vamdc.portal.session.queryBuilder.fields;

import javax.faces.event.ValueChangeEvent;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.unitConv.UnitConverter;

public class UnitConvRangeField extends RangeField{
	
	public UnitConvRangeField(Restrictable keyword, String title, UnitConverter converter) {
		super(keyword, title);
		this.converter = converter;
	}

	@Override
	public String getView() { return "/xhtml/query/fields/unitConvRangeField.xhtml"; }

	private UnitConverter converter;
	private String userHiValue="";
	private String userLoValue="";
	
	public UnitConverter getConverter(){
		return converter;
	}
	
	public String getConverted(){
		
		String result = "";
		result += loValue;
		result +=" to ";
		result += hiValue;
		return result;
	}

	private String doConv(String value) {
		String result ="";
		if (value==null || value.length()==0)
			return result;
		try {
			Double userHiValue = Double.parseDouble(value);
			result=converter.getConvertedValue(userHiValue)+" ";
		}catch (NumberFormatException e){}
		return result;
	}

	public String getUserHiValue() { return userHiValue; }
	public void setUserHiValue(String userHiValue) { 
		this.userHiValue = userHiValue;
		this.setHiValue(doConv(userHiValue));
	}
	public String getUserLoValue() { return userLoValue; }
	public void setUserLoValue(String userLoValue) { 
		this.userLoValue = userLoValue;
		this.setLoValue(doConv(userLoValue));
	}
	
	public void update(ValueChangeEvent event){
		converter.setSelectedOption((String)event.getNewValue());
		this.setLoValue(doConv(userLoValue));
		this.setHiValue(doConv(userHiValue));
		this.fixCompareOrder();
	}
	
}
