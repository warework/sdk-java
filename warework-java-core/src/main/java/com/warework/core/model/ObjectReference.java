package com.warework.core.model;

/**
 * Bean that holds the information required to create a reference to an object
 * that exists in a Provider.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class ObjectReference {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private Scope referenceScope;

	// Name of the object.
	private String referenceName;

	// Name of the Provider where to retrieve the object.
	private String referenceProvider;

	// Name of the object in the Provider.
	private String referenceObject;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Provider belongs to.
	 * 
	 * @return Scope configuration.<br>
	 * <br>
	 */
	public Scope getScope() {
		return referenceScope;
	}

	/**
	 * Sets the Scope where this Provider belongs to.
	 * 
	 * @param scope
	 *            Scope configuration.<br>
	 * <br>
	 */
	public void setScope(Scope scope) {
		referenceScope = scope;
	}

	/**
	 * Gets the name of the Object Reference.
	 * 
	 * @return Name of the Object Reference.<br>
	 * <br>
	 */
	public String getName() {
		return referenceName;
	}

	/**
	 * Sets the name for this Object Reference.
	 * 
	 * @param name
	 *            Name for the Object Reference.<br>
	 * <br>
	 */
	public void setName(String name) {
		referenceName = name;
	}

	/**
	 * Gets the name of the Provider where to retrieve the object.
	 * 
	 * @return Name of the Provider.<br>
	 * <br>
	 */
	public String getProvider() {
		return referenceProvider;
	}

	/**
	 * Sets the name of the Provider where to retrieve the object.
	 * 
	 * @param provider
	 *            Name of the Provider.<br>
	 * <br>
	 */
	public void setProvider(String provider) {
		referenceProvider = provider;
	}

	/**
	 * Gets the name of the object in the Provider.
	 * 
	 * @return Name of the object.<br>
	 * <br>
	 */
	public String getObject() {
		return referenceObject;
	}

	/**
	 * Sets the name of the object in the Provider.
	 * 
	 * @param value
	 *            Name of the object.<br>
	 * <br>
	 */
	public void setObject(String value) {
		referenceObject = value;
	}

	/**
	 * Gets the name of the Object Reference.
	 * 
	 * @return Name of the Object Reference.<br>
	 * <br>
	 */
	public String toString() {
		return getName();
	}

}
