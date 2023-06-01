package com.warework.core.service.client;

import com.warework.core.scope.ScopeFacade;

/**
 * Thrown when a Client does not support an operation.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class UnsupportedOperationClientException extends ClientException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = -259159960378751395L;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception.
	 * 
	 * @param scope           Scope facade used to perform loggin. This parameter is
	 *                        optional but without it this exception will not use
	 *                        the log Service. To perform loggin you'll also need to
	 *                        pass the 'logLevel' parameter.<br>
	 *                        <br>
	 * @param errorMessage    Message that describes what's going on.<br>
	 *                        <br>
	 * @param sourceException Exception that originates the creation of this
	 *                        exception (so, it acts as a wrapper). This parameter
	 *                        is optional.<br>
	 *                        <br>
	 * @param logLevel        Indicates how to perform the log. Use the
	 *                        <code>com.warework.service.log.LogServiceConstants</code>
	 *                        to specify the level of the log. This parameter is
	 *                        optional but without it this exception will not use
	 *                        the log service. To perform loggin you'll also need to
	 *                        pass the 'scope' parameter.<br>
	 *                        <br>
	 */
	public UnsupportedOperationClientException(final ScopeFacade scope, final String errorMessage,
			final Throwable sourceException, final int logLevel) {
		super(scope, errorMessage, sourceException, logLevel);
	}

}
