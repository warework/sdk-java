package com.warework.core.callback;

/**
 * Holds the information required for a successfully executed operation.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class CallbackSuccess {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Success message.
	private String successMessage;

	// Operaion result.
	private Object operationResult;

	// Main callback operation to perform.
	private AbstractBaseCallback source;

	// Callback operation invoker.
	private CallbackInvoker callbackInvoker;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the message to log when operation is executed successfully.
	 * 
	 * @return Message for successful operation.<br>
	 * <br>
	 */
	public String getMessage() {
		return successMessage;
	}

	/**
	 * Gets the result of the operation.
	 * 
	 * @return Operation result.<br>
	 * <br>
	 */
	public Object getResult() {
		return operationResult;
	}

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
	 * Gets the callback operation invoker.
	 * 
	 * @return Callback operation invoker.<br>
	 * <br>
	 */
	public CallbackInvoker getCallbackInvoker() {
		return callbackInvoker;
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the message to log when operation is executed successfully.
	 * 
	 * @param message
	 *            Message for successful operation.<br>
	 * <br>
	 */
	void setMessage(String message) {
		this.successMessage = message;
	}

	/**
	 * Sets the result of the operation.
	 * 
	 * @param result
	 *            Operation result.<br>
	 * <br>
	 */
	void setResult(Object result) {
		this.operationResult = result;
	}

	/**
	 * Sets the main callback operation to perform.
	 * 
	 * @param sourceCallback
	 *            Source callback.<br>
	 * <br>
	 */
	void setSourceCallback(AbstractBaseCallback sourceCallback) {
		this.source = sourceCallback;
	}

	/**
	 * Sets the callback operation invoker.
	 * 
	 * @param invoker
	 *            Callback operation ivoker.<br>
	 * <br>
	 */
	void setCallbackInvoker(CallbackInvoker invoker) {
		this.callbackInvoker = invoker;
	}

}
