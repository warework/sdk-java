package com.warework.service.datastore.query.oo;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Represents an expression like <code>(attribute OPERATOR)</code>, where:
 * 
 * <ul>
 * <li><b><code>attribute</code></b> is the attribute of the object where to
 * apply the <code>OPERATOR</code> and the <code>value</code>.</li>
 * <li><b><code>OPERATOR</code></b> is any constant defined at
 * <code>com.warework.service.datastore.query.oo.Operator</code> like:
 * <code>IS_NULL</code> or <code>IS_NOT_NULL</code>.</li>
 * <li><b><code>value</code></b> is the value to apply to the attribute with the
 * <code>OPERATOR</code></li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class OperandlessExpression extends AbstractExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -903676865354152427L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTE
	// ///////////////////////////////////////////////////////////////////

	// Attribute of the object.
	private String objectAttribute;

	// Operation for the attribute.
	private Operator operation;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Create an expression like <code>(attribute OPERATOR)</code>.
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
	OperandlessExpression(Where where, String attribute, Operator operator) {

		// Initialize parent.
		super(where);

		// Set the attribute of the object.
		this.objectAttribute = attribute;

		// Set the operation to perform.
		this.operation = operator;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the attribute.
	 * 
	 * @return Object's attribute.<br>
	 * <br>
	 */
	public String getAttribute() {
		return objectAttribute;
	}

	/**
	 * Gets the operator.
	 * 
	 * @return Operation to perform.<br>
	 * <br>
	 */
	public Operator getOperator() {
		return operation;
	}

	/**
	 * Gets the oposite expression.
	 * 
	 * @return Oposite expression.<br>
	 * <br>
	 */
	public AbstractExpression getOposite() {
		return new OperandlessExpression(getWhere(), getAttribute(),
				getOperator().getOposite());
	}

	/**
	 * Gets the representation of the expression.
	 * 
	 * @return Representation of the expression.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if attribute and operator exist.
		if ((objectAttribute != null) && (operation != null)) {
			return StringL2Helper.CHARACTER_LEFT_PARENTHESES + objectAttribute
					+ StringL2Helper.CHARACTER_SPACE + operation.getName()
					+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
