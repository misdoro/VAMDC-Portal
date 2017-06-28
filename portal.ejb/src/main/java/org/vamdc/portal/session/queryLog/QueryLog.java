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

		return Collections.unmodifiableList(queries);
	}

	public void save(Query query, String queryID) {
		if (query.getUser()!=null && (queryID==null || queryID.substring(0,1).equals("p"))){
			if (queryID!=null) 
				query.setQueryID(Integer.valueOf(queryID.substring(1)));
			persistentQueryLog.save(query);
		}else{
			if (queryID!=null) 
				query.setQueryID(Integer.valueOf(queryID.substring(1)));
			sessionQueryLog.save(query);
		}
	}

	public void persistSessionQueries(){
		for (Query sessionQuery:sessionQueryLog.getStoredQueries()){
			sessionQuery.setUser(auth.getUser());
			sessionQuery.setQueryID(null);
			persistentQueryLog.save(sessionQuery);
		}
		sessionQueryLog.clear();
	}

	public void deleteQuery(String queryID){
		if (queryID!=null){
			if (queryID.substring(0, 1).equals("p"))
				persistentQueryLog.delete(Integer.valueOf(queryID.substring(1)));
			else
				sessionQueryLog.delete(Integer.valueOf(queryID.substring(1)));
		}
	}

	public Query getQuery(String queryID){
		if (queryID!=null){
			List<Query> queries = null;
			int intQueryID = Integer.parseInt(queryID.substring(1));
			if (queryID.substring(0, 1).equals("p"))
				queries = persistentQueryLog.getStoredQueries();
			else 
				queries = sessionQueryLog.getStoredQueries();

			for (Query query:queries){
				if (query.getQueryID().equals(intQueryID))
					return query;
			}
		}
		return null;
	}

}
