package com.warework.core.loader;

import java.util.Map;

import com.warework.core.scope.ScopeFacade;

/**
 * Loads a configuration for a Scope or a Service. It should read the
 * configuration from a source (a XML file or a relational database for
 * example), transform this source into an object and finally return this
 * object.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface LoaderFacade {

	/**
	 * Loads an object.
	 * 
	 * @param scope      Scope where this Loader belongs to.<br>
	 *                   <br>
	 * @param parameters Initialization parameters (as string-object pairs) for this
	 *                   Loader.<br>
	 *                   <br>
	 * @return Object loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	Object load(final ScopeFacade scope, final Map<String, Object> parameters) throws LoaderException;

}
