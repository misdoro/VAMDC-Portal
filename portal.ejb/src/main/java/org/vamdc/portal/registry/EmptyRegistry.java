package org.vamdc.portal.registry;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import net.ivoa.xml.voresource.v1.Resource;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.RegistryCommunicationException;

/**
 * Dummy registry client that is returned when the registry response is not yet ready
 * @author doronin
 *
 */
public class EmptyRegistry implements Registry{

	public URL getAvailabilityURL(String arg0)
			throws RegistryCommunicationException {
		throw new RegistryCommunicationException("Please wait for querying registry");
	}

	public URL getCapabilitiesURL(String arg0)
			throws RegistryCommunicationException {
		throw new RegistryCommunicationException("Please wait for querying registry");
	}

	public Collection<String> getIVOAIDs(Service arg0)
			throws RegistryCommunicationException {
		return Collections.emptyList();
	}

	public Resource getResourceMetadata(String arg0)
			throws RegistryCommunicationException {
		throw new RegistryCommunicationException("Please wait for querying registry");
	}

	public Set<Restrictable> getRestrictables(String arg0)
			throws RegistryCommunicationException {
		return Collections.emptySet();
	}

	public URL getVamdcTapURL(String arg0)
			throws RegistryCommunicationException {
		throw new RegistryCommunicationException("Please wait for querying registry");
	}

}
