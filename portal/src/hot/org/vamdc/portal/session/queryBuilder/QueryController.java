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
			return "queryLog";
		}else{
			return "query";
		}
			
	}
	
	public String preview(){
		
		if (queryData.isValid()){
			log.info("Preview action");
			return "preview";
		}else 
			return "query";
	}
	
	public String refine(){
		log.info("refine query");
		return "query";
	}
	
	public void action(){
		queryData.count();
	}
	
	public void addFormAtoms(){
		queryData.addForm(new AtomsForm());
	}
	
}
