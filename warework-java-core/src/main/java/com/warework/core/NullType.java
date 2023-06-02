package com.warework.core;

import com.warework.core.util.CommonValueL1Constants;

/**
 * Identifies types for <code>null</code> values.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class NullType {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines a generic type for a <code>null</code> value.
	 * Please note that subclasses may ignore this constant.
	 */
	public static final NullType GENERIC_TYPE = new NullType(0);

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Type of the object where to set the null value.
	private int objectType;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private NullType() {
	}

	/**
	 * Defines a <code>null</code> value for a specific data type.
	 * 
	 * @param type
	 *            Specific type identificator.<br>
	 * <br>
	 */
	public NullType(int type) {
		this();
		this.objectType = type;
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the object where to set the <code>null</code> value.
	 * 
	 * @return Identificator for the type of the object.<br>
	 * <br>
	 */
	public int getType() {
		return objectType;
	}

	/**
	 * Get the <code>null</code> string value.
	 * 
	 * @return The string <code>null</code>.<br>
	 * <br>
	 */
	public String toString() {
		return CommonValueL1Constants.STRING_NULL;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param object
	 *            Object to compare.<br>
	 * <br>
	 * 
	 * @return <code>true</code> if objects are the same and <code>false</code>
	 *         if not.<br>
	 * <br>
	 */
	public boolean equals(Object object) {

		// Validate that it is an Null Type object.
		if (object instanceof NullType) {

			// Get the Null Type object.
			NullType nullType = (NullType) object;

			// Validate Null Type value.
			if (objectType == nullType.getType()) {
				return true;
			}

		}

		// At this point, objects are not the same.
		return false;

	}

	/**
	 * Returns a hash code value for the object. This method is supported for
	 * the benefit of hashtables such as those provided by java.util.Hashtable.
	 * 
	 * @return A hash code value for this object.<br>
	 * <br>
	 */
	public int hashCode() {
		return 31 + objectType;
	}

}
