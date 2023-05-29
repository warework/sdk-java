package com.warework.core.service.client;

import com.warework.core.scope.ScopeFacade;

/**
 * Thrown when a Client encounters a problem and provides a response code.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ReplyCodeClientException extends ClientException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = 4980476575300810536L;

	// Client response code.
	private int replyCode;

	// Client error code.
	private String errorCode;

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
	 * @param replyCode       Underlying Client response code.<br>
	 *                        <br>
	 * @param errorCode       Underlying Client error code.<br>
	 *                        <br>
	 */
	public ReplyCodeClientException(final ScopeFacade scope, final String errorMessage, final Throwable sourceException,
			final int logLevel, final int replyCode, final String errorCode) {

		// Invoke parent.
		super(scope, errorMessage, sourceException, logLevel);

		// Set Client response code.
		this.replyCode = replyCode;

		// Set Client error code.
		this.errorCode = errorCode;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHOD
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Client responde code.
	 * 
	 * @return Client responde code.<br>
	 *         <br>
	 */
	public int getReplyCode() {
		return replyCode;
	}

	/**
	 * Gets the Client error code.
	 * 
	 * @return Client error code.<br>
	 *         <br>
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
