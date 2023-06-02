package com.warework.service.log.client.connector;

import com.warework.service.log.client.ConsoleLogger;

/**
 * This Connector holds the information required to create connections for
 * Console Loggers.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ConsoleConnector extends AbstractLoggerConnector {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Client.
	 * 
	 * @return A <code>com.warework.service.log.client.ConsoleLogger</code>
	 *         client.<br>
	 * <br>
	 */
	public Class<ConsoleLogger> getClientType() {
		return ConsoleLogger.class;
	}

	/**
	 * Gets a <code>java.io.PrintStream</code> connection for the Client. This
	 * connection is unique for a specific Client.
	 * 
	 * @return Client's connection.<br>
	 * <br>
	 */
	public Object getClientConnection() {
		return System.out;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * As this Connector always returns a specific connection the result of this
	 * method is always <code>null</code>.
	 * 
	 * @return Always <code>null</code>.<br>
	 * <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
