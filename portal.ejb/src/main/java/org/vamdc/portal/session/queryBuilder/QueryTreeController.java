package org.vamdc.portal.session.queryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.security.User;
import org.vamdc.portal.session.preview.PersistableQueryInterface;
import org.vamdc.portal.session.preview.PreviewManager;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.Order;
import org.vamdc.portal.session.queryBuilder.formsTree.AtomsTreeForm;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.SearchMode;
import org.vamdc.portal.session.queryBuilder.formsTree.TreeFormInterface;
import org.vamdc.portal.session.queryLog.QueryLog;
import org.vamdc.portal.session.security.UserInfo;

@Name("queryTree")
@Scope(ScopeType.CONVERSATION)
public class QueryTreeController  implements QueryTreeInterface, PersistableQueryInterface{

	@In(create=true) @Out QueryData queryData;
	@In(create=true) UserInfo auth;
	@Logger
	transient private Log log;	
	@In(create=true) private PreviewManager preview;
	@In(create=true) private QueryLog queryLog;
	
	private SearchMode searchMode = SearchMode.species;	
	
	public QueryTreeController(){
		this.addForm(new RootForm(this));
	}	
	
	@End
	public String saveQuery(){
		if (queryData.isValid()){
			persistQuery();			
			//conversation.endAndRedirect();
			log.info("Save action");
			return RedirectPage.QUERY_LOG;
		}else{
			return RedirectPage.QUERYTREE;
		}
			
	}
	
	private void persistQuery() {
		QueryPersister p = new QueryPersister(this);		
		Query query = p.constructQuery();//constructQuery();
		queryLog.save(query,queryData.getEditQueryId());
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
	 * @throws Exception 
	 */
	public List<Form> getForms() {
		List<Form> forms= new ArrayList<Form>();
		forms = this.getQueryData().getOrderedGuidedForm();
		/*
		switch(searchMode){
			case collision :
				forms = this.getCollisionForms();
				break;
			case radiative :
				forms = this.getQueryData().getOrderedGuidedForm();
				break;
			case species :
				forms = this.getQueryData().getOrderedGuidedForm();
				break;		
		}		*/
		//return queryData.getUnsortedForms();
		return forms;
	}
	
	private List<Form> getRadiativeForms(){
		List<Form> formsList = this.getQueryData().getForms();
		List<Form> result = new ArrayList<Form>();
		
		for(Form f : formsList){
			if(f.getOrder() == Order.GuidedRoot)
				result.add(0, f);
			if(f.getOrder() == Order.GuidedStates)
				result.add(1, f);
		}
		
		return formsList;		
	}
	
	private List<Form> getCollisionForms(){
		List<Form> formsList = this.getQueryData().getForms();
		return formsList;		
	}
	
	private List<Form> getSpeciesForms(){
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


	public String preview() {
		if (queryData.isValid() && isDone()) {
			return RedirectPage.PREVIEW;
		} else
			return RedirectPage.QUERYTREE;
	}	
	
	
	@Override	
	public <T extends AbstractForm & TreeFormInterface> void addForm(T form) {
		this.getQueryData().addForm(form);	
	}		
	
	public QueryData getQueryData(){
		if(queryData == null){
			queryData = (QueryData)Component.getInstance("queryData"); 
			queryData.setGuidedQuery(true);
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

	@Override
	public List<HttpHeadResponse> getNodesResponse() {
		return preview.getNodes();
	}

	@Override
	public User getUser() {
		return auth.getUser();
	}


	@Override
	public QueryLog getQueryLog() {
		return this.queryLog;
	}

	@Override
	public void setSelectionMode(SearchMode searchMode) {
		this.searchMode = searchMode;
	}

	
}
