package com.warework.core.util.helper;

import java.util.Date;

import com.warework.core.ArtifactMetadata;
import com.warework.core.loader.LoaderFacade;
import com.warework.core.provider.AbstractProvider;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceFacade;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.service.workflow.WorkflowServiceConstants;
import com.warework.service.workflow.flowbase.FlowbaseFunction;

/**
 * Everything about reflection operations, data types and common values.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class ReflectionL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PACKAGE CONSTANTS

	/**
	 * Package '.' separator character.
	 */
	public static final String PACKAGE_SEPARATOR = StringL1Helper.CHARACTER_PERIOD;

	// DATA TYPES SHORT NAMES

	/**
	 * <code>boolean</code> short data type name.
	 */
	public static final String DATA_TYPE_NAME_BOOL = "bool";

	/**
	 * <code>boolean</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_BOOLEAN = "boolean";

	/**
	 * <code>byte</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_BYTE = "byte";

	/**
	 * <code>character</code> short data type name.
	 */
	public static final String DATA_TYPE_NAME_CHAR = "char";

	/**
	 * <code>character</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_CHARACTER = "character";

	/**
	 * <code>date</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_DATE = "date";

	/**
	 * <code>double</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_DOUBLE = "double";

	/**
	 * <code>float</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_FLOAT = "float";

	/**
	 * <code>int</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_INT = "int";

	/**
	 * <code>integer</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_INTEGER = "integer";

	/**
	 * <code>long</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_LONG = "long";

	/**
	 * <code>number</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_NUMBER = "number";

	/**
	 * <code>short</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_SHORT = "short";

	/**
	 * <code>string</code> data type name.
	 */
	public static final String DATA_TYPE_NAME_STRING = "string";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ReflectionL1Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the class from a given class name. You can provide short names like
	 * 'int' to get a 'java.lang.Integer'.
	 * 
	 * @param type Full class name or abbreviation.<br>
	 *             <br>
	 * @return Specific class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class that represents the given string.<br>
	 *                                <br>
	 */
	public static Class<?> getType(final String type) throws ClassNotFoundException {
		if ((type.equalsIgnoreCase(DATA_TYPE_NAME_BOOLEAN)) || (type.equals(DATA_TYPE_NAME_BOOL))) {
			return Boolean.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_NUMBER)) {
			return Number.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_BYTE)) {
			return Byte.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_SHORT)) {
			return Short.class;
		} else if ((type.equalsIgnoreCase(DATA_TYPE_NAME_INT)) || (type.equalsIgnoreCase(DATA_TYPE_NAME_INTEGER))) {
			return Integer.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_LONG)) {
			return Long.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_FLOAT)) {
			return Float.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_DOUBLE)) {
			return Double.class;
		} else if ((type.equalsIgnoreCase(DATA_TYPE_NAME_CHAR)) || (type.equalsIgnoreCase(DATA_TYPE_NAME_CHARACTER))) {
			return Character.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_STRING)) {
			return String.class;
		} else if (type.equalsIgnoreCase(DATA_TYPE_NAME_DATE)) {
			return Date.class;
		} else {
			return Class.forName(type);
		}
	}

	/**
	 * Validates if a class is equals to or extends another given class.
	 * 
	 * @param classA Source class to compare (with classB).<br>
	 *               <br>
	 * @param classB Class that could be extended by classA or is the same as
	 *               classA.<br>
	 *               <br>
	 * @return <code>true</code> if classA is the same or extends classB.<br>
	 *         <br>
	 */
	public static boolean isType(final Class<?> classA, final Class<?> classB) {
		return classB.isAssignableFrom(classA);
	}

	/**
	 * Gets the name of the class without the package.
	 * 
	 * @param type Class where to extract the name.<br>
	 *             <br>
	 * @return Name of the class.<br>
	 *         <br>
	 */
	public static String getClassName(final Class<?> type) {

		// Get the name of the class (package + class).
		String className = type.getName();

		// Remove the package only when it exists.
		return (className.indexOf(CommonValueL1Constants.CHAR_PERIOD) > 1)
				? type.getName().substring(className.lastIndexOf(CommonValueL1Constants.CHAR_PERIOD) + 1,
						className.length())
				: className;

	}

	/**
	 * Validates if a class exists.
	 * 
	 * @param className Name of the class.<br>
	 *                  <br>
	 * @return <code>true</code> if class exists, <code>false</code> if not.<br>
	 *         <br>
	 */
	public static boolean existsClass(final String className) {

		// Get the Class.
		Class<?> type = null;
		try {
			type = Class.forName(className);
		} catch (final ClassNotFoundException e) {
			return false;
		}

		// Validate that Class exists.
		return (type != null);

	}

	/**
	 * Gets the class of a Provider.
	 * 
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>FileText</code>. If so, this method automatically
	 *                  builds the full name for the class:
	 *                  <code>com.warework.provider.FileTextProvider</code>.<br>
	 *                  <br>
	 * @return Provider class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Provider.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends AbstractProvider> getProviderType(final String className)
			throws ClassNotFoundException {

		// Update the class name when it is not complete.
		String type = null;
		if (className.indexOf(PACKAGE_SEPARATOR) < 0) {
			type = ScopeL1Constants.PACKAGE_PROVIDER + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_PROVIDER);
		} else {
			type = className;
		}

		// Return component type.
		return (Class<? extends AbstractProvider>) Class.forName(type);

	}

	/**
	 * Gets the class of a Service.
	 * 
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>Converter</code>. If so, this method automatically
	 *                  builds the full name for the class:
	 *                  <code>com.warework.service.converter.ConverterServiceImpl</code>
	 *                  .<br>
	 *                  <br>
	 * @return Service class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Service.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends ServiceFacade> getServiceType(final String className) throws ClassNotFoundException {

		// Update the class name when it is not complete.
		String type = null;
		if (className.indexOf(PACKAGE_SEPARATOR) < 0) {
			type = ScopeL1Constants.PACKAGE_SERVICE + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToLowerCase(className) + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_SERVICE)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_IMPL);
		} else {
			type = className;
		}

		// Return component type.
		return (Class<? extends ServiceFacade>) Class.forName(type);

	}

	/**
	 * Gets the class of a Connector.
	 * 
	 * @param service   Service of the Connector.<br>
	 *                  <br>
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>Console</code>. If so, this method automatically
	 *                  builds the full name for the class:
	 *                  <code>com.warework.service.log.client.connector.ConsoleConnector</code>
	 *                  .<br>
	 *                  <br>
	 * @return Connector class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Connector.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends ConnectorFacade> getConnectorType(final ProxyServiceFacade service, String className)
			throws ClassNotFoundException {

		// Update the class name when it is not complete.
		String type = null;
		if (className.indexOf(PACKAGE_SEPARATOR) < 0) {

			// Get the name of the Service class.
			final String serviceClassName = service.getClass().getName();

			// Create the full class name.
			type = ScopeL1Constants.PACKAGE_SERVICE + PACKAGE_SEPARATOR
					+ serviceClassName.substring(ScopeL1Constants.PACKAGE_SERVICE.length() + 1,
							serviceClassName.lastIndexOf(CommonValueL1Constants.CHAR_PERIOD))
					+ PACKAGE_SEPARATOR + ScopeL1Constants.PACKAGE_CLIENTS + PACKAGE_SEPARATOR
					+ ScopeL1Constants.PACKAGE_CONNECTORS + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_CONNECTOR);

		} else {
			type = className;
		}

		// Return component type.
		return (Class<? extends ConnectorFacade>) Class.forName(type);

	}

	/**
	 * Gets the class of a Loader.
	 * 
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>ScopeClasspathJSON</code>. If so, this method
	 *                  automatically builds the full name for the class:
	 *                  <code>com.warework.loader.ScopeClasspathJSONLoader</code>
	 *                  .<br>
	 *                  <br>
	 * @return Loader class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Loader.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends LoaderFacade> getLoaderType(final String className) throws ClassNotFoundException {

		// Update the class name when it is not complete.
		String type = null;
		if (className.indexOf(PACKAGE_SEPARATOR) < 0) {
			type = ScopeL1Constants.PACKAGE_LOADER + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_LOADER);
		} else {
			type = className;
		}

		// Return component type.
		return (Class<? extends LoaderFacade>) Class.forName(type);

	}

	/**
	 * Gets the class of a Loader.
	 * 
	 * @param service   Service of the Loader.<br>
	 *                  <br>
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>ScopeClasspathJSON</code>. If so, this method
	 *                  automatically builds the full name for the class:
	 *                  <code>com.warework.loader.ScopeClasspathJSONLoader</code>
	 *                  .<br>
	 *                  <br>
	 * @return Loader class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Loader.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends LoaderFacade> getLoaderType(final ServiceFacade service, final String className)
			throws ClassNotFoundException {
		try {
			return getLoaderType(className);
		} catch (final ClassNotFoundException e) {

			// Get the name of the Service class.
			final String serviceClassName = service.getClass().getName();

			// Create the full class name.
			final String type = ScopeL1Constants.PACKAGE_SERVICE + PACKAGE_SEPARATOR
					+ serviceClassName.substring(ScopeL1Constants.PACKAGE_SERVICE.length() + 1,
							serviceClassName.lastIndexOf(CommonValueL1Constants.CHAR_PERIOD))
					+ PACKAGE_SEPARATOR + StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_LOADER);

			// Return component type.
			return (Class<? extends LoaderFacade>) Class.forName(type);

		}
	}

	/**
	 * Gets the class of a Flowbase function.
	 * 
	 * @param className Name of the class. It can be a short name, for example:
	 *                  <code>FileText</code>. If so, this method automatically
	 *                  builds the full name for the class:
	 *                  <code>com.warework.service.workflow.flowbase.function.FooFunction</code>
	 *                  .<br>
	 *                  <br>
	 * @return Flowbase function class.<br>
	 *         <br>
	 * @throws ClassNotFoundException If there is an error when trying to get the
	 *                                class of the Flowbase function.<br>
	 *                                <br>
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends FlowbaseFunction> getFlowbaseFunctionType(final String className)
			throws ClassNotFoundException {

		// Update the class name when it is not complete.
		String type = null;
		if (className.indexOf(PACKAGE_SEPARATOR) < 0) {
			type = WorkflowServiceConstants.PACKAGE_FLOWBASE_FUNCTIONS + PACKAGE_SEPARATOR
					+ StringL1Helper.firstLetterToUpperCase(className)
					+ StringL1Helper.firstLetterToUpperCase(CommonValueL1Constants.STRING_FUNCTION);
		} else {
			type = className;
		}

		// Return component type.
		return (Class<? extends FlowbaseFunction>) Class.forName(type);

	}

	/**
	 * Validates that a given artifact is compatible with a given version.
	 * 
	 * @param artifactVersion Component of the Framework.<br>
	 *                        <br>
	 * @param requiredVersion Version to compare with.<br>
	 *                        <br>
	 * @return <code>true</code> if the version of the artifact is greater or equal
	 *         to the given version, <code>false</code> if not. For example, if
	 *         given artifact is <code>2.5.1</code> and version is
	 *         <code>1.0.0</code> then it returns <code>true</code>.<br>
	 *         <br>
	 */
	public static boolean validateVersion(final ArtifactMetadata artifactVersion, final String requiredVersion) {
		return validateVersion(artifactVersion.getVersion(), requiredVersion);
	}

	/**
	 * Validates that a one version is compatible with another version.
	 * 
	 * @param sourceVersion   Source version.<br>
	 *                        <br>
	 * @param requiredVersion Version to compare with.<br>
	 *                        <br>
	 * @return <code>true</code> if the source version is greater or equal to the
	 *         given version, <code>false</code> if not. For example, if given
	 *         source version is <code>2.5.1</code> and required version is
	 *         <code>1.0.0</code> then it returns <code>true</code>.<br>
	 *         <br>
	 */
	public static boolean validateVersion(final String sourceVersion, final String requiredVersion) {

		// Get class version.
		final int[] source = toVersionNumbers(sourceVersion);

		// Get version to compare with.
		final int[] required = toVersionNumbers(requiredVersion);

		// Compare.
		for (int i = 0; i < source.length; i++) {

			// Get artifact version.
			final int artifactValue = source[i];

			// Get version to compare.
			final int requiredValue = required[i];

			// Compare versions.
			if (artifactValue > requiredValue) {
				return true;
			} else if (artifactValue < requiredValue) {
				return false;
			}

		}

		// At this point, versions are the same.
		return true;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the version with numbers.
	 * 
	 * @param version Version.<br>
	 *                <br>
	 * @return Version with numeric values.<br>
	 *         <br>
	 */
	private static int[] toVersionNumbers(final String version) {

		// Get an array where each position is a value of the version.
		final String[] splitVersion = StringL1Helper.toStrings(version, CommonValueL1Constants.CHAR_PERIOD);

		// Setup result.
		final int[] result = new int[splitVersion.length];

		// Get the integer value of the version.
		for (int i = 0; i < splitVersion.length; i++) {
			result[i] = Integer.parseInt(splitVersion[i]);
		}

		// Return version with numeric values.
		return result;

	}

}
