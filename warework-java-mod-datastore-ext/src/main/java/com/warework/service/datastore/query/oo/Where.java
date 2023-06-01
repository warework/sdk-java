package com.warework.service.datastore.query.oo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Defines the WHERE clause to filter the result of the query.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Where implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 4328268004501473654L;

	/**
	 * Keyword 'WHERE'.
	 */
	public static final String KEYWORD = "WHERE";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Query
	private Query<?> query;

	// Variables to use in expressions.
	private Map<String, Object> variables;

	// Expressions for the WHERE clause.
	private AbstractExpression expression;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	private Where() {
	}

	/**
	 * Create the WHERE clause.
	 * 
	 * @param query
	 *            Query.<br>
	 * <br>
	 */
	Where(Query<?> query) {

		// Invoke default constructor.
		this();

		// Set the query
		this.query = query;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the query.
	 * 
	 * @return Query.<br>
	 * <br>
	 */
	public Query<?> getQuery() {
		return query;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the expression for the WHERE clause.
	 * 
	 * @param expression
	 *            Main expression (may contain multiple sub-expressions).<br>
	 * <br>
	 */
	public void setExpression(AbstractExpression expression) {
		this.expression = expression;
	}

	/**
	 * Gets the expression for the WHERE clause.
	 * 
	 * @return Main expression (may contain multiple sub-expressions).<br>
	 * <br>
	 */
	public AbstractExpression getExpression() {
		return expression;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Adds a variable in the WHERE clause to be used by an expression.
	 * 
	 * @param name
	 *            Name of the variable.<br>
	 * <br>
	 * @param value
	 *            Value of the variable.<br>
	 * <br>
	 */
	public void addVariable(String name, Object value) {

		// Create the variables Map when required.
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}

		// Add the variable.
		variables.put(name, value);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "EQUAL TO" expression like "attribute = value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "EQUAL TO" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createEqualToValue(String attribute, Object value) {
		return new ValueOperandExpression(this, attribute, Operator.EQUAL_TO,
				value);
	}

	/**
	 * Creates a "EQUAL TO" expression like "attribute = value" where "value" is
	 * a String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "EQUAL TO" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createEqualToValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.EQUAL_TO, type, value, formatPattern, locale, timeZone);
	}

	/**
	 * Creates a "EQUAL TO" expression like "attribute = value" where "value" is
	 * the value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "EQUAL TO" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createEqualToVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.EQUAL_TO, variableName);
	}

	/**
	 * Creates a "EQUAL TO" expression like "attribute = value" where "value" is
	 * the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "EQUAL TO" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createEqualToProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.EQUAL_TO, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "NOT EQUAL TO" expression like "attribute != value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT EQUAL TO" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotEqualToValue(String attribute,
			Object value) {
		return new ValueOperandExpression(this, attribute,
				Operator.NOT_EQUAL_TO, value);
	}

	/**
	 * Creates a "NOT EQUAL TO" expression like "attribute != value" where
	 * "value" is a String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT EQUAL TO" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createNotEqualToValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.NOT_EQUAL_TO, type, value, formatPattern, locale,
				timeZone);
	}

	/**
	 * Creates a "NOT EQUAL TO" expression like "attribute != value" where
	 * "value" is the value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT EQUAL TO" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotEqualToVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.NOT_EQUAL_TO, variableName);
	}

	/**
	 * Creates a "NOT EQUAL TO" expression like "attribute != value" where
	 * "value" is the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT EQUAL TO" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotEqualToProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.NOT_EQUAL_TO, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "LESS THAN" expression like "attribute &lt; value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanValue(String attribute, Object value) {
		return new ValueOperandExpression(this, attribute, Operator.LESS_THAN,
				value);
	}

	/**
	 * Creates a "LESS THAN" expression like "attribute &lt; value" where
	 * "value" is a String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.LESS_THAN, type, value, formatPattern, locale,
				timeZone);
	}

	/**
	 * Creates a "LESS THAN" expression like "attribute &lt; value" where
	 * "value" is the value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.LESS_THAN, variableName);
	}

	/**
	 * Creates a "LESS THAN" expression like "attribute &lt; value" where
	 * "value" is the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.LESS_THAN, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "LESS THAN OR EQUAL TO" expression like
	 * "attribute &lt;= value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanOrEqualToValue(String attribute,
			Object value) {
		return new ValueOperandExpression(this, attribute,
				Operator.LESS_THAN_OR_EQUAL_TO, value);
	}

	/**
	 * Creates a "LESS THAN OR EQUAL TO" expression like "attribute &lt;= value"
	 * where "value" is a String object that needs to be parsed to a specific
	 * type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanOrEqualToValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.LESS_THAN_OR_EQUAL_TO, type, value, formatPattern,
				locale, timeZone);
	}

	/**
	 * Creates a "LESS THAN OR EQUAL TO" expression like "attribute &lt;= value"
	 * where "value" is the value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanOrEqualToVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.LESS_THAN_OR_EQUAL_TO, variableName);
	}

	/**
	 * Creates a "LESS THAN OR EQUAL TO" expression like "attribute &lt;= value"
	 * where "value" is the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LESS THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLessThanOrEqualToProviderValue(
			String attribute, String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.LESS_THAN_OR_EQUAL_TO, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "GREATER THAN" expression like "attribute &gt; value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanValue(String attribute,
			Object value) {
		return new ValueOperandExpression(this, attribute,
				Operator.GREATER_THAN, value);
	}

	/**
	 * Creates a "GREATER THAN" expression like "attribute &gt; value" where
	 * "value" is a String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.GREATER_THAN, type, value, formatPattern, locale,
				timeZone);
	}

	/**
	 * Creates a "GREATER THAN" expression like "attribute &gt; value" where
	 * "value" is the value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.GREATER_THAN, variableName);
	}

	/**
	 * Creates a "GREATER THAN" expression like "attribute &gt; value" where
	 * "value" is the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.GREATER_THAN, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "GREATER THAN OR EQUAL TO" expression like
	 * "attribute &gt;= value".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanOrEqualToValue(String attribute,
			Object value) {
		return new ValueOperandExpression(this, attribute,
				Operator.GREATER_THAN_OR_EQUAL_TO, value);
	}

	/**
	 * Creates a "GREATER THAN OR EQUAL TO" expression like
	 * "attribute &gt;= value" where "value" is a String object that needs to be
	 * parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanOrEqualToValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.GREATER_THAN_OR_EQUAL_TO, type, value, formatPattern,
				locale, timeZone);
	}

	/**
	 * Creates a "GREATER THAN OR EQUAL TO" expression like
	 * "attribute &gt;= value" where "value" is the value of a variable defined
	 * in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createGreaterThanOrEqualToVariable(
			String attribute, String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.GREATER_THAN_OR_EQUAL_TO, variableName);
	}

	/**
	 * Creates a "GREATER THAN OR EQUAL TO" expression like
	 * "attribute &gt;= value" where "value" is the value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "GREATER THAN OR EQUAL TO" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.
	 */
	public AbstractExpression createGreaterThanOrEqualToProviderValue(
			String attribute, String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.GREATER_THAN_OR_EQUAL_TO, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "LIKE" expression.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LIKE" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLikeValue(String attribute, Object value) {
		return new ValueOperandExpression(this, attribute, Operator.LIKE, value);
	}

	/**
	 * Creates an expression "attribute LIKE value" where "value" is a String
	 * object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LIKE" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createLikeValue(String attribute, Class<?> type,
			String value, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {
		return new ParseStringOperandExpression(this, attribute, Operator.LIKE,
				type, value, formatPattern, locale, timeZone);
	}

	/**
	 * Creates an expression "attribute LIKE value" where "value" is the value
	 * of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LIKE" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLikeVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute, Operator.LIKE,
				variableName);
	}

	/**
	 * Creates an expression "attribute LIKE value" where "value" is the value
	 * given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "LIKE" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createLikeProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute, Operator.LIKE,
				providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "NOT LIKE" expression.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT LIKE" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotLikeValue(String attribute, Object value) {
		return new ValueOperandExpression(this, attribute, Operator.NOT_LIKE,
				value);
	}

	/**
	 * Creates an expression "attribute NOT LIKE value" where "value" is a
	 * String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT LIKE" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createNotLikeValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.NOT_LIKE, type, value, formatPattern, locale, timeZone);
	}

	/**
	 * Creates an expression "attribute NOT LIKE value" where "value" is the
	 * value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT LIKE" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotLikeVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.NOT_LIKE, variableName);
	}

	/**
	 * Creates an expression "attribute NOT LIKE value" where "value" is the
	 * value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT LIKE" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotLikeProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.NOT_LIKE, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "CONTAINS" expression.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "CONTAINS" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createContainsValue(String attribute, Object value) {
		return new ValueOperandExpression(this, attribute, Operator.CONTAINS,
				value);
	}

	/**
	 * Creates an expression "attribute CONTAINS value" where "value" is a
	 * String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "CONTAINS" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createContainsValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.CONTAINS, type, value, formatPattern, locale, timeZone);
	}

	/**
	 * Creates an expression "attribute CONTAINS value" where "value" is the
	 * value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "CONTAINS" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createContainsVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.CONTAINS, variableName);
	}

	/**
	 * Creates an expression "attribute CONTAINS value" where "value" is the
	 * value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "CONTAINS" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createContainsProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.CONTAINS, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a "NOT CONTAINS" expression.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT CONTAINS" operator.<br>
	 * <br>
	 * @param value
	 *            Value to use in the operation.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotContainsValue(String attribute,
			Object value) {
		return new ValueOperandExpression(this, attribute,
				Operator.NOT_CONTAINS, value);
	}

	/**
	 * Creates an expression "attribute NOT CONTAINS value" where "value" is a
	 * String object that needs to be parsed to a specific type.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT CONTAINS" operator.<br>
	 * <br>
	 * @param type
	 *            Type of the value (to match the type of the attribute).<br>
	 * <br>
	 * @param value
	 *            Value to convert.<br>
	 * <br>
	 * @param formatPattern
	 *            Format pattern to apply. When object is Float, Double or
	 *            BigDecimal check out <code>java.text.DecimalFormat</code> API
	 *            to review the format patterns. If object is Date or Calendar
	 *            check out <code>java.text.SimpleDateFormat</code> API to
	 *            review the format patterns. In dates, default date format used
	 *            is 'yyyy/MM/dd HH:mm:ss'.<br>
	 * <br>
	 * @param locale
	 *            Locale to apply in the conversion. Works with Float, Double,
	 *            BigDecimal, Date and Calendar objects.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone to apply in the conversion. Works with Date and
	 *            Calendar objects.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the value.<br>
	 * <br>
	 */
	public AbstractExpression createNotContainsValue(String attribute,
			Class<?> type, String value, String formatPattern, Locale locale,
			TimeZone timeZone) throws ParseException {
		return new ParseStringOperandExpression(this, attribute,
				Operator.NOT_CONTAINS, type, value, formatPattern, locale,
				timeZone);
	}

	/**
	 * Creates an expression "attribute NOT CONTAINS value" where "value" is the
	 * value of a variable defined in the WHERE clause.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT CONTAINS" operator.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable defined in the WHERE clause to use as the
	 *            value of the expression.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotContainsVariable(String attribute,
			String variableName) {
		return new VariableOperandExpression(this, attribute,
				Operator.NOT_CONTAINS, variableName);
	}

	/**
	 * Creates an expression "attribute NOT CONTAINS value" where "value" is the
	 * value given by a Provider.
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "NOT CONTAINS" operator.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to get the value.<br>
	 * <br>
	 * @param providerObject
	 *            Name of the object in the Provider that represents the value.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createNotContainsProviderValue(String attribute,
			String providerName, String providerObject) {
		return new ProviderOperandExpression(this, attribute,
				Operator.NOT_CONTAINS, providerName, providerObject);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an expression "attribute IS NULL".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "IS NULL" operator.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createIsNull(String attribute) {
		return new OperandlessExpression(this, attribute, Operator.IS_NULL);
	}

	/**
	 * Creates an expression "attribute IS NOT NULL".
	 * 
	 * @param attribute
	 *            Attribute of the object (type specified in the Query) where to
	 *            apply the "IS NOT NULL" operator.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public AbstractExpression createIsNotNull(String attribute) {
		return new OperandlessExpression(this, attribute, Operator.IS_NOT_NULL);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an AND expression.
	 * 
	 * @return A new expression.<br>
	 * <br>
	 */
	public And createAnd() {
		return new And(this);
	}

	/**
	 * Creates an OR expression.
	 * 
	 * @return A new expression.<br>
	 * <br>
	 */
	public Or createOr() {
		return new Or(this);
	}

	/**
	 * Creates an NOT expression.
	 * 
	 * @param expression
	 *            Expression to negate.<br>
	 * <br>
	 * @return A new expression.<br>
	 * <br>
	 */
	public Not createNot(AbstractExpression expression) {
		return new Not(this, expression);
	}

	/**
	 * Counts the number of operandless expressions.
	 * 
	 * @return Number of operandless expressions found.<br>
	 * <br>
	 */
	public int countOperandlessExpressions() {
		return countOperandlessExpressions(getExpression(), 0);
	}

	/**
	 * Gets the representation of the WHERE clause.
	 * 
	 * @return Representation of the WHERE clause.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if there is an expression.
		if (getExpression() != null) {
			if (getExpression() instanceof AbstractComposedExpression) {

				// Get the expression.
				AbstractComposedExpression composedExpression = (AbstractComposedExpression) getExpression();

				// Return the WHERE clause if expression is not empty.
				if (!composedExpression.toString().equals(
						CommonValueL2Constants.STRING_EMPTY)) {
					return KEYWORD + StringL2Helper.CHARACTER_SPACE
							+ getExpression().toString();
				}

			} else if (getExpression() instanceof AbstractOperandExpression) {

				// Get the expression.
				AbstractOperandExpression operandExpression = (AbstractOperandExpression) getExpression();

				// Return the WHERE clause if expression is not empty.
				if (operandExpression.getValue() != null) {
					return KEYWORD + StringL2Helper.CHARACTER_SPACE
							+ operandExpression;
				}

			} else if (getExpression() instanceof Not) {

				// Get the expression.
				Not notExpression = (Not) getExpression();

				// Return the WHERE clause if expression is not empty.
				if ((notExpression.getExpression() != null)
						&& (!notExpression.getExpression().toString()
								.equals(CommonValueL2Constants.STRING_EMPTY))) {
					return KEYWORD + StringL2Helper.CHARACTER_SPACE
							+ notExpression.toString();
				}

			} else { // Operandless Expression
				return KEYWORD + StringL2Helper.CHARACTER_SPACE
						+ getExpression().toString();
			}
		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the value of a variable.
	 * 
	 * @param variableName
	 *            Name of the variable.<br>
	 * <br>
	 * @return Value of the variable.<br>
	 * <br>
	 */
	Object getVariableValue(String variableName) {

		// Get the value of the variable.
		if (variables != null) {
			return variables.get(variableName);
		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Counts the number of operandless expressions.
	 * 
	 * @param expression
	 *            Expression.<br>
	 * <br>
	 * @param counter
	 *            Expressions counter.<br>
	 * <br>
	 * @return Number of operandless expressions found.<br>
	 * <br>
	 */
	private int countOperandlessExpressions(AbstractExpression expression,
			int counter) {
		if (expression instanceof AbstractComposedExpression) {

			// Get the expression.
			AbstractComposedExpression composedExpression = (AbstractComposedExpression) expression;

			// Count sub-expressions only if composed expression is not empty.
			if (!composedExpression.isEmpty()) {

				// Get the collection of sub-expressions.
				Collection<AbstractExpression> subExpressions = composedExpression
						.getExpressions();

				// Include in the WHERE clause each sub-expression.
				for (Iterator<AbstractExpression> iterator = subExpressions
						.iterator(); iterator.hasNext();) {

					// Get one sub-expression.
					AbstractExpression subExpression = iterator.next();

					// Extract sub-expression and count.
					counter = countOperandlessExpressions(subExpression,
							counter);

				}

			}

			// Return count.
			return counter;

		} else if (expression instanceof OperandlessExpression) {
			return (counter + 1);
		} else {

			// Get the expression.
			Not not = (Not) expression;

			// Extract sub-expression and count.
			return countOperandlessExpressions(not.getExpression(), counter);

		}
	}

}
