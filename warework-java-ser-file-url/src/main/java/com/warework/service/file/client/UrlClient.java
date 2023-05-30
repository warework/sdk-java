package com.warework.service.file.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.UnsupportedOperationClientException;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.FileRef;
import com.warework.service.file.client.connector.UrlConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * File Client that performs operations with URL.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class UrlClient extends AbstractFileClient {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "url-client";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Throws an exception as this operation is not supported.
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
	 * @throws UnsupportedOperationClientException Always thrown as this operation
	 *                                             is not allowed.<br>
	 *                                             <br>
	 */
	public boolean write(final InputStream source, final long size, final Map<String, Object> options,
			final String target) throws UnsupportedOperationClientException {
		throw new UnsupportedOperationClientException(getScopeFacade(),
				"WAREWORK cannot create file at '" + target + "' in File Client '" + getName() + "' at Service '"
						+ getService().getName() + "' because this operation is not supported.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Throws an exception as this operation is not supported.
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
	 * @throws UnsupportedOperationClientException Always thrown as this operation
	 *                                             is not allowed.<br>
	 *                                             <br>
	 */
	public boolean makeDir(final String path, final Map<String, Object> options)
			throws UnsupportedOperationClientException {
		throw new UnsupportedOperationClientException(getScopeFacade(),
				"WAREWORK cannot create directory at '" + path + "' in File Client '" + getName() + "' at Service '"
						+ getService().getName() + "' because this operation is not supported.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Throws an exception as this operation is not supported.
	 * 
	 * @param path    File to delete.<br>
	 *                <br>
	 * @param options Options to delete the file. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws UnsupportedOperationClientException Always thrown as this operation
	 *                                             is not allowed.<br>
	 *                                             <br>
	 */
	public boolean delete(final String path, final Map<String, Object> options)
			throws UnsupportedOperationClientException {
		throw new UnsupportedOperationClientException(getScopeFacade(),
				"WAREWORK cannot delete file at '" + path + "' in File Client '" + getName() + "' at Service '"
						+ getService().getName() + "' because this operation is not supported.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Throws an exception as this operation is not supported.
	 * 
	 * @param source  File to change the name in the File Client.<br>
	 *                <br>
	 * @param newname New name for the source file.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws UnsupportedOperationClientException Always thrown as this operation
	 *                                             is not allowed.<br>
	 *                                             <br>
	 */
	public boolean rename(final String source, final String newname) throws UnsupportedOperationClientException {
		throw new UnsupportedOperationClientException(getScopeFacade(),
				"WAREWORK cannot rename file at '" + source + "' in File Client '" + getName() + "' at Service '"
						+ getService().getName() + "' because this operation is not supported.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Throws an exception as this operation is not supported.
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
	 * @throws UnsupportedOperationClientException Always thrown as this operation
	 *                                             is not allowed.<br>
	 *                                             <br>
	 */
	public List<FileRef> list(final String path, final Map<String, Object> options, final String[] orderBy)
			throws UnsupportedOperationClientException {
		throw new UnsupportedOperationClientException(getScopeFacade(),
				"WAREWORK cannot list files from '" + path + "' in File Client '" + getName() + "' at Service '"
						+ getService().getName() + "' because this operation is not supported.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Gets a file reference.
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
		try {

			// Create the URL that points to the file in the HTTP server.
			final URL url = new URL(getConnection().toString() + updatePath(path));

			// Create new connection to target resource.
			final URLConnection connection = url.openConnection();

			// Validate file exists.
			if (connection == null) {

				// Log a message.
				getScopeFacade().info("WAREWORK cannot find file '" + path + "' in File Client '" + getName()
						+ "' of Service '" + getService().getName() + "' because it does not exists.");

				// At this point, no resources are found.
				return null;

			} else {

				// First validate a connection can be made prior to return the file reference.
				connection.connect();

				// Log a message.
				getScopeFacade().info("WAREWORK successfully found file at '" + path + "' in File Client '" + getName()
						+ "' of Service '" + getService().getName() + "'.");

				// Return file reference.
				return new FileRefUrlImpl(this, connection, null);

			}

		} catch (final MalformedURLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + path + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because URL is malformed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final FileNotFoundException e) {

			// Log a message.
			getScopeFacade().warn("WAREWORK cannot find file '" + path + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName()
					+ "' because it does not exists, it's a directory or it's not accessible.");

			// Nothing to return.
			return null;

		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + path + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following I/O exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Specifies if Client supports directories.
	 * 
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	public boolean allowsDirectories() {
		return false;
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
	 * Loads a file as an input stream.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @param log     Flag to log if operation is successfull.<br>
	 *                <br>
	 * @return Stream to read the file or <code>null</code> if file does not
	 *         exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	protected InputStream performRead(final String source, final Map<String, Object> options, final boolean log)
			throws ClientException {
		try {

			// Create the URL that points to the file in the HTTP server.
			final URL url = new URL(getConnection().toString() + updatePath(source));

			// Create new connection to target resource.
			final URLConnection connection = url.openConnection();

			// Validate file exists.
			if (connection == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot read file '" + source + "' in File Client '" + getName()
						+ "' of Service '" + getService().getName() + "' because it does not exists.");

				// At this point, no resources are found.
				return null;

			} else {

				// Create input stream.
				final InputStream sourceStream = connection.getInputStream();

				// Get resource size.
				final int length = connection.getContentLength();

				// Log a message.
				if (log) {
					getScopeFacade().info("WAREWORK successfully loaded file '" + source + "' in File Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");
				}

				// Return operation result.
				return (length > -1) ? new LengthInputStream(sourceStream, length) : sourceStream;

			}

		} catch (final MalformedURLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file  from '" + source + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because URL is malformed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final FileNotFoundException e) {

			// Log a message.
			getScopeFacade().warn("WAREWORK cannot load file '" + source + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName() + "' because it does not exists or it's a directory.");

			// Nothing to return.
			return null;

		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following I/O exception is thrown: "
							+ e.getMessage(),
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
		return (getConnector().toString(UrlConnector.PARAMETER_BASE_PATH) == null) ? path
				: getConnector().toString(UrlConnector.PARAMETER_BASE_PATH) + path;
	}

}
