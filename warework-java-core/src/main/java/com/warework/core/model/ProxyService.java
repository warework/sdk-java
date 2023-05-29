package com.warework.core.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;

/**
 * Bean that holds the information required to create a Proxy Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class ProxyService extends Service {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Context for clients.
	private Map<String, Client> clients;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a Client to be created on Service startup.
	 * 
	 * @param client
	 *            Object that holds the Client's configuration.<br>
	 * <br>
	 */
	public void setClient(Client client) {

		// Create the parameter's context when required.
		if (clients == null) {
			clients = new HashMap<String, Client>();
		}

		// Set the Service for this Client.
		client.setService(this);

		// Set the Client.
		clients.put(client.getName(), client);

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
		Client client = new Client();

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
	public Client getClient(String name) {
		if (clients != null) {
			return (Client) clients.get(name);
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
			return DataStructureL1Helper.toEnumeration(clients.keySet());
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
		Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Create the parameter.
			Parameter parameter = new Parameter();

			// Set the name and the value of the parameter.
			parameter.setName(parameterName);
			parameter.setValue(parameterValue);

			// Get the parameters' list.
			List<Parameter> parameters = client.getParameters();
			if (parameters == null) {
				parameters = new ArrayList<Parameter>();
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
		Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			List<Parameter> parameters = client.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if ((parameters != null) && (parameters.size() > 0)) {

				// Convert the list into a map.
				Map<String, Object> params = Parameter.toHashMap(parameters);

				// Return the parameter names.
				return DataStructureL1Helper.toEnumeration(params.keySet());

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
		Client client = getClient(clientName);

		// Add the parameter only if the Client exists.
		if (client != null) {

			// Get the parameters' list.
			List<Parameter> parameters = client.getParameters();

			// Remove the parameter only if at least one parameter exists.
			if (parameters != null) {

				// Create a new vector where to copy the parameters that must
				// remain for the Client.
				List<Parameter> parametersCopy = new ArrayList<Parameter>();

				// Copy each parameter that must remain for the Client.
				for (Iterator<Parameter> iterator = parameters.iterator(); iterator
						.hasNext();) {

					// Get a parameter.
					Parameter parameter = iterator.next();

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
