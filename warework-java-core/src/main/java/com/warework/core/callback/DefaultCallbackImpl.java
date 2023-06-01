package com.warework.core.callback;

import java.util.Map;

import com.warework.core.scope.ScopeFacade;

/**
 * Provides a default implementation for a callback operation.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class DefaultCallbackImpl extends AbstractCallback {

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
	public DefaultCallbackImpl(ScopeFacade scope) {
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
	public DefaultCallbackImpl(ScopeFacade scope, Map<String, Object> attributes) {
		super(scope, attributes);
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Callback method to implement when operation is a failure.
	 * 
	 * @param exception
	 *            Exception that represents the failure.<br>
	 * <br>
	 */
	protected void onFailure(Throwable exception) {
	}

	/**
	 * Callback method to implement when operation is successful.
	 */
	protected void onSuccess(Object result) {
	}

}
