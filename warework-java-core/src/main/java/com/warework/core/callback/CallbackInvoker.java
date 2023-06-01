package com.warework.core.callback;

/**
 * Executes a callback invocation.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class CallbackInvoker {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Success message.
	private String successMessage;

	// Current callback where to execute the operation.
	private AbstractBaseCallback currentCallback;

	// Main callback operation to perform.
	private AbstractBaseCallback sourceCallback;

	// Result of the operation.
	private Object result;

	// Callback invokers chain.

	private CallbackInvoker next, previous;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private CallbackInvoker() {

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the object required to invoke the callback operation.
	 * 
	 * @param currentCallback Current callback where to execute the operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback operation to perform.<br>
	 *                        <br>
	 * @param successMessage  Message to log if operation is executed
	 *                        successfully.<br>
	 *                        <br>
	 * @param previous        Previous callback invocation.<br>
	 *                        <br>
	 * @return Object required to invoke the callback operation.<br>
	 *         <br>
	 */
	public static CallbackInvoker createInvoker(final AbstractBaseCallback currentCallback,
			final AbstractBaseCallback sourceCallback, final String successMessage, final CallbackSuccess previous) {

		// Create the callback invoker.
		final CallbackInvoker invoker = new CallbackInvoker();

		// Set invoker fields.
		invoker.successMessage = successMessage;
		invoker.currentCallback = currentCallback;
		invoker.sourceCallback = sourceCallback;

		// Build invocation chain.
		if ((previous != null) && (previous.getCallbackInvoker() != null)) {

			// Set previous invoker for current invoker.
			invoker.previous = previous.getCallbackInvoker();

			// Set next invoker (current invoker) for previous invoker.
			previous.getCallbackInvoker().next = invoker;

		}

		// Return the invoker.
		return invoker;

	}

	/**
	 * Executes a successful callback operation.
	 * 
	 * @param currentCallback Current callback where to execute the operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback operation to perform.<br>
	 *                        <br>
	 * @param successMessage  Message to log if operation is executed
	 *                        successfully.<br>
	 *                        <br>
	 * @param previous        Previous callback invocation.<br>
	 *                        <br>
	 * @param destroyBatch    Specifies if batch attributes should be removed.<br>
	 *                        <br>
	 * @param result          Result for the successful operation.
	 * @return Object used to invoke the callback operation.<br>
	 *         <br>
	 */
	public static CallbackInvoker invokeSuccess(final AbstractBaseCallback currentCallback,
			final AbstractBaseCallback sourceCallback, final String successMessage, final CallbackSuccess previous,
			final boolean destroyBatch, final Object result) {

		// Remove callback batch parameters.
		if (destroyBatch) {
			currentCallback.getControl().destroyBatch();
		}

		// Create the object required to invoke the callback operation.
		final CallbackInvoker invoker = createInvoker(currentCallback, sourceCallback, successMessage, previous);

		// Set the result for the callback operation.
		invoker.success(result);

		// Return the invoker.
		return invoker;

	}

	/**
	 * Executes a failure in the callback operation.
	 * 
	 * @param currentCallback Current callback where to execute the operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback operation to perform.<br>
	 *                        <br>
	 * @param exception       Error found.<br>
	 * @return Object used to invoke the callback operation.<br>
	 *         <br>
	 */
	public static CallbackInvoker invokeFailure(final AbstractBaseCallback currentCallback,
			final AbstractBaseCallback sourceCallback, final Throwable exception) {

		// Create the object required to invoke the callback
		// operation.
		final CallbackInvoker invoker = createInvoker(currentCallback, sourceCallback, null, null);

		// Set the failure for the callback operation.
		invoker.failure(exception);

		// Return the invoker.
		return invoker;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Resets current attributes of the invoker with new values.
	 * 
	 * @param currentCallback Current callback where to execute the operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback operation to perform.<br>
	 *                        <br>
	 * @param successMessage  Message to log if operation is executed
	 *                        successfully.<br>
	 *                        <br>
	 * @param previous        Previous callback invocation.<br>
	 *                        <br>
	 */
	public void reset(final AbstractBaseCallback currentCallback, final AbstractBaseCallback sourceCallback,
			final String successMessage, final CallbackSuccess previous) {

		// Update invoker fields.
		this.successMessage = successMessage;
		this.currentCallback = currentCallback;
		this.sourceCallback = sourceCallback;

		// Build invocation chain.

		// Remove old previous invoker instances.
		if (this.previous != null) {
			this.previous.next = null;
		}

		// Update previos invocations.
		if (previous != null) {

			// Set previous invoker.
			this.previous = previous.getCallbackInvoker();

			// Set next invoker of previous invoker with the current instance.
			previous.getCallbackInvoker().next = this;

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the message to log when operation is executed successfully.
	 * 
	 * @return Message for successful operation.<br>
	 *         <br>
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * Gets the current callback where to execute the operation.
	 * 
	 * @return Callback operation.<br>
	 *         <br>
	 */
	public AbstractBaseCallback getCurrentCallback() {
		return currentCallback;
	}

	/**
	 * Gets the main callback operation to perform.
	 * 
	 * @return Callback operation.<br>
	 *         <br>
	 */
	public AbstractBaseCallback getSourceCallback() {
		return sourceCallback;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the next callback invoker in the invocation chain.
	 * 
	 * @return Next callback invoker.<br>
	 *         <br>
	 */
	public CallbackInvoker getNextInvoker() {
		return next;
	}

	/**
	 * Gets the previous callback invoker in the invocation chain.
	 * 
	 * @return Previous callback invoker.<br>
	 *         <br>
	 */
	public CallbackInvoker getPreviousInvoker() {
		return previous;
	}

	/**
	 * Gets the first callback invoker in the invocation chain.
	 * 
	 * @return First callback invoker.<br>
	 *         <br>
	 */
	public CallbackInvoker getFirstInvoker() {

		// Get the next callback invoker.
		CallbackInvoker current = previous;

		// Search for the last callback invoker.
		while (current != null) {
			if (current.getPreviousInvoker() != null) {
				current = current.getPreviousInvoker();
			} else {
				return current;
			}
		}

		// At this point, return the this invoker.
		return this;

	}

	/**
	 * Gets the last callback invoker in the invocation chain.
	 * 
	 * @return Last callback invoker.<br>
	 *         <br>
	 */
	public CallbackInvoker getLastInvoker() {

		// Get the next callback invoker.
		CallbackInvoker current = next;

		// Search for the last callback invoker.
		while (current != null) {
			if (current.getNextInvoker() != null) {
				current = current.getNextInvoker();
			} else {
				return current;
			}
		}

		// At this point, return the this invoker.
		return this;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the result of the operation.
	 * 
	 * @return Result of the operation.<br>
	 *         <br>
	 */
	public Object getResult() {
		return result;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a succesful callback operation.
	 * 
	 * @param result Result of the operation.<br>
	 *               <br>
	 */
	public void success(Object result) {

		// Set the result of the operation.
		this.result = result;

		// Create a successful callback bean.
		final CallbackSuccess success = new CallbackSuccess();

		// Set the information required for a successfully executed operation.
		success.setResult(result);
		success.setMessage(successMessage);
		success.setSourceCallback(sourceCallback);
		success.setCallbackInvoker(this);

		// Execute the operation.
		currentCallback.getControl().success(success);

	}

	/**
	 * Executes a failed callback operation.
	 * 
	 * @param exception Error found.<br>
	 *                  <br>
	 */
	public void failure(final Throwable exception) {

		// Create a failure callback bean.
		CallbackFailure failure = new CallbackFailure();

		// Set the information required for a failed executed operation.
		failure.setException(exception);
		failure.setSourceCallback(sourceCallback);

		// Execute the operation.
		currentCallback.getControl().failure(failure);

	}

}
