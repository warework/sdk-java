package com.warework.provider;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Warework Singleton Provider implements a factory that creates unique
 * instances of objects in a specific scope. The first time you request an
 * object for a specific class, a new instance is created, stored (inside the
 * Provider) and returned to the user. If you request the same object later on,
 * it will return the stored instance, so, that is why every object of the same
 * class will be the same.<br>
 * <br>
 * <strong>Create a Singleton Provider</strong><br>
 * <br>
 * This Provider does not require to be configured, so we just need to create
 * and register it in a scope as follows:<br>
 * <br>
 * <code>
 * // Create the Provider.<br> 
 * scope.createProvider("singleton-provider", SingletonProvider.class, null);<br>
 * </code> <br>
 * <strong>Retrieve objects from a Singleton Provider</strong><br>
 * <br>
 * At this point the Singleton Provider is running and we can request objects
 * from it. To do so, we just need to use the name of a class to retrieve its
 * instance:<br>
 * <br>
 * <code>
 * // Get an instance of a map. <br>
 * Map map1 = (Map) scope.getObject("singleton-provider", "java.util.Hashtable");<br>
 * </code> <br>
 * This line of code creates a new <code>java.util.Hashtable</code> the first
 * time it is invoked. It is very important to know that every indicated class
 * must have a default constructor (without parame.-ters). If we invoke it
 * again, we will get the same instance:<br>
 * <br>
 * <code>
 * // Get the same instance as map1.<br> 
 * Map map2 = (Map) scope.getObject("singleton-provider", "java.util.Hashtable");<br>
 * </code> <br>
 * <br>
 * <strong>Minimum prerequisites to run this Provider:</strong><br>
 * <br>
 * <ul>
 * <li><strong>Runtime:</strong> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class SingletonProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "singleton"
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Singleton instances of the objects.
	private Map<String, Object> instances;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		instances = null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does not perform any operation.
	 */
	protected void initialize() {
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
	 * Retrieves unique instances of objects. The first time you give a name of
	 * a class to this method it creates a new instance of the object but next
	 * time the instance will always be the same.
	 * 
	 * @param type
	 *            Name of the class to create the instance. This string must
	 *            represent a class which has a default constructor (without
	 *            parameters).<br>
	 * <br>
	 * @return Object.<br>
	 * <br>
	 */
	protected Object getObject(String type) {
		if ((instances != null) && (instances.containsKey(type))) {
			return instances.get(type);
		} else {

			// Create an instance of the object.
			Object object = null;
			try {

				// Get the class of the object.
				Class<?> clazz = Class.forName(type);

				// Create an instance of the object.
				object = clazz.newInstance();

			} catch (Exception e) {

				// Log the exception.
				if ((e.getMessage() != null) && (!e.getMessage().equals(""))) {

					// Log a Warework message.
					getScopeFacade().log(
							"WAREWORK cannot create an instance of object '"
									+ type + "' in Provider '" + getName()
									+ "' due to the following error: ",
							LogServiceConstants.LOG_LEVEL_WARN);

					// Log the exception message.
					getScopeFacade().log(e.getMessage(),
							LogServiceConstants.LOG_LEVEL_WARN);

				} else {
					getScopeFacade().log(
							"WAREWORK cannot create an instance of object '"
									+ type + "' in Provider '" + getName()
									+ "' because a '" + e.getClass().getName()
									+ "' exception is thrown.",
							LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Nothing to return.
				return null;

			}

			// Initialize the context of the objects.
			if (instances == null) {
				instances = new HashMap<String, Object>();
			}

			// Register the singleton instance.
			instances.put(type, object);

			// Return the object.
			return object;

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
	protected Enumeration<String> getObjectNames() {
		if (instances != null) {
			return DataStructureL1Helper.toEnumeration(instances.keySet());
		} else {
			return null;
		}
	}

}
