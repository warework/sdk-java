package com.warework.service.datastore.view;

import java.util.List;
import java.util.Map;

import com.warework.core.callback.AbstractCallback;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.data.OrderBy;
import com.warework.service.datastore.query.oo.Operator;
import com.warework.service.datastore.query.oo.Query;

/**
 * View for Object Data Stores.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface ObjectDatastoreView extends DatastoreView {

	/**
	 * Saves an object or a collection of objects in a Data Store.
	 * 
	 * @param object
	 *            Object/s to save. You can provide a simple Java Bean, an array
	 *            or a <code>java.util.Collection</code>. If this object is not
	 *            an array nor a <code>java.util.Collection</code> then this
	 *            method tries to save the object directly in the underlying
	 *            Data Store. When it represents an array or a
	 *            <code>java.util.Collection</code> then a batch operation is
	 *            perfomed. Batch operations are handled directly by the Data
	 *            Store if they support it. Otherwise, this method extracts
	 *            every object from the array/collection and tries to save each
	 *            one in the Data Store.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to save the object in the
	 *             Data Store.<br>
	 * <br>
	 */
	void save(Object object) throws ClientException;

	/**
	 * Saves an object or a collection of objects in a Data Store and invokes a
	 * callback method for every object saved or when any error is found.
	 * 
	 * @param object
	 *            Object/s to save. You can provide a simple Java Bean, an array
	 *            or a <code>java.util.Collection</code>. If this object is not
	 *            an array nor a <code>java.util.Collection</code> then this
	 *            method tries to save the object directly in the underlying
	 *            Data Store and after that invokes the callback
	 *            <code>onSuccess</code> / <code>onFailure</code> method just
	 *            one time. When it represents an array or a
	 *            <code>java.util.Collection</code> then a batch operation is
	 *            perfomed. Batch operations are handled directly by the Data
	 *            Store if they support it. If so, a callback method is invoked
	 *            just one time after operation is done. Otherwise, this method
	 *            extracts every object from the array/collection, tries to save
	 *            each one in the Data Store and invokes the
	 *            <code>onSuccess</code> callback method for each object saved
	 *            or <code>onFailure</code> one time if any error is found. This
	 *            allows you to track the progress of the operation to perform.
	 *            If the underlying Data Store supports batch operations but you
	 *            need to track in detail the progress of each object saved,
	 *            then you should configure the Connector of the Data Store with
	 *            <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *            <code>true</code>.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve saved object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void save(Object object, AbstractCallback callback);

	/**
	 * Updates an object or a collection of objects in a Data Store.
	 * 
	 * @param object
	 *            Object/s to update. You can provide a simple Java Bean, an
	 *            array or a <code>java.util.Collection</code>. If this object
	 *            is not an array nor a <code>java.util.Collection</code> then
	 *            this method tries to update the object directly in the
	 *            underlying Data Store. When it represents an array or a
	 *            <code>java.util.Collection</code> then a batch operation is
	 *            perfomed. Batch operations are handled directly by the Data
	 *            Store if they support it. Otherwise, this method extracts
	 *            every object from the array/collection and tries to update
	 *            each one in the Data Store.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the object/s in
	 *             the Data Store.<br>
	 * <br>
	 */
	void update(Object object) throws ClientException;

	/**
	 * Updates an object or a collection of objects in a Data Store and invokes
	 * a callback method for every object updated or when any error is found.
	 * 
	 * @param object
	 *            Object/s to update. You can provide a simple Java Bean, an
	 *            array or a <code>java.util.Collection</code>. If this object
	 *            is not an array nor a <code>java.util.Collection</code> then
	 *            this method tries to update the object directly in the
	 *            underlying Data Store and invokes a callback
	 *            <code>onSuccess</code> / <code>onFailure</code> method just
	 *            one time. When it represents an array or a
	 *            <code>java.util.Collection</code> then a batch operation is
	 *            perfomed. Batch operations are handled directly by the Data
	 *            Store if they support it. If so, a callback method is invoked
	 *            just one time after operation is done. Otherwise, this method
	 *            extracts every object from the array/collection, tries to
	 *            update each one in the Data Store and invokes the
	 *            <code>onSuccess</code> callback method for each object updated
	 *            or <code>onFailure</code> one time if any error is found. This
	 *            allows you to track the progress of the operation to perform.
	 *            If the underlying Data Store supports batch operations but you
	 *            need to track in detail the progress of each object updated,
	 *            then you should configure the Connector of the Data Store with
	 *            <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *            <code>true</code>.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve updated object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void update(Object object, AbstractCallback callback);

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object
	 *            Object/s to delete. You can provide a simple Java Bean (to
	 *            remove just one entity in the Data Store), a class (to remove
	 *            every object of the specified type), a <code>Query</code>
	 *            object (to remove every object the query returns), an array or
	 *            a <code>java.util.Collection</code> (to remove multiple
	 *            objects at once). When this object represents an array or a
	 *            <code>java.util.Collection</code> then a batch operation is
	 *            perfomed. Batch operations are handled directly by the Data
	 *            Store if they support it. Otherwise, this method extracts
	 *            every object from the array/collection and tries to delete
	 *            each one in the Data Store. If the underlying Data Store
	 *            supports batch operations but you need to track in detail the
	 *            progress of each object deleted, then you should configure the
	 *            Connector of the Data Store with
	 *            <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *            <code>true</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to delete the object/s in
	 *             the Data Store.<br>
	 * <br>
	 */
	void delete(Object object) throws ClientException;

	/**
	 * Deletes every object of the same type.
	 * 
	 * @param type
	 *            Type of objects to delete.<br>
	 * <br>
	 * @param <E>
	 *            The type of object to delete.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to delete the object/s in
	 *             the Data Store.<br>
	 * <br>
	 */
	<E> void delete(Class<E> type) throws ClientException;

	/**
	 * Deletes an object in a Data Store. It can also delete multiple objects at
	 * once if you provide a <code>Query</code> object, a class (to remove every
	 * object), an array or a <code>java.util.Collection</code>.
	 * 
	 * @param object
	 *            Object/s to delete. You can provide a simple Java Bean (to
	 *            remove just one entity in the Data Store), a class (to remove
	 *            every object of the specified type), a <code>Query</code>
	 *            object (to remove every object the query returns), an array or
	 *            a <code>java.util.Collection</code> (to remove multiple
	 *            objects at once). If this object is a Java Bean then this
	 *            method tries to delete the object directly in the underlying
	 *            Data Store and invokes a callback <code>onSuccess</code> /
	 *            <code>onFailure</code> method just one time. When it
	 *            represents an array or a <code>java.util.Collection</code>
	 *            then a batch operation is perfomed. Batch operations are
	 *            handled directly by the Data Store if they support it. If so,
	 *            a callback method is invoked just one time after operation is
	 *            done. Otherwise, this method extracts every object from the
	 *            array/collection, tries to delete each one in the Data Store
	 *            and invokes the <code>onSuccess</code> callback method for
	 *            each object deleted or <code>onFailure</code> one time if any
	 *            error is found. This allows you to track the progress of the
	 *            operation to perform. If the underlying Data Store supports
	 *            batch operations but you need to track in detail the progress
	 *            of each object deleted, then you should configure the
	 *            Connector of the Data Store with
	 *            <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to
	 *            <code>true</code>. If you provide a
	 *            <code>com.warework.service.datastore.query.oo.Query</code>
	 *            object, this method can perform two different actions: [1] if
	 *            the underlying Data Store cannot directly delete the objects
	 *            from a given query, then this method first executes a query
	 *            with the <code>Query</code> object and after that deletes
	 *            every object of the collection returned by the query (a batch
	 *            operation if performed); [2] if Data Store can delete objects
	 *            directly from the query then this operation is executed in one
	 *            single step. Please review the documentation of the Data Store
	 *            for further details about how this operation is supported.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve deleted object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void delete(Object object, AbstractCallback callback);

	/**
	 * Deletes every object of the same type.
	 * 
	 * @param type
	 *            Type of objects to delete.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve deleted object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 * @param <E>
	 *            The type of object to delete.<br>
	 * <br>
	 */
	<E> void delete(Class<E> type, AbstractCallback callback);

	/**
	 * Loads a query from the default Provider of this View and deletes every
	 * object specified by the query.
	 * 
	 * @param name
	 *            Name of the query to load from the default Provider defined
	 *            for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the
	 *            <code>Map</code> represent the values that will replace the
	 *            variables. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to delete the object/s in
	 *             the Data Store.<br>
	 * <br>
	 */
	void executeDeleteByName(String name, Map<String, Object> values)
			throws ClientException;

	/**
	 * Loads a query from a Provider and deletes every object specified by the
	 * query.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the
	 *            <code>Map</code> represent the values that will replace the
	 *            variables. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to delete the object/s in
	 *             the Data Store.<br>
	 * <br>
	 */
	void executeDeleteByName(String providerName, String queryName,
			Map<String, Object> values) throws ClientException;

	/**
	 * Loads a query from the default Provider of this View and deletes every
	 * object specified by the query.
	 * 
	 * @param name
	 *            Name of the query to load from the default Provider defined
	 *            for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the
	 *            <code>Map</code> represent the values that will replace the
	 *            variables. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve deleted object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeDeleteByName(String name, Map<String, Object> values,
			AbstractCallback callback);

	/**
	 * Loads a query from a Provider and deletes every object specified by the
	 * query.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the
	 *            <code>Map</code> represent the values that will replace the
	 *            variables. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.You can retrieve deleted object in
	 *            the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeDeleteByName(String providerName, String queryName,
			Map<String, Object> values, AbstractCallback callback);

	/**
	 * Finds an object by its values. This method returns the object of the Data
	 * Store which matches the type and all non-null field values from a given
	 * object. This is done via reflecting the type and all of the fields from
	 * the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). So, the object you will
	 * get will be the same type as the object that you will provide. The result
	 * of the query must return one single object in order to avoid an
	 * exception. In many cases, you may only need to provide the ID fields of
	 * the object to retrieve one single object. In relational databases like
	 * Oracle or MySQL, these ID fields are the primary keys.
	 * 
	 * @param filter
	 *            Filter used to find an object in the Data Store. This object
	 *            specifies two things: first, the type of the object to search
	 *            for in the Data Store, and second, the values that identify
	 *            the object in the Data Store.<br>
	 * <br>
	 * @return Object from the Data Store that matches the type and the values
	 *         of the given object. If more than one object is found in the Data
	 *         Store, then an exception is thrown. If no objects are found, then
	 *         this method returns <code>null</code>.<br>
	 * <br>
	 * @param <E>
	 *            The type of object to search for.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to find the object in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> E find(E filter) throws ClientException;

	/**
	 * Finds an object by its values. This method returns the object of the Data
	 * Store which matches the type and all non-null field values from a given
	 * object. This is done via reflecting the type and all of the fields from
	 * the given object, and building a query expression where all
	 * non-null-value fields are combined with AND expressions (for example:
	 * <code>name='John' AND color='red' ...</code> ). So, the object you will
	 * get will be the same type as the object that you will provide. The result
	 * of the query must return one single object in order to avoid an
	 * exception. In many cases, you may only need to provide the ID fields of
	 * the object to retrieve one single object. In relational databases like
	 * Oracle or MySQL, these ID fields are the primary keys.
	 * 
	 * @param filter
	 *            Filter used to find an object in the Data Store. This object
	 *            specifies two things: first, the type of the object to search
	 *            for in the Data Store, and second, the values that identify
	 *            the object in the Data Store.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the result of the
	 *            operation in the <code>onSuccess</code> method of the
	 *            callback. <br>
	 * <br>
	 */
	void find(Object filter, AbstractCallback callback);

	/**
	 * Lists all of the objects that match the type and all non-null field
	 * values from a given object. This is done via reflecting the type and all
	 * of the fields from the given object, and building a query expression
	 * where all non-null-value fields are combined with AND expressions (for
	 * example: <code>name='John' AND color='red' ...</code> ). The result of
	 * the query may return null or a list with one or multiple objects in it.
	 * The objects you may get in the list will be the same type as the object
	 * that you will provide.
	 * 
	 * @param filter
	 *            Filter used to list the objects in the Data Store. You can
	 *            filter by type or by type and values. When this filter
	 *            represents a non-class object (a Java Bean for example), this
	 *            object specifies two things: first, the type of the object to
	 *            search for in the Data Store, and second, the values that
	 *            identify the object in the Data Store. This is when you filter
	 *            by type and values. You can also provide a class as a filter
	 *            to search just by type.<br>
	 * <br>
	 * @param operators
	 *            Operations for fields. When the filter for the query is
	 *            created, the "equals to" operator is used by default in every
	 *            expression. For example: <code>name='John'</code>. You can
	 *            specify a different operator if you wish. With this method
	 *            parameter, you can provide a <code>Map</code> where the keys
	 *            represent the attribute of the object and the values of the
	 *            Map represent the operation to perform, for example:
	 *            <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *            <code>name LIKE 'John'</code>). This method parameter gives
	 *            you some extra flexibility to filter the query. If you provide
	 *            a class for the filter parameter of this method, then only two
	 *            operators can be used: <code>Operator.IS_NULL</code> and
	 *            <code>Operator.IS_NOT_NULL</code> (this is because you cannot
	 *            provide the values of the fields in a class object).<br>
	 * <br>
	 * @param orderBy
	 *            Order of the fields for the result of the query. With this
	 *            parameter you can specify that the result of the query should
	 *            be sorted by certain fields in ascending or descending order.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @param <E>
	 *            The type of object to search for.<br>
	 * <br>
	 * 
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given object. If no objects are found, then this
	 *         method returns <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to list the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> List<E> list(E filter, Map<String, Operator> operators,
			OrderBy orderBy, int page, int pageSize) throws ClientException;

	/**
	 * Lists all of the objects that match the type from a given class. This is
	 * done via reflecting the type and all of the fields from the given object,
	 * and building a query expression. The result of the query may return null
	 * or a list with one or multiple objects in it. The objects you may get in
	 * the list will be the same type as the object that you will provide.
	 * 
	 * @param filter
	 *            Filter used to list the objects in the Data Store.<br>
	 * <br>
	 * @param operators
	 *            Operations for fields. When the filter for the query is
	 *            created, the "equals to" operator is used by default in every
	 *            expression. You can specify a different operator if you wish.
	 *            With this method parameter, you can provide a <code>Map</code>
	 *            where the keys represent the attribute of the object and the
	 *            values of the Map represent the operation to perform, for
	 *            example: <code>mymap.put("name",
	 *            Operator.IS_NOT_NULL)</code> (it creates the expression:
	 *            <code>name IS_NOT_NULL</code>). This method parameter gives
	 *            you some extra flexibility to filter the query. Only two
	 *            operators can be used: <code>Operator.IS_NULL</code> and
	 *            <code>Operator.IS_NOT_NULL</code> (this is because you cannot
	 *            provide the values of the fields in a class object).<br>
	 * <br>
	 * @param orderBy
	 *            Order of the fields for the result of the query. With this
	 *            parameter you can specify that the result of the query should
	 *            be sorted by certain fields in ascending or descending order.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @param <E>
	 *            The type of objects to search for.<br>
	 * <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given object. If no objects are found, then this
	 *         method returns <code>null</code>.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to list the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> List<E> list(Class<E> filter, Map<String, Operator> operators,
			OrderBy orderBy, int page, int pageSize) throws ClientException;

	/**
	 * Lists all of the objects that match the type and all non-null field
	 * values from a given object. This is done via reflecting the type and all
	 * of the fields from the given object, and building a query expression
	 * where all non-null-value fields are combined with AND expressions (for
	 * example: <code>name='John' AND color='red' ...</code> ). The result of
	 * the query may return null or a list with one or multiple objects in it.
	 * The objects you may get in the list will be the same type as the object
	 * that you will provide.
	 * 
	 * @param filter
	 *            Filter used to list the objects in the Data Store. You can
	 *            filter by type or by type and values. When this filter
	 *            represents a non-class object (a Java Bean for example), this
	 *            object specifies two things: first, the type of the object to
	 *            search for in the Data Store, and second, the values that
	 *            identify the object in the Data Store. This is when you filter
	 *            by type and values. You can also provide a class as a filter
	 *            to search just by type.<br>
	 * <br>
	 * @param operators
	 *            Operations for fields. When the filter for the query is
	 *            created, the "equals to" operator is used by default in every
	 *            expression. For example: <code>name='John'</code>. You can
	 *            specify a different operator if you wish. With this method
	 *            parameter, you can provide a <code>Map</code> where the keys
	 *            represent the attribute of the object and the values of the
	 *            Map represent the operation to perform, for example:
	 *            <code>mymap.put("name",
	 *            Operator.LIKE)</code> (it creates the expression:
	 *            <code>name LIKE 'John'</code>). This method parameter gives
	 *            you some extra flexibility to filter the query. If you provide
	 *            a class for the filter parameter of this method, then only two
	 *            operators can be used: <code>Operator.IS_NULL</code> and
	 *            <code>Operator.IS_NOT_NULL</code> (this is because you cannot
	 *            provide the values of the fields in a class object).<br>
	 * <br>
	 * @param orderBy
	 *            Order of the fields for the result of the query. With this
	 *            parameter you can specify that the result of the query should
	 *            be sorted by certain fields in ascending or descending order.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the
	 *            <code>List</code> result of the operation in the
	 *            <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void list(Object filter, Map<String, Operator> operators, OrderBy orderBy,
			int page, int pageSize, AbstractCallback callback);

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query
	 *            Object that specifies what to search for. It defines the type
	 *            of the object to search for, how to filter the result, which
	 *            order to apply and the page to retrieve.<br>
	 * <br>
	 * @param <E>
	 *            The type of objects to search for.<br>
	 * <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         values of the given query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to list the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> List<E> list(Query<E> query) throws ClientException;

	/**
	 * Lists all of the objects for a given query.
	 * 
	 * @param query
	 *            Object that specifies what to search for. It defines the type
	 *            of the object to search for, how to filter the result, which
	 *            order to apply and the page to retrieve.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the
	 *            <code>List</code> result of the operation in the
	 *            <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 * @param <E>
	 *            The type of objects to search for.<br>
	 * <br>
	 */
	<E> void list(Query<E> query, AbstractCallback callback);

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for
	 * this View (or the next View in the stack of Views of the Data Store) and
	 * executes it in the Data Store.
	 * 
	 * @param name
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to list the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	Object executeQueryByName(String name, Map<String, Object> values,
			int page, int pageSize) throws ClientException;

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the
	 * Data Store.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to list the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	Object executeQueryByName(String providerName, String queryName,
			Map<String, Object> values, int page, int pageSize)
			throws ClientException;

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the
	 * Data Store.
	 * 
	 * @param name
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the
	 *            <code>List</code> result of the operation in the
	 *            <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeQueryByName(String name, Map<String, Object> values, int page,
			int pageSize, AbstractCallback callback);

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in the
	 * Data Store.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            objects (check out <code>pageSize</code> argument of this
	 *            method) that you want in the result of a query, Warework
	 *            automatically calculates the number of pages that hold this
	 *            number of objects. You have to pass an integer value greater
	 *            than zero to retrieve a specific page from the result. Set
	 *            this argument to <code>-1</code> to retrieve every object in
	 *            one page.<br>
	 * <br>
	 * @param pageSize
	 *            Number of objects that you want in the result of the query.
	 *            Set this argument to <code>-1</code> to retrieve every object.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the
	 *            <code>List</code> result of the operation in the
	 *            <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeQueryByName(String providerName, String queryName,
			Map<String, Object> values, int page, int pageSize,
			AbstractCallback callback);

	/**
	 * Counts the number of objects of the specified query. <b>WARNING</b>: If
	 * the underlying Data Store cannot directly count the objects with a
	 * specific funcion, the Framework tries to count them by running the query
	 * and counting the objects returned by the query (counts the number of
	 * objects that exist in the list returned by the query). Keep this in mind
	 * because queries that return many objects may cause memory problems.
	 * Review the documentation of the Data Store for further details.
	 * Relational Data Stores (JDBC, Android SQLite and JPA for example) do not
	 * have this problem.
	 * 
	 * @param query
	 *            Object that specifies what to search for. It defines the type
	 *            of the object to search for, how to filter the result, which
	 *            order to apply and the page to retrieve.<br>
	 * <br>
	 * @param <E>
	 *            The type of objects to count.<br>
	 * <br>
	 * @return Number of objects found.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to count the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> int count(Query<E> query) throws ClientException;

	/**
	 * Counts the number of objects of the specified query. <b>WARNING</b>: If
	 * the underlying Data Store cannot directly count the objects with a
	 * specific funcion, the Framework tries to count them by running the query
	 * and counting the objects returned by the query (counts the number of
	 * objects that exist in the list returned by the query). Keep this in mind
	 * because queries that return many objects may cause memory problems.
	 * Review the documentation of the Data Store for further details.
	 * Relational Data Stores (JPA for example) do not have this problem.
	 * 
	 * @param query
	 *            Object that specifies what to search for. It defines the type
	 *            of the object to search for, how to filter the result, which
	 *            order to apply and the page to retrieve.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the result of the
	 *            count in the <code>onSuccess</code> method of the callback. <br>
	 * @param <E>
	 *            The type of objects to count.<br>
	 * <br>
	 */
	<E> void count(Query<E> query, AbstractCallback callback);

	/**
	 * Counts every object of a specified type that exist in the Data Store.
	 * <b>WARNING</b>: If the underlying Data Store cannot directly count the
	 * objects with a specific function, the Framework tries to count them by
	 * running the query and counting the objects returned by the query (counts
	 * the number of objects that exist in the list returned by the query). Keep
	 * this in mind because queries that return many objects may cause memory
	 * problems. Review the documentation of the Data Store for further details.
	 * Relational Data Stores (JPA for example) do not have this problem.
	 * 
	 * @param type
	 *            Type of object to count.<br>
	 * <br>
	 * @param <E>
	 *            The type of objects to count.<br>
	 * <br>
	 * @return Number of objects found.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to count the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	<E> int count(Class<E> type) throws ClientException;

	/**
	 * Counts every object of a specified type that exist in the Data Store.
	 * <b>WARNING</b>: If the underlying Data Store cannot directly count the
	 * objects with a specific function, the Framework tries to count them by
	 * running the query and counting the objects returned by the query (counts
	 * the number of objects that exist in the list returned by the query). Keep
	 * this in mind because queries that return many objects may cause memory
	 * problems. Review the documentation of the Data Store for further details.
	 * Relational Data Stores (JPA for example) do not have this problem.
	 * 
	 * @param type
	 *            Type of object to count.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the result of the
	 *            count in the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 * @param <E>
	 *            The type of objects to count.<br>
	 * <br>
	 */
	<E> void count(Class<E> type, AbstractCallback callback);

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for
	 * this View (or the next View in the stack of Views of the Data Store) and
	 * counts the number of objects that it returns. <b>WARNING</b>: If the
	 * underlying Data Store cannot directly count the objects with a specific
	 * function, the Framework tries to count them by running the query and
	 * counting the objects returned by the query (counts the number of objects
	 * that exist in the list returned by the query). Keep this in mind because
	 * queries that return many objects may cause memory problems. Review the
	 * documentation of the Data Store for further details. Relational Data
	 * Stores (JPA for example) do not have this problem.
	 * 
	 * @param name
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @return Number of objects found.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to count the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	int executeCountByName(String name, Map<String, Object> values)
			throws ClientException;

	/**
	 * Loads a <code>Query</code> object from the default Provider defined for
	 * this View (or the next View in the stack of Views of the Data Store) and
	 * counts the number of objects that it returns. <b>WARNING</b>: If the
	 * underlying Data Store cannot directly count the objects with a specific
	 * function, the Framework tries to count them by running the query and
	 * counting the objects returned by the query (counts the number of objects
	 * that exist in the list returned by the query). Keep this in mind because
	 * queries that return many objects may cause memory problems. Review the
	 * documentation of the Data Store for further details. Relational Data
	 * Stores (JPA for example) do not have this problem.
	 * 
	 * @param name
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the result of the
	 *            count in the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeCountByName(String name, Map<String, Object> values,
			AbstractCallback callback);

	/**
	 * Loads a <code>Query</code> object from a Provider and counts the number
	 * of objects that it returns. <b>WARNING</b>: If the underlying Data Store
	 * cannot directly count the objects with a specific function, the Framework
	 * tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the
	 * list returned by the query). Keep this in mind because queries that
	 * return many objects may cause memory problems. Review the documentation
	 * of the Data Store for further details. Relational Data Stores (JPA for
	 * example) do not have this problem.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @return Number of objects found.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to count the object/s in the
	 *             Data Store.<br>
	 * <br>
	 */
	int executeCountByName(String providerName, String queryName,
			Map<String, Object> values) throws ClientException;

	/**
	 * Loads a <code>Query</code> object from a Provider and counts the number
	 * of objects that it returns. <b>WARNING</b>: If the underlying Data Store
	 * cannot directly count the objects with a specific function, the Framework
	 * tries to count them by running the query and counting the objects
	 * returned by the query (counts the number of objects that exist in the
	 * list returned by the query). Keep this in mind because queries that
	 * return many objects may cause memory problems. Review the documentation
	 * of the Data Store for further details. Relational Data Stores (JPA for
	 * example) do not have this problem.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the query.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            loaded from the Provider and the values of the Map represent
	 *            the values that will replace the variables. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query loaded.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler. You can retrieve the result of the
	 *            count in the <code>onSuccess</code> method of the callback. <br>
	 * <br>
	 */
	void executeCountByName(String providerName, String queryName,
			Map<String, Object> values, AbstractCallback callback);

}
