package com.warework.core.util.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.util.helper.beans.Sample1Bean;
import com.warework.core.util.helper.beans.Sample2Bean;
import com.warework.core.util.helper.beans.Sample3Bean;
import com.warework.core.util.helper.beans.Sample4Bean;

/**
 * 
 */
public class ReflectionL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings("rawtypes")
	public void testGetTypesA1() {

		//
		Object[] objects = new Object[] { "test", new Integer(4), Boolean.TRUE };

		//
		try {

			//
			Class[] types = ReflectionL2Helper.getTypes(objects);

			//
			if ((types != null) && (types.length == 3)) {

				//
				if (!types[0].equals(String.class)) {
					fail();
				}

				//
				if (!types[1].equals(Integer.class)) {
					fail();
				}

				//
				if (!types[2].equals(Boolean.class)) {
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
	public void testGetTypesA2() {

		//
		Object[] objects = new Object[] {};

		//
		try {

			//
			Class[] types = ReflectionL2Helper.getTypes(objects);

			//
			if ((types == null) || (types.length != 0)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testGetTypesA3() {

		//
		Object[] objects = new Object[] { "test", null, Boolean.TRUE };

		//
		try {

			//
			Class[] types = ReflectionL2Helper.getTypes(objects);

			//
			if ((types != null) && (types.length == 3)) {

				//
				if (!types[0].equals(String.class)) {
					fail();
				}

				//
				if (types[1] != null) {
					fail();
				}

				//
				if (!types[2].equals(Boolean.class)) {
					fail();
				}

			} else {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testIsTypeA1() {

		try {

			if (!ReflectionL2Helper.isType(Timestamp.class, Date.class)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings("rawtypes")
	public void testIsTypeB1() {

		//
		Class[] types = new Class[] { Calendar.class, Time.class, Date.class };

		try {

			if (!ReflectionL2Helper.isType(new Date(), types)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testIsTypeB2() {

		//
		Class[] types = new Class[] { Calendar.class, Time.class, Date.class };

		try {

			if (ReflectionL2Helper.isType("test", types)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testGetClassName1() {

		//
		if (!ReflectionL2Helper.getClassName(ReflectionL2Helper.class).equals(
				"ReflectionL2Helper")) {
			fail();
		}

	}

	@SuppressWarnings("rawtypes")
	public void testGetNumbers() {

		// Get the numbers' classes
		Class[] numbers = ReflectionL2Helper.getNumbers();

		//
		if (!numbers[0].equals(Byte.class)) {
			fail();
		} else if (!numbers[1].equals(Short.class)) {
			fail();
		} else if (!numbers[2].equals(Integer.class)) {
			fail();
		} else if (!numbers[3].equals(BigInteger.class)) {
			fail();
		} else if (!numbers[4].equals(Long.class)) {
			fail();
		} else if (!numbers[5].equals(Float.class)) {
			fail();
		} else if (!numbers[6].equals(Double.class)) {
			fail();
		} else if (!numbers[7].equals(BigDecimal.class)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	@SuppressWarnings("rawtypes")
	public void testGetPrimitiveNumbers() {

		// Get the numbers' classes
		Class[] numbers = ReflectionL2Helper.getPrimitiveNumbers();

		//
		if (!numbers[0].equals(byte.class)) {
			fail();
		} else if (!numbers[1].equals(short.class)) {
			fail();
		} else if (!numbers[2].equals(int.class)) {
			fail();
		} else if (!numbers[3].equals(long.class)) {
			fail();
		} else if (!numbers[4].equals(float.class)) {
			fail();
		} else if (!numbers[5].equals(double.class)) {
			fail();
		}

	}

	public void testIsPrimitiveType1() {

		//
		if ((!ReflectionL2Helper.isPrimitiveType(Boolean.TRUE))
				|| (!ReflectionL2Helper.isPrimitiveType(true))) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Byte.MIN_VALUE)) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Short.MIN_VALUE)) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Integer.MIN_VALUE)) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Long.MIN_VALUE)) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Float.MIN_VALUE)) {
			fail();
		}

		//
		if (!ReflectionL2Helper.isPrimitiveType(Double.MIN_VALUE)) {
			fail();
		}

		//
		if ((!ReflectionL2Helper.isPrimitiveType(new Character('x')))
				|| (!ReflectionL2Helper.isPrimitiveType('x'))) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	public void testInvokeStaticMethod1() {
		try {
			//
			Boolean result = (Boolean) ReflectionL2Helper.invokeStaticMethod(
					"hasDecimals", MathL1Helper.class,
					new Class[] { double.class }, new Object[] { 2.3f });

			//
			if ((result == null) || (result.equals(Boolean.FALSE))) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testInvokeStaticMethod2() {
		try {
			//
			Boolean result = (Boolean) ReflectionL2Helper.invokeStaticMethod(
					"hasDecimals",
					"com.warework.core.util.helper.MathL1Helper",
					new Class[] { double.class }, new Object[] { 2.3f });

			//
			if ((result == null) || (result.equals(Boolean.FALSE))) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	public void testSetAttributeValue1() {

		//
		Sample1Bean bean = new Sample1Bean();

		//
		try {
			ReflectionL2Helper.setAttributeValue(bean, "name", "john");
		} catch (Exception e) {
			fail();
		}

		//
		if ((bean.getName() == null) || (!bean.getName().equals("john"))) {
			fail();
		}

	}

	public void testSetAttributeValue2() {

		//
		Sample1Bean bean = new Sample1Bean();

		//
		try {
			ReflectionL2Helper.setAttributeValue(bean, "sample2",
					new Sample2Bean());
			ReflectionL2Helper.setAttributeValue(bean, "sample2.title", "mr.");
		} catch (Exception e) {
			fail();
		}

		//
		if ((bean.getSample2() == null)
				|| (!bean.getSample2().getTitle().equals("mr."))) {
			fail();
		}

	}

	public void testSetAttributeValue3() {

		//
		Sample1Bean bean1 = new Sample1Bean();
		{

			//
			Sample2Bean bean2 = new Sample2Bean();
			{

				//
				List<Sample1Bean> lista = new ArrayList<Sample1Bean>();
				{
					lista.add(new Sample1Bean());
					lista.add(new Sample1Bean());
					lista.add(new Sample1Bean());
				}

				//
				bean2.setSampleList(lista);

			}

			//
			bean1.setSample2(bean2);

		}

		//
		try {
			ReflectionL2Helper.setAttributeValue(bean1,
					"sample2.sampleList[1].name", "john");
		} catch (Exception e) {
			fail();
		}

		//
		if (!bean1.getSample2().getSampleList().get(1).getName().equals("john")) {
			fail();
		}

	}

	public void testSetAttributeValue4() {

		//
		Sample1Bean bean1 = new Sample1Bean();
		{

			//
			Sample2Bean bean2 = new Sample2Bean();
			{

				//
				Sample1Bean[] array = new Sample1Bean[3];
				{
					array[0] = new Sample1Bean();
					array[1] = new Sample1Bean();
					array[2] = new Sample1Bean();
				}

				//
				bean2.setSampleArray(array);

			}

			//
			bean1.setSample2(bean2);

		}

		//
		try {
			ReflectionL2Helper.setAttributeValue(bean1,
					"sample2.sampleArray[1].name", "john");
		} catch (Exception e) {
			fail();
		}

		//
		if (!bean1.getSample2().getSampleArray()[1].getName().equals("john")) {
			fail();
		}

	}

	public void testSetAttributeValue5() {

		//
		Sample1Bean bean1 = new Sample1Bean();
		{

			//
			Sample2Bean bean2 = new Sample2Bean();
			{

				//
				Map<String, Sample1Bean> map = new HashMap<String, Sample1Bean>();
				{
					map.put("one", new Sample1Bean());
					map.put("two", new Sample1Bean());
					map.put("three", new Sample1Bean());
				}

				//
				bean2.setSampleMap(map);

			}

			//
			bean1.setSample2(bean2);

		}

		//
		try {
			ReflectionL2Helper.setAttributeValue(bean1,
					"sample2.sampleMap['two'].name", "john");
		} catch (Exception e) {
			fail();
		}

		//
		if (!bean1.getSample2().getSampleMap().get("two").getName()
				.equals("john")) {
			fail();
		}

	}
	
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testMapProperties1() {

		//
		Sample3Bean bean = new Sample3Bean();
		{
			bean.setId(Integer.valueOf(1));
			bean.setTitle("James Bond");
		}

		//
		Map<String, Object> map = null;
		try {
			map = ReflectionL2Helper.toHashMap(bean);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//
		if (map == null) {
			fail();
		}

		//
		if (map.get("id") != null) {

			//
			Object value = map.get("id");

			//
			if (!(value instanceof Integer)
					|| (((Integer) value).intValue() != 1)) {
				fail();
			}

		} else {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testMapProperties2() {

		//
		Sample4Bean bean = new Sample4Bean();
		{
			bean.setTitle("James Bond");
		}

		//
		Map<String, Object> map = null;
		try {
			map = ReflectionL2Helper.toHashMap(bean);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//
		if (map == null) {
			fail();
		}

		//
		if (map.get("notBeanAttribute") != null) {
			fail();
		}
	}
	

	/**
	 * 
	 */
	public void testMapProperties3() {

		//
		Sample1Bean bean1 = new Sample1Bean();
		{

			//
			Sample2Bean bean2 = new Sample2Bean();
			{

				//
				Map<String, Sample1Bean> map = new HashMap<String, Sample1Bean>();
				{
					map.put("one", new Sample1Bean());
					map.put("two", new Sample1Bean());
					map.put("three", new Sample1Bean());
				}

				//
				bean2.setSampleMap(map);

			}

			//
			bean1.setSample2(bean2);

		}

		//
		Map<String, Object> map = null;
		try {
			map = ReflectionL2Helper.toHashMap(bean1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//
		if (map == null) {
			fail();
		}

		//
		if (map.get("sample2") == null) {
			fail();
		}
		
		//
		Object bean2 = map.get("sample2");
		if (!(bean2 instanceof Sample2Bean)){
			fail();
		}
		
	}

}
