package com.warework.core.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;

/**
 * Bean that holds the information required to create a Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class Service {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private Scope serviceScope;

	// Name of the Service.
	private String serviceName;

	// Implementation class of the Service.
	private String serviceClass;

	// Parameters that configure the Service.
	private List<Parameter> serviceParameters;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Service belongs to.
	 * 
	 * @return Scope configuration.<br>
	 * <br>
	 */
	public Scope getScope() {
		return serviceScope;
	}

	/**
	 * Sets the Scope where this Service belongs to.
	 * 
	 * @param scope
	 *            Scope configuration.<br>
	 * <br>
	 */
	public void setScope(Scope scope) {
		serviceScope = scope;
	}

	/**
	 * Gets the name of the Service.
	 * 
	 * @return Name of the Service.<br>
	 * <br>
	 */
	public String getName() {
		return serviceName;
	}

	/**
	 * Sets the name for the Service.
	 * 
	 * @param name
	 *            Name for the Service.<br>
	 * <br>
	 */
	public void setName(String name) {
		serviceName = name;
	}

	/**
	 * Gets the implementation class of the Service.
	 * 
	 * @return Implementation class of the Service.<br>
	 * <br>
	 */
	public String getClazz() {
		return serviceClass;
	}

	/**
	 * Sets the implementation class of the Service.
	 * 
	 * @param clazz
	 *            The implementation class of the Service.<br>
	 * <br>
	 */
	public void setClazz(String clazz) {
		serviceClass = clazz;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the parameters used to configure the Service on starup.
	 * 
	 * @return List of parameters.<br>
	 * <br>
	 */
	public List<Parameter> getParameters() {
		return serviceParameters;
	}

	/**
	 * Gets the parameters used to configure the Service on starup.
	 * 
	 * @param parameters
	 *            List of parameters.<br>
	 * <br>
	 */
	public void setParameters(List<Parameter> parameters) {
		serviceParameters = parameters;
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
		if (serviceParameters == null) {
			serviceParameters = new ArrayList<Parameter>();
		}

		// Create a new parameter.
		Parameter parameter = new Parameter();

		// Add the values in the parameter.
		parameter.setName(name);
		parameter.setValue(value);

		// Set the parameter.
		serviceParameters.add(parameter);

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
		if (serviceParameters != null) {
			return Parameter.toHashMap(serviceParameters).get(name);
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
		if ((serviceParameters != null) && (serviceParameters.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(Parameter.toHashMap(serviceParameters)
					.keySet());
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
		if (serviceParameters != null) {

			// Get the parameters as a map.
			Map<String, Object> initParameters = Parameter
					.toHashMap(serviceParameters);

			// Remove the parameter.
			initParameters.remove(name);

			// Remove the parameters' context.
			if (initParameters.size() < 1) {
				serviceParameters = null;
			} else {
				serviceParameters = Parameter.toArrayList(initParameters);
			}

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Service.
	 * 
	 * @return Name of the Service.<br>
	 * <br>
	 */
	public String toString() {
		return getName();
	}

}
