package com.warework.service.file.client;

import java.util.Date;

import org.apache.commons.net.ftp.FTPFile;

import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.connector.FtpConnector;

/**
 * FTP file reference wrapper.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FileRefFtpImpl extends AbstractFileRef {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped file.
	private FTPFile file;

	// Base path where file or directory exists.
	private String basePath;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a reference to a FTP file or directory.
	 * 
	 * @param client   Client where this file exists.<br>
	 *                 <br>
	 * @param file     Source file to wrap.<br>
	 *                 <br>
	 * @param basePath Base path where file or directory exists.<br>
	 *                 <br>
	 * @param orderBy  Criteria to sort this file.<br>
	 *                 <br>
	 */
	FileRefFtpImpl(final FileClientFacade client, final FTPFile file, final String basePath, final String[] orderBy) {

		// Invoke default initialization.
		super(client, file, orderBy);

		// Set the file to wrap.
		this.file = file;

		// Set base path.
		this.basePath = basePath;

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
		final String path = (basePath.equals(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE))
				? FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + file.getName()
				: basePath + FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + file.getName();

		// Remove base path when defined.
		return (getClient().getConnector().getInitParameter(FtpConnector.PARAMETER_BASE_PATH) == null) ? path
				: path.substring(getClient().getConnector().toString(FtpConnector.PARAMETER_BASE_PATH).length(),
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
		final long size = file.getSize();

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
		return (file.getTimestamp() == null) ? null : file.getTimestamp().getTime();
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
		return file.isSymbolicLink();
	}

	/**
	 * Gets the name of the user owning the file. Sometimes this will be a string
	 * representation of the user number.
	 * 
	 * @return Name of the user owning the file.<br>
	 *         <br>
	 */
	public String getUser() {
		return file.getUser();
	}

}
