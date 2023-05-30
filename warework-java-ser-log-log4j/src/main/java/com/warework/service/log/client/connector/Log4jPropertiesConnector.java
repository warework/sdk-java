package com.warework.service.log.client.connector;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector holds the information required to create Log4j Loggers using a
 * properties configuration file.<br>
 * <br>
 * To add a Log4j Logger into the Log Service you have to invoke method
 * <code>createClient()</code> that exists in its Facade with a name, a Log4j
 * Connector class and a configuration for the Logger. If you plan to configure
 * Log4j with a properties file, then you should use this Connector as
 * follows:<br>
 * <br>
 * <code>
 * // Create the configuration for the Logger.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the location of the Log4j configuration file.<br>
 * config.put(Log4jPropertiesConnector.PARAMETER_CONFIG_TARGET, "/META-INF/system/log4j.properties");<br>
 * <br>
 * // Create the Log4j Logger.<br>
 * logService.createClient("log4j-logger", Log4jPropertiesConnector.class, config);<br> 
 * </code> <br>
 * The <code>Log4jPropertiesConnector</code> collects the configuration and sets
 * up a Log4j Logger with the properties file that exists where
 * <code>PARAMETER_CONFIG_TARGET</code> specifies. Please review in the Log4j
 * documentation how to configure Log4j with properties files.<br>
 * <br>
 * It is very important to know that the name you give to the Warework Logger
 * must be the same name as the Log4j Logger. In a Log4j properties
 * configuration file it could be like this:<br>
 * <br>
 * <code>
 * log4j.logger.<b>log4j-logger</b>=DEBUG, consoleApp<br> 
 * log4j.appender.consoleApp=org.apache.log4j.ConsoleAppender<br>
 * log4j.appender.consoleApp.layout=org.apache.log4j.PatternLayout<br>
 * log4j.appender.consoleApp.layout.ConversionPattern=[%d]-[%-5p] - %m%n<br>
 * </code> <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * <li><b>Libraries:</b>
 * <ul>
 * <li>Apache Log4j version 1.2.12</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class Log4jPropertiesConnector extends AbstractLog4jConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configures Log4j from an external properties file.
	 * 
	 * @param config Location of the properties files.<br>
	 *               <br>
	 * @throws ConnectorException If there is an error when trying to configure the
	 *                            Logger.<br>
	 *                            <br>
	 */
	protected void configure(final InputStream config) throws ConnectorException {

		// Create a configuration for the logger.
		final Properties properties = new Properties();

		// Load the properties configuration file.
		try {
			properties.load(config);
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot read the Log4j properties configuration file because the following exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Configure the logger.
		PropertyConfigurator.configure(properties);

	}

}
