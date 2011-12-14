package org.vamdc.portal;

import org.jboss.seam.annotations.Name;
import org.vamdc.portal.Settings;

/**
 * Class providing access to enum settings getters, used from the xhtml pages. 
 * @author doronin
 *
 */
@Name("Setting")
public class Setting {
	public String getConsumerAccessURL(){
		return Settings.STATIC_ACCESS_URL.get();
	}
	
	public String getDisplayURL(){
		return Settings.DISPLAY_URL.get();
	}
	
}
