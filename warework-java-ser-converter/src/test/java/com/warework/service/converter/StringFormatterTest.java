package com.warework.service.converter;

import com.warework.core.scope.AbstractSerConverterTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class StringFormatterTest extends AbstractSerConverterTestCase {

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
			service.connect("uppercase-formatter");

			//
			service.disconnect("uppercase-formatter");

		} catch (final Exception e) {
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
			service.connect("lowercase-formatter");

			//
			service.disconnect("lowercase-formatter");

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testConnect3() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("trim-formatter");

			//
			service.disconnect("trim-formatter");

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testConnect4() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("first-letter-to-lowercase-formatter");

			//
			service.disconnect("first-letter-to-lowercase-formatter");

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testConnect5() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("first-letter-to-uppercase-formatter");

			//
			service.disconnect("first-letter-to-uppercase-formatter");

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
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			String result = (String) service.transform("uppercase-formatter", "hola");

			//
			if (!result.equals("HOLA")) {
				fail();
			}

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
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			String result = (String) service.transform("lowercase-formatter", "HOLA");

			//
			if (!result.equals("hola")) {
				fail();
			}

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
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String result = (String) service.transform("trim-formatter", "  HOLA  ");

			//
			if (!result.equals("HOLA")) {
				fail();
			}

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
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String result = (String) service.transform("first-letter-to-lowercase-formatter", "HOLA");

			//
			if (!result.equals("hOLA")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testTransform5() {
		try {

			//
			final ScopeFacade scope = create("system-1");

			//
			final ConverterServiceFacade service = (ConverterServiceFacade) scope
					.getService(ConverterServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final String result = (String) service.transform("first-letter-to-uppercase-formatter", "hola");

			//
			if (!result.equals("Hola")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
