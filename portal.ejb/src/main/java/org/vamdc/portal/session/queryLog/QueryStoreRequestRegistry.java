package org.vamdc.portal.session.queryLog;

import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.web.ServletContexts;
import org.vamdc.portal.session.security.UserInfo;

@Name("querystore")
@Scope(ScopeType.PAGE)
public class QueryStoreRequestRegistry {

	/**
	 * Map storing last uuid for a given node key = node id
	 */
	private Map<String, QueryStoreProcessor> querystoreProcessors = new HashMap<String, QueryStoreProcessor>(
			5);

	public void launchQueryStoreProcessor(String token, String nodeIvoaID ){
		/**
		 * send request to querystore, do nothing if already done once on this page
		 */
		if (!this.querystoreProcessors.containsKey(nodeIvoaID)){
			this.querystoreProcessors.put(nodeIvoaID, new QueryStoreProcessor(token));
			this.querystoreProcessors.get(nodeIvoaID).process();
		}
	}

	/**
	 * return result of querystore for a request
	 * 
	 * @return
	 **/	
	public Map<String, QueryStoreProcessor> getQuerystoreProcessors() {
		return this.querystoreProcessors;
	}	
	
	
	/**
	 * Get IP address of the user that is asking for the association
	 * Requires access to ServletContexts
	 * 
	 * @return
	 */
	public String getIpAdress() {		
		String ipAddress = ServletContexts.instance().getRequest()
				.getHeader("X-FORWARDED-FOR");
		
		if (ipAddress == null) {
			ipAddress = ServletContexts.instance().getRequest().getRemoteAddr();
		}
		return ipAddress;
	}

}
