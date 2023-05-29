package com.warework.service.log.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.service.log.LogServiceImpl;

/**
 * This Connector holds common information required to create connections for
 * Loggers.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractLoggerConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Service where Clients created by this Connector can
	 * work.
	 * 
	 * @return Type of the Service.<br>
	 * <br>
	 */
	protected Class<LogServiceImpl> getServiceType() {
		return LogServiceImpl.class;
	}

}
