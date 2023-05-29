package com.warework.core.service;

/**
 * Constants for Proxy Services.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class ProxyServiceConstants extends ServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default Client of a Service.
	 */
	public static final String DEFAULT_CLIENT_NAME = "default-client";

	// OPERATION NAMES

	/**
	 * Creates a Client and binds it to a Proxy Service. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client will be
	 * bound in the Service. If a Client of the same name is already bound to
	 * this Service, an exception is thrown. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>connector-type</code></strong>: Implementation of the Connector
	 * that specifies which Client to use and how to create the connections for
	 * the Client. This parameter must be a <code>java.lang.Class</code> or a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>connector-parameters</code></strong>: Parameters (as string
	 * values) that specifies how the Connector will create the connections for
	 * the Client. This parameter is optional and it can be a
	 * <code>java.util.Map</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_CREATE_CLIENT = "create-client";

	/**
	 * Gets the names of all the clients bound to this Service. <br>
	 * <br>
	 * This operation returns a <code>java.util.Vector</code> with the names of
	 * the clients or <code>null</code> if no one exist.<br>
	 */
	public static final String OPERATION_NAME_GET_CLIENT_NAMES = "get-client-names";

	/**
	 * Validates if a Client is bound to this Service. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Boolean</code> with
	 * <code>true</code> if the Client exists and <code>false</code> if the
	 * Client does not exists.<br>
	 */
	public static final String OPERATION_NAME_EXISTS_CLIENT = "exists-client";

	/**
	 * Removes a Client bound to the Proxy Service. If this Service does not
	 * have a Client bound with the specified name, this method does nothing. If
	 * Client is connected this method disconnects it first. Use the following
	 * parameters in order to invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_REMOVE_CLIENT = "remove-client";

	/**
	 * Removes all the clients bound to the Proxy Service. This operation does
	 * not returns anything.
	 */
	public static final String OPERATION_NAME_REMOVE_ALL_CLIENTS = "remove-all-clients";

	/**
	 * Gets the class that implements the Client. Use the following parameters
	 * in order to invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Class</code> that represents the
	 * type of Client or <code>null</code> if Client does not exists.<br>
	 */
	public static final String OPERATION_NAME_GET_CLIENT_TYPE = "get-client-type";

	/**
	 * Validates if a Client is the same type of a given class. Use the
	 * following parameters in order to invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>client-type</code></strong>: Class to compare with the class of
	 * the Client.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Boolean</code> with
	 * <code>true</code> if the Client's type is the same as given class and
	 * <code>false</code> if not.<br>
	 */
	public static final String OPERATION_NAME_IS_CLIENT_TYPE = "is-client-type";

	/**
	 * Gets the connection of a Client. First you'll need to connect the Client
	 * to retrieve the connection, otherwise this method will return
	 * <code>null</code>. Use the following parameters in order to invoke this
	 * operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Object</code> with the Client's
	 * connection or <code>null</code> if it's not connected.<br>
	 */
	public static final String OPERATION_NAME_GET_CLIENT_CONNECTION = "get-client-connection";

	/**
	 * Opens a connection with a Client. If Client's connection is already open
	 * this method throws an exception. Use the following parameters in order to
	 * invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_CONNECT_CLIENT = "connect-client";

	/**
	 * Closes a connection with a Client. Use the following parameters in order
	 * to invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_DISCONNECT_CLIENT = "disconnect-client";

	/**
	 * Closes the connection of every Client.<br>
	 * <br>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_DISCONNECT_ALL_CLIENTS = "disconnect-all-clients";

	/**
	 * Validates if a Client is connected. Use the following parameters in order
	 * to invoke this operation: <br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name to which the Client is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </ul>
	 * This operation returns a <code>java.lang.Boolean</code> with
	 * <code>true</code> if the Client is connected and <code>false</code> if
	 * the Client is not connected.
	 */
	public static final String OPERATION_NAME_IS_CLIENT_CONNECTED = "is-client-connected";

	// OPERATION PARAMETERS

	/**
	 * Client of the Proxy Service where to execute the operation. This
	 * parameter must be a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_CLIENT_NAME = "client-name";

	/**
	 * Implementation of the Connector that specifies which Client to use and
	 * how to create the connections for the Client. This parameter must be a
	 * <code>java.lang.Class</code> or a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_CONNECTOR_TYPE = "connector-type";

	/**
	 * Parameters (as string values) that specifies how the Connector will
	 * create the connections for the Client. This parameter must be a
	 * <code>java.util.Map</code>.
	 */
	public static final String OPERATION_PARAMETER_CONNECTOR_PARAMETERS = "connector-parameters";

	/**
	 * Class that represents the type of the Client. This parameter must be a
	 * <code>java.lang.Class</code> or a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_CLIENT_TYPE = "client-type";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ProxyServiceConstants() {

	}

}
