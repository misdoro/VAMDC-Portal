package org.vamdc.portal.session.queryBuilder.forms;

import org.vamdc.dictionary.Restrictable;

/**
 * Base class for the form field
 * @author doronin
 */
public abstract class AbstractField {
	private String value;
	private Restrictable keyword;
	private String title;
	
	public AbstractField(Restrictable keyword){
		this.keyword = keyword;
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
		if (value==null || value.trim().length()==0)
			return "";
		
		StringBuilder result = new StringBuilder();
		
		result.append(keyword.name());
		result.append(" = ");
		result.append("\"");
		result.append(value);
		result.append("\"");
		
		return result.toString();
	}
	
	/**
	 * Get a face suitable for the form field display
	 */
	public abstract String getFace();
	
	public String getTitle(){
		return this.title;
	}

}
