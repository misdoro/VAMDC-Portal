package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData {
	
	private Integer counter=0;
	
	public Collection<Restrictable> getKeywords(){
		ArrayList<Restrictable> result = new ArrayList<Restrictable>();
		result.add(Restrictable.InchiKey);
		result.add(Restrictable.ParticleName);
		return result;
	}
	
	public String getQueryString(){
		return "select * where inchikey=\"blahblah\" or inchikey=\"fooBar\" or ParticleName=\"electron\""; 
	}
	
	public int getCount(){
		return counter;
	}
	
	public void count(){
		counter++;
	}
	
	public boolean isValid(){
		return true;
	}
	
}
