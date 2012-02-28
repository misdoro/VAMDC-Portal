package org.vamdc.portal.session.queryBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.tapservice.vss2.RestrictExpression;
import org.vamdc.tapservice.vss2.impl.QueryImpl;

@Name("queryData")
@Scope(ScopeType.CONVERSATION)
public class QueryData implements Serializable{

	private static final long serialVersionUID = -6797195825696968172L;
	
	//All forms and lists are serialized explicitly
	private transient Collection<Form> forms;
	private transient List<Form> formsList;
	
	//Species-related forms
	private transient Collection<Form> speciesForms;
	private transient Form queryEditForm=null;
	
	private String comments="";
	private String editedQueryId;

	public QueryData(){
		initCollections();
	}
	
	private void initCollections(){
		forms=Collections.synchronizedSet(new TreeSet<Form>(new Order()));
		formsList = Collections.synchronizedList(new ArrayList<Form>());
		speciesForms=Collections.synchronizedSet(new TreeSet<Form>(new Order()));
	}
	
	public Collection<Restrictable> getKeywords(){
		if (isUserModified()){
			return getKeywordsFromQuery(queryEditForm.getValue());
		}
		return getKeywordsFromForms();
	}
	
	private Collection<Restrictable> getKeywordsFromForms(){
		EnumSet<Restrictable> result = EnumSet.noneOf(Restrictable.class);
		for (Form form:getForms()){
			result.addAll(form.getKeywords());
		}
		return result;
			
	}
	
	private Collection<Restrictable> getKeywordsFromQuery(String query){
		EnumSet<Restrictable> result = EnumSet.noneOf(Restrictable.class);
		Query parser;
		try{
			parser = new QueryImpl(getQueryString(),null);
		}catch (IllegalArgumentException e){
			return result;
		}
		
		Collection<RestrictExpression> keywords = parser.getRestrictsList();
		if (keywords!=null){
			for (RestrictExpression keyword:keywords){
				result.add(keyword.getColumn());
			}
		}
		
		return result;
	}

	public String buildQueryString(){
		return "select * where "+QueryGenerator.getFormsQuery(getForms());
	}
	
	public String getQueryString(){
		if (isUserModified())
			return queryEditForm.getValue();
		return buildQueryString(); 
	}
	
	private boolean isUserModified(){
		return (queryEditForm!=null && queryEditForm.getValue().length()>0);
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
	
	/**
	 * Check if we can add a form
	 * @param newForm form to add
	 * @return true if form can be added
	 */
	private boolean canAdd(Form newForm){
		if (newForm.getOrder()<Order.SINGLE_LIMIT)
			return true;
		for (Form form:forms){
			if (form.getOrder().equals(newForm.getOrder()))
				return false;
		}
		return true;
	}
	
	public void addForm(Form form){
		if (quickAddForm(form)){
			formsList = Collections.synchronizedList(new ArrayList<Form>(forms));
		}
	}
	
	private boolean quickAddForm(Form form){
		if (canAdd(form)){
			forms.add(form);
			form.setQueryData(this);
			if (form.getOrder()<Order.SPECIES_LIMIT)
				speciesForms.add(form);
			return true;
		}
		return false;
	}
	
	public void setQueryEditForm(Form form){
		this.queryEditForm = form;
	}
	
	public void deleteForm(Form form){
		forms.remove(form);
		if (form.getOrder()<Order.SPECIES_LIMIT)
			speciesForms.remove(form);
		formsList = Collections.synchronizedList(new ArrayList<Form>(forms));
	}
	
	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }
	
	public String getEditQueryId() { return editedQueryId; }
	public void setEditQueryId(String editQueryId) { this.editedQueryId = editQueryId; }

	public EntityManager getEntityManager(){
		return (EntityManager) Component.getInstance("entityManager");
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException{
		s.defaultWriteObject();
		
		int numForms = forms.size();
		s.writeInt(numForms);
		for (Form f:forms){
			s.writeObject(f);
		}
	}
	
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException{
		s.defaultReadObject();
		int numForms = s.readInt();
		
		this.initCollections();
		
		for (int i=0;i<numForms;i++){
			quickAddForm((Form) s.readObject());
		}
		
	}
	
}
