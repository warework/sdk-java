package com.warework.service.datastore.client;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.log.LogServiceConstants;

/**
 * <u> Key-Value View for Hashtable Data Stores</u><br>
 * <br>
 * This View is a class that implements the Key-Value interface for the
 * Hashtable Data Store. Now we are going to see how to associate a View of this
 * type with a Hashtable Data Store and how to perform operations with the View.<br>
 * <br>
 * <ul>
 * <li>Setting up the View<br>
 * </li>
 * </ul>
 * <br>
 * There is no need to configure this View with initialization parameters, so
 * just add the View in the stack of Views of the Hashtable Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;Key-Value&nbsp;View&nbsp;for&nbsp;Hashtable&nbsp;Data&nbsp;Stores.&nbsp;<br>datastoreService.addView(&quot;hashtable-datastore&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;HashtableViewImpl.class,&nbsp;&quot;key-value-view&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * Check it now how to do it with the Data Store Service XML configuration file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&hellip;&nbsp;xsd&#47;datastore-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;hashtable-datastore&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.connector.HashtableConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.HashtableViewImpl&quot;&nbsp;name=&quot;key-value-view&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <ul>
 * <li>Working with the Key-Value View for Hashtable Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * Now we are going to review the basic operations provided by the
 * HashtableViewImpl class. Working with this View is fairly easy because the <a
 * href="http://goo.gl/B87dia">KeyValueView</a> interface (package:
 * com.warework.service.datastore.view) only exposes a few methods and they all
 * are very simple. <br>
 * <br>
 * The first operation you have to perform to start working with a Data Store is
 * the connect operation. There is no exception with the Key-Value View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;hashtable-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * The following example shows how to add a new value associated to a key in the
 * Hashtable Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;hashtable-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key&nbsp;named&nbsp;'user.name'&nbsp;or&nbsp;update&nbsp;it.&nbsp;<br>view.put(&quot;user.name&quot;,&nbsp;&quot;John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * Remember, you can use values different than Strings: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;hashtable-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;536&nbsp;key&nbsp;named&nbsp;with&nbsp;the&nbsp;current&nbsp;date.&nbsp;<br>view.put(new&nbsp;Integer(536),&nbsp;new&nbsp;Date());</code>
 * <br>
 * <br>
 * Now, you can retrieve the value associated to a key like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;the&nbsp;536&nbsp;key.&nbsp;<br>Date&nbsp;value&nbsp;=&nbsp;(Date)&nbsp;view.get(new&nbsp;Integer(536));</code>
 * <br>
 * <br>
 * This is how to remove a key and the value associated to it:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;the&nbsp;key&nbsp;from&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.&nbsp;<br>view.remove(&quot;user.name&quot;);</code>
 * <br>
 * <br>
 * You can also get a list with the keys that exist in the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;every&nbsp;key&nbsp;from&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.&nbsp;<br>Enumeration&lt;Object&gt;&nbsp;keys&nbsp;=&nbsp;view.keys();</code>
 * <br>
 * <br>
 * When finished, you have to disconnect the Hashtable Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Close&nbsp;the&nbsp;connection&nbsp;with&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.disconnect();</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class HashtableViewImpl extends AbstractDatastoreView implements
		KeyValueView {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the value to which the specified key is mapped in the Hashtable.
	 * 
	 * @param key
	 *            Hashtable key.<br>
	 * <br>
	 * @return The value to which the specified key is mapped in the Hashtable.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to get the value from the
	 *             Data Store.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	public Object get(Object key) throws ClientException {
		if (isConnected()) {

			// Get the connection with the Data Store.
			Map<Object, Object> connection = (Map<Object, Object>) getConnection();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to request key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Get the value to which the specified key is mapped in the
			// Hashtable.
			Object result = connection.get(key);

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully retrieved the value for key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

			// Return the value to which the specified key is mapped in the
			// Hashtable.
			return result;

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot get the value of a key in Data Store '"
							+ getName() + "' at Service '"
							+ getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Maps the specified key to the specified value in the Hashtable. Neither
	 * the key nor the value can be <code>null</code>.
	 * 
	 * @param key
	 *            Key for the value.<br>
	 * <br>
	 * @param value
	 *            Value to set.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to store the value in the
	 *             Data Store.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	public void put(Object key, Object value) throws ClientException {
		if (isConnected()) {

			// Get the connection with the Data Store.
			Map<Object, Object> connection = (Map<Object, Object>) getConnection();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to store a value for key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Set the value for the specified key.
			connection.put(key, value);

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully stored the value for key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add or update a key in Data Store "
							+ getName() + "' at Service '"
							+ getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Removes the key (and its corresponding value) from the Hashtable.
	 * 
	 * @param key
	 *            Hashtable key.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to remove the value in the
	 *             Data Store.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	public void remove(Object key) throws ClientException {
		if (isConnected()) {

			// Get the connection with the Data Store.
			Map<Object, Object> connection = (Map<Object, Object>) getConnection();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to remove key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Remove the value from the Map.
			connection.remove(key);

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully removed key '" + key
							+ "' in Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete a key in Data Store " + getName()
							+ "' at Service '" + getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets an enumeration of the keys in the Hashtable.
	 * 
	 * @return An enumeration of the keys in this Hashtable.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to get the keys.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	public Enumeration<Object> keys() throws ClientException {
		if (isConnected()) {

			// Get the connection with the Data Store.
			Map<Object, Object> connection = (Map<Object, Object>) getConnection();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to request the keys in Data Store '"
							+ getName() + "' of Service '"
							+ getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Get an enumeration with the keys.
			Set<Object> result = connection.keySet();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully requested the keys in Data Store '"
							+ getName() + "' of Service '"
							+ getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

			// Return an enumeration of the keys.
			return DataStructureL1Helper.toEnumeration(result);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the keys from Data Store '"
							+ getName() + "' at Service '"
							+ getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Counts the number of keys in the Data Store.
	 * 
	 * @return Number of keys in the Data Store.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to get the size.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	public int size() throws ClientException {
		if (isConnected()) {

			// Get the connection with the Data Store.
			Map<Object, Object> connection = (Map<Object, Object>) getConnection();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to count the number of keys in Data Store '"
							+ getName() + "' of Service '"
							+ getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Get the size of the map.
			int result = connection.size();

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully counted the number of keys in Data Store '"
							+ getName() + "' of Service '"
							+ getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

			// Return the size of the map.
			return result;

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot count the number of keys from Data Store '"
							+ getName() + "' at Service '"
							+ getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

}
