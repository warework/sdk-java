package com.warework.service.datastore;

import java.util.ArrayList;
import java.util.List;

import com.warework.core.loader.AbstractJsonLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.util.json.JsonArray;
import com.warework.core.util.json.JsonException;
import com.warework.core.util.json.JsonObject;
import com.warework.service.datastore.model.Datastore;
import com.warework.service.datastore.model.DatastoreService;
import com.warework.service.datastore.model.View;
import com.warework.service.log.LogServiceConstants;

/**
 * Parses a JSON object with the configutation of a Proxy Service.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class DatastoreClasspathJsonLoader extends AbstractJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// ELEMENTS

	private static final String ELEMENT_DATASTORES = "datastores";

	private static final String ELEMENT_VIEW = "view";

	private static final String ELEMENT_VIEWS = "views";

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
			DatastoreService datastoreService = createDatastoreService();

			// Set the Proxy Service parameters.
			if (json.has(ELEMENT_PARAMETERS)) {
				datastoreService.setParameters(parseParameters(json
						.getJSONArray(ELEMENT_PARAMETERS)));
			}

			// Set the Clients for the Proxy Service.
			if (json.has(ELEMENT_DATASTORES)) {
				configDatastores(datastoreService,
						json.getJSONArray(ELEMENT_DATASTORES));
			}

			// Return updated Scope.
			return datastoreService;

		} catch (JsonException e) {
			throw new LoaderException(
					getScopeFacade(),
					"WAREWORK cannot parse the JSON Data Store Service configuration file because the following error is found: "
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
	protected DatastoreService createDatastoreService() {
		return new DatastoreService();
	}

	/**
	 * Creates a new Data Store.
	 * 
	 * @return A new and empty Data Store.<br>
	 * <br>
	 */
	protected Datastore createDatastore() {
		return new Datastore();
	}

	/**
	 * Creates a new View.
	 * 
	 * @return A new and empty View.<br>
	 * <br>
	 */
	protected View createView() {
		return new View();
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configure Data Stores.
	 * 
	 * @param datastoreService
	 *            Data Store Service configuration.<br>
	 * <br>
	 * @param jsonDatastores
	 *            JSON object that represents the Data Stores.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to parse the JSON.<br>
	 * <br>
	 * @throws LoaderException
	 *             If there is an error when trying to parse the JSON.<br>
	 * <br>
	 */
	private void configDatastores(DatastoreService datastoreService,
			JsonArray jsonDatastores) throws JsonException, LoaderException {

		// Count Data Stores.
		int maxDatastores = jsonDatastores.length();

		// Process every Data Store.
		for (int index = 0; index < maxDatastores; index++) {

			// Get a Data Store.
			Object object = jsonDatastores.get(index);

			// Set the Data Store.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the Data Store.
				JsonObject jsonDatastore = (JsonObject) object;

				// Create a new Data Store.
				Datastore datastore = createDatastore();

				// Set the name of the Data Store.
				if (jsonDatastore.has(ELEMENT_NAME)) {
					datastore.setName(jsonDatastore.getString(ELEMENT_NAME));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Data Store Service configuration file because there is a Data Store without name.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the Connector.
				if (jsonDatastore.has(ELEMENT_CONNECTOR)) {
					datastore.setConnector(jsonDatastore
							.getString(ELEMENT_CONNECTOR));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Data Store Service configuration file because there is a Data Store without a Connector.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the parameters of the Data Store.
				if (jsonDatastore.has(ELEMENT_PARAMETERS)) {
					datastore.setParameters(parseParameters(jsonDatastore
							.getJSONArray(ELEMENT_PARAMETERS)));
				}

				// Set the Views of the Data Store.
				if (jsonDatastore.has(ELEMENT_VIEWS)) {
					datastore.setViews(parseViews(jsonDatastore
							.getJSONArray(ELEMENT_VIEWS)));
				}

				// Set the Data Store in the Service.
				datastoreService.setClient(datastore);

			}

		}

	}

	/**
	 * Configure Views.
	 * 
	 * @param jsonViews
	 *            Views configuration.<br>
	 * <br>
	 * @return Collection of Views.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to parse the JSON.<br>
	 * <br>
	 * @throws LoaderException
	 *             If there is an error when trying to parse the JSON.<br>
	 * <br>
	 */
	private List<View> parseViews(JsonArray jsonViews) throws JsonException,
			LoaderException {

		// Count Views.
		int maxViews = jsonViews.length();

		// Create a collection for the Views.
		List<View> views = new ArrayList<View>();

		// Process every View.
		for (int index = 0; index < maxViews; index++) {

			// Get a View.
			Object object = jsonViews.get(index);

			// Set the View.
			if (object instanceof JsonObject) {

				// Get the JSON object that represents the View.
				JsonObject jsonView = (JsonObject) object;

				// Create a new View.
				View view = createView();

				// Set the name of the View.
				if (jsonView.has(ELEMENT_NAME)) {
					view.setName(jsonView.getString(ELEMENT_NAME));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Data Store Service configuration file because there is a View without name.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the class of the View.
				if (jsonView.has(ELEMENT_VIEW)) {
					view.setClazz(jsonView.getString(ELEMENT_VIEW));
				} else {
					throw new LoaderException(
							getScopeFacade(),
							"WAREWORK cannot parse the JSON Data Store Service configuration file because there is a View without type/class (check out '"
									+ ELEMENT_VIEW + "' attribute).", null,
							LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Set the Provider of the View.
				if (jsonView.has(ELEMENT_PROVIDER)) {
					view.setStatementProvider(jsonView
							.getString(ELEMENT_PROVIDER));
				}

				// Set the parameters of the View.
				if (jsonView.has(ELEMENT_PARAMETERS)) {
					view.setParameters(parseParameters(jsonView
							.getJSONArray(ELEMENT_PARAMETERS)));
				}

				// Set the View.
				views.add(view);

			}

		}

		// Return the collection of Views.
		return views;

	}

}
