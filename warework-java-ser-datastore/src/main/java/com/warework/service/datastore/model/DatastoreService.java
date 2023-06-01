package com.warework.service.datastore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.warework.core.model.ProxyService;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.bean.Parameter;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceImpl;
import com.warework.service.datastore.view.DatastoreView;

/**
 * Bean that holds the information for a Data Store Service.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class DatastoreService extends ProxyService {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Service. If no name is defined then the default Service
	 * name is returned (check out this default name at:
	 * <code>com.warework.service.datastore.DatastoreServiceConstants.DEFAULT_SERVICE_NAME</code>
	 * ).
	 * 
	 * @return Name of the Service.<br>
	 *         <br>
	 */
	public String getName() {
		return (super.getName() == null) ? DatastoreServiceConstants.DEFAULT_SERVICE_NAME : super.getName();
	}

	/**
	 * Gets the implementation class of the Service. If no class is defined then the
	 * default Service class <code>DatastoreServiceImpl.class</code> is returned.
	 * 
	 * @return Implementation class of the Service.<br>
	 *         <br>
	 */
	public String getClazz() {
		return (super.getClazz() == null) ? DatastoreServiceImpl.class.getName() : super.getClazz();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the definition of a Data Store that will be created in the Service on
	 * startup.
	 * 
	 * @param name          The name to which the Data Store will be bound. If a
	 *                      Data Store with the same name is already bound to the
	 *                      Data Store Service, an exception will be thrown. This
	 *                      value cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param connectorType Implementation of the Connector that specifies which
	 *                      Data Store to create and how to create the connections
	 *                      for the Data Store.<br>
	 *                      <br>
	 * @param parameters    Parameters (as string-object pairs) that specifies how
	 *                      the Connector will create the connections for the Data
	 *                      Store.<br>
	 *                      <br>
	 * @param views         Stack of Views for the Data Store. The last View in the
	 *                      list will be the one on the top of the stack of Views so
	 *                      it will be accesible in first place. In the other hand,
	 *                      the first View in the list will be accesible in last
	 *                      place. This value can be <code>null</code> but if it's
	 *                      not, it must have objects of
	 *                      <code>com.warework.service.datastore.model.View</code>
	 *                      type.<br>
	 *                      <br>
	 */
	public void setDatastore(final String name, final Class<? extends ConnectorFacade> connectorType,
			final Map<String, Object> parameters, final List<View> views) {

		// Create a new Data Store.
		final Datastore datastore = new Datastore();

		// Setup the Data Store.
		datastore.setName(name);
		datastore.setConnector(connectorType.getName());
		datastore.setParameters(Parameter.toArrayList(parameters));

		// Set the Views only if they exist.
		if ((views != null) && (views.size() > 0)) {
			datastore.setViews(views);
		}

		// Set the Data Store.
		setClient(datastore);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Adds a View in the stack of Views of a Data Store.
	 * 
	 * @param datastoreName     The name of the Data Store where to include the
	 *                          View. This value cannot be <code>null</code>.<br>
	 *                          <br>
	 * @param viewType          Implementation of the View to be bounded into the
	 *                          Data Store.<br>
	 *                          <br>
	 * @param viewName          Name of the View to include in the Data Store.<br>
	 *                          <br>
	 * @param statementProvider The name of the Provider where to extract
	 *                          statements. This value can be <code>null</code>.<br>
	 *                          <br>
	 * @param parameters        Parameters for the View.<br>
	 *                          <br>
	 */
	public void setView(final String datastoreName, final Class<? extends DatastoreView> viewType,
			final String viewName, final String statementProvider, final Map<String, Object> parameters) {

		// Create the View.
		final View view = new View();

		// Setup the View.
		view.setClazz(viewType.getName());
		view.setName(viewName);
		view.setStatementProvider(statementProvider);
		view.setParameters(Parameter.toArrayList(parameters));

		// Add View in Data Store.
		setView(datastoreName, view);

	}

	/**
	 * Adds a View in the stack of Views of a Data Store.
	 * 
	 * @param datastoreName The name of the Data Store where to include the View.
	 *                      This value cannot be <code>null</code>.<br>
	 *                      <br>
	 * @param view          Object that holds the View's configuration.<br>
	 *                      <br>
	 */
	public void setView(final String datastoreName, final View view) {

		// Get the definition of a Data Store.
		final Datastore datastore = (Datastore) getClient(datastoreName);

		// Add the View only if the Data Store exists.
		if (datastore != null) {

			// Set the Data Store.
			view.setDatastore(datastore);

			// Get the Views of the Data Store.
			List<View> views = datastore.getViews();
			if (views == null) {
				views = new ArrayList<View>();
			}

			// Set the View.
			views.add(view);

			// Update the Views of the Data Store.
			datastore.setViews(views);

		}

	}

	/**
	 * Gets the View that exists on top of the stack of Views of a Data Store.
	 * 
	 * @param datastoreName The name to which the Data Store is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 * @return A Data Store View or <code>null</code> if no View exist for the Data
	 *         Store.<br>
	 *         <br>
	 */
	public View getView(final String datastoreName) {

		// Get the definition of a Data Store.
		final Datastore datastore = (Datastore) getClient(datastoreName);

		// Add the parameter only if the Data Store exists.
		if (datastore != null) {

			// Get the Views of the Data Store.
			final List<View> views = datastore.getViews();

			// Create the Views if it's null.
			if ((views != null) && (views.size() > 0)) {
				return (View) views.get(views.size() - 1);
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Removes the View that exists on top of the stack of Views of a Data Store.
	 * 
	 * @param datastoreName The name to which the Data Store is bound. This value
	 *                      cannot be <code>null</code>.<br>
	 *                      <br>
	 */
	public void removeView(final String datastoreName) {

		// Get the definition of a Data Store.
		final Datastore datastore = (Datastore) getClient(datastoreName);

		// Add the parameter only if the Data Store exists.
		if (datastore != null) {

			// Get the Views of the Data Store.
			final List<View> views = datastore.getViews();

			// Create the Views if it's null.
			if ((views != null) && (views.size() > 0)) {
				views.remove(views.size() - 1);
			}

		}

	}

}
