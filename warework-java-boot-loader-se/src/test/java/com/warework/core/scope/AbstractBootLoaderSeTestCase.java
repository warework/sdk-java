package com.warework.core.scope;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;

import com.warework.core.util.helper.ResourceL1Helper;

import junit.framework.TestCase;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractBootLoaderSeTestCase extends TestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-boot-loader-se";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes every Scope from all contexts.
	 */
	public void tearDown() {
		try {
			ScopeContext.shutdown();
		} catch (final ScopeException e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Scope with the configuration defined in an serialized, XML or
	 * JSON file that exists in the META-INF directory.
	 * 
	 * @param fileName   Name of the XML or serialized file to load at META-INF
	 *                   directory. For example: use <code>system</code> to load the
	 *                   <code>/META-INF/system.ser</code>,
	 *                   <code>/META-INF/system.json</code> or the
	 *                   <code>/META-INF/system.xml</code> file from your project.
	 *                   <b>NOTE</b>: Every resource related with "system" should be
	 *                   allocated under the directory "/META-INF/system/".<br>
	 *                   <br>
	 * @param scopeName  Name of the Scope. Use this name later on with the
	 *                   <code>ScopeContext</code> class to retrieve the Scope
	 *                   created. If you are loading the configuration of the Scope
	 *                   from a serialized file you can skip this argument (just
	 *                   provide <code>null</code>) to use default Scope name stored
	 *                   in the serialized file (otherwise, existing name will be
	 *                   overriden).<br>
	 *                   <br>
	 * @param parentName Name of an Scope that already exists and will be the parent
	 *                   Scope of the Scope to create. Provide <code>null</code> to
	 *                   specify that the Scope to create has no parent Scope. If
	 *                   parent Scope name is defined in configuration file and this
	 *                   argument is not <code>null</code>, it will override the
	 *                   name defined in the configuration file.<br>
	 *                   <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade create(final String fileName, final String scopeName, final String parentName)
			throws ScopeException {
		return ScopeContext.create(AbstractBootLoaderSeTestCase.class,
				PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR + fileName, scopeName, parentName);
	}

	/**
	 * Decides if a Scope exists in the context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return <code>true</code> if the Scope exists or false if not.<br>
	 *         <br>
	 */
	protected boolean exists(final String name) {
		return ScopeContext.exists(name);
	}

	/**
	 * Gets an existing Scope.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A previously created Scope.<br>
	 *         <br>
	 */
	protected ScopeFacade get(String name) {
		return ScopeContext.get(name);
	}

	/**
	 * Retrieves the name of every Scope that exists in the context.
	 * 
	 * @return Scopes' names. If no Scope exists in the context this method returns
	 *         <code>null</code>.<br>
	 *         <br>
	 */
	protected Enumeration<String> list() {
		return ScopeContext.list();
	}

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
	protected Enumeration<String> list(final String name) {
		return ScopeContext.list(name);
	}

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
	protected boolean remove(final String name) throws ScopeException {
		return ScopeContext.remove(name);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object instance in a file.
	 * 
	 * @param object   Object to store in file.<br>
	 *                 <br>
	 * @param fileName Name of the file.<br>
	 *                 <br>
	 * @throws IOException
	 */
	protected final static void serialize(final Object object, final String fileName) throws IOException {

		// Write object with ObjectOutputStream
		final ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName));

		// Write object out to disk
		stream.writeObject(object);

		// Close stream.
		stream.close();

	}

}
