package com.warework.core.util.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.io.LengthInputStream;

/**
 * Performs common strings operations.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class StringL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// All possible chars for representing a number as a String.
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	// CONSTANTS FOR SIMPLE ID CREATION

	private static final int SIMPLE_ID_SHIFT_TO_HEX = 4;

	private static final int SIMPLE_ID_BUFFER_SIZE = 64;

	private static final int SIMPLE_ID_MASK = (1 << SIMPLE_ID_SHIFT_TO_HEX) - 1;

	// CHARACTERS

	/**
	 * Character '&amp;'.
	 */
	public static final String CHARACTER_AMPERSAND = "&";

	/**
	 * Character ':'.
	 */
	public static final String CHARACTER_COLON = ":";

	/**
	 * Character ','.
	 */
	public static final String CHARACTER_COMMA = ",";

	/**
	 * Character '"'.
	 */
	public static final String CHARACTER_DOUBLE_QUOTE = "\"";

	/**
	 * Character '.'.
	 */
	public static final String CHARACTER_PERIOD = ".";

	/**
	 * Character '='.
	 */
	public static final String CHARACTER_EQUALS = "=";

	/**
	 * Character '!'.
	 */
	public static final String CHARACTER_EXCLAMATION = "!";

	/**
	 * Character '/'.
	 */
	public static final String CHARACTER_FORWARD_SLASH = "/";

	/**
	 * Character '&gt;'.
	 */
	public static final String CHARACTER_GREATER_THAN = ">";

	/**
	 * Character '-'.
	 */
	public static final String CHARACTER_HYPHEN = "-";

	/**
	 * Character '('.
	 */
	public static final String CHARACTER_LEFT_PARENTHESES = "(";

	/**
	 * Character '&lt;'.
	 */
	public static final String CHARACTER_LESS_THAN = "<";

	/**
	 * Character '\n'.
	 */
	public static final String CHARACTER_NEW_LINE = "\n";

	/**
	 * Character '?'.
	 */
	public static final String CHARACTER_QUESTION = "?";

	/**
	 * Character '}'.
	 */
	public static final String CHARACTER_RIGHT_CURLY_BRACKET = "}";

	/**
	 * Character ')'.
	 */
	public static final String CHARACTER_RIGHT_PARENTHESES = ")";

	/**
	 * Character ';'.
	 */
	public static final String CHARACTER_SEMICOLON = ";";

	/**
	 * Character "'".
	 */
	public static final String CHARACTER_SINGLE_QUOTE = "'";

	/**
	 * Character ' '.
	 */
	public static final String CHARACTER_SPACE = " ";

	/**
	 * Character '_'.
	 */
	public static final String CHARACTER_UNDERSCORE = "_";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * JavaScript compressor.
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	private static class JavaScriptCompressor {

		// ///////////////////////////////////////////////////////////////////
		// CONSTANTS
		// ///////////////////////////////////////////////////////////////////

		// End of file character.
		private static final int EOF = -1;

		// CHARACTERS

		private final static char CHARACTER_0 = '0';

		private final static char CHARACTER_9 = '9';

		private final static char CHARACTER_a = 'a';

		private final static char CHARACTER_z = 'z';

		private final static char CHARACTER_A = 'A';

		private final static char CHARACTER_Z = 'Z';

		private final static char CHARACTER_AMPERSAND = '&';

		private final static char CHARACTER_ASTERISK = '*';

		private final static char CHARACTER_BACKSLASH = '\\';

		private final static char CHARACTER_CARRIAGE_RETURN = '\r';

		private final static char CHARACTER_COLON = ':';

		private final static char CHARACTER_COMMA = ',';

		private final static char CHARACTER_DOLLAR = '$';

		private final static char CHARACTER_DOUBLE_QUOTE = '"';

		private final static char CHARACTER_EQUAL = '=';

		private final static char CHARACTER_LEFT_CURLY_BRACKET = '{';

		private final static char CHARACTER_LEFT_PARENTHESIS = '(';

		private final static char CHARACTER_LEFT_SQUARE_BRACKET = '[';

		private final static char CHARACTER_MINUS = '-';

		private final static char CHARACTER_NEW_LINE = '\n';

		private final static char CHARACTER_PLUS = '+';

		private final static char CHARACTER_QUESTION = '?';

		private final static char CHARACTER_RIGHT_CURLY_BRACKET = '}';

		private final static char CHARACTER_RIGHT_PARENTHESIS = ')';

		private final static char CHARACTER_RIGHT_SQUARE_BRACKET = ']';

		private final static char CHARACTER_SINGLE_QUOTE = '\'';

		private final static char CHARACTER_SPACE = ' ';

		private final static char CHARACTER_UNDERSCORE = '_';

		private final static char CHARACTER_VERTICAL_BAR = '|';

		// ///////////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////////

		// Input stream for the JavaScript source.
		private PushbackInputStream in;

		// Output stream for the compressed JavaScript.
		private ByteArrayOutputStream out;

		//
		private int theA;

		//
		private int theB;

		// ///////////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////////

		/**
		 * Minimizes a JavaScript code.
		 * 
		 * @param source Source JavaScript to compress.<br>
		 *               <br>
		 * @return Minimized JavaScript.<br>
		 *         <br>
		 * @throws IOException If there is an error when trying to perform the
		 *                     transformation.<br>
		 *                     <br>
		 */
		public String compress(final String javascript) throws IOException {

			// Clean previous operations.
			reset();

			// Load the JavaScript into the input stream.
			in = new PushbackInputStream(new ByteArrayInputStream(javascript.getBytes()));

			// Setup the output stream where to store the compressed
			// JavaScript.
			out = new ByteArrayOutputStream();

			// Initialize the first character.
			theA = CHARACTER_NEW_LINE;

			//
			nextB();

			//
			while (theA != EOF) {
				switch (theA) {
				case CHARACTER_SPACE:

					//
					if (isAlphanum(theB)) {
						outputA();
					} else {
						copyBtoA();
					}

					//
					break;

				case CHARACTER_NEW_LINE:

					//
					switch (theB) {
					case CHARACTER_LEFT_CURLY_BRACKET:
					case CHARACTER_LEFT_SQUARE_BRACKET:
					case CHARACTER_LEFT_PARENTHESIS:
					case CHARACTER_PLUS:
					case CHARACTER_MINUS:

						//
						outputA();

						//
						break;

					case CHARACTER_SPACE:

						//
						nextB();

						//
						break;

					default:
						if (isAlphanum(theB)) {
							outputA();
						} else {
							copyBtoA();
						}
					}

					//
					break;

				default:

					//
					switch (theB) {
					case CHARACTER_SPACE:

						//
						if (isAlphanum(theA)) {

							//
							outputA();

							//
							break;
						}

						//
						nextB();

						//
						break;

					case CHARACTER_NEW_LINE:

						//
						switch (theA) {
						case CHARACTER_RIGHT_CURLY_BRACKET:
						case CHARACTER_RIGHT_SQUARE_BRACKET:
						case CHARACTER_RIGHT_PARENTHESIS:
						case CHARACTER_PLUS:
						case CHARACTER_MINUS:
						case CHARACTER_DOUBLE_QUOTE:
						case CHARACTER_SINGLE_QUOTE:

							//
							outputA();

							//
							break;

						default:
							if (isAlphanum(theA)) {
								outputA();
							} else {
								nextB();
							}
						}

						//
						break;

					default:

						//
						outputA();

						//
						break;

					}

				}
			}

			//
			out.flush();

			//
			in.close();

			//
			final String jsCompressed = new String(out.toByteArray());

			//
			reset();

			// Nothing to return at this point.
			return jsCompressed;

		}

		// ///////////////////////////////////////////////////////////////////
		// PRIVATE METHODS
		// ///////////////////////////////////////////////////////////////////

		/**
		 * Validates if a given character is a letter, digit, underscore, dollar sign,
		 * or non-ASCII character.
		 * 
		 * @param character Character to validate.
		 * @return <code>true</code> if the character is a letter, digit, underscore,
		 *         dollar sign, or non-ASCII character.<br>
		 *         <br>
		 */
		private boolean isAlphanum(final int character) {
			return ((character >= CHARACTER_a && character <= CHARACTER_z)
					|| (character >= CHARACTER_0 && character <= CHARACTER_9)
					|| (character >= CHARACTER_A && character <= CHARACTER_Z) || character == CHARACTER_UNDERSCORE
					|| character == CHARACTER_DOLLAR || character == CHARACTER_BACKSLASH || character > 126);
		}

		/**
		 * Gets the next character from the input stream. Watch out for lookahead. If
		 * the character is a control character, translate it to a space or linefeed.
		 * 
		 * @return Next character from the input stream.<br>
		 *         <br>
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private int nextCharacter() throws IOException {

			// Read the character from the stream.
			final int character = in.read();

			// Return the character.
			if (character >= CHARACTER_SPACE || character == CHARACTER_NEW_LINE || character == EOF) {
				return character;
			} else if (character == CHARACTER_CARRIAGE_RETURN) {
				return CHARACTER_NEW_LINE;
			} else {
				return CHARACTER_SPACE;
			}

		}

		/**
		 * Gets the next character from the input stream, excluding comments (validates
		 * if a '/' is followed by a '/' or '*').
		 * 
		 * @return Next character from the input stream.<br>
		 *         <br>
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private int nextCharacterExcludeComments() throws IOException {

			// Get the next character from the input stream.
			int character = nextCharacter();

			//
			if (character == CommonValueL1Constants.CHAR_FORWARD_SLASH) {
				switch (peek()) {
				case CommonValueL1Constants.CHAR_FORWARD_SLASH:
					for (;;) {

						// Get the next character.
						character = nextCharacter();

						//
						if (character <= CHARACTER_NEW_LINE) {
							return character;
						}

					}
				case CHARACTER_ASTERISK:

					// Move to next character.
					nextCharacter();

					//
					for (;;) {
						switch (nextCharacter()) {
						case CHARACTER_ASTERISK:

							//
							if (peek() == CommonValueL1Constants.CHAR_FORWARD_SLASH) {

								// Move to next character.
								nextCharacter();

								// Return a space.
								return CHARACTER_SPACE;

							}

							// End loop.
							break;

						case EOF:
							throw new IOException(
									"WAREWORK cannot compress JavaScript because a comment is not terminated.");
						}
					}

				default:
					return character;
				}
			}

			// Return the character.
			return character;

		}

		/**
		 * Get the next character without getting it.
		 * 
		 * @return Next character from the input stream.<br>
		 *         <br>
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private int peek() throws IOException {

			// Read the character from the stream.
			final int character = in.read();

			// Push back the character in the stream.
			in.unread(character);

			// Return the character.
			return character;

		}

		/**
		 * Write character A in the output.
		 * 
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private void outputA() throws IOException {

			// Write character A in the output.
			out.write(theA);

			// Copy character B into A.
			copyBtoA();

		}

		/**
		 * Copy character B into A.
		 * 
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private void copyBtoA() throws IOException {

			//
			theA = theB;

			//
			if ((theA == CHARACTER_SINGLE_QUOTE) || (theA == CHARACTER_DOUBLE_QUOTE)) {
				for (;;) {

					//
					out.write(theA);

					//
					theA = nextCharacter();

					//
					if (theA == theB) {
						break;
					}

					//
					if (theA <= CHARACTER_NEW_LINE) {
						throw new IOException(
								"WAREWORK cannot compress JavaScript because a string literal is not terminated.");
					}

					//
					if (theA == CHARACTER_BACKSLASH) {

						//
						out.write(theA);

						//
						theA = nextCharacter();

					}

				}
			}

			//
			nextB();

		}

		/**
		 * Next character.
		 * 
		 * @throws IOException If there is an error when trying to parse the JavaScript
		 *                     code.<br>
		 *                     <br>
		 */
		private void nextB() throws IOException {

			//
			theB = nextCharacterExcludeComments();

			//
			if (theB == CommonValueL1Constants.CHAR_FORWARD_SLASH && (theA == CHARACTER_LEFT_PARENTHESIS
					|| theA == CHARACTER_COMMA || theA == CHARACTER_EQUAL || theA == CHARACTER_COLON
					|| theA == CHARACTER_LEFT_SQUARE_BRACKET || theA == CommonValueL1Constants.CHAR_EXCLAMATION
					|| theA == CHARACTER_AMPERSAND || theA == CHARACTER_VERTICAL_BAR || theA == CHARACTER_QUESTION
					|| theA == CHARACTER_LEFT_CURLY_BRACKET || theA == CHARACTER_RIGHT_CURLY_BRACKET
					|| theA == CommonValueL1Constants.CHAR_SEMICOLON || theA == CHARACTER_NEW_LINE)) {

				//
				out.write(theA);

				//
				out.write(theB);

				//
				for (;;) {

					//
					theA = nextCharacter();

					//
					if (theA == CommonValueL1Constants.CHAR_FORWARD_SLASH) {
						break;
					} else if (theA == CHARACTER_BACKSLASH) {

						//
						out.write(theA);

						//
						theA = nextCharacter();

					} else if (theA <= CHARACTER_NEW_LINE) {
						throw new IOException(
								"WAREWORK cannot compress JavaScript because a regular expresion literal is not terminated.");
					}

					//
					out.write(theA);

				}

				//
				theB = nextCharacterExcludeComments();

			}

		}

		/**
		 * Reset variables.
		 */
		private void reset() {
			in = null;
			out = null;
			theA = 0;
			theB = 0;
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Last time an ID was created.
	private static long lastTime = System.currentTimeMillis();

	// Counter for the ID.
	private static int counterID = -1;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected StringL1Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts all of the strings to upper case.
	 * 
	 * @param words Strings to convert.<br>
	 *              <br>
	 * @return The strings converted to upper case.<br>
	 *         <br>
	 */
	public static String[] toUpperCase(final String[] words) {

		// Uppercase each string
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].toUpperCase();
		}

		// Return the array
		return words;

	}

	/**
	 * Converts all of the strings to lower case.
	 * 
	 * @param words Strings to convert.<br>
	 *              <br>
	 * @return The strings converted to lower case.<br>
	 *         <br>
	 */
	public static String[] toLowerCase(final String[] words) {

		// Lowercase each string
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].toLowerCase();
		}

		// Return the array
		return words;

	}

	/**
	 * Converts the first letter of a string to upper case.
	 * 
	 * @param word String to convert.<br>
	 *             <br>
	 * @return The string with the first letter in upper case.<br>
	 *         <br>
	 */
	public static String firstLetterToUpperCase(final String word) {
		return (word.equals(CommonValueL1Constants.STRING_EMPTY)) ? word
				: word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
	}

	/**
	 * Converts the first letter of a string to lower case.
	 * 
	 * @param word String to convert.<br>
	 *             <br>
	 * @return The string with the first letter in lower case.<br>
	 *         <br>
	 */
	public static String firstLetterToLowerCase(final String word) {
		return (word.equals(CommonValueL1Constants.STRING_EMPTY)) ? word
				: word.substring(0, 1).toLowerCase() + word.substring(1, word.length());
	}

	/**
	 * Converts characters with diacritical marks to uppercase characters without
	 * diacritical marks. It also transforms specific latin scripts to to the
	 * corresponding source letters.
	 * 
	 * @param text String to convert.<br>
	 *             <br>
	 * @return The string to uppercase and without diacritical marks.<br>
	 *         <br>
	 */
	public static String normalizeUppercase(final String text) {
		return normalizeLowercase(text).toUpperCase();
	}

	/**
	 * Converts characters with diacritical marks to lowercase characters without
	 * diacritical marks. It also transforms specific latin scripts to to the
	 * corresponding source letters.
	 * 
	 * @param text String to convert.<br>
	 *             <br>
	 * @return The string to lowercase and without diacritical marks.<br>
	 *         <br>
	 */
	public static String normalizeLowercase(final String text) {

		// Transform given text to uppecase.
		String output = text.toLowerCase();

		// Replacement set 1:
		// https://www.fileformat.info/info/unicode/char/00e1/index.htm
		output = output.replace('\u00E1', 'a');
		output = output.replace('\u00E9', 'e');
		output = output.replace('\u00ED', 'i');
		output = output.replace('\u00F3', 'o');
		output = output.replace('\u00FA', 'u');
		output = output.replace('\u00FD', 'y');

		// Replacement set 2:
		// https://www.fileformat.info/info/unicode/char/00e0/index.htm
		output = output.replace('\u00E0', 'a');
		output = output.replace('\u00E8', 'e');
		output = output.replace('\u00EC', 'i');
		output = output.replace('\u00F2', 'o');
		output = output.replace('\u00F9', 'u');

		// Replacement set 3:
		// https://www.fileformat.info/info/unicode/char/00e4/index.htm
		output = output.replace('\u00E4', 'a');
		output = output.replace('\u00EB', 'e');
		output = output.replace('\u00EF', 'i');
		output = output.replace('\u00F6', 'o');
		output = output.replace('\u00FC', 'u');
		output = output.replace('\u00FF', 'y');

		// Replacement set 4:
		// https://www.fileformat.info/info/unicode/char/00e2/index.htm
		output = output.replace('\u00E2', 'a');
		output = output.replace('\u00EA', 'e');
		output = output.replace('\u00EE', 'i');
		output = output.replace('\u00F4', 'o');
		output = output.replace('\u00FB', 'u');

		// Replacement set 5:
		// https://www.fileformat.info/info/unicode/char/00e3/index.htm
		output = output.replace('\u00E3', 'a');
		output = output.replace('\u00F5', 'o');
		output = output.replace('\u00F1', 'n');

		// Replacement set 6:
		// https://www.fileformat.info/info/unicode/char/00e5/index.htm
		output = output.replace('\u00E5', 'a');

		// Replacement 7:
		// https://www.fileformat.info/info/unicode/char/00f8/index.htm
		output = output.replace('\u00F8', 'o');

		// Replacement 8:
		// https://www.fileformat.info/info/unicode/char/00e7/index.htm
		output = output.replace('\u00E7', 'c');

		// Replacement 9:
		// https://www.fileformat.info/info/unicode/char/00e6/index.htm
		output = output.replace("\u00E6", "ae");

		// Return updated text.
		return output;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Replaces the variables that exists in the given string with the values from
	 * the given map.
	 * 
	 * @param string Source string. Every variable must be inside '${' and '}' so
	 *               the variable CAR must be in this string as '${CAR}'.<br>
	 *               <br>
	 * @param values Map where the keys are the names of the variables in the string
	 *               and the values those that will replace the variables.<br>
	 *               <br>
	 * @return String with the variables replaced.<br>
	 *         <br>
	 */
	public static String replace(final String string, final Map<String, ?> values) {

		// Reference the original value so given one is not modified.
		String result = string;

		// Replace each variable only when they exist.
		if ((values != null) && (values.size() > 0)) {

			// Get the keys of the map.
			final Set<String> keys = values.keySet();

			// Replace each key.
			for (final Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

				// Get one key.
				final String key = iterator.next();

				// Complete the key.
				final String fullKey = CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET + key
						+ CHARACTER_RIGHT_CURLY_BRACKET;

				// Search for the key in the string.
				int index = result.indexOf(fullKey);

				// Replace each key in the string.
				while (index > -1) {

					// Rebuild the string.
					result = result.substring(0, index) + (values.get(key))
							+ result.substring(index + fullKey.length(), result.length());

					// Search for another key.
					index = result.indexOf(fullKey);

				}

			}

		}

		// Return the updatted message.
		return result;

	}

	/**
	 * Extracts the values from a map in the order that indicates the variables
	 * defined in a string.
	 * 
	 * @param string Source string. Every variable must be inside '${' and '}' so
	 *               the variable CAR must be in this string as '${CAR}'.<br>
	 *               <br>
	 * @param values Map where the keys are the names of the variables in the string
	 *               and the values those that will replace the variables.<br>
	 *               <br>
	 * @return List of values for each variable defined in the string.<br>
	 *         <br>
	 */
	public static Object[] values(final String string, final Map<String, Object> values) {

		// Create a list where to store each value found.
		final List<Object> valuesFound = new ArrayList<Object>();

		// Search for the begining of the variable.
		int beginIndex = string.indexOf(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);

		// String to process.
		String updatedString = string;

		// Search for variables.
		while (beginIndex > -1) {

			// Search for the end of the variable.
			int endIndex = updatedString.indexOf(CHARACTER_RIGHT_CURLY_BRACKET);

			// Process the variable.
			if (beginIndex < endIndex) {

				// Get the variable name.
				String key = updatedString.substring(beginIndex + 2, endIndex);

				// Store the value of the variable in the list.
				valuesFound.add(values.get(key));

				// Update the string.
				updatedString = updatedString.substring(0, beginIndex)
						+ updatedString.substring(endIndex + 1, updatedString.length());

				// Search for the begining of the variable.
				beginIndex = updatedString.indexOf(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);

			} else {
				break;
			}

		}

		// Transform the list into an array.
		final Object[] result = new Object[valuesFound.size()];
		for (int i = 0; i < valuesFound.size(); i++) {
			result[i] = valuesFound.get(i);
		}

		// Return the list of values.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Extracts the key part from a string that holds a key-value pair.
	 * 
	 * @param property A property like 'car=red'.<br>
	 *                 <br>
	 * @return Key.<br>
	 *         <br>
	 */
	public static String getPropertyKey(final String property) {
		return property.substring(0, property.indexOf(StringL1Helper.CHARACTER_EQUALS));
	}

	/**
	 * Extracts the value part from a string that holds a key-value pair.
	 * 
	 * @param property A property like 'car=red'.<br>
	 *                 <br>
	 * @return Value.<br>
	 *         <br>
	 */
	public static String getPropertyValue(final String property) {
		return property.substring(property.indexOf(StringL1Helper.CHARACTER_EQUALS) + 1, property.length());
	}

	/**
	 * Gets the value of a property specified in a string that represents a
	 * collection of properties.
	 * 
	 * @param properties A string like "[name=John,age=18,friend=David]". This
	 *                   string is like a Map where the keys are "name", "age" and
	 *                   "friend" (each one as a string object) and the values (for
	 *                   each key) are "John", "18" and "David" (also as string
	 *                   objects).<br>
	 *                   <br>
	 * @param key        Indicates the value to retrieve. For "name" in a string
	 *                   like "[name=John,age=18,friend=David]" the result will be
	 *                   "John".<br>
	 *                   <br>
	 * @param delimiter  Character that separates two properties (i.e.: ',').<br>
	 *                   <br>
	 * @return The value of a property from a set of properties.<br>
	 *         <br>
	 */
	public static String getPropertyValue(final String properties, final String key, final char delimiter) {
		return DataStructureL1Helper.toHashMap(properties, delimiter).get(key);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a given string into an InputStream.
	 * 
	 * @param string      Source string.<br>
	 *                    <br>
	 * @param charsetName The name of a supported charset for the given string.
	 *                    Provide <code>null</code> to use system default encoding
	 *                    charset. Please bear in mind that the number of bytes
	 *                    taken to represent the string depends entirely on which
	 *                    encoding you use to turn it into bytes, so input stream
	 *                    size to return will depend on it.<br>
	 *                    <br>
	 * @return The string as an InputStream.<br>
	 *         <br>
	 * @throws UnsupportedEncodingException If given charset name is not supported.
	 */
	public static InputStream toInputStream(final String string, final String charsetName)
			throws UnsupportedEncodingException {

		// Get the bytes from the string.
		final byte[] bytes = (charsetName == null) ? string.getBytes() : string.getBytes(charsetName);

		// Return string bytes as an input stream.
		return new LengthInputStream(new ByteArrayInputStream(bytes), bytes.length);

	}

	/**
	 * Dumps text from a given InputStream into a string using UTF-8 encoding and a
	 * default buffer size.
	 * 
	 * @param inputStream Input stream where to read the text.<br>
	 *                    <br>
	 * @return Target text as a string.<br>
	 *         <br>
	 * @throws IOException If there is an error when reading the stream.<br>
	 *                     <br>
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		return toString(inputStream, CommonValueL1Constants.ENCODING_TYPE_UTF8,
				CommonValueL1Constants.DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Dumps text from a given InputStream into a string.
	 * 
	 * @param inputStream Input stream where to read the text.<br>
	 *                    <br>
	 * @param encoding    Input stream encoding (i.e.: UTF-8).<br>
	 *                    <br>
	 * @param bufferSize  Reader buffer size (bytes).<br>
	 *                    <br>
	 * @return Target text as a string.<br>
	 *         <br>
	 * @throws IOException If there is an error when reading the stream.<br>
	 *                     <br>
	 */
	public static String toString(final InputStream inputStream, final String encoding, final int bufferSize)
			throws IOException {

		// Create a buffer.
		final char[] buffer = new char[bufferSize];

		// Output text.
		final StringBuffer text = new StringBuffer();

		// Create a input reader.
		final Reader reader = new InputStreamReader(inputStream, encoding);

		// Number of characters to append.
		int length = 0;

		// Read text.
		while (length >= 0) {

			// Get the number of characters to append.
			length = reader.read(buffer, 0, buffer.length);

			// Appends the string to this string buffer.
			if (length >= 0) {
				text.append(buffer, 0, length);
			}

		}

		// Close input reader.
		reader.close();

		// Return text.
		return text.toString();

	}

	/**
	 * Breaks a given string into multiple strings.
	 * 
	 * @param string    Source string.<br>
	 *                  <br>
	 * @param delimiter Character that separates two substrings (i.e.: ';').<br>
	 *                  <br>
	 * @return Array with each string.<br>
	 *         <br>
	 */
	public static String[] toStrings(final String string, final char delimiter) {

		// Calculate the size of the string.
		final int length = string.length();

		// Beginning of the string.
		int start = 0;

		// Collection where to place the strings found.
		final List<String> strings = new ArrayList<String>();

		// Extract each string.
		for (int i = 0; i < length; i++) {
			if (string.charAt(i) == delimiter) {

				// Get a word from the string.
				final String substring = string.substring(start, i);

				// Add the string.
				if (!substring.equals(CommonValueL1Constants.STRING_EMPTY)) {
					strings.add(substring);
				}

				// Point to next start.
				start = i + 1;

			}
		}

		// Extract the last word.
		final String substring = string.substring(start, length);

		// Add the last word.
		if (!substring.equals(CommonValueL1Constants.STRING_EMPTY)) {
			strings.add(substring);
		}

		// Create an array for the result.
		final String[] result = new String[strings.size()];

		// Copy each word found into the result.
		for (int i = 0; i < strings.size(); i++) {
			result[i] = strings.get(i);
		}

		// Return the result.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Counts the character occurrences in a string.
	 * 
	 * @param text      String where to search for the character.<br>
	 *                  <br>
	 * @param character Character to search for.<br>
	 *                  <br>
	 * @return Number of occurrences of the character in the string.<br>
	 *         <br>
	 */
	public static int countMatches(final String text, final char character) {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == character) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Creates an ID string.
	 * 
	 * @return ID with time in hexadecimal and a sequence number also in
	 *         hexadecimal.<br>
	 *         <br>
	 */
	public static String createSimpleID() {

		// Get the current time.
		long time = System.currentTimeMillis();

		// Reset counter ID.
		if ((lastTime != time) || (counterID == Integer.MAX_VALUE - 1)) {
			counterID = -1;
		}

		// Update last time.
		lastTime = time;

		// Buffer for the hexadecimal string.
		final char[] buffer = new char[SIMPLE_ID_BUFFER_SIZE];

		// Array index.
		int index = SIMPLE_ID_BUFFER_SIZE;

		// Create the hexadecimal string.
		do {

			// Get an hexadecimal character.
			buffer[--index] = DIGITS[(int) (time & SIMPLE_ID_MASK)];

			// Shift.
			time >>>= SIMPLE_ID_SHIFT_TO_HEX;

		} while (time != 0);

		// Get the time as an hexadecimal string.
		final String timeHexString = new String(buffer, index, (64 - index));

		// Create and return the ID.
		return timeHexString + CHARACTER_COLON + Integer.toHexString(++counterID);

	}

	/**
	 * Converts a given string into an object.
	 * 
	 * @param type   Type of the target object.<br>
	 *               <br>
	 * @param string Source string.<br>
	 *               <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws IllegalArgumentException If there is an error when trying to parse
	 *                                  the string.<br>
	 *                                  <br>
	 */
	public static Object parse(final Class<?> type, final String string) throws IllegalArgumentException {

		// Parse Boolean.
		if (type.equals(Boolean.class)) {
			return (string.equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) ? Boolean.TRUE : Boolean.FALSE;
		}

		// Parse Byte.
		if (type.equals(Byte.class)) {
			return new Byte(Byte.parseByte(string));
		}

		// Parse Class.
		if (type.equals(Short.class)) {
			return new Short(Short.parseShort(string));
		}

		// Parse Integer.
		if (type.equals(Integer.class)) {
			return Integer.valueOf(string);
		}

		// Parse Long.
		if (type.equals(Long.class)) {
			return new Long(Long.parseLong(string));
		}

		// Parse String, StringBuffer and StringBuilder.
		if (type.equals(String.class)) {
			return string;
		} else if (type.equals(StringBuffer.class)) {
			return new StringBuffer(string);
		}

		// Parse Character.
		if (type.equals(Character.class)) {
			if (string.length() == 1) {
				return new Character(string.charAt(0));
			} else {
				throw new IllegalArgumentException("Can not parse given string as a character");
			}
		}

		// Parse TimeZone.
		if (type.equals(TimeZone.class)) {
			return TimeZone.getTimeZone(string);
		}

		// Parse Class.
		if (type.equals(Class.class)) {
			try {
				return Class.forName(string);
			} catch (final ClassNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Minimizes a given JavaScript code (removes empty spaces, comments and so on).
	 * 
	 * @param code Uncompressed JavaScript code.<br>
	 *             <br>
	 * @return JavaScript code without empty spaces, comments and so on.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to compress the
	 *                     JavaScript code.<br>
	 *                     <br>
	 */
	public static String compressJavaScript(final String code) throws IOException {
		return new JavaScriptCompressor().compress(code).trim();
	}

}
