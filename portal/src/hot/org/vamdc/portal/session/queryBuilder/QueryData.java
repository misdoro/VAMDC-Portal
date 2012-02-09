package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.TreeSet;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData {


	private Collection<Form> forms=Collections.synchronizedSet(new TreeSet<Form>(new Order()));
	private List<Form> formsList = Collections.synchronizedList(new ArrayList<Form>());
	
	//Species-related forms
	private Collection<Form> speciesForms=Collections.synchronizedSet(new TreeSet<Form>(new Order()));
	//ProcessForm
	private Form processForm=null;
	
	
	private String comments="";
	
	private String editedQueryId;

	public Collection<Restrictable> getKeywords(){
		EnumSet<Restrictable> result = EnumSet.noneOf(Restrictable.class);
		for (Form form:getForms()){
			result.addAll(form.getKeywords());
		}
		return result;
			
	}

	public String getQueryString(){
		
		return "select * where "+QueryGenerator.getFormsQuery(getForms()); 
	}
	




	public boolean isValid(){
		return getKeywords().size()>0;
	}

	/**
	 * This method must be fast and must return a fast collection
	 * @return
	 */
	public List<Form> getForms(){
		return formsList;
	}
	
	public Collection<Form> getSpeciesForms(){
		return new ArrayList<Form>(speciesForms);
	}

	public void addSpeciesForm(Form form){
		addForm(form);
		speciesForms.add(form);
	}
	
	public boolean addProcessForm(Form form){
		if (processForm==null){
			processForm=form;
			addForm(form);
			return true;
		}
		return false;
	}
	
	public void addForm(Form form){
		forms.add(form);
		formsList = Collections.synchronizedList(new ArrayList<Form>(forms));
	}
	
	public void deleteForm(Form form){
		forms.remove(form);
		speciesForms.remove(form);
		if (processForm==form)
			processForm=null;
		formsList = Collections.synchronizedList(new ArrayList<Form>(forms));
	}
	
	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }
	
	public String getEditQueryId() { return editedQueryId; }
	public void setEditQueryId(String editQueryId) { this.editedQueryId = editQueryId; }
	
	public void loadQuery(String queryString) {
		// TODO load forms contents from the query string.
	}

}
