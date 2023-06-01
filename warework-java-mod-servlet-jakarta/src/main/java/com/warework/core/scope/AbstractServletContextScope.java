package com.warework.core.scope;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import com.warework.core.model.Scope;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractServletContextScope extends AbstractJakartaContext.AbstractServletContextScope {

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
	public Enumeration<String> getServletNames() {
		return getWrappedScope().getServletNames();
	}

	/**
	 * @deprecated As of Java Servlet API 2.0, with no replacement.<br>
	 *             <br>
	 */
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

	/*
	 * 
	 */
	public boolean setInitParameter(final String name, final String value) {
		return getWrappedScope().setInitParameter(name, value);
	}

	/*
	 * 
	 */
	public int getEffectiveMajorVersion() {
		return getWrappedScope().getEffectiveMajorVersion();
	}

	/*
	 * 
	 */
	public int getEffectiveMinorVersion() {
		return getWrappedScope().getEffectiveMinorVersion();
	}

	/*
	 * 
	 */
	public ServletRegistration.Dynamic addServlet(final String servletName, final String className) {
		return getWrappedScope().addServlet(servletName, className);
	}

	/*
	 * 
	 */
	public ServletRegistration.Dynamic addServlet(final String servletName, final Servlet servlet) {
		return getWrappedScope().addServlet(servletName, servlet);
	}

	/*
	 * 
	 */
	public ServletRegistration.Dynamic addServlet(final String servletName,
			final Class<? extends Servlet> servletClass) {
		return getWrappedScope().addServlet(servletName, servletClass);
	}

	/*
	 * 
	 */
	public <T extends Servlet> T createServlet(final Class<T> clazz) throws ServletException {
		return getWrappedScope().createServlet(clazz);
	}

	/*
	 * 
	 */
	public ServletRegistration getServletRegistration(final String servletName) {
		return getWrappedScope().getServletRegistration(servletName);
	}

	/*
	 * 
	 */
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return getWrappedScope().getServletRegistrations();
	}

	/*
	 * 
	 */
	public FilterRegistration.Dynamic addFilter(final String filterName, final String className) {
		return getWrappedScope().addFilter(filterName, className);
	}

	/*
	 * 
	 */
	public FilterRegistration.Dynamic addFilter(final String filterName, final Filter filter) {
		return getWrappedScope().addFilter(filterName, filter);
	}

	/*
	 * 
	 */
	public FilterRegistration.Dynamic addFilter(final String filterName, final Class<? extends Filter> filterClass) {
		return getWrappedScope().addFilter(filterName, filterClass);
	}

	/*
	 * 
	 */
	public <T extends Filter> T createFilter(final Class<T> clazz) throws ServletException {
		return getWrappedScope().createFilter(clazz);
	}

	/*
	 * 
	 */
	public FilterRegistration getFilterRegistration(final String filterName) {
		return getWrappedScope().getFilterRegistration(filterName);
	}

	/*
	 * 
	 */
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return getWrappedScope().getFilterRegistrations();
	}

	/*
	 * 
	 */
	public SessionCookieConfig getSessionCookieConfig() {
		return getWrappedScope().getSessionCookieConfig();
	}

	/*
	 * 
	 */
	public void setSessionTrackingModes(final Set<SessionTrackingMode> sessionTrackingModes) {
		getWrappedScope().setSessionTrackingModes(sessionTrackingModes);
	}

	/*
	 * 
	 */
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return getWrappedScope().getDefaultSessionTrackingModes();
	}

	/*
	 * 
	 */
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return getWrappedScope().getEffectiveSessionTrackingModes();
	}

	/*
	 * 
	 */
	public void addListener(final String className) {
		getWrappedScope().addListener(className);
	}

	/*
	 * 
	 */
	public <T extends EventListener> void addListener(final T t) {
		getWrappedScope().addListener(t);
	}

	/*
	 * 
	 */
	public void addListener(final Class<? extends EventListener> listenerClass) {
		getWrappedScope().addListener(listenerClass);
	}

	/*
	 * 
	 */
	public <T extends EventListener> T createListener(final Class<T> clazz) throws ServletException {
		return getWrappedScope().createListener(clazz);
	}

	/*
	 * 
	 */
	public JspConfigDescriptor getJspConfigDescriptor() {
		return getWrappedScope().getJspConfigDescriptor();
	}

	/*
	 * 
	 */
	public ClassLoader getClassLoader() {
		return getWrappedScope().getClassLoader();
	}

	/*
	 * 
	 */
	public void declareRoles(final String... roleNames) {
		getWrappedScope().declareRoles(roleNames);
	}

	/*
	 * 
	 */
	public String getVirtualServerName() {
		return getWrappedScope().getVirtualServerName();
	}

	/*
	 * 
	 */
	public ServletRegistration.Dynamic addJspFile(final String servletName, final String jspFile) {
		return getWrappedScope().addJspFile(servletName, jspFile);
	}

	/*
	 * 
	 */
	public int getSessionTimeout() {
		return getWrappedScope().getSessionTimeout();
	}

	/*
	 * 
	 */
	public void setSessionTimeout(final int sessionTimeout) {
		getWrappedScope().setSessionTimeout(sessionTimeout);
	}

	/*
	 * 
	 */
	public String getRequestCharacterEncoding() {
		return getWrappedScope().getRequestCharacterEncoding();
	}

	/*
	 * 
	 */
	public void setRequestCharacterEncoding(final String encoding) {
		getWrappedScope().setRequestCharacterEncoding(encoding);
	}

	/*
	 * 
	 */
	public String getResponseCharacterEncoding() {
		return getWrappedScope().getResponseCharacterEncoding();
	}

	/*
	 * 
	 */
	public void setResponseCharacterEncoding(final String encoding) {
		getWrappedScope().setResponseCharacterEncoding(encoding);
	}

}
