package com.warework.loader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Loads an XML file that represents the configutation of a Scope and parses its
 * content.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ServletXmlLoader extends ScopeXmlLoader {

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
			super.startElement(uri, localName, qName, attributes);
		}

		// Process provider element.
		if ((qName.equals(ELEMENT_PROVIDER)) || (localName.equals(ELEMENT_PROVIDER))) {
			super.startElement(uri, localName, qName, attributes);
		}

		// Process service element.
		if ((qName.equals(ELEMENT_SERVICE)) || (localName.equals(ELEMENT_SERVICE))) {
			super.startElement(uri, localName, qName, attributes);
		}

		// Process object element.
		if ((qName.equals(ELEMENT_OBJECT)) || (localName.equals(ELEMENT_OBJECT))) {
			super.startElement(uri, localName, qName, attributes);
		}

		// Process parameter element.
		if ((qName.equals(ELEMENT_PARAMETER)) || (localName.equals(ELEMENT_PARAMETER))) {
			super.startElement(uri, localName, qName, attributes);
		}

	}

}
