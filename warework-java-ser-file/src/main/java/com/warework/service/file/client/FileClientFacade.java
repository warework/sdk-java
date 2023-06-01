package com.warework.service.file.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;
import com.warework.service.file.FileRef;

/**
 * Performs File operations.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface FileClientFacade extends ClientFacade {

	/**
	 * Loads a file or directory as an input stream.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @return Stream to read the file or directory or <code>null</code> if file or
	 *         directory does not exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	InputStream read(final String source, final Map<String, Object> options) throws ClientException;

	/**
	 * Reads the content of a file.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @param target  Where to copy the content of the file. Output stream is not
	 *                closed when operation is successfully completed.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	boolean read(final String source, final Map<String, Object> options, final OutputStream target)
			throws ClientException;

	/**
	 * Writes the content of a file.
	 * 
	 * @param source  Source file to read. Input stream is not closed when operation
	 *                is successfully completed.<br>
	 *                <br>
	 * @param size    Bytes of the source file.<br>
	 *                <br>
	 * @param options Options to write the file. Check out the underlying File
	 *                Client to review which options it may accept. This argument is
	 *                not mandatory.<br>
	 *                <br>
	 * @param target  Where to copy the content of the source file in the target
	 *                File Client.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to write the
	 *                         file.<br>
	 *                         <br>
	 */
	boolean write(final InputStream source, final long size, final Map<String, Object> options, final String target)
			throws ClientException;

	/**
	 * Creates a new directory.
	 * 
	 * @param path    Directory to create.<br>
	 *                <br>
	 * @param options Options to create the directory in the Client. Check out the
	 *                underlying File Client to review which options it may accept.
	 *                This argument is not mandatory.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to create the
	 *                         directory.<br>
	 *                         <br>
	 */
	boolean makeDir(final String path, final Map<String, Object> options) throws ClientException;

	/**
	 * Deletes a single file or directory.
	 * 
	 * @param path    File or directory to delete.<br>
	 *                <br>
	 * @param options Options to delete the file. Check out the underlying File
	 *                Client to review which options it may accept. This argument is
	 *                not mandatory.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         file.<br>
	 *                         <br>
	 */
	boolean delete(final String path, final Map<String, Object> options) throws ClientException;

	/**
	 * Deletes multiple files or directories.
	 * 
	 * @param resources Files or directories to delete.<br>
	 *                  <br>
	 * @param options   Options to delete the file. Check out the underlying File
	 *                  Client to review which options it may accept. This argument
	 *                  is not mandatory.<br>
	 *                  <br>
	 * @return <code>true</code> if all files and or directories were deleted,
	 *         <code>false</code> if not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         files.<br>
	 *                         <br>
	 */
	boolean delete(final Collection<FileRef> resources, final Map<String, Object> options) throws ClientException;

	/**
	 * Renames a file or directory.
	 * 
	 * @param source  File to change the name in the File Client.<br>
	 *                <br>
	 * @param newname New name for the source file.<br>
	 *                <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to rename the file
	 *                         or directory.<br>
	 *                         <br>
	 */
	boolean rename(final String source, final String newname) throws ClientException;

	/**
	 * Lists resources (files and directories) in a specific path.
	 * 
	 * @param path    Where to list the resources in the file storage.<br>
	 *                <br>
	 * @param options Options to list the files in the Client. Check out the
	 *                underlying File Client to review which options it may accept.
	 *                This argument is not mandatory.<br>
	 *                <br>
	 * @param orderBy Criteria to sort the resources in the file storage. Use any of
	 *                the <code>FileServiceConstants.ORDER_BY_xyz</code> constants
	 *                to specify what to sort. Specific clients may accept custom
	 *                criteria.<br>
	 *                <br>
	 * @return A list with the resources found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the
	 *                         resources.<br>
	 *                         <br>
	 */
	List<FileRef> list(final String path, final Map<String, Object> options, final String[] orderBy)
			throws ClientException;

	/**
	 * Gets a resource (file or directory) reference.
	 * 
	 * @param path    Path to the resource in the file storage.<br>
	 *                <br>
	 * @param options Options to find the file in the Client. Check out the
	 *                underlying File Client to review which options it may accept.
	 *                This argument is not mandatory.<br>
	 *                <br>
	 * @return A reference to the resource.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to find the
	 *                         resource.<br>
	 *                         <br>
	 */
	FileRef find(final String path, final Map<String, Object> options) throws ClientException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the base path defined for this Client.<br>
	 * <br>
	 * This method will return the value specified for parameter
	 * <code>AbstractFileClientConnector.PARAMETER_BASE_PATH</code> when Client was
	 * created.
	 * 
	 * @return Base path to append before any given path and for every operation to
	 *         perform, or <code>null</code> if no base path was defined.<br>
	 *         <br>
	 */
	String getBasePath();

	/**
	 * Creates the specific sysntax to interact with a directory.
	 * 
	 * @param name Name of the directory without the directory character separator
	 *             ('/' or '\').<br>
	 *             <br>
	 * @return Directory name as a path, with the character used to separate
	 *         directories.<br>
	 *         <br>
	 */
	String toDirectory(final String name);

	/**
	 * Extracts from a given path the name of each directory.
	 * 
	 * @param path Path where to retrieve the directory names.<br>
	 *             <br>
	 * @return Directory names or <code>null</code> if no directories exist.<br>
	 *         <br>
	 */
	List<String> toPathChain(final String path);

	/**
	 * Specifies if Client supports directories.
	 * 
	 * @return <code>true</code> if directories are supportted.<br>
	 *         <br>
	 */
	boolean allowsDirectories();

	/**
	 * Specifies if Client requires the number of bytes when <code>write</code>
	 * operation is invoked.
	 * 
	 * @return <code>true</code> if length is required in <code>write</code>
	 *         operation.<br>
	 *         <br>
	 */
	boolean requiresWriteLength();

}
