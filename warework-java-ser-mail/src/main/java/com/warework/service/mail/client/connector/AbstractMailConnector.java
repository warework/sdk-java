package com.warework.service.mail.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.service.mail.MailServiceImpl;

/**
 * Abstract Connector that holds common information required to create
 * connections with mail servers.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractMailConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the service where clients created by this connector can
	 * work.
	 * 
	 * @return Type of the service.
	 */
	protected Class<MailServiceImpl> getServiceType() {
		return MailServiceImpl.class;
	}

}
