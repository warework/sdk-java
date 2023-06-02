package com.warework.loader;

import com.warework.core.loader.AbstractJsonLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.model.Provider;
import com.warework.core.model.Scope;
import com.warework.core.model.Service;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.json.JsonArray;
import com.warework.core.util.json.JsonException;
import com.warework.core.util.json.JsonObject;
import com.warework.service.log.LogServiceConstants;

/**
 * Parses a JSON object with the configutation of a Scope.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ScopeClasspathJsonLoader extends AbstractJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant to identify <code>context</code> elements.
	 */
	private static final String ELEMENT_CONTEXT = CommonValueL1Constants.STRING_CONTEXT;

	/**
	 * Constant to identify <code>objects</code> elements.
	 */
	private static final String ELEMENT_OBJECTS = CommonValueL1Constants.STRING_OBJECTS;

	/**
	 * Constant to identify <code>parent</code> elements.
	 */
	private static final String ELEMENT_PARENT = CommonValueL1Constants.STRING_PARENT;

	/**
	 * Constant to identify <code>providers</code> elements.
	 */
	private static final String ELEMENT_PROVIDERS = CommonValueL1Constants.STRING_PROVIDERS;

	/**
	 * Constant to identify <code>services</code> elements.
	 */
	private static final String ELEMENT_SERVICES = CommonValueL1Constants.STRING_SERVICES;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
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
	protected Object parse(final JsonObject json) throws LoaderException {
		try {

			// Create the Scope configuration.
			final Scope scope = createScope();

			// Set the Scope name.
			if (json.has(ELEMENT_NAME)) {
				scope.setName(json.getString(ELEMENT_NAME));
			}

			// Set the Scope initialization parameters.
			if (json.has(ELEMENT_PARAMETERS)) {
				configInitParameters(scope, json.getJSONArray(ELEMENT_PARAMETERS));
			}

			// Set the Providers for the Scope.
			if (json.has(ELEMENT_PROVIDERS)) {
				configProviders(scope, json.getJSONArray(ELEMENT_PROVIDERS));
			}

			// Set the Services for the Scope.
			if (json.has(ELEMENT_SERVICES)) {
				configServices(scope, json.getJSONArray(ELEMENT_SERVICES));
			}

			// Set the Object References for the Scope.
			if (json.has(ELEMENT_OBJECTS)) {
				configObjects(scope, json.getJSONArray(ELEMENT_OBJECTS));
			}

			// Set the Parent for the Scope.
			if (json.has(ELEMENT_PARENT)) {
				scope.setParent(json.getString(ELEMENT_PARENT));
			}

			// Set the Context for the Scope.
			if (json.has(ELEMENT_CONTEXT)) {

				// Get the JSON Context object.
				final JsonArray jsonContext = json.getJSONArray(ELEMENT_CONTEXT);

				// Count Context Scopes.
				final int maxContext = jsonContext.length();

				// Process every Context Scope.
				for (int i = 0; i < maxContext; i++) {

					// Get a Context Scope.
					final Object object = jsonContext.get(i);

					// Set Context Scope.
					if (object instanceof JsonObject) {

						// Get the JSON object that represents the Context
						// Scope.
						final JsonObject jsonConextScope = (JsonObject) object;

						// Get the Context Scope.
						final Scope contextScope = (Scope) parse(jsonConextScope);

						// Set the Context Scope in the Scope.
						scope.setContextScope(contextScope);

					}

				}

			}

			// Return updated Scope.
			return scope;

		} catch (final JsonException e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot parse the JSON Scope configuration file because the following error is found: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
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

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configure initialization parameters.
	 * 
	 * @param scope          Scope configuration.<br>
	 *                       <br>
	 * @param jsonParameters JSON object that represents the initialization
	 *                       parameters.<br>
	 *                       <br>
	 * @throws JsonException   If there is an error when working with the JSON
	 *                         array.<br>
	 *                         <br>
	 * @throws LoaderException If there is an error when trying to parse the array
	 *                         of parameters.<br>
	 *                         <br>
	 */
	private void configInitParameters(final Scope scope, final JsonArray jsonParameters)
			throws JsonException, LoaderException {

		// Count initialization parameters.
		final int maxParameters = jsonParameters.length();

		// Process every initialization parameter.
		for (int index = 0; index < maxParameters; index++) {

			// Get a initialization parameter.
			final Object object = jsonParameters.get(index);

			// Set the initialization parameter.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the initialization
				// parameter.
				final JsonObject parameter = (JsonObject) object;

				// Set the initialization parameter.
				if ((parameter.has(ELEMENT_NAME)) && (parameter.has(ELEMENT_VALUE))) {
					scope.setInitParameter(parameter.getString(ELEMENT_NAME), parameter.getString(ELEMENT_VALUE));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is an initialization parameter without name or a value specified.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}

	}

	/**
	 * Configure Providers.
	 * 
	 * @param scope         Scope configuration.<br>
	 *                      <br>
	 * @param jsonProviders JSON object that represents the Providers.<br>
	 *                      <br>
	 * @throws JsonException   If there is an error when working with the JSON
	 *                         array.<br>
	 *                         <br>
	 * @throws LoaderException If there is an error when trying to parse the array
	 *                         of Providers.<br>
	 *                         <br>
	 */
	private void configProviders(final Scope scope, final JsonArray jsonProviders)
			throws JsonException, LoaderException {

		// Count Providers.
		final int maxProviders = jsonProviders.length();

		// Process every Provider.
		for (int index = 0; index < maxProviders; index++) {

			// Get a Provider.
			final Object object = jsonProviders.get(index);

			// Set the Provider.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the Provider.
				final JsonObject jsonProvider = (JsonObject) object;

				// Create a new Provider
				final Provider provider = createProvider();

				// Set the name of the Provider.
				if (jsonProvider.has(ELEMENT_NAME)) {
					provider.setName(jsonProvider.getString(ELEMENT_NAME));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is a Provider without name.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the class of the Provider.
				if (jsonProvider.has(ELEMENT_PROVIDER)) {
					provider.setClazz(jsonProvider.getString(ELEMENT_PROVIDER));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is a Provider without class.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the parameters of the Provider.
				if (jsonProvider.has(ELEMENT_PARAMETERS)) {
					provider.setParameters(parseParameters(jsonProvider.getJSONArray(ELEMENT_PARAMETERS)));
				}

				// Set the Provider in the Scope.
				scope.setProvider(provider);

			}

		}

	}

	/**
	 * Configure Services.
	 * 
	 * @param scope        Scope configuration.<br>
	 *                     <br>
	 * @param jsonServices JSON object that represents the Services.<br>
	 *                     <br>
	 * @throws JsonException   If there is an error when working with the JSON
	 *                         array.<br>
	 *                         <br>
	 * @throws LoaderException If there is an error when trying to parse the array
	 *                         of Services.<br>
	 *                         <br>
	 */
	private void configServices(final Scope scope, final JsonArray jsonServices) throws JsonException, LoaderException {

		// Count Services.
		final int maxServices = jsonServices.length();

		// Process every Service.
		for (int index = 0; index < maxServices; index++) {

			// Get a Service.
			final Object object = jsonServices.get(index);

			// Set the Service.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the Service.
				final JsonObject jsonService = (JsonObject) object;

				// Create a new Service.
				final Service service = createService();

				// Set the name of the Service.
				if (jsonService.has(ELEMENT_NAME)) {
					service.setName(jsonService.getString(ELEMENT_NAME));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is a Service without name.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the class of the Service.
				if (jsonService.has(ELEMENT_SERVICE)) {
					service.setClazz(jsonService.getString(ELEMENT_SERVICE));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is a Service without class.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the parameters of the Service.
				if (jsonService.has(ELEMENT_PARAMETERS)) {
					service.setParameters(parseParameters(jsonService.getJSONArray(ELEMENT_PARAMETERS)));
				}

				// Set the Service in the Scope.
				scope.setService(service);

			}

		}

	}

	/**
	 * Configure Object References.
	 * 
	 * @param scope       Scope configuration.<br>
	 *                    <br>
	 * @param jsonObjects JSON object that represents the Object References.<br>
	 *                    <br>
	 * @throws JsonException   If there is an error when working with the JSON
	 *                         array.<br>
	 *                         <br>
	 * @throws LoaderException If there is an error when trying to parse the array
	 *                         of object references.<br>
	 *                         <br>
	 */
	private void configObjects(final Scope scope, final JsonArray jsonObjects) throws JsonException, LoaderException {

		// Count Object References.
		final int maxObjects = jsonObjects.length();

		// Process every Object Reference.
		for (int index = 0; index < maxObjects; index++) {

			// Get a Object Reference.
			final Object object = jsonObjects.get(index);

			// Set the Object Reference.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the Object Reference.
				final JsonObject jsonProvider = (JsonObject) object;

				// Include the object reference.
				if ((jsonProvider.has(ELEMENT_NAME)) && (jsonProvider.has(ELEMENT_PROVIDER))
						&& (jsonProvider.has(ELEMENT_OBJECT))) {
					scope.setObjectReference(jsonProvider.getString(ELEMENT_NAME),
							jsonProvider.getString(ELEMENT_PROVIDER), jsonProvider.getString(ELEMENT_OBJECT));
				} else {
					throw new LoaderException(getScopeFacade(),
							"WAREWORK cannot parse the JSON Scope configuration file because there is an Object Reference without name, Provider or object name defined.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}

	}

}
