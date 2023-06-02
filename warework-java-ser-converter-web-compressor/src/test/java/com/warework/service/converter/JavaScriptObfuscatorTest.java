package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterWebCompressorTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class JavaScriptObfuscatorTest extends AbstractSerConverterWebCompressorTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnect1() {
		try {

			//
			final ScopeFacade system = create("test");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) system
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CONVERTER_NAME_JS_OBFUSCATOR);

			//
			service.disconnect(CONVERTER_NAME_JS_OBFUSCATOR);

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testTransform1() {
		try {

			//
			final ScopeFacade system = create("test");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) system
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String resource = load("sample-1.js");

			//
			service.connect(CONVERTER_NAME_JS_OBFUSCATOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_JS_OBFUSCATOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_JS_OBFUSCATOR);

		} catch (final Exception e) {
			fail();
		}
	}

}
