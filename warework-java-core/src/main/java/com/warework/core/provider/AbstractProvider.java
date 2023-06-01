package com.warework.core.provider;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Provider.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies if the objects managed by the
	 * Provider have to be bounded into the Scope automatically when the Provider is
	 * created. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then it is <code>false</code>.
	 */
	public static final String PARAMETER_CREATE_OBJECTS = "create-objects";

	/**
	 * Initialization parameter that specifies if the connection with the Provider
	 * have to be closed after each 'lookup' operation is requested. If so, the
	 * connection is opened automatically on next 'lookup' operation. Accepted
	 * values for this parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects). If
	 * this parameter is not specified then it is <code>false</code>.
	 */
	public static final String PARAMETER_DISCONNECT_ON_LOOKUP = "disconnect-on-lookup";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope where this Provider belongs to.
	private ScopeFacade scope;

	// Name of the Provider.
	private String name;

	// Initialization parameters (as string-object pairs) for this Provider
	private Map<String, Object> parameters;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to initialize the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected abstract void initialize() throws ProviderException;

	/**
	 * Opens a connection with the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to connect the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected abstract void connect() throws ProviderException;

	/**
	 * Closes the connection with the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to disconnect the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected abstract void disconnect() throws ProviderException;

	/**
	 * Validates if the connection of the Provider is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected abstract boolean isClosed();

	/**
	 * Retrieves an object.
	 * 
	 * @param name Name of the object to retrieve.<br>
	 *             <br>
	 * @return Object.<br>
	 *         <br>
	 */
	protected abstract Object getObject(String name);

	/**
	 * Gets the names of the objects of this Provider.
	 * 
	 * @return Names of the objects of this Provider.<br>
	 *         <br>
	 * @throws ProviderException If there is an error when trying to retrieve the
	 *                           name of each object from the Provider.<br>
	 *                           <br>
	 */
	protected abstract Enumeration<String> getObjectNames() throws ProviderException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @param scope      Scope where this Provider belongs to.<br>
	 *                   <br>
	 * @param name       Name of the Provider.<br>
	 *                   <br>
	 * @param parameters Initialization parameters (as string-object pairs) for this
	 *                   Provider.<br>
	 *                   <br>
	 * @throws ProviderException If there is an error when trying to initialize the
	 *                           Provider.<br>
	 *                           <br>
	 */
	public void init(final ScopeFacade scope, final String name, final Map<String, Object> parameters)
			throws ProviderException {

		// Validate that Scope exists.
		if (scope == null) {
			if ((name != null) && (!name.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ProviderException(null,
						"WAREWORK cannot create Provider '" + name + "' because given Scope is null.", null, -1);
			} else {
				throw new ProviderException(null, "WAREWORK cannot create Provider because given Scope is null.", null,
						-1);
			}
		}

		// Validate the name of the Provider.
		if ((name == null) || (name.equals(CommonValueL1Constants.STRING_EMPTY))) {
			throw new ProviderException(scope, "WAREWORK cannot create the Provider in Scope '" + scope.getName()
					+ "' because given name is null or empty.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Set the Scope for this Provider.
		this.scope = scope;

		// Set the name of this Provider.
		this.name = name;

		// Set the initialization parameters.
		this.parameters = parameters;

		// Initialize the Provider.
		initialize();

		// Create every object that exist in the Provider when specified.
		if (isInitParameter(PARAMETER_CREATE_OBJECTS)) {

			// Get the names of the objects of this Provider.
			final Enumeration<String> objectNames = getObjectNames();

			// Create each object.
			if (objectNames != null) {
				while (objectNames.hasMoreElements()) {

					// Get the name of the object.
					final String objectName = objectNames.nextElement();

					// Create the object.
					scope.createObject(objectName, name, objectName);

				}
			}

		}

	}

	/**
	 * Gets the Scope where this Provider belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	public ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Gets the name of the Provider.
	 * 
	 * @return Name of the Provider.<br>
	 *         <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves an object.
	 * 
	 * @param name Name of the object to retrieve.<br>
	 *             <br>
	 * @return Object.<br>
	 *         <br>
	 */
	public Object lookup(final String name) {

		// Open a connection with the Provider if it's closed.
		if (isClosed()) {
			try {
				connect();
			} catch (final ProviderException e) {

				// Log a message.
				getScopeFacade().log(
						"WAREWORK cannot open a connection with Provider '" + getName()
								+ "' because it generated the following error: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);

				// Nothing to return.
				return null;

			}
		}

		// Retrieves an object.
		final Object object = getObject(name);

		// Disconnect the Provider.
		if (isInitParameter(PARAMETER_DISCONNECT_ON_LOOKUP)) {
			try {
				disconnect();
			} catch (final ProviderException e) {
				getScopeFacade().log(
						"WAREWORK cannot close the connection of Provider '" + getName()
								+ "' because it generated the following error: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Return the object.
		return object;

	}

	/**
	 * Terminates the execution of the Provider. This is a default implementation.
	 * Subclasses may override this method to specify how to shutdown a Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to close the
	 *                           Provider.<br>
	 *                           <br>
	 */
	public void close() throws ProviderException {
	}

	/**
	 * Gets the name of the Provider.
	 * 
	 * @return Name of the Provider.<br>
	 *         <br>
	 */
	public String toString() {
		return getName();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or null if no one exist.<br>
	 *         <br>
	 */
	protected Enumeration<String> getInitParameterNames() {
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
	protected Object getInitParameter(final String name) {
		return (parameters == null) ? null : parameters.get(name);
	}

	/**
	 * Gets the boolean value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return <code>true</code> if the parameter is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that is
	 *         equals, in any case form, to <code>true</code>. Otherwise, this
	 *         method returns <code>false</code> (for example: if it does not
	 *         exist).<br>
	 *         <br>
	 */
	protected boolean isInitParameter(final String name) {

		// Get the value of the parameter.
		final Object value = getInitParameter(name);

		// Decide the boolean value of the parameter.
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if ((value instanceof String)
					&& (((String) value).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
				return true;
			}
		}

		// At this point, default result is false.
		return false;

	}

}
