package com.warework.core.scope;

import com.warework.core.model.Scope;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

import jakarta.servlet.ServletRequest;

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

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates servlet request provides an ID.
	 * 
	 * @param request Servlet request where to validate the Id.<br>
	 *                <br>
	 * @return <code>true</code> if request has a not null or empty string ID.
	 */
	@Override
	protected boolean validateID(final ServletRequest request) {
		return !((request.getRequestId() == null)
				|| (request.getRequestId().equals(CommonValueL2Constants.STRING_EMPTY)));
	}

	/**
	 * Creates a servlet request ID.
	 * 
	 * @param request Servlet request where to create the Id.<br>
	 *                <br>
	 * @return ID for the servlet request.
	 */
	@Override
	protected String createID(final ServletRequest request) {
		return validateID(request) ? request.getRequestId() : StringL2Helper.create128BitHexRandomString();
	}

	/**
	 * Gets the name of the Request Scope.
	 * 
	 * @param request JEE Request Scope.<br>
	 *                <br>
	 * @return Name of the Warework Request Scope.<br>
	 *         <br>
	 */
	@Override
	protected String getScopeName(final ServletRequest request) {
		return KEYWORD_WEB + StringL2Helper.CHARACTER_GREATER_THAN + KEYWORD_SCOPE_REQUEST
				+ StringL2Helper.CHARACTER_LEFT_PARENTHESES
				+ ((validateID(request)) ? request.getRequestId() : (String) request.getAttribute(ATTRIBUTE_SCOPE_ID))
				+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
	}

}
