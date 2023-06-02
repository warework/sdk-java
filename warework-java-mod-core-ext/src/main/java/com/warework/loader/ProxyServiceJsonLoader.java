package com.warework.loader;

import java.io.InputStream;

import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.model.ser.Client;
import com.warework.core.model.ser.ProxyService;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.bean.ser.Parameter;

/**
 * Loads a JSON file with the configutation of a Scope and parses its content.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ProxyServiceJsonLoader extends ProxyServiceClasspathJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads the JSON file.
	 * 
	 * @return JSON file.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected InputStream loadJSON() throws LoaderException {
		return new ResourceLoader(getScopeFacade()).getResource(getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Proxy Service.
	 * 
	 * @return A new and empty Proxy Service.<br>
	 *         <br>
	 */
	protected ProxyService createProxyService() {
		return new ProxyService();
	}

	/**
	 * Creates a new Client.
	 * 
	 * @return A new and empty Client.<br>
	 *         <br>
	 */
	protected Client createClient() {
		return new Client();
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
