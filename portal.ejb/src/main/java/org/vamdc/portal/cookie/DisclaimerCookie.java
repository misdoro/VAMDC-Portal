package org.vamdc.portal.cookie;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;

@Name("disclaimer")
@Scope(ScopeType.PAGE)
public class DisclaimerCookie {
	
	private String name = "disclaimer";
	private CookieHelper cookieHelper = new CookieHelper(); 
	
	/**
	 * 
	 * @return false if cookie is not defined or if it has not been accepted
	 */
	public Boolean getValue(){
		if(cookieHelper.getCookie(this.name) != null){
			return Boolean.valueOf(cookieHelper.getCookie(this.name).getValue());
		}
		return false;
	}
	
	public void setValue(Boolean value){
		// 1 year expire date
		cookieHelper.setCookie(this.name, value.toString(),  365 * 24 * 60 * 60 );
	}
}
