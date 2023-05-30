package com.warework.loader;

import java.io.ObjectInputStream;

import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.service.log.LogServiceConstants;

/**
 * Loads a serialized Java Object.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ObjectDeserializerLoader extends AbstractLoader {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads an object.
	 * 
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected Object load() throws LoaderException {
		try {

			// Get the stream as an object stream.
			final ObjectInputStream stream = new ObjectInputStream(new ResourceLoader(getScopeFacade()).getResource(
					getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
					getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET)));

			// Return the object that represents the serialized object.
			final Object output = stream.readObject();

			// Close stream.
			stream.close();

			// Return loaded object.
			return output;

		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot deserialize the configuration file because the following exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

}
