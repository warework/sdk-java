package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterWebCompressorTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class XmlCompressorTest extends AbstractSerConverterWebCompressorTestCase {

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
			service.connect(CONVERTER_NAME_XML_COMPRESSOR);

			//
			service.disconnect(CONVERTER_NAME_XML_COMPRESSOR);

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
			final String resource = load("sample-1.xml");

			//
			service.connect(CONVERTER_NAME_XML_COMPRESSOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_XML_COMPRESSOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_XML_COMPRESSOR);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testTransform2() {
		try {

			//
			final ScopeFacade system = create("test");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) system
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String resource = load("sample-1.xml");

			//
			service.connect(CONVERTER_NAME_XML_COMPRESSOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_XML_COMPRESSOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_XML_COMPRESSOR);

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testTransform3() {
		try {

			//
			final ScopeFacade system = create("test");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) system
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String resource = load("sample-1.xml");

			//
			service.connect(CONVERTER_NAME_XML_COMPRESSOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_XML_COMPRESSOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_XML_COMPRESSOR);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testTransform4() {
		try {

			//
			final ScopeFacade system = create("test");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) system
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String resource = load("sample-1.html");

			//
			service.connect(CONVERTER_NAME_XML_COMPRESSOR);

			//
			final String result = (String) service.transform(CONVERTER_NAME_XML_COMPRESSOR, resource);
			if (result == null) {
				fail();
			}

			//
			service.disconnect(CONVERTER_NAME_XML_COMPRESSOR);

		} catch (final Exception e) {
			fail();
		}
	}

}
