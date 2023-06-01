package com.warework.core.loader;

import java.io.InputStream;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Manager to load resources from the classpath.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ClasspathResourceLoader extends AbstractResourceManager {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the helper to load resources.
	 * 
	 * @param scope Scope where this helper belongs to.<br>
	 *              <br>
	 */
	public ClasspathResourceLoader(final ScopeFacade scope) {
		super(scope);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

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
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	public InputStream getResource(final Object contextType, final Object configTarget) throws LoaderException {
		if (configTarget instanceof InputStream) {
			return (InputStream) configTarget;
		} else if (configTarget instanceof String) {

			// Validate config target.
			if (configTarget.equals(CommonValueL1Constants.STRING_EMPTY)) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load resources from target path because it's empty.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the context type specified for the Loader.
			Object context = null;
			if ((contextType == null) && (getScopeFacade() != null)) {
				context = getScopeFacade().getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER);
			} else {
				context = contextType;
			}

			// Validate that a context for the loader exists.
			if (context == null) {
				throw new LoaderException(getScopeFacade(),
						"WAREWORK cannot load resource '" + configTarget
								+ "' because no suitable context loader is provided.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Load the resource with the context class.
			return loadContextResource(context, (String) configTarget);

		} else {
			throw new LoaderException(
					getScopeFacade(), "WAREWORK cannot load resource because it's null or unknown (must be "
							+ String.class.getName() + " or " + InputStream.class.getName() + ".",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

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
		if ((contextType instanceof Class) || (contextType instanceof String)) {

			// Get the class for the context.
			Class<?> contextClass = null;
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

			// Return the stream that points to the resource.
			try {
				return contextClass.getResourceAsStream(configTarget);
			} catch (final Exception e) {
				throw new LoaderException(
						getScopeFacade(), "WAREWORK cannot load resource '" + configTarget
								+ "' due to the following problem: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			return loadContextResource(contextType.getClass(), configTarget);
		}

	}

}
