package com.warework.service.datastore.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.log.LogServiceConstants;

/**
 * <u> Key-Value View for JDBC Data Stores</u><br>
 * <br>
 * <span>This View allows you to use a table from a relational database as a
 * Key-Value Data Store. </span><span>The </span><a target="blank" HREF=
 * "http://api.warework.com/java/warework-java-ser-datastore-jdbc/2.0.0/com/warework/service/datastore/client/KeyValueJdbcViewImpl.html"
 * ><span>KeyValueJdbcViewImpl</span></a><span> class at
 * </span><span>com.warework.service.datastore.client </span><span>package
 * implements the </span><span>KeyValueView</span><span> interface and it
 * provides the necessary methods to achieve this task. To work with it you have
 * to specify a table from a database, which column of this table correspond to
 * the keys (it must be of </span><span>String</span><span> type) and the column
 * that matches the values for these keys. Now we are going to see how to
 * associate a View of this type with a JDBC Data Store and how to perform
 * operations with the View.</span><br>
 * <br>
 * <ul class="t0">
 * <li>Setting up the View<br>
 * </li>
 * </ul>
 * <br>
 * <span>As stated before, i</span><span>n order to work with this View you
 * always have to configure three initialization parameters: the name of a
 * table, the name of the column in this table that represents the keys and the
 * name of the column that represents the values.</span><span> There is another
 * optional parameter that allows you to define the name of the schema:
 * </span><span>PARAMETER_SCHEMA</span><span>. You may use it in some relational
 * databases to specify the schema where the table exists. </span><span>The
 * following example shows you how to setup the configuration for this View:
 * </span> <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;View.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;View&nbsp;with&nbsp;initialization&nbsp;parameters.&nbsp;<br>parameters.put(KeyValueJdbcViewImpl.PARAMETER_SCHEMA,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;SAMPLE_SCHEMA&quot;);&nbsp;<br>parameters.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;SAMPLE_TABLE&quot;);&nbsp;<br>parameters.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;KEY_COLUNM&quot;);&nbsp;<br>parameters.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;VALUE_COLUMN&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;the&nbsp;Key-Value&nbsp;View&nbsp;for&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.addView(&quot;jdbc-datastore&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;KeyValueJdbcViewImpl.class,&nbsp;&quot;keyvalue-view&quot;,&nbsp;null,&nbsp;parameters);</code>
 * <br>
 * <br>
 * <br>
 * Check it now how to do it with the Data Store Service XML configuration
 * file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&hellip;&nbsp;xsd&#47;datastore-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.connector.JdbcConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.KeyValueJdbcViewImpl&quot;&nbsp;name=&quot;keyvalue-view&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;schema&quot;&nbsp;value=&quot;SAMPLE_SCHEMA&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-name&quot;&nbsp;value=&quot;SAMPLE_TABLE&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-key-field&quot;&nbsp;value=&quot;KEY_COLUMN&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-value-field&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;VALUE_COLUMN&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;view&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <br>
 * You can also provide short class names to identify Connectors and Views:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&hellip;&nbsp;xsd&#47;datastore-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;connector=&quot;JDBC&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;KeyValueJDBC&quot;&nbsp;name=&quot;keyvalue-view&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;schema&quot;&nbsp;value=&quot;SAMPLE_SCHEMA&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-name&quot;&nbsp;value=&quot;SAMPLE_TABLE&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-key-field&quot;&nbsp;value=&quot;KEY_COLUMN&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;table-value-field&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;VALUE_COLUMN&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;view&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>Working with the Key-Value View for </span><span>JDBC Data
 * Stores</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>Now we are going to review the basic operations provided by the
 * </span><span>KeyValueJdbcViewImpl</span><span> class. Working with this View
 * is fairly easy because the </span><span>KeyValueView</span><span> interface
 * (package: </span><span>com.warework.service.datastore.view</span><span>) only
 * exposes a few methods and they all are very simple. </span> <br>
 * <br>
 * <span>The first operation you have to perform to start working with a Data
 * Store is the </span><span>connect</span><span> operation. There is no
 * exception with the Key-Value View:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * <span>The following example shows how to add a new
 * </span><span>String</span><span> value associated to a key in the JDBC Data
 * Store:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key&nbsp;named&nbsp;'user.name'&nbsp;or&nbsp;update&nbsp;it.&nbsp;<br>view.put(&quot;user.name&quot;,&nbsp;&quot;John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * <span>The value field in a JDBC Data Store can be different than
 * </span><span>String</span><span> type so you can probably store values like
 * </span><span>Integer</span><span> or </span><span>Date</span><span>. This
 * limitation is imposed by the underlying database; read the documentation of
 * your database vendor and check out which types are available. This example is
 * completely perfect with this interface:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Key-Value&nbsp;interface.&nbsp;<br>KeyValueView&nbsp;view&nbsp;=&nbsp;(KeyValueView)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;key-value&nbsp;or&nbsp;update&nbsp;an&nbsp;existing&nbsp;one.&nbsp;<br>view.put(&quot;user.age&quot;,&nbsp;new&nbsp;Integer(25));</code>
 * <br>
 * <br>
 * <span>Now, you can retrieve the va</span><span>lue associated to a key like
 * this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;the&nbsp;'user.age'&nbsp;key.&nbsp;<br>Integer&nbsp;value&nbsp;=&nbsp;(Integer)&nbsp;view.get(&quot;user.age&quot;);</code>
 * <br>
 * <br>
 * This is how to remove a key and the value associated to it:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;a&nbsp;row&nbsp;in&nbsp;a&nbsp;table.&nbsp;<br>view.remove(&quot;user.age&quot;);</code>
 * <br>
 * <br>
 * You can also get a list with the names of the keys that exist in the Data
 * Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;every&nbsp;key&nbsp;from&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>Enumeration&lt;Object&gt;&nbsp;keys&nbsp;=&nbsp;view.keys();</code>
 * <br>
 * <br>
 * <span>To retrieve the number of key-value pairs you have to invoke the
 * </span><span>size</span><span> method like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;keys&nbsp;in&nbsp;Data&nbsp;Store.&nbsp;<br>int&nbsp;size&nbsp;=&nbsp;view.size();</code>
 * <br>
 * <br>
 * <br>
 * <span>Once you have performed a set of </span><span>put</span><span> or
 * </span><span>remove </span><span>operations, it might be a good idea to
 * perform </span><span>commit </span><span>to register the changes in the JDBC
 * Data Store:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commit&nbsp;changes&nbsp;in&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.commit();</code>
 * <br>
 * <br>
 * <br>
 * <span>When the work is done, you have to disconnect the JDBC Data
 * Store:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Close&nbsp;the&nbsp;connection&nbsp;with&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.disconnect();</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class KeyValueJdbcViewImpl extends AbstractDatastoreView implements KeyValueView {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies a string that represents the database
	 * schema. This parameter is optional.
	 */
	public static final String PARAMETER_SCHEMA = "schema";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * the table to use as a Key-Value Data Store. This parameter is mandatory.
	 */
	public static final String PARAMETER_TABLE_NAME = "table-name";

	/**
	 * Initialization parameter that specifies a string with the name of the column
	 * (this column must exist in the table specified with
	 * <code>PARAMETER_TABLE_NAME</code>) that represents the keys. This column in
	 * the table must be a string/varchar type. This parameter is mandatory.
	 */
	public static final String PARAMETER_TABLE_KEY_FIELD = "table-key-field";

	/**
	 * Initialization parameter that specifies a string with the name of the column
	 * (this column must exist in the table specified with
	 * <code>PARAMETER_TABLE_NAME</code>) that represents the values. This column in
	 * the table can be any type you want. This parameter is mandatory.
	 */
	public static final String PARAMETER_TABLE_VALUE_FIELD = "table-value-field";

	/**
	 * Optional initialization parameter that specifies if key and value fileds
	 * should be inside double quotes. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). Default value is <code>false</code>.
	 */
	public static final String PARAMETER_QUOTE_FIELDS = "quote-fields";

	// VARIABLE REPLACEMENT

	/**
	 * Name of the <code>KEY</code> variable to be replaced in the SQL statement.
	 */
	protected static final String VARIABLE_KEY = "KEY";

	/**
	 * Name of the <code>VALUE</code> variable to be replaced in the SQL statement.
	 */
	protected static final String VARIABLE_VALUE = "VALUE";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// PARAMETER VALUES

	// Database schema.
	protected String schema;

	// Name of the table.
	protected String tableName;

	// Column for keys.
	protected String tableKeyField;

	// Column for values.
	protected String tableValueField;

	// Create key and value fields inside double quotes.
	protected boolean quoteFields = false;

	// STATEMENTS

	private String selectSingle, selectAll, selectExists;

	private String insert;

	private String update;

	private String delete;

	private String count;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the value to which the specified key is mapped in the JDBC Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @return The value to which the specified key is mapped in the JDBC Data
	 *         Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the value
	 *                         from the Data Store.<br>
	 *                         <br>
	 */
	public Object get(final Object key) throws ClientException {
		if (isConnected()) {

			// Return the result of the query.
			Object value = null;
			try {

				// Queries the table that represents the Key-Value Data Store
				// for a specific key.
				final ResultSet resultSet = executeQuerySingle(key);

				// Get the value.
				if (resultSet.next()) {
					value = resultSet.getObject(tableValueField);
				}

				// Close the JDBC ResultSet.
				resultSet.close();

			} catch (final SQLException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot execute the query in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the database reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Return the value.
			return value;

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot get the value of a key in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Maps the specified key to the specified value in the JDBC Data Store. Neither
	 * the key nor the value can be <code>null</code>.
	 * 
	 * @param key   Key for the value.<br>
	 *              <br>
	 * @param value Value to set.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to store the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void put(final Object key, final Object value) throws ClientException {
		if (isConnected()) {

			// Validate that key and value are not null.
			if ((key == null) || (key.equals(CommonValueL1Constants.STRING_EMPTY)) || (value == null)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot add or update a key in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the key and/or the value are null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Define variables.
			final Map<String, Object> values = new HashMap<String, Object>();

			// Set the variables.
			values.put(VARIABLE_KEY, key);
			values.put(VARIABLE_VALUE, value);

			// Execute the statement.
			executeUpdate(key, values);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add or update a key in Data Store " + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Removes the key (and its corresponding value) from the JDBC Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void remove(final Object key) throws ClientException {
		if (isConnected()) {

			// Validate that key is not null.
			if ((key == null) || (key.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot remove a key in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the key is null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Execute the statement.
			executeDelete(key);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete a key in Data Store " + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets an enumeration of the keys in the JDBC Data Store.
	 * 
	 * @return An enumeration of the keys in this JDBC Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the keys.<br>
	 *                         <br>
	 */
	public Enumeration<Object> keys() throws ClientException {
		if (isConnected()) {

			// Create an SELECT statement.
			if (selectAll == null) {
				selectAll = createSelectAll();
			}

			// Get the connection.
			final Connection connection = (Connection) getConnection();

			// Log statement.
			getScopeFacade().log("WAREWORK is going to execute the following query in Data Store '" + getName()
					+ "' of Service '" + getService().getName() + "':\n" + selectAll,
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Get the keys.
			final List<Object> keys = new ArrayList<Object>();
			try {

				// Create the statement.
				final PreparedStatement preparedStatemet = connection.prepareStatement(selectAll);

				// Run the query.
				final ResultSet resultSet = preparedStatemet.executeQuery();

				// Add each key in the list.
				while (resultSet.next()) {

					// Get the key.
					final Object key = resultSet.getObject(tableKeyField);

					// Do not add the same key more than one time.
					if (!keys.contains(key)) {
						keys.add(key);
					}

				}

				// Close the JDBC ResultSet.
				resultSet.close();

				// Log a message.
				getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName()
						+ "' of Service '" + getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

			} catch (final SQLException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot retrieve the keys of Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because JDBC reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Return the keys.
			return (keys.size() > 0) ? DataStructureL1Helper.toEnumeration(keys) : null;

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the keys from Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Counts the number of keys in the JDBC Data Store.
	 * 
	 * @return Number of keys in the JDBC Data Store.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the size.<br>
	 *                         <br>
	 */
	public int size() throws ClientException {
		if (isConnected()) {

			// Create an SELECT statement.
			if (count == null) {
				count = createSelectCount();
			}

			// Get the connection.
			final Connection connection = (Connection) getConnection();

			// Log statement.
			getScopeFacade().log("WAREWORK is going to execute the following query in Data Store '" + getName()
					+ "' of Service '" + getService().getName() + "':\n" + count, LogServiceConstants.LOG_LEVEL_DEBUG);

			// Get the keys.
			int size = 0;
			try {

				// Create the statement.
				final PreparedStatement preparedStatemet = connection.prepareStatement(count);

				// Run the query.
				final ResultSet resultSet = preparedStatemet.executeQuery();

				// Go to first row.
				resultSet.next();

				// Get the count.
				size = resultSet.getInt(1);

				// Close the JDBC ResultSet.
				resultSet.close();

				// Log a message.
				getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName()
						+ "' of Service '" + getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

			} catch (final SQLException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot count keys in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because JDBC reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Return the number of keys found.
			return size;

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
	 *                         View.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {

		// Validate the Data Store.
		if (!getDatastore().getConnector().getClientType().equals(JdbcDatastore.class)) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because this View only works with '" + JdbcDatastore.class.getName()
							+ "' and the Data Store where you want to add this View is an instance of '"
							+ getDatastore().getConnector().getClientType() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Validate schema.
		initSchema();

		// Validate the name of the table.
		initTableName();

		// Get the name of the key field.
		initTableFieldKey();

		// Get the name of the value field.
		initTableFieldValue();

		// Setup if field names should be quoted.
		initQuoteFieldsValue();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the SQL statement to select a single value in the table that
	 * represents the Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectSingle() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create the SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create the SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);

		// Return statement.
		return sql.toString();

	}

	/**
	 * Creates the SQL statement to validate that a single value exists in the table
	 * that represents the Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectExists() {
		return createSelectSingle();
	}

	/**
	 * Creates the SQL statement to select all keys in the table that represents the
	 * Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectAll() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_DISTINCT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);

		// Return statement.
		return sql.toString();

	}

	/**
	 * Creates the SQL statement to count all available keys in the table that
	 * represents the Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectCount() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_COUNT);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);

		// Return statement.
		return sql.toString();

	}

	/**
	 * Creates the UPDATE statement.
	 * 
	 * @return SQL update statement.<br>
	 *         <br>
	 */
	protected String createUpdate() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_UPDATE);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_SET);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_VALUE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);

		// Return the update statement.
		return sql.toString();

	}

	/**
	 * Creates the INSERT statement.
	 * 
	 * @return SQL insert statement.<br>
	 *         <br>
	 */
	protected String createInsert() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_INSERT);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_VALUES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_VALUE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Return the insert statement.
		return sql.toString();

	}

	/**
	 * Creates the DELETE statement.
	 * 
	 * @return SQL insert statement.<br>
	 *         <br>
	 */
	protected String createDelete() throws ClientException {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_DELETE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);

		// Cache this statement.
		return sql.toString();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Queries the table that represents the Key-Value Data Store for a specific
	 * key.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @return Result of the database.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected final ResultSet executeQuerySingle(final Object key) throws ClientException {

		// Create the SQL statement if required.
		if (selectSingle == null) {
			selectSingle = createSelectSingle();
		}

		// Execute the statement.
		try {
			return queryKey(selectSingle, key);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the query in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because the database reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Queries the table that represents the Key-Value Data Store to validate a
	 * specific key exists.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @return <code>true</code> if key exists and <code>false</code> if not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected final boolean executeQueryExists(final Object key) throws ClientException {

		// Create the SQL statement if required.
		if (selectExists == null) {
			selectExists = createSelectExists();
		}

		// Execute the statement.
		try {

			// Execute query.
			final ResultSet resultSet = queryKey(selectExists, key);

			// Validate if key exists.
			final boolean exists = ((resultSet != null) && (resultSet.next()));

			// Close the result.
			resultSet.close();

			// Return if key exists.
			return exists;

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot execute the query in Data Store '" + getName()
					+ "' at Service '" + getService().getName()
					+ "' because the database reported the following error when trying to validate if the key already exists: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Inserts or updates the table that represents the Key-Value Data Store.
	 * 
	 * @param key       Data Store key.<br>
	 *                  <br>
	 * @param variables Variables to replace in the SQL statement.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to insert or update
	 *                         the Data Store.<br>
	 *                         <br>
	 */
	protected final void executeUpdate(final Object key, final Map<String, Object> variables) throws ClientException {

		// Create the statement to execute in the database.
		String statement = null;
		if (executeQueryExists(key)) {

			// Create an UPDATE statement if required.
			if (update == null) {
				update = createUpdate();
			}

			// Set the statement to execute.
			statement = update;

		} else {

			// Create an INSERT statement if required.
			if (insert == null) {
				insert = createInsert();
			}

			// Set the statement to execute.
			statement = insert;

		}

		// Execute the statement.
		getDatastore().update(statement, variables);

	}

	/**
	 * Removes the key (and its corresponding value) from the JDBC Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected final void executeDelete(final Object key) throws ClientException {

		// Create an DELETE statement if required.
		if (delete == null) {
			delete = createDelete();
		}

		// Define variables.
		final Map<String, Object> values = new HashMap<String, Object>();

		// Set the variables.
		values.put(VARIABLE_KEY, key);

		// Execute the statement.
		getDatastore().update(delete, values);

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Queries the table that represents the Key-Value Data Store for a specific
	 * key.
	 * 
	 * @param statement SQL statement to execute.<br>
	 *                  <br>
	 * @param key       Data Store key.<br>
	 *                  <br>
	 * @return Result of the database.<br>
	 *         <br>
	 * @throws SQLException If there is an error when trying to query the Data
	 *                      Store.<br>
	 *                      <br>
	 */
	private ResultSet queryKey(final String statement, final Object key) throws SQLException {

		// Define variables.
		final Map<String, Object> values = new HashMap<String, Object>();

		// Set the key variable.
		values.put(VARIABLE_KEY, key);

		// Extracts the values in the order that indicates the
		// variables defined in the statement.
		final Object[] valuesFound = StringL1Helper.values(statement, values);

		// Updates the value of each key in the values map with '?'.
		final Map<String, Object> updatedValues = DataStructureL1Helper.updateValues(values,
				StringL1Helper.CHARACTER_QUESTION);

		// Convert each variable to '?'.
		final String queryStatement = StringL1Helper.replace(statement, updatedValues);

		// Get the connection.
		final Connection connection = (Connection) getConnection();

		// Get the Data Store.
		final JdbcDatastore datastore = (JdbcDatastore) getDatastore();

		// Create a JDBC statement.
		final PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);

		// Setup the JDBC statement with the values.
		datastore.setupPreparedStatement(preparedStatement, valuesFound);

		// Setup the maximum number of rows to retrieve.
		preparedStatement.setMaxRows(1);

		// Log statement.
		getScopeFacade().log("WAREWORK is going to execute the following query in Data Store '" + getName()
				+ "' of Service '" + getService().getName() + "':\n" + selectSingle,
				LogServiceConstants.LOG_LEVEL_DEBUG);

		// Run the query.
		final ResultSet result = preparedStatement.executeQuery();

		// Log a message.
		getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

		// Return result.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialize <code>PARAMETER_SCHEMA</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         Schema.<br>
	 *                         <br>
	 */
	private void initSchema() throws ClientException {

		// Get the value of the parameter.
		final Object ddbbSchema = getInitParameter(PARAMETER_SCHEMA);

		// Validate schema.
		if ((ddbbSchema != null) && (!(ddbbSchema instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because given '" + PARAMETER_SCHEMA + "' parameter is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else if (ddbbSchema != null) {
			schema = (String) ddbbSchema;
		}

	}

	/**
	 * Initialize <code>PARAMETER_TABLE_NAME</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the name
	 *                         of the table.<br>
	 *                         <br>
	 */
	private void initTableName() throws ClientException {

		// Get the value of the parameter.
		final Object ddbbTableName = getInitParameter(PARAMETER_TABLE_NAME);

		// Validate the name of the table.
		if ((ddbbTableName == null) || (!(ddbbTableName instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because given '" + PARAMETER_TABLE_NAME + "' parameter is null or not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			tableName = (String) ddbbTableName;
		}

	}

	/**
	 * Initialize <code>PARAMETER_TABLE_KEY_FIELD</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         key.<br>
	 *                         <br>
	 */
	private void initTableFieldKey() throws ClientException {

		// Get the value of the parameter.
		final Object ddbbTableFieldKey = getInitParameter(PARAMETER_TABLE_KEY_FIELD);

		// Get the name of the key field.
		if ((ddbbTableFieldKey == null) || (!(ddbbTableFieldKey instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because given '" + PARAMETER_TABLE_KEY_FIELD + "' parameter is null or not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			tableKeyField = (String) ddbbTableFieldKey;
		}

	}

	/**
	 * Initialize <code>PARAMETER_TABLE_VALUE_FIELD</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         field value.<br>
	 *                         <br>
	 */
	private void initTableFieldValue() throws ClientException {

		// Get the value of the parameter.
		final Object ddbbTableFieldValue = getInitParameter(PARAMETER_TABLE_VALUE_FIELD);

		// Get the name of the value field.
		if ((ddbbTableFieldValue == null) || (!(ddbbTableFieldValue instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because given '" + PARAMETER_TABLE_VALUE_FIELD
							+ "' parameter is null or not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			tableValueField = (String) ddbbTableFieldValue;
		}

	}

	/**
	 * Initialize <code>PARAMETER_QUOTE_FIELDS</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         field value.<br>
	 *                         <br>
	 */
	private void initQuoteFieldsValue() throws ClientException {

		// Get the value of the parameter.
		final Object quoteFieldValue = getInitParameter(PARAMETER_QUOTE_FIELDS);

		// Process value field.
		if (quoteFieldValue != null) {
			if (quoteFieldValue instanceof String) {

				// Get the string value of the parameter.
				final String quoteFields = (String) quoteFieldValue;

				// Validate parameter.
				if (quoteFields.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) {
					this.quoteFields = true;
				} else if (quoteFields.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_FALSE)) {
					this.quoteFields = false;
				} else {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
									+ "' because given '" + PARAMETER_QUOTE_FIELDS
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else if (quoteFieldValue instanceof Boolean) {
				this.quoteFields = (Boolean) quoteFieldValue;
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
								+ "' because given '" + PARAMETER_QUOTE_FIELDS + "' parameter is not a string or a '"
								+ Boolean.class.getName() + "' value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

}
