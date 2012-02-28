package org.vamdc.portal.session.queryBuilder.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

public abstract class AbstractForm implements Form{
	

	private static final long serialVersionUID = -2420596698571830221L;
	protected List<AbstractField> fields;
	protected transient QueryData queryData;
	private final String id;
	protected String prefix;
	
	public AbstractForm(){ 
		this.id=UUID.randomUUID().toString();
		this.prefix="";
		this.fields = new ArrayList<AbstractField>();
	}
	
	public void setQueryData(QueryData queryData){ 
		this.queryData = queryData;
	}
	
	void addField(AbstractField field){
		fields.add(field);
	}
	
	public String getQueryPart() {
		String query="";
		for (AbstractField field:fields){
			if (field.hasValue()){
				if (query.length()>0){
					query+=" AND "+field.getQuery();
				}
				else query=field.getQuery();
			}
		}
		return query;
	}
	
	public Collection<AbstractField> getFields() { return fields; }
	
	public Collection<Restrictable> getKeywords() {
		EnumSet<Restrictable> keywords = EnumSet.noneOf(Restrictable.class);
		for (AbstractField field:fields){
			if (field.hasValue() && field.getKeyword()!=null){
				keywords.add(field.getKeyword());
			}
		}
		return keywords;
	}
	
	public void clear(){
		for (AbstractField field:fields){
			field.clear();
		}
	}

	public void delete(){
		queryData.deleteForm(this);
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setPrefix(String prefix){
		if (prefix==null)
			prefix="";
		this.prefix = prefix;
		
		if (prefix.length()>0)
			prefix+=".";
		
		for (AbstractField field:fields){
			field.setPrefix(prefix);
		}
	}
	public void setPrefixIndex(Integer index){
		String prefix = this.prefix;
		if (index!=null && prefix!=null && prefix.length()>0){
			prefix=prefix+index+".";
			for (AbstractField field:fields){
				field.setPrefix(prefix);
			}
		}
	}
	
	public String getPrefix(){
		return this.prefix;
	}
	
	public final String getFullTitle(){
		if (prefix!=null && prefix.length()>0)
			return getTitle()+" ("+prefix+")";
		return getTitle();
	}
	
	public String getValue(){
		return "";
	}
	
}
