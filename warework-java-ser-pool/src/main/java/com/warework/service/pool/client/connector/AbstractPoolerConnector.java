package com.warework.service.pool.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.service.pool.PoolServiceImpl;

/**
 * This connector holds common information required to create connections for
 * Poolers.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractPoolerConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the service where clients created by this connector can
	 * work.
	 * 
	 * @return Type of the service.<br>
	 * <br>
	 */
	protected Class<PoolServiceImpl> getServiceType() {
		return PoolServiceImpl.class;
	}

}
