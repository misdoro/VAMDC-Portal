package org.vamdc.portal.session.queryLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.vamdc.portal.entity.query.Query;
import org.vamdc.portal.session.security.UserInfo;

@Name("queryLog")
@Scope(ScopeType.EVENT)
public class QueryLog {

	@In(create=true) PersistentQueryLog persistentQueryLog;
	
	@In(create=true) SessionQueryLog sessionQueryLog;

	@In(create=true) UserInfo auth;
	
	@Logger private Log log;
	
	public List<QueryFacade> getQueries(){
		List<QueryFacade> queries;
		
		queries = new ArrayList<QueryFacade>();
		
		for (Query stored:persistentQueryLog.getStoredQueries()){
			queries.add(new QueryFacade(stored,"p"));
		}
		for (Query session:sessionQueryLog.getStoredQueries()){
			queries.add(new QueryFacade(session,"s"));
		}
		log.info("Loaded #0 queries to log", queries.size());
		return Collections.unmodifiableList(queries);
	}

	public void save(Query query) {
		if (query.getUser()!=null){
			log.info("Saving query to the persistent log");
			persistentQueryLog.save(query);
		}else{
			log.info("Saving query to the session log");
			sessionQueryLog.save(query);
		}
	}
	
	public void persistSessionQueries(){
		for (Query sessionQuery:sessionQueryLog.getStoredQueries()){
			sessionQuery.setUser(auth.getUser());
			persistentQueryLog.save(sessionQuery);
		}
		sessionQueryLog.clear();
	}
	
	public void deleteQuery(String id){
		log.info("Delete query"+id);
		if (id!=null){
			if (id.substring(0, 1).equals("p"))
				persistentQueryLog.delete(Integer.valueOf(id.substring(1)));
			else
				sessionQueryLog.delete(Integer.valueOf(id.substring(1)));
		}
	}
	
}
