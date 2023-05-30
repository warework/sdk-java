package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class JavaScriptCompressorTest extends AbstractSerConverterTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	private final static String CONVERTER_NAME = "js-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * There's no need to connect/disconnect this Client
	 */

	public void testConnect1() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CONVERTER_NAME);

			//
			service.disconnect(CONVERTER_NAME);

		} catch (final Exception e) {
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
			final String js = "/**" + "\n" + " * Shows an alert box and displays 'Hello'." + "\n" + " */" + "\n"
					+ "function sayHello() { " + "\n" + "	" + "\n" + "	// Remove every option from the combo."
					+ "\n" + "	alert('Hello');" + "\n" + "	" + "\n" + "}";

			//
			final String result = (String) service.transform(CONVERTER_NAME, js);

			//
			if (!result.equals("function sayHello(){alert('Hello');}")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
