package com.warework.service.datastore.client;

import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;

/**
 * Client to perform Data Store operations.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface DatastoreFacade extends ClientFacade {

	/**
	 * Executes a statement that retrieves information from a Data Store.
	 * 
	 * @param statement
	 *            Query in a specific language accepted by the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	Object query(Object statement, Map<String, Object> values)
			throws ClientException;

	/**
	 * Executes a statement that inserts, updates or deletes information in a
	 * Data Store.
	 * 
	 * @param statement
	 *            Indicates the specific operation to perform and the data to
	 *            update in the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	void update(Object statement, Map<String, Object> values)
			throws ClientException;

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	void commit() throws ClientException;

}
