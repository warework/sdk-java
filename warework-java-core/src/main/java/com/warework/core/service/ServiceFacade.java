package com.warework.core.service;

import com.warework.core.scope.ScopeFacade;

/**
 * This facade is the entry point to perform operations with Services. To create
 * a new instance use the <code>createService</code> method of the scope where
 * the Service will be.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface ServiceFacade {

	/**
	 * Gets the name of the service.
	 * 
	 * @return Name of the service.<br>
	 *         <br>
	 */
	String getName();

	/**
	 * Gets the scope where this service belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	ScopeFacade getScopeFacade();

}
