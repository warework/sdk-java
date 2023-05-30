package com.warework.service.file.client.connector;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.file.client.AmazonS3Client;
import com.warework.service.log.LogServiceConstants;

/**
 * This connector holds the required information to retrieve Amazon S3 storage
 * connections.<br>
 * <br>
 * In order to configure this Client you have to specify at least three
 * parameters:<br>
 * <br>
 * <ul>
 * <li>PARAMETER_ACCESS_KEY: access key for the Amazon Web Service account. This
 * parameter must be specified in this Connector, as an initialization parameter
 * in the Scope or as a system property. It must be a string value.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>PARAMETER_SECRET_KEY: secret key for the Amazon Web Service account. This
 * parameter must be specified in this Connector, as an initialization parameter
 * in the Scope or as a system property. It must be a string value.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>PARAMETER_BUCKET_NAME: name of the Amazon S3 bucket where the objects
 * exist. This parameter is mandatory and it must be a string value.<br>
 * </li>
 * </ul>
 * <br>
 * In most cases, you will specify these parameters when the Amazon S3 Client is
 * created:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Amazon&nbsp;S3&nbsp;Client.<br>Map&lt;String,&nbsp;Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;Amazon&nbsp;S3&nbsp;storage&nbsp;configuration.<br>config.put(AmazonS3Connector.PARAMETER_ACCESS_KEY,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;&lt;your-access-key&gt;&quot;);<br>config.put(AmazonS3Connector.PARAMETER_SECRET_KEY,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;&lt;your-secret-key&gt;&quot;);<br>config.put(AmazonS3Connector.&nbsp;PARAMETER_BUCKET_NAME,&nbsp;&quot;&lt;your-bucket-name&gt;&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Amazon&nbsp;S3&nbsp;Client.<br>fileService.createClient(&quot;amazon-s3&quot;,&nbsp;AmazonS3Connector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;config);</code>
 * <br>
 * <br>
 * Check it now how to do it with the Proxy Service XML configuration file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;amazon-s3&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.file.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;AmazonS3Connector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;aws-access-key&quot;&nbsp;&nbsp;value=&quot;&lt;your-access-key&gt;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;aws-secret-key&quot;&nbsp;value=&quot;&lt;your-account-key&gt;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;bucket-name&quot;&nbsp;value=&quot;&lt;your-bucket-name&gt;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * If you plan to use multiple Amazon services (like Amazon S3 and DynamoDB),
 * you can share the access and secret keys as initialization parameters of the
 * Scope:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;aws-access-key&quot;&nbsp;value=&quot;&lt;your-account-name&gt;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;aws-secret-key&quot;&nbsp;value=&quot;&lt;your-account-key&gt;&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;providers&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;providers&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;services&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;services&gt;<br><br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * It is also important to keep in mind that these parameters can also be
 * specified as system properties. The Amazon S3 connector tries, when no
 * parameters are defined in the Connector or the configuration of the Scope, to
 * retrieve the value for the account name and key with System
 * .getProperty(&quot;&lt;parameter-name&gt;&quot;).<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class AmazonS3Connector extends AbstractFileClientConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies the access key for the Amazon Web
	 * Service account. This parameter must be specified in this Connector, as an
	 * initialization parameter in the Scope or as a system property. It must be a
	 * string value.
	 */
	public static final String PARAMETER_ACCESS_KEY = "aws-access-key";

	/**
	 * Initialization parameter that specifies the secret key for the Amazon Web
	 * Service account. This parameter must be specified in this Connector, as an
	 * initialization parameter in the Scope or as a system property. It must be a
	 * string value.
	 */
	public static final String PARAMETER_SECRET_KEY = "aws-secret-key";

	/**
	 * Initialization parameter that specifies the name of the Amazon S3 bucket
	 * where the objects exist. This parameter is mandatory and it must be a string
	 * value.
	 */
	public static final String PARAMETER_BUCKET_NAME = "bucket-name";

	/**
	 * Initialization parameter that specifies the size for the buffer to use in
	 * <code>read</code> operations. This parameter is not mandatory. When provided,
	 * it must be a string or an integer value with the number of bytes for the
	 * buffer. If this parameter is not provided then it defaults to
	 * <code>AbstractFileClient.DEFAULT_BUFFER_SIZE</code>.
	 */
	public static final String PARAMETER_BUFFER_SIZE = AbstractFileClientConnector.PARAMETER_BUFFER_SIZE;

	// COMMON AWS INITIALIZATION PARAMETERS

	/**
	 * Initialization parameter that specifies the amount of time to wait (in
	 * milliseconds) when initially establishing a connection before giving up and
	 * timing out. A value of 0 (or lower) means infinity, and is not recommended.
	 * This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_CONNECTION_TIMEOUT = "client-connection-timeout";

	/**
	 * Initialization parameter that specifies the maximum number of allowed open
	 * HTTP connections. Minimum accepted value is 1. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_MAX_CONNECTIONS = "client-max-connections";

	/**
	 * Initialization parameter that specifies the maximum number of retry attempts
	 * for failed retryable requests (i.e.: 5xx error responses from services). This
	 * is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_MAX_ERROR_RETRY = "client-max-error-retry";

	/**
	 * Initialization parameter that specifies the Windows domain name for
	 * configuration an NTML proxy. If you aren't using a Windows NTLM proxy, you do
	 * not need to set this field.
	 */
	public static final String PARAMETER_CLIENT_PROXY_DOMAIN = "client-proxy-domain";

	/**
	 * Initialization parameter that specifies the proxy host the client will
	 * connect through. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_PROXY_HOST = "client-proxy-host";

	/**
	 * Initialization parameter that specifies the proxy password to use when
	 * connecting through a proxy. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_PROXY_PASSWORD = "client-proxy-password";

	/**
	 * Initialization parameter that specifies the proxy port the client will
	 * connect through. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_PROXY_PORT = "client-proxy-port";

	/**
	 * Initialization parameter that specifies the proxy user name to use if
	 * connecting through a proxy. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_PROXY_USERNAME = "client-proxy-username";

	/**
	 * Initialization parameter that specifies the Windows workstation name for
	 * configuring NTLM proxy support. If you aren't using a Windows NTLM proxy, you
	 * do not need to set this field.
	 */
	public static final String PARAMETER_CLIENT_PROXY_WORKSTATION = "client-proxy-workstation";

	/**
	 * Initialization parameter that specifies the amount of time to wait (in
	 * milliseconds) for data to be transfered over an established, open connection
	 * before the connection times out and is closed. A value of 0 (or lower) means
	 * infinity, and isn't recommended. This is an optional parameter.
	 */
	public static final String PARAMETER_CLIENT_SOCKET_TIMEOUT = "client-socket-timeout";

	/**
	 * Initialization parameter that specifies the optional size hints (in bytes)
	 * for the low level TCP send buffer. This is an advanced option for advanced
	 * users who want to tune low level TCP parameters to try and squeeze out more
	 * performance.<br>
	 * <br>
	 * The optimal TCP buffer sizes for a particular application are highly
	 * dependent on network configuration and operating system configuration and
	 * capabilities. For example, most modern operating systems provide auto-tuning
	 * functionality for TCP buffer sizes, which can have a big impact on
	 * performance for TCP connections that are held open long enough for the
	 * auto-tuning to optimize buffer sizes.<br>
	 * <br>
	 * Large buffer sizes (ex: 2MB) will allow the operating system to buffer more
	 * data in memory without requiring the remote server to acknowledge receipt of
	 * that information, so can be particularly useful when the network has high
	 * latency.<br>
	 * <br>
	 * This is only a hint, and the operating system may choose not to honor it.
	 * When using this option, users should always check the operating system's
	 * configured limits and defaults. Most OS's have a maximum TCP buffer size
	 * limit configured, and won't let you go beyond that limit unless you
	 * explicitly raise the max TCP buffer size limit.
	 */
	public static final String PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT = "client-socket-send-buffer-size-hint";

	/**
	 * Initialization parameter that specifies the optional size hints (in bytes)
	 * for the low level TCP receive buffer. This is an advanced option for advanced
	 * users who want to tune low level TCP parameters to try and squeeze out more
	 * performance.<br>
	 * <br>
	 * The optimal TCP buffer sizes for a particular application are highly
	 * dependent on network configuration and operating system configuration and
	 * capabilities. For example, most modern operating systems provide auto-tuning
	 * functionality for TCP buffer sizes, which can have a big impact on
	 * performance for TCP connections that are held open long enough for the
	 * auto-tuning to optimize buffer sizes.<br>
	 * <br>
	 * Large buffer sizes (ex: 2MB) will allow the operating system to buffer more
	 * data in memory without requiring the remote server to acknowledge receipt of
	 * that information, so can be particularly useful when the network has high
	 * latency.<br>
	 * <br>
	 * This is only a hint, and the operating system may choose not to honor it.
	 * When using this option, users should always check the operating system's
	 * configured limits and defaults. Most OS's have a maximum TCP buffer size
	 * limit configured, and won't let you go beyond that limit unless you
	 * explicitly raise the max TCP buffer size limit.
	 */
	public static final String PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT = "client-socket-receive-buffer-size-hint";

	/**
	 * Initialization parameter that specifies the AWS Region.<br>
	 * <br>
	 * Regions allow you to access AWS services that physically reside in a specific
	 * geographic region. This can be useful both for redundancy and to keep your
	 * data and applications running close to where you and your users will access
	 * them.<br>
	 * <br>
	 * Be aware that regions are logically isolated from each other, so for example,
	 * you won't be able to access US East resources when communicating with the EU
	 * West endpoint.<br>
	 * <br>
	 * For information about AWS regions and endpoints, go to
	 * <a href="http://docs.aws.amazon.com/general/latest/gr/rande.html" target=
	 * "_blank">Regions and Endpoints in the Amazon Web Services General
	 * Reference</a>.
	 */
	public static final String PARAMETER_ENDPOINT = "endpoint";

	/**
	 * Initialization parameter that specifies if given paths for files and
	 * directories should be transformed to follow AWS S3 convention.<br>
	 * <br>
	 * AWS S3 convention mandates that directories must end with '/' character, for
	 * example: 'my-dir/'. If you want to use the '/my-dir' standard convention in
	 * every operation with this Client you need to set this parameter to
	 * <code>true</code>.<br>
	 * <br>
	 * Examples:<br>
	 * <br>
	 * - 'test.txt' will remain the same.<br>
	 * - '/test.txt' will be updated to 'test.txt'.<br>
	 * - '/my-dir' will be updated to 'my-dir/'.<br>
	 * - 'my-dir' will be updated to 'my-dir/'.<br>
	 * - '/my-dir-1/my-dir-2' will be updated to 'my-dir-1/my-dir-2/'.<br>
	 * - '/my-dir-1/my-dir-2.txt/my-dir-3' will be updated to
	 * 'my-dir-1/my-dir-2.txt/my-dir-3/'.<br>
	 * - '/my-dir/test.txt' will be updated to 'my-dir/test.txt'.<br>
	 * <br>
	 * You must be aware anyway that taking this action comes with some drawbacks.
	 * First, all files must have the '.' character in order to differentiate them
	 * of directories. Also that no directories can be named just with '/'
	 * character, something that S3 allows to.<br>
	 * <br>
	 * <b>IMPORTANT</b>: Value specified with <code>PARAMETER_BASE_PATH</code> will
	 * also be updated if this parameter is set to <code>true</code>.<br>
	 * <br>
	 * This parameter is optional and by default it's <code>false</code>. Accepted
	 * values for this parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects).
	 */
	public static final String PARAMETER_PATH_TRANSFORM = "path-transform";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Amazon Web Service client configuration.
	private ClientConfiguration config;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the File Client.
	 * 
	 * @return A <code>com.warework.service.file.client.AmazonS3Client</code>
	 *         Client.<br>
	 *         <br>
	 */
	public Class<AmazonS3Client> getClientType() {
		return AmazonS3Client.class;
	}

	/**
	 * Gets a connection with the Amazon S3 Web Service.
	 * 
	 * @return A <code>com.amazonaws.services.s3.AmazonS3</code> instance.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection with the Client.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Get the AWS credentials.
		final AWSCredentials credentials = (BasicAWSCredentials) getConnectionSource();

		// Create client builder.
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();

		// Set client config.
		builder = builder.withClientConfiguration(getClientConfig());

		// Configure endpoint/region.
		final Object endpoint = getInitParameter(PARAMETER_ENDPOINT);
		if (endpoint != null) {
			builder = builder.withRegion((String) endpoint);
		}

		// Set credentials.
		builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));

		// Create a connection with an Amazon Web Service.
		return builder.build();

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates the initialization parameters.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Initialize parent.
		super.initialize();

		// Get the access key.
		Object accessKey = getInitParameter(PARAMETER_ACCESS_KEY);
		if (accessKey == null) {

			// Get the access key from the Scope initialization parameter.
			accessKey = getScopeFacade().getInitParameter(PARAMETER_ACCESS_KEY);

			// Validate the access key.
			if (accessKey != null) {
				if (!(accessKey instanceof String)) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because given Scope initialization parameter '" + PARAMETER_ACCESS_KEY
									+ "' is not a '" + String.class.getName() + "' object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName() + "' because parameter '"
								+ PARAMETER_ACCESS_KEY + "' is mandatory and it is not specified in the Connector.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			if (!(accessKey instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because given connector parameter '" + PARAMETER_ACCESS_KEY + "' is not a '"
								+ String.class.getName() + "' object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Get the account key.
		Object secretKey = getInitParameter(PARAMETER_SECRET_KEY);
		if (secretKey != null) {
			if (!(secretKey instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because given connector parameter '" + PARAMETER_SECRET_KEY + "' is not a '"
								+ String.class.getName() + "' object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Get the account key from the Scope initialization parameter.
			secretKey = getScopeFacade().getInitParameter(PARAMETER_SECRET_KEY);

			// Validate the account key.
			if (secretKey != null) {
				if (!(secretKey instanceof String)) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because given Scope initialization parameter '" + PARAMETER_SECRET_KEY
									+ "' is not a '" + String.class.getName() + "' object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName() + "' because parameter '"
								+ PARAMETER_SECRET_KEY + "' is mandatory and it is not specified in the Connector.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the name of the bucket.
		final Object bucketName = getInitParameter(PARAMETER_BUCKET_NAME);
		if ((bucketName == null) || !(bucketName instanceof String)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize File Client '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because given '" + PARAMETER_BUCKET_NAME
							+ "' connector parameter is not a string value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the size of the buffer.
		final Object bufferSize = getInitParameter(PARAMETER_BUFFER_SIZE);
		if ((bufferSize != null) && !(bufferSize instanceof String) && !(bufferSize instanceof Integer)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize File Client '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because given '" + PARAMETER_BUFFER_SIZE
							+ "' connector parameter is not an integer or a string value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Validate client configuration.
		if (config == null) {

			// Validate the parameter.
			final Object connectionTimeout = getInitParameter(PARAMETER_CLIENT_CONNECTION_TIMEOUT);
			if (connectionTimeout != null) {

				// Validate type and values of the parameter.
				if (connectionTimeout instanceof String) {
					try {
						Integer.parseInt((String) connectionTimeout);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '" + PARAMETER_CLIENT_CONNECTION_TIMEOUT
										+ "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(connectionTimeout instanceof Integer)) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_CONNECTION_TIMEOUT
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object maxConnections = getInitParameter(PARAMETER_CLIENT_MAX_CONNECTIONS);
			if (maxConnections != null) {

				// Validate type and values of the parameter.
				int paramValue = -1;
				if (maxConnections instanceof String) {
					try {
						paramValue = Integer.parseInt((String) maxConnections);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '" + PARAMETER_CLIENT_MAX_CONNECTIONS
										+ "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(maxConnections instanceof Integer)) {
					paramValue = (Integer) maxConnections;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_MAX_CONNECTIONS
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Validate range of values for the parameter.
				if (paramValue < 1) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_MAX_CONNECTIONS
									+ "' is less than 1.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object maxErrorRetry = getInitParameter(PARAMETER_CLIENT_MAX_ERROR_RETRY);
			if (maxErrorRetry != null) {

				// Validate type and values of the parameter.
				if (maxErrorRetry instanceof String) {
					try {
						Integer.parseInt((String) maxErrorRetry);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '" + PARAMETER_CLIENT_MAX_ERROR_RETRY
										+ "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(maxErrorRetry instanceof Integer)) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_MAX_ERROR_RETRY
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object proxyPort = getInitParameter(PARAMETER_CLIENT_PROXY_PORT);
			if (proxyPort != null) {

				// Validate type and values of the parameter.
				int paramValue = -1;
				if (proxyPort instanceof String) {
					try {
						paramValue = Integer.parseInt((String) proxyPort);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_PORT
										+ "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(proxyPort instanceof Integer)) {
					paramValue = (Integer) proxyPort;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_PORT
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Validate range of values for the parameter.
				if (paramValue < 0) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_PORT
									+ "' is less than 1.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object socketTimeout = getInitParameter(PARAMETER_CLIENT_SOCKET_TIMEOUT);
			if (socketTimeout != null) {

				// Validate type and values of the parameter.
				if (socketTimeout instanceof String) {
					try {
						Integer.parseInt((String) socketTimeout);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '" + PARAMETER_CLIENT_SOCKET_TIMEOUT
										+ "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(socketTimeout instanceof Integer)) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '" + PARAMETER_CLIENT_SOCKET_TIMEOUT
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object socketSendBufferSizeHint = getInitParameter(PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT);
			if (socketSendBufferSizeHint != null) {

				// Validate type and values of the parameter.
				int paramValue = -1;
				if (socketSendBufferSizeHint instanceof String) {
					try {
						paramValue = Integer.parseInt((String) socketSendBufferSizeHint);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot initialize S3 File Client '" + getClientName()
										+ "' because the value of parameter '"
										+ PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT + "' is not an integer value.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(socketSendBufferSizeHint instanceof Integer)) {
					paramValue = (Integer) socketSendBufferSizeHint;
				} else {
					throw new ConnectorException(getScopeFacade(), "WAREWORK cannot initialize S3 File Client '"
							+ getClientName() + "' because the value of parameter '"
							+ PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT + "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Validate range of values for the parameter.
				if (paramValue < 1) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '"
									+ PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT + "' is less than 1.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object socketReceiveBufferSizeHint = getInitParameter(
					PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT);
			if (socketReceiveBufferSizeHint != null) {

				// Validate type and values of the parameter.
				int paramValue = -1;
				if (socketReceiveBufferSizeHint instanceof String) {
					try {
						paramValue = Integer.parseInt((String) socketReceiveBufferSizeHint);
					} catch (final Exception e) {
						throw new ConnectorException(getScopeFacade(), "WAREWORK cannot initialize S3 File Client '"
								+ getClientName() + "' because the value of parameter '"
								+ PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT + "' is not an integer value.", null,
								LogServiceConstants.LOG_LEVEL_WARN);
					}
				} else if (!(socketReceiveBufferSizeHint instanceof Integer)) {
					paramValue = (Integer) socketReceiveBufferSizeHint;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '"
									+ PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT
									+ "' is not an Integer or a String object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

				// Validate range of values for the parameter.
				if (paramValue < 1) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize S3 File Client '" + getClientName()
									+ "' because the value of parameter '"
									+ PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT + "' is less than 1.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Validate the parameter.
			final Object proxyDomain = getInitParameter(PARAMETER_CLIENT_PROXY_DOMAIN);
			if ((proxyDomain != null) && !(proxyDomain instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_DOMAIN
								+ "' is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate the parameter.
			final Object proxyHost = getInitParameter(PARAMETER_CLIENT_PROXY_HOST);
			if ((proxyHost != null) && !(proxyHost instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_HOST
								+ "' is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate the parameter.
			final Object proxyPassword = getInitParameter(PARAMETER_CLIENT_PROXY_PASSWORD);
			if ((proxyPassword != null) && !(proxyPassword instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_PASSWORD
								+ "' is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate the parameter.
			final Object proxyUsername = getInitParameter(PARAMETER_CLIENT_PROXY_USERNAME);
			if ((proxyUsername != null) && !(proxyUsername instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_USERNAME
								+ "' is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Validate the parameter.
			final Object proxyWorkstation = getInitParameter(PARAMETER_CLIENT_PROXY_WORKSTATION);
			if ((proxyWorkstation != null) && !(proxyWorkstation instanceof String)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize S3 File Client '" + getClientName()
								+ "' because the value of parameter '" + PARAMETER_CLIENT_PROXY_WORKSTATION
								+ "' is not a string value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Validate the parameter.
		final Object endpoint = getInitParameter(PARAMETER_ENDPOINT);
		if ((endpoint != null) && !(endpoint instanceof String)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize S3 File Client '" + getClientName()
							+ "' because the value of parameter '" + PARAMETER_ENDPOINT + "' is not a string value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the value of the path transform parameter.
		final Object pathTransform = getInitParameter(PARAMETER_PATH_TRANSFORM);
		if ((pathTransform != null) && (!(pathTransform instanceof String)) && (!(pathTransform instanceof Boolean))) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize S3 File Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because given '" + PARAMETER_PATH_TRANSFORM
							+ "' parameter is not a String or a Boolean object.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Retrieves the Amazon Web Service credentials.
	 * 
	 * @return A new instance of
	 *         <code>com.amazonaws.auth.BasicAWSCredentials</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {

		// Get the access key.
		String accessKey = (String) getInitParameter(PARAMETER_ACCESS_KEY);
		if (accessKey == null) {
			accessKey = (String) getScopeFacade().getInitParameter(PARAMETER_ACCESS_KEY);
		}

		// Get the secret key.
		String secretKey = (String) getInitParameter(PARAMETER_SECRET_KEY);
		if (secretKey == null) {
			secretKey = (String) getScopeFacade().getInitParameter(PARAMETER_SECRET_KEY);
		}

		// Create and return the AWS credentials.
		return new BasicAWSCredentials(accessKey, secretKey);

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Amazon Web Service Client configuration.
	 * 
	 * @return Amazon Web Service client configuration.<br>
	 *         <br>
	 */
	private ClientConfiguration getClientConfig() {

		// Create the configuration if required.
		if (config == null) {

			// Create the S3 configuration.
			config = new ClientConfiguration();

			// Get the value of the parameters.
			if ((getInitParameter(PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT) != null)
					&& (getInitParameter(PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT) != null)) {

				// Get the value of the parameter.
				final Object socketSendBufferSizeHint = getInitParameter(PARAMETER_CLIENT_SOCKET_SEND_BUFFER_SIZE_HINT);

				// Parse the value of the parameter.
				int sendSize = -1;
				if (socketSendBufferSizeHint instanceof Integer) {
					sendSize = (Integer) socketSendBufferSizeHint;
				} else {
					sendSize = Integer.parseInt((String) socketSendBufferSizeHint);
				}

				// Get the value of the parameter.
				final Object socketReceiveBufferSizeHint = getInitParameter(
						PARAMETER_CLIENT_SOCKET_RECEIVE_BUFFER_SIZE_HINT);

				// Parse the value of the parameter.
				int receiveSize = -1;
				if (socketReceiveBufferSizeHint instanceof Integer) {
					receiveSize = (Integer) socketReceiveBufferSizeHint;
				} else {
					receiveSize = Integer.parseInt((String) socketReceiveBufferSizeHint);
				}

				// Set the value of the parameters in the configuration object.
				config.setSocketBufferSizeHints(sendSize, receiveSize);

			}

			// Get the value of the parameter.
			final Object connectionTimeout = getInitParameter(PARAMETER_CLIENT_CONNECTION_TIMEOUT);
			if (connectionTimeout != null) {

				// Parse the value.
				int paramValue = -1;
				if (connectionTimeout instanceof Integer) {
					paramValue = (Integer) connectionTimeout;
				} else {
					paramValue = Integer.parseInt((String) connectionTimeout);
				}

				// Set the value of the parameter in the configuration object.
				if (paramValue > 0) {
					config.setConnectionTimeout(paramValue);
				} else {
					config.setConnectionTimeout(0);
				}

			}

			// Get the value of the parameter.
			final Object maxConnections = getInitParameter(PARAMETER_CLIENT_MAX_CONNECTIONS);
			if (maxConnections != null) {
				if (maxConnections instanceof Integer) {
					config.setMaxConnections((Integer) maxConnections);
				} else {
					config.setMaxConnections(Integer.parseInt((String) maxConnections));
				}
			}

			// Get the value of the parameter.
			final Object maxErrorRetry = getInitParameter(PARAMETER_CLIENT_MAX_ERROR_RETRY);
			if (maxErrorRetry != null) {
				if (maxErrorRetry instanceof Integer) {
					config.setMaxErrorRetry((Integer) maxErrorRetry);
				} else {
					config.setMaxErrorRetry(Integer.parseInt((String) maxErrorRetry));
				}
			}

			// Get the value of the parameter.
			final Object proxyPort = getInitParameter(PARAMETER_CLIENT_PROXY_PORT);
			if (proxyPort != null) {
				if (proxyPort instanceof Integer) {
					config.setProxyPort((Integer) proxyPort);
				} else {
					config.setProxyPort(Integer.parseInt((String) proxyPort));
				}
			}

			// Get the value of the parameter.
			final Object socketTimeout = getInitParameter(PARAMETER_CLIENT_SOCKET_TIMEOUT);
			if (socketTimeout != null) {

				// Parse the value.
				int paramValue = -1;
				if (socketTimeout instanceof Integer) {
					paramValue = (Integer) socketTimeout;
				} else {
					paramValue = Integer.parseInt((String) socketTimeout);
				}

				// Set the value of the parameter in the configuration object.
				if (paramValue > 0) {
					config.setSocketTimeout(paramValue);
				} else {
					config.setSocketTimeout(0);
				}

			}

			// Get the value of the parameter.
			final Object proxyDomain = getInitParameter(PARAMETER_CLIENT_PROXY_DOMAIN);
			if (proxyDomain != null) {
				config.setProxyDomain((String) proxyDomain);
			}

			// Get the value of the parameter.
			final Object proxyHost = getInitParameter(PARAMETER_CLIENT_PROXY_HOST);
			if (proxyHost != null) {
				config.setProxyHost((String) proxyHost);
			}

			// Get the value of the parameter.
			final Object proxyPassword = getInitParameter(PARAMETER_CLIENT_PROXY_PASSWORD);
			if (proxyPassword != null) {
				config.setProxyPassword((String) proxyPassword);
			}

			// Get the value of the parameter.
			final Object proxyUsername = getInitParameter(PARAMETER_CLIENT_PROXY_USERNAME);
			if (proxyUsername != null) {
				config.setProxyUsername((String) proxyUsername);
			}

			// Get the value of the parameter.
			final Object proxyWorkstation = getInitParameter(PARAMETER_CLIENT_PROXY_WORKSTATION);
			if (proxyWorkstation != null) {
				config.setProxyWorkstation((String) proxyWorkstation);
			}

		}

		// Return the configuration object.
		return config;

	}

}
