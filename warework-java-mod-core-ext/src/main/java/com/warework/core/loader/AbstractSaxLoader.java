package com.warework.core.loader;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.DataStructureL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Loads an XML file and parses its content.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractSaxLoader extends DefaultHandler implements LoaderFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// XML ELEMENT NAMES

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_CLASS = CommonValueL2Constants.STRING_CLASS;

	/**
	 * Constant to identify <code>client</code> elements.
	 */
	protected static final String ELEMENT_CLIENT = CommonValueL2Constants.STRING_CLIENT;

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_CONNECTOR = CommonValueL2Constants.STRING_CONNECTOR;

	/**
	 * Constant to identify <code>class</code> elements.
	 */
	protected static final String ELEMENT_NAME = CommonValueL2Constants.STRING_NAME;

	/**
	 * Constant to identify <code>object</code> elements.
	 */
	protected static final String ELEMENT_OBJECT = CommonValueL2Constants.STRING_OBJECT;

	/**
	 * Constant to identify <code>parameter</code> elements.
	 */
	protected static final String ELEMENT_PARAMETER = CommonValueL2Constants.STRING_PARAMETER;

	/**
	 * Constant to identify <code>provider</code> elements.
	 */
	protected static final String ELEMENT_PROVIDER = CommonValueL2Constants.STRING_PROVIDER;

	/**
	 * Constant to identify <code>service</code> elements.
	 */
	protected static final String ELEMENT_SERVICE = CommonValueL2Constants.STRING_SERVICE;

	/**
	 * Constant to identify <code>type</code> elements.
	 */
	protected static final String ELEMENT_TYPE = CommonValueL2Constants.STRING_TYPE;

	/**
	 * Constant to identify <code>value</code> elements.
	 */
	protected static final String ELEMENT_VALUE = CommonValueL2Constants.STRING_VALUE;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope where this Service belongs to.
	private ScopeFacade scope;

	// Initialization parameters (as string values) for this Loader.
	private Map<String, Object> parameters;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the object that represents the XML file.
	 * 
	 * @return Object that contains the information of the XML file.<br>
	 *         <br>
	 */
	protected abstract Object getConfiguration();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

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
	public Object load(final ScopeFacade scope, final Map<String, Object> parameters) throws LoaderException {

		// Set the scope.
		this.scope = scope;

		// Set the initialization parameters.
		this.parameters = parameters;

		// Gets the stream that points to the XML file.
		final InputStream resource = new ResourceLoader(getScopeFacade()).getResource(
				getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET));

		// Create a parser for the XML file.
		SAXParser parser = null;
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
		} catch (final Exception e) {
			throw new LoaderException(scope,
					"WAREWORK cannot load the XML configuration file because the creation of the XML parser generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the configuration.
		Object configuration = null;
		if (resource != null) {

			// Parse the XML file.
			try {
				parser.parse(resource, this);
			} catch (final Exception e) {
				throw new LoaderException(scope,
						"WAREWORK cannot parse the XML configuration file because the parser generated the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the configuration.
			configuration = getConfiguration();

		}

		// Return the configuration.
		return configuration;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Loader belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if no one
	 *         exist.<br>
	 *         <br>
	 */
	protected final Enumeration<String> getInitParameterNames() {
		return (parameters == null) ? null : DataStructureL2Helper.toEnumeration(parameters.keySet());
	}

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	protected final Object getInitParameter(final String name) {

		// Retrieve the value only if initialization parameters is not empty.
		if (parameters != null) {
			return parameters.get(name);
		}

		// At this point, nothing to return.
		return null;

	}

}
