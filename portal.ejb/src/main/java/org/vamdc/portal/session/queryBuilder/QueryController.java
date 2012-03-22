package org.vamdc.portal.session.queryBuilder;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.log.Log;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.HttpHeadResponse.Response;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.session.preview.PreviewManager;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm;
import org.vamdc.portal.session.queryBuilder.forms.CommentsForm;
import org.vamdc.portal.session.queryBuilder.forms.EnvironmentForm;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm;
import org.vamdc.portal.session.queryBuilder.forms.ParticlesForm;
import org.vamdc.portal.session.queryBuilder.forms.QueryEditForm;
import org.vamdc.portal.session.queryBuilder.forms.RadiativeForm;
import org.vamdc.portal.session.queryLog.QueryLog;
import org.vamdc.portal.session.security.UserInfo;

/**
 * Main class of a query page
 * @author doronin
 *
 */
@Name("query")
@Scope(ScopeType.EVENT)
public class QueryController {
	
	@Logger
	Log log;
	
	@In
	Conversation conversation;
	
	@In(create=true) UserInfo auth;
	
	@In(create=true) @Out QueryData queryData;
	
	@In(create=true) private PreviewManager preview;
	
	@In(create=true) private QueryLog queryLog;
	
	@End
	public String saveQuery(){
		
		if (queryData.isValid()){
			persistQuery();
			
			
			conversation.endAndRedirect();
			log.info("Save action");
			return RedirectPage.QUERY_LOG;
		}else{
			return RedirectPage.QUERY;
		}
			
	}
	
	private void persistQuery() {
		Query query = constructQuery();
		queryLog.save(query,queryData.getEditQueryId());
	}
	
	private Query constructQuery(){
		Query result=null;
		if (queryData.getEditQueryId()!=null)
			result = queryLog.getQuery(queryData.getEditQueryId());
		else
			result = new Query();
		result.setComments(queryData.getComments());
		result.setQueryString(queryData.getQueryString());
		result.setResponses(selectRespondedNodes());
		result.setUser(auth.getUser());
		result.setDate(new Date());
		return result;
	}

	private List<HttpHeadResponse> selectRespondedNodes() {
		ArrayList<HttpHeadResponse> responses = new ArrayList<HttpHeadResponse>();
		
		for (HttpHeadResponse response:preview.getNodes()){
			if (response.getStatus()==Response.OK)
				responses.add(response);
		}
		return responses;
	}

	
	
	public String preview(){
		
		if (queryData.isValid()){
			return RedirectPage.PREVIEW;
		}else 
			return RedirectPage.QUERY;
	}
	
	public String edit(String queryID){
		queryData.setEditQueryId(queryID);
		return clone(queryID);
	}
	
	public String clone(String queryID){
		conversation.begin();
		Query query = queryLog.getQuery(queryID);
		queryData.setComments(query.getComments());
		QueryLoader.loadQuery(queryData,query.getQueryString());
		return RedirectPage.QUERY;
	}
		
	public void addFormAtoms(){
		queryData.addForm(new AtomsForm());
	}
	
	public void addFormMolecules(){
		queryData.addForm(new MoleculesForm());
	}
	
	public void addFormParticles(){
		queryData.addForm(new ParticlesForm());
	}
	
	public void addFormRadiative(){
		queryData.addForm(new RadiativeForm());
	}
	
	public void addFormCollisions(){
		queryData.addForm(new CollisionsForm());
	}
	
	public void addFormEnvironment(){
		queryData.addForm(new EnvironmentForm());
	}
	
	public void addFormComments(){
		queryData.addForm(new CommentsForm());
	}
	
	public void addFormQueryEditor(){
		queryData.addForm(new QueryEditForm());
	}
	
	public void action(){
		
	}

	
}
