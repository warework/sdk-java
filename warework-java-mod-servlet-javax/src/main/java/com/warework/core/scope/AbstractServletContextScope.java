package com.warework.core.scope;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractServletContextScope extends AbstractJavaxContext.AbstractServletContextScope {

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
	AbstractServletContextScope(final Scope config) throws ScopeException {
		super(config);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Enumeration<String> getInitParameterNames() {
		return getWrappedScope().getInitParameterNames();
	}

	/*
	 * 
	 */
	@Override
	public Object getAttribute(final String name) {
		return getWrappedScope().getAttribute(name);
	}

	/*
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Enumeration<String> getAttributeNames() {
		return getWrappedScope().getAttributeNames();
	}

	/*
	 * 
	 */
	@Override
	public void setAttribute(final String name, final Object value) {
		getWrappedScope().setAttribute(name, value);
	}

	/*
	 * 
	 */
	@Override
	public void removeAttribute(final String name) {
		getWrappedScope().removeAttribute(name);
	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	public String getContextPath() {
		return getWrappedScope().getContextPath();
	}

	/*
	 * 
	 */
	public int getMajorVersion() {
		return getWrappedScope().getMajorVersion();
	}

	/*
	 * 
	 */
	public String getMimeType(final String file) {
		return getWrappedScope().getMimeType(file);
	}

	/*
	 * 
	 */
	public int getMinorVersion() {
		return getWrappedScope().getMinorVersion();
	}

	/*
	 * 
	 */
	public RequestDispatcher getNamedDispatcher(final String name) {
		return getWrappedScope().getNamedDispatcher(name);
	}

	/*
	 * 
	 */
	public String getRealPath(final String path) {
		return getWrappedScope().getRealPath(path);
	}

	/*
	 * 
	 */
	public RequestDispatcher getRequestDispatcher(final String path) {
		return getWrappedScope().getRequestDispatcher(path);
	}

	/*
	 * 
	 */
	public URL getResource(final String path) throws MalformedURLException {
		return getWrappedScope().getResource(path);
	}

	/*
	 * 
	 */
	public InputStream getResourceAsStream(final String path) {
		return getWrappedScope().getResourceAsStream(path);
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getResourcePaths(final String path) {
		return getWrappedScope().getResourcePaths(path);
	}

	/*
	 * 
	 */
	public String getServerInfo() {
		return getWrappedScope().getServerInfo();
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, with no direct replacement.<br>
	 *             <br>
	 */
	public Servlet getServlet(final String name) throws ServletException {
		return getWrappedScope().getServlet(name);
	}

	/*
	 * 
	 */
	public String getServletContextName() {
		return getWrappedScope().getServletContextName();
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, with no replacement.<br>
	 *             <br>
	 */
	@SuppressWarnings("unchecked")
	public Enumeration<String> getServletNames() {
		return getWrappedScope().getServletNames();
	}

	/**
	 * @deprecated As of Java Servlet API 2.0, with no replacement.<br>
	 *             <br>
	 */
	@SuppressWarnings("unchecked")
	public Enumeration<Servlet> getServlets() {
		return getWrappedScope().getServlets();
	}

	/*
	 * 
	 */
	public void log(final String message) {
		getWrappedScope().log(message);
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, use
	 *             {@link #log(String message, Throwable throwable)} instead.<br>
	 *             <br>
	 */
	public void log(final Exception exception, final String message) {
		getWrappedScope().log(exception, message);
	}

	/*
	 * 
	 */
	public void log(final String message, final Throwable throwable) {
		getWrappedScope().log(message, throwable);
	}

}
