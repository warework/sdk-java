package com.warework.service.datastore.query.oo;

import java.util.Collection;
import java.util.Iterator;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Represents a composed expression with OR like:
 * <code>(expr1 OR expr2 OR exp3 ...)</code>.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Or extends AbstractComposedExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 1047214750015213398L;

	/**
	 * Keyword 'OR'.
	 */
	public static final String KEYWORD = "OR";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an OR compossed expression.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 */
	Or(Where where) {
		super(where);
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

		// Get the expressions.
		Collection<AbstractExpression> expressions = getExpressions();

		// Create the oposite expression.
		And and = new And(getWhere());

		// Set each oposite sub-expression.
		for (Iterator<AbstractExpression> iterator = expressions.iterator(); iterator
				.hasNext();) {

			// Get a expression.
			AbstractExpression expression = iterator.next();

			// Set the oposite expression.
			and.add(expression.getOposite());

		}

		// Return the oposite expression.
		return and;

	}

	/**
	 * Gets the representation of the expression.
	 * 
	 * @return Representation of the expression.<br>
	 * <br>
	 */
	public String toString() {

		// Create the String only if expressions exist.
		if (!isEmpty()) {

			// Prepare the output String.
			StringBuilder output = new StringBuilder();

			// Get the expressions.
			Collection<AbstractExpression> expressions = getExpressions();

			// Build the output String.
			for (Iterator<AbstractExpression> iterator = expressions.iterator(); iterator
					.hasNext();) {

				// Get an expression.
				AbstractExpression expression = iterator.next();

				// Append only when there is something to show.
				if (!expression.toString().equals(
						CommonValueL2Constants.STRING_EMPTY)) {

					// Add the String representation of the expression.
					output.append(expression.toString());

					// Append AND if required.
					if (iterator.hasNext()) {
						output.append(StringL2Helper.CHARACTER_SPACE);
						output.append(KEYWORD);
						output.append(StringL2Helper.CHARACTER_SPACE);
					}

				}

			}

			// Return the String representation of the expression.
			return StringL2Helper.CHARACTER_LEFT_PARENTHESES
					+ output.toString()
					+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;

		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
