package com.warework.core.scope;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
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
	public AsyncContext startAsync() throws IllegalStateException {
		return getWrappedScope().startAsync();
	}

	/*
	 * 
	 */
	public AsyncContext startAsync(final ServletRequest servletRequest, final ServletResponse servletResponse)
			throws IllegalStateException {
		return getWrappedScope().startAsync();
	}

	/*
	 * 
	 */
	public boolean isAsyncStarted() {
		return getWrappedScope().isAsyncStarted();
	}

	/*
	 * 
	 */
	public boolean isAsyncSupported() {
		return getWrappedScope().isAsyncSupported();
	}

	/*
	 * 
	 */
	public AsyncContext getAsyncContext() {
		return getWrappedScope().getAsyncContext();
	}

	/*
	 * 
	 */
	public DispatcherType getDispatcherType() {
		return getWrappedScope().getDispatcherType();
	}

}
