package com.warework.core.util.helper;

import java.util.Hashtable;

import com.warework.core.scope.AbstractCoreTestCase;

/**
 * Tests common strings operations.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class StringL1HelperTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testToUpperCase1() {

		//
		final String[] words = new String[] { "james", "arnold", "steve" };

		//
		final String[] result = StringL1Helper.toUpperCase(words);

		//
		if ((result != null) && (result.length == 3)) {

			if ((!result[0].equals("JAMES")) || (!result[1].equals("ARNOLD")) || (!result[2].equals("STEVE"))) {
				fail();
			}

		} else {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToUpperCase2() {

		//
		final String[] words = new String[] {};

		//
		final String[] result = StringL1Helper.toUpperCase(words);

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testToLowerCase1() {

		//
		final String[] words = new String[] { "JAMES", "ARNOLD", "STEVE" };

		//
		final String[] result = StringL1Helper.toLowerCase(words);

		//
		if ((result != null) && (result.length == 3)) {
			if ((!result[0].equals("james")) || (!result[1].equals("arnold")) || (!result[2].equals("steve"))) {
				fail();
			}
		} else {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToLowerCase2() {

		//
		final String[] words = new String[] {};

		//
		final String[] result = StringL1Helper.toLowerCase(words);

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testFirstLetterToUpperCase1() {
		if (!StringL1Helper.firstLetterToUpperCase("hello").equals("Hello")) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testFirstLetterToUpperCase2() {
		if (!StringL1Helper.firstLetterToUpperCase("h").equals("H")) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testFirstLetterToUpperCase3() {
		if (!StringL1Helper.firstLetterToUpperCase("").equals("")) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testFirstLetterToLowerCase1() {
		if (!StringL1Helper.firstLetterToLowerCase("Hello").equals("hello")) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testFirstLetterToLowerCase2() {
		if (!StringL1Helper.firstLetterToLowerCase("H").equals("h")) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testFirstLetterToLowerCase3() {
		if (!StringL1Helper.firstLetterToLowerCase("").equals("")) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testReplace1() {

		//
		final Hashtable<String, String> variables = new Hashtable<String, String>();
		{
			variables.put("name", "John");
		}

		//
		if (!StringL1Helper.replace("Name=${name}", variables).equals("Name=John")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testReplace2() {

		//
		final Hashtable<String, String> variables = new Hashtable<String, String>();
		{
			variables.put("name", "John");
			variables.put("age", "23");
		}

		//
		if (!StringL1Helper.replace("Name=${name};Age=${age}", variables).equals("Name=John;Age=23")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testReplace3() {

		//
		final Hashtable<String, String> variables = new Hashtable<String, String>();
		{
			variables.put("name", "John");
			variables.put("age", "23");
		}

		//
		if (!StringL1Helper.replace("Name1=${name};Name2=${name}", variables).equals("Name1=John;Name2=John")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testReplace4() {
		if (!StringL1Helper.replace("Name1=${name};Name2=${name}", null).equals("Name1=${name};Name2=${name}")) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testValues1() {

		//
		final String sample = "SELECT * FROM PEOPLE WHERE ID=${user.id} AND NAME=${user.name} AND ID2=${user.id}";

		//
		final Hashtable<String, Object> values = new Hashtable<String, Object>();
		{
			values.put("user.id", new Integer(1));
			values.put("user.name", "john");
		}

		//
		final Object[] result = StringL1Helper.values(sample, values);

		//
		if ((result != null) && (result.length == 3)) {

			//
			Object first = result[0];
			if (!(first instanceof Integer)) {
				fail();
			}

			//
			Object second = result[1];
			if (!(second instanceof String)) {
				fail();
			}

			//
			Object third = result[2];
			if (!(third instanceof Integer)) {
				fail();
			}

		} else {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetPropertyKey() {
		if (!StringL1Helper.getPropertyKey("name=james").equals("name")) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetPropertyValue() {
		if (!StringL1Helper.getPropertyValue("name=james").equals("james")) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testToStrings1() {

		//
		final String[] result = StringL1Helper.toStrings("james,arnold,steve", ',');

		//
		if ((result == null) || (result.length != 3)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}
		if (!result[1].equals("arnold")) {
			fail();
		}
		if (!result[2].equals("steve")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings2() {

		//
		final String[] result = StringL1Helper.toStrings("james,arnold,steve,", ',');

		//
		if ((result == null) || (result.length != 3)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}
		if (!result[1].equals("arnold")) {
			fail();
		}
		if (!result[2].equals("steve")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings3() {

		//
		final String[] result = StringL1Helper.toStrings("james", ',');

		//
		if ((result == null) || (result.length != 1)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings4() {

		//
		final String[] result = StringL1Helper.toStrings("james,", ',');

		//
		if ((result == null) || (result.length != 1)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings5() {

		//
		final String[] result = StringL1Helper.toStrings("", ',');

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings6() {

		//
		final String[] result = StringL1Helper.toStrings(",", ',');

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings7() {

		//
		final String[] result = StringL1Helper.toStrings(",,,,,,", ',');

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings8() {

		//
		final String[] result = StringL1Helper.toStrings(",james,arnold,steve", ',');

		//
		if ((result == null) || (result.length != 3)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}
		if (!result[1].equals("arnold")) {
			fail();
		}
		if (!result[2].equals("steve")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStrings9() {

		//
		final String[] result = StringL1Helper.toStrings(",james,arnold,steve,", ',');

		//
		if ((result == null) || (result.length != 3)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}
		if (!result[1].equals("arnold")) {
			fail();
		}
		if (!result[2].equals("steve")) {
			fail();
		}

	}

}
