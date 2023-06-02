package com.warework.core.callback;

import java.util.Map;

import com.warework.core.scope.ScopeFacade;

/**
 * Provides a default implementation for a callback operation. This class is
 * used internally by the Framework. Users rarely use it in their apps.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractDefaultCallback extends AbstractBaseCallback {

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
	public AbstractDefaultCallback(ScopeFacade scope) {
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
	public AbstractDefaultCallback(ScopeFacade scope, Map<String, Object> attributes) {
		super(scope, attributes);
	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Callback method to implement when operation is a failure.
	 * 
	 * @param failure
	 *            Exception that represents the failure.<br>
	 * <br>
	 */
	protected void onFailure(CallbackFailure failure) {
		CallbackInvoker.invokeFailure(failure.getSourceCallback(), null,
				failure.getException());
	}

}
