package com.warework.service.log.client;

import com.warework.core.service.client.AbstractClient;
import com.warework.service.log.LogServiceConstants;

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
 * @version ${project.version}
 */
public abstract class AbstractServletLogger extends AbstractClient implements LoggerFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "servlet-logger";

	// LOG LEVELS

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
	 * Constant that defines the FATAL log level. This level designates serious
	 * failures that will presumably lead the application to abort. This log level
	 * matches the FATAL level specified at
	 * <code>com.warework.service.log.LogServiceConstants.LOG_LEVEL_FATAL</code>
	 * (both have the same value).
	 */
	public static final int LOG_LEVEL_FATAL = LogServiceConstants.LOG_LEVEL_FATAL;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message.
	 * 
	 * @param level   Indicates how to perform the log.<br>
	 *                <br>
	 * @param message Message to log.<br>
	 *                <br>
	 */
	protected abstract void log(final String level, final String message);

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
	public final void log(final String message, final int logLevel) {
		if (logLevel == LOG_LEVEL_DEBUG) {
			log(LogServiceConstants.LOG_NAME_DEBUG, message);
		} else if (logLevel == LOG_LEVEL_INFO) {
			log(LogServiceConstants.LOG_NAME_INFO, message);
		} else if (logLevel == LOG_LEVEL_WARN) {
			log(LogServiceConstants.LOG_NAME_WARN, message);
		} else if (logLevel == LOG_LEVEL_FATAL) {
			log(LogServiceConstants.LOG_NAME_FATAL, message);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does nothing.
	 */
	protected final void close() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code> (connection is always open).<br>
	 *         <br>
	 */
	protected final boolean isClosed() {
		return false;
	}

	/**
	 * Creates the message to log.
	 * 
	 * @param level   Indicates how to perform the log.<br>
	 *                <br>
	 * @param message Message to log.<br>
	 *                <br>
	 * @return Log message.
	 */
	protected final String createMessage(final String level, final String message) {
		return "[" + level + " @ " + getScopeFacade().getName() + "] - " + message;
	}

}
