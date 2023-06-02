package com.warework.core.util.helper;

import com.warework.core.ArtifactMetadata;
import com.warework.core.scope.AbstractCoreTestCase;

/**
 * Tests common reflection operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ReflectionL1HelperTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 *
	 */
	private final class SampleArtifactMetadata1 implements ArtifactMetadata {

		public String getVersion() {
			return "1.0.0";
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testIsTypeA1() {

		try {

			if (!ReflectionL1Helper.isType(Integer.class, Number.class)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testIsTypeA2() {

		try {

			if (!ReflectionL1Helper.isType(Integer.class, Integer.class)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testValidateVersion1() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (!ReflectionL1Helper.validateVersion(am, "1.0.0")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion2() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (!ReflectionL1Helper.validateVersion(am, "0.9.0")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion3() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (!ReflectionL1Helper.validateVersion(am, "0.0.9")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion4() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (!ReflectionL1Helper.validateVersion(am, "0.9.9")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion5() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (ReflectionL1Helper.validateVersion(am, "1.0.1")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion6() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (ReflectionL1Helper.validateVersion(am, "1.1.0")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testValidateVersion7() {

		//
		final ArtifactMetadata am = new SampleArtifactMetadata1();

		try {

			if (ReflectionL1Helper.validateVersion(am, "2.0.0")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetClassName1() {
		if (!ReflectionL1Helper.getClassName(ReflectionL1Helper.class).equals("ReflectionL1Helper")) {
			fail();
		}
	}

}
