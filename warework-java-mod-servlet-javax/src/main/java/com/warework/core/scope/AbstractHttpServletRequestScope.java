package com.warework.core.scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractHttpServletRequestScope extends AbstractJavaxContext.AbstractHttpServletRequestScope {

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
	AbstractHttpServletRequestScope(final Scope config) throws ScopeException {
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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

	// ///////////////////////////////////////////////////////////////////

	/*
	 * 
	 */
	public String getAuthType() {
		return getWrappedScope().getAuthType();
	}

	/*
	 * 
	 */
	public Cookie[] getCookies() {
		return getWrappedScope().getCookies();
	}

	/*
	 * 
	 */
	public long getDateHeader(final String name) {
		return getWrappedScope().getDateHeader(name);
	}

	/*
	 * 
	 */
	public String getHeader(final String name) {
		return getWrappedScope().getHeader(name);
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Enumeration<String> getHeaders(final String name) {
		return getWrappedScope().getHeaders(name);
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Enumeration<String> getHeaderNames() {
		return getWrappedScope().getHeaderNames();
	}

	/*
	 * 
	 */
	public int getIntHeader(final String name) {
		return getWrappedScope().getIntHeader(name);
	}

	/*
	 * 
	 */
	public String getMethod() {
		return getWrappedScope().getMethod();
	}

	/*
	 * 
	 */
	public String getPathInfo() {
		return getWrappedScope().getPathInfo();
	}

	/*
	 * 
	 */
	public String getPathTranslated() {
		return getWrappedScope().getPathTranslated();
	}

	/*
	 * 
	 */
	public String getContextPath() {
		return getWrappedScope().getContextPath();
	}

	/*
	 * 
	 */
	public String getQueryString() {
		return getWrappedScope().getQueryString();
	}

	/*
	 * 
	 */
	public String getRemoteUser() {
		return getWrappedScope().getRemoteUser();
	}

	/*
	 * 
	 */
	public boolean isUserInRole(final String role) {
		return getWrappedScope().isUserInRole(role);
	}

	/*
	 * 
	 */
	public Principal getUserPrincipal() {
		return getWrappedScope().getUserPrincipal();
	}

	/*
	 * 
	 */
	public String getRequestedSessionId() {
		return getWrappedScope().getRequestedSessionId();
	}

	/*
	 * 
	 */
	public String getRequestURI() {
		return getWrappedScope().getRequestURI();
	}

	/*
	 * 
	 */
	public StringBuffer getRequestURL() {
		return getWrappedScope().getRequestURL();
	}

	/*
	 * 
	 */
	public String getServletPath() {
		return getWrappedScope().getServletPath();
	}

	/*
	 * 
	 */
	public boolean isRequestedSessionIdValid() {
		return getWrappedScope().isRequestedSessionIdValid();
	}

	/*
	 * 
	 */
	public boolean isRequestedSessionIdFromCookie() {
		return getWrappedScope().isRequestedSessionIdFromCookie();
	}

	/*
	 * 
	 */
	public boolean isRequestedSessionIdFromURL() {
		return getWrappedScope().isRequestedSessionIdFromURL();
	}

	/**
	 * @deprecated As of Version 2.1 of the Java Servlet API, use
	 *             {@link #isRequestedSessionIdFromURL} instead.
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return getWrappedScope().isRequestedSessionIdFromUrl();
	}

}
