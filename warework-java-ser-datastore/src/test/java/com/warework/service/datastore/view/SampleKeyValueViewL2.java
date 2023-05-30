package com.warework.service.datastore.view;

import java.util.Enumeration;

import com.warework.core.service.client.ClientException;
import com.warework.service.datastore.AbstractDatastoreView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class SampleKeyValueViewL2 extends AbstractDatastoreView implements KeyValueView {

	public void put(Object key, Object value) throws ClientException {
		getParentKeyValueView().put(key, value);
	}

	public Object get(Object key) throws ClientException {
		if ((key instanceof String) && ((String) key).equalsIgnoreCase("name")) {
			return "arnold";
		} else {
			return null;
		}
	}

	public void remove(Object key) throws ClientException {
		getParentKeyValueView().remove(key);
	}

	public Enumeration<Object> keys() throws ClientException {
		return getParentKeyValueView().keys();
	}

	public int size() throws ClientException {
		return getParentKeyValueView().size();
	}

	private KeyValueView getParentKeyValueView() {
		return (KeyValueView) getParentView();
	}

}
