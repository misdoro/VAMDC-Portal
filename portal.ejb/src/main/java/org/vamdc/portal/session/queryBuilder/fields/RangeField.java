package org.vamdc.portal.session.queryBuilder.fields;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.LogicNode.Operator;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class RangeField extends AbstractField{

	private static final long serialVersionUID = 6586234035266177619L;

	public RangeField(Restrictable keyword, String title) {
		super(keyword, title);
	}

	@Override
	public String getView() { return "/xhtml/query/fields/rangeField.xhtml"; }

	protected String hiValue="";
	protected String loValue="";

	public String getHiValue(){	return hiValue; }
	public String getLoValue(){	return loValue; }

	public void setHiValue(String value){
		if (isValidValue(value)) 
			this.hiValue = value; 
	}
	public void setLoValue(String value){
		if (isValidValue(value))
			this.loValue = value;
	}

	private boolean isValidValue(String value) {
		if (value==null || value.length()==0)
			return true;
		try {
			Double num = Double.valueOf(value);
			if (num!=null && !num.isNaN())
				return true;
		}catch (NumberFormatException e){
			return false;
		}
		return false;
	}

	@Override
	public String getQuery(){
		if (ignoreField)
			return "";
		fixCompareOrder();
		String result = "";
		if (hiValue!=null && hiValue.equals(loValue)){
			result=getQueryPart(this.keyword.name(),"=",loValue);
		}else{
			result=getQueryPart(this.keyword.name(),">=",loValue);
			String hiPart = getQueryPart(this.keyword.name(),"<=",hiValue);
			if (hiPart.length()>0){
				if (result.length()>0)
					result+=" AND ";
				result+=hiPart;
			}
		}
		return result;

	}

	private String getQueryPart(String keyword,String compare, String value){
		if (fieldIsSet(value)){
			return addPrefix()+keyword+" "+compare+" "+value;
		}
		return "";
	}

	protected void fixCompareOrder(){
		if (fieldIsSet(hiValue) && fieldIsSet(loValue)){
			Double lo=Double.NaN;
			Double hi=Double.NaN;
			try{
				lo = Double.parseDouble(loValue);
				hi = Double.parseDouble(hiValue);
			}catch(NumberFormatException e){

			}

			if (lo>hi){
				String tmp = loValue;
				loValue=hiValue;
				hiValue=tmp;
			}
		}
	}

	@Override
	public boolean hasValue(){
		return !ignoreField && (fieldIsSet(hiValue)|| fieldIsSet(loValue));
	}

	@Override
	public void clear(){
		hiValue="";
		loValue="";
	}

	@Override 
	public void loadFromQuery(LogicNode part){
		if (part==null || part.getOperator()== null)
			return;
		Operator clause = part.getOperator();
		switch(clause){
		case EQUAL_TO:
			RestrictExpression key = (RestrictExpression) part;
			this.setHiValue(key.getValue().toString());
			this.setLoValue(key.getValue().toString());
			break;
		case OR:
			return;//Group separation is not done properly, return
		case AND:
			for (Object rangeKey:part.getValues()){
				RestrictExpression rkey = (RestrictExpression) rangeKey;
				switch (rkey.getOperator()){
				case GREATER_THAN_EQUAL_TO:
				case GREATER_THAN:
					this.setLoValue(rkey.getValue().toString());
					break;
				case LESS_THAN_EQUAL_TO:
				case LESS_THAN:
					this.setHiValue(rkey.getValue().toString());
					break;
				default:
					break;
				}
			}

		}
	}
	
	@Override
	public String getSummary(){
		if (!this.hasValue())
			return "";
		return this.getTitle()+" from "+this.loValue+" to "+this.hiValue;
	}

}
