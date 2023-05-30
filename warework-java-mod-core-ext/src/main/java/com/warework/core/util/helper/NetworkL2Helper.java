package com.warework.core.util.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.json.JsonObject;

/**
 * Performs common network operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class NetworkL2Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// HTTP HEADERS

	/**
	 * HTTP header that specifies the character sets that are acceptable.
	 */
	public final static String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";

	/**
	 * HTTP header that specifies the authentication credentials for HTTP
	 * authentication.
	 */
	public final static String HTTP_HEADER_AUTHORIZATION = "Authorization";

	/**
	 * HTTP header that specifies the MIME type of the body of the request (used
	 * with POST and PUT requests).
	 */
	public final static String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

	// MIME CONTENT TYPES

	/**
	 * MIME character to separate main type with mime subtype.
	 */
	public static final String MIME_SUBTYPE_SEPARATOR = StringL1Helper.CHARACTER_FORWARD_SLASH;

	/**
	 * The <code>application/xml-dtd</code> Media Type for Document Type Definition
	 * (DTD) files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_DTD = "application/xml-dtd";

	/**
	 * The <code>application/vnd.ms-fontobject</code> Media Type for EOT font files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_EOT = "application/vnd.ms-fontobject";

	/**
	 * The <code>application/java-vm</code> Media Type for Java class files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_JAVA = "application/java-vm";

	/**
	 * The <code>application/java-archive</code> Media Type for JAR files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_JAVA_ARCHIVE = "application/java-archive";

	/**
	 * The <code>application/javascript</code> Media Type for JavaScript files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_JAVASCRIPT = "application/javascript";

	/**
	 * The <code>application/json</code> Media Type for JavaScript Object Notation
	 * (JSON) content.
	 */
	public final static String CONTENT_TYPE_APPLICATION_JSON = "application/json";

	/**
	 * The <code>application/pdf</code> Media Type for PDF files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_PDF = "application/pdf";

	/**
	 * The <code>application/vnd.rar</code> Media Type for RAR files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_RAR = "application/vnd.rar";

	/**
	 * The <code>application/java-serialized-object</code> Media Type for Java
	 * serialized files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_SER = "application/java-serialized-object";

	/**
	 * The <code>application/sql</code> Media Type for SQL files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_SQL = "application/sql";

	/**
	 * The <code>application/x-tar</code> Media Type for TAR files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_TAR = "application/x-tar";

	/**
	 * The <code>application/manifest+json</code> Media Type for JSON web manifest
	 * files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_WEB_MANIFEST = "application/manifest+json";

	/**
	 * The <code>application/wsdl+xml</code> Media Type for WSDL files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_WSDL = "application/wsdl+xml";

	/**
	 * The <code>application/xml</code> Media Type for XML content.
	 */
	public final static String CONTENT_TYPE_APPLICATION_XML = "application/xml";

	/**
	 * This is the default content type for the <code>Content-Type</code> HTTP
	 * header and specified that the message sent to the server is a query string
	 * where [Reserved and] non-alphanumeric characters are replaced by `%HH', a
	 * percent sign and two hexadecimal digits representing the ASCII code of the
	 * character.
	 */
	public final static String CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	/**
	 * The <code>application/zip</code> Media Type for ZIP files.
	 */
	public final static String CONTENT_TYPE_APPLICATION_ZIP = "application/zip";

	/**
	 * The <code>image/gif</code> Media Type for GIF image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_GIF = "image/gif";

	/**
	 * The <code>image/jpg</code> Media Type for JPG image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_JPG = "image/jpg";

	/**
	 * The <code>image/vnd.microsoft.icon</code> Media Type for ICO image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_ICO = "image/vnd.microsoft.icon";

	/**
	 * The <code>image/png</code> Media Type for PNG image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_PNG = "image/png";

	/**
	 * The <code>image/svg+xml</code> Media Type for SVG image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_SVG = "image/svg+xml";

	/**
	 * The <code>image/webp</code> Media Type for WEBP image files.
	 */
	public final static String CONTENT_TYPE_IMAGE_WEBP = "image/webp";

	/**
	 * The <code>font/ttf</code> Media Type for TTF font files.
	 */
	public final static String CONTENT_TYPE_FONT_TTF = "font/ttf";

	/**
	 * The <code>font/woff</code> Media Type for WOFF font files.
	 */
	public final static String CONTENT_TYPE_FONT_WOFF = "font/woff";

	/**
	 * The <code>font/woff2</code> Media Type for WOFF2 font files.
	 */
	public final static String CONTENT_TYPE_FONT_WOFF2 = "font/woff2";

	/**
	 * The <code>text/css</code> Media Type for Cascading Style Sheets (CSS) files.
	 */
	public final static String CONTENT_TYPE_TEXT_CSS = "text/css";

	/**
	 * The <code>text/csv</code> Media Type for Comma Separated Values (CSV) files.
	 */
	public final static String CONTENT_TYPE_TEXT_CSV = "text/csv";

	/**
	 * The <code>text/html</code> Media Type for HTML content.
	 */
	public final static String CONTENT_TYPE_TEXT_HTML = "text/html";

	/**
	 * The <code>text/x-java-source</code> Media Type for Java source files.
	 */
	public final static String CONTENT_TYPE_TEXT_JAVA = "text/x-java-source";

	/**
	 * The <code>text/plain</code> Media Type for plain text content. This is the
	 * default value for text files.
	 */
	public final static String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

	/**
	 * The <code>application/octet-stream</code> Media Type for unknown binary
	 * content.
	 */
	public final static String CONTENT_TYPE_UNKNOWN_BINARY = "application/octet-stream";

	// HOSTNAME VERIFIER TYPES

	/**
	 * Always trust host name of the target endpoint and the certificate common name
	 * (not required to match).
	 */
	public static final HostnameVerifier HOSTNAME_VERIFIER_ALL_TRUSTED = createVerifierAllTrusted();

	// ///////////////////////////////////////////////////////////////////

	// KEYWORDS

	private final static String POST = "POST";

	private final static String GET = "GET";

	// RESPONSE CODES

	private final static int RESPONSE_CODE_OK = 200;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected NetworkL2Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sends an HTTP/S GET request with key-value pairs data.<br>
	 * <br>
	 * 
	 * @param endpoint         Target server where to send the request.<br>
	 *                         <br>
	 * @param header           Connection properties as key value pairs (optional).
	 *                         <br>
	 *                         <br>
	 * @param parameters       Request data as key-value pairs to include in the
	 *                         request (optional).<br>
	 *                         <br>
	 * @param responseEncoding Input stream encoding (i.e.: UTF-8).<br>
	 *                         <br>
	 * @param bufferSize       Reader buffer size (bytes).<br>
	 *                         <br>
	 * @param verifier         Host name SSL connection verifier.<br>
	 *                         <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpGetResponse(final String endpoint, final Map<String, String> header,
			final Map<String, String> parameters, final String responseEncoding, final int bufferSize,
			final HostnameVerifier verifier) throws IOException {

		// Create the URL.
		final URL target = createURL(endpoint, parameters);

		// Open URL connection.
		final HttpURLConnection connection = (HttpURLConnection) target.openConnection();

		// Send http request and return server response.
		try {

			// Configure connection.
			connection.setRequestMethod(GET);
			connection.setDoOutput(true);

			// Setup HTTPS connection attributes.
			if ((connection instanceof HttpsURLConnection) && (verifier != null)) {

				// Get HTTPS connection.
				final HttpsURLConnection https = (HttpsURLConnection) connection;

				// Set host name SSL connection verifier.
				https.setHostnameVerifier(verifier);

			}

			// Set request properties.
			if ((header != null) && (header.size() > 0)) {
				setRequestProperties(connection, header);
			}

			// Get the status code from an HTTP response message.
			final int responseCode = connection.getResponseCode();

			// Validate result.
			if (responseCode == RESPONSE_CODE_OK) {

				// Send an HTTP POST request.
				final InputStream stream = validateInputStream(connection.getInputStream());

				// Validate response.
				if (stream == null) {
					return null;
				} else {

					// Parse response.
					final String response = StringL2Helper.toString(stream, responseEncoding, bufferSize);

					// Close response.
					stream.close();

					// Return HTTP reponse.
					return response;

				}

			} else {
				throw new IOException(Integer.toString(responseCode));
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * Sends an HTTP GET request with key-value pairs data.<br>
	 * <br>
	 * 
	 * @param endpoint   Target server where to send the request.<br>
	 *                   <br>
	 * @param header     Connection properties as key value pairs (optional). <br>
	 *                   <br>
	 * @param parameters Request data as key-value pairs to include in the request
	 *                   (optional).<br>
	 *                   <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpGetResponse(final String endpoint, final Map<String, String> header,
			final Map<String, String> parameters) throws IOException {
		return toStringHttpGetResponse(endpoint, header, parameters, CommonValueL1Constants.ENCODING_TYPE_UTF8,
				CommonValueL1Constants.DEFAULT_BUFFER_SIZE, null);
	}

	/**
	 * Sends an HTTP GET request with data.<br>
	 * <br>
	 * 
	 * @param endpoint   Target server where to send the request.<br>
	 *                   <br>
	 * @param header     HTTP headers as key value pairs (optional). <br>
	 *                   <br>
	 * @param parameters Request data as key-value pairs to include in the request
	 *                   (optional).<br>
	 *                   <br>
	 * @param bufferSize Buffer size to load response bytes.<br>
	 *                   <br>
	 * @param verifier   Host name SSL connection verifier.<br>
	 *                   <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static byte[] toByteArrayHttpGetResponse(final String endpoint, final Map<String, String> header,
			final Map<String, String> parameters, final int bufferSize, final HostnameVerifier verifier)
			throws IOException {

		// Create the URL.
		final URL target = createURL(endpoint, parameters);

		// Open URL connection.
		final HttpURLConnection connection = (HttpURLConnection) target.openConnection();

		// Send http request and return server response.
		try {

			// Configure connection.
			connection.setRequestMethod(GET);
			connection.setDoOutput(true);

			// Setup HTTPS connection attributes.
			if ((connection instanceof HttpsURLConnection) && (verifier != null)) {

				// Get HTTPS connection.
				final HttpsURLConnection https = (HttpsURLConnection) connection;

				// Set host name SSL connection verifier.
				https.setHostnameVerifier(verifier);

			}

			// Set request properties.
			if ((header != null) && (header.size() > 0)) {
				setRequestProperties(connection, header);
			}

			// Get the status code from an HTTP response message.
			final int responseCode = connection.getResponseCode();

			// Validate result.
			if (responseCode == RESPONSE_CODE_OK) {

				// Send an HTTP GET request.
				final InputStream stream = validateInputStream(connection.getInputStream());

				// Validate response.
				if (stream == null) {
					return null;
				} else {

					// Response bytes.
					final ByteArrayOutputStream reponse = new ByteArrayOutputStream();

					// Create buffer to load bytes.
					final byte[] buffer = new byte[bufferSize];

					// Read response.
					int read;
					while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
						reponse.write(buffer, 0, read);
					}

					// Close response.
					stream.close();

					// Return HTTP reponse.
					final byte[] output = reponse.toByteArray();

					// Close temporary stream.
					reponse.close();

					// Return reponse data.
					return output;

				}

			} else {
				throw new IOException(Integer.toString(responseCode));
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sends an HTTP POST request with data.<br>
	 * <br>
	 * The POST request method is designed to request that a web server accept the
	 * data enclosed in the request message's body for storage. It is often used
	 * when uploading a file or submitting a completed web form.
	 * 
	 * @param endpoint Target server where to send the request.<br>
	 *                 <br>
	 * @param header   HTTP headers as key value pairs (optional). <br>
	 *                 <br>
	 * @param request  Data to include in the request (optional).<br>
	 *                 <br>
	 * @param verifier Host name SSL connection verifier.<br>
	 *                 <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpPostResponse(final String endpoint, final Map<String, String> header,
			final byte[] request, final HostnameVerifier verifier) throws IOException {

		// Create the URL.
		final URL target = new URL(endpoint);

		// Open URL connection.
		final HttpURLConnection connection = (HttpURLConnection) target.openConnection();

		// Send http request and return server response.
		try {

			// Configure connection.
			connection.setRequestMethod(POST);
			connection.setDoOutput(true);

			// Setup HTTPS connection attributes.
			if ((connection instanceof HttpsURLConnection) && (verifier != null)) {

				// Get HTTPS connection.
				final HttpsURLConnection https = (HttpsURLConnection) connection;

				// Set host name SSL connection verifier.
				https.setHostnameVerifier(verifier);

			}

			// Set request properties.
			if ((header != null) && (header.size() > 0)) {
				setRequestProperties(connection, header);
			}

			// Configure request data.
			if (request != null) {

				// Create a writer to set the data.
				final OutputStream writer = connection.getOutputStream();

				// Set request data.
				writer.write(request);

				// Flush writer.
				writer.flush();

				// Close writer.
				writer.close();

			}

			// Get the status code from an HTTP response message.
			final int responseCode = connection.getResponseCode();

			// Validate result.
			if (responseCode == RESPONSE_CODE_OK) {

				// Send an HTTP POST request.
				final InputStream stream = validateInputStream(connection.getInputStream());

				// Validate response.
				if (stream == null) {
					return null;
				} else {

					// Parse response.
					final String response = StringL2Helper.toString(stream);

					// Close response.
					stream.close();

					// Return HTTP reponse.
					return response;

				}

			} else {
				throw new IOException(Integer.toString(responseCode));
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * Sends an HTTP POST request with data.<br>
	 * <br>
	 * The POST request method is designed to request that a web server accept the
	 * data enclosed in the request message's body for storage. It is often used
	 * when uploading a file or submitting a completed web form.
	 * 
	 * @param endpoint         Target server where to send the request.<br>
	 *                         <br>
	 * @param header           HTTP headers as key value pairs (optional). <br>
	 *                         <br>
	 * @param request          Data to include in the request (optional). It can be
	 *                         a string with key-value pairs, a JSON string, ...<br>
	 *                         <br>
	 * @param responseEncoding Input stream encoding (i.e.: UTF-8).<br>
	 *                         <br>
	 * @param bufferSize       Reader buffer size (bytes).<br>
	 *                         <br>
	 * @param verifier         Host name SSL connection verifier.<br>
	 *                         <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpPostResponse(final String endpoint, final Map<String, String> header,
			final String request, final String responseEncoding, final int bufferSize, final HostnameVerifier verifier)
			throws IOException {

		// Create the URL.
		final URL target = new URL(endpoint);

		// Open URL connection.
		final HttpURLConnection connection = (HttpURLConnection) target.openConnection();

		// Send http request and return server response.
		try {

			// Configure connection.
			connection.setRequestMethod(POST);
			connection.setDoOutput(true);

			// Setup HTTPS connection attributes.
			if ((connection instanceof HttpsURLConnection) && (verifier != null)) {

				// Get HTTPS connection.
				final HttpsURLConnection https = (HttpsURLConnection) connection;

				// Set host name SSL connection verifier.
				https.setHostnameVerifier(verifier);

			}

			// Set request properties.
			if ((header != null) && (header.size() > 0)) {
				setRequestProperties(connection, header);
			}

			// Configure request data.
			if (request != null) {

				// Create a writer to set the data.
				final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

				// Set request data.
				writer.write(request);

				// Flush writer.
				writer.flush();

				// Close writer.
				writer.close();

			}

			// Get the status code from an HTTP response message.
			final int responseCode = connection.getResponseCode();

			// Validate result.
			if (responseCode == RESPONSE_CODE_OK) {

				// Send an HTTP POST request.
				final InputStream stream = validateInputStream(connection.getInputStream());

				// Validate response.
				if (stream == null) {
					return null;
				} else {

					// Parse response.
					final String response = StringL2Helper.toString(stream, responseEncoding, bufferSize);

					// Close response stream.
					stream.close();

					// Return response.
					return response;

				}

			} else {
				throw new IOException(Integer.toString(responseCode));
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * Sends an HTTP POST request with data.<br>
	 * <br>
	 * The POST request method is designed to request that a web server accept the
	 * data enclosed in the request message's body for storage. It is often used
	 * when uploading a file or submitting a completed web form.
	 * 
	 * @param endpoint Target server where to send the request.<br>
	 *                 <br>
	 * @param header   HTTP headers as key value pairs (optional). <br>
	 *                 <br>
	 * @param request  Data to include in the request (optional). It can be a string
	 *                 with key-value pairs, a JSON string, ...<br>
	 *                 <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpPostResponse(final String endpoint, final Map<String, String> header,
			final String request) throws IOException {
		return toStringHttpPostResponse(endpoint, header, request, CommonValueL1Constants.ENCODING_TYPE_UTF8,
				CommonValueL1Constants.DEFAULT_BUFFER_SIZE, null);
	}

	/**
	 * Sends an HTTP POST request with key-value pairs data.
	 * 
	 * @param endpoint Target server where to send the request.<br>
	 *                 <br>
	 * @param header   HTTP headers as key value pairs (optional). <br>
	 *                 <br>
	 * @param request  Request data as key-value pairs to include in the request
	 *                 (optional).<br>
	 *                 <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpPostResponse(final String endpoint, final Map<String, String> header,
			final Map<String, String> request) throws IOException {
		return ((request != null) && (request.size() > 0))
				? toStringHttpPostResponse(endpoint, header,
						createParameters(request, CommonValueL2Constants.ENCODING_TYPE_UTF8))
				: toStringHttpPostResponse(endpoint, header, (String) null);
	}

	/**
	 * Sends an HTTP POST request with JSON data.
	 * 
	 * @param endpoint Target server where to send the request.<br>
	 *                 <br>
	 * @param header   HTTP headers as key value pairs (optional). <br>
	 *                 <br>
	 * @param request  JSON Data to include in the request (optional).<br>
	 *                 <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static String toStringHttpPostResponse(final String endpoint, final Map<String, String> header,
			final JsonObject request) throws IOException {
		return (request == null) ? toStringHttpPostResponse(endpoint, header, (String) null)
				: toStringHttpPostResponse(endpoint, header, request.toString());
	}

	/**
	 * Sends an HTTP POST request with data.<br>
	 * <br>
	 * The POST request method is designed to request that a web server accept the
	 * data enclosed in the request message's body for storage. It is often used
	 * when uploading a file or submitting a completed web form.
	 * 
	 * @param endpoint   Target server where to send the request.<br>
	 *                   <br>
	 * @param header     HTTP headers as key value pairs (optional). <br>
	 *                   <br>
	 * @param request    Data to include in the request (optional).<br>
	 *                   <br>
	 * @param bufferSize Buffer size to load response bytes.<br>
	 *                   <br>
	 * @param verifier   Host name SSL connection verifier.<br>
	 *                   <br>
	 * @return Server response.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to send the request or
	 *                     process the response.<br>
	 *                     <br>
	 */
	public static byte[] toByteArrayHttpPostResponse(final String endpoint, final Map<String, String> header,
			final byte[] request, final int bufferSize, final HostnameVerifier verifier) throws IOException {

		// Create the URL.
		final URL target = new URL(endpoint);

		// Open URL connection.
		final HttpURLConnection connection = (HttpURLConnection) target.openConnection();

		// Send http request and return server response.
		try {

			// Configure connection.
			connection.setRequestMethod(POST);
			connection.setDoOutput(true);

			// Setup HTTPS connection attributes.
			if ((connection instanceof HttpsURLConnection) && (verifier != null)) {

				// Get HTTPS connection.
				final HttpsURLConnection https = (HttpsURLConnection) connection;

				// Set host name SSL connection verifier.
				https.setHostnameVerifier(verifier);

			}

			// Set request properties.
			if ((header != null) && (header.size() > 0)) {
				setRequestProperties(connection, header);
			}

			// Configure request data.
			if (request != null) {

				// Create a writer to set the data.
				final OutputStream writer = connection.getOutputStream();

				// Set request data.
				writer.write(request);

				// Flush writer.
				writer.flush();

				// Close writer.
				writer.close();

			}

			// Get the status code from an HTTP response message.
			final int responseCode = connection.getResponseCode();

			// Validate result.
			if (responseCode == RESPONSE_CODE_OK) {

				// Send an HTTP GET request.
				final InputStream stream = validateInputStream(connection.getInputStream());

				// Validate response.
				if (stream == null) {
					return null;
				} else {

					// Response bytes.
					final ByteArrayOutputStream reponse = new ByteArrayOutputStream();

					// Create buffer to load bytes.
					final byte[] buffer = new byte[bufferSize];

					// Read response.
					int read;
					while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
						reponse.write(buffer, 0, read);
					}

					// Close response.
					stream.close();

					// Return HTTP reponse.
					final byte[] output = reponse.toByteArray();

					// Close temporary stream.
					reponse.close();

					// Return reponse data.
					return output;

				}

			} else {
				throw new IOException(Integer.toString(responseCode));
			}

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a string with encoded key-value pairs.
	 * 
	 * @param parameters Key-value pairs used to encode and create the result
	 *                   string.<br>
	 *                   <br>
	 * @param encoding   Encoding type. For example: utf-8.<br>
	 *                   <br>
	 * @return String with encoded key-value pairs.<br>
	 *         <br>
	 * @throws UnsupportedEncodingException If there is an error when trying to
	 *                                      encode the values.<br>
	 *                                      <br>
	 */
	public static String createParameters(final Map<String, String> parameters, final String encoding)
			throws UnsupportedEncodingException {

		// Get the keys.
		final Set<String> names = parameters.keySet();

		// Build here the string to return.
		final StringBuilder output = new StringBuilder();

		// Encode each value and build string.
		for (final Iterator<String> iterator = names.iterator(); iterator.hasNext();) {

			// Get parameter key.
			final String name = iterator.next();

			// Encoded and set key-value pair.
			output.append(URLEncoder.encode(name, encoding));
			output.append(StringL1Helper.CHARACTER_EQUALS);
			output.append(URLEncoder.encode(parameters.get(name), encoding));

			// Append '&' character.
			if (iterator.hasNext()) {
				output.append(StringL1Helper.CHARACTER_AMPERSAND);
			}

		}

		// Return encoded string.
		return output.toString();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a hostname verifier to always trust host name of the target endpoint
	 * and the certificate common name (not required to match).
	 * 
	 * @return All trusted hostname verifier.<br>
	 *         <br>
	 */
	private static HostnameVerifier createVerifierAllTrusted() {
		return new HostnameVerifier() {
			public boolean verify(final String hostname, final SSLSession session) {
				return true;
			}
		};
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets a collection of properties in the HTTP connection.
	 * 
	 * @param connection        HTTP connection where to set the properties.<br>
	 *                          <br>
	 * @param requestProperties Properties to set.<br>
	 *                          <br>
	 */
	private static void setRequestProperties(final HttpURLConnection connection,
			final Map<String, String> requestProperties) {
		if ((requestProperties != null) && (requestProperties.size() > 0)) {

			// Get the keys.
			final Set<String> keys = requestProperties.keySet();

			// Set each property.
			for (final Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

				// Get one key.
				final String key = iterator.next();

				// Set the key.
				connection.setRequestProperty(key, requestProperties.get(key));

			}

		}
	}

	/**
	 * Validates the content of an input stream and returns <code>null</code> when
	 * it is empty.
	 * 
	 * @param stream Input stream.<br>
	 *               <br>
	 * @return A new input stream with the content to load or <code>null</code> if
	 *         input stream is empty.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to process the input
	 *                     stream.<br>
	 *                     <br>
	 */
	private static InputStream validateInputStream(final InputStream stream) throws IOException {

		// Wrapper for he input stream.
		final PushbackInputStream wrapper = new PushbackInputStream(stream);

		// Read first byte.
		int data = wrapper.read();
		if (data == -1) {

			// Close input stream.
			stream.close();

			// Return nothing.
			return null;

		}

		// Restore byte.
		wrapper.unread(data);

		// Return input stream.
		return wrapper;

	}

	/**
	 * Creates an URL.
	 * 
	 * @param endpoint   Target server where to send the request.<br>
	 *                   <br>
	 * @param parameters Request data as key-value pairs to include in the request
	 *                   (optional).<br>
	 *                   <br>
	 * @return A new URL.
	 * @throws IOException If there is an error creating the URL.<br>
	 *                     <br>
	 */
	private static URL createURL(final String endpoint, final Map<String, String> parameters) throws IOException {
		return ((parameters != null) && (parameters.size() > 0)) ? new URL(endpoint + StringL1Helper.CHARACTER_QUESTION
				+ createParameters(parameters, CommonValueL2Constants.ENCODING_TYPE_UTF8)) : new URL(endpoint);
	}

}
