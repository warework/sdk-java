package com.warework.core.util.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Defines the order of the attributes for the result of the query.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class OrderBy implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 3552500639441159699L;

	// KEYWORDS

	/**
	 * "ORDER BY" keyword.
	 */
	public static final String KEYWORD_ORDER_BY = "ORDER BY";

	/**
	 * "ASC" keyword.
	 */
	public static final String KEYWORD_ASCENDING = "ASC";

	/**
	 * "DESC" keyword.
	 */
	public static final String KEYWORD_DESCENDING = "DESC";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Order for every attribute.
	private List<String[]> orders = new ArrayList<String[]>();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sort in an ascending way an attribute.
	 * 
	 * @param attribute Attribute to sort.<br>
	 *                  <br>
	 * @return An instance of this object.<br>
	 *         <br>
	 */
	public OrderBy addAscending(final String attribute) {

		// Add order type.
		orders.add(new String[] { attribute, KEYWORD_ASCENDING });

		// Return an instance of this object.
		return this;

	}

	/**
	 * Sort in an descending way an attribute.
	 * 
	 * @param attribute Attribute to sort.<br>
	 *                  <br>
	 * @return An instance of this object.<br>
	 *         <br>
	 */
	public OrderBy addDescending(final String attribute) {

		// Add order type.
		orders.add(new String[] { attribute, KEYWORD_DESCENDING });

		// Return an instance of this object.
		return this;

	}

	/**
	 * Gets the attribute specified in a position of the ORDER BY clause.
	 * 
	 * @param index Position where the attribute exists.<br>
	 *              <br>
	 * @return Attribute name.<br>
	 *         <br>
	 */
	public String getAttribute(final int index) {
		if ((index >= 0) && (index < orders.size())) {
			return orders.get(index)[0];
		} else {
			return null;
		}
	}

	/**
	 * Gets the type of the order for an attribute.
	 * 
	 * @param index Position where the attribute exists.<br>
	 *              <br>
	 * @return Order type (ASC or DESC).<br>
	 *         <br>
	 */
	public String getOrderType(final int index) {
		if ((index >= 0) && (index < orders.size())) {
			return orders.get(index)[1];
		} else {
			return null;
		}
	}

	/**
	 * Count how many attributes have to be sorted.
	 * 
	 * @return Number of attributes to sort.<br>
	 *         <br>
	 */
	public int size() {
		return orders.size();
	}

	/**
	 * Decides if the ORDER BY clause is empty.
	 * 
	 * @return <code>true</code> if there are no attributes to sort and
	 *         <code>false</code> if there is at least one attribute to sort.<br>
	 *         <br>
	 */
	public boolean isEmpty() {
		return (orders.size() <= 0);
	}

	/**
	 * Gets the representation of the ORDER BY clause.
	 * 
	 * @return Representation of the ORDER BY clause.<br>
	 *         <br>
	 */
	public String toString() {

		// Create the String only if there is at least one attribute to sort.
		if (!isEmpty()) {

			// Prepare the output String.
			final StringBuilder orderBy = new StringBuilder();

			// Append the ORDER BY keyword.
			orderBy.append(KEYWORD_ORDER_BY);
			orderBy.append(StringL2Helper.CHARACTER_SPACE);

			// Add each attribute to sort.
			for (int i = 0; i < size(); i++) {
				orderBy.append(getAttribute(i));
				orderBy.append(StringL2Helper.CHARACTER_SPACE);
				orderBy.append(getOrderType(i));
				orderBy.append(StringL2Helper.CHARACTER_COMMA);
				orderBy.append(StringL2Helper.CHARACTER_SPACE);
			}

			// Remove last characters.
			orderBy.delete(orderBy.length() - 2, orderBy.length() - 1);

			// Return the ORDER BY clause.
			return orderBy.toString().trim();

		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
