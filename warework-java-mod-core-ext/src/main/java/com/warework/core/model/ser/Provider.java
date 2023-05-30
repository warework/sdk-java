package com.warework.core.model.ser;

import java.io.Serializable;
import java.util.List;

/**
 * Bean that holds the information required to create a Provider.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class Provider extends com.warework.core.model.Provider implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = -1674568191258729023L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Initialization configuration.
	private com.warework.core.model.Scope scope;

	// Name of the Provider.
	private String name;

	// Implementation class of the Provider.
	private String clazz;

	// Parameters that configure the Provider.
	private List<com.warework.core.util.bean.Parameter> parameters;

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
	 * Gets the name of the Provider.
	 * 
	 * @return Name of the Provider.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the Provider.
	 * 
	 * @param name
	 *            Name for the Provider.<br>
	 * <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the implementation class of the Provider.
	 * 
	 * @return Implementation class of the Provider.<br>
	 * <br>
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * Sets the implementation class of the Provider.
	 * 
	 * @param clazz
	 *            The implementation class of the Provider.<br>
	 * <br>
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets the parameters used to configure the Provider on starup.
	 * 
	 * @return List of parameters.<br>
	 * <br>
	 */
	public List<com.warework.core.util.bean.Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Gets the parameters used to configure the Provider on starup.
	 * 
	 * @param parameters
	 *            List of parameters.<br>
	 * <br>
	 */
	public void setParameters(
			List<com.warework.core.util.bean.Parameter> parameters) {
		this.parameters = parameters;
	}

}
