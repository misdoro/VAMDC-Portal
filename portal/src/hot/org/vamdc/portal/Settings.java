package org.vamdc.portal;

import org.jboss.seam.contexts.ServletLifecycle;

/**
 * Various configuration properties names and their default values.
 * @author doronin
 *
 */
public enum Settings {

	HTTP_CONNECT_TIMEOUT("HTTPConnTimeout", "2000"),
	HTTP_DATA_TIMEOUT("HTTPDataTimeout","30000"),
	REGISTRY_URL("registryURL","http://casx019-zone1.ast.cam.ac.uk/registry/services/RegistryQueryv1_0"),
	REGISTRY_UPDATE_INTERVAL("registryUpdateInterval","300000"),
	AVAILABILITY_MONITOR_INTERVAL("availabilityMonitorInterval","300000"),
	REGISTRY_RETRY_INTERVAL("registryRetryInterval","10000"),
	HTTP_HEAD_TIMEOUT("HTTPHeadTimeout","60000"),
	;
	
	
	private final String key;
	private final String defValue;
	private String value;
	
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
		String path = ServletLifecycle.getServletContext().getContextPath();
		if (path!=null && path.length()>1){
			path=path.substring(1);
			specificValue = System.getProperty(path+"."+this.key);
		}
		
		if (specificValue!=null && specificValue.length()>0)
			this.value = specificValue;
		else
			this.value = System.getProperty(this.key, this.defValue);
		return this.value;
	}
	
}
