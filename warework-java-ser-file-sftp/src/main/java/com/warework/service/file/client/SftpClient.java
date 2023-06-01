package com.warework.service.file.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ReplyCodeClientException;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.file.FileRef;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.connector.SftpConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * File Client that performs operations with SFTP servers.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class SftpClient extends AbstractFileClient {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Extra connection for specific operations (for example: required when writting
	// a stream that comes from the same connection).
	private ChannelSftp alternateConnection;

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

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Load file.
			connection.get(updatedSource, target);

			// Return operation result.
			return true;

		} catch (final SftpException e) {
			throw createException(
					"WAREWORK cannot read file '" + target + "' in SFTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e);
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
						"WAREWORK cannot write file '" + target + "' in SFTP Client '" + getName() + "' at Service '"
								+ getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedTarget.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot write file '" + target + "' in SFTP Client '" + getName() + "' at Service '"
								+ getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedTarget.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot write file '" + target + "' in SFTP Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Validate stream comes from current connection.
			if (source instanceof SftpInputStream) {

				// Get SFTP stream.
				final SftpInputStream stream = (SftpInputStream) source;

				// Validate stream connection is the same as current connection.
				if (stream.getChannel() == connection) {

					// Create new connection with the same SFTP Client. This is because JSch cannot
					// use Input and Output Streams at the same time within the same connection.
					if (alternateConnection == null) {
						alternateConnection = (ChannelSftp) getConnector().getClientConnection();
					}

					// Copy file.
					alternateConnection.put(source, updatedTarget);

					// Log a message.
					getScopeFacade().info("WAREWORK successfully copied file '" + stream.getPath() + "' to '" + target
							+ "' within SFTP Client '" + getName() + "' of Service '" + getService().getName() + "'.");

					// Return operation result.
					return true;

				}

			}

			// Write file.
			connection.put(source, updatedTarget);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully created file '" + target + "' in SFTP Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final SftpException e) {
			throw createException(
					"WAREWORK cannot write file '" + target + "' in SFTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e);
		} catch (ConnectorException e) {
			throw new ClientException(getScopeFacade(), target, null, DEFAULT_BUFFER_SIZE);
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

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Create directory in target SFTP server.
			connection.mkdir(updatedPath);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully created directory '" + path + "' in SFTP Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final SftpException e) {
			throw createException(
					"WAREWORK cannot create directory '" + path + "' in SFTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e);
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
			if (path.length() < 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot delete file or directory from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Find FTP server file or directory.
			final FileRef resource = find(path, options);

			// Validate resource exists.
			if (resource == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot delete file or directory '" + path + "' in SFTP Client '"
						+ getName() + "' at Service '" + getService().getName() + "' becase it does not exists.");

				// Return operation result.
				return false;

			} else {

				// Delete file or directory in target SFTP server.
				if (resource.isDirectory()) {

					// Delete directory.
					connection.rmdir(updatedPath);

					// Log a message.
					getScopeFacade().info("WAREWORK successfully deleted directory '" + path + "' in SFTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

				} else {

					// Delete file.
					connection.rm(updatedPath);

					// Log a message.
					getScopeFacade().info("WAREWORK successfully deleted file '" + path + "' in SFTP Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");

				}

				// Return operation result.
				return true;

			}

		} catch (final SftpException e) {
			throw createException("WAREWORK cannot delete file or directory '" + path + "' in SFTP Client '" + getName()
					+ "' at Service '" + getService().getName() + "' because the following exception is thrown: "
					+ e.getMessage(), e);
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
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedSource.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedSource.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate path length.
			if (updatedNewname.length() <= 1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedNewname.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedNewname.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName() + "' to '" + newname
								+ "' because target path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Rename file or directory.
			connection.rename(updatedSource, updatedNewname);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully renamed file or directory '" + source + "' in SFTP Client '"
					+ getName() + "' of Service '" + getService().getName() + "' to '" + newname + "'.");

			// Return operation result.
			return true;

		} catch (final SftpException e) {
			throw createException("WAREWORK cannot rename file or directory '" + source + "' in SFTP Client '"
					+ getName() + "' at Service '" + getService().getName()
					+ "' because the following exception is thrown: " + e.getMessage(), e);
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
						"WAREWORK cannot list files and directories from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedPath.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedPath.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list files and directories from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Store file references here.
			final List<FileRef> output = new ArrayList<FileRef>();

			// List files.
			final Vector<?> list = connection.ls(updatedPath);
			for (final Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

				// Get one file or directory.
				final LsEntry entry = (LsEntry) iterator.next();

				// Validate file or directory.
				if ((!entry.getFilename().equals(ResourceL1Helper.DIRECTORY_CURRENT))
						&& (!entry.getFilename().equals(ResourceL1Helper.DIRECTORY_PARENT))) {
					if (updatedPath.equals(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE)) {
						output.add(new FileRefSftpImpl(this, entry,
								FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + entry.getFilename(), orderBy));
					} else {
						output.add(new FileRefSftpImpl(this, entry,
								updatedPath + FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + entry.getFilename(),
								orderBy));
					}
				}

			}

			// Log a message.
			getScopeFacade().info("WAREWORK successfully listed files and directories from '" + path
					+ "' in SFTP Client '" + getName() + "' of Service '" + getService().getName() + "'.");

			// Return files and directories found.
			return output;

		} catch (final SftpException e) {
			throw createException("WAREWORK cannot list files and directories from '" + path + "' in SFTP Client '"
					+ getName() + "' at Service '" + getService().getName()
					+ "' because the following exception is thrown: " + e.getMessage(), e);

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
						"WAREWORK cannot find file or directory from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be at least one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedPath.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot find file or directory from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedPath.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot find file or directory from '" + path + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Find and return resource.
			final LsEntry entry = find(connection, updatedPath);
			if (entry == null) {

				// Log a message.
				getScopeFacade().info("WAREWORK cannot find file or directory from '" + path + "' in SFTP Client '"
						+ getName() + "' of Service '" + getService().getName() + "' because it does not exists.");

				// At this point, no resources are found.
				return null;

			} else {

				// Log a message.
				getScopeFacade().info("WAREWORK successfully found file or directory at '" + path + "' in SFTP Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");

				// Return resource reference found.
				return new FileRefSftpImpl(this, entry, updatedPath, null);

			}

		} catch (final SftpException e) {
			throw createException("WAREWORK cannot find file or directory from '" + path + "' in SFTP Client '"
					+ getName() + "' at Service '" + getService().getName()
					+ "' because the following exception is thrown: " + e.getMessage(), e);
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
						"WAREWORK cannot read file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path must be more than one character length.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate directory separator character.
			if (updatedSource.indexOf('\\') > 0) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path contains invalid directory character '\\'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate first character.
			if (updatedSource.charAt(0) != CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot read file or directory '" + source + "' in SFTP Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because given path does not start with character '/'.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the connection with the SFTP Client.
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Load resource.
			final LsEntry entry = find(connection, updatedSource);
			if (entry == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot load file '" + source + "' in SFTP Client '" + getName()
						+ "' of Service '" + getService().getName() + "' because it does not exists.");

				// Return resource as a stream.
				return null;

			} else if (entry.getAttrs().isDir()) {
				throw new ReplyCodeClientException(getScopeFacade(),
						"WAREWORK cannot read file '" + source + "' in  SFTP Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because it's a directory.",
						null, LogServiceConstants.LOG_LEVEL_WARN, ChannelSftp.SSH_FX_NO_SUCH_FILE,
						Integer.toString(ChannelSftp.SSH_FX_NO_SUCH_FILE));
			} else {

				// Load file.
				final InputStream stream = new SftpInputStream(connection, updatedSource, entry.getAttrs().getSize());

				// Log a message.
				getScopeFacade().info("WAREWORK successfully loaded file '" + source + "' in SFTP Client '" + getName()
						+ "' of Service '" + getService().getName() + "'.");

				// Return resource as a stream.
				return stream;

			}

		} catch (final SftpException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot load file '" + source + "' in SFTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the SFTP server
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         connection with the SFTP server.<br>
	 *                         <br>
	 *                         >
	 */
	protected void close() throws ClientException {

		// Exception that may be thrown when trying to close the connection.
		JSchException exception = null;

		// Close main connection.
		try {

			// Get the connection with the SFTP Client
			final ChannelSftp connection = (ChannelSftp) getConnection();

			// Close channel.
			connection.disconnect();

			// Close session
			connection.getSession().disconnect();

		} catch (final JSchException e) {
			exception = e;
		}

		// Close alternate connection.
		if (alternateConnection != null) {
			try {

				// Close channel.
				alternateConnection.disconnect();

				// Close session
				alternateConnection.getSession().disconnect();

			} catch (final JSchException e) {
				exception = e;
			}
		}

		// Throw exception when required.
		if (exception != null) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot close connection in SFTP Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because the following exception is thrown: "
							+ exception.getMessage(),
					exception, LogServiceConstants.LOG_LEVEL_WARN);
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
		final ChannelSftp connection = (ChannelSftp) getConnection();

		// Close channel.
		return connection.isClosed();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// //////////////////////////////////////////////////////////////////

	/**
	 * Add base path when required.
	 * 
	 * @param path Source path.<br>
	 *             <br>
	 * @return Updated path.<br>
	 *         <br>
	 */
	private String updatePath(final String path) {
		return (getConnector().toString(SftpConnector.PARAMETER_BASE_PATH) == null) ? path
				: getConnector().toString(SftpConnector.PARAMETER_BASE_PATH) + path;
	}

	/**
	 * Creates an exception with an error code.
	 * 
	 * @param message Exception message.<br>
	 *                <br>
	 * @param e       Source exception.<br>
	 *                <br>
	 * @return New exception with eror code.<br>
	 *         <br>
	 */
	private ClientException createException(final String message, final SftpException e) {

		// Find error message code limit.
		final int index = e.toString().indexOf(':');

		// Process error code.
		if (index > 0) {

			// Get exception error code.
			final String code = e.toString().substring(0, index);

			// Create new exception.
			try {
				return new ReplyCodeClientException(getScopeFacade(), message, e, LogServiceConstants.LOG_LEVEL_WARN,
						Integer.valueOf(code), code);
			} catch (final NumberFormatException e2) {
				// DO NOTHING
			}

		}

		// At this point, throw standard Client exception.
		return new ClientException(getScopeFacade(), message, e, LogServiceConstants.LOG_LEVEL_WARN);

	}

	/**
	 * Find resource in SFTP server.
	 * 
	 * @param connection SFTP connection.<br>
	 *                   <br>
	 * @param path       Path to the resource in the file storage.<br>
	 *                   <br>
	 * @return SFTP resource.<br>
	 *         <br>
	 * @throws SftpException If there is an error when trying to close the
	 *                       connection with the SFTP server.<br>
	 *                       <br>
	 */
	private LsEntry find(final ChannelSftp connection, final String path) throws SftpException {
		try {

			// List available files at given path.
			Vector<?> list = connection.ls(path);

			// First try to find path as a specific file.
			for (final Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

				// Get one file or directory.
				final LsEntry entry = (LsEntry) iterator.next();

				// Validate file.
				if (path.endsWith(entry.getFilename())) {
					return entry;
				}

			}

			// Get path to parent directory.
			String parent = path.substring(0, path.lastIndexOf(CommonValueL1Constants.CHAR_FORWARD_SLASH));
			if (parent.equals(CommonValueL1Constants.STRING_EMPTY)) {
				parent = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE;
			}

			// Try to find path as a specific directory.
			if (!parent.equals(path)) {

				// List files.
				list = connection.ls(parent);

				// Get the name of the directory to retrieve.
				final String directoryName = path
						.substring(path.lastIndexOf(CommonValueL1Constants.CHAR_FORWARD_SLASH) + 1, path.length());

				// Search for specific directory in parent directory.
				for (final Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

					// Get one file or directory.
					final LsEntry entry = (LsEntry) iterator.next();

					// Validate file or directory.
					if (entry.getFilename().equals(directoryName)) {
						return entry;
					}

				}

			}

			// Return files and directories found.
			return null;

		} catch (final SftpException e) {
			if (e.toString().startsWith(ChannelSftp.SSH_FX_NO_SUCH_FILE + StringL1Helper.CHARACTER_COLON)) {
				return null;
			} else {
				throw e;
			}
		}
	}

}
