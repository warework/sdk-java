package com.warework.core.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Helper to load resources from a given directory.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class DirectoryResources extends AbstractResourceManager {

	// ///////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////

	// RESOURCE LOCATIONS

	/**
	 * Flag to specify that resource is loaded with a context class provided by the
	 * user.
	 */
	private static final short LOADER_TYPE_CONTEXT_CLASS = 0x0;

	/**
	 * Flag to specify that resource is loaded with the Android
	 * <code>android.content.Context</code> class.
	 */
	private static final short LOADER_TYPE_ANDROID = 0x1;

	/**
	 * Flag to specify that resource is loaded with the Servlet
	 * <code>javax.servlet.ServletContext</code> class.
	 */
	private static final short LOADER_TYPE_SERVLET = 0x2;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Object that will be used to load resources.
	private Object contextLoader;

	// Extension for the files to load.
	private String fileExtension;

	// Resources found.
	private Map<String, String> resources;

	// Resource location.
	private short loaderType;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the helper to handle resources in a directory.
	 * 
	 * @param scope         Scope where this helper belongs to.<br>
	 *                      <br>
	 * @param fileExtension Extension for every file to load.<br>
	 *                      <br>
	 * @param configTarget  Directory where to load the resources.<br>
	 *                      <br>
	 * @param contextType   Object used to load resources. It can be a Class, a
	 *                      string that represents a class, a string that specifies
	 *                      the name of a Provider that holds an object named
	 *                      'context-loader' or just a regular object. When a class
	 *                      is provided then this class will be used to load the
	 *                      resources. When a <br>
	 *                      <br>
	 * @throws LoaderException If there is an error when trying to find the
	 *                         resources.<br>
	 *                         <br>
	 */
	public DirectoryResources(final ScopeFacade scope, final String fileExtension, final String configTarget,
			final Object contextType) throws LoaderException {

		// Initialize parent.
		super(scope);

		// Set the extension of the file.
		this.fileExtension = fileExtension;

		// Validate config target.
		if ((configTarget == null) || (configTarget.equals(CommonValueL2Constants.STRING_EMPTY))) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resources from target path because it's null or empty.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the object specified for the Loader that will be used to load resources.
		if (contextType == null) {
			this.contextLoader = scope.getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER);
		} else {
			this.contextLoader = contextType;
		}

		// List resources.
		resources = listResources(configTarget);

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a resource.
	 * 
	 * @param name Name of the file/resource to read, without the file
	 *             extension.<br>
	 *             <br>
	 * @return Stream that represents the resource loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to get the
	 *                         resource.<br>
	 *                         <br>
	 */
	public InputStream getResource(final String name) throws LoaderException {

		// Read resource content.
		if ((resources != null) && (resources.containsKey(name))) {

			// Path to the resource.
			final String targetPath = resources.get(name);

			// Load resource locally.
			if (contextLoader != null) {
				if ((loaderType == LOADER_TYPE_CONTEXT_CLASS) || (loaderType == LOADER_TYPE_SERVLET)) {
					try {
						return new URL(targetPath).openStream();
					} catch (final Exception e) {
						throw new LoaderException(
								getScopeFacade(), "WAREWORK cannot load resource '" + targetPath
										+ "' due to the following problem: " + e.getMessage(),
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else {
					try {
						return (InputStream) ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_OPEN,
								ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_GET_ASSETS,
										contextLoader, new Class[] {}, new Object[] {}),
								new Class[] { String.class }, new Object[] { targetPath });
					} catch (final Exception e) {
						throw new LoaderException(getScopeFacade(),
								"WAREWORK cannot load Android resource '" + targetPath
										+ "' due to the following problem: " + e.getMessage(),
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names of every file that exists in the base directory and matches
	 * the given file extension.
	 * 
	 * @return Names of the files in the base directory or <code>null</code> if no
	 *         one exists.<br>
	 *         <br>
	 */
	public Enumeration<String> getResourceNames() {
		return (resources == null) ? null : Collections.enumeration(resources.keySet());
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * List the resources with the context loader specified.
	 * 
	 * @param configTarget Directory where to load the resources.<br>
	 *                     <br>
	 * @return Map with the names and locations of the resources.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to list the
	 *                         resources.<br>
	 *                         <br>
	 */
	private Map<String, String> listResources(final String configTarget) throws LoaderException {

		// Validate that a context loader exists.
		if (this.contextLoader != null) {

			// Get the list of files from a context class.
			if (this.contextLoader instanceof Class) {

				// Set the location of the resource.
				loaderType = LOADER_TYPE_CONTEXT_CLASS;

				// Return resources found.
				return listClassResources((Class<?>) this.contextLoader, configTarget);

			}

			// Get the list of files from a context class that is specified as a string
			// object.
			Class<?> contextClass = null;
			if (this.contextLoader instanceof String) {

				// Get the class for the context.
				try {
					contextClass = Class.forName((String) this.contextLoader);
				} catch (final Exception e) {
					// DO NOTHING.
				}

				// Return resources found from context class or with a new context loader.
				if (contextClass == null) {

					// Get the context loader from a Provider.
					this.contextLoader = getScopeFacade().getObject((String) this.contextLoader,
							ScopeL1Constants.PARAMETER_CONTEXT_LOADER);

					// Return resources found with new context loader.
					return listResources(configTarget);

				} else {

					// Set the location of the resource.
					loaderType = LOADER_TYPE_CONTEXT_CLASS;

					// Return resources found.
					return listClassResources(contextClass, configTarget);

				}

			}

			// Get the Android context class.
			try {
				contextClass = Class.forName(ScopeL1Constants.CONTEXT_CLASS_ANDROID_CONTEXT);
			} catch (final Exception e) {
				// DO NOTHING.
			}

			// Get the list of files from the Android context.
			if ((contextClass != null) && (contextClass.isInstance(this.contextLoader))) {

				// Set the location of the resource.
				loaderType = LOADER_TYPE_ANDROID;

				// Return resources found.
				return listAndroidResources(this.contextLoader, configTarget);

			}

			// Get the Servlet context class.
			try {

				// First try to load Jakarta Servlet context class.
				contextClass = Class.forName(ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAKARTA);

				// Load old Sun/Oracle Servlet context class if required.
				if (contextClass == null) {
					contextClass = Class.forName(ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX);
				}

			} catch (final Exception e) {
				// DO NOTHING.
			}

			// Get the list of files from the servlet context.
			if ((contextClass != null) && (contextClass.isInstance(this.contextLoader))) {

				// Set the location of the resource.
				loaderType = LOADER_TYPE_SERVLET;

				// Return resources found.
				return listServletResources(configTarget);

			}

		}

		// At this point, no suitable loader is available to load resources.
		throw new LoaderException(getScopeFacade(),
				"WAREWORK cannot load resource '" + configTarget + "' because no suitable context loader is provided.",
				null, LogServiceConstants.LOG_LEVEL_WARN);

	}

	/**
	 * Gets the names and locations of the resources found in a directory of the
	 * Java project.
	 * 
	 * @param contextClass Class used to load resources.<br>
	 *                     <br>
	 * @param configTarget Directory where to load the resources.<br>
	 *                     <br>
	 * @return Map with the names and locations of the resources.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to list the
	 *                         resources.<br>
	 *                         <br>
	 */
	@SuppressWarnings("deprecation")
	private Map<String, String> listClassResources(final Class<?> contextClass, final String configTarget)
			throws LoaderException {

		// Target path.
		String target = configTarget;

		// Validate that config target starts with a forward slash.
		if (!target.startsWith(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resource '" + configTarget + "' because path does not starts with '/'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Append forward slash at the end of the config target if not
		// exists.
		if ((target.length() > 1) && (!target.endsWith(ResourceL1Helper.DIRECTORY_SEPARATOR))) {
			target = target + ResourceL1Helper.DIRECTORY_SEPARATOR;
		}

		// Path to the context class.
		final String classpath = ResourceL1Helper.DIRECTORY_SEPARATOR
				+ contextClass.getName().replace(CommonValueL2Constants.CHAR_PERIOD,
						CommonValueL2Constants.CHAR_FORWARD_SLASH)
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_CLASS;

		// In case of a JAR file, we can't actually find a directory (if you
		// run 'class.getResource(x)' when 'x' is a directory inside a JAR
		// file, you'll get null). We have to search for the contents of
		// that directory one by one. The trick is to perform
		// 'class.getResource(x)' with the 'contextClass' (in this case,
		// Java can find without problems the resource in the JAR). After
		// that we can open the JAR file and search for the files.
		final URL url = contextClass.getResource(classpath);

		// Get the string representation of the URL that points to the
		// class.
		final String externalForm = url.toString();

		// Get the URL protocol.
		final String protocol = url.getProtocol();

		// Initialize where to store resources found.
		final Map<String, String> resources = new HashMap<String, String>();

		// List resources.
		if ((protocol.equalsIgnoreCase(URL_PROTOCOL_FILE)) || (protocol.equalsIgnoreCase(URL_PROTOCOL_VFS))) {

			// Get the location of the class.
			final String urlPath = url.getPath();

			// Get the directory.
			final String pathName = urlPath.substring(0, urlPath.lastIndexOf(classpath)) + target;

			// Get the URL without the package directory.
			final String baseURL = externalForm.substring(0, externalForm.lastIndexOf(classpath)) + target;

			// Get the directory as a Java object.
			final File directory = new File(pathName);

			// Validate it is a directory.
			if (!directory.isDirectory()) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load resource '" + configTarget + "' because it's not a directory.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// List the resources from the directory.
			final String[] entries = directory.list();

			// Filter resources found.
			for (int i = 0; i < entries.length; i++) {

				// Get one resource from the directory.
				final String entry = entries[i];

				// Process only those files with the extension provided.
				if ((entry.indexOf(ResourceL1Helper.DIRECTORY_SEPARATOR) < 0)
						&& (entry.endsWith(ResourceL2Helper.FILE_EXTENSION_SEPARATOR + fileExtension))) {

					// Get the name of the file (without the extension).
					final String resourceName = entry.substring(0, entry.lastIndexOf(fileExtension) - 1);

					// Get the URL that points to the resource.
					final String resourcePath = baseURL + entry;

					// Save the resource.
					resources.put(resourceName, resourcePath);

				}

			}

		} else {

			// Get the location of the JAR file.
			String jarPath = null;
			if (url.getProtocol().equals(URL_PROTOCOL_JAR)) {
				jarPath = url.getPath().substring(5, url.getPath().indexOf(CommonValueL2Constants.CHAR_EXCLAMATION));
			} else { // ZIP
				jarPath = url.getPath().substring(0, url.getPath().indexOf(CommonValueL2Constants.CHAR_EXCLAMATION));
			}

			// Decode the URL.
			String decodedPath = null;
			try {
				decodedPath = URLDecoder.decode(jarPath, ENCODING_UTF8);
			} catch (final Exception e) {
				decodedPath = URLDecoder.decode(jarPath);
			}

			// Get the JAR file.
			JarFile jar = null;
			try {
				jar = new JarFile(decodedPath);
			} catch (final Exception e) {
				throw new LoaderException(
						getScopeFacade(), "WAREWORK cannot load resource '" + configTarget
								+ "' because cannot open JAR file '" + decodedPath + "' where it exists.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Gives all entries in JAR.
			final Enumeration<JarEntry> entries = jar.entries();

			// Retrieve just the files from the directory we are looking
			// for.
			while (entries.hasMoreElements()) {

				// Get one JAR entry.
				final String jarEntry = ResourceL1Helper.DIRECTORY_SEPARATOR + entries.nextElement().getName();

				// Filter according to the path.
				if (jarEntry.startsWith(target)) {

					// Get the file from the specified directory of the JAR.
					final String entry = jarEntry.substring(target.length());

					// Skip subdirectories.
					if ((entry.endsWith(ResourceL2Helper.FILE_EXTENSION_SEPARATOR + fileExtension))
							&& (entry.indexOf(ResourceL1Helper.DIRECTORY_SEPARATOR) == -1)) {

						// Get the name of the file (without the extension).
						final String resourceName = entry.substring(0, entry.lastIndexOf(fileExtension) - 1);

						// Get the URL that points to the resource.
						final String resourcePath = externalForm.substring(0,
								externalForm.indexOf(StringL2Helper.CHARACTER_EXCLAMATION) + 1) + jarEntry;

						// Save the resource.
						resources.put(resourceName, resourcePath);

					}

				}

			}

			// Close the JAR file.
			try {
				jar.close();
			} catch (final IOException e) {
				// DO NOTHING.
			}

		}

		// Return entries found.
		return (resources.size() > 0) ? resources : null;

	}

	/**
	 * Gets the names and locations of the resources found in a directory of an
	 * Android project.
	 * 
	 * @param contextType  Android context class used to load resources.<br>
	 *                     <br>
	 * @param configTarget Directory where to lload the resources.
	 * @return Map with the names and locations of the resources.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to list the
	 *                         resources.<br>
	 *                         <br>
	 */
	private Map<String, String> listAndroidResources(final Object contextType, final String configTarget)
			throws LoaderException {

		// Target path.
		String targetPath = configTarget;

		// Validate that config target starts with a forward slash.
		if (!targetPath.startsWith(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			throw new LoaderException(getScopeFacade(), "WAREWORK cannot list Android resources from '" + configTarget
					+ "' because path does not starts with '/'.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Remove the '/' at the begining.
		if (targetPath.equals(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			targetPath = CommonValueL2Constants.STRING_EMPTY;
		} else {
			targetPath = targetPath.substring(1, targetPath.length());
		}

		// Retrieve resources from 'assets' folder.
		String[] entries = null;
		try {
			entries = (String[]) ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_LIST,
					ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_GET_ASSETS, contextType,
							new Class[] {}, new Object[] {}),
					new Class[] { String.class }, new Object[] { targetPath });
		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot list resources '" + targetPath + "' with Android context class "
							+ ScopeL1Constants.CONTEXT_CLASS_ANDROID_CONTEXT + " due to the following problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Find resources.
		if ((entries != null) && (entries.length > 0)) {

			// Initialize where to store resources found.
			final Map<String, String> resources = new HashMap<String, String>();

			// Retrieve just the files we are looking for.
			for (int i = 0; i < entries.length; i++) {

				// Get one resource from 'assets' folder.
				final String entry = entries[i];

				// Filter resources found.
				if ((entry.endsWith(ResourceL2Helper.FILE_EXTENSION_SEPARATOR + fileExtension))
						&& (entry.indexOf(ResourceL1Helper.DIRECTORY_SEPARATOR) == -1)) {
					resources.put(entry.substring(0, entry.lastIndexOf(fileExtension) - 1),
							targetPath + ResourceL1Helper.DIRECTORY_SEPARATOR + entry);
				}

			}

			// Return entries found.
			if (resources.size() > 0) {
				return resources;
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the names and locations of the resources found in a directory of the
	 * Java Web project.
	 * 
	 * @param configTarget Directory where to load the resources.<br>
	 *                     <br>
	 * @return Map with the names and locations of the resources.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to list the
	 *                         resources.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> listServletResources(final String configTarget) throws LoaderException {

		// Target path.
		String target = configTarget;

		// Validate that config target starts with a forward slash.
		if (!target.startsWith(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			throw new LoaderException(getScopeFacade(), "WAREWORK cannot list Servlet resources from '" + configTarget
					+ "' because path does not starts with '/'.", null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Append forward slash at the end of the config target if not
		// exists.
		if ((target.length() > 1) && (!target.endsWith(ResourceL1Helper.DIRECTORY_SEPARATOR))) {
			target = target + ResourceL1Helper.DIRECTORY_SEPARATOR;
		}

		// Get the base directory where the files exist.
		Set<String> servletResources = null;
		try {
			servletResources = (Set<String>) ReflectionL2Helper.invokeMethod(
					ScopeL1Constants.CONTEXT_METHOD_GET_RESOURCE_PATHS, contextLoader, new Class[] { String.class },
					new Object[] { target });
		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot list resources '" + configTarget + "' with Servlet context class "
							+ ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX + " due to the following problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Find resources only if they exist.
		if ((servletResources != null) && (servletResources.size() > 0)) {

			// Initialize where to store resources found.
			final Map<String, String> resources = new HashMap<String, String>();

			// Find resources.
			try {
				for (final Iterator<String> iterator = servletResources.iterator(); iterator.hasNext();) {

					// Get one resource.
					final String entry = iterator.next();

					// Filter resources found.
					if (entry.endsWith(ResourceL2Helper.FILE_EXTENSION_SEPARATOR + fileExtension)) {

						// Get the URL that points to the resource.
						final URL resourceURL = (URL) ReflectionL2Helper.invokeMethod(
								ScopeL1Constants.CONTEXT_METHOD_GET_RESOURCE, contextLoader,
								new Class[] { String.class }, new Object[] { entry });

						// Get the string that represents the URL.
						final String resourcePath = resourceURL.toExternalForm();

						// Get the name of the file (without the extension).
						final String resourceName = resourcePath.substring(
								resourcePath.lastIndexOf(ResourceL1Helper.DIRECTORY_SEPARATOR) + 1,
								resourcePath.lastIndexOf(ResourceL2Helper.FILE_EXTENSION_SEPARATOR + fileExtension));

						// Save the resource.
						resources.put(resourceName, resourcePath);

					}

				}
			} catch (final Exception e) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot get resource '" + configTarget + "' with Servlet context class "
								+ ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX
								+ " due to the following problem: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Return the entries found.
			if (resources.size() > 0) {
				return resources;
			}

		}

		// At this point, nothing to return.
		return null;

	}

}
