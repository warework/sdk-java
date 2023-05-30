package com.warework.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provider that retrieves objects instances from a JNDI context. <br>
 * <br>
 * The JNDI Provider is responsible for locating objects from naming services. A
 * naming service maintains a set of bindings, which relate names to objects and
 * provide the ability to look up objects by name.<br>
 * <br>
 * JNDI allows the components in distributed applications to locate each other.
 * For instance: a client can retrieve from an application server a Data Source
 * object via JNDI to connect with a database. Warework JNDI Provider wraps the
 * JNDI API to provide these features and integrate them into the Framework.<br>
 * <br>
 * <b>Configure and create a JNDI Provider</b><br>
 * <br>
 * The configuration of this Provider is optional. Typically, in Server
 * platforms you will not configure JNDI to locate data sources, EJBs, JMS, Mail
 * Sessions, and so on in the network (by default, the JNDI context is connected
 * to the local naming service). So, to start working with this Provider is
 * fairly simple:<br>
 * <br>
 * <code>
 * // Create the Provider.<br>  
 * scope.createProvider("jndi-provider", JndiProvider.class, null);<br>  
 * </code> <br>
 * If you are not working in a Server platform or, for example, you have to
 * connect with a remote naming service, you can then specify some
 * initialization parameters for the configuration of the JNDI Provider. The
 * following example shows how to connect with a remote LDAP server:<br>
 * <br>
 * <code>
 * // Create the configuration of the provider. <br> 
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br> 
 * <br> 
 * // Configure the Provider.<br>  
 * parameters.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");<br>  
 * parameters.put(Context.PROVIDER_URL, "ldap://ldap.test.com:389"); <br> 
 * parameters.put(Context.SECURITY_PRINCIPAL, "james wood"); <br> 
 * parameters.put(Context.SECURITY_CREDENTIALS, "password"); <br> 
 * <br> 
 * // Create the Provider.<br>  
 * scope.createProvider("jndi-provider", JndiProvider.class, parameters);<br>  
 * </code> <br>
 * <b>Retrieve objects from a JNDI Provider</b><br>
 * <br>
 * At this point the JNDI Provider is running and we can request objects from
 * it. To do so, we just need to provide the name of an existing object in the
 * naming service. For example, suppose that your program is running on an
 * application server and that you want to retrieve a Data Source from it, you
 * would write code that looks as follows:<br>
 * <br>
 * <code>
 * // Get a Data Source to connect with a database.<br>  
 * DataSource ds = (DataSource) scope.getObject("jndi-provider", "jdbc/MyApp");<br> 
 * </code> <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JndiProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "jndi" + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Configuration of the context.
	private Hashtable<String, String> environment;

	// Name-to-object bindings.
	private Context context;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		environment = null;
		context = null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 */
	protected void initialize() {

		// Get the names of the initialization parameters.
		final Enumeration<String> parameterNames = getInitParameterNames();

		// Create the environment if at least one parameter exist.
		if (parameterNames != null) {

			// Define an environment for the context.
			environment = new Hashtable<String, String>();

			// Copy each initialization parameter into the environment.
			while (parameterNames.hasMoreElements()) {

				// Get the name of a parameter.
				final String parameterName = parameterNames.nextElement();

				// Configure the environment.
				if ((!parameterName.equals(PARAMETER_CREATE_OBJECTS))
						&& (!parameterName.equals(PARAMETER_DISCONNECT_ON_LOOKUP))) {

					// Get the value of the parameter.
					final Object parameterValue = getInitParameter(parameterName);

					// Register the environment parameter.
					if (parameterValue instanceof String) {
						environment.put(parameterName, (String) parameterValue);
					}

				}

			}

			// Remove the environment if no parameters exist.
			if (environment.size() < 1) {
				environment = null;
			}

		}

	}

	/**
	 * Creates the initial context.
	 * 
	 * @throws ProviderException If there is an error when trying to connect the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void connect() throws ProviderException {
		try {
			if (environment == null) {
				context = new InitialContext();
			} else {
				context = new InitialContext(environment);
			}
		} catch (final NamingException e) {
			throw new ProviderException(getScopeFacade(), "WAREWORK cannot connect Provider '" + getName()
					+ "' because the making of the initial context generated the following error: " + e.getMessage(), e,
					LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to disconnect the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void disconnect() throws ProviderException {
		try {

			// Close the context connection.
			context.close();

			// Remove the context.
			context = null;

		} catch (final NamingException e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot disconnect Provider '" + getName()
							+ "' because the initial context reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Validates if the connection of the context is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return (context == null);
	}

	/**
	 * Retrieves an object.
	 * 
	 * @param name Name of the object to retrieve.<br>
	 *             <br>
	 * @return An object from the JNDI context.<br>
	 *         <br>
	 */
	protected Object getObject(final String name) {

		// Retrieve the object only if the initial context is initialized.
		if (context != null) {
			try {
				return context.lookup(name);
			} catch (final NamingException e) {
				getScopeFacade().log(
						"WAREWORK cannot retrieve object '" + name + "' in Provider '" + getName()
								+ "' because the initial context reported the following error: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names of the objects bound in the context.
	 * 
	 * @return Names of the objects of the context.<br>
	 *         <br>
	 * @throws ProviderException If there is an error when trying to retrieve the
	 *                           name of the objects.<br>
	 *                           <br>
	 */
	protected Enumeration<String> getObjectNames() throws ProviderException {

		// Temporal storage for each name found.
		final List<String> names = new ArrayList<String>();

		// Retrieve each name.
		try {

			// Get the list with the names.
			final NamingEnumeration<NameClassPair> enumeration = context.list(getProviderURL());

			// Retrieve each name.
			while (enumeration.hasMore()) {
				names.add(enumeration.next().getName());
			}

		} catch (final Exception e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot retrieve the names of the objects that exist in Provider '" + getName()
							+ "' because the names list generates the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the names found.
		return (names.size() > 0) ? Collections.enumeration(names) : null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the context's Provider URL.
	 * 
	 * @return Provider URL.<br>
	 *         <br>
	 * @throws NamingException If there is an error when trying to retrieve the
	 *                         Provider URL.<br>
	 *                         <br>
	 */
	private String getProviderURL() throws NamingException {

		// Get the context's environment.
		final Hashtable<?, ?> config = context.getEnvironment();

		// Return the Provider URL.
		return (String) config.get(Context.PROVIDER_URL);

	}

}
