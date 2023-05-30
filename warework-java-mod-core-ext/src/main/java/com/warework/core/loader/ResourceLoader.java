package com.warework.core.loader;

import java.io.InputStream;
import java.net.URL;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Helper to load resources.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ResourceLoader extends AbstractResourceManager {

	// ///////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////

	/**
	 * Creates the helper to load resources.
	 * 
	 * @param scope Scope where this helper belongs to.<br>
	 *              <br>
	 */
	public ResourceLoader(final ScopeFacade scope) {
		super(scope);
	}

	// ///////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////

	/**
	 * Gets the resource as a stream.
	 * 
	 * @param contextType  Class (or a string that represents a class) used to load
	 *                     resources.<br>
	 *                     <br>
	 * @param configTarget Resource to load.<br>
	 *                     <br>
	 * @return Stream that represents the resource loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to get the
	 *                         resource.<br>
	 *                         <br>
	 */
	public InputStream getResource(final Object contextType, final Object configTarget) throws LoaderException {
		if (configTarget instanceof String) {

			// Current object used to load resources.
			Object targetContext = contextType;

			// Validate config target.
			if (configTarget.equals(CommonValueL2Constants.STRING_EMPTY)) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load resources from target path because it's empty.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// First, try to load resource with URL.
			try {
				return new URL((String) configTarget).openStream();
			} catch (final Exception e) {
				// DO NOTHING.
			}

			// Get the context type specified for the Loader.
			if ((targetContext == null) && (getScopeFacade() != null)) {
				targetContext = getScopeFacade().getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER);
			}

			// Validate that a context for the loader exists.
			if (targetContext == null) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load resource '" + configTarget
								+ "' because no suitable context loader is provided.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			} else if ((targetContext instanceof String) && (getScopeFacade() != null)) {

				// Get the context loader object from a Provider.
				final Object contextLoader = getScopeFacade().getObject((String) targetContext,
						ScopeL1Constants.PARAMETER_CONTEXT_LOADER);

				// Update context type.
				if (contextLoader != null) {
					targetContext = contextLoader;
				}

			}

			// Load the resource with the context class.
			return loadContextResource(targetContext, (String) configTarget);

		} else if (configTarget instanceof InputStream) {
			return (InputStream) configTarget;
		} else if (configTarget instanceof URL) {

			// Get the URL for the resource.
			final URL url = (URL) configTarget;

			// Return the stream for the URL.
			try {
				return url.openStream();
			} catch (final Exception e) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load " + URL.class.getName() + " resource '" + url
								+ "' due to the following problem: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resource because it's null or unknown (must be " + String.class.getName()
							+ ", " + InputStream.class.getName() + " or " + URL.class.getName() + ").",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////

	/**
	 * Gets the names and locations of the resources found in a directory of the
	 * Java project.
	 * 
	 * @param contextType  Class used to load resources.<br>
	 *                     <br>
	 * @param configTarget Resource to load.<br>
	 *                     <br>
	 * @return Stream that represents the resource loaded.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	private InputStream loadContextResource(final Object contextType, final String configTarget)
			throws LoaderException {

		// Validate that config target starts with a forward slash.
		if (!configTarget.startsWith(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resource '" + configTarget + "' because path does not starts with '/'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Load the resource with in a specific context.
		Class<?> contextClass = null;
		if ((contextType instanceof Class) || (contextType instanceof String)) {

			// Get the class for the context.
			if (contextType instanceof Class) {
				contextClass = (Class<?>) contextType;
			} else {
				try {
					contextClass = Class.forName((String) contextType);
				} catch (final Exception e) {
					throw new LoaderException(getScopeFacade(), "WAREWORK cannot load resource '" + configTarget
							+ "' because context class '" + ((String) contextType)
							+ "' does not represents a valid class as it cannot be instantiated by the Framework.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}

			// Return the stream for the URL.
			try {
				return contextClass.getResourceAsStream(configTarget);
			} catch (final Exception e) {
				throw new LoaderException(
						getScopeFacade(), "WAREWORK cannot load resource '" + configTarget
								+ "' due to the following problem: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the Android context class.
		try {
			contextClass = Class.forName(ScopeL1Constants.CONTEXT_CLASS_ANDROID_CONTEXT);
		} catch (final Exception e) {
			// DO NOTHING.
		}

		// Get the list of files from the Android context.
		if ((contextClass != null) && (contextClass.isInstance(contextType))) {
			return loadAndroidResource(contextType, configTarget);
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
		if ((contextClass != null) && (contextClass.isInstance(contextType))) {
			return loadServletResource(contextType, configTarget);
		} else {
			return loadContextResource(contextType.getClass(), configTarget);
		}

	}

	/**
	 * Loads a resource from the Android context object.
	 * 
	 * @param contextType  Object to use to load the resource.<br>
	 *                     <br>
	 * @param configTarget Target resource to load.<br>
	 *                     <br>
	 * @return Stream that points to the resource.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	private InputStream loadAndroidResource(Object contextType, String configTarget) throws LoaderException {

		// Remove the first '/' character.
		String targetPath = configTarget;
		if (targetPath.startsWith(ResourceL1Helper.DIRECTORY_SEPARATOR)) {
			targetPath = targetPath.substring(1, targetPath.length());
		}

		// Load resource with Android context class.
		try {
			return (InputStream) ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_OPEN,
					ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_GET_ASSETS, contextType),
					new Class[] { String.class }, new Object[] { targetPath });
		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resource '" + targetPath + "' with Android context class "
							+ ScopeL1Constants.CONTEXT_CLASS_ANDROID_CONTEXT + " due to the following problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Loads a resource from the Servlet context object.
	 * 
	 * @param contextType  Object to use to load the resource.<br>
	 *                     <br>
	 * @param configTarget Target resource to load.<br>
	 *                     <br>
	 * @return Stream that points to a resource.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	private InputStream loadServletResource(final Object contextType, final String configTarget)
			throws LoaderException {
		try {

			// Get the URL that points to the resource.
			final URL url = (URL) ReflectionL2Helper.invokeMethod(ScopeL1Constants.CONTEXT_METHOD_GET_RESOURCE,
					contextType, new Class[] { String.class }, new Object[] { configTarget });

			// Return the resource as a stream.
			return url.openStream();

		} catch (final Exception e) {
			throw new LoaderException(getScopeFacade(),
					"WAREWORK cannot load resource '" + configTarget + "' with Servlet context class "
							+ ScopeL1Constants.CONTEXT_CLASS_SERVLET_CONTEXT_JAVAX + " due to the following problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

}
