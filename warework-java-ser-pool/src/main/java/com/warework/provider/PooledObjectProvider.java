package com.warework.provider;

import java.util.Enumeration;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.ServiceFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.pool.PoolServiceFacade;

/**
 * Warework Pooled Object Provider is responsible for retrieving pooled objects
 * from the Pool Service. The way it works is very simple: you have to specify
 * the name of a Pooler/Client that exists in the Pool Service and this Provider
 * will return the pooled objects that this Pooler can provide.<br>
 * <br>
 * <b>Configure and create a Pooled Object Provider</b><br>
 * <br>
 * To configure this Provider you just need to give the name of the Pool Service
 * where to retrieve pooled objects from Poolers:<br>
 * <br>
 * <code>
 * // Create the configuration of the Provider. <br>
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider. <br>
 * parameters.put(PooledObjectProvider. PARAMETER_SERVICE_NAME, "pool-service");<br> 
 * <br>
 * // Create the Provider. <br>
 * scope.createProvider("pooled-object-provider", PooledObjectProvider.class, parameters);<br> 
 * </code> <br>
 * <b>Retrieve objects from a Pooled Object Provider</b><br>
 * <br>
 * At this point the Pooled Object Provider is running and we can request
 * objects from it. To do so, we just need to provide the name of a Pooler that
 * exists in the Pool Service.<br>
 * <br>
 * <code>
 * // Get a pooled object from 'sample-pooler'. <br>
 * Object pooledObject = scope.getObject("pooled-object-provider", "sample-pooler");<br>
 * </code> <br>
 * This line of code performs the following actions:<br>
 * <br>
 * <ol>
 * <li>The <code>pooled-object-provider</code> gets an instance of the Pool
 * Service named <code>pool-service</code>.</li>
 * <li>The <code>pooled-object-provider</code> gets a pooled object from
 * <code>sample-pooler</code> and returns it.</li>
 * </ol>
 * 
 * <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class PooledObjectProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "pooled-object" + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the name of the Pool Service where to
	 * retrieve pooled object instances. This parameter is mandatory in order to
	 * make this Provider work and it must be a string object with the name of an
	 * existing Pool Service.
	 */
	public static final String PARAMETER_SERVICE_NAME = "service-name";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the Service.
	private String serviceName;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		serviceName = null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to initialize the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void initialize() throws ProviderException {

		// Get the name of the Service.
		final Object nameOfService = getInitParameter(PARAMETER_SERVICE_NAME);

		// Validate and store the name of the Service.
		if ((nameOfService != null) && (nameOfService instanceof String)) {
			serviceName = (String) nameOfService;
		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName() + "' because given parameter '"
							+ PARAMETER_SERVICE_NAME + "' is null or it is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * This method does not perform any operation.
	 */
	protected void connect() {
		// DO NOTHING.
	}

	/**
	 * This method does not perform any operation.
	 */
	protected void disconnect() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection of the Provider is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Retrieves a pooled object from a Pool Service.
	 * 
	 * @param name Name of the Pooler that exists in the Pool Service (specified
	 *             with the parameter <code>PARAMETER_ServiceName</code>) where to
	 *             retrieve a pooled object.<br>
	 *             <br>
	 * @return A pooled object from a Pool Service.<br>
	 *         <br>
	 */
	protected Object getObject(final String name) {

		// Get the Pool Service.
		final ServiceFacade service = getScopeFacade().getService(serviceName);

		// Validate the Service.
		if ((service != null) && (service instanceof PoolServiceFacade)) {

			// Get the Pool Facade.
			final PoolServiceFacade datastoreService = (PoolServiceFacade) service;

			// Return a pooled object form the Pool Service.
			return datastoreService.getObject(name);

		} else {

			// Log a Warework message.
			getScopeFacade().log("WAREWORK cannot retrieve object '" + name + "' from Provider '" + getName()
					+ "' because the name of the Service where to retrieve a pooled object is null or it is not a Pool Service.",
					LogServiceConstants.LOG_LEVEL_WARN);

			// Nothing to return.
			return null;

		}

	}

	/**
	 * Gets the names of every Pooler that exist in the Pool Service.
	 * 
	 * @return Names of the objects that this Provider accepts.<br>
	 *         <br>
	 */
	protected Enumeration<String> getObjectNames() throws ProviderException {

		// Get the Pool Service.
		final ServiceFacade service = getScopeFacade().getService(serviceName);

		// Validate the Service.
		if ((service != null) && (service instanceof PoolServiceFacade)) {

			// Get the Pool Facade.
			final PoolServiceFacade poolService = (PoolServiceFacade) service;

			// Return the name of every Pooler that exist in the Pool Service
			return poolService.getClientNames();

		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot retrieve the names of the objects in Provider '" + getName()
							+ "' because Service '" + serviceName
							+ "' does not exists or it is not an instance of the Pool Service.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
