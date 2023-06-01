package com.warework.service.file.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.service.file.FileRef;
import com.warework.service.file.FileServiceConstants;

/**
 * Default file reference implementation.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractFileRef implements FileRef {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Client where this file exists.
	private FileClientFacade client;

	// Name of the Client where this file exists.
	private String name;

	// Source object to wrap, the one that holds the full file or directory
	// information.
	private Object source;

	// Criteria to sort this file.
	private String[] order;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Does not perform any operation.
	 */
	private AbstractFileRef() {
		// DO NOTHING.
	}

	/**
	 * Creates a reference to a file.
	 * 
	 * @param client  Client where this file exists.<br>
	 *                <br>
	 * @param source  Name of the Client where this file exists.<br>
	 *                <br>
	 * @param orderBy Criteria to sort this file.<br>
	 *                <br>
	 */
	protected AbstractFileRef(final FileClientFacade client, final Object source, final String[] orderBy) {

		// Invoke default constructor.
		this();

		// Set the Client where this file exists.
		this.client = client;

		// Set the source object to wrap.
		this.source = source;

		// Set how to sort this file.
		this.order = orderBy;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the source object that holds the full file or directory information.
	 * 
	 * @return Wrapped object with the full file or directory information.<br>
	 *         <br>
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * Gets the name of the Client where this file or directory exists.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	public final String getClientName() {
		return name;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and <code>false</code> if
	 *         the connection is closed.<br>
	 *         <br>
	 */
	public final boolean isConnected() {
		return client.isConnected();
	}

	/**
	 * Opens a connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to connect the
	 *                         Client.<br>
	 *                         <br>
	 */
	public final void connect() throws ClientException {
		client.connect();
	}

	/**
	 * Closes the connection with the Client.
	 * 
	 * @throws ClientException If there is an error when trying to disconnect the
	 *                         Client.<br>
	 *                         <br>
	 */
	public final void disconnect() throws ClientException {
		client.disconnect();
	}

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
	public InputStream getInputStream(final Map<String, Object> options) throws ClientException {
		return client.read(getPath(), options);
	}

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
	public void read(final OutputStream target, final Map<String, Object> options) throws ClientException {
		client.read(getPath(), options, target);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the file path.
	 * 
	 * @return File path.<br>
	 *         <br>
	 */
	public String toString() {
		return getPath();
	}

	/**
	 * Compares this file reference with the specified file reference for order.
	 * 
	 * @param fileRef the file reference to be compared.<br>
	 *                <br>
	 * @return A negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object.<br>
	 *         <br>
	 */
	public int compareTo(final FileRef fileRef) {

		// Compare objects when order by exists.
		if ((order != null) && (order.length > 0)) {
			for (int index = 0; index < order.length; index++) {

				// Get an order by criterion.
				final String criterion = order[index];

				// Compare objects.
				int result = 0;
				if ((criterion.equals(FileServiceConstants.ORDER_BY_PATH_ASCENDING))
						|| (criterion.equals(FileServiceConstants.ORDER_BY_PATH_DESCENDING))) {
					result = getPath().compareTo(fileRef.getPath());
				} else if ((criterion.equals(FileServiceConstants.ORDER_BY_SIZE_ASCENDING))
						|| (criterion.equals(FileServiceConstants.ORDER_BY_SIZE_DESCENDING))) {

					// Get the size of this file.
					final long value1 = getSize();

					// Get the size of the file to compare.
					final long value2 = fileRef.getSize();

					// Compare size.
					if (value1 > value2) {
						result = 1;
					} else if (value1 < value2) {
						result = -1;
					}

				} else if ((criterion.equals(FileServiceConstants.ORDER_BY_LAST_MODIFIED_ASCENDING))
						|| (criterion.equals(FileServiceConstants.ORDER_BY_LAST_MODIFIED_DESCENDING))) {
					result = getLastModified().compareTo(fileRef.getLastModified());
				}

				// Return result only if both values are not equal.
				if (result != 0) {
					return ((criterion.equals(FileServiceConstants.ORDER_BY_PATH_ASCENDING))
							|| (criterion.equals(FileServiceConstants.ORDER_BY_SIZE_ASCENDING))
							|| (criterion.equals(FileServiceConstants.ORDER_BY_LAST_MODIFIED_ASCENDING))) ? result
									: -result;
				}

			}
		}

		// At this point, both objects are equal.
		return 0;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the File Client that created this file reference.
	 * 
	 * @return File Client that created this file reference.<br>
	 *         <br>
	 */
	protected final FileClientFacade getClient() {
		return client;
	}

	/**
	 * Gets the criteria to sort this file.
	 * 
	 * @return Order by criteria.<br>
	 *         <br>
	 */
	protected String[] getOrderBy() {
		return order;
	}

}
