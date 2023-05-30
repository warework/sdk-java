package com.warework.core.scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.warework.core.model.Scope;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.service.converter.ConverterServiceConstants;
import com.warework.service.converter.ConverterServiceImpl;
import com.warework.service.converter.client.connector.CssCompressorConnector;
import com.warework.service.converter.client.connector.HtmlCompressorConnector;
import com.warework.service.converter.client.connector.JavaScriptObfuscatorConnector;
import com.warework.service.converter.client.connector.XmlCompressorConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractSerConverterWebCompressorTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-ser-converter-web-compressor";

	//

	protected static final String CONVERTER_NAME_HTML_COMPRESSOR = "html-compressor";

	protected static final String CONVERTER_NAME_JS_OBFUSCATOR = "js-compressor";

	protected static final String CONVERTER_NAME_CSS_COMPRESSOR = "css-compressor";

	protected static final String CONVERTER_NAME_XML_COMPRESSOR = "xml-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade create(final String name) throws ScopeException {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		// Setup the parameters.
		config.setService(ConverterServiceConstants.DEFAULT_SERVICE_NAME, ConverterServiceImpl.class, null, null);

		//
		config.setClient(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				HtmlCompressorConnector.class, null);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				HtmlCompressorConnector.PARAMETER_PRESERVE_LINE_BREAKS, Boolean.FALSE);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				HtmlCompressorConnector.PARAMETER_JAVASCRIPT_OBFUSCATE, Boolean.TRUE);

		//
		config.setClient(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_JS_OBFUSCATOR,
				JavaScriptObfuscatorConnector.class, null);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				JavaScriptObfuscatorConnector.PARAMETER_DISABLE_OPTIMIZATIONS, Boolean.FALSE);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				JavaScriptObfuscatorConnector.PARAMETER_OBFUSCATE, Boolean.TRUE);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_HTML_COMPRESSOR,
				JavaScriptObfuscatorConnector.PARAMETER_PRESERVE_SEMICOLONS, Boolean.FALSE);

		//
		config.setClient(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_CSS_COMPRESSOR,
				CssCompressorConnector.class, null);

		//
		config.setClient(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_XML_COMPRESSOR,
				XmlCompressorConnector.class, null);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_XML_COMPRESSOR,
				XmlCompressorConnector.PARAMETER_REMOVE_COMMENTS, Boolean.TRUE);
		config.setClientParameter(ConverterServiceConstants.DEFAULT_SERVICE_NAME, CONVERTER_NAME_XML_COMPRESSOR,
				XmlCompressorConnector.PARAMETER_REMOVE_INTERTAG_SPACES, Boolean.TRUE);

		// Create new Scope.
		return create(config);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	protected final String load(final String resource) throws IOException {

		//
		final InputStream stream = AbstractSerConverterWebCompressorTestCase.class
				.getResourceAsStream(ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ resource);

		// Get the input stream for the text file.
		final InputStreamReader streamReader = new InputStreamReader(stream);

		// Setup a buffer.
		final BufferedReader reader = new BufferedReader(streamReader);

		// Text file.
		final StringBuffer result = new StringBuffer();

		// Read the text file.
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.append(line + "\n");
		}

		//
		reader.close();

		//
		streamReader.close();

		//
		stream.close();

		// Remove new line character.
		if (result.length() > 0) {
			return result.substring(0, result.length() - 1);
		} else {
			return result.toString();
		}

	}

}
