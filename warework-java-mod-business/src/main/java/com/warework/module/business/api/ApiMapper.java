package com.warework.module.business.api;

import java.util.Collection;
import java.util.Locale;

/**
 *  
 * @author Jose Schiaffino
 * @version 3.0.0
 *
 * @param <T>
 */
public interface ApiMapper<T> {

	/**
	 * 
	 * @return
	 * @throws ApiException
	 */
	Collection<String> getKeys() throws ApiException;

	/**
	 * 
	 * @param key
	 * @param locale
	 * @return
	 * @throws ApiException
	 */
	T toObject(final String key, final Locale locale) throws ApiException;

}
