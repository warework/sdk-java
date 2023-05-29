package com.warework.core.loader;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ResourceL1Helper;

/**
 * Manager to load resources.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractResourceManager {

	// ///////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////

	// URL PROTOCOLS

	/**
	 * <code>JAR</code> protocol for URLs.
	 */
	protected static final String URL_PROTOCOL_JAR = ResourceL1Helper.FILE_EXTENSION_JAR;

	/**
	 * <code>FILE</code> protocol for URLs.
	 */
	protected static final String URL_PROTOCOL_FILE = "file";

	/**
	 * <code>VFS</code> protocol for URLs.
	 */
	protected static final String URL_PROTOCOL_VFS = "vfs";

	// METHOD NAMES

	/**
	 * Method name <code>decode</code>.
	 */
	protected static final String METHOD_NAME_DECODE = "decode";

	// ENCODING TYPES

	/**
	 * Encoding type <code>utf-8</code>.
	 */
	protected static final String ENCODING_UTF8 = CommonValueL1Constants.ENCODING_TYPE_UTF8;

	// RESOURCES

	/**
	 * <code>.class</code> file extension.
	 */
	protected static final String FILE_EXTENSION_CLASS = CommonValueL1Constants.STRING_CLASS;

	// ///////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////

	// Scope.
	private ScopeFacade scope;

	// ///////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////

	/**
	 * Creates the helper to load resources.
	 * 
	 * @param scope Scope where this helper belongs to.<br>
	 *              <br>
	 */
	public AbstractResourceManager(final ScopeFacade scope) {
		this.scope = scope;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Provider belongs to.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

}
