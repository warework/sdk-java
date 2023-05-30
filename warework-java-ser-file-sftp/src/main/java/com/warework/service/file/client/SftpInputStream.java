package com.warework.service.file.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.warework.core.util.io.LengthInputStream;

/**
 * Wrapper for <code>java.io.InputStream</code> for SFTP Clients.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class SftpInputStream extends LengthInputStream {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// SFTP connection.
	private ChannelSftp connection;

	// Path of the resource in the SFTP server.
	private String path;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Wrapper for <code>java.io.InputStream</code> for SFTP Clients.
	 * 
	 * @param connection SFTP connection.<br>
	 *                   <br>
	 * @param path       File to read in the File Client.<br>
	 *                   <br>
	 * @param size       Wrapped stream content length.<br>
	 *                   <br>
	 * @throws SftpException If there is an error when trying to create the
	 *                       stream.<br>
	 *                       <br>
	 */
	SftpInputStream(final ChannelSftp connection, final String path, final long size) throws SftpException {

		// Invoke default constructor.
		super(connection.get(path), size);

		// Set the SFTP connection.
		this.connection = connection;

		// Set the path to the resource.
		this.path = path;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the path to the resource in the remote SFTP server.
	 * 
	 * @return Resource path.<br>
	 *         <br>
	 */
	public String getPath() {
		return path;
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the SFTP connection.
	 * 
	 * @return SFTP connection.<br>
	 *         <br>
	 */
	ChannelSftp getChannel() {
		return connection;
	}

}
