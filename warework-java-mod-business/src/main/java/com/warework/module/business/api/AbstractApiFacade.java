package com.warework.module.business.api;

import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractApiFacade implements ApiFacade {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope Facade.
	private ScopeFacade scope;

	// API connection.
	private AbstractApiConnection api;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private AbstractApiFacade() {
		// DO NOTHING.
	}

	/**
	 * Initializes the API facade.
	 * 
	 * @param scope Scope Facade.<br>
	 *              <br>
	 * @param api   API connection.<br>
	 *              <br>
	 */
	public AbstractApiFacade(final ScopeFacade scope, final AbstractApiConnection api) {

		// Invoke default constructor.
		this();

		// Set Scope facade.
		this.scope = scope;

		// Set API connection.
		this.api = api;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope Facade.
	 * 
	 * @return Scope Facade.<br>
	 *         <br>
	 */
	public final ScopeFacade getScopeFacade() {
		return scope;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the API connection.
	 * 
	 * @return API connection.<br>
	 *         <br>
	 */
	protected final AbstractApiConnection getConnection() {
		return api;
	}

	/**
	 * Creates an API exception.
	 * 
	 * @param message Exception message.<br>
	 *                <br>
	 * @param e       Source exception.<br>
	 *                <br>
	 * @return API exception.<br>
	 *         <br>
	 */
	protected final ApiException createException(final String message, final Exception e) {
		return new ApiException(getScopeFacade(), message, e, LogServiceConstants.LOG_LEVEL_WARN);
	}

}
