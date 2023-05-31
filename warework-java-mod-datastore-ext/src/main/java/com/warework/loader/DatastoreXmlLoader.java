package com.warework.loader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.warework.core.loader.AbstractSaxLoader;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.datastore.DatastoreExtensionServiceConstants;
import com.warework.service.datastore.model.ser.Datastore;
import com.warework.service.datastore.model.ser.DatastoreService;
import com.warework.service.datastore.model.ser.View;

/**
 * Loads an XML file that represents the configutation of a Data Store Service
 * and parse its content.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class DatastoreXmlLoader extends AbstractSaxLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// XML ELEMENT NAMES

	private static final String ELEMENT_DATASTORE = DatastoreExtensionServiceConstants.SERVICE;

	private static final String ELEMENT_DATASTORE_SERVICE = DatastoreExtensionServiceConstants.SERVICE
			+ StringL2Helper.CHARACTER_HYPHEN + CommonValueL2Constants.STRING_SERVICE;

	private static final String ELEMENT_VIEW = "view";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Target configuration object.
	private DatastoreService config;

	// Last element processed.
	private Object lastElement;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
			throws SAXException {
		if ((qName.equals(ELEMENT_DATASTORE_SERVICE)) || (localName.equals(ELEMENT_DATASTORE_SERVICE))) {

			// Create a new configuration object.
			config = new DatastoreService();

			// Reset the last element processed.
			lastElement = null;

		} else if ((qName.equals(ELEMENT_PARAMETER)) || (localName.equals(ELEMENT_PARAMETER))) {
			parseParameter(attributes);
		} else if ((qName.equals(ELEMENT_DATASTORE)) || (localName.equals(ELEMENT_DATASTORE))) {
			parseDatastore(attributes);
		} else if ((qName.equals(ELEMENT_VIEW)) || (localName.equals(ELEMENT_VIEW))) {
			parseView(attributes);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the object that represents the XML file.
	 * 
	 * @return Object that contains the information of the XML file.<br>
	 *         <br>
	 */
	protected Object getConfiguration() {
		return config;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Parse parameter.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the
	 *                      parameter.<br>
	 *                      <br>
	 */
	private void parseParameter(final Attributes attributes) throws SAXException {
		if (lastElement instanceof Datastore) {
			parseDatastoreParameter(attributes);
		} else if (lastElement instanceof View) {
			parseViewParameter(attributes);
		} else if (lastElement instanceof DatastoreService) {

			// Set an initialization parameter.
			config.setParameter(attributes.getValue(ELEMENT_NAME), attributes.getValue(ELEMENT_VALUE));

			// Skip execution.
			return;

		} else {
			throw new SAXException("WAREWORK cannot parse the XML file because it is not well formed.");
		}
	}

	/**
	 * Parse Data Store parameter.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 */
	private void parseDatastoreParameter(final Attributes attributes) {

		// Get the Data Store.
		final Datastore datastore = (Datastore) lastElement;

		// Get the parameters of the Data Store.
		List<com.warework.core.util.bean.Parameter> parameters = datastore.getParameters();

		// Create a new vector if no parameters exist.
		if (parameters == null) {
			parameters = new ArrayList<com.warework.core.util.bean.Parameter>();
		}

		// Create a new parameter.
		final Parameter parameter = new Parameter();

		// Set the name and value for the parameter.
		parameter.setName(attributes.getValue(ELEMENT_NAME));
		parameter.setValue(attributes.getValue(ELEMENT_VALUE));

		// Add the parameter in the collection.
		parameters.add(parameter);

		// Update the parameter list.
		datastore.setParameters(parameters);

	}

	/**
	 * Parse View parameter.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 */
	private void parseViewParameter(final Attributes attributes) {

		// Get the View.
		final View view = (View) lastElement;

		// Get the parameters of the View.
		List<com.warework.core.util.bean.Parameter> parameters = view.getParameters();

		// Create a new vector if no parameters exist.
		if (parameters == null) {
			parameters = new ArrayList<com.warework.core.util.bean.Parameter>();
		}

		// Create a new parameter.
		final Parameter parameter = new Parameter();

		// Set the name and value for the parameter.
		parameter.setName(attributes.getValue(ELEMENT_NAME));
		parameter.setValue(attributes.getValue(ELEMENT_VALUE));

		// Add the parameter in the collection.
		parameters.add(parameter);

		// Update the parameter list.
		view.setParameters(parameters);

	}

	/**
	 * Parse Data Store.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the Data
	 *                      Store.<br>
	 *                      <br>
	 */
	private void parseDatastore(final Attributes attributes) throws SAXException {

		// Create a new Data Store.
		final Datastore datastore = new Datastore();

		// Set the name of the Data Store.
		datastore.setName(attributes.getValue(ELEMENT_NAME));

		// Set the implementation class of the Connector.
		datastore.setConnector(attributes.getValue(ELEMENT_CONNECTOR));

		// Register a new Data Store.
		config.setClient(datastore);

		// Set the last element processed.
		lastElement = config.getClient(datastore.getName());

	}

	/**
	 * Parse View.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the View.<br>
	 *                      <br>
	 */
	private void parseView(final Attributes attributes) throws SAXException {

		// Create the View.
		final View view = new View();

		// Get the name of the View.
		view.setName(attributes.getValue(ELEMENT_NAME));

		// Get the implementation class of the View.
		view.setClazz(attributes.getValue(ELEMENT_CLASS));

		// Get the provider where to retrieve statements for the View.
		view.setStatementProvider(attributes.getValue(ELEMENT_PROVIDER));

		// Register the View for the Data Store.
		if (lastElement instanceof Datastore) {

			// Get the Data Store.
			final Datastore datastore = (Datastore) lastElement;

			// Register the View for the Data Store.
			config.setView(datastore.getName(), view);

			// Set the last element processed.
			lastElement = config.getView(datastore.getName());

		} else if (lastElement instanceof View) {

			// Get the View.
			final View lastView = (View) lastElement;

			// Register the View for the Data Store.
			config.setView(lastView.getDatastore().getName(), view);

			// Set the last element processed.
			lastElement = config.getView(lastView.getDatastore().getName());

		} else {
			throw new SAXException("WAREWORK cannot parse the XML file because it is not well formed.");
		}

	}

}
