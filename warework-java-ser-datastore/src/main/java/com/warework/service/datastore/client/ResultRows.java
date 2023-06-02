package com.warework.service.datastore.client;

import java.util.Date;
import java.util.Map;

import com.warework.core.service.client.ClientException;

/**
 * This interface represents the table of data returned by the database. It
 * allows iterating each row of the table result and picking up the values of
 * its columns. Check it out with the following example:<br>
 * <br>
 * <code>
 * // Execute the statement to retrieve some data.<br> 
 * ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM HOME_USERS", -1, -1);<br> 
 * <br>
 * // Iterate rows until the end of the table. First row is 1, second 2 and so on. You must perform 'result.next()' at least one time to point the cursor to the first row.<br> 
 * while (result.next()) {<br>
 * <br> 
 * // Get the boolean value of a column. If the value is SQL NULL (it is null in the database), the value returned is null.<br> 
 * Boolean column1 = result.getBoolean("COLUMN1");<br>
 * <br>
 * // Get the numeric value of a column. You must specify the numeric type to get.<br> 
 * Short column2A = result.getNumber("COLUMN2A", Short.class); <br>
 * <br>
 * Integer column2B = result.getNumber("COLUMN2B", Integer.class);<br> 
 * <br>
 * Long column2C = result.getNumber("COLUMN2C", Long.class);<br> 
 * <br>
 * Float column2D = result.getNumber("COLUMN2D", Float.class);<br> 
 * <br>
 * Double column2E = result.getNumber("COLUMN2E", Double.class);<br> 
 * <br>
 * BigDecimal column2F = result.getNumber("COLUMN2F", BigDecimal.class);<br> 
 * <br>
 * // Get the string value.<br> 
 * String column3 = result.getString("COLUMN3");<br>
 * <br>
 * // Get the date value.<br> 
 * Date column4 = result.getDate("COLUMN4");<br>
 * <br>
 * // Get the array of bytes.<br> 
 * byte[] column5 = result.getBlob("COLUMN5");<br>
 * <br>
 * }<br>
 * </code> <br>
 * When you iterate result rows, you can specify the column (where to get the
 * data) by name or by column index: <br>
 * <br>
 * <code>
 * // Execute the statement to retrieve some data.<br> 
 * ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM HOME_USERS", -1, -1);<br> 
 * <br> 
 * // Iterate rows.<br> 
 * while (result.next()) {<br>
 * <br>
 *    // Get the string value of a given column name.<br> 
 *    String column3A = result.getString("COLUMN3");<br>
 * <br>
 *    // Get the string value of a given column index.<br> 
 *    String column3B = result.getString(3);<br>
 * <br>
 * }<br>
 * </code> <br>
 * Another option is to retrieve a whole row as a Java Bean object. To achieve
 * this, you have to provide to getBean a class that represents a Java Bean. A
 * new instance of this class is created and used to store the values of the
 * result columns. You may also provide a mapping where to relate result columns
 * with bean attributes. Check it out with one example:<br>
 * <br>
 * <code>
 * // Execute the statement to retrieve some data. <br>
 * ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM HOME_USERS", -1, -1);<br> 
 * <br>
 * // Map table result columns with bean attributes.<br> 
 * Map&lt;String, Object&gt; mapping = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Map result 'NAME' column with bean 'name' attribute.<br> 
 * mapping.put("NAME", "name");<br>
 * <br>
 * // Map result 'DATE_OF_BIRTH' column with bean 'dateOfBirth' attribute.<br> 
 * mapping.put("DATE_OF_BIRTH", "dateOfBirth");<br>
 * <br>
 * // Map result 'PASSWORD' column with bean 'password' attribute.<br> 
 * mapping.put("PASSWORD", "password");<br>
 * <br>
 * // Iterate rows.<br> 
 * while (result.next()) {<br>
 * <br>
 *    // Copy the values of specified columns into the User bean.<br> 
 *    User user = result.getBean(User.class, mapping);<br>
 * <br>
 * }<br>
 * </code> <br>
 * Warework can also create this mapping automatically when the columns names
 * and bean attributes follow a specific naming convention. Here are the rules
 * for columns and attributes names:<br>
 * 
 * <ul>
 * <li>Names of database columns are uppercase, for example:
 * <code>PASSWORD</code>. Names of bean attributes are lowercase, for example:
 * <code>password</code>.</li>
 * <li>Spaces in columns names are specified with the underscore character:
 * <code>DATE_OF_BIRTH</code>. The attributes of the bean use the camel
 * notation: <code>dateOfBirth</code>.</li>
 * </ul>
 * 
 * Check out more examples:<br>
 * <br>
 * <ul>
 * <li><code>NAME1</code> equals to <code>name1</code></li>
 * <li><code>NAME_A</code> equals to <code>nameA</code></li>
 * <li><code>A</code> equals to <code>a</code></li>
 * <li><code>AA</code> equals to <code>aa</code></li>
 * <li><code>A_B</code> equals to <code>aB</code></li>
 * <li><code>A_B_C</code> equals to <code>aBC</code></li>
 * </ul>
 * 
 * If <code>getBean</code> method does not receive the mapping configuration, it
 * extracts by reflection the name of every attribute that exist in the given
 * bean class. Each attribute name is converted into the corresponding column
 * name and this column name is finally used to retrieve the value from the
 * database result. If an attribute of the Java Bean does not exist as a result
 * column, then it is discarded. The following example shows how this naming
 * convention simplifies quite a lot the work:<br>
 * <br>
 * <code>
 * // Execute the statement to retrieve some data.<br> 
 * ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM HOME_USERS", -1, -1);<br> 
 * <br>
 * // Iterate rows.<br> 
 * while (result.next()) {<br>
 * <br>
 *    // Copy the values of columns found into the User bean.<br> 
 *    User user = result.getBean(User.class, null);<br>
 * <br>
 * }<br>
 * </code> <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface ResultRows {

	/**
	 * Gets the boolean value of the specified column in the current row.
	 * 
	 * @param columnIndex
	 *            First column is 1, second is 2, ...<br>
	 * <br>
	 * @return Column value as a boolean object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	Boolean getBoolean(int columnIndex) throws ClientException;

	/**
	 * Gets the boolean value of the specified column in the current row.
	 * 
	 * @param columnName
	 *            SQL name of the column.<br>
	 * <br>
	 * @return Column value as a boolean object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	Boolean getBoolean(String columnName) throws ClientException;

	/**
	 * Gets the numeric value of the specified column in the current row.
	 * 
	 * @param columnIndex
	 *            First column is 1, second is 2, ...<br>
	 * <br>
	 * @param type
	 *            Type of number to get. For example, if you provide a
	 *            <code>java.lang.Short</code> then this method returns the
	 *            value of the specified column in the current row as a
	 *            <code>java.lang.Short</code> object. You may provide
	 *            <code>java.lang.Short</code>, <code>java.lang.Integer</code>,
	 *            <code>java.lang.Long</code>, <code>java.lang.Float</code>,
	 *            <code>java.lang.Double</code> and
	 *            <code>java.math.BigDecimal</code>.<br>
	 * <br>
	 * @param <E>
	 *            Number type.<br>
	 * <br>
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	<E extends Number> E getNumber(int columnIndex, Class<E> type)
			throws ClientException;

	/**
	 * Gets the numeric value of the specified column in the current row.
	 * 
	 * @param columnName
	 *            SQL name of the column.<br>
	 * <br>
	 * @param type
	 *            Type of number to get. For example, if you provide a
	 *            <code>java.lang.Short</code> then this method returns the
	 *            value of the specified column in the current row as a
	 *            <code>java.lang.Short</code> object. You may provide
	 *            <code>java.lang.Short</code>, <code>java.lang.Integer</code>,
	 *            <code>java.lang.Long</code>, <code>java.lang.Float</code>,
	 *            <code>java.lang.Double</code> and
	 *            <code>java.math.BigDecimal</code>.<br>
	 * <br>
	 * @param <E>
	 *            Number type.<br>
	 * <br>
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	<E extends Number> E getNumber(String columnName, Class<E> type)
			throws ClientException;

	/**
	 * Gets the string value of the specified column in the current row.
	 * 
	 * @param columnIndex
	 *            First column is 1, second is 2, ...<br>
	 * <br>
	 * @return Column value as a string object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	String getString(int columnIndex) throws ClientException;

	/**
	 * Gets the string value of the specified column in the current row.
	 * 
	 * @param columnName
	 *            SQL name of the column.<br>
	 * <br>
	 * @return Column value as a string object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	String getString(String columnName) throws ClientException;

	/**
	 * Gets the date value of the specified column in the current row.
	 * 
	 * @param columnIndex
	 *            First column is 1, second is 2, ...<br>
	 * <br>
	 * @return Column value as a date object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	Date getDate(int columnIndex) throws ClientException;

	/**
	 * Gets the date value of the specified column in the current row.
	 * 
	 * @param columnName
	 *            SQL name of the column.<br>
	 * <br>
	 * @return Column value as a date object; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	Date getDate(String columnName) throws ClientException;

	/**
	 * Gets the blob value of the specified column in the current row.
	 * 
	 * @param columnIndex
	 *            First column is 1, second is 2, ...<br>
	 * <br>
	 * @return Column value as an array of bytes; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	byte[] getBlob(int columnIndex) throws ClientException;

	/**
	 * Gets the blob value of the specified column in the current row.
	 * 
	 * @param columnName
	 *            SQL name of the column.<br>
	 * <br>
	 * @return Column value as an array of bytes; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	byte[] getBlob(String columnName) throws ClientException;

	/**
	 * Gets the Java Bean that represents the current row. This method creates a
	 * new instance of a bean/class provided and each database result column is
	 * associated with an attribute of the bean. You can provide a mapping where
	 * to relate result columns with bean attributes. If this mapping is not
	 * provided, then this method tries to automatically assign the values of
	 * the result columns in the corresponding bean attributes. For example: if
	 * an <code>User</code> bean has an attribute named 'password', then this
	 * method looks for a column named 'PASSWORD'; if attribute is named
	 * 'dateOfBirth', then it looks for column 'DATE_OF_BIRTH' and so on.
	 * 
	 * @param beanType
	 *            Bean class. This parameter is mandatory.<br>
	 * <br>
	 * @param mapping
	 *            Map where the keys represent database column names and each
	 *            value represents the attribute in the bean where to set the
	 *            value of the database column. For example:
	 *            {key=USER_NAME;value=userName} means that database result
	 *            column name is "USER_NAME" and its value is assigned to
	 *            "userName" attribute at given Java bean (via "setUserName"
	 *            method). Keys can also be <code>java.lang.Integer</code>
	 *            objects that represent the column index. For example:
	 *            {key=2;value=userName}. With column index, first column is 1,
	 *            second is 2 and so on. This parameter is optional.<br>
	 * <br>
	 * @param <E>
	 *            Bean type.<br>
	 * <br>
	 * @return Java bean that represents the current row.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to load the value.<br>
	 * <br>
	 */
	<E> E getBean(Class<E> beanType, Map<Object, Object> mapping)
			throws ClientException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Moves the cursor down one row from its current position. The cursor is
	 * initially positioned before the first row; the first call to the method
	 * makes the first row the current row; the second call makes the second row
	 * the current row, and so on.
	 * 
	 * @return <code>true</code> if the new current row is valid;
	 *         <code>false</code> if there are no more rows.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to retrieve the next value.<br>
	 * <br>
	 */
	boolean next() throws ClientException;

	/**
	 * Gets the current row number. Initially it's 0. The first row is 1, the
	 * second row 2 and so on.
	 * 
	 * @return Current row number.<br>
	 * <br>
	 */
	int row();

	/**
	 * Closes the database result set.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to close the Data Store
	 *             result.<br>
	 * <br>
	 */
	void close() throws ClientException;

}
