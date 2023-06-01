package com.warework.module.business.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.NetworkL2Helper;
import com.warework.core.util.json.JsonArray;
import com.warework.core.util.json.JsonException;
import com.warework.core.util.json.JsonObject;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractApiConnection {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope Facade.
	private ScopeFacade scope;

	// API rate calls limit control.
	private ApiRate rate;

	// Log level when API rate calls limit is reached.
	private int log = LogServiceConstants.LOG_LEVEL_WARN;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private AbstractApiConnection() {
		// DO NOTHING.
	}

	/**
	 * Intializes the API connection.
	 * 
	 * @param scope Scope Facade.<br>
	 *              <br>
	 * @param rate  API rate calls control.<br>
	 *              <br>
	 */
	public AbstractApiConnection(final ScopeFacade scope, final ApiRate rate) {

		// Invoke default constructor.
		this();

		// Set Scope Facade.
		this.scope = scope;

		// Set API rate calls control.
		this.rate = rate;

	}

	/**
	 * Intializes the API connection.
	 * 
	 * @param scope Scope Facade.<br>
	 *              <br>
	 * @param rate  API rate calls control.<br>
	 *              <br>
	 * @param log   Log level when API rate calls limit is reached.<br>
	 *              <br>
	 */
	public AbstractApiConnection(final ScopeFacade scope, final ApiRate rate, final int log) {

		// Invoke constructor.
		this(scope, rate);

		// Set log level for API rate calls limit.
		this.log = log;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope Facade.
	 * 
	 * @return Scope Facade.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Invokes API.
	 * 
	 * @param url        API URL.<br>
	 *                   <br>
	 * @param header     Request headers.<br>
	 *                   <br>
	 * @param parameters Request parameters.<br>
	 *                   <br>
	 * @param config     API request and response config.<br>
	 *                   <br>
	 * @return API JSON object response.<br>
	 *         <br>
	 * @throws ApiException If there is an error when running the API query.<br>
	 *                      <br>
	 */
	protected final JsonObject queryJSONObject(final String url, final Map<String, String> header,
			final Map<String, String> parameters, final ApiConfig config) throws ApiException {

		// Log message.
		log(url, parameters);

		// Invoke API.
		if (rate.increase()) {
			try {
				return toJSONObject(url, header, parameters, config);
			} catch (final FileNotFoundException e) {
				throw new ApiException(getScopeFacade(), "WAREWORK cannot found remote resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			} catch (final IOException e) {
				throw new ApiException(getScopeFacade(),
						"WAREWORK cannot connect with remote resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			} catch (final JsonException e) {
				throw new ApiException(getScopeFacade(), "WAREWORK cannot parse JSON resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ApiRateLimitException(getScopeFacade(), "WAREWORK found that API rate limit has been exceeded.",
					null, log);
		}

	}

	/**
	 * Invokes API.
	 * 
	 * @param url        API URL.<br>
	 *                   <br>
	 * @param header     Request headers.<br>
	 *                   <br>
	 * @param parameters Request parameters.<br>
	 *                   <br>
	 * @param config     API request and response config.<br>
	 *                   <br>
	 * @return API JSON array response.<br>
	 *         <br>
	 * @throws ApiException If there is an error when running the API query.<br>
	 *                      <br>
	 */
	protected final JsonArray queryJSONArray(final String url, final Map<String, String> header,
			final Map<String, String> parameters, final ApiConfig config) throws ApiException {

		// Log message.
		log(url, parameters);

		// Invoke API.
		if (rate.increase()) {
			try {
				return toJSONArray(url, header, parameters, config);
			} catch (final FileNotFoundException e) {
				throw new ApiException(getScopeFacade(), "WAREWORK cannot found remote resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			} catch (final IOException e) {
				throw new ApiException(getScopeFacade(),
						"WAREWORK cannot connect with remote resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			} catch (final JsonException e) {
				throw new ApiException(getScopeFacade(), "WAREWORK cannot parse JSON resource: " + e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ApiRateLimitException(getScopeFacade(), "WAREWORK found that API rate limit has been exceeded.",
					null, log);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Invokes API.
	 * 
	 * @param url        API URL.<br>
	 *                   <br>
	 * @param parameters Request parameters.<br>
	 *                   <br>
	 * @throws ApiException If there is an error when creating URL parameters.<br>
	 *                      <br>
	 */
	private void log(final String url, final Map<String, String> parameters) throws ApiException {
		if ((parameters == null) || (parameters.size() == 0)) {
			getScopeFacade().info("WAREWORK is going to invoke " + url);
		} else {
			try {
				getScopeFacade().info("WAREWORK is going to invoke " + url + "?"
						+ NetworkL2Helper.createParameters(parameters, CommonValueL2Constants.ENCODING_TYPE_UTF8));
			} catch (final UnsupportedEncodingException e) {
				throw new ApiException(getScopeFacade(),
						"WAREWORK cannot execute API query because a problem was found while encoding query parameters: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}
	}

	/**
	 * Invoke API.
	 * 
	 * @param url        API URL.<br>
	 *                   <br>
	 * @param header     Request headers.<br>
	 *                   <br>
	 * @param parameters Request parameters.<br>
	 *                   <br>
	 * @param config     API request and response config.<br>
	 *                   <br>
	 * @return API JSON object response.<br>
	 *         <br>
	 * @throws IOException   If there is an error invoking the API.<br>
	 *                       <br>
	 * @throws JsonException If there is an parsing the API response.<br>
	 *                       <br>
	 */
	private JsonObject toJSONObject(final String url, final Map<String, String> header,
			final Map<String, String> parameters, final ApiConfig config) throws IOException, JsonException {

		// Setup header attributes.
		Map<String, String> headerAttr = null;
		if (header == null) {
			headerAttr = new HashMap<String, String>();
		} else {
			headerAttr = header;
		}
		headerAttr.put(NetworkL2Helper.HTTP_HEADER_CONTENT_TYPE, NetworkL2Helper.CONTENT_TYPE_APPLICATION_JSON);

		// Invoke API and return JSON object.
		return new JsonObject(NetworkL2Helper.toStringHttpGetResponse(url, headerAttr, parameters, config.getEncoding(),
				config.getBufferSize(), config.getVerifier()));

	}

	/**
	 * Invoke API.
	 * 
	 * @param url        API URL.<br>
	 *                   <br>
	 * @param header     Request headers.<br>
	 *                   <br>
	 * @param parameters Request parameters.<br>
	 *                   <br>
	 * @param config     API request and response config.<br>
	 *                   <br>
	 * @return API JSON array response.<br>
	 *         <br>
	 * @throws IOException   If there is an error invoking the API.<br>
	 *                       <br>
	 * @throws JsonException If there is an parsing the API response.<br>
	 *                       <br>
	 */
	private JsonArray toJSONArray(final String url, final Map<String, String> header,
			final Map<String, String> parameters, final ApiConfig config) throws IOException, JsonException {

		// Setup header attributes.
		Map<String, String> headerAttr = null;
		if (header == null) {
			headerAttr = new HashMap<String, String>();
		} else {
			headerAttr = header;
		}
		headerAttr.put(NetworkL2Helper.HTTP_HEADER_CONTENT_TYPE, NetworkL2Helper.CONTENT_TYPE_APPLICATION_JSON);

		// Invoke API and return JSON array.
		return new JsonArray(NetworkL2Helper.toStringHttpGetResponse(url, headerAttr, parameters, config.getEncoding(),
				config.getBufferSize(), config.getVerifier()));

	}

}
