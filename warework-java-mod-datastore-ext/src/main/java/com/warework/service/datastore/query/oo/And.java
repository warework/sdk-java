package com.warework.service.datastore.query.oo;

import java.util.Collection;
import java.util.Iterator;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Represents a composed expression with AND like:
 * <code>(expr1 AND expr2 AND exp3 ...)</code>.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class And extends AbstractComposedExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 354756962902609695L;

	/**
	 * Keyword 'AND'.
	 */
	public static final String KEYWORD = "AND";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an AND compossed expression.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 */
	And(Where where) {
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
		Or or = new Or(getWhere());

		// Set each oposite sub-expression.
		for (Iterator<AbstractExpression> iterator = expressions.iterator(); iterator
				.hasNext();) {

			// Get a expression.
			AbstractExpression expression = iterator.next();

			// Set the oposite expression.
			or.add(expression.getOposite());

		}

		// Return the oposite expression.
		return or;

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

			// Number of expressions displayed.
			int displayed = 0;

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

					// Add one expression displayed.
					displayed = displayed + 1;

					// Append AND if required.
					if (iterator.hasNext()) {
						output.append(StringL2Helper.CHARACTER_SPACE);
						output.append(KEYWORD);
						output.append(StringL2Helper.CHARACTER_SPACE);
					}

				}

			}

			// Return the String representation of the expression.
			if (displayed > 0) {
				if (displayed == 1) {
					return output.toString();
				} else {
					return StringL2Helper.CHARACTER_LEFT_PARENTHESES
							+ output.toString()
							+ StringL2Helper.CHARACTER_RIGHT_PARENTHESES;
				}
			}

		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
