package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterWebCompressorTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class CssCompressorTest extends AbstractSerConverterWebCompressorTestCase {

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
			service.connect(CONVERTER_NAME_CSS_COMPRESSOR);

			//
			service.disconnect(CONVERTER_NAME_CSS_COMPRESSOR);

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
			final String resource = load("sample-1.css");

			//
			service.connect(CONVERTER_NAME_CSS_COMPRESSOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_CSS_COMPRESSOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_CSS_COMPRESSOR);

		} catch (final Exception e) {
			fail();
		}
	}

}
