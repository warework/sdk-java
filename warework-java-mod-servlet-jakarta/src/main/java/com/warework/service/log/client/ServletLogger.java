package com.warework.service.log.client;

import jakarta.servlet.ServletContext;

/**
 * Logger that uses the JEE server to log the messages. <br>
 * <br>
 * <u><strong>Accepted log levels are (check out and use these levels with the
 * public constants defined in this class)</strong></u>: <br>
 * <ul>
 * <li><strong>DEBUG</strong> (lowest value) for detailed informational events
 * used to debug applications.</li>
 * <li><strong>INFO</strong> informational messages that highlight the progress
 * of the application.</li>
 * <li><strong>WARNING</strong> for potential problems.</li>
 * <li><strong>FATAL</strong> (highest value) for serious failures that will
 * presumably lead the application to abort.</li>
 * </ul>
 * In order to keep compatibility issues with other loggers is highly
 * recommended that developers use the default constants defined at
 * <code>com.warework.service.log.LogServiceConstants</code> to perform loggin
 * operations with this class. Check out the description of each log level of
 * this class to see if they match with one of the default constants. It will
 * help to migrate to another logger in the future if it's needed.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ServletLogger extends AbstractServletLogger {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message.
	 * 
	 * @param level   Indicates how to perform the log.<br>
	 *                <br>
	 * @param message Message to log.<br>
	 *                <br>
	 */
	protected void log(final String level, final String message) {

		// Get where to log.
		final ServletContext out = (ServletContext) getConnection();

		// Log the message.
		out.log(createMessage(level, message));

	}

}
