package com.warework.service.datastore.query.oo;

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
 * <code>OPERATOR</code>.</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class ValueOperandExpression extends AbstractOperandExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 833831401902647042L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTE
	// ///////////////////////////////////////////////////////////////////

	// Value for the operand in the expression.
	private Object operandValue;

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
	 * @param value
	 *            Value to apply to the attribute with the <code>OPERATOR</code>
	 *            .<br>
	 * <br>
	 */
	ValueOperandExpression(Where where, String attribute, Operator operator,
			Object value) {

		// Initialize parent.
		super(where, attribute, operator);

		// Set the value for the operand.
		this.operandValue = value;

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

}
