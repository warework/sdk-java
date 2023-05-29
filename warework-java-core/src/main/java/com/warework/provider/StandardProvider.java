package com.warework.provider;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * This Provider creates instances of objects that have a default constructor
 * (without parameters).<br>
 * <br>
 * Warework Standard Provider is responsible for creating objects instances from
 * a predefined list of classes. Each of those classes is assigned a name at
 * configuration time and, later on, new instances of those classes can be
 * retrieved by name. Objects instances are not singleton so you will get a new
 * one every time you request an object.<br>
 * <br>
 * <strong>Configure and create a Standard Provider</strong><br>
 * <br>
 * To configure this Provider you just need to give a name to each class in a
 * <code>java.util.Map</code>.<br>
 * <br>
 * <code>
 * // Create the configuration of the provider.<br> 
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider.<br> 
 * parameters.put("map", Hashtable.class);<br> 
 * parameters.put("list", Vector.class);<br>
 * </code> <br>
 * It is very important to know that every indicated class must have a default
 * constructor (without parameters). Also that you can specify each class with a
 * string that represents the name of the class.<br>
 * <br>
 * <code>
 * // Configure the Provider.<br> 
 * parameters.put("map", "java.util.Hashtable");<br>
 * parameters.put("list", "java.util.Vector");<br>
 * </code> <br>
 * Once it is configured, you can create and register the Standard Provider in a
 * Scope as follows:<br>
 * <br>
 * <code>
 * // Create the Provider.<br> 
 * scope.createProvider("standard-provider", StandardProvider.class, parameters);<br>
 * </code> <br>
 * <strong>Retrieve objects from a Standard Provider</strong><br>
 * <br>
 * At this point the Standard Provider is running and we can request objects
 * from it. To do so, we just need to use any given name at configuration time.<br>
 * <br>
 * <code>
 * // Create a new list.<br> 
 * Vector list = (Vector) scope.getObject("standard-provider", "list");<br>
 * </code> <br>
 * This line of code creates a new <code>java.util.Vector</code> every time it
 * is invoked. Based on the previous example, to get a new
 * <code>java.util.Hashtable</code> simply change the name of the object to
 * create:<br>
 * <br>
 * <code>
 * // Create a new map. <br>
 * Map map = (Map) scope.getObject("standard-provider", "map");<br>
 * </code> <br>
 * <br>
 * <strong>Minimum prerequisites to run this Provider:</strong><br>
 * <br>
 * <ul>
 * <li><strong>Runtime:</strong> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class StandardProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "standard"
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

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
	 * Creates an instances of an object. The object must have a default
	 * constructor (without parameters) or this method will return
	 * <code>null</code>.
	 * 
	 * @param name
	 *            Name of the initialization parameter that maps the class used
	 *            to create the instance.<br>
	 * <br>
	 * @return Object.<br>
	 * <br>
	 */
	protected Object getObject(String name) {

		// Get the parameter.
		Object objectType = getInitParameter(name);

		// Return an instance of the object.
		if (objectType != null) {
			if (objectType instanceof String) {
				try {

					// Get the class of the object.
					Class<?> clazz = Class.forName((String) objectType);

					// Create an instance of the object.
					return clazz.newInstance();

				} catch (Exception e) {
					getScopeFacade()
							.log("WAREWORK cannot retrieve object '"
									+ name
									+ "' in Provider '"
									+ getName()
									+ "' because the class it specifies cannot be instantiated.",
									LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (objectType instanceof Class) {
				try {

					// Get the class of the object.
					Class<?> clazz = (Class<?>) objectType;

					// Create an instance of the object.
					return clazz.newInstance();

				} catch (Exception e) {
					getScopeFacade()
							.log("WAREWORK cannot retrieve object '"
									+ name
									+ "' in Provider '"
									+ getName()
									+ "' because the class it specifies cannot be instantiated.",
									LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				getScopeFacade()
						.log("WAREWORK cannot retrieve object '"
								+ name
								+ "' in Provider '"
								+ getName()
								+ "' because object does not specifies a class.",
								LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			getScopeFacade().log(
					"WAREWORK cannot retrieve object '" + name
							+ "' in Provider '" + getName()
							+ "' because object does not exists.",
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Nothing to return at this point.
		return null;

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

		// Get the names of the initialization parameters.
		Enumeration<String> parameterNames = getInitParameterNames();

		// Filter the parameters.
		if (parameterNames != null) {

			// Create a vector where to copy the names.
			List<String> names = new ArrayList<String>();

			// Copy each name.
			while (parameterNames.hasMoreElements()) {

				// Get the name of a parameter.
				String parameterName = (String) parameterNames.nextElement();

				// Validate the parameter name.
				if ((!parameterName.equals(PARAMETER_CREATE_OBJECTS))
						&& (getInitParameter(parameterName) instanceof String)) {
					names.add(parameterName);
				}

			}

			// Return the names.
			if (names.size() > 0) {
				return DataStructureL1Helper.toEnumeration(names);
			}

		}

		// At this point, nothing to return.
		return null;

	}

}
