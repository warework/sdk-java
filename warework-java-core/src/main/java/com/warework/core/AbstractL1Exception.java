package com.warework.core;

import com.warework.core.scope.ScopeFacade;

/**
 * Base implementation for exceptions.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractL1Exception extends Exception {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serialization ID.
	private static final long serialVersionUID = 3642192871897964682L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope where this exception belongs to.
	private ScopeFacade scope;

	// Source exception.
	private Throwable source;

	// Log level.
	private int logLevel;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the exception.
	 * 
	 * @param scope           Scope facade used to perform loggin. This parameter is
	 *                        optional but without it this method will not use the
	 *                        Log Service to log the exception. To perform loggin
	 *                        you'll also need to pass the 'logLevel' parameter.<br>
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
	public AbstractL1Exception(final ScopeFacade scope, final String errorMessage, final Throwable sourceException,
			final int logLevel) {

		// Save the error message.
		super(errorMessage);

		// Set the Scope.
		this.scope = scope;

		// Save the exception.
		this.source = sourceException;

		// Set level of the log.
		this.logLevel = logLevel;

		// Log the exception message when log service is enabled.
		if (scope != null) {
			scope.log(getMessage(), logLevel);
		}

	}

	/**
	 * Creates the exception from another exception.
	 * 
	 * @param exception Source exception to encapsulate.<br>
	 *                  <br>
	 */
	public AbstractL1Exception(final AbstractL1Exception exception) {

		/*
		 * As given exception is a Warework exception and message already was processed
		 * with the Log Service, there's no need to process it again with the Log
		 * Service (this is to avoid duplicate log messages).
		 */

		// Save the error message.
		super(exception.getMessage());

		// Set the Scope.
		this.scope = exception.scope;

		// Save the exception.
		this.source = exception;

		// Set level of the log.
		this.logLevel = exception.logLevel;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this exception belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	public final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Gets the source exception.
	 * 
	 * @return Source exception.<br>
	 *         <br>
	 */
	public Throwable getSourceException() {
		return source;
	}

	/**
	 * Gets the exception's message.
	 * 
	 * @return Exception's message.<br>
	 *         <br>
	 */
	public String toString() {
		if (getMessage() != null) {
			return getMessage();
		} else if ((source != null) && (source.getMessage() != null)) {
			return source.getMessage();
		} else {
			return "Exception '" + this.getClass().getName() + "' thrown!";
		}
	}

}
