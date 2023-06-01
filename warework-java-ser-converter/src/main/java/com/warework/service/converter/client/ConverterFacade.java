package com.warework.service.converter.client;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;

/**
 * Client that transforms a given object into another object.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface ConverterFacade extends ClientFacade {

	/**
	 * Transforms a given object into another one.
	 * 
	 * @param source
	 *            Source object to transform.<br>
	 * <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to perform the
	 *             transformation.<br>
	 * <br>
	 */
	Object transform(final Object source) throws ClientException;

}
