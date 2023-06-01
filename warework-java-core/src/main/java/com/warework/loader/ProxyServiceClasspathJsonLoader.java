package com.warework.loader;

import com.warework.core.loader.AbstractJsonLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.model.Client;
import com.warework.core.model.ProxyService;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.json.JsonArray;
import com.warework.core.util.json.JsonException;
import com.warework.core.util.json.JsonObject;
import com.warework.service.log.LogServiceConstants;

/**
 * Parses a JSON object with the configutation of a Proxy Service.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ProxyServiceClasspathJsonLoader extends AbstractJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// ELEMENTS

	private static final String ELEMENT_CLIENTS = CommonValueL1Constants.STRING_CLIENTS;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Transforms a JSON object into a Java Bean.
	 * 
	 * @param json
	 *            Object that reprents the JSON file.<br>
	 * <br>
	 * @return Object that represents the result of the transformation.<br>
	 * <br>
	 * @throws LoaderException
	 *             If there is an error when trying to parse the JSON.<br>
	 * <br>
	 */
	protected Object parse(JsonObject json) throws LoaderException {
		try {

			// Create the Proxy Service configuration.
			ProxyService proxyService = createProxyService();

			// Set the Proxy Service parameters.
			if (json.has(ELEMENT_PARAMETERS)) {
				proxyService.setParameters(parseParameters(json
						.getJSONArray(ELEMENT_PARAMETERS)));
			}

			// Set the Clients for the Proxy Service.
			if (json.has(ELEMENT_CLIENTS)) {
				configClients(proxyService, json.getJSONArray(ELEMENT_CLIENTS));
			}

			// Return updated Scope.
			return proxyService;

		} catch (JsonException e) {
			throw new LoaderException(
					getScopeFacade(),
					"WAREWORK cannot parse the JSON Proxy Service configuration file because the following error is found: "
							+ e.getMessage(), e,
					LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Proxy Service.
	 * 
	 * @return A new and empty Proxy Service.<br>
	 * <br>
	 */
	protected ProxyService createProxyService() {
		return new ProxyService();
	}

	/**
	 * Creates a new Client.
	 * 
	 * @return A new and empty Client.<br>
	 * <br>
	 */
	protected Client createClient() {
		return new Client();
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configure Clients.
	 * 
	 * @param proxyService
	 *            Proxy Service configuration.<br>
	 * <br>
	 * @param jsonClients
	 *            JSON object that represents the Clients.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when working with the JSON array.<br>
	 * <br>
	 * @throws LoaderException
	 *             If there is an error when trying to parse the array of
	 *             Clients.<br>
	 * <br>
	 */
	private void configClients(ProxyService proxyService, JsonArray jsonClients)
			throws JsonException, LoaderException {

		// Count Clients.
		int maxServices = jsonClients.length();

		// Process every Client.
		for (int index = 0; index < maxServices; index++) {

			// Get a Client.
			Object object = jsonClients.get(index);

			// Set the Client.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the Client.
				JsonObject jsonClient = (JsonObject) object;

				// Create a new Client.
				Client client = createClient();

				// Set the name of the Client.
				if (jsonClient.has(ELEMENT_NAME)) {
					client.setName(jsonClient.getString(ELEMENT_NAME));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Proxy Service configuration file because there is a Client without name.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the class of the Service.
				if (jsonClient.has(ELEMENT_CONNECTOR)) {
					client.setConnector(jsonClient.getString(ELEMENT_CONNECTOR));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Proxy Service configuration file because there is a Client without a Connector.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the parameters of the Service.
				if (jsonClient.has(ELEMENT_PARAMETERS)) {
					client.setParameters(parseParameters(jsonClient
							.getJSONArray(ELEMENT_PARAMETERS)));
				}

				// Set the Service in the Scope.
				proxyService.setClient(client);

			}

		}

	}

}
