package org.vamdc.portal.async;

import java.io.Serializable;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


@Name("asyncbean")
@Scope(ScopeType.SESSION)
public class ASyncBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3179233668566407678L;

	@Email(message="invalid email address")
	@NotNull
	private String email = new String();
	
	public void setEmail(String email){
		this.email = email;
	}
	

	public String getEmail(){
		return email;
	}
}
