package com.warework.tag;

import java.io.IOException;

import com.warework.core.scope.ScopeFacade;

import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ScopeObjectTag extends SimpleTagSupport {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Provider and object name.
	private String name, provider;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the name of the object to get. If 'provider' attribute is defined then
	 * this tag searchs for the object in that Provider, otherwise it will use a
	 * previously created object reference.
	 * 
	 * @param name Object name.<br>
	 *             <br>
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the name of the Provider where to get the object. If it's not defined
	 * then this tag will search the for a previously created object reference.
	 * 
	 * @param provider Provider name.<br>
	 *                 <br>
	 */
	public void setProvider(final String provider) {
		this.provider = provider;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Writes the object of a Provider in the output.
	 *
	 * @throws IOException If there was an error writing to the output stream.
	 */
	@Override
	public void doTag() throws IOException {

		// Get page context.
		final PageContext pageContext = (PageContext) getJspContext();

		// Get Warework Scope.
		final ScopeFacade scope = (ScopeFacade) pageContext.getRequest();

		// Write provider object.
		if (provider == null) {
			getJspContext().getOut().write(scope.getObject(name).toString());
		} else {
			getJspContext().getOut().write(scope.getObject(name, provider).toString());
		}

	}

}