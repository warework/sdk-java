package com.warework.core.model.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.DataStructureL2Helper;

/**
 * Bean that holds the information required to create a Proxy Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class ProxyService extends com.warework.core.model.ProxyService
		implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -5882702979436695448L;

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

	// Context for clients.
	private Map<String, com.warework.core.model.ser.Client> clients;

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
			return DataStructureL2Helper.toEnumeration(Parameter.toHashMap(
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

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Client to be created on Service startup.
	 * 
	 * @param client
	 *            Object that holds the Client's configuration.<br>
	 * <br>
	 */
	public void setClient(com.warework.core.model.Client client) {

		// Create the parameter's context when required.
		if (clients == null) {
			clients = new HashMap<String, com.warework.core.model.ser.Client>();
		}

		// Make the Client serializable if required.
		com.warework.core.model.ser.Client serializableClient = null;
		if (client instanceof com.warework.core.model.ser.Client) {
			serializableClient = (com.warework.core.model.ser.Client) client;
		} else {

			// Create a new serialiable Client.
			serializableClient = new com.warework.core.model.ser.Client();

			// Setup Client.
			serializableClient.setName(client.getName());
			serializableClient.setConnector(client.getConnector());
			serializableClient.setParameters(client.getParameters());

		}

		// Set the Service for this Client.
		serializableClient.setService(this);

		// Set the Client.
		clients.put(serializableClient.getName(), serializableClient);

	}

	/**
	 * Sets a Client to be created on Service startup.
	 * 
	 * @param name
	 *            The name to which the Client is bound. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @param connector
	 *            Connector type.<br>
	 * <br>
	 */
	public void setClient(String name,
			Class<? extends ConnectorFacade> connector) {
		setClient(name, connector, null);
	}

	/**
	 * Sets a Client to be created on Service startup.
	 * 
	 * @param name
	 *            The name to which the Client is bound. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @param connector
	 *            Connector type.<br>
	 * <br>
	 * @param parameters
	 *            Parameters (as string-object pairs) that specifies how the
	 *            Connector will create the connections for the Client.<br>
	 * <br>
	 */
	public void setClient(String name,
			Class<? extends ConnectorFacade> connector,
			Map<String, Object> parameters) {

		// Create the Client configuration.
		com.warework.core.model.ser.Client client = new com.warework.core.model.ser.Client();

		// Configure the Client.
		client.setName(name);
		client.setConnector(connector.getName());
		client.setParameters(Parameter.toArrayList(parameters));

		// Set the Client.
		setClient(client);

	}

	/**
	 * Gets the definition of a Client to be created in the Service on startup.
	 * 
	 * @param name
	 *            The name to which the Client is bound.<br>
	 * <br>
	 * @return A Client definition or null if no Client exists.<br>
	 * <br>
	 */
	public com.warework.core.model.Client getClient(String name) {
		if ((clients != null) && (clients.containsKey(name))) {
			return (com.warework.core.model.Client) clients.get(name);
		} else {
			return null;
		}
	}

	/**
	 * Gets the names of all the clients bound to the Service.
	 * 
	 * @return Names of the clients or null if no one exist.<br>
	 * <br>
	 */
	public Enumeration<String> getClientNames() {

		// Return the names of the clients.
		if ((clients != null) && (clients.size() > 0)) {
			return DataStructureL2Helper.toEnumeration(clients.keySet());
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes the definition of a Client.
	 * 
	 * @param name
	 *            The name to which the Client is bound. If <code>null</code>
	 *            then every Client is removed.<br>
	 * <br>
	 */
	public void removeClient(String name) {
		if (clients != null) {
			if (name != null) {

				// Remove the Client.
				clients.remove(name);

				// Remove the parameters' context.
				if (clients.size() < 1) {
					clients = null;
				}

			} else {
				clients = null;
			}
		}
	}

	/**
	 * Adds a parameter in the configuration of a Client.
	 * 
	 * @param clientName
	 *            The name to which the Client is bound. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @param parameterName
	 *            Name of the parameter.<br>
	 * <br>
	 * @param parameterValue
	 *            Value of the parameter.<br>
	 * <br>
	 */
	public void setClientParameter(String clientName, String parameterName,
			java.lang.Object parameterValue) {

		// Get the definition of a cliente.
		com.warework.core.model.Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Create the parameter.
			Parameter parameter = new Parameter();

			// Set the name and the value of the parameter.
			parameter.setName(parameterName);
			parameter.setValue(parameterValue);

			// Get the parameters' list.
			List<com.warework.core.util.bean.Parameter> parameters = client
					.getParameters();
			if (parameters == null) {
				parameters = new ArrayList<com.warework.core.util.bean.Parameter>();
			}

			// Add the parameter in the list.
			parameters.add(parameter);

			// Update the parameters' list.
			client.setParameters(parameters);

		}

	}

	/**
	 * Gets the names of all the parameters of the configuration of a Client.
	 * 
	 * @param clientName
	 *            The name to which the Client is bound. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @return Name of each parameter of the configuration of a Client.<br>
	 * <br>
	 */
	public Enumeration<String> getClientParameterNames(String clientName) {

		// Get the definition of a Client.
		com.warework.core.model.Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			List<com.warework.core.util.bean.Parameter> parameters = client
					.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {

				// Convert the list into a hashtable.
				Map<String, Object> params = Parameter.toHashMap(parameters);

				// Return the parameter names.
				return DataStructureL2Helper.toEnumeration(params.keySet());

			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes a parameter from the configuration of a Client.
	 * 
	 * @param clientName
	 *            The name to which the Client is bound. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @param parameterName
	 *            Name of the parameter to remove.<br>
	 * <br>
	 */
	public void removeClientParameter(String clientName, String parameterName) {

		// Get the definition of a Client.
		com.warework.core.model.Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			List<com.warework.core.util.bean.Parameter> parameters = client
					.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if (parameters != null) {

				// Create a new vector where to copy the parameters that must
				// remain for the Client.
				List<com.warework.core.util.bean.Parameter> parametersCopy = new ArrayList<com.warework.core.util.bean.Parameter>();

				// Copy each parameter that must remain for the Client.
				for (int i = 0; i < parameters.size(); i++) {

					// Get a parameter.
					Parameter parameter = (Parameter) parameters.get(i);

					// Avoid the parameter to remove.
					if (!parameter.getName().equals(parameterName)) {
						parametersCopy.add(parameter);
					}

				}

				// Update the parameters' list.
				if (parametersCopy.size() > 0) {
					parameters = parametersCopy;
				} else {
					parameters = null;
				}

			}

			// Update the parameters' list.
			client.setParameters(parameters);

		}

	}

}
