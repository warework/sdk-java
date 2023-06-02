package com.warework.core.scope;

import java.util.Enumeration;

import com.warework.core.model.Scope;

/**
 * Scopes instances factory and storage. This is the entry point to create,
 * retrieve, search, list and remove Scopes.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface Context {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	ScopeFacade create(final Scope config) throws ScopeException;

	/**
	 * Decides if a Scope exists in the context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return <code>true</code> if the Scope exists or false if not.<br>
	 *         <br>
	 */
	boolean exists(final String name);

	/**
	 * Gets an existing Scope.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A previously created Scope.<br>
	 *         <br>
	 */
	ScopeFacade get(final String name);

	/**
	 * Retrieves the name of every Scope that exists in the context.
	 * 
	 * @return Scopes' names. If no Scope exists in the context this method returns
	 *         <code>null</code>.<br>
	 *         <br>
	 */
	Enumeration<String> list();

	/**
	 * Retrieves the name of every Scope that exists in the context of a specific
	 * Domain/Scope.
	 * 
	 * @param name Name of the Scope where the context exists. This Scope is the
	 *             Domain where to search for the its scopes.<br>
	 *             <br>
	 * @return Scopes' names. If no Scope exists in the context of the Scope, this
	 *         method returns <code>null</code>.<br>
	 *         <br>
	 */
	Enumeration<String> list(final String name);

	/**
	 * Removes an existing Scope in the context.
	 * 
	 * @param name Name of the Scope of remove.<br>
	 *             <br>
	 * @return <code>true</code> if Scope was removed and <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to remove the
	 *                        Scope.<br>
	 *                        <br>
	 */
	boolean remove(final String name) throws ScopeException;

}
