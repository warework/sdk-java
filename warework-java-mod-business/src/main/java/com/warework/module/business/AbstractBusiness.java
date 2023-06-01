package com.warework.module.business;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.data.OrderBy;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.module.business.dao.AbstractDao;
import com.warework.module.business.dao.AbstractQuery;
import com.warework.module.business.dao.Dao;
import com.warework.module.business.dao.DaoException;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.client.ResultRows;
import com.warework.service.datastore.view.DatastoreView;
import com.warework.service.datastore.view.RdbmsView;
import com.warework.service.log.LogServiceConstants;

/**
 * Base functionality for business objects.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractBusiness {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final Character DEFAULT_SQL_STATEMENT_DELIMITER = Character.valueOf(';');

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Wrapper to compare beans.
	 * 
	 * @author Jose Schiaffino
	 * @version ${project.version}
	 */
	private final class ComparableBean<T> implements Comparable<ComparableBean<T>> {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Bean to wrap, used to compare with another bean.
		private T bean1;

		// Order clause of the query.
		private OrderBy orderBy;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates a wrapper for a bean so it can be compared with another bean.
		 * 
		 * @param bean    Object/bean to wrap.<br>
		 *                <br>
		 * @param orderBy Order clause of the query.<br>
		 *                <br>
		 */
		private ComparableBean(final T bean, final OrderBy orderBy) {

			// Set the bean.
			this.bean1 = bean;

			// Set the order clause of the query.
			this.orderBy = orderBy;

		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Compares the values of the attrbiutes of the wrapped bean with the values of
		 * the attrbiutes of another bean.
		 * 
		 * @param bean2 Bean to compare with the wrapped bean.<br>
		 *              <br>
		 */
		public int compareTo(final ComparableBean<T> bean2) {
			try {

				// Compare each attribute of both beans.
				for (int index = 0; index < orderBy.size(); index++) {

					// Get the attribute to compare.
					final String attribute = orderBy.getAttribute(index);

					// Get the value of the attribute in the first bean.
					final Object value1 = ReflectionL2Helper.getAttributeValue(bean1, attribute);

					// Get the value of the attribute in the second bean.
					final Object value2 = ReflectionL2Helper.getAttributeValue(bean2.getBean(), attribute);

					// Validate null values.
					if ((value1 == null) && (value2 == null)) {
						continue;
					} else if (value1 == null) {
						return -1;
					} else if (value2 == null) {
						return 1;
					}

					// Compare values.
					int result = -1;
					if (value1 instanceof Boolean) {
						result = ((Boolean) value1).compareTo((Boolean) value2);
					} else if (value1 instanceof Byte) {
						result = ((Byte) value1).compareTo((Byte) value2);
					} else if (value1 instanceof Short) {
						result = ((Short) value1).compareTo((Short) value2);
					} else if (value1 instanceof Integer) {
						result = ((Integer) value1).compareTo((Integer) value2);
					} else if (value1 instanceof Long) {
						result = ((Long) value1).compareTo((Long) value2);
					} else if (value1 instanceof Float) {
						result = ((Float) value1).compareTo((Float) value2);
					} else if (value1 instanceof Double) {
						result = ((Double) value1).compareTo((Double) value2);
					} else if (value1 instanceof BigDecimal) {
						result = ((BigDecimal) value1).compareTo((BigDecimal) value2);
					} else if (value1 instanceof BigInteger) {
						result = ((BigInteger) value1).compareTo((BigInteger) value2);
					} else if (value1 instanceof Character) {
						result = ((Character) value1).compareTo((Character) value2);
					} else if (value1 instanceof String) {
						result = ((String) value1).compareTo((String) value2);
					} else if (value1 instanceof Date) {
						result = ((Date) value1).compareTo((Date) value2);
					} else {
						throw new ClassCastException(
								"WAREWORK cannot sort the result of the query because the type of the attribute '"
										+ attribute + "' is '" + value1.getClass().getName()
										+ "' and it is not supported.");
					}

					// Return result only if both values are not equal.
					if (result != 0) {

						// Get the order type.
						final String orderType = orderBy.getOrderType(index);

						// Return result.
						if (orderType.equals(OrderBy.KEYWORD_ASCENDING)) {
							return result;
						} else {
							return -result;
						}

					}

				}

				// At this point, both objects are equal.
				return 0;

			} catch (final Exception e) {
				throw new ClassCastException(
						"WAREWORK cannot sort the result of the query because the value of an attribute throws the following exception when it is accessed: "
								+ e.getMessage());
			}
		}

		// ///////////////////////////////////////////////////////////////
		// PRIVATE METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the value of the wrapped bean.
		 * 
		 * @return Wrapped bean.<br>
		 *         <br>
		 */
		private T getBean() {
			return bean1;
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected abstract String getDefaultDatastoreName();

	/**
	 * 
	 * @return
	 */
	protected abstract String getDefaultDatastoreView();

	/**
	 * 
	 * @return
	 */
	protected abstract Class<? extends AbstractBusinessException> getExceptionType();

	/**
	 * 
	 * @return
	 */
	protected abstract String getModuleName();

	/**
	 * 
	 * @param key
	 * @return
	 */
	protected abstract String getBusinessProperty(final String key);

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// The Scope where to perform the operations.
	private ScopeFacade scope;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private AbstractBusiness() {
		// DO NOTHING.
	}

	/**
	 * Initializes the business object with the Scope.
	 * 
	 * @param scope Scope for the business objects.<br>
	 *              <br>
	 */
	public AbstractBusiness(final ScopeFacade scope) {

		// Invoke default constructor.
		this();

		// Set Scope.
		this.scope = scope;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the Scope.
	 * 
	 * @return The Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Creates a new business object.
	 * 
	 * @param type Business object to create.<br>
	 *             <br>
	 * @return A new instance of a business object.<br>
	 *         <br>
	 */
	protected final <E extends AbstractBusiness> E createBusiness(final Class<E> type) {
		try {
			return (E) ReflectionL2Helper.createInstance(type, new Class[] { ScopeFacade.class },
					new Object[] { scope });
		} catch (final NoSuchMethodException e) {
			return null;
		} catch (final InstantiationException e) {
			return null;
		} catch (final IllegalAccessException e) {
			return null;
		} catch (final InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	protected String createUid() {
		return StringL2Helper.create128BitHexRandomString();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operation
	 */
	protected final void businessBegin(final String operation) {
		getScopeFacade().info(log(operation) + "Started execution...");
	}

	/**
	 * 
	 * @param operation
	 * @param connectDatabase
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final boolean businessBegin(final String operation, final boolean connectDatabase)
			throws AbstractBusinessException {

		//
		businessBegin(operation);

		//
		return connectDatabase(operation);

	}

	/**
	 * 
	 * @param operation
	 * @param message
	 * @param e
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final AbstractBusinessException businessException(final String operation, final String message,
			final Exception e) throws AbstractBusinessException {

		// Log message.
		warn(operation, message);

		// Throw exception.
		try {
			return ReflectionL2Helper.createInstance(getExceptionType(),
					new Class[] { ScopeFacade.class, String.class, Throwable.class, int.class },
					new Object[] { getScopeFacade(), message, e, LogServiceConstants.LOG_LEVEL_WARN });
		} catch (final Exception e2) {

			// Log message.
			warn(operation, "Cannot create instance of business exception '" + getExceptionType().getName()
					+ "' due to the following problem: " + e2.getMessage());

			// Throw default exception instead.
			return createDefaultException(operation, message, e2);

		}

	}

	/**
	 * 
	 * @param operation
	 */
	protected final void businessEnd(final String operation) {
		getScopeFacade().info(log(operation) + "Finished execution.");
	}

	/**
	 * 
	 * @param operation
	 * @param transaction
	 */
	protected final void businessEnd(final String operation, final boolean transaction) {

		//
		disconnectDatabase(operation, transaction);

		//
		businessEnd(operation);

	}

	/**
	 * 
	 * @param operation
	 * @param message
	 * @param e
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final AbstractBusinessException businessEnd(final String operation, final String message,
			final Exception e) throws AbstractBusinessException {

		//
		final AbstractBusinessException exception = businessException(operation, message, e);

		//
		businessEnd(operation);

		//
		throw exception;

	}

	/**
	 * 
	 * @param operation
	 * @param message
	 * @param e
	 * @param transaction
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final AbstractBusinessException businessEnd(final String operation, final String message,
			final Exception e, final boolean transaction) throws AbstractBusinessException {

		//
		disconnectDatabase(operation, transaction);

		//
		return businessEnd(operation, message, e);

	}

	/**
	 * 
	 * @param operation
	 * @param inputName
	 * @param value
	 */
	protected final void businessInput(final String operation, final String inputName, final String value) {
		if (value == null) {
			info(operation, "#INPUT - " + inputName + " = <not-spcified>");
		} else {
			info(operation, "#INPUT - " + inputName + " = " + value);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operation
	 * @param message
	 */
	protected final void debug(final String operation, final String message) {
		getScopeFacade().debug(log(operation) + message);
	}

	/**
	 * 
	 * @param operation
	 * @param message
	 */
	protected final void info(final String operation, final String message) {
		getScopeFacade().info(log(operation) + message);
	}

	/**
	 * 
	 * @param operation
	 * @param e
	 */
	protected final void warn(final String operation, final Exception e) {
		if (e == null) {
			getScopeFacade().warn(log(operation) + "Reported an unspecified error!");
		} else {
			getScopeFacade().warn(log(operation) + e.getMessage());
		}
	}

	/**
	 * 
	 * @param operation
	 * @param message
	 */
	protected final void warn(final String operation, final String message) {
		getScopeFacade().warn(log(operation) + message);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Closes the connection with the database.
	 * 
	 * @param operation Business operation currently in execution.
	 * @param ddbb      Database connection.<br>
	 *                  <br>
	 * @throws ClientException
	 */
	protected final void disconnect(final String operation, final DatastoreView ddbb) {
		if (ddbb.isConnected()) {
			try {
				ddbb.disconnect();
			} catch (final ClientException e) {
				warn(operation, "Cannot close datastore connection due to the following problem: " + e.getMessage());
			}
		} else {
			info(operation, "Cannot disconnect database because connection was already closed.");
		}
	}

	/**
	 * 
	 * @param operation
	 * @param ddbb
	 */
	protected final void rollback(final String operation, final RdbmsView ddbb) {
		try {
			ddbb.rollback();
		} catch (final ClientException e) {
			warn(operation, "Cannot perform rollback in database '" + getDefaultDatastoreName()
					+ "' due to the following problem: " + e.getMessage());
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Datastore Service.
	 * 
	 * @return name of the Datastore Service.<br>
	 *         <br>
	 */
	protected String getDefaultDatastoreServiceName() {
		return DatastoreServiceConstants.DEFAULT_SERVICE_NAME;
	}

	/**
	 * Gets the connection with the database.
	 * 
	 * @param operation Business operation in execution.<br>
	 *                  <br>
	 * @param connect   Open connection?<br>
	 *                  <br>
	 * @return Database connection.<br>
	 *         <br>
	 * @throws AbstractBusinessException
	 */
	protected final RdbmsView getDatabase(final String operation, final boolean connect)
			throws AbstractBusinessException {

		// Get datastore service.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {
			throw createDefaultException(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.", null);
		}

		// Get the datastore view.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {
			throw createDefaultException(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.",
					null);
		} else if (!(view instanceof RdbmsView)) {
			throw createDefaultException(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.",
					null);
		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Connect with database.
		if (connect) {
			if (ddbb.isConnected()) {
				info(operation, "Connection not stablished because it was already created.");
			} else {
				try {
					ddbb.connect();
				} catch (final ClientException e) {
					throw createDefaultException(operation,
							"Cannot connect relational database '" + getDefaultDatastoreName()
									+ "' in datastore service '" + getDefaultDatastoreServiceName()
									+ "' due to the following problem: " + e.getMessage(),
							e);
				}
			}
		}

		// Return database connection.
		return ddbb;

	}

	/**
	 * 
	 * @param operation
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final boolean connectDatabase(final String operation) throws AbstractBusinessException {

		// Connect with database.
		getDatabase(operation, true);

		// FALSE = transaction not started.
		return false;

	}

	/**
	 * Close the connection with the default database.
	 * 
	 * @param operation   Business operation currently in execution.
	 * @param transaction <code>true</code> connection has an active transaction.
	 */
	protected final void disconnectDatabase(final String operation, final boolean transaction) {

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Execute rollback if transaction is still active. All transactions must be
		// committed before closing them, so if they are still alive, perform rollback.
		if (transaction) {
			try {

				// Log message.
				warn(operation, "Running rollback in database '" + getDefaultDatastoreName()
						+ "' because current transaction wasn't closed!");

				// Execute rollback.
				ddbb.rollback();

			} catch (final ClientException e) {
				warn(operation, "Cannot perform rollback in database '" + getDefaultDatastoreName()
						+ "' due to the following problem: " + e.getMessage());
			}
		}

		// Close connection with database.
		disconnect(operation, ddbb);

	}

	/**
	 * 
	 * @param operation
	 * @param transaction Current transaction state.
	 * @return
	 * @throws ClientException
	 */
	protected final boolean beginDatabaseTransaction(final String operation, final boolean transaction)
			throws ClientException {

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return transaction;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Begin a transaction in the database.
		if (transaction) {
			info(operation, "Cannot begin transaction because it was already started.");
		} else {
			ddbb.beginTransaction();
		}

		// TRUE = transaction started.
		return true;

	}

	/**
	 * 
	 * @param operation
	 * @param transaction
	 * @return
	 * @throws ClientException
	 */
	protected final boolean commitDatabaseTransaction(final String operation, final boolean transaction)
			throws ClientException {

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return transaction;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Commit changes.
		if (transaction) {
			ddbb.commit();
		} else {
			info(operation, "Cannot commit transaction because there is not an active transaction in datastore '"
					+ getDefaultDatastoreName() + "' of datastore service '" + getDefaultDatastoreServiceName() + "'.");
		}

		// FALSE = transaction finished.
		return false;

	}

	/**
	 * Rollbacks database changes.
	 * 
	 * @param operation   Business operation currently in execution.<br>
	 *                    <br>
	 * @param transaction Transaction state.<br>
	 *                    <br>
	 * @return
	 */
	protected final boolean rollbackDatabaseTransaction(final String operation, final boolean transaction) {

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return transaction;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return transaction;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Execute rollback.
		if (transaction) {
			try {

				// Perform database operation.
				ddbb.rollback();

				// Return transaction state.
				return false;

			} catch (final ClientException e) {

				// Log message.
				warn(operation, "Cannot perform rollback in database '" + getDefaultDatastoreName()
						+ "' due to the following problem: " + e.getMessage());

				// Return transaction state.
				return transaction;

			}
		} else {

			// Log message.
			info(operation, "Cannot perform rollback in database '" + getDefaultDatastoreName() + "' of service '"
					+ getDefaultDatastoreServiceName() + "' because there is no active transaction.");

			// Return transaction state.
			return transaction;

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object in the database.
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type      DAO type.<br>
	 *                  <br>
	 * @param object    Object to save.<br>
	 *                  <br>
	 * @throws DaoException
	 */
	protected final <T> void save(final String operation, final Class<? extends Dao<T>> type, final T object)
			throws DaoException {
		createDAO(operation, type).save(object);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	protected final <T> void save(final String operation, final Class<? extends Dao<T>> type, final T object,
			final int index, final Character delimiter) throws DaoException {
		createDAO(operation, type).save(object, index, delimiter);
	}

	/**
	 * Updates an object in the database.
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type      DAO type.<br>
	 *                  <br>
	 * @param object    Object to update.<br>
	 *                  <br>
	 * @throws DaoException
	 */
	protected final <T> void update(final String operation, final Class<? extends Dao<T>> type, final T object)
			throws DaoException {
		createDAO(operation, type).update(object);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	protected final <T> void update(final String operation, final Class<? extends Dao<T>> type, final T object,
			final int index, final Character delimiter) throws DaoException {
		createDAO(operation, type).update(object, index, delimiter);
	}

	/**
	 * Deletes an object in the database.
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type      DAO type.<br>
	 *                  <br>
	 * @param object    Object to delete.<br>
	 *                  <br>
	 * @throws DaoException
	 */
	protected final <T> void delete(final String operation, final Class<? extends Dao<T>> type, final T object)
			throws DaoException {
		createDAO(operation, type).delete(object);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	protected final <T> void delete(final String operation, final Class<? extends Dao<T>> type, final T object,
			final int index, final Character delimiter) throws DaoException {
		createDAO(operation, type).delete(object, index, delimiter);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param query
	 * @param locale
	 * @return
	 * @throws DaoException
	 */
	protected final <T> List<T> query(final String operation, final AbstractQuery<T> query, final Locale locale)
			throws DaoException {
		return createDAO(operation, query.getDAOType()).select(query, locale);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param query
	 * @param locale
	 * @return
	 * @throws DaoException
	 */
	protected final <T> T find(final String operation, final AbstractQuery<T> query, final Locale locale)
			throws DaoException {
		return createDAO(operation, query.getDAOType()).find(query, locale);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	protected final <T> int count(final String operation, final AbstractQuery<T> query) throws DaoException {
		return createDAO(operation, query.getDAOType()).count(query);
	}

	/**
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param statement
	 * @param variables
	 * @return
	 * @throws ClientException
	 */
	protected final ResultRows select(final String operation, final String statement,
			final Map<String, Object> variables) throws ClientException {

		// Load SQL statement.
		String sql = null;
		try {
			sql = AbstractDao.filterSQL(getStatementSql(statement));
		} catch (final IOException e) {

			// Log message.
			warn(operation, "Cannot find SQL statement '" + AbstractDao.DEFAULT_STATEMENT_PATH + statement
					+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL + "'.");

			// Stop execution.
			return null;

		}

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return null;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return null;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return null;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Execute query in database.
		return (ResultRows) ddbb.executeQuery(sql, variables, -1, -1);

	}

	/**
	 * 
	 * @param operation
	 * @param statement
	 * @param variables
	 * @throws ClientException
	 */
	protected final void update(final String operation, final String statement, final Map<String, Object> variables)
			throws ClientException {

		// Load SQL statement.
		String sql = null;
		try {
			sql = AbstractDao.filterSQL(getStatementSql(statement));
		} catch (final IOException e) {

			// Log message.
			warn(operation, "Cannot find SQL statement '" + AbstractDao.DEFAULT_STATEMENT_PATH + statement
					+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL + "'.");

			// Stop execution.
			return;

		}

		// Obtenemos el servicio Data Store.
		final DatastoreServiceFacade service = (DatastoreServiceFacade) getScopeFacade()
				.getService(getDefaultDatastoreServiceName());
		if (service == null) {

			// Log message.
			warn(operation, "Cannot get database connection because default datastore service '"
					+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return;

		}

		// Obtenemos la interfaz de trabajo para la BBDD.
		final AbstractDatastoreView view = service.getView(getDefaultDatastoreName(), getDefaultDatastoreView());
		if (view == null) {

			// Log message.
			warn(operation,
					"Cannot get relational database connection because default datastore '" + getDefaultDatastoreName()
							+ "' or datastore view '" + getDefaultDatastoreView() + "' in datastore service '"
							+ getDefaultDatastoreServiceName() + "' does not exists.");

			// Stop execution.
			return;

		} else if (!(view instanceof RdbmsView)) {

			// Log message.
			warn(operation,
					"Cannot get default relational database connection because datastore view '"
							+ getDefaultDatastoreView() + "' for datastore '" + getDefaultDatastoreName()
							+ "' in datastore service '" + getDefaultDatastoreServiceName()
							+ "' is not an instance of '" + RdbmsView.class.getName() + "'.");

			// Stop execution.
			return;

		}

		// Get relational database view.
		final RdbmsView ddbb = (RdbmsView) view;

		// Execute query in database.
		ddbb.executeUpdate(sql, variables, DEFAULT_SQL_STATEMENT_DELIMITER);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param statementId
	 * @return
	 * @throws IOException
	 */
	protected String getStatementSql(final String statementId) throws IOException {
		return StringL2Helper.toString(getClass().getResourceAsStream(AbstractDao.DEFAULT_STATEMENT_PATH + statementId
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SQL));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sorts a collection of beans and copies the result into a given list.
	 * 
	 * @param source  Source collection where to retrieve the beans to sort.<br>
	 *                <br>
	 * @param orderBy Order clause to apply in the sort operation.<br>
	 *                <br>
	 * @param target  Target collection where to copy the sorted beans.<br>
	 *                <br>
	 */
	protected <T> void sort(final Iterable<T> source, final OrderBy orderBy, final List<T> target) {

		// Create a list where to wrap each bean to compare.
		final List<ComparableBean<T>> comparableBeans = new ArrayList<ComparableBean<T>>();

		// Wrap every bean to compare and include them in the list.
		for (T bean : source) {
			comparableBeans.add(new ComparableBean<T>(bean, orderBy));
		}

		// Sort the list.
		sort(comparableBeans, target);

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operation
	 * @return
	 */
	private String log(final String operation) {
		return "[BUSINESS-'" + operation + "'@'" + getClass().getName() + "'] - ";
	}

	/**
	 * 
	 * @param operation
	 * @param message
	 * @param e
	 * @return
	 */
	private DefaultBusinessException createDefaultException(final String operation, final String message,
			final Exception e) {
		return new DefaultBusinessException(getScopeFacade(), log(operation) + message, e,
				LogServiceConstants.LOG_LEVEL_WARN);
	}

	/**
	 * Creates a new DAO.
	 * 
	 * @param operation Business operation currently in execution.<br>
	 *                  <br>
	 * @param type      Type of DAO to create.<br>
	 *                  <br>
	 * @return A new DAO.<br>
	 *         <br>
	 */
	private <T> Dao<T> createDAO(final String operation, final Class<? extends Dao<T>> type) {
		try {
			return ReflectionL2Helper.createInstance(type,
					new Class[] { String.class, ScopeFacade.class, RdbmsView.class },
					new Object[] { getModuleName(), getScopeFacade(), getDatabase(operation, false) });
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * Sorts a collection of beans and copies the result into a given list.
	 * 
	 * @param comparableBeans Collection of wrapped beans ready to be sorted.<br>
	 *                        <br>
	 * @param target          Target collection where to copy the sorted beans.<br>
	 *                        <br>
	 */
	private <T> void sort(final List<ComparableBean<T>> comparableBeans, final List<T> target) {

		// Sort the collection.
		Collections.sort(comparableBeans);

		// Copy beans from source collection into target collection.
		for (final Iterator<ComparableBean<T>> iterator = comparableBeans.iterator(); iterator.hasNext();) {

			// Get a wrapped bean.
			final ComparableBean<T> comparableBean = iterator.next();

			// Store the bean in target collection.
			target.add(comparableBean.getBean());

		}

	}

}
