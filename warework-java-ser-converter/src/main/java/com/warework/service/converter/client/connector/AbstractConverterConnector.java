package com.warework.service.converter.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.service.converter.ConverterServiceImpl;

/**
 * This connector holds common information required to create connections for
 * converters.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractConverterConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the service where clients created by this connector can
	 * work.
	 * 
	 * @return Type of the service.
	 */
	protected Class<ConverterServiceImpl> getServiceType() {
		return ConverterServiceImpl.class;
	}

}
