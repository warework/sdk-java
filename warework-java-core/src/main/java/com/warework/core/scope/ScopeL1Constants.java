package com.warework.core.scope;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ReflectionL1Helper;

/**
 * Constants for Scopes.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class ScopeL1Constants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// INITIALIZATION PARAMETERS

	/**
	 * Initialization parameter that specifies a Loader implementation class used to
	 * retrieve the configuration of the Scope.
	 */
	public static final String PARAMETER_CONFIG_CLASS = "config-class";

	/**
	 * Initialization parameter that specifies where to load resources. If this
	 * parameter references a string object, then the Framework performs one of
	 * these actions (just one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Provider, Service,
	 * Connector or Loader configuration and, if it exists, the Framework extracts
	 * the corresponding class/object and uses it to get the resource. (b) searchs
	 * for the <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where
	 * the Provider, Service, Connector or Loader exists, and extracts the
	 * corresponding class to finally retrieve the resource with it. (c) return
	 * <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = "config-target";

	/**
	 * Initialization parameter that specifies the class or object that should be
	 * used to load resources. The value of this parameter usually is the name of a
	 * class or an object that exists in your project, so the Warework Framework can
	 * read resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = "context-loader";

	/**
	 * Initialization parameter that specifies a Scope that is wrapped with another
	 * Scope. The Scope that acts as a wrapper is the main Scope and it may delegate
	 * certain operations to the wrapped Scope.
	 */
	public static final String PARAMETER_WRAPPED_SCOPE = "wrapped-scope";

	/**
	 * Initialization parameter that specifies if the parent's initialization
	 * parameters of a Scope to create have to be extended. Accepted values for this
	 * parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects). If
	 * this parameter is not specified then it is by default <code>true</code>.
	 */
	public static final String PARAMETER_EXTEND_PARENT_INIT_PARAMETERS = "extend-parent-init-parameters";

	/**
	 * Initialization parameter that specifies if the domain's initialization
	 * parameters of a Scope to create have to be imported. Accepted values for this
	 * parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects). If
	 * this parameter is not specified then it is <code>false</code>.
	 */
	public static final String PARAMETER_IMPORT_DOMAIN_INIT_PARAMETERS = "import-domain-init-parameters";

	/**
	 * Initialization parameter that specifies the timeout (in milliseconds) for a
	 * Scope.
	 */
	public static final String PARAMETER_SCOPE_TIMEOUT = "scope-timeout";

	/**
	 * Initialization parameter that specifies to create a synchronized Scope for a
	 * multi-threaded environment.
	 */
	public static final String PARAMETER_SYNCHRONIZED_SCOPE = "synchronized-scope";

	/**
	 * Initialization parameter that specifies the implementation class used to
	 * handle the events when a Scope is created.
	 */
	public static final String PARAMETER_FACTORY_EVENT_CLASS = "factory-event-class";

	/**
	 * Initialization parameter that specifies the user name for the Scope.
	 */
	public static final String PARAMETER_USER = "user";

	/**
	 * Initialization parameter that specifies the password of the user for the
	 * Scope.
	 */
	public static final String PARAMETER_PASSWORD = "password";

	/**
	 * Initialization parameter that specifies that Services and Providers should
	 * not be created on start up. Instead, they will be created on request time
	 * when you invoke <code>getService()</code> or <code>getObject()</code>
	 * methods. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then the value for it is <code>true</code>.
	 */
	public static final String PARAMETER_LAZY_LOAD = "lazy-load";

	// KNOWN CONTEXT CLASSES AND METHODS

	/**
	 * Class name for the Android context class.
	 */
	public static final String CONTEXT_CLASS_ANDROID_CONTEXT = "android.content.Context";

	/**
	 * Class name for the Java Enterprise Edition Sun/Oracle context class.
	 */
	public static final String CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX = "javax.servlet.ServletContext";

	/**
	 * Class name for the Java Enterprise Edition Jakarta context class.
	 */
	public static final String CONTEXT_CLASS_SERVLET_CONTEXT_JAKARTA = "jakarta.servlet.ServletContext";

	/**
	 * Method to get an instance of <code>android.content.res.AssetManager</code>.
	 */
	public static final String CONTEXT_METHOD_GET_ASSETS = "getAssets";

	/**
	 * Method to get the stream for the XML file in Android's Context class.
	 */
	public static final String CONTEXT_METHOD_OPEN = "open";

	/**
	 * Method to list the files from the Android context class.
	 */
	public static final String CONTEXT_METHOD_LIST = "list";

	/**
	 * Method to get the URL that points to the resource in
	 * <code>ServlerContext</code> class.
	 */
	public static final String CONTEXT_METHOD_GET_RESOURCE = "getResource";

	/**
	 * Method to get the base directory where files exist from the Servlet context
	 * class.
	 */
	public static final String CONTEXT_METHOD_GET_REAL_PATH = "getRealPath";

	/**
	 * Method to list resources from a directory in a Servlet context class.
	 */
	public static final String CONTEXT_METHOD_GET_RESOURCE_PATHS = "getResourcePaths";

	// PACKAGES

	/**
	 * Package name for Warework.
	 */
	public static final String PACKAGE_WAREWORK = "com" + ReflectionL1Helper.PACKAGE_SEPARATOR
			+ CommonValueL1Constants.STRING_WAREWORK;

	/**
	 * Package name for Warework CORE classes.
	 */
	public static final String PACKAGE_CORE = PACKAGE_WAREWORK + ReflectionL1Helper.PACKAGE_SEPARATOR + "core";

	/**
	 * Package name for Warework Loader classes.
	 */
	public static final String PACKAGE_LOADER = PACKAGE_WAREWORK + ReflectionL1Helper.PACKAGE_SEPARATOR + "loader";

	/**
	 * Package name for Warework artifacts.
	 */
	public static final String PACKAGE_ARTIFACT = PACKAGE_WAREWORK + ReflectionL1Helper.PACKAGE_SEPARATOR + "lib";

	/**
	 * Package name for Warework Providers.
	 */
	public static final String PACKAGE_PROVIDER = PACKAGE_WAREWORK + ReflectionL1Helper.PACKAGE_SEPARATOR
			+ CommonValueL1Constants.STRING_PROVIDER;

	/**
	 * Package name for Warework Services.
	 */
	public static final String PACKAGE_SERVICE = PACKAGE_WAREWORK + ReflectionL1Helper.PACKAGE_SEPARATOR
			+ CommonValueL1Constants.STRING_SERVICE;

	/**
	 * Package name for Warework Clients.
	 */
	public static final String PACKAGE_CLIENTS = CommonValueL1Constants.STRING_CLIENT;

	/**
	 * Package name for Warework Connectors.
	 */
	public static final String PACKAGE_CONNECTORS = CommonValueL1Constants.STRING_CONNECTOR;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ScopeL1Constants() {
		// DO NOTHING.
	}

}
