package com.warework.core.util.helper;

import com.warework.core.scope.AbstractCoreTestCase;

/**
 * Tests common collections operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class CodecL1HelperTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testEncodeDecodeBase64_1() {

		//
		final String test = "Hello World";

		//
		try {

			//
			final String encode = CodecL1Helper.encodeBase64(test);

			//
			if (encode != null) {

				//
				final String decoded = CodecL1Helper.decodeBase64(encode);

				//
				if (!decoded.equals(test)) {
					fail();
				}

			} else {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testEncodeDecodeHex1() {

		//
		final String test = "Hello World";

		//
		try {

			//
			final String encode = CodecL1Helper.encodeHex(test);

			//
			if (encode != null) {

				//
				final String decoded = CodecL1Helper.decodeHex(encode);

				//
				if (!decoded.equals(test)) {
					fail();
				}

			} else {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
