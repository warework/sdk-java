package com.warework.core.scope;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServletContextScope extends AbstractServletContextScope {

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
	ServletContextScope(final Scope config) throws ScopeException {
		super(config);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * SERVLET API 3.1
	 */

	/*
	 * 
	 */
	public String getVirtualServerName() {
		return getWrappedScope().getVirtualServerName();
	}

	// ///////////////////////////////////////////////////////////////////

	/*
	 * SERVLET API 3.0
	 */

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
	public Dynamic addServlet(final String servletName, final String className) {
		return getWrappedScope().addServlet(servletName, className);
	}

	/*
	 * 
	 */
	public Dynamic addServlet(final String servletName, final Servlet servlet) {
		return getWrappedScope().addServlet(servletName, servlet);
	}

	/*
	 * 
	 */
	public Dynamic addServlet(final String servletName, final Class<? extends Servlet> servletClass) {
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
	public javax.servlet.FilterRegistration.Dynamic addFilter(final String filterName, final String className) {
		return getWrappedScope().addFilter(filterName, className);
	}

	/*
	 * 
	 */
	public javax.servlet.FilterRegistration.Dynamic addFilter(final String filterName, final Filter filter) {
		return getWrappedScope().addFilter(filterName, filter);
	}

	/*
	 * 
	 */
	public javax.servlet.FilterRegistration.Dynamic addFilter(final String filterName,
			final Class<? extends Filter> filterClass) {
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

}
