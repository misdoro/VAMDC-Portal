package org.vamdc.portal.registry;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Callable;

import net.ivoa.xml.vosiavailability.v1.Availability;

import org.vamdc.portal.Settings;
import org.vamdc.portal.registry.AvailabilityMonitor.Status;

import com.sun.jersey.api.client.WebResource;

public class AvailabilityClient implements Callable<AvailabilityMonitor.Status>{

	private URL endpointURL;
	
	public AvailabilityClient(URL endpointURL){
		this.endpointURL = endpointURL;
	}
	
	public Status call() {
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
		client.setConnectTimeout(Settings.HTTP_CONNECT_TIMEOUT.getInt());
		client.setReadTimeout(Settings.HTTP_DATA_TIMEOUT.getInt());
		WebResource availResource = null;
		try {
			availResource = client.resource(endpointURL.toURI());
		} catch (URISyntaxException e) {
		}
		Availability avail = availResource.get(Availability.class);
		if (avail!=null && avail.isAvailable())
			return Status.OK;
		else
			return Status.FAIL;
	}

}
