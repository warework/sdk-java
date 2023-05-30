package com.warework.core.provider;

import com.warework.core.util.helper.ResourceL2Helper;

/**
 * Parses an XML file from a given directory and returns the object that
 * represents the XML file.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractSaxProvider extends AbstractResourceProvider {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the XML file extension.
	 * 
	 * @return Word <code>xml</code>.<br>
	 * <br>
	 */
	protected String getFileExtension() {
		return ResourceL2Helper.FILE_EXTENSION_XML;
	}

}
