package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.QueryForm;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData {

	private Integer counter=0;

	private Collection<QueryForm> forms=new ArrayList<QueryForm>();

	public Collection<Restrictable> getKeywords(){
		EnumSet<Restrictable> result = EnumSet.noneOf(Restrictable.class);
		for (QueryForm form:forms){
			result.addAll(form.getKeywords());
		}
		return result;
			
	}

	public String getQueryString(){
		return "select * where inchikey=\"blahblah\" or inchikey=\"fooBar\" or ParticleName=\"electron\""; 
	}

	public int getCount(){
		return counter;
	}

	public void count(){
		Random val = new Random();
		Integer index = val.nextInt(Restrictable.values().length);
		counter++;
	}

	public boolean isValid(){
		return true;
	}

	public Collection<QueryForm> getForms(){
		return forms;
	}

	void addForm(QueryForm form){
		forms.add(form);
	}
	
	void deleteForm(QueryForm form){
		forms.remove(form);
	}

}
