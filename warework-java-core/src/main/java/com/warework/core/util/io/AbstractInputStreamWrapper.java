package com.warework.core.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper for <code>java.io.InputStream</code>.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractInputStreamWrapper extends InputStream {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Wrapped stream.
	private InputStream stream;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private AbstractInputStreamWrapper() {
		// DO NOTHING.
	}

	/**
	 * Wrapper for <code>java.io.InputStream</code> with content length.
	 * 
	 * @param stream Wrapped stream.<br>
	 *               <br>
	 */
	public AbstractInputStreamWrapper(final InputStream stream) {

		// Invoke default constructor.
		this();

		// Set wrapped stream.
		this.stream = stream;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#read()</code>.
	 *
	 * @return Wrapped <code>java.io.InputStream#read()</code> response.<br>
	 *         <br>
	 * @exception IOException If wrapped <code>java.io.InputStream#read()</code>
	 *                        throws an exception.<br>
	 *                        <br>
	 */
	public int read() throws IOException {
		return stream.read();
	}

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#read(byte[])</code>.
	 *
	 * @param b Buffer into which the data is read.<br>
	 *          <br>
	 * @return Wrapped <code>java.io.InputStream#read(byte[])</code> response.<br>
	 *         <br>
	 * @exception IOException If wrapped
	 *                        <code>java.io.InputStream#read(byte[])</code> throws
	 *                        an exception.<br>
	 *                        <br>
	 */
	public int read(byte b[]) throws IOException {
		return stream.read(b);
	}

	/**
	 * Delegates execution to wrapped
	 * <code>java.io.InputStream#read(byte,int,int)</code>.
	 *
	 * @param b   Buffer into which the data is read.<br>
	 *            <br>
	 * @param off Start offset in array <code>b</code> at which the data is
	 *            written.<br>
	 *            <br>
	 * @param len Maximum number of bytes to read.<br>
	 *            <br>
	 * @return Wrapped <code>java.io.InputStream#read(byte,int,int</code>
	 *         response.<br>
	 *         <br>
	 * @exception IOException If wrapped
	 *                        <code>java.io.InputStream#read(byte,int,int)</code>
	 *                        throws an exception.<br>
	 *                        <br>
	 */
	public int read(byte b[], int off, int len) throws IOException {
		return stream.read(b, off, len);
	}

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#skip(int)</code>.
	 *
	 * @param n Number of bytes to be skipped.<br>
	 *          <br>
	 * @return Wrapped <code>java.io.InputStream#skip()</code> response.<br>
	 *         <br>
	 * @exception IOException If wrapped <code>java.io.InputStream#skip()</code>
	 *                        throws an exception.<br>
	 *                        <br>
	 */
	public long skip(long n) throws IOException {
		return stream.skip(n);
	}

	/**
	 * Returns the number of bytes that can be read (or skipped over) from wrapped
	 * input stream without blocking by the next caller of a method for this input
	 * stream. The next caller might be the same thread or another thread.
	 *
	 * @return Wrapped <code>java.io.InputStream#available()</code> response.<br>
	 *         <br>
	 * @exception IOException If wrapped
	 *                        <code>java.io.InputStream#available()</code> throws an
	 *                        exception.<br>
	 *                        <br>
	 */
	public int available() throws IOException {
		return stream.available();
	}

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#close()</code>.
	 *
	 * @exception IOException If wrapped <code>java.io.InputStream#close()</code>
	 *                        throws an exception.<br>
	 *                        <br>
	 */
	public void close() throws IOException {
		stream.close();
	}

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#mark(int)</code>.
	 *
	 * @param readlimit Maximum limit of bytes that can be read before the mark
	 *                  position becomes invalid.<br>
	 *                  <br>
	 */
	public synchronized void mark(int readlimit) {
		stream.mark(readlimit);
	}

	/**
	 * Delegates execution to wrapped <code>java.io.InputStream#reset()</code>.
	 *
	 * @exception IOException If wrapped <code>java.io.InputStream#reset()</code>
	 *                        throws an exception.<br>
	 *                        <br>
	 */
	public synchronized void reset() throws IOException {
		stream.reset();
	}

	/**
	 * Delegates execution to wrapped
	 * <code>java.io.InputStream#markSupported()</code>.
	 *
	 * @return Wrapped <code>java.io.InputStream#markSupported()</code>
	 *         response.<br>
	 *         <br>
	 */
	public boolean markSupported() {
		return stream.markSupported();
	}

}
