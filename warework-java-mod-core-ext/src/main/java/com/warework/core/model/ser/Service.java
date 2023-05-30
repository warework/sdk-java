package com.warework.core.model.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;

/**
 * Bean that holds the information required to create a Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class Service extends com.warework.core.model.Service implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 5055030486412856279L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private com.warework.core.model.Scope scope;

	// Name of the Service.
	private String name;

	// Implementation class of the Service.
	private String clazz;

	// Parameters that configure the Service.
	private List<com.warework.core.util.bean.Parameter> parameters;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Service belongs to.
	 * 
	 * @return Scope configuration.<br>
	 * <br>
	 */
	public com.warework.core.model.Scope getScope() {
		return scope;
	}

	/**
	 * Sets the Scope where this Service belongs to.
	 * 
	 * @param scope
	 *            Scope configuration.<br>
	 * <br>
	 */
	public void setScope(com.warework.core.model.Scope scope) {
		this.scope = scope;
	}

	/**
	 * Gets the name of the Service.
	 * 
	 * @return Name of the Service.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the Service.
	 * 
	 * @param name
	 *            Name for the Service.<br>
	 * <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the implementation class of the Service.
	 * 
	 * @return Implementation class of the Service.<br>
	 * <br>
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * Sets the implementation class of the Service.
	 * 
	 * @param clazz
	 *            The implementation class of the Service.<br>
	 * <br>
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the parameters used to configure the Service on starup.
	 * 
	 * @return List of parameters.<br>
	 * <br>
	 */
	public List<com.warework.core.util.bean.Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Gets the parameters used to configure the Service on starup.
	 * 
	 * @param parameters
	 *            List of parameters.<br>
	 * <br>
	 */
	public void setParameters(
			List<com.warework.core.util.bean.Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Sets the value for a parameter.
	 * 
	 * @param name
	 *            Name of the parameter.<br>
	 * <br>
	 * @param value
	 *            Value of the parameter.<br>
	 * <br>
	 */
	public void setParameter(String name, java.lang.Object value) {

		// Create the parameter's context when required.
		if (parameters == null) {
			parameters = new ArrayList<com.warework.core.util.bean.Parameter>();
		}

		// Create a new parameter.
		Parameter parameter = new Parameter();

		// Add the values in the parameter.
		parameter.setName(name);
		parameter.setValue(value);

		// Set the parameter.
		parameters.add(parameter);

	}

	/**
	 * Gets the value of a parameter.
	 * 
	 * @param name
	 *            Name of the parameter.<br>
	 * <br>
	 * @return Value of the parameter.<br>
	 * <br>
	 */
	public java.lang.Object getParameter(String name) {
		if (parameters != null) {
			return Parameter.toHashMap(parameters).get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of the parameters.
	 * 
	 * @return Parameters' names or null if no one exist.<br>
	 * <br>
	 */
	public Enumeration<String> getParameterNames() {

		// Return the names of the parameters.
		if ((parameters != null) && (parameters.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(Parameter.toHashMap(
					parameters).keySet());
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes an initialization parameter.
	 * 
	 * @param name
	 *            Name of the parameter to remove.<br>
	 * <br>
	 */
	public void removeParameter(String name) {
		if (parameters != null) {

			// Get the parameters as a map.
			Map<String, Object> initParameters = Parameter
					.toHashMap(parameters);

			// Remove the parameter.
			initParameters.remove(name);

			// Remove the parameters' context.
			if (initParameters.size() < 1) {
				parameters = null;
			} else {
				parameters = Parameter.toArrayList(initParameters);
			}

		}
	}

}
