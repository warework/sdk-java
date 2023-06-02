package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class Base64ConverterTest extends AbstractSerConverterTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnect1() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("base64-encoder");

			//
			service.disconnect("base64-encoder");

		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testConnect2() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("base64-decoder");

			//
			service.disconnect("base64-decoder");

		} catch (Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testTransformA1() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String result = (String) service.transform("base64-encoder", "Hola");

			//
			if (!result.equals("SG9sYQ==")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testTransformA2() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String result = (String) service.transform("base64-decoder", "SG9sYQ==");

			//
			if (!result.equals("Hola")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
