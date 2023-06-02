package com.warework.core.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.core.util.json.JsonArray;
import com.warework.core.util.json.JsonException;
import com.warework.core.util.json.JsonObject;
import com.warework.service.log.LogServiceConstants;

/**
 * Loads a JSON file and parses its content.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractJsonLoader extends AbstractLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_CLASS = CommonValueL1Constants.STRING_CLASS;

	/**
	 * Constant to identify <code>client</code> elements.
	 */
	protected static final String ELEMENT_CLIENT = CommonValueL1Constants.STRING_CLIENT;

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_CONNECTOR = CommonValueL1Constants.STRING_CONNECTOR;

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_NAME = CommonValueL1Constants.STRING_NAME;

	/**
	 * Constant to identify <code>object</code> elements.
	 */
	protected static final String ELEMENT_OBJECT = CommonValueL1Constants.STRING_OBJECT;

	/**
	 * Constant to identify <code>parameters</code> elements.
	 */
	protected static final String ELEMENT_PARAMETERS = CommonValueL1Constants.STRING_PARAMETERS;

	/**
	 * Constant to identify <code>provider</code> elements.
	 */
	protected static final String ELEMENT_PROVIDER = CommonValueL1Constants.STRING_PROVIDER;

	/**
	 * Constant to identify <code>service</code> elements.
	 */
	protected static final String ELEMENT_SERVICE = CommonValueL1Constants.STRING_SERVICE;

	/**
	 * Constant to identify <code>type</code> elements.
	 */
	protected static final String ELEMENT_TYPE = CommonValueL1Constants.STRING_TYPE;

	/**
	 * Constant to identify <code>value</code> elements.
	 */
	protected static final String ELEMENT_VALUE = CommonValueL1Constants.STRING_VALUE;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Transforms a JSON object into a Java Bean.
	 * 
	 * @param json Object that reprents the JSON file.<br>
	 *             <br>
	 * @return Object that represents the result of the transformation.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to parse the
	 *                         JSON.<br>
	 *                         <br>
	 */
	protected abstract Object parse(final JsonObject json) throws LoaderException;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads the content of a JSON file into an object.
	 * 
	 * @throws LoaderException If there is an error when trying to load or parse the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected final Object load() throws LoaderException {

		// Load the content of the JSON file.
		JsonObject json = null;
		try {
			json = new JsonObject(StringL1Helper.compressJavaScript(StringL1Helper.toString(loadJSON())));
		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load the JSON configuration file because the creation of the JSON parser generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Parse JSON.
		return parse(json);

	}

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
		return new ClasspathResourceLoader(getScopeFacade()).getResource(
				getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET));
	}
	
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the boolean value from a string.
	 * 
	 * @param value String with <code>true</code> or <code>false</code> values in
	 *              upper or lower case.<br>
	 *              <br>
	 * @return <code>Boolean.TRUE</code> if string is <code>true</code> in upper or
	 *         lower case and <code>false</code> otherwise.<br>
	 *         <br>
	 */
	protected final Boolean parseBoolean(final String value) {
		return (value.equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Configure parameters.
	 * 
	 * @param jsonParameters JSON object that represents the parameters.<br>
	 *                       <br>
	 * @return A list with the parameter beans in it.<br>
	 *         <br>
	 * @throws JsonException   If there is an error when working with the JSON
	 *                         array.<br>
	 *                         <br>
	 * @throws LoaderException If there is an error when trying to parse the array
	 *                         of parameters.<br>
	 *                         <br>
	 */
	protected final List<Parameter> parseParameters(final JsonArray jsonParameters)
			throws JsonException, LoaderException {

		// Count parameters.
		int maxParameters = jsonParameters.length();

		// Set the parameters
		List<Parameter> result = null;
		if (maxParameters > 0) {

			// Initialize parameters' list.
			result = new ArrayList<Parameter>();

			// Set every parameter found.
			for (int index = 0; index < maxParameters; index++) {

				// Get a parameter.
				final Object object = jsonParameters.get(index);

				// Set the parameter.
				if (object instanceof JsonObject) {

					// Get the JSON object that represents the parameter.
					final JsonObject jsonParameter = (JsonObject) object;

					// Create a new Parameter.
					final Parameter parameter = createParameter();

					// Set the name of the parameter.
					if (jsonParameter.has(ELEMENT_NAME)) {
						parameter.setName(jsonParameter.getString(ELEMENT_NAME));
					} else {
						throw new LoaderException(getScopeFacade(),
								"WAREWORK cannot parse the JSON configuration file because there is a parameter without the '"
										+ ELEMENT_NAME + "' attribute defined.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Set the value of the parameter.
					if (jsonParameter.has(ELEMENT_VALUE)) {
						parameter.setValue(jsonParameter.getString(ELEMENT_VALUE));
					} else {
						throw new LoaderException(getScopeFacade(),
								"WAREWORK cannot parse the JSON configuration file because there is a parameter without the '"
										+ ELEMENT_VALUE + "' attribute defined.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Add the parameter.
					result.add(parameter);

				}

			}

		}

		// Return the parameters' list.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

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
