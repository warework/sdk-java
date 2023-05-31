package com.warework.core.scope;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.model.Scope;
import com.warework.core.provider.ProviderException;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.loader.ServletXmlLoader;
import com.warework.provider.CacheProvider;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.AbstractServletConnector;
import com.warework.service.log.client.connector.ServletConnector;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractJakartaContext extends AbstractContext
		implements ServletContextListener, HttpSessionListener, ServletRequestListener, Filter {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// CONTEXTS

	private static final Map<String, AbstractScope> CONTEXT_SERVLET = new HashMap<String, AbstractScope>();

	private static final Map<String, AbstractScope> CONTEXT_SESSION = new HashMap<String, AbstractScope>();

	private static final Map<String, AbstractScope> CONTEXT_REQUEST = new HashMap<String, AbstractScope>();

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	public static abstract class AbstractServletContextScope extends AbstractContext.AbstractServletContextScope
			implements ServletContext {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// JEE wrapped Scope.
		private ServletContext context;

		// Name of the Provider that holds Scope initialization parameters.
		private String parameterProvider;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is a problem initializing the Scope.<br>
		 *                        <br>
		 */
		AbstractServletContextScope(final Scope config) throws ScopeException {
			super(config);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the value of an initialization parameter.
		 * 
		 * @param name Name of the initialization parameter.<br>
		 *             <br>
		 * @return Value of the initialization parameter.<br>
		 *         <br>
		 */
		@Override
		public final String getInitParameter(final String name) {
			return (name.equals(ScopeL1Constants.PARAMETER_CONTEXT_LOADER)) ? parameterProvider
					: getWrappedScope().getInitParameter(name);
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
		@Override
		public final void removeProvider(final String name) throws ProviderException {
			if (name.equals(parameterProvider)) {
				throw new ProviderException(this,
						"WAREWORK cannot remove Provider '" + name + "' because it is internally used by the Scope.",
						null, LogServiceConstants.LOG_LEVEL_FATAL);
			} else {
				super.removeProvider(name);
			}
		}

		/**
		 * Gets a <code>ServletContext</code> object that corresponds to a specified URL
		 * on the server.<br>
		 * <br>
		 * This method allows servlets to gain access to the context for various parts
		 * of the server, and as needed obtain {@link RequestDispatcher} objects from
		 * the context. The given path must be begin with "/", is interpreted relative
		 * to the server's document root and is matched against the context roots of
		 * other web applications hosted on this container.<br>
		 * <br>
		 * In a security conscious environment, the servlet container may return
		 * <code>null</code> for a given URL.
		 * 
		 * @param uripath A <code>String</code> specifying the context path of another
		 *                web application in the container.<br>
		 *                <br>
		 * @return The <code>ServletContext</code> object that corresponds to the named
		 *         URL, or null if either none exists or the container wishes to
		 *         restrict this access.<br>
		 *         <br>
		 *
		 */
		public final ServletContext getContext(final String uripath) {

			// Get the servlet context.
			final ServletContext servletContext = context.getContext(uripath);

			// Return Warework Scope that matches JEE servlet context Scope.
			return (servletContext == null) ? null : (ServletContext) CONTEXT_SERVLET.get(getScopeName(servletContext));

		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Setup initialization parameters.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 */
		@Override
		protected final void setupParameters(final Scope config) {
			this.context = (ServletContext) config.getInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE);
		}

		/**
		 * Registers that Scope is initialized.
		 * 
		 * @throws ScopeException If there is a problem initializing the Scope.<br>
		 *                        <br>
		 */
		@Override
		protected final void start() throws ScopeException {
			try {

				// Set the name of the Provider that holds Scope initialization parameters.
				parameterProvider = StringL2Helper.create128BitHexRandomString();

				// Setup Provider configuration.
				final Map<String, Object> parameters = new HashMap<String, Object>();

				// Set context to load resources.
				parameters.put(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, context);

				// Create Provider.
				setupProvider(parameterProvider, CacheProvider.class, parameters);

			} catch (final ProviderException e) {
				throw new ScopeException(this,
						"WAREWORK cannot initialize the Scope because a problem was found when trying to create a Provider to hold the initialization parameters.",
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		/**
		 * This method does not perform any operation.
		 * 
		 * @param config Configuration for the Scope.<br>
		 *               <br>
		 */
		@Override
		protected final void initialize(final Scope config) {
			if (context.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) != null) {
				warn("WAREWORK is overriding servlet initialization parameter '"
						+ ScopeL1Constants.PARAMETER_CONTEXT_LOADER
						+ "' and will return a different value when invoking method 'getInitParameter'.");
			}
		}

		/**
		 * Gets the JEE Scope wrapped by this Warework Scope.
		 * 
		 * @return JEE servlet context Scope.<br>
		 *         <br>
		 */
		protected final ServletContext getWrappedScope() {
			return context;
		}

	}

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	public static abstract class AbstractHttpSessionScope extends AbstractContext.AbstractHttpSessionScope
			implements HttpSession {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// JEE wrapped Scope.
		private HttpSession session;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is a problem initializing the Scope.<br>
		 *                        <br>
		 */
		AbstractHttpSessionScope(final Scope config) throws ScopeException {
			super(config);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the Domain Scope of this Scope.
		 * 
		 * @return Servlet context Scope if no session exists.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getDomain() {
			return CONTEXT_SERVLET.get(getScopeName(session.getServletContext()));
		}

		/**
		 * Gets the ServletContext to which this session belongs.
		 * 
		 * @return The ServletContext object for the web application.<br>
		 *         <br>
		 */
		public final ServletContext getServletContext() {
			return (ServletContext) CONTEXT_SERVLET.get(getScopeName(session.getServletContext()));
		}

		/**
		 * Gets the names of the initialization parameters.
		 * 
		 * @return Initialization parameters' names or <code>null</code> if Scope has no
		 *         initialization parameters.<br>
		 *         <br>
		 */
		@Override
		public final Enumeration<String> getInitParameterNames() {
			return getDomain().getInitParameterNames();
		}

		/**
		 * Gets the value of an initialization parameter.
		 * 
		 * @param name Name of the initialization parameter.<br>
		 *             <br>
		 * @return Value of the initialization parameter.<br>
		 *         <br>
		 */
		@Override
		public final String getInitParameter(final String name) {
			return (String) getDomain().getInitParameter(name);
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
		@Override
		public final void setAttribute(final String name, final Object value) {
			if ((validateID(this)) || (!name.equals(ATTRIBUTE_SCOPE_ID))) {
				session.setAttribute(name, value);
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
		@Override
		public final void removeAttribute(final String name) {
			if ((validateID(this)) || (!name.equals(ATTRIBUTE_SCOPE_ID))) {
				session.removeAttribute(name);
			}
		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Setup initialization parameters.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 */
		@Override
		protected final void setupParameters(final Scope config) {
			this.session = (HttpSession) config.getInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE);
		}

		/**
		 * Gets the JEE Scope wrapped by this Warework Scope.
		 * 
		 * @return JEE HTTP session Scope.<br>
		 *         <br>
		 */
		protected final HttpSession getWrappedScope() {
			return session;
		}

	}

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	public static abstract class AbstractServletRequestScope extends AbstractContext.AbstractServletRequestScope
			implements ServletRequest {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// JEE servlet context.
		private ServletContext context;

		// JEE wrapped Scope.
		private ServletRequest request;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is a problem initializing the Scope.<br>
		 *                        <br>
		 */
		AbstractServletRequestScope(final Scope config) throws ScopeException {
			super(config);
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the Domain Scope of this Scope.
		 * 
		 * @return Servlet context Scope if no session exists.<br>
		 *         <br>
		 */
		@Override
		public ScopeFacade getDomain() {
			return CONTEXT_SERVLET.get(getScopeName(context));
		}

		/**
		 * Gets the names of the initialization parameters.
		 * 
		 * @return Initialization parameters' names or <code>null</code> if Scope has no
		 *         initialization parameters.<br>
		 *         <br>
		 */
		@Override
		public final Enumeration<String> getInitParameterNames() {
			return getDomain().getInitParameterNames();
		}

		/**
		 * Gets the value of an initialization parameter.
		 * 
		 * @param name Name of the initialization parameter.<br>
		 *             <br>
		 * @return Value of the initialization parameter.<br>
		 *         <br>
		 */
		@Override
		public final String getInitParameter(final String name) {
			return (String) getDomain().getInitParameter(name);
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
		@Override
		public final void setAttribute(final String name, final Object value) {
			if ((validateID()) || (!name.equals(ATTRIBUTE_SCOPE_ID))) {
				request.setAttribute(name, value);
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
		@Override
		public final void removeAttribute(final String name) {
			if ((validateID()) || (!name.equals(ATTRIBUTE_SCOPE_ID))) {
				request.removeAttribute(name);
			}
		}

		/**
		 * Gets the servlet context to which this ServletRequest was last dispatched.
		 *
		 * @return the servlet context to which this ServletRequest was last
		 *         dispatched.<br>
		 *         <br>
		 *
		 * @since Servlet 3.0
		 */
		public final ServletContext getServletContext() {
			return (ServletContext) CONTEXT_SERVLET.get(getScopeName(context));
		}

		// ///////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Setup initialization parameters.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 */
		@Override
		protected void setupParameters(final Scope config) {

			// Set servlet conext Scope.
			this.context = (ServletContext) config.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER);

			// Set JEE request wrapped Scope.
			this.request = (ServletRequest) config.getInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE);

		}

		/**
		 * Gets the JEE Scope wrapped by this Warework Scope.
		 * 
		 * @return JEE servlet request Scope.<br>
		 *         <br>
		 */
		protected ServletRequest getWrappedScope() {
			return request;
		}

		/**
		 * Validates servlet request provides an ID.
		 * 
		 * @return <code>true</code> if request has a not null or empty string ID.
		 */
		protected boolean validateID() {
			return false;
		}

	}

	/**
	 * 
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	public static abstract class AbstractHttpServletRequestScope
			extends AbstractJakartaContext.AbstractServletRequestScope implements HttpServletRequest {

		// ///////////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////////

		// JEE wrapped Scope.
		private HttpServletRequest request;

		// ///////////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////////

		/**
		 * Creates the Scope.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is a problem initializing the Scope.<br>
		 *                        <br>
		 */
		AbstractHttpServletRequestScope(final Scope config) throws ScopeException {
			super(config);
		}

		// ///////////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////////

		/**
		 * Gets the Domain Scope of this Scope.
		 * 
		 * @return HTTP session Scope or servlet context Scope if no session exists.<br>
		 *         <br>
		 */
		@Override
		public final ScopeFacade getDomain() {

			// Get current session associated with this request. MUST create always the
			// session here because the default domain Scope of the HTTP request is the HTTP
			// session Scope.
			final HttpSession session = request.getSession(true);

			// Retuen session or servlet Scope.
			return (session == null) ? super.getDomain() : CONTEXT_SESSION.get(getScopeName(session));

		}

		/**
		 * Gets the current session associated with this request or creates a new one if
		 * the request does not have a session.
		 * 
		 * @return the <code>HttpSession</code> associated with this request.<br>
		 *         <br>
		 */
		public final HttpSession getSession() {
			return getSession(true);
		}

		/**
		 * Gets the current <code>HttpSession</code> associated with this request or, if
		 * there is no current session and <code>create</code> is true, returns a new
		 * session.<br>
		 * <br>
		 * If <code>create</code> is <code>false</code> and the request has no valid
		 * <code>HttpSession</code>, this method returns <code>null</code>.<br>
		 * <br>
		 * To make sure the session is properly maintained, you must call this method
		 * before the response is committed. If the container is using cookies to
		 * maintain session integrity and is asked to create a new session when the
		 * response is committed, an IllegalStateException is thrown.
		 *
		 * @param create <code>true</code> to create a new session for this request if
		 *               necessary; <code>false</code> to return <code>null</code> if
		 *               there's no current session.<br>
		 *               <br>
		 * @return the <code>HttpSession</code> associated with this request or
		 *         <code>null</code> if <code>create</code> is <code>false</code> and
		 *         the request has no valid session.<br>
		 *         <br>
		 */
		public final HttpSession getSession(final boolean create) {

			// Get current session associtaed with this request.
			final HttpSession session = request.getSession(create);

			// Get session Scope.
			return (session == null) ? null : (HttpSession) CONTEXT_SESSION.get(getScopeName(session));

		}

		// ///////////////////////////////////////////////////////////////////
		// PROTECTED METHODS
		// ///////////////////////////////////////////////////////////////////

		/**
		 * Setup initialization parameters.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 */
		@Override
		protected final void setupParameters(final Scope config) {

			// Must setup parent parameters frist to configure servlet context and JEE
			// request Scope.
			super.setupParameters(config);

			// Set JEE HTTP request wrapped Scope.
			this.request = (HttpServletRequest) config.getInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE);

		}

		/**
		 * Gets the JEE Scope wrapped by this Warework Scope.
		 * 
		 * @return JEE HTTP request Scope.<br>
		 *         <br>
		 */
		@Override
		protected final HttpServletRequest getWrappedScope() {
			return request;
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Servlet Scope.
	 * 
	 * @param event The ServletContextEvent containing the ServletContext that is
	 *              being initialized.<br>
	 *              <br>
	 */
	public void contextInitialized(final ServletContextEvent event) {
		try {

			// Get the JEE servlet context.
			final ServletContext context = event.getServletContext();

			// Get the name of the Scope.
			final String name = getScopeName(context);

			// Get XML configuration file path.
			URL configTarget = null;
			try {
				configTarget = getScopeURL(context, DEFAULT_CONFIG_NAME_APPLICATION);
			} catch (final MalformedURLException e) {
				// DO NOTHING. CONFIG IS OPTIONAL.
			}

			// Create new Scope configuration.
			final Scope config = new Scope(name);

			// Configure Scope to create.
			config.setInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE, context);
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, context);

			// Load XML configuration file.
			if (configTarget != null) {
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ServletXmlLoader.class);
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET, configTarget);
			}

			// Setup Log Service.
			config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);

			// Setup default logger.
			config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceConstants.DEFAULT_CLIENT_NAME,
					ServletConnector.class, null);
			config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceConstants.DEFAULT_CLIENT_NAME,
					AbstractServletConnector.PARAMETER_CONTEXT_LOADER, context);

			// Add Scope in this context.
			CONTEXT_SERVLET.put(name, createServletScope(updateConfig(config)));

		} catch (final ScopeException e) {
			event.getServletContext()
					.log("WAREWORK cannot create the Servlet Scope because the following problem was found: "
							+ e.getMessage(), e);
		}
	}

	/**
	 * Removes the Servlet Scope.
	 * 
	 * @param event The ServletContextEvent containing the ServletContext that is
	 *              being removed.<br>
	 *              <br>
	 */
	public void contextDestroyed(final ServletContextEvent event) {

		// Get the JEE servlet context.
		final ServletContext context = event.getServletContext();

		// Get the name of the Scope.
		final String name = getScopeName(context);

		// Validate Scope exists in global context.
		if (CONTEXT_SERVLET.containsKey(name)) {

			// Shutdown Scope if it's running.
			CONTEXT_SERVLET.get(name).close();

			// Remove Scope from the context.
			CONTEXT_SERVLET.remove(name);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Session Scope.
	 * 
	 * @param event The <code>HttpSessionEvent</code> containing the
	 *              <code>HttpSession</code> that is being initialized.<br>
	 *              <br>
	 */
	public void sessionCreated(final HttpSessionEvent event) {
		try {

			// Get the JEE session.
			final HttpSession session = event.getSession();

			// Get the JEE servlet context.
			final ServletContext context = session.getServletContext();

			// Get ID of the HTTP session.
			final String id = createID(session);

			// Create the name of the Session Scope.
			final String name = KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_SESSION
					+ StringL2Helper.CHARACTER_LEFT_PARENTHESES + id + StringL2Helper.CHARACTER_RIGHT_PARENTHESES;

			// Get XML configuration file path.
			URL configTarget = null;
			try {
				configTarget = getScopeURL(context, DEFAULT_CONFIG_NAME_SESSION);
			} catch (final MalformedURLException e) {
				// DO NOTHING. CONFIG IS OPTIONAL.
			}

			// Create new Scope configuration.
			final Scope config = new Scope(name);

			// Configure Scope to create.
			config.setInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE, session);
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, context);

			// Load XML configuration file.
			if (configTarget != null) {
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ServletXmlLoader.class);
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET, configTarget);
			}

			// Add Scope in this context.
			CONTEXT_SESSION.put(name, createSessionScope(updateConfig(config)));

			// Save session ID as a session attribute when no session ID exists.
			if (!validateID(session)) {
				session.setAttribute(ATTRIBUTE_SCOPE_ID, id);
			}

		} catch (final ScopeException e) {
			event.getSession().getServletContext()
					.log("WAREWORK cannot create the HTTP Session Scope because the following problem was found: "
							+ e.getMessage(), e);
		}
	}

	/**
	 * Removes the Session Scope.
	 * 
	 * @param event The HttpSessionEvent containing the HttpSession that is being
	 *              removed.<br>
	 *              <br>
	 */
	public void sessionDestroyed(final HttpSessionEvent event) {

		// Get the JEE session.
		final HttpSession session = event.getSession();

		// Get the name of the Scope.
		final String name = getScopeName(session);

		// Validate Scope exists in global context.
		if (CONTEXT_SESSION.containsKey(name)) {

			// Shutdown Scope if it's running.
			CONTEXT_SESSION.get(name).close();

			// Remove Scope from the context.
			CONTEXT_SESSION.remove(name);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Request Scope.
	 * 
	 * @param event The ServletRequestEvent containing the ServletRequest that is
	 *              being initialized.<br>
	 *              <br>
	 */
	public void requestInitialized(final ServletRequestEvent event) {
		try {

			// Get the JEE servlet request.
			final ServletRequest request = event.getServletRequest();

			// Get the JEE servlet context.
			final ServletContext context = event.getServletContext();

			// Create unique request name/ID.
			final String id = createID(request);

			// Create the name of the Request Scope.
			final String name = KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_REQUEST
					+ StringL2Helper.CHARACTER_LEFT_PARENTHESES + id + StringL2Helper.CHARACTER_RIGHT_PARENTHESES;

			// Get XML configuration file path.
			URL configTarget = null;
			try {
				configTarget = getScopeURL(context, DEFAULT_CONFIG_NAME_REQUEST);
			} catch (final MalformedURLException e) {
				// DO NOTHING. CONFIG IS OPTIONAL.
			}

			// Create new Scope configuration.
			final Scope config = new Scope(name);

			// Configure Scope to create.
			config.setInitParameter(ScopeL1Constants.PARAMETER_WRAPPED_SCOPE, request);
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, context);

			// Load XML configuration file.
			if (configTarget != null) {
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ServletXmlLoader.class);
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET, configTarget);
			}

			// Add Scope in this context.
			CONTEXT_REQUEST.put(name,
					(request instanceof HttpServletRequest) ? createHttpRequestScope(updateConfig(config))
							: createRequestScope(updateConfig(config)));

			// Save request ID as a request attribute.
			if (!validateID(request)) {
				request.setAttribute(ATTRIBUTE_SCOPE_ID, id);
			}

		} catch (final ScopeException e) {
			event.getServletContext()
					.log("WAREWORK cannot create the Request Scope because the following problem was found: "
							+ e.getMessage(), e);
		}
	}

	/**
	 * Removes the Request Scope.
	 * 
	 * @param event The ServletRequestEvent containing the ServletRequest that is
	 *              being removed.<br>
	 *              <br>
	 */
	public void requestDestroyed(final ServletRequestEvent event) {

		// Get the JEE servlet request.
		final ServletRequest request = event.getServletRequest();

		// Get the name of the Scope.
		final String name = getScopeName(request);

		// Validate Scope exists in global context.
		if (CONTEXT_REQUEST.containsKey(name)) {

			// Shutdown Scope if it's running.
			CONTEXT_REQUEST.get(name).close();

			// Remove Scope from the context.
			CONTEXT_REQUEST.remove(name);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does not perform any operation.
	 */
	public final void init(final FilterConfig config) throws ServletException {
		// DO NOTHING.
	}

	/**
	 * Replace JEE request with Warework request Scope.
	 * 
	 * @param request  JEE servlet request.<br>
	 *                 <br>
	 * @param response JEE servlet response.<br>
	 *                 <br>
	 * @param chain    Filter chain.<br>
	 *                 <br>
	 * 
	 */
	public final void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter((ServletRequest) CONTEXT_REQUEST.get(getScopeName(request)), response);
	}

	/**
	 * This method does not perform any operation.
	 */
	public final void destroy() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates servlet request provides an ID.
	 * 
	 * @param request Servlet request where to validate the Id.<br>
	 *                <br>
	 * @return <code>true</code> if request has a not null or empty string ID.
	 */
	protected boolean validateID(final ServletRequest request) {
		return false;
	}

	/**
	 * Creates a servlet request ID.
	 * 
	 * @param request Servlet request where to create the Id.<br>
	 *                <br>
	 * @return ID for the servlet request.
	 */
	protected String createID(final ServletRequest request) {
		return StringL2Helper.create128BitHexRandomString();
	}

	/**
	 * Gets the name of the Request Scope.
	 * 
	 * @param request JEE Request Scope.<br>
	 *                <br>
	 * @return Name of the Warework Request Scope.<br>
	 *         <br>
	 */
	protected String getScopeName(final ServletRequest request) {

		// Get the request ID.
		final String id = (String) request.getAttribute(ATTRIBUTE_SCOPE_ID);

		// Return Scope name.
		return KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_REQUEST
				+ StringL2Helper.CHARACTER_LEFT_PARENTHESES + id + StringL2Helper.CHARACTER_RIGHT_PARENTHESES;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates HTTP session provides an ID.
	 * 
	 * @param session HTTP session where to validate the Id.<br>
	 *                <br>
	 * @return <code>true</code> if session has a not null or empty string ID.
	 */
	private static boolean validateID(final HttpSession session) {
		return !((session.getId() == null) || (session.getId().equals(CommonValueL2Constants.STRING_EMPTY)));
	}

	/**
	 * Creates an HTTP session ID.
	 * 
	 * @param session HTTP session where to create the Id.<br>
	 *                <br>
	 * @return ID for the HTTP session.
	 */
	private static String createID(final HttpSession session) {
		return validateID(session) ? session.getId() : StringL2Helper.create128BitHexRandomString();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Servlet Scope.
	 * 
	 * @param context JEE Servlet Scope.<br>
	 *                <br>
	 * @return Name of the Warework Servlet Scope.<br>
	 *         <br>
	 */
	private static String getScopeName(final ServletContext context) {

		// Get the Servlet Context name.
		String id = context.getServletContextName();
		if ((id == null) || (id.equals(CommonValueL2Constants.STRING_EMPTY))) {
			id = context.getContextPath();
		}

		// Return the name for the Warework Servlet Scope.
		return KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_APPLICATION
				+ StringL2Helper.CHARACTER_LEFT_PARENTHESES + id + StringL2Helper.CHARACTER_RIGHT_PARENTHESES;

	}

	/**
	 * Gets the name of the Session Scope.
	 * 
	 * @param session JEE Session Scope.<br>
	 *                <br>
	 * @return Name of the Warework Session Scope.<br>
	 *         <br>
	 */
	private static String getScopeName(final HttpSession session) {
		return KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_SESSION
				+ StringL2Helper.CHARACTER_LEFT_PARENTHESES
				+ ((validateID(session)) ? session.getId() : (String) session.getAttribute(ATTRIBUTE_SCOPE_ID))
				+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the URL that points to a resource.
	 * 
	 * @param context  Servlet context to load the resource.<br>
	 *                 <br>
	 * @param filename Name of the file to load.<br>
	 *                 <br>
	 * @return The URL that points to the resource.<br>
	 *         <br>
	 * @throws MalformedURLException If there is an error when trying to create the
	 *                               URL.<br>
	 *                               <br>
	 */
	private URL getScopeURL(final ServletContext context, final String filename) throws MalformedURLException {
		return context.getResource(ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_WEB_INF
				+ ResourceL1Helper.DIRECTORY_SEPARATOR + filename + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
				+ ResourceL2Helper.FILE_EXTENSION_XML);
	}

}
