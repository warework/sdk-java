package com.warework.core.scope;

import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.model.Scope;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractContext {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// COMMON KEYWORDS

	protected static final String KEYWORD_SCOPE = "scope";

	protected static final String KEYWORD_SCOPE_APPLICATION = "application";

	protected static final String KEYWORD_SCOPE_SESSION = "session";

	protected static final String KEYWORD_SCOPE_REQUEST = "request";

	protected static final String KEYWORD_WEB = "web";

	// DEFAULT CONFIG FILE NAMES

	protected static final String DEFAULT_CONFIG_NAME_APPLICATION = KEYWORD_SCOPE + StringL2Helper.CHARACTER_HYPHEN
			+ KEYWORD_SCOPE_APPLICATION;

	protected static final String DEFAULT_CONFIG_NAME_SESSION = KEYWORD_SCOPE + StringL2Helper.CHARACTER_HYPHEN
			+ KEYWORD_SCOPE_SESSION;

	protected static final String DEFAULT_CONFIG_NAME_REQUEST = KEYWORD_SCOPE + StringL2Helper.CHARACTER_HYPHEN
			+ KEYWORD_SCOPE_REQUEST;

	// ATTRIBUTE NAME

	protected static final String ATTRIBUTE_SCOPE_ID = "$" + KEYWORD_SCOPE + StringL2Helper.CHARACTER_HYPHEN + "id";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	public static abstract class AbstractServletContextScope extends AbstractSynchronizedScope {

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException
		 */
		AbstractServletContextScope(final Scope config) throws ScopeException {
			super(config, null, null);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets nothing. Parent Scopes cannot be configured in JEE environments.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getParent() {
			return null;
		}

		/**
		 * Gets nothing. No domain Scopes is available for Servlet Scopes.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getDomain() {
			return null;
		}

		/**
		 * Gets nothing. Context are managed by the JEE container.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		public final Context getContext() {
			return null;
		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * This method does not perform any operation.
		 */
		@Override
		protected final void shutdown() {
			// DO NOTHING.
		}

	}

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	public static abstract class AbstractHttpSessionScope extends AbstractSynchronizedScope {

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException
		 */
		AbstractHttpSessionScope(final Scope config) throws ScopeException {
			super(config, null, null);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets nothing. Parent Scopes cannot be configured in JEE environments.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getParent() {
			return null;
		}

		/**
		 * Gets nothing. Context are managed by the JEE container.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		public final Context getContext() {
			return null;
		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * This method does not perform any operation.
		 * 
		 * @param config Configuration for the Scope.<br>
		 *               <br>
		 */
		@Override
		protected final void initialize(final Scope config) {
			// DO NOTHING.
		}

		/**
		 * This method does not perform any operation.
		 */
		@Override
		protected final void shutdown() {
			// DO NOTHING.
		}

	}

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	public static abstract class AbstractServletRequestScope extends AbstractScope {

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException
		 */
		AbstractServletRequestScope(final Scope config) throws ScopeException {
			super(config, null, null);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets nothing. Parent Scopes cannot be configured in JEE environments.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getParent() {
			return null;
		}

		/**
		 * Gets nothing. Context are managed by the JEE container.
		 * 
		 * @return Always <code>null</code>.<br>
		 *         <br>
		 */
		public final Context getContext() {
			return null;
		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * This method does not perform any operation.
		 * 
		 * @param config Configuration for the Scope.<br>
		 *               <br>
		 */
		@Override
		protected final void initialize(final Scope config) {
			// DO NOTHING.
		}

		/**
		 * This method does not perform any operation.
		 */
		@Override
		protected final void shutdown() {
			// DO NOTHING.
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Servlet Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException
	 */
	protected abstract AbstractScope createServletScope(final Scope config) throws ScopeException;

	/**
	 * Creates the Session Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException
	 */
	protected abstract AbstractScope createSessionScope(final Scope config) throws ScopeException;

	/**
	 * Creates the Request Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException
	 */
	protected abstract AbstractScope createRequestScope(final Scope config) throws ScopeException;

	/**
	 * Creates the HTTP Request Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException
	 */
	protected abstract AbstractScope createHttpRequestScope(final Scope config) throws ScopeException;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

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
	protected final Scope updateConfig(final Scope config1) throws ScopeException {

		// Get the loader type of the configuration.
		Class<? extends LoaderFacade> loaderType = null;
		try {
			loaderType = getParameterType(config1, ScopeL1Constants.PARAMETER_CONFIG_CLASS, LoaderFacade.class);
		} catch (final ClassNotFoundException e) {
			throw new ScopeException(null,
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
				throw new ScopeException(null,
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
	protected final <T> Class<? extends T> getParameterType(final Scope config, final String parameterName,
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

}
