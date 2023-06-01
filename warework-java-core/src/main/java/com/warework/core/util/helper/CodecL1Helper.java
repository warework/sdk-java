package com.warework.core.util.helper;

import java.io.IOException;

/**
 * Performs common codec operations.
 * 
 * @author Robert Harder (rob@iharder.net) for Base64 implementation.
 * @author Jose Schiaffino
 * @version ${project.version} (Base64 version 2.3.7)
 */
public abstract class CodecL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// RADIX TO PARSE VALUES

	private static final int RADIX_16 = 16;

	// VALUES TO SHIFT BITS

	private static final int SHIFT_4 = 4;

	private static final int SHIFT_6 = 6;

	private static final int SHIFT_8 = 8;

	private static final int SHIFT_12 = 12;

	private static final int SHIFT_16 = 16;

	private static final int SHIFT_18 = 18;

	private static final int SHIFT_24 = 24;

	// HEXADECIMAL DIGITS

	// Used to build output as Hex.
	private static final char[] HEXADECIMAL_DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	// Used to build output as Hex.
	private static final char[] HEXADECIMAL_DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
			'C', 'D', 'E', 'F' };

	// BASE64

	// The 64 valid Base64 values.
	private static final byte[] BASE64_STANDARD_ALPHABET = { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
			(byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
			(byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V', (byte) 'W',
			(byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
			(byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
			(byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
			(byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7', (byte) '8', (byte) '9', (byte) '+', (byte) '/' };

	// Translates a Base64 value to either its 6-bit reconstruction value or a
	// negative number indicating some other meaning.
	private static final byte[] BASE64_STANDARD_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
																// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			62, // Plus sign at decimal 43
			-9, -9, -9, // Decimal 44 - 46
			63, // Slash at decimal 47
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through
															// 'N'
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O'
															// through 'Z'
			-9, -9, -9, -9, -9, -9, // Decimal 91 - 96
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a'
																// through 'm'
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n'
																// through 'z'
			-9, -9, -9, -9, -9 // Decimal 123 - 127
			, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 128 -
																// 139
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 140 -
																// 152
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 153 -
																// 165
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 166 -
																// 178
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 179 -
																// 191
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 192 -
																// 204
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 205 -
																// 217
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 218 -
																// 230
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 231 -
																// 243
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255
	};

	// Used in the URL- and Filename-safe dialect described in Section 4 of
	// RFC3548: <a
	// href="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs.org/rfcs/rfc3548.html</a>.
	// Notice that the last two bytes become "hyphen" and "underscore" instead
	// of "plus" and "slash."
	private static final byte[] BASE64_URL_SAFE_ALPHABET = { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
			(byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
			(byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V', (byte) 'W',
			(byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
			(byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
			(byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
			(byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7', (byte) '8', (byte) '9', (byte) '-', (byte) '_' };

	// Used in decoding URL- and Filename-safe dialects of.
	private static final byte[] BASE64_URL_SAFE_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
																// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			-9, // Plus sign at decimal 43
			-9, // Decimal 44
			62, // Minus sign at decimal 45
			-9, // Decimal 46
			-9, // Slash at decimal 47
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through
															// 'N'
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O'
															// through 'Z'
			-9, -9, -9, -9, // Decimal 91 - 94
			63, // Underscore at decimal 95
			-9, // Decimal 96
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a'
																// through 'm'
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n'
																// through 'z'
			-9, -9, -9, -9, -9 // Decimal 123 - 127
			, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 128 -
																// 139
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 140 -
																// 152
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 153 -
																// 165
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 166 -
																// 178
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 179 -
																// 191
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 192 -
																// 204
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 205 -
																// 217
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 218 -
																// 230
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 231 -
																// 243
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255
	};

	// This technique is described here: <a
	// href="http://www.faqs.org/qa/rfcc-1940.html">http://www.faqs.org/qa/rfcc-1940.html</a>.
	private static final byte[] BASE64_ORDERED_ALPHABET = { (byte) '-', (byte) '0', (byte) '1', (byte) '2', (byte) '3',
			(byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C',
			(byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
			(byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
			(byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) '_', (byte) 'a', (byte) 'b', (byte) 'c',
			(byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l',
			(byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
			(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z' };

	// Used in decoding the "ordered" dialect of.
	private static final byte[] ORDERED_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
			-5, -5, // Whitespace: Tab and Linefeed
			-9, -9, // Decimal 11 - 12
			-5, // Whitespace: Carriage Return
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 -
																// 26
			-9, -9, -9, -9, -9, // Decimal 27 - 31
			-5, // Whitespace: Space
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
			-9, // Plus sign at decimal 43
			-9, // Decimal 44
			0, // Minus sign at decimal 45
			-9, // Decimal 46
			-9, // Slash at decimal 47
			1, 2, 3, 4, 5, 6, 7, 8, 9, 10, // Numbers zero through nine
			-9, -9, -9, // Decimal 58 - 60
			-1, // Equals sign at decimal 61
			-9, -9, -9, // Decimal 62 - 64
			11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, // Letters 'A'
																// through 'M'
			24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, // Letters 'N'
																// through 'Z'
			-9, -9, -9, -9, // Decimal 91 - 94
			37, // Underscore at decimal 95
			-9, // Decimal 96
			38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, // Letters 'a'
																// through 'm'
			51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, // Letters 'n'
																// through 'z'
			-9, -9, -9, -9, -9 // Decimal 123 - 127
			, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 128
																	// - 139
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 140 -
																// 152
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 153 -
																// 165
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 166 -
																// 178
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 179 -
																// 191
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 192 -
																// 204
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 205 -
																// 217
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 218 -
																// 230
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 231 -
																// 243
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255
	};

	// Indicates white space in encoding.
	private static final byte BASE64_WHITE_SPACE_ENC = -5;

	// Indicates equals sign in encoding.
	private static final byte BASE64_EQUALS_SIGN_ENC = -1;

	// No options specified. Value is zero.
	private static final int BASE64_NO_OPTIONS = 0;

	// Encode using Base64-like encoding that is URL- and Filename-safe as
	// described in Section 4 of RFC3548: <a
	// href="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs.org/rfcs/rfc3548.html</a>.
	// It is important to note that data encoded this way is <em>not</em>
	// officially valid Base64, or at the very least should not be called Base64
	// without also specifying that is was encoded using the URL- and
	// Filename-safe dialect.
	private static final int BASE64_URL_SAFE = 16;

	// Encode using the special "ordered" dialect of Base64 described here: <a
	// href="http://www.faqs.org/qa/rfcc-1940.html">http://www.faqs.org/qa/rfcc-
	// 1940.html</a>.
	private static final int BASE64_ORDERED = 32;

	// MISC.

	// The equals sign (=) as a byte.
	private static final byte BYTE_EQUALS_SIGN = (byte) '=';

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected CodecL1Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a given array of bytes into an array of characters that represents
	 * the hexadecimal values of each byte.
	 * 
	 * @param source Array of bytes to transform.<br>
	 *               <br>
	 * @return A char[] containing hexadecimal characters. The returned array will
	 *         be double the length of the passed array, as it takes two characters
	 *         to represent any given byte.<br>
	 *         <br>
	 */
	public static char[] encodeHex(final byte[] source) {
		return encodeHex(source, false);
	}

	/**
	 * Converts a given string into another string that represents the hexadecimal
	 * values of each character of the string.
	 * 
	 * @param source String to transform.<br>
	 *               <br>
	 * @return A string containing hexadecimal characters. The returned string will
	 *         be double the length of the passed string, as it takes two characters
	 *         to represent any given character.<br>
	 *         <br>
	 */
	public static String encodeHex(final String source) {

		// Encode string.
		final char[] result = encodeHex(source.getBytes());

		// Return encoded string.
		return (result != null) ? new String(result) : null;

	}

	/**
	 * Converts a given string into another string that represents the hexadecimal
	 * values of each character of the string.
	 * 
	 * @param source      String to transform.<br>
	 *                    <br>
	 * @param charsetName Source and target string charset encoding (i.e:
	 *                    UTF-8).<br>
	 *                    <br>
	 * @return A string containing hexadecimal characters. The returned string will
	 *         be double the length of the passed string, as it takes two characters
	 *         to represent any given character.<br>
	 *         <br>
	 * @throws IOException If the named charset is not supported.<br>
	 *                     <br>
	 */
	public static String encodeHex(final String source, final String charsetName) throws IOException {

		// Encode string.
		final char[] result = encodeHex(source.getBytes(charsetName));

		// Return encoded string.
		return (result != null) ? new String(new String(result).getBytes(charsetName), charsetName) : null;

	}

	/**
	 * Converts a given array of bytes into an array of characters that represents
	 * the hexadecimal values of each byte.
	 * 
	 * @param source      Array of bytes to transform.<br>
	 *                    <br>
	 * @param toUpperCase <code>true</code> transforms to uppercase each heaxdecimal
	 *                    letter of the result and <code>false</code> to
	 *                    lowercase.<br>
	 *                    <br>
	 * @return A char[] containing hexadecimal characters. The returned array will
	 *         be double the length of the passed array, as it takes two characters
	 *         to represent any given byte.<br>
	 *         <br>
	 */
	public static char[] encodeHex(final byte[] source, final boolean toUpperCase) {
		return (toUpperCase) ? byteToHex(source, HEXADECIMAL_DIGITS_UPPER)
				: byteToHex(source, HEXADECIMAL_DIGITS_LOWER);
	}

	/**
	 * Converts a given string into another string that represents the hexadecimal
	 * values of each character of the string.
	 * 
	 * @param source      String to transform.<br>
	 *                    <br>
	 * @param toUpperCase <code>true</code> transforms to uppercase each heaxdecimal
	 *                    letter of the result and <code>false</code> to
	 *                    lowercase.<br>
	 *                    <br>
	 * @return A string containing hexadecimal characters. The returned string will
	 *         be double the length of the passed string, as it takes two characters
	 *         to represent any given character.<br>
	 *         <br>
	 */
	public static String encodeHex(final String source, final boolean toUpperCase) {

		// Encode string.
		final char[] result = encodeHex(source.getBytes(), toUpperCase);

		// Return encoded string.
		return (result != null) ? new String(result) : null;

	}

	/**
	 * Converts a given string into another string that represents the hexadecimal
	 * values of each character of the string.
	 * 
	 * @param source      String to transform.<br>
	 *                    <br>
	 * @param charsetName Source and target string charset encoding (i.e:
	 *                    UTF-8).<br>
	 *                    <br>
	 * @param toUpperCase <code>true</code> transforms to uppercase each heaxdecimal
	 *                    letter of the result and <code>false</code> to
	 *                    lowercase.<br>
	 *                    <br>
	 * @return A string containing hexadecimal characters. The returned string will
	 *         be double the length of the passed string, as it takes two characters
	 *         to represent any given character.<br>
	 *         <br>
	 * @throws IOException If the named charset is not supported.<br>
	 *                     <br>
	 */
	public static String encodeHex(final String source, final String charsetName, final boolean toUpperCase)
			throws IOException {

		// Encode string.
		final char[] result = encodeHex(source.getBytes(charsetName), toUpperCase);

		// Return encoded string.
		return (result != null) ? new String(new String(result).getBytes(charsetName), charsetName) : null;

	}

	/**
	 * Converts a given array of characters that represents hexadecimal values into
	 * an array of bytes.
	 * 
	 * @param source Array of hexadecimal characters to transform.<br>
	 *               <br>
	 * @return A byte array containing binary data decoded from the supplied char
	 *         array. Returned array will be half the length of the passed array, as
	 *         it takes two characters to represent any given byte. This method
	 *         returns <code>null</code> if an odd number or illegal of characters
	 *         is supplied.<br>
	 *         <br>
	 */
	public static byte[] decodeHex(final char[] source) {

		// Get the size of the source data.
		final int length = source.length;

		// Validate given array of hexadecimal characters.
		if ((length & 0x01) != 0) {
			return null;
		}

		// Create the target array for the result.
		final byte[] target = new byte[length >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < length; i = i + 1) {

			// Convert the hexadecimal character to an integer.
			int digit = Character.digit(source[j], RADIX_16) << SHIFT_4;
			if (digit == -1) {
				return null;
			}

			// Next position in the source array.
			j = j + 1;

			// Update digit if required.
			digit = digit | Character.digit(source[j], RADIX_16);
			if (digit == -1) {
				return null;
			}

			// Next position in the source array.
			j = j + 1;

			// Set the byte value into the target array.
			target[i] = (byte) (digit & 0xFF);

		}

		// Return decoded data.
		return target;

	}

	/**
	 * Converts a given string that represents hexadecimal values into another
	 * string.
	 * 
	 * @param source String of hexadecimal characters to transform.<br>
	 *               <br>
	 * @return A string containing binary data decoded from the supplied string.
	 *         Returned string will be half the length of the passed array, as it
	 *         takes two characters to represent any given character in the string.
	 *         This method returns <code>null</code> if an odd number or illegal of
	 *         characters is supplied.<br>
	 *         <br>
	 */
	public static String decodeHex(final String source) {

		// Decode string.
		final byte[] result = decodeHex(source.toCharArray());

		// Return decoded string.
		return (result != null) ? new String(result) : null;

	}

	/**
	 * Converts a given string that represents hexadecimal values into another
	 * string.
	 * 
	 * @param source      String of hexadecimal characters to transform.<br>
	 *                    <br>
	 * @param charsetName Source and target string charset encoding (i.e:
	 *                    UTF-8).<br>
	 *                    <br>
	 * @return A string containing binary data decoded from the supplied string.
	 *         Returned string will be half the length of the passed array, as it
	 *         takes two characters to represent any given character in the string.
	 *         This method returns <code>null</code> if an odd number or illegal of
	 *         characters is supplied.<br>
	 *         <br>
	 * @throws IOException If the named charset is not supported.<br>
	 *                     <br>
	 */
	public static String decodeHex(final String source, final String charsetName) throws IOException {

		// Decode string.
		final byte[] result = decodeHex(source.toCharArray());

		// Return decoded string.
		return (result != null) ? new String(new String(result).getBytes(charsetName), charsetName) : null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Encodes a byte array into Base64 notation.
	 * 
	 * @param source The data to convert.<br>
	 *               <br>
	 * @return The Base64-encoded data as a byte[] (of ASCII characters).<br>
	 *         <br>
	 */
	public static byte[] encodeBase64(final byte[] source) {
		try {
			return encodeBytesToBytes(source, 0, source.length, BASE64_NO_OPTIONS);
		} catch (final IOException e) {
			return null;
		}
	}

	/**
	 * Encodes a string into Base64 notation.
	 * 
	 * @param source The string to convert.<br>
	 *               <br>
	 * @return The Base64-encoded data as a string (of ASCII characters).<br>
	 *         <br>
	 */
	public static String encodeBase64(final String source) {

		// Encode string.
		final byte[] result = encodeBase64(source.getBytes());

		// Return encoded string.
		return (result != null) ? new String(result) : null;

	}

	/**
	 * Encodes a string into Base64 notation.
	 * 
	 * @param source      The string to convert.<br>
	 *                    <br>
	 * @param charsetName Source and target string charset encoding (i.e:
	 *                    UTF-8).<br>
	 *                    <br>
	 * @return The Base64-encoded data as a string (of ASCII characters).<br>
	 *         <br>
	 * @throws IOException If the named charset is not supported.<br>
	 *                     <br>
	 */
	public static String encodeBase64(final String source, final String charsetName) throws IOException {

		// Encode string.
		final byte[] result = encodeBase64(source.getBytes(charsetName));

		// Return encoded string.
		return (result != null) ? new String(result, charsetName) : null;

	}

	/**
	 * Decodes ASCII characters in the form of a byte array.
	 * 
	 * @param source The Base64 encoded data.<br>
	 *               <br>
	 * @return Decoded data.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to decode the data.<br>
	 *                     <br>
	 */
	public static byte[] decodeBase64(final byte[] source) throws IOException {
		return decode(source, 0, source.length, BASE64_NO_OPTIONS);
	}

	/**
	 * Decodes a string of ASCII characters.
	 * 
	 * @param source The Base64 encoded data.<br>
	 *               <br>
	 * @return Decoded data.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to decode the data.<br>
	 *                     <br>
	 */
	public static String decodeBase64(final String source) throws IOException {

		// Decode string.
		final byte[] result = decodeBase64(source.getBytes());

		// Return dencoded string.
		return (result != null) ? new String(result) : null;

	}

	/**
	 * Decodes a string of ASCII characters.
	 * 
	 * @param source      The Base64 encoded data.<br>
	 *                    <br>
	 * @param charsetName Source and target string charset encoding (i.e:
	 *                    UTF-8).<br>
	 *                    <br>
	 * @return Decoded data.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to decode the data.<br>
	 *                     <br>
	 */
	public static String decodeBase64(final String source, final String charsetName) throws IOException {

		// Decode string.
		final byte[] result = decodeBase64(source.getBytes(charsetName));

		// Return dencoded string.
		return (result != null) ? new String(result, charsetName) : null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a given array of bytes into an array of characters that represents
	 * the hexadecimal values of each byte.
	 * 
	 * @param source   Array of bytes to transform.<br>
	 *                 <br>
	 * @param toDigits Output alphabet.<br>
	 *                 <br>
	 * @return A char[] containing hexadecimal characters. The returned array will
	 *         be double the length of the passed array, as it takes two characters
	 *         to represent any given byte.<br>
	 *         <br>
	 */
	private static char[] byteToHex(final byte[] source, final char[] toDigits) {

		// Get the size of the source data.
		final int length = source.length;

		// Create the target array for the result.
		final char[] target = new char[length << 1];

		// Encode data.
		for (int i = 0, j = 0; i < length; i++) {

			// Get the hexadecimal value.
			target[j++] = toDigits[(0xF0 & source[i]) >>> SHIFT_4];

			// Get the hexadecimal value.
			target[j++] = toDigits[0x0F & source[i]];

		}

		// Return encoded data.
		return target;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Encodes up to three bytes of the array <var>source</var> and writes the
	 * resulting four Base64 bytes to <var>destination</var>. The source and
	 * destination arrays can be manipulated anywhere along their length by
	 * specifying <var>srcOffset</var> and <var>destOffset</var>. This method does
	 * not check to make sure your arrays are large enough to accomodate
	 * <var>srcOffset</var> + 3 for the <var>source</var> array or
	 * <var>destOffset</var> + 4 for the <var>destination</var> array. The actual
	 * number of significant bytes in your array is given by
	 * <var>numSigBytes</var>.<br>
	 * <br>
	 * This is the lowest level of the encoding methods with all possible
	 * parameters.
	 * 
	 * @param source      the array to convert.<br>
	 *                    <br>
	 * @param srcOffset   the index where conversion begins.<br>
	 *                    <br>
	 * @param numSigBytes the number of significant bytes in your array.<br>
	 *                    <br>
	 * @param destination the array to hold the conversion.<br>
	 *                    <br>
	 * @param destOffset  the index where output will be put.<br>
	 *                    <br>
	 * @return the <var>destination</var> array.<br>
	 *         <br>
	 */
	private static byte[] encode3to4(final byte[] source, final int srcOffset, final int numSigBytes,
			final byte[] destination, final int destOffset, final int options) {

		// Get the BASE64 alphabet.
		final byte[] alphabet = getAlphabet(options);

		// 1 2 3
		// 01234567890123456789012345678901 Bit position
		// --------000000001111111122222222 Array position from threeBytes
		// --------| || || || | Six bit groups to index ALPHABET
		// >>18 >>12 >> 6 >> 0 Right shift necessary
		// 0x3f 0x3f 0x3f Additional AND

		// Create buffer with zero-padding if there are only one or two
		// significant bytes passed in the array.
		// We have to shift left 24 in order to flush out the 1's that appear
		// when Java treats a value as negative that is cast from a byte to an
		// int.
		final int inBuff = (numSigBytes > 0 ? ((source[srcOffset] << SHIFT_24) >>> SHIFT_8) : 0)
				| (numSigBytes > 1 ? ((source[srcOffset + 1] << SHIFT_24) >>> SHIFT_16) : 0)
				| (numSigBytes > 2 ? ((source[srcOffset + 2] << SHIFT_24) >>> SHIFT_24) : 0);

		switch (numSigBytes) {
		case 3:
			destination[destOffset] = alphabet[(inBuff >>> SHIFT_18)];
			destination[destOffset + 1] = alphabet[(inBuff >>> SHIFT_12) & 0x3f];
			destination[destOffset + 2] = alphabet[(inBuff >>> SHIFT_6) & 0x3f];
			destination[destOffset + 3] = alphabet[(inBuff) & 0x3f];
			return destination;

		case 2:
			destination[destOffset] = alphabet[(inBuff >>> SHIFT_18)];
			destination[destOffset + 1] = alphabet[(inBuff >>> SHIFT_12) & 0x3f];
			destination[destOffset + 2] = alphabet[(inBuff >>> SHIFT_6) & 0x3f];
			destination[destOffset + 3] = BYTE_EQUALS_SIGN;
			return destination;

		case 1:
			destination[destOffset] = alphabet[(inBuff >>> SHIFT_18)];
			destination[destOffset + 1] = alphabet[(inBuff >>> SHIFT_12) & 0x3f];
			destination[destOffset + 2] = BYTE_EQUALS_SIGN;
			destination[destOffset + 3] = BYTE_EQUALS_SIGN;
			return destination;

		default:
			return destination;
		}
	}

	/**
	 * Gets one of the BASE64_SOMETHING_ALPHABET byte arrays depending on the
	 * options specified. It's possible, though silly, to specify BASE64_ORDERED
	 * <strong>and</strong> BASE64_URL_SAFE in which case one of them will be
	 * picked, though there is no guarantee as to which one will be picked.
	 * 
	 * @param options Alphabet selector.<br>
	 *                <br>
	 * @return Base64 alphabet.<br>
	 *         <br>
	 */
	private static byte[] getAlphabet(final int options) {
		if ((options & BASE64_URL_SAFE) == BASE64_URL_SAFE) {
			return BASE64_URL_SAFE_ALPHABET;
		} else if ((options & BASE64_ORDERED) == BASE64_ORDERED) {
			return BASE64_ORDERED_ALPHABET;
		} else {
			return BASE64_STANDARD_ALPHABET;
		}
	}

	/**
	 * Decodes four bytes from array <var>source</var> and writes the resulting
	 * bytes (up to three of them) to <var>destination</var>. The source and
	 * destination arrays can be manipulated anywhere along their length by
	 * specifying <var>srcOffset</var> and <var>destOffset</var>. This method does
	 * not check to make sure your arrays are large enough to accomodate
	 * <var>srcOffset</var> + 4 for the <var>source</var> array or
	 * <var>destOffset</var> + 3 for the <var>destination</var> array. This method
	 * returns the actual number of bytes that were converted from the Base64
	 * encoding. This is the lowest level of the decoding methods with all possible
	 * parameters.
	 * 
	 * @param source      The array to convert.<br>
	 *                    <br>
	 * @param srcOffset   The index where conversion begins.<br>
	 *                    <br>
	 * @param destination The array to hold the conversion.<br>
	 *                    <br>
	 * @param destOffset  The index where output will be put.<br>
	 *                    <br>
	 * @param options     Alphabet type is pulled from this (standard, url-safe,
	 *                    ordered).<br>
	 *                    <br>
	 * @return Number of decoded bytes converted.<br>
	 *         <br>
	 */
	private static int decode4to3(final byte[] source, final int srcOffset, final byte[] destination,
			final int destOffset, final int options) {

		// Validate source.
		if (source == null) {
			throw new IllegalArgumentException("Source array was null.");
		}

		// Validate destination.
		if (destination == null) {
			throw new IllegalArgumentException("Destination array was null.");
		}

		// Validate source offset.
		if (srcOffset < 0 || srcOffset + 3 >= source.length) {
			throw new IllegalArgumentException("Source array cannot have given offset and still process four bytes.");
		}

		// Validate destination offset.
		if (destOffset < 0 || destOffset + 2 >= destination.length) {
			throw new IllegalArgumentException("Destination array cannot have offset and still store three bytes.");
		}

		// Get the decode alphabet.
		final byte[] decodabet = getDecodabet(options);

		// Example: Dk==
		if (source[srcOffset + 2] == BYTE_EQUALS_SIGN) {

			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
			int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << SHIFT_18)
					| ((decodabet[source[srcOffset + 1]] & 0xFF) << SHIFT_12);

			// Fill the array that holds the conversion.
			destination[destOffset] = (byte) (outBuff >>> 16);

			// Return the number of decoded bytes converted.
			return 1;

		} else if (source[srcOffset + 3] == BYTE_EQUALS_SIGN) {

			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
			// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
			int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << SHIFT_18)
					| ((decodabet[source[srcOffset + 1]] & 0xFF) << SHIFT_12)
					| ((decodabet[source[srcOffset + 2]] & 0xFF) << SHIFT_6);

			// Fill the array that holds the conversion.
			destination[destOffset] = (byte) (outBuff >>> SHIFT_16);
			destination[destOffset + 1] = (byte) (outBuff >>> SHIFT_8);

			// Return the number of decoded bytes converted.
			return 2;

		} else {

			// Two ways to do the same thing. Don't know which way I like best.
			// int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6
			// )
			// | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
			// | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
			// | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
			int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << SHIFT_18)
					| ((decodabet[source[srcOffset + 1]] & 0xFF) << SHIFT_12)
					| ((decodabet[source[srcOffset + 2]] & 0xFF) << SHIFT_6)
					| ((decodabet[source[srcOffset + 3]] & 0xFF));

			// Fill the array that holds the conversion.
			destination[destOffset] = (byte) (outBuff >> SHIFT_16);
			destination[destOffset + 1] = (byte) (outBuff >> SHIFT_8);
			destination[destOffset + 2] = (byte) (outBuff);

			// Return the number of decoded bytes converted.
			return 3;

		}
	}

	/**
	 * Gets one of the BASE64_SOMETHING_DECODABET byte arrays depending on the
	 * options specified. It's possible, though silly, to specify BASE64_URL_SAFE
	 * and BASE64_URL_SAFE in which case one of them will be picked, though there is
	 * no guarantee as to which one will be picked.
	 * 
	 * @param options Alphabet selector.<br>
	 *                <br>
	 * @return Base64 decode alphabet.<br>
	 *         <br>
	 */
	private static byte[] getDecodabet(final int options) {
		if ((options & BASE64_URL_SAFE) == BASE64_URL_SAFE) {
			return BASE64_URL_SAFE_DECODABET;
		} else if ((options & BASE64_ORDERED) == BASE64_ORDERED) {
			return ORDERED_DECODABET;
		} else {
			return BASE64_STANDARD_DECODABET;
		}
	}

	/**
	 * Encodes a byte array into Base64 notation.
	 * 
	 * @param source  The data to convert.<br>
	 *                <br>
	 * @param offset  Offset in array where conversion should begin.<br>
	 *                <br>
	 * @param length  Length of data to convert.<br>
	 *                <br>
	 * @param options Specified options.<br>
	 *                <br>
	 * @return The Base64-encoded data as a String.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to encode the data.<br>
	 *                     <br>
	 */
	private static byte[] encodeBytesToBytes(final byte[] source, final int offset, final int length, final int options)
			throws IOException {

		// Validate source.
		if (source == null) {
			throw new IllegalArgumentException("Cannot serialize a null array.");
		}

		// Validate offset.
		if (offset < 0) {
			throw new IllegalArgumentException("Cannot have negative offset: " + offset);
		}

		// Validate length.
		if (length < 0) {
			throw new IllegalArgumentException("Cannot have length offset: " + length);
		}

		// Validate offset + length size.
		if (offset + length > source.length) {
			throw new IllegalArgumentException("Invalid offset and length");
		}

		// Bytes needed for actual encoding.
		final int encLen = (length / 3) * 4 + (length % 3 > 0 ? 4 : 0);

		// Create an output buffer.
		final byte[] outBuff = new byte[encLen];

		//
		int d = 0;

		//
		int e = 0;

		//
		final int newLength = length - 2;

		//
		for (; d < newLength; d += 3, e += 4) {
			encode3to4(source, d + offset, 3, outBuff, e, options);
		}

		//
		if (d < length) {

			//
			encode3to4(source, d + offset, length - d, outBuff, e, options);

			//
			e = e + 4;

		}

		// Only resize array if we didn't guess it right.
		if (e <= outBuff.length - 1) {

			// If breaking lines and the last byte falls right at
			// the line length (76 bytes per line), there will be
			// one extra byte, and the array will need to be resized.
			// Not too bad of an estimate on array size, I'd say.
			byte[] finalOut = new byte[e];

			//
			System.arraycopy(outBuff, 0, finalOut, 0, e);

			// Return the Base64-encoded data as a String.
			return finalOut;

		} else {
			return outBuff;
		}

	}

	/**
	 * Low-level access to decoding ASCII characters in the form of a byte array.
	 * <strong>Ignores GUNZIP option, if it's set.</strong> This is not generally a
	 * recommended method, although it is used internally as part of the decoding
	 * process. Special case: if len = 0, an empty array is returned. Still, if you
	 * need more speed and reduced memory footprint (and aren't gzipping), consider
	 * this method.
	 * 
	 * @param source  The Base64 encoded data.<br>
	 *                <br>
	 * @param offset  The offset of where to begin decoding.<br>
	 *                <br>
	 * @param length  The length of characters to decode.<br>
	 *                <br>
	 * @param options Can specify options such as alphabet type to use.<br>
	 *                <br>
	 * @return Decoded data.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to decode the data.<br>
	 *                     <br>
	 */
	private static byte[] decode(final byte[] source, final int offset, final int length, final int options)
			throws IOException {

		// Validate source.
		if (source == null) {
			throw new IllegalArgumentException("Cannot decode null source array.");
		}

		// Validate offset.
		if (offset < 0 || offset + length > source.length) {
			throw new IllegalArgumentException("Invalid offset");
		}

		// Validate length.
		if (length == 0) {
			return new byte[0];
		} else if (length < 4) {
			throw new IllegalArgumentException(
					"Base64-encoded string must have at least four characters, but length specified was " + length);
		}

		// Get the Base64 decode alphabet.
		final byte[] decodabet = getDecodabet(options);

		// Estimate on array size.
		final int len34 = length * 3 / 4;

		// Upper limit on size of output.
		final byte[] outBuff = new byte[len34];

		// Keep track of where we're writing.
		int outBuffPosn = 0;

		// Four byte buffer from source, eliminating white space.
		final byte[] b4 = new byte[4];

		// Keep track of four byte input buffer.
		int b4Posn = 0;

		// Source array counter.
		int i = 0;

		// Special value from DECODABET.
		byte sbiDecode = 0;

		// Loop through source.
		for (i = offset; i < offset + length; i++) {

			//
			sbiDecode = decodabet[source[i] & 0xFF];

			// White space, Equals sign, or legit Base64 character
			// Note the values such as -5 and -9 in the
			// DECODABETs at the top of the file.
			if (sbiDecode >= BASE64_WHITE_SPACE_ENC) {
				if (sbiDecode >= BASE64_EQUALS_SIGN_ENC) {

					// Save non-whitespace.
					b4[b4Posn++] = source[i];

					// Time to decode?
					if (b4Posn > 3) {

						//
						outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);

						//
						b4Posn = 0;

						// If that was the equals sign, break out of 'for' loop
						if (source[i] == BYTE_EQUALS_SIGN) {
							break;
						}

					}
				}
			} else {
				throw new IOException("Bad Base64 input character decimal");
			}
		}

		//
		final byte[] out = new byte[outBuffPosn];

		//
		System.arraycopy(outBuff, 0, out, 0, outBuffPosn);

		//
		return out;

	}

}
