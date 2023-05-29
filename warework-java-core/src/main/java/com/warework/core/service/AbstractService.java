package com.warework.core.service;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractService implements ServiceFacade {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope where this Service belongs to.
	private ScopeFacade scope;

	// Name of the Service.
	private String name;

	// Initialization parameters (as string values) for this Service.
	private Map<String, Object> initParameters;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Service.
	 * 
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Service.<br>
	 *                          <br>
	 */
	public abstract void close() throws ServiceException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Service.
	 * 
	 * @throws ServiceException If there is an error when trying to initialize the
	 *                          Proxy Service.<br>
	 *                          <br>
	 */
	protected abstract void initialize() throws ServiceException;

	/**
	 * Configures the Service.
	 * 
	 * @param config Service's configuration.<br>
	 *               <br>
	 * @throws ServiceException If there is an error when trying to configure the
	 *                          Service.<br>
	 *                          <br>
	 */
	protected abstract void configure(final Object config) throws ServiceException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Service.
	 * 
	 * @param scope      Scope where this Service belongs to.<br>
	 *                   <br>
	 * @param name       Name of the Service.<br>
	 *                   <br>
	 * @param parameters Initialization parameters (as string-object pairs) for this
	 *                   Service. If parameter
	 *                   <code>com.warework.core.service.ServiceConstants.PARAMETER_CONFIG_CLASS</code>
	 *                   is defined then this method loads a specific configuration
	 *                   loader class for this Service, loads the configuration and
	 *                   pass the configuration object to the Service so the Service
	 *                   can be configured. If this parameter does not exists then
	 *                   this method looks for the parameter
	 *                   <code>com.warework.core.service.ServiceConstants.PARAMETER_CONFIG_TARGET</code>
	 *                   and if it's defined, then this method retrieves directly
	 *                   the configuration object from this parameter and pass it to
	 *                   the Service so the Service can be configured. If any of
	 *                   those parameters are defined then Service is not
	 *                   configured.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to initialize the
	 *                          Service.<br>
	 *                          <br>
	 */
	public void init(final ScopeFacade scope, final String name, final Map<String, Object> parameters)
			throws ServiceException {

		// Validate that Scope exists.
		if (scope == null) {
			if ((name == null) || (name.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ServiceException(null,
						"WAREWORK cannot create Service '" + name + "' because given Scope is null.", null, -1);
			} else {
				throw new ServiceException(null, "WAREWORK cannot create Service because given Scope is null.", null,
						-1);
			}
		}

		// Validate the name of the Service.
		if ((name == null) || (name.equals(CommonValueL1Constants.STRING_EMPTY))) {
			throw new ServiceException(scope, "WAREWORK cannot create the Service in Scope '" + scope.getName()
					+ "' because given name is null or empty.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Set the Scope.
		this.scope = scope;

		// Set the name of the Service.
		this.name = name;

		// Set the initialization parameters.
		this.initParameters = parameters;

		// Initialize the Service.
		initialize();

		// Get the type of the configuration.
		final Class<? extends LoaderFacade> serviceConfigType = getConfigType(scope, name);

		// Configure the Service.
		if (serviceConfigType == null) {

			// Get the object that holds the configuration of the Service.
			final Object configTarget = getInitParameter(ServiceConstants.PARAMETER_CONFIG_TARGET);

			// Configure the Service with the object.
			if (configTarget != null) {
				configure(configTarget);
			}

		} else {
			configureService(scope, serviceConfigType, name, parameters);
		}

	}

	/**
	 * Gets the name of the Service.
	 * 
	 * @return Name of the Service.<br>
	 *         <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the Scope where this Service belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	public ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Executes a Service operation.
	 * 
	 * @param operationName Name of the operation to execute.<br>
	 *                      <br>
	 * @param parameters    Operation parameters.<br>
	 *                      <br>
	 * @return Operation result.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to executea Service
	 *                          operation.<br>
	 *                          <br>
	 */
	public Object execute(final String operationName, final Map<String, Object> parameters) throws ServiceException {
		throw new ServiceException(
				getScopeFacade(), "WAREWORK cannot execute '" + operationName
						+ "' operation because it is not supported by the '" + getName() + "' Service.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Gets the name of the Service.
	 * 
	 * @return Name of the Service.<br>
	 *         <br>
	 */
	public String toString() {
		return getName();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if no one
	 *         exist.<br>
	 *         <br>
	 */
	protected Enumeration<String> getInitParameterNames() {
		return (initParameters == null) ? null : DataStructureL1Helper.toEnumeration(initParameters.keySet());
	}

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	protected Object getInitParameter(final String name) {
		return (initParameters == null) ? null : initParameters.get(name);
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the configuration Loader.
	 * 
	 * @param scope Scope where this Service belongs to.<br>
	 *              <br>
	 * @param name  Name of the Service.<br>
	 *              <br>
	 * @return Configuration type.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to get
	 *                          configuration loader type.<br>
	 *                          <br>
	 */
	@SuppressWarnings("unchecked")
	private Class<? extends LoaderFacade> getConfigType(final ScopeFacade scope, final String name)
			throws ServiceException {

		// Get the configuration parameter.
		Object configParameter = getInitParameter(ServiceConstants.PARAMETER_CONFIG_CLASS);

		// Get the type of the configuration.
		if (configParameter != null) {
			if (configParameter instanceof Class) {
				return (Class<? extends LoaderFacade>) configParameter;
			} else if (configParameter instanceof String) {
				try {
					return ReflectionL1Helper.getLoaderType(this, (String) configParameter);
				} catch (final ClassNotFoundException e) {
					throw new ServiceException(scope,
							"WAREWORK cannot create Service '" + name
									+ "' because given configuration loader class does not exists.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Configures a Service.
	 * 
	 * @param scope             Scope where this Service belongs to.<br>
	 *                          <br>
	 * @param serviceConfigType Type of the configuration.<br>
	 *                          <br>
	 * @param name              Name of the Service.<br>
	 *                          <br>
	 * @param parameters        Initialization parameters (as string-object pairs)
	 *                          for this Service. If parameter
	 *                          <code>com.warework.core.service.ServiceConstants.PARAMETER_CONFIG_CLASS</code>
	 *                          is defined then this method loads a specific
	 *                          configuration loader class for this Service, loads
	 *                          the configuration and pass the configuration object
	 *                          to the Service so the Service can be configured. If
	 *                          this parameter does not exists then this method
	 *                          looks for the parameter
	 *                          <code>com.warework.core.service.ServiceConstants.PARAMETER_CONFIG_TARGET</code>
	 *                          and if it's defined, then this method retrieves
	 *                          directly the configuration object from this
	 *                          parameter and pass it to the Service so the Service
	 *                          can be configured. If any of those parameters are
	 *                          defined then Service is not configured.<br>
	 *                          <br>
	 * @throws ServiceException If there is an error when trying to configure the
	 *                          Service.<br>
	 *                          <br>
	 */
	private void configureService(final ScopeFacade scope, final Class<? extends LoaderFacade> serviceConfigType,
			final String name, final Map<String, Object> parameters) throws ServiceException {

		// Get the configuration of the Service.
		Object serviceConfig = null;
		try {
			serviceConfig = AbstractLoader.load(getScopeFacade(), serviceConfigType, parameters);
		} catch (final LoaderException e) {
			throw new ServiceException(scope,
					"WAREWORK cannot create Service '" + name + "' because its configuration cannot be loaded.", e,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Configure the Service.
		if (serviceConfig != null) {
			configure(serviceConfig);
		} else if (getInitParameter(ServiceConstants.PARAMETER_CONFIG_TARGET) != null) {
			configure(getInitParameter(ServiceConstants.PARAMETER_CONFIG_TARGET));
		}

	}

}
