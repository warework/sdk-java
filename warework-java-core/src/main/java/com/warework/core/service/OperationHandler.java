package com.warework.core.service;

import java.util.Map;

/**
 * Handles Services operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface OperationHandler {

	/**
	 * Initializes the operation handler.
	 * 
	 * @param service
	 *            Service where to perform the operations.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to initialize the operation
	 *             handler.<br>
	 * <br>
	 */
	void init(ServiceFacade service) throws ServiceException;

	/**
	 * Executes a Service operation.
	 * 
	 * @param operationName
	 *            Name of the operation to execute.<br>
	 * <br>
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Operation result.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	Object execute(String operationName, Map<String, Object> parameters)
			throws ServiceException;

}
