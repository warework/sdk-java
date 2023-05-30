package com.warework.service.file.client;

import java.net.URLConnection;
import java.util.Date;

import com.warework.service.file.client.connector.UrlConnector;

/**
 * URL reference wrapper.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FileRefUrlImpl extends AbstractFileRef {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped URL connection.
	private URLConnection connection;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a reference to file in the local file system.
	 * 
	 * @param client     Client where this file exists.<br>
	 *                   <br>
	 * @param connection Source URL connection to wrap.<br>
	 *                   <br>
	 * @param orderBy    Criteria to sort this file.<br>
	 *                   <br>
	 */
	FileRefUrlImpl(final FileClientFacade client, final URLConnection connection, final String[] orderBy) {

		// Invoke default initialization.
		super(client, connection, orderBy);

		// Set the URL to wrap.
		this.connection = connection;

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
		final String path = connection.getURL().getPath();

		// Remove base path when defined.
		return (getClient().getConnector().getInitParameter(UrlConnector.PARAMETER_BASE_PATH) == null) ? path
				: path.substring(getClient().getConnector().toString(UrlConnector.PARAMETER_BASE_PATH).length(),
						path.length());

	}

	/**
	 * Validates if this reference points to a file or a directory.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	public boolean isDirectory() {
		return false;
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

		// Get resource size.
		final int size = connection.getContentLength();

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

		// Get resource size.
		final long milliseconds = connection.getLastModified();

		// Create and return date from milliseconds.
		return (milliseconds <= 0) ? null : new Date(milliseconds);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a date that represents the value of the expires HTTP header field.
	 * 
	 * @return The expiration date of the resource that this URL references or
	 *         <code>null</code> if unknown.<br>
	 *         <br>
	 */
	public Date getExpiration() {

		// Get resource size.
		final long milliseconds = connection.getExpiration();

		// Create and return date from milliseconds.
		return (milliseconds <= 0) ? null : new Date(milliseconds);

	}

	/**
	 * Gets a date that represents the value of the <code>If-Modified-Since</code>
	 * HTTP header field.
	 * 
	 * @return The value of the <code>If-Modified-Since</code> HTTP header field or
	 *         <code>null</code> if unknown.<br>
	 *         <br>
	 */
	public Date getIfModifiedSince() {

		// Get resource size.
		final long milliseconds = connection.getIfModifiedSince();

		// Create and return date from milliseconds.
		return (milliseconds <= 0) ? null : new Date(milliseconds);

	}

}
