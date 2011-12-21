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

	private static final long serialVersionUID = 2555499604910810322L;
	private List<Query> storedQueries = new ArrayList<Query>();

	public List<Query> getStoredQueries() {
		return Collections.unmodifiableList(storedQueries);
	}

	public void addQuery(Query newQuery){
		synchronized(storedQueries){
			this.storedQueries.add(newQuery);
		}
	}
	
}
