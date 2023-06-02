package com.warework.core.scope;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.model.Scope;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.loader.ScopeJsonLoader;
import com.warework.loader.ScopeXmlLoader;
import com.warework.service.log.LogServiceConstants;

/**
 * Scope instances factory and storage. This is the entry point to create,
 * retrieve, list and remove Scopes.<br>
 * <br>
 * To create a new Scope you have to invoke method <code>create()</code> in this
 * context class with the following arguments:<br>
 * <ul>
 * <li><b>A context class</b>: the first argument must be any class that exists
 * in your project and it is used to load resources from it.</li>
 * <li><b>File name</b>: it represents the name of the serialized, XML or JSON
 * file to load for the Scope from <code>META-INF</code> directory.</li>
 * <li><b>Scope name</b>: it is the name that you will use to retrieve the Scope
 * later on.</li>
 * <li><b>Parent Scope name</b> (OPTIONAL): name of an Scope that already exists
 * and will be used as the parent Scope of the Scope to create.</li>
 * </ul>
 * <br>
 * You can create a new Scope like this:<br>
 * <br>
 * <code>
 * public class <b>Sample1</b> {<br>
 * &nbsp;&nbsp;&nbsp;public static void main(String[] args) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;try {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ScopeFacade scope = ScopeContext.create(<b>Sample1.class</b>, "scope-config", "system");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * }<br>
 * </code> <br>
 * The previous sample first tries to load from
 * <code>/META-INF/scope-config.ser</code> a serialized configuration of the
 * Scope (Java object stored as binary files). If this file does not exist then
 * it tries to load the XML version of the configuration file (
 * <code>/META-INF/scope-config.xml</code>) and finally it tries with the JSON
 * version ( <code>/META-INF/scope-config.json</code>). When any of those
 * configuration files is found, a new Scope named "<code>system</code>" is
 * created in te context.<br>
 * <br>
 * You can get an instance of a previously created Scope with method
 * <code>get()</code>:<br>
 * <br>
 * <code>
 * public class Sample1 {<br>
 * &nbsp;&nbsp;&nbsp;public static void main(String[] args) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;try {<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// Create the Scope.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ScopeContext.create(Sample1.class, "scope-config", "system");<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// Get the Scope.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ScopeFacade system = ScopeContext.get("system");<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * }<br>
 * </code> <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ScopeContext {

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default implementation for the context of the Scopes.<br>
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	private static abstract class AbstractContext implements Context {

		// ///////////////////////////////////////////////////////////////
		// CONSTANTS
		// ///////////////////////////////////////////////////////////////

		// Global storage for Scopes to control ny name unique Scope instances. Multiple
		// Scopes with the same name are not allowed.
		private static final Map<String, ScopeFacade> GLOBAL_CONTEXT_BY_NAME = new HashMap<String, ScopeFacade>();

		// Global storage for Scopes to control ny sequence unique Scope instances.
		// Multiple Scopes with the same name are not allowed.
		private static final List<ScopeFacade> GLOBAL_CONTEXT_BY_SEQUENCE = new ArrayList<ScopeFacade>();

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Domain Scope for every Scope created in this context.
		private ScopeFacade domain;

		// Storage for scopes.
		private Map<String, AbstractScope> context = new HashMap<String, AbstractScope>();

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates the context.
		 * 
		 * @param domain Domain Scope for every Scope created in this context.<br>
		 *               <br>
		 */
		private AbstractContext(final ScopeFacade domain) {
			this.domain = domain;
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates a Scope for this Domain/Scope.
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
		public ScopeFacade create(final Scope config) throws ScopeException {

			// Validate the name of the Scope.
			if ((config.getName() == null) || (config.getName().equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ScopeException(domain,
						"WAREWORK cannot create a new instance of the Scope because Scope's name is null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Updated configuration of the Scope.
			Scope updatedConfig = null;

			// New instance of the Scope.
			AbstractScope scope = null;

			// Create Scope and store in context.
			synchronized (GLOBAL_CONTEXT_BY_NAME) {
				if (GLOBAL_CONTEXT_BY_NAME.containsKey(config.getName())) {
					throw new ScopeException(domain,
							"WAREWORK cannot create a new instance of the Scope because Scope '" + config.getName()
									+ "' already exists.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				} else {

					// Get the full configuration for the Scope.
					updatedConfig = updateConfig(config);

					// Create Scope factory event handler instance.
					ScopeFactoryEventHandler handler = null;
					try {

						// Get the Scope factory event handler class when defined.
						final Class<? extends ScopeFactoryEventHandler> handlerType = getParameterType(updatedConfig,
								ScopeL1Constants.PARAMETER_FACTORY_EVENT_CLASS, ScopeFactoryEventHandler.class);

						// Create handler instance.
						if (handlerType != null) {
							handler = handlerType.newInstance();
						}

					} catch (final Exception e) {
						throw new ScopeException(domain,
								"WAREWORK cannot create a new instance of the Scope because given Scope factory event handler class cannot be instantiated.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Handle config loaded event.
					if (handler != null) {
						handler.configLoaded(updatedConfig);
					}

					// Get parent Scope if it exists.
					ScopeFacade parent = null;
					if (updatedConfig.getParent() != null) {
						if (GLOBAL_CONTEXT_BY_NAME.containsKey(updatedConfig.getParent())) {
							parent = GLOBAL_CONTEXT_BY_NAME.get(updatedConfig.getParent());
						} else {
							throw new ScopeException(domain,
									"WAREWORK cannot create a new instance of the Scope because specified parent '"
											+ updatedConfig.getParent() + "' does not exists.",
									null, LogServiceConstants.LOG_LEVEL_WARN);
						}
					}

					// Create an instance of the Scope.
					scope = ScopeContext.create(updatedConfig, parent, domain);

					// Handle Scope created event.
					if (handler != null) {
						handler.scopeCreated(updatedConfig, scope);
					}

					// Add Scope in this context.
					synchronized (context) {
						context.put(scope.getName(), scope);
					}

					// Add Scope in global context by name.
					GLOBAL_CONTEXT_BY_NAME.put(scope.getName(), scope);

					// Add Scope in global context by sequence.
					GLOBAL_CONTEXT_BY_SEQUENCE.add(scope);

				}

			}

			// Create context Scopes. Must be created only after main Scope exists.
			setupContext(scope, updatedConfig);

			// Set the Scope.
			return scope;

		}

		/**
		 * Decides if a Scope exists in the context.
		 * 
		 * @param name Name of the Scope.<br>
		 *             <br>
		 * @return <code>true</code> if the Scope exists or false if not.<br>
		 *         <br>
		 */
		public boolean exists(final String name) {
			synchronized (context) {
				return context.containsKey(name);
			}
		}

		/**
		 * Gets an existing Scope.
		 * 
		 * @param name Name of the Scope.<br>
		 *             <br>
		 * @return A previously created Scope.<br>
		 *         <br>
		 */
		public ScopeFacade get(final String name) {

			// Validate that Scope exists.
			synchronized (context) {
				if (context.containsKey(name)) {

					// Get the Scope.
					final ScopeFacade scope = context.get(name);

					// When Scope's name is null then Scope is over.
					if ((scope != null) && (scope.active())) {
						return scope;
					}

				}
			}

			// At this point, nothing to return.
			return null;

		}

		/**
		 * Retrieves the name of every Scope that exists in the context.
		 * 
		 * @return Scopes' names. If no Scope exists in the context this method returns
		 *         <code>null</code>.<br>
		 *         <br>
		 */
		public Enumeration<String> list() {

			// Validate context and return scope names.
			synchronized (context) {
				if (context.size() > 0) {
					return DataStructureL1Helper.toEnumeration(context.keySet());
				}
			}

			// At this point, nothing to return.
			return null;

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
		public Enumeration<String> list(final String name) {

			// Get the Scope.
			final ScopeFacade scope = get(name);

			// When Scope's name is null then Scope is over.
			if ((scope != null) && (scope.getContext() != null)) {
				return scope.getContext().list();
			}

			// At this point, nothing to return.
			return null;

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
		public boolean remove(final String name) throws ScopeException {
			synchronized (GLOBAL_CONTEXT_BY_NAME) {
				return shutdown(name);
			}
		}

		// ///////////////////////////////////////////////////////////////
		// PRIVATE METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the full configuration for the Scope. One Scope 'A' can request its
		 * configuration from Scope 'B', 'B' from 'C' and so on. It loads recursively
		 * all the configurations to create a single configuration object.
		 * 
		 * @param config1 Root configuration object. The information that exists in this
		 *                configuration will override the information loaded in other
		 *                configurations.<br>
		 *                <br>
		 * @return Scope configuration.<br>
		 *         <br>
		 * @throws ScopeException If there is an error when trying to update the
		 *                        configuration of the Scope.<br>
		 *                        <br>
		 */
		private Scope updateConfig(final Scope config1) throws ScopeException {

			// Get the loader type of the configuration.
			Class<? extends LoaderFacade> loaderType = null;
			try {
				loaderType = getParameterType(config1, ScopeL1Constants.PARAMETER_CONFIG_CLASS, LoaderFacade.class);
			} catch (final ClassNotFoundException e) {
				throw new ScopeException(domain,
						"WAREWORK cannot create a new instance of the Scope because the loader type for a configuration of a Scope is not valid. Check out this error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate if Scope's configuration references to another Scope's
			// configuration.
			if (loaderType != null) {

				// Load Scope's configuration.
				Scope config2 = null;
				try {
					config2 = (Scope) AbstractLoader.load(null, loaderType, config1.getInitParameters());
				} catch (final Exception e) {
					throw new ScopeException(domain,
							"WAREWORK cannot create a new instance of the Scope because the following exception is thrown when trying to load the configuration of the Scope: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Context loader propagation.
				if ((config2 != null) && (config2.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) == null)
						&& (config1.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) != null)) {
					config2.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER,
							config1.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER));
				}

				// Check if new configuration references to another configurations.
				config2 = updateConfig(config2);

				// Override the configuration.
				config2.update(config1);

				// Return the updated configuration.
				return config2;

			}

			// Return the configuration as it is.
			return config1;

		}

		/**
		 * Gets the class than implements an interface defined by an initialization
		 * parameter.
		 * 
		 * @param <T>           Interface defined by the initialization parameter class.
		 * @param config        Scope's configuration.<br>
		 *                      <br>
		 * @param parameterName Name of the initialization parameter that holds the
		 *                      specific class type that implements the interface.<br>
		 *                      <br>
		 * @param type          Interface implemented by the class defined in the
		 *                      initialization parameter.<br>
		 *                      <br>
		 * @return Class that extends the interface defined by the initialization
		 *         parameter.<br>
		 *         <br>
		 * @throws ClassNotFoundException
		 */
		@SuppressWarnings("unchecked")
		private <T> Class<? extends T> getParameterType(final Scope config, final String parameterName,
				final Class<T> type) throws ClassNotFoundException {

			// Get the type of the loader.
			final Object parameterValue = config.getInitParameter(parameterName);

			// Get the class of the loader.
			Class<? extends T> parameterType = null;
			if (parameterValue != null) {
				if (parameterValue instanceof String) {
					parameterType = (Class<? extends T>) Class.forName((String) parameterValue);
				} else if (parameterValue instanceof Class) {
					parameterType = (Class<? extends T>) parameterValue;
				}
			}

			// Return the type of the loader.
			return parameterType;

		}

		/**
		 * Initializes context Scopes.
		 * 
		 * @param config Scope's configuration.<br>
		 *               <br>
		 * @throws ScopeException If there is an error when trying to initialize the
		 *                        Context Scopes.<br>
		 *                        <br>
		 */
		private void setupContext(final ScopeFacade scope, final Scope config) throws ScopeException {

			// Get context Scope names.
			final Enumeration<String> contextScopeNames = config.getContextScopeNames();

			// Validate Scopes exists.
			if (contextScopeNames != null) {

				// Get context.
				final Context context = scope.getContext();

				// Validate context exists.
				if (context != null) {
					while (contextScopeNames.hasMoreElements()) {

						// Get the configuration for the context Scope.
						final Scope contextScopeConfig = config.getContextScope(contextScopeNames.nextElement());

						// Context loader propagation.
						if ((contextScopeConfig.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) == null)
								&& (config.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER) != null)) {
							contextScopeConfig.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER,
									config.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER));
						}

						// Create the Scope in the context.
						context.create(contextScopeConfig);

					}
				}

			}

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
		private boolean shutdown(final String name) throws ScopeException {

			// Validate Scope exists in global context.
			if (GLOBAL_CONTEXT_BY_NAME.containsKey(name)) {
				synchronized (context) {
					if (context.containsKey(name)) {

						// Get the Scope.
						final AbstractScope scope = context.get(name);

						// Validate Scope does not have Scopes in its context. Keep in mind that those
						// Scopes must be deleted first because if you delete the domain Scope you will
						// not be able to get the instances of the Scopes of its context later on.
						if ((scope.getContext() != null) && (scope.getContext().list() != null)) {
							throw new ScopeException(scope, "WAREWORK cannot remove Scope '" + name
									+ "' from context because it contains Scopes in its context that should be deleted first.",
									null, LogServiceConstants.LOG_LEVEL_WARN);
						}

						// Validate Scope has no children (they all must be deleted first).
						for (final Iterator<ScopeFacade> iterator = GLOBAL_CONTEXT_BY_SEQUENCE.iterator(); iterator
								.hasNext();) {

							// Get parent from available Scope.
							final ScopeFacade child = iterator.next();

							// Check that available Scope is not child.
							if ((child.getParent() != null) && (child.getParent().getName().equals(name))) {
								throw new ScopeException(scope,
										"WAREWORK cannot remove Scope '" + name
												+ "' from context because it has active child '" + child
												+ "' that should be deleted first.",
										null, LogServiceConstants.LOG_LEVEL_WARN);
							}

						}

						// Shutdown Scope if it's running.
						scope.close();

						// Remove Scope from the context.
						context.remove(name);

						// Remove by name Scope from the global context.
						GLOBAL_CONTEXT_BY_NAME.remove(name);

						// Remove by index Scope from the global context.
						GLOBAL_CONTEXT_BY_SEQUENCE.remove(scope);

						// Scope was removed.
						return true;

					}
				}
			}

			// At this point, Scope could not be removed.
			return false;

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Root context.
	private static final AbstractContext CONTEXT = new AbstractContext(null) {

	};

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private ScopeContext() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Scope with the configuration defined in an serialized, XML or
	 * JSON file that exists in the META-INF directory.
	 * 
	 * @param contextClass Specifies the class that should be used to load
	 *                     resources. It should be a class that exists in your
	 *                     project, so the Framework can read resources from it.<br>
	 *                     <br>
	 * @param fileName     Name of the XML or serialized file to load at META-INF
	 *                     directory. For example: use <code>system</code> to load
	 *                     the <code>/META-INF/system.ser</code>,
	 *                     <code>/META-INF/system.json</code> or the
	 *                     <code>/META-INF/system.xml</code> file from your project.
	 *                     <b>NOTE</b>: Every resource related with "system" should
	 *                     be allocated under the directory "/META-INF/system/".<br>
	 *                     <br>
	 * @param scopeName    Name of the Scope. Use this name later on with the
	 *                     <code>ScopeContext</code> class to retrieve the Scope
	 *                     created. If you are loading the configuration of the
	 *                     Scope from a serialized file you can skip this argument
	 *                     (just provide <code>null</code>) to use default Scope
	 *                     name stored in the serialized file (otherwise, existing
	 *                     name will be overriden).<br>
	 *                     <br>
	 * @param parentName   Name of an Scope that already exists and will be the
	 *                     parent Scope of the Scope to create. Provide
	 *                     <code>null</code> to specify that the Scope to create has
	 *                     no parent Scope. If parent Scope name is defined in
	 *                     configuration file and this argument is not
	 *                     <code>null</code>, it will override the name defined in
	 *                     the configuration file.<br>
	 *                     <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	public static ScopeFacade create(final Class<?> contextClass, final String fileName, final String scopeName,
			final String parentName) throws ScopeException {

		// Try to find the serialized file with the configuration of the Scope.
		URL targetURL = contextClass.getResource(ResourceL1Helper.DIRECTORY_SEPARATOR
				+ ResourceL2Helper.DIRECTORY_META_INF + ResourceL1Helper.DIRECTORY_SEPARATOR + fileName
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);
		if (targetURL != null) {
			try {

				// Open a stream for the serialized file.
				final InputStream stream = targetURL.openStream();

				// Load configuration of the Scope.
				final Scope config = (Scope) new ObjectInputStream(stream).readObject();

				// close the configuration stream.
				stream.close();

				// Set the name for the Scope.
				config.setName(scopeName);

				// Set the name of the parent Scope.
				if (parentName != null) {
					config.setParent(parentName);
				}

				// Set application context class.
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, contextClass);

				// Create and return a new instance of a Scope.
				return CONTEXT.create(config);

			} catch (final Exception e) {
				throw new ScopeException(null, "WAREWORK cannot create new Scope from serialized configuration '"
						+ targetURL + "' because the following problem was found: " + e.getMessage(), null, -1);
			}
		}

		// Try to find the XML file with the configuration of the Scope.
		targetURL = contextClass.getResource(ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
				+ ResourceL1Helper.DIRECTORY_SEPARATOR + fileName + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
				+ ResourceL2Helper.FILE_EXTENSION_XML);
		if (targetURL != null) {

			// Create new empty Scope configuration.
			final Scope config = new Scope(scopeName);

			// Set the name of the parent Scope.
			if (parentName != null) {
				config.setParent(parentName);
			}

			// Set application context class.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, contextClass);

			// Set the configuration loader.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ScopeXmlLoader.class);

			// Setup the configuration file.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET, targetURL);

			// Create and return a new instance of a Scope.
			return CONTEXT.create(config);

		}

		// Try to find the JSON file with the configuration of the Scope.
		targetURL = contextClass.getResource(ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
				+ ResourceL1Helper.DIRECTORY_SEPARATOR + fileName + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
				+ ResourceL2Helper.FILE_EXTENSION_JSON);
		if (targetURL != null) {

			// Create new empty Scope configuration.
			final Scope config = new Scope(scopeName);

			// Set the name of the parent Scope.
			if (parentName != null) {
				config.setParent(parentName);
			}

			// Set application context class.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, contextClass);

			// Set the configuration loader.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ScopeJsonLoader.class);

			// Setup the configuration file.
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET, targetURL);

			// Create and return a new instance of a Scope.
			return CONTEXT.create(config);

		}

		// At this point no available configurationn file was found.
		throw new ScopeException(null,
				"WAREWORK cannot create new Scope because no serialized, XML or JSON configuration file was found.",
				null, -1);

	}

	/**
	 * Decides if a Scope exists in the context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return <code>true</code> if the Scope exists or false if not.<br>
	 *         <br>
	 */
	public static boolean exists(final String name) {
		return CONTEXT.exists(name);
	}

	/**
	 * Gets an existing Scope.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A previously created Scope.<br>
	 *         <br>
	 */
	public static ScopeFacade get(String name) {
		return CONTEXT.get(name);
	}

	/**
	 * Retrieves the name of every Scope that exists in the context.
	 * 
	 * @return Scopes' names. If no Scope exists in the context this method returns
	 *         <code>null</code>.<br>
	 *         <br>
	 */
	public static Enumeration<String> list() {
		return CONTEXT.list();
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
	public static Enumeration<String> list(final String name) {
		return CONTEXT.list(name);
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
	public static boolean remove(final String name) throws ScopeException {
		return CONTEXT.remove(name);
	}

	/**
	 * Removes all Scopes from every context.
	 * 
	 * @throws ScopeException If there is an error when trying to remove the
	 *                        Scope.<br>
	 *                        <br>
	 */
	public static void shutdown() throws ScopeException {
		synchronized (AbstractContext.GLOBAL_CONTEXT_BY_NAME) {
			while (AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE.size() > 0) {

				// Get last created Scope.
				final ScopeFacade scope = AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE
						.get(AbstractContext.GLOBAL_CONTEXT_BY_SEQUENCE.size() - 1);

				// Remove Scope from context.
				if (scope.getDomain() == null) {
					CONTEXT.shutdown(scope.getName());
				} else {

					// Get context that created the Scope.
					final AbstractContext context = (AbstractContext) scope.getDomain().getContext();

					// Remove Scope from context.
					context.shutdown(scope.getName());

				}

			}
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Create a new instance of the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @param parent Parent Scope for this Scope.<br>
	 *               <br>
	 * @param domain Domain Scope for this Scope.<br>
	 *               <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	private static AbstractScope create(final Scope config, final ScopeFacade parent, final ScopeFacade domain)
			throws ScopeException {
		return new AbstractScope(config, parent, domain) {

			// ///////////////////////////////////////////////////////////
			// ATTRIBUTES
			// ///////////////////////////////////////////////////////////

			// Context for Scopes.
			private Context context;

			// ///////////////////////////////////////////////////////////
			// PUBLIC METHODS
			// ///////////////////////////////////////////////////////////

			/**
			 * Gets the context for the scopes that belong to the Domain specified by this
			 * Scope.
			 * 
			 * @return Scope's context.<br>
			 *         <br>
			 */
			public Context getContext() {

				// Validate.
				if (active()) {

					// Create context first time it's requested.
					if (context == null) {
						context = new AbstractContext(this) {

						};
					}

					// Return context.
					return context;

				}

				// Nothing to return at this point.
				return null;

			}

			// ///////////////////////////////////////////////////////////
			// PROTECTED METHODS
			// ///////////////////////////////////////////////////////////

			/**
			 * This method does not perform any operation.
			 * 
			 * @param config Configuration for the Scope.<br>
			 *               <br>
			 */
			protected void initialize(final Scope config) {
				// DO NOTHING.
			}

			/**
			 * This method does not perform any operation.
			 */
			protected void shutdown() {
				// DO NOTHING.
			}

		};
	}

}
