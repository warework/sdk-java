package com.warework.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.warework.core.AbstractL1Exception;
import com.warework.core.loader.DirectoryResources;
import com.warework.core.loader.LoaderException;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * The FileText Provider is responsible for returning the content of text files
 * as String objects. The idea is that with a given file name (without the
 * extension) this Provider is capable to return a <code>String</code> with the
 * content of the file.<br>
 * <br>
 * It allows you to read any kind of text file ( <code>.txt</code>,
 * <code>.sql</code>, <code>.csv</code>, etc.) and it is very useful when you
 * have to load resources for your project, like query or update statements for
 * a Database Management System (for example, you can read SQL statements from
 * text files and execute them later on in the database) .<br>
 * <br>
 * With this Provider you can also specify a set of characters that will be used
 * to filter the output. You can for example, setup this Provider to read
 * <code>.sql</code> files without the new line character.<br>
 * <br>
 * <b>Configure and create a FileText Provider</b><br>
 * <br>
 * To configure this Provider you just need to give the base directory where to
 * read the text files and the extension for these files (for example:
 * <code>.txt</code>, <code>.sql</code>, <code>.csv</code>, ...):<br>
 * <br>
 * <code>
 * // Create the configuration of the provider.<br> 
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Configure the Provider.<br> 
 * parameters.put(FileTextProvider.PARAMETER_CONFIG_TARGET, "/META-INF");<br> 
 * parameters.put(FileTextProvider.PARAMETER_FILE_EXTENSION, "txt"); <br>
 * <br>
 * // Create the Provider.<br> 
 * scope.createProvider("filetext-provider", FileTextProvider.class, parameters);<br> 
 * </code> <br>
 * The above example shows how to read <code>.txt</code> files from
 * <code>/META-INF</code> directory. It is very important to bear in mind that
 * only files with the <code>.txt</code> extension will be processed.<br>
 * <br>
 * The specific place where resources can be loaded for your project is
 * specified in each Warework Distribution. Typically, in a Desktop Distribution
 * the <code>/META-INF</code> directory is located in the source folder of your
 * own project. Other types of Distributions may read your resources from
 * <code>/WEB-INF</code> for example, so please review the documentation
 * associated to your Distribution to know where your resources context is.<br>
 * <br>
 * With this Provider you can also specify a set of characters that will be used
 * to filter the output. You can for example, setup this Provider to read
 * <code>.sql</code> files without the new line character. Review the constants
 * defined at <code>FileTextProvider</code> class, those with the
 * <code>PARAMETER_REMOVE-xyz-CHARACTER</code> pattern, and check out which
 * characters can be removed from the loaded text.<br>
 * <br>
 * <b>Retrieve objects from a FileText Provider</b><br>
 * <br>
 * At this point the FileText Provider is running and we can request objects
 * from it. To do so, we just need to provide the name of a text file that
 * exists in the directory and with the extension specified in the
 * configuration.<br>
 * <br>
 * <code>
 * // Get a String with the content of 'notice.txt'. <br>
 * String notice = (String) scope.getObject("filetext-provider", "notice");<br>
 * </code> <br>
 * When this line of code is executed, the filetext-provider performs the
 * following actions:<br>
 * <br>
 * <ol>
 * <li>Looks for a file named <code>/META-INF/notice.txt</code>.</li>
 * <li>Reads the content of the file and places it in a <code>String</code>
 * object.</li>
 * <li>Filters unwanted characters (if it is specified in the
 * configuration).</li>
 * <li>Returns a String with the content of the file filtered.</li>
 * </ol>
 * <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FileTextProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "filetext" + StringL2Helper.CHARACTER_HYPHEN
			+ CommonValueL2Constants.STRING_PROVIDER;

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

	/**
	 * Initialization parameter that specifies the extension for every file to load.
	 * For example: if you want to load ".sql" files from a specific directory, you
	 * have to use for this parameter the value "sql"; by doing so, you can read the
	 * text of a file named "query-users.sql" just by using "query-users" as the
	 * name of the object to retrieve from this Provider. Keep in mind that the base
	 * directory where to load every file is defined with the
	 * <code>PARAMETER_CONFIG_TARGET</code>.
	 */
	public static final String PARAMETER_FILE_EXTENSION = "file-extension";

	/**
	 * Initialization parameter that specifies whether the 'new line' character
	 * should be eliminated on each file loaded. Accepted values for this parameter
	 * are <code>true</code> or <code>false</code> (as <code>java.lang.String</code>
	 * or <code>java.lang.Boolean</code> objects). If this parameter is not
	 * specified then the value for it is <code>false</code>.
	 */
	public static final String PARAMETER_REMOVE_NEW_LINE_CHARACTER = "remove-new-line-character";

	/**
	 * Initialization parameter that specifies whether the 'tab' character should be
	 * eliminated on each file loaded. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then the value for it is <code>false</code>.
	 */
	public static final String PARAMETER_REMOVE_TAB_CHARACTER = "remove-tab-character";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Resources helper.
	private DirectoryResources resourcesHelper;

	// Characters to be removed on each text file.
	private char[] charactersToRemove;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		// DO NOTHING.
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

		// Get the file extension.
		final Object extension = getInitParameter(PARAMETER_FILE_EXTENSION);
		if ((extension == null) || !(extension instanceof String)) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName() + "' because given parameter '"
							+ PARAMETER_FILE_EXTENSION + "' is null or it is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Initialize the resources helper.
		try {
			resourcesHelper = new DirectoryResources(getScopeFacade(), (String) extension, (String) configTarget,
					getInitParameter(PARAMETER_CONTEXT_LOADER));
		} catch (final LoaderException e) {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because the following exception is thrown when trying to list the resources: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Store every character that must be removed on each text file.
		final List<Character> characters = new ArrayList<Character>();

		// Remove new line character.
		if (isInitParameter(PARAMETER_REMOVE_NEW_LINE_CHARACTER)) {
			characters.add(new Character('\n'));
		}

		// Remove new line character.
		if (isInitParameter(PARAMETER_REMOVE_TAB_CHARACTER)) {
			characters.add(new Character('\t'));
		}

		// Store the characters to remove in an array.
		if (characters.size() > 0) {

			// Setup the array for the characters to remove.
			charactersToRemove = new char[characters.size()];

			// Index for the array of characters to remove.
			int index = 0;

			// Copy each character to remove.
			for (Iterator<Character> iterator = characters.iterator(); iterator.hasNext();) {

				// Set the character to remove.
				charactersToRemove[index] = iterator.next().charValue();

				// Next position for the array.
				index = index + 1;

			}

			// Sort the characters to speed up the search later.
			Arrays.sort(this.charactersToRemove);

		}

	}

	/**
	 * This method does not perform any operation.
	 */
	protected void connect() {
		// DO NOTHING.
	}

	/**
	 * This method does not perform any operation.
	 */
	protected void disconnect() {
		// DO NOTHING.
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
	 * Reads the contents of a text file and returns a <code>String</code> with that
	 * text.
	 * 
	 * @param name Name of the file to read, without the file extension.<br>
	 *             <br>
	 * @return <code>java.lang.String</code> with the text of a file.<br>
	 *         <br>
	 */
	protected Object getObject(String name) {

		// Read the text file.
		InputStream inputStream = null;
		try {
			inputStream = resourcesHelper.getResource(name);
		} catch (final AbstractL1Exception e) {
			getScopeFacade().log(e.getMessage(), LogServiceConstants.LOG_LEVEL_DEBUG);
		}

		// Read the contents of the text file.
		String text = null;
		if (inputStream != null) {
			try {
				text = toString(inputStream);
			} catch (final Exception e) {
				getScopeFacade().log(
						"WAREWORK cannot read text file named '" + name + "' in Provider '" + getName()
								+ "' because the following exception is thrown: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_DEBUG);
			}
		}

		// Process the result and return it.
		if (text != null) {
			return filterText(text);
		}

		// Return nothing.
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
	protected Enumeration<String> getObjectNames() {
		return resourcesHelper.getResourceNames();
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Dumps text from a given InputStream into a string.
	 * 
	 * @param path Input stream where to read the text.<br>
	 *             <br>
	 * @return Target text as a string.<br>
	 *         <br>
	 * @throws IOException If there is an error when trying to read the stream.<br>
	 *                     <br>
	 */
	private String toString(final InputStream inputStream) throws IOException {

		// Get the input stream for the text file.
		final InputStreamReader streamReader = new InputStreamReader(inputStream);

		// Setup a buffer.
		final BufferedReader reader = new BufferedReader(streamReader);

		// Each line.
		String line = null;

		// Text file.
		final StringBuffer result = new StringBuffer();

		// Read the text file.
		while ((line = reader.readLine()) != null) {
			result.append(line);
			result.append(StringL1Helper.CHARACTER_NEW_LINE);
		}

		// Remove new line character.
		if (result.length() > 0) {
			return result.toString().substring(0, result.length() - 1);
		} else {
			return result.toString();
		}

	}

	/**
	 * Removes a set of characters in a string.
	 * 
	 * @param string Source string.<br>
	 *               <br>
	 * @return String without given characters.<br>
	 *         <br>
	 */
	private String filterText(final String string) {
		if (this.charactersToRemove != null) {

			// Set up the result.
			final StringBuffer result = new StringBuffer();

			// Get the max length of the string.
			final int maxLength = string.length();

			// Remove every given character.
			for (int i = 0; i < maxLength; i++) {

				// Get a character from the string.
				final char character = string.charAt(i);

				// Find the position of the character in the characters to
				// remove array.
				int index = Arrays.binarySearch(this.charactersToRemove, character);

				// Match if character if a character to remove.
				if (index < 0) {
					result.append(character);
				}

			}

			// Return the string.
			return result.toString();

		} else {
			return string;
		}
	}

}