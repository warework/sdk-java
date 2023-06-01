package com.warework.core.util;

/**
 * Common constant values.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class CommonValueL1Constants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// CHAR

	/**
	 * Char '!'.
	 */
	public static final char CHAR_EXCLAMATION = '!';

	/**
	 * Char '/'.
	 */
	public static final char CHAR_FORWARD_SLASH = '/';

	/**
	 * Char '.'.
	 */
	public static final char CHAR_PERIOD = '.';

	/**
	 * Char ';'.
	 */
	public static final char CHAR_SEMICOLON = ';';

	// STRING

	/**
	 * String 'class'.
	 */
	public static final String STRING_CLASS = "class";

	/**
	 * String 'client'.
	 */
	public static final String STRING_CLIENT = "client";

	/**
	 * String 'clients'.
	 */
	public static final String STRING_CLIENTS = "clients";

	/**
	 * String 'connector'.
	 */
	public static final String STRING_CONNECTOR = "connector";

	/**
	 * String 'context'.
	 */
	public static final String STRING_CONTEXT = "context";

	/**
	 * String 'domain'.
	 */
	public static final String STRING_DOMAIN = "domain";

	/**
	 * '${' characters.
	 */
	public static final String STRING_DOLLAR_LEFT_CURLY_BRACKET = "${";

	/**
	 * Empty string.
	 */
	public static final String STRING_EMPTY = "";

	/**
	 * String 'false'.
	 */
	public static final String STRING_FALSE = "false";

	/**
	 * String 'function'.
	 */
	public static final String STRING_FUNCTION = "function";

	/**
	 * String 'impl'.
	 */
	public static final String STRING_IMPL = "impl";

	/**
	 * String 'java'.
	 */
	public static final String STRING_JAVA = "java";

	/**
	 * String 'loader'.
	 */
	public static final String STRING_LOADER = "loader";

	/**
	 * String 'name'.
	 */
	public static final String STRING_NAME = "name";

	/**
	 * String 'null'.
	 */
	public static final String STRING_NULL = "null";

	/**
	 * String 'object'.
	 */
	public static final String STRING_OBJECT = "object";

	/**
	 * String 'object'.
	 */
	public static final String STRING_OBJECTS = "objects";

	/**
	 * String 'parameter'.
	 */
	public static final String STRING_PARAMETER = "parameter";

	/**
	 * String 'parameters'.
	 */
	public static final String STRING_PARAMETERS = "parameters";

	/**
	 * String 'parent'.
	 */
	public static final String STRING_PARENT = "parent";

	/**
	 * String 'provider'.
	 */
	public static final String STRING_PROVIDER = "provider";

	/**
	 * String 'providers'.
	 */
	public static final String STRING_PROVIDERS = "providers";

	/**
	 * String 'scope'.
	 */
	public static final String STRING_SCOPE = "scope";

	/**
	 * String 'service'.
	 */
	public static final String STRING_SERVICE = "service";

	/**
	 * String 'services'.
	 */
	public static final String STRING_SERVICES = "services";

	/**
	 * String 'true'.
	 */
	public static final String STRING_TRUE = "true";

	/**
	 * String 'type'.
	 */
	public static final String STRING_TYPE = "type";

	/**
	 * String 'value'.
	 */
	public static final String STRING_VALUE = "value";

	/**
	 * String 'view'.
	 */
	public static final String STRING_VIEW = "view";

	/**
	 * String 'warework'.
	 */
	public static final String STRING_WAREWORK = "warework";

	// ENCODING

	/**
	 * UTF-8 encoding.
	 */
	public static final String ENCODING_TYPE_UTF8 = "UTF-8";
	
	// DEFAULT VALUES
	
	/**
	 * Default buffer size in bytes: 8 KB.
	 */
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected CommonValueL1Constants() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a boolean object from a given value.
	 * 
	 * @param value Object that represents the boolean value. You can provide a
	 *              string, a boolean or <code>null</code>.<br>
	 *              <br>
	 * @return The boolean object that represents the value or null if given value
	 *         is null or it does not represents a boolean value.<br>
	 *         <br>
	 */
	public static Boolean toBoolean(final Object value) {

		// Return the boolean value.
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean) value;
			} else if (value instanceof String) {
				if (STRING_TRUE.equalsIgnoreCase((String) value)) {
					return Boolean.TRUE;
				} else if (STRING_FALSE.equalsIgnoreCase((String) value)) {
					return Boolean.FALSE;
				}
			}
		}

		// At this point, default value is null.
		return null;

	}

	/**
	 * Gets an integer object from a given value.
	 * 
	 * @param value Object that represents the integer value. You can provide a
	 *              string, a number (any) or <code>null</code>.<br>
	 *              <br>
	 * @return The integer object that represents the value or null if given value
	 *         is null or it does not represents an integer value.<br>
	 *         <br>
	 */
	public static Integer toInteger(final Object value) {

		// Return the integer value.
		if (value != null) {
			if (value instanceof Integer) {
				return (Integer) value;
			} else if (value instanceof Number) {
				return ((Number) value).intValue();
			} else if (value instanceof String) {
				try {
					return Integer.valueOf((String) value);
				} catch (Exception e) {
					return null;
				}
			}
		}

		// At this point, default value is null.
		return null;

	}

	/**
	 * Gets a string object from a given value.
	 * 
	 * @param value Object that represents the string value. You can provide a
	 *              string or <code>null</code>.<br>
	 *              <br>
	 * @return The string object that represents the value or null if given value is
	 *         null or it is not a string value.<br>
	 *         <br>
	 */
	public static String toString(final Object value) {
		return ((value != null) && (value instanceof String)) ? (String) value : null;
	}

}
