package com.warework.core.service;

import com.warework.core.service.client.ReplyCodeClientException;

/**
 * Thrown when a Service encounters a problem and provides a response code.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ReplyCodeServiceException extends ServiceException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = 7298423918681088534L;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception from another <code>ReplyCodeClientException</code>.
	 * 
	 * @param exception Source exception to encapsulate.<br>
	 *                  <br>
	 */
	public ReplyCodeServiceException(final ReplyCodeClientException exception) {
		super(exception);
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

		// Get Client exception.
		final ReplyCodeClientException exception = (ReplyCodeClientException) getSourceException();

		// Return Client responde code.
		return exception.getReplyCode();

	}

	/**
	 * Gets the Client error code.
	 * 
	 * @return Client error code.<br>
	 *         <br>
	 */
	public String getErrorCode() {

		// Get Client exception.
		final ReplyCodeClientException exception = (ReplyCodeClientException) getSourceException();

		// Return Client responde code.
		return exception.getErrorCode();

	}

}
