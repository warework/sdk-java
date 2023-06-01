package com.warework.module.business;

import com.warework.core.AbstractL1Exception;
import com.warework.core.scope.ScopeFacade;

/**
 * Business exceptions.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractBusinessException extends AbstractL1Exception {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// SERIAL UID
	private static final long serialVersionUID = 3999723440011899203L;

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
	public AbstractBusinessException(final ScopeFacade scope, final String errorMessage,
			final Throwable sourceException, final int logLevel) {
		super(scope, errorMessage, sourceException, logLevel);
	}

}
