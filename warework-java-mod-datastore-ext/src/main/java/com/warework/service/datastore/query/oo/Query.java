package com.warework.service.datastore.query.oo;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.data.OrderBy;
import com.warework.core.util.helper.DataStructureL2Helper;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Defines how to perform a query in an Object-Oriented Data Store.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Query<E> implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 5428446440901519265L;

	/**
	 * Keyword 'PAGE'.
	 */
	public static final String KEYWORD_PAGE = "PAGE";

	/**
	 * Keyword 'PAGE_SIZE'.
	 */
	public static final String KEYWORD_PAGE_SIZE = "SIZE";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope.
	private transient ScopeFacade scope;

	// Name of the query.
	private String name;

	// Object to query.
	private Class<E> object;

	// WHERE clause for the query.
	private Where where;

	// ORDER BY clause for the query.
	private OrderBy orderBy;

	// Page to retrieve.
	private int pageNumber = -1;

	// Max. results per page.
	private int pageResults = -1;

	// Parameters of the query.
	private Map<String, Object> parameters;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private Query() {
		// DO NOTHING.
	}

	/**
	 * Creates a query for a specific object type.
	 * 
	 * @param scope  Scope.<br>
	 *               <br>
	 * @param object Type of the object to query in the Object-Oriented Data
	 *               Store.<br>
	 *               <br>
	 */
	public Query(final ScopeFacade scope, final Class<E> object) {
		this();
		this.scope = scope;
		this.object = object;
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new query.
	 * 
	 * @param scope  Scope.<br>
	 *               <br>
	 * @param object Type of the object to query in the Object-Oriented Data
	 *               Store.<br>
	 *               <br>
	 * @return A new query for a specific object type.
	 */
	public static <E> Query<E> create(final ScopeFacade scope, final Class<E> object) {
		return new Query<E>(scope, object);
	}

	/**
	 * Gets the Scope used in this WHERE clause.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	public ScopeFacade getScopeFacade() {
		return scope;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the query.
	 * 
	 * @return Name of the query.<br>
	 *         <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the query.
	 * 
	 * @param name Name for the query.<br>
	 *             <br>
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the type of the object to query in the Object-Oriented Data Store.
	 * 
	 * @return Object to query.<br>
	 *         <br>
	 */
	public Class<E> getObject() {
		return object;
	}

	/**
	 * Gets the WHERE clause.
	 * 
	 * @return WHERE clause<br>
	 *         <br>
	 */
	public Where getWhere() {
		return getWhere(false);
	}

	/**
	 * Gets the WHERE clause.
	 * 
	 * @param create <code>true</code> creates a new WHERE clause (if a WHERE clause
	 *               already exists, then it is replaced for a new empty one) and
	 *               <code>false</code> just returns <code>null</code> if no WHERE
	 *               clause were created or returns the previously created WHERE
	 *               clause.<br>
	 *               <br>
	 * @return WHERE clause<br>
	 *         <br>
	 */
	public Where getWhere(final boolean create) {

		// Create a new WHERE clause if required.
		if (create) {
			where = new Where(this);
		}

		// Return the WHERE clause.
		return where;

	}

	/**
	 * Gets the ORDER BY clause.
	 * 
	 * @return ORDER BY clause.<br>
	 *         <br>
	 */
	public OrderBy getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the ORDER BY clause.
	 * 
	 * @param orderBy ORDER BY clause.<br>
	 *                <br>
	 */
	public void setOrderBy(final OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Gets the page to get from the result.
	 * 
	 * @return Page to retrieve.<br>
	 *         <br>
	 */
	public int getPage() {
		return pageNumber;
	}

	/**
	 * Sets the page to get from the result.
	 * 
	 * @param page Page to retrieve.<br>
	 *             <br>
	 */
	public void setPage(final int page) {
		this.pageNumber = page;
	}

	/**
	 * Gets the maximum results per page.
	 * 
	 * @return Results per page.<br>
	 *         <br>
	 */
	public int getPageSize() {
		return pageResults;
	}

	/**
	 * Sets the maximum results per page.
	 * 
	 * @param pageSize Results per page.<br>
	 *                 <br>
	 */
	public void setPageSize(final int pageSize) {
		this.pageResults = pageSize;
	}

	/**
	 * Specifies if this query is paginated.
	 * 
	 * @return <code>true</code> if page and page size values are greater than
	 *         <code>-1</code>. If not, then returns <code>false</code>.
	 */
	public boolean isPaginated() {
		return ((getPage() > -1) && (getPageSize() > -1));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the value for a parameter.
	 * 
	 * @param name  Name of the parameter.<br>
	 *              <br>
	 * @param value Value of the parameter.<br>
	 *              <br>
	 */
	public void setParameter(final String name, final Object value) {

		// Create the parameter's context when required.
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}

		// Set the parameter.
		parameters.put(name, value);

	}

	/**
	 * Gets the names of the parameters.
	 * 
	 * @return Parameters' names or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getParameterNames() {
		return (parameters == null) ? null : DataStructureL2Helper.toEnumeration(parameters.keySet());
	}

	/**
	 * Gets the value of an parameter.
	 * 
	 * @param name Name of the parameter.<br>
	 *             <br>
	 * @return Value of the parameter.<br>
	 *         <br>
	 */
	public Object getParameter(final String name) {

		// Retrieve the value only if initialization parameters is not empty.
		if (parameters != null) {
			return parameters.get(name);
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the boolean value of a parameter.
	 * 
	 * @param name Name of the parameter.<br>
	 *             <br>
	 * @return <code>true</code> if the parameter is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that is
	 *         equals (in any case form) to <code>true</code>. Otherwise, this
	 *         method returns <code>false</code> (for example: if it does not
	 *         exist).<br>
	 *         <br>
	 */
	public boolean isParameter(final String name) {

		// Get the value of the parameter.
		final Object value = getParameter(name);

		// Decide the boolean value of the parameter.
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if ((value instanceof String)
					&& (((String) value).equalsIgnoreCase(CommonValueL2Constants.STRING_TRUE))) {
				return true;
			}
		}

		// At this point, default result is false.
		return false;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the representation of the query with an Object-Oriented notation.
	 * 
	 * @return Representation of the query.<br>
	 *         <br>
	 */
	public String toString() {

		// Create the String only if there is an object type specified for the
		// query.
		if (getObject() != null) {

			// Output String.
			final StringBuilder output = new StringBuilder();

			// Set the class name.
			output.append(ReflectionL2Helper.getClassName(getObject()));

			// Set the WHERE clause.
			if (getWhere() != null) {

				// Create the WHERE clause.
				final String whereClause = getWhere().toString().trim();

				// Set the WHERE clause and remove LR-empty spaces.
				if (!whereClause.equals(CommonValueL2Constants.STRING_EMPTY)) {
					output.append(StringL2Helper.CHARACTER_SPACE);
					output.append(whereClause);
				}

			}

			// Set the ORDER BY clause.
			if (getOrderBy() != null) {

				// Create the ORDER BY clause.
				final String orderByClause = getOrderBy().toString().trim();

				// Set the ORDER BY clause and remove LR-empty spaces.
				if (!orderByClause.equals(CommonValueL2Constants.STRING_EMPTY)) {
					output.append(StringL2Helper.CHARACTER_SPACE);
					output.append(orderByClause);
				}

			}

			// Set the page clause.
			if ((getPage() > 0) && (getPageSize() > 0)) {
				output.append(StringL2Helper.CHARACTER_SPACE);
				output.append(KEYWORD_PAGE);
				output.append(StringL2Helper.CHARACTER_SPACE);
				output.append(getPage());
				output.append(StringL2Helper.CHARACTER_SPACE);
				output.append(KEYWORD_PAGE_SIZE);
				output.append(StringL2Helper.CHARACTER_SPACE);
				output.append(getPageSize());
			}

			// Return the String.
			return output.toString();

		}

		// At this point, String is empty.
		return CommonValueL2Constants.STRING_EMPTY;

	}

}
