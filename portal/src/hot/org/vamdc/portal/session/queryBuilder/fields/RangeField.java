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

	protected String loValue="";
	protected String hiValue="";

	public String getHiValue(){	return hiValue; }
	public String getLoValue(){	return loValue; }

	public void setLoValue(String loValue){
		this.loValue = loValue;
	}

	public void setHiValue(String hiValue){
		this.hiValue = hiValue;
	}

	@Override
	public String getQuery(){
		fixCompareOrder();
		String result = "";
		if (hiValue!=null && hiValue.equals(loValue)){
			result=getQueryPart(this.keyword.name(),"=",loValue);
		}else{
			result=getQueryPart(this.keyword.name(),">=",loValue);
			if (result.length()>0)
				result+=" AND ";
			result+=getQueryPart(this.keyword.name(),"<=",hiValue);
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
		return (fieldIsSet(hiValue)|| fieldIsSet(loValue));
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
			if (key.getPrefix()!=null)
				this.setPrefix(key.getPrefix().getPrefix().name());
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
					return;
				}
			}
			
		}
	}

}
