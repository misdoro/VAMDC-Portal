package org.vamdc.portal.session.queryBuilder.fields;

import java.util.UUID;

import org.vamdc.dictionary.Restrictable;

/**
 * Base class for the form field
 * @author doronin
 */
public abstract class AbstractField {
	private String value;
	protected Restrictable keyword;
	private String title;
	private String id;
	private String prefix;
	
	public AbstractField(Restrictable keyword, String title){
		this.keyword = keyword;
		this.title = title;
		this.id = UUID.randomUUID().toString();
		this.prefix="";
	}
	
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	
	public Restrictable getKeyword() {
		return keyword;
	}
	
	public void setKeyword(Restrictable keyword) {
		this.keyword = keyword;
	}
	
	public String getQuery(){
		if (keyword==null || value==null || value.trim().length()==0)
			return "";
		
		StringBuilder result = new StringBuilder();
		result.append(prefix);
		result.append(keyword.name());
		String tryIn = tryIn();
		if (tryIn.length()>0){
			result.append(tryIn);
			return result.toString();
		}else{
			result.append(" = ");
			result.append("\"");
			result.append(value);
			result.append("\"");
		}
		return result.toString();
	}
	
	private String tryIn(){
		String result ="";
		result+=" IN (";
		String in = "";
		int numRes=0;
		for (String part:value.split("[,.:_]")){
			String val = part.trim();
			if (val.length()>0){
				numRes++;
				if (in.length()>0)
					in+=",";
				in+="'"+val+"'";
			}
		}
		result+=in+") ";
		if (numRes>1)
			return result;
		return "";
	}
	
	public boolean hasValue(){
		return this.value!=null && this.value.length()>0;
	}
	
	/**
	 * Get a face suitable for the form field display
	 */
	public abstract String getView();
	
	
	public String getTitle(){ return this.title; }
	public String getUnits(){ 
		if (this.keyword!=null)
			return this.keyword.getUnits(); 
		return "";
	}
	public String getDescription(){ 
		if (this.keyword!=null)
			return this.keyword.getDescription(); 
		return "";
	}
	public String getId(){ return this.id; }
	
	public void clear(){ this.value=""; }

	protected boolean fieldIsSet(String value){
		return (value!=null && value.length()>0);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
