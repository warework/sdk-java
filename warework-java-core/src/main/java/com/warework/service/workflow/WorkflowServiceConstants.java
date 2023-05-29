package com.warework.service.workflow;

import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Workflow Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class WorkflowServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "workflow";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	/**
	 * Name of the variable that holds the result of the process or task. Use this
	 * name in your processes and the Workflow Service will be able to find and
	 * return the result.
	 */
	public static final String DEFAULT_RESULT_VARIABLE_NAME = "presult";

	/**
	 * In Workflows that perform activities use this name to specify the last
	 * activity in the process. If you use this name for the last activity, the
	 * Workflow Service knows that it can retrieve variable values before the
	 * process finishes and where to send a signal (which activity) to trigger the
	 * finalization of the process.
	 */
	public static final String DEFAULT_WAIT_BEFORE_END_ACTIVITY_NAME = "wait_before_end";

	// ENGINES

	/**
	 * Flowbase Engine name. This is the name of the built-in workflow engine.
	 */
	public static final String FLOWBASE = "flowbase";

	// PACKAGES

	/**
	 * Package name for FLowbase Functions.
	 */
	public static final String PACKAGE_FLOWBASE_FUNCTIONS = ScopeL1Constants.PACKAGE_WAREWORK
			+ ReflectionL1Helper.PACKAGE_SEPARATOR + CommonValueL1Constants.STRING_SERVICE
			+ ReflectionL1Helper.PACKAGE_SEPARATOR + SERVICE + ReflectionL1Helper.PACKAGE_SEPARATOR + FLOWBASE
			+ ReflectionL1Helper.PACKAGE_SEPARATOR + CommonValueL1Constants.STRING_FUNCTION;

	// OPERATION NAMES

	/**
	 * Executes a process/task in a Workflow Engine. Use the following parameters in
	 * order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client
	 * (Workflow Engine) where to perform the operation. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>process-name</code></strong>: Specifies the task or process
	 * to execute. This parameter must be a <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>variables</code></strong>: Optional parameter that
	 * specifies a <code>java.util.Map</code> with variable names and values for the
	 * operation to execute.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>result-variable-name</code></strong>: Optional operation
	 * parameter that specifies the name of the variable that holds the result of
	 * the execution of the process / task. This parameter must be a
	 * <code>java.lang.String</code>. If it is not specified then the default value
	 * of <code>process-result</code> is used.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>activity-name</code></strong>: Optional parameter that
	 * specifies the activity name of the process that waits for the user to
	 * retrieve the result (the value of the variable that holds the result of the
	 * execution of the process). This parameter must be a
	 * <code>java.lang.String</code>. If it is not specified then the default value
	 * of <code>wait-before-end</code> is used.<br>
	 * <br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Object</code> with the result of the
	 * process.
	 */
	public static final String OPERATION_NAME_EXECUTE = "execute";

	/**
	 * Starts the execution of a process in a Workflow Engine. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client
	 * (Workflow Engine) where to start the process. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>process-name</code></strong>: Specifies the task or process
	 * to execute. This parameter must be a <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>variables</code></strong>: Optional parameter that
	 * specifies a <code>java.util.Map</code> with variable names and values for the
	 * operation to execute.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.String</code> with the process ID.
	 * Use this ID to perform more operations later on with this process.
	 */
	public static final String OPERATION_NAME_START_PROCESS = "start-process";

	/**
	 * Gets the value of a variable that exists in a process of a Workflow Engine.
	 * Use the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client
	 * (Workflow Engine) where to get the variable. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>process-id</code></strong>: Specifies the process of the
	 * Workflow Engine where the variable exists. <strong>This is not the name of
	 * the process</strong>. One process / task can be executed multiple times and
	 * each execution will create an unique process ID. While the name of the
	 * process is the same for every instance, the ID of the process is unique for
	 * each process instance. This parameter must be a
	 * <code>java.lang.String</code>. <br>
	 * <br>
	 * </li>
	 * <li><strong><code>variable-name</code></strong>: Specifies the name of a
	 * variable in a Workflow process. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation returns a <code>java.lang.Object</code> with the value of the
	 * variable.
	 */
	public static final String OPERATION_NAME_GET_VARIABLE = "get-variable";

	/**
	 * Sends a signal to a waiting activity in a Workflow Engine. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client
	 * (Workflow Engine) where to send the signal. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>process-id</code></strong>: Specifies the process of the
	 * Workflow Engine where to send the signal. <strong>This is not the name of the
	 * process</strong>. One process / task can be executed multiple times and each
	 * execution will create an unique process ID. While the name of the process is
	 * the same for every instance, the ID of the process is unique for each process
	 * instance. This parameter must be a <code>java.lang.String</code>. <br>
	 * <br>
	 * </li>
	 * <li><strong><code>activity-name</code></strong>: Parameter that specifies the
	 * activity name of the process that waits for the user to retrieve the result
	 * (the value of the variable that holds the result of the execution of the
	 * process). This parameter must be a <code>java.lang.String</code>. <br>
	 * <br>
	 * </li>
	 * <li><strong><code>variables</code></strong>: Optional parameter that
	 * specifies a <code>java.util.Map</code> with variable names and values for the
	 * activity.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_SIGNAL = "signal";

	/**
	 * Removes an existing process from a Workflow Engine. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><strong><code>client-name</code></strong>: The name of the Client
	 * (Workflow Engine) where to remove the process. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><strong><code>process-id</code></strong>: Specifies the process of the
	 * Workflow Engine to remove. <strong>This is not the name of the
	 * process</strong>. One process / task can be executed multiple times and each
	 * execution will create an unique process ID. While the name of the process is
	 * the same for every instance, the ID of the process is unique for each process
	 * instance. This parameter must be a <code>java.lang.String</code>. <br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_DESTROY = "destroy";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies the task or process to execute.
	 * <strong>This is not the ID of the process</strong>. One process / task can be
	 * executed multiple times and each execution will create an unique process ID.
	 * While the name of the process is the same for every instance, the ID of the
	 * process is unique for each process instance. This parameter must be a
	 * <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_PROCESS_NAME = "process-name";

	/**
	 * Operation parameter that identifies an specific process ot task instance.
	 * <strong>This is not the name of the process</strong>. One process / task can
	 * be executed multiple times and each execution will create an unique process
	 * ID. While the name of the process is the same for every instance, the ID of
	 * the process is unique for each process instance. This parameter must be a
	 * <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_PROCESS_ID = "process-id";

	/**
	 * Operation parameter that specifies a <code>java.util.Map</code> with variable
	 * names and values.
	 */
	public static final String OPERATION_PARAMETER_VARIABLES = "variables";

	/**
	 * Operation parameter that specifies the name of the variable that holds the
	 * result of the execution of the process / task. This parameter must be a
	 * <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_RESULT_VARIABLE_NAME = "result-variable-name";

	/**
	 * Operation parameter that specifies the name of an activity in a Workflow
	 * process. This parameter must be a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_ACTIVITY_NAME = "activity-name";

	/**
	 * Operation parameter that specifies the name of a variable in a Workflow
	 * process. This parameter must be a <code>java.lang.String</code>.
	 */
	public static final String OPERATION_PARAMETER_VARIABLE_NAME = "variable-name";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private WorkflowServiceConstants() {
	}

}
