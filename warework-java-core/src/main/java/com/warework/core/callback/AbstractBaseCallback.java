package com.warework.core.callback;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a callback operation. This class should
 * be used only internally by the Framework. <br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public abstract class AbstractBaseCallback {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// ATTRIBUTES

	/**
	 * Attribute that specifies the amount of milliseconds to wait before a
	 * callback operation is invoked. Useful for asynchronous operations /
	 * callbacks.
	 */
	public static final String ATTRIBUTE_MAX_CALLBACK_WAIT = "max-callback-wait";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Controller for the callback. Only Framework developers should use this
	 * class.<br>
	 */
	public final class Control {

		// ///////////////////////////////////////////////////////////////
		// CONSTANTS
		// ///////////////////////////////////////////////////////////////

		// CALLBACK STATES

		private static final byte STATUS_NOT_FINISHED = -1;

		private static final byte STATUS_FINISHED_FAILURE = 0;

		private static final byte STATUS_FINISHED_SUCCESS = 1;

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Exception that finished the execution with a failure.
		private Throwable exception;

		// Callback status.
		private byte status = STATUS_NOT_FINISHED;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * This constructor does not perform any operation.
		 */
		private Control() {
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Sets the value of an attribute.
		 * 
		 * @param name
		 *            Name of the attribute.<br>
		 * <br>
		 * @param value
		 *            Value of the attribute.<br>
		 * <br>
		 */
		public void setAttribute(String name, Object value) {

			// Initialize attributes storage when required.
			if (callbackAttributes == null) {
				callbackAttributes = new HashMap<String, Object>();
			}

			// Set the attribute.
			callbackAttributes.put(name, value);

		}

		/**
		 * Removes one or multiple attributes.
		 * 
		 * @param name
		 *            Name of the attribute to remove. If <code>null</code> then
		 *            every attribute is removed.<br>
		 * <br>
		 */
		public void removeAttribute(String name) {
			if (callbackAttributes != null) {
				if (name != null) {

					// Remove the attribute.
					callbackAttributes.remove(name);

					// Remove the attribute's storage.
					if (callbackAttributes.size() < 1) {
						callbackAttributes = null;
					}

				} else {
					callbackAttributes = null;
				}
			}
		}

		// ///////////////////////////////////////////////////////////////

		/**
		 * Performs common operations for failures and delegates the execution
		 * to the class that implements the callback.
		 * 
		 * @param failure
		 *            Error found.<br>
		 * <br>
		 */
		public void failure(CallbackFailure failure) {

			// Set the status of the operation.
			status = STATUS_FINISHED_FAILURE;

			// Save the exception.
			exception = failure.getException();

			// Delegate the execution to the class that implements the callback.
			onFailure(failure);

		}

		/**
		 * Performs common tasks for successfully executed operations and
		 * delegates the execution to the class that implements the callback.
		 * 
		 * @param success
		 *            Success operation data.<br>
		 * <br>
		 */
		public void success(CallbackSuccess success) {

			// Get the message to log when operation is successful.
			if ((success.getMessage() != null)
					&& (!success.getMessage().equals(
							CommonValueL1Constants.STRING_EMPTY))) {
				getScopeFacade().log(success.getMessage(),
						LogServiceConstants.LOG_LEVEL_INFO);
			}

			// Get the batch controller. DO NOT RENAME THIS VARIABLE AS 'batch'
			// TO PASS 'Hidden Field' Rule in Sonar.
			Batch batchController = getBatch();

			// Process the batch operation when required.
			if (batchController == null) {
				status = STATUS_FINISHED_SUCCESS;
			} else {

				// Increase callbacks counter.
				int callbacksCounter = batchController.count() + 1;

				// Validate the status of the bath operation.
				if ((callbacksCounter >= batchController.size())
						&& (status != STATUS_FINISHED_SUCCESS)) {

					// Set the status of the operation.
					status = STATUS_FINISHED_SUCCESS;

					// Log the end of the batch operation.
					getScopeFacade().log(
							"WAREWORK finalized batch operation '"
									+ getBatch().id() + "' successfully.",
							LogServiceConstants.LOG_LEVEL_DEBUG);

				}

				// Save the callbacks counter with the new value.
				batchController.callbacksCounter = callbacksCounter;

			}

			// Delegate the execution to the class that implements the callback.
			onSuccess(success);

		}

		// ///////////////////////////////////////////////////////////////

		/**
		 * Validates if the callback operation is finished.
		 * 
		 * @return <code>true</code> if operation is terminated and
		 *         <code>false</code> if not.<br>
		 * <br>
		 */
		public boolean isFinished() {
			return (status != STATUS_NOT_FINISHED);
		}

		/**
		 * Validates if the callback operation finished with a failure.
		 * 
		 * @return <code>true</code> if operation is terminated in failure and
		 *         <code>false</code> if not.<br>
		 * <br>
		 */
		public boolean isFailure() {
			return (status == STATUS_FINISHED_FAILURE);
		}

		/**
		 * Validates if the callback operation finished successfully.
		 * 
		 * @return <code>true</code> if operation terminated successfully and
		 *         <code>false</code> if not.<br>
		 * <br>
		 */
		public boolean isSuccess() {
			return (status == STATUS_FINISHED_SUCCESS);
		}

		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the exception that finished the execution with a failure.
		 * 
		 * @return Exception that represents the error.<br>
		 * <br>
		 */
		public Throwable getException() {
			return exception;
		}

		/**
		 * Waits until the callback operation is executed.
		 * 
		 * @return <code>true</code> if operation finished without problems and
		 *         <code>false</code> if operation took more than the
		 *         milliseconds specified with
		 *         <code>ATTRIBUTE_MaxWaitMillis</code>.<br>
		 * <br>
		 */
		public boolean waitFinished() {

			// Get the milliseconds to wait.
			long maxWait = -1;
			if ((getAttribute(ATTRIBUTE_MAX_CALLBACK_WAIT) != null)
					&& (getAttribute(ATTRIBUTE_MAX_CALLBACK_WAIT) instanceof Long)) {
				maxWait = ((Long) getAttribute(ATTRIBUTE_MAX_CALLBACK_WAIT))
						.longValue();
			}

			// Get the current time.
			long start = System.currentTimeMillis();

			// Wait until operation is done.
			while (status == STATUS_NOT_FINISHED) {
				if ((maxWait >= 0)
						&& ((System.currentTimeMillis() - start) > maxWait)) {
					return false;
				}
			}

			// At this point, operation finished without problems.
			return true;

		}

		// ///////////////////////////////////////////////////////////////

		/**
		 * Sets up the callback to perform batch operations.
		 * 
		 * @param size
		 *            Batch size (number of items to process).<br>
		 * <br>
		 * @return Object with information about the batch operation.<br>
		 * <br>
		 */
		public Batch initBatch(int size) {

			// Create or destroy batch related attributes.
			if (size > -1) {

				// Create batch operation.
				batch = new Batch(StringL1Helper.createSimpleID(), size, 0,
						System.currentTimeMillis());

				// Log the end of the batch operation.
				getScopeFacade().log(
						"WAREWORK initialized batch operation '" + batch.id()
								+ "'.", LogServiceConstants.LOG_LEVEL_DEBUG);

			} else {
				batch = null;
			}

			// Return the batch controller.
			return batch;

		}

		/**
		 * Removes every attribute related with the batch operation.
		 */
		public void destroyBatch() {
			initBatch(-1);
		}

	}

	/**
	 * Batch operation information. Developers should use this class to control
	 * the state of the batch operation.<br>
	 */
	public final class Batch {

		// ///////////////////////////////////////////////////////////////
		// CONSTANTS
		// ///////////////////////////////////////////////////////////////

		// Percentage max value.
		private static final int MAX_PERCENTAGE = 100;

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// ID for the batch operation.
		private String id;

		// Amount of callbacks to perform in a batch operation. This attribute
		// is mandatory in order to make batch operations work.
		private int size;

		// Amount of callbacks executed in a batch operation. This attribute is
		// mandatory in order to make batch operations work.
		private int callbacksCounter;

		// Time (in milliseconds) when the batch operation started.
		private long startTimeMillis;

		// ///////////////////////////////////////////////////////////////
		// CONSTRUCTORS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Creates a batch controller.
		 * 
		 * @param id
		 *            ID for the batch operation.<br>
		 * <br>
		 * @param size
		 *            Amount of callbacks to perform in a batch operation. This
		 *            attribute is mandatory in order to make batch operations
		 *            work.<br>
		 * <br>
		 * @param callbacksCounter
		 *            Amount of callbacks executed in a batch operation. This
		 *            attribute is mandatory in order to make batch operations
		 *            work.<br>
		 * <br>
		 * @param startTimeMillis
		 *            Time (in milliseconds) when the batch operation started.<br>
		 * <br>
		 */
		private Batch(String id, int size, int callbacksCounter,
				long startTimeMillis) {
			this.id = id;
			this.size = size;
			this.callbacksCounter = callbacksCounter;
			this.startTimeMillis = startTimeMillis;
		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the ID of the batch operation.
		 * 
		 * @return Batch operation ID.<br>
		 * <br>
		 */
		public String id() {
			return id;
		}

		/**
		 * Gets the amount of callbacks to perform in the batch operation.
		 * 
		 * @return Number of callbacks to execute.<br>
		 * <br>
		 */
		public int size() {
			return size;
		}

		/**
		 * Counts the amount of callbacks executed in the batch operation.
		 * 
		 * @return Number of callbacks executed in the batch operation.<br>
		 * <br>
		 */
		public int count() {
			return callbacksCounter;
		}

		/**
		 * Gets the time when the batch operation started.
		 * 
		 * @return Time in milliseconds.<br>
		 * <br>
		 */
		public long startTime() {
			return startTimeMillis;
		}

		/**
		 * Gets how long is taking the current batch operation.
		 * 
		 * @return Time in milliseconds.<br>
		 * <br>
		 */
		public long duration() {

			// Get the time when batch operation started.
			long start = startTime();

			// Return operation time.
			if (start > -1) {
				return System.currentTimeMillis() - start;
			}

			// At this point, duration cannot be calculated.
			return -1;

		}

		/**
		 * Gets the progress of the current batch operation.
		 * 
		 * @return Percentage of completion.<br>
		 * <br>
		 */
		public float progress() {

			// Get the size of the batch operation.
			int callbackSize = size();

			// Get the number of callback oerations performed.
			int count = count();

			// Return the percentage of completion.
			if ((callbackSize > -1) && (count > -1)) {

				// Calculate percentage.
				float result = ((count * MAX_PERCENTAGE) / callbackSize);

				// Return percentage value.
				if (result < MAX_PERCENTAGE) {
					return result;
				} else {
					return MAX_PERCENTAGE;
				}

			}

			// At this point, progress cannot be calculated.
			return -1f;

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Scope.
	private ScopeFacade scope;

	// Attributes (as string-object pairs) for this callback.
	private Map<String, Object> callbackAttributes;

	// Controller for the callback.
	private Control control = new Control();

	// Batch operation information.
	private Batch batch;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Callback method to implement when operation is a failure.
	 * 
	 * @param failure
	 *            Failure operation data.<br>
	 * <br>
	 */
	protected abstract void onFailure(CallbackFailure failure);

	/**
	 * Callback method to implement when operation is successful.
	 * 
	 * @param success
	 *            Success operation data.<br>
	 * <br>
	 */
	protected abstract void onSuccess(CallbackSuccess success);

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private AbstractBaseCallback() {

	}

	/**
	 * Creates a callback operation.
	 * 
	 * @param scope
	 *            Scope where this callback operation should belong to.<br>
	 * <br>
	 */
	public AbstractBaseCallback(ScopeFacade scope) {

		// Invoke default constructor.
		this();

		// Set the Scope.
		this.scope = scope;

	}

	/**
	 * Creates a callback operation.
	 * 
	 * @param scope
	 *            Scope where this callback operation should belong to.<br>
	 * <br>
	 * @param attributes
	 *            Attributes (as string-object pairs) for this callback.<br>
	 * <br>
	 */
	public AbstractBaseCallback(ScopeFacade scope,
			Map<String, Object> attributes) {

		// Create the callback operation with the Scope.
		this(scope);

		// Set the attributes.
		this.callbackAttributes = attributes;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where this callback operation belongs to.
	 * 
	 * @return Scope.<br>
	 * <br>
	 */
	public ScopeFacade getScopeFacade() {
		return scope;
	}

	/**
	 * Gets the controller for the callback operation. Developers should not use
	 * this method.
	 * 
	 * @return Controller for the callback operation.<br>
	 * <br>
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * Gets an object with information about the batch operation.
	 * 
	 * @return Batch operation object.<br>
	 * <br>
	 */
	public Batch getBatch() {
		return batch;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the attributes.
	 * 
	 * @return Attributes' names or null if no one exist.<br>
	 * <br>
	 */
	public Enumeration<String> getAttributeNames() {
		if (callbackAttributes != null) {
			return DataStructureL1Helper.toEnumeration(callbackAttributes
					.keySet());
		} else {
			return null;
		}
	}

	/**
	 * Gets the value of an attribute.
	 * 
	 * @param name
	 *            Name of the attribute.<br>
	 * <br>
	 * @return Value of the attribute.<br>
	 * <br>
	 */
	public Object getAttribute(String name) {

		// Retrieve the value only if attributes is not empty.
		if (callbackAttributes != null) {
			return callbackAttributes.get(name);
		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Gets the boolean value of an attribute.
	 * 
	 * @param name
	 *            Name of the attribute.<br>
	 * <br>
	 * @return <code>true</code> if the attribute is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that
	 *         is equals, in any case form, to <code>true</code>. Otherwise,
	 *         this method returns <code>false</code> (for example: if it does
	 *         not exist).<br>
	 * <br>
	 */
	public boolean isAttribute(String name) {

		// Get the value of the attribute.
		Object value = getAttribute(name);

		// Decide the boolean value of the attribute.
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if ((value instanceof String)
					&& (((String) value)
							.equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
				return true;
			}
		}

		// At this point, default result is false.
		return false;

	}

}
