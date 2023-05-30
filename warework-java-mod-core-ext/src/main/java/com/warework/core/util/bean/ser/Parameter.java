package com.warework.core.util.bean.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bean that holds a name-value pair to represent a parameter.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class Parameter extends com.warework.core.util.bean.Parameter implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 1596233440043987222L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the parameter.
	private String parameterName;

	// Value of the parameter.
	private Object parameterValue;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	public Parameter() {

	}

	/**
	 * Creates the parameter with the name and the value.
	 * 
	 * @param name
	 *            Name of the parameter.<br>
	 * <br>
	 * @param value
	 *            Value of the parameter.<br>
	 * <br>
	 */
	public Parameter(String name, Object value) {

		// Set the name of the parameter.
		this.parameterName = name;

		// Set the value of the parameter.
		this.parameterValue = value;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the parameter.
	 * 
	 * @return Name of the parameter.<br>
	 * <br>
	 */
	public String getName() {
		return parameterName;
	}

	/**
	 * Sets the name of the parameter.
	 * 
	 * @param name
	 *            Name of the parameter.<br>
	 * <br>
	 */
	public void setName(String name) {
		parameterName = name;
	}

	/**
	 * Gets the value of the parameter.
	 * 
	 * @return Value of the parameter.<br>
	 * <br>
	 */
	public Object getValue() {
		return parameterValue;
	}

	/**
	 * Sets the value of the parameter.
	 * 
	 * @param value
	 *            Value of the parameter.<br>
	 * <br>
	 */
	public void setValue(Object value) {
		parameterValue = value;
	}

	// ///////////////////////////////////////////////////////////////////
	// STATIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a <code>java.util.Collection</code> of parameters into an
	 * <code>java.util.HashMap</code>.
	 * 
	 * @param parameters
	 *            Collection of parameters.<br>
	 * <br>
	 * @return A <code>java.util.HashMap</code> where the key is the name of the
	 *         parameter and the value is the value of the parameter.<br>
	 * <br>
	 */
	public static Map<String, Object> toHashMap(
			Collection<com.warework.core.util.bean.Parameter> parameters) {

		// Transform the list into a HashMap.
		if ((parameters != null) && (parameters.size() > 0)) {

			// Create the HashMap to return.
			HashMap<String, Object> result = new HashMap<String, Object>();

			// Copy each parameter into the HashMap.
			for (Iterator<com.warework.core.util.bean.Parameter> iterator = parameters
					.iterator(); iterator.hasNext();) {

				// Get one parameter.
				com.warework.core.util.bean.Parameter parameter = iterator
						.next();

				// Add the parameter.
				result.put(parameter.getName(), parameter.getValue());
			}

			// Return the HashMap.
			if (result.size() > 0) {
				return result;
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Converts a <code>java.util.Map</code> of strings into an
	 * <code>java.util.List</code> of parameters.
	 * 
	 * @param parameters
	 *            Collection of strings.<br>
	 * <br>
	 * @return A new <code>java.util.ArrayList</code> with parameter objetcs.<br>
	 * <br>
	 */
	public static List<com.warework.core.util.bean.Parameter> toArrayList(
			Map<String, Object> parameters) {

		// Transform the Hashtable into a Vector.
		if ((parameters != null) && (parameters.size() > 0)) {

			// Create the Vector to return.
			List<com.warework.core.util.bean.Parameter> result = new ArrayList<com.warework.core.util.bean.Parameter>();

			// Get the keys of the Hashtable.
			Set<String> keys = parameters.keySet();

			// Copy each parameter into the Hashtable.
			for (Iterator<String> iterator = keys.iterator(); iterator
					.hasNext();) {

				// Get the name of the parameter.
				String name = iterator.next();

				// Create the parameter.
				Parameter parameter = new Parameter();

				// Set the values of the parameter.
				parameter.setName(name);
				parameter.setValue(parameters.get(name));

				// Add the parameter.
				result.add(parameter);

			}

			// Return the Hashtable.
			if (result.size() > 0) {
				return result;
			}

		}

		// At this point, nothing to return.
		return null;

	}

}
