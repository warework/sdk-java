package com.warework.service.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;

/**
 * Service that performs operations with file systems.<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface FileServiceFacade extends ProxyServiceFacade {

	/**
	 * Reads the content of a file.
	 * 
	 * @param clientName Name of the File Client where to read the file.<br>
	 *                   <br>
	 * @param source     File to read in the File Client.<br>
	 *                   <br>
	 * @param options    Options to read the file. Check out the underlying File
	 *                   Client to review which options it may accept. This argument
	 *                   is not mandatory.<br>
	 *                   <br>
	 * @return Stream to read the file or directory or <code>null</code> if file or
	 *         directory does not exists.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to read the
	 *                          file.<br>
	 *                          <br>
	 */
	InputStream read(final String clientName, final String source, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Reads the content of a file.
	 * 
	 * @param clientName Name of the File Client where to read the file.<br>
	 *                   <br>
	 * @param source     File to read in the File Client.<br>
	 *                   <br>
	 * @param options    Options to read the file. Check out the underlying File
	 *                   Client to review which options it may accept. This argument
	 *                   is not mandatory.<br>
	 *                   <br>
	 * @param target     Where to copy the content of the file. Output stream is not
	 *                   closed when operation is successfully completed.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to read the
	 *                          file.<br>
	 *                          <br>
	 */
	boolean read(final String clientName, final String source, final Map<String, Object> options,
			final OutputStream target) throws ServiceException;

	/**
	 * Loads the content of a file into a string.
	 * 
	 * @param clientName Name of the File Client where to read the file.<br>
	 *                   <br>
	 * @param source     File to read in the File Client.<br>
	 *                   <br>
	 * @param options    Options to read the file. Check out the underlying File
	 *                   Client to review which options it may accept. This argument
	 *                   is not mandatory.<br>
	 *                   <br>
	 * @return Content of the loaded file.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to read the
	 *                          file.<br>
	 *                          <br>
	 */
	String readAsText(final String clientName, final String source, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Writes the content of a file.
	 * 
	 * @param clientName Name of the File Client where to write the file.<br>
	 *                   <br>
	 * @param source     Source file to read. Input stream is not closed when
	 *                   operation is successfully completed.<br>
	 *                   <br>
	 * @param size       Bytes of the source file.<br>
	 *                   <br>
	 * @param options    Options to write the file. Check out the underlying File
	 *                   Client to review which options it may accept. This argument
	 *                   is not mandatory.<br>
	 *                   <br>
	 * @param target     Where to copy the content of the source file in the target
	 *                   File Client.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to write the
	 *                          file.<br>
	 *                          <br>
	 */
	boolean write(final String clientName, final InputStream source, final long size, final Map<String, Object> options,
			final String target) throws ServiceException;

	/**
	 * Writes the content of a file.
	 * 
	 * @param clientName Name of the File Client where to write the file.<br>
	 *                   <br>
	 * @param source     Source file to read. Input stream is not closed when
	 *                   operation is successfully completed.<br>
	 *                   <br>
	 * @param options    Options to write the file. Check out the underlying File
	 *                   Client to review which options it may accept. This argument
	 *                   is not mandatory.<br>
	 *                   <br>
	 * @param target     Where to copy the content of the source file in the target
	 *                   File Client.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to write the
	 *                          file.<br>
	 *                          <br>
	 */
	boolean write(final String clientName, final byte[] source, final Map<String, Object> options, final String target)
			throws ServiceException;

	/**
	 * Writes a text file with the content of a string.
	 * 
	 * @param clientName Name of the File Client where to write the text file.<br>
	 *                   <br>
	 * @param content    Source string with the content to write in the text
	 *                   file.<br>
	 *                   <br>
	 * @param options    Options to write the text file. Check out the underlying
	 *                   File Client to review which options it may accept. This
	 *                   argument is not mandatory.<br>
	 *                   <br>
	 * @param target     Where to create the text file in the target File
	 *                   Client.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to write the text
	 *                          file.<br>
	 *                          <br>
	 */
	boolean write(final String clientName, final String content, final Map<String, Object> options, final String target)
			throws ServiceException;

	/**
	 * Creates a new directory.
	 * 
	 * @param clientName Name of the File Client where to create the directory.<br>
	 *                   <br>
	 * @param path       Directory to create.<br>
	 *                   <br>
	 * @param options    Options to create the directory in the Client. Check out
	 *                   the underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          directory.<br>
	 *                          <br>
	 */
	boolean makeDir(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Deletes a single file or a directory with its files and subdirectories.<br>
	 * <br>
	 * In order to recursively delete the files and subdirectories from a given
	 * directory, you have to include the <code>process-recursively</code> parameter
	 * in the <code>options</code> argument.
	 * 
	 * @param clientName Name of the File Client where to remove the files and/or
	 *                   directories.<br>
	 *                   <br>
	 * @param path       Target file or directory to delete. If it is a directory,
	 *                   you can recursively remove its files and directories with
	 *                   the <code>options</code> argument.<br>
	 *                   <br>
	 * @param options    Options to delete the file/directory. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to delete the files
	 *                          and/or directories.<br>
	 *                          <br>
	 */
	boolean delete(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Deletes multiple files and/or directories.<br>
	 * <br>
	 * 
	 * @param clientName Name of the File Client where to remove the files and/or
	 *                   directories.<br>
	 *                   <br>
	 * @param resources  Files and/or directories to remove.<br>
	 *                   <br>
	 * @param options    Options to delete the file/directory. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to delete the files
	 *                          and/or directories.<br>
	 *                          <br>
	 */
	boolean delete(final String clientName, final Collection<FileRef> resources, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Renames a file or directory.<br>
	 * <br>
	 * If the underlying File Client does not support file and directory renaming
	 * then this method will only perform this operation if the resource to update
	 * is a file. It does so by copying the source file into a new file and after
	 * that, deleting the source file. In such case, this method starts by reading
	 * the content of the source file (stores the file in memory) and after that
	 * saves the file in the target Client (copies the data from memory). Bear in
	 * mind that this method may cause memory problems for huge files.<br>
	 * <br>
	 * As you can see, you should handle this method with care when the underlying
	 * File Client does not support file and directory renaming, so please review
	 * the documentation of the File Client where to perform this operation.
	 * 
	 * @param clientName Name of the File Client where to rename the file or
	 *                   directory.<br>
	 *                   <br>
	 * @param source     File to change the name in the File Client.<br>
	 *                   <br>
	 * @param newname    New name for the source file.<br>
	 *                   <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to rename the file
	 *                          or directory.<br>
	 *                          <br>
	 */
	boolean rename(final String clientName, final String source, final String newname) throws ServiceException;

	/**
	 * Copies files and directories from one Client into another one.<br>
	 * <br>
	 * In order to recursively copy all files and subdirectories from a given
	 * directory, you have to include the <code>process-recursively</code> parameter
	 * in the <code>optionsA</code> argument of sourceA Client.<br>
	 * <br>
	 * Bear in mind that some Clients do not provide file length. In such cases, use
	 * the <code>read</code> and <code>write</code> methods to perform the
	 * <code>copy</code> operation with a temporary storage (for example: in your
	 * local file system).
	 * 
	 * @param clientNameA Source File Client where to read the file.<br>
	 *                    <br>
	 * @param sourceA     Source file to read.<br>
	 *                    <br>
	 * @param optionsA    Options to read the file in the Source Client. Check out
	 *                    the underlying File Client to review which options it may
	 *                    accept. This argument is not mandatory.<br>
	 *                    <br>
	 * @param clientNameB Target File Client where to write the file.<br>
	 *                    <br>
	 * @param targetB     Where to copy the content of the source file in the target
	 *                    File Client.<br>
	 *                    <br>
	 * @param optionsB    Options to write the file in the target Client. Check out
	 *                    the underlying File Client to review which options it may
	 *                    accept. This argument is not mandatory.<br>
	 *                    <br>
	 * @return Number of files and directories found and copied:
	 *         <code>int[]{files-found, directories-found, files-copied,
	 *         directories-copied}</code>. Checkout index for each value in the
	 *         array at <code>FileServiceConstants.RESPONSE_INDEX_xxx</code>
	 *         constants.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to copy the
	 *                          file.<br>
	 *                          <br>
	 */
	int[] copy(final String clientNameA, final String sourceA, final Map<String, Object> optionsA,
			final String clientNameB, final String targetB, final Map<String, Object> optionsB) throws ServiceException;

	/**
	 * Lists resources in a specific path.
	 * 
	 * @param clientName Name of the File Client where to list the resources.<br>
	 *                   <br>
	 * @param path       Where to list the resources in the file storage.<br>
	 *                   <br>
	 * @param options    Options to list the files in the Client. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @param orderBy    Criteria to sort the resources in the file storage. Use any
	 *                   of the <code>FileServiceConstants.ORDER_BY_xyz</code>
	 *                   constants to specify what to sort. Specific clients may
	 *                   accept custom criteria.<br>
	 *                   <br>
	 * @return A list with the resources found.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to list the
	 *                          resources.<br>
	 *                          <br>
	 */
	List<FileRef> list(final String clientName, final String path, final Map<String, Object> options,
			final String[] orderBy) throws ServiceException;

	/**
	 * Lists resources sorted by path name in a specific path.
	 * 
	 * @param clientName Name of the File Client where to list the resources.<br>
	 *                   <br>
	 * @param path       Where to list the resources in the file storage.<br>
	 *                   <br>
	 * @param desc       <code>true</code> to sort result in descending order.
	 *                   <code>false</code> or <code>null</code> to sort result in
	 *                   ascending order.<br>
	 *                   <br>
	 * @return A sorted list with the resources found.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to list the
	 *                          resources.<br>
	 *                          <br>
	 */
	public List<FileRef> listOrderByPath(final String clientName, final String path, final Boolean desc)
			throws ServiceException;

	/**
	 * Lists resources sorted by size in a specific path.
	 * 
	 * @param clientName Name of the File Client where to list the resources.<br>
	 *                   <br>
	 * @param path       Where to list the resources in the file storage.<br>
	 *                   <br>
	 * @param desc       <code>true</code> to sort result in descending order.
	 *                   <code>false</code> or <code>null</code> to sort result in
	 *                   ascending order.<br>
	 *                   <br>
	 * @return A sorted list with the resources found.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to list the
	 *                          resources.<br>
	 *                          <br>
	 */
	public List<FileRef> listOrderBySize(final String clientName, final String path, final Boolean desc)
			throws ServiceException;

	/**
	 * Lists resources sorted by last modified date in a specific path.
	 * 
	 * @param clientName Name of the File Client where to list the resources.<br>
	 *                   <br>
	 * @param path       Where to list the resources in the file storage.<br>
	 *                   <br>
	 * @param desc       <code>true</code> to sort result in descending order.
	 *                   <code>false</code> or <code>null</code> to sort result in
	 *                   ascending order.<br>
	 *                   <br>
	 * @return A sorted list with the resources found.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to list the
	 *                          resources.<br>
	 *                          <br>
	 */
	public List<FileRef> listOrderByLastModified(final String clientName, final String path, final Boolean desc)
			throws ServiceException;

	/**
	 * Gets a resource (file or directory) reference.
	 * 
	 * @param clientName Name of the File Client where to find the resource.<br>
	 *                   <br>
	 * @param path       Path to the resource in the file storage.<br>
	 *                   <br>
	 * @param options    Options to find the file in the Client. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return A reference to the resource.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to find the
	 *                          resource.<br>
	 *                          <br>
	 */
	FileRef find(final String clientName, final String path, final Map<String, Object> options) throws ServiceException;

	/**
	 * Validates if a directory exists.
	 * 
	 * @param clientName Name of the File Client where to validate if resource
	 *                   exists.<br>
	 *                   <br>
	 * @param path       Path to the resource in the file storage.<br>
	 *                   <br>
	 * @param options    Options to find the file in the Client. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return <code>true</code> if directory exists and <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to find the
	 *                          resource.<br>
	 *                          <br>
	 */
	boolean existsDir(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException;

	/**
	 * Validates if a file exists.
	 * 
	 * @param clientName Name of the File Client where to validate if resource
	 *                   exists.<br>
	 *                   <br>
	 * @param path       Path to the resource in the file storage.<br>
	 *                   <br>
	 * @param options    Options to find the file in the Client. Check out the
	 *                   underlying File Client to review which options it may
	 *                   accept. This argument is not mandatory.<br>
	 *                   <br>
	 * @return <code>true</code> if file exists and <code>false</code> if not.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to find the
	 *                          resource.<br>
	 *                          <br>
	 */
	boolean existsFile(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the base path defined for this Client.<br>
	 * <br>
	 * This method will return the value specified for parameter
	 * <code>AbstractFileClientConnector.PARAMETER_BASE_PATH</code> when Client was
	 * created.
	 * 
	 * @param clientName Name of the File Client where to get its base path.<br>
	 *                   <br>
	 * @return Base path to append before any given path and for every operation to
	 *         perform, or <code>null</code> if no base path was defined.<br>
	 *         <br>
	 * @throws ServiceException If Client does not exists.<br>
	 *                          <br>
	 */
	String getBasePath(final String clientName) throws ServiceException;

	/**
	 * Specifies if Client supports directories.
	 * 
	 * @param clientName Name of the File Client where to find the resource.<br>
	 *                   <br>
	 * @return <code>true</code> if directories are supportted.<br>
	 *         <br>
	 * @throws ServiceException If Client does not exists.<br>
	 *                          <br>
	 */
	boolean allowsDirectories(final String clientName) throws ServiceException;

	/**
	 * Specifies if Client requires the number of bytes when <code>write</code>
	 * operation is invoked.
	 * 
	 * @param clientName Name of the File Client where to validate if it requires
	 *                   write length.<br>
	 *                   <br>
	 * @return <code>true</code> if length is required in <code>write</code>
	 *         operation.<br>
	 *         <br>
	 * @throws ServiceException If Client does not exists.<br>
	 *                          <br>
	 */
	boolean requiresWriteLength(final String clientName) throws ServiceException;

}
