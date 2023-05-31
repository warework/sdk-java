package com.warework.service.datastore.query.oo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents an abstract composed expression like
 * <code>(expr1 AND expr2 OR exp3 ...)</code>.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractComposedExpression extends AbstractExpression {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -8659725524859695006L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Collection of expressions.
	private Collection<AbstractExpression> expresions = new ArrayList<AbstractExpression>();

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a compossed expression.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 */
	AbstractComposedExpression(Where where) {
		super(where);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Add a sub-expression in this expression.
	 * 
	 * @param expression
	 *            Expression to add.<br>
	 * <br>
	 */
	public void add(AbstractExpression expression) {
		expresions.add(expression);
	}

	/**
	 * Gets the collection of sub-expressions that exist in this expression.
	 * 
	 * @return Collection of sub-expressions that exist in this expression.<br>
	 * <br>
	 */
	public Collection<AbstractExpression> getExpressions() {
		return expresions;
	}

	/**
	 * Decides if there are sub-expressions in this expression.
	 * 
	 * @return <code>true</code> if the are not sub-expressions in this
	 *         expression and <code>false</code> if not.<br>
	 * <br>
	 */
	public boolean isEmpty() {
		return ((expresions == null) || (expresions.size() <= 0));
	}

}
