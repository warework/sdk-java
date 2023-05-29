package com.warework.service.log;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Log Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class LogServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "log";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// DEFAULT LOG LEVELS

	/**
	 * Constant that defines the <code>DEBUG</code> log level. This level
	 * designates detailed informational events used to debug applications.
	 */
	public static final int LOG_LEVEL_DEBUG = 0x10;

	/**
	 * Constant that defines the <code>INFO</code> log level. This level
	 * designates informational messages that highlight the progress of the
	 * application.
	 */
	public static final int LOG_LEVEL_INFO = 0x20;

	/**
	 * Constant that defines the <code>WARNING</code> log level. This level
	 * designates a potential problem.
	 */
	public static final int LOG_LEVEL_WARN = 0x30;

	/**
	 * Constant that defines the <code>FATAL</code> log level. This level
	 * designates serious failures that will presumably lead the application to
	 * abort.
	 */
	public static final int LOG_LEVEL_FATAL = 0x40;

	// DEFAULT LOG NAMES

	/**
	 * Constant that defines the <code>DEBUG</code> word.
	 */
	public static final String LOG_NAME_DEBUG = "DEBUG";

	/**
	 * Constant that defines the <code>INFO</code> word.
	 */
	public static final String LOG_NAME_INFO = "INFO";

	/**
	 * Constant that defines the <code>WARNING</code> word.
	 */
	public static final String LOG_NAME_WARN = "WARNING";

	/**
	 * Constant that defines the <code>FATAL</code> word.
	 */
	public static final String LOG_NAME_FATAL = "FATAL";

	// OPERATION NAMES

	/**
	 * Logs a message in a Logger. Use the following parameters in order to
	 * invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client where to
	 * perform the log operation. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>level</code></strong>: Specifies how to perform the log. You have
	 * to provide a <code>java.lang.Integer</code> or a
	 * <code>java.lang.String</code> object with the level of the log. Common
	 * level values are also defined in this class (check out
	 * <code>LOG_LEVEL_xyz</code> and <code>LOG_NAME_zyx</code> constants).<br>
	 * <br>
	 * </li>
	 * <li><strong><code>message</code></strong>: The message to log. This parameter must
	 * be a <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_LOG = "log";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies how to perform the log. You have to
	 * provide a <code>java.lang.Integer</code> object with the level of the
	 * log. Common level values are also defined in this class.
	 */
	public static final String OPERATION_PARAMETER_LOG_LEVEL = "level";

	/**
	 * Operation parameter that specifies the message to log. This parameter
	 * must be a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_LOG_MESSAGE = "message";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private LogServiceConstants() {
	}

}
