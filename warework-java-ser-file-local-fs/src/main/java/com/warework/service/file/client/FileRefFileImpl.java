package com.warework.service.file.client;

import java.io.File;
import java.util.Date;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.service.file.client.connector.LocalFileSystemConnector;

/**
 * Local file reference wrapper.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FileRefFileImpl extends AbstractFileRef {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped file.
	private File file;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a reference to file in the local file system.
	 * 
	 * @param client  Client where this file exists.<br>
	 *                <br>
	 * @param file    Source file to wrap.<br>
	 *                <br>
	 * @param orderBy Criteria to sort this file.<br>
	 *                <br>
	 */
	FileRefFileImpl(final FileClientFacade client, final File file, final String[] orderBy) {

		// Invoke default initialization.
		super(client, file, orderBy);

		// Set the file to wrap.
		this.file = file;

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

		// Get path.
		final String path = file.getPath();

		// Remove base path when defined.
		final String updated = (getClient().getConnector()
				.getInitParameter(LocalFileSystemConnector.PARAMETER_BASE_PATH) == null) ? path
						: path.substring(getClient().getConnector()
								.toString(LocalFileSystemConnector.PARAMETER_BASE_PATH).length(), path.length());

		// Update path with unix style directory character.
		return (getClient().getConnector().isInitParameter(LocalFileSystemConnector.PARAMETER_PATH_TRANSFORM))
				? updated.replace('\\', CommonValueL2Constants.CHAR_FORWARD_SLASH)
				: updated;

	}

	/**
	 * Validates if this reference points to a file or a directory.
	 * 
	 * @return <code>true</code> if this reference points to a directory and
	 *         <code>false</code> if it points to a file.<br>
	 *         <br>
	 */
	public boolean isDirectory() {
		return file.isDirectory();
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

		// Get the length of the file.
		final long size = file.length();

		// Return length.
		return (size >= 0) ? size : -1;

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
		return new Date(file.lastModified());
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates if this file is a hidden file or directory.
	 * 
	 * @return <code>true</code> if and only if the file denoted by this abstract
	 *         pathname is hidden according to the conventions of the underlying
	 *         platform.<br>
	 *         <br>
	 */
	public boolean isHidden() {
		return file.isHidden();
	}

	/**
	 * Validates whether the application can read the file.
	 * 
	 * @return <code>true</code> if and only if the file specified by this pathname
	 *         exists and can be read by the application; <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 */
	public boolean canRead() {
		return file.canRead();
	}

	/**
	 * Validates whether the application can modify the file denoted by this
	 * pathname.
	 * 
	 * @return <code>true</code> if and only if the file system actually contains a
	 *         file denoted by this pathname and the application is allowed to write
	 *         to the file; <code>false</code> otherwise.<br>
	 *         <br>
	 */
	public boolean canWrite() {
		return file.canWrite();
	}

}
