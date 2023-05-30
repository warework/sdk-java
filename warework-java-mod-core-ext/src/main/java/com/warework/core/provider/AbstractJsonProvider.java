package com.warework.core.provider;

import com.warework.core.util.helper.ResourceL2Helper;

/**
 * Parses a JSON file from a given directory and returns the object that
 * represents the JSON file.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class AbstractJsonProvider extends AbstractResourceProvider {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the JSON file extension.
	 * 
	 * @return Word <code>json</code>.<br>
	 * <br>
	 */
	protected String getFileExtension() {
		return ResourceL2Helper.FILE_EXTENSION_JSON;
	}

}
