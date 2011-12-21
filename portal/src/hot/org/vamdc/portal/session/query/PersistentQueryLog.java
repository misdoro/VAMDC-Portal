package org.vamdc.portal.session.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.vamdc.portal.entity.query.Query;

@Name("persistentQueryLog")
public class PersistentQueryLog {

	@Logger private Log log;
	
	@In private Credentials credentials;
	@In private Identity identity;
	@In private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Query> getStoredQueries(){
		log.info("Reading saved queries");
		String user = null;
		List<Query> queries = null;
		
		if (identity!=null && identity.isLoggedIn() && credentials!=null)
			user=credentials.getUsername();
		
		if (user!=null && user.length()>0){
			queries=entityManager.createQuery("from Query where user.username =:username").setParameter("username", user).getResultList();
		}
		
		if (queries!=null && queries.size()>0){
			log.info("Read #0 queries",queries.size());
			return Collections.unmodifiableList(queries);
		}
		return new ArrayList<Query>();
	}
	
}
