package com.warework.core;

import java.util.Locale;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;

/**
 * Provides a default implementation for an exception.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractL2Exception extends AbstractL1Exception {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial version.
	private static final long serialVersionUID = 8535449625822464124L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Specific error message.
	private String message;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception.
	 * 
	 * @param scope
	 *            Scope facade used to perform loggin. This parameter is
	 *            optional but without it this method will not use the Log
	 *            Service to log the exception. To perform loggin you'll also
	 *            need to pass the 'logLevel' parameter.<br>
	 * <br>
	 * @param resourcePackage
	 *            Package where the properties file that contains the messages
	 *            exists. The name of the properties file is defined at
	 *            <code>com.warework.core.util.helper.ResourceL1Helper</code>
	 *            with the constant 'FILE_NAME_MessagesResource'.<br>
	 * <br>
	 * @param messageKey
	 *            Key of the properties file that points to the error message.<br>
	 * <br>
	 * @param values
	 *            Replaces variables with messages in the target error message.
	 *            Keys are the names of the variables in the message and the
	 *            values those that will replace the variables. Example: if the
	 *            error message in the properties file is 'My car is ${NAME}',
	 *            the map's key should be NAME and the value for that key could
	 *            be RED.<br>
	 * <br>
	 * @param locale
	 *            Indicates which language-contry specific properties file to
	 *            use.<br>
	 * <br>
	 * @param sourceException
	 *            Exception that originates the creation of this exception (so,
	 *            it acts as a wrapper). This parameter is optional.<br>
	 * <br>
	 * @param logLevel
	 *            Indicates how to perform the log. Use the
	 *            <code>com.warework.service.log.LogServiceConstants</code> to
	 *            specify the level of the log. This parameter is optional but
	 *            without it this exception will not use the Log Service. To
	 *            perform loggin you'll also need to pass the 'scope' parameter.<br>
	 * <br>
	 */
	public AbstractL2Exception(ScopeFacade scope, Package resourcePackage,
			String messageKey, Map<String, String> values, Locale locale,
			Throwable sourceException, int logLevel) {

		// Note: Resource bundle instances created by the getBundle factory
		// methods are cached by default (check out:
		// http://download-llnw.oracle.com/javase/1.5.0/docs/api/java/util/ResourceBundle.html)

		// Save the key as the message at root exception.
		super(scope, messageKey, sourceException, (short) -1);

		// Get the message from the properties file.
		message = ResourceL2Helper.getProperty(resourcePackage,
				ResourceL2Helper.FILE_NAME_MESSAGES_RESOURCE, messageKey,
				locale);

		// Replace the variables from the error message.
		if ((message != null) && (values != null)) {
			message = StringL2Helper.replace(message, values);
		}

		// Log the exception message when Log Service is enabled.
		if (scope != null) {
			scope.log(message, logLevel);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the detail message string of this exception.
	 * 
	 * @return Exception message.<br>
	 * <br>
	 */
	public String getMessage() {
		return message;
	}

}