package com.warework.core.scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.model.Client;
import com.warework.core.model.ObjectReference;
import com.warework.core.model.Provider;
import com.warework.core.model.Scope;
import com.warework.core.model.Service;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.AbstractService;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;
import com.warework.core.service.ServiceFacade;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.service.converter.ConverterServiceConstants;
import com.warework.service.converter.ConverterServiceFacade;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.workflow.WorkflowServiceConstants;
import com.warework.service.workflow.WorkflowServiceFacade;

/**
 * Provides a default implementation for a Scope.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractScope implements ScopeFacade {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Flag that indicates if Scope is running.
	private boolean active;

	// Name of the Scope.
	private String name;

	// Parent and Domain scopes.
	private ScopeFacade parent, domain;

	// Last time Scope was used.
	private long time, timeout;

	// Context for initialization parameters, Providers, Services, Object
	// References and attributes.

	private Map<String, Object> parameters, attributes, providers, services;

	private Map<String, ObjectReference> objects;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @param parent Parent Scope for this Scope.<br>
	 *               <br>
	 * @param domain Domain Scope for this Scope.<br>
	 *               <br>
	 * @throws ScopeException
	 */
	AbstractScope(final Scope config, final ScopeFacade parent, final ScopeFacade domain) throws ScopeException {

		// Set parent Scope.
		this.parent = parent;

		// Set domain Scope.
		this.domain = domain;

		// Set the name for the Scope.
		this.name = config.getName();

		// Setup initialization parameters.
		setupParameters(config);

		// Setup the timeout value.
		this.timeout = 0;

		// Start the Scope.
		start();

		// Set that Scope is enabled.
		this.active = true;

		// Get if Services and Providers should not be created on start up.
		final boolean lazyLoad = isLazyLoad();

		// Setup providers. MUST CREATE BEFORE SERVICES!
		setupProviders(config, lazyLoad);

		// Setup default log Service.
		setupDefaultLog(config);

		// Log a message.
		info("##### WAREWORK IS SETTING UP SCOPE '" + this.name + "'...");

		// Setup services. MUST CREATE AFTER PROVIDERS!
		setupServices(config, lazyLoad);

		// Setup Object References. MUST CREATE AFTER SERVICES!
		setupObjectReferences(config);

		// Sets custom Scope initialization.
		initialize(config);

		// Log message..
		info("##### WAREWORK CONFIGURED SCOPE '" + this.name + "' AND NOW IT'S READY.");

	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets custom Scope initialization.
	 * 
	 * @param config Scope configuration.<br>
	 *               <br>
	 * @throws ScopeException If there is an error when trying to initialize the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected abstract void initialize(final Scope config) throws ScopeException;

	/**
	 * Terminates the execution of the Scope.
	 */
	protected abstract void shutdown();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets if Scope is alive.
	 * 
	 * @return <code>true</code> if Scope is still running and <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 */
	public boolean active() {

		// Validate scope is started.
		if (active) {
			if (timeout <= 0) {
				return true;
			} else {

				// Get the current time.
				final long currentTime = System.currentTimeMillis();

				// Validate if Scope is out of date.
				if ((currentTime - time) >= timeout) {

					// Need to reset timeout here to avoid recursive loops with
					// log method.
					timeout = 0;

					// Log message.
					warn("WAREWORK cannot perform more operations with Scope '" + getName()
							+ "' because it passed the timeout limit.");

					// Close the Scope.
					close();

				} else {

					// Update time.
					time = currentTime;

					// Indicate that Scope is enabled.
					return true;

				}

			}
		}

		// Indicate that Scope is not enabled.
		return false;

	}

	/**
	 * Gets the name of this Scope.
	 * 
	 * @return Name of the Scope.<br>
	 *         <br>
	 */
	public String getName() {
		return name;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Parent Scope of this Scope.
	 * 
	 * @return Parent Scope.<br>
	 *         <br>
	 */
	public ScopeFacade getParent() {
		return active() ? parent : null;
	}

	/**
	 * Gets the Domain Scope of this Scope.
	 * 
	 * @return Domain Scope.<br>
	 *         <br>
	 */
	public ScopeFacade getDomain() {
		return active() ? domain : null;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if Scope has no
	 *         initialization parameters.<br>
	 *         <br>
	 */
	public Enumeration<String> getInitParameterNames() {

		// Validate.
		if ((active()) && (this.parameters != null) && (this.parameters.size() > 0)) {

			// Create a Vector for the initialization parameters.
			List<String> parameters = new ArrayList<String>();

			// Extract Parent parameters.
			if ((getParent() != null)
					&& (!this.parameters.containsKey(ScopeL1Constants.PARAMETER_EXTEND_PARENT_INIT_PARAMETERS))
					|| (validateParameter(
							this.parameters.get(ScopeL1Constants.PARAMETER_EXTEND_PARENT_INIT_PARAMETERS)))) {

				// Get the Parent's initialization parameters.
				final Enumeration<String> parentParamNames = getParent().getInitParameterNames();

				// Copy Parent's initialization parameters only if they exist.
				if (parentParamNames != null) {
					parameters = DataStructureL1Helper.toArrayList(parentParamNames);
				}

			}

			// Extract Domain parameters.
			if ((getDomain() != null) && (validateParameter(
					this.parameters.get(ScopeL1Constants.PARAMETER_IMPORT_DOMAIN_INIT_PARAMETERS)))) {

				// Get the Domain's initialization parameters.
				final Enumeration<String> domainParamNames = getDomain().getInitParameterNames();

				// Copy Domain's initialization parameters only if they exist.
				if (domainParamNames != null) {
					while (domainParamNames.hasMoreElements()) {
						parameters.add(domainParamNames.nextElement());
					}
				}

			}

			// Get the names of the parameters of this Scope.
			final Enumeration<String> paramNames = Collections.enumeration(this.parameters.keySet());

			// Extract current Scope parameters.
			while (paramNames.hasMoreElements()) {
				parameters.add(paramNames.nextElement());
			}

			// Remove duplicated names.
			parameters = DataStructureL1Helper.removeDuplicated(parameters);

			// Return the name of each parameter.
			if (parameters.size() > 0) {
				return DataStructureL1Helper.toEnumeration(parameters);
			}

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	public java.lang.Object getInitParameter(final String name) {

		// Validate.
		if ((active()) && (parameters != null) && (parameters.size() > 0)) {

			// Get the parameter value.
			final Object parameterValue = parameters.get(name);

			// Return the paramter value.
			if (parameterValue != null) {
				return parameterValue;
			} else if ((getParent() != null)
					&& (!parameters.containsKey(ScopeL1Constants.PARAMETER_EXTEND_PARENT_INIT_PARAMETERS))
					|| (validateParameter(parameters.get(ScopeL1Constants.PARAMETER_EXTEND_PARENT_INIT_PARAMETERS)))) {
				return getParent().getInitParameter(name);
			} else if ((getDomain() != null)
					&& (validateParameter(parameters.get(ScopeL1Constants.PARAMETER_IMPORT_DOMAIN_INIT_PARAMETERS)))) {
				return getDomain().getInitParameter(name);
			}

		}

		// Nothing to return at this point.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the objects bound to this Scope.
	 * 
	 * @return Names of the objects or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getAttributeNames() {

		// Validate.
		if ((active()) && (attributes != null) && (attributes.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(attributes.keySet());
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Gets the object bound with the specified name in this Scope, or
	 * <code>null</code> if no object is bound under the name.
	 * 
	 * @param name Name of the object.<br>
	 *             <br>
	 * @return Object that exists in this Scope or <code>null</code> if no object is
	 *         bound with this name.<br>
	 *         <br>
	 */
	public Object getAttribute(final String name) {

		// Validate.
		if ((active()) && (attributes != null)) {
			return attributes.get(name);
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Binds an object to a given attribute name to this Scope, using the name
	 * specified. If an object of the same name is already bound to the Scope, the
	 * object is replaced.
	 * 
	 * @param name  The name to which the object is bound. If an object of the same
	 *              name is already bound to this session, the object is
	 *              replaced.<br>
	 *              <br>
	 * @param value The object to be bound.<br>
	 *              <br>
	 */
	public void setAttribute(final String name, final Object value) {
		if (active()) {

			// Create the attributes context when needed.
			if (attributes == null) {
				attributes = new HashMap<String, Object>();
			}

			// Set the attribute.
			attributes.put(name, value);

		}
	}

	/**
	 * Removes the object bound with the specified name from this Scope. If this
	 * Scope does not have an object bound with the specified name, this method does
	 * nothing.
	 * 
	 * @param name The name to which the object is bound.<br>
	 *             <br>
	 */
	public void removeAttribute(final String name) {
		if ((active()) && (attributes != null)) {

			// Remove the attribute.
			attributes.remove(name);

			// Remove the context if it's empty.
			if (attributes.size() < 1) {
				attributes = null;
			}

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the providers bound to this Scope, Parent Scope and
	 * Domain Scope.
	 * 
	 * @return Names of the providers or null if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getProviderNames() {

		// Validate that requested operation is in time.
		if (active()) {

			// Get the names that exist in this Scope.
			Enumeration<String> enum1 = null;
			if ((providers != null) && (providers.size() > 0)) {
				enum1 = DataStructureL1Helper.toEnumeration(providers.keySet());
			}

			// Get the names that exist at Parent Scope.
			Enumeration<String> enum2 = null;
			if (getParent() != null) {
				enum2 = getParent().getProviderNames();
			}

			// Get the names that exist at Domain Scope.
			Enumeration<String> enum3 = null;
			if (getDomain() != null) {
				enum3 = getDomain().getProviderNames();
			}

			// Merge names and return them.
			return DataStructureL1Helper.mergeNames(enum1, DataStructureL1Helper.mergeNames(enum2, enum3));

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Validates if a Provider is bound to this Scope, Parent Scope or Domain Scope.
	 * 
	 * @param name The name to which the Provider is bound.<br>
	 *             <br>
	 * @return <code>true</code> if the Provider exists and <code>false</code> if
	 *         the Provider does not exists.<br>
	 *         <br>
	 */
	public boolean existsProvider(final String name) {

		// Validate that requested operation is in time.
		if (active()) {

			// Gets the names of all the Providers bound to this Scope.
			final Enumeration<String> providerNames = getProviderNames();

			// Search for the Provider.
			if (providerNames != null) {
				while (providerNames.hasMoreElements()) {
					if (providerNames.nextElement().equals(name)) {
						return true;
					}
				}
			}

		}

		// At this point, return false.
		return false;

	}

	/**
	 * Creates a Provider and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Provider is bound. If a Provider of
	 *                   the same name is already bound to this Scope, an exception
	 *                   is thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Provider (class that extends
	 *                   <code>com.warework.core.provider.AbstractProvider</code>
	 *                   class).<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the
	 *                   Provider.<br>
	 *                   <br>
	 * @throws ProviderException If there is an error when trying to create the
	 *                           Provider.<br>
	 *                           <br>
	 */
	public void createProvider(final String name, final Class<? extends AbstractProvider> type,
			final Map<String, Object> parameters) throws ProviderException {
		if (active()) {
			setupProvider(name, type, parameters);
		}
	}

	/**
	 * Removes the Provider bound with the specified name from this Scope. If this
	 * Scope does not have a Provider bound with the specified name, this method
	 * does nothing.
	 * 
	 * @param name The name to which the Provider is bound.<br>
	 *             <br>
	 * @throws ProviderException If there is an error when trying to remove the
	 *                           Provider.<br>
	 *                           <br>
	 */
	public void removeProvider(final String name) throws ProviderException {
		if (active()) {
			deleteProvider(name);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the Services bound to this Scope, Parent Scope and
	 * Domain Scope.
	 * 
	 * @return Names of the Services or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getServiceNames() {

		// Validate that requested operation is in time.
		if (active()) {

			// Get the names that exist in this Scope.
			Enumeration<String> enum1 = null;
			if ((services != null) && (services.size() > 0)) {
				enum1 = DataStructureL1Helper.toEnumeration(services.keySet());
			}

			// Get the names that exist at Parent Scope.
			Enumeration<String> enum2 = null;
			if (getParent() != null) {
				enum2 = getParent().getServiceNames();
			}

			// Get the names that exist at Domain Scope.
			Enumeration<String> enum3 = null;
			if (getDomain() != null) {
				enum3 = getDomain().getServiceNames();
			}

			// Merge names and return them.
			return DataStructureL1Helper.mergeNames(enum1, DataStructureL1Helper.mergeNames(enum2, enum3));

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Gets the Service bound with the specified name in this Scope, Parent Scope or
	 * Domain Scope, or <code>null</code> if no Service is bound under the name.
	 * 
	 * @param name Name of the Service.<br>
	 *             <br>
	 * @return A Service or <code>null</code> if no Service is bound with this
	 *         name.<br>
	 *         <br>
	 */
	public ServiceFacade getService(final String name) {

		// Validate that requested operation is in time.
		if (active()) {

			// Get the Service.
			ServiceFacade service = null;
			if ((services != null) && (services.containsKey(name))) {

				// Get the facade or the Service's config.
				final Object lazyFlag = services.get(name);

				// Create the Service if it is not ready.
				if (lazyFlag instanceof Service) {
					try {

						// Get the Service configuration.
						final Service config = (Service) lazyFlag;

						// Create the Service.
						service = setupService(config.getName(), ReflectionL1Helper.getServiceType(config.getClazz()),
								Parameter.toHashMap(config.getParameters()));

					} catch (final Exception e) {
						// DO NOT PERFORM LOG HERE!!!
						return null;
					}
				} else {
					service = (ServiceFacade) lazyFlag;
				}

			}

			// Return the Service.
			if (service != null) {
				return service;
			} else if (getParent() != null) {

				// Get the service from Parent.
				final ServiceFacade parentService = getParent().getService(name);

				// Return service.
				return ((parentService == null) && (getDomain() != null)) ? getDomain().getService(name)
						: parentService;

			} else if (getDomain() != null) {
				return getDomain().getService(name);
			}

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Creates a Service and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Service is bound. If a Service of the
	 *                   same name is already bound to this Scope, an exception is
	 *                   thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Service (class that extends
	 *                   <code>com.warework.core.service.AbstractService</code>
	 *                   class).<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the Service.
	 * @return A new Service instance.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Service.<br>
	 *                          <br>
	 */
	public ServiceFacade createService(final String name, final Class<? extends ServiceFacade> type,
			final Map<String, Object> parameters) throws ServiceException {

		// Validate that requested operation is in time.
		if (active()) {
			return setupService(name, type, parameters);
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Removes the Service bound with the specified name from this Scope. If this
	 * Scope does not have a Service bound with the specified name, this method does
	 * nothing.
	 * 
	 * @param name The name to which the Service is bound.<br>
	 *             <br>
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Service.<br>
	 *                          <br>
	 */
	public void removeService(final String name) throws ServiceException {
		if (active()) {
			deleteService(name);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the Object References bound to this Scope.
	 * 
	 * @return Names of the references or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getObjectNames() {

		// Validate scope.
		if (active()) {

			// Get the names that exist in this Scope.
			Enumeration<String> enum1 = null;
			if ((objects != null) && (objects.size() > 0)) {
				enum1 = DataStructureL1Helper.toEnumeration(objects.keySet());
			}

			// Get the names that exist at Parent Scope.
			Enumeration<String> enum2 = null;
			if (getParent() != null) {
				enum2 = getParent().getObjectNames();
			}

			// Get the names that exist at Domain Scope.
			Enumeration<String> enum3 = null;
			if (getDomain() != null) {
				enum3 = getDomain().getObjectNames();
			}

			// Merge names and return them.
			return DataStructureL1Helper.mergeNames(enum1, DataStructureL1Helper.mergeNames(enum2, enum3));

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Gets an object from a Provider that is referenced with the specified name.
	 * This reference may exists in this Scope.
	 * 
	 * @param name Name of the provider-object reference. Check how this name is
	 *             given at <code>createObject</code> method.<br>
	 *             <br>
	 * @return Object from a Provider. If no reference, Provider or object exists in
	 *         the Provider then this method returns <code>null</code> .<br>
	 *         <br>
	 */
	public Object getObject(final String name) {

		// Validate scope.
		if (active()) {

			// Get the Object Reference.
			ObjectReference object = null;
			if (objects != null) {
				object = (ObjectReference) objects.get(name);
			}

			// Get the object from the Provider.
			if (object != null) {
				return getObject(object.getProvider(), object.getObject());
			} else if (getParent() != null) {
				return getParent().getObject(name);
			} else if (getDomain() != null) {
				return getDomain().getObject(name);
			}

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Gets an object from a Provider.
	 * 
	 * @param providerName   Name of the Provider that should exists in this
	 *                       Scope.<br>
	 *                       <br>
	 * @param providerObject Name of the object in the Provider.<br>
	 *                       <br>
	 * @return Object from the Provider. If no Provider or object exists in the
	 *         Provider then this method returns <code>null</code>.<br>
	 *         <br>
	 */
	public Object getObject(final String providerName, final String providerObject) {

		// Validate that requested operation is in time.
		if (active()) {

			// Get the Provider.
			AbstractProvider provider = null;
			if ((providers != null) && (providers.containsKey(providerName))) {

				// Get the facade or the Provider's config.
				final Object lazyFlag = providers.get(providerName);

				// Create the Provider if it is not ready.
				if (lazyFlag instanceof Provider) {
					try {

						// Get the Provider's configuration.
						final Provider config = (Provider) lazyFlag;

						// Create the Provider.
						setupProvider(providerName, ReflectionL1Helper.getProviderType(config.getClazz()),
								Parameter.toHashMap(config.getParameters()));

						// Get the provider.
						provider = (AbstractProvider) providers.get(providerName);

					} catch (final Exception e) {
						// DO NOT PERFORM LOG HERE!!!
						return null;
					}
				} else {
					provider = (AbstractProvider) lazyFlag;
				}

			}

			// Get the object.
			if (provider != null) {
				return provider.lookup(providerObject);
			} else if (getParent() != null) {
				return getParent().getObject(providerName, providerObject);
			} else if (getDomain() != null) {
				return getDomain().getObject(providerName, providerObject);
			}

		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Creates a reference to an object that should exist in a Provider, registers
	 * the reference in this Scope using the name specified and returns an instance
	 * of the object.
	 * 
	 * @param name           Name of the Object Reference to register. If a
	 *                       reference of the same name is already bound to this
	 *                       Scope, the reference is replaced.<br>
	 *                       <br>
	 * @param providerName   Name of the Provider that exists in this Scope.<br>
	 *                       <br>
	 * @param providerObject Name of the object in the Provider.<br>
	 *                       <br>
	 * @return Object from the Provider. If no Provider or object exists in the
	 *         Provider then this method returns <code>null</code>.<br>
	 *         <br>
	 */
	public Object createObject(final String name, final String providerName, final String providerObject) {

		// Validate scope.
		if (active()) {
			return setupObjectReference(name, providerName, providerObject);
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Removes an Object Reference that is bound to this Scope.
	 * 
	 * @param name Name of the reference to remove.<br>
	 *             <br>
	 */
	public void removeObject(final String name) {
		if ((active()) && (objects != null)) {

			// Remove the object.
			objects.remove(name);

			// Remove the context if it's empty.
			if (objects.size() < 1) {
				objects = null;
			}

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message with the default Log Service.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'log-service' that
	 * implements the <code>com.warework.service.log.LogServiceFacade</code>
	 * interface and then invokes the log operation on it with a logger/client named
	 * <code>default-client</code>. If no Service or logger is found then log is not
	 * performed. <br>
	 * <br>
	 * By default the Log Service is not initialized. The easiest way to turn it on
	 * is with the method <code>enableDefaultLog()</code> of the Scope
	 * initialization class ( <code>com.warework.core.model.Scope</code>). Invoke
	 * it, create a new Scope with this configuration and the Scope will setup
	 * everything needed to run this log method. <br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * logger that the Log Service contains. So if your logger performs, for
	 * example, operations in a database, it connects to the database everytime it's
	 * closed and it keeps the connecion open.
	 * 
	 * @param message  Message to log.<br>
	 *                 <br>
	 * @param logLevel Indicates how to perform the log. Check out the default
	 *                 levels at
	 *                 <code>com.warework.service.log.LogServiceConstants</code> and
	 *                 keep in mind that each logger provides specific log levels
	 *                 (loggers may define these levels as public constants).<br>
	 *                 <br>
	 */
	public void log(final String message, final int logLevel) {
		if (active()) {

			// Get the log Service.
			final LogServiceFacade service = getLogService();

			// Validate Service and perform log.
			if ((service != null) && (service.existsClient(LogServiceConstants.DEFAULT_CLIENT_NAME))) {

				// Connect the log Service when required.
				if (!service.isConnected(LogServiceConstants.DEFAULT_CLIENT_NAME)) {
					try {
						service.connect(LogServiceConstants.DEFAULT_CLIENT_NAME);
					} catch (final ServiceException e) {
						return;
					}
				}

				// Perform log.
				service.log(LogServiceConstants.DEFAULT_CLIENT_NAME, message, logLevel);

			}

		}
	}

	/**
	 * Logs an info message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_INFO);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	public void info(final String message) {
		log(message, LogServiceConstants.LOG_LEVEL_INFO);
	}

	/**
	 * Logs a debug message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_DEBUG);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	public void debug(final String message) {
		log(message, LogServiceConstants.LOG_LEVEL_DEBUG);
	}

	/**
	 * Logs a warning message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_WARN);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	public void warn(final String message) {
		log(message, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Transforms a given object into another object.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'converter-service' that
	 * implements the
	 * <code>com.warework.service.converter.ConverterServiceFacade</code> interface
	 * and then invokes the <code>transform</code> operation on it.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * converter client that the Converter Service contains.
	 * 
	 * @param converterName The name to which the Converter is bound in the
	 *                      Service.<br>
	 *                      <br>
	 * @param source        Source object to transform.<br>
	 *                      <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to perform the
	 *                          transformation.<br>
	 *                          <br>
	 */
	public Object transform(final String converterName, final Object source) throws ServiceException {

		// Get the Service.
		final ServiceFacade service = getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

		// Validate it is a Converter Service.
		if ((service != null) && (service instanceof ConverterServiceFacade)) {

			// Get the Converter Service.
			final ConverterServiceFacade converterService = (ConverterServiceFacade) service;

			// Connect converter client.
			if (!converterService.isConnected(converterName)) {
				converterService.connect(converterName);
			}

			// Transform object.
			return converterService.transform(converterName, source);

		} else {
			throw new ServiceException(this,
					"WAREWORK cannot transform object because Service '"
							+ ConverterServiceConstants.DEFAULT_SERVICE_NAME
							+ "' does not exists or it is not a Converter Service.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Transforms an object from a Provider into another object.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'converter-service' that
	 * implements the
	 * <code>com.warework.service.converter.ConverterServiceFacade</code> interface
	 * and then invokes the <code>transform</code> operation on it.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * converter client that the Converter Service contains.
	 * 
	 * @param converterName  The name to which the Converter is bound in the
	 *                       Service.<br>
	 *                       <br>
	 * @param providerName   Name of the Provider where to retrieve the source
	 *                       object to transform.<br>
	 *                       <br>
	 * @param providerObject Name of the object to retrieve from the Provider that
	 *                       represents the source object to transform.<br>
	 *                       <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to perform the
	 *                          transformation.<br>
	 *                          <br>
	 */
	public Object transform(final String converterName, final String providerName, final String providerObject)
			throws ServiceException {

		// Get the Service.
		final ServiceFacade service = getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

		// Validate it is a Converter Service.
		if ((service != null) && (service instanceof ConverterServiceFacade)) {

			// Get the Converter Service.
			final ConverterServiceFacade converterService = (ConverterServiceFacade) service;

			// Connect converter client.
			if (!converterService.isConnected(converterName)) {
				converterService.connect(converterName);
			}

			// Transform object.
			return converterService.transform(converterName, providerName, providerObject);

		} else {
			throw new ServiceException(this,
					"WAREWORK cannot transform object because Service '"
							+ ConverterServiceConstants.DEFAULT_SERVICE_NAME
							+ "' does not exists or it is not a Converter Service.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes a process or task in a Workflow Engine.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'workflow-service' that
	 * implements the
	 * <code>com.warework.service.workflow.WorkflowServiceFacade</code> interface
	 * and then invokes the <code>execute</code> operation on it with a
	 * engine/client named <code>default-client</code>. If no Service or engine is
	 * found then operation is not performed.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * engine that the Workflow Service contains.
	 * 
	 * @param contextScope Name of the Scope that exists in the context of this
	 *                     Scope and contains the Workflow Service where to execute
	 *                     the operation. If this argument is <code>null</code> then
	 *                     this method will look for the Workflow Service in this
	 *                     Scope.<br>
	 *                     <br>
	 * @param processName  Name of the process or task to execute.<br>
	 *                     <br>
	 * @param variables    Input variables for the process.<br>
	 *                     <br>
	 * @return Result of the execution of the process.<br>
	 *         <br>
	 * @throws ServiceException ServiceException If there is an error when trying to
	 *                          execute the process / task.<br>
	 *                          <br>
	 */
	public Object execute(final String contextScope, final String processName, final Map<String, Object> variables)
			throws ServiceException {

		// Get the Service.
		ServiceFacade service = null;
		if (contextScope == null) {
			service = getService(WorkflowServiceConstants.DEFAULT_SERVICE_NAME);
		} else if (getContext().exists(contextScope)) {
			service = getContext().get(contextScope).getService(WorkflowServiceConstants.DEFAULT_SERVICE_NAME);
		}

		// Validate it is a Workflow Service.
		if ((service != null) && (service instanceof WorkflowServiceFacade)) {

			// Get the Workflow Service.
			final WorkflowServiceFacade workflowService = (WorkflowServiceFacade) service;

			// Connect engine.
			if (!workflowService.isConnected(ProxyServiceConstants.DEFAULT_CLIENT_NAME)) {
				workflowService.connect(ProxyServiceConstants.DEFAULT_CLIENT_NAME);
			}

			// Execute the process.
			return workflowService.execute(ProxyServiceConstants.DEFAULT_CLIENT_NAME, processName, variables);

		} else {
			throw new ServiceException(this,
					"WAREWORK cannot execute the process because Service '"
							+ WorkflowServiceConstants.DEFAULT_SERVICE_NAME
							+ "' does not exists or it is not a Workflow Service.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Scope.
	 * 
	 * @return Name of the Scope.<br>
	 *         <br>
	 */
	public String toString() {
		return getName();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Setup initialization parameters.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException
	 */
	protected void setupParameters(final Scope config) throws ScopeException {

		// Get initialization parameter names.
		final Enumeration<String> parameterNames = config.getInitParameterNames();

		// Setup the parameters.
		if (parameterNames != null) {

			// Create a map for the parameters.
			parameters = new HashMap<String, Object>();

			// Copy each parameter into the map.
			while (parameterNames.hasMoreElements()) {

				// Get the name of a parameter.
				final String parameterName = parameterNames.nextElement();

				// Set the value of the parameter.
				parameters.put(parameterName, config.getInitParameter(parameterName));

			}

		}

	}

	/**
	 * Registers that Scope is initialized.
	 * 
	 * @throws ScopeException
	 */
	protected void start() throws ScopeException {

		// Get the timeout parameter value.
		final Object parameter = getInitParameter(ScopeL1Constants.PARAMETER_SCOPE_TIMEOUT);

		// Validate the parameter value. WARNING!: It may throw an exception
		// so every subclass must catch it.
		long scopeTimeout = 0;
		if (parameter != null) {
			if (parameter instanceof String) {
				try {
					scopeTimeout = Long.parseLong((String) parameter);
				} catch (final Exception e) {
					throw new ScopeException(null, "WAREWORK cannot create a new instance of Scope '" + getName()
							+ "' because the timeout parameter value is wrong. Check out this error: " + e.getMessage(),
							e, -1);
				}
			} else if (parameter instanceof Long) {
				scopeTimeout = ((Long) parameter).longValue();
			}
		}

		// Set the initialization time.
		if (scopeTimeout > 0) {
			time = System.currentTimeMillis();
		}

		// Setup the timeout value.
		timeout = scopeTimeout;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Provider and binds it to this Scope, using the name specified.
	 * 
	 * @param provider Provider configuration.<br>
	 *                 <br>
	 */
	protected void setupProvider(final Provider provider) {

		// Initialize Provider's context.
		if (providers == null) {
			providers = new HashMap<String, Object>();
		}

		// Register the Provider.
		providers.put(provider.getName(), provider);

	}

	/**
	 * Creates a Provider and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Provider is bound. If a Provider of
	 *                   the same name is already bound to this Scope, an exception
	 *                   is thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Provider (class that extends
	 *                   <code>com.warework.core.provider.AbstractProvider</code>
	 *                   class).<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the
	 *                   Provider.<br>
	 *                   <br>
	 * @throws ProviderException If there is an error when trying to create the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void setupProvider(final String name, final Class<? extends AbstractProvider> type,
			final Map<String, Object> parameters) throws ProviderException {

		// Validate that no Provider already exists with this name.
		if ((providers != null) && (providers.containsKey(name))) {

			// Get the facade or the Provider's config.
			final Object lazyFlag = providers.get(name);

			// Throw exception if Provider is already created.
			if (lazyFlag instanceof AbstractProvider) {
				throw new ProviderException(this,
						"WAREWORK cannot create Provider '" + name
								+ "' again in this Scope as a Provider with this name already exists.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Create an instance of the Provider.
		AbstractProvider provider = null;
		try {
			provider = type.newInstance();
		} catch (final Exception e) {
			throw new ProviderException(this,
					"WAREWORK cannot create a new instance of Provider '" + name
							+ "' because its facade class can not be instantiated.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Initialize the Provider.
		provider.init(this, name, parameters);

		// Initialize the providers context when needed.
		if (providers == null) {
			providers = new HashMap<String, Object>();
		}

		// Register the Provider.
		providers.put(name, provider);

		// Log a message.
		info("WAREWORK created Provider '" + name + "'.");

	}

	/**
	 * Creates a Service and binds it to this Scope, using the name specified.
	 * 
	 * @param service Service configuration.<br>
	 *                <br>
	 */
	protected void setupService(final Service service) {

		// Initialize Service's context.
		if (services == null) {
			services = new HashMap<String, Object>();
		}

		// Register the Service.
		services.put(service.getName(), service);

	}

	/**
	 * Creates a Service and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Service is bound. If a Service of the
	 *                   same name is already bound to this Scope, an exception is
	 *                   thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Service (class that extends
	 *                   <code>com.warework.core.service.AbstractService</code>
	 *                   class).<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the Service.
	 * @return A new Service instance.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Service.<br>
	 *                          <br>
	 */
	protected ServiceFacade setupService(final String name, final Class<? extends ServiceFacade> type,
			final Map<String, Object> parameters) throws ServiceException {

		// Validate that no Service already exists with this name.
		if ((services != null) && (services.containsKey(name))) {

			// Get the facade or the Service's config.
			final Object lazyFlag = services.get(name);

			// Throw exception if Service is already created.
			if (lazyFlag instanceof ServiceFacade) {
				throw new ServiceException(this,
						"WAREWORK cannot create Service '" + name
								+ "' again in this Scope as a Service with this name already exists.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Create an instance of the Service.
		AbstractService service = null;
		try {
			service = (AbstractService) type.newInstance();
		} catch (final Exception e) {
			throw new ServiceException(this,
					"WAREWORK cannot create a new instance of Service '" + name
							+ "' because its facade class can not be instantiated.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Initialize the Service.
		service.init(this, name, parameters);

		// Initialize the services context when needed.
		if (services == null) {
			services = new HashMap<String, Object>();
		}

		// Register the Service.
		services.put(name, service);

		// Log a message.
		info("WAREWORK created Service '" + name + "'.");

		// Return the Service.
		return service;

	}

	/**
	 * Creates a reference to an object that should exist in a Provider, registers
	 * the reference in this Scope using the name specified and returns an instance
	 * of the object.
	 * 
	 * @param name           Name of the Object Reference to register. If a
	 *                       reference of the same name is already bound to this
	 *                       Scope, the reference is replaced.<br>
	 *                       <br>
	 * @param providerName   Name of the Provider that exists in this Scope.<br>
	 *                       <br>
	 * @param providerObject Name of the object in the Provider.<br>
	 *                       <br>
	 * @return Object from the Provider. If no Provider or object exists in the
	 *         Provider then this method returns <code>null</code>.<br>
	 *         <br>
	 */
	protected Object setupObjectReference(final String name, final String providerName, final String providerObject) {

		// Get a record for the object.
		ObjectReference reference = null;
		if (objects == null) {

			// Initialize the objects registry.
			objects = new HashMap<String, ObjectReference>();

			// Create a new record for the object so Warework can register
			// it.
			reference = new ObjectReference();

		} else if (objects.containsKey(name)) {
			reference = (ObjectReference) objects.get(name);
		} else {
			reference = new ObjectReference();
		}

		// Setup the record values.
		reference.setName(name);
		reference.setProvider(providerName);
		reference.setObject(providerObject);

		// Register the object.
		objects.put(name, reference);

		// Return the object from the Provider.
		return getObject(providerName, providerObject);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes the Provider bound with the specified name from this Scope. If this
	 * Scope does not have a Provider bound with the specified name, this method
	 * does nothing.
	 * 
	 * @param name The name to which the Provider is bound.<br>
	 *             <br>
	 * @throws ProviderException If there is an error when trying to remove the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void deleteProvider(final String name) throws ProviderException {
		if ((providers != null) && (providers.containsKey(name))) {

			// Log message.
			debug("WAREWORK will try to remove Provider '" + name + "'...");

			// Get the Provider or the Provider's config.
			final Object lazyFlag = providers.get(name);

			// Close the Provider if required.
			if (lazyFlag instanceof AbstractProvider) {

				// Get the Provider.
				final AbstractProvider provider = (AbstractProvider) lazyFlag;

				// Shut down the Provider.
				provider.close();

			}

			// Remove the instance of the Provider.
			providers.remove(name);

			// Log message.
			info("WAREWORK removed Provider '" + name + "'.");

			// Remove the context if it's empty.
			if (providers.size() < 1) {
				providers = null;
			}

		}
	}

	/**
	 * Removes every Service. If one fails, this method doesn't stop and tries to
	 * delete the next one. It also deletes the default log at the end so Warework
	 * can log that other services are deleted.
	 */
	protected void deleteServices() {

		// Log message.
		info("WAREWORK is removing Services...");

		// Get the name of the services.
		final Enumeration<String> serviceNames = getServiceNames();

		// Remove services if the exist.
		if (serviceNames != null) {

			// Flag to remove the log Service at the end.
			boolean removeDefaultLogService = false;

			// Remove services.
			while (serviceNames.hasMoreElements()) {

				// Get the name of a Service.
				final String serviceName = serviceNames.nextElement();

				// Remove the Service (not the log Service).
				if (serviceName.equals(LogServiceConstants.DEFAULT_SERVICE_NAME)) {
					removeDefaultLogService = true;
				} else {
					try {
						deleteService(serviceName);
					} catch (final ServiceException e) {
						warn("WAREWORK cannot remove Service '" + serviceName + "'.");
					}
				}

			}

			// Remove the log Service.
			if (removeDefaultLogService) {

				// Get the Service that may represent the Log Service.
				final Object service = ((services != null)
						&& (services.containsKey(LogServiceConstants.DEFAULT_SERVICE_NAME)))
								? services.get(LogServiceConstants.DEFAULT_SERVICE_NAME)
								: null;

				// Validate it is the Log Service.
				if ((service != null) && (service instanceof LogServiceFacade)) {

					// Get the Log Service.
					final LogServiceFacade logService = (LogServiceFacade) service;

					// Get Log Service client names.
					final Enumeration<String> clientNames = logService.getClientNames();

					// Validate Log Service has clients.
					if (clientNames != null) {

						// Flag to remove the log Service at the end.
						boolean removeDefaultLogClient = false;

						// Remove each Log Service Client except the default client.
						while (clientNames.hasMoreElements()) {

							// Get one client name.
							final String clientName = clientNames.nextElement();

							// Remove client.
							if (clientName.equals(LogServiceConstants.DEFAULT_CLIENT_NAME)) {
								removeDefaultLogClient = true;
							} else {
								try {
									logService.removeClient(clientName);
								} catch (final ServiceException e) {
									warn("WAREWORK cannot remove Log Service client '" + clientName + "'.");
								}
							}

						}

						// Log message.
						info("##### WAREWORK FINISHED SCOPE '" + getName() + "'.");

						// Remove default log client.
						if (removeDefaultLogClient) {
							try {
								logService.removeClient(LogServiceConstants.DEFAULT_CLIENT_NAME);
							} catch (final ServiceException e) {
								// DO NOT PERFORM LOG HERE.
							}
						}

						// Remove Log Service.
						try {
							deleteService(LogServiceConstants.DEFAULT_SERVICE_NAME);
						} catch (final ServiceException e) {
							// DO NOT PERFORM LOG HERE.
						}

					}

				}

			}

		}

	}

	/**
	 * Removes the Service bound with the specified name from this Scope. If this
	 * Scope does not have a Service bound with the specified name, this method does
	 * nothing.
	 * 
	 * @param name The name to which the Service is bound.<br>
	 *             <br>
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Service.<br>
	 *                          <br>
	 */
	protected void deleteService(final String name) throws ServiceException {
		if ((services != null) && (services.containsKey(name))) {

			// Log message.
			debug("WAREWORK will try to remove Service '" + name + "'...");

			// Get the Service or the Service's config.
			final Object lazyFlag = services.get(name);

			// Close the Service if required.
			if (lazyFlag instanceof AbstractService) {

				// Get the Service.
				final AbstractService service = (AbstractService) lazyFlag;

				// Shut down the Service.
				service.close();

			}

			// Remove the Service from the context.
			services.remove(name);

			// Log message.
			info("WAREWORK removed Service '" + name + "'.");

			// Remove the context if it's empty.
			if (services.size() < 1) {
				services = null;
			}

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Scope.
	 */
	void close() {
		if (active) {

			// Log message.
			info("##### WAREWORK is shutting down Scope '" + getName() + "'...");

			// This line first, user may need services, objects and so on to
			// perform clean up operations.
			shutdown();

			// Remove every attribute.
			deleteAttributes();

			// Remove every object.
			deleteObjects();

			// Remove every Provider.
			deleteProviders();

			// Remove every Service.
			deleteServices();

			// Specify that Scope is disabled.
			active = false;

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Providers.
	 * 
	 * @param config   Scope's configuration.<br>
	 *                 <br>
	 * @param lazyLoad Load Services and Parameters on start up.<br>
	 *                 <br>
	 * @throws ScopeException If there is an error when trying to initialize the
	 *                        Providers.<br>
	 *                        <br>
	 */
	private void setupProviders(final Scope config, final boolean lazyLoad) throws ScopeException {

		// Get provider names.
		final Enumeration<String> providerNames = config.getProviderNames();

		// Create providers.
		if (providerNames != null) {
			try {
				while (providerNames.hasMoreElements()) {

					// Get the Provider.
					final Provider provider = config.getProvider(providerNames.nextElement());

					// Create the Provider.
					if (lazyLoad) {
						setupProvider(provider);
					} else {
						setupProvider(provider.getName(), ReflectionL1Helper.getProviderType(provider.getClazz()),
								Parameter.toHashMap(provider.getParameters()));
					}

				}
			} catch (final ClassNotFoundException e) {
				throw new ScopeException(this,
						"WAREWORK cannot create a new instance of Scope '" + config.getName()
								+ "' because given Provider class does not exists.",
						e, LogServiceConstants.LOG_LEVEL_FATAL);
			} catch (final Exception e) {
				throw new ScopeException(this, "WAREWORK cannot create a new instance of Scope '" + config.getName()
						+ "' because the initialization of a Provider generated the following error: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_FATAL);
			}
		}

	}

	/**
	 * Initializes the default log for the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException If there is an error when trying to initialize the
	 *                        default Log.<br>
	 *                        <br>
	 */
	@SuppressWarnings("unchecked")
	private void setupDefaultLog(final Scope config) throws ScopeException {

		// Get the Service.
		final Service service = config.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

		// Create the Service only if it exists.
		if (service != null) {

			// Get the name of the clients.
			final Enumeration<String> clientNames = config.getClientNames(service.getName());

			// Create the Service only if it contains at least one client.
			if (clientNames != null) {
				try {

					// Create the Service.
					final ProxyServiceFacade serviceFacade = (ProxyServiceFacade) setupService(service.getName(),
							ReflectionL1Helper.getServiceType(service.getClazz()),
							Parameter.toHashMap(service.getParameters()));

					// Create each logger.
					while (clientNames.hasMoreElements()) {

						// Get the name of the logger.
						final String clientName = clientNames.nextElement();

						// Create the client only if it doesn't
						// exists.
						if (!serviceFacade.existsClient(clientName)) {

							// Get the definition of a logger.
							final Client client = config.getClient(service.getName(), clientName);

							// Create the logger.
							serviceFacade.createClient(client.getName(),
									(Class<? extends ConnectorFacade>) Class.forName(client.getConnector()),
									Parameter.toHashMap(client.getParameters()));

						}

					}

				} catch (final Exception e) {
					throw new ScopeException(null, "WAREWORK cannot create a new instance of Scope '" + config.getName()
							+ "' because the initialization of the default Log Service generates the following error: "
							+ e.getMessage(), e, -1);
				}
			} else {
				try {
					setupService(service.getName(), ReflectionL1Helper.getServiceType(service.getClazz()),
							Parameter.toHashMap(service.getParameters()));
				} catch (final Exception e) {
					throw new ScopeException(null, "WAREWORK cannot create a new instance of Scope '" + config.getName()
							+ "' because the initialization of the default Log Service generates the following error: "
							+ e.getMessage(), e, -1);
				}
			}

		}

	}

	/**
	 * Initializes the Services.
	 * 
	 * @param config   Scope's configuration.<br>
	 *                 <br>
	 * @param lazyLoad Load Services and Parameters on start up.<br>
	 *                 <br>
	 * @throws ScopeException If there is an error when trying to initialize the
	 *                        Services.<br>
	 *                        <br>
	 */
	private void setupServices(final Scope config, final boolean lazyLoad) throws ScopeException {

		// Get services names.
		final Enumeration<String> serviceNames = config.getServiceNames();

		// Create services.
		if (serviceNames != null) {
			try {
				while (serviceNames.hasMoreElements()) {

					// Get the Service.
					final Service service = config.getService((String) serviceNames.nextElement());

					// Create the Service.
					if (!service.getName().equals(LogServiceConstants.DEFAULT_SERVICE_NAME)) {
						if (lazyLoad) {
							setupService(service);
						} else {
							setupService(service.getName(), ReflectionL1Helper.getServiceType(service.getClazz()),
									Parameter.toHashMap(service.getParameters()));
						}
					}

				}
			} catch (final ClassNotFoundException e) {
				throw new ScopeException(this,
						"WAREWORK cannot create a new instance of Scope '" + config.getName()
								+ "' because a given Service or client's connector class does not exists.",
						e, LogServiceConstants.LOG_LEVEL_FATAL);
			} catch (final Exception e) {
				throw new ScopeException(this, "WAREWORK cannot create a new instance of Scope '" + config.getName()
						+ "' because the initialization of a Service generated the following error: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_FATAL);
			}
		}

	}

	/**
	 * Initializes the Object References.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException If there is an error when trying to initialize the
	 *                        Object References.<br>
	 *                        <br>
	 */
	private void setupObjectReferences(final Scope config) throws ScopeException {

		// Get object names.
		final Enumeration<String> objectNames = config.getObjectReferenceNames();

		// Create objects.
		if (objectNames != null) {
			while (objectNames.hasMoreElements()) {

				// Get the object.
				final ObjectReference objectRecord = config.getObjectReference((String) objectNames.nextElement());

				// Create the object.
				setupObjectReference(objectRecord.getName(), objectRecord.getProvider(), objectRecord.getObject());

			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the default Log Service.
	 * 
	 * @return Default Log Service.<br>
	 *         <br>
	 */
	private LogServiceFacade getLogService() {

		// Get the log Service.
		final ServiceFacade service = getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

		// Return the Service.
		if ((service != null) && (service instanceof LogServiceFacade)) {
			return (LogServiceFacade) service;
		} else {
			return null;
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes every attribute.
	 */
	private void deleteAttributes() {

		// Log message.
		info("WAREWORK is removing Scope attributes...");

		// Get the attributes' names.
		final Enumeration<String> names = getAttributeNames();

		// Remove the attributes.
		if (names != null) {
			while (names.hasMoreElements()) {
				removeAttribute(names.nextElement());
			}
		}

	}

	/**
	 * Removes every object-provider reference.
	 */
	private void deleteObjects() {

		// Log message.
		info("WAREWORK is removing object references...");

		// Get the name of the objects.
		final Enumeration<String> objectNames = getObjectNames();

		// Remove every object.
		if (objectNames != null) {
			while (objectNames.hasMoreElements()) {
				removeObject((String) objectNames.nextElement());
			}
		}

	}

	/**
	 * Removes every Provider. If one fails, this method doesn't stop and tries to
	 * delete the next one.
	 */
	private void deleteProviders() {

		// Log message.
		info("WAREWORK is removing Providers...");

		// Get the name of the providers.
		final Enumeration<String> providerNames = getProviderNames();

		// Remove the providers.
		if (providerNames != null) {
			while (providerNames.hasMoreElements()) {

				// Get the name of the Provider.
				final String providerName = providerNames.nextElement();

				// Remove the Provider.
				try {
					deleteProvider(providerName);
				} catch (final ProviderException e) {
					warn("WAREWORK cannot remove Provider'" + providerName + "'.");
				}

			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Evaluates a boolean parameter.
	 * 
	 * @param parameter Parameter as a <code>java.lang.String</code> or
	 *                  <code>java.lang.Object</code> object.<br>
	 *                  <br>
	 * @return <code>true</code> if the value of the parameter equals to TRUE and
	 *         <code>false</code> if not.<br>
	 *         <br>
	 */
	private boolean validateParameter(final Object parameter) {

		// Decide if the Scope must look for Parent parameters.
		if (parameter != null) {
			if ((parameter instanceof Boolean) && (((Boolean) parameter).equals(Boolean.TRUE))) {
				return true;
			} else if ((parameter instanceof String)
					&& (((String) parameter).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
				return true;
			}
		}

		// At this point, return false.
		return false;

	}

	/**
	 * Gets the boolean value of an initialization parameter.
	 * 
	 * @return <code>true</code> if initializacion parameter is <code>true</code> as
	 *         a boolean value or a s
	 */
	private boolean isLazyLoad() {

		// Validate that parameter exists.
		if ((parameters != null) && (parameters.containsKey(ScopeL1Constants.PARAMETER_LAZY_LOAD))) {

			// Get the parameter that indicates that Services should not be
			// created on start up.
			final Object value = parameters.get(ScopeL1Constants.PARAMETER_LAZY_LOAD);

			// Decide if the Scope must look for Parent parameters.
			if ((value instanceof Boolean) && (((Boolean) value).equals(Boolean.FALSE))) {
				return false;
			} else if ((value instanceof String)
					&& (((String) value).equalsIgnoreCase(CommonValueL1Constants.STRING_FALSE))) {
				return false;
			}

		}

		// Return if the Scope must load Services and Parameters on start up.
		return true;

	}

}
