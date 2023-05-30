package com.warework.loader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.warework.core.loader.AbstractSaxLoader;
import com.warework.core.model.ser.Scope;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.service.ServiceFacade;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.bean.ser.Parameter;
import com.warework.core.util.helper.ReflectionL2Helper;

/**
 * Loads an XML file that represents the configutation of a Scope and parses its
 * content.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ScopeXmlLoader extends AbstractSaxLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant to identify <code>context</code> elements.
	 */
	protected static final String ELEMENT_CONTEXT = CommonValueL2Constants.STRING_CONTEXT;

	/**
	 * Constant to identify <code>domain</code> elements.
	 */
	protected static final String ELEMENT_DOMAIN = CommonValueL2Constants.STRING_DOMAIN;

	/**
	 * Constant to identify <code>parent</code> elements.
	 */
	protected static final String ELEMENT_PARENT = CommonValueL2Constants.STRING_PARENT;

	/**
	 * Constant to identify <code>scope</code> elements.
	 */
	protected static final String ELEMENT_SCOPE = CommonValueL2Constants.STRING_SCOPE;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Target configuration object.
	private Scope config;

	// Previous processed Scopes.
	private List<Scope> previous = new ArrayList<Scope>();

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

		// Process scope element.
		if ((qName.equals(ELEMENT_SCOPE)) || (localName.equals(ELEMENT_SCOPE))) {

			// Create a new configuration object.
			config = new Scope(attributes.getValue(ELEMENT_NAME));

			// Reset the last element processed.
			lastElement = null;

		}

		// Process parent element.
		if ((qName.equals(ELEMENT_PARENT)) || (localName.equals(ELEMENT_PARENT))) {

			// Set the parent for the scope.
			config.setParent(attributes.getValue(ELEMENT_PARENT));

			// Reset the last element processed.
			lastElement = null;

		}

		// Process context element.
		if ((qName.equals(ELEMENT_CONTEXT)) || (localName.equals(ELEMENT_CONTEXT))) {

			// Backup previous Scope.
			previous.add(config);

			// Reset the last element processed.
			lastElement = null;

		}

		// Process parameter element.
		if ((qName.equals(ELEMENT_PARAMETER)) || (localName.equals(ELEMENT_PARAMETER))) {
			parseParameter(attributes);
		}

		// Process provider element.
		if ((qName.equals(ELEMENT_PROVIDER)) || (localName.equals(ELEMENT_PROVIDER))) {
			parseProvider(attributes);
		}

		// Process service element.
		if ((qName.equals(ELEMENT_SERVICE)) || (localName.equals(ELEMENT_SERVICE))) {
			parseService(attributes);
		}

		// Process object element.
		if ((qName.equals(ELEMENT_OBJECT)) || (localName.equals(ELEMENT_OBJECT))) {

			// Get the name of the object.
			final String name = attributes.getValue(ELEMENT_NAME);

			// Get the Provider.
			final String provider = attributes.getValue(ELEMENT_PROVIDER);

			// Get the object of the Provider.
			final String object = attributes.getValue(ELEMENT_OBJECT);

			// Register a new object.
			config.setObjectReference(name, provider, object);

			// Set the last element processed.
			lastElement = config.getObjectReference(name);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void endElement(final String uri, final String localName, final String qName) {

		// Validate parent.
		final boolean parent = ((qName.equals(ELEMENT_PARENT)) || (localName.equals(ELEMENT_PARENT)));

		// Validate domain.
		final boolean domain = ((qName.equals(ELEMENT_DOMAIN)) || (localName.equals(ELEMENT_DOMAIN)));

		// Validate context.
		final boolean context = ((qName.equals(ELEMENT_CONTEXT)) || (localName.equals(ELEMENT_CONTEXT)));

		// Validate parent, domain or context.
		if (parent || domain || context) {

			// Restore main Scope.
			config = (Scope) previous.get(previous.size() - 1);

			// Remove the last configuration.
			previous.remove(previous.size() - 1);

		} else if (((qName.equals(ELEMENT_SCOPE)) || (localName.equals(ELEMENT_SCOPE))) && (previous.size() > 0)) {

			/* At this point, we are closing the <scope> tag of a scope from the context. */

			// Get the domain scope.
			Scope domainScope = (Scope) previous.get(previous.size() - 1);

			// Set the context scope.
			domainScope.setContextScope(config);

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.loader.AbstractSAXLoader#getConfiguration()
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
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the
	 *                      parameter.<br>
	 *                      <br>
	 */
	private void parseParameter(final Attributes attributes) {

		// Get the parameters.
		List<com.warework.core.util.bean.Parameter> parameters = null;
		if (lastElement instanceof com.warework.core.model.Provider) {

			// Get the last element processed.
			final com.warework.core.model.Provider provider = (com.warework.core.model.Provider) lastElement;

			// Get the parameters.
			parameters = provider.getParameters();

		} else if (lastElement instanceof com.warework.core.model.Service) {

			// Get the last element processed.
			final com.warework.core.model.Service service = (com.warework.core.model.Service) lastElement;

			// Get the parameters.
			parameters = service.getParameters();

		} else {

			// Set an initialization parameter.
			config.setInitParameter(attributes.getValue(ELEMENT_NAME), attributes.getValue(ELEMENT_VALUE));

			// Skip execution.
			return;

		}

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
		if (lastElement instanceof com.warework.core.model.Provider) {

			// Get the last element processed.
			final com.warework.core.model.Provider provider = (com.warework.core.model.Provider) lastElement;

			// Update the parameter list.
			provider.setParameters(parameters);

		} else {

			// Get the last element processed.
			final com.warework.core.model.Service service = (com.warework.core.model.Service) lastElement;

			// Update the parameter list.
			service.setParameters(parameters);

		}

	}

	/**
	 * Parse a Provider.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the
	 *                      Provider.<br>
	 *                      <br>
	 */
	private void parseProvider(final Attributes attributes) throws SAXException {

		// Get the name of the Provider.
		final String name = attributes.getValue(ELEMENT_NAME);

		// Get the implementation class of the Provider.
		final String providerClass = attributes.getValue(ELEMENT_CLASS);

		// Get the type of the Provider.
		Class<? extends AbstractProvider> providerType = null;
		if ((providerClass != null) && (!providerClass.equals(CommonValueL2Constants.STRING_EMPTY))) {
			try {
				providerType = ReflectionL2Helper.getProviderType(providerClass);
			} catch (final ClassNotFoundException e) {
				throw new SAXException(e);
			}
		} else {
			throw new SAXException(
					"WAREWORK cannot parse the XML file because there is a Provider which class is not defined.");
		}

		// Register a new Provider.
		config.setProvider(name, providerType, null);

		// Set the last element processed.
		lastElement = config.getProvider(name);

	}

	/**
	 * Parse a Service.
	 * 
	 * @param attributes XML attributes.<br>
	 *                   <br>
	 * @throws SAXException If there is an error when trying to parse the
	 *                      Service.<br>
	 *                      <br>
	 */
	private void parseService(final Attributes attributes) throws SAXException {

		// Get the name of the Service.
		final String name = attributes.getValue(ELEMENT_NAME);

		// Get the implementation class of the Service.
		final String serviceClass = attributes.getValue(ELEMENT_CLASS);

		// Get the type of the Service.
		Class<? extends ServiceFacade> serviceType = null;
		if ((serviceClass != null) && (!serviceClass.equals(CommonValueL2Constants.STRING_EMPTY))) {
			try {
				serviceType = ReflectionL2Helper.getServiceType(serviceClass);
			} catch (final ClassNotFoundException e) {
				throw new SAXException(e);
			}
		} else {
			throw new SAXException(
					"WAREWORK cannot parse the XML file because there is a Service which class is not defined.");
		}

		// Register a new Service.
		config.setService(name, serviceType, null, null);

		// Set the last element processed.
		lastElement = config.getService(name);

	}

}
