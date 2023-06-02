package com.warework.core.callback;

/**
 * Holds the information required for a failed operation.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class CallbackFailure {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Failure.
	private Throwable exception;

	// Main callback operation to perform.
	private AbstractBaseCallback source;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the failure of the operation.
	 * 
	 * @return Operation failure.<br>
	 * <br>
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * Sets the failure of the operation.
	 * 
	 * @param exception
	 *            Operation failure.<br>
	 * <br>
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the main callback operation to perform.
	 * 
	 * @return Source callback.<br>
	 * <br>
	 */
	public AbstractBaseCallback getSourceCallback() {
		return source;
	}

	/**
	 * Sets the main callback operation to perform.
	 * 
	 * @param sourceCallback
	 *            Source callback.<br>
	 * <br>
	 */
	public void setSourceCallback(AbstractBaseCallback sourceCallback) {
		this.source = sourceCallback;
	}

}
