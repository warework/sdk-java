package com.warework.module.business.api;

import java.io.Serializable;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ApiRate implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = -5335187507545816093L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRBIUTES
	// ///////////////////////////////////////////////////////////////////

	// Minutes
	private long timeFrameLength;

	//
	private long timeFrameStart;

	//
	private int operationsCounter = 0;

	//
	private int operationsLimit;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRCUTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param length     Time frame length in milliseconds when a specific amount of
	 *                   operations can be executed.<br>
	 *                   <br>
	 * @param operations Number of operations that can be executed in the time
	 *                   frame.<br>
	 *                   <br>
	 */
	public ApiRate(final long length, final int operations) {

		//
		timeFrameLength = length;

		//
		timeFrameStart = System.currentTimeMillis();

		//
		operationsLimit = operations;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Perform basic validation and increases the number of operations executed.
	 * 
	 * @return
	 */
	public final boolean increase() {
		if (validate()) {

			// Reset time frame operations counter and start time.
			reset();

			//
			if (operationsCounter > operationsLimit) {
				return false;
			} else {
				operationsCounter = operationsCounter + 1;
			}

			//
			return true;

		} else {
			return false;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	public final int getOperationsLimit() {
		return operationsLimit;
	}

	/**
	 * 
	 * @return
	 */
	public final int getOperationsCounter() {

		//
		reset();

		//
		return operationsCounter;

	}

	/**
	 * 
	 * @return
	 */
	public final int getOperationsLeft() {
		return getOperationsLimit() - getOperationsCounter();
	}

	/**
	 * 
	 * @return
	 */
	public final long getTimeFrameLength() {
		return timeFrameLength;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected boolean validate() {
		return true;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private void reset() {
		if ((System.currentTimeMillis() - timeFrameStart) >= timeFrameLength) {

			//
			timeFrameStart = System.currentTimeMillis();

			//
			operationsCounter = 0;

		}
	}

}
