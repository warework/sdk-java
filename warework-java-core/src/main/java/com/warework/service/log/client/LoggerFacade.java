package com.warework.service.log.client;

import com.warework.core.service.client.ClientFacade;

/**
 * Client that perform log operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface LoggerFacade extends ClientFacade {

	/**
	 * Logs a message.
	 * 
	 * @param message
	 *            Message to log.<br>
	 * <br>
	 * @param logLevel
	 *            Indicates how to perform the log: 32=INFO, 16=DEBUG,
	 *            48=WARNING, 64=FATAL, etc. Check out these levels at
	 *            <code>com.warework.service.log.LogServiceConstants</code> and
	 *            keep in mind that specific Logger implementations may use
	 *            custom log levels.<br>
	 * <br>
	 */
	void log(String message, int logLevel);

}
