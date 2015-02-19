package org.vamdc.portal;

import javax.servlet.ServletContext;

import org.jboss.seam.contexts.ServletLifecycle;

/**
 * Various configuration properties names and their default values.
 * @author doronin
 *
 */
public enum Settings {

	HTTP_CONNECT_TIMEOUT("HTTPConnTimeout", "2000"),
	HTTP_DATA_TIMEOUT("HTTPDataTimeout","30000"),
	REGISTRY_URL("registryURL","http://registry.vamdc.eu/registry-12.07/services/RegistryQueryv1_0"),
	REGISTRY_UPDATE_INTERVAL("registryUpdateInterval","300000"),
	AVAILABILITY_MONITOR_INTERVAL("availabilityMonitorInterval","300000"),
	REGISTRY_RETRY_INTERVAL("registryRetryInterval","10000"),
	HTTP_HEAD_TIMEOUT("HTTPHeadTimeout","60000"),
	PDL_SERVER_URL("pdlServerURL", "http://vm-euhoutestc62.obspm.fr/vamdc/OnlineCode?"),
	;
	
	
	private final String key;
	private final String defValue;
	private String value=null;
	
	Settings(String key, String def){
		this.key = key;
		this.defValue=def;
	}
	
	public final String get(){
		if (value!=null)
			return value;
		return loadValue();
	}
	
	public final int getInt(){
		if (value!=null)
			return Integer.valueOf(value);
		return Integer.valueOf(loadValue());
	}
	
	private final String loadValue(){
		String specificValue="";
		ServletContext ctx=ServletLifecycle.getServletContext();
		String path = null;
		if (ctx!=null)
			path = ctx.getContextPath();
		if (path!=null && path.length()>1){
			path=path.substring(1)+"."+this.key;
			specificValue = System.getProperty(path);
		}
		
		if (specificValue!=null && specificValue.length()>0){
			this.value = specificValue;
		}
		else
			this.value = System.getProperty(this.key, this.defValue);
		return this.value;
	}
	
}
