package com.warework.service.pool;

import java.util.Map;

import com.warework.core.service.AbstractProxyService;
import com.warework.core.service.ServiceException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.pool.client.PoolerFacade;

/**
 * Pool service implementation. <br>
 * Warework Pool Service is a manager for Clients that implement the Object Pool
 * design pattern and it provides the necessary methods to retrieve objects from
 * pools in a very easy way.<br>
 * <br>
 * An object pool is a set of initialized objects that are kept ready to use,
 * rather than allocated and destroyed on demand. Object pooling can offer a
 * significant performance boost in situations where the cost of initializing a
 * class instance is high, the rate of instantiation of a class is high, and the
 * number of instances in use at any one time is low. The pooled object is
 * obtained in predictable time when creation of the new objects (especially
 * over network) may take variable time. These benefits are mostly true for
 * objects which are expensive with respect to time, such as database or socket
 * connections.<br>
 * <br>
 * <b>Create and retrieve a Pool Service</b><br>
 * <br>
 * To create the Pool Service in a Scope, you always need to provide a unique
 * name for the Service and the <code>PoolServiceImpl</code> class that exists
 * in the <code>com.warework.service.pool</code> package:<br>
 * <br>
 * <code>
 * // Create the Pool Service and register it in a Scope.<br> 
 * scope.createService("pool-service", PoolServiceImpl.class, null);<br>
 * </code> <br>
 * Once it is created, you can get it using the same name (when you retrieve an
 * instance of a Pool Service, you will get the <code>PoolServiceFacade</code>
 * interface):<br>
 * <br>
 * <code>
 * // Get an instance of the Pool Service.<br> 
 * PoolServiceFacade poolService = (PoolServiceFacade) scope.getService("pool-service");<br>
 * </code> <br>
 * Most of the times, you will need to specify a set of parameters that
 * configure how the Service must work. Review the next section to know how to
 * define Poolers/Clients with these parameters.<br>
 * <br>
 * <b>Add and connect Poolers</b><br>
 * <br>
 * Now the Pool Service is running but you need at least one Client or Pooler
 * where to perform operations. To add a Pooler into the Service you have to
 * invoke method <code>createClient()</code> that exists in its Facade. This
 * method requests a name for the new Pooler and a Connector which performs the
 * creation of the Pooler. Let's see how to register a sample Pooler in this
 * Service:<br>
 * <br>
 * <code>
 * // Add a Pooler in the Pool Service.<br> 
 * poolService.createClient("sample-pooler", SampleConnector.class, null);<br>
 * </code> <br>
 * The <code>SampleConnector</code> class creates the Sample Pooler and
 * registers it in the Pool Service. After that, we have to tell the Pool
 * Service that we want to perform operations with the Sample Pooler. We do so
 * by connecting the Pooler:<br>
 * <br>
 * <code>
 * // Connect the Sample Pooler.<br> 
 * poolService.connect("sample-pooler");<br>
 * </code> <br>
 * Remember to review the documentation of each Pooler to know which parameters
 * it accepts.<br>
 * <br>
 * <b>Perform Pool operations</b><br>
 * <br>
 * Each Pooler exposes its functionality in the PoolServiceFacade interface and
 * it is where you can find the necessary method to retrieve objects from pools.
 * This interface is really simple. As it represents a Proxy Service, you can
 * handle multiple Poolers and you have to identify each one by name. So, if you
 * need to retrieve an object from a pool, you just have to specify the name of
 * the Pooler/Client to use:<br>
 * <br>
 * <code>
 * // Get an object from the pool.<br> 
 * Object pooledObject = poolService.getObject("sample-pooler");<br>
 * </code> <br>
 * And that's it. The previous line of code simply indicates the Pooler to use
 * to retrieve a pooled object. Each Pooler returns a specific type of pooled
 * object, for example: if your Pooler represents a pool of database
 * connections, it will provide a reusable database connection.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class PoolServiceImpl extends AbstractProxyService implements PoolServiceFacade {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a pooled object from a Pooler.
	 * 
	 * @param poolName Name of the Pooler where to retrieve a pooled object.<br>
	 *                 <br>
	 * @return Object from a pool of objects.<br>
	 *         <br>
	 */
	public Object getObject(final String poolName) {

		// Get the facade of the Pool Service.
		final PoolerFacade pool = (PoolerFacade) getClient(poolName);

		// Return a pooled object from the Pooler.
		if (pool == null) {

			// Log a message.
			getScopeFacade().log(
					"WAREWORK cannot retrieve an object from pool '" + poolName + "' because it does not exists.",
					LogServiceConstants.LOG_LEVEL_WARN);

			// Return nothing.
			return null;

		} else {
			return pool.getObject();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a Mail Service operation.
	 * 
	 * @param operationName Name of the operation to execute.<br>
	 *                      <br>
	 * @param parameters    Operation parameters.<br>
	 *                      <br>
	 * @return Operation result.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          Proxy Service operation.<br>
	 *                          <br>
	 */
	public Object execute(final String operationName, final Map<String, Object> parameters) throws ServiceException {
		if (operationName.equals(PoolServiceConstants.OPERATION_NAME_GET_OBJECT)) {
			return executeGetObject(parameters);
		} else {
			return super.execute(operationName, parameters);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a pooled object from a Pooler.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          Proxy Service operation.<br>
	 *                          <br>
	 */
	private Object executeGetObject(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(PoolServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot execute '"
					+ PoolServiceConstants.OPERATION_NAME_GET_OBJECT + "' operation because given parameter '"
					+ PoolServiceConstants.OPERATION_PARAMETER_CLIENT_NAME + "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the object from the pool.
		return getObject((String) clientName);

	}

}
