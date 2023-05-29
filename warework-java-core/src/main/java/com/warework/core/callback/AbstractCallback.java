package com.warework.core.callback;

import java.util.Map;

import com.warework.core.scope.ScopeFacade;

/**
 * Provides a default implementation for a callback operation. This is the class
 * that users should extend to implement their callbacks.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractCallback extends AbstractBaseCallback {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a callback operation.
	 * 
	 * @param scope
	 *            Scope where this callback operation should belong to.<br>
	 * <br>
	 */
	public AbstractCallback(ScopeFacade scope) {
		super(scope);
	}

	/**
	 * Creates a callback operation.
	 * 
	 * @param scope
	 *            Scope where this callback operation should belong to.
	 * @param attributes
	 *            Attributes (as string-object pairs) for this callback.<br>
	 * <br>
	 */
	public AbstractCallback(ScopeFacade scope, Map<String, Object> attributes) {
		super(scope, attributes);
	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Callback method to implement when operation is a failure.
	 * 
	 * @param exception
	 *            Exception that represents the failure.<br>
	 * <br>
	 */
	protected abstract void onFailure(Throwable exception);

	/**
	 * Callback method to implement when operation is successful.
	 * 
	 * @param result
	 *            Result of the operation.<br>
	 * <br>
	 */
	protected abstract void onSuccess(Object result);

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Callback method to implement when operation is a failure.
	 * 
	 * @param failure
	 *            Failure operation data.<br>
	 * <br>
	 */
	protected void onFailure(CallbackFailure failure) {
		onFailure(failure.getException());
	}

	/**
	 * Callback method to implement when operation is successful.
	 * 
	 * @param success
	 *            Success operation data.<br>
	 * <br>
	 */
	protected void onSuccess(CallbackSuccess success) {
		onSuccess(success.getResult());
	}

}
