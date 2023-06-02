package com.warework.core.service.client;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Client.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractClient implements ClientFacade {

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Client's attributes.
	 */
	public final class ClientAttributes {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Client's attributes.
		private Map<String, Object> attributes;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the Client's attributes manager.
		 * 
		 * @param attributes Client's attributes.<br>
		 *                   <br>
		 */
		private ClientAttributes(final Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the value of an attribute.
		 * 
		 * @param name Name of the attribute.<br>
		 *             <br>
		 * @return Value of the attribute.<br>
		 *         <br>
		 */
		public Object getAttribute(final String name) {
			return (attributes == null) ? null : attributes.get(name);
		}

		/**
		 * Gets the names of the attributes.
		 * 
		 * @return Attributes' names or null if no one exist.<br>
		 *         <br>
		 */
		public Enumeration<String> getAttributeNames() {
			return (attributes == null) ? null : DataStructureL1Helper.toEnumeration(attributes.keySet());
		}

		/**
		 * Gets the boolean value of an attribute.
		 * 
		 * @param name Name of the attribute.<br>
		 *             <br>
		 * @return <code>true</code> if the attribute is a
		 *         <code>java.lang.Boolean</code> that is equals to
		 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that is
		 *         equals, in any case form, to <code>true</code>. Otherwise, this
		 *         method returns <code>false</code> (for example: if it does not
		 *         exist).<br>
		 *         <br>
		 */
		public boolean isAttribute(final String name) {

			// Get the value of the attribute.
			final Object value = getAttribute(name);

			// Decide the boolean value of the attribute.
			if (value != null) {
				if (value instanceof Boolean) {
					return ((Boolean) value).booleanValue();
				} else if ((value instanceof String)
						&& (((String) value).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
					return true;
				}
			}

			// At this point, default result is false.
			return false;

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Client's Connector.
	private ConnectorFacade connector;

	// Client's connection.
	private Object connection;

	// Client's attributes.
	private ClientAttributes clientAttributes;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Closes the connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         Client.<br>
	 *                         <br>
	 */
	protected abstract void close() throws ClientException;

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected abstract boolean isClosed();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Client.
	 * 
	 * @param connector Information required to create connections for this
	 *                  Client.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         Client.<br>
	 *                         <br>
	 */
	public void init(final ConnectorFacade connector) throws ClientException {

		// Set the Connector.
		this.connector = connector;

		// User specific initialization.
		initialize();

	}

	/**
	 * Gets the Service to which this Client belongs.
	 * 
	 * @return Service.<br>
	 *         <br>
	 */
	public ProxyServiceFacade getService() {
		return connector.getService();
	}

	/**
	 * Gets the name of this Client.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	public String getName() {
		return connector.getClientName();
	}

	/**
	 * Opens a connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to connect the
	 *                         Client.<br>
	 *                         <br>
	 */
	public void connect() throws ClientException {
		if (!isConnected()) {

			// Connect Client.
			try {
				connection = connector.getClientConnection();
			} catch (final ConnectorException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot open a connection with Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because its Connector generated the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Log a message.
			getScopeFacade().log(
					"WAREWORK connected Client '" + getName() + "' in Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot open a connection with Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because it's already open.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to disconnect the
	 *                         Client.<br>
	 *                         <br>
	 */
	public void disconnect() throws ClientException {
		if (connection != null) {

			// Close the connection with the Client.
			if (!isClosed()) {
				close();
			}

			// Set the connection to null.
			connection = null;

			// Log a message.
			getScopeFacade().log(
					"WAREWORK disconnected Client '" + getName() + "' in Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		}
	}

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and <code>false</code> if
	 *         the connection is closed.<br>
	 *         <br>
	 */
	public boolean isConnected() {
		if (connection != null) {

			// Validate the connection.
			final boolean isClosed = isClosed();

			// Remove the connection object if it's closed.
			if (isClosed) {
				connection = null;
			}

			// Return the validation.
			return !isClosed;

		} else {
			return false;
		}
	}

	/**
	 * Gets the connection of the Client.
	 * 
	 * @return Client's connection.<br>
	 *         <br>
	 */
	public Object getConnection() {
		return connection;
	}

	/**
	 * Gets the Connector of the Client.
	 * 
	 * @return Client's Connector.<br>
	 *         <br>
	 */
	public ConnectorFacade getConnector() {
		return connector;
	}

	/**
	 * Gets the attributes that define specific features of the Client.
	 * 
	 * @return Client's attributes manager.<br>
	 *         <br>
	 */
	public ClientAttributes getClientAttributes() {

		// Initialize client attributes.
		if (clientAttributes == null) {
			clientAttributes = new ClientAttributes(getClientInfo());
		}

		// Return client attributes.
		return clientAttributes;

	}

	/**
	 * Destroys the Client.
	 * 
	 * @throws ClientException If there is an error when trying to remove the
	 *                         Client.<br>
	 *                         <br>
	 */
	public void destroy() throws ClientException {
		// DO NOTHING.
	}

	/**
	 * Gets the name of the Client.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	public String toString() {
		return getName();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Client.
	 * 
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         Client.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {
		// DO NOTHING.
	}

	/**
	 * Gets the attributes that define specific features of the Client. This method
	 * should be overriden to return the attributes.
	 * 
	 * @return Attributes of the Client.<br>
	 *         <br>
	 */
	protected Map<String, Object> getClientInfo() {
		return null;
	}

	/**
	 * Gets the scope where Client exists.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected ScopeFacade getScopeFacade() {
		return getService().getScopeFacade();
	}

}
