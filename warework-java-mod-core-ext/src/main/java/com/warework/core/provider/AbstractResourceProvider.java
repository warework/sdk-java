package com.warework.core.provider;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.AbstractL1Exception;
import com.warework.core.loader.AbstractLoader;
import com.warework.core.loader.DirectoryResources;
import com.warework.core.loader.LoaderException;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.service.log.LogServiceConstants;

/**
 * Parses a resource file from a given directory and returns the object that
 * represents that file.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractResourceProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies where to load resources. If this
	 * parameter references a string object, then the Framework performs one of
	 * these actions (just one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Provider configuration
	 * and, if it exists, the Framework extracts the corresponding class/object and
	 * uses it to get the resource. (b) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where the
	 * Provider and extracts the corresponding class to finally retrieve the
	 * resource with it. (c) return <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = ScopeL1Constants.PARAMETER_CONFIG_TARGET;

	/**
	 * Initialization parameter that specifies the class that should be used to load
	 * resources. The value of this parameter usually is the name of a class that
	 * exists in your project, so the Warework Framework can read resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Resources helper.
	private DirectoryResources resourcesHelper;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the class that represents which Loader to use to load the resources.
	 * 
	 * @return A Loader class.<br>
	 *         <br>
	 */
	protected abstract Class<? extends LoaderFacade> getLoaderType();

	/**
	 * Gets the file extension of the resources to load (for example: ".xml" or
	 * ".json").
	 * 
	 * @return File extension of the resources.<br>
	 *         <br>
	 */
	protected abstract String getFileExtension();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException If there is an error when trying to initialize the
	 *                           Provider.<br>
	 *                           <br>
	 */
	protected void initialize() throws ProviderException {

		// Get the target configuration.
		final Object configTarget = getInitParameter(PARAMETER_CONFIG_TARGET);
		if ((configTarget == null) || !(configTarget instanceof String)) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName() + "' because given parameter '"
							+ PARAMETER_CONFIG_TARGET + "' is null or it is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Initialize the resources helper.
		try {
			resourcesHelper = new DirectoryResources(getScopeFacade(), getFileExtension(), (String) configTarget,
					getInitParameter(PARAMETER_CONTEXT_LOADER));
		} catch (final LoaderException e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because the following exception is thrown when trying to list the resources: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * This method does not perform any operation.
	 */
	protected void connect() {
	}

	/**
	 * This method does not perform any operation.
	 */
	protected void disconnect() {
	}

	/**
	 * Validates if the connection of the Provider is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Parses the content of the resource loaded into a Java object.
	 * 
	 * @param name Name of the file to read, without the file extension.<br>
	 *             <br>
	 * @return Object that represents the content of the resource loaded.<br>
	 *         <br>
	 */
	protected Object getObject(final String name) {

		// Read the text file.
		InputStream inputStream = null;
		try {
			inputStream = resourcesHelper.getResource(name);
		} catch (final AbstractL1Exception e) {
			getScopeFacade().log(e.getMessage(), LogServiceConstants.LOG_LEVEL_DEBUG);
		}

		// Deserialize the file.
		if (inputStream != null) {
			try {
				return parse(inputStream);
			} catch (final Exception e) {
				getScopeFacade().log(
						"WAREWORK cannot parse resource/file named '" + name + "' in Provider '" + getName()
								+ "' because the following exception is thrown: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_DEBUG);
			}
		}

		// Return nothing.
		return null;

	}

	/**
	 * Gets the names of every resource/file that exists in the base directory and
	 * matches the specified file extension.
	 * 
	 * @return Names of the files in the base directory or <code>null</code> if no
	 *         one exists.<br>
	 *         <br>
	 */
	protected Enumeration<String> getObjectNames() {
		return resourcesHelper.getResourceNames();
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Parses the content of the resource loaded into a Java object.
	 * 
	 * @param resource Resource/file loaded.<br>
	 *                 <br>
	 * @return Object that represents the content of the resource.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to parse the
	 *                         resource.<br>
	 *                         <br>
	 */
	private Object parse(final InputStream resource) throws LoaderException {

		// Create a configuration for a Loader.
		final Map<String, Object> parameters = new HashMap<String, Object>();

		// Configure a Loader.
		parameters.put(ScopeL1Constants.PARAMETER_CONFIG_TARGET, resource);

		// Return the object that represents the file.
		return AbstractLoader.load(getScopeFacade(), getLoaderType(), parameters);

	}

}
