package com.warework.service.datastore.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Wrapper for a JDBC <code>java.sql.ResultSet</code> object. Please, use the
 * <code>com.warework.service.datastore.client.ResultRows</code> interface
 * instead of this implementation class.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class JdbcResultRows extends AbstractResultRows {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// JDBC Result set.
	private ResultSet result;

	// Current row number.
	private int row = -1;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets up a wrapper for a JDBC Result Set.
	 * 
	 * @param datastore Data Store.<br>
	 *                  <br>
	 * @param resultSet JDBC Result set.<br>
	 *                  <br>
	 */
	JdbcResultRows(final AbstractDatastore datastore, final ResultSet resultSet) {

		// Set the Data Store.
		super(datastore);

		// Set the JDBC Result set.
		this.result = resultSet;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Java Bean that represents the current row. This method creates a new
	 * instance of a bean/class provided and each database result column is
	 * associated with an attribute of the bean. You can provide a mapping where to
	 * relate result columns with bean attributes. If this mapping is not provided,
	 * then this method tries to automatically assign the values of the result
	 * columns in the corresponding bean attributes. For example: if an
	 * <code>User</code> bean has an attribute named 'password', then this method
	 * looks for a column named 'PASSWORD'; if attribute is named 'dateOfBirth',
	 * then it looks for column 'DATE_OF_BIRTH' and so on.
	 * 
	 * @param beanType Bean class. This parameter is mandatory.<br>
	 *                 <br>
	 * @param mapping  Map where the keys represent database column names and each
	 *                 value represents the attribute in the bean where to set the
	 *                 value of the database column. For example:
	 *                 {key=USER_NAME;value=userName} means that database result
	 *                 column name is "USER_NAME" and its value is assigned to
	 *                 "userName" attribute at given Java bean (via "setUserName"
	 *                 method). Keys can also be <code>java.lang.Integer</code>
	 *                 objects that represent the column index. For example:
	 *                 {key=2;value=userName}. With column index, first column is 1,
	 *                 second is 2 and so on. This parameter is optional.<br>
	 *                 <br>
	 * @return Java bean that represents the current row.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	public <E> E getBean(final Class<E> beanType, final Map<Object, Object> mapping) throws ClientException {

		// Create a new instance of the bean.
		E bean = null;
		try {
			bean = beanType.newInstance();
		} catch (final Exception e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the Java Bean for row '" + row()
							+ "' because a new instance of bean type '" + beanType.getName()
							+ "' cannot be created. Check out this error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Copy the values of the columns in the attibutes of the bean.
		if ((mapping != null) && (mapping.size() > 0)) {

			// Get the column names.
			final Set<Object> tableColumns = mapping.keySet();

			// Set the value of each column in the associated attribute of the
			// Java Bean.
			try {
				for (final Iterator<Object> iterator = tableColumns.iterator(); iterator.hasNext();) {

					// Get the name of one column.
					final Object tableColumn = iterator.next();

					// Map the given column name to its column index.
					int columnIndex = -1;
					if (tableColumn instanceof String) {
						columnIndex = getColumnIndex((String) tableColumn);
					} else if (tableColumn instanceof Integer) {
						columnIndex = ((Integer) tableColumn).intValue();
					} else {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot retrieve the Java Bean for row '" + row()
										+ "' because a key of the given Map is not a String or an Integer object."
										+ beanType.getName() + "'.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Set the value of the column in the attribute of the bean.
					if (columnIndex > 0) {

						// Get the attribute of the bean.
						final String objectAttribute = (String) mapping.get(tableColumn);

						// Get the attribute object.
						final Field field = ReflectionL2Helper.getField(beanType, objectAttribute);

						// Get the type of the attribute.
						final Class<?> attributeType = field.getType();

						// Get the value of the column.
						Object value = null;
						if (attributeType.equals(Boolean.class)) {
							value = getBoolean(columnIndex);
						} else if (attributeType.equals(Short.class)) {
							value = getNumber(columnIndex, Short.class);
						} else if (attributeType.equals(Integer.class)) {
							value = getNumber(columnIndex, Integer.class);
						} else if (attributeType.equals(Long.class)) {
							value = getNumber(columnIndex, Long.class);
						} else if (attributeType.equals(Float.class)) {
							value = getNumber(columnIndex, Float.class);
						} else if (attributeType.equals(Double.class)) {
							value = getNumber(columnIndex, Double.class);
						} else if ((attributeType.equals(BigDecimal.class)) || (attributeType.equals(Number.class))) {
							value = getNumber(columnIndex, BigDecimal.class);
						} else if (attributeType.equals(String.class)) {
							value = getString(columnIndex);
						} else if (attributeType.equals(Date.class)) {
							value = getDate(columnIndex);
						} else if (attributeType.equals(byte[].class)) {
							value = getBlob(columnIndex);
						} else {
							throw new ClientException(getScopeFacade(),
									"WAREWORK cannot retrieve the Java Bean for row '" + row() + "' because column '"
											+ columnIndex + "' does not match the bean ('" + beanType.getName()
											+ "') attribute of type '" + attributeType.getName() + "'.",
									null, LogServiceConstants.LOG_LEVEL_WARN);
						}

						// Set the value in the bean only if it is not null.
						if (value != null) {
							setBeanAttributeValue(bean, field.getName(), attributeType, value);
						}
					} else {
						throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the Java Bean for row '"
								+ row()
								+ "' because given column name with mapping parameter is not found in the database result set.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

				}
			} catch (final ClientException e) {
				throw e;
			} catch (final Exception e) {
				throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the Java Bean for row '" + row()
						+ "' because given bean type '" + beanType.getName()
						+ "' does not accept the specified attribute. Review the bean and the mapping settings. Also check out this error: "
						+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {

			// Get the fileds of the bean.
			final Field[] fields = ReflectionL2Helper.getFields(beanType);

			// Set the database values for every bean attribute.
			if ((fields != null) && (fields.length > 0)) {
				for (int i = 0; i < fields.length; i++) {

					// Get one bean attribute.
					final Field field = fields[i];

					// Transform the attribute name into a column name.
					final String columnName = toColumnName(field.getName());

					// Map the given column name to its column index.
					int columnIndex = -1;
					try {
						columnIndex = getColumnIndex(columnName);
					} catch (final ClientException e) {
						// DO NOTHING
					}

					// Set the database value only if column exists.
					if (columnIndex > 0) {

						// Get the type of the bean attribute.
						final Class<?> attributeType = field.getType();

						// Get the value of the column.
						Object value = null;
						if (attributeType.equals(Boolean.class)) {
							value = getBoolean(columnIndex);
						} else if (attributeType.equals(Short.class)) {
							value = getNumber(columnIndex, Short.class);
						} else if (attributeType.equals(Integer.class)) {
							value = getNumber(columnIndex, Integer.class);
						} else if (attributeType.equals(Long.class)) {
							value = getNumber(columnIndex, Long.class);
						} else if (attributeType.equals(Float.class)) {
							value = getNumber(columnIndex, Float.class);
						} else if (attributeType.equals(Double.class)) {
							value = getNumber(columnIndex, Double.class);
						} else if ((attributeType.equals(BigDecimal.class)) || (attributeType.equals(Number.class))) {
							value = getNumber(columnIndex, BigDecimal.class);
						} else if (attributeType.equals(String.class)) {
							value = getString(columnIndex);
						} else if (attributeType.equals(Date.class)) {
							value = getDate(columnIndex);
						} else if (attributeType.equals(byte[].class)) {
							value = getBlob(columnIndex);
						}

						// Set the value in the bean only if it is not null.
						if (value != null) {
							try {
								setBeanAttributeValue(bean, field.getName(), attributeType, value);
							} catch (final Exception e) {
								throw new ClientException(getScopeFacade(),
										"WAREWORK cannot retrieve the Java Bean for row '" + row()
												+ "' because given bean type '" + beanType.getName()
												+ "' does not accept a result column value. Check out this error: "
												+ e.getMessage(),
										e, LogServiceConstants.LOG_LEVEL_WARN);
							}
						}

					}

				}

			}

		}

		// Return the bean with the values of the columns.
		return bean;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Moves the cursor down one row from its current position. The cursor is
	 * initially positioned before the first row; the first call to the method makes
	 * the first row the current row; the second call makes the second row the
	 * current row, and so on.
	 * 
	 * @return <code>true</code> if the new current row is valid; <code>false</code>
	 *         if there are no more rows.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to retrieve the next
	 *                         value.<br>
	 *                         <br>
	 */
	public boolean next() throws ClientException {
		try {

			// Move the cursor down one row.
			final boolean valid = result.next();

			// Increment row counter.
			if (valid) {
				row = row + 1;
			}

			// Return if the new current row is valid.
			return valid;

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot move the cursor down one row because the database result set reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Gets the current row number. Initially it's 0. The first row is 1, the second
	 * row 2 and so on.
	 * 
	 * @return Current row number.<br>
	 *         <br>
	 */
	public int row() {
		return (row + 1);
	}

	/**
	 * Releases the ResultSet object's database and JDBC resources immediately
	 * instead of waiting for this to happen when it is automatically closed. A
	 * ResultSet object is also automatically closed when it is garbage collected.
	 * 
	 * @throws ClientException If there is an error when trying to close the Data
	 *                         Store result.<br>
	 *                         <br>
	 */
	public void close() throws ClientException {
		try {
			result.close();
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot close the database result set because it reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
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
	protected Boolean loadBoolean(final int columnIndex) throws ClientException {

		// Get the value from the database.
		Object value = null;
		try {
			value = result.getObject(columnIndex);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' and column '" + columnIndex
					+ "' (first column is 1, second is 2, ...) because the database result reported the following error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the value.
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean) value;
			} else {
				throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
						+ "' and column '" + columnIndex
						+ "' (first column is 1, second is 2, ...) because the database result is not a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// SQL value is null.
		return null;

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
	 * @return Column value as a <code>Short</code>, <code>Integer</code>,
	 *         <code>Long</code>, <code>Float</code>, <code>Double</code> or
	 *         <code>BigDecimal</code> object; if the value is SQL NULL, the value
	 *         returned is <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to load the
	 *                         value.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	protected <E extends Number> E loadNumber(final int columnIndex, final Class<E> type) throws ClientException {

		// Get the value from the database.
		Object value = null;
		try {
			value = result.getObject(columnIndex);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' and column '" + columnIndex
					+ "' (first column is 1, second is 2, ...) because the database result reported the following error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the value.
		if (value != null) {
			if (value instanceof Number) {

				// Get the value as a number.
				final Number number = (Number) value;

				// Return the numeric value.
				if (type.equals(BigDecimal.class)) {
					return (number instanceof BigDecimal) ? (E) number : (E) new BigDecimal(number.doubleValue());
				} else if (type.equals(Double.class)) {
					return (number instanceof Double) ? (E) number : (E) Double.valueOf(number.doubleValue());
				} else if (type.equals(Float.class)) {
					return (number instanceof Float) ? (E) number : (E) Float.valueOf(number.floatValue());
				} else if (type.equals(Long.class)) {
					return (number instanceof Long) ? (E) number : (E) Long.valueOf(number.longValue());
				} else if (type.equals(Integer.class)) {
					return (number instanceof Integer) ? (E) number : (E) Integer.valueOf(number.intValue());
				} else if (type.equals(Short.class)) {
					return (number instanceof Short) ? (E) number : (E) Short.valueOf(number.shortValue());
				} else {
					throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
							+ "' and column '" + columnIndex
							+ "' (first column is 1, second is 2, ...) because given number type is not supported.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
						+ "' and column '" + columnIndex
						+ "' (first column is 1, second is 2, ...) because the database result is not a numeric value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// SQL value is null.
		return null;

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
	protected String loadString(final int columnIndex) throws ClientException {
		try {
			return result.getString(columnIndex);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' and column '" + columnIndex
					+ "' (first column is 1, second is 2, ...) because the database result reported the following error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
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
	protected Date loadDate(final int columnIndex) throws ClientException {
		try {
			return result.getDate(columnIndex);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' and column '" + columnIndex
					+ "' (first column is 1, second is 2, ...) because the database result reported the following error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
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
	protected byte[] loadBlob(final int columnIndex) throws ClientException {
		try {
			return result.getBytes(columnIndex);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot retrieve the value for row '" + row()
					+ "' and column '" + columnIndex
					+ "' (first column is 1, second is 2, ...) because the database result reported the following error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the column index for a given column name.
	 * 
	 * @param columnName Name of the column.<br>
	 *                   <br>
	 * @return Column index.<br>
	 *         <br>
	 */
	protected int getColumnIndex(final String columnName) throws ClientException {
		try {
			return result.findColumn(columnName);
		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot retrieve the value for row '" + row() + "' and column '" + columnName
							+ "' because the database result reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the value for an attribute in a bean by calling a 'set' method.
	 * 
	 * @param bean          Object where to invoke the 'set' method.<br>
	 *                      <br>
	 * @param attribute     Name of the property that exists in the bean.<br>
	 *                      <br>
	 * @param attributeType Type of the property that exists in the bean.<br>
	 *                      <br>
	 * @param value         Value to set for the attribute of the bean.<br>
	 *                      <br>
	 * @throws SecurityException         If there is an error when trying to set the
	 *                                   value.<br>
	 *                                   <br>
	 * @throws NoSuchMethodException     If there is an error when trying to set the
	 *                                   value.<br>
	 *                                   <br>
	 * @throws IllegalArgumentException  If there is an error when trying to set the
	 *                                   value.<br>
	 *                                   <br>
	 * @throws IllegalAccessException    If there is an error when trying to set the
	 *                                   value.<br>
	 *                                   <br>
	 * @throws InvocationTargetException If there is an error when trying to set the
	 *                                   value.<br>
	 *                                   <br>
	 */
	private void setBeanAttributeValue(final Object bean, final String attribute, final Class<?> attributeType,
			final Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		// Create the SET method for the bean.
		final String methodName = STRING_SET + StringL1Helper.firstLetterToUpperCase(attribute);

		// Get the SET method of the bean.
		final Method method = bean.getClass().getMethod(methodName, new Class[] { attributeType });

		// Set the value of the column in the attribute of the bean.
		method.invoke(bean, new Object[] { value });

	}

}
