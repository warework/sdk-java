package com.warework.core.service;

import com.warework.core.scope.ScopeL1Constants;

/**
 * Constants for Services.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class ServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies a loader implementation class
	 * used to retrieve the configuration of the Service.
	 */
	public static final String PARAMETER_CONFIG_CLASS = ScopeL1Constants.PARAMETER_CONFIG_CLASS;

	/**
	 * Initialization parameter that specifies where to load a configuration
	 * resource with a <code>java.io.InputStream</code>,
	 * <code>java.net.URL</code> or string object. If this parameter references
	 * a string object, then the Framework performs one of these actions (just
	 * one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Provider, Service,
	 * Connector or loader configuration and, if it exists, the Framework
	 * extracts the corresponding class/object and uses it to get the resource
	 * (for example, by calling the
	 * <code>ObjectType.class.getResource(name)</code> method). (b) tries to
	 * create a <code>java.net.URL</code> object from given string. (c) searchs
	 * for the <code>PARAMETER_CONTEXT_LOADER</code> parameter in the scope
	 * where the Provider, Service, Connector or loader exists, and extracts the
	 * corresponding class to finally retrieve the resource with it. (d) return
	 * <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = ScopeL1Constants.PARAMETER_CONFIG_TARGET;

	/**
	 * Initialization parameter that specifies the class that should be used to
	 * load resources. The value of this parameter usually is the name of a
	 * class that exists in your project, so the Warework Framework can read
	 * resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ServiceConstants() {

	}

}
