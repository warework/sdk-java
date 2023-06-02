package com.warework.service.log.client.connector;

import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * This Connector holds the information required to create connections for Log4j
 * loggers using a XML configuration file.<br>
 * <br>
 * To add a Log4j Logger into the Log Service you have to invoke method
 * <code>createClient()</code> that exists in its Facade with a name, a Log4j
 * Connector class and a configuration for the Logger. If you plan to configure
 * Log4j with an XML file, then you should use this Connector as follows:<br>
 * <br>
 * <code>
 * // Create the configuration for the Logger.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the location of the Log4j configuration file.<br>
 * config.put(Log4jXMLConnector.PARAMETER_CONFIG_TARGET, "/META-INF/system/log4j.xml");<br>
 * <br>
 * // Create the Log4j Logger.<br>
 * logService.createClient("log4j-logger", Log4jXMLConnector.class, config);<br> 
 * </code> <br>
 * The <code>Log4jXMLConnector</code> collects the configuration and sets up a
 * Log4j Logger with the XML file that exists where
 * <code>PARAMETER_CONFIG_TARGET</code> specifies. Please review in the Log4j
 * documentation how to configure Log4j with XML files.<br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <ul>
 * <li><b>Runtime:</b>
 * <ul>
 * <li>Java 1.5</li>
 * </ul>
 * </li>
 * <li><b>Libraries:</b>
 * <ul>
 * <li>Apache Log4j version 1.2.12</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class Log4jXMLConnector extends AbstractLog4jConnector {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configures Log4j from an external XML file.
	 * 
	 * @param config XML file.<br>
	 *               <br>
	 */
	protected void configure(final InputStream config) {
		new DOMConfigurator().doConfigure(config, LogManager.getLoggerRepository());
	}

}
