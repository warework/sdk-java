package com.warework.core.util.helper;

import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.warework.core.util.CommonValueL2Constants;

/**
 * Performs common resources operations.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class ResourceL2Helper extends ResourceL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// FILE EXTENSIONS

	/**
	 * Constant that identifies the '.bar' file extension.
	 */
	public static final String FILE_EXTENSION_BAR = "bar";

	/**
	 * Constant that identifies the '.css' file extension.
	 */
	public static final String FILE_EXTENSION_CSS = "css";

	/**
	 * Constant that identifies the '.csv' file extension.
	 */
	public static final String FILE_EXTENSION_CSV = "csv";

	/**
	 * Constant that identifies the '.dtd' file extension.
	 */
	public static final String FILE_EXTENSION_DTD = "dtd";

	/**
	 * Constant that identifies the '.ear' file extension.
	 */
	public static final String FILE_EXTENSION_EAR = "ear";

	/**
	 * Constant that identifies the '.eot' file extension.
	 */
	public static final String FILE_EXTENSION_EOT = "eot";

	/**
	 * Constant that identifies the '.gif' file extension.
	 */
	public static final String FILE_EXTENSION_GIF = "gif";

	/**
	 * Constant that identifies the '.html' file extension.
	 */
	public static final String FILE_EXTENSION_HTML = "html";

	/**
	 * Constant that identifies the '.ico' file extension.
	 */
	public static final String FILE_EXTENSION_ICO = "ico";

	/**
	 * Constant that identifies the '.jpg' file extension.
	 */
	public static final String FILE_EXTENSION_JPG = "jpg";

	/**
	 * Constant that identifies the '.jpql' file extension.
	 */
	public static final String FILE_EXTENSION_JPQL = "jpql";

	/**
	 * Constant that identifies the '.war' file extension.
	 */
	public static final String FILE_EXTENSION_KEY = "key";

	/**
	 * Constant that identifies the '.MF' file extension.
	 */
	public static final String FILE_EXTENSION_MF = "MF";

	/**
	 * Constant that identifies the '.pdf' file extension.
	 */
	public static final String FILE_EXTENSION_PDF = "pdf";

	/**
	 * Constant that identifies the '.png' file extension.
	 */
	public static final String FILE_EXTENSION_PNG = "png";

	/**
	 * Constant that identifies the '.properties' file extension.
	 */
	public static final String FILE_EXTENSION_PROPERTIES = "properties";

	/**
	 * Constant that identifies the '.rar' file extension.
	 */
	public static final String FILE_EXTENSION_RAR = "rar";

	/**
	 * Constant that identifies the '.ser' file extension.
	 */
	public static final String FILE_EXTENSION_SER = "ser";

	/**
	 * Constant that identifies the '.sql' file extension.
	 */
	public static final String FILE_EXTENSION_SQL = "sql";

	/**
	 * Constant that identifies the '.svg' file extension.
	 */
	public static final String FILE_EXTENSION_SVG = "svg";

	/**
	 * Constant that identifies the '.tar' file extension.
	 */
	public static final String FILE_EXTENSION_TAR = "tar";

	/**
	 * Constant that identifies the '.template' file extension.
	 */
	public static final String FILE_EXTENSION_TEMPLATE = "template";

	/**
	 * Constant that identifies the '.tld' file extension.
	 */
	public static final String FILE_EXTENSION_TLD = "tld";

	/**
	 * Constant that identifies the '.ttf' file extension.
	 */
	public static final String FILE_EXTENSION_TTF = "ttf";

	/**
	 * Constant that identifies the '.tmp' file extension.
	 */
	public static final String FILE_EXTENSION_TMP = "tmp";

	/**
	 * Constant that identifies the '.txt' file extension.
	 */
	public static final String FILE_EXTENSION_TXT = "txt";

	/**
	 * Constant that identifies the '.war' file extension.
	 */
	public static final String FILE_EXTENSION_WAR = "war";

	/**
	 * Constant that identifies the '.webmanifest' file extension.
	 */
	public static final String FILE_EXTENSION_WEBMANIFEST = "webmanifest";

	/**
	 * Constant that identifies the '.webp' file extension.
	 */
	public static final String FILE_EXTENSION_WEBP = "webp";

	/**
	 * Constant that identifies the '.woff' file extension.
	 */
	public static final String FILE_EXTENSION_WOFF = "woff";

	/**
	 * Constant that identifies the '.woff2' file extension.
	 */
	public static final String FILE_EXTENSION_WOFF2 = "woff2";

	/**
	 * Constant that identifies the '.wsdl' file extension.
	 */
	public static final String FILE_EXTENSION_WSDL = "wsdl";

	/**
	 * Constant that identifies the '.xsd' file extension.
	 */
	public static final String FILE_EXTENSION_XSD = "xsd";

	/**
	 * Constant that identifies the '.zip' file extension.
	 */
	public static final String FILE_EXTENSION_ZIP = "zip";

	// DEFAULT FILE NAMES.

	/**
	 * Constant that identifies the 'MANIFEST' file name.
	 */
	public static final String FILE_NAME_MANIFEST = "MANIFEST";

	/**
	 * Constant that identifies the 'messages' file name.
	 */
	public static final String FILE_NAME_MESSAGES_RESOURCE = "messages";

	/**
	 * Constant that identifies the 'configuration' file name.
	 */
	public static final String FILE_NAME_CONFIGURATION_RESOURCE = "configuration";

	// DIRECTORIES

	/**
	 * Constant that identifies the 'WEB-INF' directory.
	 */
	public static final String DIRECTORY_WEB_INF = "WEB-INF";

	/**
	 * Constant that identifies the 'WEB-INF/lib' directory.
	 */
	public static final String DIRECTORY_WEB_INF_LIBRARIES = DIRECTORY_WEB_INF + ResourceL1Helper.DIRECTORY_SEPARATOR
			+ DIRECTORY_LIBRARIES;

	// PROPERTIES FILES

	/**
	 * Constant that identifies the property separator character.
	 */
	public static final String PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR = StringL1Helper.CHARACTER_PERIOD;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ResourceL2Helper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the the most properly MIME content type from a given file extension.
	 * 
	 * @param fileName File name where to extract the file extension to match with
	 *                 the MIME type.<br>
	 *                 <br>
	 * @return MIME content type that matches the file extension or
	 *         <code>application/octet-stream</code> if no matches are found.<br>
	 *         <br>
	 */
	public static String getContentType(final String fileName) {

		// Find file extensions character position in file name.
		final int indexOfExtSeparator = fileName.indexOf(CommonValueL2Constants.CHAR_PERIOD);
		if (indexOfExtSeparator < 0) {
			return NetworkL2Helper.CONTENT_TYPE_UNKNOWN_BINARY;
		}

		// First try with default JRE/JDK implementation.
		final String contentType = URLConnection.guessContentTypeFromName(fileName);
		if ((contentType != null) && (!contentType.equals(CommonValueL2Constants.STRING_EMPTY))
				&& (!contentType.equalsIgnoreCase(NetworkL2Helper.CONTENT_TYPE_UNKNOWN_BINARY))) {
			return contentType;
		}

		// Get the file extension.
		final String fileExtension = fileName.substring(indexOfExtSeparator + 1, fileName.length()).toLowerCase();

		// Find suitable MIME type.
		if (fileExtension.equals(FILE_EXTENSION_CLASS)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_JAVA;
		} else if (fileExtension.equals(FILE_EXTENSION_CSS)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_CSS;
		} else if (fileExtension.equals(FILE_EXTENSION_CSV)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_CSV;
		} else if (fileExtension.equals(FILE_EXTENSION_DTD)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_DTD;
		} else if (fileExtension.equals(FILE_EXTENSION_EOT)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_EOT;
		} else if (fileExtension.equals(FILE_EXTENSION_GIF)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_GIF;
		} else if (fileExtension.equals(FILE_EXTENSION_HTML)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_HTML;
		} else if (fileExtension.equals(FILE_EXTENSION_ICO)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_ICO;
		} else if (fileExtension.equals(FILE_EXTENSION_JAR)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_JAVA_ARCHIVE;
		} else if (fileExtension.equals(FILE_EXTENSION_JAVA)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_JAVA;
		} else if (fileExtension.equals(FILE_EXTENSION_JPG)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_JPG;
		} else if (fileExtension.equals(FILE_EXTENSION_JS)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_JAVASCRIPT;
		} else if (fileExtension.equals(FILE_EXTENSION_JSON)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_JSON;
		} else if (fileExtension.equals(FILE_EXTENSION_PDF)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_PDF;
		} else if (fileExtension.equals(FILE_EXTENSION_PNG)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_PNG;
		} else if (fileExtension.equals(FILE_EXTENSION_PROPERTIES)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_PLAIN;
		} else if (fileExtension.equals(FILE_EXTENSION_RAR)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_RAR;
		} else if (fileExtension.equals(FILE_EXTENSION_SER)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_SER;
		} else if (fileExtension.equals(FILE_EXTENSION_SQL)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_SQL;
		} else if (fileExtension.equals(FILE_EXTENSION_SVG)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_SVG;
		} else if (fileExtension.equals(FILE_EXTENSION_TAR)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_TAR;
		} else if (fileExtension.equals(FILE_EXTENSION_TTF)) {
			return NetworkL2Helper.CONTENT_TYPE_FONT_TTF;
		} else if (fileExtension.equals(FILE_EXTENSION_TXT)) {
			return NetworkL2Helper.CONTENT_TYPE_TEXT_PLAIN;
		} else if (fileExtension.equals(FILE_EXTENSION_WEBMANIFEST)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_WEB_MANIFEST;
		} else if (fileExtension.equals(FILE_EXTENSION_WEBP)) {
			return NetworkL2Helper.CONTENT_TYPE_IMAGE_WEBP;
		} else if (fileExtension.equals(FILE_EXTENSION_WOFF)) {
			return NetworkL2Helper.CONTENT_TYPE_FONT_WOFF;
		} else if (fileExtension.equals(FILE_EXTENSION_WOFF2)) {
			return NetworkL2Helper.CONTENT_TYPE_FONT_WOFF2;
		} else if (fileExtension.equals(FILE_EXTENSION_WSDL)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_WSDL;
		} else if (fileExtension.equals(FILE_EXTENSION_XML)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_XML;
		} else if (fileExtension.equals(FILE_EXTENSION_XSD)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_XML;
		} else if (fileExtension.equals(FILE_EXTENSION_ZIP)) {
			return NetworkL2Helper.CONTENT_TYPE_APPLICATION_ZIP;
		}

		// At this point, try with 'MimetypesFileTypeMap' available from Java 1.6.
		try {
			return (String) ReflectionL2Helper.invokeMethod("getContentType",
					Class.forName("javax.activation.MimetypesFileTypeMap").newInstance(), new Class[] { String.class },
					new Object[] { fileName });
		} catch (final Exception e) {
			return NetworkL2Helper.CONTENT_TYPE_UNKNOWN_BINARY;
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a properties file.
	 * 
	 * @param target Location of the properties file.<br>
	 *               <br>
	 * @return Properties configuration.<br>
	 *         <br>
	 */
	public static Properties loadProperties(final URL target) {

		// Create a new properties configuration
		final Properties configuration = new Properties();

		// Load the configuration
		try {
			configuration.load(target.openStream());
		} catch (final Exception e) {
			return null;
		}

		// Return the configuration loaded
		return configuration;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a resource bundle from a given package. Keep in mind that resource
	 * bundle instances are cached by default.
	 * 
	 * @param p          Package where to load the resource bundle. If it's null,
	 *                   then resource bundle should exists in the default
	 *                   package.<br>
	 *                   <br>
	 * @param bundleName Name of the resource bundle to load without the
	 *                   ".properties" file extension and the locale string, just
	 *                   the name.<br>
	 *                   <br>
	 * @param locale     Locale for the resource bundle. If null then a properties
	 *                   file without the locale specific string is loaded.<br>
	 *                   <br>
	 * @return Resource bundle.<br>
	 *         <br>
	 */
	public static ResourceBundle loadResourceBundle(final Package p, final String bundleName, final Locale locale) {
		return (p == null) ? loadResourceBundle(bundleName, locale)
				: loadResourceBundle(p.getName() + ReflectionL2Helper.PACKAGE_SEPARATOR + bundleName, locale);
	}

	/**
	 * Loads a resource bundle from a given package. Keep in mind that resource
	 * bundle instances are cached by default.
	 * 
	 * @param bundleName Name of the resource bundle to load without the
	 *                   ".properties" file extension and the locale string, just
	 *                   the name. It must include the package where the resource
	 *                   exists, like: 'com.mycompany.messages' (where
	 *                   'com.mycompany' is the package and 'messages' is the
	 *                   resource).<br>
	 *                   <br>
	 * @param locale     Locale for the resource bundle. If null then a properties
	 *                   file without the locale specific string is loaded.<br>
	 *                   <br>
	 * @return Resource bundle.<br>
	 *         <br>
	 */
	public static ResourceBundle loadResourceBundle(final String bundleName, final Locale locale) {
		return (locale == null) ? ResourceBundle.getBundle(bundleName) : ResourceBundle.getBundle(bundleName, locale);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a property from a resource bundle that exists in a given package.
	 * 
	 * @param p          Package where to load the resource bundle. If it's null,
	 *                   then resource bundle should exists in the default
	 *                   package.<br>
	 *                   <br>
	 * @param bundleName Name of the resource bundle to load without the
	 *                   ".properties" file extension and the locale string, just
	 *                   the name.<br>
	 *                   <br>
	 * @param key        Key of the property from the resource bundle loaded.<br>
	 *                   <br>
	 * @param locale     Locale for the resource bundle. If null then a properties
	 *                   file without the locale specific string is loaded.<br>
	 *                   <br>
	 * @return Value of the property loaded from the resource bundle.<br>
	 *         <br>
	 */
	public static String getProperty(final Package p, final String bundleName, final String key, final Locale locale) {
		return loadResourceBundle(p, bundleName, locale).getString(key);
	}

	/**
	 * Gets a property from a resource bundle that exists in a given package.
	 * 
	 * @param bundleName Name of the resource bundle to load without the
	 *                   ".properties" file extension and the locale string, just
	 *                   the name. It must include the package where the resource
	 *                   exists, like: 'com.mycompany.messages' (where
	 *                   'com.mycompany' is the package and 'messages' is the
	 *                   resource).<br>
	 *                   <br>
	 * @param key        Key of the property from the resource bundle loaded.<br>
	 *                   <br>
	 * @param locale     Locale for the resource bundle. If null then a properties
	 *                   file without the locale specific string is loaded.<br>
	 *                   <br>
	 * @return Value of the property loaded from the resource bundle.<br>
	 *         <br>
	 */
	public static String getProperty(final String bundleName, final String key, final Locale locale) {
		return loadResourceBundle(bundleName, locale).getString(key);
	}

}
