package com.warework.service.file.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerFileTestCase;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.service.file.client.connector.AmazonS3Connector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class AmazonS3ClientTest extends AbstractSerFileTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected final Class<? extends ConnectorFacade> getClientConnectorType() {
		return AmazonS3Connector.class;
	}

	/**
	 * 
	 * @return
	 */
	protected final Map<String, Object> getClientParameters() {

		//
		final Map<String, Object> parameters = new HashMap<String, Object>();

		//
		parameters.put(AmazonS3Connector.PARAMETER_BUCKET_NAME, "whooptrip.com");
		parameters.put(AmazonS3Connector.PARAMETER_ACCESS_KEY, "AKIAQU5GOC342NRPOZIS");
		parameters.put(AmazonS3Connector.PARAMETER_SECRET_KEY, "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");
		parameters.put(AmazonS3Connector.PARAMETER_ENDPOINT, "eu-west-1");
		
		//
		parameters.put(AmazonS3Connector.PARAMETER_BASE_PATH, "_test/");
		parameters.put(AmazonS3Connector.PARAMETER_PATH_TRANSFORM, Boolean.TRUE);

		//
		return parameters;

	}

}
