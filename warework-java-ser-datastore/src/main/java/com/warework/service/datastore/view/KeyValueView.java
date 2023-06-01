package com.warework.service.datastore.view;

import java.util.Enumeration;

import com.warework.core.service.client.ClientException;

/**
 * <u> Key-Value View</u><br>
 * <br>
 * This View is an interface that represents an abstract Data Store composed of
 * a collection of key-value pairs, such that each possible key appears at most
 * once in the collection. It is like a Hashtable but this time the operations
 * are performed in a Data Store. Operations associated with this View
 * allow:<br>
 * <br>
 * <ul>
 * <li>Addition of pairs to the collection.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>Removal of pairs from the collection.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>Modification of the values of existing pairs.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>Lookup of the value associated with a particular key.<br>
 * </li>
 * </ul>
 * <br>
 * Working with this View is fairly easy because the
 * <a href="http://goo.gl/sIDhS9">KeyValueView</a> interface (package:
 * com.warework.service.datastore.view) only exposes a few methods and they all
 * are very simple. <br>
 * <br>
 * The first operation you have to perform to start working with a Data Store is
 * the connect operation. There is no exception with the Key-Value View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * The following example shows how to add a new value associated to a key in the
 * Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;View.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key&nbsp;named&nbsp;'user.name'&nbsp;or&nbsp;update&nbsp;it.&nbsp;<br>view.put(&quot;user.name&quot;,&nbsp;&quot;John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * Some Data Stores may accept other keys and values different than Strings.
 * This limitation is imposed by the Data Store so read carefully the
 * documentation associated to the implementation class of the View and the Data
 * Store that it supports. This example is completely perfect with this
 * interface:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;View.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key-value&nbsp;or&nbsp;update&nbsp;an&nbsp;existing&nbsp;one.&nbsp;<br>view.put(new&nbsp;Integer(23),&nbsp;Boolean.TRUE);</code>
 * <br>
 * <br>
 * Now, you can retrieve the value associated to the key like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;key&nbsp;'23'.&nbsp;<br>Boolean&nbsp;value&nbsp;=&nbsp;(Boolean)&nbsp;view.get(new&nbsp;Integer(23));</code>
 * <br>
 * <br>
 * This is how to remove a key and the value associated to it:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;the&nbsp;key&nbsp;from&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.remove(&quot;user.enabled&quot;);</code>
 * <br>
 * <br>
 * You can also get a list with the keys that exist in the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;every&nbsp;key&nbsp;from&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>Enumeration&lt;Object&gt;&nbsp;keys&nbsp;=&nbsp;view.keys();</code>
 * <br>
 * <br>
 * To retrieve the number of key-value pairs you have to invoke the size method
 * like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;keys&nbsp;in&nbsp;Data&nbsp;Store.&nbsp;<br>int&nbsp;size&nbsp;=&nbsp;view.size();</code>
 * <br>
 * <br>
 * Once you have performed a set of put or remove operations, it might be a good
 * idea to perform commit to register the changes in the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commit&nbsp;changes&nbsp;in&nbsp;a&nbsp;Data&nbsp;Store.&nbsp;<br>view.commit();</code>
 * <br>
 * <br>
 * When the work is done, you have to disconnect the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Close&nbsp;the&nbsp;connection&nbsp;with&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.disconnect();</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface KeyValueView extends DatastoreView {

	/**
	 * Maps the specified key to the specified value in the Data Store. Neither the
	 * key nor the value can be <code>null</code>.
	 * 
	 * @param key   Key for the value.<br>
	 *              <br>
	 * @param value Value to set.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to store the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	void put(final Object key, final Object value) throws ClientException;

	/**
	 * Gets the value to which the specified key is mapped in the Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @return The value to which the specified key is mapped in the Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the value
	 *                         from the Data Store.<br>
	 *                         <br>
	 */
	Object get(final Object key) throws ClientException;

	/**
	 * Removes the key (and its corresponding value) from the Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	void remove(final Object key) throws ClientException;

	/**
	 * Gets an enumeration of the keys in the Data Store.
	 * 
	 * @return An enumeration of the keys in this Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the keys.<br>
	 *                         <br>
	 */
	Enumeration<Object> keys() throws ClientException;

	/**
	 * Counts the number of keys in the Data Store.
	 * 
	 * @return Number of keys in the Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the size.<br>
	 *                         <br>
	 */
	int size() throws ClientException;

	/**
	 * Commits changes in the Data Store.
	 * 
	 * @throws ClientException If there is an error when trying to commit the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void commit() throws ClientException;

}
