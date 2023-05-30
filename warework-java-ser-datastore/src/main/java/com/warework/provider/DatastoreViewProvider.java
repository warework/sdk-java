package com.warework.provider;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.ServiceFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.log.LogServiceConstants;

/**
 * <span>Warework</span><span> Data Store View Provider is responsible of
 * retrieving Views from a Data Store Service. The way it works is quite simple:
 * you provide the name of a Data Store that exists in the Data Store Service
 * and this Provider will return a View associated to this Data Store (if there
 * is any).</span><br>
 * <br>
 * <br>
 * <b> Configure and create a Data Store View Provider</b><br>
 * <br>
 * <span>You can configure this Provider in two different ways. First, you can
 * give just the </span><span>name of the Data Store Service where to retrieve
 * the default View from Data Stores:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Provider.&nbsp;<br>Map&lt;String, Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(DatastoreViewProvider.PARAMETER_SERVICE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datastore-service&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;datastore-view-provider&quot;,&nbsp;DatastoreViewProvider.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * <span>When this Provider is configured like this, you will retrieve Views by
 * specifying the name of the Data Store. That is, you provide the name of the
 * Data Store and it will return the default View of the Data
 * Store.</span><span> This configuration is better when you need only one View
 * from multiple Data Stores.</span><br>
 * <br>
 * Another way to configure this Provider is by specifying also the name of the
 * Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Provider.&nbsp;<br>Map&lt;String, Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(DatastoreViewProvider.PARAMETER_SERVICE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datastore-service&quot;);&nbsp;<br>parameters.put(DatastoreViewProvider.PARAMETER_DATASTORE_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;my-data-store&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;datastore-view-provider&quot;,&nbsp;DatastoreViewProvider.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * <span>If the name of the Data Store is given then you will retrieve Views by
 * name (every object that you request to the Provider will be a View that
 * exists in a specific Data Store). This configur</span><span>ation is better
 * when you need to work with different Views from just one Data Store.</span><br>
 * <br>
 * If you plan to configure this Provider on startup with an XML file then
 * follow this template:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;providers&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider&nbsp;name=&quot;datastore-view-provider&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.provider.DatastoreViewProvider&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;service-name&quot;&nbsp;value=&quot;datastore-service&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;view-name&quot;&nbsp;value=&quot;datastore-view&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;provider&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;providers&gt;&nbsp;&nbsp;&nbsp;<br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * <br>
 * <b> Retrieve objects from a Data Store View Provider</b><br>
 * <br>
 * <span>At this point the </span><span>Data Store View Provider is running and
 * we can request objects from it. If we configured the Provider just with the
 * name of the Data Store Service, we can request objects by providing the name
 * the Data Store:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;View&nbsp;associated&nbsp;to&nbsp;the&nbsp;'jdbc-datastore'.&nbsp;<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;scope.getObject(&quot;datastore-view-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;jdbc-datastore&quot;);</code>
 * <br>
 * <br>
 * <span>This line of code </span><span>performs the following actions:</span><br>
 * <br>
 * <ul class="t0">
 * <li><span>The </span><span>datastore-view-provider</span><span> gets an
 * instance of the Data Store Service named
 * </span><span>datastore-service</span><span>. </span> <br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li><span>The </span><span>datastore-view-provider</span><span> gets the
 * Current View associated to </span><span>jdbc-datastore</span><span> and
 * returns it. If this Data Store does not have any View in its stack of Views,
 * then this Provider will return </span><span>null</span><span>.</span><br>
 * </ul>
 * <br>
 * If we configured this Provider with the name of the Data Store then we have
 * to provide the name of the View that exists in the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;View&nbsp;associated&nbsp;to&nbsp;the&nbsp;'jdbc-datastore'.&nbsp;<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;scope.getObject(&quot;datastore-view-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;rdbms-view&quot;);</code>
 * <br>
 * <br>
 * <span>Now, t</span><span>his line of code performs these actions:</span><br>
 * <br>
 * <ul class="t0">
 * <li><span>The </span><span>datastore-view-provider</span><span> gets an
 * instance of the Data Store Service named
 * </span><span>datastore-service</span><span>. </span> <br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li><span>The </span><span>datastore-view-provider</span><span> gets the
 * &quot;</span><span>rdbms-view</span><span>&quot; View associated to
 * </span><span>jdbc-datastore</span><span> and returns it. If this Data Store
 * does not have this View in its stack of Views, then this Provider will return
 * </span><span>null</span><span>.</span><br>
 * </ul>
 * <br>
 * <b>Minimum prerequisites to run this Provider:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class DatastoreViewProvider extends AbstractProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "datastore-view"
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_PROVIDER;

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the name of the Data Store
	 * Service where to retrieve the instance of a View. This parameter is
	 * mandatory in order to make this Provider work and it must be a string
	 * object with the name of an existing Data Store Service.
	 */
	public static final String PARAMETER_SERVICE_NAME = "service-name";

	/**
	 * Initialization parameter that specifies the name of the Data Store where
	 * to retrieve the View. This parameter is optional. If you do not use this
	 * parameter then you will request objects to this Provider by specifing the
	 * name of the Data Store, that is, you specify the name of the Data Store
	 * and you will get the default View of the Data Store. Otherwise, when you
	 * use this parameter, you will request objects to this Provider by
	 * specifing the name of a View that exists in the Data Store (defined with
	 * this parameter). When defined, this parameter must be a string.
	 */
	public static final String PARAMETER_DATASTORE_NAME = "datastore-name";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the Service.
	private String serviceName;

	// Data Store name.
	private String datastoreName;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Provider.
	 */
	public void close() {
		serviceName = null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Provider.
	 * 
	 * @throws ProviderException
	 *             If there is an error when trying to initialize the Provider.<br>
	 * <br>
	 */
	protected void initialize() throws ProviderException {

		// Validate and store the name of the Service.
		Object serviceName = getInitParameter(PARAMETER_SERVICE_NAME);
		if ((serviceName != null) && (serviceName instanceof String)) {
			this.serviceName = (String) serviceName;
		} else {
			throw new ProviderException(getScopeFacade(),
					"WAREWORK cannot create Provider '" + getName()
							+ "' because given parameter '"
							+ PARAMETER_SERVICE_NAME
							+ "' is null or it is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Validate and store the name of the Data Store.
		Object datastoreName = getInitParameter(PARAMETER_DATASTORE_NAME);
		if (datastoreName != null) {
			if ((!(datastoreName instanceof String))
					|| (((String) datastoreName)
							.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ProviderException(
						getScopeFacade(),
						"WAREWORK cannot create Provider '" + getName()
								+ "' because given parameter '"
								+ PARAMETER_DATASTORE_NAME
								+ "' is not a string or it is an empty string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			} else {
				this.datastoreName = (String) datastoreName;
			}
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
	 * <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Retrieves an instance of a View from a Data Store Service.
	 * 
	 * @param name
	 *            Name of the Data Store that exists in the Data Store Service
	 *            (specified with the parameter
	 *            <code>PARAMETER_SERVICE_NAME</code>) where to retrieve the
	 *            Current View or name of the View that exists in a Data Store
	 *            (specified with the parameter
	 *            <code>PARAMETER_DATASTORE_NAME</code>).<br>
	 * <br>
	 * @return The Current View of a Data Store or <code>null</code> if Service
	 *         does not exists, it is not an instance of a Data Store Service or
	 *         there are no Views associated to the Data Store.<br>
	 * <br>
	 */
	protected Object getObject(String name) {

		// Get the Data Store Service.
		ServiceFacade service = getScopeFacade().getService(serviceName);

		// Validate the Service.
		if ((service != null) && (service instanceof DatastoreServiceFacade)) {

			// Get the Data Store Facade.
			DatastoreServiceFacade datastoreService = (DatastoreServiceFacade) service;

			// Return the Current View.
			if (datastoreName == null) {
				return datastoreService.getView(name);
			} else if (datastoreService.existsClient(datastoreName)) {
				return datastoreService.getView(datastoreName, name);
			} else {

				// Log a Warework message.
				getScopeFacade().log(
						"WAREWORK cannot retrieve object '" + name
								+ "' from Provider '" + getName()
								+ "' because Data Store '" + datastoreName
								+ "' does not exists.",
						LogServiceConstants.LOG_LEVEL_WARN);

				// Nothing to return.
				return null;

			}

		} else {

			// Log a Warework message.
			getScopeFacade()
					.log("WAREWORK cannot retrieve object '"
							+ name
							+ "' from Provider '"
							+ getName()
							+ "' because the name of the Service where to retrieve the View is null or it is not a Data Store Service.",
							LogServiceConstants.LOG_LEVEL_WARN);

			// Nothing to return.
			return null;

		}

	}

	/**
	 * Gets the names of the objects of this Provider.
	 * 
	 * @return Names of the objects of this Provider.<br>
	 * <br>
	 */
	protected Enumeration<String> getObjectNames() throws ProviderException {

		// Get the Data Store Service.
		ServiceFacade service = getScopeFacade().getService(serviceName);

		// Validate the Service.
		if ((service != null) && (service instanceof DatastoreServiceFacade)) {

			// Get the Data Store Facade.
			DatastoreServiceFacade datastoreService = (DatastoreServiceFacade) service;

			//
			if (datastoreName == null) {

				// Storage for avaliable Data Stores with Views.
				List<String> avaliableNames = new ArrayList<String>();

				// Get the name of every Data Store.
				Enumeration<String> datastoreNames = datastoreService
						.getClientNames();

				// Filter Data Stores: choose only those with Views.
				while (datastoreNames.hasMoreElements()) {

					// Get the name of a Data Store.
					String datastoreName = (String) datastoreNames
							.nextElement();

					// Validate if Data Store has Views.
					if (!datastoreService.isDefaultView(datastoreName)) {
						avaliableNames.add(datastoreName);
					}

				}

				// Return avaliable Data Stores with Views.
				return DataStructureL1Helper.toEnumeration(avaliableNames);

			} else {
				return datastoreService.getViewNames(datastoreName);
			}

		} else {
			throw new ProviderException(
					getScopeFacade(),
					"WAREWORK cannot retrieve the names of the objects in Provider '"
							+ getName()
							+ "' because Service '"
							+ serviceName
							+ "' does not exists or it is not an instance of the Data Store Service.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
