package com.warework.service.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.warework.core.service.AbstractProxyService;
import com.warework.core.service.ReplyCodeServiceException;
import com.warework.core.service.ServiceException;
import com.warework.core.service.UnsupportedOperationServiceException;
import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ReplyCodeClientException;
import com.warework.core.service.client.UnsupportedOperationClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.client.AbstractFileClient;
import com.warework.service.file.client.FileClientFacade;
import com.warework.service.log.LogServiceConstants;

/**
 * Implementation of the Service that performs operations with file systems.<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class FileServiceImpl extends AbstractProxyService implements FileServiceFacade {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

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
	public InputStream read(final String clientName, final String source, final Map<String, Object> options)
			throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot read the file in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to read file or directory '" + source + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// Read the file.
		try {
			return client.read(source, options);
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public boolean read(final String clientName, final String source, final Map<String, Object> options,
			final OutputStream target) throws ServiceException {

		/*
		 * As a general Java convention, streams provided by users should not be closed
		 * by invoked methods. As users create the streams, they have to decide what to
		 * do with them.
		 */

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot read the file in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to read file or directory '" + source + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// Read the file.
		try {
			return client.read(source, options, target);
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public String readAsText(final String clientName, final String source, final Map<String, Object> options)
			throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot read the file in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to read file or directory '" + source + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// Get the charset encoding.
		String charsetName = null;
		if ((options != null) && (options.containsKey(AbstractFileClient.OPERATION_OPTION_CHARSET_NAME))) {

			// Get the option value.
			final Object optionValue = options.get(AbstractFileClient.OPERATION_OPTION_CHARSET_NAME);

			// Validate option value.
			if (optionValue instanceof String) {
				charsetName = (String) optionValue;
			} else {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot read file in Client '" + clientName + "' at Service '" + getName()
								+ "' because given charset name is not a '" + String.class.getName() + "' value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			charsetName = CommonValueL1Constants.ENCODING_TYPE_UTF8;
		}

		// Get buffer size.
		int bufferSize = -1;
		if ((options != null) && (options.containsKey(AbstractFileClient.OPERATION_OPTION_BUFFER_SIZE))) {

			// Get the option value.
			final Object optionValue = options.get(AbstractFileClient.OPERATION_OPTION_BUFFER_SIZE);

			// Validate option value.
			if (optionValue instanceof String) {
				try {
					bufferSize = Integer.parseInt((String) optionValue);
				} catch (final NumberFormatException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot read file in Client '" + clientName + "' at Service '" + getName()
									+ "' because given buffer size as a '" + String.class.getName()
									+ "' value with option parameter '"
									+ AbstractFileClient.OPERATION_OPTION_BUFFER_SIZE + "' is not a parsable '"
									+ Integer.class.getName() + "'.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (optionValue instanceof Integer) {
				bufferSize = (Integer) optionValue;
			} else {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot read file in Client '" + clientName + "' at Service '" + getName()
								+ "' because given buffer size with option parameter '"
								+ AbstractFileClient.OPERATION_OPTION_BUFFER_SIZE + "' is not a '"
								+ String.class.getName() + "' or a " + Integer.class.getName() + " value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			bufferSize = AbstractFileClient.DEFAULT_BUFFER_SIZE;
		}

		// Load text file.
		InputStream file = null;
		try {

			// Load file.
			file = client.read(source, options);

			// Copy stream into a string.
			if (file == null) {
				return null;
			} else {
				return StringL1Helper.toString(file, charsetName, bufferSize);
			}

		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		} catch (final IOException e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot read file in Client '" + clientName + "' at Service '" + getName()
							+ "' because its content cannot be loaded into a '" + String.class.getName() + "'.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (final IOException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot read file in Client '" + clientName + "' at Service '" + getName()
									+ "' because the stream created to load the file cannot be closed.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}

	}

	/**
	 * Writes the content of a file.
	 * 
	 * @param clientName Name of the File Client where to write the file.<br>
	 *                   <br>
	 * @param source     Source file to read. <b>Stream is not closed</b> when
	 *                   operation is completed.<br>
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
	public boolean write(final String clientName, final InputStream source, final long size,
			final Map<String, Object> options, final String target) throws ServiceException {

		/*
		 * As a general Java convention, streams provided by users should not be closed
		 * by invoked methods. As users create the streams, they have to decide what to
		 * do with them.
		 */

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot write the file in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to write file or directory '" + target + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// Write the file.
		try {

			// Create directories first.
			if ((processRecursively(options)) && (client.allowsDirectories())) {

				// Get each directory to create.
				final List<String> chain = client.toPathChain(target);

				// Create full path and write file.
				if ((chain == null) || (chain.size() < 1)) {
					return false;
				} else if (chain.size() > 1) {

					// Create each directory found.
					final StringBuilder process = new StringBuilder();

					// Create directories.
					for (int index = 0; index < (chain.size() - 1); index = index + 1) {

						// Add directory to path.
						process.append(client.toDirectory(chain.get(index)));

						// Find directory to validate if it already exists.
						final FileRef dir = client.find(process.toString(), options);

						// Create directory.
						if ((dir == null) || (!dir.isDirectory())) {
							if (!client.makeDir(process.toString(), options)) {
								return false;
							}
						}

					}

				}

			}

			// Write file.
			return client.write(source, size, options, target);

		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public boolean write(final String clientName, final byte[] source, final Map<String, Object> options,
			final String target) throws ServiceException {
		InputStream stream = null;
		try {

			// Create input stream from source bytes.
			stream = new ByteArrayInputStream(source);

			// Write file.
			return write(clientName, stream, source.length, options, target);

		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (final IOException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot write text file in Client '" + clientName + "' at Service '" + getName()
									+ "' because stream created from given source bytes cannot be closed.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}
	}

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
	public boolean write(final String clientName, final String content, final Map<String, Object> options,
			final String target) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot write text file in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to write text file '" + target + "' in File Client '" + clientName
				+ "' of Service '" + getName() + "'.");

		// Get the charset encoding.
		String charsetName = null;
		if ((options != null) && (options.containsKey(AbstractFileClient.OPERATION_OPTION_CHARSET_NAME))) {

			// Get the option value.
			final Object optionValue = options.get(AbstractFileClient.OPERATION_OPTION_CHARSET_NAME);

			// Validate option value.
			if (optionValue instanceof String) {
				charsetName = (String) optionValue;
			} else {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot write text file in Client '" + clientName + "' at Service '" + getName()
								+ "' because given charset with option parameter '"
								+ AbstractFileClient.OPERATION_OPTION_CHARSET_NAME + "' is not a '"
								+ String.class.getName() + "' value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Write the file.
		LengthInputStream source = null;
		try {

			// Create directories first.
			if ((processRecursively(options)) && (client.allowsDirectories())) {

				// Get each directory to create.
				final List<String> chain = client.toPathChain(target);

				// Create full path and write file.
				if ((chain == null) || (chain.size() < 1)) {
					return false;
				} else if (chain.size() > 1) {

					// Create each directory found.
					final StringBuilder process = new StringBuilder();

					// Create each directory.
					for (int index = 0; index < (chain.size() - 1); index = index + 1) {

						// Add directory to path.
						process.append(client.toDirectory(chain.get(index)));

						// Find directory to validate if it already exists.
						final FileRef dir = client.find(process.toString(), options);

						// Create directory.
						if ((dir == null) || (!dir.isDirectory())) {
							if (!client.makeDir(process.toString(), options)) {
								return false;
							}
						}

					}

				}

			}

			// Get stream from source string.
			source = (LengthInputStream) StringL1Helper.toInputStream(content, charsetName);

			// Write text file.
			return client.write(source, source.getLength(), options, target);

		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot write text file in Client '" + clientName + "' at Service '" + getName()
							+ "' because given charset name " + charsetName + " to encode the string is unknown.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} finally {
			if (source != null) {
				try {
					source.close();
				} catch (final IOException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot write text file in Client '" + clientName + "' at Service '" + getName()
									+ "' because stream created from given string cannot be closed.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}

	}

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
	public boolean makeDir(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot make directory in File Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to create directory '" + path + "' in File Client '" + clientName
				+ "' of Service '" + getName() + "'.");

		// Make directory.
		try {
			if ((processRecursively(options)) && (client.allowsDirectories())) {

				// Get each directory to create.
				final List<String> chain = client.toPathChain(path);
				if ((chain == null) || (chain.size() < 1)) {
					return false;
				}

				// Create each directory found.
				final StringBuilder process = new StringBuilder();
				for (final Iterator<String> iterator = chain.iterator(); iterator.hasNext();) {

					// Add directory to path.
					process.append(client.toDirectory(iterator.next()));

					// Find directory to validate if it already exists.
					final FileRef dir = client.find(process.toString(), options);

					// Create directory.
					if ((dir == null) || (!dir.isDirectory())) {
						if (!client.makeDir(process.toString(), options)) {
							return false;
						}
					}

				}

				// At this point path was succesfully created.
				return true;

			} else {
				return client.makeDir(path, options);
			}
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public boolean delete(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot delete any files and/or directories in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Delete file or directory.
		try {
			if (processRecursively(options)) {

				// Log a message.
				getScopeFacade().debug("WAREWORK is going to recursively delete directory (or single file) '" + path
						+ "' in File Client '" + clientName + "' of Service '" + getName() + "'.");

				// First try to find the file or directory to delete.
				final FileRef resource = client.find(path, options);
				if (resource == null) {

					// Log message.
					getScopeFacade().warn("WAREWORK cannot delete file or directory '" + path + "' in Client '"
							+ client.getName() + "' at Service '" + getName()
							+ "' because it does not exists or cannot be found.");

					// Return operation result.
					return false;

				}

				// Files or directories to delete.
				final List<FileRef> resources = new ArrayList<FileRef>();

				// Delete directory tree.
				if (resource.isDirectory()) {
					toDirectoryResources(client, path, options, resources, true);
				}

				// Last resource to delete is root directory.
				resources.add(resource);

				// Delete files or directories.
				return client.delete(resources, options);

			} else {

				// Log a message.
				getScopeFacade().debug("WAREWORK is going to delete file or directory '" + path + "' in File Client '"
						+ clientName + "' of Service '" + getName() + "'.");

				// Delete file or directory.
				return client.delete(path, options);

			}
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public boolean delete(final String clientName, final Collection<FileRef> resources,
			final Map<String, Object> options) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot delete any files and/or directories in Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Delete all files and/or directories.
		if (resources.size() > 0) {
			try {
				return client.delete(resources, options);
			} catch (final ReplyCodeClientException e) {
				throw new ReplyCodeServiceException(e);
			} catch (final UnsupportedOperationClientException e) {
				throw new UnsupportedOperationServiceException(e);
			} catch (final ClientException e) {
				throw new ServiceException(e);
			}
		} else {

			// Log a message.
			getScopeFacade().info("WAREWORK did not delete any file in Client '" + clientName + "' at Service '"
					+ getName() + "' because given collection of files to remove is empty.");

			// Return operation result.
			return true;

		}

	}

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
	public boolean rename(final String clientName, final String source, final String newname) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot rename file or directory in File Client '" + clientName + "' at Service '"
							+ getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to rename file or directory '" + source + "' to '" + newname
				+ "' in File Client '" + clientName + "' of Service '" + getName() + "'.");

		// Rename file or directory.
		try {
			return client.rename(source, newname);
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public int[] copy(final String clientNameA, final String sourceA, final Map<String, Object> optionsA,
			final String clientNameB, final String targetB, final Map<String, Object> optionsB)
			throws ServiceException {

		// Get the first File Client.
		final FileClientFacade clientA = (FileClientFacade) getClient(clientNameA);
		if (clientA == null) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot copy '" + sourceA + "' from File Client '" + clientNameA + "' of Service '"
							+ getName() + "' to '" + targetB + "' in File Client '" + clientNameB + "' because Client '"
							+ clientNameA + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the second File Client.
		final FileClientFacade clientB = (FileClientFacade) getClient(clientNameB);
		if (clientB == null) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot copy '" + sourceA + "' from File Client '" + clientNameA + "' of Service '"
							+ getName() + "' to '" + targetB + "' in File Client '" + clientNameB + "' because Client '"
							+ clientB + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to copy '" + sourceA + "' from File Client '" + clientNameA
				+ "' of Service '" + getName() + "' to '" + targetB + "' in File Client '" + clientNameB + "'.");

		// Copy file or directory.
		try {

			// Find file or directory to copy.
			final FileRef source = clientA.find(sourceA, optionsA);

			// Setup response: files and directories found and copied.
			final int[] response = new int[] { 0, 0, 0, 0 };

			// Process file or directory..
			if (source != null) {
				if (source.isDirectory()) {
					if (processRecursively(optionsA)) {

						/*
						 * 1. Setup.
						 */

						// Get base path.
						final String basePath = source.getPath();

						// Decide if operation should continue when a failure is found.
						final boolean continueOnFailure = continueOnFailure(optionsB);

						// Operation result.
						boolean success = true;

						/*
						 * 2. Get all files and directories to copy.
						 */

						// Files or directories to delete.
						final List<FileRef> resources = new ArrayList<FileRef>();

						// First resource to copy is root directory.
						resources.add(source);

						// Directory tree to process.
						final int[] found = toDirectoryResources(clientA, sourceA, optionsA, resources, false);

						// Set files found.
						response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = found[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND];

						// Set directories found.
						response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = found[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND]
								+ 1;

						/*
						 * 3. Copy each file and directory found.
						 */
						for (final Iterator<FileRef> iterator = resources.iterator(); iterator.hasNext();) {

							// Get one file or directory to copy.
							final FileRef resource = iterator.next();

							// Get source file or directory path.
							final String sourcePath = resource.getPath();

							// Create target path where to copy the file or directory
							final String targetPath = targetB
									+ sourcePath.substring(basePath.length(), sourcePath.length());

							// Copy file or directory.
							if (resource.isDirectory()) {
								if (clientB.allowsDirectories()) {

									// Create directory.
									success = clientB.makeDir(targetPath, optionsB);

									// Increase counter of directories copied.
									response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED] = response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED]
											+ 1;

								}
							} else {

								// Copy file.
								success = copy(resource, optionsA, clientB, targetPath, optionsB);

								// Increase counter of directories copied.
								response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED] = response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED]
										+ 1;

							}

							// Checkout if operation should continue.
							if ((!success) && (!continueOnFailure)) {
								break;
							}

						}

						// 4. Return operation result.
						if (success) {
							getScopeFacade().info("WAREWORK successfully copied "
									+ response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED] + " files of "
									+ response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] + " and "
									+ response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED]
									+ " directories of "
									+ response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] + " from '"
									+ sourceA + "' in File Client '" + clientA.getName() + "' of Service '" + getName()
									+ "' to '" + targetB + "' in File Client '" + clientNameB + "'.");
						} else {
							getScopeFacade().warn("WAREWORK only copied "
									+ response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED] + " files of "
									+ response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] + " and "
									+ response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED]
									+ " directories of "
									+ response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] + " from '"
									+ sourceA + "' in File Client '" + clientA.getName() + "' of Service '" + getName()
									+ "' to '" + targetB + "' in File Client '" + clientNameB + "'.");
						}

						// Return operation result.
						return response;

					} else if (clientB.allowsDirectories()) {
						if (clientB.makeDir(targetB, optionsB)) {

							// Log message.
							getScopeFacade().info("WAREWORK successfully copied directory from '" + sourceA
									+ "' in File Client '" + clientA.getName() + "' of Service '" + getName() + "' to '"
									+ targetB + "' in File Client '" + clientNameB + "'.");

							// Set files found.
							response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = 1;

							// Set files copied.
							response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED] = 1;

						} else {

							// Log message.
							getScopeFacade().warn("WAREWORK cannot copy directory from '" + sourceA
									+ "' in File Client '" + clientA.getName() + "' of Service '" + getName() + "' to '"
									+ targetB + "' in File Client '" + clientNameB + "'.");

							// Set files found.
							response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = 1;

						}
					} else {

						// Log message.
						getScopeFacade().warn("WAREWORK cannot copy directory from '" + sourceA + "' in File Client '"
								+ clientA.getName() + "' of Service '" + getName()
								+ "' because directories cannot be created in '" + targetB + "' in File Client '"
								+ clientNameB + "'.");

						// Set files found.
						response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = 1;

					}
				} else if (copy(source, optionsA, clientB, targetB, optionsB)) {

					// Log message.
					getScopeFacade().info("WAREWORK successfully copied file from '" + sourceA + "' in File Client '"
							+ clientA.getName() + "' of Service '" + getName() + "' to '" + targetB
							+ "' in File Client '" + clientNameB + "'.");

					// Set files found.
					response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = 1;

					// Set files copied.
					response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED] = 1;

				} else {

					// Log message.
					getScopeFacade().warn("WAREWORK cannot copy file from '" + sourceA + "' in File Client '"
							+ clientA.getName() + "' of Service '" + getName() + "' to '" + targetB
							+ "' in File Client '" + clientNameB + "'.");

					// Set files found.
					response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = 1;

				}
			}

			// Return files and directories found and copied.
			return response;

		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(e);
		}

	}

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
	public List<FileRef> list(final String clientName, final String path, final Map<String, Object> options,
			final String[] orderBy) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot list the resources in File Client '" + clientName
							+ "' at Service '" + getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK is going to list files and directories from '" + path + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// List the resources.
		List<FileRef> result = null;
		try {
			if (processRecursively(options)) {

				// Files or directories to delete.
				result = new ArrayList<FileRef>();

				// Directory tree to process.
				toDirectoryResources(client, path, options, result, reverseTree(options));

			} else {
				result = client.list(path, options, orderBy);
			}
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot list the resources in File Client '" + clientName + "' at Service '" + getName()
							+ "' because Client reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Group resources and sort result.
		if ((result != null) && (result.size() > 0) && (orderBy != null) && (orderBy.length > 0)) {
			Collections.sort(result);
		}

		// Return resources found.
		return result;

	}

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
			throws ServiceException {
		return ((desc != null) && (desc))
				? list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_PATH_DESCENDING })
				: list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_PATH_ASCENDING });
	}

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
			throws ServiceException {
		return ((desc != null) && (desc))
				? list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_SIZE_DESCENDING })
				: list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_SIZE_ASCENDING });
	}

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
			throws ServiceException {
		return ((desc != null) && (desc))
				? list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_LAST_MODIFIED_DESCENDING })
				: list(clientName, path, null, new String[] { FileServiceConstants.ORDER_BY_LAST_MODIFIED_ASCENDING });
	}

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
	public FileRef find(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot find the resource in File Client '" + clientName
							+ "' at Service '" + getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().debug("WAREWORK will try to find file or directory at '" + path + "' in File Client '"
				+ clientName + "' of Service '" + getName() + "'.");

		// Find resource.
		try {
			return client.find(path, options);
		} catch (final ReplyCodeClientException e) {
			throw new ReplyCodeServiceException(e);
		} catch (final UnsupportedOperationClientException e) {
			throw new UnsupportedOperationServiceException(e);
		} catch (final ClientException e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot find file or directory in File Client '" + clientName + "' at Service '"
							+ getName() + "' because Client reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

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
	public boolean existsDir(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException {

		// Find resource (file or directory).
		final FileRef resource = find(clientName, path, options);

		// Validate resource exists and that it's a directory.
		return ((resource != null) && (resource.isDirectory()));

	}

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
	public boolean existsFile(final String clientName, final String path, final Map<String, Object> options)
			throws ServiceException {

		// Find resource (file or directory).
		final FileRef resource = find(clientName, path, options);

		// Validate resource exists and that it's a directory.
		return ((resource != null) && (!resource.isDirectory()));

	}

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
	public String getBasePath(final String clientName) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot get the base path for File Client '" + clientName
							+ "' at Service '" + getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return Client's base path.
		return client.getBasePath();

	}

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
	public boolean allowsDirectories(final String clientName) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot find the resource in File Client '" + clientName
							+ "' at Service '" + getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return if Client allows working with directories.
		return client.allowsDirectories();

	}

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
	public boolean requiresWriteLength(final String clientName) throws ServiceException {

		// Get the facade of the File Client.
		final FileClientFacade client = (FileClientFacade) getClient(clientName);
		if (client == null) {
			throw new ServiceException(
					getScopeFacade(), "WAREWORK cannot find the resource in File Client '" + clientName
							+ "' at Service '" + getName() + "' because Client does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return if Client requires write length.
		return client.requiresWriteLength();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get every file or directory that exists in a specific directory.
	 * 
	 * @param client  File Client where to delete the files and directories.<br>
	 *                <br>
	 * @param path    Target directory where to get the files and
	 *                subdirectories.<br>
	 *                <br>
	 * @param options Options to list the resources. Check out the underlying File
	 *                Client to review which options it may accept. This argument is
	 *                not mandatory.<br>
	 *                <br>
	 * @param found   Files or directories found in the directory.<br>
	 *                <br>
	 * @param reverse Tree form structure to create. <code>false</code> will create
	 *                a normal structure form and <code>true</code> a reverse
	 *                tree.<br>
	 *                <br>
	 * @throws ClientException If there is an error when trying to delete the files
	 *                         and directories.<br>
	 *                         <br>
	 */
	private int[] toDirectoryResources(final FileClientFacade client, final String path,
			final Map<String, Object> options, final List<FileRef> found, final boolean reverse)
			throws ClientException {

		// Get the resources.
		final List<FileRef> resources = client.list(path, options, null);

		// Setup response: number files and directories found.
		final int[] response = new int[] { 0, 0 };

		// Delete files and directories.
		if (resources != null) {
			for (final Iterator<FileRef> iterator = resources.iterator(); iterator.hasNext();) {

				// Gets the reference to the resource.
				final FileRef resource = iterator.next();

				// Add resource at this point to keep normal tree form.
				if (!reverse) {

					// Increase files and directories found.
					if (resource.isDirectory()) {
						response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND]
								+ 1;
					} else {
						response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND]
								+ 1;
					}

					// Add resource.
					found.add(resource);

				}

				// Process each file and subdirectory that exist in the current directory.
				if (resource.isDirectory()) {

					// Process directory resources.
					final int[] responseDir = toDirectoryResources(client, resource.getPath(), options, found, reverse);

					// Increase files found.
					response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND]
							+ responseDir[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND];

					// Increase files and directories found.
					response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND]
							+ responseDir[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND];

				}

				// Add resource at this point to keep reverse tree form.
				if (reverse) {

					// Increase files and directories found.
					if (resource.isDirectory()) {
						response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND]
								+ 1;
					} else {
						response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] = response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND]
								+ 1;
					}

					// Add resource.
					found.add(resource);

				}

			}
		}

		// Return number files and directories found.
		return response;

	}

	/**
	 * Copies one file.
	 * 
	 * @param source   File from source Client.
	 * @param optionsA
	 * @param optionsA Options to read the file in the Source Client. Check out the
	 *                 underlying File Client to review which options it may accept.
	 *                 This argument is not mandatory.<br>
	 *                 <br>
	 * @param clientB  Target File Client where to write the file.<br>
	 *                 <br>
	 * @param targetB  Where to copy the content of the source file in the target
	 *                 File Client.<br>
	 *                 <br>
	 * @param optionsB Options to write the file in the target Client. Check out the
	 *                 underlying File Client to review which options it may accept.
	 *                 This argument is not mandatory.<br>
	 *                 <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to copy the
	 *                         file.<br>
	 *                         <br>
	 */
	private boolean copy(final FileRef source, final Map<String, Object> optionsA, final FileClientFacade clientB,
			final String targetB, final Map<String, Object> optionsB) throws ClientException {

		/*
		 * There's no need to validate source file/directory exists because it's a file
		 * reference object (so resource already exists).
		 */

		// Load file from source.
		final InputStream input = source.getInputStream(optionsA);

		// Copy file.
		if (input == null) {
			return false;
		} else {
			try {

				// Copy file with file length.
				if (input instanceof LengthInputStream) {

					// Get source stream.
					final LengthInputStream stream = (LengthInputStream) input;

					// Write file or directory into target.
					if (stream.getLength() >= 0) {
						return clientB.write(stream, stream.getLength(), optionsB, targetB);
					}

				}

				// Copy file without file length.
				if (clientB.requiresWriteLength()) {

					// Log message.
					getScopeFacade().warn("WAREWORK cannot copy '" + source.getPath() + "' from File Client '"
							+ source.getClientName() + "' of Service '" + getName() + "' to '" + targetB
							+ "' in File Client '" + clientB.getName()
							+ "' because file size is unknown and it's required by '" + clientB.getName()
							+ "' to be known in advance. You'll need to copy this file in a temporary storage using 'read' and 'write' methods separately.");

					// Cannot copy file or directory.
					return false;

				} else {
					return clientB.write(input, -1, optionsB, targetB);
				}

			} finally {
				try {
					input.close();
				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot copy '" + source.getPath() + "' from File Client '"
									+ source.getClientName() + "' of Service '" + getName() + "' to '" + targetB
									+ "' in File Client '" + clientB.getName()
									+ "' because the following I/O exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Decides if an operation should process subdirectories.
	 * 
	 * @param options Operation options.<br>
	 *                <br>
	 * @return <code>true</code> if subdirectories should be processed,
	 *         <code>false</code> if not.<br>
	 *         <br>
	 */
	private boolean processRecursively(final Map<String, Object> options) {
		return validateBoolean(options, AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY);
	}

	/**
	 * Decides if an operation should process subdirectories.
	 * 
	 * @param options Operation options.<br>
	 *                <br>
	 * @return <code>true</code> if subdirectories should be processed,
	 *         <code>false</code> if not.<br>
	 *         <br>
	 */
	private boolean reverseTree(final Map<String, Object> options) {
		return validateBoolean(options, AbstractFileClient.OPERATION_OPTION_REVERSE_TREE);
	}

	/**
	 * Decides if operation should continue when a failure is found.
	 * 
	 * @param options Operation options.<br>
	 *                <br>
	 * @return <code>true</code> if operation should continue, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 */
	private boolean continueOnFailure(final Map<String, Object> options) {
		return validateBoolean(options, AbstractFileClient.OPERATION_OPTION_CONTINUE_ON_FAILURE);
	}

	/**
	 * Validates a boolean operation option.
	 * 
	 * @param options    Operation options.<br>
	 *                   <br>
	 * @param optionName Name of the option to validate.<br>
	 *                   <br>
	 * @return <code>true</code> option is validated <code>false</code> if not.<br>
	 *         <br>
	 */
	private boolean validateBoolean(final Map<String, Object> options, final String optionName) {

		// Decide if the operation should be validated.
		if ((options != null) && (options.containsKey(optionName))) {

			// Get the value of the parameter.
			final Object option = options.get(optionName);

			// Return the value of the parameter.
			if (option instanceof Boolean) {
				return (Boolean) option;
			} else if (option instanceof String) {
				return ((String) option).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE);
			}

		}

		// At this point, the operation is validated.
		return false;

	}

}
