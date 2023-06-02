package com.warework.service.file;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.file.client.AbstractFileClient;

/**
 * Constants for the File Service.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class FileServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "file";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// DIRECTORY CHARACTER SEPARATOR

	/**
	 * Unix-style forward slash separators in paths.
	 */
	public static final String DIRECTORY_SEPARATOR_UNIX_STYLE = ResourceL1Helper.DIRECTORY_SEPARATOR;

	/**
	 * Windows-style back slash separators in paths.
	 */
	public static final String DIRECTORY_SEPARATOR_WINDOWS_STYLE = "\\";

	// ORDER BY

	/**
	 * Sorts the files and directories of a query by pathname in ascending order.
	 */
	public static final String ORDER_BY_PATH_ASCENDING = "path-asc";

	/**
	 * Sorts the files and directories of a query by pathname in descending order.
	 */
	public static final String ORDER_BY_PATH_DESCENDING = "path-desc";

	/**
	 * Sorts the files and directories of a query by file size in ascending order.
	 */
	public static final String ORDER_BY_SIZE_ASCENDING = "size-asc";

	/**
	 * Sorts the files and directories of a query by file size in descending order.
	 */
	public static final String ORDER_BY_SIZE_DESCENDING = "size-desc";

	/**
	 * Sorts the files and directories of a query by last modified attribute in
	 * ascending order.
	 */
	public static final String ORDER_BY_LAST_MODIFIED_ASCENDING = "last-modified-asc";

	/**
	 * Sorts the files and directories of a query by last modified attribute in
	 * descending order.
	 */
	public static final String ORDER_BY_LAST_MODIFIED_DESCENDING = "last-modified-desc";

	// RESPONSE ARRAY INDEX

	/**
	 * Response array index for files found.
	 */
	public static final int RESPONSE_INDEX_FILES_FOUND = 0;

	/**
	 * Response array index for directories found.
	 */
	public static final int RESPONSE_INDEX_DIRECTORIES_FOUND = 1;

	/**
	 * Response array index for files copied.
	 */
	public static final int RESPONSE_INDEX_FILES_COPIED = 2;

	/**
	 * Response array index for directories copied.
	 */
	public static final int RESPONSE_INDEX_DIRECTORIES_COPIED = 3;

	// OPTIONS

	/**
	 * Operation option to process files and/or directories recusively.
	 */
	public static final Map<String, Object> OPERATION_OPTION_PROCESS_RECURSIVELY = createOptionProcessRecursively();

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private FileServiceConstants() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates options to process files and/or directories recusively.
	 * 
	 * @return Options to process files and/or directories recusively.<br>
	 *         <br>
	 */
	private static Map<String, Object> createOptionProcessRecursively() {

		// Configure options.
		final Map<String, Object> options = new HashMap<String, Object>();
		{
			options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
		}

		// Return options.
		return options;

	}

}
