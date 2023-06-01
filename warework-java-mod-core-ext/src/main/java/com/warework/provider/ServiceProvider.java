package com.warework.provider;

import java.util.Enumeration;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * This Provider retrieves objects instances from Services and it is useful when
 * you want a Provider to invoke a method in a Service that returns something.
 * The way it works is simple; just specify which Service to use and which
 * method of the Service to call, then the Provider executes this method and the
 * object that it returns is the object that you will get from this Provider.<br>
 * <br>
 * <b>Configure and create a Service Provider</b><br>
 * <br>
 * To configure this Provider you need to set at least two parameters, both of
 * them located in the <code>ServiceProvider</code> class, at
 * <code>com.warework.provider</code> package:<br>
 * <br>
 * <ul>
 * <li><code>PARAMETER_SERVICE_NAME</code>: Specifies the name of the Service
 * where to retrieve the objects instances.</li>
 * <li><code>PARAMETER_GET_INSTANCE_METHOD</code>: Specifies the name of the
 * method that retrieves one instance of an object in the Service. The method to
 * invoke in the Service must accept one <code>String</code> argument.</li>
 * </ul>
 * <br>
 * If we want to retrieve objects from a Service named
 * <code>sample-service</code>, we can write something like this:<br>
 * <br>
 * <code>
 * // Create the configuration of the provider. <br>
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider.<br> 
 * parameters.put(ServiceProvider.PARAMETER_SERVICE_NAME, "sample-service");<br> 
 * parameters.put(ServiceProvider.PARAMETER_GET_INSTANCE_METHOD, "get"); <br>
 * </code> <br>
 * This Provider will look for a Service named <code>sample-service</code> and
 * then invoke method <code>get(String)</code> on it. The value for the String
 * argument is the name you give to retrieve the object in the Provider. This is
 * reviewed in the next section.<br>
 * <br>
 * Once it is configured, you can create and register the Service Provider in a
 * Scope as follows:<br>
 * <br>
 * <code>
 * // Create the Provider. <br>
 * scope.createProvider("service-provider", ServiceProvider.class, parameters);<br> 
 * </code> <br>
 * There is a third parameter defined with the constant
 * <code>ServiceProvider.PARAMETER_LIST_NAMES_METHOD</code>. It references a
 * method in the Service that retrieves the names of the objects managed by the
 * Service. This parameter is optional but if you want to load all objects from
 * the Provider (with <code>PARAMETER_CREATE_OBJECTS</code>) on startup then you
 * have to define it. The method in the Service referenced with this parameter
 * must be without arguments and it have to return an <code>Enumeration</code>
 * of <code>String</code> values (the name of each object to retrieve). Check
 * this out with the following example:<br>
 * <br>
 * <code>
 * // Create the configuration of the provider.<br> 
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider.<br> 
 * parameters.put(ServiceProvider.PARAMETER_SERVICE_NAME, "sample-service");<br> 
 * parameters.put(ServiceProvider.PARAMETER_GET_INSTANCE_METHOD, "get");<br>
 * parameters.put(ServiceProvider.PARAMETER_LIST_NAMES_METHOD, "list"); <br>
 * parameters.put(ServiceProvider.PARAMETER_CREATE_OBJECTS, Boolean.TRUE);<br> 
 * <br>
 * // Create the Provider.<br> 
 * scope.createProvider("service-provider", ServiceProvider.class, parameters);<br>
 * </code> <br>
 * When the Provider is created, it gets the Sample Service and executes the
 * <code>list()</code> method to list the names. Each of these names is used to
 * register an Object Reference in the Scope so every object that the Service
 * can retrieve is accessible with the <code>scope.getObject(String)</code>
 * method.<br>
 * <br>
 * <b>Retrieve objects from a Service Provider</b><br>
 * <br>
 * At this point the Service Provider is running and we can request objects from
 * it. To do so, we just need to use any object name registered by the Service:<br>
 * <br>
 * <code>
 * // Get an instance of an object that exist in the Sample Service.<br> 
 * Object object = scope.getObject("service-provider", "service-object");<br>
 * </code> <br>
 * This line of code performs the following actions:<br>
 * <br>
 * <ol>
 * <li>The Provider retrieves the Sample Service because the value of
 * <code>PARAMETER_SERVICE_NAME</code> is <code>sample-service</code>.</li>
 * <li>
 * The Provider executes the get method in the Sample Service with the value
 * <code>service-object</code> for the String argument; something like:<br>
 * <br>
 * <code>
 * service.get("service-object")<br>
 * </code> <br>
 * </li>
 * <li>The Provider returns the object from the get method of the Sample
 * Service.</li>
 * </ol>
 * <br>
 * If you defined <code>PARAMETER_LIST_NAMES_METHOD</code> and
 * <code>PARAMETER_CreateObjects</code> in the configuration of the Provider
 * then this object can be retrieved like this:<br>
 * <br>
 * <code>
 * // Get an instance of an object that exist in the Sample Service.<br> 
 * Object object = scope.getObject("service-object");<br>
 * </code> <br>
 * <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServiceProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "service"
			+ StringL2Helper.CHARACTER_HYPHEN
			+ CommonValueL2Constants.STRING_PROVIDER;

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the name of the Service where to
	 * retrieve the objects instances. This parameter is mandatory in order to
	 * make this Provider work and it must be a string object with the name of
	 * an existing Service.
	 */
	public static final String PARAMETER_SERVICE_NAME = "service-name";

	/**
	 * Initialization parameter that specifies the name of the method that
	 * retrieves one instance of an object in the Service. The method to invoke
	 * in the Service must accept just one string argument. This parameter is
	 * mandatory in order to make this Provider work and it must be a string
	 * object with the name of an existing method in the Service.
	 */
	public static final String PARAMETER_GET_INSTANCE_METHOD = "get-instance-method";

	/**
	 * Initialization parameter that specifies the name of the method that lists
	 * the names of the objects that the Service can retrieve. The method to
	 * invoke in the Service must not accept any argument. This parameter must
	 * be a string object with the name of an existing method in the Service.
	 */
	public static final String PARAMETER_LIST_NAMES_METHOD = "list-names-method";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the Service.
	private String serviceName;

	// Name of the method to invoke in the Service.
	private String methodName;

	// Name of the method to invoke in the Service that obtains the names of
	// each object.
	private String listNamesMethod;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		serviceName = null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException
	 *             If there is an error when trying to initialize the Provider.<br>
	 * <br>
	 */
	protected void initialize() throws ProviderException {

		// Get the name of the Service.
		Object nameOfService = getInitParameter(PARAMETER_SERVICE_NAME);

		// Validate and store the name of the Service.
		if ((nameOfService != null) && (nameOfService instanceof String)) {
			serviceName = (String) nameOfService;
		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because given parameter '"
							+ PARAMETER_SERVICE_NAME
							+ "' is null or it is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Service.
		Object nameOfGetMethod = getInitParameter(PARAMETER_GET_INSTANCE_METHOD);

		// Validate and store the name of the Service.
		if ((nameOfGetMethod != null) && (nameOfGetMethod instanceof String)) {
			methodName = (String) nameOfGetMethod;
		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because given parameter '"
							+ PARAMETER_GET_INSTANCE_METHOD
							+ "' is null or it is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Service.
		Object nameOfListMethod = getInitParameter(PARAMETER_LIST_NAMES_METHOD);

		// Validate and store the name of the Service.
		if (nameOfListMethod != null) {
			if (nameOfListMethod instanceof String) {
				listNamesMethod = (String) nameOfListMethod;
			} else {
				throw new ProviderException(getScopeFacade(),
						"WAREWORK cannot create Provider '" + getName()
								+ "' because given parameter '"
								+ PARAMETER_LIST_NAMES_METHOD
								+ "' is not a string.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * This method does not perform any operation.
	 */
	protected void connect() {
	}

	/**
	 * This method does not perform any operation.
	 */
	protected void disconnect() {
	}

	/**
	 * Retrieves an instance of an object from a Service. This method invokes
	 * the method specified with the parameter 'get-instance-method' in the
	 * Service specified with the parameter 'service-name'. The method to invoke
	 * in the Service must accept just one string argument. This argument is
	 * used as the name of the object to retrieve from the Service.
	 * 
	 * @param name
	 *            Name of the object to retrieve in the Service.<br>
	 * <br>
	 * @return Object or <code>null</code> if Service does not exists or it is
	 *         not possible to retrieve the object from the Service.<br>
	 * <br>
	 */
	protected Object getObject(String name) {

		// Validate that Service exists.
		if (getScopeFacade().getService(serviceName) == null) {

			// Log a Warework message.
			getScopeFacade()
					.log("WAREWORK cannot retrieve object '"
							+ name
							+ "' from Provider '"
							+ getName()
							+ "' (which tries to retrieve the object from Service '"
							+ serviceName + "').",
							LogServiceConstants.LOG_LEVEL_WARN);

			// Nothing to return.
			return null;

		}

		// Invoke the method in the Service.
		try {
			return ReflectionL2Helper.invokeMethod(methodName, getScopeFacade()
					.getService(serviceName), new Class[] { String.class },
					new Object[] { name });
		} catch (Exception e) {

			// Log the exception.
			if ((e.getMessage() != null) && (!e.getMessage().equals(""))) {

				// Log a Warework message.
				getScopeFacade().log(
						"WAREWORK cannot retrieve an object from Service '"
								+ serviceName + "' in Provider '" + getName()
								+ "' due to the following error: ",
						LogServiceConstants.LOG_LEVEL_WARN);

				// Log the exception message.
				getScopeFacade().log(e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);

			} else {
				getScopeFacade().log(
						"WAREWORK cannot retrieve an object from Service '"
								+ serviceName + "' in Provider '" + getName()
								+ "' because a '" + e.getClass().getName()
								+ "' exception is thrown.",
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Nothing to return.
			return null;

		}
	}

	/**
	 * Validates if the connection of the Provider is closed.
	 * 
	 * @return <code>false</code>.<br>
	 * <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Gets the names of the objects of this Provider.
	 * 
	 * @return Names of the objects of this Provider.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	protected Enumeration<String> getObjectNames() throws ProviderException {

		// Validate that Service exists.
		if (getScopeFacade().getService(serviceName) == null) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot retrieve the names of the objects in Provider '"
							+ getName() + "' because Service '" + serviceName
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the names of the objects.
		try {
			return (Enumeration<String>) ReflectionL2Helper.invokeMethod(
					listNamesMethod, getScopeFacade().getService(serviceName));
		} catch (Exception e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot retrieve the names of the objects in Provider '"
							+ getName() + "' because method '" + methodName
							+ "()' is not found in Service '" + serviceName
							+ "'.", e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
