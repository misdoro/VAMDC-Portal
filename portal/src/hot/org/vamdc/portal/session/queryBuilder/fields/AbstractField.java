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
	
	public AbstractField(Restrictable keyword, String title){
		this.keyword = keyword;
		this.title = title;
		this.id = UUID.randomUUID().toString();
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
		
		result.append(keyword.name());
		result.append(" = ");
		result.append("\"");
		result.append(value);
		result.append("\"");
		
		return result.toString();
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
	
}
