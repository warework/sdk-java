package com.warework.service.file.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.FileRef;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.connector.AbstractFileClientConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * Defines common operations for File Clients.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractFileClient extends AbstractClient implements FileClientFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// OPERATION OPTIONS

	/**
	 * Operation parameter that specifies the size for the buffer to use in
	 * <code>read</code> and <code>write</code> operations. This parameter must be a
	 * string or an integer value with the number of bytes for the buffer. If this
	 * parameter is not provided then it defaults to
	 * <code>AbstractFileClient.DEFAULT_BUFFER_SIZE</code>.
	 */
	public static final String OPERATION_OPTION_BUFFER_SIZE = AbstractFileClientConnector.PARAMETER_BUFFER_SIZE;

	/**
	 * Operation parameter that specifies the charset encoding to use when reading
	 * and writting text files (example: UTF-8). If it is not specified then default
	 * system charset is used. Please bear in mind that the number of bytes taken to
	 * represent the string depends entirely on which encoding you use to turn it
	 * into bytes, so target size of the text file to create will depend on it.<br>
	 * <br>
	 * This option must be a string value.
	 */
	public static final String OPERATION_OPTION_CHARSET_NAME = "charset-name";

	/**
	 * Operation parameter that specifies to continue even if minor (non exception
	 * related) failure is found. It's commonly used in copy operations when
	 * multiple files are copied (to specify to continue copying files even if one
	 * file cannot be copied).<br>
	 * <br>
	 * This option must specify a string or a boolean with values <code>true</code>
	 * or <code>false</code>. By default it is <code>false</code>.
	 */
	public static final String OPERATION_OPTION_CONTINUE_ON_FAILURE = "continue-on-failure";

	/**
	 * Operation parameter that specifies if files and directories that exist in a
	 * directory should be processed recursively.<br>
	 * <br>
	 * This option must specify a string or a boolean with values <code>true</code>
	 * or <code>false</code>. By default it is <code>false</code>.
	 */
	public static final String OPERATION_OPTION_PROCESS_RECURSIVELY = "process-recursively";

	/**
	 * Operation parameter that specifies the tree form structure to return.
	 * <code>false</code> will create a normal structure form and <code>true</code>
	 * a reverse tree.<br>
	 * <br>
	 * This option must specify a string or a boolean with values <code>true</code>
	 * or <code>false</code>. By default it is <code>false</code>.
	 */
	public static final String OPERATION_OPTION_REVERSE_TREE = "reverse-tree";

	// CLIENT INFO

	/**
	 * Client info parameter that specifies the base path used in the File Client.
	 */
	public static final String CLIENT_INFO_BASE_PATH = "base-path";

	/**
	 * Client info parameter that specifies if File Client allows working with
	 * directories.
	 */
	public static final String CLIENT_INFO_ALLOWS_DIRECTORIES = "allows-directories";

	/**
	 * Client info parameter that specifies if File Client requires to specify the
	 * length of the file in write operations.
	 */
	public static final String CLIENT_INFO_REQUIRES_WRITE_LENGTH = "requires-write-length";

	/**
	 * Client info parameter that specifies if File Client requires to specify the
	 * length of the file in write operations.
	 */
	public static final String CLIENT_INFO_DIRECTORY_CHARACTER_SEPARATOR = "directory-character-separator";

	// SHARED CONSTANTS

	/**
	 * Default buffer size in bytes: 8 KB.
	 */
	public static final int DEFAULT_BUFFER_SIZE = CommonValueL1Constants.DEFAULT_BUFFER_SIZE;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
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
	 * @return Stream to read the file or directory or <code>null</code> if file
	 *         does not exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	protected abstract InputStream performRead(final String source, final Map<String, Object> options,
			final boolean log) throws ClientException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
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
	 * @return Stream to read the file or directory or <code>null</code> if file
	 *         does not exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	public InputStream read(final String source, final Map<String, Object> options) throws ClientException {
		return performRead(source, options, true);
	}

	/**
	 * Reads the content of a file.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. This argument is not mandatory.<br>
	 *                <br>
	 * @param target  Where to copy the content of the file. Output stream is not
	 *                closed when operation is successfully completed.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	public boolean read(final String source, final Map<String, Object> options, final OutputStream target)
			throws ClientException {

		// Resource to read.
		InputStream stream = null;

		// Read the content of a file.
		try {

			// Validate options.
			validate(options);

			// Get input stream.
			stream = performRead(source, options, false);

			// Load file or directory.
			if (stream == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot load file or directory '" + source + "' in File Client '"
						+ getName() + "' of Service '" + getService().getName() + "' because it does not exists.");

				// Nothing to return.
				return false;

			} else {

				// Create buffered source stream.
				stream = new BufferedInputStream(stream);

				// Copy source stream bytes into target output stream.
				copy(stream, target, getBufferSize(options), true);

				// Log a message.
				getScopeFacade().info("WAREWORK successfully loaded file or directory '" + source + "' in File Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");

				// Return operation result.
				return true;

			}

		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file or directory '" + source + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following I/O exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot close the connection after reading file or directory '" + source
									+ "' in File Client '" + getName() + "' at Service '" + getService().getName()
									+ "' because the following I/O exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}

	}

	/**
	 * Deletes multiple files or directories.
	 * 
	 * @param resources Files or directories to delete.<br>
	 *                  <br>
	 * @param options   Options to delete the file. Check out the underlying File
	 *                  Client to review which options it may accept. This argument
	 *                  is not mandatory.<br>
	 *                  <br>
	 * @return <code>true</code> if all files and or directories were deleted,
	 *         <code>false</code> if not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         files.<br>
	 *                         <br>
	 */
	public boolean delete(final Collection<FileRef> resources, final Map<String, Object> options)
			throws ClientException {

		// Flag to know if all files or directorios were deleted.
		boolean success = true;

		// Delete each file or directory form the collection.
		for (final Iterator<FileRef> iterator = resources.iterator(); iterator.hasNext();) {
			if (!delete(iterator.next().getPath(), options)) {
				success = false;
			}
		}

		// Return if all files and/or directories were deleted.
		return success;

	}

	/**
	 * Renames a file.
	 * 
	 * @param source  File to change the name in the File Client.<br>
	 *                <br>
	 * @param newname New name for the source file.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         files.<br>
	 *                         <br>
	 */
	public boolean rename(final String source, final String newname) throws ClientException {

		// Load source file or directory.
		final InputStream input = read(source, null);

		// Validate file or directory exists.
		if (input == null) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot rename file or directory '" + source + "' in File Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because resource does not exists or cannot be found.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			try {

				// Copy file or directory with length.
				if (input instanceof LengthInputStream) {

					// Get source stream.
					final LengthInputStream stream = (LengthInputStream) input;

					// Write file or directory into target.
					if (stream.getLength() >= 0) {
						if (write(stream, stream.getLength(), null, newname)) {

							// Delete source file or diretory.
							final boolean success = delete(source, null);

							// Log message when operation is successfull.
							if (success) {
								getScopeFacade().info("WAREWORK successfully renamed file or directory '" + source
										+ "' to '" + newname + "' in File Client '" + getName() + "' of Service '"
										+ getService().getName() + "'.");
							}

							// Return operation result.
							return success;

						} else {

							// Log message.
							getScopeFacade().warn("WAREWORK cannot rename file or directory '" + source
									+ "' in File Client '" + getName() + "' at Service '" + getService().getName()
									+ "' because target resource cannot be created.");

							// Return operation result.
							return false;

						}
					}

				}

				// Copy file without file length.
				if (requiresWriteLength()) {
					throw new ClientException(getScopeFacade(), "WAREWORK cannot natively rename file or directory '"
							+ source + "' in File Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because file size is unknown and it's required by '" + getName()
							+ "' to be known in advance. To rename this file or directory you'll need to make a copy it in a temporary storage using 'read' and 'write' methods separately.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				} else if (write(input, -1, null, newname)) {

					// Delete source file or diretory.
					final boolean success = delete(source, null);

					// Log message when operation is successfull.
					if (success) {
						getScopeFacade().info("WAREWORK successfully renamed file or directory '" + source + "' to '"
								+ newname + "' in File Client '" + getName() + "' of Service '" + getService().getName()
								+ "'.");
					} else {
						getScopeFacade().warn("WAREWORK cannot rename file or directory '" + source + "' to '" + newname
								+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");
					}

					// Return operation result.
					return success;

				} else {

					// Log message.
					getScopeFacade().warn("WAREWORK cannot rename file or directory '" + source + "' in File Client '"
							+ getName() + "' at Service '" + getService().getName()
							+ "' because target resource cannot be created.");

					// Return operation result.
					return false;

				}

			} finally {
				try {
					input.close();
				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot rename file or directory '" + source + "' in File Client '" + getName()
									+ "' at Service '" + getService().getName()
									+ "' because the following I/O exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
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

		// Get the resources.
		final List<FileRef> resources = list(getPathDirectories(path), options, null);

		// Find resource.
		if (resources != null) {
			for (final Iterator<FileRef> iterator = resources.iterator(); iterator.hasNext();) {

				// Get one resource found.
				final FileRef fileRef = iterator.next();

				// Validate it is the resource we are looking for.
				if (fileRef.getPath().equals(path)) {
					return fileRef;
				}

			}
		}

		// At this point, no resources are found.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the base path defined for this Client.<br>
	 * <br>
	 * This method will return the value specified for parameter
	 * <code>AbstractFileClientConnector.PARAMETER_BASE_PATH</code> when Client was
	 * created.
	 * 
	 * @return Base path to append before any given path and for every operation to
	 *         perform, or <code>null</code> if no base path was defined.<br>
	 *         <br>
	 */
	public final String getBasePath() {
		return (getConnector().getInitParameter(AbstractFileClientConnector.PARAMETER_BASE_PATH) == null) ? null
				: getConnector().toString(AbstractFileClientConnector.PARAMETER_BASE_PATH);
	}

	/**
	 * Creates the specific sysntax to interact with a directory.
	 * 
	 * @param name Name of the directory without the directory character separator
	 *             ('/' or '\').<br>
	 *             <br>
	 * @return Directory name as a path, with the character used to separate
	 *         directories.<br>
	 *         <br>
	 */
	public String toDirectory(final String name) {
		return getDirChar() + name;
	}

	/**
	 * Extracts from a given path the name of each directory.
	 * 
	 * @param path Path where to retrieve the directory names.<br>
	 *             <br>
	 * @return Directory names or <code>null</code> if no directories exist.<br>
	 *         <br>
	 */
	public final List<String> toPathChain(final String path) {

		// Setup directories chain.
		final List<String> chain = new ArrayList<String>();

		// Find directory names.
		toPathChain(chain, path);

		// Setup response.
		return (chain.size() > 0) ? chain : null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the attributes that define specific features of the Client. This method
	 * should be overriden to return the attributes.
	 * 
	 * @return Attributes of the Client.<br>
	 *         <br>
	 */
	protected Map<String, Object> getClientInfo() {

		// Setup info.
		final Map<String, Object> info = new HashMap<String, Object>();

		// Set base path.
		if (getConnector().getInitParameter(AbstractFileClientConnector.PARAMETER_BASE_PATH) != null) {
			info.put(AbstractFileClient.CLIENT_INFO_BASE_PATH,
					getConnector().getInitParameter(AbstractFileClientConnector.PARAMETER_BASE_PATH));
		}

		// Set allows directories.
		info.put(AbstractFileClient.CLIENT_INFO_ALLOWS_DIRECTORIES, Boolean.valueOf(allowsDirectories()));

		// Set write length requirement.
		info.put(AbstractFileClient.CLIENT_INFO_REQUIRES_WRITE_LENGTH, Boolean.valueOf(requiresWriteLength()));

		// Set directory character separator.
		info.put(AbstractFileClient.CLIENT_INFO_DIRECTORY_CHARACTER_SEPARATOR, Character.valueOf(getDirChar()));

		// Return client info.
		return info;

	}

	/**
	 * Gets the directories from a given path, removing the file name from it.
	 * 
	 * @param path Path to the resource in the file storage.<br>
	 *             <br>
	 * @return Path directories without file name.<br>
	 *         <br>
	 */
	protected final String getPathDirectories(final String path) {

		// Get path.
		final String target = path.substring(0, path.lastIndexOf(CommonValueL1Constants.CHAR_FORWARD_SLASH));

		// Return path.
		return (target.equals(CommonValueL1Constants.STRING_EMPTY))
				? FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE
				: target;

	}

	/**
	 * Gets the character used to separate directories.
	 * 
	 * @return Directory character separator.<br>
	 *         <br>
	 */
	protected char getDirChar() {
		return CommonValueL1Constants.CHAR_FORWARD_SLASH;
	}

	/**
	 * Default implementation to extract from a given path the name of each
	 * directory.
	 * 
	 * @param chain Directory names found in path.<br>
	 *              <br>
	 * @param path  Path where to retrieve the directory names.<br>
	 *              <br>
	 */
	protected void toPathChain(final List<String> chain, final String path) {

		// Remove first directory character.
		if ((path.startsWith(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE))
				|| (path.startsWith(FileServiceConstants.DIRECTORY_SEPARATOR_WINDOWS_STYLE))) {

			// Process without directory character at the begining.
			toPathChain(chain, path.substring(1, path.length()));

			// Do not process current iteration.
			return;

		}

		// Search for '/' character.
		final int indexOfForwardSlash = path.indexOf(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE);

		// Search for '\' character.
		final int indexOfBackSlash = path.indexOf(FileServiceConstants.DIRECTORY_SEPARATOR_WINDOWS_STYLE);

		// Process with first directory character found.
		if ((indexOfForwardSlash > -1) && (indexOfBackSlash < 0)) {

			// Add directory to chain.
			chain.add(path.substring(0, indexOfForwardSlash));

			// Search for next directory.
			toPathChain(chain, path.substring(indexOfForwardSlash + 1, path.length()));

		} else if ((indexOfForwardSlash < 0) && (indexOfBackSlash > -1)) {

			// Add directory to chain.
			chain.add(path.substring(0, indexOfBackSlash));

			// Search for next directory.
			toPathChain(chain, path.substring(indexOfBackSlash + 1, path.length()));

		} else if ((indexOfForwardSlash > -1) && (indexOfBackSlash > -1)) {
			if (indexOfForwardSlash < indexOfBackSlash) {

				// Add directory to chain.
				chain.add(path.substring(0, indexOfForwardSlash));

				// Search for next directory.
				toPathChain(chain, path.substring(indexOfForwardSlash + 1, path.length()));

			} else {

				// Add directory to chain.
				chain.add(path.substring(0, indexOfBackSlash));

				// Search for next directory.
				toPathChain(chain, path.substring(indexOfBackSlash + 1, path.length()));

			}
		} else if ((indexOfForwardSlash < 0) && (indexOfBackSlash < 0)) {
			chain.add(path);
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies the content of an input stream into an output stream.
	 * 
	 * @param source     Input stream where to read the bytes.<br>
	 *                   <br>
	 * @param target     Output stream where to write the bytes.<br>
	 *                   <br>
	 * @param bufferSize Size of the temporary buffer.<br>
	 *                   <br>
	 * @param flush      Provide <code>true</code> to automatically flush the
	 *                   buffer.<br>
	 *                   <br>
	 * @throws IOException If there is an error when trying to copy the data from
	 *                     the input stream to the output stream.<br>
	 *                     <br>
	 */
	protected final void copy(final InputStream source, final OutputStream target, final int bufferSize,
			final boolean flush) throws IOException {

		// Create a new buffer to read the bytes from the source stream.
		final byte[] buffer = new byte[bufferSize];

		// Copy source stream bytes into target output stream.
		int bytesRead;
		while ((bytesRead = source.read(buffer)) != -1) {

			// Write buffer.
			target.write(buffer, 0, bytesRead);

			// Flush data.
			if (flush) {
				target.flush();
			}

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the size for a new buffer to create.
	 * 
	 * @param options Operation options. This argument is not mandatory.<br>
	 *                <br>
	 * @return Buffer size in bytes.<br>
	 *         <br>
	 */
	protected final int getBufferSize(final Map<String, Object> options) {

		// Get the buffer size.
		Integer bufferSize = null;
		if ((options != null) && (options.containsKey(OPERATION_OPTION_BUFFER_SIZE))) {

			// Get the value of the parameter.
			final Object parameterValue = options.get(OPERATION_OPTION_BUFFER_SIZE);

			// Set the size of the buffer.
			if (parameterValue instanceof Integer) {
				bufferSize = (Integer) parameterValue;
			} else if (parameterValue instanceof String) {
				try {
					bufferSize = Integer.valueOf((String) parameterValue);
				} catch (final NumberFormatException e) {
					bufferSize = DEFAULT_BUFFER_SIZE;
				}
			}

		} else {
			bufferSize = getConnector().toInteger(AbstractFileClientConnector.PARAMETER_BUFFER_SIZE);
		}

		// Return the size of the buffer.
		return (bufferSize == null) ? DEFAULT_BUFFER_SIZE : bufferSize;

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
		if ((options != null) && (options.size() > 0)) {

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_BUFFER_SIZE)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_BUFFER_SIZE);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Number)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_BUFFER_SIZE
									+ "' option is not a '" + Integer.class.getName() + "' or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}
	}

	/**
	 * Validates the <code>OPERATION_OPTION_PROCESS_RECURSIVELY</code> option.
	 * 
	 * @param options Options to validate.<br>
	 *                <br>
	 * @throws ClientException If operation option is not a boolean or a string
	 *                         value.
	 */
	protected boolean validateProcessRecursively(final Map<String, Object> options) throws ClientException {

		// Validate option.
		if ((options != null) && (options.containsKey(OPERATION_OPTION_PROCESS_RECURSIVELY))) {

			// Get the value of the option.
			final Object optionValue = options.get(OPERATION_OPTION_PROCESS_RECURSIVELY);

			// Validate option value.
			if (!(optionValue instanceof String) && !(optionValue instanceof Boolean)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given '" + OPERATION_OPTION_PROCESS_RECURSIVELY
								+ "' option is not a boolean or a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			} else if (optionValue instanceof Boolean) {
				return (Boolean) optionValue;
			} else if (optionValue.toString().equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) {
				return true;
			}

		}

		// Do not process recursively.
		return false;

	}

}
