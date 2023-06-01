package com.warework.loader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.warework.core.loader.AbstractSaxLoader;
import com.warework.core.model.ser.Client;
import com.warework.core.model.ser.ProxyService;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.bean.ser.Parameter;

/**
 * Loads an XML file that represents the configutation of a Proxy Service and
 * parses its content.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ProxyServiceXmlLoader extends AbstractSaxLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant to identify <code>proxy-service</code> elements.
	 */
	private static final String ELEMENT_PROXY_SERVICE = CommonValueL2Constants.STRING_PROXY_SERVICE;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Target configuration object.
	private ProxyService config;

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
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ((qName.equals(ELEMENT_PROXY_SERVICE))
				|| (localName.equals(ELEMENT_PROXY_SERVICE))) {

			// Create a new configuration object.
			config = new ProxyService();

			// Reset the last element processed.
			lastElement = config;

		} else if ((qName.equals(ELEMENT_PARAMETER))
				|| (localName.equals(ELEMENT_PARAMETER))) {
			parseParameter(attributes);
		} else if ((qName.equals(ELEMENT_CLIENT))
				|| (localName.equals(ELEMENT_CLIENT))) {
			parseClient(attributes);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the object that represents the XML file.
	 * 
	 * @return Object that contains the information of the XML file.<br>
	 * <br>
	 */
	protected Object getConfiguration() {
		return config;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Parse parameters.
	 * 
	 * @param attributes
	 *            XML attributes.<br>
	 * <br>
	 * @throws SAXException
	 *             If there is an error when trying to parse the parameter.<br>
	 * <br>
	 */
	private void parseParameter(Attributes attributes) throws SAXException {

		// Get the parameters.
		List<com.warework.core.util.bean.Parameter> parameters = null;
		if (lastElement instanceof Client) {

			// Get the last element processed.
			Client client = (Client) lastElement;

			// Get the parameters.
			parameters = client.getParameters();

		} else if (lastElement instanceof ProxyService) {

			// Set an initialization parameter.
			config.setParameter(attributes.getValue(ELEMENT_NAME),
					attributes.getValue(ELEMENT_VALUE));

			// Skip execution.
			return;

		} else {
			throw new SAXException(
					"WAREWORK cannot parse the XML file because it is not well formed.");
		}

		// Create a new vector if no parameters exist.
		if (parameters == null) {
			parameters = new ArrayList<com.warework.core.util.bean.Parameter>();
		}

		// Create a new parameter.
		Parameter parameter = new Parameter();

		// Set the name and value for the parameter.
		parameter.setName(attributes.getValue(ELEMENT_NAME));
		parameter.setValue(attributes.getValue(ELEMENT_VALUE));

		// Add the parameter in the collection.
		parameters.add(parameter);

		// Get the last element processed.
		Client client = (Client) lastElement;

		// Update the parameter list.
		client.setParameters(parameters);

	}

	/**
	 * Parse Client.
	 * 
	 * @param attributes
	 *            XML attributes.<br>
	 * <br>
	 * @throws SAXException
	 *             If there is an error when trying to parse the Client.<br>
	 * <br>
	 */
	private void parseClient(Attributes attributes) throws SAXException {

		// Create a new Client.
		Client client = new Client();

		// Set the name of the client.
		client.setName(attributes.getValue(ELEMENT_NAME));

		// Set the implementation class of the connector.
		client.setConnector(attributes.getValue(ELEMENT_CONNECTOR));

		// Register a new client.
		config.setClient(client);

		// Set the last element processed.
		lastElement = config.getClient(client.getName());

	}

}
