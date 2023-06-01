package com.warework.module.business.dao;

import java.util.List;
import java.util.Locale;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface Dao<T> {

	/**
	 * 
	 * @param object
	 */
	void save(final T object) throws DaoException;

	/**
	 * 
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	void save(final T object, final int index, final Character delimiter) throws DaoException;

	/**
	 * 
	 * @param object
	 */
	void update(final T object) throws DaoException;

	/**
	 * 
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	void update(final T object, final int index, final Character delimiter) throws DaoException;

	/**
	 * 
	 * @param object
	 */
	void delete(final T object) throws DaoException;

	/**
	 * 
	 * @param object
	 * @param index
	 * @param delimiter
	 * @throws DaoException
	 */
	void delete(final T object, final int index, final Character delimiter) throws DaoException;

	/**
	 * 
	 * @param query
	 * @param locale
	 * @return
	 * @throws DaoException
	 */
	List<T> select(final AbstractQuery<T> query, final Locale locale) throws DaoException;

	/**
	 * 
	 * @param query
	 * @param locale
	 * @return
	 * @throws DaoException
	 */
	T find(final AbstractQuery<T> query, final Locale locale) throws DaoException;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int count(final AbstractQuery<T> query) throws DaoException;

}
