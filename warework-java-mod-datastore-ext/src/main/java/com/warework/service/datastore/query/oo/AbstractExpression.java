package com.warework.service.datastore.query.oo;

import java.io.Serializable;

/**
 * Abstract representation of an expression for the WHERE clause.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractExpression implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -5497936601800553720L;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the oposite expression.
	 * 
	 * @return Oposite expression.<br>
	 * <br>
	 */
	public abstract AbstractExpression getOposite();

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Where clause.
	private Where whereClause;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	private AbstractExpression() {
	}

	/**
	 * Creates an expression.
	 * 
	 * @param where
	 *            Where clause for this expression.<br>
	 * <br>
	 */
	AbstractExpression(Where where) {

		// Invoke default constructor.
		this();

		// Set the WHERE clause.
		this.whereClause = where;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the WHERE clause.
	 * 
	 * @return WHERE clause.<br>
	 * <br>
	 */
	public Where getWhere() {
		return whereClause;
	}

}
