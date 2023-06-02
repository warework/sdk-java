package com.warework.module.business.api;

import java.util.Collection;

import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractApiMapper<T> implements ApiMapper<T> {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private ScopeFacade scope;

	//
	private ApiFacade apiFacade;

	//
	private String operation;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private AbstractApiMapper() {
		// DO NOTHING.
	}

	/**
	 * 
	 * @param operation
	 * @param apiFacade
	 */
	public AbstractApiMapper(final String operation, final ApiFacade apiFacade) {

		//
		this();

		//
		this.scope = apiFacade.getScopeFacade();

		//
		this.apiFacade = apiFacade;

		//
		this.operation = operation;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return API response keys.<br>
	 *         <br>
	 * @throws ApiException
	 */
	public Collection<String> getKeys() throws ApiException {
		return apiFacade.getKeys();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return Operation name.<br>
	 *         <br>
	 */
	protected final String getOperation() {
		return operation;
	}

	/**
	 * 
	 * @return Scope Facade instance.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * 
	 * @return API facade instance.<br>
	 *         <br>
	 */
	protected final ApiFacade getAPIFacade() {
		return apiFacade;
	}

	/**
	 * 
	 * @param message
	 * @param e
	 * @return New exception.<br>
	 *         <br>
	 */
	protected final ApiException createException(final String message, final Exception e) {
		return new ApiException(getScopeFacade(), message, e, LogServiceConstants.LOG_LEVEL_WARN);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param message
	 */
	protected final void debug(final String message) {
		getScopeFacade().debug(log(message));
	}

	/**
	 * 
	 * @param message
	 */
	protected final void info(final String message) {
		getScopeFacade().info(log(message));
	}

	/**
	 * 
	 * @param message
	 */
	protected final void warn(final String message) {
		getScopeFacade().warn(log(message));
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param message
	 * @return New log message.<br>
	 *         <br>
	 */
	private String log(final String message) {
		return "[BUSINESS-'" + getOperation() + "'@'" + getClass().getName() + "'] - " + message;
	}

}
