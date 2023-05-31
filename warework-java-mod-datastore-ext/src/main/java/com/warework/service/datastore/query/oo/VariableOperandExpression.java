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
 * <code>OPERATOR</code>. This value is given by a variable defined in the
 * <code>WHERE</code> clause/object.</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class VariableOperandExpression extends AbstractOperandExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -5273728576290693272L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the variable.
	private String variableName;

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
	 * @param variableName
	 *            Name of the variable that holds the value for the expression.<br>
	 * <br>
	 */
	VariableOperandExpression(Where where, String attribute, Operator operator,
			String variableName) {

		// Initialize parent.
		super(where, attribute, operator);

		// Set the name of the variable.
		this.variableName = variableName;

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
		return getWhere().getVariableValue(variableName);
	}

}
