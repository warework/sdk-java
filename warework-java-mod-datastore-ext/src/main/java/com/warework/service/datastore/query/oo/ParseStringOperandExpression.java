package com.warework.service.datastore.query.oo;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Represents an expression like <code>(attribute OPERATOR value)</code>, where:
 * 
 * <ul>
 * <li><b><code>attribute</code></b> is the attribute of the object where to
 * apply the <code>OPERATOR</code> and the <code>value</code>.</li>
 * <li><b><code>OPERATOR</code></b> is any constant defined at
 * <code>com.warework.service.datastore.query.oo.Operator</code> like:
 * <code>EQUAL_TO</code>, <code>NOT_EQUAL_TO</code>, <code>LESS_THAN</code>, ...
 * </li>
 * <li><b><code>value</code></b> is the value to apply to the attribute with the
 * <code>OPERATOR</code>. This value is given as a String object and it is
 * converted into another object that matches the type of the attribute.</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class ParseStringOperandExpression extends AbstractOperandExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -2849513851598236770L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTE
	// ///////////////////////////////////////////////////////////////////

	// Value for the operand in the expression.
	private Object operandValue;

	// TimeZone to display date values.
	private TimeZone timeZone;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Create an expression like <code>(attribute OPERATOR value)</code>.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 * @param attribute
	 *            Attribute of the object where to apply the
	 *            <code>OPERATOR</code> and the <code>value</code>.<br>
	 * <br>
	 * @param operator
	 *            Operator for the expression.<br>
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
	 * @throws ParseException
	 */
	ParseStringOperandExpression(Where where, String attribute,
			Operator operator, Class<?> type, String value,
			String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Initialize parent.
		super(where, attribute, operator);

		// Parse the value.
		operandValue = StringL2Helper.parse(type, value, formatPattern, locale,
				timeZone);

		// Set the time zone.
		this.timeZone = timeZone;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the value.
	 * 
	 * @return Value.<br>
	 * <br>
	 */
	public Object getValue() {
		return operandValue;
	}

	/**
	 * Gets the representation of the expression.
	 * 
	 * @return Representation of the expression.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if attribute and operator exist.
		if ((getAttribute() != null) && (getOperator() != null)
				&& (operandValue != null)) {
			if (operandValue instanceof String) {
				return StringL2Helper.CHARACTER_LEFT_PARENTHESES
						+ getAttribute() + StringL2Helper.CHARACTER_SPACE
						+ getOperator().toString()
						+ StringL2Helper.CHARACTER_SPACE
						+ StringL2Helper.CHARACTER_SINGLE_QUOTE
						+ operandValue.toString()
						+ StringL2Helper.CHARACTER_SINGLE_QUOTE
						+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
			} else if (operandValue instanceof Date) {
				return StringL2Helper.CHARACTER_LEFT_PARENTHESES
						+ getAttribute()
						+ StringL2Helper.CHARACTER_SPACE
						+ getOperator().toString()
						+ StringL2Helper.CHARACTER_SPACE
						+ StringL2Helper.CHARACTER_SINGLE_QUOTE
						+ DateL2Helper.toStringDDMMYYYYHHMMSS(
								(Date) operandValue, timeZone)
						+ StringL2Helper.CHARACTER_SINGLE_QUOTE
						+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
			} else {
				return StringL2Helper.CHARACTER_LEFT_PARENTHESES
						+ getAttribute() + StringL2Helper.CHARACTER_SPACE
						+ getOperator().toString()
						+ StringL2Helper.CHARACTER_SPACE
						+ operandValue.toString()
						+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
			}
		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
