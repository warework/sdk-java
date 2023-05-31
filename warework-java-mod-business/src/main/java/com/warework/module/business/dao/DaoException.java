package com.warework.module.business.dao;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.module.business.AbstractBusinessException;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class DaoException extends AbstractBusinessException {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// SERIAL UID
	private static final long serialVersionUID = 4270339571476338251L;

	// ERROR CODE
	private static final String DAO_ERROR_CODE = "ddbb.dao";

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
	 * @param moduleName      Name of the module.<br>
	 *                        <br>
	 * @param operationType   Operation executed.<br>
	 *                        <br>
	 * @param daoName         Name of the DAO.<br>
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
	public DaoException(final ScopeFacade scope, final String moduleName, final String operationType,
			final String daoName, final Throwable sourceException, final int logLevel) {
		super(scope, moduleName + StringL2Helper.CHARACTER_PERIOD + DAO_ERROR_CODE + StringL2Helper.CHARACTER_PERIOD
				+ daoName + StringL2Helper.CHARACTER_PERIOD + operationType, sourceException, logLevel);
	}

}
