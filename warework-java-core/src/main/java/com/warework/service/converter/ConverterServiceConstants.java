package com.warework.service.converter;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Converter Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ConverterServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "converter";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// OPERATION NAMES

	/**
	 * Transforms a given object into another one. Use the following parameters in
	 * order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name of the Client where to perform
	 * the transformation. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>source</code></b>: Object to transform.<br>
	 * </li>
	 * </ul>
	 * This operation returns a new <code>java.lang.Object</code> with the
	 * transformation of the source object.
	 */
	public static final String OPERATION_NAME_TRANSFORM = "transform";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies the object to transform.
	 */
	public static final String OPERATION_PARAMETER_SOURCE = "source";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private ConverterServiceConstants() {
		// DO NOTHING.
	}

}
