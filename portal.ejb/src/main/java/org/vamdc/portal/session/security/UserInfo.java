package org.vamdc.portal.session.security;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.Redirect;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.vamdc.portal.entity.security.User;

@Name("auth")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class UserInfo {
	
	@In private EntityManager entityManager;
	@In private Credentials credentials;
	@In private Identity identity;
	
	public User getUser(){		
		String user = null;
		if (identity!=null && identity.isLoggedIn() && credentials!=null)
			user=credentials.getUsername();
		if (user!=null && user.length()>0){
			return (User) entityManager.createQuery("from User where username =:username").setParameter("username", user).getSingleResult();
		}
		return null;
	}
	
	
	@In private Redirect redirect;
	public String login(){
		if (identity !=null && !identity.isLoggedIn()){
			redirect.captureCurrentView();
			return "login";
		}
		return "";
	}
}
