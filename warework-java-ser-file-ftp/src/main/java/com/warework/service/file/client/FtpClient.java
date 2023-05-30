package com.warework.service.file.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamException;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ReplyCodeClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.FileRef;
import com.warework.service.file.client.connector.FtpConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * File Client that performs operations with FTP servers.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FtpClient extends AbstractFileClient {

	// ///////////////////////////////////////////////////////////////////
	// PULBIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Reads the content of a file.
	 * 
	 * @param source  File to read from the FTP server.<br>
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
		try {

			// Update path.
			final String updatedSource = updatePath(source);

			/*
			 * As a general Java convention, streams provided by users should not be closed
			 * by invoked methods. As users create the streams, they have to decide what to
			 * do with them.
			 */

			// Validate path length.
			if (updatedSource.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedSource.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedSource.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Load file.
			if (connection.retrieveFile(updatedSource, target)) {

				// Get server operation response code.
				final int replyCode = connection.getReplyCode();

				// Validate loaded file.
				if (FTPReply.isPositiveCompletion(replyCode)) {

					// Log a message.
					getScopeFacade().info("WAREWORK successfully read file '" + source + "' in FTP Client '" + getName()
							+ "' of Service '" + getService().getName() + "'.");

					// Return operation result.
					return true;

				} else {
					throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot read file '" + source
							+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because remote FTP server did not send a positive completion response (checkout reply code '"
							+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
							connection.getReplyString());
				}

			} else {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot read file '" + target + "' in FTP Client '" + getName()
						+ "' at Service '" + getService().getName() + "'.");

				// Return operation result.
				return false;

			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final CopyStreamException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + target + "' in FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following I/O exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Writes the content of a file.
	 * 
	 * @param source  Source file to read. Input stream is not closed when operation
	 *                is successfully completed.<br>
	 *                <br>
	 * @param size    Bytes of the source file.<br>
	 *                <br>
	 * @param options Options to write the file. This argument is not mandatory.<br>
	 *                <br>
	 * @param target  Where to copy the content of the source file from the target
	 *                FTP server.<br>
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
		try {

			// Update path.
			final String updatedTarget = updatePath(target);

			/*
			 * As a general Java convention, streams provided by users should not be closed
			 * by invoked methods. As users create the streams, they have to decide what to
			 * do with them.
			 */

			// Validate path length.
			if (updatedTarget.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot write file '" + target + "' in FTP Client '" + getName() + "' at Service '"
								+ getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedTarget.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot write file '" + target + "' in FTP Client '" + getName() + "' at Service '"
								+ getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedTarget.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot write file '" + target + "' in FTP Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Save file.
			if (connection.storeFile(updatedTarget, source)) {

				// Get server operation response code.
				final int replyCode = connection.getReplyCode();

				// Validate saved file.
				if (FTPReply.isPositiveCompletion(replyCode)) {

					// Log a message.
					getScopeFacade().info("WAREWORK successfully created file '" + target + "' in FTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

					// Return operation result.
					return true;

				} else {
					throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot write file '" + source
							+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because remote FTP server did not send a positive completion response (checkout reply code '"
							+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
							connection.getReplyString());
				}

			} else {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot write file '" + target + "' in FTP Client '" + getName()
						+ "' at Service '" + getService().getName() + "'.");

				// Return operation result.
				return false;

			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot write file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final CopyStreamException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot write file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot write file '" + target + "' in FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Creates a new directory.
	 * 
	 * @param path    Directory to create.<br>
	 *                <br>
	 * @param options Options to create the directory in the Client. Check out the
	 *                underlying FTP server to review which options it may accept.
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

			// Update path.
			final String updatedPath = updatePath(path);

			// Validate path length.
			if (updatedPath.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot create directory at '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedPath.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot create directory at '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedPath.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot create directory at '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Create directory.
			final int replyCode = connection.mkd(updatedPath);

			// Validate directory created.
			if (FTPReply.isPositiveCompletion(replyCode)) {

				// Log a message.
				getScopeFacade().info("WAREWORK successfully created directory '" + path + "' in FTP Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");

				// Return operation result.
				return true;

			} else {
				throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot create directory '" + path
						+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
						+ "' because remote FTP server did not send a positive completion response (checkout reply code '"
						+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
						connection.getReplyString());
			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot create directory '" + path + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot create directory '" + path + "' in FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
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

			// Update path.
			final String updatedPath = updatePath(path);

			// Validate path length.
			if (updatedPath.length() < 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot delete file or directory from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Find FTP server file or directory.
			final FileRef resource = find(path, options);

			// Validate resource exists.
			if (resource == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot delete file or directory '" + path + "' in FTP Client '"
						+ getName() + "' at Service '" + getService().getName() + "' becase it does not exists.");

				// Return operation result.
				return false;

			} else {

				// Delete file or directory.
				int replyCode = -1;
				if (resource.isDirectory()) {
					replyCode = connection.rmd(updatedPath);
				} else {
					replyCode = connection.dele(updatedPath);
				}

				// Validate directory created.
				if (FTPReply.isPositiveCompletion(replyCode)) {

					// Log a message.
					if (resource.isDirectory()) {
						getScopeFacade().info("WAREWORK successfully deleted directory '" + path + "' in FTP Client '"
								+ getName() + "' of Service '" + getService().getName() + "'.");
					} else {
						getScopeFacade().info("WAREWORK successfully deleted file '" + path + "' in FTP Client '"
								+ getName() + "' of Service '" + getService().getName() + "'.");
					}

					// Return operation result.
					return true;

				} else {
					throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot delete file or directory '"
							+ path + "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because remote FTP server did not send a positive completion response (checkout reply code '"
							+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
							connection.getReplyString());
				}

			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot delete file or directory '" + path + "' in  FTP Client '" + getName()
							+ "' at Service '" + getService().getName() + "' because FTP connection is closed: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete file or directory '" + path + "' in FTP Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Renames a file or directory.
	 * 
	 * @param source  File to change the name in the FTP server.<br>
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

			// Update path.
			final String updatedSource = updatePath(source);

			// Update path.
			final String updatedNewname = updatePath(newname);

			// Validate path length.
			if (updatedSource.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedSource.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedSource.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate path length.
			if (updatedNewname.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedNewname.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedNewname.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Find resource to rename.
			int replyCode = connection.rnfr(updatedSource);

			// Validate directory created.
			if (!FTPReply.isPositiveIntermediate(replyCode)) {
				throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot rename file or directory '"
						+ source + "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
						+ "' because remote FTP server did not send a positive intermediate response (checkout reply code '"
						+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
						connection.getReplyString());
			}

			// Rename file or directory.
			replyCode = connection.rnto(updatedNewname);

			// Validate directory created.
			if (FTPReply.isPositiveCompletion(replyCode)) {

				// Log a message.
				getScopeFacade().info("WAREWORK successfully renamed file or directory '" + source + "' in FTP Client '"
						+ getName() + "' of Service '" + getService().getName() + "' to '" + newname + "'.");

				// Return operation result.
				return true;

			} else {
				throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot rename file or directory '"
						+ source + "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
						+ "' because remote FTP server did not send a positive completion response (checkout reply code '"
						+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
						connection.getReplyString());
			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot rename file or directory '" + source + "' in  FTP Client '" + getName()
							+ "' at Service '" + getService().getName() + "' because FTP connection is closed: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot rename file or directory '" + source + "' in FTP Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following exception is thrown: " + e.getMessage(),
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
	public List<FileRef> list(final String path, final Map<String, Object> options, final String[] orderBy)
			throws ClientException {
		try {

			// Update path.
			final String updatedPath = updatePath(path);

			// Validate path length.
			if (updatedPath.length() < 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedPath.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedPath.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Store file references here.
			final List<FileRef> output = new ArrayList<FileRef>();

			// List files.
			final FTPFile[] files = connection.listFiles(updatedPath);
			for (int i = 0; i < files.length; i++) {
				output.add(new FileRefFtpImpl(this, files[i], updatedPath, orderBy));
			}

			// Log a message.
			getScopeFacade().info("WAREWORK successfully listed files and directories from '" + path
					+ "' in FTP Client '" + getName() + "' of Service '" + getService().getName() + "'.");

			// Return files and directories found.
			return output;

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot list files and directories from '" + path + "' in  FTP Client '" + getName()
							+ "' at Service '" + getService().getName() + "' because FTP connection is closed: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
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
		try {

			// Update path.
			final String updatedPath = updatePath(path);

			// Validate path length.
			if (updatedPath.length() < 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedPath.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedPath.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Get base path where to search for the resource.
			final String basePath = getPathDirectories(updatedPath);

			// Get the name of the resource to search for.
			final String resourceName = getFileName(updatedPath);

			// List files.
			FTPFile[] resources = connection.listFiles(basePath);
			for (int i = 0; i < resources.length; i++) {

				// Get remote resource.
				final FTPFile file = resources[i];

				// Validate resource is the one we are looking for.
				if (file.getName().equals(resourceName)) {

					// Log a message.
					getScopeFacade().info("WAREWORK successfully found file at '" + path + "' in FTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

					// Return resource.
					return new FileRefFtpImpl(this, file, basePath, null);

				}

			}

			// List directories.
			resources = connection.listDirectories(basePath);
			for (int i = 0; i < resources.length; i++) {

				// Get remote resource.
				final FTPFile directory = resources[i];

				// Validate resource is the one we are looking for.
				if (directory.getName().equals(resourceName)) {

					// Log a message.
					getScopeFacade().info("WAREWORK successfully found directory at '" + path + "' in FTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

					// Return resource.
					return new FileRefFtpImpl(this, directory, basePath, null);

				}

			}

			// Log a message.
			getScopeFacade().info("WAREWORK cannot find file or directory at '" + path + "' in FTP Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// At this point, no resource was found.
			return null;

		} catch (final FTPConnectionClosedException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
							+ "' at Service '" + getService().getName() + "' because connection is closed.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot list files and directories from '" + path + "' in FTP Client '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

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
	 * @param log     Not used (handled as always <code>true</code>).<br>
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

			// Update path.
			final String updatedSource = updatePath(source);

			// Validate path length.
			if (updatedSource.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedSource.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedSource.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in FTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Find target file or directory.
			final FTPFile resource = connection.mlistFile(updatedSource);
			if (resource == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot read file '" + source + "' in FTP Client '" + getName()
						+ "' of Service '" + getService().getName() + "' because it does not exists.");

				// Nothing to return at this point.
				return null;

			} else if (resource.isDirectory()) {
				throw new ReplyCodeClientException(getScopeFacade(),
						"WAREWORK cannot read file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because it's a directory.",
						null, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.DIRECTORY_STATUS,
						connection.getReplyString());
			}

			// Get resource size.
			final long size = (resource.getSize() < 0) ? -1 : resource.getSize();

			// Get resource as a stream.
			final InputStream stream = connection.retrieveFileStream(updatedSource);

			// Get FTP server reply code.
			final int replyCode = connection.getReplyCode();

			// Return stream.
			if (stream == null) {
				if (replyCode == FTPReply.FILE_UNAVAILABLE) {

					// Log a message.
					getScopeFacade().warn("WAREWORK cannot read file '" + source + "' in FTP Client '" + getName()
							+ "' of Service '" + getService().getName() + "' because it is unavailable.");

					// Nothing to return at this point.
					return null;

				} else {
					throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot read file '" + source
							+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because remote server was not able to process the request (checkout reply code '"
							+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
							connection.getReplyString());
				}
			} else if (FTPReply.isPositivePreliminary(replyCode)) {
				/*
				 * The SIZE command is not a part of the official FTP standard but has been
				 * implemented by many popular FTP servers.
				 */
				if (connection.completePendingCommand()) {

					// Log a message.
					getScopeFacade().info("WAREWORK successfully loaded file '" + source + "' in FTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

					// Return resource as a stream.
					return new LengthInputStream(stream, size);

				} else {
					throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot read file '" + source
							+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because remote FTP server pending operation couldn't be completed (checkout pending operation reply code '"
							+ replyCode + "' and complete pending command reply code '" + connection.getReplyCode()
							+ "').", null, LogServiceConstants.LOG_LEVEL_WARN, connection.getReplyCode(),
							connection.getReplyString());
				}
			} else {

				// Close input stream.
				stream.close();

				// Notify error.
				throw new ReplyCodeClientException(getScopeFacade(), "WAREWORK cannot read file '" + source
						+ "' in FTP Client '" + getName() + "' at Service '" + getService().getName()
						+ "' because remote FTP server did not send a positive intermediate response (checkout reply code '"
						+ replyCode + "').", null, LogServiceConstants.LOG_LEVEL_WARN, replyCode,
						connection.getReplyString());

			}

		} catch (final FTPConnectionClosedException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because FTP connection is closed: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, FTPReply.SERVICE_NOT_AVAILABLE, null);
		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in  FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following I/O exception is thrown: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the FTP server.
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         connection with the FTP server.<br>
	 *                         <br>
	 */
	protected void close() throws ClientException {
		try {

			// Get the connection with the FTP Client.
			final FTPClient connection = (FTPClient) getConnection();

			// Close the connection.
			connection.disconnect();

		} catch (final IOException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot close connection in FTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>true</code> if connection is closed; <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 */
	protected boolean isClosed() {

		// Get the connection with the FTP Client.
		final FTPClient connection = (FTPClient) getConnection();

		// Test if connection is closed.
		return !connection.isConnected();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the character used to separate directories.
	 * 
	 * @return Directory character separator.<br>
	 *         <br>
	 */
	protected char getDirChar() {

		// Get the directory character separator.
		final Object dirChar = getConnector().getInitParameter(FtpConnector.PARAMETER_DIRECTORY_CHARACTER_SEPARATOR);

		// Return the directory character separator.
		if (dirChar == null) {
			return CommonValueL1Constants.CHAR_FORWARD_SLASH;
		} else if (dirChar instanceof Character) {
			return (Character) dirChar;
		} else {
			return dirChar.toString().trim().charAt(0);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the file from a given path.
	 * 
	 * @param path Path where to extract the name of the file.<br>
	 *             <br>
	 * @return File name of the path.<br>
	 *         <br>
	 */
	private String getFileName(final String path) {
		return path.substring(path.lastIndexOf(CommonValueL1Constants.CHAR_FORWARD_SLASH) + 1, path.length());
	}

	/**
	 * Add base path when required.
	 * 
	 * @param path Source path.<br>
	 *             <br>
	 * @return Updated path.<br>
	 *         <br>
	 */
	private String updatePath(final String path) {
		return (getConnector().toString(FtpConnector.PARAMETER_BASE_PATH) == null) ? path
				: getConnector().toString(FtpConnector.PARAMETER_BASE_PATH) + path;
	}

}
