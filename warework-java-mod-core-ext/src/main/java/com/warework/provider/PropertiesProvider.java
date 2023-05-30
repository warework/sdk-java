package com.warework.provider;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.DataStructureL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * The Properties Provider is responsible for returning properties from a
 * properties file. Properties files are mainly used in Java related
 * technologies to store the configurable parameters of an application. A
 * properties file is a simple text file that may contain multiple key-value
 * pairs (properties) like this:<br>
 * <br>
 * <code>
 * # This is a comment<br>
 * app.cache.enable=true<br>
 * app.cache.refresh=6000<br>
 * app.url.help=/help/index.html<br>
 * </code> <br>
 * To retrieve with this Provider the value of a property that exists in the
 * file you have to use the key that is associated to the value. For example, to
 * get <code>/help/index.html</code> you will need to provide the key
 * <code>app.url.help</code>. If you are new with properties files, please keep
 * in mind that:<br>
 * <br>
 * <ul>
 * <li>Keys should be unique in the same properties file.<br>
 * <br>
 * </li>
 * <li>Keys and values are String objects so this Provider always returns
 * strings.</li>
 * </ul>
 * <br>
 * <b>Configure and create a Properties Provider</b><br>
 * <br>
 * To configure this Provider you just need to give the location of a properties
 * file:<br>
 * <br>
 * <code>
 * // Create the configuration of the Provider.<br> 
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider.<br> 
 * parameters.put(PropertiesProvider.PARAMETER_CONFIG_TARGET, "/META-INF/system/data/properties/config.properties");<br> 
 * <br>
 * // Create the Provider.<br> 
 * scope.createProvider("properties-provider", PropertiesProvider.class, parameters);<br> 
 * </code> <br>
 * The above example shows how to read the <code>config.properties</code> file
 * from <code>/META-INF/system/data/properties</code> directory.<br>
 * <br>
 * The specific place where resources can be loaded for your project is
 * specified in each Warework Distribution. Typically, in a Desktop Distribution
 * the <code>/META-INF</code> directory is located in the source folder of your
 * own project. Other types of Distributions may read your resources from
 * <code>/WEB-INF</code> for example, so please review the documentation
 * associated to your Distribution to know where your resources context is.<br>
 * <br>
 * <br>
 * <b>Retrieve properties from a Properties Provider</b><br>
 * <br>
 * At this point the Properties Provider is running and we can request string
 * values from it. To do so, we just need to provide the key of a property that
 * exists in the properties file:<br>
 * <br>
 * <code>
 * // Get the string value of a key defined in the properties file.<br>
 * String cache = (String) scope.getObject("properties-provider", "app.cache.enable");<br>
 * </code> <br>
 * <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class PropertiesProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "properties" + StringL2Helper.CHARACTER_HYPHEN
			+ CommonValueL2Constants.STRING_PROVIDER;

	/**
	 * Initialization parameter that specifies where to load resources. If this
	 * parameter references a string object, then the Framework performs one of
	 * these actions (just one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Provider configuration
	 * and, if it exists, the Framework extracts the corresponding class/object and
	 * uses it to get the resource. (b) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where the
	 * Provider and extracts the corresponding class to finally retrieve the
	 * resource with it. (c) return <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = ScopeL1Constants.PARAMETER_CONFIG_TARGET;

	/**
	 * Initialization parameter that specifies the class that should be used to load
	 * resources. The value of this parameter usually is the name of a class that
	 * exists in your project, so the Warework Framework can read resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Properties file loaded.
	private Properties properties;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to initialize the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void initialize() throws ProviderException {

		// Get the stream for the properties file.
		InputStream resourceStream = null;
		try {
			resourceStream = new ResourceLoader(getScopeFacade())
					.getResource(getInitParameter(PARAMETER_CONTEXT_LOADER), getInitParameter(PARAMETER_CONFIG_TARGET));
		} catch (final LoaderException e) {
			throw new ProviderException(getScopeFacade(), "WAREWORK cannot create Provider '" + getName()
					+ "' because the following exception is thrown when trying to find the resource: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create the properties.
		properties = new Properties();
		try {

			// Load the properties file into the collection.
			properties.load(resourceStream);

			// Close the stream.
			resourceStream.close();

		} catch (final Exception e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot initialize Provider '" + getName()
							+ "' because the InputStream that points to the properties file cannot be loaded into a '"
							+ Properties.class.getName() + "' object. Check out the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * This method does not perform any operation.
	 */
	protected void connect() {
		// DO NOTHING.
	}

	/**
	 * This method does not perform any operation.
	 */
	protected void disconnect() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection of the Provider is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Gets a property from the properties file.
	 * 
	 * @param key Property key.<br>
	 *            <br>
	 * @return <code>java.lang.String</code> with the value of the property. The
	 *         method returns <code>null</code> if the property is not found.<br>
	 *         <br>
	 */
	protected Object getObject(final String key) {
		return (properties == null) ? null : properties.getProperty(key);
	}

	/**
	 * Gets the names of every property that exists in the properties file.
	 * 
	 * @return Keys from the properties file.<br>
	 *         <br>
	 */
	protected Enumeration<String> getObjectNames() {
		if (properties == null) {
			return null;
		} else {

			// Create a collection to store the keys as string values.
			final Set<String> target = new HashSet<String>();

			// Get the keys.
			final Set<Object> keys = properties.keySet();

			// Copy each key into the target collection.
			for (final Iterator<Object> iterator = keys.iterator(); iterator.hasNext();) {
				target.add((String) iterator.next());
			}

			// Return the keys.
			return DataStructureL2Helper.toEnumeration(target);

		}
	}

}
