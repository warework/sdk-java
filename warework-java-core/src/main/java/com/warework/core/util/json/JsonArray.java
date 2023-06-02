package com.warework.core.util.json;

/*
 Copyright (c) 2002 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.warework.core.util.CommonValueL1Constants;

/**
 * A JSONArray is an ordered sequence of values. Its external text form is a
 * string wrapped in square brackets with commas separating the values. The
 * internal form is an object having <code>get</code> and <code>opt</code>
 * methods for accessing the values by index, and <code>put</code> methods for
 * adding or replacing values. The values can be any of these types:
 * <code>Boolean</code>, <code>JSONArray</code>, <code>JSONObject</code>,
 * <code>Number</code>, <code>String</code>, or the
 * <code>JSONObject.NULL object</code>.
 * <p>
 * The constructor can convert a JSON text into a Java object. The
 * <code>toString</code> method converts to JSON text.
 * <p>
 * A <code>get</code> method returns a value if one can be found, and throws an
 * exception if one cannot be found. An <code>opt</code> method returns a
 * default value instead of throwing an exception, and so is useful for
 * obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an
 * object which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coercion for you.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to
 * JSON syntax rules. The constructors are more forgiving in the texts they will
 * accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just
 * before the closing bracket.</li>
 * <li>The <code>null</code> value will be inserted when there is <code>,</code>
 * &nbsp;<small>(comma)</small> elision.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single
 * quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote
 * or single quote, and if they do not contain leading or trailing spaces, and
 * if they do not contain any of these characters:
 * <code>{ } [ ] / \ : , #</code> and if they do not look like numbers and if
 * they are not the reserved words <code>true</code>, <code>false</code>, or
 * <code>null</code>.</li>
 * </ul>
 * 
 * @author JSON.org
 * @version 2014-05-03
 */
public final class JsonArray {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * The arrayList where the JSONArray's properties are kept.
	 */
	private final ArrayList<Object> myArrayList;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Construct an empty JSONArray.
	 */
	public JsonArray() {
		this.myArrayList = new ArrayList<Object>();
	}

	/**
	 * Construct a JSONArray from a JSONTokener.
	 * 
	 * @param x
	 *            A JSONTokener.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is a syntax error.<br>
	 * <br>
	 */
	public JsonArray(JsonTokener x) throws JsonException {
		this();
		if (x.nextClean() != '[') {
			throw x.syntaxError("A JSONArray text must start with '['");
		}
		if (x.nextClean() != ']') {
			x.back();
			for (;;) {
				if (x.nextClean() == ',') {
					x.back();
					this.myArrayList.add(JsonObject.NULL);
				} else {
					x.back();
					this.myArrayList.add(x.nextValue());
				}
				switch (x.nextClean()) {
				case ',':
					if (x.nextClean() == ']') {
						return;
					}
					x.back();
					break;
				case ']':
					return;
				default:
					throw x.syntaxError("Expected a ',' or ']'");
				}
			}
		}
	}

	/**
	 * Construct a JSONArray from a source JSON text.
	 * 
	 * @param source
	 *            A string that begins with <code>[</code>&nbsp;<small>(left
	 *            bracket)</small> and ends with <code>]</code>
	 *            &nbsp;<small>(right bracket)</small>.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is a syntax error.<br>
	 * <br>
	 */
	public JsonArray(String source) throws JsonException {
		this(new JsonTokener(source));
	}

	/**
	 * Construct a JSONArray from a Collection.
	 * 
	 * @param collection
	 *            A Collection.<br>
	 * <br>
	 */
	public JsonArray(Collection<Object> collection) {
		this.myArrayList = new ArrayList<Object>();
		if (collection != null) {
			Iterator<Object> iter = collection.iterator();
			while (iter.hasNext()) {
				this.myArrayList.add(JsonObject.wrap(iter.next()));
			}
		}
	}

	/**
	 * Construct a JSONArray from an array
	 * 
	 * @param array
	 *            An array.<br>
	 * <br>
	 * @throws JsonException
	 *             If not an array.<br>
	 * <br>
	 */
	public JsonArray(Object array) throws JsonException {
		this();
		if (array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; i += 1) {
				this.put(JsonObject.wrap(Array.get(array, i)));
			}
		} else {
			throw new JsonException(
					"JSONArray initial value should be a string or collection or array.");
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the object value associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return An object value.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is no value for the index.<br>
	 * <br>
	 */
	public Object get(int index) throws JsonException {
		Object object = this.opt(index);
		if (object == null) {
			throw new JsonException("JSONArray[" + index + "] not found.");
		}
		return object;
	}

	/**
	 * Get the boolean value associated with an index. The string values "true"
	 * and "false" are converted to boolean.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The truth.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is no value for the index or if the value is not
	 *             convertible to boolean.<br>
	 * <br>
	 */
	public boolean getBoolean(int index) throws JsonException {
		Object object = this.get(index);
		if (object.equals(Boolean.FALSE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("false"))) {
			return false;
		} else if (object.equals(Boolean.TRUE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("true"))) {
			return true;
		}
		throw new JsonException("JSONArray[" + index + "] is not a boolean.");
	}

	/**
	 * Get the double value associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 * @throws JsonException
	 *             If the key is not found or if the value cannot be converted
	 *             to a number.<br>
	 * <br>
	 */
	public double getDouble(int index) throws JsonException {
		Object object = this.get(index);
		try {
			return object instanceof Number ? ((Number) object).doubleValue()
					: Double.parseDouble((String) object);
		} catch (Exception e) {
			throw new JsonException("JSONArray[" + index + "] is not a number.");
		}
	}

	/**
	 * Get the int value associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 * @throws JsonException
	 *             If the key is not found or if the value is not a number.<br>
	 * <br>
	 */
	public int getInt(int index) throws JsonException {
		Object object = this.get(index);
		try {
			return object instanceof Number ? ((Number) object).intValue()
					: Integer.parseInt((String) object);
		} catch (Exception e) {
			throw new JsonException("JSONArray[" + index + "] is not a number.");
		}
	}

	/**
	 * Get the JSONArray associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return A JSONArray value.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is no value for the index. or if the value is not a
	 *             JSONArray.<br>
	 * <br>
	 */
	public JsonArray getJSONArray(int index) throws JsonException {
		Object object = this.get(index);
		if (object instanceof JsonArray) {
			return (JsonArray) object;
		}
		throw new JsonException("JSONArray[" + index + "] is not a JSONArray.");
	}

	/**
	 * Get the JSONObject associated with an index.
	 * 
	 * @param index
	 *            subscript.<br>
	 * <br>
	 * @return A JSONObject value.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is no value for the index or if the value is not a
	 *             JSONObject.<br>
	 * <br>
	 */
	public JsonObject getJSONObject(int index) throws JsonException {
		Object object = this.get(index);
		if (object instanceof JsonObject) {
			return (JsonObject) object;
		}
		throw new JsonException("JSONArray[" + index + "] is not a JSONObject.");
	}

	/**
	 * Get the long value associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 * @throws JsonException
	 *             If the key is not found or if the value cannot be converted
	 *             to a number.<br>
	 * <br>
	 */
	public long getLong(int index) throws JsonException {
		Object object = this.get(index);
		try {
			return object instanceof Number ? ((Number) object).longValue()
					: Long.parseLong((String) object);
		} catch (Exception e) {
			throw new JsonException("JSONArray[" + index + "] is not a number.");
		}
	}

	/**
	 * Get the string associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.
	 * @return A string value.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is no string value for the index.<br>
	 * <br>
	 */
	public String getString(int index) throws JsonException {
		Object object = this.get(index);
		if (object instanceof String) {
			return (String) object;
		}
		throw new JsonException("JSONArray[" + index + "] not a string.");
	}

	/**
	 * Determine if the value is null.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return true if the value at the index is null, or if there is no value.<br>
	 * <br>
	 */
	public boolean isNull(int index) {
		return JsonObject.NULL.equals(this.opt(index));
	}

	/**
	 * Make a string from the contents of this JSONArray. The
	 * <code>separator</code> string is inserted between each element. Warning:
	 * This method assumes that the data structure is acyclical.
	 * 
	 * @param separator
	 *            A string that will be inserted between the elements.<br>
	 * <br>
	 * @return a string.<br>
	 * <br>
	 * @throws JsonException
	 *             If the array contains an invalid number.<br>
	 * <br>
	 */
	public String join(String separator) throws JsonException {
		int len = this.length();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i += 1) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(JsonObject.valueToString(this.myArrayList.get(i)));
		}
		return sb.toString();
	}

	/**
	 * Get the number of elements in the JSONArray, included nulls.
	 * 
	 * @return The length (or size).<br>
	 * <br>
	 */
	public int length() {
		return this.myArrayList.size();
	}

	/**
	 * Get the optional object value associated with an index.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return An object value, or null if there is no object at that index.<br>
	 * <br>
	 */
	public Object opt(int index) {
		return (index < 0 || index >= this.length()) ? null : this.myArrayList
				.get(index);
	}

	/**
	 * Get the optional boolean value associated with an index. It returns false
	 * if there is no value at that index, or if the value is not Boolean.TRUE
	 * or the String "true".
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The truth.<br>
	 * <br>
	 */
	public boolean optBoolean(int index) {
		return this.optBoolean(index, false);
	}

	/**
	 * Get the optional boolean value associated with an index. It returns the
	 * defaultValue if there is no value at that index or if it is not a Boolean
	 * or the String "true" or "false" (case insensitive).
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @param defaultValue
	 *            A boolean default.<br>
	 * <br>
	 * @return The truth.<br>
	 * <br>
	 */
	public boolean optBoolean(int index, boolean defaultValue) {
		try {
			return this.getBoolean(index);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Get the optional double value associated with an index. NaN is returned
	 * if there is no value for the index, or if the value is not a number and
	 * cannot be converted to a number.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public double optDouble(int index) {
		return this.optDouble(index, Double.NaN);
	}

	/**
	 * Get the optional double value associated with an index. The defaultValue
	 * is returned if there is no value for the index, or if the value is not a
	 * number and cannot be converted to a number.
	 * 
	 * @param index
	 *            subscript.<br>
	 * <br>
	 * @param defaultValue
	 *            The default value.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public double optDouble(int index, double defaultValue) {
		try {
			return this.getDouble(index);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Get the optional int value associated with an index. Zero is returned if
	 * there is no value for the index, or if the value is not a number and
	 * cannot be converted to a number.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public int optInt(int index) {
		return this.optInt(index, 0);
	}

	/**
	 * Get the optional int value associated with an index. The defaultValue is
	 * returned if there is no value for the index, or if the value is not a
	 * number and cannot be converted to a number.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @param defaultValue
	 *            The default value.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public int optInt(int index, int defaultValue) {
		try {
			return this.getInt(index);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Get the optional JSONArray associated with an index.
	 * 
	 * @param index
	 *            subscript.<br>
	 * <br>
	 * @return A JSONArray value, or null if the index has no value, or if the
	 *         value is not a JSONArray.<br>
	 * <br>
	 */
	public JsonArray optJSONArray(int index) {
		Object o = this.opt(index);
		return o instanceof JsonArray ? (JsonArray) o : null;
	}

	/**
	 * Get the optional JSONObject associated with an index. Null is returned if
	 * the key is not found, or null if the index has no value, or if the value
	 * is not a JSONObject.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return A JSONObject value.<br>
	 * <br>
	 */
	public JsonObject optJSONObject(int index) {
		Object o = this.opt(index);
		return o instanceof JsonObject ? (JsonObject) o : null;
	}

	/**
	 * Get the optional long value associated with an index. Zero is returned if
	 * there is no value for the index, or if the value is not a number and
	 * cannot be converted to a number.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public long optLong(int index) {
		return this.optLong(index, 0);
	}

	/**
	 * Get the optional long value associated with an index. The defaultValue is
	 * returned if there is no value for the index, or if the value is not a
	 * number and cannot be converted to a number.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @param defaultValue
	 *            The default value.<br>
	 * <br>
	 * @return The value.<br>
	 * <br>
	 */
	public long optLong(int index, long defaultValue) {
		try {
			return this.getLong(index);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Get the optional string value associated with an index. It returns an
	 * empty string if there is no value at that index. If the value is not a
	 * string and is not null, then it is coverted to a string.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @return A String value.<br>
	 * <br>
	 */
	public String optString(int index) {
		return this.optString(index, CommonValueL1Constants.STRING_EMPTY);
	}

	/**
	 * Get the optional string associated with an index. The defaultValue is
	 * returned if the key is not found.
	 * 
	 * @param index
	 *            The index must be between 0 and length() - 1.<br>
	 * <br>
	 * @param defaultValue
	 *            The default value.<br>
	 * <br>
	 * @return A String value.<br>
	 * <br>
	 */
	public String optString(int index, String defaultValue) {
		Object object = this.opt(index);
		return JsonObject.NULL.equals(object) ? defaultValue : object
				.toString();
	}

	/**
	 * Append a boolean value. This increases the array's length by one.
	 * 
	 * @param value
	 *            A boolean value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(boolean value) {
		this.put(value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	/**
	 * Put a value in the JSONArray, where the value will be a JSONArray which
	 * is produced from a Collection.
	 * 
	 * @param value
	 *            A Collection value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(Collection<Object> value) {
		this.put(new JsonArray(value));
		return this;
	}

	/**
	 * Append a double value. This increases the array's length by one.
	 * 
	 * @param value
	 *            A double value.<br>
	 * <br>
	 * @throws JsonException
	 *             if the value is not finite.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(double value) throws JsonException {
		Double d = Double.valueOf(value);
		JsonObject.testValidity(d);
		this.put(d);
		return this;
	}

	/**
	 * Append an int value. This increases the array's length by one.
	 * 
	 * @param value
	 *            An int value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(int value) {
		this.put(Integer.valueOf(value));
		return this;
	}

	/**
	 * Append an long value. This increases the array's length by one.
	 * 
	 * @param value
	 *            A long value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(long value) {
		this.put(Long.valueOf(value));
		return this;
	}

	/**
	 * Put a value in the JSONArray, where the value will be a JSONObject which
	 * is produced from a Map.
	 * 
	 * @param value
	 *            A Map value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(Map<String, Object> value) {
		this.put(new JsonObject(value));
		return this;
	}

	/**
	 * Append an object value. This increases the array's length by one.
	 * 
	 * @param value
	 *            An object value. The value should be a Boolean, Double,
	 *            Integer, JSONArray, JSONObject, Long, or String, or the
	 *            JSONObject.NULL object.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 */
	public JsonArray put(Object value) {
		this.myArrayList.add(value);
		return this;
	}

	/**
	 * Put or replace a boolean value in the JSONArray. If the index is greater
	 * than the length of the JSONArray, then null elements will be added as
	 * necessary to pad it out.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            A boolean value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative.<br>
	 * <br>
	 */
	public JsonArray put(int index, boolean value) throws JsonException {
		this.put(index, value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	/**
	 * Put a value in the JSONArray, where the value will be a JSONArray which
	 * is produced from a Collection.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            A Collection value.<br>
	 * <br>
	 * @return this.
	 * @throws JsonException
	 *             If the index is negative or if the value is not finite.<br>
	 * <br>
	 */
	public JsonArray put(int index, Collection<Object> value)
			throws JsonException {
		this.put(index, new JsonArray(value));
		return this;
	}

	/**
	 * Put or replace a double value. If the index is greater than the length of
	 * the JSONArray, then null elements will be added as necessary to pad it
	 * out.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            A double value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative or if the value is not finite.<br>
	 * <br>
	 */
	public JsonArray put(int index, double value) throws JsonException {
		this.put(index, Double.valueOf(value));
		return this;
	}

	/**
	 * Put or replace an int value. If the index is greater than the length of
	 * the JSONArray, then null elements will be added as necessary to pad it
	 * out.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            An int value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative.<br>
	 * <br>
	 */
	public JsonArray put(int index, int value) throws JsonException {
		this.put(index, Integer.valueOf(value));
		return this;
	}

	/**
	 * Put or replace a long value. If the index is greater than the length of
	 * the JSONArray, then null elements will be added as necessary to pad it
	 * out.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            A long value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative.<br>
	 * <br>
	 */
	public JsonArray put(int index, long value) throws JsonException {
		this.put(index, Long.valueOf(value));
		return this;
	}

	/**
	 * Put a value in the JSONArray, where the value will be a JSONObject that
	 * is produced from a Map.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            The Map value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative or if the the value is an invalid
	 *             number.<br>
	 * <br>
	 */
	public JsonArray put(int index, Map<String, Object> value)
			throws JsonException {
		this.put(index, new JsonObject(value));
		return this;
	}

	/**
	 * Put or replace an object value in the JSONArray. If the index is greater
	 * than the length of the JSONArray, then null elements will be added as
	 * necessary to pad it out.
	 * 
	 * @param index
	 *            The subscript.<br>
	 * <br>
	 * @param value
	 *            The value to put into the array. The value should be a
	 *            Boolean, Double, Integer, JSONArray, JSONObject, Long, or
	 *            String, or the JSONObject.NULL object.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the index is negative or if the the value is an invalid
	 *             number.<br>
	 * <br>
	 */
	public JsonArray put(int index, Object value) throws JsonException {
		JsonObject.testValidity(value);
		if (index < 0) {
			throw new JsonException("JSONArray[" + index + "] not found.");
		}
		if (index < this.length()) {
			this.myArrayList.set(index, value);
		} else {
			while (index != this.length()) {
				this.put(JsonObject.NULL);
			}
			this.put(value);
		}
		return this;
	}

	/**
	 * Remove an index and close the hole.
	 * 
	 * @param index
	 *            The index of the element to be removed.<br>
	 * <br>
	 * @return The value that was associated with the index, or null if there
	 *         was no value.<br>
	 * <br>
	 */
	public Object remove(int index) {
		return index >= 0 && index < this.length() ? this.myArrayList
				.remove(index) : null;
	}

	/**
	 * Determine if two JSONArrays are similar. They must contain similar
	 * sequences.
	 * 
	 * @param other
	 *            The other JSONArray.<br>
	 * <br>
	 * @return true if they are equal.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	public boolean similar(Object other) throws JsonException {
		if (!(other instanceof JsonArray)) {
			return false;
		}
		int len = this.length();
		if (len != ((JsonArray) other).length()) {
			return false;
		}
		for (int i = 0; i < len; i += 1) {
			Object valueThis = this.get(i);
			Object valueOther = ((JsonArray) other).get(i);
			if (valueThis instanceof JsonObject) {
				if (!((JsonObject) valueThis).similar(valueOther)) {
					return false;
				}
			} else if (valueThis instanceof JsonArray) {
				if (!((JsonArray) valueThis).similar(valueOther)) {
					return false;
				}
			} else if (!valueThis.equals(valueOther)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Produce a JSONObject by combining a JSONArray of names with the values of
	 * this JSONArray.
	 * 
	 * @param names
	 *            A JSONArray containing a list of key strings. These will be
	 *            paired with the values.<br>
	 * <br>
	 * @return A JSONObject, or null if there are no names or if this JSONArray
	 *         has no values.<br>
	 * <br>
	 * @throws JsonException
	 *             If any of the names are null.<br>
	 * <br>
	 */
	public JsonObject toJSONObject(JsonArray names) throws JsonException {
		if (names == null || names.length() == 0 || this.length() == 0) {
			return null;
		}
		JsonObject jo = new JsonObject();
		for (int i = 0; i < names.length(); i += 1) {
			jo.put(names.getString(i), this.opt(i));
		}
		return jo;
	}

	/**
	 * Make a JSON text of this JSONArray. For compactness, no unnecessary
	 * whitespace is added. If it is not possible to produce a syntactically
	 * correct JSON text then null will be returned instead. This could occur if
	 * the array contains an invalid number.
	 * <p>
	 * Warning: This method assumes that the data structure is acyclical.
	 * 
	 * @return a printable, displayable, transmittable representation of the
	 *         array.<br>
	 * <br>
	 */
	public String toString() {
		try {
			return this.toString(0);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Make a prettyprinted JSON text of this JSONArray. Warning: This method
	 * assumes that the data structure is acyclical.
	 * 
	 * @param indentFactor
	 *            The number of spaces to add to each level of indentation.<br>
	 * <br>
	 * @return a printable, displayable, transmittable representation of the
	 *         object, beginning with <code>[</code>&nbsp;<small>(left
	 *         bracket)</small> and ending with <code>]</code>
	 *         &nbsp;<small>(right bracket)</small>.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	public String toString(int indentFactor) throws JsonException {
		StringWriter sw = new StringWriter();
		synchronized (sw.getBuffer()) {
			return this.write(sw, indentFactor, 0).toString();
		}
	}

	/**
	 * Write the contents of the JSONArray as JSON text to a writer. For
	 * compactness, no whitespace is added.
	 * <p>
	 * Warning: This method assumes that the data structure is acyclical.
	 * 
	 * @param writer
	 *            Writer.<br>
	 * <br>
	 * @return The writer.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	public Writer write(Writer writer) throws JsonException {
		return this.write(writer, 0, 0);
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Write the contents of the JSONArray as JSON text to a writer. For
	 * compactness, no whitespace is added.
	 * <p>
	 * Warning: This method assumes that the data structure is acyclical.
	 * 
	 * @param indentFactor
	 *            The number of spaces to add to each level of indentation.<br>
	 * <br>
	 * @param indent
	 *            The indention of the top level.<br>
	 * <br>
	 * @return The writer.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	Writer write(Writer writer, int indentFactor, int indent)
			throws JsonException {
		try {
			boolean commanate = false;
			int length = this.length();
			writer.write('[');

			if (length == 1) {
				JsonObject.writeValue(writer, this.myArrayList.get(0),
						indentFactor, indent);
			} else if (length != 0) {
				final int newindent = indent + indentFactor;

				for (int i = 0; i < length; i += 1) {
					if (commanate) {
						writer.write(',');
					}
					if (indentFactor > 0) {
						writer.write('\n');
					}
					JsonObject.indent(writer, newindent);
					JsonObject.writeValue(writer, this.myArrayList.get(i),
							indentFactor, newindent);
					commanate = true;
				}
				if (indentFactor > 0) {
					writer.write('\n');
				}
				JsonObject.indent(writer, indent);
			}
			writer.write(']');
			return writer;
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

}
