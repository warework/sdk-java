package com.warework.module.business.api;

import com.warework.core.scope.ScopeFacade;
import com.warework.module.business.AbstractBusinessException;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ApiException extends AbstractBusinessException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// SERIAL UID
	private static final long serialVersionUID = 8353207420242782719L;

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
	public ApiException(final ScopeFacade scope, final String errorMessage, final Throwable sourceException,
			final int logLevel) {
		super(scope, errorMessage, sourceException, logLevel);
	}

}
