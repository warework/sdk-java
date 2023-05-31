package com.warework.core.scope;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
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

	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	public boolean authenticate(final HttpServletResponse response) throws IOException, ServletException {
		return getWrappedScope().authenticate(response);
	}

	/*
	 * 
	 */
	public void login(final String username, final String password) throws ServletException {
		getWrappedScope().login(username, password);
	}

	/*
	 * 
	 */
	public void logout() throws ServletException {
		getWrappedScope().logout();
	}

	/*
	 * 
	 */
	public Collection<Part> getParts() throws IOException, ServletException {
		return getWrappedScope().getParts();
	}

	/*
	 * 
	 */
	public Part getPart(final String name) throws IOException, ServletException {
		return getWrappedScope().getPart(name);
	}

}
