package com.warework.service.datastore.client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.warework.core.callback.CallbackInvoker;
import com.warework.core.service.client.ClientException;
import com.warework.service.log.LogServiceConstants;

/**
 * <u> RDBMS View for JDBC Data Stores</u><br>
 * <br>
 * <span>Th</span><span>e </span><span>JdbcViewImpl</span><span> class (package:
 * </span><span>com.warework.service.datastore.client</span><span>) is a View
 * that implements the </span><span>RdbmsView</span><span> interface and it
 * provides a better way to interact with relational databases using JDBC. Now
 * we are going to see how to associate a View of this type with a JDBC Data
 * Store and how to perform operations with the View.</span><br>
 * <br>
 * <ul class="t0">
 * <li>Setting up the View<br>
 * </li>
 * </ul>
 * <br>
 * <span>There is no need to configure this View</span><span> with
 * initialization parameters, so just add the View in the stack of Views of the
 * JDBC Data Store:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;the&nbsp;RDBMS&nbsp;View&nbsp;for&nbsp;JDBC&nbsp;Data&nbsp;Stores.&nbsp;<br>datastoreService.addView(&quot;jdbc-datastore&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;JdbcViewImpl.class,&nbsp;&quot;rdbms-view&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Data Store Service XML
 * configuration file:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&hellip;&nbsp;xsd&#47;datastore-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.connector.JdbcConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;com.warework.service.datastore.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;client.JdbcViewImpl&quot;&nbsp;name=&quot;rdbms-view&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <br>
 * You can also provide short class names to identify Connectors and Views:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;datastore-service.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;connector=&quot;Jdbc&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;view&nbsp;class=&quot;Jdbc&quot;&nbsp;name=&quot;rdbms-view&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;views&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>Working with the </span><span>RDBMS View for JDBC Data
 * Stores</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>Now we are going to review the </span><span>methods provided by the
 * </span><span>JdbcViewImpl</span><span> class. Two basic operations are
 * allowed here:</span><br>
 * <br>
 * <span><I>Update operations</I></span><br>
 * <br>
 * <br>
 * <span>Executes a given SQL statement, which may be an
 * </span><span>INSERT</span><span>, </span><span>UPDATE</span><span>, or
 * </span><span>DELETE</span><span> statement or an SQL statement that returns
 * nothing, such as an SQL DDL statement (for example: </span><span>CREATE
 * TABLE</span><span>).</span><br>
 * <br>
 * <br>
 * <I>Query operations</I><br>
 * <br>
 * <br>
 * <span>Executes a given SQL statement, typically a static SQL
 * </span><span>SELECT</span><span> statement, to retrieve data from the
 * relational database.</span><br>
 * <br>
 * Even before you perform any of these operations, what you have to do first is
 * to connect to the relational database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View&nbsp;interface.<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * Perfect, now a connection with the database is ready to accept SQL commands.
 * In the following examples we are going to save some information in a
 * relational database but before that, it is recommended to begin a transaction
 * with the database. To do it you have to perform the following actions:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View.<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;relational&nbsp;database.&nbsp;<br>view.beginTransaction();</code>
 * <br>
 * <br>
 * Now it is the right time to perform some update operations. First, we are
 * going to add one row in a table of the database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View.<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(&quot;jdbc-datastore&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;execute.&nbsp;<br>String&nbsp;sql&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(1,&nbsp;'John&nbsp;Wood')&quot;;<br><br>&#47;&#47;&nbsp;Run&nbsp;the&nbsp;SQL&nbsp;update&nbsp;statement.&nbsp;<br>view.executeUpdate(sql,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * <span>Another option is to execute multiple statements at once. You can do it
 * by specifying a separ</span><span>ator character which delimits each
 * statement:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;first&nbsp;user.&nbsp;<br>String&nbsp;sql1&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;&quot;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;+&nbsp;&quot;(1,&nbsp;'John&nbsp;Wood')&quot;;<br><br>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;second&nbsp;user.&nbsp;<br>String&nbsp;sql2&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;&quot;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;+&nbsp;&quot;(2,&nbsp;'James&nbsp;Sharpe')&quot;;<br><br>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;third&nbsp;user.&nbsp;<br>String&nbsp;sql3&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;&quot;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;+&nbsp;&quot;(3,&nbsp;'Sofia&nbsp;Green')&quot;;<br><br>&#47;&#47;&nbsp;Execute&nbsp;three&nbsp;SQL&nbsp;update&nbsp;statements&nbsp;at&nbsp;once.&nbsp;<br>view.executeUpdate(sql1&nbsp;+&nbsp;&quot;;&quot;&nbsp;+&nbsp;sql2&nbsp;+&nbsp;&quot;;&quot;&nbsp;+&nbsp;sql3,&nbsp;null,&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * <br>
 * Perform update operations like this when you have to dynamically construct
 * SQL statements in Java. If your statements are not too complex to create,
 * like the ones we saw in the previous example, you should consider storing
 * them on separate files as they are easier to maintain. A very convenient way
 * to keep SQL statements in separate files consist of keeping each statement
 * (or a set of related statements) in an independent text file, for example:
 * <span>create-user.sql</span>, <span>delete-user.sql</span>,
 * <span>update-user.sql</span>, etc. Later on we can read these text files with
 * a Provider (Warework recommends you to use the FileText Provider for this
 * task) and use this Provider to read the statements for the RDBMS View.
 * Remember that you can define a default Provider for a View when you associate
 * a View to a Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;and&nbsp;link&nbsp;a&nbsp;Provider&nbsp;to&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;jdbc-datastore&quot;,&nbsp;JdbcViewImpl.class,&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;rdbms-view&quot;,&nbsp;&quot;sql-provider&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * <span>The </span><span>sql-provider</span><span> Provider now can be used in
 * the View as the default Provider to read text files from a specific
 * directory. Let us say we have the following content for
 * </span><span>create-user.sql</span><span>:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(1,&nbsp;'John&nbsp;Wood')</code>
 * <br>
 * <br>
 * <br>
 * <span>If </span><span>sql-provider</span><span> is the default Provider in a
 * RDBMS View, we can read the content of this file with the following
 * code:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'create-user.sql'&nbsp;and&nbsp;execute&nbsp;it.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * <span>When </span><span>executeUpdateByName</span><span> is invoked, these
 * actions are performed:</span><br>
 * <br>
 * <br>
 * <br>
 * <br>
 * <span>The RDBMS View requests the </span><span>create-user</span><span>
 * object to </span><span>sql-provider</span><span>.</span><br>
 * <br>
 * <br>
 * <span>sql-provider</span><span> reads the content of
 * </span><span>create-user.sql</span><span> and returns it (as a
 * </span><span>String</span><span> object).</span><br>
 * <br>
 * <br>
 * <span>The RDBMS View executes the statement included at
 * </span><span>create-user.sql</span><span> in the JDBC Data Store. </span>
 * <br>
 * <br>
 * <br>
 * <br>
 * <br>
 * <span>Th</span><span>e RDBMS View and the FileText Provider are perfect
 * mates. Both, in combination, will simplify a lot the process of executing
 * scripts in your database. Just write simple text files with SQL statements
 * and let Warework execute them for you. It is recommended that you check out
 * the documentation associated to the FileText Provider to fully take advantage
 * of this feature.</span><br>
 * <br>
 * <br>
 * <span>If we need a generic statement to create new users in the relational
 * database, we can define the script </span><span>create-user.sql</span><span>
 * with some variables, like this:</span><br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME})</code>
 * <br>
 * <br>
 * Then replace these variables with the values that you need:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;user&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>Remember that you can </span><span>also specify
 * </span><span>null</span><span> values with the
 * </span><span>JDBCNullType</span><span>:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;JDBCNullType.VARCHAR);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;user&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>When your script contains multiple statements, you also have to
 * indicate the character that de</span><span>limits each statement. Suppose we
 * have the following </span><span>create-user.sql</span><span>
 * script:</span><br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME});<br>INSERT&nbsp;INTO&nbsp;ACTIVE_USERS&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID});</code>
 * <br>
 * <br>
 * <br>
 * Now we can replace variables in multiple statements with this code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;user&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * <span>We can also use a Provider that is not the default Provider of the
 * View. In this case, we just need to specify the Pr</span><span>ovider where
 * to retrieve the statement to execute:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Execute&nbsp;a&nbsp;statement&nbsp;from&nbsp;the&nbsp;Provider&nbsp;that&nbsp;we&nbsp;define.&nbsp;<br>view.executeUpdateByName(&quot;another-provider&quot;,&nbsp;&quot;create-user&quot;,&nbsp;values,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * <span>This time, the </span><span>create-user.sql</span><span> statement is
 * not retrieved by the default Provider of the View. The Framework requests
 * this statement from a Provider named
 * &quot;</span><span>another-provider</span><span>&quot; and once it is loaded,
 * then it is executed in the Data Store.</span><br>
 * <br>
 * <span>Th</span><span>e RDBMS View also allows developers to
 * </span><span>define a callback</span><span> object that will be invoked when
 * the operation is done or it fails. Check out the following
 * example:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;object&nbsp;once&nbsp;operation&nbsp;is&nbsp;executed.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;successful&nbsp;operation&nbsp;here.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;'result'&nbsp;is&nbsp;null&nbsp;in&nbsp;RDBMS&nbsp;update&nbsp;operations.<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * <span>Now the RDBMS View redirects the execution to the callback object when
 * </span><span>executeUpdateByName</span><span> is invoked. If operation is
 * successful then method </span><span>onSuccess</span><span> is executed.
 * Otherwise, if any error is found, then method
 * </span><span>onFailure</span><span> is executed. </span> <br>
 * <br>
 * <span>As you can see, creating the callback object is fairly easy. It is
 * mandatory to provide a Scope (you can retrieve it from the Data Store Service
 * for example) and implement two methods. When a callback object is defined for
 * update operations of the RDBMS View (like
 * </span><span>executeUpdateByName</span><span>), it is important to bear in
 * mind that </span><span>onSuccess</span><span> method argument is always
 * </span><span>null</span><span>. </span> <br>
 * <br>
 * <span>Warework also automatically detects </span><span>batch
 * operations</span><span> in scripts with multiple statements so you can
 * perform an operation at </span><span>onSuccess</span><span> every time a
 * single statement is executed in the Data Store. For example, if we have the
 * following script:</span><br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME});<br>INSERT&nbsp;INTO&nbsp;ACTIVE_USERS&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID});</code>
 * <br>
 * <br>
 * <br>
 * <span>We can log the percentage of completion with this code:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;object&nbsp;once&nbsp;operation&nbsp;is&nbsp;executed.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;This&nbsp;method&nbsp;is&nbsp;executed&nbsp;twice,&nbsp;one&nbsp;for&nbsp;every&nbsp;statement&nbsp;in&nbsp;the<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;'create-user'&nbsp;script.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;progress:&nbsp;&quot;&nbsp;+&nbsp;getBatch().progress());<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * <span>Method </span><span>getBatch</span><span> provides the following useful
 * information about the batch operation in execution:</span><br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().count()</span>: counts the amount of callbacks executed
 * in the batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().duration()</span>: gets how long (in milliseconds) is
 * taking the current batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().id()</span>: gets the ID of the batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().progress()</span>: gets the percentage of completion of
 * the current batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().size()</span>: gets the total of callbacks to perform in
 * the batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <ul class="t0">
 * <li><span>getBatch().startTime()</span>: gets the time (in milliseconds )
 * when the batch operation started.<br>
 * </li>
 * </ul>
 * <br>
 * It is also possible to pass objects / attributes to the callback so you can
 * use them at <span>onSuccess</span> or <span>onFailure</span>. For this
 * purpose, we have to use a <span>Map</span> when the callback is created.
 * Check out this example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Attributes&nbsp;for&nbsp;the&nbsp;callback.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;attributes&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;attributes.&nbsp;<br>attributes.put(&quot;color&quot;,&nbsp;&quot;red&quot;);<br>attributes.put(&quot;password&quot;,&nbsp;new&nbsp;Integer(123));<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;with&nbsp;attributes.&nbsp;<br>view.executeUpdateByName(&quot;statement-name&quot;,&nbsp;null,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade(),&nbsp;attributes){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;color&nbsp;=&nbsp;(String)&nbsp;getAttribute(&quot;color&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Retrieve&nbsp;every&nbsp;attribute&nbsp;name&nbsp;with&nbsp;'getAttributeNames()'.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integer&nbsp;password&nbsp;=&nbsp;(Integer)&nbsp;getAttribute(&quot;password&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * <span>Every update operation that we performed in the previous examples is
 * related to the transaction that we created earlier in this section. Once the
 * work is done, you should e</span><span>ither commit or rollback the
 * transaction. If the operations were executed without problems, then you
 * should perform </span><span>commit</span><span> to register the changes in
 * the database:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commits&nbsp;changes&nbsp;in&nbsp;the&nbsp;Relational&nbsp;Database&nbsp;Management&nbsp;System.&nbsp;<br>view.commit();</code>
 * <br>
 * <br>
 * <span>In the other hand, if you find a failure, something unexpected happened
 * or you just do not want to register the changes in the database, then you
 * should perform </span><span>rollback</span><span> to undo every update
 * operation executed since the transaction was started:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Cancel&nbsp;latest&nbsp;update&nbsp;operations.&nbsp;<br>view.rollback();</code>
 * <br>
 * <br>
 * <span>We have reviewed with this interface how to connect to a database and
 * perform update operations in it with a transaction. Now we are going to focus
 * on query operations to know how to retrieve data from a relational database.
 * The following code is an example to perform this action:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * <span>This code executes the SQL statement into the relational database and
 * returns an o</span><span>bject that represents the result provided by the
 * database. By default, this result is in the form of a
 * </span><span>ResultRows</span><span> object (check out the specific types
 * returned by the implementation class of this View because each Data Store may
 * provide the chance to get a different object as result) which represents the
 * table of data returned by the database. It allows iterating each row of the
 * table result and picking up the values of its columns. Check it out with the
 * following example:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows&nbsp;until&nbsp;the&nbsp;end&nbsp;of&nbsp;the&nbsp;table.&nbsp;First&nbsp;row&nbsp;is&nbsp;1,&nbsp;second&nbsp;2&nbsp;and&nbsp;<br>&#47;&#47;&nbsp;so&nbsp;&nbsp;on.&nbsp;You&nbsp;must&nbsp;perform&nbsp;'result.next()'&nbsp;at&nbsp;least&nbsp;one&nbsp;time&nbsp;to&nbsp;point&nbsp;<br>&#47;&#47;&nbsp;the&nbsp;cursor&nbsp;to&nbsp;the&nbsp;first&nbsp;row.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;*<br>&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;For&nbsp;each&nbsp;row&nbsp;we&nbsp;can&nbsp;retrieve&nbsp;the&nbsp;value&nbsp;of&nbsp;each&nbsp;column.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;*&#47;<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;boolean&nbsp;value&nbsp;of&nbsp;a&nbsp;column.&nbsp;If&nbsp;the&nbsp;value&nbsp;is&nbsp;SQL&nbsp;NULL<br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;(it&nbsp;is&nbsp;null&nbsp;in&nbsp;the&nbsp;database),&nbsp;the&nbsp;value&nbsp;returned&nbsp;is&nbsp;null.&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean&nbsp;column1&nbsp;=&nbsp;result.getBoolean(&quot;COLUMN1&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;numeric&nbsp;value&nbsp;of&nbsp;a&nbsp;column.&nbsp;You&nbsp;must&nbsp;specify&nbsp;the<br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;numeric&nbsp;type&nbsp;to&nbsp;get.&nbsp;<br>&nbsp;&nbsp;&nbsp;Short&nbsp;column2A&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2A&quot;,&nbsp;Short.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Integer&nbsp;column2B&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2B&quot;,&nbsp;Integer.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Long&nbsp;column2C&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2C&quot;,&nbsp;Long.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Float&nbsp;column2D&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2D&quot;,&nbsp;Float.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Double&nbsp;column2E&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2E&quot;,&nbsp;Double.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;BigDecimal&nbsp;column2F&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2F&quot;,&nbsp;BigDecimal.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3&nbsp;=&nbsp;result.getString(&quot;COLUMN3&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;date&nbsp;value.&nbsp;<br>&nbsp;&nbsp;&nbsp;Date&nbsp;column4&nbsp;=&nbsp;result.getDate(&quot;COLUMN4&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;array&nbsp;of&nbsp;bytes.&nbsp;<br>&nbsp;&nbsp;&nbsp;byte[]&nbsp;column5&nbsp;=&nbsp;result.getBlob(&quot;COLUMN5&quot;);<br><br>}</code>
 * <br>
 * <br>
 * <span>When you iterate result rows, you can specify the column
 * </span><span>(where to get the data) by name or by column index:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value&nbsp;of&nbsp;a&nbsp;given&nbsp;column&nbsp;name.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3A&nbsp;=&nbsp;result.getString(&quot;COLUMN3&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value&nbsp;of&nbsp;a&nbsp;given&nbsp;column&nbsp;index.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3B&nbsp;=&nbsp;result.getString(3);<br><br>}</code>
 * <br>
 * <br>
 * <span>Another option is to retrieve a whole row as a Java Bean object. To
 * achieve this, you have to provide to </span><span>getBean</span><span> a
 * class that represents a Java Bean. A new instance of this class is created
 * and used to store the values of the result columns. You may also provide a
 * mapping where to relate result columns with bean attributes. Check it out
 * with one example:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Map&nbsp;table&nbsp;result&nbsp;columns&nbsp;with&nbsp;bean&nbsp;attributes.&nbsp;<br>Map&lt;Object,&nbsp;Object&gt;&nbsp;mapping&nbsp;=&nbsp;new&nbsp;HashMap&lt;Object,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'NAME'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'name'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;NAME&quot;,&nbsp;&quot;name&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'DATE_OF_BIRTH'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'dateOfBirth'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;DATE_OF_BIRTH&quot;,&nbsp;&quot;dateOfBirth&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'PASSWORD'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'password'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;PASSWORD&quot;,&nbsp;&quot;password&quot;);<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;specified&nbsp;columns&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;mapping);<br><br>}</code>
 * <br>
 * <br>
 * Warework can also create this mapping automatically when the columns names
 * and bean attributes follow a specific naming convention. Here are the rules
 * for columns and attributes names:<br>
 * <br>
 * <span>Names of database columns are uppercase, for example:
 * </span><span>PASSWORD</span><span>. Names of bean attributes are lowercase,
 * for example: </span><span>password</span><span>.</span><br>
 * <br>
 * <br>
 * <span>Spaces in columns names are specified with the underscore
 * character</span><span>: </span><span>DATE_OF_BIRTH</span><span>. The
 * attributes of the bean use the camel notation, for example:
 * </span><span>dateOfBirth</span><span>. </span> <br>
 * <br>
 * Check out more examples:<br>
 * <br>
 * <span>NAME1</span><span> equals to </span><span>name1</span><br>
 * <br>
 * <br>
 * <span>NAME_A</span><span> equals to </span><span>nameA</span><br>
 * <br>
 * <br>
 * <span>A</span><span> equals to </span><span>a</span><br>
 * <br>
 * <br>
 * <span>AA</span><span> equals to </span><span>aa</span><br>
 * <br>
 * <br>
 * <span>A_B</span><span> equals to </span><span>aB</span><br>
 * <br>
 * <br>
 * <span>A_B_C</span><span> equals to </span><span>aBC</span><br>
 * <br>
 * <br>
 * <br>
 * <br>
 * <span>If </span><span>getBean</span><span> method does not receive the
 * mapping configuration, it extracts by reflection the name of every attribute
 * that exist in the given bean class. Each attribute name is converted into the
 * corresponding column name and this column name is finally used to retrieve
 * the value from the database result. If an attribute of the Java Bean does not
 * exist as a result column, then it is discarded. The following example shows
 * how this naming convention simplifies quite a lot the work:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;columns&nbsp;found&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;null);<br><br>}</code>
 * <br>
 * <br>
 * You can also transform the database result into a list:<br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;resultRows&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Map&nbsp;table&nbsp;result&nbsp;columns&nbsp;with&nbsp;bean&nbsp;attributes.&nbsp;<br>Map&lt;Object,&nbsp;Object&gt;&nbsp;mapping&nbsp;=&nbsp;new&nbsp;HashMap&lt;Object,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'NAME'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'name'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;NAME&quot;,&nbsp;&quot;name&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'DATE_OF_BIRTH'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'dateOfBirth'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;DATE_OF_BIRTH&quot;,&nbsp;&quot;dateOfBirth&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'PASSWORD'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'password'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;PASSWORD&quot;,&nbsp;&quot;password&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;list&nbsp;with&nbsp;User&nbsp;beans&nbsp;that&nbsp;represent&nbsp;each&nbsp;row&nbsp;from&nbsp;the&nbsp;<br>&#47;&#47;&nbsp;database&nbsp;result.&nbsp;<br>List&lt;User&gt;&nbsp;resultList&nbsp;=&nbsp;AbstractResultRows.toList(resultRows,&nbsp;<br>&nbsp;&nbsp;&nbsp;User.class,&nbsp;mapping);</code>
 * <br>
 * <br>
 * <br>
 * It is also possible to use auto-mapping:<br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;resultRows&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;list&nbsp;with&nbsp;User&nbsp;beans&nbsp;that&nbsp;represent&nbsp;each&nbsp;row&nbsp;from&nbsp;the&nbsp;<br>&#47;&#47;&nbsp;database&nbsp;result.&nbsp;<br>List&lt;User&gt;&nbsp;resultList&nbsp;=&nbsp;AbstractResultRows.toList(resultRows,&nbsp;<br>&nbsp;&nbsp;&nbsp;User.class,&nbsp;null);</code>
 * <br>
 * <br>
 * <br>
 * <span>By default, you will always get a </span><span>ResultRows</span><span>
 * object with a JDBC Data Store when you run a query. It is also possible to
 * configure this Data Store to retrieve JDBC
 * </span><span>ResultSet</span><span> objects instead of Warework
 * </span><span>ResultRows</span><span> objects. To achieve this, you have to
 * configure the JDBC Data Store with
 * </span><span>PARAMETER_NATIVE_RESULT_SET</span><span>, like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>Map&lt;String,&nbsp;Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;name&nbsp;of&nbsp;the&nbsp;Provider&nbsp;where&nbsp;to&nbsp;retrieve&nbsp;the&nbsp;Connection&nbsp;object.<br>config.put(JdbcConnector.PARAMETER_NATIVE_RESULT_SET,&nbsp;Boolean.TRUE);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;jdbc-datastore&quot;,&nbsp;JdbcConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <br>
 * <span>Once it is configured like this, you will get a
 * </span><span>ResultSet</span><span> object when you run a query:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;and&nbsp;return&nbsp;a&nbsp;JDBC&nbsp;ResultSet&nbsp;object.&nbsp;<br>ResultSet&nbsp;result&nbsp;=&nbsp;(ResultSet)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * <span>Sometimes you may need to limit the number of rows returned by a
 * database when a query operation is performed.</span><span> Let us say that
 * there are 26 registries or rows in the </span><span>HOME_USER</span><span>
 * table and that we just expect to retrieve the first 10 rows. We can write
 * something like this:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;first&nbsp;10&nbsp;rows.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;1,&nbsp;10);</code>
 * <br>
 * <br>
 * <span>What is going on right now? When you specify the number of rows that
 * you want in the result of a database, Warework automatically calculates the
 * number of pages </span><span>that hold this number of rows. In the previous
 * example we specified 10 rows per result and with this information Warework
 * estimates that the size of each page is 10 rows and that there are three
 * pages: page 1 with 10 rows, page 2 with 10 rows and page 3 with 6 rows. If
 * now we need to retrieve the next ten rows, we have to indicate that we want
 * the second page:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;rows&nbsp;from&nbsp;11&nbsp;to&nbsp;20.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;2,&nbsp;10);</code>
 * <br>
 * <br>
 * <span>If we request page number three, we get the last 6 registries from the
 * database. The important fact to keep in mind here is that the number of rows
 * remains as 10</span><span>:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;rows&nbsp;from&nbsp;21&nbsp;to&nbsp;26.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;3,&nbsp;10);</code>
 * <br>
 * <br>
 * <span>With queries, you can also write </span><span>SELECT</span><span>
 * statements in separate text files (this time, just one statement per file).
 * Suppose that the following code is the content of the
 * </span><span>list-users.sql</span><span>file:</span><br>
 * <br>
 * <br>
 * <code>SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER</code><br>
 * <br>
 * <span>If </span><span>sql-provider</span><span> still is our default
 * Provider, we can read the script like this:</span><br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'list-users.sql'&nbsp;and&nbsp;execute&nbsp;it&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * <span>There is also the possibility to define some variables in the
 * query</span><span>:</span><br>
 * <br>
 * <br>
 * <code>SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&nbsp;A&nbsp;WHERE&nbsp;A.ID&nbsp;=&nbsp;${USER_ID}</code>
 * <br>
 * <br>
 * <span>Now we can assign a value to this variable to complete the query. This
 * is done as follows:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;to&nbsp;filter&nbsp;the&nbsp;query.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(8375));&nbsp;<br><br>&#47;&#47;&nbsp;Read&nbsp;'list-users.sql',&nbsp;replace&nbsp;variables&nbsp;and&nbsp;execute&nbsp;the&nbsp;statement.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQueryByName(&quot;list-users&quot;,&nbsp;values,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * <span>The two last arguments allow you to define the page and
 * max</span><span>imum number of rows to retrieve. The following example shows
 * how to get the second page with a fixed size of 10 rows per page: </span>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;second&nbsp;page&nbsp;with&nbsp;no&nbsp;more&nbsp;than&nbsp;10&nbsp;registries&nbsp;in&nbsp;it.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.&nbsp;<br>&nbsp;&nbsp;&nbsp;executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;2,&nbsp;10);</code>
 * <br>
 * <br>
 * <br>
 * <span>We can also define callbacks in query operations. This time,
 * </span><span>onSuccess</span><span> provides the result of the
 * query:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Handle&nbsp;result&nbsp;with&nbsp;callback&nbsp;object.&nbsp;<br>view.executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Do&nbsp;something.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;result&nbsp;form&nbsp;the&nbsp;database.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;r;&nbsp;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;columns&nbsp;found&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;null);<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * <br>
 * Query operations also allow us to use a different Provider than the default
 * one defined for the View:<br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'list-users.sql'&nbsp;and&nbsp;execute&nbsp;it&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQueryByName(&quot;another-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;list-users&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * As always, when the work is done, you have to disconnect the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Close&nbsp;the&nbsp;connection&nbsp;with&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.disconnect();</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class JdbcViewImpl extends AbstractRdbmsView {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Begins a transaction in the Relational Database Management System.
	 * 
	 * @throws ClientException If there is an error when trying to begin the
	 *                         transaction in the Data Store.<br>
	 *                         <br>
	 */
	protected void performBeginTransaction() throws ClientException {
		try {

			// Get the connection.
			final Connection connection = (Connection) getConnection();

			// Begin the transaction.
			connection.setAutoCommit(false);

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot begin a transaction in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because the database reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Rolls back changes in the Relational Database Management System.
	 * 
	 * @throws ClientException If there is an error when trying to perform rollback
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	protected void performRollback() throws ClientException {
		try {

			// Get the connection.
			Connection connection = (Connection) getConnection();

			// Perform roll back.
			connection.rollback();

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot perform rollback in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because the database reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes an SQL query statement in the Relational Database Management System.
	 * 
	 * @param sql      SQL statement, typically a static SQL <code>SELECT</code>
	 *                 statement, to retrieve data from the relational database.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the
	 *                 query-string loaded from the Provider and the values those
	 *                 that will replace the variables. Every variable must be
	 *                 inside '${' and '}' so the variable CAR must be in this
	 *                 query-string as '${CAR}'. Pass <code>null</code> to this
	 *                 parameter to make no changes in the query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 rows (check out <code>pageRows</code> argument of this
	 *                 method) that you want in the result of a query, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of rows. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the result. Set
	 *                 this argument to <code>-1</code> to retrieve every result in
	 *                 one page.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the result of the database.
	 *                 Set this argument to <code>-1</code> to retrieve every
	 *                 row.<br>
	 *                 <br>
	 * @param invoker  Object required to invoke the callback operation.<br>
	 *                 <br>
	 */
	protected void performQuery(String sql, Map<String, Object> values, int page, int pageRows,
			CallbackInvoker invoker) {

		// Get the JDBC Data Store.
		final JdbcDatastore datastore = (JdbcDatastore) getDatastore();

		// Execute the statement.
		Object result = null;
		try {
			result = datastore.executeQuery(sql, values, page, pageRows);
		} catch (final ClientException e) {

			// Execute failure.
			invoker.failure(e);

			// Stop execution.
			return;

		}

		// Set the result for the callback operation.
		invoker.success(result);

	}

	/**
	 * Executes a set of SQL update statements in the Relational Database Management
	 * System.
	 * 
	 * @param statement SQL statement, which may be an <code>INSERT</code>,
	 *                  <code>UPDATE</code>, or <code>DELETE</code> statement or an
	 *                  SQL statement that returns nothing, such as an SQL DDL
	 *                  statement (for example: <code>CREATE TABLE</code>).<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the
	 *                  update-string loaded from the Provider and the values those
	 *                  that will replace the variables. Every variable must be
	 *                  inside '${' and '}' so the variable CAR must be in this
	 *                  statement-string as '${CAR}'. Pass <code>null</code> to this
	 *                  parameter to make no changes in the statement loaded.<br>
	 *                  <br>
	 * @param invoker   Object required to invoke the callback operation.<br>
	 *                  <br>
	 */
	protected void performUpdate(final String statement, final Map<String, Object> values, final CallbackInvoker invoker) {

		// Get the JDBC Data Store.
		final JdbcDatastore datastore = (JdbcDatastore) getDatastore();

		// Execute the statement.
		try {
			datastore.executeUpdate(statement, values);
		} catch (final ClientException e) {

			// Execute failure.
			invoker.failure(e);

			// Stop execution.
			return;

		}

		// Delegate execution to callback.
		invoker.success(null);

	}

}
