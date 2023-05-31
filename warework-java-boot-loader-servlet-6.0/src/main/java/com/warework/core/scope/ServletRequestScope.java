package com.warework.core.scope;

import com.warework.core.model.Scope;
import com.warework.core.util.CommonValueL2Constants;

import jakarta.servlet.ServletConnection;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ServletRequestScope extends AbstractServletRequestScope {

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
	ServletRequestScope(final Scope config) throws ScopeException {
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
