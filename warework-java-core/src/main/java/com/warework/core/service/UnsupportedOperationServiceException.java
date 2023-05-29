package com.warework.core.service;

import com.warework.core.service.client.UnsupportedOperationClientException;

/**
 * Thrown when a Service encounters a Client does not support an operation.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class UnsupportedOperationServiceException extends ServiceException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = -7632189128640749964L;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception from another <code>ReplyCodeClientException</code>.
	 * 
	 * @param exception Source exception to encapsulate.<br>
	 *                  <br>
	 */
	public UnsupportedOperationServiceException(final UnsupportedOperationClientException exception) {
		super(exception);
	}

}
