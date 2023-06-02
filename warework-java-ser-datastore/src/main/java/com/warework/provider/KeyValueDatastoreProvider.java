package com.warework.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.ServiceException;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.log.LogServiceConstants;

/**
 * <span>Warework</span><span> Key-Value Data Store Provider allows you to
 * retrieve values associated to keys that exist in a Data Store. Every Data
 * Store which has a Key-Value View can be used by this Provider, so you can get
 * values associated to keys that exist in relational databases or properties
 * files, for example. The only restriction to use this Provider is that keys
 * must be string values in the Data Store.</span><br>
 * <br>
 * <br>
 * <b> Create a Key-Value Data Store Provider</b><br>
 * <br>
 * <span>To configure this Provider you </span><span>have to indicate the
 * following parameters:</span><br>
 * <br>
 * <ul class="t0">
 * <li><span><I>Service name</I></span><span>: this parameter represents the
 * name of the Data Store Service where to perform the operations.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><I>Data Store name</I></span><span>: this one is to identify the
 * target Data Store that exists in the Data Store Service.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><I>Data Store View name</I></span><span>: this parameter identifies
 * the specific View to use from the Data Store.</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>Suppose that we have a </span><a target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-ser-datastore-jdbc"
 * ><span>JDBC Data Store</span></a><span> in the Service and a View on top of
 * it that is an instance of the </span><span>KeyValueJdbcViewImpl</span><span>
 * class (this class is provided with the Warework JDBC Data Store). We can
 * configure this provider like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;provider.&nbsp;<br>Map&lt;String, Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_SERVICE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datastore-service&quot;);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_DATASTORE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;jdbc-datastore&quot;);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.&nbsp;<br>&nbsp;&nbsp;&nbsp;PARAMETER_DATASTORE_VIEW_NAME,&quot;key-value-view-name&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;key-value-datastore-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;KeyValueDatastoreProvider.class,&nbsp;parameters);</code>
 * <br>
 * <br>
 * <span>You can also configure this Provider with the name of the
 * View</span><span>:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;provider.&nbsp;<br>Map&lt;String, Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_SERVICE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datastore-service&quot;);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_DATASTORE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;jdbc-datastore&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;key-value-datastore-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;KeyValueDatastoreProvider.class,&nbsp;parameters);</code>
 * <br>
 * <br>
 * <span>Optionally, you can also enable cache so when a key is requested more
 * than one time, instead of retrieving it from the Data Store, you will get a
 * copy that resides on your local machine</span><span> / memory. You can
 * achieve this task by specifying the </span><span>enable-cache</span><span>
 * parameter: </span> <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;provider.&nbsp;<br>Map&lt;String, Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_SERVICE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datastore-service&quot;);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_DATASTORE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;jdbc-datastore&quot;);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.&nbsp;<br>&nbsp;&nbsp;&nbsp;PARAMETER_DATASTORE_VIEW_IMPLEMENTATION,&nbsp;KeyValueJdbcViewImpl.class);&nbsp;<br>parameters.put(KeyValueDatastoreProvider.PARAMETER_ENABLE_CACHE,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.TRUE);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;key-value-datastore-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;KeyValueDatastoreProvider.class,&nbsp;parameters);</code>
 * <br>
 * <br>
 * If you plan to configure this Provider on startup with an XML file then
 * follow this template:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;providers&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider&nbsp;name=&quot;key-value-datastore-provider&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.provider.KeyValueDatastoreProvider&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;service-name&quot;&nbsp;value=&quot;datastore-service&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;datastore-name&quot;&nbsp;value=&quot;jdbc-datastore&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;datastore-view-name&quot;&nbsp;value=&quot;key-value-view-name&nbsp;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;enable-cache&quot;&nbsp;value=&quot;false&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;provider&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;providers&gt;&nbsp;&nbsp;&nbsp;<br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * <br>
 * <b> Retrieve objects from a Key-Value Data Store Provider</b><br>
 * <br>
 * <span>At this point the </span><span>Key-Value Data Store Provider is running
 * and we can request objects from it. To do so, we just need to provide a key
 * that exists in the Data Store to retrieve the value associated to it:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;a&nbsp;property&nbsp;from&nbsp;the&nbsp;database.&nbsp;<br>String&nbsp;userName&nbsp;=&nbsp;(String)&nbsp;scope.getObject(&quot;key-value-datastore-provider&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;user.name&quot;);</code>
 * <br>
 * <br>
 * <span>This line of code searches for the value of
 * </span><span>user.name</span><span> in the JDBC Data Store using the
 * specified View. Remember that Key-Value Views can return other types of
 * result different than Strings:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;associated&nbsp;to&nbsp;a&nbsp;key&nbsp;from&nbsp;the&nbsp;database.&nbsp;<br>Date&nbsp;dateOfBirth&nbsp;=&nbsp;(Date)&nbsp;scope.getObject(&quot;key-value-datastore-provider&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;user.dateOfBirth&quot;);</code>
 * <br>
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
public final class KeyValueDatastoreProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "keyvalue-datastore"
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the name of the Data Store
	 * Service where to retrieve the instance of a Key-Value View. This
	 * parameter is mandatory in order to make this Provider work and it must be
	 * a string object with the name of an existing Data Store Service.
	 */
	public static final String PARAMETER_SERVICE_NAME = "service-name";

	/**
	 * Initialization parameter that specifies the name of the Data Store that
	 * exists in the Data Store Service and where to retrieve the instance of
	 * the Key-Value View. This parameter is mandatory in order to make this
	 * Provider work and it must be a string object with the name of an existing
	 * Data Store.
	 */
	public static final String PARAMETER_DATASTORE_NAME = "datastore-name";

	/**
	 * Initialization parameter that specifies the name of the Key-Value View in
	 * the Data Store. This parameter is mandatory if the implementation class
	 * of the View is not defined.
	 */
	public static final String PARAMETER_DATASTORE_VIEW_NAME = "datastore-view-name";

	/**
	 * Initialization parameter that specifies if keys loaded from a Data Store
	 * (that implements a Key-Value View) have to be stored in memory. This
	 * parameter is optional, by default it is <code>false</code>.
	 */
	public static final String PARAMETER_ENABLE_CACHE = "enable-cache";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Configuration of the context.
	private Map<String, Object> cache;

	// Specifies if keys have to be stored in memory
	private boolean cacheEnabled = false;

	// Name of the Data Store Service.
	private String serviceName;

	// Name of the Data Store.
	private String datastoreName;

	// Name of the View.
	private String datastoreViewName;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the provider.
	 */
	public void close() {
		cache = null;
		cacheEnabled = false;
		serviceName = null;
		datastoreName = null;
		datastoreViewName = null;
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

		// Get the name of the Data Store..
		Object datastoreName = getInitParameter(PARAMETER_DATASTORE_NAME);

		// Validate and save the name of the Data Store.
		if ((datastoreName != null) && (datastoreName instanceof String)) {
			this.datastoreName = (String) datastoreName;
		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because given parameter '"
							+ PARAMETER_DATASTORE_NAME
							+ "' is null or it is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Data Store..
		Object datastoreViewName = getInitParameter(PARAMETER_DATASTORE_VIEW_NAME);

		// Validate and save the name of the Data Store.
		if (datastoreViewName != null) {
			if ((datastoreViewName != null)
					&& (datastoreViewName instanceof String)) {
				this.datastoreViewName = (String) datastoreViewName;
			} else {
				throw new ProviderException(getScopeFacade(),
						"WAREWORK cannot create Provider '" + getName()
								+ "' because given parameter '"
								+ PARAMETER_DATASTORE_VIEW_NAME
								+ "' is not a string.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate View parameters.
		if (this.datastoreViewName == null) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because parameter '"
							+ PARAMETER_DATASTORE_VIEW_NAME
							+ "' is not specified.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Setup cache mode.
		cacheEnabled = isInitParameter(PARAMETER_ENABLE_CACHE);

	}

	/**
	 * Connects the Data Store.
	 * 
	 * @throws ProviderException
	 *             If there is an error when trying to connect the Provider.<br>
	 * <br>
	 */
	protected void connect() throws ProviderException {

		// Validate the Data Store.
		validateDatastore("connect");

		// Get the Data Store Service.
		DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(serviceName);

		// Connect the Data Store.
		if (!service.isConnected(datastoreName)) {
			try {
				service.connect(datastoreName);
			} catch (ServiceException e) {
				throw new ProviderException(getScopeFacade(),
						"WAREWORK cannot connect Provider '" + getName()
								+ "' because it cannot connect Data Store '"
								+ datastoreName + "' in Service '"
								+ serviceName + "'.", e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * Closes the connection with the Data Store.
	 * 
	 * @throws ProviderException
	 *             If there is an error when trying to disconnect the Provider.<br>
	 * <br>
	 */
	protected void disconnect() throws ProviderException {

		// Validate the Data Store.
		validateDatastore("disconnect");

		// Get the Data Store Service.
		DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(serviceName);

		// Disconnect the Data Store.
		if (service.isConnected(datastoreName)) {
			try {
				service.disconnect(datastoreName);
			} catch (ServiceException e) {
				throw new ProviderException(getScopeFacade(),
						"WAREWORK cannot disconnect Provider '" + getName()
								+ "' because it cannot disconnect Data Store '"
								+ datastoreName + "' in Service '"
								+ serviceName + "'.", e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * Validates if the connection with the Data Store is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and
	 *         <code>false</code> if the connection is open.<br>
	 * <br>
	 */
	protected boolean isClosed() {

		// Validate the Data Store.
		try {
			validateDatastore("disconnect");
		} catch (ProviderException e) {
			return true;
		}

		// Get the Data Store Service.
		DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(serviceName);

		// Validates if the connection with the Data Store is closed.
		return !service.isConnected(datastoreName);

	}

	/**
	 * Gets the value associated to a key in a Data Store.
	 * 
	 * @param key
	 *            Key used to retrieve the value from a Data Store.<br>
	 * <br>
	 * @return Value associated to the key.<br>
	 * <br>
	 */
	protected Object getObject(String key) {
		if ((cache != null) && (cache.containsKey(key))) {
			return cache.get(key);
		} else {

			// Validate the Data Store.
			try {
				validateDatastore("retrieve the value for '" + key + "' in ");
			} catch (ProviderException e) {
				getScopeFacade()
						.log("WAREWORK cannot get the value for key '"
								+ key
								+ "' in Provider '"
								+ getName()
								+ "' because the following error was found with the Data Store: "
								+ e.getMessage(),
								LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the Data Store Service.
			DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
					.getService(serviceName);

			// Get the View of the Data Store.
			KeyValueView view = (KeyValueView) service.getView(datastoreName,
					datastoreViewName);

			// Validate that Data Store is connected.
			if (view.isConnected()) {

				// Get the value for the key.
				Object value = null;
				try {
					value = view.get(key);
				} catch (ClientException e) {
					getScopeFacade()
							.log("WAREWORK cannot get the value for key '"
									+ key
									+ "' in Provider '"
									+ getName()
									+ "' because the following error was found with the Data Store: "
									+ e.getMessage(),
									LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Cache the value if required.
				if ((cacheEnabled) && (value != null)) {

					// Create the cache.
					if (cache == null) {
						cache = new HashMap<String, Object>();
					}

					// Store the value.
					cache.put(key, value);

				}

				// Return the value.
				return value;

			} else {

				// Log a message.
				getScopeFacade().log(
						"WAREWORK cannot get the value for key '" + key
								+ "' in Provider '" + getName()
								+ "' because Data Store '" + datastoreName
								+ "' in Service '" + serviceName
								+ "' is not connected.",
						LogServiceConstants.LOG_LEVEL_WARN);

				// Nothing to return.
				return null;

			}

		}
	}

	/**
	 * Gets the keys that exist in the Data Store.
	 * 
	 * @return Name of every key.<br>
	 * <br>
	 * @throws ProviderException
	 *             If there is an error when trying to get the name of the
	 *             objects.<br>
	 * <br>
	 */
	protected Enumeration<String> getObjectNames() throws ProviderException {

		// Validate the Data Store.
		validateDatastore("retrieve the name of every key in ");

		// Get the Data Store Service.
		DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(serviceName);

		// Get the View of the Data Store.
		KeyValueView view = (KeyValueView) service.getView(datastoreName,
				datastoreViewName);

		// Validate if Data Store is connected.
		if (view.isConnected()) {

			// Get the keys.
			Enumeration<Object> keys = null;
			try {
				keys = view.keys();
			} catch (ClientException e) {
				throw new ProviderException(
						getScopeFacade(),
						"WAREWORK cannot retrieve the name of every key in Provider '"
								+ getName()
								+ "' because Data Store '"
								+ datastoreName
								+ "' reported the following error when trying to retrieve the keys: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Create a list with the keys that are string values.
			List<String> result = new ArrayList<String>();

			// Copy each string key into the list.
			while (keys.hasMoreElements()) {

				// Get one key.
				Object key = keys.nextElement();

				// Store only if key is a string.
				if (key instanceof String) {
					result.add((String) key);
				}

			}

			// Return the keys.
			return Collections.enumeration(result);

		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot retrieve the name of every key in Provider '"
							+ getName() + "' because Data Store '"
							+ datastoreName + "' in Service '" + serviceName
							+ "' is not connected.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates that a Data Store Service exists, that this Service has a Data
	 * Store in it, with a Key-Value View associated to it.
	 * 
	 * @param operation
	 *            Name of the operation to log in the exception message.<br>
	 * <br>
	 * @throws ProviderException
	 *             If there is an error when trying to validate the Data Store.<br>
	 * <br>
	 */
	private void validateDatastore(String operation) throws ProviderException {
		if (getScopeFacade().getService(serviceName) == null) {
			throw new ProviderException(
					getScopeFacade(),
					"WAREWORK cannot "
							+ operation
							+ " Provider '"
							+ getName()
							+ "' because no Service can be found with the name specified at '"
							+ PARAMETER_SERVICE_NAME
							+ "' initialization parameter.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		} else if (!(getScopeFacade().getService(serviceName) instanceof DatastoreServiceFacade)) {
			throw new ProviderException(getScopeFacade(), "WAREWORK cannot "
					+ operation + " Provider '" + getName()
					+ "' because Service '" + serviceName
					+ "' is not an instance of '"
					+ DatastoreServiceFacade.class.getName() + "'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		} else {

			// Get the Data Store Service.
			DatastoreServiceFacade datastoreService = (DatastoreServiceFacade) getScopeFacade()
					.getService(serviceName);

			// Perform more validations.
			if (!datastoreService.existsClient(datastoreName)) {
				throw new ProviderException(getScopeFacade(), "WAREWORK cannot "
						+ operation + " Provider '" + getName()
						+ "' because Data Store '" + datastoreName
						+ "' can not be found in Service '" + serviceName
						+ "'.", null, LogServiceConstants.LOG_LEVEL_WARN);
			} else if (datastoreService.getView(datastoreName,
					datastoreViewName) == null) {
				throw new ProviderException(getScopeFacade(),
						"WAREWORK cannot " + operation + " Provider '"
								+ getName() + "' because Data Store '"
								+ datastoreName + "' at Service '"
								+ serviceName + "' does not have a '"
								+ datastoreViewName + "' View.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

}
