package com.warework.service.file.client;

import java.util.Date;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.warework.service.file.client.connector.SftpConnector;

/**
 * SFTP file reference wrapper.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class FileRefSftpImpl extends AbstractFileRef {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped file.
	private LsEntry file;

	// Base path where file or directory exists.
	private String path;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a reference to a SFTP file or directory.
	 * 
	 * @param client  Client where this file exists.<br>
	 *                <br>
	 * @param file    Source file to wrap.<br>
	 *                <br>
	 * @param path    Path where file or directory exists.<br>
	 *                <br>
	 * @param orderBy Criteria to sort this file.<br>
	 *                <br>
	 */
	FileRefSftpImpl(final FileClientFacade client, final LsEntry file, final String path, final String[] orderBy) {

		// Invoke default initialization.
		super(client, file, orderBy);

		// Set the file to wrap.
		this.file = file;

		// Set base path.
		this.path = path;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the path name for this file or directory.
	 * 
	 * @return The string form of this path name.<br>
	 *         <br>
	 */
	public String getPath() {
		return (getClient().getConnector().getInitParameter(SftpConnector.PARAMETER_BASE_PATH) == null) ? path
				: path.substring(getClient().getConnector().toString(SftpConnector.PARAMETER_BASE_PATH).length(),
						path.length());
	}

	/**
	 * Validates if this reference points to a file or a directory.
	 * 
	 * @return <code>true</code> if this reference points to a directory and
	 *         <code>false</code> if it points to a file.<br>
	 *         <br>
	 */
	public boolean isDirectory() {
		return file.getAttrs().isDir();
	}

	/**
	 * Gets the size in bytes for the file represented by this reference.
	 * 
	 * @return File size in bytes. If this reference points to a directory then
	 *         <code>-1</code> is returned. This method returns also <code>-1</code>
	 *         when the size is unknown.<br>
	 *         <br>
	 */
	public long getSize() {
		return isDirectory() ? -1 : file.getAttrs().getSize();
	}

	/**
	 * Gets a date that represents when the file or directory was last modified.
	 * 
	 * @return Last modified date. This method returns <code>null</code> when date
	 *         is unknown. Some Clients may not be able to return the date for
	 *         directories.<br>
	 *         <br>
	 */
	public Date getLastModified() {

		// Get last modified time.
		final int time = file.getAttrs().getMTime();
		if (time >= 0) {
			return new Date(time * 1000L);
		}

		// At this point, date is unknown.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests if the file is a symbolic link.
	 * 
	 * @return <code>true</code> if and only if the file specified by this pathname
	 *         is a symbolic link; <code>false</code> otherwise.<br>
	 *         <br>
	 */
	public boolean isSymbolicLink() {
		return file.getAttrs().isLink();
	}

	/**
	 * Gets the user ID.
	 * 
	 * @return User ID.<br>
	 *         <br>
	 */
	public int getUserId() {
		return file.getAttrs().getUId();
	}

	/**
	 * Gets the group ID.
	 * 
	 * @return Group ID.<br>
	 *         <br>
	 */
	public int getGroupId() {
		return file.getAttrs().getGId();
	}

}
