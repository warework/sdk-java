package com.warework.service.datastore.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.annotation.Id;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.service.datastore.Entity;
import com.warework.service.log.LogServiceConstants;

/**
 * Client that defines common operations for Object Data Stores.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractObjectDatastore extends AbstractDatastore {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// ANNOTATIONS

	private static final String ANNOTATION_CLASS_JPA_ENTITY = "javax.persistence.Entity";

	private static final String ANNOTATION_CLASS_JPA_ID = "javax.persistence.Id";

	private static final String ANNOTATION_PARAMETER_NAME = CommonValueL2Constants.STRING_NAME;

	// COMMON PARAMETERS FOR CONNECTORS

	/**
	 * Initialization parameter that forces the Framework to handle batch operations
	 * even if the underlying Client supports them. For example, in some Data Stores
	 * (Amazon DynamoDB or Kinvey Data Stores) you can save/update/delete collection
	 * of entities with batch operations. When a Data Store natively does not
	 * supports batch operations, the Framework automatically handles the collection
	 * to extract each value and then simulate a batch operation. Even when Data
	 * Stores support batch operations natively, you may want to execute them with
	 * the Framework (that is, iterate a collection and save/update/delete each
	 * entity individually). This allows you to track each save/update/delete
	 * operation individually. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then it is <code>false</code>.
	 */
	public static final String CONNECTOR_PARAMETER_SKIP_NATIVE_BATCH_SUPPORT = "skip-native-batch-support";

	// CLIENT'S ATTRIBUTES

	/**
	 * Attribute that specifies if Data Store supports save batch operations.
	 * Accepted values for this attribute are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this attribute is not specified
	 * then it is <code>false</code>.
	 */
	public static final String CLIENT_ATTRIBUTE_SAVE_BATCH_OPERATION_SUPPORT = "save-batch-operation-support";

	/**
	 * Attribute that specifies if Data Store supports update batch operations.
	 * Accepted values for this attribute are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this attribute is not specified
	 * then it is <code>false</code>.
	 */
	public static final String CLIENT_ATTRIBUTE_UPDATE_BATCH_OPERATION_SUPPORT = "update-batch-operation-support";

	/**
	 * Attribute that specifies if Data Store supports delete batch operations.
	 * Accepted values for this attribute are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this attribute is not specified
	 * then it is <code>false</code>.
	 */
	public static final String CLIENT_ATTRIBUTE_DELETE_BATCH_OPERATION_SUPPORT = "delete-batch-operation-support";

	/**
	 * Attribute that specifies if Data Store provides methods to wait until
	 * asynchronous operations are finished. Accepted values for this attribute are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this attribute is not specified
	 * then it is <code>false</code>.
	 */
	public static final String CLIENT_ATTRIBUTE_ASYNCHRONOUS_BLOCKING_SUPPORT = "asynchronous-blocking-support";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Flag for batch operations.TRUE = execute the batch operation with the
	// Framework. FALSE = delegate this operation to the Data Store.

	private Boolean executeSaveBatch;

	private Boolean executeUpdateBatch;

	private Boolean executeDeleteBatch;

	private Boolean executeAsynchronousBlocking;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Updates the attributes of an object.
	 * 
	 * @param object Object to update.<br>
	 *               <br>
	 * @param values Each key defined in the map represents the name of the
	 *               attribute to update (for example: <code>name</code> or
	 *               <code>user.contact</code>). Each value in the map represents
	 *               the value to replace in the attribute of the object. About the
	 *               keys, you can navigate through the beans using the dot
	 *               notation. Examples: if object <code>X</code> has a property
	 *               named <code>title</code> and the method <code>setTitle</code>,
	 *               you can set the value for <code>title</code> with the name for
	 *               the attribute (this argument) <code>title</code>. If
	 *               <code>X</code> contains object <code>Y</code> with an attribute
	 *               named <code>y</code> and <code>Y</code> contains the attribute
	 *               <code>age</code>, you can set the value for <code>age</code>
	 *               with <code>y.age</code>. The dot separates each bean and you
	 *               can make the navigation as deep as you want.
	 *               <code>travel.person.name</code> invokes <code>getTravel</code>
	 *               first, then <code>getPerson</code> and finally
	 *               <code>setName</code>. You can also navigate through arrays,
	 *               collections and maps. Examples:
	 *               <code>agenda.friends[0].name</code> invokes
	 *               <code>getAgenda</code> first, after that
	 *               <code>getFriends</code> and the element that exists in the
	 *               <code>0</code> position of the collection or array that it
	 *               returns, and finally the <code>setName</code> method in the
	 *               object that exists in that position of the array or collection.
	 *               For maps, the value inside of '[' and ']' must exists between
	 *               single quotes. Example:
	 *               <code>agenda.friends['john'].name</code> retrieves this time
	 *               from the <code>friends</code> attribute a
	 *               <code>java.util.Map</code> and uses <code>john</code> as the
	 *               key of the map to retrieve the value.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to update the
	 *                         object.<br>
	 *                         <br>
	 */
	protected void updateObject(final Object object, final Map<String, Object> values) throws ClientException {
		if ((values != null) && (values.size() > 0)) {

			// Get the attributes to update.
			final Set<String> keys = values.keySet();

			// Update each attribute.
			for (final Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

				// Get an attribute.
				final String key = iterator.next();

				// Update the value.
				try {
					ReflectionL2Helper.setAttributeValue(object, key, values.get(key));
				} catch (final Exception e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot perform the requested operation in Data Store '" + getName()
									+ "' at Service '" + getService().getName() + "' because the attribute '"
									+ ((String) key) + "' cannot be updated at given object. Check out this error: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

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
	@SuppressWarnings("unchecked")
	String getEntityName(final Class<?> entityType) {

		// Get the name of the entity with the annotation.
		if (ReflectionL2Helper.existsClassAnnotation(entityType, Entity.class)) {

			// Get the Warework entity annotation.
			final Entity annotation = (Entity) entityType.getAnnotation(Entity.class);

			// Return the entity name.
			return annotation.name();

		} else {

			// Search for the JPA entity annotation.
			Class<? extends Annotation> jpaEntityAnnotation = null;
			try {
				jpaEntityAnnotation = (Class<? extends Annotation>) Class.forName(ANNOTATION_CLASS_JPA_ENTITY);
			} catch (final Exception e) {
				return entityType.getName();
			}

			// Return the entity name.
			if ((jpaEntityAnnotation != null)
					&& (ReflectionL2Helper.existsClassAnnotation(entityType, jpaEntityAnnotation))) {
				try {
					return (String) ReflectionL2Helper.getClassAnnotationValue(entityType, jpaEntityAnnotation,
							ANNOTATION_PARAMETER_NAME);
				} catch (final Exception e) {
					return entityType.getName();
				}
			}

		}

		// At this point, the entity name is the name of the class.
		return entityType.getName();

	}

	/**
	 * Gets the name of the first field found in a class with the Warework
	 * <code>Id</code> or the JPA <code>Id</code> annotations.
	 * 
	 * @param type Class where to search for the field name.<br>
	 *             <br>
	 * @return Name of the first field found with the Warework <code>Id</code> or
	 *         the JPA <code>Id</code> annotations.<br>
	 *         <br>
	 */
	@SuppressWarnings("unchecked")
	String getFirstIdFieldName(final Class<?> type) {

		// Get the fileds of the class.
		final Field[] fields = type.getFields();

		// Search for the JPA Id annotation.
		Class<? extends Annotation> jpaIdAnnotation = null;
		try {
			jpaIdAnnotation = (Class<? extends Annotation>) Class.forName(ANNOTATION_CLASS_JPA_ID);
		} catch (final Exception e) {
			// DO NOTHING.
		}

		// Get the name of the field.
		if (jpaIdAnnotation == null) {
			for (int i = 0; i < fields.length; i++) {

				// Get a field.
				final Field field = fields[i];

				// Return the name of the field if it has the ID annotation.
				if (field.isAnnotationPresent(Id.class)) {
					return field.getName();
				}

			}
		} else {
			for (int i = 0; i < fields.length; i++) {

				// Get a field.
				final Field field = fields[i];

				// Return the name of the field if it has the ID annotation.
				if ((field.isAnnotationPresent(Id.class)) || (field.isAnnotationPresent(jpaIdAnnotation))) {
					return field.getName();
				}

			}
		}

		// At this point, no field found with any of the ID annotations.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Specifies if a update batch operation should be executed by the underlying
	 * Data Store or managed automatically by the Framework.
	 * 
	 * @return <code>true</code> to execute the batch operation by the Framework and
	 *         <code>false</code> to delegate this operation to the Data Store.<br>
	 *         <br>
	 */
	boolean executeUpdateBatch() {

		// Initialize flag.
		if (executeUpdateBatch == null) {
			if (getClientAttributes()
					.isAttribute(AbstractObjectDatastore.CLIENT_ATTRIBUTE_UPDATE_BATCH_OPERATION_SUPPORT)) {
				if (getConnector().isInitParameter(CONNECTOR_PARAMETER_SKIP_NATIVE_BATCH_SUPPORT)) {
					executeUpdateBatch = Boolean.TRUE;
				} else {
					executeUpdateBatch = Boolean.FALSE;
				}
			} else {
				executeUpdateBatch = Boolean.TRUE;
			}
		}

		// Return if the batch operation should be executed.
		return executeUpdateBatch;

	}

	/**
	 * Specifies if a delete batch operation should be executed by the underlying
	 * Data Store or managed automatically by the Framework.
	 * 
	 * @return <code>true</code> to execute the batch operation by the Framework and
	 *         <code>false</code> to delegate this operation to the Data Store.<br>
	 *         <br>
	 */
	boolean executeDeleteBatch() {

		// Initialize flag.
		if (executeDeleteBatch == null) {
			if (getClientAttributes()
					.isAttribute(AbstractObjectDatastore.CLIENT_ATTRIBUTE_DELETE_BATCH_OPERATION_SUPPORT)) {
				if (getConnector().isInitParameter(CONNECTOR_PARAMETER_SKIP_NATIVE_BATCH_SUPPORT)) {
					executeDeleteBatch = Boolean.TRUE;
				} else {
					executeDeleteBatch = Boolean.FALSE;
				}
			} else {
				executeDeleteBatch = Boolean.TRUE;
			}
		}

		// Return if the batch operation should be executed.
		return executeDeleteBatch;

	}

	/**
	 * Specifies if a save batch operation should be executed by the underlying Data
	 * Store or managed automatically by the Framework.
	 * 
	 * @return <code>true</code> to execute the batch operation by the Framework and
	 *         <code>false</code> to delegate this operation to the Data Store.<br>
	 *         <br>
	 */
	boolean executeSaveBatch() {

		// Initialize flag.
		if (executeSaveBatch == null) {
			if (getClientAttributes()
					.isAttribute(AbstractObjectDatastore.CLIENT_ATTRIBUTE_SAVE_BATCH_OPERATION_SUPPORT)) {
				if (getConnector().isInitParameter(CONNECTOR_PARAMETER_SKIP_NATIVE_BATCH_SUPPORT)) {
					executeSaveBatch = Boolean.TRUE;
				} else {
					executeSaveBatch = Boolean.FALSE;
				}
			} else {
				executeSaveBatch = Boolean.TRUE;
			}
		}

		// Return if the batch operation should be executed.
		return executeSaveBatch;

	}

	/**
	 * Specifies if Data Store provides methods to wait until asynchronous
	 * operations are finished.
	 * 
	 * @return <code>true</code> to block operation until it is finished and
	 *         <code>false</code> if not.<br>
	 *         <br>
	 */
	boolean executeAsynchronousBlocking() {

		// Initialize flag.
		if (executeAsynchronousBlocking == null) {
			if (getClientAttributes()
					.isAttribute(AbstractObjectDatastore.CLIENT_ATTRIBUTE_ASYNCHRONOUS_BLOCKING_SUPPORT)) {
				executeAsynchronousBlocking = Boolean.TRUE;
			} else {
				executeAsynchronousBlocking = Boolean.FALSE;
			}
		}

		// Return if the blocking should be executed.
		return executeAsynchronousBlocking;

	}

}
