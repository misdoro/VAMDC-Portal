package org.vamdc.portal.session.security;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.vamdc.portal.entity.security.User;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In EntityManager entityManager;
    @In Identity identity;
    @In Credentials credentials;

    public boolean authenticate()
    {
    	        
       //if (user!=null){
        	identity.addRole("user");
        	identity.addRole("admin");
            return true;
       // }
      //  return false;
    }

}
