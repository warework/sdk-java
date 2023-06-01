package com.warework.loader;

import java.io.InputStream;

import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.model.ser.Provider;
import com.warework.core.model.ser.Scope;
import com.warework.core.model.ser.Service;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.bean.ser.Parameter;

/**
 * Loads a JSON file with the configutation of a Scope and parses its content.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ScopeJsonLoader extends ScopeClasspathJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads the JSON file.
	 * 
	 * @return JSON file.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to parse the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected InputStream loadJSON() throws LoaderException {
		return new ResourceLoader(getScopeFacade()).getResource(getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Scope.
	 * 
	 * @return A new and empty Scope.<br>
	 *         <br>
	 */
	protected Scope createScope() {
		return new Scope();
	}

	/**
	 * Creates a new Provider.
	 * 
	 * @return A new and empty Provider.<br>
	 *         <br>
	 */
	protected Provider createProvider() {
		return new Provider();
	}

	/**
	 * Creates a new Service.
	 * 
	 * @return A new and empty Service.<br>
	 *         <br>
	 */
	protected Service createService() {
		return new Service();
	}

	/**
	 * Creates a new parameter.
	 * 
	 * @return A new and empty parameter.<br>
	 *         <br>
	 */
	protected Parameter createParameter() {
		return new Parameter();
	}

}
