package com.warework.service.datastore.client;

import java.util.Enumeration;
import java.util.Properties;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.log.LogServiceConstants;

/**
 * <u> Key-Value View for Properties Data Stores</u><br>
 * <br>
 * This View is a class that implements the Key-Value interface and it
 * represents an abstract Data Store composed of a collection of key-value
 * pairs, such that each possible key appears at most once in the collection.
 * <br>
 * <br>
 * Now we are going to see how to associate a View of this type with a
 * Properties Data Store and how to perform operations with the View.<br>
 * <br>
 * <ul class="t0">
 * <li>Setting up the View<br>
 * </li>
 * </ul>
 * <br>
 * There is no need to configure this View with initialization parameters, so
 * just add the View in the stack of Views of the Properties Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;Key-Value&nbsp;View&nbsp;for&nbsp;Properties&nbsp;Data&nbsp;Stores.&nbsp;<br>datastoreService.addView(&quot;properties-datastore&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;PropertiesViewImpl.class,&nbsp;&quot;keyvalue-view&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * Check it now how to do it with the Data Store Service XML configuration
 * file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&hellip;&nbsp;xsd&#47;datastore-service-1.1.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;properties-datastore&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.connector.PropertiesConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-target&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;&#47;META-INF&#47;config.properties&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.PropertiesViewImpl&quot;&nbsp;name=&quot;keyvalue-view&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <ul class="t0">
 * <li>Working with the Key-Value View for Properties Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * Now we are going to review the basic operations provided by the
 * PropertiesViewImpl class. Working with this View is fairly easy because the
 * <a target="blank" HREF=
 * "http://api.warework.com/java/warework-java-ser-datastore/2.0.0/com/warework/service/datastore/view/KeyValueView.html"
 * >KeyValueView</a> interface (package: com.warework.service.datastore.view)
 * only exposes a few methods and they all are very simple. <br>
 * <br>
 * The first operation you have to perform to start working with a Data Store is
 * the connect operation. There is no exception with the Key-Value View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;properties-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * The following example shows how to add a new value associated to a key in the
 * Properties Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;properties-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key&nbsp;named&nbsp;'user.name'&nbsp;or&nbsp;update&nbsp;it.&nbsp;<br>view.put(&quot;user.name&quot;,&nbsp;&quot;John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * A very important fact to bear in mind is that this View will accept only
 * String values, if a different type is given, then an exception will be
 * thrown. <br>
 * <br>
 * Now, you can retrieve the value associated to a key like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;the&nbsp;'user.name'&nbsp;key.&nbsp;<br>String&nbsp;value&nbsp;=&nbsp;(String)&nbsp;view.get(&quot;user.name&quot;);</code>
 * <br>
 * <br>
 * This is how to remove a key and the value associated to it:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;the&nbsp;key&nbsp;from&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.&nbsp;<br>view.remove(&quot;user.name&quot;);</code>
 * <br>
 * <br>
 * You can also get a list with the names of the keys that exist in the Data
 * Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;every&nbsp;key&nbsp;from&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.&nbsp;<br>Enumeration&lt;Object&gt;&nbsp;keys&nbsp;=&nbsp;view.keys();</code>
 * <br>
 * <br>
 * To retrieve the number of key-value pairs you have to invoke the size method
 * like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;keys&nbsp;in&nbsp;Data&nbsp;Store.&nbsp;<br>int&nbsp;size&nbsp;=&nbsp;view.size();</code>
 * <br>
 * <br>
 * <br>
 * Once you have performed a set of put or remove operations, it might be a good
 * idea to perform commit to register the changes in the Properties Data
 * Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commit&nbsp;changes&nbsp;in&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.&nbsp;<br>view.commit();</code>
 * <br>
 * <br>
 * <br>
 * When the work is done, you have to disconnect the Properties Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Close&nbsp;the&nbsp;connection&nbsp;with&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.disconnect();</code>
 * <br>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class PropertiesViewImpl extends AbstractDatastoreView implements KeyValueView {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Maps the specified key to the specified value in the properties file. Neither
	 * the key nor the value can be <code>null</code>.
	 * 
	 * @param key   Key for the value.<br>
	 *              <br>
	 * @param value Value to set.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	public void put(final Object key, final Object value) throws ClientException {
		if (key instanceof String) {

			// Validate that key and value are not null.
			if ((key == null) || (key.equals(CommonValueL1Constants.STRING_EMPTY)) || (value == null)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot add or update a key in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the key and/or the value are null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate the type of the value.
			if (value instanceof String) {
				update(key + StringL1Helper.CHARACTER_EQUALS + value, null);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot set the value for key '" + key + "' in Data Store '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given value is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot set the value for key '" + key + "' in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because given key is not a String object.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the value to which the specified key is mapped in the properties file.
	 * 
	 * @param key Properties file key.<br>
	 *            <br>
	 * @return The value to which the specified key is mapped in the properties
	 *         file.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	public Object get(final Object key) throws ClientException {
		return query(key, null);
	}

	/**
	 * Removes the key (and its corresponding value) from the properties file.
	 * 
	 * @param key Properties file key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove a
	 *                         Key-Value pair in the Data Store.<br>
	 *                         <br>
	 */
	public void remove(final Object key) throws ClientException {
		if (key instanceof String) {
			if (isConnected()) {

				// Get the connection.
				final Properties connection = (Properties) getConnection();

				// Remove a key.
				connection.remove(key);

			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot retrieve the keys from Data Store '" + getName() + "' at service '"
								+ getService().getName() + "' because Data Store connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the keys from Data Store '" + getName() + "' at service '"
							+ getService().getName() + "' because given key is not a String object.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets an enumeration of the keys in the Data Store.
	 * 
	 * @return An enumeration of the keys in this Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the keys from
	 *                         the Data Store.<br>
	 *                         <br>
	 */
	public Enumeration<Object> keys() throws ClientException {
		if (isConnected()) {

			// Get the connection.
			final Properties connection = (Properties) getConnection();

			// Return the keys.
			return connection.keys();

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the keys from Data Store '" + getName() + "' at service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Counts the number of keys in the Data Store.
	 * 
	 * @return Number of keys in the Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the
	 *                         keys.<br>
	 *                         <br>
	 */
	public int size() throws ClientException {
		if (isConnected()) {

			// Get the connection.
			final Properties connection = (Properties) getConnection();

			// Return size.
			return connection.size();

		} else {
			throw new ClientException(
					getScopeFacade(), "WAREWORK cannot count keys in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization of the View.
	 * 
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         Data Store.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {
		if (!getDatastore().getConnector().getClientType().equals(PropertiesDatastore.class)) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because this View only works with '" + PropertiesDatastore.class.getName()
							+ "' and the Data Store where you want to add this View is an instance of '"
							+ getDatastore().getConnector().getClientType() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
