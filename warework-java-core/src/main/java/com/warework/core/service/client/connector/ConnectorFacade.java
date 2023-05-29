package com.warework.core.service.client.connector;

import java.util.Enumeration;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.client.ClientFacade;

/**
 * This facade holds the information required to create connections for Clients.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface ConnectorFacade {

	/**
	 * Gets the Service that created this Connector. The Client uses this method
	 * to interact with the Service.
	 * 
	 * @return Service.<br>
	 * <br>
	 */
	ProxyServiceFacade getService();

	/**
	 * Gets the Client represented by this Connector.
	 * 
	 * @return Client.<br>
	 * <br>
	 */
	ClientFacade getClient();

	/**
	 * Gets the name of the Client to which this Connector creates connections.
	 * 
	 * @return Name of the Client.<br>
	 * <br>
	 */
	String getClientName();

	/**
	 * Gets the type of the Client. Must be an implementation of the
	 * <code>com.warework.core.service.client.ClientFacade</code> interface.
	 * 
	 * @return Type of the Client.<br>
	 * <br>
	 */
	Class<? extends ClientFacade> getClientType();

	/**
	 * Gets a connection for the Client. This connection is unique for a
	 * specific Client. WARNING!: Never return null with this method in order to
	 * make the Client work.
	 * 
	 * @return Client's connection.<br>
	 * <br>
	 * @throws ConnectorException
	 *             If there is an error when trying to get the Client
	 *             connection.<br>
	 * <br>
	 */
	Object getClientConnection() throws ConnectorException;

	/**
	 * Gets the object that contains the information required to create
	 * connections.
	 * 
	 * @return A connection source.<br>
	 * <br>
	 */
	Object getConnectionSource();

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if no one
	 *         exist.<br>
	 * <br>
	 */
	Enumeration<String> getInitParameterNames();

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name
	 *            Name of the initialization parameter.<br>
	 * <br>
	 * @return Value of the initialization parameter.<br>
	 * <br>
	 */
	Object getInitParameter(String name);

	/**
	 * Gets the boolean value of an initialization parameter.
	 * 
	 * @param name
	 *            Name of the initialization parameter.<br>
	 * <br>
	 * @return <code>true</code> if the parameter is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that
	 *         is equals (in any case form) to <code>true</code>. Otherwise,
	 *         this method returns <code>false</code> (for example: if it does
	 *         not exist).<br>
	 * <br>
	 */
	boolean isInitParameter(String name);

	/**
	 * Gets a boolean value of an initialization parameter.
	 * 
	 * @param parameterName
	 *            Name of the initialization parameter.<br>
	 * <br>
	 * @return The boolean value of the initialization parameter or null if
	 *         parameter is not a boolean object or a string that represents a
	 *         boolean value.<br>
	 * <br>
	 */
	Boolean toBoolean(String parameterName);

	/**
	 * Gets the an integer value of an initialization parameter.
	 * 
	 * @param parameterName
	 *            Name of the initialization parameter.<br>
	 * <br>
	 * @return The integer value of the initialization parameter or null if
	 *         parameter is not an integer object or a string that represents an
	 *         integer value.<br>
	 * <br>
	 */
	Integer toInteger(String parameterName);

	/**
	 * Gets a string value of an initialization parameter.
	 * 
	 * @param parameterName
	 *            Name of the initialization parameter.<br>
	 * <br>
	 * @return The string value of the initialization parameter or null if
	 *         parameter is not a string object.<br>
	 * <br>
	 */
	String toString(String parameterName);

}
