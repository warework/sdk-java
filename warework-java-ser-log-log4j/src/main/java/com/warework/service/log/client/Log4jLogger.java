package com.warework.service.log.client;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.warework.core.service.client.AbstractClient;
import com.warework.service.log.LogServiceConstants;

/**
 * Logger that uses the Log4j to log the messages.<br>
 * <br>
 * <b>Accepted log levels are (check out and use these levels with the public
 * constants defined in this class):</b> <br>
 * <ul>
 * <li><b>TRACE</b> (lowest value) designates finer-grained informational events
 * than the DEBUG level.</li>
 * <li><b>DEBUG</b> for detailed informational events used to debug
 * applications.</li>
 * <li><b>INFO</b> informational messages that highlight the progress of the
 * application.</li>
 * <li><b>WARN</b> for potential problems.</li>
 * <li><b>ERROR</b> designates error events that might still allow the
 * application to continue running.</li>
 * <li><b>FATAL</b> (highest value) for serious failures that will presumably
 * lead the application to abort.</li>
 * </ul>
 * In order to keep compatibility issues with other Loggers, it is highly
 * recommended that developers use the default constants defined at
 * <code>com.warework.service.log.LogServiceConstants</code> to perform loggin
 * operations with this class. Check out the description of each log level of
 * this class to see if they match with one of the default constants. It will
 * help to migrate to another logger in the future if it's needed. <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Log4jLogger extends AbstractClient implements LoggerFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Prefix for every variable to process in the Log4j Conversion Pattern.
	private static final String VARIABLE_PREFIX = "variable-";

	// Variable for the name of the Scope..
	private static final String VARIABLE_SCOPE_NAME = "scope-name";

	/**
	 * Constant that defines the default name for the Client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "log4j-logger";

	// LOG LEVELS

	/**
	 * Constant that defines the TRACE log level. This level designates
	 * finer-grained informational events than the DEBUG level.
	 */
	public static final int LOG_LEVEL_TRACE = 0x00;

	/**
	 * Constant that defines the DEBUG log level. This level designates detailed
	 * informational events used to debug applications. This log level matches the
	 * DEBUG level specified at
	 * <code>com.warework.service.log.LogServiceConstants.LOG_LEVEL_DEBUG</code>
	 * (both have the same value).
	 */
	public static final int LOG_LEVEL_DEBUG = LogServiceConstants.LOG_LEVEL_DEBUG;

	/**
	 * Constant that defines the INFO log level. This level designates informational
	 * messages that highlight the progress of the application. This log level
	 * matches the INFO level specified at
	 * <code>com.warework.service.log.LogServiceConstants.LOG_LEVEL_INFO</code>
	 * (both have the same value).
	 */
	public static final int LOG_LEVEL_INFO = LogServiceConstants.LOG_LEVEL_INFO;

	/**
	 * Constant that defines the WARNING log level. This level designates a
	 * potential problem. This log level matches the WARNING level specified at
	 * <code>com.warework.service.log.LogServiceConstants.LOG_LEVEL_WARN</code>
	 * (both have the same value).
	 */
	public static final int LOG_LEVEL_WARN = LogServiceConstants.LOG_LEVEL_WARN;

	/**
	 * Constant that defines the ERROR log level. This level designates error events
	 * that might still allow the application to continue running.
	 */
	public static final int LOG_LEVEL_ERROR = 0x38;

	/**
	 * Constant that defines the FATAL log level. This level designates serious
	 * failures that will presumably lead the application to abort. This log level
	 * matches the FATAL level specified at
	 * <code>com.warework.service.log.LogServiceConstants.LOG_LEVEL_FATAL</code>
	 * (both have the same value).
	 */
	public static final int LOG_LEVEL_FATAL = LogServiceConstants.LOG_LEVEL_FATAL;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Variables to process in the Log4j Conversion Pattern.
	private Set<String> variables;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message.
	 * 
	 * @param message  Message to log.<br>
	 *                 <br>
	 * @param logLevel Indicates how to perform the log. Use the public constants of
	 *                 this class to specify these levels.<br>
	 *                 <br>
	 */
	public void log(final String message, final int logLevel) {
		if (isConnected()) {

			// Get where to log.
			final Logger logger = (Logger) getConnection();

			// Setup Conversion Pattern variables.
			if (variables != null) {
				for (final Iterator<String> iterator = variables.iterator(); iterator.hasNext();) {

					// Get the name of a variable.
					final String name = iterator.next();

					// Set a variable.
					MDC.put(name, getConnector().getInitParameter(name));

				}
			}

			// Set the name of the scope.
			MDC.put(VARIABLE_PREFIX + VARIABLE_SCOPE_NAME, getScopeFacade().getName());

			// Log the message.
			if (logLevel == LOG_LEVEL_TRACE) {
				logger.trace(message);
			} else if (logLevel == LOG_LEVEL_DEBUG) {
				logger.debug(message);
			} else if (logLevel == LOG_LEVEL_INFO) {
				logger.info(message);
			} else if (logLevel == LOG_LEVEL_WARN) {
				logger.warn(message);
			} else if (logLevel == LOG_LEVEL_ERROR) {
				logger.error(message);
			} else if (logLevel == LOG_LEVEL_FATAL) {
				logger.fatal(message);
			}

			// Cleanup Conversion Pattern variables.
			if (variables != null) {
				for (final Iterator<String> iterator = variables.iterator(); iterator.hasNext();) {

					// Get the name of a variable.
					final String name = iterator.next();

					// Remove a variable.
					MDC.remove(name);

				}

			}

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Does nothing.
	 */
	protected void close() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code> (connection is always open).<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Initializes the Client.
	 */
	protected void initialize() {

		// Get each connector parameter name.
		final Enumeration<String> paramNames = getConnector().getInitParameterNames();

		// Retrieve variables only if init. parameters exist.
		if (paramNames != null) {
			while (paramNames.hasMoreElements()) {

				// Get the name of a init. parameter.
				final String name = paramNames.nextElement();

				// Validate it is a variable.
				if (name.startsWith(VARIABLE_PREFIX)) {

					// Create the variables collection when required.
					if (variables == null) {
						variables = new HashSet<String>();
					}

					// Register the variable.
					variables.add(name);

				}

			}

		}

	}

}
