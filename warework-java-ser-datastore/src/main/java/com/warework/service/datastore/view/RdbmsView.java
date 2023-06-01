package com.warework.service.datastore.view;

import java.util.Map;

import com.warework.core.callback.AbstractCallback;
import com.warework.core.service.client.ClientException;

/**
 * <u> Relational Database Management System (RDBMS) View </u><br>
 * <br>
 * This View is an interface that represents a collection of data items
 * organized as a set of formally-described tables. You can take advantage of
 * this View to query and update with SQL statements relational databases like
 * Oracle, MySQL, DB2, etc. Two basic operations are allowed here:<br>
 * <br>
 * <ul>
 * <li>Update operations<br>
 * </li>
 * </ul>
 * <br>
 * Executes a given SQL statement, which may be an INSERT, UPDATE, or DELETE
 * statement or an SQL statement that returns nothing, such as an SQL DDL
 * statement (for example: CREATE TABLE).<br>
 * <br>
 * <ul>
 * <li>Query operations<br>
 * </li>
 * </ul>
 * <br>
 * Executes a given SQL statement, typically a static SQL SELECT statement, to
 * retrieve data from the relational database.<br>
 * <br>
 * Even before you perform any of these operations, what you have to do first is
 * to connect to the database management system:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View&nbsp;interface.&nbsp;<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * Perfect, now a connection with the database is ready to accept SQL commands.
 * In the following examples we are going to save some information in a database
 * but before that, it is recommended to begin a transaction with the
 * database.<br>
 * <br>
 * A transaction comprises a unit of work performed within a database management
 * system, and treated in a coherent and reliable way independent of other
 * transactions. Transactions in a database environment have two main
 * purposes:<br>
 * <br>
 * <ul>
 * <li>To provide reliable units of work that allow correct recovery from
 * failures and keep a database consistent even in cases of system failure, when
 * execution stops (completely or partially) and many operations upon a database
 * remain uncompleted, with unclear status.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>To provide isolation between programs accessing a database concurrently.
 * If this isolation is not provided the programs outcome are possibly
 * erroneous.<br>
 * </li>
 * </ul>
 * <br>
 * Transactions provide an &quot;all-or-nothing&quot; proposition, stating that
 * each work-unit performed in a database must either complete in its entirety
 * or have no effect whatsoever. Further, the system must isolate each
 * transaction from other transactions, results must conform to existing
 * constraints in the database, and transactions that complete successfully must
 * get written to durable storage.<br>
 * <br>
 * To begin a transaction in a database management system you have to perform
 * the following actions:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View&nbsp;interface.&nbsp;<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();</code>
 * <br>
 * <br>
 * Now it is the right time to perform some update operations. First, we are
 * going to add one row in a table of the database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;RDBMS&nbsp;View&nbsp;interface.&nbsp;<br>RdbmsView&nbsp;view&nbsp;=&nbsp;(RdbmsView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;execute.&nbsp;<br>String&nbsp;sql&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(1,&nbsp;'John&nbsp;Wood')&quot;;<br><br>&#47;&#47;&nbsp;Run&nbsp;the&nbsp;SQL&nbsp;update&nbsp;statement.&nbsp;<br>view.executeUpdate(sql,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * Another option is to execute multiple statements at once. You can do it by
 * specifying a separator character which delimits each statement:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;first&nbsp;user.&nbsp;<br>String&nbsp;sql1&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(1,&nbsp;'John&nbsp;Wood')&quot;;<br><br>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;second&nbsp;user.&nbsp;<br>String&nbsp;sql2&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(2,&nbsp;'James&nbsp;Sharpe')&quot;;<br><br>&#47;&#47;&nbsp;SQL&nbsp;statement&nbsp;to&nbsp;create&nbsp;third&nbsp;user.&nbsp;<br>String&nbsp;sql3&nbsp;=&nbsp;&quot;INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(3,&nbsp;'Sofia&nbsp;Green')&quot;;<br><br>&#47;&#47;&nbsp;Execute&nbsp;three&nbsp;SQL&nbsp;update&nbsp;statements&nbsp;at&nbsp;once.&nbsp;<br>view.executeUpdate(sql1&nbsp;+&nbsp;&quot;;&quot;&nbsp;+&nbsp;sql2&nbsp;+&nbsp;&quot;;&quot;&nbsp;+&nbsp;sql3,&nbsp;null,&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * Perform update operations like this when you have to dynamically construct
 * SQL statements in Java. If your statements are not too complex to create,
 * like the ones we saw in the previous example, you should consider storing
 * them on separate files as they are easier to maintain. A very convenient way
 * to keep SQL statements in separate files consist of keeping each statement
 * (or a set of related statements) in an independent text file, for example:
 * create-user.sql, delete-user.sql, update-user.sql, etc. Later on we can read
 * these text files with a Provider (Warework recommends you to use the
 * <a href="http://goo.gl/X4JRrB">FileText Provider</a> for this task) and use
 * this Provider to read the statements for a View. Remember that you can define
 * a default Provider for a View when you associate a View to a Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;and&nbsp;link&nbsp;a&nbsp;Provider&nbsp;to&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleViewImpl.class,&nbsp;&quot;view1&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * The sql-provider Provider now can be used in the View as the default Provider
 * to read text files from a specific directory. Let us say we have the
 * following content for create-user.sql:<br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(1,&nbsp;'John&nbsp;Wood')</code>
 * <br>
 * <br>
 * If sql-provider is the default Provider in a
 * <a href="http://goo.gl/Pqra1C">RDBMS View</a>, we can read the content of
 * this file with the following code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'create-user.sql'&nbsp;and&nbsp;execute&nbsp;it.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * When executeUpdateByName is invoked, these actions are performed:<br>
 * <br>
 * <ul>
 * <li>The RDBMS View requests the create-user object to sql-provider.<br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li>sql-provider reads the content of create-user.sql and returns it (as a
 * String object).<br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li>The RDBMS View executes the statement included at create-user.sql in the
 * Data Store. <br>
 * </ul>
 * <br>
 * The RDBMS View and the FileText Provider are perfect mates. Both, in
 * combination, will simplify a lot the process of executing scripts in your
 * database. Just write simple text files with SQL statements and let Warework
 * execute them for you. It is recommended that you check out the documentation
 * associated to the FileText Provider to fully take advantage of this
 * feature.<br>
 * <br>
 * If we need a generic statement to create new users in the database, we can
 * define the script create-user.sql with some variables, like this:<br>
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
 * When your script contains multiple statements, you also have to indicate the
 * character that delimits each statement. Suppose we have the following
 * create-user.sql script:<br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME});<br>INSERT&nbsp;INTO&nbsp;ACTIVE_USERS&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID});</code>
 * <br>
 * <br>
 * Now we can replace variables in multiple statements with this code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;user&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * We can also use a Provider that is not the default Provider of the View. In
 * this case, we just need to specify the Provider where to retrieve the
 * statement to execute:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Execute&nbsp;a&nbsp;statement&nbsp;from&nbsp;the&nbsp;Provider&nbsp;that&nbsp;we&nbsp;define.&nbsp;<br>view.executeUpdateByName(&quot;another-provider&quot;,&nbsp;&quot;create-user&quot;,&nbsp;values,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;Character(';'));</code>
 * <br>
 * <br>
 * This time, the create-user.sql statement is not retrieved by the default
 * Provider of the View. The framework requests this statement from a Provider
 * named &quot;another-provider&quot; and once it is loaded, then it is executed
 * in the Data Store.<br>
 * <br>
 * The RDBMS View also allows developers to define a callback object that will
 * be invoked when the operation is done or it fails. Check out the following
 * example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;object&nbsp;once&nbsp;operation&nbsp;is&nbsp;executed.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;successful&nbsp;operation&nbsp;here.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;'result'&nbsp;is&nbsp;null&nbsp;in&nbsp;RDBMS&nbsp;update&nbsp;operations.<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Now the RDBMS View redirects the execution to the callback object when
 * executeUpdateByName is invoked. If operation is successful then method
 * onSuccess is executed. Otherwise, if any error is found, then method
 * onFailure is executed. <br>
 * <br>
 * As you can see, creating the callback object is fairly easy. It is mandatory
 * to provide a Scope (you can retrieve it from the Data Store Service for
 * example) and implement two methods. When a callback object is defined for
 * update operations of the RDBMS View (like executeUpdateByName), it is
 * important to bear in mind that onSuccess method argument is always null. <br>
 * <br>
 * Warework also automatically detects batch operations in scripts with multiple
 * statements so you can perform an operation at onSuccess every time a single
 * statement is executed in the Data Store. For example, if we have the
 * following script:<br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME});<br>INSERT&nbsp;INTO&nbsp;ACTIVE_USERS&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID});</code>
 * <br>
 * <br>
 * We can log the percentage of completion with this code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;object&nbsp;once&nbsp;operation&nbsp;is&nbsp;executed.&nbsp;<br>view.executeUpdateByName(&quot;create-user&quot;,&nbsp;values,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;This&nbsp;method&nbsp;is&nbsp;executed&nbsp;twice,&nbsp;one&nbsp;for&nbsp;every&nbsp;statement&nbsp;in&nbsp;the<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;'create-user'&nbsp;script.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;progress:&nbsp;&quot;&nbsp;+&nbsp;getBatch().progress());<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Method getBatch provides the following useful information about the batch
 * operation in execution:<br>
 * <br>
 * <ul>
 * <li>getBatch().count(): counts the amount of callbacks executed in the batch
 * operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>getBatch().duration(): gets how long (in milliseconds) is taking the
 * current batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>getBatch().id(): gets the ID of the batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>getBatch().progress(): gets the percentage of completion of the current
 * batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>getBatch().size(): gets the total of callbacks to perform in the batch
 * operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>getBatch().startTime(): gets the time (in milliseconds ) when the batch
 * operation started.<br>
 * </li>
 * </ul>
 * <br>
 * It is also possible to pass objects / attributes to the callback so you can
 * use them at onSuccess or onFailure. For this purpose, we have to use a
 * Hashtable when the callback is created. Check out this example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Attributes&nbsp;for&nbsp;the&nbsp;callback.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;attributes&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;attributes.&nbsp;<br>attributes.put(&quot;color&quot;,&nbsp;&quot;red&quot;);<br>attributes.put(&quot;password&quot;,&nbsp;new&nbsp;Integer(123));<br><br>&#47;&#47;&nbsp;Redirect&nbsp;to&nbsp;callback&nbsp;with&nbsp;attributes.&nbsp;<br>view.executeUpdateByName(&quot;statement-name&quot;,&nbsp;null,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade(),&nbsp;attributes){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;color&nbsp;=&nbsp;(String)&nbsp;getAttribute(&quot;color&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Retrieve&nbsp;every&nbsp;attribute&nbsp;name&nbsp;with&nbsp;'getAttributeNames()'.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integer&nbsp;password&nbsp;=&nbsp;(Integer)&nbsp;getAttribute(&quot;password&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Every update operation that we performed in the previous examples is related
 * to the transaction that we created earlier in this section. Once the work is
 * done, you should either commit or rollback the transaction. If the operations
 * were executed without problems, then you should perform commit to register
 * the changes in the database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commits&nbsp;changes&nbsp;in&nbsp;the&nbsp;Database&nbsp;Management&nbsp;System.&nbsp;<br>view.commit();</code>
 * <br>
 * <br>
 * In the other hand, if you find a failure, something unexpected happened or
 * you just do not want to register the changes in the database, then you should
 * perform rollback to undo every update operation executed since the
 * transaction was started:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Cancel&nbsp;latest&nbsp;update&nbsp;operations.&nbsp;<br>view.rollback();</code>
 * <br>
 * <br>
 * We have reviewed with this interface how to connect to a database and perform
 * update operations in it with a transaction. Now we are going to focus on
 * query operations to know how to retrieve data from a relational database. The
 * following code is an example to perform this action:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * This code executes the SQL statement into the relational database and returns
 * an object that represents the result provided by the database. By default,
 * this result is in the form of a ResultRows object (check out the specific
 * types returned by the implementation class of this View because each Data
 * Store may provide the chance to get a different object as result) which
 * represents the table of data returned by the database. It allows iterating
 * each row of the table result and picking up the values of its columns. Check
 * it out with the following example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows&nbsp;until&nbsp;the&nbsp;end&nbsp;of&nbsp;the&nbsp;table.&nbsp;First&nbsp;row&nbsp;is&nbsp;1,&nbsp;second&nbsp;2&nbsp;and&nbsp;so&nbsp;<br>&#47;&#47;&nbsp;on.&nbsp;You&nbsp;must&nbsp;perform&nbsp;'result.next()'&nbsp;at&nbsp;least&nbsp;one&nbsp;time&nbsp;to&nbsp;point&nbsp;the&nbsp;cursor&nbsp;<br>&#47;&#47;&nbsp;to&nbsp;the&nbsp;first&nbsp;row.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;*<br>&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;For&nbsp;each&nbsp;row&nbsp;we&nbsp;can&nbsp;retrieve&nbsp;the&nbsp;value&nbsp;of&nbsp;each&nbsp;column.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;*&#47;<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;boolean&nbsp;value&nbsp;of&nbsp;a&nbsp;column.&nbsp;If&nbsp;the&nbsp;value&nbsp;is&nbsp;SQL&nbsp;NULL<br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;(it&nbsp;is&nbsp;null&nbsp;in&nbsp;the&nbsp;database),&nbsp;the&nbsp;value&nbsp;returned&nbsp;is&nbsp;null.&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean&nbsp;column1&nbsp;=&nbsp;result.getBoolean(&quot;COLUMN1&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;numeric&nbsp;value&nbsp;of&nbsp;a&nbsp;column.&nbsp;You&nbsp;must&nbsp;specify&nbsp;the<br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;numeric&nbsp;type&nbsp;to&nbsp;get.&nbsp;<br>&nbsp;&nbsp;&nbsp;Short&nbsp;column2A&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2A&quot;,&nbsp;Short.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Integer&nbsp;column2B&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2B&quot;,&nbsp;Integer.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Long&nbsp;column2C&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2C&quot;,&nbsp;Long.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Float&nbsp;column2D&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2D&quot;,&nbsp;Float.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;Double&nbsp;column2E&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2E&quot;,&nbsp;Double.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;BigDecimal&nbsp;column2F&nbsp;=&nbsp;result.getNumber(&quot;COLUMN2F&quot;,&nbsp;BigDecimal.class);&nbsp;<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3&nbsp;=&nbsp;result.getString(&quot;COLUMN3&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;date&nbsp;value.&nbsp;<br>&nbsp;&nbsp;&nbsp;Date&nbsp;column4&nbsp;=&nbsp;result.getDate(&quot;COLUMN4&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;array&nbsp;of&nbsp;bytes.&nbsp;<br>&nbsp;&nbsp;&nbsp;byte[]&nbsp;column5&nbsp;=&nbsp;result.getBlob(&quot;COLUMN5&quot;);<br><br>}</code>
 * <br>
 * <br>
 * When you iterate result rows, you can specify the column (where to get the
 * data) by name or by column index:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value&nbsp;of&nbsp;a&nbsp;given&nbsp;column&nbsp;name.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3A&nbsp;=&nbsp;result.getString(&quot;COLUMN3&quot;);<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;string&nbsp;value&nbsp;of&nbsp;a&nbsp;given&nbsp;column&nbsp;index.&nbsp;<br>&nbsp;&nbsp;&nbsp;String&nbsp;column3B&nbsp;=&nbsp;result.getString(3);<br><br>}</code>
 * <br>
 * <br>
 * Another option is to retrieve a whole row as a Java Bean object. To achieve
 * this, you have to provide to getBean a class that represents a Java Bean. A
 * new instance of this class is created and used to store the values of the
 * result columns. You may also provide a mapping where to relate result columns
 * with bean attributes. Check it out with one example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Map&nbsp;table&nbsp;result&nbsp;columns&nbsp;with&nbsp;bean&nbsp;attributes.&nbsp;<br>Map&lt;Object,&nbsp;Object&gt;&nbsp;mapping&nbsp;=&nbsp;new&nbsp;HashMap&lt;&nbsp;Object,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'NAME'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'name'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;NAME&quot;,&nbsp;&quot;name&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'DATE_OF_BIRTH'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'dateOfBirth'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;DATE_OF_BIRTH&quot;,&nbsp;&quot;dateOfBirth&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'PASSWORD'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'password'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;PASSWORD&quot;,&nbsp;&quot;password&quot;);<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;specified&nbsp;columns&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;mapping);<br><br>}</code>
 * <br>
 * <br>
 * Warework can also create this mapping automatically when the columns names
 * and bean attributes follow a specific naming convention. Here are the rules
 * for columns and attributes names:<br>
 * <br>
 * <ul>
 * <li>Names of database columns are uppercase, for example: PASSWORD. Names of
 * bean attributes are lowercase, for example: password.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>Spaces in columns names are specified with the underscore character:
 * DATE_OF_BIRTH. The attributes of the bean use the camel notation:
 * dateOfBirth. <br>
 * </li>
 * </ul>
 * <br>
 * Check out more examples:<br>
 * <br>
 * <ul>
 * <li>NAME1 equals to name1<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>NAME_A equals to nameA<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>A equals to a<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>AA equals to aa<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>A_B equals to aB<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>A_B_C equals to aBC<br>
 * </li>
 * </ul>
 * <br>
 * If getBean method does not receive the mapping configuration, it extracts by
 * reflection the name of every attribute that exist in the given bean class.
 * Each attribute name is converted into the corresponding column name and this
 * column name is finally used to retrieve the value from the database result.
 * If an attribute of the Java Bean does not exist as a result column, then it
 * is discarded. The following example shows how this naming convention
 * simplifies quite a lot the work:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;columns&nbsp;found&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;null);<br><br>}</code>
 * <br>
 * <br>
 * You can also transform the database result into a list:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Map&nbsp;table&nbsp;result&nbsp;columns&nbsp;with&nbsp;bean&nbsp;attributes.&nbsp;<br>Map&lt;Object,&nbsp;Object&gt;&nbsp;mapping&nbsp;=&nbsp;new&nbsp;HashMap&lt;Object,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'NAME'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'name'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;NAME&quot;,&nbsp;&quot;name&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'DATE_OF_BIRTH'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'dateOfBirth'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;DATE_OF_BIRTH&quot;,&nbsp;&quot;dateOfBirth&quot;);<br><br>&#47;&#47;&nbsp;Map&nbsp;result&nbsp;'PASSWORD'&nbsp;column&nbsp;with&nbsp;bean&nbsp;'password'&nbsp;attribute.&nbsp;<br>mapping.put(&quot;PASSWORD&quot;,&nbsp;&quot;password&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;list&nbsp;with&nbsp;User&nbsp;beans&nbsp;that&nbsp;represent&nbsp;each&nbsp;row&nbsp;from&nbsp;the&nbsp;database<br>&#47;&#47;&nbsp;result.&nbsp;<br>List&lt;User&gt;&nbsp;resultList&nbsp;=&nbsp;AbstractResultRows.toList(resultRows,&nbsp;User.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;mapping);</code>
 * <br>
 * <br>
 * It is also possible to use auto-mapping:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Execute&nbsp;the&nbsp;statement&nbsp;to&nbsp;retrieve&nbsp;some&nbsp;data.&nbsp;<br>ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,<br>&nbsp;&nbsp;&nbsp;null,&nbsp;&nbsp;-1,&nbsp;-1);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;list&nbsp;with&nbsp;User&nbsp;beans&nbsp;that&nbsp;represent&nbsp;each&nbsp;row&nbsp;from&nbsp;the&nbsp;database<br>&#47;&#47;&nbsp;result.&nbsp;<br>List&lt;User&gt;&nbsp;resultList&nbsp;=&nbsp;AbstractResultRows.toList(resultRows,&nbsp;User.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);</code>
 * <br>
 * <br>
 * Sometimes you may need to limit the number of rows returned by a database
 * when a query operation is performed. Let us say that there are 26 registries
 * or rows in the HOME_USER table and that we just expect to retrieve the first
 * 10 rows. We can write something like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;first&nbsp;10&nbsp;rows.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;1,&nbsp;10);</code>
 * <br>
 * <br>
 * What is going on right now? When you specify the number of rows that you want
 * in the result of a database, Warework automatically calculates the number of
 * pages that hold this number of rows. In the previous example we specified 10
 * rows per result and with this information Warework estimates that the size of
 * each page is 10 rows and that there are three pages: page 1 with 10 rows,
 * page 2 with 10 rows and page 3 with 6 rows. If now we need to retrieve the
 * next ten rows, we have to indicate that we want the second page:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;rows&nbsp;from&nbsp;11&nbsp;to&nbsp;20.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;2,&nbsp;10);</code>
 * <br>
 * <br>
 * If we request page number three, we get the last 6 registries from the
 * database. The important fact to keep in mind here is that the number of rows
 * remains as 10:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;rows&nbsp;from&nbsp;21&nbsp;to&nbsp;26.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQuery(&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;,&nbsp;null,&nbsp;3,&nbsp;10);</code>
 * <br>
 * <br>
 * With queries, you can also write SELECT statements in separate text files
 * (this time, just one statement per file). Suppose that the following code is
 * the content of the list-users.sqlfile:<br>
 * <br>
 * <br>
 * <code>SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER</code><br>
 * <br>
 * If sql-provider still is our default Provider, we can read the script like
 * this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'list-users.sql'&nbsp;and&nbsp;execute&nbsp;it&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * There is also the possibility to define some variables in the query:<br>
 * <br>
 * <br>
 * <code>SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&nbsp;A&nbsp;WHERE&nbsp;A.ID&nbsp;=&nbsp;${USER_ID}</code>
 * <br>
 * <br>
 * Now we can assign a value to this variable to complete the query. This is
 * done as follows:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;to&nbsp;filter&nbsp;the&nbsp;query.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(8375));&nbsp;<br><br>&#47;&#47;&nbsp;Read&nbsp;'list-users.sql',&nbsp;replace&nbsp;variables&nbsp;and&nbsp;execute&nbsp;the&nbsp;final&nbsp;statement.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQueryByName(&quot;list-users&quot;,&nbsp;values,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * The two last arguments allow you to define the page and maximum number of
 * rows to retrieve. The following example shows how to get the second page with
 * a fixed size of 10 rows per page: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;second&nbsp;page&nbsp;with&nbsp;no&nbsp;more&nbsp;than&nbsp;10&nbsp;registries&nbsp;in&nbsp;it.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;2,&nbsp;10);</code>
 * <br>
 * <br>
 * We can also define callbacks in query operations. This time, onSuccess
 * provides the result of the query:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Handle&nbsp;result&nbsp;with&nbsp;callback&nbsp;object.&nbsp;<br>view.executeQueryByName(&quot;list-users&quot;,&nbsp;null,&nbsp;-1,&nbsp;-1,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Do&nbsp;something.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;r)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;the&nbsp;result&nbsp;form&nbsp;the&nbsp;database.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ResultRows&nbsp;result&nbsp;=&nbsp;(ResultRows)&nbsp;r;&nbsp;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Iterate&nbsp;rows.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;while&nbsp;(result.next())&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Copy&nbsp;the&nbsp;values&nbsp;of&nbsp;columns&nbsp;found&nbsp;into&nbsp;the&nbsp;User&nbsp;bean.&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;result.getBean(User.class,&nbsp;null);<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Query operations also allow us to use a different Provider than the default
 * one defined for the View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'list-users.sql'&nbsp;and&nbsp;execute&nbsp;it&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;view.executeQueryByName(&quot;another-provider&quot;,&nbsp;&quot;list-users&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
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
public interface RdbmsView extends DbmsView {

	/**
	 * Executes an SQL query statement in the Relational Database Management System.
	 * 
	 * @param sql      SQL statement, typically a static SQL <code>SELECT</code>
	 *                 statement, to retrieve data from the relational database.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 statement and the values those that will replace the
	 *                 variables. Every variable must be inside '${' and '}' so the
	 *                 variable CAR must be in this query-string as '${CAR}'. Pass
	 *                 <code>null</code> to this parameter to make no changes in the
	 *                 query.<br>
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
	 * @return Object that holds the result of the query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	Object executeQuery(final String sql, final Map<String, Object> values, final int page, final int pageRows)
			throws ClientException;

	/**
	 * Executes an SQL query statement in the Relational Database Management System.
	 * 
	 * @param sql      SQL statement, typically a static SQL <code>SELECT</code>
	 *                 statement, to retrieve data from the relational database.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the query
	 *                 statement and the values those that will replace the
	 *                 variables. Every variable must be inside '${' and '}' so the
	 *                 variable CAR must be in this query-string as '${CAR}'. Pass
	 *                 <code>null</code> to this parameter to make no changes in the
	 *                 query.<br>
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
	 * @param callback Operation response handler.<br>
	 *                 <br>
	 */
	void executeQuery(final String sql, final Map<String, Object> values, final int page, final int pageRows,
			final AbstractCallback callback);

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and executes
	 * it in the Relational Database Management System.
	 * 
	 * @param name     Name of the query statement to load from the default Provider
	 *                 defined for this View.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the
	 *                 query-string loaded from the Provider and the values those
	 *                 that will replace the variables. Every variable must be
	 *                 inside '${' and '}' so the variable CAR must be in this
	 *                 query-string as '${CAR}'. Pass <code>null</code> to this
	 *                 parameter to make no changes in the query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 rows (check out <code>pageRows</code> argument) that you want
	 *                 in the result of a database, Warework automatically
	 *                 calculates the number of pages that hold this number of rows.
	 *                 You have to pass an integer value greater than zero to
	 *                 retrieve a specific page from the result. Set this argument
	 *                 to <code>-1</code> to retrieve every row in one page.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the result of the query. Set
	 *                 this argument to <code>-1</code> to retrieve every row.<br>
	 *                 <br>
	 * @return Object that holds the result of the query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	Object executeQueryByName(final String name, final Map<String, Object> values, final int page, final int pageRows)
			throws ClientException;

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and executes
	 * it in the Relational Database Management System.
	 * 
	 * @param name     Name of the query statement to load from the default Provider
	 *                 defined for this View.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the
	 *                 query-string loaded from the Provider and the values those
	 *                 that will replace the variables. Every variable must be
	 *                 inside '${' and '}' so the variable CAR must be in this
	 *                 query-string as '${CAR}'. Pass <code>null</code> to this
	 *                 parameter to make no changes in the query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 rows (check out <code>pageRows</code> argument) that you want
	 *                 in the result of a database, Warework automatically
	 *                 calculates the number of pages that hold this number of rows.
	 *                 You have to pass an integer value greater than zero to
	 *                 retrieve a specific page from the result. Set this argument
	 *                 to <code>-1</code> to retrieve every row in one page.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the result of the query. Set
	 *                 this argument to <code>-1</code> to retrieve every row.<br>
	 *                 <br>
	 * @param callback Operation response handler.<br>
	 *                 <br>
	 */
	void executeQueryByName(final String name, final Map<String, Object> values, final int page, final int pageRows,
			final AbstractCallback callback);

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from a given Provider and executes it in the
	 * Relational Database Management System.
	 * 
	 * @param providerName Name of the Provider where to read the statement.<br>
	 *                     <br>
	 * @param queryName    Name of the query statement to load from the
	 *                     Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the
	 *                     query-string loaded from the Provider and the values
	 *                     those that will replace the variables. Every variable
	 *                     must be inside '${' and '}' so the variable CAR must be
	 *                     in this query-string as '${CAR}'. Pass <code>null</code>
	 *                     to this parameter to make no changes in the query
	 *                     loaded.<br>
	 *                     <br>
	 * @param page         Page to get from the result. When you specify the number
	 *                     of rows (check out <code>pageRows</code> argument) that
	 *                     you want in the result of a database, Warework
	 *                     automatically calculates the number of pages that hold
	 *                     this number of rows. You have to pass an integer value
	 *                     greater than zero to retrieve a specific page from the
	 *                     result. Set this argument to <code>-1</code> to retrieve
	 *                     every row in one page.<br>
	 *                     <br>
	 * @param pageRows     Number of rows that you want in the result of the query.
	 *                     Set this argument to <code>-1</code> to retrieve every
	 *                     row.<br>
	 *                     <br>
	 * @return Object that holds the result of the query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	Object executeQueryByName(final String providerName, final String queryName, final Map<String, Object> values,
			final int page, final int pageRows) throws ClientException;

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from a given Provider and executes it in the
	 * Relational Database Management System.
	 * 
	 * @param providerName Name of the Provider where to load the statement.<br>
	 *                     <br>
	 * @param queryName    Name of the query statement to load from the
	 *                     Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the
	 *                     query-string loaded from the Provider and the values
	 *                     those that will replace the variables. Every variable
	 *                     must be inside '${' and '}' so the variable CAR must be
	 *                     in this query-string as '${CAR}'. Pass <code>null</code>
	 *                     to this parameter to make no changes in the query
	 *                     loaded.<br>
	 *                     <br>
	 * @param page         Page to get from the result. When you specify the number
	 *                     of rows (check out <code>pageRows</code> argument) that
	 *                     you want in the result of a database, Warework
	 *                     automatically calculates the number of pages that hold
	 *                     this number of rows. You have to pass an integer value
	 *                     greater than zero to retrieve a specific page from the
	 *                     result. Set this argument to <code>-1</code> to retrieve
	 *                     every row in one page.<br>
	 *                     <br>
	 * @param pageRows     Number of rows that you want in the result of the query.
	 *                     Set this argument to <code>-1</code> to retrieve every
	 *                     row.<br>
	 *                     <br>
	 * @param callback     Operation response handler.<br>
	 *                     <br>
	 */
	void executeQueryByName(final String providerName, final String queryName, final Map<String, Object> values,
			final int page, final int pageRows, final AbstractCallback callback);

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a set of SQL update statements in the Relational Database Management
	 * System.
	 * 
	 * @param statements SQL statements, which may be an <code>INSERT</code>,
	 *                   <code>UPDATE</code>, or <code>DELETE</code> statements or
	 *                   SQL statements that returns nothing, such as an SQL DDL
	 *                   script (for example: with <code>CREATE TABLE</code>
	 *                   statements).<br>
	 *                   <br>
	 * @param values     Map where the keys represent variable names in the update
	 *                   statement and the values those that will replace the
	 *                   variables. Every variable must be inside '${' and '}' so
	 *                   the variable CAR must be in this statement-string as
	 *                   '${CAR}'. Pass <code>null</code> to this parameter to make
	 *                   no changes in the statement.<br>
	 *                   <br>
	 * @param delimiter  Character that separates each statement (for example: ';').
	 *                   Use <code>null</code> to indicate that just one statement
	 *                   is passed to the database.<br>
	 *                   <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void executeUpdate(final String statements, final Map<String, Object> values, final Character delimiter)
			throws ClientException;

	/**
	 * Executes a set of SQL update statements in the Relational Database Management
	 * System.
	 * 
	 * @param statements SQL statements, which may be an <code>INSERT</code>,
	 *                   <code>UPDATE</code>, or <code>DELETE</code> statements or
	 *                   SQL statements that returns nothing, such as an SQL DDL
	 *                   script (for example: with <code>CREATE TABLE</code>
	 *                   statements).<br>
	 *                   <br>
	 * @param values     Map where the keys represent variable names in the update
	 *                   statement and the values those that will replace the
	 *                   variables. Every variable must be inside '${' and '}' so
	 *                   the variable CAR must be in this statement-string as
	 *                   '${CAR}'. Pass <code>null</code> to this parameter to make
	 *                   no changes in the statement.<br>
	 *                   <br>
	 * @param delimiter  Character that separates each statement (for example: ';').
	 *                   Use <code>null</code> to indicate that just one statement
	 *                   is passed to the database.<br>
	 *                   <br>
	 * @param callback   Operation response handler.<br>
	 *                   <br>
	 */
	void executeUpdate(final String statements, final Map<String, Object> values, final Character delimiter,
			final AbstractCallback callback);

	/**
	 * Reads a set of SQL update statements in the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and executes
	 * them in the Relational Database Management System.
	 * 
	 * @param name      Name of the statement to load from the default Provider
	 *                  defined for this View.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the
	 *                  update-string loaded from the Provider and the values those
	 *                  that will replace the variables. Every variable must be
	 *                  inside '${' and '}' so the variable CAR must be in this
	 *                  statement-string as '${CAR}'. Pass <code>null</code> to this
	 *                  parameter to make no changes in the statement loaded.<br>
	 *                  <br>
	 * @param delimiter Character that separates each statement (i.e.: ';'). Use
	 *                  <code>null</code> to indicate that just one statement is
	 *                  passed to the database.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void executeUpdateByName(final String name, final Map<String, Object> values, final Character delimiter)
			throws ClientException;

	/**
	 * Reads a set of SQL update statements in the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and executes
	 * them in the Relational Database Management System.
	 * 
	 * @param name      Name of the statement to load from the default Provider
	 *                  defined for this View.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the
	 *                  update-string loaded from the Provider and the values those
	 *                  that will replace the variables. Every variable must be
	 *                  inside '${' and '}' so the variable CAR must be in this
	 *                  statement-string as '${CAR}'. Pass <code>null</code> to this
	 *                  parameter to make no changes in the statement loaded.<br>
	 *                  <br>
	 * @param delimiter Character that separates each statement (i.e.: ';'). Use
	 *                  <code>null</code> to indicate that just one statement is
	 *                  passed to the database.<br>
	 *                  <br>
	 * @param callback  Operation response handler.<br>
	 *                  <br>
	 */
	void executeUpdateByName(final String name, final Map<String, Object> values, final Character delimiter,
			final AbstractCallback callback);

	/**
	 * Reads a set of SQL update statements from a Provider and executes them in the
	 * Relational Database Management System.
	 * 
	 * @param providerName  Name of the Provider where to read the statement.<br>
	 *                      <br>
	 * @param statementName Name of the statement to load from the Provider.<br>
	 *                      <br>
	 * @param values        Map where the keys represent variable names in the
	 *                      update-string loaded from the Provider and the values
	 *                      those that will replace the variables. Every variable
	 *                      must be inside '${' and '}' so the variable CAR must be
	 *                      in this statement-string as '${CAR}'. Pass
	 *                      <code>null</code> to this parameter to make no changes
	 *                      in the statement loaded.<br>
	 *                      <br>
	 * @param delimiter     Character that separates each statement (i.e.: ';'). Use
	 *                      <code>null</code> to indicate that just one statement is
	 *                      passed to the database.<br>
	 *                      <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void executeUpdateByName(final String providerName, final String statementName, final Map<String, Object> values,
			final Character delimiter) throws ClientException;

	/**
	 * Reads a set of SQL update statements from a Provider and executes them in the
	 * Relational Database Management System.
	 * 
	 * @param providerName  Name of the Provider where to read the statement.<br>
	 *                      <br>
	 * @param statementName Name of the statement to load from the Provider.<br>
	 *                      <br>
	 * @param values        Map where the keys represent variable names in the
	 *                      update-string loaded from the Provider and the values
	 *                      those that will replace the variables. Every variable
	 *                      must be inside '${' and '}' so the variable CAR must be
	 *                      in this statement-string as '${CAR}'. Pass
	 *                      <code>null</code> to this parameter to make no changes
	 *                      in the statement loaded.<br>
	 *                      <br>
	 * @param delimiter     Character that separates each statement (i.e.: ';'). Use
	 *                      <code>null</code> to indicate that just one statement is
	 *                      passed to the database.<br>
	 *                      <br>
	 * @param callback      Operation response handler.<br>
	 *                      <br>
	 */
	void executeUpdateByName(final String providerName, final String statementName, final Map<String, Object> values,
			final Character delimiter, final AbstractCallback callback);

}
