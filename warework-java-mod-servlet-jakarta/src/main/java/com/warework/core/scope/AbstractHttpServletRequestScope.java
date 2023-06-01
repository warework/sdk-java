package com.warework.core.scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import com.warework.core.model.Scope;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Part;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractHttpServletRequestScope extends AbstractJakartaContext.AbstractHttpServletRequestScope {

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
	public Enumeration<String> getHeaders(final String name) {
		return getWrappedScope().getHeaders(name);
	}

	/*
	 * 
	 */
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

	/*
	 * 
	 */
	public String changeSessionId() {
		return getWrappedScope().changeSessionId();
	}

	/*
	 * 
	 */
	public <T extends HttpUpgradeHandler> T upgrade(final Class<T> handlerClass) throws IOException, ServletException {
		return getWrappedScope().upgrade(handlerClass);
	}

	/*
	 * 
	 */
	public long getContentLengthLong() {
		return getWrappedScope().getContentLengthLong();
	}

}
