package com.warework.service.log;

import java.util.Map;

import com.warework.core.service.AbstractProxyService;
import com.warework.core.service.ServiceException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.client.LoggerFacade;

/**
 * Log service implementation. <br>
 * <br>
 * The Log Service is the central unit where to perform log operations in the
 * Warework Framework. It allows developers to send messages at runtime with a
 * level of granularity and in a chosen output. As this Service is a Proxy
 * Service, you can handle different implementations of Clients (called Loggers)
 * within the same place and interact with them whenever you need. This
 * functio-nality helps developers, for example, to manage and use the Apache
 * Log4j software library and a custom Logger at the same time and with a common
 * interface.<br>
 * <br>
 * <strong>Create and retrieve a Log Service</strong><br>
 * <br>
 * To create the Log Service in a Scope, you always need to provide a unique
 * name for the Service and the <code>LogServiceImpl</code> class that exists in
 * the <code>com.warework.service.log</code> package.<br>
 * <br>
 * <code>
 * // Create the Log Service and register it in a Scope.<br> 
 * scope.createService("log-service", LogServiceImpl.class, null);<br>
 * </code> <br>
 * Once it is created, you can get it using the same name (when you retrieve an
 * instance of a Log Service, you will get the <code>LogServiceFacade</code>
 * interface):<br>
 * <br>
 * <code>
 * // Get an instance of the Log Service.<br> 
 * LogServiceFacade logService = (LogServiceFacade) scope.getService("log-service");<br>
 * </code> <br>
 * <strong>Add and connect to Loggers</strong><br>
 * <br>
 * Now the Log Service is running but you need at least one Logger to perform
 * log operations. To add a Logger into the Log Service you have to invoke
 * method <code>createClient()</code> that exists in its Facade. This method
 * requests a name for the new Logger and a Connector which performs the
 * creation of the Logger. Let's see how to register a sample Logger in this
 * Service:<br>
 * <br>
 * <code>
 * // Add a Logger in the Log Service.<br> 
 * logService.createClient("sample-logger", SampleConnector.class, null);<br>
 * </code> <br>
 * The <code>SampleConnector</code> class creates the Sample Logger and stores
 * it in the Log Service. After that, we have to tell the Log Service that we
 * want to perform operations with the Sample Logger. We do so by connecting the
 * Logger:<br>
 * <br>
 * <code>
 * // Connect the Sample Logger.<br> 
 * logService.connect("sample-logger");<br>
 * </code> <br>
 * <strong>Perform Log operations</strong><br>
 * <br>
 * Each Logger performs log operations with a level of granularity. Warework
 * defines a common set of levels in the <code>LogServiceConstants</code> class
 * that exists in the <code>com.warework.service.log</code> package and each one
 * designates a log message in a specific context.<br>
 * <br>
 * These levels are generic and mostly used by all Loggers. Even when Loggers
 * define more levels, Warework recommends you using only those specified at
 * <code>LogServiceConstants</code> to keep compatibility across them. It is
 * helpful if you plan to change the Logger in the future, use many of them at
 * the same time or migrate your application into a different environment.<br>
 * <br>
 * To log a message with the Log Service is quite simple: just invoke the log
 * method of the Log Service Facade with the name of the Logger to use, the
 * message to log and the level of the log.<br>
 * <br>
 * <code>
 * // Log a message using a standard log level.<br> 
 * logService.log("sample-logger", "A message from the Log Service", LogServiceConstants.LOG_LEVEL_INFO);<br>
 * </code> <br>
 * You can also use each Logger levels directly with the specific constants that
 * it defines in the Client class.<br>
 * <br>
 * <code>
 * // Log a message with a specific sample Logger level. <br>
 * logService.log("sample-logger", "Another message from the Log Service", SampleLogger.LOG_LEVEL_Trace);<br>
 * </code> <br>
 * As log operations are very common, Warework includes a faster way to perform
 * them with the log method that exists in the Facade of each Scope. This method
 * permits to invoke a log operation in a default Logger without the need to
 * retrieve the Log Service.<br>
 * <br>
 * <code>
 * // Log a message from a scope.<br> 
 * scope.log("This is the fastest way!", LogServiceConstants.LOG_LEVEL_INFO);<br>
 * </code> <br>
 * The previous line of code looks in the Scope for a Service named
 * <code>log-service</code> that implements the
 * <code>com.warework.service.log.LogServiceFacade</code> interface and then
 * invokes the log operation on it with a Logger named
 * <code>default-client</code>. So, in order to make this method work, we have
 * to create first the Log Service and a Logger with specific names.<br>
 * <br>
 * <code>
 * // Create the Log Service.<br> 
 * LogServiceFacade logService = (LogServiceFacade) scope.createService("log-service", LogServiceImpl.class, null);<br> 
 * <br>
 * // Create a Logger.<br> 
 * logService.createClient("default-client", SampleConnector.class, null);<br>
 * </code> <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class LogServiceImpl extends AbstractProxyService implements LogServiceFacade {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs an info message in a Logger. It is equals to
	 * <code>log(loggerName, message, LogServiceConstants.LOG_LEVEL_INFO);</code> .
	 * 
	 * @param loggerName Name of the Logger where to perform the log.<br>
	 *                   <br>
	 * @param message    Message to log.<br>
	 *                   <br>
	 */
	public void info(final String loggerName, final String message) {
		log(loggerName, message, LogServiceConstants.LOG_LEVEL_INFO);
	}

	/**
	 * Logs a debug message in a Logger. It is equals to
	 * <code>log(loggerName, message, LogServiceConstants.LOG_LEVEL_DEBUG);</code> .
	 * 
	 * @param loggerName Name of the Logger where to perform the log.<br>
	 *                   <br>
	 * @param message    Message to log.<br>
	 *                   <br>
	 */
	public void debug(final String loggerName, final String message) {
		log(loggerName, message, LogServiceConstants.LOG_LEVEL_DEBUG);
	}

	/**
	 * Logs a warning message in a Logger. It is equals to
	 * <code>log(loggerName, message, LogServiceConstants.LOG_LEVEL_WARN);</code> .
	 * 
	 * @param loggerName Name of the Logger where to perform the log.<br>
	 *                   <br>
	 * @param message    Message to log.<br>
	 *                   <br>
	 */
	public void warn(final String loggerName, final String message) {
		log(loggerName, message, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Logs a fatal message in a Logger. It is equals to
	 * <code>log(loggerName, message, LogServiceConstants.LOG_LEVEL_FATAL);</code> .
	 * 
	 * @param loggerName Name of the Logger where to perform the log.<br>
	 *                   <br>
	 * @param message    Message to log.<br>
	 *                   <br>
	 */
	public void fatal(final String loggerName, final String message) {
		log(loggerName, message, LogServiceConstants.LOG_LEVEL_FATAL);
	}

	/**
	 * Logs a message in a Logger.
	 * 
	 * @param loggerName Name of the Logger where to perform the log.<br>
	 *                   <br>
	 * @param message    Message to log.<br>
	 *                   <br>
	 * @param logLevel   Indicates how to perform the log: 32=INFO, 16=DEBUG,
	 *                   48=WARNING, 64=FATAL, etc. Check out these levels at
	 *                   <code>com.warework.service.log.LogServiceConstants</code>
	 *                   and keep in mind that specific Logger implementations may
	 *                   use custom log levels.<br>
	 *                   <br>
	 */
	public void log(final String loggerName, final String message, final int logLevel) {

		// Get the logger.
		final LoggerFacade logger = (LoggerFacade) getClient(loggerName);

		// Log message.
		if (logger != null) {
			logger.log(message, logLevel);
		}

	}

	/**
	 * Logs a message from a Provider.
	 * 
	 * @param loggerName   Name of the Logger where to perform the log.<br>
	 *                     <br>
	 * @param providerName Provider where to retrieve the message.<br>
	 *                     <br>
	 * @param messageName  Name of the message in the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the
	 *                     message loaded from the Provider and the values those
	 *                     that will replace the variables. Every variable must be
	 *                     inside '${' and '}' so the variable CAR must be in this
	 *                     message as '${CAR}'. Pass <code>null</code> to this
	 *                     parameter to make no changes in the message loaded.<br>
	 *                     <br>
	 * @param logLevel     Indicates how to perform the log: 32=INFO, 16=DEBUG,
	 *                     48=WARNING, 64=FATAL, etc. Check out these levels at
	 *                     <code>com.warework.service.log.LogServiceConstants</code>
	 *                     and keep in mind that specific Logger implementations may
	 *                     use custom log levels.<br>
	 *                     <br>
	 */
	public void log(final String loggerName, final String providerName, final String messageName,
			final Map<String, String> values, final int logLevel) {

		// Get the message.
		final Object message = getScopeFacade().getObject(providerName, messageName);

		// Log message.
		if (message instanceof String) {
			log(loggerName, StringL1Helper.replace((String) message, values), logLevel);
		}

	}

	/**
	 * Executes a Log Service operation.
	 * 
	 * @param operationName Name of the operation to execute.<br>
	 *                      <br>
	 * @param parameters    Operation parameters.<br>
	 *                      <br>
	 * @return Operation result.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	public Object execute(final String operationName, final Map<String, Object> parameters) throws ServiceException {

		// Validate the operation to perform.
		if (operationName.equals(LogServiceConstants.OPERATION_NAME_LOG)) {
			executeLog(parameters);
		} else {
			return super.execute(operationName, parameters);
		}

		// Nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes the log operation.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	private void executeLog(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object client = parameters.get(LogServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((client == null) || !(client instanceof String)) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot execute '" + LogServiceConstants.OPERATION_NAME_LOG
					+ "' operation because given parameter '" + LogServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
					+ "' is not a string or it does not exists.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the log level.
		Object level = parameters.get(LogServiceConstants.OPERATION_PARAMETER_LOG_LEVEL);
		if ((level == null) || (!(level instanceof Integer) && !(level instanceof String))) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot execute '" + LogServiceConstants.OPERATION_NAME_LOG
					+ "' operation because given parameter '" + LogServiceConstants.OPERATION_PARAMETER_LOG_LEVEL
					+ "' is not defined or it is not an integer object.", null, LogServiceConstants.LOG_LEVEL_WARN);
		} else if (level instanceof String) {

			// Get the string value of the log level.
			final String logLevel = (String) level;

			// Map common level names with log level values.
			if (logLevel.equalsIgnoreCase(LogServiceConstants.LOG_NAME_DEBUG)) {
				level = Integer.valueOf(LogServiceConstants.LOG_LEVEL_DEBUG);
			} else if (logLevel.equalsIgnoreCase(LogServiceConstants.LOG_NAME_INFO)) {
				level = Integer.valueOf(LogServiceConstants.LOG_LEVEL_INFO);
			} else if (logLevel.equalsIgnoreCase(LogServiceConstants.LOG_NAME_WARN)) {
				level = Integer.valueOf(LogServiceConstants.LOG_LEVEL_WARN);
			} else if (logLevel.equalsIgnoreCase(LogServiceConstants.LOG_NAME_FATAL)) {
				level = Integer.valueOf(LogServiceConstants.LOG_LEVEL_FATAL);
			} else {
				try {
					level = Integer.valueOf((String) level);
				} catch (NumberFormatException e) {
					throw new ServiceException(getScopeFacade(), "WAREWORK cannot execute '"
							+ LogServiceConstants.OPERATION_NAME_LOG + "' operation because given parameter '"
							+ LogServiceConstants.OPERATION_PARAMETER_LOG_LEVEL
							+ "' is a string value without a valid level name (" + LogServiceConstants.LOG_NAME_DEBUG
							+ ", " + LogServiceConstants.LOG_NAME_INFO + ", " + LogServiceConstants.LOG_NAME_WARN
							+ " or " + LogServiceConstants.LOG_NAME_FATAL
							+ ") or an ID that represents with an integer value a specific log level.", e,
							LogServiceConstants.LOG_LEVEL_WARN);
				}
			}

		}

		// Get the log message.
		final Object logMessage = parameters.get(LogServiceConstants.OPERATION_PARAMETER_LOG_MESSAGE);
		if ((logMessage == null) || !(logMessage instanceof String)) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot execute '" + LogServiceConstants.OPERATION_NAME_LOG
					+ "' operation because given parameter '" + LogServiceConstants.OPERATION_PARAMETER_LOG_MESSAGE
					+ "' is not defined or it is not a string.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Perform log.
		log((String) client, (String) logMessage, ((Integer) level).intValue());

	}

}
