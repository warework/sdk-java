package com.warework.service.file.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.FileRef;
import com.warework.service.file.client.connector.LocalFileSystemConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * File Client that performs operations with the local file system.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class LocalFileSystemClient extends AbstractFileClient {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "local-fs-client";

	/**
	 * Operation parameter that specifies if the file or directory should be read
	 * only or writeable. Values for this parameter can be <code>true</code> (to
	 * make it read only) or <code>false</code> (to make it writeable) as boolean or
	 * string objects.
	 */
	public static final String OPERATION_OPTION_READ_ONLY = "read-only";

	/**
	 * Operation parameter that specifies the last modified date for a file or
	 * directory. Values for this parameter can be <code>java.util.Date</code> or
	 * <code>java.lang.String</code> (with date format 'yyyy/MM/dd HH:mm:ss')
	 * objects.
	 */
	public static final String OPERATION_OPTION_LAST_MODIFIED = "last-modified";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Writes the content of a file.
	 * 
	 * @param source  Source file to read. Input stream is not closed when operation
	 *                is successfully completed.<br>
	 *                <br>
	 * @param size    Bytes of the source file.<br>
	 *                <br>
	 * @param options Options to write the file. Check out the underlying File
	 *                Client to review which options it may accept. This argument is
	 *                not mandatory.<br>
	 *                <br>
	 * @param target  Where to copy the content of the source file in the target
	 *                File Client.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to write the
	 *                         file.<br>
	 *                         <br>
	 */
	public boolean write(final InputStream source, final long size, final Map<String, Object> options,
			final String target) throws ClientException {
		BufferedOutputStream buffer = null;
		try {

			/*
			 * As a general Java convention, streams provided by users should not be closed
			 * by invoked methods. As users create the streams, they have to decide what to
			 * do with them.
			 */

			// Validate options.
			validate(options);

			// Create target file.
			final File file = new File(updatePath(target));

			// Create buffered target stream.
			buffer = new BufferedOutputStream(new FileOutputStream(file));

			// Copy source stream bytes into target output stream.
			copy(source, buffer, getBufferSize(options), true);

			// Update file.
			update(file, options);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully created file '" + target + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot write file '" + target + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following I/O exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final SecurityException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot write file '" + source + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following security exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot close stream after writting file '" + target + "' in File Client '"
									+ getName() + "' at Service '" + getService().getName()
									+ "' because the following I/O exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}
	}

	/**
	 * Creates a new directory, including anynecessary but nonexistent parent
	 * directories. Note that if thisoperation fails it may have succeeded in
	 * creating some of the necessaryparent directories.
	 * 
	 * @param path    Directory to create.<br>
	 *                <br>
	 * @param options Options to create the directory in the Client. Check out the
	 *                underlying File Client to review which options it may accept.
	 *                This argument is not mandatory.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to create the
	 *                         directory.<br>
	 *                         <br>
	 */
	public boolean makeDir(final String path, final Map<String, Object> options) throws ClientException {
		try {

			// Get the object that points to the directory.
			final File target = new File(updatePath(path));

			// Validate that directory does not exists.
			if (target.exists()) {

				// Log message.
				getScopeFacade().warn("WAREWORK cannot create directory '" + path + "' in File Client '" + getName()
						+ "' at Service '" + getService().getName() + "' because directory already exists.");

				// Return operation result.
				return false;

			} else {

				// Create the directory.
				final boolean success = target.mkdirs();

				// Log a message.
				if (success) {

					// Update directory.
					update(target, options);

					// Log message.
					getScopeFacade().info("WAREWORK successfully created directory '" + path + "' in File Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

				} else {
					getScopeFacade().warn("WAREWORK cannot create directory '" + path + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName() + "'.");
				}

				// Return operation result.
				return success;

			}

		} catch (final SecurityException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot create directory '" + path + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following security exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Deletes a single file or directory.
	 * 
	 * @param path    File or directory to delete.<br>
	 *                <br>
	 * @param options Options to delete the file. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         file.<br>
	 *                         <br>
	 */
	public boolean delete(final String path, final Map<String, Object> options) throws ClientException {
		try {

			// Get resource.
			final File resource = new File(updatePath(path));

			// Delete file or directory if it exists.
			if (resource.exists()) {

				// Delete file or directory.
				final boolean success = resource.delete();

				// Log a message.
				if (success) {
					if (resource.isDirectory()) {
						getScopeFacade().info("WAREWORK successfully deleted directory '" + path + "' in File Client '"
								+ getName() + "' of Service '" + getService().getName() + "'.");
					} else {
						getScopeFacade().info("WAREWORK successfully deleted file '" + path + "' in File Client '"
								+ getName() + "' of Service '" + getService().getName() + "'.");
					}
				} else if (resource.isDirectory()) {
					getScopeFacade().warn("WAREWORK cannot delete directory '" + path + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName() + "'.");
				} else {
					getScopeFacade().warn("WAREWORK cannot delete file '" + path + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName() + "'.");
				}

				// Return operation result.
				return success;

			} else {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot delete file or directory '" + path + "' in File Client '"
						+ getName() + "' at Service '" + getService().getName()
						+ "' because it does not exists or cannot be found.");

				// Return operation result.
				return false;

			}

		} catch (final SecurityException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete file or directory '" + path + "' in Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following security problem: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Renames a file or directory.
	 * 
	 * @param source  File to change the name in the File Client.<br>
	 *                <br>
	 * @param newname New name for the source file.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to rename the file
	 *                         or directory.<br>
	 *                         <br>
	 */
	public boolean rename(final String source, final String newname) throws ClientException {
		try {

			// File (or directory) with old name.
			final File oldFile = new File(updatePath(source));

			// File (or directory) with new name.
			final File newFile = new File(updatePath(newname));
			if (newFile.exists()) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file '" + source + "' to '" + newname + "' in Client '" + getName()
								+ "' at Service '" + getService().getName() + "' because file '" + newname
								+ "' already exists.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Rename file or directory.
			final boolean success = oldFile.renameTo(newFile);

			// Log a message.
			if (success) {
				getScopeFacade().info("WAREWORK successfully renamed file or directory '" + source + "' to '" + newname
						+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");
			} else {
				getScopeFacade().warn("WAREWORK cannot rename file or directory '" + source + "' to '" + newname
						+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");
			}

			// Return operation result.
			return success;

		} catch (final SecurityException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot rename file or directory '" + source + "' to '" + newname + "' in File Client '"
							+ getName() + "' of Service '" + getService().getName()
							+ "' due to the following security problem: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Lists resources (files and directories) in a specific path.
	 * 
	 * @param path    Where to list the resources in the file storage.<br>
	 *                <br>
	 * @param options Options to list the files. This argument is not mandatory.<br>
	 *                <br>
	 * @param orderBy Criteria to sort the resources in the file storage. Use any of
	 *                the <code>FileServiceConstants.ORDER_BY_xyz</code> constants
	 *                to specify what to sort. Specific clients may accept custom
	 *                criteria.<br>
	 *                <br>
	 */
	public List<FileRef> list(final String path, final Map<String, Object> options, final String[] orderBy) {

		// Get the object that points to the directory.
		final File target = new File(updatePath(path));

		// Store file references here.
		final List<FileRef> output = new ArrayList<FileRef>();

		// List files
		if (target.isFile()) {

			// Wrap file.
			output.add(new FileRefFileImpl(this, target, orderBy));

			// Log a message.
			getScopeFacade().info("WAREWORK successfully found the file from '" + path + "' in File Client '"
					+ getName() + "' of Service '" + getService().getName() + "'.");

			// Return resources found.
			return output;

		} else {

			// List files and directories.
			final File[] files = target.listFiles();

			// Wrap files and directories.
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					output.add(new FileRefFileImpl(this, files[i], orderBy));
				}
			}

			// Log a message.
			getScopeFacade().info("WAREWORK successfully listed files and directories from '" + path
					+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");

			// Return resources found.
			return output;

		}

	}

	/**
	 * Gets a resource (file or directory) reference.
	 * 
	 * @param path    Path to the resource in the file storage.<br>
	 *                <br>
	 * @param options Options to find the file in the Client. Check out the
	 *                underlying File Client to review which options it may accept.
	 *                This argument is not mandatory.<br>
	 *                <br>
	 * @return A reference to the resource.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to find the
	 *                         resource.<br>
	 *                         <br>
	 */
	public FileRef find(final String path, final Map<String, Object> options) throws ClientException {

		// Get the object that points to the directory.
		final File target = new File(updatePath(path));

		// Validate file or directory exists.
		if (target.exists()) {

			// Log a message.
			if (target.isFile()) {
				getScopeFacade().info("WAREWORK successfully found file at '" + path + "' in File Client '" + getName()
						+ "' of Service '" + getService().getName() + "'.");
			} else {
				getScopeFacade().info("WAREWORK successfully found directory at '" + path + "' in File Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");
			}

			// Return file or directory reference.
			return new FileRefFileImpl(this, target, null);

		}

		// Log a message.
		getScopeFacade().info("WAREWORK cannot find file or directory at '" + path + "' in File Client '" + getName()
				+ "' of Service '" + getService().getName() + "'.");

		// At this point, no resources are found.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the character used to separate directories.
	 * 
	 * @return Directory character separator.<br>
	 *         <br>
	 */
	protected char getDirChar() {
		return File.separatorChar;
	}

	/**
	 * Specifies if Client supports directories.
	 * 
	 * @return Always <code>true</code>.<br>
	 *         <br>
	 */
	public boolean allowsDirectories() {
		return true;
	}

	/**
	 * Specifies if Client requires the number of bytes when <code>write</code>
	 * operation is invoked.
	 * 
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	public boolean requiresWriteLength() {
		return false;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a file or directory as an input stream.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @param log     Flag to log if operation is successfull.<br>
	 *                <br>
	 * @return Stream to read the file or directory or <code>null</code> if file or
	 *         directory does not exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	protected InputStream performRead(final String source, final Map<String, Object> options, final boolean log)
			throws ClientException {
		try {

			// Get resource. DO NOT VALIDATE FILE/DIRECTORY EXISTS. A FileNotFoundException
			// will be thrown in this case.
			final File resource = new File(updatePath(source));

			// Create input stream.
			final InputStream sourceStream = new FileInputStream(resource);

			// Log a message.
			if (log) {
				getScopeFacade().info("WAREWORK successfully loaded file or directory '" + source + "' in File Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");
			}

			// Return operation result.
			return new LengthInputStream(sourceStream, resource.length());

		} catch (final FileNotFoundException e) {

			// Log a message.
			getScopeFacade().warn("WAREWORK cannot load file or directory '" + source + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName() + "' because it does not exists.");

			// Nothing to return.
			return null;

		} catch (final SecurityException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file or directory '" + source + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following security exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the File Client.
	 */
	protected void close() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates options.
	 * 
	 * @param options Options to validate.<br>
	 *                <br>
	 * @throws ClientException
	 */
	protected void validate(final Map<String, Object> options) throws ClientException {

		// Validate common operation parameters.
		super.validate(options);

		// Validate specific operation parameters.
		if ((options != null) && (options.size() > 0)) {

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_ONLY)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_ONLY);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Boolean)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_ONLY
									+ "' option is not a boolean or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_LAST_MODIFIED)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_LAST_MODIFIED);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Date)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_LAST_MODIFIED
									+ "' option is not a string or a '" + Date.class.getName() + "' value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Add base path when required.
	 * 
	 * @param path Source path.<br>
	 *             <br>
	 * @return Updated path.<br>
	 *         <br>
	 */
	private String updatePath(final String path) {

		// Get path with base path.
		final String updated = (getConnector().toString(LocalFileSystemConnector.PARAMETER_BASE_PATH) == null) ? path
				: getConnector().toString(LocalFileSystemConnector.PARAMETER_BASE_PATH) + path;

		// Update path with unix style directory character.
		return (getConnector().isInitParameter(LocalFileSystemConnector.PARAMETER_PATH_TRANSFORM))
				? updated.replace('\\', CommonValueL2Constants.CHAR_FORWARD_SLASH)
				: updated;

	}

	/**
	 * Updates a file with a set of given options.
	 * 
	 * @param file    File to update.<br>
	 *                <br>
	 * @param options Options to execute.<br>
	 *                <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         file.<br>
	 *                         <br>
	 */
	private void update(final File file, final Map<String, Object> options) throws ClientException {
		if ((options != null) && (options.size() > 0)) {

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_ONLY)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_ONLY);

				// Validate option value.
				boolean succeeded = false;
				if (optionValue instanceof String) {

					// Get the string value of the option.
					final String option = (String) optionValue;

					// Validate option value.
					if (option.equalsIgnoreCase(CommonValueL2Constants.STRING_TRUE)) {
						if (file.canWrite()) {
							try {
								succeeded = file.setReadOnly();
							} catch (final SecurityException e) {
								throw new ClientException(getScopeFacade(),
										"WAREWORK cannot make file '" + file.getName() + "' read only in File Client '"
												+ getName() + "' at Service '" + getService().getName()
												+ "' because the following security exception is thrown: "
												+ e.getMessage(),
										e, LogServiceConstants.LOG_LEVEL_WARN);
							}
						} else {
							succeeded = true;
						}
					} else if (option.equalsIgnoreCase(CommonValueL2Constants.STRING_FALSE)) {
						if (file.canWrite()) {
							succeeded = true;
						} else {
							try {
								succeeded = file.setWritable(true);
							} catch (final SecurityException e) {
								throw new ClientException(getScopeFacade(),
										"WAREWORK cannot make file '" + file.getName() + "' read only in File Client '"
												+ getName() + "' at Service '" + getService().getName()
												+ "' because the following security exception is thrown: "
												+ e.getMessage(),
										e, LogServiceConstants.LOG_LEVEL_WARN);
							}
						}
					} else {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot make file '" + file.getName() + "' read only in File Client '"
										+ getName() + "' at Service '" + getService().getName()
										+ "' because given operation parameter '" + OPERATION_OPTION_READ_ONLY
										+ "' is not a boolean value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

				} else {
					if ((Boolean) optionValue) {
						if (file.canWrite()) {
							try {
								succeeded = file.setReadOnly();
							} catch (final SecurityException e) {
								throw new ClientException(getScopeFacade(),
										"WAREWORK cannot make file '" + file.getName() + "' read only in File Client '"
												+ getName() + "' at Service '" + getService().getName()
												+ "' because the following security exception is thrown: "
												+ e.getMessage(),
										e, LogServiceConstants.LOG_LEVEL_WARN);
							}
						} else {
							succeeded = true;
						}
					} else {
						if (file.canWrite()) {
							succeeded = true;
						} else {
							try {
								succeeded = file.setWritable(true);
							} catch (final SecurityException e) {
								throw new ClientException(getScopeFacade(),
										"WAREWORK cannot make file '" + file.getName() + "' read only in File Client '"
												+ getName() + "' at Service '" + getService().getName()
												+ "' because the following security exception is thrown: "
												+ e.getMessage(),
										e, LogServiceConstants.LOG_LEVEL_WARN);
							}
						}
					}
				}

				// Validate if operation succeeded.
				if (!succeeded) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot make file read only in File Client '" + getName() + "' at Service '"
									+ getService().getName()
									+ "' because the underlying file system reported an unknown error.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_LAST_MODIFIED)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_LAST_MODIFIED);

				// Get date option value.
				Date option = null;
				if (optionValue instanceof String) {
					try {
						option = DateL2Helper.toDateYYYYMMDDHHMMSS((String) optionValue, null);
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot set last modified date for file in File Client '" + getName()
										+ "' at Service '" + getService().getName()
										+ "' because given date does not follows pattern '"
										+ DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS
										+ "' or given value is not valid. Check out this exception: " + e.getMessage(),
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else {
					option = (Date) optionValue;
				}

				// Set last modified date.
				try {
					if (!file.setLastModified(option.getTime())) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot set last modified date for file in File Client '" + getName()
										+ "' at Service '" + getService().getName() + "'.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} catch (final SecurityException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot set last modified date for file in File Client '" + getName()
									+ "' at Service '" + getService().getName()
									+ "' because the following security exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}
	}

}
