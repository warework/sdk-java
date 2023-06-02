package com.warework.core.util.helper;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.warework.core.scope.AbstractCoreTestCase;

/**
 * Tests common collections operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class DataStructureL1HelperTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testCopyHashtable1() {

		//
		final Map<String, String> map1 = new Hashtable<String, String>();
		{
			map1.put("1", "james");
			map1.put("2", "arnold");
		}

		//
		final Map<String, String> map2 = new Hashtable<String, String>();
		{
			map2.put("3", "steve");
			map2.put("4", "david");
		}

		//
		try {

			//
			final Map<String, String> result = DataStructureL1Helper.copyMap(map1,
					map2);

			//
			if ((!result.get("1").equals("james"))
					&& (!result.get("2").equals("arnold"))
					&& (!result.get("3").equals("steve"))
					&& (!result.get("4").equals("david"))) {
				fail();
			}

			//
			if ((!map2.get("1").equals("james"))
					&& (!map2.get("2").equals("arnold"))
					&& (!map2.get("3").equals("steve"))
					&& (!map2.get("4").equals("david"))) {
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
	public void testRemoveDuplicated1() {

		//
		final List<String> c1 = new Vector<String>();
		{
			c1.add("james");
			c1.add("steve");
			c1.add("james");
		}

		//
		final List<String> c2 = DataStructureL1Helper.removeDuplicated(c1);

		//
		if ((c1 == null) || (c1.size() != 3)) {
			fail();
		}

		//
		if ((c2 == null) || (c2.size() != 2)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRemoveDuplicated2() {

		//
		final List<String> c1 = new Vector<String>();
		{
			c1.add("james");
			c1.add("steve");
		}

		//
		final List<String> c2 = DataStructureL1Helper.removeDuplicated(c1);

		//
		if ((c1 == null) || (c1.size() != 2)) {
			fail();
		}

		//
		if ((c2 == null) || (c2.size() != 2)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRemoveDuplicated3() {

		//
		final List<String> c1 = new Vector<String>();

		//
		final List<String> c2 = DataStructureL1Helper.removeDuplicated(c1);

		//
		if ((c1 == null) || (c1.size() != 0)) {
			fail();
		}

		//
		if ((c2 == null) || (c2.size() != 0)) {
			fail();
		}

	}

}
