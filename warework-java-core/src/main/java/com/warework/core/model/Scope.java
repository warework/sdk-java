package com.warework.core.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.loader.LoaderFacade;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.service.ServiceConstants;
import com.warework.core.service.ServiceFacade;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.provider.SingletonProvider;
import com.warework.provider.StandardProvider;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.AbstractLoggerConnector;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * Defines how each Scope have to be initialized. <br>
 * <br>
 * <strong>Context of a Scope:</strong><br>
 * <br>
 * The Context of a Scope is a special area where a Scope holds references to
 * other Scopes. If Scope A adds A1 and A2 Scopes in its Context:
 * <ul>
 * <li>A is the Domain of A1 and A2.</li>
 * <li>A can retrieve A1 and A2 Scopes.</li>
 * <li>A1 and A2 Scopes can retrieve Scope/Domain A.</li>
 * <li>A Services, Providers and Object References are accesible directly from
 * A1 and A2.</li>
 * <li>A1 and A2 Services, Providers and Object References are not accesible
 * directly from Scope A: you must get Scope's A Context first, then a Scope
 * from it (A1 or A2) and finally retrieve the Service, Provider or Object
 * Reference from there.</li>
 * </ul>
 * Example: if Domain 'Shopping Center' has 'Shop 1' and 'Shop 2' in its Context
 * (that is, 'Shop 1' and 'Shop 2' exist in the 'Shopping Center'), 'Shop 1' can
 * access each Service that 'Shopping Center' has but 'Shopping Center' cannot
 * directly see 'Shop 1' Services.<br>
 * <br>
 * <B>Domain of a Scope:</B><br>
 * <br>
 * A Domain is a Scope that holds another Scope in its Context. Services,
 * Providers and Object References that exist at Domain Scope are accesible from
 * this Scope (if you invoke the 'getService' method in this Scope you can get a
 * Service from Domain). <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class Scope {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the Scope.
	private String name;

	// Context for initialization parameters, providers, services, clients,
	// objects and attributes.

	private Map<String, Object> parameters;

	private Map<String, Provider> providers;

	private Map<String, Service> services;

	private Map<String, ObjectReference> objects;

	// Parent Scope.
	private String parent;

	// Context scopes.
	private Map<String, Scope> context;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	public Scope() {
	}

	/**
	 * Configuration to start up a Scope with the Framework.
	 * 
	 * @param name Name for the Scope. This name must be unique in order to create a
	 *             new instance of a Scope, otherwise the factory will return an
	 *             existing instance of a Scope. It cannot be <code>null</code> or
	 *             represent an empty string.<br>
	 *             <br>
	 */
	public Scope(final String name) {
		this.name = name;
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name for this Scope.
	 * 
	 * @return Name for the Scope.<br>
	 *         <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the Scope. This name must be unique in order to create a
	 * new instance of a Scope, otherwise the factory will return an existing
	 * instance of a Scope.
	 * 
	 * @param name Name for the Scope. It cannot be <code>null</code> or represent
	 *             an empty string.<br>
	 *             <br>
	 */
	public void setName(final String name) {
		this.name = name;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Parent Scope name of this Scope.
	 * 
	 * @return Parent Scope name.<br>
	 *         <br>
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Sets the Parent Scope name of this Scope. Services, Providers and Object
	 * References that exist at Parent Scope are accesible from this Scope.
	 * 
	 * @param name Parent Scope name.<br>
	 *             <br>
	 */
	public void setParent(final String name) {
		parent = name;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Scope in the Context of this Scope.
	 * 
	 * @param scope Scope to store in the context.<br>
	 *              <br>
	 */
	public void setContextScope(final Scope scope) {

		// Create the Scope's Context when required.
		if (context == null) {
			context = new HashMap<String, Scope>();
		}

		// Set the parameter.
		context.put(scope.getName(), scope);

	}

	/**
	 * Gets a Scope from the Context of this Scope.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return Context Scope.<br>
	 *         <br>
	 */
	public Scope getContextScope(final String name) {
		if ((context != null) && (context.containsKey(name))) {
			return context.get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of every Scope for the Context of this Scope.
	 * 
	 * @return Context Scope names or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getContextScopeNames() {

		// Return the names of the context scopes.
		if ((context != null) && (context.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(context.keySet());
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes a Scope or multiple Scopes in the Context.
	 * 
	 * @param name Name of the Scope to remove. If <code>null</code> then every
	 *             Scope is removed from the Context.<br>
	 *             <br>
	 */
	public void removeContextScope(final String name) {
		if (context != null) {
			if (name != null) {

				// Remove the context Scope.
				context.remove(name);

				// Remove the context.
				if (context.size() < 1) {
					context = null;
				}

			} else {
				context = null;
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the timeout for this Scope.
	 * 
	 * @param millis Timeout for the Scope in milliseconds. Indicates how long this
	 *               Scope can stay enabled without performing any operation.<br>
	 *               <br>
	 */
	public void setTimeout(final long millis) {
		setInitParameter(ScopeL1Constants.PARAMETER_SCOPE_TIMEOUT, new Long(millis));
	}

	/**
	 * Enables a default log Service for this Scope with a default console logger in
	 * it.
	 */
	public void enableDefaultLog() {
		enableDefaultLog(ConsoleConnector.class, null);
	}

	/**
	 * Enables a default log Service for this Scope with a specific logger in it.
	 * 
	 * @param loggerConnector      Implementation of the Connector that specifies
	 *                             which logger to create and how to create the
	 *                             connections for the logger.<br>
	 *                             <br>
	 * @param connectionParameters Parameters (as string-object values) that
	 *                             specifies how to create the connection with the
	 *                             logger. This is an optional value.<br>
	 *                             <br>
	 */
	public void enableDefaultLog(final Class<? extends AbstractLoggerConnector> loggerConnector,
			final Map<String, Object> connectionParameters) {

		// Create the Service.
		setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);

		// Create the Client.
		setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, ProxyServiceConstants.DEFAULT_CLIENT_NAME, loggerConnector,
				connectionParameters);

	}

	/**
	 * Defines a reference to an object in a Singleton Provider. This method creates
	 * the definition of the Singleton Provider if it does not exists (the
	 * implementation used for this Provider is
	 * <code>com.warework.provider.SingletonProvider</code> and its name is defined
	 * with the constant
	 * <code>com.warework.provider.SingletonProvider.DEFAULT_PROVIDER_NAME</code> )
	 * and once the Provider is specified then the definition of the reference to
	 * the object is created.
	 * 
	 * @param name       Name for the provider-object reference to register. If a
	 *                   reference of the same name is already bound, the reference
	 *                   is replaced. This value cannot be <code>null</code>.<br>
	 *                   <br>
	 * @param objectType Class to create the instance. This class must have a
	 *                   default constructor (without parameters). This value cannot
	 *                   be <code>null</code>.<br>
	 *                   <br>
	 */
	public void setSingletonObject(final String name, final Class<?> objectType) {

		// Create the Provider if required.
		if (getProvider(SingletonProvider.DEFAULT_PROVIDER_NAME) == null) {
			setProvider(SingletonProvider.DEFAULT_PROVIDER_NAME, SingletonProvider.class, null);
		}

		// Register the object in the Scope.
		setObjectReference(name, SingletonProvider.DEFAULT_PROVIDER_NAME, objectType.getName());

	}

	/**
	 * Defines a reference to an object in a Standard Provider. This method creates
	 * the definition of the Standard Provider if it does not exists (the
	 * implementation used for this Provider is
	 * <code>com.warework.provider.StandardProvider</code> and its name is defined
	 * with the constant
	 * <code>com.warework.provider.StandardProvider.DEFAULT_PROVIDER_NAME</code> )
	 * and once the Provider is specified then the definition of the reference to
	 * the object is created.
	 * 
	 * @param name       This name is used for the provider-object reference to
	 *                   register and for the name of the object in the Provider. If
	 *                   a reference of the same name is already bound, the
	 *                   reference is replaced. This value cannot be
	 *                   <code>null</code>.<br>
	 *                   <br>
	 * @param objectType Class to create the instance. This class must have a
	 *                   default constructor (without parameters).<br>
	 *                   <br>
	 */
	public void setStandardObject(final String name, final Class<?> objectType) {

		// Create the Provider if required.
		if (getProvider(StandardProvider.DEFAULT_PROVIDER_NAME) == null) {
			setProvider(StandardProvider.DEFAULT_PROVIDER_NAME, StandardProvider.class, null);
		}

		// Register the new object for the Provider.
		setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, name, objectType);

		// Register the object in the Scope.
		setObjectReference(name, StandardProvider.DEFAULT_PROVIDER_NAME, name);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the value for an initialization parameter.
	 * 
	 * @param name  Name of the initialization parameter.<br>
	 *              <br>
	 * @param value Value of the initialization parameter.<br>
	 *              <br>
	 */
	public void setInitParameter(final String name, final java.lang.Object value) {

		// Create the parameter's context when required.
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}

		// Set the parameter.
		parameters.put(name, value);

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
		return ((parameters != null) && (parameters.containsKey(name))) ? parameters.get(name) : null;
	}

	/**
	 * Gets the boolean value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return <code>true</code> if the parameter is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that is
	 *         equals (in any case form) to <code>true</code>. Otherwise, this
	 *         method returns <code>false</code> (for example: if it does not
	 *         exist).<br>
	 *         <br>
	 */
	public boolean isInitParameter(final String name) {

		// Get the value of the parameter.
		final Boolean value = CommonValueL1Constants.toBoolean(getInitParameter(name));

		// Return the value of the parameter.
		return (value == null) ? false : value;

	}

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if no one
	 *         exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getInitParameterNames() {

		// Return the names of the parameters.
		if ((parameters != null) && (parameters.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(parameters.keySet());
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets a copy of the initialization parameters.
	 * 
	 * @return Names and values of the initialization parameters.<br>
	 *         <br>
	 */
	public Map<String, Object> getInitParameters() {
		return DataStructureL1Helper.copyMap(parameters, new HashMap<String, Object>());
	}

	/**
	 * Removes one or multiple initialization parameter.
	 * 
	 * @param name Name of the initialization parameter to remove. If
	 *             <code>null</code> then every initialization parameter is
	 *             removed.<br>
	 *             <br>
	 */
	public void removeInitParameter(final String name) {
		if (parameters != null) {
			if (name != null) {

				// Remove the parameter.
				parameters.remove(name);

				// Remove the parameters' context.
				if (parameters.size() < 1) {
					parameters = null;
				}

			} else {
				parameters = null;
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Provider in this Scope.
	 * 
	 * @param name       The name to which the Provider will be bound. If a Provider
	 *                   with the same name is already bound in this Scope, an
	 *                   exception will be thrown. This value cannot be
	 *                   <code>null</code>.<br>
	 *                   <br>
	 * @param type       Implementation of the Provider. This value cannot be
	 *                   <code>null</code>.<br>
	 *                   <br>
	 * @param parameters Parameters (as string-object pairs) that configure the
	 *                   Provider. This is an optional value.<br>
	 *                   <br>
	 */
	public void setProvider(final String name, final Class<? extends AbstractProvider> type,
			final Map<String, Object> parameters) {

		// Create the context for providers if required.
		if (providers == null) {
			providers = new HashMap<String, Provider>();
		}

		// Create a new Provider.
		final Provider provider = new Provider();

		// Setup the Provider.
		provider.setScope(this);
		provider.setName(name);
		provider.setClazz(type.getName());
		provider.setParameters(Parameter.toArrayList(parameters));

		// Register the Provider.
		providers.put(name, provider);

	}

	/**
	 * Sets a Provider in this Scope.
	 * 
	 * @param config Provider's configuration.<br>
	 *               <br>
	 */
	public void setProvider(final Provider config) {

		// Create the context for Providers if required.
		if (providers == null) {
			providers = new HashMap<String, Provider>();
		}

		// Setup the Provider.
		config.setScope(this);

		// Register the Provider.
		providers.put(config.getName().toString(), config);

	}

	/**
	 * Gets a Provider from this Scope.
	 * 
	 * @param name The name to which the Provider is bound. This value cannot be
	 *             <code>null</code>.<br>
	 *             <br>
	 * @return A Provider definition or <code>null</code> if no Provider exists.<br>
	 *         <br>
	 */
	public Provider getProvider(final String name) {
		if ((providers != null) && (providers.containsKey(name))) {
			return providers.get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of all the Providers bound to this Scope.
	 * 
	 * @return Names of the Providers or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getProviderNames() {
		if ((providers != null) && (providers.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(providers.keySet());
		} else {
			return null;
		}
	}

	/**
	 * Removes the definition of one or multiple Provider.
	 * 
	 * @param name The name to which the Provider is bound. If <code>null</code>
	 *             then every defined Provider is removed.<br>
	 *             <br>
	 */
	public void removeProvider(final String name) {
		if (providers != null) {
			if (name != null) {

				// Remove the Provider if exists.
				if (providers.containsKey(name)) {
					providers.remove(name);
				}

				// Reset the context if required.
				if ((providers != null) && (providers.size() < 1)) {
					providers = null;
				}

			} else {
				providers = null;
			}
		}
	}

	/**
	 * Adds a parameter in the configuration of a Provider.
	 * 
	 * @param providerName   The name to which the Provider is bound. This value
	 *                       cannot be <code>null</code>.<br>
	 *                       <br>
	 * @param parameterName  Name of the parameter.<br>
	 *                       <br>
	 * @param parameterValue Value of the parameter.<br>
	 *                       <br>
	 */
	public void setProviderParameter(final String providerName, final String parameterName,
			final java.lang.Object parameterValue) {

		// Get the definition of a Provider.
		final Provider provider = getProvider(providerName);

		// Add the parameter only if the Provider exists.
		if (provider != null) {

			// Create the parameter.
			final Parameter parameter = new Parameter();

			// Set the name and the value of the parameter.
			parameter.setName(parameterName);
			parameter.setValue(parameterValue);

			// Get the parameters' list.
			List<Parameter> parameters = provider.getParameters();
			if (parameters == null) {
				parameters = new ArrayList<Parameter>();
			}

			// Add the parameter in the list.
			parameters.add(parameter);

			// Update the parameters' list.
			provider.setParameters(parameters);

		}

	}

	/**
	 * Gets the value of the parameter of a Provider.
	 * 
	 * @param providerName  The name to which the Provider is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter.<br>
	 *                      <br>
	 * @return Value of the parameter.<br>
	 *         <br>
	 */
	public java.lang.Object getProviderParameter(final String providerName, final String parameterName) {

		// Get the definition of a Provider.
		final Provider provider = getProvider(providerName);

		// Add the parameter only if the Provider exists.
		if (provider != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = provider.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return Parameter.toHashMap(parameters).get(parameterName);
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names of all the parameters in the configuration of a Provider.
	 * 
	 * @param providerName The name to which the Provider is bound. This value
	 *                     cannot be <code>null</code>.<br>
	 *                     <br>
	 * @return Name of each parameter of the configuration of a Provider.<br>
	 *         <br>
	 */
	public Enumeration<String> getProviderParameterNames(final String providerName) {

		// Get the definition of a Provider.
		final Provider provider = getProvider(providerName);

		// Add the parameter only if the Provider exists.
		if (provider != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = provider.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return DataStructureL1Helper.toEnumeration(Parameter.toHashMap(parameters).keySet());
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes a parameter in the configuration of a Provider.
	 * 
	 * @param providerName  The name to which the Provider is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter to remove.<br>
	 *                      <br>
	 */
	public void removeProviderParameter(final String providerName, final String parameterName) {

		// Get the definition of a Provider.
		final Provider provider = getProvider(providerName);

		// Add the parameter only if the Provider exists.
		if (provider != null) {

			// Get the parameters' list.
			List<Parameter> parameters = provider.getParameters();

			// Remove the parameter.
			if (parameters != null) {

				// Create a new vector where to copy the parameters that must
				// remain for the Provider.
				final List<Parameter> parametersCopy = new ArrayList<Parameter>();

				// Copy each parameter that must remain for the Provider.
				for (int i = 0; i < parameters.size(); i++) {

					// Get a parameter.
					final Parameter parameter = parameters.get(i);

					// Avoid the parameter to remove.
					if (!parameter.getName().equals(parameterName)) {
						parametersCopy.add(parameter);
					}

				}

				// Update the parameters' list.
				if (parametersCopy.size() > 0) {
					parameters = parametersCopy;
				} else {
					parameters = null;
				}

			}

			// Update the parameters' list.
			provider.setParameters(parameters);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Service in this Scope.
	 * 
	 * @param name        The name to which the Service will be bound. If a Service
	 *                    of the same name is already bound in this Scope, an
	 *                    exception will be thrown. This value cannot be
	 *                    <code>null</code>.<br>
	 *                    <br>
	 * @param serviceType Implementation of the Service. This value cannot be
	 *                    <code>null</code>.<br>
	 *                    <br>
	 * @param parameters  Parameters (as string-object pairs) that configure the
	 *                    Service. This is an optional value.<br>
	 *                    <br>
	 * @param loaderType  Implementation of the loader used to read the
	 *                    configuration of the Service. This is an optional
	 *                    value.<br>
	 *                    <br>
	 */
	public void setService(final String name, final Class<? extends ServiceFacade> serviceType,
			final Map<String, Object> parameters, final Class<? extends LoaderFacade> loaderType) {

		// Create the context for services if required.
		if (services == null) {
			services = new HashMap<String, Service>();
		}

		// Intialize the parameters collection if necessary.
		Map<String, Object> initParams = null;
		if (parameters == null) {
			initParams = new HashMap<String, Object>();
		} else {
			initParams = parameters;
		}

		// Create a new Service.
		final Service service = new Service();

		// IMPORTANT!: As this Service object represents the configuration for
		// the Service, we have to register itself as the configuration for the
		// Service.
		// DO NOT COMMENT/REMOVE THIS CODE!!!: This is required in order to
		// configure the Framework with Java Beans. Check out JDBC Data Store
		// test suite, there Scopes are created and configured with Java Beans.
		// If you remove this code those tests will not work.
		if (!initParams.containsKey(ServiceConstants.PARAMETER_CONFIG_TARGET)) {
			initParams.put(ServiceConstants.PARAMETER_CONFIG_TARGET, service);
		}

		// Setup the Service.
		service.setScope(this);
		service.setName(name);
		service.setClazz(serviceType.getName());
		service.setParameters(Parameter.toArrayList(initParams));

		// Register the Service.
		services.put(name, service);

		// Set the configuration loader for the Service.
		if (loaderType != null) {
			setServiceConfigClass(name, loaderType);
		}

	}

	/**
	 * Sets a Service in this Scope.
	 * 
	 * @param config Service's configuration.<br>
	 *               <br>
	 */
	public void setService(final Service config) {

		// Create the context for services if required.
		if (services == null) {
			services = new HashMap<String, Service>();
		}

		// Setup the Service.
		config.setScope(this);

		// IMPORTANT!: As this Service object represents the configuration for
		// the Service, we have to register itself as the configuration for the
		// Service.
		// DO NOT COMMENT/REMOVE THIS CODE!!!: This is required in order to
		// configure the Framework with Java Beans. Check out JDBC Data Store
		// test suite, there Scopes are created and configured with Java Beans.
		// If you remove this code those tests will not work.
		if (config.getParameter(ServiceConstants.PARAMETER_CONFIG_TARGET) == null) {
			config.setParameter(ServiceConstants.PARAMETER_CONFIG_TARGET, config);
		}

		// Register the Service.
		services.put(config.getName().toString(), config);

	}

	/**
	 * Gets a Service from this Scope.
	 * 
	 * @param name The name to which the Service is bound. This value cannot be
	 *             <code>null</code>.<br>
	 *             <br>
	 * @return A Service definition or <code>null</code> if no Service exists.<br>
	 *         <br>
	 */
	public Service getService(final String name) {
		if ((services != null) && (services.containsKey(name))) {
			return services.get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of all the services bound to this Scope.
	 * 
	 * @return Names of the services or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getServiceNames() {
		if ((services != null) && (services.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(services.keySet());
		} else {
			return null;
		}
	}

	/**
	 * Removes a Service in this Scope.
	 * 
	 * @param name The name to which the Service is bound. If <code>null</code> then
	 *             every defined Service is removed.<br>
	 *             <br>
	 */
	public void removeService(final String name) {
		if (services != null) {
			if (name != null) {

				// Remove the Service if exists.
				if (services.containsKey(name)) {
					services.remove(name);
				}

				// Reset the context if required.
				if ((services != null) && (services.size() < 1)) {
					services = null;
				}
			} else {
				services = null;
			}
		}
	}

	/**
	 * Adds a parameter in the configuration of a Service.
	 * 
	 * @param serviceName    The name to which the Service is bound. This value
	 *                       cannot be <code>null</code>.<br>
	 *                       <br>
	 * @param parameterName  Name of the parameter. This value cannot be
	 *                       <code>null</code>.<br>
	 *                       <br>
	 * @param parameterValue Value of the parameter. This value cannot be
	 *                       <code>null</code> .<br>
	 *                       <br>
	 */
	public void setServiceParameter(final String serviceName, final String parameterName,
			final java.lang.Object parameterValue) {

		// Get the definition of a Service.
		final Service service = getService(serviceName);

		// Add the parameter only if the Service exists.
		if (service != null) {

			// Create the parameter.
			final Parameter parameter = new Parameter();

			// Set the name and the value of the parameter.
			parameter.setName(parameterName);
			parameter.setValue(parameterValue);

			// Get the parameters' list.
			List<Parameter> parameters = service.getParameters();
			if (parameters == null) {
				parameters = new ArrayList<Parameter>();
			}

			// Add the parameter in the list.
			parameters.add(parameter);

			// Update the parameters' list.
			service.setParameters(parameters);

		}

	}

	/**
	 * Gets the value of the parameter of a Service.
	 * 
	 * @param serviceName   The name to which the Service is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter. This value cannot be
	 *                      <code>null</code>.<br>
	 *                      <br>
	 * @return Value of the parameter.<br>
	 *         <br>
	 */
	public java.lang.Object getServiceParameter(final String serviceName, final String parameterName) {

		// Get the definition of a Service.
		final Service service = getService(serviceName);

		// Add the parameter only if the Service exists.
		if (service != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = service.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return Parameter.toHashMap(parameters).get(parameterName);
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names of all the parameters of the configuration of a Service.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @return Name of each parameter of the configuration of a Service.<br>
	 *         <br>
	 */
	public Enumeration<String> getServiceParameterNames(final String serviceName) {

		// Get the definition of a Service.
		final Service service = getService(serviceName);

		// Add the parameter only if the Service exists.
		if (service != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = service.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return DataStructureL1Helper.toEnumeration(Parameter.toHashMap(parameters).keySet());
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes a parameter from the configuration of a Service.
	 * 
	 * @param serviceName   The name to which the Service is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter to remove. This value cannot be
	 *                      <code>null</code>.<br>
	 *                      <br>
	 */
	public void removeServiceParameter(final String serviceName, final String parameterName) {

		// Get the definition of a Service.
		final Service service = getService(serviceName);

		// Add the parameter only if the Service exists.
		if (service != null) {

			// Get the parameters' list.
			List<Parameter> parameters = service.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if (parameters != null) {

				// Create a new vector where to copy the parameters that must
				// remain for the Service.
				final List<Parameter> parametersCopy = new ArrayList<Parameter>();

				// Copy each parameter that must remain for the Service.
				for (int i = 0; i < parameters.size(); i++) {

					// Get a parameter.
					final Parameter parameter = parameters.get(i);

					// Avoid the parameter to remove.
					if (!parameter.getName().equals(parameterName)) {
						parametersCopy.add(parameter);
					}

				}

				// Update the parameters' list.
				if (parametersCopy.size() > 0) {
					parameters = parametersCopy;
				} else {
					parameters = null;
				}

			}

			// Update the parameters' list.
			service.setParameters(parameters);

		}

	}

	/**
	 * Sets the configuration object for a Service.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @param config      Object that represents the Service's configuration. This
	 *                    value cannot be <code>null</code>.<br>
	 *                    <br>
	 */
	public void setServiceConfigTarget(final String serviceName, final java.lang.Object config) {
		setServiceParameter(serviceName, ServiceConstants.PARAMETER_CONFIG_TARGET, config);
	}

	/**
	 * Sets the configuration loader class for a Service.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @param loaderType  Loader implementation class used to retrieve the
	 *                    configuration of the Service This value cannot be
	 *                    <code>null</code>.<br>
	 *                    <br>
	 */
	public void setServiceConfigClass(final String serviceName, final Class<? extends LoaderFacade> loaderType) {
		setServiceParameter(serviceName, ServiceConstants.PARAMETER_CONFIG_CLASS, loaderType);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Client in a Proxy Service.
	 * 
	 * @param serviceName          The name to which the Service is bound. This
	 *                             value cannot be <code>null</code>.
	 *                             <strong>WARNING</strong>: If Service is
	 *                             registered as a simple Service, it's upgraded to
	 *                             a Proxy Service automatically.<br>
	 *                             <br>
	 * @param clientName           The name to which the Client will be bound. If a
	 *                             Client with the same name is already bound to the
	 *                             Service then client will be overriden. This value
	 *                             cannot be <code>null</code>.<br>
	 *                             <br>
	 * @param connectorType        Implementation of the Connector that specifies
	 *                             which Client to create and how to create the
	 *                             connections for the Client. This value cannot be
	 *                             <code>null</code>.<br>
	 *                             <br>
	 * @param connectionParameters Parameters (as string-object pairs) that
	 *                             specifies how the Connector will create the
	 *                             connections for the Client. This is an optional
	 *                             value.<br>
	 *                             <br>
	 */
	public void setClient(final String serviceName, final String clientName,
			Class<? extends ConnectorFacade> connectorType, final Map<String, Object> connectionParameters) {

		// Get the Service.
		final Service service = getService(serviceName);

		// Validate that Service exists.
		if (service != null) {

			// Get the Proxy Service.
			ProxyService proxyService = null;
			if (service instanceof ProxyService) {
				proxyService = (ProxyService) service;
			} else {

				// Create a new Proxy Service if it's a simple Service.
				proxyService = new ProxyService();

				// Setup the Service.
				proxyService.setScope(this);
				proxyService.setClazz(service.getClazz());
				proxyService.setName(serviceName);
				proxyService.setParameters(service.getParameters());

				// Remove old Service configuration.
				// DO NOT COMMENT/REMOVE THIS CODE!!!: This is required in order
				// to configure the Framework with Java Beans. Check out JDBC
				// Data Store test suite, there Scopes are created and
				// configured with Java Beans. If you remove this code those
				// tests will not work.
				if (proxyService.getParameter(ServiceConstants.PARAMETER_CONFIG_TARGET) == service) {
					proxyService.removeParameter(ServiceConstants.PARAMETER_CONFIG_TARGET);
				}

				// Remove existing simple Service.
				removeService(serviceName);

				// Register the Proxy Service.
				setService(proxyService);

			}

			// Create a new Client.
			final Client client = new Client();

			// Setup the Client.
			client.setService(proxyService);
			client.setName(clientName);
			client.setConnector(connectorType.getName());
			client.setParameters(Parameter.toArrayList(connectionParameters));

			// Register the Client.
			proxyService.setClient(client);

		}

	}

	/**
	 * Gets a Client from a Proxy Service.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @param clientName  The name to which the Client is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @return A Client configuration or <code>null</code> if no Client exists.<br>
	 *         <br>
	 */
	public Client getClient(final String serviceName, final String clientName) {

		// Get the Service.
		final Service service = getService(serviceName);

		// Validate that Service exists.
		if ((service != null) && (service instanceof ProxyService)) {

			// Get the Proxy Service.
			ProxyService proxyService = (ProxyService) service;

			// Return the Client.
			return proxyService.getClient(clientName);

		} else {
			return null;
		}

	}

	/**
	 * Gets the names of all the clients bound to a Proxy Service.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @return Names of the clients or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getClientNames(final String serviceName) {

		// Get the Service.
		final Service service = getService(serviceName);

		// Validate that Service exists.
		if ((service != null) && (service instanceof ProxyService)) {

			// Get the Proxy Service.
			final ProxyService proxyService = (ProxyService) service;

			// Return the name of the clients.
			return proxyService.getClientNames();

		} else {
			return null;
		}

	}

	/**
	 * Removes the definition of a Service.
	 * 
	 * @param serviceName The name to which the Service is bound. If this value is
	 *                    <code>null</code> then this method does not filters by the
	 *                    name of the Service and searchs for every Client to be
	 *                    removed.<br>
	 *                    <br>
	 * @param clientName  The name to which the Client is bound. If this value is
	 *                    <code>null</code> then this method remove every
	 *                    Client.<br>
	 *                    <br>
	 */
	public void removeClient(final String serviceName, final String clientName) {

		// Get the Service.
		final Service service = getService(serviceName);

		// Validate that Service exists.
		if ((service != null) && (service instanceof ProxyService)) {

			// Get the Proxy Service.
			final ProxyService proxyService = (ProxyService) service;

			// Remove the Client
			proxyService.removeClient(clientName);

		}

	}

	/**
	 * Adds a parameter in the configuration of a Client.
	 * 
	 * @param serviceName    The name to which the Service is bound. This value
	 *                       cannot be <code>null</code>.<br>
	 *                       <br>
	 * @param clientName     The name to which the Client is bound. This value
	 *                       cannot be <code>null</code>.<br>
	 *                       <br>
	 * @param parameterName  Name of the parameter. This value cannot be
	 *                       <code>null</code>.<br>
	 *                       <br>
	 * @param parameterValue Value of the parameter. This value cannot be
	 *                       <code>null</code> .<br>
	 *                       <br>
	 */
	public void setClientParameter(final String serviceName, final String clientName, final String parameterName,
			final java.lang.Object parameterValue) {

		// Get the definition of a Client.
		final Client client = getClient(serviceName, clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Create the parameter.
			final Parameter parameter = new Parameter();

			// Set the name and the value of the parameter.
			parameter.setName(parameterName);
			parameter.setValue(parameterValue);

			// Get the parameters' list.
			List<Parameter> parameters = client.getParameters();
			if (parameters == null) {
				parameters = new ArrayList<Parameter>();
			}

			// Add the parameter in the list.
			parameters.add(parameter);

			// Update the parameters' list.
			client.setParameters(parameters);

		}

	}

	/**
	 * Gets a parameter from the configuration of a Client.
	 * 
	 * @param serviceName   The name to which the Service is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param clientName    The name to which the Client is bound. This value cannot
	 *                      be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter. This value cannot be
	 *                      <code>null</code>.
	 * @return Value of the parameter.<br>
	 *         <br>
	 */
	public java.lang.Object getClientParameter(final String serviceName, final String clientName,
			final String parameterName) {

		// Get the definition of a Client.
		final Client client = getClient(serviceName, clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = client.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return Parameter.toHashMap(parameters).get(parameterName);
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names of all the parameters of the configuration of a Client.
	 * 
	 * @param serviceName The name to which the Service is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @param clientName  The name to which the Client is bound. This value cannot
	 *                    be <code>null</code>.<br>
	 *                    <br>
	 * @return Name of each parameter of the configuration of a Client.<br>
	 *         <br>
	 */
	public Enumeration<String> getClientParameterNames(final String serviceName, final String clientName) {

		// Get the definition of a Client.
		final Client client = getClient(serviceName, clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			final List<Parameter> parameters = client.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {
				return DataStructureL1Helper.toEnumeration(Parameter.toHashMap(parameters).keySet());
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes a parameter from the configuration of a Client.
	 * 
	 * @param serviceName   The name to which the Service is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param clientName    The name to which the Client is bound. This value cannot
	 *                      be <code>null</code>.<br>
	 *                      <br>
	 * @param parameterName Name of the parameter to remove. This value cannot be
	 *                      <code>null</code>.<br>
	 *                      <br>
	 */
	public void removeClientParameter(final String serviceName, final String clientName, final String parameterName) {

		// Get the definition of a Client.
		final Client client = getClient(serviceName, clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			List<Parameter> parameters = client.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if (parameters != null) {

				// Create a new vector where to copy the parameters that must
				// remain for the Service.
				final List<Parameter> parametersCopy = new ArrayList<Parameter>();

				// Copy each parameter that must remain for the Service.
				for (int i = 0; i < parameters.size(); i++) {

					// Get a parameter.
					final Parameter parameter = (Parameter) parameters.get(i);

					// Avoid the parameter to remove.
					if (!parameter.getName().equals(parameterName)) {
						parametersCopy.add(parameter);
					}

				}

				// Update the parameters' list.
				if (parametersCopy.size() > 0) {
					parameters = parametersCopy;
				} else {
					parameters = null;
				}

			}

			// Update the parameters' list.
			client.setParameters(parameters);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a reference to an object in this Scope.
	 * 
	 * @param name     Name of the provider-object reference to register. If a
	 *                 reference of the same name is already bound to this Scope,
	 *                 the reference is replaced. This value cannot be
	 *                 <code>null</code>.<br>
	 *                 <br>
	 * @param provider Name of the Provider that exists in the Scope.<br>
	 *                 <br>
	 * @param object   Name of the object in the Provider. If this parameter is
	 *                 <code>null</code> then an exception will be thrown on Scope
	 *                 start up.<br>
	 *                 <br>
	 */
	public void setObjectReference(final String name, final String provider, final String object) {

		// Create the context for object references if required.
		if (objects == null) {
			objects = new HashMap<String, ObjectReference>();
		}

		// Create a new object.
		final ObjectReference objectRecord = new ObjectReference();

		// Setup the object.
		objectRecord.setScope(this);
		objectRecord.setName(name);
		objectRecord.setProvider(provider);
		objectRecord.setObject(object);

		// Register the object reference.
		objects.put(name, objectRecord);

	}

	/**
	 * Gets a reference to an object from this Scope.
	 * 
	 * @param name Name of the provider-object reference to register. This value
	 *             cannot be <code>null</code>.<br>
	 *             <br>
	 * @return A provider-object reference or <code>null</code> if no one
	 *         exists.<br>
	 *         <br>
	 */
	public ObjectReference getObjectReference(final String name) {
		if ((objects != null) && (objects.containsKey(name))) {
			return (ObjectReference) objects.get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of every provider-object references that exist in this Scope.
	 * 
	 * @return Provider-object references that exist under the given name or
	 *         <code>null</code> if no one exists. Each reference will be in a
	 *         specific Scope.<br>
	 *         <br>
	 */
	public Enumeration<String> getObjectReferenceNames() {
		if ((objects != null) && (objects.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(objects.keySet());
		} else {
			return null;
		}
	}

	/**
	 * Removes a provider-object reference.
	 * 
	 * @param name The name to which the provider-object reference is bound. If
	 *             <code>null</code> then every defined provider-object reference is
	 *             removed.<br>
	 *             <br>
	 */
	public void removeObjectReference(final String name) {
		if (objects != null) {
			if (name != null) {

				// Remove the object-reference if exists.
				if (objects.containsKey(name)) {
					objects.remove(name);
				}

				// Reset the context if required.
				if ((objects != null) && (objects.size() < 1)) {
					objects = null;
				}

			} else {
				objects = null;
			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Updates this configuration with a given Scope configuration. Only not-null
	 * values are overriden.
	 * 
	 * @param scope Configuration to apply in this Scope (overrides Providers,
	 *              Services, ... that exist in this Scope).<br>
	 *              <br>
	 */
	public void update(final Scope scope) {

		// Update the name of the Scope.
		if (scope.getName() != null) {
			name = scope.getName();
		}

		// Copy initialization parameters.
		copyInitParams(scope);

		// Copy Provider.
		copyProviders(scope);

		// Copy Service.
		copyServices(scope);

		// Copy object reference.
		copyObjectReferences(scope);

		// Set parent Scope name.
		if (scope.getParent() != null) {
			parent = scope.getParent();
		}

		// Copy context scopes.
		copyContextScopes(scope);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Scope.
	 * 
	 * @return Name of the Scope.<br>
	 *         <br>
	 */
	public String toString() {
		if (getName() == null) {
			return CommonValueL1Constants.STRING_EMPTY;
		} else {
			return getName();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copy initialization parameters from a given Scope into this Scope.
	 * 
	 * @param scope Scope where to retrieve the initialization parameters.<br>
	 *              <br>
	 */
	private void copyInitParams(final Scope scope) {

		// Get the name of every initialization parameter.
		final Enumeration<String> initParamNames = scope.getInitParameterNames();

		// Copy initialization parameters.
		if (initParamNames != null) {
			while (initParamNames.hasMoreElements()) {

				// Get the name of the initialization parameter.
				final String initParamName = initParamNames.nextElement();

				// Set the initialization parameter in this Scope.
				setInitParameter(initParamName, scope.getInitParameter(initParamName));

			}
		}

	}

	/**
	 * Copy the Providers from a given Scope into this Scope.
	 * 
	 * @param scope Scope where to retrieve the Providers.<br>
	 *              <br>
	 */
	private void copyProviders(final Scope scope) {

		// Get the name of every Provider.
		final Enumeration<String> providerNames = scope.getProviderNames();

		// Copy Provider.
		if (providerNames != null) {
			while (providerNames.hasMoreElements()) {

				// Get the name of the Provider.
				final String providerName = providerNames.nextElement();

				// Get the Provider config.
				final Provider provider = scope.getProvider(providerName);

				// Update the Scope for the Provider.
				provider.setScope(this);

				// Set the Provider in this Scope.
				setProvider(provider);

			}
		}

	}

	/**
	 * Copy the Services from a given Scope into this Scope.
	 * 
	 * @param scope Scope where to retrieve the Services.<br>
	 *              <br>
	 */
	private void copyServices(final Scope scope) {

		// Get the name of every Service.
		final Enumeration<String> serviceNames = scope.getServiceNames();

		// Copy Service.
		if (serviceNames != null) {
			while (serviceNames.hasMoreElements()) {

				// Get the name of the Service.
				final String serviceName = serviceNames.nextElement();

				// Get the Service config.
				final Service service = scope.getService(serviceName);

				// Update the Scope for the Service.
				service.setScope(this);

				// Set the Service in this Scope.
				setService(service);

			}
		}

	}

	/**
	 * Copy Object References from a given Scope into this Scope.
	 * 
	 * @param scope Scope where to retrieve the Object References.<br>
	 *              <br>
	 */
	private void copyObjectReferences(final Scope scope) {

		// Get the name of every object reference.
		final Enumeration<String> objectReferenceNames = scope.getObjectReferenceNames();

		// Copy object reference.
		if (objectReferenceNames != null) {
			while (objectReferenceNames.hasMoreElements()) {

				// Get the name of the object reference.
				final String objectReferenceName = objectReferenceNames.nextElement();

				// Get the object reference config.
				final ObjectReference objectReference = scope.getObjectReference(objectReferenceName);

				// Set the object reference in this Scope.
				setObjectReference(objectReference.getName(), objectReference.getProvider(),
						objectReference.getObject());

			}
		}

	}

	/**
	 * Copy every context Scope from a given Scope into this Scope.
	 * 
	 * @param scope Scope where to retrieve every context Scope.<br>
	 *              <br>
	 */
	private void copyContextScopes(final Scope scope) {

		// Get the name of every context Scope.
		final Enumeration<String> contextScopeNames = scope.getContextScopeNames();

		// Copy context scopes.
		if (contextScopeNames != null) {
			while (contextScopeNames.hasMoreElements()) {
				setContextScope(scope.getContextScope(contextScopeNames.nextElement()));
			}
		}

	}

}
