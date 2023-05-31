package com.warework.core.scope;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ScopeContext extends AbstractJakartaContext {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Servlet Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException If there is a problem initializing the Scope.<br>
	 *                        <br>
	 */
	@Override
	protected AbstractServletContextScope createServletScope(final Scope config) throws ScopeException {
		return new ServletContextScope(config);
	}

	/**
	 * Creates the Session Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException If there is a problem initializing the Scope.<br>
	 *                        <br>
	 */
	@Override
	protected AbstractHttpSessionScope createSessionScope(final Scope config) throws ScopeException {
		return new HttpSessionScope(config);
	}

	/**
	 * Creates the Request Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException If there is a problem initializing the Scope.<br>
	 *                        <br>
	 */
	@Override
	protected AbstractServletRequestScope createRequestScope(final Scope config) throws ScopeException {
		return new ServletRequestScope(config);
	}

	/**
	 * Creates the HTTP Request Scope.
	 * 
	 * @param config Configuration of the Scope to create.<br>
	 *               <br>
	 * @return New instance of a Warework Scope.<br>
	 *         <br>
	 * @throws ScopeException If there is a problem initializing the Scope.<br>
	 *                        <br>
	 */
	@Override
	protected AbstractHttpServletRequestScope createHttpRequestScope(final Scope config) throws ScopeException {
		return new HttpServletRequestScope(config);
	}

}
