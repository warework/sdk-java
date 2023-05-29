package com.warework.core.scope;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.provider.AbstractProvider;
import com.warework.core.provider.ProviderException;
import com.warework.core.service.ServiceException;
import com.warework.core.service.ServiceFacade;

/**
 * A Warework Scope is a closed context where Services, Providers and other
 * types of objects exist. Scopes can be a lot of things; it is up on you to
 * define what you want them to be. Imagine that we want to build an application
 * or system that represents a fruit market; you can create a Scope to represent
 * the market and execute the required operations with specific Providers and
 * Services.<br>
 * <br>
 * Scopes provide a set of common methods to work with Framework components and
 * other objects. They can store any kind of objects in a shared memory and
 * create, invoke and remove Services and Providers.<br>
 * <br>
 * Sometimes Scopes may need to work with other Scopes to better organize
 * complex environments and expand the available functionality. Warework
 * provides the required methods to achieve this task and help developers create
 * bigger systems with control. In this Framework, the organization of complex
 * systems is performed with:<br>
 * <br>
 * <strong>Parent Scopes</strong><br>
 * <br>
 * You can create a hierarchy of Scopes by saying that one Scope extends another
 * Scope. That is, a Scope named B can extend another Scope named A in a way
 * that B integrates the functionality provided by A.<br>
 * <br>
 * When Scope B extends Scope A, B can use Services, Providers and Object
 * References that exist in Scope A. It is important to know that parent Scopes
 * can only be assigned when children Scopes are created. You cannot link Scope
 * A with Scope B when B is already created.<br>
 * <br>
 * <strong>Domains and the context of a Scope</strong><br>
 * <br>
 * Warework gives the possibility to include multiple Scopes into another Scope.
 * This level of organization let software developers create domains where
 * Scopes exist. If we include Scope A1 and Scope A2 into Scope A, Warework
 * assumes the following facts:
 * <UL>
 * <LI>Scope A is the domain (named "Domain Scope") of Scopes A1 and A2.</LI>
 * <LI>Scopes A1 and A2 exist in the context of Scope A (Scope A can retrieve
 * Scopes A1 and A2).</LI>
 * <LI>Scopes A1 and A2 can retrieve an instance of their Domain Scope A.</LI>
 * <LI>Scopes A1 and A2 can directly use Services, Providers and Object
 * References that exist in Scope A.</LI>
 * <LI>Scope A cannot directly use Services, Providers and Object References
 * that exist in Scopes A1 and A2.</LI>
 * </UL>
 * <br>
 * <strong>Working with Scopes</strong><br>
 * <br>
 * Software developers can interact with Scopes through the ScopeFacade
 * interface. The Warework Scope Facade provides methods to perform operations
 * with Services, Providers and other objects.<br>
 * <br>
 * <u>Create, retrieve and remove Services in a Scope</u><br>
 * <br>
 * To create a Service in a Scope, you always need to provide a unique name for
 * the Service and the implementation class of the Service.<br>
 * <br>
 * <code>
 * // Create a new Service and register it in the Scope.<br> 
 * scope.createService("log-service", LogServiceImpl.class, null);<br>
 * </code> <br>
 * Once it is created, you can retrieve it using the same name (when you
 * retrieve an instance of a Service, you will get an interface that represents
 * a facade for that Service):<br>
 * <br>
 * <code>
 * // Get an instance of the Log Service. <br>
 * LogServiceFacade logService = (LogServiceFacade) scope.getService("log-service");<br>
 * </code> <br>
 * You can also perform both steps in a single line of code:<br>
 * <br>
 * <code>
 * // Create a new Service, register in the Scope and return it.<br>
 * LogServiceFacade logService = (LogServiceFacade) scope.createService("log-service", LogServiceImpl.class, null);<br>
 * </code> <br>
 * While the name of the Service can be any one you want, the implementation
 * class must be the one specified in the Service documentation.<br>
 * <br>
 * You should also need to bear in mind that Services created in a Scope are not
 * only accessible in that Scope. Check this out with the following example:<br>
 * <br>
 * <ul>
 * <li>Imagine that we have two Scopes named A and B:<br>
 * <br>
 * <code>
 * // Get an instance of Scope A. <br>
 * ScopeFacade scopeA = ...;<br>
 * <br>
 * // Get an instance of Scope B.<br> 
 * ScopeFacade scopeB = ...;<br>
 * </code> <br>
 * </li>
 * <li>If Scope B extends Scope A then we can access Scope A Services directly
 * from the Scope B facade like this:<br>
 * <br>
 * <code>
 * // Get one Service in Scope A from Scope B.<br>
 * LogServiceFacade logService = (LogServiceFacade) scopeB.getService("log-service");<br>
 * </code> <br>
 * </li>
 * <li>If Scope A is the Domain of Scope B then we can access Scope A Services
 * directly from the Scope B facade exactly as we saw in the previous point.<br>
 * </li>
 * </ul>
 * <br>
 * Here it is shown how to remove a Service:<br>
 * <br>
 * <code>
 * // Remove the Log Service. <br>
 * scope.removeService("log-service");<br>
 * </code> <br>
 * This line of code removes the Service in a specific Scope. You cannot
 * directly remove Services that exist in parent and domain Scopes.<br>
 * <br>
 * Sometimes you may require a list of Services that are accessible in a Scope.
 * The following line of code gets the names of every Service that exists in a
 * Scope, its parents Scopes and its domain Scope:<br>
 * <br>
 * <code>
 * // Get the name of every accessible Service.<br> 
 * Enumeration names = scope.getServiceNames();<br>
 * </code> <br>
 * <u>Create and remove Providers in a Scope</u><br>
 * <br>
 * To create a Provider in a Scope, you always need to specify a unique name for
 * the Provider and the implementation class of the Provider.<br>
 * <br>
 * <code>
 * // Create a new provider and register it in the Scope. This provider retrieves objects that represent food.<br> 
 * scope.createProvider("food-provider", FoodProvider.class, null);<br>
 * </code> <br>
 * We cannot obtain their interfaces this time because its functionality is
 * included in the Facade of the Scope with the getObject methods (remember:
 * Providers just provide objects instances). When working with Providers, you
 * need to know that on one side you can manage them (create and remove each
 * Provider) and on the other side you can manage the objects a Provider can
 * retrieve (create, get and remove references to them).<br>
 * <br>
 * To remove a Provider is quite simple:<br>
 * <br>
 * <code>
 * // Remove the food Provider. <br>
 * scope.removeProvider("food-provider");<br>
 * </code> <br>
 * This line of code removes the Provider in a specific Scope. You cannot
 * directly remove Providers that exist in parent and domain Scopes.<br>
 * <br>
 * Sometimes you may require a list of Providers that are accessible in a Scope.
 * The following line of code gets the names of every Provider that exists in a
 * Scope, its parents Scopes and its domain Scope:<br>
 * <br>
 * <code>
 * // Get the name of every accessible Provider.<br>
 * Enumeration providerNames = scope.getProviderNames();<br>
 * </code> <br>
 * To validate if a Provider exists in a Scope, its parents Scopes or its domain
 * Scope, run this code:<br>
 * <br>
 * <code>
 * // Validate if the 'food-provider' exists.<br>
 * boolean exists = scope.existsProvider("food-provider");<br>
 * </code> <br>
 * <u>Create, use and remove objects references in a Scope</u><br>
 * <br>
 * Once you have at least one Provider in a Scope, you can create a reference to
 * an object that exists in that Provider. To create a reference you always need
 * to specify a unique name for it, the Provider where to extract the object and
 * which object to get:<br>
 * <br>
 * <code>
 * // Create a reference named 'fruit' to an object named 'apple' in a Provider named 'food-provider'.<br> 
 * scope.createObject("fruit", "food-provider", "apple");<br>
 * </code> <br>
 * Now that we have the reference created, we can access the object just by
 * doing:<br>
 * <br>
 * <code>
 * // Get the 'apple' object from the 'food-provider'.<br> 
 * Food fruit = (Food) scope.getObject("fruit");<br>
 * </code> <br>
 * You can also request an object directly, without the need to create a
 * reference:<br>
 * <br>
 * <code>
 * // Get the 'apple' object from the 'food-provider'. <br>
 * Food fruit = (Food) scope.getObject("food-provider", "apple");<br>
 * </code> <br>
 * The powerfull idea behind this is that you are isolating how an object is
 * created. In our example, as the Food Provider just knows about creating food
 * objects like apples and kiwis, every object that this Provider can retrieve
 * implements the Food interface. This allows us to easily change the food we
 * want:<br>
 * <br>
 * <code>
 * // Gets the 'kiwi' object from the 'food-provider'.<br> 
 * Food fruit = (Food) scope.getObject("food-provider", "kiwi");<br>
 * </code> <br>
 * Now, let's remove an Object Reference:<br>
 * <br>
 * <code>
 * // Remove the fruit Object Reference. <br>
 * scope.removeObject("fruit");<br>
 * </code> <br>
 * This line of code removes the Object Reference (not the object itself in the
 * Provider) in a specific Scope. You cannot remove references that exist in its
 * parents Scopes or its domain Scope.<br>
 * <br>
 * The following line of code gets the names of every Object Reference that
 * exists in a Scope, its parents Scopes and its domain Scope:<br>
 * <br>
 * <code>
 * // Get the name of every accessible Object Reference. <br>
 * Enumeration names = scope.getObjectNames();<br>
 * </code> <br>
 * <u>Get, add and remove objects in a Scope</u><br>
 * <br>
 * Scopes provide a special area where to store objects of any type. These
 * objects can be accessed only from the Scope facade where they exist.<br>
 * <br>
 * To store an object, you have to give the object a name (this name must be
 * unique):<br>
 * <br>
 * <code>
 * // Save the fruit in the shared memory.<br> 
 * scope.setAttribute("best-market-food", fruit);<br>
 * </code> <br>
 * To get the object:<br>
 * <br>
 * <code>
 * // Get the fruit from the shared memory.<br> 
 * Food food = (Food) scope.getAttribute("best-market-food");<br>
 * </code> <br>
 * To remove that object:<br>
 * <br>
 * <code>
 * // Remove the fruit in the shared memory.<br> 
 * scope.removeAttribute("best-market-food");<br>
 * </code> <br>
 * The following line of code gets the names of every object that is stored in
 * the shared memory of a Scope:<br>
 * <br>
 * <code>
 * // Get the name of every object stored in the Scope.<br> 
 * Enumeration names = scope.getAttributeNames();<br>
 * </code> <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface ScopeFacade {

	/**
	 * Gets if Scope is alive.
	 * 
	 * @return <code>true</code> if Scope is still running and <code>false</code>
	 *         otherwise.<br>
	 *         <br>
	 */
	boolean active();

	/**
	 * Gets the name of this Scope.
	 * 
	 * @return Name of the Scope.<br>
	 *         <br>
	 */
	String getName();

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Parent Scope of this Scope.
	 * 
	 * @return Parent Scope.<br>
	 *         <br>
	 */
	ScopeFacade getParent();

	/**
	 * Gets the Domain Scope of this Scope.
	 * 
	 * @return Domain Scope.<br>
	 *         <br>
	 */
	ScopeFacade getDomain();

	/**
	 * Gets the context for the scopes that belong to the domain specified by this
	 * Scope.
	 * 
	 * @return Scope's context.<br>
	 *         <br>
	 */
	Context getContext();

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if system has
	 *         no initialization parameters.<br>
	 *         <br>
	 */
	Enumeration<String> getInitParameterNames();

	/**
	 * Gets the value of a initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	Object getInitParameter(final String name);

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the objects bound to this Scope.
	 * 
	 * @return Names of the objects or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	Enumeration<String> getAttributeNames();

	/**
	 * Gets the object bound with the specified name in this Scope, or
	 * <code>null</code> if no object is bound under the name.
	 * 
	 * @param name Name of the object.<br>
	 *             <br>
	 * @return Object that exists in this Scope or <code>null</code> if no object is
	 *         bound with this name.<br>
	 *         <br>
	 */
	Object getAttribute(final String name);

	/**
	 * Binds an object to a given attribute name to this Scope, using the name
	 * specified. If an object of the same name is already bound to the Scope, the
	 * object is replaced.
	 * 
	 * @param name  The name to which the object is bound. If an object of the same
	 *              name is already bound to this session, the object is
	 *              replaced.<br>
	 *              <br>
	 * @param value The object to be bound.<br>
	 *              <br>
	 */
	void setAttribute(final String name, final Object value);

	/**
	 * Removes the object bound with the specified name from this Scope. If this
	 * Scope does not have an object bound with the specified name, this method does
	 * nothing.
	 * 
	 * @param name The name to which the object is bound.<br>
	 *             <br>
	 */
	void removeAttribute(final String name);

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the providers bound to this Scope.
	 * 
	 * @return Names of the providers or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	Enumeration<String> getProviderNames();

	/**
	 * Validates if a Provider is bound to this Scope.
	 * 
	 * @param name The name to which the Provider is bound.<br>
	 *             <br>
	 * @return <code>true</code> if the Provider exists and <code>false</code> if
	 *         the Provider does not exists.<br>
	 *         <br>
	 */
	boolean existsProvider(final String name);

	/**
	 * Creates a Provider and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Provider is bound. If a Provider of
	 *                   the same name is already bound to this Scope, an exception
	 *                   is thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Provider.<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the
	 *                   Provider.<br>
	 *                   <br>
	 * @throws ProviderException If there is an error when trying to create the
	 *                           Provider.<br>
	 *                           <br>
	 */
	void createProvider(final String name, final Class<? extends AbstractProvider> type,
			final Map<String, Object> parameters) throws ProviderException;

	/**
	 * Removes the Provider bound with the specified name from this Scope. If this
	 * Scope does not have a Provider bound with the specified name, this method
	 * does nothing.
	 * 
	 * @param name The name to which the Provider is bound.<br>
	 *             <br>
	 * @throws ProviderException If there is an error when trying to remove the
	 *                           Provider.<br>
	 *                           <br>
	 */
	void removeProvider(final String name) throws ProviderException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the services bound to this Scope.
	 * 
	 * @return Names of the services or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	Enumeration<String> getServiceNames();

	/**
	 * Gets the Service bound with the specified name in this Scope, or
	 * <code>null</code> if no Service is bound under the name.
	 * 
	 * @param name Name of the Service.<br>
	 *             <br>
	 * @return A Service or <code>null</code> if no Service is bound with this
	 *         name.<br>
	 *         <br>
	 */
	ServiceFacade getService(final String name);

	/**
	 * Creates a Service and binds it to this Scope, using the name specified.
	 * 
	 * @param name       The name to which the Service is bound. If a Service of the
	 *                   same name is already bound to this Scope, an exception is
	 *                   thrown.<br>
	 *                   <br>
	 * @param type       Implementation of the Service.<br>
	 *                   <br>
	 * @param parameters Parameters (as string values) that configure the
	 *                   Service.<br>
	 *                   <br>
	 * @return A new Service instance.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Service.<br>
	 *                          <br>
	 */
	ServiceFacade createService(final String name, final Class<? extends ServiceFacade> type,
			final Map<String, Object> parameters) throws ServiceException;

	/**
	 * Removes the Service bound with the specified name from this Scope. If this
	 * Scope does not have a Service bound with the specified name, this method does
	 * nothing.
	 * 
	 * @param name The name to which the Service is bound.<br>
	 *             <br>
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Service.<br>
	 *                          <br>
	 */
	void removeService(final String name) throws ServiceException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of all the provider-object references bound to this Scope.
	 * 
	 * @return Names of the references or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	Enumeration<String> getObjectNames();

	/**
	 * Gets an object from a Provider that is referenced with the specified name.
	 * This reference may exists in this Scope.
	 * 
	 * @param name Name of the provider-object reference. Check how this name is
	 *             given at 'createObject' method.<br>
	 *             <br>
	 * @return Object from a Provider. If no reference, Provider or object exists in
	 *         the Provider then this method returns <code>null</code> .<br>
	 *         <br>
	 */
	Object getObject(final String name);

	/**
	 * Gets an object from a Provider.
	 * 
	 * @param providerName   Name of the Provider that should exists in this
	 *                       Scope.<br>
	 *                       <br>
	 * @param providerObject Name of the object in the Provider.<br>
	 *                       <br>
	 * @return Object from the Provider. If no Provider or object exists in the
	 *         Provider then this method returns <code>null</code>.<br>
	 *         <br>
	 */
	Object getObject(final String providerName, final String providerObject);

	/**
	 * Creates a reference to an object that should exists in a Provider, registers
	 * the reference in this Scope using the name specified and returns an instance
	 * of the object.
	 * 
	 * @param name           Name of the provider-object reference to register. If a
	 *                       reference of the same name is already bound to this
	 *                       Scope, the reference is replaced.<br>
	 *                       <br>
	 * @param providerName   Name of the Provider that exists in this Scope.<br>
	 *                       <br>
	 * @param providerObject Name of the object in the Provider.<br>
	 *                       <br>
	 * @return Object from the Provider. If no Provider or object exists in the
	 *         Provider then this method returns <code>null</code>.<br>
	 *         <br>
	 */
	Object createObject(final String name, final String providerName, final String providerObject);

	/**
	 * Removes an provider-object reference that is bound to this Scope.
	 * 
	 * @param name Name of the reference to remove.<br>
	 *             <br>
	 */
	void removeObject(final String name);

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Logs a message with the default Log Service.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'log-service' that
	 * implements the <code>com.warework.service.log.LogServiceFacade</code>
	 * interface and then invokes the log operation on it with a logger/client named
	 * <code>default-client</code>. If no Service or logger is found then log is not
	 * performed. <br>
	 * <br>
	 * By default the Log Service is not initialized. The easiest way to turn it on
	 * is with the method <code>enableDefaultLog()</code> of the Scope
	 * initialization class ( <code>com.warework.core.model.Scope</code>). Invoke
	 * it, create a new Scope with this configuration and the Scope will setup
	 * everything needed to run this log method. <br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * logger that the Log Service contains. So if your logger performs, for
	 * example, operations in a database, it connects to the database everytime it's
	 * closed and it keeps the connecion open.
	 * 
	 * @param message  Message to log.<br>
	 *                 <br>
	 * @param logLevel Indicates how to perform the log. Check out the default
	 *                 levels at
	 *                 <code>com.warework.service.log.LogServiceConstants</code> and
	 *                 keep in mind that each logger provides specific log levels
	 *                 (loggers may define those levels as public constants).<br>
	 *                 <br>
	 */
	void log(final String message, final int logLevel);

	/**
	 * Logs an info message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_INFO);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	void info(final String message);

	/**
	 * Logs a debug message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_DEBUG);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	void debug(final String message);

	/**
	 * Logs a warning message with the default Log Service. It is equals to
	 * <code>log(message, LogServiceConstants.LOG_LEVEL_WARN);</code>.
	 * 
	 * @param message Message to log.<br>
	 *                <br>
	 */
	void warn(final String message);

	/**
	 * Transforms a given object into another object.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'converter-service' that
	 * implements the
	 * <code>com.warework.service.converter.ConverterServiceFacade</code> interface
	 * and then invokes the <code>transform</code> operation on it.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * converter client that the Converter Service contains.
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
	 * Transforms an object from a Provider into another object.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'converter-service' that
	 * implements the
	 * <code>com.warework.service.converter.ConverterServiceFacade</code> interface
	 * and then invokes the <code>transform</code> operation on it.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * converter client that the Converter Service contains.
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

	/**
	 * Executes a process or task in a Workflow Engine.<br>
	 * <br>
	 * This method looks in the Scope for a Service named 'workflow-service' that
	 * implements the
	 * <code>com.warework.service.workflow.WorkflowServiceFacade</code> interface
	 * and then invokes the <code>execute</code> operation on it with a
	 * engine/client named <code>default-client</code>. If no Service or engine is
	 * found then operation is not performed.<br>
	 * <br>
	 * <strong>WARNING:</strong> This method automatically connects to the default
	 * engine that the Workflow Service contains.
	 * 
	 * @param contextScope Name of the Scope that exists in the context of this
	 *                     Scope and contains the Workflow Service where to execute
	 *                     the operation. If this argument is <code>null</code> then
	 *                     this method will look for the Workflow Service in this
	 *                     Scope.<br>
	 *                     <br>
	 * @param processName  Name of the process or task to execute.<br>
	 *                     <br>
	 * @param variables    Input variables for the process.<br>
	 *                     <br>
	 * @return Result of the execution of the process.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          process / task.<br>
	 *                          <br>
	 */
	Object execute(final String contextScope, final String processName, final Map<String, Object> variables)
			throws ServiceException;

}
