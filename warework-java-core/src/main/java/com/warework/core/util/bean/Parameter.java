package com.warework.core.util.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Bean that holds a name-value pair to represent a parameter.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class Parameter {

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
	 * @param name  Name of the parameter.<br>
	 *              <br>
	 * @param value Value of the parameter.<br>
	 *              <br>
	 */
	public Parameter(final String name, final Object value) {

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
	 *         <br>
	 */
	public String getName() {
		return parameterName;
	}

	/**
	 * Sets the name of the parameter.
	 * 
	 * @param name Name of the parameter.<br>
	 *             <br>
	 */
	public void setName(final String name) {
		parameterName = name;
	}

	/**
	 * Gets the value of the parameter.
	 * 
	 * @return Value of the parameter.<br>
	 *         <br>
	 */
	public Object getValue() {
		return parameterValue;
	}

	/**
	 * Sets the value of the parameter.
	 * 
	 * @param value Value of the parameter.<br>
	 *              <br>
	 */
	public void setValue(final Object value) {
		parameterValue = value;
	}

	/**
	 * Gets the representation of the parameter.
	 * 
	 * @return Representation of the parameter.<br>
	 *         <br>
	 */
	public String toString() {

		// Set the name.
		String output = null;
		if (getName() == null) {
			output = CommonValueL1Constants.STRING_NULL;
		} else {
			output = getName();
		}

		// Add the equal symbol.
		output = output + StringL1Helper.CHARACTER_EQUALS;

		// Set the value.
		if (getValue() == null) {
			output = output + CommonValueL1Constants.STRING_NULL;
		} else if (getValue() instanceof String) {
			output = output + getValue();
		} else {
			output = output + getValue().toString();
		}

		// Return the parameter representation.
		return output;

	}

	// ///////////////////////////////////////////////////////////////////
	// STATIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a <code>java.util.Collection</code> of parameters into an
	 * <code>java.util.HashMap</code>.
	 * 
	 * @param parameters Collection of parameters.<br>
	 *                   <br>
	 * @return A <code>java.util.HashMap</code> where the key is the name of the
	 *         parameter and the value is the value of the parameter.<br>
	 *         <br>
	 */
	public static Map<String, Object> toHashMap(final Collection<Parameter> parameters) {

		// Transform the vector into a Map.
		if ((parameters != null) && (parameters.size() > 0)) {

			// Create the Map to return.
			final Map<String, Object> result = new HashMap<String, Object>();

			// Copy each parameter into the Map.
			for (final Iterator<Parameter> iterator = parameters.iterator(); iterator.hasNext();) {

				// Get the parameter.
				final Parameter parameter = iterator.next();

				// Add the parameter.
				result.put(parameter.getName(), parameter.getValue());

			}

			// Return the Map.
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
	 * @param parameters Collection of strings.<br>
	 *                   <br>
	 * @return A new <code>java.util.ArrayList</code> with parameter objetcs.<br>
	 *         <br>
	 */
	public static List<Parameter> toArrayList(final Map<String, Object> parameters) {

		// Transform the Map into a Vector.
		if ((parameters != null) && (parameters.size() > 0)) {

			// Create the Vector to return.
			final List<Parameter> result = new ArrayList<Parameter>();

			// Get the keys of the Map.
			final Set<String> keys = parameters.keySet();

			// Copy each parameter into the Map.
			for (final Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

				// Get the name of the parameter.
				final String name = iterator.next();

				// Create the parameter.
				final Parameter parameter = new Parameter();

				// Set the values of the parameter.
				parameter.setName(name);
				parameter.setValue(parameters.get(name));

				// Add the parameter.
				result.add(parameter);

			}

			// Return the Map.
			if (result.size() > 0) {
				return result;
			}

		}

		// At this point, nothing to return.
		return null;

	}

}
