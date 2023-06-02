package com.warework.core.scope;

import java.util.Enumeration;

import javax.servlet.http.HttpSessionContext;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
@SuppressWarnings("deprecation")
public abstract class AbstractHttpSessionScope extends AbstractJavaxContext.AbstractHttpSessionScope {

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
	AbstractHttpSessionScope(final Scope config) throws ScopeException {
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
	public long getCreationTime() {
		return getWrappedScope().getCreationTime();
	}

	/*
	 * 
	 */
	public String getId() {
		return getWrappedScope().getId();
	}

	/*
	 * 
	 */
	public long getLastAccessedTime() {
		return getWrappedScope().getLastAccessedTime();
	}

	/*
	 * 
	 */
	public void setMaxInactiveInterval(final int interval) {
		getWrappedScope().setMaxInactiveInterval(interval);
	}

	/*
	 * 
	 */
	public int getMaxInactiveInterval() {
		return getWrappedScope().getMaxInactiveInterval();
	}

	/**
	 * @deprecated As of Version 2.1, this method is deprecated and has no
	 *             replacement. It will be removed in a future version of the Java
	 *             Servlet API.<br>
	 *             <br>
	 */
	public HttpSessionContext getSessionContext() {
		return getWrappedScope().getSessionContext();
	}

	/**
	 *
	 * @deprecated As of Version 2.2, this method is replaced by
	 *             {@link #getAttribute}.<br>
	 *             <br>
	 * @param name a string specifying the name of the object.<br>
	 *             <br>
	 * @return the object with the specified name.<br>
	 *         <br>
	 * @exception IllegalStateException if this method is called on an invalidated
	 *                                  session.
	 */
	public Object getValue(final String name) {
		return getWrappedScope().getValue(name);
	}

	/**
	 *
	 * @deprecated As of Version 2.2, this method is replaced by
	 *             {@link #getAttributeNames}.<br>
	 *             <br>
	 *
	 * @return an array of <code>String</code> objects specifying the names of all
	 *         the objects bound to this session.<br>
	 *         <br>
	 *
	 * @exception IllegalStateException if this method is called on an invalidated
	 *                                  session.
	 */
	public String[] getValueNames() {
		return getWrappedScope().getValueNames();
	}

	/**
	 *
	 * @deprecated As of Version 2.2, this method is replaced by
	 *             {@link #setAttribute}.<br>
	 *             <br>
	 *
	 * @param name  the name to which the object is bound; cannot be null.<br>
	 *              <br>
	 *
	 * @param value the object to be bound; cannot be null.<br>
	 *              <br>
	 *
	 * @exception IllegalStateException if this method is called on an invalidated
	 *                                  session.
	 *
	 */
	public void putValue(final String name, final Object value) {
		getWrappedScope().putValue(name, value);
	}

	/**
	 *
	 * @deprecated As of Version 2.2, this method is replaced by
	 *             {@link #removeAttribute}.<br>
	 *             <br>
	 *
	 * @param name the name of the object to remove from this session.<br>
	 *             <br>
	 *
	 * @exception IllegalStateException if this method is called on an invalidated
	 *                                  session.
	 */
	public void removeValue(final String name) {
		getWrappedScope().removeValue(name);
	}

	/*
	 * 
	 */
	public void invalidate() {
		getWrappedScope().invalidate();
	}

	/*
	 * 
	 */
	public boolean isNew() {
		return getWrappedScope().isNew();
	}

}
