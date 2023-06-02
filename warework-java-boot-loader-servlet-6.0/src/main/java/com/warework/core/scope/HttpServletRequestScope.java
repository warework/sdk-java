package com.warework.core.scope;

import com.warework.core.model.Scope;
import com.warework.core.util.CommonValueL2Constants;

import jakarta.servlet.ServletConnection;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class HttpServletRequestScope extends AbstractHttpServletRequestScope {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException
	 */
	HttpServletRequestScope(final Scope config) throws ScopeException {
		super(config);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	public String getRequestId() {
		return getWrappedScope().getRequestId();
	}

	/*
	 * 
	 */
	public String getProtocolRequestId() {
		return getWrappedScope().getProtocolRequestId();
	}

	/*
	 * 
	 */
	public ServletConnection getServletConnection() {
		return getWrappedScope().getServletConnection();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates servlet request provides an ID.
	 * 
	 * @return <code>true</code> if request has a not null or empty string ID.
	 */
	protected boolean validateID() {
		return !((getRequestId() == null) || (getRequestId().equals(CommonValueL2Constants.STRING_EMPTY)));
	}

}
