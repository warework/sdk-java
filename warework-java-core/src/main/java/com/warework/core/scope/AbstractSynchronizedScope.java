package com.warework.core.scope;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.model.ObjectReference;
import com.warework.core.model.Provider;
import com.warework.core.model.Scope;
import com.warework.core.model.Service;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.AbstractService;
import com.warework.core.service.ServiceException;
import com.warework.core.service.ServiceFacade;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;

/**
 * Provides a default implementation for a Scope that will be used in a
 * multi-threaded environment. This implementation is usefull when multiple
 * threads access the same scope instance.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractSynchronizedScope extends AbstractScope {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Context for initialization parameters, Providers, Services, Object
	// References and attributes.

	private Map<String, Object> attributes, providers, services;

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
	AbstractSynchronizedScope(final Scope config, final ScopeFacade parent, final ScopeFacade domain)
			throws ScopeException {
		super(config, parent, domain);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the objects bound to this Scope.
	 * 
	 * @return Names of the objects or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getAttributeNames() {

		// Validate.
		if ((active()) && (attributes != null)) {
			synchronized (attributes) {
				if (attributes.size() > 0) {
					return DataStructureL1Helper.toEnumeration(attributes.keySet());
				}
			}
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
			synchronized (attributes) {
				return attributes.get(name);
			}
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

			// Initialize attributes when required.
			if (attributes == null) {
				attributes = new HashMap<String, Object>();
			}

			// Save attribute.
			synchronized (attributes) {
				attributes.put(name, value);
			}

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
			synchronized (attributes) {
				attributes.remove(name);
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
			if (providers != null) {
				synchronized (providers) {
					if (providers.size() > 0) {
						enum1 = DataStructureL1Helper.toEnumeration(providers.keySet());
					}
				}
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
		if ((active()) && (providers != null)) {
			synchronized (providers) {
				if (providers.containsKey(name)) {

					// Get the Provider or the Provider's config.
					final Object lazyFlag = providers.get(name);

					// Close the Provider if required.
					if (lazyFlag instanceof AbstractProvider) {

						// Get the Provider.
						final AbstractProvider provider = (AbstractProvider) lazyFlag;

						// Shut down the Provider.
						provider.close();

						// Log message.
						log("WAREWORK removed Provider '" + name + "'.", LogServiceConstants.LOG_LEVEL_INFO);

					}

					// Remove the instance of the Provider.
					providers.remove(name);

				}
			}
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
			if (services != null) {
				synchronized (services) {
					if (services.size() > 0) {
						enum1 = DataStructureL1Helper.toEnumeration(services.keySet());
					}
				}
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

			// Get the Service configuration or Service implementation.
			Object lazyFlag = null;
			if (services != null) {
				synchronized (services) {
					if (services.containsKey(name)) {
						lazyFlag = services.get(name);
					}
				}
			}

			// Create the Service if it is not ready.
			ServiceFacade service = null;
			if (lazyFlag != null) {
				if (lazyFlag instanceof Service) {

					// Get the Service configuration.
					final Service config = (Service) lazyFlag;

					// Create the Service.
					try {
						service = createService(config.getName(), ReflectionL1Helper.getServiceType(config.getClazz()),
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
				if ((parentService == null) && (getDomain() != null)) {
					return getDomain().getService(name);
				} else {
					return parentService;
				}

			} else if (getDomain() != null) {
				return getDomain().getService(name);
			}

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
		if ((active()) && (services != null)) {
			synchronized (services) {
				if (services.containsKey(name)) {

					// Get the Service or the Service's config.
					final Object lazyFlag = services.get(name);

					// Close the Service if required.
					if (lazyFlag instanceof AbstractService) {

						// Get the Service.
						final AbstractService service = (AbstractService) lazyFlag;

						// Shut down the Service.
						service.close();

						// Log message.
						log("WAREWORK removed Service '" + name + "'.", LogServiceConstants.LOG_LEVEL_INFO);

					}

					// Remove the Service from the context.
					services.remove(name);

				}
			}
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
			if (objects != null) {
				synchronized (objects) {
					if (objects.size() > 0) {
						enum1 = DataStructureL1Helper.toEnumeration(objects.keySet());
					}
				}
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
				synchronized (objects) {
					object = (ObjectReference) objects.get(name);
				}
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
			if (providers != null) {
				synchronized (providers) {
					if (providers.containsKey(providerName)) {

						// Get the facade or the Provider's config.
						final Object lazyFlag = providers.get(providerName);

						// Create the Provider if it is not ready.
						if (lazyFlag instanceof Provider) {
							try {

								// Get the Provider's configuration.
								final Provider config = (Provider) lazyFlag;

								// Create the Provider.
								createProvider(providerName, ReflectionL1Helper.getProviderType(config.getClazz()),
										Parameter.toHashMap(config.getParameters()));

								// Get the provider.
								provider = (AbstractProvider) providers.get(providerName);

							} catch (Exception e) {
								// DO NOT PERFORM LOG HERE!!!
								return null;
							}
						} else {
							provider = (AbstractProvider) lazyFlag;
						}

					}
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
	 * Removes an Object Reference that is bound to this Scope.
	 * 
	 * @param name Name of the reference to remove.<br>
	 *             <br>
	 */
	public void removeObject(final String name) {
		if ((active()) && (objects != null)) {
			synchronized (objects) {
				objects.remove(name);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
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
		synchronized (providers) {
			providers.put(provider.getName(), provider);
		}

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

		// Initialize providers when required.
		if (providers == null) {
			providers = new HashMap<String, Object>();
		}

		// Create provider.
		synchronized (providers) {

			// Validate that no Provider already exists with this name.
			if (providers.containsKey(name)) {

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

			// Register the Provider.
			providers.put(name, provider);

		}

		// Log a message.
		log("WAREWORK created Provider '" + name + "'.", LogServiceConstants.LOG_LEVEL_INFO);

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
		synchronized (services) {
			services.put(service.getName(), service);
		}

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

		// Initialize services when required.
		if (services == null) {
			services = new HashMap<String, Object>();
		}

		// Create and configure service.
		synchronized (services) {

			// Validate that no Service already exists with this name.
			if (services.containsKey(name)) {

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

			// Register the Service.
			services.put(name, service);

			// Log a message.
			log("WAREWORK created Service '" + name + "'.", LogServiceConstants.LOG_LEVEL_INFO);

			// Return the Service.
			return service;

		}

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

		// Initialize services when required.
		if (objects == null) {
			objects = new HashMap<String, ObjectReference>();
		}

		// Save object reference.
		synchronized (objects) {

			// Get a record for the object.
			final ObjectReference reference = (objects.containsKey(name)) ? (ObjectReference) objects.get(name)
					: new ObjectReference();

			// Setup the record values.
			reference.setName(name);
			reference.setProvider(providerName);
			reference.setObject(providerObject);

			// Register the object.
			objects.put(name, reference);

		}

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
		if (providers != null) {
			synchronized (providers) {
				if (providers.containsKey(name)) {

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
				Object service = null;
				if (services != null) {
					synchronized (services) {
						service = services.get(LogServiceConstants.DEFAULT_SERVICE_NAME);
					}
				}

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
		if (services != null) {
			synchronized (services) {
				if (services.containsKey(name)) {

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
		}
	}

}
