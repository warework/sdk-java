package com.warework.core.util.helper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.warework.core.scope.AbstractModCoreExtTestCase;

/**
 * 
 */
public class StringL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	public void testToStringA1() {

		//
		if (!StringL2Helper.toString(new Float(2.3), null, Locale.ENGLISH, null).equals("2.3")) {
			fail();
		}

		//
		if (!StringL2Helper.toString(2.3f, null, Locale.ENGLISH, null).equals("2.3")) {
			fail();
		}

		//
		if (!StringL2Helper.toString(new Float(1234), "0.###E0", Locale.ENGLISH, null).equals("1.234E3")) {
			fail();
		}

		//
		if (!StringL2Helper.toString(1234f, "0.###E0", Locale.ENGLISH, null).equals("1.234E3")) {
			fail();
		}

	}

	public void testToStringA2() {

		if (!StringL2Helper.toString(new Double(2.3), null, Locale.ENGLISH, null).equals("2.3")) {
			fail();
		}

		if (!StringL2Helper.toString(2.3d, null, Locale.ENGLISH, null).equals("2.3")) {
			fail();
		}

		if (!StringL2Helper.toString(new Double(1234), "0.###E0", Locale.ENGLISH, null).equals("1.234E3")) {
			fail();
		}

		if (!StringL2Helper.toString(1234d, "0.###E0", Locale.ENGLISH, null).equals("1.234E3")) {
			fail();
		}

	}

	public void testToStringA3() {

		//
		if (!StringL2Helper.toString(new BigDecimal(2.3), null, Locale.ENGLISH, null).equals("2.3")) {
			fail();
		}

		//
		if (!StringL2Helper.toString(new BigDecimal(1234), "0.###E0", Locale.ENGLISH, null).equals("1.234E3")) {
			fail();
		}

	}

	public void testToStringA4() {

		//
		Date date = null;
		try {
			date = (Date) StringL2Helper.parse(Date.class, "2010/03/18 17:00:00", "yyyy/MM/dd HH:mm:ss", Locale.ENGLISH,
					TimeZone.getTimeZone("Europe/London"));
		} catch (ParseException e) {
			fail();
		}

		//
		if (!StringL2Helper.toString(date, "yyyy/MM/dd HH:mm:ss", Locale.ENGLISH, TimeZone.getTimeZone("Europe/London"))
				.equals("2010/03/18 17:00:00")) {
			fail();
		}

	}

	public void testToStringA5() {

		//
		Calendar calendar = null;
		try {
			calendar = (Calendar) StringL2Helper.parse(Calendar.class, "2010/03/18 17:00:00", "yyyy/MM/dd HH:mm:ss",
					Locale.ENGLISH, TimeZone.getTimeZone("Europe/London"));
		} catch (ParseException e) {
			fail();
		}

		//
		if (!StringL2Helper
				.toString(calendar, "yyyy/MM/dd HH:mm:ss", Locale.ENGLISH, TimeZone.getTimeZone("Europe/London"))
				.equals("2010/03/18 17:00:00")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testToStringB1() {

		//
		Float[] values = new Float[] { 2.3f, 3.5f, 7.5f };

		//
		if (!StringL2Helper.toString(values, null, Locale.ENGLISH, null, ";").equals("2.3;3.5;7.5")) {
			fail();
		}

	}

	public void testToStringB2() {

		//
		Float[] values = new Float[] { 2.3f };

		//
		if (!StringL2Helper.toString(values, null, Locale.ENGLISH, null, null).equals("2.3")) {
			fail();
		}

	}

	public void testToStringB3() {

		//
		Float[] values = new Float[] {};

		//
		if (!StringL2Helper.toString(values, null, Locale.ENGLISH, null, null).equals("")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testToStringC1() {

		//
		URL url = StringL2HelperTest.class.getResource("sample1.txt");

		//
		try {
			if (!StringL2Helper.toString(url).equals("name=james")) {
				fail();
			}
		} catch (IOException e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testToStringD1() {

		//
		Map<String, String> map = new HashMap<String, String>();
		{
			map.put("name", "james");
			map.put("age", "24");
		}

		//
		if ((!StringL2Helper.toString(map, null, null, null, ";").equals("name=james;age=24"))
				&& (!StringL2Helper.toString(map, null, null, null, ";").equals("age=24;name=james"))) {
			fail();
		}

	}

	public void testToStringD2() {

		//
		Map<String, String> map = new HashMap<String, String>();
		{
			map.put("name", "james");
		}

		//
		if (!StringL2Helper.toString(map, null, null, null, ";").equals("name=james")) {
			fail();
		}

	}

	public void testToStringD3() {

		//
		Map<String, String> map = new HashMap<String, String>();

		//
		if (!StringL2Helper.toString(map, null, null, null, ";").equals("")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testToStrings1() {

		//
		String[] result = StringL2Helper.toStrings("james,arnold,steve", ",");

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

	public void testToStrings2() {

		//
		String[] result = StringL2Helper.toStrings("james,arnold,steve,", ",");

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

	public void testToStrings3() {

		//
		String[] result = StringL2Helper.toStrings("james", ",");

		//
		if ((result == null) || (result.length != 1)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}

	}

	public void testToStrings4() {

		//
		String[] result = StringL2Helper.toStrings("james,", ",");

		//
		if ((result == null) || (result.length != 1)) {
			fail();
		}

		//
		if (!result[0].equals("james")) {
			fail();
		}

	}

	public void testToStrings5() {

		//
		String[] result = StringL2Helper.toStrings("", ",");

		//
		if ((result == null) || (result.length != 1)) {
			fail();
		}

	}

	public void testToStrings6() {

		//
		String[] result = StringL2Helper.toStrings(",", ",");

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	public void testToStrings7() {

		//
		String[] result = StringL2Helper.toStrings(",,,,,,", ",");

		//
		if ((result == null) || (result.length != 0)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testRemoveCharacters1() {

		//
		String result = StringL2Helper.removeCharacters("james,arnold,steve", new char[] { ',' });

		//
		if (!result.equals("jamesarnoldsteve")) {
			fail();
		}

	}

	public void testRemoveCharacters2() {

		//
		String result = StringL2Helper.removeCharacters("james", new char[] { ',' });

		//
		if (!result.equals("james")) {
			fail();
		}

	}

	public void testRemoveCharacters3() {

		//
		String result = StringL2Helper.removeCharacters("", new char[] { ',' });

		//
		if (!result.equals("")) {
			fail();
		}

	}

	public void testRemoveCharacters4() {

		//
		String result = StringL2Helper.removeCharacters("james,arnold,steve", new char[] {});

		//
		if (!result.equals("james,arnold,steve")) {
			fail();
		}

	}

	public void testRemoveCharacters5() {

		//
		String result = StringL2Helper.removeCharacters("james,arnold;steve", new char[] { ',', ';' });

		//
		if (!result.equals("jamesarnoldsteve")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testLeaveCharacters1() {

		//
		String result = StringL2Helper.leaveCharacters("james,arnold,steve", new char[] { 'a' });

		//
		if (!result.equals("aa")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testGetPropertyKey() {

		if (!StringL2Helper.getPropertyKey("name=james").equals("name")) {
			fail();
		}

	}

	public void testGetPropertyValue() {

		if (!StringL2Helper.getPropertyValue("name=james").equals("james")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testToHashMap1() {

		//
		Map<String, String> map = StringL2Helper.toHashMap("name=james;age=13", ";");

		//
		if (!map.get("name").equals("james")) {
			fail();
		}
		if (!map.get("age").equals("13")) {
			fail();
		}

	}

	public void testToHashMap2() {

		//
		Map<String, String> map = StringL2Helper.toHashMap("name=james", ";");

		//
		if (!map.get("name").equals("james")) {
			fail();
		}

	}

	public void testToHashMap3() {

		//
		Map<String, String> map = StringL2Helper.toHashMap("", ";");

		//
		if (map.size() != 0) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testReplace1() {

		//
		String string = "My name is not ${NAME} and my age is not ${AGE}";

		//
		Map<String, String> values = new HashMap<String, String>();
		{
			values.put("NAME", "James");
			values.put("AGE", "24");
		}

		//
		String result = StringL2Helper.replace(string, values);

		//
		if (!result.equals("My name is not James and my age is not 24")) {
			fail();
		}

	}

	public void testReplace2() {

		//
		String string = "My name is not ${NAME} and my age is not ${AGE}";

		//
		Map<String, String> values = new HashMap<String, String>();

		//
		String result = StringL2Helper.replace(string, values);

		//
		if (!result.equals(string)) {
			fail();
		}

	}

	public void testReplace3() {

		//
		String string = "My name is not ${NAME} and my age is not ${AGE}";

		//
		String result = StringL2Helper.replace(string, (Map<String, String>) null);

		//
		if (!result.equals(string)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testParse1() {

		//
		try {

			//
			boolean value = (Boolean) StringL2Helper.parse(boolean.class, "true", null, null, null);

			//
			if (!value) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			boolean value = (Boolean) StringL2Helper.parse(Boolean.class, "true", null, null, null);

			//
			if (!value) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse2() {

		//
		try {

			//
			byte value = (Byte) StringL2Helper.parse(byte.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Byte value = (Byte) StringL2Helper.parse(Byte.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse3() {

		//
		try {

			//
			short value = (Short) StringL2Helper.parse(short.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Short value = (Short) StringL2Helper.parse(Short.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse4() {

		//
		try {

			//
			int value = (Integer) StringL2Helper.parse(int.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Integer value = (Integer) StringL2Helper.parse(Integer.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse5() {

		//
		try {

			//
			long value = (Long) StringL2Helper.parse(long.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Long value = (Long) StringL2Helper.parse(Long.class, "2", null, null, null);

			//
			if (value != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse6() {

		try {

			//
			BigInteger value = (BigInteger) StringL2Helper.parse(BigInteger.class, "2", null, null, null);

			//
			if (value.intValue() != 2) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse7() {

		try {

			//
			BigDecimal value = (BigDecimal) StringL2Helper.parse(BigDecimal.class, "2.2", null, Locale.ENGLISH, null);

			//
			if (value.floatValue() != 2.2f) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse8() {

		//
		try {

			//
			float value = (Float) StringL2Helper.parse(float.class, "2.2", null, Locale.ENGLISH, null);

			//
			if (value != 2.2f) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Float value = (Float) StringL2Helper.parse(Float.class, "2.2", null, Locale.ENGLISH, null);

			//
			if (value != 2.2f) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse9() {

		//
		try {

			//
			double value = (Double) StringL2Helper.parse(double.class, "2.2", null, Locale.ENGLISH, null);

			//
			if (value != 2.2d) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Double value = (Double) StringL2Helper.parse(Double.class, "2.2", null, Locale.ENGLISH, null);

			//
			if (value != 2.2d) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse10() {

		try {

			//
			String value = (String) StringL2Helper.parse(String.class, "hello", null, null, null);

			//
			if (!value.equals("hello")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse10a() {

		try {

			//
			StringBuffer value = (StringBuffer) StringL2Helper.parse(StringBuffer.class, "hello", null, null, null);

			//
			if (!value.toString().equals("hello")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse10b() {

		try {

			//
			StringBuilder value = (StringBuilder) StringL2Helper.parse(StringBuilder.class, "hello", null, null, null);

			//
			if (!value.toString().equals("hello")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse11() {

		//
		try {

			//
			char value = (Character) StringL2Helper.parse(char.class, "h", null, null, null);

			//
			if (value != 'h') {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

		//
		try {

			//
			Character value = (Character) StringL2Helper.parse(Character.class, "h", null, null, null);

			//
			if (value != 'h') {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse12() {

		try {

			//
			Date value = (Date) StringL2Helper.parse(Date.class, "2010/03/18 17:00:00", "yyyy/MM/dd HH:mm:ss", null,
					null);

			//
			if (!StringL2Helper.toString(value, "yyyy/MM/dd HH:mm:ss", null, null).equals("2010/03/18 17:00:00")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse13() {

		try {

			//
			Timestamp value = (Timestamp) StringL2Helper.parse(Timestamp.class, "2010/03/18 17:00:00",
					"yyyy/MM/dd HH:mm:ss", null, null);

			//
			if (!StringL2Helper.toString(value, "yyyy/MM/dd HH:mm:ss", null, null).equals("2010/03/18 17:00:00")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse14() {

		try {

			//
			Time value = (Time) StringL2Helper.parse(Time.class, "2010/03/18 17:00:00", "yyyy/MM/dd HH:mm:ss", null,
					null);

			//
			if (!StringL2Helper.toString(value, "yyyy/MM/dd HH:mm:ss", null, null).equals("2010/03/18 17:00:00")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse15() {

		try {

			//
			Calendar value = (Calendar) StringL2Helper.parse(Calendar.class, "2010/03/18 17:00:00",
					"yyyy/MM/dd HH:mm:ss", null, null);

			//
			if (!StringL2Helper.toString(value, "yyyy/MM/dd HH:mm:ss", null, null).equals("2010/03/18 17:00:00")) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse16() {

		try {

			//
			Locale value = (Locale) StringL2Helper.parse(Locale.class, Locale.ENGLISH.toString(), null, null, null);

			//
			if (!value.toString().equals(Locale.ENGLISH.toString())) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse17() {

		try {

			//
			TimeZone value = (TimeZone) StringL2Helper.parse(TimeZone.class, TimeZone.getDefault().getID(), null, null,
					null);

			//
			if (!value.getID().equals(TimeZone.getDefault().getID())) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse18() {

		try {

			//
			ArrayList<String> value = (ArrayList<String>) StringL2Helper.parse(ArrayList.class, "1,2,3", ",", null,
					null);

			//
			if (value != null) {

				//
				if (!value.get(0).equals("1")) {
					fail();
				}

				//
				if (!value.get(1).equals("2")) {
					fail();
				}

				//
				if (!value.get(2).equals("3")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse19() {

		try {

			//
			Vector<String> value = (Vector<String>) StringL2Helper.parse(Vector.class, "1,2,3", ",", null, null);

			//
			if (value != null) {

				//
				if (!value.get(0).equals("1")) {
					fail();
				}

				//
				if (!value.get(1).equals("2")) {
					fail();
				}

				//
				if (!value.get(2).equals("3")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse20() {

		try {

			//
			LinkedList<String> value = (LinkedList<String>) StringL2Helper.parse(LinkedList.class, "1,2,3", ",", null,
					null);

			//
			if (value != null) {

				//
				if (!value.get(0).equals("1")) {
					fail();
				}

				//
				if (!value.get(1).equals("2")) {
					fail();
				}

				//
				if (!value.get(2).equals("3")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse21() {

		try {

			//
			CopyOnWriteArrayList<String> value = (CopyOnWriteArrayList<String>) StringL2Helper
					.parse(CopyOnWriteArrayList.class, "1,2,3", ",", null, null);

			//
			if (value != null) {

				//
				if (!value.get(0).equals("1")) {
					fail();
				}

				//
				if (!value.get(1).equals("2")) {
					fail();
				}

				//
				if (!value.get(2).equals("3")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse22() {

		try {

			//
			Stack<String> value = (Stack<String>) StringL2Helper.parse(Stack.class, "1,2,3", ",", null, null);

			//
			if (value != null) {

				//
				if (!value.get(0).equals("1")) {
					fail();
				}

				//
				if (!value.get(1).equals("2")) {
					fail();
				}

				//
				if (!value.get(2).equals("3")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse23() {

		try {

			//
			HashMap<String, String> value = (HashMap<String, String>) StringL2Helper.parse(HashMap.class,
					"name=james;age=34", ";", null, null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testParse24() {

		try {

			//
			Hashtable<String, String> value = (Hashtable<String, String>) StringL2Helper.parse(Hashtable.class,
					"name=james;age=34", ";", null, null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testParse25() {

		try {

			//
			ConcurrentHashMap value = (ConcurrentHashMap) StringL2Helper.parse(ConcurrentHashMap.class,
					"name=james;age=34", ";", null, null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testParse26() {

		try {

			//
			LinkedHashMap value = (LinkedHashMap) StringL2Helper.parse(LinkedHashMap.class, "name=james;age=34", ";",
					null, null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	public void testParse27() {

		try {

			//
			Properties value = (Properties) StringL2Helper.parse(Properties.class, "name=james;age=34", ";", null,
					null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testParse28() {

		try {

			//
			TreeMap value = (TreeMap) StringL2Helper.parse(TreeMap.class, "name=james;age=34", ";", null, null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testParse29() {

		try {

			//
			WeakHashMap value = (WeakHashMap) StringL2Helper.parse(WeakHashMap.class, "name=james;age=34", ";", null,
					null);

			//
			if (value != null) {

				//
				if (!value.get("name").equals("james")) {
					fail();
				}

				//
				if (!value.get("age").equals("34")) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testParse30() {

		//
		try {

			//
			final File value = (File) StringL2Helper.parse(File.class,
					"src/test/resources/META-INF/warework-java-mod-core-ext/system-1.xml", null, null, null);

			//
			if (!value.isFile()) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

		//
		try {

			//
			final File value = (File) StringL2Helper.parse(File.class, "sampleX.properties", null, null, null);

			//
			if (value.isFile()) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	public void testParse31() {

		try {

			//
			URL url = StringL2HelperTest.class.getResource("sample1.properties");

			//
			URL value = (URL) StringL2Helper.parse(URL.class, url.toExternalForm(), null, null, null);

			//
			if (!value.toExternalForm().equals(url.toExternalForm())) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testParse32() {

		try {

			//
			Class value = (Class) StringL2Helper.parse(Class.class, "java.lang.Integer", null, null, null);

			//
			if (!value.equals(Integer.class)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

}
