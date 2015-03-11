package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.formsTree.AtomsTreeForm;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeFormInterface;

@Name("queryTree")
@Scope(ScopeType.CONVERSATION)
public class QueryTree  implements QueryTreeInterface{

	@In(create=true) QueryData queryData;
	@Logger
	transient private Log log;	

	public QueryTree(){
		RootForm rootForm = new RootForm(this);
		this.addForm(rootForm);
	}	
	
	private Boolean isRequestable(Form f){
		if(f.getOrder() != Order.GuidedRequestType
				&& f.getOrder() != Order.GuidedRoot
				&& f.getOrder() != Order.GuidedSpeciesType)
			return true;
		return false;
	}
	
	private Integer requestableFormsCount(){
		Integer result = 0;
		for(Form f : this.getQueryData().getForms()){
			if(isRequestable(f)){
				result++;
			}
		}		
		return result;
	}

	/**
	 * sort forms list to display menus at the top
	 * @return
	 */
	public List<Form> getForms() {
		List<Form> formsList = this.getQueryData().getForms();
		List<Form> result = new ArrayList<Form>();
		
		for(Form f : formsList){
			if(!isRequestable(f)){
				result.add(f);
			}
		}
		
		for(Form f : formsList){
			if(isRequestable(f)){
				result.add(f);				
			}
		}		
		return result;
	}

	public boolean isDone() {
		return true;
	}	

	public String saveQuery() {
		return RedirectPage.QUERY_LOG;
	}

	public String preview() {
		if (isDone()) {
			return RedirectPage.PREVIEW;
		} else
			return RedirectPage.QUERYTREE;
	}	
	
	
	@Override	
	public <T extends AbstractForm & TreeFormInterface> void addForm(T form) {
		this.getQueryData().addForm(form);	
	}		
	
	private QueryData getQueryData(){
		if(queryData == null){
			queryData = (QueryData)Component.getInstance("queryData"); 
		}
		return queryData;
	}
	

	@Override
	public Integer getFormCount() {
		return this.getQueryData().getForms().size();
	}	
	
	public Boolean getSubmitable(){
		if(this.requestableFormsCount() > 0)
			return true;
		
		return false;
	}

	
}
