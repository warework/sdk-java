package com.warework.module.business;

import java.lang.reflect.InvocationTargetException;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.helper.ReflectionL2Helper;

/**
 * Base functionality for the business factory.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractBusinessFactory {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope.
	private ScopeFacade scope;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	private AbstractBusinessFactory() {
		// DO NOTHING
	}

	/**
	 * Initializes the factory with the Scope.
	 * 
	 * @param scope Scope for the business objects to create.<br>
	 *              <br>
	 */
	public AbstractBusinessFactory(final ScopeFacade scope) {

		// Invoke default constructor.
		this();

		// Set Scope.
		this.scope = scope;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the scope used to create the business objects.
	 * 
	 * @return The scope used to create the business objects.
	 */
	public final ScopeFacade getScopeFacade() {
		return scope;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new business object.
	 * 
	 * @param type Business object to create.<br>
	 *             <br>
	 * @return A new instance of a business object.<br>
	 *         <br>
	 */
	protected final <E extends AbstractBusiness> E create(final Class<E> type) {
		try {
			return (E) ReflectionL2Helper.createInstance(type, new Class[] { ScopeFacade.class },
					new Object[] { scope });
		} catch (final NoSuchMethodException e) {
			return null;
		} catch (final InstantiationException e) {
			return null;
		} catch (final IllegalAccessException e) {
			return null;
		} catch (final InvocationTargetException e) {
			return null;
		}
	}

}
