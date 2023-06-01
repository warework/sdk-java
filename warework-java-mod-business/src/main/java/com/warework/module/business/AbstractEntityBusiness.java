package com.warework.module.business;

import java.util.List;
import java.util.Locale;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.module.business.dao.AbstractQuery;
import com.warework.module.business.dao.Dao;
import com.warework.module.business.dao.DaoException;

/**
 * Entity functionality for business objects.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractEntityBusiness<T> extends AbstractBusiness {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// OPERATIONS

	protected static final String OPERATION_SAVE = "SAVE";

	protected static final String OPERATION_UPDATE = "UPDATE";

	protected static final String OPERATION_DELETE = "DELETE";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @author jschiaffino
	 *
	 */
	protected abstract class EntityUpdater {

		/**
		 * 
		 * @param <T>
		 * @param entity
		 * @return
		 */
		public abstract T update(final T entity);

	}

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected abstract Class<? extends Dao<T>> getDAOType();

	/**
	 * 
	 * @return
	 */
	protected abstract EntityUpdater getSaveEntityUpdater();

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the business object with the Scope.
	 * 
	 * @param scope Scope for the business objects.<br>
	 *              <br>
	 */
	public AbstractEntityBusiness(final ScopeFacade scope) {
		super(scope);
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws AbstractBusinessException
	 */
	public final T save(final T entity) throws AbstractBusinessException {

		// Begin business operation.
		boolean transaction = businessBegin(OPERATION_SAVE, true);

		// Perform database operations.
		try {

			// Begin transaction in database.
			transaction = beginDatabaseTransaction(OPERATION_SAVE, transaction);

			// Save object in database.
			save(OPERATION_SAVE, getDAOType(), getSaveEntityUpdater().update(entity));

			// Commit changes.
			transaction = commitDatabaseTransaction(OPERATION_SAVE, transaction);

			// Return entity.
			return entity;

		} catch (final DaoException e) {
			throw businessException(OPERATION_SAVE,
					"Cannot save object '" + entity.getClass().getName() + "' (" + entity.toString() + ") in database '"
							+ getDefaultDatastoreName() + "' of datastore service '" + getDefaultDatastoreServiceName()
							+ "' due to the following problem: " + e.getMessage(),
					e);
		} catch (final ClientException e) {
			throw businessException(OPERATION_SAVE,
					"Cannot save object '" + entity.getClass().getName() + "' (" + entity.toString() + ") in database '"
							+ getDefaultDatastoreName() + "' of datastore service '" + getDefaultDatastoreServiceName()
							+ "' due to the following problem: " + e.getMessage(),
					e);
		} finally {
			businessEnd(OPERATION_SAVE, transaction);
		}

	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws AbstractBusinessException
	 */
	public final T update(final T entity) throws AbstractBusinessException {

		// Begin business operation.
		boolean transaction = businessBegin(OPERATION_UPDATE, true);

		// Perform database operations.
		try {

			// Begin transaction in database.
			transaction = beginDatabaseTransaction(OPERATION_UPDATE, transaction);

			// Save object in database.
			update(OPERATION_UPDATE, getDAOType(), entity);

			// Commit changes.
			transaction = commitDatabaseTransaction(OPERATION_UPDATE, transaction);

			// Return entity.
			return entity;

		} catch (final DaoException e) {
			throw businessException(OPERATION_UPDATE,
					"Cannot update object '" + entity.getClass().getName() + "' (" + entity.toString()
							+ ") in database '" + getDefaultDatastoreName() + "' of datastore service '"
							+ getDefaultDatastoreServiceName() + "' due to the following problem: " + e.getMessage(),
					e);
		} catch (final ClientException e) {
			throw businessException(OPERATION_UPDATE,
					"Cannot update object '" + entity.getClass().getName() + "' (" + entity.toString()
							+ ") in database '" + getDefaultDatastoreName() + "' of datastore service '"
							+ getDefaultDatastoreServiceName() + "' due to the following problem: " + e.getMessage(),
					e);
		} finally {
			businessEnd(OPERATION_UPDATE, transaction);
		}

	}

	/**
	 * 
	 * @param entity
	 * @throws AbstractBusinessException
	 */
	public final void delete(final T entity) throws AbstractBusinessException {

		// Begin business operation.
		boolean transaction = businessBegin(OPERATION_DELETE, true);

		// Perform database operations.
		try {

			// Begin transaction in database.
			transaction = beginDatabaseTransaction(OPERATION_DELETE, transaction);

			// Delete object in database.
			delete(OPERATION_DELETE, getDAOType(), entity);

			// Commit changes.
			transaction = commitDatabaseTransaction(OPERATION_DELETE, transaction);

		} catch (final DaoException e) {
			throw businessException(OPERATION_DELETE,
					"Cannot delete object '" + entity.getClass().getName() + "' (" + entity.toString()
							+ ") in database '" + getDefaultDatastoreName() + "' of datastore service '"
							+ getDefaultDatastoreServiceName() + "' due to the following problem: " + e.getMessage(),
					e);
		} catch (final ClientException e) {
			throw businessException(OPERATION_DELETE,
					"Cannot delete object '" + entity.getClass().getName() + "' (" + entity.toString()
							+ ") in database '" + getDefaultDatastoreName() + "' of datastore service '"
							+ getDefaultDatastoreServiceName() + "' due to the following problem: " + e.getMessage(),
					e);
		} finally {
			businessEnd(OPERATION_DELETE, transaction);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operation
	 * @param query
	 * @param locale
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final List<T> handleQuery(final String operation, final AbstractQuery<T> query, final Locale locale)
			throws AbstractBusinessException {

		// Begin business operation.
		final boolean transaction = businessBegin(operation, true);

		// Execute query in database.
		try {
			return query(operation, query, locale);
		} catch (final DaoException e) {
			throw businessException(operation,
					"Cannot execute query '" + query.getClass().getName() + "' in database '"
							+ getDefaultDatastoreName() + "' of datastore service '" + getDefaultDatastoreServiceName()
							+ "' due to the following problem: " + e.getMessage(),
					e);
		} finally {
			businessEnd(operation, transaction);
		}

	}

	/**
	 * 
	 * @param operation
	 * @param query
	 * @param locale
	 * @return
	 * @throws AbstractBusinessException
	 */
	protected final T handleFind(final String operation, final AbstractQuery<T> query, final Locale locale)
			throws AbstractBusinessException {

		// Begin business operation.
		final boolean transaction = businessBegin(operation, true);

		// Execute query in database.
		try {
			return find(operation, query, locale);
		} catch (final DaoException e) {
			throw businessException(operation,
					"Cannot execute query '" + query.getClass().getName() + "' in database '"
							+ getDefaultDatastoreName() + "' of datastore service '" + getDefaultDatastoreServiceName()
							+ "' due to the following problem: " + e.getMessage(),
					e);
		} finally {
			businessEnd(operation, transaction);
		}

	}

}
