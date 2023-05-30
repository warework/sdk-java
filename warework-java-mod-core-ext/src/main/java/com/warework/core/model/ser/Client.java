package com.warework.core.model.ser;

import java.io.Serializable;
import java.util.List;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Bean that holds the information required to create a Client in a Proxy
 * Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class Client extends com.warework.core.model.Client implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -2130103365478510928L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Service of the Client.
	private com.warework.core.model.Service service;

	// Name of the Client.
	private String name;

	// Connector type.
	private String connector;

	// Connection parameters.
	private List<com.warework.core.util.bean.Parameter> parameters;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Proxy Service of this Client.
	 * 
	 * @return Client's Service.<br>
	 * <br>
	 */
	public com.warework.core.model.Service getService() {
		return service;
	}

	/**
	 * Sets the Proxy Service of this Client.
	 * 
	 * @param service
	 *            Client's Service.<br>
	 * <br>
	 */
	public void setService(com.warework.core.model.Service service) {
		this.service = service;
	}

	/**
	 * Gets the name to which the Client will be bound.
	 * 
	 * @return Name of the Client.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name to which the Client will be bound.
	 * 
	 * @param name
	 *            Name of the Client<br>
	 * <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the class of the Connector that specifies which Client to create and
	 * how to create the connections for the Client.
	 * 
	 * @return Connector type.<br>
	 * <br>
	 */
	public String getConnector() {
		return connector;
	}

	/**
	 * Sets the class of the Connector that specifies which Client to create and
	 * how to create the connections for the Client.
	 * 
	 * @param connector
	 *            Connector type.<br>
	 * <br>
	 */
	public void setConnector(String connector) {
		this.connector = connector;
	}

	/**
	 * Gets the Client's parameter list that specifies how the Connector will
	 * create the connections for the Client.
	 * 
	 * @return List of parameters (with
	 *         <code>com.warework.core.util.bean.Parameter</code> objects in
	 *         it).<br>
	 * <br>
	 */
	public List<com.warework.core.util.bean.Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Sets the Client's parameter list that specifies how the Connector will
	 * create the connections for the Client.
	 * 
	 * @param parameters
	 *            List of parameters (with
	 *            <code>com.warework.core.util.bean.Parameter</code> objects in
	 *            it).<br>
	 * <br>
	 */
	public void setParameters(
			List<com.warework.core.util.bean.Parameter> parameters) {
		this.parameters = Parameter.toArrayList(Parameter.toHashMap(parameters));
	}

	/**
	 * Gets the representation of the Client.
	 * 
	 * @return Representation of the Client.<br>
	 * <br>
	 */
	public String toString() {

		// Set the name.
		String output = CommonValueL2Constants.STRING_NAME
				+ StringL2Helper.CHARACTER_EQUALS;
		if (getName() == null) {
			output = output + CommonValueL2Constants.STRING_NULL;
		} else {
			output = output + getName();
		}

		// Add the connector.
		output = output + StringL2Helper.CHARACTER_SPACE
				+ StringL2Helper.CHARACTER_SEMICOLON
				+ StringL2Helper.CHARACTER_SPACE
				+ CommonValueL2Constants.STRING_CONNECTOR
				+ StringL2Helper.CHARACTER_HYPHEN
				+ CommonValueL2Constants.STRING_TYPE
				+ StringL2Helper.CHARACTER_EQUALS;
		if (getConnector() == null) {
			output = output + CommonValueL2Constants.STRING_NULL;
		} else {
			output = output + getConnector();
		}

		// Add the parameters
		output = output + StringL2Helper.CHARACTER_SPACE
				+ StringL2Helper.CHARACTER_SEMICOLON
				+ StringL2Helper.CHARACTER_SPACE
				+ CommonValueL2Constants.STRING_PARAMETERS
				+ StringL2Helper.CHARACTER_EQUALS + "[";
		if ((parameters != null) && (parameters.size() > 0)) {

			// Add the parameters
			for (int index = 0; index < parameters.size(); index++) {

				// Get one item.
				java.lang.Object object = parameters.get(index);

				// Add the parameter.
				if (object instanceof Parameter) {
					output = output + object
							+ StringL2Helper.CHARACTER_SEMICOLON;
				}

			}

			// Remove the last ';'.
			output = output.substring(0, output.length() - 1);

		}
		output = output + "]";

		// Return the parameter representation.
		return output;

	}

}
