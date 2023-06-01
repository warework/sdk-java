package com.warework.service.pool;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Pool Service.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class PoolServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "pool";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// OPERATION NAMES

	/**
	 * Gets a pooled object from a Pooler. Use the following parameters in order to
	 * invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: Name of the Pooler where to retrieve a
	 * pooled object. This parameter is mandatory and it must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Object</code> with an object from
	 * the pool of objects.
	 */
	public static final String OPERATION_NAME_GET_OBJECT = "get-object";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private PoolServiceConstants() {
		// DO NOTHING.
	}

}
