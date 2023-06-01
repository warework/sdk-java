package com.warework.core.scope;

import com.warework.core.AbstractL1Exception;

/**
 * Thrown when a Scope encounters a problem during initialization or
 * finalization.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ScopeException extends AbstractL1Exception {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = 5872674677116249096L;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception.
	 * 
	 * @param scope           Scope facade used to perform loggin. This parameter is
	 *                        optional but without it this exception will not use
	 *                        the Log Service. To perform loggin you'll also need to
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
	public ScopeException(final ScopeFacade scope, final String errorMessage, final Throwable sourceException,
			final int logLevel) {
		super(scope, errorMessage, sourceException, logLevel);
	}

	/**
	 * Creates the exception from another exception.
	 * 
	 * @param exception Source exception to encapsulate.<br>
	 *                  <br>
	 */
	public ScopeException(final AbstractL1Exception exception) {
		super(exception);
	}

}
