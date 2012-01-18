package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData {
	
	private Integer counter=0;
	private final ArrayList<Restrictable> usedKeywords= new ArrayList<Restrictable>();
	
	
	public Collection<Restrictable> getKeywords(){
		
		return usedKeywords;
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
		usedKeywords.add(Restrictable.values()[index]);
		counter++;
	}
	
	public boolean isValid(){
		return true;
	}
	
}
