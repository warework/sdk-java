package com.warework.service.datastore.model.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.DataStructureL2Helper;
import com.warework.service.datastore.DatastoreExtensionServiceConstants;
import com.warework.service.datastore.DatastoreServiceImpl;
import com.warework.service.datastore.view.DatastoreView;

/**
 * Bean that holds the information for a Data Store Service.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class DatastoreService extends
		com.warework.service.datastore.model.DatastoreService implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 7346389771940439042L;

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
	private Map<String, Datastore> clients;

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
	 * Gets the name of the Service. If no name is defined then the default
	 * Service name is returned (check out this default name at:
	 * <code>com.warework.service.datastore.DatastoreServiceConstants.DEFAULT_SERVICE_NAME</code>
	 * ).
	 * 
	 * @return Name of the Service.<br>
	 * <br>
	 */
	public String getName() {
		return (name == null) ? DatastoreExtensionServiceConstants.DEFAULT_SERVICE_NAME : name;
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
	 * Gets the implementation class of the Service. If no class is defined then
	 * the default Service class <code>DatastoreServiceImpl.class</code> is
	 * returned.
	 * 
	 * @return Implementation class of the Service.<br>
	 * <br>
	 */
	public String getClazz() {
		return (clazz == null) ? DatastoreServiceImpl.class.getName() : clazz;
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
			clients = new HashMap<String, Datastore>();
		}

		// Create a new Provider.
		Datastore serializableClient = new Datastore();

		// Setup the Data Store.
		serializableClient.setService(this);
		serializableClient.setName(client.getName());
		serializableClient.setConnector(client.getConnector());
		serializableClient.setParameters(client.getParameters());

		// Setup Views.
		if (client instanceof com.warework.service.datastore.model.Datastore) {

			// Get the Data Store bean.
			com.warework.service.datastore.model.Datastore datastore = (com.warework.service.datastore.model.Datastore) client;

			// Set the Views.
			serializableClient.setViews(datastore.getViews());

		}

		// Set the Client.
		clients.put(client.getName(), serializableClient);

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

		// Create the Client configuration.
		Datastore client = new Datastore();

		// Configure the Client.
		client.setName(name);
		client.setConnector(connector.getName());
		client.setService(this);

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
					com.warework.core.util.bean.Parameter parameter = parameters
							.get(i);

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

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the definition of a Data Store that will be created in the Service
	 * on startup.
	 * 
	 * @param name
	 *            The name to which the Data Store will be bound. If a Data
	 *            Store with the same name is already bound to the Data Store
	 *            Service, an exception will be thrown. This value cannot be
	 *            <code>null</code>.<br>
	 * <br>
	 * @param connectorType
	 *            Implementation of the Connector that specifies which Data
	 *            Store to create and how to create the connections for the Data
	 *            Store.<br>
	 * <br>
	 * @param parameters
	 *            Parameters (as string-object pairs) that specifies how the
	 *            Connector will create the connections for the Data Store.<br>
	 * <br>
	 * @param views
	 *            Stack of Views for the Data Store. The last View in the list
	 *            will be the one on the top of the stack of Views so it will be
	 *            accesible in first place. In the other hand, the first View in
	 *            the list will be accesible in last place. This value can be
	 *            <code>null</code> but if it's not, it must have objects of
	 *            <code>com.warework.service.datastore.model.View</code> type.<br>
	 * <br>
	 */
	public void setDatastore(String name,
			Class<? extends ConnectorFacade> connectorType,
			Map<String, Object> parameters,
			List<com.warework.service.datastore.model.View> views) {

		// Create a new Data Store.
		Datastore datastore = new Datastore();

		// Setup the Data Store.
		datastore.setName(name);
		datastore.setConnector(connectorType.getName());
		datastore.setParameters(Parameter.toArrayList(parameters));

		// Set the Views only if they exist.
		if ((views != null) && (views.size() > 0)) {
			datastore.setViews(views);
		}

		// Set the Data Store.
		setClient(datastore);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Adds a View in the stack of Views of a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound. This value cannot
	 *            be <code>null</code>.<br>
	 * <br>
	 * @param viewType
	 *            Implementation of the View to be bounded into the Data Store.<br>
	 * <br>
	 * @param viewName
	 *            Name of the View to include in the Data Store.<br>
	 * <br>
	 * @param statementProvider
	 *            The name of the Provider where to extract statements. This
	 *            value can be <code>null</code>.<br>
	 * <br>
	 */
	public void setView(String datastoreName,
			Class<? extends DatastoreView> viewType, String viewName,
			String statementProvider, Map<String, Object> parameters) {

		// Create the View.
		View view = new View();

		// Setup the View.
		view.setClazz(viewType.getName());
		view.setName(viewName);
		view.setStatementProvider(statementProvider);
		view.setParameters(Parameter.toArrayList(parameters));

		// Add View in Data Store.
		setView(datastoreName, view);

	}

	/**
	 * Gets the View that exists on top of the stack of Views of a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound. This value cannot
	 *            be <code>null</code>.<br>
	 * <br>
	 * @return A Data Store View or <code>null</code> if no View exist for the
	 *         Data Store.<br>
	 * <br>
	 */
	public com.warework.service.datastore.model.View getView(
			String datastoreName) {

		// Get the definition of a Data Store.
		Datastore datastore = (Datastore) getClient(datastoreName);

		// Add the parameter only if the Data Store exists.
		if (datastore != null) {

			// Get the Views of the Data Store.
			List<com.warework.service.datastore.model.View> views = datastore
					.getViews();

			// Create the Views if it's null.
			if ((views != null) && (views.size() > 0)) {
				return views.get(views.size() - 1);
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes the View that exists on top of the stack of Views of a Data
	 * Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound. This value cannot
	 *            be <code>null</code>.<br>
	 * <br>
	 */
	public void removeView(String datastoreName) {

		// Get the definition of a Data Store.
		Datastore datastore = (Datastore) getClient(datastoreName);

		// Add the parameter only if the Data Store exists.
		if (datastore != null) {

			// Get the Views of the Data Store.
			List<com.warework.service.datastore.model.View> views = datastore
					.getViews();

			// Create the Views if it's null.
			if ((views != null) && (views.size() > 0)) {
				views.remove(views.size() - 1);
			}

		}

	}

}
