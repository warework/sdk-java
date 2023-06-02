package com.warework.service.datastore.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Common operations for database result sets.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractResultRows implements ResultRows {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Columns words separator.

	private static final String COLUMN_WORD_SEPARATOR = StringL1Helper.CHARACTER_UNDERSCORE;

	// STRINGS

	protected static final String STRING_SET = "set";

	// FIELD NAMES

	protected static final String FIELD_NAME_THIS = "this$";

	protected static final String FIELD_NAME_CLASS = "class$";

	protected static final String FIELD_NAME_SERIAL_VERSION_UID = "serialVersionUID";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Data Store.
	private AbstractDatastore datastore;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the column index for a given column name.
	 * 
	 * @param columnName Name of the column.<br>
	 *                   <br>
	 * @return Column index.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to get the column
	 *                         index.<br>
	 *                         <br>
	 */
	protected abstract int getColumnIndex(final String columnName) throws ClientException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the boolean value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a boolean object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	protected abstract Boolean loadBoolean(final int columnIndex) throws ClientException;

	/**
	 * Gets the numeric value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @param type        Type of number to get. For example, if you provide a
	 *                    <code>java.lang.Short</code> then this method returns the
	 *                    value of the specified column in the current row as a
	 *                    <code>java.lang.Short</code> object. You may provide
	 *                    <code>java.lang.Short</code>,
	 *                    <code>java.lang.Integer</code>,
	 *                    <code>java.lang.Long</code>, <code>java.lang.Float</code>,
	 *                    <code>java.lang.Double</code> and
	 *                    <code>java.math.BigDecimal</code>.<br>
	 *                    <br>
	 * @param <E>         The type of number.<br>
	 *                    <br>
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	protected abstract <E extends Number> E loadNumber(final int columnIndex, final Class<E> type)
			throws ClientException;

	/**
	 * Gets the string value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a string object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	protected abstract String loadString(final int columnIndex) throws ClientException;

	/**
	 * Gets the date value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a date object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	protected abstract Date loadDate(final int columnIndex) throws ClientException;

	/**
	 * Gets the blob value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as an array of bytes; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	protected abstract byte[] loadBlob(final int columnIndex) throws ClientException;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	private AbstractResultRows() {
		// DO NOTHInG.
	}

	/**
	 * Sets up a wrapper for a database result set.
	 * 
	 * @param datastore Data Store.<br>
	 *                  <br>
	 */
	protected AbstractResultRows(final AbstractDatastore datastore) {

		// Invoke default constructor.
		this();

		// Set the Data Store.
		this.datastore = datastore;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the boolean value of the specified column in the current row.
	 * 
	 * @param columnName SQL name of the column.<br>
	 *                   <br>
	 * @return Column value as a boolean object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public Boolean getBoolean(final String columnName) throws ClientException {
		return getBoolean(getColumnIndex(columnName));
	}

	/**
	 * Gets the numeric value of the specified column in the current row.
	 * 
	 * @param columnName SQL name of the column.<br>
	 *                   <br>
	 * @param type       Type of number to get. For example, if you provide a
	 *                   <code>java.lang.Short</code> then this method returns the
	 *                   value of the specified column in the current row as a
	 *                   <code>java.lang.Short</code> object. You may provide
	 *                   <code>java.lang.Short</code>,
	 *                   <code>java.lang.Integer</code>,
	 *                   <code>java.lang.Long</code>, <code>java.lang.Float</code>,
	 *                   <code>java.lang.Double</code> and
	 *                   <code>java.math.BigDecimal</code>.<br>
	 *                   <br>
	 * @param <E>        Number type.<br>
	 *                   <br>
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public <E extends Number> E getNumber(final String columnName, final Class<E> type) throws ClientException {
		return getNumber(getColumnIndex(columnName), type);
	}

	/**
	 * Gets the string value of the specified column in the current row.
	 * 
	 * @param columnName SQL name of the column.<br>
	 *                   <br>
	 * @return Column value as a string object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public String getString(final String columnName) throws ClientException {
		return getString(getColumnIndex(columnName));
	}

	/**
	 * Gets the date value of the specified column in the current row.
	 * 
	 * @param columnName SQL name of the column.<br>
	 *                   <br>
	 * @return Column value as a date object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public Date getDate(final String columnName) throws ClientException {
		return getDate(getColumnIndex(columnName));
	}

	/**
	 * Gets the blob value of the specified column in the current row.
	 * 
	 * @param columnName SQL name of the column.<br>
	 *                   <br>
	 * @return Column value as an array of bytes; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public byte[] getBlob(final String columnName) throws ClientException {
		return getBlob(getColumnIndex(columnName));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the boolean value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a boolean object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public Boolean getBoolean(final int columnIndex) throws ClientException {
		if (columnIndex > 0) {
			return loadBoolean(columnIndex);
		} else {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' because given index is less than zero. With column index, first column is 1, second is 2, and so on.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the numeric value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @param type        Type of number to get. For example, if you provide a
	 *                    <code>java.lang.Short</code> then this method returns the
	 *                    value of the specified column in the current row as a
	 *                    <code>java.lang.Short</code> object. You may provide
	 *                    <code>java.lang.Short</code>,
	 *                    <code>java.lang.Integer</code>,
	 *                    <code>java.lang.Long</code>, <code>java.lang.Float</code>,
	 *                    <code>java.lang.Double</code> and
	 *                    <code>java.math.BigDecimal</code>.<br>
	 *                    <br>
	 * @param <E>         Number type.<br>
	 *                    <br>
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public <E extends Number> E getNumber(final int columnIndex, final Class<E> type) throws ClientException {
		if (columnIndex > 0) {
			return loadNumber(columnIndex, type);
		} else {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' because given index is less than zero. With column index, first column is 1, second is 2, and so on.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the string value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a string object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public String getString(final int columnIndex) throws ClientException {
		if (columnIndex > 0) {
			return loadString(columnIndex);
		} else {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' because given index is less than zero. With column index, first column is 1, second is 2, and so on.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the date value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as a date object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public Date getDate(final int columnIndex) throws ClientException {
		if (columnIndex > 0) {
			return loadDate(columnIndex);
		} else {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' because given index is less than zero. With column index, first column is 1, second is 2, and so on.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the blob value of the specified column in the current row.
	 * 
	 * @param columnIndex First column is 1, second is 2, ...<br>
	 *                    <br>
	 * @return Column value as an array of bytes; if the value is SQL NULL, the
	 *         value returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public byte[] getBlob(final int columnIndex) throws ClientException {
		if (columnIndex > 0) {
			return loadBlob(columnIndex);
		} else {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' because given index is less than zero. With column index, first column is 1, second is 2, and so on.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies each row of a database result into a list of Java Beans (each bean
	 * stores the values of the columns for every row). You can provide a mapping
	 * where to relate result columns with bean attributes. If this mapping is not
	 * provided, then this method tries to automatically assign the values of the
	 * result columns in the corresponding bean attributes. For example: if an
	 * <code>User</code> bean has an attribute named 'password', then this method
	 * looks for a column named 'PASSWORD'; if attribute is named 'dateOfBirth',
	 * then it looks for column 'DATE_OF_BIRTH' and so on.
	 * 
	 * @param resultRows Database result.<br>
	 *                   <br>
	 * @param beanType   Bean class. This parameter is mandatory.<br>
	 *                   <br>
	 * @param mapping    Map where the keys represent database column names and each
	 *                   value represents the attribute in the bean where to set the
	 *                   value of the database column. For example:
	 *                   {key=USER_NAME;value=userName} means that database result
	 *                   column name is "USER_NAME" and its value is assigned to
	 *                   "userName" attribute at given Java bean (via "setUserName"
	 *                   method). Keys can also be <code>java.lang.Integer</code>
	 *                   objects that represent the column index. For example:
	 *                   {key=2;value=userName}. With column index, first column is
	 *                   1, second is 2 and so on. This parameter is optional.<br>
	 *                   <br>
	 * @param <E>        List type.<br>
	 *                   <br>
	 * @return List with Java Beans where each bean represents a result row.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public static <E> List<E> toList(final ResultRows resultRows, final Class<E> beanType,
			final Map<Object, Object> mapping) throws ClientException {

		// Prepare the list to return.
		final List<E> resultList = new ArrayList<E>();

		// Copy each bean into the list.
		while (resultRows.next()) {
			resultList.add(resultRows.getBean(beanType, mapping));
		}

		// Return the list of Java Beans.
		return resultList;

	}

	/**
	 * Copies the first row of a database result into a Java Bean (each bean stores
	 * the values of the columns for the row). You can provide a mapping where to
	 * relate result columns with bean attributes. If this mapping is not provided,
	 * then this method tries to automatically assign the values of the result
	 * columns in the corresponding bean attributes. For example: if an
	 * <code>User</code> bean has an attribute named 'password', then this method
	 * looks for a column named 'PASSWORD'; if attribute is named 'dateOfBirth',
	 * then it looks for column 'DATE_OF_BIRTH' and so on.
	 * 
	 * @param resultRows Database result.<br>
	 *                   <br>
	 * @param beanType   Bean class. This parameter is mandatory.<br>
	 *                   <br>
	 * @param mapping    Map where the keys represent database column names and each
	 *                   value represents the attribute in the bean where to set the
	 *                   value of the database column. For example:
	 *                   {key=USER_NAME;value=userName} means that database result
	 *                   column name is "USER_NAME" and its value is assigned to
	 *                   "userName" attribute at given Java bean (via "setUserName"
	 *                   method). Keys can also be <code>java.lang.Integer</code>
	 *                   objects that represent the column index. For example:
	 *                   {key=2;value=userName}. With column index, first column is
	 *                   1, second is 2 and so on. This parameter is optional.<br>
	 *                   <br>
	 * @param <E>        Bean type.<br>
	 *                   <br>
	 * @return Bean than represents a result row.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to create the
	 *                         beans.<br>
	 *                         <br>
	 */
	public static <E> E toBean(final ResultRows resultRows, final Class<E> beanType, final Map<Object, Object> mapping)
			throws ClientException {

		// Get the list of beans.
		final List<E> resultList = toList(resultRows, beanType, mapping);

		// Return the first row/bean.
		if ((resultList != null) && (resultList.size() > 0)) {
			return resultList.get(0);
		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Data Store.
	 * 
	 * @return Data Store.<br>
	 *         <br>
	 */
	protected AbstractDatastore getDatastore() {
		return datastore;
	}

	/**
	 * Gets the Scope.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected ScopeFacade getScopeFacade() {
		return getDatastore().getService().getScopeFacade();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the corresponding column name for a given bean attribute.
	 * 
	 * @param beanAttribute Attribute name of the bean.<br>
	 *                      <br>
	 * @return Database column name.<br>
	 *         <br>
	 */
	protected String toColumnName(final String beanAttribute) {

		// Initialize the name of the column.
		String columnName = CommonValueL1Constants.STRING_EMPTY;

		// Transform the bean attribute name into a database column name.
		for (int i = 0; i < beanAttribute.length(); i++) {
			if (Character.isUpperCase(beanAttribute.charAt(i))) {
				if (i > 0) {
					columnName = columnName + COLUMN_WORD_SEPARATOR + beanAttribute.substring(i, i + 1);
				} else {
					columnName = columnName + beanAttribute.substring(i, i + 1);
				}
			} else {
				columnName = columnName + beanAttribute.substring(i, i + 1).toUpperCase();
			}
		}

		// Return the database column name.
		return columnName;

	}

}
