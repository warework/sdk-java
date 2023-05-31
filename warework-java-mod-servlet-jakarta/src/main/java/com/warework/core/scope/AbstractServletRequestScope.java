package com.warework.core.scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import com.warework.core.model.Scope;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractServletRequestScope extends AbstractJakartaContext.AbstractServletRequestScope {

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
	AbstractServletRequestScope(final Scope config) throws ScopeException {
		super(config);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	@Override
	public Enumeration<String> getAttributeNames() {
		return getWrappedScope().getAttributeNames();
	}

	/*
	 * 
	 */
	@Override
	public Object getAttribute(final String name) {
		return getWrappedScope().getAttribute(name);
	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	public String getParameter(final String name) {
		return getWrappedScope().getParameter(name);
	}

	/*
	 * 
	 */
	public Enumeration<String> getParameterNames() {
		return getWrappedScope().getParameterNames();
	}

	/*
	 * 
	 */
	public String[] getParameterValues(final String name) {
		return getWrappedScope().getParameterValues(name);
	}

	/*
	 * 
	 */
	public Map<String, String[]> getParameterMap() {
		return getWrappedScope().getParameterMap();
	}

	/*
	 * 
	 */
	public String getCharacterEncoding() {
		return getWrappedScope().getCharacterEncoding();
	}

	/*
	 * 
	 */
	public void setCharacterEncoding(final String env) throws UnsupportedEncodingException {
		getWrappedScope().setCharacterEncoding(env);
	}

	/*
	 * 
	 */
	public int getContentLength() {
		return getWrappedScope().getContentLength();
	}

	/*
	 * 
	 */
	public String getContentType() {
		return getWrappedScope().getContentType();
	}

	/*
	 * 
	 */
	public ServletInputStream getInputStream() throws IOException {
		return getWrappedScope().getInputStream();
	}

	/*
	 * 
	 */
	public String getProtocol() {
		return getWrappedScope().getProtocol();
	}

	/*
	 * 
	 */
	public String getScheme() {
		return getWrappedScope().getScheme();
	}

	/*
	 * 
	 */
	public String getServerName() {
		return getWrappedScope().getServerName();
	}

	/*
	 * 
	 */
	public int getServerPort() {
		return getWrappedScope().getServerPort();
	}

	/*
	 * 
	 */
	public BufferedReader getReader() throws IOException {
		return getWrappedScope().getReader();
	}

	/*
	 * 
	 */
	public String getRemoteAddr() {
		return getWrappedScope().getRemoteAddr();
	}

	/*
	 * 
	 */
	public String getRemoteHost() {
		return getWrappedScope().getRemoteHost();
	}

	/*
	 * 
	 */
	public Locale getLocale() {
		return getWrappedScope().getLocale();
	}

	/*
	 * 
	 */
	public Enumeration<Locale> getLocales() {
		return getWrappedScope().getLocales();
	}

	/*
	 * 
	 */
	public boolean isSecure() {
		return getWrappedScope().isSecure();
	}

	/*
	 * 
	 */
	public RequestDispatcher getRequestDispatcher(final String path) {
		return getWrappedScope().getRequestDispatcher(path);
	}

	/**
	 * @deprecated As of Version 2.1 of the Java Servlet API, use
	 *             {@link ServletContext#getRealPath} instead.
	 */
	public String getRealPath(final String path) {
		return getWrappedScope().getRealPath(path);
	}

	/*
	 * 
	 */
	public int getRemotePort() {
		return getWrappedScope().getRemotePort();
	}

	/*
	 * 
	 */
	public String getLocalName() {
		return getWrappedScope().getLocalName();
	}

	/*
	 * 
	 */
	public String getLocalAddr() {
		return getWrappedScope().getLocalAddr();
	}

	/*
	 * 
	 */
	public int getLocalPort() {
		return getWrappedScope().getLocalPort();
	}

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

	/*
	 * 
	 */
	public long getContentLengthLong() {
		return getWrappedScope().getContentLengthLong();
	}

}