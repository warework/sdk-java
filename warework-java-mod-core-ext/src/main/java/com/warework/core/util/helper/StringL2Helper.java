package com.warework.core.util.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.warework.core.util.CommonValueL2Constants;

/**
 * Performs common strings operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class StringL2Helper extends StringL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Constants for Helpers.

	protected static final int MINIMUM_PROPERTY_CHARACTERS = 3;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected StringL2Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts an object into a string.
	 * 
	 * @param object        Object to convert.
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns. If object is Date or Calendar check out
	 *                      <code>java.text.SimpleDateFormat</code> API to review
	 *                      the format patterns. In dates, default date format used
	 *                      is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return String that represents the given object.<br>
	 *         <br>
	 */
	public static String toString(Object object, String formatPattern, Locale locale, TimeZone timeZone) {
		if ((object instanceof Float) || (object instanceof Double) || (object instanceof BigDecimal)) {
			return decimalToString(object, formatPattern, locale);
		} else if ((object instanceof Date) || ((object instanceof Calendar))) {
			return dateToString(object, formatPattern, locale, timeZone);
		} else {
			return object.toString();
		}
	}

	/**
	 * Converts an array of objecs into a string.
	 * 
	 * @param values        Objects to convert.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns. If object is Date or Calendar check out
	 *                      <code>java.text.SimpleDateFormat</code> API to review
	 *                      the format patterns. In dates, default date format used
	 *                      is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @param delimiter     String to add between the values.<br>
	 *                      <br>
	 * @return String that represents the given objects.<br>
	 *         <br>
	 */
	public static String toString(Object[] values, String formatPattern, Locale locale, TimeZone timeZone,
			String delimiter) {

		// Set up the result.
		StringBuilder result = new StringBuilder();

		// Create the delimiter if it's null.
		String separator = null;
		if (delimiter == null) {
			separator = CommonValueL2Constants.STRING_EMPTY;
		} else {
			separator = delimiter;
		}

		// Copy each value into the result.
		for (int i = 0; i < values.length - 1; i++) {

			// Get an object value.
			String object = toString(values[i], formatPattern, locale, timeZone);

			// Add the object value.
			result.append(object + separator);

		}

		// Add the last object value.
		if (values.length > 0) {
			result.append(values[values.length - 1]);
		}

		// Return the string.
		return result.toString();

	}

	/**
	 * Converts a map into a string.
	 * 
	 * @param map           Object to convert.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns. If object is Date or Calendar check out
	 *                      <code>java.text.SimpleDateFormat</code> API to review
	 *                      the format patterns. In dates, default date format used
	 *                      is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @param delimiter     String to add between the values.<br>
	 *                      <br>
	 * @return String that represents the given map.<br>
	 *         <br>
	 */
	@SuppressWarnings("rawtypes")
	public static String toString(Map map, String formatPattern, Locale locale, TimeZone timeZone, String delimiter) {

		// Create the delimiter if it's null.
		String separator = null;
		if (delimiter == null) {
			separator = CommonValueL2Constants.STRING_EMPTY;
		} else {
			separator = delimiter;
		}

		// Set up the result.
		StringBuilder result = new StringBuilder();

		// Copy each value into the result.
		for (Iterator<?> iterator = map.keySet().iterator(); iterator.hasNext();) {

			// Get a key of the map.
			Object mapKey = iterator.next();

			// Get the key object as a string.
			String key = toString(mapKey, formatPattern, locale, timeZone);

			// Get the value object as a string.
			String value = toString(map.get(mapKey), formatPattern, locale, timeZone);

			// Add the key and value of the map.
			result.append(key + CHARACTER_EQUALS + value + separator);

		}

		// Return the string
		if (result.length() > 0) {
			return result.substring(0, result.length() - separator.length());
		} else {
			return result.toString();
		}

	}

	/**
	 * Dumps text from a given URL into a string.
	 * 
	 * @param path Target to read.<br>
	 *             <br>
	 * @return Target text as a string.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to load the
	 *                     resource.<br>
	 *                     <br>
	 */
	public static String toString(URL path) throws IOException {
		return toString(path.openStream());
	}

	/**
	 * Breaks a given string into multiple strings.
	 * 
	 * @param string    Source string.<br>
	 *                  <br>
	 * @param delimiter String that separates two substrings (i.e.: '\n').<br>
	 *                  <br>
	 * @return Array with each string.<br>
	 *         <br>
	 */
	public static String[] toStrings(String string, String delimiter) {
		return string.split(delimiter);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes escape characters.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return String without escape characters.<br>
	 *         <br>
	 */
	public static String removeEscapeCharacters(String string) {
		return removeCharacters(string, new char[] { '\n', '\b', '\t', '\f', 'r', '\0' });
	}

	/**
	 * Removes spaces.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return String without spaces.<br>
	 *         <br>
	 */
	public static String removeSpaces(String string) {
		return removeCharacters(string, new char[] { ' ' });
	}

	/**
	 * Removes a set of characters in a string.
	 * 
	 * @param string     Source string.<br>
	 *                   <br>
	 * @param characters Characters to remove in the string.<br>
	 *                   <br>
	 * @return String without given characters.<br>
	 *         <br>
	 */
	public static String removeCharacters(String string, char[] characters) {

		// Must create a copy of the characters provided to not change the
		// original characters array.
		char[] charactersCopy = null;
		try {
			charactersCopy = (char[]) ReflectionL2Helper.copyObject(characters);
		} catch (Exception e) {
			return null;
		}

		// Sort the characters to speed up the search later.
		Arrays.sort(charactersCopy);

		// Set up the result.
		StringBuilder result = new StringBuilder();

		// Get the max length of the string.
		int maxLength = string.length();

		// Remove every given character.
		for (int i = 0; i < maxLength; i++) {

			// Get a character from the string.
			char character = string.charAt(i);

			// Find the position of the character in the characters to remove
			// array.
			int index = Arrays.binarySearch(charactersCopy, character);

			// Match if character if a character to remove.
			if (index < 0) {
				result.append(character);
			}

		}

		// Return the string.
		return result.toString();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes every character that is not in a given array of characters.
	 * 
	 * @param string     Source string.<br>
	 *                   <br>
	 * @param characters Characters to leave in the output string.<br>
	 *                   <br>
	 * @return String with the characters that exists in the given array.<br>
	 *         <br>
	 */
	public static String leaveCharacters(String string, char[] characters) {

		// Must create a copy of the characters provided to not change the
		// original characters array.
		char[] charactersCopy = null;
		try {
			charactersCopy = (char[]) ReflectionL2Helper.copyObject(characters);
		} catch (Exception e) {
			return null;
		}

		// Sort characters to speed up the search later.
		Arrays.sort(charactersCopy);

		// Set up the result.
		StringBuilder result = new StringBuilder();

		// Get the max length of the string.
		int maxLength = string.length();

		// Leave every given character.
		for (int i = 0; i < maxLength; i++) {

			// Get a character from the string.
			char character = string.charAt(i);

			// Find the position of the character in the characters to leave
			// array.
			int index = Arrays.binarySearch(charactersCopy, character);

			// Match if character if a character to leave.
			if (index >= 0) {
				result.append(character);
			}

		}

		// Return the string.
		return result.toString();

	}

	/**
	 * Removes every character that is not a digit.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return String without any character that is not a digit.<br>
	 *         <br>
	 */
	public static String leaveDigits(String string) {
		return leaveCharacters(string, new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a given string into a map.
	 * 
	 * @param source    Set of properties like 'car=red;motorbike=blue'.<br>
	 *                  <br>
	 * @param delimiter String that separates each property (i.e.: ';').<br>
	 *                  <br>
	 * @return Map of strings, with the values of the given string.<br>
	 *         <br>
	 */
	public static HashMap<String, String> toHashMap(String source, String delimiter) {

		// Extractthe properties.
		String[] properties = toStrings(source, delimiter);

		// Create the result holder.
		HashMap<String, String> result = new HashMap<String, String>();

		// Map each property.
		for (int i = 0; i < properties.length; i++) {

			// Get one property.
			String property = properties[i];

			// Map this property.
			if ((property.indexOf(StringL1Helper.CHARACTER_EQUALS) >= 0)
					&& (property.length() >= MINIMUM_PROPERTY_CHARACTERS)) {
				result.put(getPropertyKey(property), getPropertyValue(property));
			}

		}

		// Return properties.
		return result;

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

			// Get the keys from the map.
			final Set<String> keys = values.keySet();

			// Replace each variable.
			for (final Iterator<String> iter = keys.iterator(); iter.hasNext();) {

				// Get a key.
				final String key = iter.next();

				// Get a value.
				String value = values.get(key).toString();
				if (value == null) {
					value = CommonValueL2Constants.STRING_NULL;
				}

				// Create the regular expression.
				final String queryKeyAsRegExpr = "\\$\\{" + key + "\\}";

				// Replace the variable.
				result = result.replaceAll(queryKeyAsRegExpr, value);

			}

		}

		// Return the updatted message.
		return result;

	}

	/**
	 * Replaces a string in a given text.
	 * 
	 * @param text        Text where to replace the string.<br>
	 *                    <br>
	 * @param source      String to find the text that will be replaced.<br>
	 *                    <br>
	 * @param replacement String to place in the text.<br>
	 *                    <br>
	 * @return Text updated with the given replacement.<br>
	 *         <br>
	 */
	public static String replace(String text, String source, String replacement) {
		return text.replaceAll(source, replacement);
	}

	/**
	 * Counts the number of lines that exists in a given string.
	 * 
	 * @param string The string where to count the lines.<br>
	 *               <br>
	 * @return Number of lines that exists in a given string.<br>
	 *         <br>
	 */
	public static int countLines(String string) {
		return toStrings(string, CHARACTER_NEW_LINE).length;
	}

	/**
	 * Converts a given string into an object.
	 * 
	 * @param type          Type of the target object.<br>
	 *                      <br>
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns. If object is Date or Calendar check out
	 *                      <code>java.text.SimpleDateFormat</code> API to review
	 *                      the format patterns. In dates, default date format used
	 *                      is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	public static Object parse(Class<?> type, String string, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Parse Boolean.
		if (type.equals(Boolean.class)) {
			return Boolean.valueOf(string);
		} else if (type.equals(boolean.class)) {
			return Boolean.valueOf(string).booleanValue();
		}

		// Parse Byte.
		if (type.equals(Byte.class)) {
			return Byte.valueOf(string);
		} else if (type.equals(byte.class)) {
			return Byte.valueOf(string).byteValue();
		}

		// Parse Short.
		if (type.equals(Short.class)) {
			return Short.valueOf(string);
		} else if (type.equals(short.class)) {
			return Short.valueOf(string).shortValue();
		}

		// Parse Integer.
		if (type.equals(Integer.class)) {
			return Integer.valueOf(string);
		} else if (type.equals(int.class)) {
			return Integer.valueOf(string).intValue();
		}

		// Parse Long.
		if (type.equals(Long.class)) {
			return Long.valueOf(string);
		} else if (type.equals(long.class)) {
			return Long.valueOf(string).longValue();
		}

		// Parse BigInteger.
		if (type.equals(BigInteger.class)) {
			return new BigInteger(string);
		}

		// Parse BigDecimal.
		if (type.equals(BigDecimal.class)) {
			return parseBigDecimal(string, formatPattern, locale);
		}

		// Parse Float and Double.
		if ((type.equals(Float.class)) || (type.equals(float.class)) || (type.equals(Double.class))
				|| (type.equals(double.class))) {
			return parseDecimal(type, string, formatPattern, locale);
		}

		// Parse String, StringBuffer and StringBuilder.
		if (type.equals(String.class)) {
			return string;
		} else if (type.equals(StringBuffer.class)) {
			return new StringBuffer(string);
		} else if (type.equals(StringBuilder.class)) {
			return new StringBuilder(string);
		}

		// Parse Character.
		if ((type.equals(Character.class)) || (type.equals(char.class))) {
			return parseCharacter(type, string);
		}

		// Parse Date, Time, Timestamp and Calendar.
		if (type.equals(Date.class)) {
			return parseDate(string, formatPattern, locale, timeZone);
		} else if (type.equals(Time.class)) {
			return parseTime(string, formatPattern, locale, timeZone);
		} else if (type.equals(Timestamp.class)) {
			return parseTimestamp(string, formatPattern, locale, timeZone);
		} else if (type.equals(Calendar.class)) {
			return parseCalendar(string, formatPattern, locale, timeZone);
		}

		// Parse TimeZone.
		if (type.equals(TimeZone.class)) {
			return TimeZone.getTimeZone(string);
		}

		// Parse Locale.
		if (type.equals(Locale.class)) {
			return parseLocale(string);
		}

		// Parse UUID.
		if (type.equals(UUID.class)) {
			return UUID.fromString(string);
		}

		// Parse ArrayList.
		if (type.equals(ArrayList.class)) {
			return toList(new ArrayList<String>(), string, formatPattern);
		}

		// Parse Vector.
		if (type.equals(Vector.class)) {
			return toList(new Vector<String>(), string, formatPattern);
		}

		// Parse LinkedList.
		if (type.equals(LinkedList.class)) {
			return toList(new LinkedList<String>(), string, formatPattern);
		}

		// Parse CopyOnWriteArrayList.
		if (type.equals(CopyOnWriteArrayList.class)) {
			return toList(new CopyOnWriteArrayList<String>(), string, formatPattern);
		}

		// Parse Stack.
		if (type.equals(Stack.class)) {
			return toList(new Stack<String>(), string, formatPattern);
		}

		// Parse HashMap.
		if (type.equals(HashMap.class)) {
			return DataStructureL2Helper.toHashMap(toHashMap(string, formatPattern));
		}

		// Parse Hashtable.
		if (type.equals(Hashtable.class)) {
			return DataStructureL2Helper.toHashtable(toHashMap(string, formatPattern));
		}

		// Parse ConcurrentHashMap.
		if (type.equals(ConcurrentHashMap.class)) {
			return DataStructureL2Helper.toConcurrentHashMap(toHashMap(string, formatPattern));
		}

		// Parse LinkedHashMap.
		if (type.equals(LinkedHashMap.class)) {
			return DataStructureL2Helper.toLinkedHashMap(toHashMap(string, formatPattern));
		}

		// Parse Properties.
		if (type.equals(Properties.class)) {
			return DataStructureL2Helper.toProperties(toHashMap(string, formatPattern));
		}

		// Parse TreeMap.
		if (type.equals(TreeMap.class)) {
			return DataStructureL2Helper.toTreeMap(toHashMap(string, formatPattern));
		}

		// Parse WeakHashMap.
		if (type.equals(WeakHashMap.class)) {
			return DataStructureL2Helper.toWeakHashMap(toHashMap(string, formatPattern));
		}

		// Parse File.
		if (type.equals(File.class)) {
			return new File(string);
		}

		// Parse URL.
		if (type.equals(URL.class)) {
			try {
				return new URL(string);
			} catch (MalformedURLException e) {
				throw new ParseException(e.getMessage(), 0);
			}
		}

		// Parse Class.
		if (type.equals(Class.class)) {
			try {
				return Class.forName(string);
			} catch (ClassNotFoundException e) {
				throw new ParseException(e.getMessage(), 0);
			}
		} else {
			return null;
		}

	}

	/**
	 * Saves a string in a file.
	 * 
	 * @param text     Text to store.<br>
	 *                 <br>
	 * @param file     Target file name where to save the text.<br>
	 *                 <br>
	 * @param encoding Encoding for the file. Pass <code>null</code> to use the
	 *                 system's default encoding.<br>
	 *                 <br>
	 * @throws IOException If there is an error when trying to save the text.<br>
	 *                     <br>
	 */
	public static void save(String text, String file, String encoding) throws IOException {

		// Create the file.
		Writer out = null;
		if (encoding != null) {
			out = new OutputStreamWriter(new FileOutputStream(file), encoding);
		} else {
			out = new OutputStreamWriter(new FileOutputStream(file));
		}

		// Write the text in the file.
		out.write(text);

		// Close the connection.
		out.close();

	}

	/**
	 * Creates a 128 bit random string encoded in hexadecimal.
	 * 
	 * @return A 128 bit random string.
	 */
	public static String create128BitHexRandomString() {

		// Create a class to generate cryptographically secure pseudo-random
		// numbers.
		SecureRandom random = new SecureRandom();

		// Create the random string and return the result.
		return new String(CodecL1Helper.encodeHex(random.generateSeed(16)));

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Breaks a given string into multiple strings.
	 * 
	 * @param result    Target list where to copy each value of the string.<br>
	 *                  <br>
	 * @param string    Source set of values.<br>
	 *                  <br>
	 * @param delimiter String that separates two substrings (i.e.: '\n').<br>
	 *                  <br>
	 * @return List with the values of the string.<br>
	 *         <br>
	 */
	protected static List<String> toList(List<String> result, String string, String delimiter) {

		// Create a helper to iterate in the string
		StringTokenizer valuesTokenizer = new StringTokenizer(string, delimiter);

		// Copy each value from the string into the result
		while (valuesTokenizer.hasMoreTokens()) {

			// Get the value from the string
			String value = valuesTokenizer.nextToken();

			// Add the value
			result.add(value);

		}

		// Return the array with the values
		return result;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts an object into a string.
	 * 
	 * @param object        Object to convert.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @return String that represents the given object.<br>
	 *         <br>
	 */
	private static String decimalToString(Object object, String formatPattern, Locale locale) {

		// Get the number format.
		NumberFormat nf = null;
		if ((formatPattern != null) && (locale != null)) {
			nf = new DecimalFormat(formatPattern, new DecimalFormatSymbols(locale));
		} else if (locale != null) {
			nf = DecimalFormat.getInstance(locale);
		} else if (formatPattern != null) {
			nf = new DecimalFormat(formatPattern);
		} else {
			nf = DecimalFormat.getInstance();
		}

		// Convert the object.
		return nf.format(object);

	}

	/**
	 * Converts an object into a string.
	 * 
	 * @param object        Object to convert.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. If object is Date or Calendar
	 *                      check out <code>java.text.SimpleDateFormat</code> API to
	 *                      review the format patterns.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion. Works with Float,
	 *                      Double, BigDecimal, Date and Calendar objects.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion.<br>
	 *                      <br>
	 * @return String that represents the given object.<br>
	 *         <br>
	 */
	private static String dateToString(Object object, String formatPattern, Locale locale, TimeZone timeZone) {

		// Get the value.
		Date dateValue = null;
		if (object instanceof Date) {
			dateValue = (Date) object;
		} else if (object instanceof Calendar) {

			// Get the calendar.
			Calendar calendar = (Calendar) object;

			// Get the date from the calendar.
			dateValue = calendar.getTime();

		} else {
			return null;
		}

		// Return the value as a string.
		if ((formatPattern != null) && (locale != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern, locale);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if ((formatPattern != null) && (locale != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern, locale);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if ((formatPattern != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if ((locale != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS, locale);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if (locale != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS, locale);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if (formatPattern != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

		// Return the value as a string.
		if (timeZone != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		} else {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS);

			// Return the string that represents the date.
			return dateFormat.format(dateValue);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Parses a string into a BigDecimal.
	 * 
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static BigDecimal parseBigDecimal(String string, String formatPattern, Locale locale)
			throws ParseException {

		// Get the decimal number.
		Double result = (Double) parse(Double.class, string, formatPattern, locale, null);

		// Return the big decimal.
		return new BigDecimal(result.doubleValue());

	}

	/**
	 * Parses a string into a decimal number.
	 * 
	 * @param type          Type of the target object.<br>
	 *                      <br>
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. When object is Float, Double or
	 *                      BigDecimal check out
	 *                      <code>java.text.DecimalFormat</code> API to review the
	 *                      format patterns.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Number parseDecimal(Class<?> type, String string, String formatPattern, Locale locale)
			throws ParseException {

		// Get the formatter fpr the number.
		NumberFormat numberFormat = null;
		if ((formatPattern != null) && (locale != null)) {
			numberFormat = new DecimalFormat(formatPattern, new DecimalFormatSymbols(locale));
		} else if (locale != null) {
			numberFormat = DecimalFormat.getInstance(locale);
		} else if (formatPattern != null) {
			numberFormat = new DecimalFormat(formatPattern);
		} else {
			numberFormat = DecimalFormat.getInstance();
		}

		// Return the decimal number.
		Number result = numberFormat.parse(string);
		if (type.equals(Float.class)) {
			return Float.valueOf(result.floatValue());
		} else if (type.equals(float.class)) {
			return result.floatValue();
		} else if (type.equals(Double.class)) {
			return Double.valueOf(result.doubleValue());
		} else {
			return result.doubleValue();
		}

	}

	/**
	 * Parses a string into a Character.
	 * 
	 * @param type   Type of the target object.<br>
	 *               <br>
	 * @param string Source string.<br>
	 *               <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Character parseCharacter(Class<?> type, String string) throws ParseException {
		if (string.length() == 1) {
			return (type.equals(Character.class)) ? Character.valueOf(string.charAt(0)) : string.charAt(0);
		} else if (string.length() > 1) {
			throw new ParseException("Can not parse given string as a character", 1);
		} else {
			throw new ParseException("Can not parse given string as a character", 0);
		}
	}

	/**
	 * Parses a string into a Date.
	 * 
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. If object is Date or Calendar
	 *                      check out <code>java.text.SimpleDateFormat</code> API to
	 *                      review the format patterns. In dates, default date
	 *                      format used is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Date parseDate(String string, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Parse date with formatPattern, locale and timeZone.
		if ((formatPattern != null) && (locale != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern, locale);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with formatPattern and locale.
		if ((formatPattern != null) && (locale != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern, locale);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with timeZone and formatPattern.
		if ((formatPattern != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with locale and timeZone.
		if ((locale != null) && (timeZone != null)) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS, locale);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with locale.
		if (locale != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS, locale);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with formatPattern.
		if (formatPattern != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(formatPattern);

			// Return the date.
			return dateFormat.parse(string);

		}

		// Parse date with timeZone.
		if (timeZone != null) {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS);

			// Set the time zone.
			dateFormat.setTimeZone(timeZone);

			// Return the date.
			return dateFormat.parse(string);

		} else {

			// Define a format for the date.
			DateFormat dateFormat = new SimpleDateFormat(DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS);

			// Return the date.
			return dateFormat.parse(string);

		}

	}

	/**
	 * Parses a string into a Time object.
	 * 
	 * @param string        Source string.
	 * @param formatPattern <br>
	 *                      <br>
	 *                      Format pattern to apply. If object is Date or Calendar
	 *                      check out <code>java.text.SimpleDateFormat</code> API to
	 *                      review the format patterns. In dates, default date
	 *                      format used is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Time parseTime(String string, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Parse value as a time objet.
		Date date = (Date) parse(Date.class, string, formatPattern, locale, timeZone);

		// Return the timestamp.
		return new Time(date.getTime());

	}

	/**
	 * Parses a string into a Timestamp.
	 * 
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. If object is Date or Calendar
	 *                      check out <code>java.text.SimpleDateFormat</code> API to
	 *                      review the format patterns. In dates, default date
	 *                      format used is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Timestamp parseTimestamp(String string, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Parse value as a date objet.
		Date date = (Date) parse(Date.class, string, formatPattern, locale, timeZone);

		// Return the timestamp.
		return new Timestamp(date.getTime());

	}

	/**
	 * Parses a string into a Calendar.
	 * 
	 * @param string        Source string.<br>
	 *                      <br>
	 * @param formatPattern Format pattern to apply. If object is Date or Calendar
	 *                      check out <code>java.text.SimpleDateFormat</code> API to
	 *                      review the format patterns. In dates, default date
	 *                      format used is 'yyyy/MM/dd HH:mm:ss'.<br>
	 *                      <br>
	 * @param locale        Locale to apply in the conversion.<br>
	 *                      <br>
	 * @param timeZone      TimeZone to apply in the conversion. Works with Date and
	 *                      Calendar objects.<br>
	 *                      <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Calendar parseCalendar(String string, String formatPattern, Locale locale, TimeZone timeZone)
			throws ParseException {

		// Create a calendar.
		Calendar calendar = null;
		if ((locale != null) && (timeZone != null)) {
			calendar = Calendar.getInstance(timeZone, locale);
		} else if (timeZone != null) {
			calendar = Calendar.getInstance(timeZone);
		} else if (locale != null) {
			calendar = Calendar.getInstance(locale);
		} else {
			calendar = Calendar.getInstance();
		}

		// Parse value as a date objet.
		Date date = (Date) parse(Date.class, string, formatPattern, locale, timeZone);

		// Set the time.
		if (date != null) {
			calendar.setTime(date);
		}

		// Return the calendar.
		return calendar;

	}

	/**
	 * Parses a string into a Locale.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return Object with the value of the string.<br>
	 *         <br>
	 * @throws ParseException If there is an error when trying to parse the
	 *                        value.<br>
	 *                        <br>
	 */
	private static Locale parseLocale(String string) throws ParseException {
		if (string.indexOf('_') == -1) {
			return new Locale(string.toLowerCase());
		} else {

			// Get the language.
			String language = string.substring(0, string.indexOf('_'));

			// Get the country.
			String country = string.substring(string.indexOf('_') + 1, string.length());

			// Return the locale.
			return new Locale(language.toLowerCase(), country.toUpperCase());

		}
	}

}