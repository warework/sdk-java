package com.warework.service.file.client;

import java.util.Date;

import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.connector.AmazonS3Connector;

/**
 * Amazon S3 reference wrapper.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class FileRefAmazonS3Impl extends AbstractFileRef {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a reference to an Amazon S3 file.
	 * 
	 * @param client  Client where this file exists.<br>
	 *                <br>
	 * @param source  Source Amazon S3 file to wrap.<br>
	 *                <br>
	 * @param orderBy Criteria to sort this file.<br>
	 *                <br>
	 */
	FileRefAmazonS3Impl(final FileClientFacade client, final S3ObjectSummary source, final String[] orderBy) {
		super(client, source, orderBy);
	}

	/**
	 * Creates a reference to an Amazon S3 common prefix (aka: directory).
	 * 
	 * @param client  Client where this file exists.<br>
	 *                <br>
	 * @param source  Source Amazon S3 common prefix to wrap.<br>
	 *                <br>
	 * @param orderBy Criteria to sort this file.<br>
	 *                <br>
	 */
	FileRefAmazonS3Impl(final FileClientFacade client, final String source, final String[] orderBy) {
		super(client, source, orderBy);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the path name for this file or directory.
	 * 
	 * @return The string form of this path name.<br>
	 *         <br>
	 */
	public String getPath() {
		if (getSource() instanceof S3ObjectSummary) {

			// Get a file reference.
			final S3ObjectSummary file = (S3ObjectSummary) getSource();

			// Return the name of the file.
			return updatePath(file.getKey());

		} else {
			return updatePath((String) getSource());
		}
	}

	/**
	 * Validates if this reference points to a file or a directory. As directories
	 * are not supported by Amazon S3, this method always returns
	 * <code>false</code>.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	public boolean isDirectory() {
		return (getSource() instanceof String);
	}

	/**
	 * Gets the size in bytes for the file represented by this reference.
	 * 
	 * @return File size in bytes. <code>-1</code> if reosurce points to an Amazon
	 *         S3 common prefix.<br>
	 *         <br>
	 */
	public long getSize() {
		if (getSource() instanceof S3ObjectSummary) {

			// Get a file reference.
			final S3ObjectSummary file = (S3ObjectSummary) getSource();

			// Return the file size.
			return file.getSize();

		} else {
			return -1;
		}
	}

	/**
	 * Gets a date that represents when the file was last modified.
	 * 
	 * @return Last modified date. This method returns <code>null</code> when target
	 *         file represents a directory.<br>
	 *         <br>
	 */
	public Date getLastModified() {

		// Validate source type.
		if (getSource() instanceof S3ObjectSummary) {

			// Get a file reference.
			final S3ObjectSummary file = (S3ObjectSummary) getSource();

			// Return the last modified date.
			return file.getLastModified();

		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the bucket where the reference for this file exists.
	 * 
	 * @return Name of the bucket.<br>
	 *         <br>
	 */
	public String getBucketName() {
		return getClient().getConnector().toString(AmazonS3Connector.PARAMETER_BUCKET_NAME);
	}

	/**
	 * Gets the ETag for this file.
	 * 
	 * @return File ETag.<br>
	 *         <br>
	 */
	public String getETag() {

		// Validate source type.
		if (getSource() instanceof S3ObjectSummary) {

			// Get a file reference.
			final S3ObjectSummary file = (S3ObjectSummary) getSource();

			// Return the ETag.
			return file.getETag();

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the owner of this file.
	 * 
	 * @return File owner.<br>
	 *         <br>
	 */
	public Owner getOwner() {

		// Validate source type.
		if (getSource() instanceof S3ObjectSummary) {

			// Get a file reference.
			final S3ObjectSummary file = (S3ObjectSummary) getSource();

			// Return the ETag.
			return file.getOwner();

		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Transform AWS S3 path to standard.
	 * 
	 * @param path Source path.<br>
	 *             <br>
	 * @return Updated path.<br>
	 *         <br>
	 */
	private String updatePath(final String path) {
		if (getClient().getConnector().isInitParameter(AmazonS3Connector.PARAMETER_PATH_TRANSFORM)) {
			if (getClient().getConnector().getInitParameter(AmazonS3Connector.PARAMETER_BASE_PATH) == null) {
				if (path.endsWith(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE)) {
					return FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + path.substring(0, path.length() - 1);
				} else if (path.lastIndexOf(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE) > path
						.lastIndexOf(ResourceL2Helper.FILE_EXTENSION_SEPARATOR)) {
					return FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + path
							+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR;
				} else {
					return FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + path;
				}
			} else {

				// Transform base path.
				String basePath = getClient().getConnector().toString(AmazonS3Connector.PARAMETER_BASE_PATH);
				if (basePath.endsWith(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE)) {
					basePath = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE
							+ basePath.substring(0, basePath.length() - 1);
				} else {
					basePath = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + basePath;
				}

				// Transform path.
				String updatedPath = null;
				if (path.endsWith(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE)) {
					updatedPath = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE
							+ path.substring(0, path.length() - 1);
				} else if (path.lastIndexOf(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE) > path
						.lastIndexOf(ResourceL2Helper.FILE_EXTENSION_SEPARATOR)) {
					updatedPath = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + path
							+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR;
				} else {
					updatedPath = FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE + path;
				}

				// Remove existing base path form path.
				return updatedPath.substring(basePath.length(), updatedPath.length());

			}
		} else if (getClient().getConnector().getInitParameter(AmazonS3Connector.PARAMETER_BASE_PATH) == null) {
			return path;
		} else {
			/*
			 * Remove existing base path form path.
			 */
			return path.substring(getClient().getConnector().toString(AmazonS3Connector.PARAMETER_BASE_PATH).length(),
					path.length());
		}
	}

}
