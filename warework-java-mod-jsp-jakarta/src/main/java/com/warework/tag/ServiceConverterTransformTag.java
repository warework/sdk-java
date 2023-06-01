package com.warework.tag;

import java.io.IOException;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.core.service.ServiceFacade;
import com.warework.service.converter.ConverterServiceConstants;
import com.warework.service.converter.ConverterServiceFacade;

import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServiceConverterTransformTag extends SimpleTagSupport {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Provider and object name.
	private String service, client, object, provider;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the name of the Converter Service where to transform the object.
	 * 
	 * @param service Converter service name.<br>
	 *                <br>
	 */
	public void setService(final String service) {
		this.service = service;
	}

	/**
	 * Sets the name of the Client in the Converter Service where to transform the
	 * object.
	 * 
	 * @param client Converter service client name.<br>
	 *               <br>
	 */
	public void setClient(final String client) {
		this.client = client;
	}

	/**
	 * Sets the name of the Provider where to get the object.
	 * 
	 * @param provider Provider name.<br>
	 *                 <br>
	 */
	public void setProvider(final String provider) {
		this.provider = provider;
	}

	/**
	 * Sets the name of the object to get from the Provider that will be
	 * transformed.
	 * 
	 * @param object Object name.<br>
	 *               <br>
	 */
	public void setObject(final String object) {
		this.object = object;
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

		// Get the Converter Service.
		final ServiceFacade service = scope.getService(getService());

		// Validate it is the Converter Service.
		if (service instanceof ConverterServiceFacade) {

			// Cast to the Converter Service.
			final ConverterServiceFacade converterService = (ConverterServiceFacade) service;

			// Validate client exists.
			if (converterService.existsClient(getClient())) {

				// Transform object.
				Object output = null;
				if (provider == null) {

					// Get object from Provider.
					final Object object = scope.getObject(this.object);

					// Transform object.
					if (object != null) {
						try {
							output = converterService.transform(client, object);
						} catch (final ServiceException e) {
							// DO NOTHING.
						}
					}

				} else {
					try {
						output = converterService.transform(client, provider, object);
					} catch (final ServiceException e) {
						// DO NOTHING.
					}
				}

				// Write transformed object.
				if (output != null) {
					getJspContext().getOut().write(output.toString());
				}

			}

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the Converter Service where to transform the object.
	 * 
	 * @return Converter Service name.
	 */
	private String getService() {
		return (service == null) ? ConverterServiceConstants.DEFAULT_SERVICE_NAME : service;
	}

	/**
	 * Gets the name of the Client in the Converter Service where to transform the
	 * object.
	 * 
	 * @return Client name.
	 */
	private String getClient() {
		return (client == null) ? ConverterServiceConstants.DEFAULT_CLIENT_NAME : client;
	}

}