package com.warework.core.util.helper;


/**
 * Performs common math operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class MathL2Helper extends MathL1Helper {

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
	protected MathL2Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a double with the number of decimals indicated.
	 * 
	 * @param number
	 *            Value.<br>
	 * <br>
	 * @param decimals
	 *            Amount of decimals.<br>
	 * <br>
	 * @return Rounded value.<br>
	 * <br>
	 */
	public static double getDouble(double number, int decimals) {

		// Get 10^decimals.
		long aux = (long) Math.pow(BASE_10, decimals);

		// Return the double with the number of decimals indicated.
		return (double) Math.round(number * aux) / aux;

	}

	/**
	 * Gets a float with the number of decimals indicated.
	 * 
	 * @param number
	 *            Value.<br>
	 * <br>
	 * @param decimals
	 *            Amount of decimals.<br>
	 * <br>
	 * @return Rounded value.<br>
	 * <br>
	 */
	public static float getFloat(float number, int decimals) {

		// Get 10^decimals.
		long aux = (long) Math.pow(BASE_10, decimals);

		// Return the double with the number of decimals indicated.
		return (float) Math.round(number * aux) / aux;

	}

}