package com.warework.service.datastore.model;

import java.util.Iterator;
import java.util.List;

import com.warework.core.model.Client;

/**
 * Bean that holds the information of a Data Store.
 * 
 * @author Jose Schiaffino
 * @version 2.1.0
 */
public class Datastore extends Client {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Views of the Data Store.
	private List<View> views;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Views of a Data Store.
	 * 
	 * @return Views of a Data Store.<br>
	 * <br>
	 */
	public List<View> getViews() {
		return views;
	}

	/**
	 * Sets the Vviews of a Data Store.
	 * 
	 * @param value
	 *            Views of a Data Store.<br>
	 * <br>
	 */
	public void setViews(List<View> value) {
		this.views = value;
	}

	/**
	 * Gets a View.
	 * 
	 * @param name
	 *            Name of the View to search for.<br>
	 * <br>
	 * @return View.<br>
	 * <br>
	 */
	public View getView(String name) {

		// Search for the View.
		if (getViews() != null) {
			for (Iterator<com.warework.service.datastore.model.View> iterator = getViews()
					.iterator(); iterator.hasNext();) {

				// Get one View.
				View view = iterator.next();

				// Match View.
				if (view.getName().equals(name)) {
					return view;
				}

			}
		}

		// At this point, View does not exists.
		return null;

	}

}
