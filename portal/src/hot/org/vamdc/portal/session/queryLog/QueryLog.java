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
import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.entity.query.Query;

@Name("queryLog")
@Scope(ScopeType.EVENT)
public class QueryLog {

	@In(create=true) PersistentQueryLog persistentQueryLog;
	
	@In(create=true) SessionQueryLog sessionQueryLog;
	
	@Logger private Log log;
	
	public List<QueryFacade> getQueries(){
		List<QueryFacade> queries;
		
		queries = new ArrayList<QueryFacade>();
		
		for (Query stored:persistentQueryLog.getStoredQueries()){
			log.info("Loading query #0 to log", stored.getQueryID());
			QueryFacade query = new QueryFacade(stored);
			log.info("Loading query #0 to log", stored.getQueryID());
			queries.add(query);
			
		}
		for (Query session:sessionQueryLog.getStoredQueries()){
			log.info("Loading query #0 to log", session.getQueryID());
			queries.add(new QueryFacade(session));
		}
		log.info("Loaded #0 queries to log", queries.size());
		return Collections.unmodifiableList(queries);
	}

	public void save(Query query) {
		if (query.getUser()!=null){
			persistentQueryLog.save(query);
		}else{
			sessionQueryLog.save(query);
		}
	}
	
}
