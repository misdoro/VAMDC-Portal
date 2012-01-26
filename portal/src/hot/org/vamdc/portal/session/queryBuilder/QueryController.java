package org.vamdc.portal.session.queryBuilder;



import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.entity.security.User;
import org.vamdc.portal.session.preview.PreviewManager;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryLog.QueryLog;

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
	
	@In private EntityManager entityManager;
	@In private Credentials credentials;
	@In private Identity identity;
	
	@In(create=true) @Out QueryData queryData;
	
	@In private PreviewManager preview;
	
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
		queryLog.save(query);
		
		
	}
	
	private Query constructQuery(){
		Query result = new Query();
		result.setComments(queryData.getComments());
		result.setQueryString(queryData.getQueryString());
		result.setResponses(new ArrayList<HttpHeadResponse>(preview.getNodes()));
		result.setUser(getUser());
		return result;
	}

	private User getUser(){
		String user = null;
		if (identity!=null && identity.isLoggedIn() && credentials!=null)
			user=credentials.getUsername();
		if (user!=null && user.length()>0){
			return (User) entityManager.createQuery("from User where username =:username").setParameter("username", user).getSingleResult();
		}
		return null;
	}
	
	public String preview(){
		
		if (queryData.isValid()){
			return RedirectPage.PREVIEW;
		}else 
			return RedirectPage.QUERY;
	}
		
	public void addFormAtoms(){
		queryData.addForm(new AtomsForm());
	}
	
}
