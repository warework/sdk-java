package com.warework.core.model;

import java.util.List;

import com.warework.core.util.bean.Parameter;

/**
 * Bean that holds the information required to create a Provider.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class Provider {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private Scope providerScope;

	// Name of the Provider.
	private String providerName;

	// Implementation class of the Provider.
	private String providerClass;

	// Parameters that configure the Provider.
	private List<Parameter> providerParameters;

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
		return providerScope;
	}

	/**
	 * Sets the Scope where this Provider belongs to.
	 * 
	 * @param scope
	 *            Scope configuration.<br>
	 * <br>
	 */
	public void setScope(Scope scope) {
		providerScope = scope;
	}

	/**
	 * Gets the name of the Provider.
	 * 
	 * @return Name of the Provider.<br>
	 * <br>
	 */
	public String getName() {
		return providerName;
	}

	/**
	 * Sets the name for the Provider.
	 * 
	 * @param name
	 *            Name for the Provider.<br>
	 * <br>
	 */
	public void setName(String name) {
		providerName = name;
	}

	/**
	 * Gets the implementation class of the Provider.
	 * 
	 * @return Implementation class of the Provider.<br>
	 * <br>
	 */
	public String getClazz() {
		return providerClass;
	}

	/**
	 * Sets the implementation class of the Provider.
	 * 
	 * @param clazz
	 *            The implementation class of the Provider.<br>
	 * <br>
	 */
	public void setClazz(String clazz) {
		providerClass = clazz;
	}

	/**
	 * Gets the parameters used to configure the Provider on starup.
	 * 
	 * @return List of parameters.<br>
	 * <br>
	 */
	public List<Parameter> getParameters() {
		return providerParameters;
	}

	/**
	 * Gets the parameters used to configure the Provider on starup.
	 * 
	 * @param parameters
	 *            List of parameters.<br>
	 * <br>
	 */
	public void setParameters(List<Parameter> parameters) {
		providerParameters = parameters;
	}

	/**
	 * Gets the name of this Provider.
	 * 
	 * @return Name of the Provider.<br>
	 * <br>
	 */
	public String toString() {
		return getName();
	}

}
