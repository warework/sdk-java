package com.warework.core.util.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.warework.core.scope.AbstractModCoreExtTestCase;

/**
 * 
 */
public class DataStructureL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testGet2() {

		//
		Vector values = new Vector();
		{
			values.add("james");
			values.add("arnold");
			values.add("steve");
		}

		//
		try {

			//
			String value = (String) DataStructureL2Helper.get(values, 1);

			//
			if (!value.equals("arnold")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testGet4() {

		//
		Hashtable values = new Hashtable();
		{
			values.put(new Integer(0), "james");
			values.put(new Integer(1), "arnold");
			values.put(new Integer(2), "steve");
		}

		//
		try {

			//
			String value = (String) DataStructureL2Helper.get(values, 1);

			//
			if (!value.equals("arnold")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testGet5() {

		//
		String values = "a,b,c";

		//
		try {

			//
			String value = (String) DataStructureL2Helper.get(values, 1);

			//
			if (value != null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testSet2() {

		//
		Vector values = new Vector();
		{
			values.add("james");
			values.add("arnold");
			values.add("steve");
		}

		//
		try {

			//
			DataStructureL2Helper.set(values, 1, "wayne");

			//
			String value = (String) DataStructureL2Helper.get(values, 1);

			//
			if (!value.equals("wayne")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testSet4() {

		//
		Hashtable values = new Hashtable();
		{
			values.put(new Integer(0), "james");
			values.put(new Integer(1), "arnold");
			values.put(new Integer(2), "steve");
		}

		//
		try {

			//
			DataStructureL2Helper.set(values, 1, "wayne");

			//
			String value = (String) DataStructureL2Helper.get(values, 1);

			//
			if (!value.equals("wayne")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testMergeArrays() {

		//
		int[] result = (int[]) DataStructureL2Helper.mergeArrays(new int[] { 0,
				1, 2 }, new int[] { 3, 4, 5 });

		//
		if ((result == null) || (result.length != 6)) {
			fail();
		}

		//
		if ((result[0] != 0) || (result[1] != 1) || (result[2] != 2)
				|| (result[3] != 3) || (result[4] != 4) || (result[5] != 5)) {
			fail();
		}

	}

	public void testRemoveValues() {

		//
		int[] result = (int[]) DataStructureL2Helper.removeValues(new int[] {
				0, 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5 });

		//
		if ((result == null) || (result.length != 3)) {
			fail();
		}

		//
		if ((result[0] != 0) || (result[1] != 1) || (result[2] != 2)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public void testToCollection() {

		//
		List<Integer> result = null;
		try {
			result = (List<Integer>) DataStructureL2Helper.toCollection(
					ArrayList.class, new int[] { 1, 2, 3 });
		} catch (Exception e) {
			fail();
		}

		//
		if ((result.get(0).equals(0)) || (result.get(1).equals(1))
				|| (result.get(2).equals(2))) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testCopyMap1() {

		//
		Map<String, String> map1 = new HashMap<String, String>();
		{
			map1.put("1", "james");
			map1.put("2", "arnold");
		}

		//
		Map<String, String> map2 = new Hashtable<String, String>();
		{
			map2.put("3", "steve");
			map2.put("4", "david");
		}

		//
		try {

			//
			Map<String, String> result = DataStructureL2Helper.copyMap(map1,
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

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testRemoveDuplicated1() {

		//
		List<String> c1 = new Vector<String>();
		{
			c1.add("james");
			c1.add("steve");
			c1.add("james");
		}

		//
		List<String> c2 = DataStructureL2Helper.removeDuplicated(c1);

		//
		if ((c1 == null) || (c1.size() != 3)) {
			fail();
		}

		//
		if ((c2 == null) || (c2.size() != 2)) {
			fail();
		}

	}

	public void testRemoveDuplicated2() {

		//
		List<String> c1 = new Vector<String>();
		{
			c1.add("james");
			c1.add("steve");
		}

		//
		List<String> c2 = DataStructureL2Helper.removeDuplicated(c1);

		//
		if ((c1 == null) || (c1.size() != 2)) {
			fail();
		}

		//
		if ((c2 == null) || (c2.size() != 2)) {
			fail();
		}

	}

	public void testRemoveDuplicated3() {

		//
		List<String> c1 = new Vector<String>();

		//
		List<String> c2 = DataStructureL2Helper.removeDuplicated(c1);

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
