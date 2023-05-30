package com.warework.service.datastore.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.service.datastore.DatastoreServiceImpl;

/**
 * Abstract connector that specifies common information required to create
 * connections for Data Stores.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractDatastoreConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Service where Clients or Data Stores created by this
	 * Connector can work.
	 * 
	 * @return Type of the Service.<br>
	 *         <br>
	 */
	protected Class<DatastoreServiceImpl> getServiceType() {
		return DatastoreServiceImpl.class;
	}

}
