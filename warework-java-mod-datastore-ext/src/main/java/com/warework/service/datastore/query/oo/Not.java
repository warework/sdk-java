package com.warework.service.datastore.query.oo;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Represents a NOT expression.
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class Not extends AbstractExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 8956908357548497670L;

	/**
	 * Keyword 'NOT'.
	 */
	public static final String KEYWORD = "NOT";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Expression.
	private AbstractExpression expr;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a NOT expression.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 */
	Not(Where where, AbstractExpression expression) {

		// Initialize parent.
		super(where);

		// Set the expression.
		this.expr = expression;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the expression.
	 * 
	 * @param expression
	 *            Expression.<br>
	 * <br>
	 */
	public void setExpression(AbstractExpression expression) {
		this.expr = expression;
	}

	/**
	 * Gets the expression.
	 * 
	 * @return Expression.
	 */
	public AbstractExpression getExpression() {
		return expr;
	}

	/**
	 * Gets the oposite expression.
	 * 
	 * @return Oposite expression.<br>
	 * <br>
	 */
	public AbstractExpression getOposite() {
		return expr;
	}

	/**
	 * Gets the representation of the expression.
	 * 
	 * @return Representation of the expression.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if expressions exist.
		if (expr != null) {
			return KEYWORD + StringL2Helper.CHARACTER_SPACE + expr.toString();
		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
