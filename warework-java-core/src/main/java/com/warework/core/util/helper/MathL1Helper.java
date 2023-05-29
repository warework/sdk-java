package com.warework.core.util.helper;

import java.util.Collection;
import java.util.Iterator;

/**
 * Performs common math operations.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class MathL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Base 10 for exponential functions.
	 */
	protected static final int BASE_10 = 10;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected MathL1Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the greatest number from a set of numbers.
	 * 
	 * @param numbers
	 *            Array of numbers.<br>
	 * <br>
	 * @return Greatest number.<br>
	 * <br>
	 */
	public static Number getGreatest(Number[] numbers) {

		// Setup the greatest number to return.
		Number greatest = null;

		// Search the greatest number.
		for (int i = 0; i < numbers.length; i++) {

			// Get a number.
			Number currentNumber = numbers[i];

			// Operate only if it's not null.
			if (currentNumber != null) {
				if (greatest == null) {
					greatest = currentNumber;
				} else if (greatest.doubleValue() < currentNumber.doubleValue()) {
					greatest = currentNumber;
				}
			}

		}

		// Return the greatest number.
		return greatest;

	}

	/**
	 * Gets the greatest number from a set of numbers.
	 * 
	 * @param numbers
	 *            Collection of numbers.<br>
	 * <br>
	 * @return Greatest number.<br>
	 * <br>
	 */
	public static Number getGreatest(Collection<Number> numbers) {

		// Setup the greatest number to return.
		Number greatest = null;

		// Search the greatest number.
		for (Iterator<Number> iterator = numbers.iterator(); iterator.hasNext();) {

			// Get a number.
			Number currentNumber = iterator.next();

			// Operate only if it's not null.
			if (currentNumber != null) {
				if (greatest == null) {
					greatest = currentNumber;
				} else if (greatest.doubleValue() < currentNumber.doubleValue()) {
					greatest = currentNumber;
				}
			}

		}

		// Return the greatest number.
		return greatest;

	}

	/**
	 * Gets the smallest number from a set of numbers.
	 * 
	 * @param numbers
	 *            Array of numbers.<br>
	 * <br>
	 * @return Smallest number.<br>
	 * <br>
	 */
	public static Number getSmallest(Number[] numbers) {

		// Setup the smallest number to return.
		Number smallest = null;

		// Search the smallest number.
		for (int i = 0; i < numbers.length; i++) {

			// Get a number.
			Number currentNumber = numbers[i];

			// Operate only if it's not null.
			if (currentNumber != null) {
				if (smallest == null) {
					smallest = currentNumber;
				} else if (smallest.doubleValue() > currentNumber.doubleValue()) {
					smallest = currentNumber;
				}
			}

		}

		// Return the smallest number.
		return smallest;

	}

	/**
	 * Gets the smallest number from a set of numbers.
	 * 
	 * @param numbers
	 *            Collection of numbers.<br>
	 * <br>
	 * @return Smallest number.<br>
	 * <br>
	 */
	public static Number getSmallest(Collection<Number> numbers) {

		// Setup the smallest number to return.
		Number smallest = null;

		// Search the smallest number.
		for (Iterator<Number> iterator = numbers.iterator(); iterator.hasNext();) {

			// Get a number.
			Number currentNumber = iterator.next();

			// Operate only if it's not null.
			if (currentNumber != null) {
				if (smallest == null) {
					smallest = currentNumber;
				} else if (smallest.doubleValue() > currentNumber.doubleValue()) {
					smallest = currentNumber;
				}
			}

		}

		// Return the smallest number.
		return smallest;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Decides if a double has decimals (if it is an integer value).
	 * 
	 * @param value
	 *            Value.<br>
	 * <br>
	 * @return <code>true</code> if the double has decimals and false if not.<br>
	 * <br>
	 */
	public static boolean hasDecimals(double value) {

		// Extract the decimal part.
		double decimal;
		if (value >= 0) {
			decimal = value / (Math.floor(value));
		} else {
			decimal = value / (Math.ceil(value));
		}

		// Return if double has decimals.
		return (decimal > 1);

	}

	/**
	 * Gets the decimals of a double.
	 * 
	 * @param value
	 *            Value.<br>
	 * <br>
	 * @return Decimals of a double.<br>
	 * <br>
	 */
	public static double getDecimals(double value) {
		if (value >= 0) {
			return value - Math.floor(value);
		} else {
			return value - Math.ceil(value);
		}
	}

}
