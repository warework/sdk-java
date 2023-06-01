package com.warework.service.datastore.client;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.warework.core.callback.AbstractBaseCallback;
import com.warework.core.callback.AbstractCallback;
import com.warework.core.callback.AbstractDefaultCallback;
import com.warework.core.callback.CallbackInvoker;
import com.warework.core.callback.CallbackSuccess;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.data.OrderBy;
import com.warework.core.util.helper.DataStructureL2Helper;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.query.oo.AbstractComposedExpression;
import com.warework.service.datastore.query.oo.And;
import com.warework.service.datastore.query.oo.Operator;
import com.warework.service.datastore.query.oo.Query;
import com.warework.service.datastore.query.oo.Where;
import com.warework.service.datastore.view.ObjectDatastoreView;
import com.warework.service.log.LogServiceConstants;

/**
 * View that defines common operations for Object Data Stores.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractObjectDatastoreView extends AbstractDatastoreView implements ObjectDatastoreView {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Collection to identify simple data types.
	private static final Collection<Class<?>> SIMPLE_TYPES = createSimpleTypes();

	// DATA STORE OPERATION WORDS

	private static final String MESSAGE_DATA_STORE_OPERATION_SAVE = "save";

	private static final String MESSAGE_DATA_STORE_OPERATION_UPDATE = "update";

	private static final String MESSAGE_DATA_STORE_OPERATION_DELETE = "delete";

	// CALLBACK VALIDATION MESSAGES

	private static final String MESSAGE_VALIDATE_CALLBACK_SAVE_GIVEN_OBJECTS = "save given object/s";

	private static final String MESSAGE_VALIDATE_CALLBACK_UPDATE_GIVEN_OBJECTS = "update given object/s";

	private static final String MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS = "delete given object/s";

	private static final String MESSAGE_VALIDATE_CALLBACK_DELETE_FROM_QUERY = "delete object/s from given query";

	private static final String MESSAGE_VALIDATE_CALLBACK_FIND_OBJECT = "find any object";

	private static final String MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS = "list objects";

	private static final String MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS = "count objects";

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object in a Data Store.
	 * 
	 * @param object  Object to save.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 */
	protected abstract void performSave(Object object, CallbackInvoker invoker);

	/**
	 * Updates an object in a Data Store.
	 * 
	 * @param object  Object to update.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 */
	protected abstract void performUpdate(Object object, CallbackInvoker invoker);

	/**
	 * Deletes an object in a Data Store.
	 * 
	 * @param object  Object to delete.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 */
	protected abstract void performDelete(Object object, CallbackInvoker invoker);

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query   Object that specifies what to search for. It defines the type
	 *                of the object to search for, how to filter the result, which
	 *                order to apply and the page to retrieve.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 * @param <E>     The type of query.<br>
	 *                <br>
	 */
	protected abstract <E> void performList(Query<E> query, CallbackInvoker invoker);

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object or a collection of objects in a Data Store.
	 * 
	 * @param object Object/s to save. You can provide a simple Java Bean, an array
	 *               or a <code>java.util.Collection</code>. If this object is not
	 *               an array nor a <code>java.util.Collection</code> then this
	 *               method tries to save the object directly in the underlying Data
	 *               Store. When it represents an array or a
	 *               <code>java.util.Collection</code> then a batch operation is
	 *               perfomed. Batch operations are handled directly by the Data
	 *               Store if they support it. Otherwise, this method extracts every
	 *               object from the array/collection and tries to save each one in
	 *               the Data Store.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to save the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void save(Object object) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				if (ReflectionL2Helper.isArray(object)) {
					handleSaveArray(object);
				} else if (object instanceof Collection) {
					handleSaveCollection((Collection<?>) object);
				} else {
					performSave(object);
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot save given object in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Save the object.
			save(object, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_SAVE_GIVEN_OBJECTS);

		}
	}

	/**
	 * Saves an object or a collection of objects in a Data Store and invokes a
	 * callback method for every object saved or when any error is found.
	 * 
	 * @param object   Object/s to save. You can provide a simple Java Bean, an
	 *                 array or a <code>java.util.Collection</code>. If this object
	 *                 is not an array nor a <code>java.util.Collection</code> then
	 *                 this method tries to save the object directly in the
	 *                 underlying Data Store and after that invokes the callback
	 *                 <code>onSuccess</code> / <code>onFailure</code> method just
	 *                 one time. When it represents an array or a
	 *                 <code>java.util.Collection</code> then a batch operation is
	 *                 perfomed. Batch operations are handled directly by the Data
	 *                 Store if they support it. If so, a callback method is invoked
	 *                 just one time after operation is done. Otherwise, this method
	 *                 extracts every object from the array/collection, tries to
	 *                 save each one in the Data Store and invokes the
	 *                 <code>onSuccess</code> callback method for each object saved
	 *                 or <code>onFailure</code> one time if any error is found.
	 *                 This allows you to track the progress of the operation to
	 *                 perform. If the underlying Data Store supports batch
	 *                 operations but you need to track in detail the progress of
	 *                 each object saved, then you should configure the Connector of
	 *                 the Data Store with
	 *                 <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *                 <code>true</code>.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve saved object in
	 *                 the <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 */
	public void save(Object object, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_SAVE_GIVEN_OBJECTS)) {
			return;
		}

		// Save object.
		if (ReflectionL2Helper.isArray(object)) {
			handleSaveArray(object, callback);
		} else if (object instanceof Collection) {
			handleSaveCollection((Collection<?>) object, callback);
		} else {

			// Remove callback batch parameters.
			callback.getControl().destroyBatch();

			// Save a single object.
			handleSaveEntity(object, callback);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Updates an object or a collection of objects in a Data Store.
	 * 
	 * @param object Object/s to update. You can provide a simple Java Bean, an
	 *               array or a <code>java.util.Collection</code>. If this object is
	 *               not an array nor a <code>java.util.Collection</code> then this
	 *               method tries to update the object directly in the underlying
	 *               Data Store. When it represents an array or a
	 *               <code>java.util.Collection</code> then a batch operation is
	 *               perfomed. Batch operations are handled directly by the Data
	 *               Store if they support it. Otherwise, this method extracts every
	 *               object from the array/collection and tries to update each one
	 *               in the Data Store.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public void update(Object object) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				if (ReflectionL2Helper.isArray(object)) {
					handleUpdateArray(object);
				} else if (object instanceof Collection) {
					handleUpdateCollection((Collection<?>) object);
				} else {
					performUpdate(object);
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot update given object in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Update the object.
			update(object, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_UPDATE_GIVEN_OBJECTS);

		}
	}

	/**
	 * Updates an object or a collection of objects in a Data Store and invokes a
	 * callback method for every object updated or when any error is found.
	 * 
	 * @param object   Object/s to update. You can provide a simple Java Bean, an
	 *                 array or a <code>java.util.Collection</code>. If this object
	 *                 is not an array nor a <code>java.util.Collection</code> then
	 *                 this method tries to update the object directly in the
	 *                 underlying Data Store and invokes a callback
	 *                 <code>onSuccess</code> / <code>onFailure</code> method just
	 *                 one time. When it represents an array or a
	 *                 <code>java.util.Collection</code> then a batch operation is
	 *                 perfomed. Batch operations are handled directly by the Data
	 *                 Store if they support it. If so, a callback method is invoked
	 *                 just one time after operation is done. Otherwise, this method
	 *                 extracts every object from the array/collection, tries to
	 *                 update each one in the Data Store and invokes the
	 *                 <code>onSuccess</code> callback method for each object
	 *                 updated or <code>onFailure</code> one time if any error is
	 *                 found. This allows you to track the progress of the operation
	 *                 to perform. If the underlying Data Store supports batch
	 *                 operations but you need to track in detail the progress of
	 *                 each object updated, then you should configure the Connector
	 *                 of the Data Store with
	 *                 <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *                 <code>true</code>.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve updated object
	 *                 in the <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 */
	public void update(Object object, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_UPDATE_GIVEN_OBJECTS)) {
			return;
		}

		// Update objects.
		if (ReflectionL2Helper.isArray(object)) {
			handleUpdateArray(object, callback);
		} else if (object instanceof Collection) {
			handleUpdateCollection((Collection<?>) object, callback);
		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Update the entity.
			handleUpdateEntity(object, callback);

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object Object/s to delete. You can provide a simple Java Bean (to
	 *               remove just one entity in the Data Store), a class (to remove
	 *               every object of the specified type), a <code>Query</code>
	 *               object (to remove every object the query returns), an array or
	 *               a <code>java.util.Collection</code> (to remove multiple objects
	 *               at once). When this object represents an array or a
	 *               <code>java.util.Collection</code> then a batch operation is
	 *               perfomed. Batch operations are handled directly by the Data
	 *               Store if they support it. Otherwise, this method extracts every
	 *               object from the array/collection and tries to delete each one
	 *               in the Data Store. If the underlying Data Store supports batch
	 *               operations but you need to track in detail the progress of each
	 *               object deleted, then you should configure the Connector of the
	 *               Data Store with
	 *               <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *               <code>true</code>.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public void delete(Object object) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				if (object instanceof Query) {
					performDeleteQuery((Query<?>) object);
				} else if (ReflectionL2Helper.isArray(object)) {
					handleDeleteArray(object);
				} else if (object instanceof Collection) {
					handleDeleteCollection((Collection<?>) object);
				} else {
					performDelete(object);
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot delete given object/s in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Delete the object.
			delete(object, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS);

		}
	}

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object   Object/s to delete. You can provide a simple Java Bean (to
	 *                 remove just one entity in the Data Store), a class (to remove
	 *                 every object of the specified type), a <code>Query</code>
	 *                 object (to remove every object the query returns), an array
	 *                 or a <code>java.util.Collection</code> (to remove multiple
	 *                 objects at once). If this object is a Java Bean then this
	 *                 method tries to delete the object directly in the underlying
	 *                 Data Store and invokes a callback <code>onSuccess</code> /
	 *                 <code>onFailure</code> method just one time. When it
	 *                 represents an array or a <code>java.util.Collection</code>
	 *                 then a batch operation is perfomed. Batch operations are
	 *                 handled directly by the Data Store if they support it. If so,
	 *                 a callback method is invoked just one time after operation is
	 *                 done. Otherwise, this method extracts every object from the
	 *                 array/collection, tries to delete each one in the Data Store
	 *                 and invokes the <code>onSuccess</code> callback method for
	 *                 each object deleted or <code>onFailure</code> one time if any
	 *                 error is found. This allows you to track the progress of the
	 *                 operation to perform. If the underlying Data Store supports
	 *                 batch operations but you need to track in detail the progress
	 *                 of each object deleted, then you should configure the
	 *                 Connector of the Data Store with
	 *                 <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *                 <code>true</code>. If you provide a
	 *                 <code>com.warework.service.datastore.query.oo.Query</code>
	 *                 object, this method can perform two different actions: [1] if
	 *                 the underlying Data Store cannot directly delete the objects
	 *                 from a given query, then this method first executes a query
	 *                 with the <code>Query</code> object and after that deletes
	 *                 every object of the collection returned by the query (a batch
	 *                 operation if performed); [2] if Data Store can delete objects
	 *                 directly from the query then this operation is executed in
	 *                 one single step. Please review the documentation of the Data
	 *                 Store for further details about how this operation is
	 *                 supported.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve deleted object
	 *                 in the <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 */
	public void delete(Object object, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS)) {
			return;
		}

		// Delete objects.
		handleDelete(object, callback);

	}

	/**
	 * Deletes every object of the same type.
	 * 
	 * @param type Type of objects to delete.<br>
	 *             <br>
	 * @param <E>  The type of object to delete.<br>
	 *             <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public <E> void delete(Class<E> type) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				performDelete(type);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot delete given object/s in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Delete the objects.
			delete(type, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS);

		}
	}

	/**
	 * Deletes every object of the same type.
	 * 
	 * @param type     Type of objects to delete.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve deleted object
	 *                 in the <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 * @param <E>      The type of object to delete.<br>
	 *                 <br>
	 */
	public <E> void delete(Class<E> type, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS)) {
			return;
		}

		// Delete objects.
		handleDelete(type, callback);

	}

	/**
	 * Loads a query from the default Provider of this View and deletes every object
	 * specified by the query.
	 * 
	 * @param name   Name of the query to load from the default Provider defined for
	 *               this View.<br>
	 *               <br>
	 * @param values Map where the keys represent variable names in the query loaded
	 *               from the Provider and the values of the <code>Map</code>
	 *               represent the values that will replace the variables. Pass
	 *               <code>null</code> to this parameter to make no changes in the
	 *               query loaded.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to delete the object
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void executeDeleteByName(String name, Map<String, Object> values) throws ClientException {
		executeDeleteByName(null, name, values);
	}

	/**
	 * Loads a query from a Provider and deletes every object specified by the
	 * query.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the
	 *                     <code>Map</code> represent the values that will replace
	 *                     the variables. Pass <code>null</code> to this parameter
	 *                     to make no changes in the query loaded.<br>
	 *                     <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public void executeDeleteByName(String providerName, String queryName, Map<String, Object> values)
			throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {

				// Load the query.
				Object statement = getStatement(providerName, queryName);

				// Execute the query.
				if (statement instanceof Query) {

					// Get the query object.
					Query<?> query = (Query<?>) statement;

					// Update the query with the values provided by the user.
					updateQuery(query, values, -1, -1);

					// Delete objects.
					performDeleteQuery(query);

				} else {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot delete objects with query '" + queryName + "' in Data Store '" + getName()
									+ "' at Service '" + getService().getName()
									+ "' because the type of the query returned by the Provider is not a '"
									+ Query.class.getName() + "' object.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot delete given object/s in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Delete the object.
			executeDeleteByName(providerName, queryName, values, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_FROM_QUERY);

		}
	}

	/**
	 * Loads a query from the default Provider of this View and deletes every object
	 * specified by the query.
	 * 
	 * @param name     Name of the query to load from the default Provider defined
	 *                 for this View.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 loaded from the Provider and the values of the
	 *                 <code>Map</code> represent the values that will replace the
	 *                 variables. Pass <code>null</code> to this parameter to make
	 *                 no changes in the query loaded.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve deleted object
	 *                 in the <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 */
	public void executeDeleteByName(String name, Map<String, Object> values, AbstractCallback callback) {
		executeDeleteByName(null, name, values, callback);
	}

	/**
	 * Loads a query from a Provider and deletes every object specified by the
	 * query.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the
	 *                     <code>Map</code> represent the values that will replace
	 *                     the variables. Pass <code>null</code> to this parameter
	 *                     to make no changes in the query loaded.<br>
	 *                     <br>
	 * @param callback     Operation response handler.You can retrieve deleted
	 *                     object in the <code>onSuccess</code> method of the
	 *                     callback. <br>
	 *                     <br>
	 */
	public void executeDeleteByName(String providerName, String queryName, Map<String, Object> values,
			AbstractCallback callback) {

		// Load the query.
		Object statement = getStatement(providerName, queryName);

		// Execute the query.
		if (statement instanceof Query) {

			// Get the query object.
			Query<?> query = (Query<?>) statement;

			// Update the query with the values provided by the user.
			updateQuery(query, values, -1, -1);

			// Delete objects.
			delete(query, callback);

		} else {
			CallbackInvoker.invokeFailure(callback, null,
					new ClientException(getScopeFacade(),
							"WAREWORK cannot delete objects with query '" + queryName + "' in Data Store '" + getName()
									+ "' at Service '" + getService().getName()
									+ "' because the type of the query returned by the Provider is not a '"
									+ Query.class.getName() + "' object.",
							null, LogServiceConstants.LOG_LEVEL_WARN));
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Finds an object by its values. This method returns the object of the Data
	 * Store which matches the type and all non-null field values from a given
	 * object. This is done via reflecting the type and all of the fields from the
	 * given object, and building a query expression where all non-null-value fields
	 * are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). So, the object you will get
	 * will be the same type as the object that you will provide. The result of the
	 * query must return one single object in order to avoid an exception. In many
	 * cases, you may only need to provide the ID fields of the object to retrieve
	 * one single object. In relational databases like Oracle or MySQL, these ID
	 * fields are the primary keys.
	 * 
	 * @param filter Filter used to find an object in the Data Store. This object
	 *               specifies two things: first, the type of the object to search
	 *               for in the Data Store, and second, the values that identify the
	 *               object in the Data Store.<br>
	 *               <br>
	 * @param <E>    The type of object to search for.<br>
	 *               <br>
	 * @return Object from the Data Store that matches the type and the values of
	 *         the given object. If more than one object is found in the Data Store,
	 *         then an exception is thrown. If no objects are found, then this
	 *         method returns <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to find the object
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	public <E> E find(E filter) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleFind(filter);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot find object in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_FIND_OBJECT);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute the query.
			CallbackInvoker invoker = handleFind(filter, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_FIND_OBJECT);

			// Return object found.
			return (E) invoker.getResult();

		}
	}

	/**
	 * Finds an object by its values. This method returns the object of the Data
	 * Store which matches the type and all non-null field values from a given
	 * object. This is done via reflecting the type and all of the fields from the
	 * given object, and building a query expression where all non-null-value fields
	 * are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). So, the object you will get
	 * will be the same type as the object that you will provide. The result of the
	 * query must return one single object in order to avoid an exception. In many
	 * cases, you may only need to provide the ID fields of the object to retrieve
	 * one single object. In relational databases like Oracle or MySQL, these ID
	 * fields are the primary keys.
	 * 
	 * @param filter   Filter used to find an object in the Data Store. This object
	 *                 specifies two things: first, the type of the object to search
	 *                 for in the Data Store, and second, the values that identify
	 *                 the object in the Data Store.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the result of
	 *                 the operation in the <code>onSuccess</code> method of the
	 *                 callback. <br>
	 *                 <br>
	 */
	public void find(Object filter, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_FIND_OBJECT)) {
			return;
		}

		// Find object.
		handleFind(filter, callback);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Lists all of the objects that match the type and all non-null field values
	 * from a given object. This is done via reflecting the type and all of the
	 * fields from the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). The result of the query may
	 * return null or a list with one or multiple objects in it. The objects you may
	 * get in the list will be the same type as the object that you will provide.
	 * 
	 * @param filter    Filter used to list the objects in the Data Store. This
	 *                  object specifies two things: first, the type of the object
	 *                  to search for in the Data Store, and second, the values that
	 *                  identify the object in the Data Store.<br>
	 *                  <br>
	 * @param operators Operations for fields. When the filter for the query is
	 *                  created, the "equals to" operator is used by default in
	 *                  every expression. For example: <code>name='John'</code>. You
	 *                  can specify a different operator if you wish. With this
	 *                  method parameter, you can provide a <code>Map</code> where
	 *                  the keys represent the attribute of the object and the
	 *                  values of the Map represent the operation to perform, for
	 *                  example: <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *                  <code>name LIKE 'John'</code>). This method parameter gives
	 *                  you some extra flexibility to filter the query.<br>
	 *                  <br>
	 * @param orderBy   Order of the fields for the result of the query. With this
	 *                  parameter you can specify that the result of the query
	 *                  should be sorted by certain fields in ascending or
	 *                  descending order.<br>
	 *                  <br>
	 * @param page      Page to get from the result. When you specify the number of
	 *                  objects (check out <code>pageSize</code> argument of this
	 *                  method) that you want in the result of a query, Warework
	 *                  automatically calculates the number of pages that hold this
	 *                  number of objects. You have to pass an integer value greater
	 *                  than zero to retrieve a specific page from the result. Set
	 *                  this argument to <code>-1</code> to retrieve every object in
	 *                  one page.<br>
	 *                  <br>
	 * @param pageSize  Number of objects that you want in the result of the query.
	 *                  Set this argument to <code>-1</code> to retrieve every
	 *                  object.<br>
	 *                  <br>
	 * @param <E>       The type of object to search for.<br>
	 *                  <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given object. If no objects are found, then this method
	 *         returns <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> list(E filter, Map<String, Operator> operators, OrderBy orderBy, int page, int pageSize)
			throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleQuery(filter, operators, orderBy, page, pageSize);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute the query.
			CallbackInvoker invoker = handleQuery(filter, operators, orderBy, page, pageSize, callback, null);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Return query result.
			if (invoker.getResult() instanceof List<?>) {
				return (List<E>) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not a '"
								+ List.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Lists all of the objects that match the type from a given class. This is done
	 * via reflecting the type and all of the fields from the given object, and
	 * building a query expression. The result of the query may return null or a
	 * list with one or multiple objects in it. The objects you may get in the list
	 * will be the same type as the object that you will provide.
	 * 
	 * @param filter    Filter used to list the objects in the Data Store.<br>
	 *                  <br>
	 * @param operators Operations for fields. When the filter for the query is
	 *                  created, the "equals to" operator is used by default in
	 *                  every expression. You can specify a different operator if
	 *                  you wish. With this method parameter, you can provide a
	 *                  <code>Map</code> where the keys represent the attribute of
	 *                  the object and the values of the Map represent the operation
	 *                  to perform, for example: <code>mymap.put("name",
	 *            Operator.IS_NOT_NULL)</code> (it creates the expression:
	 *                  <code>name IS_NOT_NULL</code>). This method parameter gives
	 *                  you some extra flexibility to filter the query. Only two
	 *                  operators can be used: <code>Operator.IS_NULL</code> and
	 *                  <code>Operator.IS_NOT_NULL</code> (this is because you
	 *                  cannot provide the values of the fields in a class
	 *                  object).<br>
	 *                  <br>
	 * @param orderBy   Order of the fields for the result of the query. With this
	 *                  parameter you can specify that the result of the query
	 *                  should be sorted by certain fields in ascending or
	 *                  descending order.<br>
	 *                  <br>
	 * @param page      Page to get from the result. When you specify the number of
	 *                  objects (check out <code>pageSize</code> argument of this
	 *                  method) that you want in the result of a query, Warework
	 *                  automatically calculates the number of pages that hold this
	 *                  number of objects. You have to pass an integer value greater
	 *                  than zero to retrieve a specific page from the result. Set
	 *                  this argument to <code>-1</code> to retrieve every object in
	 *                  one page.<br>
	 *                  <br>
	 * @param pageSize  Number of objects that you want in the result of the query.
	 *                  Set this argument to <code>-1</code> to retrieve every
	 *                  object.<br>
	 *                  <br>
	 * @param <E>       The type of objects to search for.<br>
	 *                  <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given object. If no objects are found, then this method
	 *         returns <code>null</code>.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> list(Class<E> filter, Map<String, Operator> operators, OrderBy orderBy, int page, int pageSize)
			throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleQuery(filter, operators, orderBy, page, pageSize);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute the query.
			CallbackInvoker invoker = handleQuery(filter, operators, orderBy, page, pageSize, callback, null);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Return query result.
			if (invoker.getResult() instanceof List<?>) {
				return (List<E>) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not a '"
								+ List.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Lists all of the objects that match the type and all non-null field values
	 * from a given object. This is done via reflecting the type and all of the
	 * fields from the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). The result of the query may
	 * return null or a list with one or multiple objects in it. The objects you may
	 * get in the list will be the same type as the object that you will provide.
	 * 
	 * @param filter    Filter used to list the objects in the Data Store. You can
	 *                  filter by type or by type and values. When this filter
	 *                  represents a non-class object (a Java Bean for example),
	 *                  this object specifies two things: first, the type of the
	 *                  object to search for in the Data Store, and second, the
	 *                  values that identify the object in the Data Store. This is
	 *                  when you filter by type and values. You can also provide a
	 *                  class as a filter to search just by type.<br>
	 *                  <br>
	 * @param operators Operations for fields. When the filter for the query is
	 *                  created, the "equals to" operator is used by default in
	 *                  every expression. For example: <code>name='John'</code>. You
	 *                  can specify a different operator if you wish. With this
	 *                  method parameter, you can provide a <code>Map</code> where
	 *                  the keys represent the attribute of the object and the
	 *                  values of the Map represent the operation to perform, for
	 *                  example: <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *                  <code>name LIKE 'John'</code>). This method parameter gives
	 *                  you some extra flexibility to filter the query. If you
	 *                  provide a class for the filter parameter of this method,
	 *                  then only two operators can be used:
	 *                  <code>Operator.IS_NULL</code> and
	 *                  <code>Operator.IS_NOT_NULL</code> (this is because you
	 *                  cannot provide the values of the fields in a class
	 *                  object).<br>
	 *                  <br>
	 * @param orderBy   Order of the fields for the result of the query. With this
	 *                  parameter you can specify that the result of the query
	 *                  should be sorted by certain fields in ascending or
	 *                  descending order.<br>
	 *                  <br>
	 * @param page      Page to get from the result. When you specify the number of
	 *                  objects (check out <code>pageSize</code> argument of this
	 *                  method) that you want in the result of a query, Warework
	 *                  automatically calculates the number of pages that hold this
	 *                  number of objects. You have to pass an integer value greater
	 *                  than zero to retrieve a specific page from the result. Set
	 *                  this argument to <code>-1</code> to retrieve every object in
	 *                  one page.<br>
	 *                  <br>
	 * @param pageSize  Number of objects that you want in the result of the query.
	 *                  Set this argument to <code>-1</code> to retrieve every
	 *                  object.<br>
	 *                  <br>
	 * @param callback  Operation response handler. You can retrieve the
	 *                  <code>List</code> result of the operation in the
	 *                  <code>onSuccess</code> method of the callback. <br>
	 *                  <br>
	 */
	public void list(Object filter, Map<String, Operator> operators, OrderBy orderBy, int page, int pageSize,
			AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS)) {
			return;
		}

		// List objects.
		handleQuery(filter, operators, orderBy, page, pageSize, callback, null);

	}

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query Object that specifies what to search for. It defines the type of
	 *              the object to search for, how to filter the result, which order
	 *              to apply and the page to retrieve.<br>
	 *              <br>
	 * @param <E>   The type of objects to search for.<br>
	 *              <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> list(Query<E> query) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleQuery(query);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute query.
			CallbackInvoker invoker = handleQuery(query, callback, null);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Return query result.
			if (invoker.getResult() instanceof List<?>) {
				return (List<E>) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not an instance of '"
								+ List.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query    Object that specifies what to search for. It defines the type
	 *                 of the object to search for, how to filter the result, which
	 *                 order to apply and the page to retrieve.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the
	 *                 <code>List</code> result of the operation in the
	 *                 <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 * @param <E>      The type of objects to search for.<br>
	 *                 <br>
	 */
	public <E> void list(Query<E> query, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS)) {
			return;
		}

		// List objects.
		handleQuery(query, callback, null);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and executes
	 * it in the Data Store.
	 * 
	 * @param name     Name of the query to load from the Provider.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 loaded from the Provider and the values of the Map represent
	 *                 the values that will replace the variables. Pass
	 *                 <code>null</code> to this parameter to make no changes in the
	 *                 query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 objects (check out <code>pageSize</code> argument of this
	 *                 method) that you want in the result of a query, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of objects. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the result. Set
	 *                 this argument to <code>-1</code> to retrieve every object in
	 *                 one page.<br>
	 *                 <br>
	 * @param pageSize Number of objects that you want in the result of the query.
	 *                 Set this argument to <code>-1</code> to retrieve every
	 *                 object.<br>
	 *                 <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public Object executeQueryByName(String name, Map<String, Object> values, int page, int pageSize)
			throws ClientException {
		return executeQueryByName(null, name, values, page, pageSize);
	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the Data
	 * Store.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @param page         Page to get from the result. When you specify the number
	 *                     of objects (check out <code>pageSize</code> argument of
	 *                     this method) that you want in the result of a query,
	 *                     Warework automatically calculates the number of pages
	 *                     that hold this number of objects. You have to pass an
	 *                     integer value greater than zero to retrieve a specific
	 *                     page from the result. Set this argument to
	 *                     <code>-1</code> to retrieve every object in one page.<br>
	 *                     <br>
	 * @param pageSize     Number of objects that you want in the result of the
	 *                     query. Set this argument to <code>-1</code> to retrieve
	 *                     every object.<br>
	 *                     <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public Object executeQueryByName(String providerName, String queryName, Map<String, Object> values, int page,
			int pageSize) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleQueryByName(providerName, queryName, values, page, pageSize);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute query.
			CallbackInvoker invoker = handleQueryByName(providerName, queryName, values, page, pageSize, callback,
					null);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

			// Return query result.
			if (invoker.getResult() instanceof List<?>) {
				return (List<?>) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not a '"
								+ List.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the Data
	 * Store.
	 * 
	 * @param name     Name of the query to load from the Provider.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 loaded from the Provider and the values of the Map represent
	 *                 the values that will replace the variables. Pass
	 *                 <code>null</code> to this parameter to make no changes in the
	 *                 query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 objects (check out <code>pageSize</code> argument of this
	 *                 method) that you want in the result of a query, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of objects. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the result. Set
	 *                 this argument to <code>-1</code> to retrieve every object in
	 *                 one page.<br>
	 *                 <br>
	 * @param pageSize Number of objects that you want in the result of the query.
	 *                 Set this argument to <code>-1</code> to retrieve every
	 *                 object.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the
	 *                 <code>List</code> result of the operation in the
	 *                 <code>onSuccess</code> method of the callback. <br>
	 *                 <br>
	 */
	public void executeQueryByName(String name, Map<String, Object> values, int page, int pageSize,
			AbstractCallback callback) {
		executeQueryByName(null, name, values, page, pageSize, callback);
	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the Data
	 * Store.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @param page         Page to get from the result. When you specify the number
	 *                     of objects (check out <code>pageSize</code> argument of
	 *                     this method) that you want in the result of a query,
	 *                     Warework automatically calculates the number of pages
	 *                     that hold this number of objects. You have to pass an
	 *                     integer value greater than zero to retrieve a specific
	 *                     page from the result. Set this argument to
	 *                     <code>-1</code> to retrieve every object in one page.<br>
	 *                     <br>
	 * @param pageSize     Number of objects that you want in the result of the
	 *                     query. Set this argument to <code>-1</code> to retrieve
	 *                     every object.<br>
	 *                     <br>
	 * @param callback     Operation response handler. You can retrieve the
	 *                     <code>List</code> result of the operation in the
	 *                     <code>onSuccess</code> method of the callback. <br>
	 *                     <br>
	 */
	public void executeQueryByName(String providerName, String queryName, Map<String, Object> values, int page,
			int pageSize, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS)) {
			return;
		}

		// List objects
		handleQueryByName(providerName, queryName, values, page, pageSize, callback, null);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Counts the number of objects of the specified query. <b>WARNING</b>: If the
	 * underlying Data Store cannot directly count the objects with a specific
	 * funcion, the Framework tries to count them by running the query and counting
	 * the objects returned by the query (counts the number of objects that exist in
	 * the list returned by the query). Keep this in mind because queries that
	 * return many objects may cause memory problems. Review the documentation of
	 * the Data Store for further details. Relational Data Stores (JDBC, Android
	 * SQLite and JPA for example) do not have this problem.
	 * 
	 * @param query Object that specifies what to search for. It defines the type of
	 *              the object to search for, how to filter the result, which order
	 *              to apply and the page to retrieve.<br>
	 *              <br>
	 * @param <E>   The type of objects to count.<br>
	 *              <br>
	 * @return Number of objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public <E> int count(Query<E> query) throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleCountQuery(query);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot count objects from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute count.
			CallbackInvoker invoker = handleCountQuery(query, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS);

			// Return count result.
			if (invoker.getResult() instanceof Integer) {
				return (Integer) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot count objects in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not a '"
								+ Integer.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Counts the number of objects of the specified query. <b>WARNING</b>: If the
	 * underlying Data Store cannot directly count the objects with a specific
	 * funcion, the Framework tries to count them by running the query and counting
	 * the objects returned by the query (counts the number of objects that exist in
	 * the list returned by the query). Keep this in mind because queries that
	 * return many objects may cause memory problems. Review the documentation of
	 * the Data Store for further details. Relational Data Stores (JPA for example)
	 * do not have this problem.
	 * 
	 * @param query    Object that specifies what to search for. It defines the type
	 *                 of the object to search for, how to filter the result, which
	 *                 order to apply and the page to retrieve.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the result of
	 *                 the count in the <code>onSuccess</code> method of the
	 *                 callback. <br>
	 *                 <br>
	 * @param <E>      The type of objects to count.<br>
	 *                 <br>
	 */
	public <E> void count(Query<E> query, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS)) {
			return;
		}

		// Count objects.
		handleCountQuery(query, callback);

	}

	/**
	 * Counts every object of a specified type that exist in the Data Store.
	 * <b>WARNING</b>: If the underlying Data Store cannot directly count the
	 * objects with a specific funcion, the Framework tries to count them by running
	 * the query and counting the objects returned by the query (counts the number
	 * of objects that exist in the list returned by the query). Keep this in mind
	 * because queries that return many objects may cause memory problems. Review
	 * the documentation of the Data Store for further details. Relational Data
	 * Stores (JPA for example) do not have this problem.
	 * 
	 * @param type Type of object to count.<br>
	 *             <br>
	 * @param <E>  The type of objects to count.<br>
	 *             <br>
	 * @return Number of objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public <E> int count(Class<E> type) throws ClientException {
		return count(new Query<E>(getScopeFacade(), type));
	}

	/**
	 * Counts every object of a specified type that exist in the Data Store.
	 * <b>WARNING</b>: If the underlying Data Store cannot directly count the
	 * objects with a specific funcion, the Framework tries to count them by running
	 * the query and counting the objects returned by the query (counts the number
	 * of objects that exist in the list returned by the query). Keep this in mind
	 * because queries that return many objects may cause memory problems. Review
	 * the documentation of the Data Store for further details. Relational Data
	 * Stores (JPA for example) do not have this problem.
	 * 
	 * @param type     Type of object to count.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the result of
	 *                 the count in the <code>onSuccess</code> method of the
	 *                 callback. <br>
	 *                 <br>
	 * @param <E>      The type of objects to count.<br>
	 *                 <br>
	 */
	public <E> void count(Class<E> type, AbstractCallback callback) {
		count(new Query<E>(getScopeFacade(), type), callback);
	}

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and counts
	 * the number of objects that it returns. <b>WARNING</b>: If the underlying Data
	 * Store cannot directly count the objects with a specific funcion, the
	 * Framework tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the list
	 * returned by the query). Keep this in mind because queries that return many
	 * objects may cause memory problems. Review the documentation of the Data Store
	 * for further details. Relational Data Stores (JPA for example) do not have
	 * this problem.
	 * 
	 * @param name   Name of the query to load from the Provider.<br>
	 *               <br>
	 * @param values Map where the keys represent variable names in the query loaded
	 *               from the Provider and the values of the Map represent the
	 *               values that will replace the variables. Pass <code>null</code>
	 *               to this parameter to make no changes in the query loaded.<br>
	 *               <br>
	 * @return Number of objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public int executeCountByName(String name, Map<String, Object> values) throws ClientException {
		return executeCountByName(null, name, values);
	}

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and counts
	 * the number of objects that it returns. <b>WARNING</b>: If the underlying Data
	 * Store cannot directly count the objects with a specific funcion, the
	 * Framework tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the list
	 * returned by the query). Keep this in mind because queries that return many
	 * objects may cause memory problems. Review the documentation of the Data Store
	 * for further details. Relational Data Stores (JPA for example) do not have
	 * this problem.
	 * 
	 * @param name     Name of the query to load from the Provider.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 loaded from the Provider and the values of the Map represent
	 *                 the values that will replace the variables. Pass
	 *                 <code>null</code> to this parameter to make no changes in the
	 *                 query loaded.<br>
	 *                 <br>
	 * @param callback Operation response handler. You can retrieve the result of
	 *                 the count in the <code>onSuccess</code> method of the
	 *                 callback. <br>
	 *                 <br>
	 */
	public void executeCountByName(String name, Map<String, Object> values, AbstractCallback callback) {
		executeCountByName(null, name, values, callback);
	}

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and counts
	 * the number of objects that it returns. <b>WARNING</b>: If the underlying Data
	 * Store cannot directly count the objects with a specific funcion, the
	 * Framework tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the list
	 * returned by the query). Keep this in mind because queries that return many
	 * objects may cause memory problems. Review the documentation of the Data Store
	 * for further details. Relational Data Stores (JPA for example) do not have
	 * this problem.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @return Number of objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	public int executeCountByName(String providerName, String queryName, Map<String, Object> values)
			throws ClientException {
		if (getObjectDatastore().executeAsynchronousBlocking()) {
			if (isConnected()) {
				return handleCountByName(providerName, queryName, values);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot count objects in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because connection is closed.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {

			// Validate connection.
			validateConnection(MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS);

			// Create a default callback handler.
			AbstractCallback callback = createDefaultCallback();

			// Execute count.
			CallbackInvoker invoker = handleCountByName(providerName, queryName, values, callback);

			// Validate the result of the callback operation.
			validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS);

			// Return count result.
			if (invoker.getResult() instanceof Integer) {
				return (Integer) invoker.getResult();
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot count objects in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the result of the query is not a '"
								+ Integer.class.getName() + "' class.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and counts
	 * the number of objects that it returns. <b>WARNING</b>: If the underlying Data
	 * Store cannot directly count the objects with a specific funcion, the
	 * Framework tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the list
	 * returned by the query). Keep this in mind because queries that return many
	 * objects may cause memory problems. Review the documentation of the Data Store
	 * for further details. Relational Data Stores (JPA for example) do not have
	 * this problem.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @param callback     Operation response handler. You can retrieve the result
	 *                     of the count in the <code>onSuccess</code> method of the
	 *                     callback. <br>
	 *                     <br>
	 */
	public void executeCountByName(String providerName, String queryName, Map<String, Object> values,
			AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback, MESSAGE_VALIDATE_CALLBACK_COUNT_OBJECTS)) {
			return;
		}

		// Count objects.
		handleCountByName(providerName, queryName, values, callback);

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object in a Data Store.
	 * 
	 * @param object Object to save.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to save the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected void performSave(Object object) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Save a single object.
		handleSaveEntity(object, callback);

		// Validate the result of the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_SAVE_GIVEN_OBJECTS);

	}

	/**
	 * Updates an object or a collection of objects in a Data Store.
	 * 
	 * @param object Object to update.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	protected void performUpdate(Object object) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Update a single object.
		handleUpdateEntity(object, callback);

		// Validate the result of the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_UPDATE_GIVEN_OBJECTS);

	}

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object Object/s to delete. You can provide a simple Java Bean (to
	 *               remove just one entity in the Data Store), a class (to remove
	 *               every object of the specified type), a <code>Query</code>
	 *               object (to remove every object the query returns), an array or
	 *               a <code>java.util.Collection</code> (to remove multiple objects
	 *               at once). When this object represents an array or a
	 *               <code>java.util.Collection</code> then a batch operation is
	 *               perfomed. Batch operations are handled directly by the Data
	 *               Store if they support it. Otherwise, this method extracts every
	 *               object from the array/collection and tries to delete each one
	 *               in the Data Store. If the underlying Data Store supports batch
	 *               operations but you need to track in detail the progress of each
	 *               object deleted, then you should configure the Connector of the
	 *               Data Store with
	 *               <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *               <code>true</code>.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         object/s in the Data Store.<br>
	 *                         <br>
	 */
	protected void performDelete(Object object) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Delete objects.
		handleDelete(object, callback);

		// Validate the result of the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_DELETE_GIVEN_OBJECTS);

	}

	/**
	 * Deletes multiple objects at once in a Data Store.
	 * 
	 * @param type Type of objects to delete.<br>
	 *             <br>
	 * @throws ClientException If there is an error when trying to query the objects
	 *                         in the Data Store.<br>
	 *                         <br>
	 * @param <E> The type of objects to delete.<br>
	 *            <br>
	 */
	protected <E> void performDelete(Class<E> type) throws ClientException {
		performDeleteQuery(new Query<E>(getScopeFacade(), type));
	}

	/**
	 * Deletes multiple objects at once in a Data Store.
	 * 
	 * @param type    Type of objects to delete.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 * @param <E>     The type of objects to delete.<br>
	 *                <br>
	 */
	protected <E> void performDelete(Class<E> type, CallbackInvoker invoker) {
		performDeleteQuery(new Query<E>(getScopeFacade(), type), invoker);
	}

	/**
	 * Deletes multiple objects at once in a Data Store.
	 * 
	 * @param query Object/s to delete.<br>
	 *              <br>
	 * @param <E>   The type of objects to delete.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to query the objects
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected <E> void performDeleteQuery(Query<E> query) throws ClientException {
		if ((!getObjectDatastore().executePaginatedQuery()) && (query.isPaginated())) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete the objects of the query in Data Store '" + getName() + "' at Service '"
							+ getService().getName()
							+ "' because given query is paginated and delete operation does not support it.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {

			// Log a message.
			getScopeFacade().log("WAREWORK is going to query Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "' to remove every object returned by the query.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Run query.
			List<E> queryResult = performList(query);

			// Create the message.
			getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

			// Delete query result.
			handleDeleteCollection(queryResult);

		}
	}

	/**
	 * Deletes multiple objects at once in a Data Store.
	 * 
	 * @param query   Object/s to delete.<br>
	 *                <br>
	 * @param <E>     The type of objects to delete.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 */
	protected <E> void performDeleteQuery(Query<E> query, CallbackInvoker invoker) {
		if ((!getObjectDatastore().executePaginatedQuery()) && (query.isPaginated())) {
			invoker.failure(new ClientException(getScopeFacade(),
					"WAREWORK cannot delete the objects of the query in Data Store '" + getName() + "' at Service '"
							+ getService().getName()
							+ "' because given query is paginated and delete operation does not support it.",
					null, LogServiceConstants.LOG_LEVEL_WARN));
		} else {

			// Log a message.
			getScopeFacade().log("WAREWORK is going to query Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "' to remove every object returned by the query.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Create the message.
			String successMessage = "WAREWORK successfully executed the query in Data Store '" + getName()
					+ "' of Service '" + getService().getName() + "'.";

			// Create a callback for a query operation. Once the query return a
			// list of objects, this callback will remove all those objects.
			AbstractBaseCallback queryCallback = new AbstractDefaultCallback(getScopeFacade()) {

				protected void onSuccess(CallbackSuccess success) {
					handleDeleteCollection((Collection<?>) success.getResult(), success.getSourceCallback());
				}

			};

			// Create the object required to invoke the callback operation.
			CallbackInvoker queryInvoker = CallbackInvoker.createInvoker(queryCallback, invoker.getCurrentCallback(),
					successMessage, null);

			// Run query.
			performList(query, queryInvoker);

		}
	}

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query Object that specifies what to search for. It defines the type of
	 *              the object to search for, how to filter the result, which order
	 *              to apply and the page to retrieve.<br>
	 *              <br>
	 * @param <E>   The type of objects to search for.<br>
	 *              <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to list the object/s
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	protected <E> List<E> performList(Query<E> query) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Execute query.
		CallbackInvoker invoker = handleQuery(query, callback, null);

		// Validate the result of the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_LIST_OBJECTS);

		// Return query result.
		if (invoker.getResult() instanceof List<?>) {
			return (List<E>) invoker.getResult();
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot list objects from Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because the result of the query is not an instance of '"
							+ List.class.getName() + "' class.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Counts the number of objects returned by a query.
	 * 
	 * @param query Objects to count.<br>
	 *              <br>
	 * @param <E>   The type of objects to count.<br>
	 *              <br>
	 * @return Number of objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the objects
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected <E> int performCountQuery(Query<E> query) throws ClientException {

		// Run query.
		List<?> queryResult = performList(query);

		// Return count.
		if ((queryResult != null) && (queryResult.size() > 0)) {
			return queryResult.size();
		} else {
			return 0;
		}

	}

	/**
	 * Counts the number of objects of the specified query.
	 * 
	 * @param query   Objects to count.<br>
	 *                <br>
	 * @param invoker Object required to invoke the callback operation.<br>
	 *                <br>
	 * @param <E>     The type of objects to count.<br>
	 *                <br>
	 */
	protected <E> void performCountQuery(Query<E> query, CallbackInvoker invoker) {

		// Create a callback for a query operation. Once the query return a
		// list of objects, this callback counts these objects.
		AbstractBaseCallback queryCallback = new AbstractDefaultCallback(getScopeFacade()) {

			protected void onSuccess(CallbackSuccess success) {

				// Get the result of the query.
				List<?> queryResult = (List<?>) success.getResult();

				// Get count.
				Integer count = null;
				if ((queryResult != null) && (queryResult.size() > 0)) {
					count = new Integer(queryResult.size());
				} else {
					count = new Integer(0);
				}

				// Invoke success in target callback.
				CallbackInvoker.invokeSuccess(success.getSourceCallback(), null, null, success, true, count);

			}

		};

		// Update invoker values.
		invoker.reset(queryCallback, invoker.getCurrentCallback(), invoker.getSuccessMessage(), null);

		// Run query.
		performList(query, invoker);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query Object that specifies what to search for. It defines the type of
	 *              the object to search for, how to filter the result, which order
	 *              to apply and the page to retrieve.<br>
	 *              <br>
	 * @param <E>   The type of objects to search for.<br>
	 *              <br>
	 * @return Query result.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to execute the query
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected <E> List<E> handleQuery(Query<E> query) throws ClientException {

		// Validate that given query is not paginated.
		if ((!getObjectDatastore().executePaginatedQuery()) && (query.isPaginated())) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the following query in Data Store '" + getName() + "' at Service '"
							+ getService().getName()
							+ "' because it is paginated and this Data Store does not support it:\n" + query.toString(),
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Log a message.
		getScopeFacade().log("WAREWORK is going to execute the following query in Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "':\n" + query.toString(), LogServiceConstants.LOG_LEVEL_DEBUG);

		// Run query.
		List<E> result = performList(query);

		// Create the message for the successful operation.
		getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_DEBUG);

		// Return query result.
		return result;

	}

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query           Object that specifies what to search for. It defines
	 *                        the type of the object to search for, how to filter
	 *                        the result, which order to apply and the page to
	 *                        retrieve.<br>
	 *                        <br>
	 * @param currentCallback Current callback to handle the result of the query
	 *                        operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback to handle the final result of the
	 *                        operation.<br>
	 *                        <br>
	 * @param <E>             The type of objects to search for.<br>
	 *                        <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	protected <E> CallbackInvoker handleQuery(Query<E> query, AbstractBaseCallback currentCallback,
			AbstractBaseCallback sourceCallback) {

		// Validate that given query is not paginated.
		if ((!getObjectDatastore().executePaginatedQuery()) && (query.isPaginated())) {
			return CallbackInvoker.invokeFailure(currentCallback, null,
					new ClientException(getScopeFacade(), "WAREWORK cannot execute the following query in Data Store '"
							+ getName() + "' at Service '" + getService().getName()
							+ "' because it is paginated and this Data Store does not support it:\n" + query.toString(),
							null, LogServiceConstants.LOG_LEVEL_WARN));
		}

		// Log a message.
		getScopeFacade().log("WAREWORK is going to execute the following query in Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "':\n" + query.toString(), LogServiceConstants.LOG_LEVEL_DEBUG);

		// Create the message for the successful operation.
		String successMessage = "WAREWORK successfully executed the query in Data Store '" + getName()
				+ "' of Service '" + getService().getName() + "'.";

		// Initialize callback batch parameters.
		currentCallback.getControl().destroyBatch();

		// Create the object required to invoke the callback
		// operation.
		CallbackInvoker invoker = CallbackInvoker.createInvoker(currentCallback, sourceCallback, successMessage, null);

		// Run query.
		performList(query, invoker);

		// Return the last callback invoker used.
		return invoker.getLastInvoker();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Update the query with the values provided by the user.
	 * 
	 * @param query    Object that represents the query to execute.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 loaded from the Provider and the values of the
	 *                 <code>Map</code> represent the values that will replace the
	 *                 variables. Pass <code>null</code> to this parameter to make
	 *                 no changes in the query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 objects (check out <code>pageSize</code> argument of this
	 *                 method) that you want in the result of a query, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of objects. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the result. Set
	 *                 this argument to <code>-1</code> to retrieve every object in
	 *                 one page.<br>
	 *                 <br>
	 * @param pageSize Number of objects that you want in the result of the query.
	 *                 Set this argument to <code>-1</code> to retrieve every
	 *                 object.<br>
	 *                 <br>
	 * @param <E>      The type of objects to search for.<br>
	 *                 <br>
	 */
	protected <E> void updateQuery(Query<E> query, Map<String, Object> values, int page, int pageSize) {

		// Set the variables in the query.
		if ((values != null) && (values.size() > 0) && (query.getWhere() != null)) {

			// Get the variable names.
			Set<String> variableNames = values.keySet();

			// Get the WHERE clause.
			Where where = query.getWhere();

			// Add the variables only if the WHERE clause exists.
			if (where != null) {
				for (Iterator<String> iterator = variableNames.iterator(); iterator.hasNext();) {

					// Get the name of a variable.
					Object variableName = iterator.next();

					// Set the variable.
					where.addVariable((String) variableName, values.get(variableName));

				}
			}

		}

		// Update pagination.
		if ((page > 0) && (pageSize > 0)) {

			// Set the page to retrieve.
			query.setPage(page);

			// Set the maximum results per page.
			query.setPageSize(pageSize);

		}

	}

	/**
	 * Sorts a collection of beans and copies the result into a given list.
	 * 
	 * @param source  Source collection where to retrieve the beans to sort.<br>
	 *                <br>
	 * @param orderBy Order clause to apply in the sort operation.<br>
	 *                <br>
	 * @param target  Target collection where to copy the sorted beans.<br>
	 *                <br>
	 * @param <T>     The type of objects in the result list.<br>
	 *                <br>
	 */
	protected <T> void sort(final Iterable<T> source, final OrderBy orderBy, final List<T> target) {
		DataStructureL2Helper.sort(source, orderBy, target);
	}

	/**
	 * Copies the objects from a specific page of a collection into a target
	 * collection.
	 * 
	 * @param source   Source collection where to retrieve the beans to sort.<br>
	 *                 <br>
	 * @param page     Page to get from source collection. When you specify the
	 *                 number of rows (check out <code>pageRows</code> argument of
	 *                 this method) that you want in the target collection, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of rows. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the source
	 *                 collection. This value must be greater than 0.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the target collection. Set
	 *                 this argument to <code>-1</code> to retrieve every row. This
	 *                 value must be greater than 0<br>
	 *                 <br>
	 * @param target   Target collection where to copy the specified page.<br>
	 *                 <br>
	 * @param <T>      The type of objects in the result list.<br>
	 *                 <br>
	 */
	protected <T> void page(final List<T> source, final int page, final int pageRows, final List<T> target) {
		DataStructureL2Helper.page(source, page, pageRows, target);
	}

	/**
	 * Gets the name of a persistence entity. A persistence entity is a class that
	 * can perform CRUD operations in a database.<br>
	 * <br>
	 * This entity may represent, for example, a table in a relational database so
	 * you can use the <code>@Entity</code> annotation in a Java class to specify
	 * the name of the table in the database where this Java class can perform CRUD
	 * operations. If you place something like
	 * <code>@Entity(name = "HOME_USER")</code> in a class, you are saying that
	 * there is a table in the database named <code>HOME_USER</code>.<br>
	 * <br>
	 * Please keep in mind that an entity can be any persistence type in the target
	 * database. It can be a table in a relational database or a document in a NoSQL
	 * database like MongoDB.<br>
	 * <br>
	 * This method searchs for a Warework <code>@Entity</code> or for a JPA
	 * <code>@Entity</code> to extrat the name of the target entity. <u>If no entity
	 * name is defined with any of those two annotations, then this method will
	 * return the name of the class of the given entity type</u>.
	 * 
	 * @param entityType Persistence entity type. It is a Java class that <u>may
	 *                   have</u> an <code>@Entity</code> annotation (it is not
	 *                   mandatory).<br>
	 *                   <br>
	 * @return If given entity type has a JPA or Warework <code>@Entity</code>
	 *         annotation then this method returns the value of the
	 *         <code>name</code> parameter specified in this annotation. If no
	 *         annotation is specified then this method returns the name of the
	 *         class of the given entity type.<br>
	 *         <br>
	 */
	protected String getEntityName(Class<?> entityType) {
		return getObjectDatastore().getEntityName(entityType);
	}

	/**
	 * Gets the Object Data Store wrapped by this View.
	 * 
	 * @return Implementation of the Object Data Store.<br>
	 *         <br>
	 */
	protected AbstractObjectDatastore getObjectDatastore() {
		return (AbstractObjectDatastore) getDatastore();
	}

	/**
	 * Creates an ID for an object.
	 * 
	 * @param object Object where to create the ID.<br>
	 *               <br>
	 * @return ID for the object.
	 */
	protected String createObjectID(Object object) {
		return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
	}

	/**
	 * Decides if an attribute of the entity/class should be processed or not.
	 * 
	 * @param entity        Object where the attribute exists.<br>
	 *                      <br>
	 * @param attributeName Name of the attribute.<br>
	 *                      <br>
	 * @return <code>false</code> to skip the attribute, <code>true</code> to
	 *         process it.<br>
	 *         <br>
	 */
	protected boolean processAttribute(Object entity, String attributeName) {
		return true;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves every object of an array in the Data Store.
	 * 
	 * @param array Objects to save.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to save the array of
	 *                         objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleSaveArray(Object array) throws ClientException {
		if (getObjectDatastore().executeSaveBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_SAVE);

				// Perform batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to save.
					Object entity = Array.get(array, index);

					// Save the entity.
					performSave(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully saved in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK saved 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.");
			}

		} else {
			performSave(array);
		}
	}

	/**
	 * Saves every object of an array in the Data Store.
	 * 
	 * @param array    Objects to save.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleSaveArray(Object array, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeSaveBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_SAVE);

				// Perform batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to save.
					Object entity = Array.get(array, index);

					// Save the entity.
					handleSaveEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK saved 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Remove callback batch parameters.
			callback.getControl().destroyBatch();

			// Save the array.
			handleSaveEntity(array, callback);

		}
	}

	/**
	 * Saves every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to save.<br>
	 *                   <br>
	 * @throws ClientException If there is an error when trying to save the
	 *                         collection of objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleSaveCollection(Collection<?> collection) throws ClientException {
		if (getObjectDatastore().executeSaveBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_SAVE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to save.
					Object entity = iterator.next();

					// Save the entity.
					performSave(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully saved in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK saved 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.");
			}

		} else {
			performSave(collection);
		}
	}

	/**
	 * Saves every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to save.<br>
	 *                   <br>
	 * @param callback   Callback to handle the result of the operation.<br>
	 *                   <br>
	 */
	private void handleSaveCollection(Collection<?> collection, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeSaveBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_SAVE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to save.
					Object entity = iterator.next();

					// Save the entity.
					handleSaveEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK saved 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Remove callback batch parameters.
			callback.getControl().destroyBatch();

			// Save the collection.
			handleSaveEntity(collection, callback);

		}
	}

	/**
	 * Saves an object in the Data Store.
	 * 
	 * @param entity   Object to save.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleSaveEntity(Object entity, AbstractBaseCallback callback) {

		// Initialize the operation.
		CallbackInvoker invoker = initEntityOperation(callback, entity, MESSAGE_DATA_STORE_OPERATION_SAVE);

		// Save the object.
		performSave(entity, invoker);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Updates every object of an array in the Data Store.
	 * 
	 * @param array Objects to update.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to save the array of
	 *                         objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleUpdateArray(Object array) throws ClientException {
		if (getObjectDatastore().executeUpdateBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_UPDATE);

				// Perform batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to save.
					Object entity = Array.get(array, index);

					// Save the entity.
					performUpdate(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully updated in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK updated 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.");
			}

		} else {
			performUpdate(array);
		}
	}

	/**
	 * Updates every object of an array in the Data Store.
	 * 
	 * @param array    Objects to update.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleUpdateArray(Object array, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeUpdateBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_UPDATE);

				// Perform a batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to update.
					Object entity = Array.get(array, index);

					// Update the entity.
					handleUpdateEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK updated 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Update the entity.
			handleUpdateEntity(array, callback);

		}
	}

	/**
	 * Updates every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to update.<br>
	 *                   <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         collection of objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleUpdateCollection(Collection<?> collection) throws ClientException {
		if (getObjectDatastore().executeUpdateBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_UPDATE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to save.
					Object entity = iterator.next();

					// Save the entity.
					performUpdate(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully updated in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK updated 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.");
			}

		} else {
			performUpdate(collection);
		}
	}

	/**
	 * Updates every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to update.<br>
	 *                   <br>
	 * @param callback   Callback to handle the result of the operation.<br>
	 *                   <br>
	 */
	private void handleUpdateCollection(Collection<?> collection, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeUpdateBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_UPDATE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to update.
					Object entity = iterator.next();

					// Update the entity.
					handleUpdateEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK updated 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Update the entity.
			handleUpdateEntity(collection, callback);

		}
	}

	/**
	 * Updates an object in the Data Store.
	 * 
	 * @param entity   Object to update.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleUpdateEntity(Object entity, AbstractBaseCallback callback) {

		// Initialize the operation.
		CallbackInvoker invoker = initEntityOperation(callback, entity, MESSAGE_DATA_STORE_OPERATION_UPDATE);

		// Update the object.
		performUpdate(entity, invoker);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object   Object/s to delete. <br>
	 *                 <br>
	 * @param callback Operation response handler.<br>
	 *                 <br>
	 */
	private void handleDelete(Object object, AbstractBaseCallback callback) {
		if (object instanceof Query) {

			// Log a message.
			getScopeFacade().log("WAREWORK is going to delete the objects that given query returns in Data Store '"
					+ getName() + "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Create the message.
			String successMessage = "WAREWORK successfully deleted every object that given query returned in Data Store '"
					+ getName() + "' of Service '" + getService().getName() + "'.";

			// Create the object required to invoke the callback
			// operation.
			CallbackInvoker invoker = CallbackInvoker.createInvoker(callback, null, successMessage, null);

			// Delete the objects from the query.
			performDeleteQuery((Query<?>) object, invoker);

		} else if (ReflectionL2Helper.isArray(object)) {
			handleDeleteArray(object, callback);
		} else if (object instanceof Collection) {
			handleDeleteCollection((Collection<?>) object, callback);
		} else if (object instanceof Class) {

			// Get the type of object to delete.
			Class<?> type = (Class<?>) object;

			// Log a message.
			getScopeFacade().log("WAREWORK is going to delete every '" + type.getName() + "' object in Data Store '"
					+ getName() + "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Create the message.
			String successMessage = "WAREWORK successfully deleted every '" + type.getName()
					+ "' object in Data Store '" + getName() + "' of Service '" + getService().getName() + "'.";

			// Create the object required to invoke the callback
			// operation.
			CallbackInvoker invoker = CallbackInvoker.createInvoker(callback, null, successMessage, null);

			// Delete the objects.
			performDelete(type, invoker);

		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Delete the entity.
			handleDeleteEntity(object, callback);

		}
	}

	/**
	 * Deletes every object of an array in the Data Store.
	 * 
	 * @param array Objects to delete.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to delete the array
	 *                         of objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleDeleteArray(Object array) throws ClientException {
		if (getObjectDatastore().executeDeleteBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_DELETE);

				// Perform batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to save.
					Object entity = Array.get(array, index);

					// Save the entity.
					performDelete(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully deleted in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK deleted 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.");
			}

		} else {
			performDelete(array);
		}
	}

	/**
	 * Deletes every object of an array in the Data Store.
	 * 
	 * @param array    Objects to delete.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleDeleteArray(Object array, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeDeleteBatch()) {

			// Get the size of the array.
			int length = Array.getLength(array);

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_DELETE);

				// Perform a batch operation.
				for (int index = 0; index < length; index = index + 1) {

					// Get an entity to delete.
					Object entity = Array.get(array, index);

					// Delete the entity.
					handleDeleteEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK deleted 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given array is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Delete the entity.
			handleDeleteEntity(array, callback);

		}
	}

	/**
	 * Deletes every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to delete.<br>
	 *                   <br>
	 * @throws ClientException If there is an error when trying to delete the
	 *                         collection of objects in the Data Store.<br>
	 *                         <br>
	 */
	private void handleDeleteCollection(Collection<?> collection) throws ClientException {
		if (getObjectDatastore().executeDeleteBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Create an ID for the batch operation.
				String batchId = StringL2Helper.createSimpleID();

				// Initialize callback for batch.
				initBatch(batchId, length, MESSAGE_DATA_STORE_OPERATION_DELETE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to save.
					Object entity = iterator.next();

					// Save the entity.
					performDelete(entity);

					// Log operation.
					getScopeFacade().info("WAREWORK successfully deleted in batch operation '" + batchId + "' object '"
							+ createObjectID(entity) + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "'.");

				}

			} else {
				getScopeFacade().info("WAREWORK deleted 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.");
			}

		} else {
			performDelete(collection);
		}
	}

	/**
	 * Deletes every object of a collection in the Data Store.
	 * 
	 * @param collection Objects to delete.<br>
	 *                   <br>
	 * @param callback   Callback to handle the result of the operation.<br>
	 *                   <br>
	 */
	private void handleDeleteCollection(Collection<?> collection, AbstractBaseCallback callback) {
		if (getObjectDatastore().executeDeleteBatch()) {

			// Get the size of the collection.
			int length = collection.size();

			// Perform the batch operation.
			if (length > 0) {

				// Initialize callback for batch.
				initBatch(callback, length, MESSAGE_DATA_STORE_OPERATION_DELETE);

				// Perform a batch operation.
				for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

					// Get an entity to delete.
					Object entity = iterator.next();

					// Delete the entity.
					handleDeleteEntity(entity, callback);

					// Break loop if operation fails.
					if (callback.getControl().isFailure()) {
						return;
					}

				}

			} else {

				// Create the message.
				String successMessage = "WAREWORK deleted 0 objects in Data Store '" + getName() + "' of Service '"
						+ getService().getName() + "' because given collection is empty.";

				// Invoke callback.
				CallbackInvoker.invokeSuccess(callback, null, successMessage, null, true, null);

			}

		} else {

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Delete the entity.
			handleDeleteEntity(collection, callback);

		}
	}

	/**
	 * Delete an object in the Data Store.
	 * 
	 * @param entity   Object to delete.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 */
	private void handleDeleteEntity(Object entity, AbstractBaseCallback callback) {

		// Initialize the operation.
		CallbackInvoker invoker = initEntityOperation(callback, entity, MESSAGE_DATA_STORE_OPERATION_DELETE);

		// Delete the object.
		performDelete(entity, invoker);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Finds an object by its values.
	 * 
	 * @param filter   Filter used to find an object in the Data Store. This object
	 *                 specifies two things: first, the type of the object to search
	 *                 for in the Data Store, and second, the values that identify
	 *                 the object in the Data Store.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 * @param <E>      The type of objects to search for.<br>
	 *                 <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to execute the query
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	private <E> E handleFind(E filter) throws ClientException {

		// DO NOT FILTER QUERY WITH PAGE=1 AND PAGE_SIZE=2 BECAUSE DATA STORE
		// MAY NOT SUPPORT PAGINATION.
		List<E> queryResult = handleQuery(filter, null, null, -1, -1);

		// Process the result of the query.
		if (queryResult != null) {
			if (queryResult.size() == 1) {
				return queryResult.get(0);
			} else if (queryResult.size() > 1) {

				// Get the first object found.
				Object first = queryResult.get(0);

				// Notify about the failure.
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot find a specific instance of '" + first.getClass().getName()
								+ "' in Data Store '" + getName() + "' at Service '" + getService().getName()
								+ "' because more than one object is found for the search criteria given.",
						null, LogServiceConstants.LOG_LEVEL_WARN);

			}
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Finds an object by its values.
	 * 
	 * @param filter   Filter used to find an object in the Data Store. This object
	 *                 specifies two things: first, the type of the object to search
	 *                 for in the Data Store, and second, the values that identify
	 *                 the object in the Data Store.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	private CallbackInvoker handleFind(Object filter, AbstractBaseCallback callback) {

		// Create a callback to handle the result of a query.
		AbstractBaseCallback queryCallback = new AbstractDefaultCallback(getScopeFacade()) {

			protected void onSuccess(CallbackSuccess success) {

				// Get the result of the query.
				List<?> queryResult = (List<?>) success.getResult();

				// Get the callback provided by the user.
				AbstractCallback sourceCallback = (AbstractCallback) success.getSourceCallback();

				// Create the object required to invoke the callback
				// operation.
				CallbackInvoker invoker = CallbackInvoker.createInvoker(sourceCallback, null, null, success);

				// Process the result of the query.
				if (queryResult != null) {
					if (queryResult.size() == 1) {
						invoker.success(queryResult.get(0));
					} else if (queryResult.size() > 1) {

						// Get the first object found.
						Object first = queryResult.get(0);

						// Notify about the failure.
						invoker.failure(new ClientException(getScopeFacade(),
								"WAREWORK cannot find a specific instance of '" + first.getClass().getName()
										+ "' in Data Store '" + getName() + "' at Service '" + getService().getName()
										+ "' because more than one object is found for the search criteria given.",
								null, LogServiceConstants.LOG_LEVEL_WARN));

					} else {
						invoker.success(null);
					}
				} else {
					invoker.success(null);
				}

			}

		};

		// DO NOT FILTER QUERY WITH PAGE=1 AND PAGE_SIZE=2 BECAUSE DATA STORE
		// MAY NOT SUPPORT PAGINATION.
		return handleQuery(filter, null, null, -1, -1, queryCallback, callback).getLastInvoker();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Lists all of the objects that match the type and all non-null field values
	 * from a given object. This is done via reflecting the type and all of the
	 * fields from the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). The result of the query may
	 * return null or a list with one or multiple objects in it. The objects you may
	 * get in the list will be the same type as the object that you will provide.
	 * 
	 * @param filter    Filter used to list the objects in the Data Store. This
	 *                  object specifies two things: first, the type of the object
	 *                  to search for in the Data Store, and second, the values that
	 *                  identify the object in the Data Store.<br>
	 *                  <br>
	 * @param operators Operations for fields. When the filter for the query is
	 *                  created, the "equals to" operator is used by default in
	 *                  every expression. For example: <code>name='John'</code>. You
	 *                  can specify a different operator if you wish. With this
	 *                  method parameter, you can provide a <code>Map</code> where
	 *                  the keys represent the attribute of the object and the
	 *                  values of the Map represent the operation to perform, for
	 *                  example: <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *                  <code>name LIKE 'John'</code>). This method parameter gives
	 *                  you some extra flexibility to filter the query.<br>
	 *                  <br>
	 * @param orderBy   Order of the fields for the result of the query. With this
	 *                  parameter you can specify that the result of the query
	 *                  should be sorted by certain fields in ascending or
	 *                  descending order.<br>
	 *                  <br>
	 * @param page      Page to get from the result. When you specify the number of
	 *                  objects (check out <code>pageSize</code> argument of this
	 *                  method) that you want in the result of a query, Warework
	 *                  automatically calculates the number of pages that hold this
	 *                  number of objects. You have to pass an integer value greater
	 *                  than zero to retrieve a specific page from the result. Set
	 *                  this argument to <code>-1</code> to retrieve every object in
	 *                  one page.<br>
	 *                  <br>
	 * @param pageSize  Number of objects that you want in the result of the query.
	 *                  Set this argument to <code>-1</code> to retrieve every
	 *                  object.<br>
	 *                  <br>
	 * @param <E>       The type of objects to search for.<br>
	 *                  <br>
	 * @return Query result.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to execute the query
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	private <E> List<E> handleQuery(E filter, Map<String, Operator> operators, OrderBy orderBy, int page, int pageSize)
			throws ClientException {

		// Create a new query.
		Query<E> query = new Query<E>(getScopeFacade(), (Class<E>) filter.getClass());

		// Set the ORDER-BY clause.
		query.setOrderBy(orderBy);

		// Set the page to retrieve.
		query.setPage(page);

		// Set the maximum results per page.
		query.setPageSize(pageSize);

		// Create a WHERE clause.
		Where where = query.getWhere(true);

		// Create AND expression.
		And and = where.createAnd();

		// Append AND expressions (filter).
		try {
			createWhere(and, filter, null, operators);
		} catch (Exception e) {
			throw new ClientException(getScopeFacade(), "WAREWORK cannot list objects from Data Store '" + getName()
					+ "' at Service '" + getService().getName()
					+ "' because the following exception is thrown when the object provided to filter the query is processed: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Count the number of sub-expressions in the AND expression.
		int expressionCount = and.getExpressions().size();

		// Append the WHERE clause. DO NOT INCLUDE JUST ONE SUB-EXPRESSION
		// IN THE AND EXPRESSION. AND/OR EXPRESSIONS MUST HAVE MORE THAN ONE
		// SUB-EXPRESSION.
		if (expressionCount > 0) {
			if (expressionCount > 1) {
				where.setExpression(and);
			} else {
				where.setExpression(and.getExpressions().iterator().next());
			}
		}

		// Run the query.
		return handleQuery(query);

	}

	/**
	 * Lists all of the objects that match the type and all non-null field values
	 * from a given object. This is done via reflecting the type and all of the
	 * fields from the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). The result of the query may
	 * return null or a list with one or multiple objects in it. The objects you may
	 * get in the list will be the same type as the object that you will provide.
	 * 
	 * @param filter          Filter used to list the objects in the Data Store.
	 *                        This object specifies two things: first, the type of
	 *                        the object to search for in the Data Store, and
	 *                        second, the values that identify the object in the
	 *                        Data Store.<br>
	 *                        <br>
	 * @param operators       Operations for fields. When the filter for the query
	 *                        is created, the "equals to" operator is used by
	 *                        default in every expression. For example:
	 *                        <code>name='John'</code>. You can specify a different
	 *                        operator if you wish. With this method parameter, you
	 *                        can provide a <code>Map</code> where the keys
	 *                        represent the attribute of the object and the values
	 *                        of the Map represent the operation to perform, for
	 *                        example: <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *                        <code>name LIKE 'John'</code>). This method parameter
	 *                        gives you some extra flexibility to filter the
	 *                        query.<br>
	 *                        <br>
	 * @param orderBy         Order of the fields for the result of the query. With
	 *                        this parameter you can specify that the result of the
	 *                        query should be sorted by certain fields in ascending
	 *                        or descending order.<br>
	 *                        <br>
	 * @param page            Page to get from the result. When you specify the
	 *                        number of objects (check out <code>pageSize</code>
	 *                        argument of this method) that you want in the result
	 *                        of a query, Warework automatically calculates the
	 *                        number of pages that hold this number of objects. You
	 *                        have to pass an integer value greater than zero to
	 *                        retrieve a specific page from the result. Set this
	 *                        argument to <code>-1</code> to retrieve every object
	 *                        in one page.<br>
	 *                        <br>
	 * @param pageSize        Number of objects that you want in the result of the
	 *                        query. Set this argument to <code>-1</code> to
	 *                        retrieve every object.<br>
	 *                        <br>
	 * @param currentCallback Current callback to handle the result of the query
	 *                        operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback to handle the final result of the
	 *                        operation.<br>
	 *                        <br>
	 * @param <E>             The type of objects to search for.<br>
	 *                        <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	@SuppressWarnings("unchecked")
	private <E> CallbackInvoker handleQuery(E filter, Map<String, Operator> operators, OrderBy orderBy, int page,
			int pageSize, AbstractBaseCallback currentCallback, AbstractBaseCallback sourceCallback) {

		// Create a new query.
		Query<E> query = new Query<E>(getScopeFacade(), (Class<E>) filter.getClass());

		// Set the ORDER-BY clause.
		query.setOrderBy(orderBy);

		// Set the page to retrieve.
		query.setPage(page);

		// Set the maximum results per page.
		query.setPageSize(pageSize);

		// Create a WHERE clause.
		Where where = query.getWhere(true);

		// Create AND expression.
		And and = where.createAnd();

		// Append AND expressions (filter).
		try {
			createWhere(and, filter, null, operators);
		} catch (Exception e) {
			return CallbackInvoker.invokeFailure(currentCallback, null,
					new ClientException(getScopeFacade(), "WAREWORK cannot list objects from Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because the following exception is thrown when the object provided to filter the query is processed: "
							+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN));
		}

		// Count the number of sub-expressions in the AND expression.
		int expressionCount = and.getExpressions().size();

		// Append the WHERE clause. DO NOT INCLUDE JUST ONE SUB-EXPRESSION
		// IN THE AND EXPRESSION. AND/OR EXPRESSIONS MUST HAVE MORE THAN ONE
		// SUB-EXPRESSION.
		if (expressionCount > 0) {
			if (expressionCount > 1) {
				where.setExpression(and);
			} else {
				where.setExpression(and.getExpressions().iterator().next());
			}
		}

		// Run the query.
		return handleQuery(query, currentCallback, sourceCallback);

	}

	/**
	 * Lists all of the objects that match the type from a given class. This is done
	 * via reflecting the type and all of the fields from the given object, and
	 * building a query expression. The result of the query may return null or a
	 * list with one or multiple objects in it. The objects you may get in the list
	 * will be the same type as the object that you will provide.
	 * 
	 * @param filter          Filter used to list the objects in the Data Store.<br>
	 *                        <br>
	 * @param operators       Operations for fields. When the filter for the query
	 *                        is created, the "equals to" operator is used by
	 *                        default in every expression. You can specify a
	 *                        different operator if you wish. With this method
	 *                        parameter, you can provide a <code>Map</code> where
	 *                        the keys represent the attribute of the object and the
	 *                        values of the Map represent the operation to perform,
	 *                        for example: <code>mymap.put("name",
	 *            Operator.IS_NOT_NULL)</code> (it creates the expression:
	 *                        <code>name IS_NOT_NULL</code>). This method parameter
	 *                        gives you some extra flexibility to filter the query.
	 *                        Only two operators can be used:
	 *                        <code>Operator.IS_NULL</code> and
	 *                        <code>Operator.IS_NOT_NULL</code> (this is because you
	 *                        cannot provide the values of the fields in a class
	 *                        object).<br>
	 *                        <br>
	 * @param orderBy         Order of the fields for the result of the query. With
	 *                        this parameter you can specify that the result of the
	 *                        query should be sorted by certain fields in ascending
	 *                        or descending order.<br>
	 *                        <br>
	 * @param page            Page to get from the result. When you specify the
	 *                        number of objects (check out <code>pageSize</code>
	 *                        argument of this method) that you want in the result
	 *                        of a query, Warework automatically calculates the
	 *                        number of pages that hold this number of objects. You
	 *                        have to pass an integer value greater than zero to
	 *                        retrieve a specific page from the result. Set this
	 *                        argument to <code>-1</code> to retrieve every object
	 *                        in one page.<br>
	 *                        <br>
	 * @param pageSize        Number of objects that you want in the result of the
	 *                        query. Set this argument to <code>-1</code> to
	 *                        retrieve every object.<br>
	 *                        <br>
	 * @param currentCallback Current callback to handle the result of the query
	 *                        operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback to handle the final result of the
	 *                        operation.<br>
	 *                        <br>
	 * @param <E>             The type of objects to search for.<br>
	 *                        <br>
	 * @return Query result.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to execute the query
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	private <E> List<E> handleQuery(Class<E> filter, Map<String, Operator> operators, OrderBy orderBy, int page,
			int pageSize) throws ClientException {

		// Create a new query.
		Query<E> query = new Query<E>(getScopeFacade(), filter);

		// Set the ORDER-BY clause.
		query.setOrderBy(orderBy);

		// Set the page to retrieve.
		query.setPage(page);

		// Set the maximum results per page.
		query.setPageSize(pageSize);

		// Create the WHERE clause if any operator exist.
		if ((operators != null) && (operators.size() > 0)) {

			// Get the fields.
			Set<String> fields = operators.keySet();

			// Create a WHERE clause.
			Where where = query.getWhere(true);

			// Create AND expression.
			And and = where.createAnd();

			// Filter only IS_NULL or IS_NOT_NULL operators.
			for (Iterator<String> iterator = fields.iterator(); iterator.hasNext();) {

				// Get one field of the object. It is used to filter the
				// results.
				String field = iterator.next();

				// Get the operation to perform.
				Operator operator = operators.get(field);

				// Append AND expression (filter).
				if ((operator.equals(Operator.IS_NULL)) || (operator.equals(Operator.IS_NOT_NULL))) {
					appendExpression(and, field, null, operators);
				} else {
					throw new ClientException(getScopeFacade(), "WAREWORK cannot list objects from Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because you are using invalid operators. When a class is provided to run a query, you can only use Operator.IS_NULL or Operator.IS_NOT_NULL to filter the query.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

			// Append the WHERE clause.
			if (!and.isEmpty()) {
				where.setExpression(and);
			}

		}

		// Run query .
		return handleQuery(query);

	}

	/**
	 * Lists all of the objects that match the type from a given class. This is done
	 * via reflecting the type and all of the fields from the given object, and
	 * building a query expression. The result of the query may return null or a
	 * list with one or multiple objects in it. The objects you may get in the list
	 * will be the same type as the object that you will provide.
	 * 
	 * @param filter          Filter used to list the objects in the Data Store.<br>
	 *                        <br>
	 * @param operators       Operations for fields. When the filter for the query
	 *                        is created, the "equals to" operator is used by
	 *                        default in every expression. You can specify a
	 *                        different operator if you wish. With this method
	 *                        parameter, you can provide a <code>Map</code> where
	 *                        the keys represent the attribute of the object and the
	 *                        values of the Map represent the operation to perform,
	 *                        for example: <code>mymap.put("name",
	 *            Operator.IS_NOT_NULL)</code> (it creates the expression:
	 *                        <code>name IS_NOT_NULL</code>). This method parameter
	 *                        gives you some extra flexibility to filter the query.
	 *                        Only two operators can be used:
	 *                        <code>Operator.IS_NULL</code> and
	 *                        <code>Operator.IS_NOT_NULL</code> (this is because you
	 *                        cannot provide the values of the fields in a class
	 *                        object).<br>
	 *                        <br>
	 * @param orderBy         Order of the fields for the result of the query. With
	 *                        this parameter you can specify that the result of the
	 *                        query should be sorted by certain fields in ascending
	 *                        or descending order.<br>
	 *                        <br>
	 * @param page            Page to get from the result. When you specify the
	 *                        number of objects (check out <code>pageSize</code>
	 *                        argument of this method) that you want in the result
	 *                        of a query, Warework automatically calculates the
	 *                        number of pages that hold this number of objects. You
	 *                        have to pass an integer value greater than zero to
	 *                        retrieve a specific page from the result. Set this
	 *                        argument to <code>-1</code> to retrieve every object
	 *                        in one page.<br>
	 *                        <br>
	 * @param pageSize        Number of objects that you want in the result of the
	 *                        query. Set this argument to <code>-1</code> to
	 *                        retrieve every object.<br>
	 *                        <br>
	 * @param currentCallback Current callback to handle the result of the query
	 *                        operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback to handle the final result of the
	 *                        operation.<br>
	 *                        <br>
	 * @param <E>             The type of objects to search for.<br>
	 *                        <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	private <E> CallbackInvoker handleQuery(Class<E> filter, Map<String, Operator> operators, OrderBy orderBy, int page,
			int pageSize, AbstractBaseCallback currentCallback, AbstractBaseCallback sourceCallback) {

		// Create a new query.
		Query<E> query = new Query<E>(getScopeFacade(), filter);

		// Set the ORDER-BY clause.
		query.setOrderBy(orderBy);

		// Set the page to retrieve.
		query.setPage(page);

		// Set the maximum results per page.
		query.setPageSize(pageSize);

		// Create the WHERE clause if any operator exist.
		if ((operators != null) && (operators.size() > 0)) {

			// Get the fields.
			Set<String> fields = operators.keySet();

			// Create a WHERE clause.
			Where where = query.getWhere(true);

			// Create AND expression.
			And and = where.createAnd();

			// Filter only IS_NULL or IS_NOT_NULL operators.
			for (Iterator<String> iterator = fields.iterator(); iterator.hasNext();) {

				// Get one field of the object. It is used to filter the
				// results.
				String field = iterator.next();

				// Get the operation to perform.
				Operator operator = operators.get(field);

				// Append AND expression (filter).
				if ((operator.equals(Operator.IS_NULL)) || (operator.equals(Operator.IS_NOT_NULL))) {
					appendExpression(and, field, null, operators);
				} else {
					return CallbackInvoker.invokeFailure(currentCallback, null,
							new ClientException(getScopeFacade(), "WAREWORK cannot list objects from Data Store '" + getName()
									+ "' at Service '" + getService().getName()
									+ "' because you are using invalid operators. When a class is provided to run a query, you can only use Operator.IS_NULL or Operator.IS_NOT_NULL to filter the query.",
									null, LogServiceConstants.LOG_LEVEL_WARN));
				}

			}

			// Append the WHERE clause.
			if (!and.isEmpty()) {
				where.setExpression(and);
			}

		}

		// Run the query.
		return handleQuery(query, currentCallback, sourceCallback);

	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the Data
	 * Store.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @param page         Page to get from the result. When you specify the number
	 *                     of objects (check out <code>pageSize</code> argument of
	 *                     this method) that you want in the result of a query,
	 *                     Warework automatically calculates the number of pages
	 *                     that hold this number of objects. You have to pass an
	 *                     integer value greater than zero to retrieve a specific
	 *                     page from the result. Set this argument to
	 *                     <code>-1</code> to retrieve every object in
	 * 
	 * @param pageSize     Number of objects that you want in the result of the
	 *                     query. Set this argument to <code>-1</code> to retrieve
	 *                     every object.<br>
	 *                     <br>
	 * @param <E>          The type of objects to search for.<br>
	 *                     <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to execute the query
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	private <E> List<E> handleQueryByName(String providerName, String queryName, Map<String, Object> values, int page,
			int pageSize) throws ClientException {

		// Load the query.
		Object statement = getStatement(providerName, queryName);

		// Validate query type and execute in Data Store.
		if (statement instanceof Query) {

			// Get the query.
			Query<?> query = (Query<?>) statement;

			// Update the query with the values provided by the user.
			updateQuery(query, values, page, pageSize);

			// Run the query.
			return (List<E>) handleQuery(query);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute query '" + queryName + "' in Data Store '" + getName() + "' at Service '"
							+ getService().getName()
							+ "' because it does not exists (provider returns null) or its type is not an instance of '"
							+ Query.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the Data
	 * Store.
	 * 
	 * @param providerName    Name of the Provider where to load the query.<br>
	 *                        <br>
	 * @param queryName       Name of the query to load from the Provider.<br>
	 *                        <br>
	 * @param values          Map where the keys represent variable names in the
	 *                        query loaded from the Provider and the values of the
	 *                        Map represent the values that will replace the
	 *                        variables. Pass <code>null</code> to this parameter to
	 *                        make no changes in the query loaded.<br>
	 *                        <br>
	 * @param page            Page to get from the result. When you specify the
	 *                        number of objects (check out <code>pageSize</code>
	 *                        argument of this method) that you want in the result
	 *                        of a query, Warework automatically calculates the
	 *                        number of pages that hold this number of objects. You
	 *                        have to pass an integer value greater than zero to
	 *                        retrieve a specific page from the result. Set this
	 *                        argument to <code>-1</code> to retrieve every object
	 *                        in
	 * 
	 * @param pageSize        Number of objects that you want in the result of the
	 *                        query. Set this argument to <code>-1</code> to
	 *                        retrieve every object.<br>
	 *                        <br>
	 * @param currentCallback Current callback to handle the result of the query
	 *                        operation.<br>
	 *                        <br>
	 * @param sourceCallback  Main callback to handle the final result of the
	 *                        operation.<br>
	 *                        <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	private CallbackInvoker handleQueryByName(String providerName, String queryName, Map<String, Object> values,
			int page, int pageSize, AbstractBaseCallback currentCallback, AbstractBaseCallback sourceCallback) {

		// Load the query.
		Object statement = getStatement(providerName, queryName);

		// Validate query type and execute in Data Store.
		if (statement instanceof Query) {

			// Get the query.
			Query<?> query = (Query<?>) statement;

			// Update the query with the values provided by the user.
			updateQuery(query, values, page, pageSize);

			// Run the query.
			return handleQuery(query, currentCallback, sourceCallback);

		} else {
			return CallbackInvoker.invokeFailure(currentCallback, sourceCallback,
					new ClientException(getScopeFacade(), "WAREWORK cannot execute query '" + queryName + "' in Data Store '"
							+ getName() + "' at Service '" + getService().getName()
							+ "' because it does not exists (provider returns null) or its type is not an instance of '"
							+ Query.class.getName() + "'.", null, LogServiceConstants.LOG_LEVEL_WARN));
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Counts the number of objects of the specified query.
	 * 
	 * @param query    Object that specifies what to search for. It defines the type
	 *                 of the object to search for, how to filter the result, which
	 *                 order to apply and the page to retrieve.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 * @param <E>      The type of objects to count.<br>
	 *                 <br>
	 * @return Objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the objects
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	private <E> int handleCountQuery(Query<E> query) throws ClientException {

		// Log a message.
		getScopeFacade().log(
				"WAREWORK is going to count the objects of the following query in Data Store '" + getName()
						+ "' of Service '" + getService().getName() + "':\n" + query.toString(),
				LogServiceConstants.LOG_LEVEL_DEBUG);

		// Counts objects.
		int count = performCountQuery(query);

		// Create the message for the successful operation.
		getScopeFacade().log("WAREWORK successfully counted the objects in Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_DEBUG);

		// Return count.
		return count;

	}

	/**
	 * Counts the number of objects of the specified query.
	 * 
	 * @param query    Object that specifies what to search for. It defines the type
	 *                 of the object to search for, how to filter the result, which
	 *                 order to apply and the page to retrieve.<br>
	 *                 <br>
	 * @param callback Callback to handle the result of the operation.<br>
	 *                 <br>
	 * @param <E>      The type of objects to count.<br>
	 *                 <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	private <E> CallbackInvoker handleCountQuery(Query<E> query, AbstractBaseCallback callback) {

		// Log a message.
		getScopeFacade().log(
				"WAREWORK is going to count the objects of the following query in Data Store '" + getName()
						+ "' of Service '" + getService().getName() + "':\n" + query.toString(),
				LogServiceConstants.LOG_LEVEL_DEBUG);

		// Create the message for the successful operation.
		String successMessage = "WAREWORK successfully counted the objects in Data Store '" + getName()
				+ "' of Service '" + getService().getName() + "'.";

		// Initialize callback batch parameters.
		callback.getControl().destroyBatch();

		// Create the object required to invoke the callback operation.
		CallbackInvoker invoker = CallbackInvoker.createInvoker(callback, null, successMessage, null);

		// Counts objects.
		performCountQuery(query, invoker);

		// Return the last callback invoker used.
		return invoker.getLastInvoker();

	}

	/**
	 * Counts the number of objects of the specified query.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @return Objects found.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to count the objects
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	private int handleCountByName(String providerName, String queryName, Map<String, Object> values)
			throws ClientException {

		// Load the query.
		Object statement = getStatement(providerName, queryName);

		// Validate query type and perform count.
		if (statement instanceof Query) {

			// Get the query.
			Query<?> query = (Query<?>) statement;

			// Update the query with the values provided by the user.
			updateQuery(query, values, -1, -1);

			// Run the query.
			return handleCountQuery(query);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot count objects with query '" + queryName + "' in Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because it does not exists (Provider returns null) or its type is not an instance of '"
							+ Query.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Counts the number of objects of the specified query.
	 * 
	 * @param providerName Name of the Provider where to load the query.<br>
	 *                     <br>
	 * @param queryName    Name of the query to load from the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the query
	 *                     loaded from the Provider and the values of the Map
	 *                     represent the values that will replace the variables.
	 *                     Pass <code>null</code> to this parameter to make no
	 *                     changes in the query loaded.<br>
	 *                     <br>
	 * @param callback     Callback to handle the result of the operation.<br>
	 *                     <br>
	 * @return Object used to invoke the callback operation. This object holds the
	 *         result of the operation.<br>
	 *         <br>
	 */
	private CallbackInvoker handleCountByName(String providerName, String queryName, Map<String, Object> values,
			AbstractBaseCallback callback) {

		// Load the query.
		Object statement = getStatement(providerName, queryName);

		// Validate query type and perform count.
		if (statement instanceof Query) {

			// Get the query.
			Query<?> query = (Query<?>) statement;

			// Update the query with the values provided by the user.
			updateQuery(query, values, -1, -1);

			// Run the query.
			return handleCountQuery(query, callback);

		} else {
			return CallbackInvoker.invokeFailure(callback, null,
					new ClientException(getScopeFacade(), "WAREWORK cannot count objects with query '" + queryName
							+ "' in Data Store '" + getName() + "' at Service '" + getService().getName()
							+ "' because it does not exists (Provider returns null) or its type is not an instance of '"
							+ Query.class.getName() + "'.", null, LogServiceConstants.LOG_LEVEL_WARN));
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the WHERE clause recursively.
	 * 
	 * @param expression    Composed expression (AND, OR) where to add more
	 *                      sub-expressions.<br>
	 *                      <br>
	 * @param entity        Object where to reflect the attributes.<br>
	 *                      <br>
	 * @param attributePath Path to find the attribute in the object.<br>
	 *                      <br>
	 * @param operators     Operations for fields. With this method parameter you
	 *                      can provide a Map where the keys represent the attribute
	 *                      of the object and the values of the Map represent the
	 *                      operation to perform, for example:
	 *                      <code>mymap.put("name",
	 *            Operator.LIKE)</code>.<br>
	 *                      <br>
	 * @throws SecurityException         If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 * @throws IllegalArgumentException  If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 * @throws NoSuchMethodException     If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 * @throws IllegalAccessException    If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 * @throws InvocationTargetException If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 * @throws NoSuchFieldException      If there is an error when trying to create
	 *                                   the WHERE clause.<br>
	 *                                   <br>
	 */
	private void createWhere(AbstractComposedExpression expression, Object entity, String attributePath,
			Map<String, Operator> operators)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {

		// Class where to extract the attributes.
		Class<?> entityType = null;
		if (entity instanceof Class) {
			entityType = (Class<?>) entity;
		} else {
			entityType = entity.getClass();
		}

		// Get the name of the attributes of the object or class.
		String[] attributeNames = ReflectionL2Helper.getAttributes(entityType);

		// Append expressions.
		if (attributeNames != null) {

			// Process each attribute.
			for (int i = 0; i < attributeNames.length; i++) {

				// Get the name of an attribute.
				String attributeName = attributeNames[i];

				// Process only accepted attributes.
				if (processAttribute(entity, attributeName)) {

					// Get the value of the attribute.
					Object attributeValue = ReflectionL2Helper.getAttributeValue(entity, attributeName);

					// Create the path to find the attribute in the object.
					String attributeKey = null;
					if (attributePath == null) {
						attributeKey = attributeName;
					} else {
						attributeKey = attributePath + StringL2Helper.CHARACTER_PERIOD + attributeName;
					}

					// Append the expression.
					if (attributeValue == null) {
						appendExpression(expression, attributeKey, null, operators);
					} else if ((!(attributeValue instanceof Collection))
							&& (!ReflectionL2Helper.isArray(attributeValue))) {
						if (SIMPLE_TYPES.contains(attributeValue.getClass())) {
							appendExpression(expression, attributeKey, attributeValue, operators);
						} else {
							createWhere(expression, attributeValue, attributeKey, operators);
						}
					}

				}

			}
		}

	}

	/**
	 * Adds an expression in a given composed expression.
	 * 
	 * @param expression     Composed expression (AND, OR) where to add more
	 *                       sub-expressions.<br>
	 *                       <br>
	 * @param attributePath  Path to find the attribute in the object.<br>
	 *                       <br>
	 * @param attributeName  Name of the attribute.<br>
	 *                       <br>
	 * @param attributeValue Value for the attribute.<br>
	 *                       <br>
	 * @param operators      Operations for fields. With this method parameter you
	 *                       can provide a Map where the keys represent the
	 *                       attribute of the object and the values of the Map
	 *                       represent the operation to perform, for example:
	 *                       <code>mymap.put("name",
	 *            Operator.LIKE)</code>.<br>
	 *                       <br>
	 */
	private void appendExpression(AbstractComposedExpression expression, String attributeKey, Object attributeValue,
			Map<String, Operator> operators) {

		// Get the WHERE clause.
		Where where = expression.getWhere();

		// Add the expression.
		if ((operators != null) && (operators.containsKey(attributeKey))) {

			// Get the operator type.
			Operator operator = operators.get(attributeKey);

			// Add a specific expression.
			if (operator.equals(Operator.IS_NULL)) {
				expression.add(where.createIsNull(attributeKey));
			} else if (operator.equals(Operator.IS_NOT_NULL)) {
				expression.add(where.createIsNotNull(attributeKey));
			} else if (attributeValue != null) {
				if (operator.equals(Operator.EQUAL_TO)) {
					expression.add(where.createEqualToValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.NOT_EQUAL_TO)) {
					expression.add(where.createNotEqualToValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.LESS_THAN)) {
					expression.add(where.createLessThanValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.LESS_THAN_OR_EQUAL_TO)) {
					expression.add(where.createLessThanOrEqualToValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.GREATER_THAN)) {
					expression.add(where.createGreaterThanValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.GREATER_THAN_OR_EQUAL_TO)) {
					expression.add(where.createGreaterThanOrEqualToValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.LIKE)) {
					expression.add(where.createLikeValue(attributeKey, attributeValue));
				} else if (operator.equals(Operator.NOT_LIKE)) {
					expression.add(where.createNotLikeValue(attributeKey, attributeValue));
				}
			}

		} else if (attributeValue != null) {
			expression.add(where.createEqualToValue(attributeKey, attributeValue));
		}

	}

	/**
	 * Creates a collection to identify simple data types.
	 * 
	 * @return Collection with simple data types.<br>
	 *         <br>
	 */
	private static Collection<Class<?>> createSimpleTypes() {

		// Create the collection.
		Collection<Class<?>> result = new HashSet<Class<?>>();

		// Set the simple data types.
		result.add(Boolean.class);
		result.add(Character.class);
		result.add(Byte.class);
		result.add(Short.class);
		result.add(Integer.class);
		result.add(Long.class);
		result.add(BigInteger.class);
		result.add(Float.class);
		result.add(BigDecimal.class);
		result.add(Double.class);
		result.add(String.class);
		result.add(java.util.Date.class);
		result.add(java.sql.Date.class);
		result.add(java.sql.Time.class);
		result.add(java.sql.Timestamp.class);
		result.add(java.util.Calendar.class);

		// return the collection.
		return result;

	}

	/**
	 * Initializes a batch operation and logs about the operation to perform.
	 * 
	 * @param batchId          Batch operation ID.<br>
	 *                         <br>
	 * @param length           Number of items to process in the batch
	 *                         operation.<br>
	 *                         <br>
	 * @param operationMessage Word that identifies the operation to perform.<br>
	 *                         <br>
	 */
	private void initBatch(String batchId, int length, String operationMessage) {
		if (length > 1) {
			getScopeFacade().log("WAREWORK started batch operation '" + batchId + "' in Data Store '" + getName()
					+ "' of Service '" + getService().getName() + "' to " + operationMessage
					+ StringL2Helper.CHARACTER_SPACE + length + " objects.", LogServiceConstants.LOG_LEVEL_INFO);
		} else {
			getScopeFacade().log(
					"WAREWORK started batch operation '" + batchId + "' in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "' to " + operationMessage + " one object.",
					LogServiceConstants.LOG_LEVEL_INFO);
		}
	}

	/**
	 * Initializes a batch operation and logs about the operation to perform.
	 * 
	 * @param callback         Callback to configure for the batch operation.<br>
	 *                         <br>
	 * @param length           Number of items to process in the batch
	 *                         operation.<br>
	 *                         <br>
	 * @param operationMessage Word that identifies the operation to perform.<br>
	 *                         <br>
	 */
	private void initBatch(AbstractBaseCallback callback, int length, String operationMessage) {

		// Initialize callback.
		callback.getControl().initBatch(length);

		// Log batch operation.
		initBatch(callback.getBatch().id(), length, operationMessage);

	}

	/**
	 * Initializes an operation to perform with an entity.
	 * 
	 * @param callback         Callback to handle the result of the operation.<br>
	 *                         <br>
	 * @param entity           Object to save/update/delete.<br>
	 *                         <br>
	 * @param operationMessage Word that identifies the operation to perform.<br>
	 *                         <br>
	 * @return Object required to invoke the callback operation.<br>
	 *         <br>
	 */
	private CallbackInvoker initEntityOperation(AbstractBaseCallback callback, Object entity, String operationMessage) {

		// Get the ID of the object.
		String objectId = createObjectID(entity);

		// Log a message.
		getScopeFacade().log("WAREWORK is going to " + operationMessage + " object '" + objectId + "' in Data Store '"
				+ getName() + "' of Service '" + getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_DEBUG);

		// Create the message.
		String successMessage = null;
		if (callback.getBatch() != null) {
			successMessage = "WAREWORK successfully " + operationMessage + "d in batch operation '"
					+ callback.getBatch().id() + "' object '" + objectId + "' in Data Store '" + getName()
					+ "' of Service '" + getService().getName() + "'.";
		} else {
			successMessage = "WAREWORK successfully " + operationMessage + "d object '" + objectId + "' in Data Store '"
					+ getName() + "' of Service '" + getService().getName() + "'.";
		}

		// Create the object required to invoke the callback
		// operation.
		return CallbackInvoker.createInvoker(callback, null, successMessage, null);

	}

}
