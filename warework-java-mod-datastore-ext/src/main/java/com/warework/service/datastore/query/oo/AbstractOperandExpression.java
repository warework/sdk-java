package com.warework.service.datastore.query.oo;

import java.util.Date;

import com.warework.core.util.CommonValueL2Constants;
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
 * <code>OPERATOR</code></li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractOperandExpression extends OperandlessExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 5264071961896986926L;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the value.
	 * 
	 * @return Value.<br>
	 * <br>
	 */
	public abstract Object getValue();

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
	 */
	AbstractOperandExpression(Where where, String attribute, Operator operator) {
		super(where, attribute, operator);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the oposite expression.
	 * 
	 * @return Oposite expression.<br>
	 * <br>
	 */
	public AbstractExpression getOposite() {

		// Get the current value of the expression.
		final Object currentValue = getValue();

		// Get the current expression.
		final AbstractOperandExpression currentExpression = this;

		// Return the oposite operation.
		return new AbstractOperandExpression(getWhere(), getAttribute(),
				getOperator().getOposite()) {

			// Serial Version.
			private static final long serialVersionUID = 6844098082537347434L;

			/**
			 * Gets the oposite expression.
			 * 
			 * @return Oposite expression.<br>
			 * <br>
			 */
			public AbstractExpression getOposite() {
				return currentExpression;
			}

			/**
			 * Get the value.
			 * 
			 * @return Value.<br>
			 * <br>
			 */
			public Object getValue() {
				return currentValue;
			}

		};

	}

	/**
	 * Gets the representation of the expression.
	 * 
	 * @return Representation of the expression.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if attribute and operator exist.
		if ((getAttribute() != null) && (getOperator() != null)) {

			// Get the value
			Object value = getValue();

			// Create the String.
			if (value != null) {
				if ((value instanceof String) || (value instanceof Date)) {
					return StringL2Helper.CHARACTER_LEFT_PARENTHESES
							+ getAttribute() + StringL2Helper.CHARACTER_SPACE
							+ getOperator().toString()
							+ StringL2Helper.CHARACTER_SPACE
							+ StringL2Helper.CHARACTER_SINGLE_QUOTE
							+ value.toString()
							+ StringL2Helper.CHARACTER_SINGLE_QUOTE
							+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
				} else {
					return StringL2Helper.CHARACTER_LEFT_PARENTHESES
							+ getAttribute() + StringL2Helper.CHARACTER_SPACE
							+ getOperator().toString()
							+ StringL2Helper.CHARACTER_SPACE + value.toString()
							+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
				}
			}

		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
