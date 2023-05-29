package com.warework.core.util.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.warework.core.util.CommonValueL1Constants;

/**
 * Performs common resources operations.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class ResourceL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// FILE EXTENSIONS

	/**
	 * Constant that identifies the file extension separator character.
	 */
	public static final String FILE_EXTENSION_SEPARATOR = StringL1Helper.CHARACTER_PERIOD;

	/**
	 * Constant that identifies the '.class' file extension.
	 */
	public static final String FILE_EXTENSION_CLASS = CommonValueL1Constants.STRING_CLASS;

	/**
	 * Constant that identifies the '.java' file extension.
	 */
	public static final String FILE_EXTENSION_JAVA = "java";

	/**
	 * Constant that identifies the '.jar' file extension.
	 */
	public static final String FILE_EXTENSION_JAR = "jar";

	/**
	 * Constant that identifies the '.json' file extension.
	 */
	public static final String FILE_EXTENSION_JSON = "json";

	/**
	 * Constant that identifies the '.js' file extension.
	 */
	public static final String FILE_EXTENSION_JS = "js";

	/**
	 * Constant that identifies the '.xml' file extension.
	 */
	public static final String FILE_EXTENSION_XML = "xml";

	// DIRECTORIES

	/**
	 * Constant that identifies the current directory.
	 */
	public static final String DIRECTORY_CURRENT = StringL1Helper.CHARACTER_PERIOD;

	/**
	 * Constant that identifies parent directory.
	 */
	public static final String DIRECTORY_PARENT = "..";

	/**
	 * Constant that identifies the directories separator character.
	 */
	public static final String DIRECTORY_SEPARATOR = StringL1Helper.CHARACTER_FORWARD_SLASH;

	/**
	 * Constant that identifies the 'lib' directory.
	 */
	public static final String DIRECTORY_LIBRARIES = "lib";

	/**
	 * Constant that identifies the 'META-INF' directory.
	 */
	public static final String DIRECTORY_META_INF = "META-INF";

	// MISC.

	// Default buffer size is 8K.
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ResourceL1Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Dumps an input stream into an array of bytes.
	 * 
	 * @param input Input stream to read.<br>
	 *              <br>
	 * @return The array of bytes with the content of the input stream.<br>
	 *         <br>
	 * @throws IOException If an I/O error occurs.<br>
	 *                     <br>
	 */
	public static byte[] toByteArray(final InputStream input) throws IOException {

		// Create an output stream to store the result.
		final ByteArrayOutputStream output = new ByteArrayOutputStream();

		// Create a buffer.
		final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

		// Read input stream.
		int read;
		while ((read = input.read(buffer, 0, buffer.length)) != -1) {
			output.write(buffer, 0, read);
		}

		// Forces any buffered output bytes to be written out.
		output.flush();

		// Return the array of bytes.
		return output.toByteArray();

	}

}
