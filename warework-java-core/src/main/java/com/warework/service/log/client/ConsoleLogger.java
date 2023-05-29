package com.warework.service.log.client;

import java.io.PrintStream;
import java.util.Calendar;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Logger that uses the console to log the messages. <br>
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
public final class ConsoleLogger extends AbstractClient implements LoggerFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "console-logger";

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

	// CONSOLE OUTPUT LOG LEVEL

	private static final String OUTPUT_DEBUG = "DEBUG";

	private static final String OUTPUT_INFO = "INFO ";

	private static final String OUTPUT_WARN = "WARN ";

	private static final String OUTPUT_FATAL = "FATAL";

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
		if (logLevel == LOG_LEVEL_DEBUG) {
			log(OUTPUT_DEBUG, message);
		} else if (logLevel == LOG_LEVEL_INFO) {
			log(OUTPUT_INFO, message);
		} else if (logLevel == LOG_LEVEL_WARN) {
			log(OUTPUT_WARN, message);
		} else if (logLevel == LOG_LEVEL_FATAL) {
			log(OUTPUT_FATAL, message);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does nothing.
	 */
	protected void close() {
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

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message.
	 * 
	 * @param level   Indicates how to perform the log.<br>
	 *                <br>
	 * @param message Message to log.<br>
	 *                <br>
	 */
	private void log(final String level, final String message) {
		
		// Create new calendar.
		final Calendar calendar = Calendar.getInstance();

		// Get where to log.
		final PrintStream out = (PrintStream) getConnection();

		// Get current month.
		final int month = calendar.get(Calendar.MONTH);

		// Get current day.
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		// Get hour.
		final int hour = calendar.get(Calendar.HOUR_OF_DAY);

		// Get minutes.
		final int min = calendar.get(Calendar.MINUTE);

		// Get seconds.
		final int sec = calendar.get(Calendar.SECOND);

		// Get milliseconds.
		final int millis = calendar.get(Calendar.MILLISECOND);

		// Create date.
		final String date = Integer.toString(calendar.get(Calendar.YEAR)) + StringL1Helper.CHARACTER_FORWARD_SLASH
				+ ((month < 10) ? "0" + month : month) + StringL1Helper.CHARACTER_FORWARD_SLASH
				+ ((day < 10) ? "0" + day : day) + StringL1Helper.CHARACTER_SPACE + ((hour < 10) ? "0" + hour : hour)
				+ StringL1Helper.CHARACTER_COLON + ((min < 10) ? "0" + min : min) + StringL1Helper.CHARACTER_COLON
				+ ((sec < 10) ? "0" + sec : sec) + StringL1Helper.CHARACTER_PERIOD
				+ ((millis < 10) ? "00" + millis : (millis < 100) ? "0" + millis : millis);

		// Log the message.
		out.println(date + " - [" + level + " @ " + getScopeFacade().getName() + "] - " + message);

	}

}
