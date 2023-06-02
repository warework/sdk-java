package com.warework.service.datastore;

/**
 * Constants for the Data Store Service.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class DatastoreExtensionServiceConstants extends DatastoreServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// OPERATION NAMES

	/**
	 * Saves an object or a collection of objects in a Object Data Store View. Use
	 * the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Object/s to save. You can provide a simple
	 * Java Bean, an array or a <code>java.util.Collection</code>. If this object is
	 * not an array nor a <code>java.util.Collection</code> then this method tries
	 * to save the object directly in the underlying Data Store. When it represents
	 * an array or a <code>java.util.Collection</code> then a batch operation is
	 * perfomed. Batch operations are handled directly by the Data Store if they
	 * support it. Otherwise, this method extracts every object from the
	 * array/collection and tries to save each one in the Data Store. This parameter
	 * is mandatory.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_SAVE = "ods-save";

	/**
	 * Updates an object or a collection of objects in a Object Data Store View. Use
	 * the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Object/s to update. You can provide a simple
	 * Java Bean, an array or a <code>java.util.Collection</code>. If this object is
	 * not an array nor a <code>java.util.Collection</code> then this method tries
	 * to update the object directly in the underlying Data Store. When it
	 * represents an array or a <code>java.util.Collection</code> then a batch
	 * operation is perfomed. Batch operations are handled directly by the Data
	 * Store if they support it. Otherwise, this method extracts every object from
	 * the array/collection and tries to update each one in the Data Store. This
	 * parameter is mandatory.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_UPDATE = "ods-update";

	/**
	 * Deletes an object in a Object Data Store View. It can also delete multiple
	 * objects at once if you provide a <code>Query</code> object, a class (to
	 * remove every object), an array or a <code>java.util.Collection</code>. Use
	 * the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Object/s to delete. You can provide a simple
	 * Java Bean (to remove just one entity in the Data Store), a class (to remove
	 * every object of the specified type), a <code>Query</code> object (to remove
	 * every object the query returns), an array or a
	 * <code>java.util.Collection</code> (to remove multiple objects at once). When
	 * this object represents an array or a <code>java.util.Collection</code> then a
	 * batch operation is perfomed. Batch operations are handled directly by the
	 * Data Store if they support it. Otherwise, this method extracts every object
	 * from the array/collection and tries to delete each one in the Data Store. If
	 * the underlying Data Store supports batch operations but you need to track in
	 * detail the progress of each object deleted, then you should configure the
	 * Connector of the Data Store with
	 * <code>PARAMETER_SKIP_NATIVE_BATCH_SUPPORT</code> set to <code>true</code>
	 * .<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_DELETE = "ods-delete";

	/**
	 * Loads a query from a Provider and deletes every object specified by the query
	 * in a Object Data Store View. Use the following parameters in order to invoke
	 * this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to retrieve
	 * the query. This parameter is optional and when provided it must be a
	 * <code>java.lang.String</code>. If this parameter is not given then the View
	 * will use the default Provider defined.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the query to retrieve from
	 * the Provider. This parameter must be a <code>java.lang.String</code> .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement loaded
	 * from the Provider. This parameter is optional and when provided it must be a
	 * map where the keys represent variable names in the query loaded from the
	 * Provider and the values of the <code>Map</code> represent the values that
	 * will replace the variables. Do not provide this parameter to make no changes
	 * in the statement loaded. <br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME = "ods-delete-by-name";

	/**
	 * Finds an object by its values. This operation returns the object of the Data
	 * Store which matches the type and all non-null field values from a given
	 * object (check out the <code>filter</code> parameter). This is done via
	 * reflecting the type and all of the fields from the given object, and building
	 * a query expression where all non-null-value fields are combined with AND
	 * expressions (for example: <code>name='John' AND color='red' ...</code> ). So,
	 * the object you will get will be the same type as the object that you will
	 * provide. The result of the query must return one single object in order to
	 * avoid an exception. In many cases, you may only need to provide the ID fields
	 * of the object to retrieve one single object. In relational databases like
	 * Oracle or MySQL, these ID fields are the primary keys. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>filter</code></b>: Filter used to find an object in the Data
	 * Store. This object specifies two things: first, the type of the object to
	 * search for in the Data Store, and second, the values that identify the object
	 * in the Data Store. This parameter is mandatory (any Java Bean).<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns the object from the Data Store that matches the type
	 * and the values of the given object/filter. If more than one object is found
	 * in the Data Store, then an exception is thrown. If no objects are found, then
	 * this operation returns <code>null</code>.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_FIND = "ods-find";

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in a Object
	 * Data Store View. Use the following parameters in order to invoke this
	 * operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to retrieve
	 * the query. This parameter is optional and when provided it must be a
	 * <code>java.lang.String</code>. If this parameter is not given then the View
	 * will use the default Provider defined.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the query to retrieve from
	 * the Provider. This parameter must be a <code>java.lang.String</code> .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement loaded
	 * from the Provider. This parameter is optional and when provided it must be a
	 * map where the keys represent variable names in the query loaded from the
	 * Provider and the values of the <code>Map</code> represent the values that
	 * will replace the variables. Do not provide this parameter to make no changes
	 * in the statement loaded. <br>
	 * <br>
	 * </li>
	 * <li><b><code>page</code></b>: Page to get from the result. When you specify
	 * the number of objects (check out <code>page-size</code> argument of this
	 * method) that you want in the result of a query, Warework automatically
	 * calculates the number of pages that hold this number of objects. You have to
	 * pass an integer value greater than zero to retrieve a specific page from the
	 * result. This parameter is optional (a <code>java.lang.Integer</code>). Do not
	 * provide this parameter to retrieve every result in one page.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page-size</code></b>: Number of objects that you want in the
	 * result of the query. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to retrieve
	 * every row.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns a list of objects from the Data Store that matches the
	 * type and the filters specified in the query loaded from the Provider.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME = "ods-query-by-name";

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in a Object
	 * Data Store View. Use the following parameters in order to invoke this
	 * operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to retrieve
	 * the query. This parameter is optional and when provided it must be a
	 * <code>java.lang.String</code>. If this parameter is not given then the View
	 * will use the default Provider defined.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the query to retrieve from
	 * the Provider. This parameter must be a <code>java.lang.String</code> .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement loaded
	 * from the Provider. This parameter is optional and when provided it must be a
	 * map where the keys represent variable names in the query loaded from the
	 * Provider and the values of the <code>Map</code> represent the values that
	 * will replace the variables. Do not provide this parameter to make no changes
	 * in the statement loaded. <br>
	 * <br>
	 * </li>
	 * <li><b><code>page</code></b>: Page to get from the result. When you specify
	 * the number of objects (check out <code>page-size</code> argument of this
	 * method) that you want in the result of a query, Warework automatically
	 * calculates the number of pages that hold this number of objects. You have to
	 * pass an integer value greater than zero to retrieve a specific page from the
	 * result. This parameter is optional (a <code>java.lang.Integer</code>). Do not
	 * provide this parameter to retrieve every result in one page.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page-size</code></b>: Number of objects that you want in the
	 * result of the query. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to retrieve
	 * every row.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the
	 * <code>ObjectDatastoreView</code> where to perform the operation. This
	 * parameter must be a <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns a list of objects from the Data Store that matches the
	 * type and the filters specified in the query loaded from the Provider.
	 */
	public static final String OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME = "ods-count-by-name";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies the filter used to find an object in a
	 * Object Data Store. This parameter is an object that specifies two things:
	 * first, the type of the object to search for in the Data Store, and second,
	 * the values that identify the object in the Data Store.
	 */
	public static final String OPERATION_PARAMETER_FILTER = "filter";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This is the default constructor and does not perform any operation.
	 */
	private DatastoreExtensionServiceConstants() {
	}

}
