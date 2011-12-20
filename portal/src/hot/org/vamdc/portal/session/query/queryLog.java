package org.vamdc.portal.session.query;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.vamdc.portal.entity.query.Query;

@Name("queryLog")
@Scope(ScopeType.EVENT)
public class queryLog {

	@In(create=true) PersistentQueryLog persistentQueryLog;
	
	@In(create=true) SessionQueryLog sessionQueryLog;
	
	@Logger private Log log;
	
	
	public List<Query> getQueries(){
		if (persistentQueryLog.getStoredQueries().size()>0)
			return persistentQueryLog.getStoredQueries();
		else
			return sessionQueryLog.getStoredQueries();
	}
	
}
