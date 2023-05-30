package com.warework.core.util.helper;

import java.util.ArrayList;
import java.util.Collection;

import com.warework.core.scope.AbstractModCoreExtTestCase;

/**
 * 
 */
public class MathL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetGreatestA1() {

		try {

			//
			if (MathL2Helper.getGreatest(new Number[] {}) != null) {
				fail();
			}

			//
			if (!MathL2Helper.getGreatest(new Number[] { 0, 215, 100, 99 })
					.equals(215)) {
				fail();
			}

			//
			if (!MathL2Helper.getGreatest(new Number[] { 215, 0, 100, 99 })
					.equals(215)) {
				fail();
			}

			//
			if (!MathL2Helper.getGreatest(new Number[] { 1, 0, 100, 215 })
					.equals(215)) {
				fail();
			}

			//
			if (!MathL2Helper.getGreatest(new Number[] { 1, 0, 368.67, 215 })
					.equals(368.67)) {
				fail();
			}

			//
			if (!MathL2Helper.getGreatest(
					new Number[] { 1, Double.MIN_VALUE, 368.67,
							Double.MAX_VALUE }).equals(Double.MAX_VALUE)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetGreatestB1() {

		try {

			//
			Collection<Number> numbers = new ArrayList<Number>();
			if (MathL2Helper.getGreatest(new Number[] {}) != null) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(0);
				numbers.add(215);
				numbers.add(100);
				numbers.add(99);
			}
			if (!MathL2Helper.getGreatest(numbers).equals(215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(215);
				numbers.add(0);
				numbers.add(100);
				numbers.add(99);
			}
			if (!MathL2Helper.getGreatest(numbers).equals(215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(1);
				numbers.add(0);
				numbers.add(100);
				numbers.add(215);
			}
			if (!MathL2Helper.getGreatest(numbers).equals(215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(1);
				numbers.add(0);
				numbers.add(368.67);
				numbers.add(215);
			}
			if (!MathL2Helper.getGreatest(numbers).equals(368.67)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(0);
				numbers.add(Double.MIN_VALUE);
				numbers.add(100);
				numbers.add(Double.MAX_VALUE);
			}
			if (!MathL2Helper.getGreatest(numbers).equals(Double.MAX_VALUE)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetSmallestA1() {

		try {

			//
			if (MathL2Helper.getSmallest(new Number[] {}) != null) {
				fail();
			}

			//
			if (!MathL2Helper.getSmallest(new Number[] { 0, -5, 100, 99 })
					.equals(-5)) {
				fail();
			}

			//
			if (!MathL2Helper.getSmallest(new Number[] { -5, 0, 100, 99 })
					.equals(-5)) {
				fail();
			}

			//
			if (!MathL2Helper.getSmallest(new Number[] { 1, 0, 100, -5 })
					.equals(-5)) {
				fail();
			}

			//
			if (!MathL2Helper.getSmallest(new Number[] { 1, 0, -368.67, 215 })
					.equals(-368.67)) {
				fail();
			}

			//
			if (!MathL2Helper.getSmallest(
					new Number[] { 1, -Double.MIN_VALUE, 368.67,
							Double.MAX_VALUE }).equals(-Double.MIN_VALUE)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetSmallestB1() {

		try {

			//
			Collection<Number> numbers = new ArrayList<Number>();
			if (MathL2Helper.getSmallest(new Number[] {}) != null) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(0);
				numbers.add(-215);
				numbers.add(100);
				numbers.add(99);
			}
			if (!MathL2Helper.getSmallest(numbers).equals(-215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(-215);
				numbers.add(0);
				numbers.add(100);
				numbers.add(99);
			}
			if (!MathL2Helper.getSmallest(numbers).equals(-215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(1);
				numbers.add(0);
				numbers.add(100);
				numbers.add(-215);
			}
			if (!MathL2Helper.getSmallest(numbers).equals(-215)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(1);
				numbers.add(0);
				numbers.add(-368.67);
				numbers.add(215);
			}
			if (!MathL2Helper.getSmallest(numbers).equals(-368.67)) {
				fail();
			}

			//
			numbers = new ArrayList<Number>();
			{
				numbers.add(0);
				numbers.add(-Double.MIN_VALUE);
				numbers.add(100);
				numbers.add(Double.MAX_VALUE);
			}
			if (!MathL2Helper.getSmallest(numbers).equals(-Double.MIN_VALUE)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetDoubleA1() {

		//
		if (MathL2Helper.getDouble(25.6478, 2) != 25.65) {
			fail();
		}

		//
		if (MathL2Helper.getDouble(25.64, 4) != 25.6400) {
			fail();
		}

		//
		if (MathL2Helper.getDouble(-25.6478, 2) != -25.65) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetFloatA1() {

		//
		if (MathL2Helper.getFloat(25.6478f, 2) != 25.65f) {
			fail();
		}

		//
		if (MathL2Helper.getFloat(25.64f, 4) != 25.6400f) {
			fail();
		}

		//
		if (MathL2Helper.getFloat(-25.6478f, 2) != -25.65f) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testHasDecimalsA1() {

		//
		if (MathL2Helper.hasDecimals(212)) {
			fail();
		}

		//
		if (!MathL2Helper.hasDecimals(212.1)) {
			fail();
		}

		//
		if (MathL2Helper.hasDecimals(-212)) {
			fail();
		}

		//
		if (!MathL2Helper.hasDecimals(-212.1)) {
			fail();
		}

		//
		if (MathL2Helper.hasDecimals(0)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetDecimals() {

		//
		if (MathL2Helper.getDouble(MathL2Helper.getDecimals(212.8), 1) != 0.8) {
			fail();
		}

		//
		if (MathL2Helper.getDouble(MathL2Helper.getDecimals(-212.8), 1) != -0.8) {
			fail();
		}

	}

}
