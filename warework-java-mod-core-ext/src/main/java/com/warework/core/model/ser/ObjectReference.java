package com.warework.core.model.ser;

import java.io.Serializable;

/**
 * Bean that holds the information required to create a reference to an object
 * that exists in a Provider.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ObjectReference extends
		com.warework.core.model.ObjectReference implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 9188532682910663834L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private com.warework.core.model.Scope scope;

	// Name of the object.
	private String name;

	// Name of the Provider where to retrieve the object.
	private String provider;

	// Name of the object in the Provider.
	private String object;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this Provider belongs to.
	 * 
	 * @return Scope configuration.<br>
	 * <br>
	 */
	public com.warework.core.model.Scope getScope() {
		return scope;
	}

	/**
	 * Sets the Scope where this Provider belongs to.
	 * 
	 * @param scope
	 *            Scope configuration.<br>
	 * <br>
	 */
	public void setScope(com.warework.core.model.Scope scope) {
		this.scope = scope;
	}

	/**
	 * Gets the name of the Object Reference.
	 * 
	 * @return Name of the Object Reference.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for this Object Reference.
	 * 
	 * @param name
	 *            Name for the Object Reference.<br>
	 * <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the Provider where to retrieve the object.
	 * 
	 * @return Name of the Provider.<br>
	 * <br>
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * Sets the name of the Provider where to retrieve the object.
	 * 
	 * @param provider
	 *            Name of the Provider.<br>
	 * <br>
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * Gets the name of the object in the Provider.
	 * 
	 * @return Name of the object.<br>
	 * <br>
	 */
	public String getObject() {
		return object;
	}

	/**
	 * Sets the name of the object in the Provider.
	 * 
	 * @param value
	 *            Name of the object.<br>
	 * <br>
	 */
	public void setObject(String value) {
		this.object = value;
	}

}
