package org.vamdc.portal.session.queryBuilder;



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
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;

/**
 * Main class of a query page
 * @author doronin
 *
 */
@Name("query")
@Scope(ScopeType.EVENT)
public class QueryController {
	
	//TODO: verify serialization
	
	@Logger
	Log log;
	
	@In
	Conversation conversation;
	
	@In(create=true) @Out QueryData queryData;
	
	@End
	public String saveQuery(){
		
		if (queryData.isValid()){
			conversation.endAndRedirect();
			log.info("Save action");
			return RedirectPage.QUERY_LOG;
		}else{
			return RedirectPage.QUERY;
		}
			
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
