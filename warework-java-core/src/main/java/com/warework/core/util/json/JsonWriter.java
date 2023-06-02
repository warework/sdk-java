package com.warework.core.util.json;

import java.io.IOException;
import java.io.Writer;

/*
 Copyright (c) 2006 JSON.org

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

/**
 * JSONWriter provides a quick and convenient way of producing JSON text. The
 * texts produced strictly conform to JSON syntax rules. No whitespace is added,
 * so the results are ready for transmission or storage. Each instance of
 * JSONWriter can produce one JSON text.
 * <p>
 * A JSONWriter instance provides a <code>value</code> method for appending
 * values to the text, and a <code>key</code> method for adding keys before
 * values in objects. There are <code>array</code> and <code>endArray</code>
 * methods that make and bound array values, and <code>object</code> and
 * <code>endObject</code> methods which make and bound object values. All of
 * these methods return the JSONWriter instance, permitting a cascade style. For
 * example,
 * 
 * <pre>
 * new JSONWriter(myWriter).object().key(&quot;JSON&quot;).value(&quot;Hello, World!&quot;)
 * 		.endObject();
 * </pre>
 * 
 * which writes
 * 
 * <pre>
 * {"JSON":"Hello, World!"}
 * </pre>
 * <p>
 * The first method called must be <code>array</code> or <code>object</code>.
 * There are no methods for adding commas or colons. JSONWriter adds them for
 * you. Objects and arrays can be nested up to 20 levels deep.
 * <p>
 * This can sometimes be easier than using a JSONObject to build a string.
 * 
 * @author JSON.org
 * @version 2011-11-24
 */
public class JsonWriter {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	private static final int MAXDEPTH = 200;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * The comma flag determines if a comma should be output before the next
	 * value.
	 */
	private boolean comma;

	/**
	 * The current mode. Values: 'a' (array), 'd' (done), 'i' (initial), 'k'
	 * (key), 'o' (object).
	 */
	protected char mode;

	/**
	 * The object/array stack.
	 */
	private final JsonObject stack[];

	/**
	 * The stack top index. A value of 0 indicates that the stack is empty.
	 */
	private int top;

	/**
	 * The writer that will receive the output.
	 */
	protected Writer writer;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRCUTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Make a fresh JSONWriter. It can be used to build one JSON text.
	 * 
	 * @param w
	 *            Writer.<br>
	 * <br>
	 */
	public JsonWriter(Writer w) {
		this.comma = false;
		this.mode = 'i';
		this.stack = new JsonObject[MAXDEPTH];
		this.top = 0;
		this.writer = w;
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Begin appending a new array. All values until the balancing
	 * <code>endArray</code> will be appended to this array. The
	 * <code>endArray</code> method must be called to mark the array's end.
	 * 
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the nesting is too deep, or if the object is started in
	 *             the wrong place (for example as a key or after the end of the
	 *             outermost array or object).<br>
	 * <br>
	 */
	public JsonWriter array() throws JsonException {
		if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
			this.push(null);
			this.append("[");
			this.comma = false;
			return this;
		}
		throw new JsonException("Misplaced array.");
	}

	/**
	 * End an array. This method most be called to balance calls to
	 * <code>array</code>.
	 * 
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If incorrectly nested.<br>
	 * <br>
	 */
	public JsonWriter endArray() throws JsonException {
		return this.end('a', ']');
	}

	/**
	 * End an object. This method most be called to balance calls to
	 * <code>object</code>.
	 * 
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If incorrectly nested.<br>
	 * <br>
	 */
	public JsonWriter endObject() throws JsonException {
		return this.end('k', '}');
	}

	/**
	 * Append a key. The key will be associated with the next value. In an
	 * object, every value must be preceded by a key.
	 * 
	 * @param string
	 *            A key string.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the key is out of place. For example, keys do not belong
	 *             in arrays or if the key is null.<br>
	 * <br>
	 */
	public JsonWriter key(String string) throws JsonException {
		if (string == null) {
			throw new JsonException("Null key.");
		}
		if (this.mode == 'k') {
			try {
				this.stack[this.top - 1].putOnce(string, Boolean.TRUE);
				if (this.comma) {
					this.writer.write(',');
				}
				this.writer.write(JsonObject.quote(string));
				this.writer.write(':');
				this.comma = false;
				this.mode = 'o';
				return this;
			} catch (IOException e) {
				throw new JsonException(e);
			}
		}
		throw new JsonException("Misplaced key.");
	}

	/**
	 * Begin appending a new object. All keys and values until the balancing
	 * <code>endObject</code> will be appended to this object. The
	 * <code>endObject</code> method must be called to mark the object's end.
	 * 
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the nesting is too deep, or if the object is started in
	 *             the wrong place (for example as a key or after the end of the
	 *             outermost array or object).<br>
	 * <br>
	 */
	public JsonWriter object() throws JsonException {
		if (this.mode == 'i') {
			this.mode = 'o';
		}
		if (this.mode == 'o' || this.mode == 'a') {
			this.append("{");
			this.push(new JsonObject());
			this.comma = false;
			return this;
		}
		throw new JsonException("Misplaced object.");

	}

	/**
	 * Append either the value <code>true</code> or the value <code>false</code>
	 * .
	 * 
	 * @param b
	 *            A boolean.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	public JsonWriter value(boolean b) throws JsonException {
		return this.append(b ? "true" : "false");
	}

	/**
	 * Append a double value.
	 * 
	 * @param d
	 *            A double.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the number is not finite.<br>
	 * <br>
	 */
	public JsonWriter value(double d) throws JsonException {
		return this.value(Double.valueOf(d));
	}

	/**
	 * Append a long value.
	 * 
	 * @param l
	 *            A long.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If there is an error when trying to perform the operation.<br>
	 * <br>
	 */
	public JsonWriter value(long l) throws JsonException {
		return this.append(Long.toString(l));
	}

	/**
	 * Append an object value.
	 * 
	 * @param object
	 *            The object to append. It can be null, or a Boolean, Number,
	 *            String, JSONObject, or JSONArray, or an object that implements
	 *            JSONString.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the value is out of sequence.<br>
	 * <br>
	 */
	public JsonWriter value(Object object) throws JsonException {
		return this.append(JsonObject.valueToString(object));
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Append a value.
	 * 
	 * @param string
	 *            A string value.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If the value is out of sequence.<br>
	 * <br>
	 */
	private JsonWriter append(String string) throws JsonException {
		if (string == null) {
			throw new JsonException("Null pointer");
		}
		if (this.mode == 'o' || this.mode == 'a') {
			try {
				if (this.comma && this.mode == 'a') {
					this.writer.write(',');
				}
				this.writer.write(string);
			} catch (IOException e) {
				throw new JsonException(e);
			}
			if (this.mode == 'o') {
				this.mode = 'k';
			}
			this.comma = true;
			return this;
		}
		throw new JsonException("Value out of sequence.");
	}

	/**
	 * End something.
	 * 
	 * @param mode
	 *            Mode.<br>
	 * <br>
	 * @param c
	 *            Closing character.<br>
	 * <br>
	 * @return this.<br>
	 * <br>
	 * @throws JsonException
	 *             If unbalanced.<br>
	 * <br>
	 */
	private JsonWriter end(char mode, char c) throws JsonException {
		if (this.mode != mode) {
			throw new JsonException(mode == 'a' ? "Misplaced endArray."
					: "Misplaced endObject.");
		}
		this.pop(mode);
		try {
			this.writer.write(c);
		} catch (IOException e) {
			throw new JsonException(e);
		}
		this.comma = true;
		return this;
	}

	/**
	 * Pop an array or object scope.
	 * 
	 * @param c
	 *            The scope to close.<br>
	 * <br>
	 * @throws JsonException
	 *             If nesting is wrong.<br>
	 * <br>
	 */
	private void pop(char c) throws JsonException {
		if (this.top <= 0) {
			throw new JsonException("Nesting error.");
		}
		char m = this.stack[this.top - 1] == null ? 'a' : 'k';
		if (m != c) {
			throw new JsonException("Nesting error.");
		}
		this.top -= 1;
		this.mode = this.top == 0 ? 'd'
				: this.stack[this.top - 1] == null ? 'a' : 'k';
	}

	/**
	 * Push an array or object scope.
	 * 
	 * @param jo
	 *            The scope to open.<br>
	 * <br>
	 * @throws JsonException
	 *             If nesting is too deep.<br>
	 * <br>
	 */
	private void push(JsonObject jo) throws JsonException {
		if (this.top >= MAXDEPTH) {
			throw new JsonException("Nesting too deep.");
		}
		this.stack[this.top] = jo;
		this.mode = jo == null ? 'a' : 'k';
		this.top += 1;
	}

}
