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
public final class ScopeInitParameterTag extends SimpleTagSupport {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the parameter.
	private String name;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the name of the parameter.
	 * 
	 * @param name Name of the parameter.<br>
	 *                      <br>
	 */
	public void setName(final String name) {
		this.name = name;
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

		// Write parameter value.
		getJspContext().getOut().write(scope.getInitParameter(name).toString());

	}

}