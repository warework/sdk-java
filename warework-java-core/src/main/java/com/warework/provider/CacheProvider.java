package com.warework.provider;

import java.util.Enumeration;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * This Provider returns instances of existing objects. It works like an objects
 * cache: you just provide a set of objects that you want to get later on (that
 * is, you get the same object instances that you provide when the Provider is
 * configured). <br>
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
 * parameters.put("object-1", object1);<br> 
 * parameters.put("object-2", object2);<br>
 * </code> <br>
 * Once it is configured, you can create and register the Cache Provider in a
 * Scope as follows:<br>
 * <br>
 * <code>
 * // Create the Provider.<br> 
 * scope.createProvider("cache-provider", CacheProvider.class, parameters);<br>
 * </code> <br>
 * <strong>Retrieve objects from a Cache Provider</strong><br>
 * <br>
 * At this point the Cache Provider is running and we can request objects from
 * it. To do so, we just need to use any given name at configuration time.<br>
 * <br>
 * <code>
 * // Get an object.<br> 
 * Object object1 = scope.getObject("cache-provider", "object-1");<br>
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
public final class CacheProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "cache"
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
	 * Gets an instance of an object.
	 * 
	 * @param name
	 *            Name of the initialization parameter that holds the instance
	 *            of the object.<br>
	 * <br>
	 * @return Object.<br>
	 * <br>
	 */
	protected Object getObject(String name) {
		return getInitParameter(name);
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
		return getInitParameterNames();
	}

}
