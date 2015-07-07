package org.vamdc.portal.session.queryBuilder.forms;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.vamdc.portal.RedirectPage;
import org.vamdc.portal.cookie.DisclaimerCookie;

@Name("disclaimerForm")
@Scope(ScopeType.PAGE)
public class DisclaimerForm {

	private static Map<String,Object> disclaimerChoices;
	static{
		disclaimerChoices = new LinkedHashMap<String,Object>();
		disclaimerChoices.put("Accept", "accept");
		disclaimerChoices.put("Decline", "decline");
	}
	
	private String response = (String)disclaimerChoices.get("Accept");
	
	public Map<String,Object> getDisclaimerChoices() {
		return disclaimerChoices;
	}
	
	public void setResponse(String response){
		this.response = response;
	}
	
	public String getResponse(){
		return this.response;
	}
	

	public String validate(){
		// guided query or advanced query form, origin of the request
		String source = FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().get("sourcePage");
		if(response.equals(disclaimerChoices.get("Accept"))){
			new DisclaimerCookie().setValue(true);
			return source; //redirect to source page if yes
		}else{
			return RedirectPage.HOME;
		}
	}
	


}
