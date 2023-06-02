package com.warework.core.scope;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.model.Scope;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.log.LogServiceConstants;

import junit.framework.TestCase;

/**
 * Common context utilities for test cases.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractTestCase extends TestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////
	
	// Scope properties.
	
	protected static final String SCOPE_NAME = "test";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default implementation for the context of the Scopes.<br>
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	protected static abstract class AbstractContext implements Context {

		// ///////////////////////////////////////////////////////////////
		// CONSTANTS
		// ///////////////////////////////////////////////////////////////

		// Global storage for Scopes to control ny name unique Scope instances. Multiple
		// Scopes with the same name are not allowed.
		private static final Map<String, ScopeFacade> GLOBAL_CONTEXT_BY_NAME = new HashMap<String, ScopeFacade>();

		// Global storage for Scopes to control ny sequence unique Scope instances.
		// Multiple Scopes with the same name are not allowed.
		private static final List<ScopeFacade> GLOBAL_CONTEXT_BY_SEQUENCE = new ArrayList<ScopeFacade>();

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Domain Scope for every Scope created in this context.
		private ScopeFacade domain;

		// Storage for scopes.
		private Map<String, AbstractScope> context = new HashMap<String, AbstractScope>();

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the context.
		 * 
		 * @param domain Domain Scope for every Scope created in this context.<br>
		 *               <br>
		 */
		protected AbstractContext(final ScopeFacade domain) {
			this.domain = domain;
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates a Scope for this Domain/Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @return A new instance of a Scope. If a Scope of the same name is already
		 *         bound to the context, the existing Scope is returned.<br>
		 *         <br>
		 * @throws ScopeException If there is an error when trying to create the
		 *                        Scope.<br>
		 *                        <br>
		 */
		public ScopeFacade create(final Scope config) throws ScopeException {

			// Validate the name of the Scope.
			if ((config.getName() == null) || (config.getName().equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ScopeException(domain,
						"WAREWORK cannot create a new instance of the Scope because Scope's name is null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Updated configuration of the Scope.
			Scope updatedConfig = null;

			// New instance of the Scope.
			AbstractScope scope = null;

			// Create Scope and store in context.
			synchronized (GLOBAL_CONTEXT_BY_NAME) {
				if (GLOBAL_CONTEXT_BY_NAME.containsKey(config.getName())) {
					throw new ScopeException(domain,
							"WAREWORK cannot create a new instance of the Scope because Scope '" + config.getName()
									+ "' already exists.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				} else {

					// Get the full configuration for the Scope.
					updatedConfig = updateConfig(config);

					// Create Scope factory event handler instance.
					ScopeFactoryEventHandler handler = null;
					try {

						// Get the Scope factory event handler class when defined.
						final Class<? extends ScopeFactoryEventHandler> handlerType = getParameterType(updatedConfig,
								ScopeL1Constants.PARAMETER_FACTORY_EVENT_CLASS, ScopeFactoryEventHandler.class);

						// Create handler instance.
						if (handlerType != null) {
							handler = handlerType.newInstance();
						}

					} catch (final Exception e) {
						throw new ScopeException(domain,
								"WAREWORK cannot create a new instance of the Scope because given Scope factory event handler class cannot be instantiated.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Handle config loaded event.
					if (handler != null) {
						handler.configLoaded(updatedConfig);
					}

					// Get parent Scope if it exists.
					ScopeFacade parent = null;
					if (updatedConfig.getParent() != null) {
						if (GLOBAL_CONTEXT_BY_NAME.containsKey(updatedConfig.getParent())) {
							parent = GLOBAL_CONTEXT_BY_NAME.get(updatedConfig.getParent());
						} else {
							throw new ScopeException(domain,
									"WAREWORK cannot create a new instance of the Scope because specified parent '"
											+ updatedConfig.getParent() + "' does not exists.",
									null, LogServiceConstants.LOG_LEVEL_WARN);
						}
					}

					// Create an instance of the Scope.
					scope = AbstractTestCase.create(updatedConfig, parent, domain);

					// Handle Scope created event.
					if (handler != null) {
						handler.scopeCreated(updatedConfig, scope);
					}

					// Add Scope in this context.
					synchronized (context) {
						context.put(scope.getName(), scope);
					}

					// Add Scope in global context by name.
					GLOBAL_CONTEXT_BY_NAME.put(scope.getName(), scope);

					// Add Scope in global context by sequence.
					GLOBAL_CONTEXT_BY_SEQUENCE.add(scope);

				}

			}

			// Create context Scopes. Must be created only after main Scope exists.
			setupContext(scope, updatedConfig);

			// Set the Scope.
			return scope;

		}

		/**
		 * Decides if a Scope exists in the context.
		 * 
		 * @param name Name of the Scope.<br>
		 *             <br>
		 * @return <code>true</code> if the Scope exists or false if not.<br>
		 *         <br>
		 */
		public boolean exists(final String name) {
			synchronized (context) {
				return context.containsKey(name);
			}
		}

		/**
		 * Gets an existing Scope.
		 * 
		 * @param name Name of the Scope.<br>
		 *             <br>
		 * @return A previously created Scope.<br>
		 *         <br>
		 */
		public ScopeFacade get(final String name) {

			// Validate that Scope exists.
			synchronized (context) {
				if (context.containsKey(name)) {

					// Get the Scope.
					final ScopeFacade scope = context.get(name);

					// When Scope's name is null then Scope is over.
					if ((scope != null) && (scope.active())) {
						return scope;
					}

				}
			}

			// At this point, nothing to return.
			return null;

		}

		/**
		 * Retrieves the name of every Scope that exists in the context.
		 * 
		 * @return Scopes' names. If no Scope exists in the context this method returns
		 *         <code>null</code>.<br>
		 *         <br>
		 */
		public Enumeration<String> list() {

			// Validate context and return scope names.
			synchronized (context) {
				if (context.size() > 0) {
					return DataStructureL1Helper.toEnumeration(context.keySet());
				}
			}

			// At this point, nothing to return.
			return null;

		}

		/**
		 * Retrieves the name of every Scope that exists in the context of a specific
		 * Domain/Scope.
		 * 
		 * @param name Name of the Scope where the context exists. This Scope is the
		 *             Domain where to search for the its scopes.<br>
		 *             <br>
		 * @return Scopes' names. If no Scope exists in the context of the Scope, this
		 *         method returns <code>null</code>.<br>
		 *         <br>
		 */
		public Enumeration<String> list(final String name) {

			// Get the Scope.
			final ScopeFacade scope = get(name);

			// When Scope's name is null then Scope is over.
			if ((scope != null) && (scope.getContext() != null)) {
				return scope.getContext().list();
			}

			// At this point, nothing to return.
			return null;

		}

		/**
		 * Removes an existing Scope in the context.
		 * 
		 * @param name Name of the Scope of remove.<br>
		 *             <br>
		 * @return <code>true</code> if Scope was removed and <code>false</code>
		 *         otherwise.<br>
		 *         <br>
		 * @throws ScopeException If there is an error when trying to remove the
		 *                        Scope.<br>
		 *                        <br>
		 */
		public boolean remove(final String name) throws ScopeException {
			synchronized (GLOBAL_CONTEXT_BY_NAME) {
				return shutdown(name);
			}
		}

		// ///////////////////////////////////////////////////////////////
		// PRIVATE METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the full configuration for the Scope. One Scope 'A' can request its
		 * configuration from Scope 'B', 'B' from 'C' and so on. It loads recursively
		 * all the configurations to create a single configuration object.
		 * 
		 * @param config1 Root configuration object. The information that exists in this
		 *                configuration will override the information loaded in other
		 *                configurations.<br>
		 *                <br>
		 * @return Scope configuration.<br>
		 *         <br>
		 * @throws ScopeException If there is an error when trying to update the
		 *                        configuration of the Scope.<br>
		 *                        <br>
		 */
		private Scope updateConfig(final Scope config1) throws ScopeException {

			// Get the loader type of the configuration.
			Class<? extends LoaderFacade> loaderType = null;
			try {
				loaderType = getParameterType(config1, ScopeL1Constants.PARAMETER_CONFIG_CLASS, LoaderFacade.class);
			} catch (final ClassNotFoundException e) {
				throw new ScopeException(domain,
						"WAREWORK cannot create a new instance of the Scope because the loader type for a configuration of a Scope is not valid. Check out this error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate if Scope's configuration references to another Scope's
			// configuration.
			if (loaderType != null) {

				// Load Scope's configuration.
				Scope config2 = null;
				try {
					config2 = (Scope) AbstractLoader.load(null, loaderType, config1.getInitParameters());
				} catch (final Exception e) {
					throw new ScopeException(domain,
							"WAREWORK cannot create a new instance of the Scope because the following exception is thrown when trying to load the configuration of the Scope: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Context loader propagation.
				if ((config2 != null) && (config2.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) == null)
						&& (config1.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) != null)) {
					config2.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER,
							config1.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER));
				}

				// Check if new configuration references to another configurations.
				config2 = updateConfig(config2);

				// Override the configuration.
				config2.update(config1);

				// Return the updated configuration.
				return config2;

			}

			// Return the configuration as it is.
			return config1;

		}

		/**
		 * Gets the class than implements an interface defined by an initialization
		 * parameter.
		 * 
		 * @param <T>           Interface defined by the initialization parameter class.
		 * @param config        Scope's configuration.<br>
		 *                      <br>
		 * @param parameterName Name of the initialization parameter that holds the
		 *                      specific class type that implements the interface.<br>
		 *                      <br>
		 * @param type          Interface implemented by the class defined in the
		 *                      initialization parameter.<br>
		 *                      <br>
		 * @return Class that extends the interface defined by the initialization
		 *         parameter.<br>
		 *         <br>
		 * @throws ClassNotFoundException
		 */
		@SuppressWarnings("unchecked")
		private <T> Class<? extends T> getParameterType(final Scope config, final String parameterName,
				final Class<T> type) throws ClassNotFoundException {

			// Get the type of the loader.
			final Object parameterValue = config.getInitParameter(parameterName);

			// Get the class of the loader.
			Class<? extends T> parameterType = null;
			if (parameterValue != null) {
				if (parameterValue instanceof String) {
					parameterType = (Class<? extends T>) Class.forName((String) parameterValue);
				} else if (parameterValue instanceof Class) {
					parameterType = (Class<? extends T>) parameterValue;
				}
			}

			// Return the type of the loader.
			return parameterType;

		}

		/**
		 * Initializes context Scopes.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is an error when trying to initialize the
		 *                        Context Scopes.<br>
		 *                        <br>
		 */
		private void setupContext(final ScopeFacade scope, final Scope config) throws ScopeException {

			// Get context Scope names.
			final Enumeration<String> contextScopeNames = config.getContextScopeNames();

			// Validate Scopes exists.
			if (contextScopeNames != null) {

				// Get context.
				final Context context = scope.getContext();

				// Validate context exists.
				if (context != null) {
					while (contextScopeNames.hasMoreElements()) {

						// Get the configuration for the context Scope.
						final Scope contextScopeConfig = config.getContextScope(contextScopeNames.nextElement());

						// Context loader propagation.
						if ((contextScopeConfig.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) == null)
								&& (config.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) != null)) {
							contextScopeConfig.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER,
									config.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER));
						}

						// Create the Scope in the context.
						context.create(contextScopeConfig);

					}
				}

			}

		}

		/**
		 * Removes an existing Scope in the context.
		 * 
		 * @param name Name of the Scope of remove.<br>
		 *             <br>
		 * @return <code>true</code> if Scope was removed and <code>false</code>
		 *         otherwise.<br>
		 *         <br>
		 * @throws ScopeException If there is an error when trying to remove the
		 *                        Scope.<br>
		 *                        <br>
		 */
		private boolean shutdown(final String name) throws ScopeException {

			// Validate Scope exists in global context.
			if (GLOBAL_CONTEXT_BY_NAME.containsKey(name)) {
				synchronized (context) {
					if (context.containsKey(name)) {

						// Get the Scope.
						final AbstractScope scope = context.get(name);

						// Validate Scope does not have Scopes in its context. Keep in mind that those
						// Scopes must be deleted first because if you delete the domain Scope you will
						// not be able to get the instances of the Scopes of its context later on.
						if ((scope.getContext() != null) && (scope.getContext().list() != null)) {
							throw new ScopeException(scope, "WAREWORK cannot remove Scope '" + name
									+ "' from context because it contains Scopes in its context that should be deleted first.",
									null, LogServiceConstants.LOG_LEVEL_WARN);
						}

						// Validate Scope has no children (they all must be deleted first).
						for (final Iterator<ScopeFacade> iterator = GLOBAL_CONTEXT_BY_SEQUENCE.iterator(); iterator
								.hasNext();) {

							// Get parent from available Scope.
							final ScopeFacade child = iterator.next();

							// Check that available Scope is not child.
							if ((child.getParent() != null) && (child.getParent().getName().equals(name))) {
								throw new ScopeException(scope,
										"WAREWORK cannot remove Scope '" + name
												+ "' from context because it has active child '" + child
												+ "' that should be deleted first.",
										null, LogServiceConstants.LOG_LEVEL_WARN);
							}

						}

						// Shutdown Scope if it's running.
						scope.close();

						// Remove Scope from the context.
						context.remove(name);

						// Remove by name Scope from the global context.
						GLOBAL_CONTEXT_BY_NAME.remove(name);

						// Remove by index Scope from the global context.
						GLOBAL_CONTEXT_BY_SEQUENCE.remove(scope);

						// Scope was removed.
						return true;

					}
				}
			}

			// At this point, Scope could not be removed.
			return false;

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Context.
	private AbstractContext context;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes every Scope from all contexts.
	 */
	public void tearDown() {
		try {
			synchronized (AbstractContext.GLOBAL_CONTEXT_BY_NAME) {
				while (AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE.size() > 0) {

					// Get last created Scope.
					final ScopeFacade scope = AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE
							.get(AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE.size() - 1);

					// Remove Scope from context.
					if (scope.getDomain() == null) {
						if (context != null) {
							context.shutdown(scope.getName());
						}
					} else {

						// Get context that created the Scope.
						final AbstractContext context = (AbstractContext) scope.getDomain().getContext();

						// Remove Scope from context.
						context.shutdown(scope.getName());

					}

				}
			}
		} catch (final ScopeException e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new instance of the root context.
	 * 
	 * @return New root context.<br>
	 *         <br>
	 */
	protected AbstractContext createContext() {
		return new AbstractContext(null) {

		};
	}

	/**
	 * Gets the root context.
	 * 
	 * @return Root context.<br>
	 *         <br>
	 */
	protected final Context getContext() {

		// Create context when required.
		if (context == null) {
			context = createContext();
		}

		// Return root context.
		return context;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade create(final Scope config) throws ScopeException {
		return getContext().create(config);
	}

	/**
	 * Decides if a Scope exists in the context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return <code>true</code> if the Scope exists or false if not.<br>
	 *         <br>
	 */
	protected boolean exists(final String name) {
		return getContext().exists(name);
	}

	/**
	 * Gets an existing Scope.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A previously created Scope.<br>
	 *         <br>
	 */
	protected ScopeFacade get(String name) {
		return getContext().get(name);
	}

	/**
	 * Retrieves the name of every Scope that exists in the context.
	 * 
	 * @return Scopes' names. If no Scope exists in the context this method returns
	 *         <code>null</code>.<br>
	 *         <br>
	 */
	protected Enumeration<String> list() {
		return getContext().list();
	}

	/**
	 * Retrieves the name of every Scope that exists in the context of a specific
	 * Domain/Scope.
	 * 
	 * @param name Name of the Scope where the context exists. This Scope is the
	 *             Domain where to search for the its scopes.<br>
	 *             <br>
	 * @return Scopes' names. If no Scope exists in the context of the Scope, this
	 *         method returns <code>null</code>.<br>
	 *         <br>
	 */
	protected Enumeration<String> list(final String name) {
		return getContext().list(name);
	}

	/**
	 * Removes an existing Scope in the context.
	 * 
	 * @param name Name of the Scope of remove.<br>
	 *             <br>
	 * @return <code>true</code> if Scope was removed and <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to remove the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected boolean remove(final String name) throws ScopeException {
		return getContext().remove(name);
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Create a new instance of the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @param parent Parent Scope for this Scope.<br>
	 *               <br>
	 * @param domain Domain Scope for this Scope.<br>
	 *               <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	private static AbstractScope create(final Scope config, final ScopeFacade parent, final ScopeFacade domain)
			throws ScopeException {
		return new AbstractScope(config, parent, domain) {

			// ///////////////////////////////////////////////////////////
			// ATTRIBUTES
			// ///////////////////////////////////////////////////////////

			// Context for Scopes.
			private Context context;

			// ///////////////////////////////////////////////////////////
			// PUBLIC METHODS
			// ///////////////////////////////////////////////////////////

			/**
			 * Gets the context for the scopes that belong to the Domain specified by this
			 * Scope.
			 * 
			 * @return Scope's context.<br>
			 *         <br>
			 */
			public Context getContext() {

				// Validate Scope is enabled.
				if (active()) {

					// Create context first time it's requested.
					if (context == null) {
						context = new AbstractContext(this) {

						};
					}

					// Return context.
					return context;

				}

				// At this point, return nothing.
				return null;

			}

			// ///////////////////////////////////////////////////////////
			// PROTECTED METHODS
			// ///////////////////////////////////////////////////////////

			/**
			 * This method does not perform any operation.
			 * 
			 * @param config Configuration for the Scope.<br>
			 *               <br>
			 */
			protected void initialize(final Scope config) {
				// DO NOTHING.
			}

			/**
			 * This method does not perform any operation.
			 */
			protected void shutdown() {
				// DO NOTHING.
			}

		};
	}

}
