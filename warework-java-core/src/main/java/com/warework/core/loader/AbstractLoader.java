package com.warework.core.loader;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Loader. Loaders typically are
 * responsible for loading configuration files and transforming them into Java
 * Objects.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractLoader implements LoaderFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// KNOWN CONTEXT CLASSES

	/**
	 * Class name for the Android context class.
	 */
	protected static final String CONTEXT_CLASS_ANDROID_CONTEXT = ScopeL1Constants.CONTEXT_CLASS_ANDROID_CONTEXT;

	/**
	 * Class name for the Java Enterprise Edition context class.
	 */
	protected static final String CONTEXT_CLASS_SERVLET_CONTEXT = ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope where this service belongs to.
	private ScopeFacade scope;

	// Initialization parameters for this Loader.
	private Map<String, Object> parameters;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads an object.
	 * 
	 * @return Object loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected abstract Object load() throws LoaderException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads an object.
	 * 
	 * @param scope      Scope where the Loader belongs to.<br>
	 *                   <br>
	 * @param loaderType Class that implements the
	 *                   <code>com.warework.loader.LoaderFacade</code>
	 *                   interface.<br>
	 *                   <br>
	 * @param parameters Initialization parameters (as string-object pairs) for this
	 *                   Loader.<br>
	 *                   <br>
	 * @return Object loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	public static Object load(final ScopeFacade scope, final Class<? extends LoaderFacade> loaderType,
			final Map<String, Object> parameters) throws LoaderException {

		// Create an instance of the configuration Loader.
		LoaderFacade loader = null;
		try {
			loader = (LoaderFacade) loaderType.newInstance();
		} catch (final Exception e) {
			throw new LoaderException(scope,
					"WAREWORK cannot create a new instance of the Loader because given class is null or it cannot be instantiated.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Load the configuration.
		return loader.load(scope, parameters);

	}

	/**
	 * Loads an object.
	 * 
	 * @param scope      Scope where this Loader belongs to.<br>
	 *                   <br>
	 * @param parameters Initialization parameters (as string-object pairs) for this
	 *                   Loader.<br>
	 *                   <br>
	 * @return Object loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	public Object load(final ScopeFacade scope, final Map<String, Object> parameters) throws LoaderException {

		// Set the scope.
		this.scope = scope;

		// Set the initialization parameters.
		this.parameters = parameters;

		// Load the object.
		return load();

	}

	/**
	 * Gets the name of the scope.
	 * 
	 * @return Name of the scope.<br>
	 *         <br>
	 */
	public String toString() {
		return "Loader for scope '" + scope.getName() + StringL1Helper.CHARACTER_SINGLE_QUOTE;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the scope where this Loader belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or null if no one exist.<br>
	 *         <br>
	 */
	protected final Enumeration<String> getInitParameterNames() {
		return (parameters == null) ? null : DataStructureL1Helper.toEnumeration(parameters.keySet());
	}

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	protected final Object getInitParameter(final String name) {
		return (parameters == null) ? null : parameters.get(name);
	}

}
