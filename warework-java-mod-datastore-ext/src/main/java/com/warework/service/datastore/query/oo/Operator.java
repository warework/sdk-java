package com.warework.service.datastore.query.oo;

import java.io.Serializable;

import com.warework.core.util.helper.StringL2Helper;

/**
 * Constants to specify operators.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Operator implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -3672000413884447040L;

	// NAME OF OPERATORS

	private static final String OPERATOR_NAME_EQUAL_TO = "EQUAL_TO";

	private static final String OPERATOR_NAME_NOT_EQUAL_TO = "NOT_EQUAL_TO";

	private static final String OPERATOR_NAME_LESS_THAN = "LESS_THAN";

	private static final String OPERATOR_NAME_LESS_THAN_OR_EQUAL_TO = "LESS_THAN_OR_EQUAL_TO";

	private static final String OPERATOR_NAME_GREATER_THAN = "GREATER_THAN";

	private static final String OPERATOR_NAME_GREATER_THAN_OR_EQUAL_TO = "GREATER_THAN_OR_EQUAL_TO";

	private static final String OPERATOR_NAME_IS_NULL = "IS_NULL";

	private static final String OPERATOR_NAME_IS_NOT_NULL = "IS_NOT_NULL";

	private static final String OPERATOR_NAME_LIKE = "LIKE";

	private static final String OPERATOR_NAME_NOT_LIKE = "NOT_LIKE";

	private static final String OPERATOR_NAME_CONTAINS = "CONTAINS";

	private static final String OPERATOR_NAME_NOT_CONTAINS = "NOT_CONTAINS";

	// SHORT NAMES FOR OPERATORS

	private static final String OPERATOR_SHORT_NAME_EQUAL_TO = "EQ";

	private static final String OPERATOR_SHORT_NAME_NOT_EQUAL_TO = "NE";

	private static final String OPERATOR_SHORT_NAME_LESS_THAN = "LT";

	private static final String OPERATOR_SHORT_NAME_LESS_THAN_OR_EQUAL_TO = "LE";

	private static final String OPERATOR_SHORT_NAME_GREATER_THAN = "GT";

	private static final String OPERATOR_SHORT_NAME_GREATER_THAN_OR_EQUAL_TO = "GE";

	private static final String OPERATOR_SHORT_NAME_IS_NULL = "IN";

	private static final String OPERATOR_SHORT_NAME_IS_NOT_NULL = "NN";

	private static final String OPERATOR_SHORT_NAME_LIKE = "LK";

	private static final String OPERATOR_SHORT_NAME_NOT_LIKE = "NL";

	private static final String OPERATOR_SHORT_NAME_CONTAINS = "CT";

	private static final String OPERATOR_SHORT_NAME_NOT_CONTAINS = "NC";

	// MATH OPERATORS

	private static final String MATH_OPERATOR_EQUAL_TO = StringL2Helper.CHARACTER_EQUALS;

	private static final String MATH_OPERATOR_NOT_EQUAL_TO = StringL2Helper.CHARACTER_EXCLAMATION
			+ StringL2Helper.CHARACTER_EQUALS;

	private static final String MATH_OPERATOR_LESS_THAN = StringL2Helper.CHARACTER_LESS_THAN;

	private static final String MATH_OPERATOR_LESS_THAN_OR_EQUAL_TO = StringL2Helper.CHARACTER_LESS_THAN
			+ StringL2Helper.CHARACTER_EQUALS;

	private static final String MATH_OPERATOR_GREATER_THAN = StringL2Helper.CHARACTER_GREATER_THAN;

	private static final String MATH_OPERATOR_GREATER_THAN_OR_EQUAL_TO = StringL2Helper.CHARACTER_GREATER_THAN
			+ StringL2Helper.CHARACTER_EQUALS;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Operator for "=".
	 */
	public static final Operator EQUAL_TO = new Operator(OPERATOR_NAME_EQUAL_TO);

	/**
	 * Operator for "!=".
	 */
	public static final Operator NOT_EQUAL_TO = new Operator(
			OPERATOR_NAME_NOT_EQUAL_TO);

	/**
	 * Operator for "&lt;".
	 */
	public static final Operator LESS_THAN = new Operator(
			OPERATOR_NAME_LESS_THAN);

	/**
	 * Operator for "&lt;=".
	 */
	public static final Operator LESS_THAN_OR_EQUAL_TO = new Operator(
			OPERATOR_NAME_LESS_THAN_OR_EQUAL_TO);

	/**
	 * Operator for "&gt;".
	 */
	public static final Operator GREATER_THAN = new Operator(
			OPERATOR_NAME_GREATER_THAN);

	/**
	 * Operator for "&gt;=".
	 */
	public static final Operator GREATER_THAN_OR_EQUAL_TO = new Operator(
			OPERATOR_NAME_GREATER_THAN_OR_EQUAL_TO);

	/**
	 * Operator for "IS NULL".
	 */
	public static final Operator IS_NULL = new Operator(OPERATOR_NAME_IS_NULL);

	/**
	 * Operator for "IS NOT NULL".
	 */
	public static final Operator IS_NOT_NULL = new Operator(
			OPERATOR_NAME_IS_NOT_NULL);

	/**
	 * Operator for "LIKE".
	 */
	public static final Operator LIKE = new Operator(OPERATOR_NAME_LIKE);

	/**
	 * Operator for "NOT LIKE".
	 */
	public static final Operator NOT_LIKE = new Operator(OPERATOR_NAME_NOT_LIKE);

	/**
	 * Operator for "CONTAINS" for string matching.
	 */
	public static final Operator CONTAINS = new Operator(OPERATOR_NAME_CONTAINS);

	/**
	 * Operator for "NOT_CONTAINS" for string matching.
	 */
	public static final Operator NOT_CONTAINS = new Operator(
			OPERATOR_NAME_NOT_CONTAINS);

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the operator.
	private String name;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	private Operator() {
	}

	/**
	 * Create an operator.
	 * 
	 * @param name
	 *            Name of the operator.<br>
	 * <br>
	 */
	private Operator(String name) {
		this.name = name;
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the name of the operator.
	 * 
	 * @return Name of the operator.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the oposite operation.
	 * 
	 * @return Oposite operation.<br>
	 * <br>
	 */
	public Operator getOposite() {
		if (name.equalsIgnoreCase(OPERATOR_NAME_EQUAL_TO)) {
			return Operator.NOT_EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_NOT_EQUAL_TO)) {
			return Operator.EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN)) {
			return Operator.GREATER_THAN_OR_EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN_OR_EQUAL_TO)) {
			return Operator.GREATER_THAN;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN)) {
			return Operator.LESS_THAN_OR_EQUAL_TO;
		} else if (name
				.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN_OR_EQUAL_TO)) {
			return Operator.LESS_THAN;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_IS_NULL)) {
			return Operator.IS_NOT_NULL;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_IS_NOT_NULL)) {
			return Operator.IS_NULL;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_LIKE)) {
			return Operator.NOT_LIKE;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_NOT_LIKE)) {
			return Operator.LIKE;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_CONTAINS)) {
			return Operator.NOT_CONTAINS;
		} else {
			return Operator.CONTAINS;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the operator.
	 * 
	 * @return Name of the operator.<br>
	 * <br>
	 */
	public String toString() {
		if (name.equalsIgnoreCase(OPERATOR_NAME_EQUAL_TO)) {
			return MATH_OPERATOR_EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_NOT_EQUAL_TO)) {
			return MATH_OPERATOR_NOT_EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN)) {
			return MATH_OPERATOR_LESS_THAN;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN_OR_EQUAL_TO)) {
			return MATH_OPERATOR_LESS_THAN_OR_EQUAL_TO;
		} else if (name.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN)) {
			return MATH_OPERATOR_GREATER_THAN;
		} else if (name
				.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN_OR_EQUAL_TO)) {
			return MATH_OPERATOR_GREATER_THAN_OR_EQUAL_TO;
		} else {
			return getName();
		}
	}

	/**
	 * Validates if a given operator is equals to this operator.
	 * 
	 * @return <code>true</code> if both are the same and <code>false</code> if
	 *         not.<br>
	 * <br>
	 */
	public boolean equals(Object object) {

		// Validate that it is an operator.
		if (object instanceof Operator) {

			// Get the operator object.
			Operator operator = (Operator) object;

			// Validate operators' values.
			if (name.equalsIgnoreCase(operator.getName())) {
				return true;
			}

		}

		// At this point, operators are not the same.
		return false;

	}

	/**
	 * Gets the operator that matches a given name.
	 * 
	 * @param name
	 *            Name of the operator.<br>
	 * <br>
	 * @return The operator that matches the given name.<br>
	 * <br>
	 */
	public static Operator toObject(String name) {

		// Gets the operator that matches the given name.
		if (name != null) {
			if ((name.equalsIgnoreCase(OPERATOR_NAME_EQUAL_TO))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_EQUAL_TO))) {
				return Operator.EQUAL_TO;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_NOT_EQUAL_TO))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_NOT_EQUAL_TO))) {
				return Operator.NOT_EQUAL_TO;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_LESS_THAN))) {
				return Operator.LESS_THAN;
			} else if ((name
					.equalsIgnoreCase(OPERATOR_NAME_LESS_THAN_OR_EQUAL_TO))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_LESS_THAN_OR_EQUAL_TO))) {
				return Operator.LESS_THAN_OR_EQUAL_TO;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_GREATER_THAN))) {
				return Operator.GREATER_THAN;
			} else if ((name
					.equalsIgnoreCase(OPERATOR_NAME_GREATER_THAN_OR_EQUAL_TO))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_GREATER_THAN_OR_EQUAL_TO))) {
				return Operator.GREATER_THAN_OR_EQUAL_TO;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_IS_NULL))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_IS_NULL))) {
				return Operator.IS_NULL;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_IS_NOT_NULL))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_IS_NOT_NULL))) {
				return Operator.IS_NOT_NULL;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_LIKE))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_LIKE))) {
				return Operator.LIKE;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_NOT_LIKE))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_NOT_LIKE))) {
				return Operator.NOT_LIKE;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_CONTAINS))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_CONTAINS))) {
				return Operator.CONTAINS;
			} else if ((name.equalsIgnoreCase(OPERATOR_NAME_NOT_CONTAINS))
					|| (name.equalsIgnoreCase(OPERATOR_SHORT_NAME_NOT_CONTAINS))) {
				return Operator.NOT_CONTAINS;
			}
		}

		// At this point, nothing to return.
		return null;

	}

}
