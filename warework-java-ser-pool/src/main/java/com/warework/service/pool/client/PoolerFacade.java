package com.warework.service.pool.client;

import com.warework.core.service.client.ClientFacade;

/**
 * Client to perform Pooler operations.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface PoolerFacade extends ClientFacade {

	/**
	 * Gets a pooled object from the Pool.
	 * 
	 * @return Pooled object from a pool.<br>
	 * <br>
	 */
	Object getObject();

}
