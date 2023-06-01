package com.warework.module.business.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.datastore.client.ResultRows;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractQuery<T> {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private ScopeFacade scope;

	//
	private Class<? extends Dao<T>> daoType;

	//
	private String queryId;

	//
	private Map<String, Object> variables;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private AbstractQuery() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param result
	 * @param locale
	 * @return
	 * @throws ClientException
	 */
	protected abstract T mapRow(final ResultRows result, final Locale locale) throws ClientException;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param scope
	 * @param daoType
	 */
	protected AbstractQuery(final ScopeFacade scope, final Class<? extends Dao<T>> daoType) {

		//
		this();

		//
		this.scope = scope;

		//
		this.queryId = ReflectionL2Helper.getClassName(getClass());
		this.queryId = StringL2Helper
				.firstLetterToLowerCase(this.queryId.substring(this.queryId.indexOf('$') + 1, this.queryId.length()));

		//
		this.daoType = daoType;

	}

	/**
	 * 
	 * @param scope
	 * @param daoType
	 * @param queryId
	 */
	protected AbstractQuery(final ScopeFacade scope, final Class<? extends Dao<T>> daoType, final String queryId) {

		//
		this();

		//
		this.scope = scope;

		//
		this.queryId = queryId;

		//
		this.daoType = daoType;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	public Class<? extends Dao<T>> getDAOType() {
		return daoType;
	}

	/**
	 * 
	 * @return
	 */
	public String getQueryId() {
		return queryId;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getVariables() {
		return variables;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * This method should be overriden to update the SQL statement that represents
	 * the query before executing it.
	 * 
	 * @param statement
	 * @return
	 */
	protected String updateSQL(final String statement) {
		return statement;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param key
	 * @param value
	 */
	protected final void addVariable(final String key, final Object value) {

		//
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}

		//
		variables.put(key, value);

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	protected final Object getVariableValue(final String key) {
		return (variables == null) ? null : variables.get(key);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final Boolean getBoolean(final ResultRows result, final Properties alias, final String prefix,
			final String table, final String column) throws ClientException {

		//
		final Number value = getNumber(result, Integer.class, alias, prefix, table, column);

		//
		if ((value != null) && (value.intValue() == 1)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	/**
	 * 
	 * @param result
	 * @param type
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final <E extends Number> E getNumber(final ResultRows result, final Class<E> type,
			final Properties alias, final String prefix, final String table, final String column)
			throws ClientException {
		return result.getNumber(createColumnName(alias, prefix, table, column), type);
	}

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final String getString(final ResultRows result, final Properties alias, final String prefix,
			final String table, final String column) throws ClientException {
		return result.getString(createColumnName(alias, prefix, table, column));
	}

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final char[] getPassword(final ResultRows result, final Properties alias, final String prefix,
			final String table, final String column) throws ClientException {

		//
		final String password = result.getString(createColumnName(alias, prefix, table, column));

		//
		return (password == null) ? null : password.toCharArray();

	}

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final Date getDate(final ResultRows result, final Properties alias, final String prefix,
			final String table, final String column) throws ClientException {
		return result.getDate(createColumnName(alias, prefix, table, column));
	}

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param tableName
	 * @param columnName
	 * @param ddbbTimeZone   Source timezone of the date stored in the database.
	 * @param targetTimeZone
	 * @return
	 * @throws ClientException
	 */
	protected static Date toDateFromStringFix(final ResultRows result, final Properties alias, final String prefix,
			final String tableName, final String columnName, final TimeZone ddbbTimeZone, final TimeZone targetTimeZone)
			throws ClientException {

		//
		final String value = getString(result, alias, prefix, tableName, columnName + "_STRING");

		//
		if (value == null) {
			return null;
		} else {
			try {
				return DateL2Helper.toDate(
						DateL2Helper.toString(DateL2Helper.toDate(value, ddbbTimeZone, "yyyy-MM-dd HH:mm:ss"),
								targetTimeZone, null, DateL2Helper.DATE_PATTERN_DDMMYYYY_HHMMSSSSS),
						targetTimeZone, DateL2Helper.DATE_PATTERN_DDMMYYYY_HHMMSSSSS);
			} catch (final ParseException e) {
				throw new ClientException(null, "", e, -1);
			}
		}

	}

	/**
	 * 
	 * @param result
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 * @throws ClientException
	 */
	protected static final byte[] getBlob(final ResultRows result, final Properties alias, final String prefix,
			final String table, final String column) throws ClientException {
		return result.getBlob(createColumnName(alias, prefix, table, column));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param prefix
	 * @param alias
	 * @return
	 */
	protected static String updatePrefix(final String prefix, final String alias) {
		if (prefix == null) {
			return alias;
		} else {
			return prefix + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + alias;
		}
	}

	/**
	 * 
	 * @param prefix
	 * @param alias
	 * @param id
	 * @return
	 */
	protected static String updatePrefix(final String prefix, final String alias, final int id) {
		if (prefix == null) {
			return alias + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + id;
		} else {
			return prefix + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + alias
					+ ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + id;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param name
	 * @return
	 */
	protected static String createAliasMapFilePath(final String name) {
		return createAliasMapFilePath(ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF, name);
	}

	/**
	 * 
	 * @param basePath
	 * @param name
	 * @return
	 */
	protected static String createAliasMapFilePath(final String basePath, final String name) {
		if (name == null) {
			return basePath + ResourceL1Helper.DIRECTORY_SEPARATOR + "mapping" + ResourceL1Helper.DIRECTORY_SEPARATOR
					+ "table-alias" + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
					+ ResourceL2Helper.FILE_EXTENSION_PROPERTIES;
		} else {
			return basePath + ResourceL1Helper.DIRECTORY_SEPARATOR + "mapping" + ResourceL1Helper.DIRECTORY_SEPARATOR
					+ "table-alias" + ResourceL2Helper.FILE_EXTENSION_SEPARATOR + name
					+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_PROPERTIES;
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param alias
	 * @param prefix
	 * @param table
	 * @param column
	 * @return
	 */
	private static String createColumnName(final Properties alias, final String prefix, final String table,
			final String column) {
		if (prefix == null) {
			return alias.getProperty(table) + AbstractDao.COLUMN_WORD_SEPARATOR + column;
		} else {
			return alias.getProperty(prefix + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + table)
					+ AbstractDao.COLUMN_WORD_SEPARATOR + column;
		}
	}

}
