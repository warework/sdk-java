package com.warework.core.model;

import java.util.List;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Bean that holds the information required to create a Client in a Proxy
 * Service.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class Client {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Service of the Client.
	private ProxyService clientService;

	// Name of the Client.
	private String clientName;

	// Connector type.
	private String clientConnector;

	// Connection parameters.
	private List<Parameter> clientParameters;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Proxy Service of this Client.
	 * 
	 * @return Client's Service.<br>
	 * <br>
	 */
	public Service getService() {
		return clientService;
	}

	/**
	 * Sets the Proxy Service of this Client.
	 * 
	 * @param service
	 *            Client's Service.<br>
	 * <br>
	 */
	public void setService(Service service) {
		clientService = (ProxyService) service;
	}

	/**
	 * Gets the name to which the Client will be bound.
	 * 
	 * @return Name of the Client.<br>
	 * <br>
	 */
	public String getName() {
		return clientName;
	}

	/**
	 * Sets the name to which the Client will be bound.
	 * 
	 * @param name
	 *            Name of the Client<br>
	 * <br>
	 */
	public void setName(String name) {
		clientName = name;
	}

	/**
	 * Gets the class of the Connector that specifies which Client to create and
	 * how to create the connections for the Client.
	 * 
	 * @return Connector type.<br>
	 * <br>
	 */
	public String getConnector() {
		return clientConnector;
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
		clientConnector = connector;
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
	public List<Parameter> getParameters() {
		return clientParameters;
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
	public void setParameters(List<Parameter> parameters) {
		clientParameters = parameters;
	}

	/**
	 * Gets the representation of the Client.
	 * 
	 * @return Representation of the Client.<br>
	 * <br>
	 */
	public String toString() {

		// Set the name.
		String output = CommonValueL1Constants.STRING_NAME
				+ StringL1Helper.CHARACTER_EQUALS;
		if (getName() == null) {
			output = output + CommonValueL1Constants.STRING_NULL;
		} else {
			output = output + getName();
		}

		// Add the connector.
		output = output + StringL1Helper.CHARACTER_SPACE
				+ StringL1Helper.CHARACTER_SEMICOLON
				+ StringL1Helper.CHARACTER_SPACE
				+ CommonValueL1Constants.STRING_CONNECTOR
				+ StringL1Helper.CHARACTER_HYPHEN
				+ CommonValueL1Constants.STRING_TYPE
				+ StringL1Helper.CHARACTER_EQUALS;
		if (getConnector() == null) {
			output = output + CommonValueL1Constants.STRING_NULL;
		} else {
			output = output + getConnector();
		}

		// Add the parameters
		output = output + StringL1Helper.CHARACTER_SPACE
				+ StringL1Helper.CHARACTER_SEMICOLON
				+ StringL1Helper.CHARACTER_SPACE
				+ CommonValueL1Constants.STRING_PARAMETERS
				+ StringL1Helper.CHARACTER_EQUALS + "[";
		if ((clientParameters != null) && (clientParameters.size() > 0)) {

			// Add the parameters
			for (int index = 0; index < clientParameters.size(); index++) {

				// Get one item.
				java.lang.Object object = clientParameters.get(index);

				// Add the parameter.
				if (object instanceof Parameter) {
					output = output + object
							+ StringL1Helper.CHARACTER_SEMICOLON;
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
