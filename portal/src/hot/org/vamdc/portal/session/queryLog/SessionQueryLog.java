package org.vamdc.portal.session.queryLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.entity.query.Query;

@Name("sessionQueryLog")
@Scope(ScopeType.SESSION)
public class SessionQueryLog implements Serializable{

	private static final long serialVersionUID = 2555499604910810323L;
	private List<Query> storedQueries = Collections.synchronizedList(new ArrayList<Query>());
	private int idCounter=0;


	public List<Query> getStoredQueries() {
		return Collections.unmodifiableList(storedQueries);
	}


	public void save(Query query) {
		Integer queryID=null;
		if ((queryID = query.getQueryID())!=null)
			delete(queryID);
		
		query.setQueryID(idCounter++);

		storedQueries.add(query);
		
	}

	public void clear(){
		storedQueries.clear();
	}


	public void delete(Integer queryId) {
		Query toRemove = null;
			for (Query stored:storedQueries){
				if (stored.getQueryID()==queryId){
					toRemove=stored;
					break;
				}
			}
			storedQueries.remove(toRemove);
		
	}

}
