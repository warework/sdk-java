package com.warework.service.workflow;

import java.util.Map;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;

/**
 * The Workflow Service is one of the most important components of the
 * Framework. It is where you instruct the Framework about the operations that
 * your app has to execute. With one single interface, you can choose operations
 * from different catalogues and decide how to execute them.<br>
 * <br>
 * <span>Warework implements with this Service the </span><a target="blank"
 * HREF=
 * "http://www.oracle.com/technetwork/java/businessdelegate-137562.html"><span
 * >Business Delegate</span></a><span> software pattern. This pattern is widely
 * used to reduce coupling between tiers; most commonly in the presentation-tier
 * to hide the underlying implementation details of the business-tier, such as
 * lookup and access details of Web Services or the EJB architecture. In the
 * case of the Workflow Service, it implements the Business Delegate software
 * pattern to delegate the execution of operations to different Workflow
 * Engines.</span><br>
 * <br>
 * <span>A Workflow Engine is a software component that defines a task or
 * process, the rules governing process decisions, and routes
 * information.</span><span> Workflow Engines are extremely useful in software
 * applications because:</span><br>
 * <br>
 * <ul class="t0">
 * <li>You can have a central repository with definitions of operations and
 * share them across multiple applications.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>Operations can be loaded </span><span>dynamically so some parts of
 * your apps can be fixed in real time.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>There are Workflow Engines that allow you to execute operations remotely
 * on a server, reducing your app's workload.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>You can orchestrate different activities using a flowchart which
 * improves the visibility and agility of your business logic.</span><span>
 * Activities can be implemented with Java code (EJBs or simple Java methods) or
 * Web Services for example.</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>When the </span><span>Workflow Service receives a request to execute an
 * operation, the Service looks for a Workflow Engine to execute the operation.
 * Each Workflow Engine then is responsible for loading the definition of a task
 * (business process) and executing it. Depending on the implementation of the
 * Engine, it will execute the task in one way or another. For example,
 * </span><a target="blank"
 * HREF="http://activiti.org/"><span>Activiti</span></a><span> and </span><a
 * target="blank"
 * HREF="https://www.jboss.org/jbpm"><span>jBPM</span></a><span> are two
 * well known Workflow Engines that support native </span><a target="blank"
 * HREF=
 * "http://en.wikipedia.org/wiki/Business_Process_Model_and_Notation"><span>
 * BPMN</span></a><span> 2.0 execution.</span><br>
 * <br>
 * <span>The Workflow Service </span><span>includes by default the Flowbase
 * Workflow Engine. It is a script engine that allows you to execute tasks
 * defined in </span><a target="blank"
 * HREF="http://en.wikipedia.org/wiki/JSON"><span>JSON</span></a><span> files.
 * In Flowbase tasks, you can define variables, loops, expressions and functions
 * like in many other programming languages. </span> <br>
 * <br>
 * <br>
 * <strong> Create and retrieve a Workflow Service</strong><br>
 * <br>
 * <br>
 * <span>To create </span><span>the Workflow Service in a Scope, you always need
 * to provide a unique name for the Service and the
 * </span><span>WorkflowServiceImpl</span><span> class that exists in the
 * </span><span>com.warework.service.workflow</span><span> package:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Workflow&nbsp;Service&nbsp;and&nbsp;register&nbsp;it&nbsp;in&nbsp;a&nbsp;Scope.&nbsp;<br>scope.createService(&quot;workflow-service&quot;,&nbsp;WorkflowServiceImpl.class,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>Once</span><span> it is created, you can get it using the same name
 * (when you retrieve an instance of a Workflow Service, you will get the
 * </span><span>WorkflowServiceFacade</span><span> interface):</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;the&nbsp;Workflow&nbsp;Service.&nbsp;<br>WorkflowServiceFacade&nbsp;worklfowService&nbsp;=&nbsp;(WorkflowServiceFacade)&nbsp;scope.&nbsp;<br>&nbsp;&nbsp;&nbsp;getService(&quot;workflow-service&quot;);</code>
 * <br>
 * <br>
 * <span>The following example shows how to define the </span><span>Workflow
 * Service in an XML file:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;<br>&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;services&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;service&nbsp;name=&quot;worklfow-service&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.service.workflow.WorkflowServiceImpl&quot;&#47;&gt;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;services&gt;<br><br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * <span>Most of the times, you will need to specify a set of parameters that
 * configure how the </span><span>Service must work. Review the next section to
 * know how to define Workflow Clients with these parameters.</span><br>
 * <br>
 * <br>
 * <b> Add and connect Workflow Engines</b><br>
 * <br>
 * <span>Now the </span><span>Workflow Service is running but you need at least
 * one Client (remember, Clients in this Service are known as Engines) where to
 * perform operations. To add a Workflow Engine into the Service you have to
 * invoke method </span><span>createClient()</span><span> that exists in its
 * Facade. This method requests a name and a Connector which performs the
 * creation of the Engine. Let&rsquo;s see how to register a sample Engine in
 * this Service:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;an&nbsp;Engine&nbsp;in&nbsp;the&nbsp;Workflow&nbsp;Service.&nbsp;<br>workflowService.createClient(&quot;sample-client&quot;,&nbsp;SampleConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);</code>
 * <br>
 * <br>
 * <span>The </span><span>SampleConnector</span><span> class creates the Sample
 * Engine and registers it in the Workflow Service. After that, we have to tell
 * the Workflow Service that we want to perform operations with the Sample
 * Engine. We do so by connecting the Engine:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Sample&nbsp;Engine.&nbsp;<br>workflowService.connect(&quot;sample-client&quot;);</code>
 * <br>
 * <br>
 * <span>T</span><span>o configure Engines/Clients in XML you need to create a
 * separate XML file for the Workflow Service and reference it from the Scope
 * XML file. The following example shows how to define a
 * </span><span>Workflow</span><span> Service and the configuration file that it
 * requires for the Engines. The first thing you have to do is to register the
 * Service as we did before, but this time in the Scope XML file with
 * initialization parameters:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;<br>&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;services&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;service&nbsp;name=&quot;workflow-service&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.service.workflow.WorkflowServiceImpl&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-class&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;com.warework.loader.ProxyServiceSAXLoader&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-target&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;&#47;META-INF&#47;system&#47;workflow-service.xml&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;service&gt;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;services&gt;<br><br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * <span>Once it is registered in the Scope we proceed with the creation of the
 * Workflow Service configuration file. Based on the previous example, this
 * could be the content of the
 * </span><span><B>workflow-service.xml</B></span><span> file:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;sample-client&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.workflow.client.connector.Sample...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Connector&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <span>You can define as many </span><span>Engines/Clients as you need for the
 * Workflow Service. Once the Scope is started, you can work with Engines like
 * this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Scope.&nbsp;<br>ScopeFacade&nbsp;scope&nbsp;=&nbsp;...;&nbsp;<br><br>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;facade&nbsp;of&nbsp;the&nbsp;Workflow&nbsp;Service.&nbsp;<br>WorkflowServiceFacade&nbsp;workflowService&nbsp;=&nbsp;(WorkflowServiceFacade)&nbsp;scope.&nbsp;<br>&nbsp;&nbsp;&nbsp;getService(&quot;workflow-service&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Client.&nbsp;<br>workflowService.connect(&quot;sample-client&quot;);<br><br>&#47;&#47;&nbsp;Perform&nbsp;operations&nbsp;with&nbsp;the&nbsp;Client.&nbsp;<br>&hellip;<br><br>&#47;&#47;&nbsp;Disconnect&nbsp;the&nbsp;Client.&nbsp;<br>workflowService.disconnect(&quot;sample-client&quot;);</code>
 * <br>
 * <br>
 * <span>Some </span><span>Engines may require configuration parameters. The
 * following example shows how an Engine specifies one initialization
 * parameter:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;sample-client&quot;&nbsp;connector=&quot;...&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;...&quot;&nbsp;value=&quot;...&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <span>Remember to review the documentation of each </span><span>Workflow
 * Engine to know which parameters it accepts.</span><br>
 * <br>
 * <br>
 * <b> Perform Workflow operations</b><br>
 * <br>
 * <span>Each </span><span>Workflow Engine exposes its functionality in the
 * </span><span>WorkflowServiceFacade</span><span> interface. With this
 * interface you can execute five different types of operations.</span><br>
 * <br>
 * <span>In order to execute a Workflow operation you must start a process
 * </span><span>in a specific Engine first. By doing so, the Engine loads the
 * definition of one operation, sets up a new context for it and finally it runs
 * the operation in a new process. The following code shows how to start a new
 * process:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Start&nbsp;a&nbsp;Workflow&nbsp;process.&nbsp;<br>String&nbsp;processId&nbsp;=&nbsp;workflowService.startProcess(&quot;engine-name&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;process-name&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>Basically, the previous code specifies which Workflow Engine to use and
 * the name of the o</span><span>peration or task to execute. The result of the
 * operation is a string that identifies the process in the Workflow Engine. It
 * is very important that you store this ID in a variable if you want to perform
 * more operations in the future with the process.</span><br>
 * <br>
 * <span>Many times you may </span><span>also need to provide some variables for
 * the operation, like input parameters in a function call, just because they
 * are required in order to start the process properly:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;to&nbsp;store&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;variables&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>variables.put(&quot;variable-name-1&quot;,&nbsp;new&nbsp;Integer(123));<br>variables.put(&quot;variable-name-2&quot;,&nbsp;&quot;another-value&quot;);<br><br>&#47;&#47;&nbsp;Start&nbsp;the&nbsp;process&nbsp;with&nbsp;the&nbsp;variables.&nbsp;<br>String&nbsp;processId&nbsp;=&nbsp;workflowService.startProcess(&quot;engine-name&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;process-name&quot;,&nbsp;variables);</code>
 * <br>
 * <br>
 * <span>Once a process is running, you can retrieve the value</span><span>s of
 * variables that exist in the process. To achieve this task, you must provide
 * the ID of the process, the name of the variable and of course, the name of
 * the Engine where the process exists:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;a&nbsp;variable.&nbsp;<br>Object&nbsp;variableValue&nbsp;=&nbsp;workflowService.getVariable(&quot;engine-name&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;processId,&nbsp;&quot;variable-name&quot;);</code>
 * <br>
 * <br>
 * <span>There are processes in Workflow Engines that execute multiple
 * activities. Most of the times these activities are executed automatically in
 * a predefined order (the activities are executed and the process finishes) but
 * sometimes they may require the interaction with the user (or an
 * exte</span><span>rnal agent). For example, imagine a process that executes
 * activities A1 and A2 automatically but stops in A3 because a user input is
 * required in order to continue with the process. In such case, the user must
 * send a signal to A3 to continue with A4, A5 and so on until the process
 * finishes. Check out how to trigger a signal with the following code:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Send&nbsp;a&nbsp;signal&nbsp;to&nbsp;a&nbsp;waiting&nbsp;activity&nbsp;in&nbsp;a&nbsp;Workflow&nbsp;Engine.&nbsp;<br>workflowService.signal(&quot;engine-name&quot;,&nbsp;processId,&nbsp;&quot;activity-name&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>You can also provide some variables with the signal as they
 * mi</span><span>ght be required by the activity:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;to&nbsp;store&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;signal.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;variables&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;signal.&nbsp;<br>variables.put(&quot;variable-name-1&quot;,&nbsp;new&nbsp;Integer(123));<br>variables.put(&quot;variable-name-2&quot;,&nbsp;&quot;another-value&quot;);<br><br>&#47;&#47;&nbsp;Send&nbsp;a&nbsp;signal&nbsp;with&nbsp;variables.&nbsp;<br>workflowService.signal(&quot;engine-name&quot;,&nbsp;processId,&nbsp;&quot;activity-name&quot;,&nbsp;variables);</code>
 * <br>
 * <br>
 * <span>When a process finishes you may also need to clean up the context
 * created.</span><span> Use the following code to perform this action:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;an&nbsp;existing&nbsp;process&nbsp;from&nbsp;a&nbsp;Workflow&nbsp;Engine.&nbsp;<br>workflowService.destroy(&quot;engine-name&quot;,&nbsp;processId);</code>
 * <br>
 * <br>
 * <span>You can use the previous code also to force the shutdown of a running
 * process. Please note that this behavior is completely defined by the
 * underlying Workflow Engine (there might be E</span><span>ngines that does not
 * require this operation at all).</span><br>
 * <br>
 * <span>To finalize, </span><span>bear in mind that the
 * </span><span>WorkflowServiceFacade</span><span> interface allows you to
 * execute all of the previous steps at once with the
 * </span><span>execute</span><span> methods. These methods are very useful for
 * certain Workflow Engines, especially for the Flowbase Engine, because many
 * times you will need to start a process, retrieve a result and clean up
 * everything for a single operation execution. Let's review the first way to
 * invoke the </span><span>execute</span><span> method:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;to&nbsp;store&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;variables&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>variables.put(&quot;variable-name-1&quot;,&nbsp;new&nbsp;Integer(123));<br>variables.put(&quot;variable-name-2&quot;,&nbsp;&quot;another-value&quot;);<br><br>&#47;&#47;&nbsp;Execute&nbsp;a&nbsp;complete&nbsp;process&nbsp;in&nbsp;a&nbsp;Workflow&nbsp;Engine.&nbsp;<br>Object&nbsp;variableValue&nbsp;=&nbsp;workflowService.execute(&quot;engine-name&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;process-name&quot;,&nbsp;variables,&nbsp;&quot;variable-name&quot;,&nbsp;&quot;activity-name&quot;);</code>
 * <br>
 * <br>
 * As you can see, we provide in this method most of the variables that we used
 * in the previous examples:<br>
 * <br>
 * <ul class="t0">
 * <li><span><B>engine-name</B></span><span>. Name of the Workflow Engine where
 * to execute the operation.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><B>process-name</B></span><span>. Name of the process / task to
 * execute. </span> <br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><B>variables</B></span><span>. Optionally, you can provide some
 * variables. </span> <br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><B>variable-name</B></span><span>. This is the name of the variable
 * in the process that holds the result that you want to get.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span><B>activity-name</B></span><span>. Name of the activity in the
 * process that waits for the user to retrieve the result. This activity is
 * required in certain Workflow Engines just to wait before the process is
 * destroyed. </span> <br>
 * </li>
 * </ul>
 * <br>
 * <span>There is another </span><span>execute</span><span> method which
 * performs the same task but requires fewer parameters:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;to&nbsp;store&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;variables&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;variables&nbsp;for&nbsp;the&nbsp;process.&nbsp;<br>variables.put(&quot;variable-name-1&quot;,&nbsp;new&nbsp;Integer(123));<br>variables.put(&quot;variable-name-2&quot;,&nbsp;&quot;another-value&quot;);<br><br>&#47;&#47;&nbsp;Execute&nbsp;a&nbsp;complete&nbsp;process&nbsp;in&nbsp;a&nbsp;Workflow&nbsp;Engine.&nbsp;<br>Object&nbsp;variableValue&nbsp;=&nbsp;workflowService.execute(&quot;engine-name&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;process-name&quot;,&nbsp;variables);</code>
 * <br>
 * <br>
 * <span>The difference with the previous </span><span>execute</span><span>
 * method is that the names of the variable and the activity are fixed. In fact,
 * it calls the previous method with </span><span>presult</span><span> as the
 * variable name (stands for &quot;process result&quot;) and
 * </span><span>wait_before_end</span><span> as the activity name. So, you
 * should configure the processes or tasks with these names in order to use this
 * method.</span><br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface WorkflowServiceFacade extends ProxyServiceFacade {

	/**
	 * Executes a complete process/task in a Workflow Engine. This method starts
	 * the execution of a process and retrieves the value of a variable when the
	 * process is waiting in a specific activity. Once the value of the variable
	 * is obtained, a signal is thrown to the activity to wake it up and
	 * continue with the process until the end.
	 * 
	 * @param engine
	 *            Workflow Engine where to execute the process.<br>
	 * <br>
	 * @param processName
	 *            Name of the process to execute. <strong>This is not the ID of
	 *            the process</strong>. One process / task can be executed
	 *            multiple times and each execution will create an unique
	 *            process ID. While the name of the process is the same for
	 *            every instance, the ID of the process is unique for each
	 *            process instance.<br>
	 * <br>
	 * @param variables
	 *            Input variables for the process. This argument is optional.<br>
	 * <br>
	 * @param variableName
	 *            Name of the variable that holds the result of the execution of
	 *            the process.<br>
	 * <br>
	 * @param activityName
	 *            Activity name of the process that waits for the user to
	 *            retrieve the result (the value of the variable that holds the
	 *            result of the execution of the process)
	 * @return Result of the process.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the task /
	 *             process.<br>
	 * <br>
	 */
	Object execute(String engine, String processName,
			Map<String, Object> variables, String variableName,
			String activityName) throws ServiceException;

	/**
	 * Executes a process/task in a Workflow Engine. This method starts the
	 * execution of a process and retrieves the value of a variable named
	 * <code>WorkflowServiceConstants.DEFAULT_RESULT_VARIABLE_NAME</code> when
	 * the process is waiting in an activity named
	 * <code>WorkflowServiceConstants.DEFAULT_WAIT_BEFORE_END_ACTIVITY_NAME</code>
	 * . Once the value of the variable is obtained, a signal is thrown to the
	 * activity to wake it up and continue with the process until the end. .
	 * 
	 * @param engine
	 *            Workflow Engine where to execute the process.<br>
	 * <br>
	 * @param processName
	 *            Name of the process to execute. <strong>This is not the ID of
	 *            the process</strong>. One process / task can be executed
	 *            multiple times and each execution will create an unique
	 *            process ID. While the name of the process is the same for
	 *            every instance, the ID of the process is unique for each
	 *            process instance.<br>
	 * <br>
	 * @param variables
	 *            Input variables for the process. This argument is optional.<br>
	 * <br>
	 * @return Result of the process.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the task /
	 *             process.<br>
	 * <br>
	 */
	Object execute(String engine, String processName,
			Map<String, Object> variables) throws ServiceException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Starts the execution of a process.
	 * 
	 * @param engine
	 *            Workflow Engine where to execute the process.<br>
	 * <br>
	 * @param processName
	 *            Name of the process to execute. <strong>This is not the ID of
	 *            the process</strong>. One process / task can be executed
	 *            multiple times and each execution will create an unique
	 *            process ID. While the name of the process is the same for
	 *            every instance, the ID of the process is unique for each
	 *            process instance.<br>
	 * <br>
	 * @param variables
	 *            Name of the variable that holds the result of the execution of
	 *            the process.<br>
	 * <br>
	 * @return Process ID. Use this ID to perform more operations later on with
	 *         this process.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to start the task / process.<br>
	 * <br>
	 */
	String startProcess(String engine, String processName,
			Map<String, Object> variables) throws ServiceException;

	/**
	 * Gets the value of a variable that exists in a process of a Workflow
	 * Engine.
	 * 
	 * @param engine
	 *            Workflow Engine where to retrieve the value of the variable.
	 * @param processId
	 *            Process of the Workflow Engine where the variable exists.
	 *            <strong>This is not the name of the process</strong>. One
	 *            process / task can be executed multiple times and each
	 *            execution will create an unique process ID. While the name of
	 *            the process is the same for every instance, the ID of the
	 *            process is unique for each process instance.<br>
	 * <br>
	 * <br>
	 * @param variableName
	 *            Variable in the process that holds the value.<br>
	 * <br>
	 * @return Value of the variable.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to get the value of the
	 *             variable.<br>
	 * <br>
	 */
	Object getVariable(String engine, String processId, String variableName)
			throws ServiceException;

	/**
	 * Sends a signal to a waiting activity in a Workflow Engine.
	 * 
	 * @param engine
	 *            Workflow Engine where to send the signal.<br>
	 * <br>
	 * @param processId
	 *            ID of the process where the activity is waiting. <strong>This
	 *            is not the name of the process</strong>. One process / task
	 *            can be executed multiple times and each execution will create
	 *            an unique process ID. While the name of the process is the
	 *            same for every instance, the ID of the process is unique for
	 *            each process instance.<br>
	 * <br>
	 * <br>
	 * @param activityName
	 *            Name of the waiting activity.<br>
	 * <br>
	 * @param variables
	 *            Input variables for the activity. This is optional.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to send the signal.<br>
	 * <br>
	 */
	void signal(String engine, String processId, String activityName,
			Map<String, Object> variables) throws ServiceException;

	/**
	 * Removes an existing process from a Workflow Engine.
	 * 
	 * @param engine
	 *            Workflow Engine where to destroy the process.<br>
	 * <br>
	 * @param processId
	 *            ID of the process to destroy. <strong>This is not the name of
	 *            the process</strong>. One process / task can be executed
	 *            multiple times and each execution will create an unique
	 *            process ID. While the name of the process is the same for
	 *            every instance, the ID of the process is unique for each
	 *            process instance.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to destroy the task /
	 *             process.<br>
	 * <br>
	 */
	void destroy(String engine, String processId) throws ServiceException;

}
