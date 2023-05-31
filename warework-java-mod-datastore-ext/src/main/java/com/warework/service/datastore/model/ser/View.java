package com.warework.service.datastore.model.ser;

import java.io.Serializable;
import java.util.List;

import com.warework.service.datastore.model.Datastore;

/**
 * Bean that holds the information for a View of a Data Store.
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class View extends com.warework.service.datastore.model.View implements
		Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Serial Version.
	private static final long serialVersionUID = 672920616965438886L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the View.
	private String name;

	// Data Store of the View.
	private Datastore datastore;

	// Implementation class of the View.
	private String clazz;

	// Statement loader of the View.
	private String statementProvider;

	// Parameters to configure the View.
	private List<com.warework.core.util.bean.Parameter> parameters;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the View.
	 * 
	 * @return View Name.<br>
	 * <br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the View.
	 * 
	 * @param name
	 *            View name.<br>
	 * <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the Data Store of the View.
	 * 
	 * @return Data store of the View.<br>
	 * <br>
	 */
	public Datastore getDatastore() {
		return datastore;
	}

	/**
	 * Sets the Data Store of the View.
	 * 
	 * @param datastore
	 *            Data store of the View.<br>
	 * <br>
	 */
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	/**
	 * Gets the implementation class of the View.
	 * 
	 * @return Implementation class of the View.<br>
	 * <br>
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * Sets the implementation class of the View.
	 * 
	 * @param clazz
	 *            Implementation class of the View.<br>
	 * <br>
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets the name of the Provider where to load statements.
	 * 
	 * @return Name of a Provider.<br>
	 * <br>
	 */
	public String getStatementProvider() {
		return statementProvider;
	}

	/**
	 * Sets the name of the Provider where to load statements.
	 * 
	 * @param providerName
	 *            Name of a Provider.<br>
	 * <br>
	 */
	public void setStatementProvider(String providerName) {
		this.statementProvider = providerName;
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
