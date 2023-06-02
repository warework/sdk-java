package com.warework.core.util.io;

import java.io.InputStream;

/**
 * Wrapper for <code>java.io.InputStream</code> with content length.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class LengthInputStream extends AbstractInputStreamWrapper {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped stream content length.
	private long length;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Wrapper for <code>java.io.InputStream</code> with content length.
	 * 
	 * @param stream Wrapped stream.<br>
	 *               <br>
	 * @param length Wrapped stream content length.<br>
	 *               <br>
	 */
	public LengthInputStream(final InputStream stream, final long length) {

		// Invoke default constructor.
		super(stream);

		// Set stream length.
		this.length = length;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets wrapped stream content length.
	 * 
	 * @return Length of the wrapped input stream.<br>
	 *         <br>
	 */
	public long getLength() {
		return (length < 0) ? -1 : length;
	}

}
