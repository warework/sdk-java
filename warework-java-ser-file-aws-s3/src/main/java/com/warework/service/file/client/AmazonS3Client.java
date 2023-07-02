package com.warework.service.file.client;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ReplyCodeClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.core.util.io.LengthInputStream;
import com.warework.service.file.FileRef;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.connector.AmazonS3Connector;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * @author jschiaffino
 *
 */
public final class AmazonS3Client extends AbstractFileClient {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "aws-s3-client";

	/*
	 * EXECUTABLE OPTIONS
	 */

	/**
	 * Operation parameter that specifies an expiration date to create a pre-signed
	 * URL for a write operation. This will allow you to upload an object using a
	 * pre-signed URL (anyone with the pre-signed URL can upload an object). The
	 * upload creates an object or replaces any existing object with the same key
	 * that is specified in the pre-signed URL. Values for this parameter can be
	 * <code>java.util.Date</code> or <code>java.lang.String</code> (with date
	 * format 'yyyy/MM/dd HH:mm:ss') objects.
	 */
	public static final String EXECUTE_OPTION_CREATE_WRITE_PRESIGNED_URL = "write-presigned-url";

	/**
	 * Operation parameter that specifies an expiration date to create a pre-signed
	 * URL for a read operation. This will allow you to download an object using a
	 * pre-signed URL (anyone with the pre-signed URL can download an object).
	 * Values for this parameter can be <code>java.util.Date</code> or
	 * <code>java.lang.String</code> (with date format 'yyyy/MM/dd HH:mm:ss')
	 * objects.
	 */
	public static final String EXECUTE_OPTION_CREATE_READ_PRESIGNED_URL = "read-presigned-url";

	/**
	 * Operation parameter that specifies an expiration date to create a pre-signed
	 * URL for a delete operation. This will allow you to delete an object using a
	 * pre-signed URL (anyone with the pre-signed URL can delete the object). Values
	 * for this parameter can be <code>java.util.Date</code> or
	 * <code>java.lang.String</code> (with date format 'yyyy/MM/dd HH:mm:ss')
	 * objects.
	 */
	public static final String EXECUTE_OPTION_CREATE_DELETE_PRESIGNED_URL = "delete-presigned-url";

	/*
	 * OPERATION OPTIONS
	 */

	// COMMON

	/**
	 * Operation parameter that specifies the Cache-Control HTTP header which allows
	 * the user to specify caching behavior along the HTTP request/reply chain.<br>
	 * <br>
	 * For more information on how the Cache-Control HTTP header affects HTTP
	 * requests and responses see
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9"
	 * >http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9</a>.
	 */
	public static final String OPERATION_OPTION_CACHE_CONTROL = "cache-control";

	/**
	 * Operation parameter that specifies the optional Content-Disposition HTTP
	 * header, which specifies presentational information such as the recommended
	 * filename for the object to be saved as.<br>
	 * <br>
	 * For more information on how the Content-Disposition header affects HTTP
	 * client behavior, see
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1"
	 * >http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1</a>.
	 */
	public static final String OPERATION_OPTION_CONTENT_DISPOSITION = "content-disposition";

	/**
	 * Operation parameter that specifies the optional Content-Encoding HTTP header
	 * specifying what content encodings have been applied to the object and what
	 * decoding mechanisms must be applied in order to obtain the media-type
	 * referenced by the Content-Type field.<br>
	 * <br>
	 * For more information on how the Content-Encoding HTTP header works, see
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11"
	 * >http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>.
	 */
	public static final String OPERATION_OPTION_CONTENT_ENCODING = "content-encoding";

	/**
	 * Operation parameter that specifies the Content-Type HTTP header indicating
	 * the type of content stored in the associated object. The value of this header
	 * is a standard MIME type.<br>
	 * <br>
	 * When uploading files, the AWS S3 Java client will attempt to determine the
	 * correct content type if one hasn't been set yet. Users are responsible for
	 * ensuring a suitable content type is set when uploading streams. If no content
	 * type is provided and cannot be determined by the filename, the default
	 * content type "application/octet-stream" will be used.<br>
	 * <br>
	 * For more information on the Content-Type header, see
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17"
	 * >http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>.
	 */
	public static final String OPERATION_OPTION_CONTENT_LENGTH = "content-length";

	/**
	 * Operation parameter that specifies the Content-Type HTTP header indicating
	 * the type of content stored in the associated object. The value of this header
	 * is a standard MIME type.<br>
	 * <br>
	 * When uploading files, the AWS S3 Java client will attempt to determine the
	 * correct content type if one hasn't been set yet. Users are responsible for
	 * ensuring a suitable content type is set when uploading streams. If no content
	 * type is provided and cannot be determined by the filename, the default
	 * content type "application/octet-stream" will be used.<br>
	 * <br>
	 * For more information on the Content-Type header, see
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17"
	 * >http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>.
	 */
	public static final String OPERATION_OPTION_CONTENT_TYPE = "content-type";

	// READ OPTIONS

	/**
	 * Read operation parameter that specifies an optional modified constraint that
	 * restricts a request to executing only if the object has been modified after
	 * the specified date. Use this parameter only for <code>read</code> operations.
	 * Values for this parameter can be <code>java.util.Date</code> or
	 * <code>java.lang.String</code> (with date format 'yyyy/MM/dd HH:mm:ss')
	 * objects.
	 */
	public static final String OPERATION_OPTION_READ_MODIFIED_SINCE = "modified-since";

	/**
	 * Read operation parameter that specifies an optional unmodified constraint
	 * that restricts a request to executing only if the object has NOT been
	 * modified after the specified date. Use this parameter only for
	 * <code>read</code> operations. Values for this parameter can be
	 * <code>java.util.Date</code> or <code>java.lang.String</code> (with date
	 * format 'yyyy/MM/dd HH:mm:ss') objects.
	 */
	public static final String OPERATION_OPTION_READ_UNMODIFIED_SINCE = "unmodified-since";

	/**
	 * Read operation parameter that specifies the beginning of a byte range within
	 * the desired object that will be downloaded by one request. Use this parameter
	 * only for <code>read</code> operations. When specifying this byte range, this
	 * parameter defaults to 0 if it is not provided because the first byte in an
	 * object has position 0; as an example, the first ten bytes of an object can be
	 * downloaded by specifying a range of 0 to 9. Values for this parameter can be
	 * <code>java.lang.Long</code> or <code>java.lang.String</code> objects. If no
	 * byte range is specified, this request downloads the entire object from Amazon
	 * S3.
	 */
	public static final String OPERATION_OPTION_READ_RANGE_START = "range-start";

	/**
	 * Read operation parameter that specifies the end of a byte range within the
	 * desired object that will be downloaded by one request. Use this parameter
	 * only for <code>read</code> operations. Values for this parameter can be
	 * <code>java.lang.Long</code> or <code>java.lang.String</code> objects. For
	 * example, the first ten bytes of an object can be downloaded by specifying a
	 * range of 0 to 9. If no byte range is specified, this request downloads the
	 * entire object from Amazon S3.
	 */
	public static final String OPERATION_OPTION_READ_RANGE_END = "range-end";

	/**
	 * Read operation parameter that specifies if the requester is charged for
	 * downloading the data from the bucket. This parameter is used for downloading
	 * an Amazon S3 Object from a Requester Pays Bucket. If a bucket is enabled for
	 * Requester Pays, then any attempt to read an object from it without Requester
	 * Pays enabled will result in a 403 error and the bucket owner will be charged
	 * for the request. Enabling Requester Pays disables the ability to have
	 * anonymous access to this bucket. Use this parameter only for
	 * <code>read</code> operations. Values for this parameter can be
	 * <code>true</code> or <code>false</code> as boolean or string objects.
	 */
	public static final String OPERATION_OPTION_READ_REQUESTER_PAYS = "requester-pays";

	/**
	 * Read operation parameter that specifies which version of the object to
	 * download. If not specified, the most recent version will be downloaded.
	 * Objects created before versioning was enabled or when versioning is suspended
	 * are given the default <code>null</code> version ID (see AWS SDK
	 * <code>Constants.NULL_VERSION_ID</code>). Note that the <code>null</code>
	 * version ID is a valid version ID and is not the same as not having a version
	 * ID. Use this parameter only for <code>read</code> operations. This parameter
	 * must be a <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_READ_VERSION_ID = "version-id";

	/**
	 * Read operation parameter that specifies the ETag constraint that when present
	 * must include a match for the object's current ETag in order for the request
	 * to be executed. If specified ETag does not match the object's current ETag,
	 * this request will not be executed. Use this parameter only for
	 * <code>read</code> operations. This parameter must be a
	 * <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_READ_MATCH_ETAG = "match-etag";

	/**
	 * Read operation parameter that specifies the ETag constraint that when present
	 * must NOT include a match for the object's current ETag in order for this
	 * request to be executed. If specified ETag matches the object's current ETag,
	 * this request will not be executed. Use this parameter only for
	 * <code>read</code> operations. This parameter must be a
	 * <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_READ_NONMATCH_ETAG = "nonmatch-etag";

	/**
	 * Operation parameter that specifies the content language response header
	 * override. Use this parameter only for <code>read</code> operations. This
	 * parameter must be a <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_READ_CONTENT_LANGUAGE = "content-language";

	/**
	 * Operation parameter that specifies the expires response header override. Use
	 * this parameter only for <code>read</code> operations. This parameter must be
	 * a <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_READ_EXPIRES = "expires";

	// WRITE OPTIONS

	/**
	 * Write operation parameter that specifies the <a href=
	 * "http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/model/BucketLifecycleConfiguration.html"
	 * >BucketLifecycleConfiguration</a> rule ID for this object's expiration, or
	 * <code>null</code> if it doesn't expire. Use this parameter only for
	 * <code>write</code> operations. This parameter must be a
	 * <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID = "expiration-time-rule-id";

	/**
	 * Write operation parameter that specifies the date when the object is no
	 * longer cacheable. Use this parameter only for <code>write</code> operations.
	 * Values for this parameter can be <code>java.util.Date</code> or
	 * <code>java.lang.String</code> (with date format 'yyyy/MM/dd HH:mm:ss')
	 * objects.
	 */
	public static final String OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE = "http-expires-date";

	/**
	 * Write operation parameter that specifies the server-side encryption algorithm
	 * when encrypting the object using AWS-managed keys. Use this parameter only
	 * for <code>write</code> operations. This parameter must be a
	 * <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM = "sse-algorithm";

	/**
	 * Write operation parameter that specifies the base64 encoded 128-bit MD5
	 * digest of the associated object (content - not including headers) according
	 * to RFC 1864. This data is used as a message integrity check to verify that
	 * the data received by Amazon S3 is the same data that the caller sent. If set
	 * to <code>null</code>,then the MD5 digest is removed from the metadata. This
	 * field represents the base64 encoded 128-bit MD5 digest digest of an object's
	 * content as calculated on the caller's side. The ETag metadata field
	 * represents the hex encoded 128-bit MD5 digest as computed by Amazon S3. The
	 * AWS S3 Java client will attempt to calculate this field automatically when
	 * uploading files to Amazon S3. Use this parameter only for <code>write</code>
	 * operations. This parameter must be a <code>java.lang.String</code> object.
	 */
	public static final String OPERATION_OPTION_WRITE_CONTENT_MD5 = "content-md5";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Writes the content of a file.
	 * 
	 * @param source  Source file to read. Input stream is not closed when operation
	 *                is successfully completed.<br>
	 *                <br>
	 * @param size    Bytes of the source file.<br>
	 *                <br>
	 * @param options Options to write the file. This argument is not mandatory.<br>
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
	public boolean write(final InputStream source, final long size, final Map<String, Object> options,
			final String target) throws ClientException {
		try {

			/*
			 * As a general Java convention, streams provided by users should not be closed
			 * by invoked methods. As users create the streams, they have to decide what to
			 * do with them.
			 */

			// Validate options.
			validateWriteOptions(options);

			// Create the object metadata.
			final ObjectMetadata metadata = new ObjectMetadata();

			// Set object length.
			metadata.setContentLength(size);

			// Update object metadata.
			updateObjectMetadata(metadata, options);

			// Get AWS S3 target path.
			final String targerPath = updatePath(target);

			// Get the name of the file from the target path.
			final String fileName = getFileName(targerPath);

			// Set target file content type.
			if ((fileName != null) && ((metadata.getContentType() == null)
					|| (metadata.getContentType().equals(CommonValueL2Constants.STRING_EMPTY)))) {
				metadata.setContentType(ResourceL2Helper.getContentType(fileName));
			}

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Upload object.
			connection.putObject(new PutObjectRequest(getBucketName(), targerPath, source, metadata));

			// Log a message.
			getScopeFacade().info("WAREWORK successfully created file '" + target + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot create file '" + target + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot create file '" + target + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * This operation is not supported by Amazon S3.
	 * 
	 * @param path    Not used.<br>
	 *                <br>
	 * @param options Not used.<br>
	 *                <br>
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	public boolean makeDir(final String path, final Map<String, Object> options) {

		/*
		 * There's no need to throw an exception here as this operation is not mandatory
		 * to create a directory.
		 */

		// Log message.
		getScopeFacade().warn("WAREWORK cannot create directory '" + path + "' in Client '" + getName()
				+ "' at Service '" + getService().getName()
				+ "' because this operation is not required by Amazon S3. To make directories in Amazon S3 simply store your object using the '/' character.");

		// Operation is not supported by Amazon S3.
		return false;

	}

	/**
	 * Deletes a single object.
	 * 
	 * @param resource Object to delete.<br>
	 *                 <br>
	 * @param options  Options to delete the object. This argument is not
	 *                 mandatory.<br>
	 *                 <br>
	 * @return <code>true</code> if successfully completed, <code>false</code> if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         file.<br>
	 *                         <br>
	 */
	public boolean delete(final String resource, final Map<String, Object> options) throws ClientException {
		try {

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Configure request.
			final DeleteObjectRequest request = new DeleteObjectRequest(getBucketName(), updatePath(resource));

			// Delete object.
			connection.deleteObject(request);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully deleted file '" + resource + "' in File Client '" + getName()
					+ "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot delete file '" + resource + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete file '" + resource + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

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
	public boolean delete(final Collection<FileRef> resources, final Map<String, Object> options)
			throws ClientException {
		try {

			/*
			 * http://docs.aws.amazon.com/AmazonS3/latest/dev/
			 * DeletingMultipleObjectsUsingJava.html
			 */

			// Create a list with the resources to delete.
			final List<KeyVersion> keys = new ArrayList<KeyVersion>();
			for (final Iterator<FileRef> iterator = resources.iterator(); iterator.hasNext();) {
				keys.add(new KeyVersion(updatePath(iterator.next().getPath())));
			}

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Create the request.
			final DeleteObjectsRequest request = new DeleteObjectsRequest(getBucketName());

			// Set the objects to delete.
			request.setKeys(keys);

			// Delete objects.
			final DeleteObjectsResult response = connection.deleteObjects(request);

			// Count deleted files.
			final int deleted = response.getDeletedObjects().size();

			// Validate operation.
			final boolean success = ((response.getDeletedObjects() != null) && (deleted == resources.size()));

			// Log a message.
			if (success) {
				getScopeFacade().info("WAREWORK successfully deleted '" + deleted + "' files in File Client '"
						+ getName() + "' of Service '" + getService().getName() + "'.");
			} else if (deleted > 0) {
				getScopeFacade().warn("WAREWORK only deleted '" + deleted + "' files in Client '" + getName()
						+ "' at Service '" + getService().getName() + "'.");
			} else {
				getScopeFacade().warn("WAREWORK cannot delete any file in Client '" + getName() + "' at Service '"
						+ getService().getName() + "'.");
			}

			// Return operation result.
			return success;

		} catch (final MultiObjectDeleteException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot delete all files in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot delete files in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete files in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Renames a file.
	 * 
	 * @param source  File to change the name in the File Client.<br>
	 *                <br>
	 * @param newname New name for the source file.<br>
	 *                <br>
	 * @return Always <code>true</code> if successfully completed, an exception if
	 *         not.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         files.<br>
	 *                         <br>
	 */
	public boolean rename(final String source, final String newname) throws ClientException {
		try {

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Get the nmae of the S3 bucket.
			final String bucketName = getBucketName();

			// Update source path.
			final String updatedSource = updatePath(source);

			// Configure copy request.
			final CopyObjectRequest copyRequest = new CopyObjectRequest(bucketName, updatedSource, bucketName,
					updatePath(newname));

			// Copy object.
			connection.copyObject(copyRequest);

			// Configure delete request.
			final DeleteObjectRequest deleteRequest = new DeleteObjectRequest(getBucketName(), updatedSource);

			// Delete object.
			connection.deleteObject(deleteRequest);

			// Log a message.
			getScopeFacade().info("WAREWORK successfully renamed file and/or directory '" + source + "' to '" + newname
					+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");

			// Return operation result.
			return true;

		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot rename file/directory in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot rename file/directory in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Lists objects in a specific path.
	 * 
	 * @param path    Where to list the resources in the file storage.<br>
	 *                <br>
	 * @param options Options to list the files. This argument is not mandatory.<br>
	 *                <br>
	 * @param orderBy Criteria to sort the resources in the file storage. Use any of
	 *                the <code>FileServiceConstants.ORDER_BY_xyz</code> constants
	 *                to specify what to sort. <br>
	 *                <br>
	 */
	public List<FileRef> list(final String path, final Map<String, Object> options, final String[] orderBy)
			throws ClientException {
		try {

			// Get the name of the bucket.
			final String bucketName = getBucketName();

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Store file references here.
			final List<FileRef> files = new ArrayList<FileRef>();

			// Get AWS path (transform given path if required).
			final String target = updatePath(path);

			// Configure request.
			final ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucketName).withPrefix(target)
					.withDelimiter(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE);

			// Execute request.
			ObjectListing objectListing = null;
			do {

				// List objects.
				objectListing = connection.listObjects(request);

				// Process result.
				if (objectListing != null) {

					// Wrap each S3 object that represents a directory.
					for (final String commonPrefix : objectListing.getCommonPrefixes()) {
						files.add(new FileRefAmazonS3Impl(this, commonPrefix, orderBy));
					}

					// Wrap each S3 object that represents a file.
					for (final S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
						if (!objectSummary.getKey().equals(target)) {
							files.add(new FileRefAmazonS3Impl(this, objectSummary, orderBy));
						}
					}

					// Set marker indicating where in the bucket to begin listing.
					request.setMarker(objectListing.getNextMarker());

				}

			} while ((objectListing != null) && (objectListing.isTruncated()));

			// Log a message.
			getScopeFacade().info("WAREWORK successfully listed files and/or directories from '" + path
					+ "' in File Client '" + getName() + "' of Service '" + getService().getName() + "'.");

			// Return files found.
			return files;

		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot list files in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot list files in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

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
	public String toDirectory(final String name) {
		return name + getDirChar();
	}

	/**
	 * Specifies if Client supports directories.
	 * 
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	public boolean allowsDirectories() {
		return false;
	}

	/**
	 * Specifies if Client requires the number of bytes when <code>write</code>
	 * operation is invoked.
	 * 
	 * @return Always <code>true</code>.<br>
	 *         <br>
	 */
	public boolean requiresWriteLength() {
		return true;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a file or directory as an input stream.
	 * 
	 * @param source  File to read in the File Client.<br>
	 *                <br>
	 * @param options Options to read the file. Check out the underlying File Client
	 *                to review which options it may accept. This argument is not
	 *                mandatory.<br>
	 *                <br>
	 * @param log     Flag to log if operation is successfull.<br>
	 *                <br>
	 * @return Stream to read the file or directory or <code>null</code> if file
	 *         does not exists.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to read the
	 *                         file.<br>
	 *                         <br>
	 */
	protected InputStream performRead(final String source, final Map<String, Object> options, final boolean log)
			throws ClientException {
		try {

			// Validate options.
			validateReadOptions(options);

			// Get the connection with the File Client.
			final AmazonS3 connection = (AmazonS3) getConnection();

			// Create request.
			final GetObjectRequest request = updateRequest(new GetObjectRequest(getBucketName(), updatePath(source)),
					options);

			// Get the response header attributes.
			request.setResponseHeaders(createResponseHeader(options));

			// Get the S3 object reference.
			final S3Object object = connection.getObject(request);

			// Return stream.
			if (object == null) {

				// Log a message.
				getScopeFacade().warn("WAREWORK cannot load file '" + source + "' in File Client '" + getName()
						+ "' of Service '" + getService().getName()
						+ "' because AWS S3 request constraints were specified but not met (for example: object is not found).");

				// Nothing to return.
				return null;

			} else {

				// Get input stream.
				final InputStream input = object.getObjectContent();

				// Log a message.
				if (log) {
					getScopeFacade().info("WAREWORK successfully loaded file '" + source + "' in File Client '"
							+ getName() + "' of Service '" + getService().getName() + "'.");
				}

				// Return operation result.
				return new LengthInputStream(input, object.getObjectMetadata().getContentLength());

			}

		} catch (final AmazonServiceException e) {
			throw new ReplyCodeClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' because AWS S3 service was not able to process the request: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN, e.getStatusCode(), e.getErrorCode());
		} catch (final SdkClientException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot read file '" + source + "' in File Client '" + getName() + "' at Service '"
							+ getService().getName() + "' due to the following AWS S3 service problem: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the File Client.
	 */
	protected void close() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the bucket.
	 * 
	 * @return Bucket name.<br>
	 *         <br>
	 */
	private String getBucketName() {
		return getConnector().toString(AmazonS3Connector.PARAMETER_BUCKET_NAME);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates response options.
	 * 
	 * @param options Options to validate.<br>
	 *                <br>
	 * @throws ClientException
	 */
	private void validateCommonOptions(final Map<String, Object> options) throws ClientException {

		/*
		 * RESPONSE HEADERS
		 */

		// Validate option.
		if (options.containsKey(OPERATION_OPTION_CACHE_CONTROL)) {

			// Get the value of the option.
			final Object optionValue = options.get(OPERATION_OPTION_CACHE_CONTROL);

			// Validate option value.
			if (!(optionValue instanceof String)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given '" + OPERATION_OPTION_CACHE_CONTROL
								+ "' option is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Validate option.
		if (options.containsKey(OPERATION_OPTION_CONTENT_DISPOSITION)) {

			// Get the value of the option.
			final Object optionValue = options.get(OPERATION_OPTION_CONTENT_DISPOSITION);

			// Validate option value.
			if (!(optionValue instanceof String)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given '" + OPERATION_OPTION_CONTENT_DISPOSITION
								+ "' option is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Validate option.
		if (options.containsKey(OPERATION_OPTION_CONTENT_ENCODING)) {

			// Get the value of the option.
			final Object optionValue = options.get(OPERATION_OPTION_CONTENT_ENCODING);

			// Validate option value.
			if (!(optionValue instanceof String)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given '" + OPERATION_OPTION_CONTENT_ENCODING
								+ "' option is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Validate option.
		if (options.containsKey(OPERATION_OPTION_CONTENT_TYPE)) {

			// Get the value of the option.
			final Object optionValue = options.get(OPERATION_OPTION_CONTENT_TYPE);

			// Validate option value.
			if (!(optionValue instanceof String)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
								+ getService().getName() + "' because given '" + OPERATION_OPTION_CONTENT_TYPE
								+ "' option is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

	/**
	 * Validates read options.
	 * 
	 * @param options Options to validate.<br>
	 *                <br>
	 * @throws ClientException
	 */
	private void validateReadOptions(final Map<String, Object> options) throws ClientException {

		// Validate common operation parameters.
		super.validate(options);

		// Validate specific operation parameters.
		if ((options != null) && (options.size() > 0)) {

			/*
			 * COMMON
			 */

			// Validate common options.
			validateCommonOptions(options);

			/*
			 * READ OPTIONS
			 */

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_MODIFIED_SINCE)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_MODIFIED_SINCE);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Date)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_READ_MODIFIED_SINCE
									+ "' option is not a date or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_UNMODIFIED_SINCE)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_UNMODIFIED_SINCE);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Date)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_READ_UNMODIFIED_SINCE
									+ "' option is not a date or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_RANGE_START)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_RANGE_START);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Long)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_RANGE_START
									+ "' option is not a long or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_RANGE_END)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_RANGE_END);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Long)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_RANGE_END
									+ "' option is not a long or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_REQUESTER_PAYS)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_REQUESTER_PAYS);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Boolean)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_READ_REQUESTER_PAYS
									+ "' option is not a boolean or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_VERSION_ID)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_VERSION_ID);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_VERSION_ID
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_MATCH_ETAG)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_MATCH_ETAG);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_MATCH_ETAG
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_NONMATCH_ETAG)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_NONMATCH_ETAG);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_NONMATCH_ETAG
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			/*
			 * RESPONSE HEADERS
			 */

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_CONTENT_LANGUAGE)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_CONTENT_LANGUAGE);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_READ_CONTENT_LANGUAGE + "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_READ_EXPIRES)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_EXPIRES);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_READ_EXPIRES
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}

	}

	/**
	 * Validates write options.
	 * 
	 * @param options Options to validate.<br>
	 *                <br>
	 * @throws ClientException
	 */
	private void validateWriteOptions(final Map<String, Object> options) throws ClientException {
		if ((options != null) && (options.size() > 0)) {

			/*
			 * COMMON
			 */

			// Validate common options.
			validateCommonOptions(options);

			/*
			 * WRITE OPTIONS
			 */

			/*
			 * RESPONSE HEADERS
			 */

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE);

				// Validate option value.
				if (!(optionValue instanceof String) && !(optionValue instanceof Date)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE
									+ "' option is not a date or a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '"
									+ OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate option.
			if (options.containsKey(OPERATION_OPTION_WRITE_CONTENT_MD5)) {

				// Get the value of the option.
				final Object optionValue = options.get(OPERATION_OPTION_WRITE_CONTENT_MD5);

				// Validate option value.
				if (!(optionValue instanceof String)) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the operation with File Client '" + getName() + "' at Service '"
									+ getService().getName() + "' because given '" + OPERATION_OPTION_WRITE_CONTENT_MD5
									+ "' option is not a string value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the response header attributes.
	 * 
	 * @param options Options where to retrieve the response header attributes.<br>
	 *                <br>
	 * @return Response header attributes or <code>null</code> if no response header
	 *         attributes exists.<br>
	 *         <br>
	 */
	private ResponseHeaderOverrides createResponseHeader(final Map<String, Object> options) {

		// Response header attributes.
		ResponseHeaderOverrides responseHeaders = null;
		if ((options != null) && (options.size() > 0)) {

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_ENCODING)) {

				// Create the response header.
				responseHeaders = new ResponseHeaderOverrides();

				// Set header.
				responseHeaders.setContentEncoding((String) options.get(OPERATION_OPTION_CONTENT_ENCODING));

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_TYPE)) {

				// Create the response header.
				if (responseHeaders == null) {
					responseHeaders = new ResponseHeaderOverrides();
				}

				// Set header.
				responseHeaders.setContentType((String) options.get(OPERATION_OPTION_CONTENT_TYPE));

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CACHE_CONTROL)) {

				// Create the response header.
				if (responseHeaders == null) {
					responseHeaders = new ResponseHeaderOverrides();
				}

				// Set header.
				responseHeaders.setCacheControl((String) options.get(OPERATION_OPTION_CACHE_CONTROL));

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_DISPOSITION)) {

				// Create the response header.
				if (responseHeaders == null) {
					responseHeaders = new ResponseHeaderOverrides();
				}

				// Set header.
				responseHeaders.setContentDisposition((String) options.get(OPERATION_OPTION_CONTENT_DISPOSITION));

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_CONTENT_LANGUAGE)) {

				// Create the response header.
				if (responseHeaders == null) {
					responseHeaders = new ResponseHeaderOverrides();
				}

				// Set header.
				responseHeaders.setContentLanguage((String) options.get(OPERATION_OPTION_READ_CONTENT_LANGUAGE));

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_EXPIRES)) {

				// Create the response header.
				if (responseHeaders == null) {
					responseHeaders = new ResponseHeaderOverrides();
				}

				// Set header.
				responseHeaders.setExpires((String) options.get(OPERATION_OPTION_READ_EXPIRES));

			}

		}

		// Return respose header attributes.
		return responseHeaders;

	}

	/**
	 * Updates the request to retrieve an object.
	 * 
	 * @param request Object request.<br>
	 *                <br>
	 * @param options Options where to retrieve the get request attributes.<br>
	 *                <br>
	 * @return Updated request object.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         request.<br>
	 *                         <br>
	 */
	private GetObjectRequest updateRequest(final GetObjectRequest request, final Map<String, Object> options)
			throws ClientException {

		// Validate options exists.
		if ((options != null) && (options.size() > 0)) {

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_MODIFIED_SINCE)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_MODIFIED_SINCE);

				// Set option.
				if (optionValue instanceof Date) {
					request.setModifiedSinceConstraint((Date) optionValue);
				} else {
					try {
						request.setModifiedSinceConstraint(
								DateL2Helper.toDateYYYYMMDDHHMMSS((String) optionValue, null));
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot parse read option '" + OPERATION_OPTION_READ_MODIFIED_SINCE
										+ "' in Client '" + getName() + "' at Service '" + getService().getName()
										+ "' because option value is a string without the required '"
										+ DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS + "' format.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_UNMODIFIED_SINCE)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_UNMODIFIED_SINCE);

				// Set option.
				if (optionValue instanceof Date) {
					request.setUnmodifiedSinceConstraint((Date) optionValue);
				} else {
					try {
						request.setUnmodifiedSinceConstraint(
								DateL2Helper.toDateYYYYMMDDHHMMSS((String) optionValue, null));
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot parse read option '" + OPERATION_OPTION_READ_UNMODIFIED_SINCE
										+ "' in Client '" + getName() + "' at Service '" + getService().getName()
										+ "' because option value is a string without the required '"
										+ DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS + "' format.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Get the range start value.
			Long rangeStart = null;
			if (options.containsKey(OPERATION_OPTION_READ_RANGE_START)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_RANGE_START);

				// Set range start value.
				if (optionValue instanceof Long) {
					rangeStart = (Long) optionValue;
				} else {
					try {
						rangeStart = (Long) StringL2Helper.parse(Long.class, (String) optionValue, null, null, null);
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot parse read option '" + OPERATION_OPTION_READ_RANGE_START
										+ "' in Client '" + getName() + "' at Service '" + getService().getName()
										+ "' because option value is a string without an integer/long value.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Get the range end value.
			Long rangeEnd = null;
			if (options.containsKey(OPERATION_OPTION_READ_RANGE_END)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_RANGE_END);

				// Set range end value.
				if (optionValue instanceof Long) {
					rangeEnd = (Long) optionValue;
				} else {
					try {
						rangeEnd = (Long) StringL2Helper.parse(Long.class, (String) optionValue, null, null, null);
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot parse read option '" + OPERATION_OPTION_READ_RANGE_END
										+ "' in Client '" + getName() + "' at Service '" + getService().getName()
										+ "' because option value is a string without an integer/long value.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Set range.
			if ((rangeStart != null) && (rangeEnd != null)) {
				request.setRange(rangeStart, rangeEnd);
			} else if (rangeEnd != null) {
				request.setRange(0, rangeEnd);
			} else if (rangeStart != null) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot configure the byte range within the desired object in Client '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because you just provided a value for read option '"
								+ OPERATION_OPTION_READ_RANGE_START + "' and a value for read option '"
								+ OPERATION_OPTION_READ_RANGE_END + "' is required in order to set the byte range.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_REQUESTER_PAYS)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_READ_REQUESTER_PAYS);

				// Set option.
				if (optionValue instanceof Boolean) {
					request.setRequesterPays((Boolean) optionValue);
				} else {
					try {
						request.setRequesterPays(
								(Boolean) StringL2Helper.parse(Boolean.class, (String) optionValue, null, null, null));
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(), "WAREWORK cannot parse read option '"
								+ OPERATION_OPTION_READ_REQUESTER_PAYS + "' in Client '" + getName() + "' at Service '"
								+ getService().getName()
								+ "' because option value is a string without the required 'true' or 'false' values.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_VERSION_ID)) {
				request.setVersionId((String) options.get(OPERATION_OPTION_READ_VERSION_ID));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_MATCH_ETAG)) {

				// Create a list to store the ETag.
				final List<String> etag = new ArrayList<String>();

				// Set the Etag in the list.
				etag.add((String) options.get(OPERATION_OPTION_READ_MATCH_ETAG));

				// Set the Etag.
				request.setMatchingETagConstraints(etag);

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_READ_NONMATCH_ETAG)) {

				// Create a list to store the ETag.
				final List<String> etag = new ArrayList<String>();

				// Set the Etag in the list.
				etag.add((String) options.get(OPERATION_OPTION_READ_NONMATCH_ETAG));

				// Set the Etag.
				request.setNonmatchingETagConstraints(etag);

			}

		}

		// Return updated request object.
		return request;

	}

	/**
	 * Update metadata.
	 * 
	 * @param metadata
	 * @param options
	 * @return
	 * @throws ClientException
	 */
	private ObjectMetadata updateObjectMetadata(final ObjectMetadata metadata, final Map<String, Object> options)
			throws ClientException {

		// Process options.
		if (options != null) {

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CACHE_CONTROL)) {
				metadata.setCacheControl((String) options.get(OPERATION_OPTION_CACHE_CONTROL));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_DISPOSITION)) {
				metadata.setContentDisposition((String) options.get(OPERATION_OPTION_CONTENT_DISPOSITION));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_ENCODING)) {
				metadata.setContentEncoding((String) options.get(OPERATION_OPTION_CONTENT_ENCODING));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_LENGTH)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_CONTENT_LENGTH);

				// Set option.
				if (optionValue instanceof Integer) {
					metadata.setContentLength((Integer) optionValue);
				} else {
					metadata.setContentLength(Integer.valueOf((String) optionValue));
				}

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_CONTENT_TYPE)) {
				metadata.setContentType((String) options.get(OPERATION_OPTION_CONTENT_TYPE));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID)) {
				metadata.setExpirationTimeRuleId((String) options.get(OPERATION_OPTION_WRITE_EXPIRATION_TIME_RULE_ID));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE)) {

				// Get the value of the read option.
				final Object optionValue = options.get(OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE);

				// Set option.
				if (optionValue instanceof Date) {
					metadata.setHttpExpiresDate((Date) optionValue);
				} else {
					try {
						metadata.setHttpExpiresDate(DateL2Helper.toDateYYYYMMDDHHMMSS((String) optionValue, null));
					} catch (final ParseException e) {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot parse write option '" + OPERATION_OPTION_WRITE_HTTP_EXPIRES_DATE
										+ "' in Client '" + getName() + "' at Service '" + getService().getName()
										+ "' because option value is a string without the required '"
										+ DateL2Helper.DATE_PATTERN_YYYYMMDD_HHMMSS + "' format.",
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}
				}

			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM)) {
				metadata.setSSEAlgorithm((String) options.get(OPERATION_OPTION_WRITE_SERVER_SIDE_ENCRIPTION_ALGORITHM));
			}

			// Configure header.
			if (options.containsKey(OPERATION_OPTION_WRITE_CONTENT_MD5)) {
				metadata.setContentMD5((String) options.get(OPERATION_OPTION_WRITE_CONTENT_MD5));
			}

		}

		// Return respose header attributes.
		return metadata;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Transform given standard path to AWS S3 path when required.
	 * 
	 * @param path Source path.<br>
	 *             <br>
	 * @return Updated path.<br>
	 *         <br>
	 */
	private String updatePath(final String path) {
		if (getConnector().isInitParameter(AmazonS3Connector.PARAMETER_PATH_TRANSFORM)) {

			// Get all files and directories.
			final String[] resources = path.split(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE);

			// Output updated path.
			final StringBuilder updated = new StringBuilder();

			// Itertate each resource.
			for (int i = 0; i < resources.length; i++) {

				// Get one resource.
				final String resource = resources[i];

				// Process each token.
				if (resource.equals(CommonValueL1Constants.STRING_EMPTY)) {
					continue;
				} else if ((i < (resources.length - 1)) || (resource.indexOf(CommonValueL1Constants.CHAR_PERIOD) < 0)) {

					/*
					 * Process directory.
					 */

					// Add directory.
					updated.append(resource);

					// Add '/' character at the end when required.
					updated.append(FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE);

				} else if (resource.endsWith(ResourceL2Helper.FILE_EXTENSION_SEPARATOR)) {
					/*
					 * Process file that ends with a period character.
					 */
					updated.append(resource.substring(0, resource.length() - 1));
				} else {
					/*
					 * Process file.
					 */
					updated.append(resource);
				}

			}

			// Return updated path.
			return (getConnector().getInitParameter(AmazonS3Connector.PARAMETER_BASE_PATH) == null) ? updated.toString()
					: getConnector().toString(AmazonS3Connector.PARAMETER_BASE_PATH) + updated.toString();

		} else if (getConnector().toString(AmazonS3Connector.PARAMETER_BASE_PATH) == null) {
			return path;
		} else {
			return getConnector().toString(AmazonS3Connector.PARAMETER_BASE_PATH) + path;
		}
	}

	/**
	 * Get the name of the file from a given AWS S3 path.
	 * 
	 * @param path AWS S3 path.<br>
	 *             <br>
	 * @return File name or null if path points to a directory.<br>
	 *         <br>
	 */
	private String getFileName(final String path) {

		// Do not get the name of a file from a directory.
		if (path.endsWith(StringL2Helper.CHARACTER_FORWARD_SLASH)) {
			return null;
		}

		// Find '/' position in path.
		final int indexOfSlash = path.lastIndexOf(CommonValueL2Constants.CHAR_FORWARD_SLASH);

		// Return the name of the file.
		return (indexOfSlash < 0) ? path : path.substring(indexOfSlash, path.length());

	}

}
