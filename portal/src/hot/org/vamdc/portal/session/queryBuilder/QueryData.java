package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.QueryPageForm;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData {


	private Collection<QueryPageForm> forms=Collections.synchronizedList(new ArrayList<QueryPageForm>());
	
	//Species-related forms
	private Collection<QueryPageForm> speciesForms=Collections.synchronizedList(new ArrayList<QueryPageForm>());
	
	private QueryPageForm processForm=null;
	
	private String comments="";
	
	private String editedQueryId;

	public Collection<Restrictable> getKeywords(){
		EnumSet<Restrictable> result = EnumSet.noneOf(Restrictable.class);
		for (QueryPageForm form:forms){
			result.addAll(form.getKeywords());
		}
		return result;
			
	}

	public String getQueryString(){
		
		return "select * where "+getFormsQuery(); 
	}
	
	private String getFormsQuery(){
		
		
		String result = "";
		for (QueryPageForm form:forms){
			String queryPart = form.getQueryPart();
			if (queryPart.length()>0){
				if (result.length()>0)
					result+=" AND "+queryPart;
				else
					result=queryPart;
			}
		}
		return result;
	}

	public boolean isValid(){
		return getKeywords().size()>0;
	}

	public Collection<QueryPageForm> getForms(){
		return forms;
	}
	
	public Collection<QueryPageForm> getSpeciesForms(){
		return speciesForms;
	}

	public void addSpeciesForm(QueryPageForm form){
		addForm(form);
		speciesForms.add(form);
	}
	
	public boolean addProcessForm(QueryPageForm form){
		if (processForm==null){
			processForm=form;
			addForm(form);
			return true;
		}
		return false;
	}
	
	public void addForm(QueryPageForm form){
		forms.add(form);
	}
	
	public void deleteForm(QueryPageForm form){
		forms.remove(form);
		speciesForms.remove(form);
		if (processForm==form)
			processForm=null;
	}

	public String getComments() { return comments; }

	public void setComments(String comments) { this.comments = comments; }
	
	public String getEditQueryId() { return editedQueryId; }

	public void setEditQueryId(String editQueryId) { this.editedQueryId = editQueryId; }

	public void loadQuery(String queryString) {
		// TODO load forms contents from the query string.
	}

}
