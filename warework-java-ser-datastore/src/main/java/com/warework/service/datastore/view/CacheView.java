package com.warework.service.datastore.view;

import com.warework.core.service.client.ClientException;

/**
 * <u>Cache View</u><br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface CacheView extends KeyValueView {

	/**
	 * Maps the specified key to the specified value in the Data Store. Neither the
	 * key nor the value can be <code>null</code>.
	 * 
	 * @param key    Key for the value.<br>
	 *               <br>
	 * @param value  Value to set.<br>
	 *               <br>
	 * @param expire Timeout in milliseconds for the specified key. After the
	 *               timeout the key will return <code>null</code>.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to store the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	void put(final Object key, final Object value, final long expire) throws ClientException;

}
