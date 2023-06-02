package com.warework.module.business.api;

import javax.net.ssl.HostnameVerifier;

import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.NetworkL2Helper;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ApiConfig {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Always trust host name of the target endpoint and the certificate common name
	 * (not required to match).
	 */
	public static final HostnameVerifier HOSTNAME_VERIFIER_ALL_TRUSTED = NetworkL2Helper.HOSTNAME_VERIFIER_ALL_TRUSTED;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Response encoding.
	private String encoding;

	// Response buffer size.
	private int bufferSize;

	//
	private HostnameVerifier verifier;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	public ApiConfig() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	public static ApiConfig createDefault() {

		//
		final ApiConfig config = new ApiConfig();

		//
		config.setBufferSize(CommonValueL1Constants.DEFAULT_BUFFER_SIZE);
		config.setEncoding(CommonValueL1Constants.ENCODING_TYPE_UTF8);

		//
		return config;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the bufferSize
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * @param bufferSize the bufferSize to set
	 */
	public void setBufferSize(final int bufferSize) {
		this.bufferSize = bufferSize;
	}

	/**
	 * @return the verifier
	 */
	public HostnameVerifier getVerifier() {
		return verifier;
	}

	/**
	 * @param verifier the verifier to set
	 */
	public void setVerifier(final HostnameVerifier verifier) {
		this.verifier = verifier;
	}

}
