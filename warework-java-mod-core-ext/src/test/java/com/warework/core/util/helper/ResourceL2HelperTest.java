package com.warework.core.util.helper;

import java.net.URL;
import java.util.Locale;

import com.warework.core.scope.AbstractModCoreExtTestCase;

/**
 * 
 */
public class ResourceL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	public void testLoadProperties1() {

		//
		URL url1 = ResourceL2HelperTest.class.getResource("sample1.properties");

		//
		if (ResourceL2Helper.loadProperties(url1) == null) {
			fail();
		}

		//
		URL url2 = ResourceL2HelperTest.class.getResource("sample2.properties");

		//
		if (ResourceL2Helper.loadProperties(url2) != null) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testLoadResourceBundle1() {

		//
		try {
			if (!ResourceL2Helper
					.loadResourceBundle(
							ResourceL2HelperTest.class.getPackage(), "sample1",
							null).getString("name").equals("james")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (!ResourceL2Helper
					.loadResourceBundle(
							ResourceL2HelperTest.class.getPackage(), "sample1",
							Locale.UK).getString("name").equals("james_en_GB")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (ResourceL2Helper.loadResourceBundle(
					ResourceL2HelperTest.class.getPackage(), "sample2", null) != null) {
				fail();
			}
		} catch (Exception e) {
		}

	}

	public void testLoadResourceBundle2() {

		//
		try {
			if (!ResourceL2Helper.loadResourceBundle(null, "sample2", null)
					.getString("name").equals("james")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (!ResourceL2Helper
					.loadResourceBundle(null, "sample2", Locale.UK)
					.getString("name").equals("james_en_GB")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (ResourceL2Helper.loadResourceBundle(null, "sample1", null) != null) {
				fail();
			}
		} catch (Exception e) {
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testGetProperty1() {

		//
		try {
			if (!ResourceL2Helper.getProperty(
					ResourceL2HelperTest.class.getPackage(), "sample1", "name",
					null).equals("james")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (!ResourceL2Helper.getProperty(
					ResourceL2HelperTest.class.getPackage(), "sample1", "name",
					Locale.UK).equals("james_en_GB")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (ResourceL2Helper.getProperty(
					ResourceL2HelperTest.class.getPackage(), "sample2", "name",
					null) != null) {
				fail();
			}
		} catch (Exception e) {
		}

	}

	public void testGetProperty2() {

		//
		try {
			if (!ResourceL2Helper.getProperty(null, "sample2", "name", null)
					.equals("james")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (!ResourceL2Helper.getProperty(null, "sample2", "name",
					Locale.UK).equals("james_en_GB")) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}

		//
		try {
			if (ResourceL2Helper.getProperty(null, "sample1", "name", null) != null) {
				fail();
			}
		} catch (Exception e) {
		}

	}

}
