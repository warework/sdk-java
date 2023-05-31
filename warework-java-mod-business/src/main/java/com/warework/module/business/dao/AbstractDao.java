package com.warework.module.business.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.datastore.client.ResultRows;
import com.warework.service.datastore.view.RdbmsView;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractDao<T> implements Dao<T> {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	public static final String DEFAULT_STATEMENT_PATH = ResourceL1Helper.DIRECTORY_SEPARATOR
			+ ResourceL2Helper.DIRECTORY_META_INF + ResourceL1Helper.DIRECTORY_SEPARATOR + "statement"
			+ ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL;

	// Characters to be removed on each text file.
	// WARNING!!!: Characters in array must be sorted. Use
	// "Arrays.sort(your-array)".
	private static final char[] SQL_FILTER_CHARS = new char[] { '\t', '\n' };

	// OPERATION TYPES

	private static final String OPERATION_INSERT = "insert";

	private static final String OPERATION_UPDATE = "update";

	private static final String OPERATION_DELETE = "delete";

	private static final String OPERATION_SELECT = "select";

	private static final String OPERATION_FIND = "find";

	// MISC.

	protected static final String COLUMN_WORD_SEPARATOR = StringL2Helper.CHARACTER_UNDERSCORE;

	// TABLE ALIAS

	// --

	protected static final String ALIAS_TABLE_A = "A";

	protected static final String ALIAS_TABLE_B = "B";

	protected static final String ALIAS_TABLE_C = "C";

	protected static final String ALIAS_TABLE_D = "D";

	protected static final String ALIAS_TABLE_E = "E";

	protected static final String ALIAS_TABLE_F = "F";

	protected static final String ALIAS_TABLE_G = "G";

	protected static final String ALIAS_TABLE_H = "H";

	protected static final String ALIAS_TABLE_I = "I";

	protected static final String ALIAS_TABLE_J = "J";

	protected static final String ALIAS_TABLE_K = "K";

	protected static final String ALIAS_TABLE_L = "L";

	protected static final String ALIAS_TABLE_M = "M";

	protected static final String ALIAS_TABLE_N = "N";

	protected static final String ALIAS_TABLE_O = "O";

	protected static final String ALIAS_TABLE_P = "P";

	protected static final String ALIAS_TABLE_Q = "Q";

	protected static final String ALIAS_TABLE_R = "R";

	protected static final String ALIAS_TABLE_S = "S";

	protected static final String ALIAS_TABLE_T = "T";

	protected static final String ALIAS_TABLE_U = "U";

	protected static final String ALIAS_TABLE_V = "V";

	protected static final String ALIAS_TABLE_W = "W";

	protected static final String ALIAS_TABLE_X = "X";

	protected static final String ALIAS_TABLE_Y = "Y";

	protected static final String ALIAS_TABLE_Z = "Z";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Module of the application where this DAO is running.
	private String moduleName;

	//
	private Class<T> entityType;

	// Scope.
	private ScopeFacade scope;

	// Relational database.
	private RdbmsView ddbb;

	// DAO name.
	private String daoName;

	//
	private String statementPath;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param source
	 * @param map
	 */
	protected abstract void mapBean(final T source, final Map<String, Object> map);

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private AbstractDao() {
		// DO NOTHING.
	}

	/**
	 * 
	 * @param moduleName Name of the module.<br>
	 *                   <br>
	 * @param type       Model bean used by this DAO.<br>
	 *                   <br>
	 * @param scope      Scope.<br>
	 *                   <br>
	 * @param ddbb       Relational database where to perform the operations.<br>
	 *                   <br>
	 */
	protected AbstractDao(final String moduleName, final Class<T> type, final ScopeFacade scope, final RdbmsView ddbb) {
		this(moduleName, type, scope, ddbb, DEFAULT_STATEMENT_PATH + ResourceL1Helper.DIRECTORY_SEPARATOR);
	}

	/**
	 * 
	 * @param moduleName        Name of the module.<br>
	 *                          <br>
	 * @param type              Model bean used by this DAO.<br>
	 *                          <br>
	 * @param scope             Scope.<br>
	 *                          <br>
	 * @param ddbb              Relational database where to perform the
	 *                          operations.<br>
	 *                          <br>
	 * @param baseStatementPath Base path to load SQL files with database
	 *                          statements.
	 */
	protected AbstractDao(final String moduleName, final Class<T> type, final ScopeFacade scope, final RdbmsView ddbb,
			final String baseStatementPath) {

		// Invoke default constructor.
		this();

		// Set the name of the module.
		this.moduleName = moduleName;

		//
		this.entityType = type;

		// Set Scope.
		this.scope = scope;

		// Set database.
		this.ddbb = ddbb;

		//
		String statement = ReflectionL2Helper.getClassName(getClass());

		//
		statement = statement.substring(0, statement.length() - 3);

		//
		daoName = StringL2Helper.firstLetterToLowerCase(statement);

		//
		statementPath = baseStatementPath + ResourceL1Helper.DIRECTORY_SEPARATOR
				+ StringL2Helper.firstLetterToLowerCase(ReflectionL2Helper.getClassName(type))
				+ ResourceL1Helper.DIRECTORY_SEPARATOR + daoName + StringL2Helper.CHARACTER_PERIOD;

	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected abstract Map<String, String[]> getTableColumns();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#save(java.lang.Object)
	 */
	public final void save(final T object) throws DaoException {
		try {
			executeUpdate(object, OPERATION_INSERT, null);
		} catch (final ClientException e) {
			throw createException(OPERATION_INSERT, e);
		} catch (final IOException e) {
			throw createException(OPERATION_INSERT, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#save(java.lang.Object, int,
	 * java.lang.Character)
	 */
	public final void save(final T object, final int index, final Character delimiter) throws DaoException {
		try {
			executeUpdate(object, OPERATION_INSERT + StringL2Helper.CHARACTER_PERIOD + index, delimiter);
		} catch (final ClientException e) {
			throw createException(OPERATION_INSERT, e);
		} catch (final IOException e) {
			throw createException(OPERATION_INSERT, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#update(java.lang.Object)
	 */
	public final void update(final T object) throws DaoException {
		try {
			executeUpdate(object, OPERATION_UPDATE, null);
		} catch (final ClientException e) {
			throw createException(OPERATION_UPDATE, e);
		} catch (final IOException e) {
			throw createException(OPERATION_UPDATE, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#update(java.lang.Object, int,
	 * java.lang.Character)
	 */
	public final void update(final T object, final int index, final Character delimiter) throws DaoException {
		try {
			executeUpdate(object, OPERATION_UPDATE + StringL2Helper.CHARACTER_PERIOD + index, delimiter);
		} catch (final ClientException e) {
			throw createException(OPERATION_UPDATE, e);
		} catch (final IOException e) {
			throw createException(OPERATION_UPDATE, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#delete(java.lang.Object)
	 */
	public final void delete(final T object) throws DaoException {
		try {
			executeUpdate(object, OPERATION_DELETE, null);
		} catch (final ClientException e) {
			throw createException(OPERATION_DELETE, e);
		} catch (final IOException e) {
			throw createException(OPERATION_DELETE, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#delete(java.lang.Object, int,
	 * java.lang.Character)
	 */
	public final void delete(final T object, final int index, final Character delimiter) throws DaoException {
		try {
			executeUpdate(object, OPERATION_DELETE + StringL2Helper.CHARACTER_PERIOD + index, delimiter);
		} catch (final ClientException e) {
			throw createException(OPERATION_DELETE, e);
		} catch (final IOException e) {
			throw createException(OPERATION_DELETE, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.t2s.shared.business.dao.DAO#select(co.t2s.shared.business.dao.
	 * AbstractQuery, java.util.Locale)
	 */
	public final List<T> select(final AbstractQuery<T> query, final Locale locale) throws DaoException {
		try {

			// Load SQL statement.
			final String sql = query.updateSQL(filterSQL(replaceTableColumns(replaceIncludes(loadStatement(query)))));

			// Execute query in database.
			final ResultRows rows = (ResultRows) ddbb.executeQuery(sql, query.getVariables(), -1, -1);

			// Create result list.
			final List<T> result = new ArrayList<T>();
			while (rows.next()) {
				result.add(query.mapRow(rows, locale));
			}

			// Close database result.
			rows.close();

			// Return mapped result.
			return result;

		} catch (final ClientException e) {
			throw createException(OPERATION_SELECT + StringL2Helper.CHARACTER_PERIOD + query.getQueryId(), e);
		} catch (final IOException e) {
			throw createException(OPERATION_SELECT + StringL2Helper.CHARACTER_PERIOD + query.getQueryId(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#count(com.warework.app.shared
	 * .business.dao.AbstractQuery)
	 */
	public final int count(final AbstractQuery<T> query) throws DaoException {
		try {

			// Load SQL statement.
			final String sql = filterSQL(replaceTableColumns(replaceIncludes(loadStatement(query))));

			// Execute query in database.
			final ResultRows rows = (ResultRows) ddbb.executeQuery(sql, query.getVariables(), -1, -1);

			// Get database result.
			Integer result = null;
			while (rows.next()) {

				// Get count.
				result = rows.getNumber(1, Integer.class);

				// Stop loop.
				break;

			}

			// Close database result.
			rows.close();

			// Return mapped result.
			return result;

		} catch (final ClientException e) {
			throw createException(OPERATION_SELECT + StringL2Helper.CHARACTER_PERIOD + query.getQueryId(), e);
		} catch (final IOException e) {
			throw createException(OPERATION_SELECT + StringL2Helper.CHARACTER_PERIOD + query.getQueryId(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.app.shared.business.dao.DAO#find(com.warework.app.shared
	 * .business.dao.AbstractQuery)
	 */
	public final T find(final AbstractQuery<T> query, final Locale locale) throws DaoException {

		//
		final List<T> result = select(query, locale);

		//
		if ((result != null) && (result.size() == 1)) {
			if (result.size() == 1) {
				return result.get(0);
			} else if (result.size() > 1) {
				throw createException(OPERATION_FIND + StringL2Helper.CHARACTER_PERIOD + query.getQueryId(), null);
			}
		}

		//
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static String getDefaultTableName(final Class<?> type) {

		// Get the name of the class.
		final String typeName = ReflectionL2Helper.getClassName(type);

		// Initialize the name of the table.
		String tableName = CommonValueL2Constants.STRING_EMPTY;

		// Transform the type name into a database table name.
		for (int i = 0; i < typeName.length(); i++) {
			if (Character.isUpperCase(typeName.charAt(i))) {
				if (i > 0) {
					tableName = tableName + COLUMN_WORD_SEPARATOR + typeName.substring(i, i + 1);
				} else {
					tableName = tableName + typeName.substring(i, i + 1);
				}
			} else {
				tableName = tableName + typeName.substring(i, i + 1).toUpperCase();
			}
		}

		// Return the database table name.
		return tableName;

	}

	/**
	 * Removes a set of characters in a string.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return String without given characters.<br>
	 *         <br>
	 */
	public static String filterSQL(final String string) {

		// Set up the result.
		final StringBuilder result = new StringBuilder();

		// Get the max length of the string.
		final int maxLength = string.length();

		// Remove every given character.
		for (int i = 0; i < maxLength; i++) {

			// Get a character from the string.
			final char character = string.charAt(i);

			// Find the position of the character in the characters to
			// remove array.
			int index = Arrays.binarySearch(SQL_FILTER_CHARS, character);

			// Match if character if a character to remove.
			if (index < 0) {
				result.append(character);
			}

		}

		// Return the string.
		return result.toString();

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
	 * 
	 * @return
	 */
	protected final Class<T> getEntityType() {
		return entityType;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new DAO exception.
	 * 
	 * @param operation
	 * @param e
	 * @return
	 */
	private DaoException createException(final String operation, final Throwable e) {
		return new DaoException(getScopeFacade(), moduleName, operation, daoName, e,
				LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * 
	 * @param daoName
	 * @param query
	 * @return
	 * @throws IOException
	 */
	private String loadStatement(final AbstractQuery<?> query) throws IOException {
		return StringL2Helper.toString(getClass().getResourceAsStream(
				statementPath + OPERATION_SELECT + StringL2Helper.CHARACTER_PERIOD + query.getQueryId()
						+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL));
	}

	/**
	 * 
	 * @param daoName
	 * @param operationType
	 * @return
	 * @throws IOException
	 */
	private String loadStatement(final String operationType) throws IOException {
		return StringL2Helper.toString(getClass().getResourceAsStream(statementPath + operationType
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL));
	}

	/**
	 * 
	 * @param object
	 * @param operationType
	 * @param delimiter
	 * @throws IOException
	 * @throws ClientException
	 */
	private void executeUpdate(final T object, String operationType, final Character delimiter)
			throws IOException, ClientException {

		// Load SQL statement.
		final String sql = filterSQL(loadStatement(operationType));

		// Create a new map.
		final Map<String, Object> mapping = new HashMap<String, Object>();

		// Map entity.
		mapBean(object, mapping);

		// Execute statement in database.
		ddbb.executeUpdate(sql, mapping, delimiter);

	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	private String replaceTableColumns(final String source) {

		//
		int begin = source.indexOf("#{");

		//
		int end = source.indexOf('}', begin);

		//
		if ((begin >= 0) && (end > begin)) {

			//
			String output = source;

			//
			StringBuilder temp = null;

			//
			int index;

			//
			String pre, content, post, table, alias, column;

			//
			while ((begin >= 0) && (end > begin)) {

				//
				pre = output.substring(0, begin);

				//
				content = output.substring(begin, end + 1);

				//
				post = output.substring(end + 1, output.length());

				// --- Get table name and alias

				//
				index = content.indexOf('(');

				//
				table = content.substring(2, index);

				//
				alias = content.substring(index + 1, content.indexOf(')'));

				// --- Replace

				//
				String[] columns = getTableColumns().get(table);

				//
				temp = new StringBuilder();

				//
				for (int i = 0; i < columns.length; i++) {

					//
					column = columns[i];

					//
					temp.append(alias);
					temp.append(StringL2Helper.CHARACTER_PERIOD);
					temp.append(StringL2Helper.CHARACTER_DOUBLE_QUOTE);
					temp.append(column);
					temp.append(StringL2Helper.CHARACTER_DOUBLE_QUOTE);
					temp.append(StringL2Helper.CHARACTER_SPACE);
					temp.append("AS");
					temp.append(StringL2Helper.CHARACTER_SPACE);
					temp.append(alias);
					temp.append(StringL2Helper.CHARACTER_UNDERSCORE);
					temp.append(column);
					temp.append(StringL2Helper.CHARACTER_COMMA);
					temp.append(StringL2Helper.CHARACTER_SPACE);

				}

				//
				output = pre + temp.substring(0, temp.length() - 2) + post;

				//
				begin = output.indexOf("#{");

				//
				end = output.indexOf("}", begin);

			}

			//
			return output;

		} else {
			return source;
		}

	}

	/**
	 * 
	 * @param source
	 * @return
	 * @throws IOException
	 */
	private String replaceIncludes(final String source) throws IOException {

		//
		int begin = source.indexOf("@{");

		//
		int end = source.indexOf('}', begin);

		//
		if ((begin >= 0) && (end > begin)) {

			//
			String output = source;

			//
			String pre, content, post, path;

			//
			while ((begin >= 0) && (end > begin)) {

				//
				pre = output.substring(0, begin);

				//
				content = output.substring(begin, end + 1);

				//
				post = output.substring(end + 1, output.length());

				// --- Get path

				//
				path = content.substring(3, content.indexOf('\'', content.indexOf('\'') + 1));

				// --- Replace

				//
				output = pre + StringL2Helper.toString(getClass().getResource(path)) + post;

				//
				begin = output.indexOf("@{");

				//
				end = output.indexOf("}", begin);

			}

			//
			return output;

		} else {
			return source;
		}

	}

}
