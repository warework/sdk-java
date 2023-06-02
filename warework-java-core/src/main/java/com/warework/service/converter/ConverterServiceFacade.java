package com.warework.service.converter;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;

/**
 * Service that performs objects transformations.<br>
 * <br>
 * Warework Converter Service is an extremely powerful Service with a very
 * simple purpose: perform object transformations. Software developers can
 * provide object instances into this Service to convert them. The result of the
 * operation is a new instance of an object (or an updated one) that represents
 * the transformation of the source object.<br>
 * <br>
 * The conversion of the object can be a basic operation like formatting a
 * string or a complex one like translating a given text to another language. As
 * this Service is a Proxy Service, developers are free to define what sorts of
 * operations are supported by this Service. They just need to include the
 * Client that performs the task they are looking for.<br>
 * <br>
 * Any process that implies a conversion fits in this Service: compressors, unit
 * conversions, translations, updaters, formatters, etc. Possibilities are
 * unlimited. That is why Clients in this Service have different names but in
 * general, they all are known as "Converters".<br>
 * <br>
 * <b>Create and retrieve a Converter Service</b><br>
 * <br>
 * To create the Converter Service in a Scope, you always need to provide a
 * unique name for the Service and the <code>ConverterServiceImpl</code> class
 * that exists in the <code>com.warework.service.converter</code> package:<br>
 * <br>
 * <code>
 * // Create the Converter Service and register it in a Scope.<br> 
 * scope.createService("converter-service", ConverterServiceImpl.class, null);<br>
 * </code> <br>
 * Once it is created, you can get it using the same name (when you retrieve an
 * instance of a Converter Service, you will get the
 * <code>ConverterServiceFacade</code> interface):<br>
 * <br>
 * <code>
 * // Get an instance of the Converter Service. <br>
 * ConverterServiceFacade converterService = (ConverterServiceFacade) scope.getService("converter-service");<br>
 * </code> <br>
 * Most of the times, you will need to specify a set of parameters that
 * configure how the Service must work. Review the next section to know how to
 * define Converter Clients with these parameters.<br>
 * <br>
 * <b>Add and connect Converters</b><br>
 * <br>
 * Now the Converter Service is running but you need at least one Client
 * (remember, Clients in this Service are known as Converters) where to perform
 * operations. To add a Converter into the Service you have to invoke method
 * <code>createClient()</code> that exists in its Facade. This method requests a
 * name and a Connector which performs the creation of the Converter. Let's see
 * how to register a sample Converter in this Service:<br>
 * <br>
 * <code>
 * // Add a Converter in the Converter Service.<br> 
 * converterService.createClient("sample-client", SampleConnector.class, null);<br>
 * </code><br>
 * <br>
 * The <code>SampleConnector</code> class creates the Sample Converter and
 * registers it in the Converter Service. After that, we have to tell the
 * Converter Service that we want to perform operations with the Sample
 * Converter. We do so by connecting the Converter:<br>
 * <br>
 * <code>
 * // Connect the Sample Converter.<br> 
 * converterService.connect("sample-client");<br>
 * </code> <br>
 * You can define as many Converters/Clients as you need for the Converter
 * Service. Once the Scope is started, you can work with Converters like
 * this:<br>
 * <br>
 * <code>
 * // Get an instance of a Scope.<br> 
 * ScopeFacade scope = ...; <br>
 * <br>
 * // Get the facade of the Converter Service.<br> 
 * ConverterServiceFacade converterService = (ConverterServiceFacade) scope.getService("converter-service");<br>
 * <br>
 * // Connect the Client.<br> 
 * converterService.connect("sample-client");<br>
 * <br>
 * // Perform operations with the Client.<br> 
 * ...<br>
 * <br>
 * // Disconnect the Client.<br> 
 * converterService.disconnect("sample-client");<br>
 * </code> <br>
 * <b>Perform object transformations</b><br>
 * <br>
 * Each Converter Client exposes its functionality in the
 * <code>ConverterServiceFacade</code> interface and it is where you can find
 * the necessary methods to perform transformations with objects.<br>
 * <br>
 * Now we are going to check out how to transform an object into another one
 * with the trans-form method. The minimum information you have to provide to
 * this method is the object you want to transform:<br>
 * <br>
 * <code>
 * // Object to transform.<br> 
 * Object source = ...<br>
 * <br>
 * // Transform the object to another object.<br> 
 * Object result = converterService.transform("sample-client", source);<br>
 * </code> <br>
 * You can also retrieve the object to transform from a specified Provider:<br>
 * <br>
 * <code>
 * // Transform an object from a Provider.<br> 
 * Object result = converterService.transform("sample-client", "providerName", "providerObject");<br>
 * </code>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface ConverterServiceFacade extends ProxyServiceFacade {

	/**
	 * Transforms a given object into another object.
	 * 
	 * @param converterName The name to which the Converter is bound in the
	 *                      Service.<br>
	 *                      <br>
	 * @param source        Source object to transform.<br>
	 *                      <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to perform the
	 *                          transformation.<br>
	 *                          <br>
	 */
	Object transform(final String converterName, final Object source) throws ServiceException;

	/**
	 * Transforms an object from a Provider into another object.
	 * 
	 * @param converterName  The name to which the Converter is bound in the
	 *                       Service.<br>
	 *                       <br>
	 * @param providerName   Name of the Provider where to retrieve the source
	 *                       object to transform.<br>
	 *                       <br>
	 * @param providerObject Name of the object to retrieve from the Provider that
	 *                       represents the source object to transform.<br>
	 *                       <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to perform the
	 *                          transformation.<br>
	 *                          <br>
	 */
	Object transform(final String converterName, final String providerName, final String providerObject)
			throws ServiceException;

}
