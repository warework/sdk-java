package com.warework.service.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import com.warework.core.service.client.ClientException;

/**
 * An abstract representation of file and directory pathnames.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface FileRef extends Comparable<FileRef> {

	/**
	 * Gets the name of the Client where this file or directory exists.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	String getClientName();

	/**
	 * Gets the source object that holds the full file or directory information.
	 * 
	 * @return Wrapped object with the full file or directory information.<br>
	 *         <br>
	 */
	Object getSource();

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and <code>false</code> if
	 *         the connection is closed.<br>
	 *         <br>
	 */
	boolean isConnected();

	/**
	 * Opens a connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to connect the
	 *                         Client.<br>
	 *                         <br>
	 */
	void connect() throws ClientException;

	/**
	 * Closes the connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to disconnect the
	 *                         Client.<br>
	 *                         <br>
	 */
	void disconnect() throws ClientException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the path name for this file or directory.
	 * 
	 * @return The string form of this path name.<br>
	 *         <br>
	 */
	String getPath();

	/**
	 * Validates if this reference points to a file or a directory.
	 * 
	 * @return <code>true</code> if this reference points to a directory and
	 *         <code>false</code> if it points to a file.<br>
	 *         <br>
	 */
	boolean isDirectory();

	/**
	 * Gets the size in bytes for the file represented by this reference.
	 * 
	 * @return File size in bytes. If this reference points to a directory then
	 *         <code>-1</code> is returned. This method returns also <code>-1</code>
	 *         when the size is unknown.<br>
	 *         <br>
	 */
	long getSize();

	/**
	 * Gets a date that represents when the file or directory was last modified.
	 * 
	 * @return Last modified date. This method returns <code>null</code> when date
	 *         is unknown. Some Clients may not be able to return the date for
	 *         directories.<br>
	 *         <br>
	 */
	Date getLastModified();

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads the content of the file as an input stream.
	 * 
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @return Input stream with the content of the file.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	InputStream getInputStream(final Map<String, Object> options) throws ClientException;

	/**
	 * Reads the content of the file.
	 * 
	 * @param target  Where to copy the content of the file.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	void read(final OutputStream target, final Map<String, Object> options) throws ClientException;

}
