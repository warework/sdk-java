package com.warework.service.datastore;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.view.DatastoreView;

/**
 * The Data Store Service is the central unit where to perform operations with
 * data repositories in this Framework. It is a Proxy Service so you can manage
 * multiple Data Stores in the same place like relational databases (MySQL,
 * Oracle, DB2, etc.) or properties files for example. This Service gives the
 * opportunity to manage any kind of Data Store and work with them using a
 * common interface.<br>
 * <br>
 * Operations like connect, disconnect, query, update and commit are supported
 * in this Service. Check out in the following example how simple is the process
 * of creating a new table in a relational database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope.&nbsp;<br>&nbsp;&nbsp;&nbsp;getService(&quot;datastore-service&quot;);<br><br>&#47;&#47;&nbsp;Connect&nbsp;to&nbsp;the&nbsp;relational&nbsp;database.&nbsp;<br>datastoreService.connect(&quot;my-database&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;table&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>datastoreService.update(&quot;my-database&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;CREATE&nbsp;TABLE&nbsp;MESSAGES&nbsp;(MESSAGE&nbsp;VARCHAR(99))&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Disconnect&nbsp;to&nbsp;the&nbsp;database.&nbsp;<br>datastoreService.disconnect(&quot;my-database&quot;);</code>
 * <br>
 * <br>
 * You can also combine the Data Store Service with Providers to load statements
 * from text files for Data Stores. That is, you can keep in separate files some
 * commands that you need to execute in specific Data Stores. It lets you write,
 * for example, SQL scripts in text files and execute them just by providing the
 * name of the file. If you are a business application developer, you will love
 * this feature.<br>
 * <br>
 * Warework also gives the opportunity to change the way a Data Store is
 * used.This Service is not just an abstract layer for Data Stores; it also
 * provides the necessary mechanisms to dynamically update the functionality of
 * a Data Store. This is done with Views and it is where the magic really begins
 * with this Service.<br>
 * <br>
 * <b> Create and retrieve a Data Store Service</b><br>
 * <br>
 * To create the Data Store Service in a Scope, you always need to provide a
 * unique name for the Service and the DatastoreServiceImpl class that exists in
 * the com.warework.service.datastore package.<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service&nbsp;and&nbsp;register&nbsp;it&nbsp;in&nbsp;a&nbsp;Scope.&nbsp;<br>scope.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;null);</code>
 * <br>
 * <br>
 * Once it is created, you can get an instance of it by using the same name
 * (when you retrieve an instance of a Data Store Service, you will get the
 * DatastoreServiceFacade interface):<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope.&nbsp;<br>&nbsp;&nbsp;&nbsp;getService(&quot;datastore-service&quot;);</code>
 * <br>
 * <br>
 * <br>
 * <b> Add and connect to Data Stores</b><br>
 * <br>
 * Now the Data Store Service is running but you need at least one Data Store
 * where to perform operations. To add a Data Store into the Service you have to
 * invoke method createClient() that exists in its Facade. This method requests
 * a name for the new Data Store and a Connector which performs the creation of
 * the Data Store. Let&rsquo;s see how to register a sample Data Store in this
 * Service:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;Data&nbsp;Store&nbsp;in&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>datastoreService.createClient(&quot;sample-datastore&quot;,&nbsp;SampleConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);</code>
 * <br>
 * <br>
 * The SampleConnector class creates the Sample Data Store and registers it in
 * the Data Store Service. After that, we have to tell the Data Store Service
 * that we want to perform operations with the Sample Data Store. We do so by
 * connecting the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.connect(&quot;sample-&nbsp;datastore&quot;);</code>
 * <br>
 * <br>
 * <br>
 * <b> Perform Data Store operations</b><br>
 * <br>
 * Each Data Store exposes its interface or functionality in two different
 * places. First, you can find basic and common operations at <a
 * href="http://goo.gl/8dJwTm">DatastoreServiceFacade</a> interface. These are
 * operations that almost every Data Store can execute like connect, disconnect,
 * query, update and commit. Like every Proxy Service, you have to provide the
 * name of the Data Store where to perform any of these operations.<br>
 * <br>
 * The second place where you can execute operations is in a View of a Data
 * Store. Views are wrappers that upgrade the functionality provided by the Data
 * Store (or another View) and they are organized like stacks. That is, you can
 * add a View on top of a Data Store but you can also add a View on top of
 * another View. This level of organization lets you transform the way which
 * data is processed. Later on you will see that you can get a View of a Data
 * Store with the DatastoreServiceFacade interface.<br>
 * <br>
 * Example: we can have a <a href="http://goo.gl/w9d0Lg">JDBC Data Store</a> to
 * perform simple database operations with SQL commands. If we want to execute
 * more specific operations we can add a <a href="http://goo.gl/enoO3n">RDBMS
 * View</a> (specific for relational databases) on top of the JDBC Data Store. <br>
 * <br>
 * <br>
 * <u> Common operations</u><br>
 * <br>
 * Now we are going to review the basic Data Store operations provided by the
 * DatastoreServiceFacade interface. We can group these operations as follows:<br>
 * <br>
 * <ul>
 * <li>Connect and disconnect Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * The first operation you have to invoke to start working with a Data Store is
 * connect:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.connect(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * If sample-datastore represents a relational database then connect will start
 * up a connection with the database. If it represents a properties file then it
 * will open the file. Each Data Store handles the connect operation in a
 * specific way.<br>
 * <br>
 * After you perform the connection, you typically will execute other Data Store
 * operations like query, update or commit. Once the job is done, you will need
 * to close the Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Disconnect&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.disconnect(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * <ul>
 * <li>Query Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * There are multiple methods named query where you can retrieve information
 * from a Data Store. The first one allows you to directly query data with a
 * statement that is supported by the Data Store. For example, if
 * sample-datastore represents a relational database then we could query the
 * Data Store like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Query&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;&quot;SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&quot;);</code>
 * <br>
 * <br>
 * Check it out now how easy is to make thesample-datastore behave as a
 * different Data Store, like a properties file for example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;value&nbsp;of&nbsp;the&nbsp;'user.name'&nbsp;property&nbsp;in&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>String&nbsp;value&nbsp;=&nbsp;(String)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;&quot;user.name&quot;);</code>
 * <br>
 * <br>
 * Very important facts to consider about this method:<br>
 * <br>
 * As always with Proxy Services, you have to indicate the name of the Client
 * (Data Store in this case) where to execute the query statement.<br>
 * <br>
 * <br>
 * Each Data Store accepts a specific query language.<br>
 * <br>
 * <br>
 * Each Data Store returns a specific type of object.<br>
 * <br>
 * Please review the documentation associated to each Data Store to know which
 * query language it accepts and what sort of object is returned.<br>
 * <br>
 * When you query a Data Store you do not perform the query directly on the Data
 * Store, instead you will execute the query in a View that wraps the Data
 * Store. By default, this query method performs the operation in the View that
 * is on top of the stack of Views of the Data Store (the &quot;Current
 * View&quot;). But wait; did we add any View in the Data Store? Well, every
 * time you create a new Data Store, the Data Store Service creates a default
 * View for the Data Store, wraps the Data Store with the default View and then
 * saves the default View (not the Data Store, because the View contains the
 * Data Store and not the other way) for later use. That is why you will always
 * perform operations with Views and not directly with Data Stores. <br>
 * <br>
 * To execute the query in a specific View you have to provide the name of the
 * View. Check it out with an example: suppose we have a View that is
 * implemented with class SampleView1 and it is responsible for transforming to
 * uppercase the queries of a properties file Data Store. If we add this View in
 * the Properties Data Store and we query it by specifying the name of the View,
 * then we will get results/strings in uppercase:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Value&nbsp;of&nbsp;property&nbsp;'user.name.john'&nbsp;is&nbsp;'John&nbsp;Wood'.&nbsp;<br>&#47;&#47;&nbsp;'query'&nbsp;returns&nbsp;'JOHN&nbsp;WOOD'.&nbsp;<br>String&nbsp;name&nbsp;=&nbsp;(String)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;&quot;sample-view1&quot;,&nbsp;&quot;user.name.john&quot;);</code>
 * <br>
 * <br>
 * If we now add SampleView2 to transform queries to lowercase then the Data
 * Store will have two Views (SampleView2 is on top of SampleView1 and
 * SampleView1 is on top of the properties Data Store): <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Value&nbsp;of&nbsp;property&nbsp;'user.name.john'&nbsp;is&nbsp;'John&nbsp;Wood'.&nbsp;<br>&#47;&#47;&nbsp;'query'&nbsp;returns&nbsp;'john&nbsp;wood'.&nbsp;<br>String&nbsp;name&nbsp;=&nbsp;(String)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;&quot;sample-view2&quot;,&nbsp;&quot;user.name.john&quot;);</code>
 * <br>
 * <br>
 * With this code we get results in lowercase and, as SampleView2 is now the
 * Current View, we can get the same results with the following code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Value&nbsp;of&nbsp;property&nbsp;'user.name.john'&nbsp;is&nbsp;'John&nbsp;Wood'.&nbsp;<br>&#47;&#47;&nbsp;'query'&nbsp;returns&nbsp;'john&nbsp;wood'.&nbsp;<br>&#47;&#47;&nbsp;We&nbsp;use&nbsp;the&nbsp;View&nbsp;on&nbsp;top&nbsp;of&nbsp;the&nbsp;stack.&nbsp;<br>String&nbsp;name&nbsp;=&nbsp;(String)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;&quot;user.name.john&quot;);</code>
 * <br>
 * <br>
 * The powerful idea about this is that you can choose which specific View to
 * use when you run a query. Let us now query the Data Store to retrieve again
 * results in uppercase:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Value&nbsp;of&nbsp;property&nbsp;'user.name.john'&nbsp;is&nbsp;'John&nbsp;Wood'.&nbsp;<br>&#47;&#47;&nbsp;'query'&nbsp;returns&nbsp;'JOHN&nbsp;WOOD'.&nbsp;<br>String&nbsp;name&nbsp;=&nbsp;(String)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;&quot;sample-view1&quot;,&nbsp;&quot;user.name.john&quot;);</code>
 * <br>
 * <br>
 * This time we do not use the Current View (SampleView2), instead we indicate
 * the Data Store Service to retrieve the View that we want and perform the
 * query on it.<br>
 * <br>
 * Another way to search for data in a Data Store is by loading and executing
 * statements that exist in a Provider. This functionality allows you to read
 * queries from files and run them in the Data Store, for example: you can <a
 * href="http://goo.gl/HMZJrt">setup a Provider to read SQL statements</a> from
 * text files and execute each one in a relational database, just by specifying
 * the name of the Provider and the name of the text file to read. For the next
 * sample code we are going to suppose that:<br>
 * <br>
 * sample-datastore represents now a relational database.<br>
 * <br>
 * <br>
 * There is a Provider named sql-provider that reads text files from a specific
 * directory. When you request an object from this Provider, the name given is
 * used to read the file. For example: if we request an object named list-users,
 * this Provider looks for a file named list-users.sql in the directory, reads
 * the file and return its content as a String object.<br>
 * <br>
 * <br>
 * <br>
 * <br>
 * And this is the example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Query&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;&quot;sql-provider&quot;,&nbsp;&quot;list-users&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * With this utility you can save prepared statements in a directory for any
 * Data Store. You can also define some variables for these queries and replace
 * them later on with specific values. Suppose that list-users.sql contains the
 * following code:<br>
 * <br>
 * <br>
 * <code>SELECT&nbsp;*&nbsp;FROM&nbsp;HOME_USER&nbsp;A&nbsp;WHERE&nbsp;A.ID&nbsp;=&nbsp;${USER_ID}</code>
 * <br>
 * <br>
 * In this query we have defined a variable named USER_ID. We can assign a value
 * to this variable to complete the query and then run it in the Data Store.
 * This is done as follows:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;to&nbsp;filter&nbsp;the&nbsp;query.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(8375));&nbsp;<br><br>&#47;&#47;&nbsp;Query&nbsp;the&nbsp;Sample&nbsp;Data&nbsp;Store.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;datastoreService.query(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;&quot;list-users&quot;,&nbsp;values);</code>
 * <br>
 * <br>
 * You can define as many variables as you need but only when the Data Store
 * supports it. Not every Data Store can replace variables with values so please
 * review the documentation of each Data Store to know if this functionality is
 * enabled. <br>
 * <br>
 * query methods also allows you to define in which View execute the query that
 * exists in a Provider. Suppose that we have a View that is implemented with
 * class SampleView3 and it is responsible for transforming to uppercase the
 * queries of a relational database.<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;results&nbsp;in&nbsp;uppercase.&nbsp;<br>Object&nbsp;result&nbsp;=&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;query(&quot;sample-datastore&quot;,&nbsp;&quot;sample-view3&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;&quot;list-users&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * It works as we saw before. We specify where to execute the query, from the
 * stack of Views of the Data Store, to get a result in a specific way. <br>
 * <br>
 * <ul>
 * <li>Update Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * Before proceed with this section, read the previous point because update
 * operations are used in the same way. This time, instead querying for data in
 * a Data Store, you will create or modify information in a Data Store. For
 * example, if sample-datastore represents a relational database then we could
 * create a new table in the Data Store like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;table&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>datastoreService.update(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;CREATE&nbsp;TABLE&nbsp;MESSAGES&nbsp;(MESSAGE&nbsp;VARCHAR(99))&quot;);</code>
 * <br>
 * <br>
 * If sample-datastore represents now a properties file then we could set a new
 * property in the Data Store like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Set&nbsp;a&nbsp;new&nbsp;property&nbsp;in&nbsp;the&nbsp;properties&nbsp;file.&nbsp;<br>datastoreService.update(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;user.name=John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * Each Data Store performs the update operation in a specific manner. If you
 * require executing update in a View, you can specify it by providing the name
 * of the View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Save&nbsp;properties&nbsp;in&nbsp;uppercase.&nbsp;<br>datastoreService.update(&quot;sample-datastore&quot;,&nbsp;&quot;sample-view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;user.name=John&nbsp;Wood&quot;);</code>
 * <br>
 * <br>
 * <br>
 * You can also read update statements from Providers:<br>
 * <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;'create-contact.sql'&nbsp;and&nbsp;execute&nbsp;it.&nbsp;<br>datastoreService.update(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;&quot;create-contact&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * Let us say that we want to create a new user in the database, we can define a
 * script named create-user.sql like this:<br>
 * <br>
 * <br>
 * <code>INSERT&nbsp;INTO&nbsp;HOME_USER&nbsp;(ID,&nbsp;NAME)&nbsp;VALUES&nbsp;(${USER_ID},&nbsp;${USER_NAME})</code>
 * <br>
 * <br>
 * Then replace these variables with the values that you need:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;update&nbsp;statement.&nbsp;<br>values.put(&quot;USER_ID&quot;,&nbsp;new&nbsp;Integer(3));<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Ian&nbsp;Sharpe&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;new&nbsp;user&nbsp;in&nbsp;the&nbsp;database.&nbsp;<br>datastoreService.update(&quot;sample-datastore&quot;,&nbsp;null,&nbsp;&quot;sql-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;create-user&quot;,&nbsp;values);</code>
 * <br>
 * <br>
 * <ul>
 * <li>Commit Data Stores<br>
 * </li>
 * </ul>
 * <br>
 * Some Data Stores require you to confirm that latest update operations have to
 * be executed. That is, you may run multiple update operations in a Data Store
 * and it will not make these changes available until you invoke the commit
 * method. It is like working with a text file in a note pad application. You
 * write some text there, but it is not written into disk until you press the
 * Save button. Databases, for example, typically require you to perform commit
 * in order to persist the data. Please review the documentation of each Data
 * Store to understand how it works with this operation.<br>
 * <br>
 * You can commit changes just by providing the name of the Data Store to
 * execute the operation:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commit&nbsp;changes&nbsp;in&nbsp;a&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.commit(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * If you want to commit changes on every Data Store at once, then
 * invokecommitAll:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Commit&nbsp;changes&nbsp;in&nbsp;every&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.commitAll();</code>
 * <br>
 * <br>
 * <br>
 * <br>
 * <u> Working with Views</u><br>
 * <br>
 * Data Stores implement the basic functionalities that allow operating with
 * specific data repositories. They expose this functionality with the
 * DatastoreServiceFacade interface, with the methods that we reviewed in the
 * previous section: connect, disconnect, query, update and commit. A key fact
 * about this is that you cannot get an instance of a Data Store.<br>
 * <br>
 * Another way to work with Data Stores is using Views. They let you to change
 * the interface of a Data Store and provide a different set of methods to
 * better adjust to your needs. Views are very handy when:<br>
 * <br>
 * <ul>
 * <li>You have to execute specialized operations in a specific type of Data
 * Store. <br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>You need to handle a Data Store in a separate class, not with the Data
 * Store Service. That is, you can get an instance of a View and work with it
 * outside the Service. <br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>You require transforming data obtained from a Data Store or data to be
 * saved in the Data Store.<br>
 * </li>
 * </ul>
 * <br>
 * <ul>
 * <li>You want the same Data Store to provide multiple interfaces to work with
 * it. <br>
 * </li>
 * </ul>
 * <br>
 * In order to work with Views, you always have to associate at least one View
 * to a Data Store. The following example shows how to create a fictitious Data
 * Store and how to associate a View to it: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;Data&nbsp;Store&nbsp;in&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>datastoreService.createClient(&quot;sample-datastore&quot;,&nbsp;SampleConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;on&nbsp;top&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;sample-view1&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * When the second line of code is executed, the functionality of
 * sample-datastore is updated to behave as SampleView1 indicates. This View is
 * added on top of the Data Store in a way that it wraps the operations provided
 * by the Data Store and now, every time you interact with the Data Store, you
 * do it through the View. Check it out with the following image:<br>
 * <br>
 * <img alt="" src=
 * "http://tutorial.warework.com/java/warework-java-ser-datastore/2.0.1/tutorial_html_51aa7599.png"
 * WIDTH=472 HEIGHT=163 ><br>
 * <br>
 * This example shows that SampleView1 completely isolates the Data Store.
 * Sometimes you may need to add more Views on top of existing ones to construct
 * a stack of Views. This is great because it allows you to manage multiple
 * interfaces for the same Data Store and choose the one you need for a specific
 * operation. Check out this code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;Data&nbsp;Store&nbsp;in&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>datastoreService.createClient(&quot;sample-datastore&quot;,&nbsp;SampleConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;first&nbsp;View.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1.class,&nbsp;&quot;view1&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;second&nbsp;View.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView2.class,&nbsp;&quot;view2&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;third&nbsp;View.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView3.class,&nbsp;&quot;view3&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);</code>
 * <br>
 * <br>
 * What is going on right now? Well, when you request an operation to the Data
 * Store, now it is processed first by SampleView3 because it is the Current
 * View (the latest added in the stack). It will produce a response based on the
 * data provided by another View of the stack or directly from the Data Store.<br>
 * <br>
 * <img alt="" src=
 * "http://tutorial.warework.com/java/warework-java-ser-datastore/2.0.1/tutorial_html_4ad7658d.png"
 * WIDTH=439 HEIGHT=313 ><br>
 * <br>
 * Another interesting feature is that Warework gives the opportunity to link a
 * View with a default Provider. This is very useful when you need to read
 * prepared statements from a specific source, like SQL commands from text
 * files. Suppose we have a Provider named sql-provider which loads SQL scripts,
 * we can link this Provider to the View when we create the View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;and&nbsp;link&nbsp;a&nbsp;Provider&nbsp;to&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1.class,&nbsp;&quot;view1&quot;<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * Now, when we request an operation to SampleView1, it can use the sql-provider
 * to load and execute scripts. Each View can use its own Provider or, if it is
 * not defined, the Provider that exists in another View of the same stack. By
 * default, when a View does not have a Provider, the View uses the Provider
 * that exists in the next View of the stack. If there are no Providers
 * associated to any Views of the stack, then the View will not be able to
 * retrieve objects from a default Provider.<br>
 * <br>
 * Sometimes you may need to configure a View with a set of parameters. Read the
 * documentation of the implementation class of each View to check out if it
 * defines initialization parameters. The following example shows you how to
 * setup the configuration for a View:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;View.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;View&nbsp;with&nbsp;initialization&nbsp;parameters.&nbsp;<br>parameters.put(&quot;sample-param-1&quot;,&nbsp;&quot;sample-value-1&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;and&nbsp;configure&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1.class,&nbsp;&quot;view1&quot;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;parameters);</code>
 * <br>
 * <br>
 * Once you have at least one View associated to a Data Store, you can get an
 * instance of a View with the following line of code (for this example, suppose
 * we added three Views as we did before):<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;Current&nbsp;View&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>SampleView3&nbsp;view&nbsp;=&nbsp;(SampleView3)&nbsp;datastoreService.getView(&quot;sample-datastore&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Perform&nbsp;operations&nbsp;with&nbsp;the&nbsp;View.&nbsp;<br>view.operation1();<br>view.operation2();<br>view.operation2();<br>...</code>
 * <br>
 * <br>
 * This method returns the Current View. If you need to retrieve another View
 * from the stack of Views of the Data Store, then you have to indicate the name
 * or the implementation class of the View that you want to get:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;a&nbsp;View&nbsp;from&nbsp;the&nbsp;stack&nbsp;of&nbsp;Views&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>SampleView2&nbsp;view&nbsp;=&nbsp;(SampleView2)&nbsp;datastoreService.getView(&quot;sample-datastore&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;view2&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Get&nbsp;a&nbsp;View&nbsp;from&nbsp;the&nbsp;stack&nbsp;of&nbsp;Views&nbsp;of&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>SampleView2&nbsp;view&nbsp;=&nbsp;(SampleView2)&nbsp;datastoreService.getView(&quot;sample-datastore&quot;,<br>&nbsp;&nbsp;&nbsp;SampleView2.class);</code>
 * <br>
 * <br>
 * Wonderful, now we can decide which interface we want to use when working with
 * a specific Data Store.<br>
 * <br>
 * What happens if you request a View when there are no Views associated to the
 * Data Store? This time you will get nothing:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;We&nbsp;get&nbsp;'null'&nbsp;because&nbsp;'sample-datastore'&nbsp;does&nbsp;not&nbsp;have&nbsp;Views.<br>Object&nbsp;view&nbsp;=&nbsp;datastoreService.getView(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * If you plan to remove Views from a Data Store, then you should consider which
 * actions can be performed:<br>
 * <br>
 * <ul>
 * <li>Remove all Views at once<br>
 * </li>
 * </ul>
 * <br>
 * This is the easiest way to empty the stack of Views of a Data Store. Just
 * invoke removeAllViews for a specific Data Store like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;every&nbsp;View&nbsp;associated&nbsp;to&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>datastoreService.removeAllViews(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * <ul>
 * <li>Remove Current View<br>
 * </li>
 * </ul>
 * <br>
 * To remove the View that exists on top of the stack of Views, just invoke
 * removeView for a specific Data Store like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Remove&nbsp;just&nbsp;one&nbsp;View,&nbsp;the&nbsp;Current&nbsp;View.&nbsp;<br>datastoreService.removeView(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * Finally, there is also another method that can be helpful in certain
 * occasions. It is the isDefaultView method and it decides if a Data Store has
 * Views associated to it.<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Returns&nbsp;TRUE&nbsp;if&nbsp;there&nbsp;are&nbsp;Views&nbsp;in&nbsp;the&nbsp;stack&nbsp;and&nbsp;FALSE&nbsp;if&nbsp;stack&nbsp;is&nbsp;empty.<br>boolean&nbsp;empty&nbsp;=&nbsp;datastoreService.isDefaultView(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * <br>
 * <b> Data Store Service configuration</b><br>
 * <br>
 * A Data Store Service is a special kind of Proxy Service which allows to be
 * configured in two different ways. In the first place, as they are Proxy
 * Services, you can configure them with the typical configuration Java objects
 * and XML files that Proxy Services support. The second option consists of
 * defining a specific Data Store Service configuration where you can set up
 * Data Stores and Views.<br>
 * <br>
 * <br>
 * <u> Configuration with Proxy Service Java objects</u><br>
 * <br>
 * The following example quickly shows how to create a Data Store Service with
 * two Data Stores to be ready on start up:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>ProxyService&nbsp;config&nbsp;=&nbsp;new&nbsp;ProxyService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;two&nbsp;Data&nbsp;Stores&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>config.setClient(&quot;sample-datastore1&quot;,&nbsp;Sample1Connector.class);&nbsp;<br>config.setClient(&quot;sample-datastore2&quot;,&nbsp;Sample2Connector.class);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Setup&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>parameters.put(ServiceConstants.PARAMETER_CONFIG_TARGET,&nbsp;config);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope<br>&nbsp;&nbsp;&nbsp;.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * You can also configure Data Stores with initialization parameters like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;configuration&nbsp;parameters&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;dsParams&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>dsParams.put(&quot;sample-param-1&quot;,&nbsp;&quot;sample-value-1&quot;);<br>dsParams.put(&quot;sample-param-2&quot;,&nbsp;&quot;sample-value-2&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>ProxyService&nbsp;config&nbsp;=&nbsp;new&nbsp;ProxyService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;two&nbsp;Data&nbsp;Stores&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>config.setClient(&quot;sample-datastore1&quot;,&nbsp;Sample1Connector.class,&nbsp;dsParams);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;the&nbsp;configuration&nbsp;parameters.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Setup&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>parameters.put(ServiceConstants.PARAMETER_CONFIG_TARGET,&nbsp;config);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope<br>&nbsp;&nbsp;&nbsp;.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * <br>
 * <u> Configuration with Data Store Service Java objects</u><br>
 * <br>
 * Data Store Services can be configured in a specific way to automatically
 * initialize the Data Stores that the Service may need. This is done with the
 * DatastoreService class that exists in com.warework.service.datastore.model
 * package. The following example shows how to configure two Data Stores when a
 * Data Store Service is created:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;two&nbsp;Data&nbsp;Stores&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;null,&nbsp;null);&nbsp;<br>config.setDatastore(&quot;sample2-datastore&quot;,&nbsp;Sample2Connector.class,&nbsp;null,&nbsp;null);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;the&nbsp;configuration&nbsp;parameters.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Setup&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>parameters.put(ServiceConstants.PARAMETER_CONFIG_TARGET,&nbsp;config);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope<br>&nbsp;&nbsp;&nbsp;.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * Do you need to pass some parameters to the Data Store? You can do it with a
 * Hashtable when the Data Store is created, like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;configuration&nbsp;parameters&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;dsParams&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>dsParams.put(&quot;sample-param-1&quot;,&nbsp;&quot;sample-value-1&quot;);<br>dsParams.put(&quot;sample-param-2&quot;,&nbsp;&quot;sample-value-2&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;with&nbsp;the&nbsp;configuration.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;dsParams,&nbsp;null);<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;the&nbsp;configuration&nbsp;parameters&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Setup&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>parameters.put(ServiceConstants.PARAMETER_CONFIG_TARGET,&nbsp;config);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope<br>&nbsp;&nbsp;&nbsp;.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * You can also include initialization parameters once the Data Store is
 * created:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Define&nbsp;one&nbsp;Data&nbsp;Store&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;null);<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>config.setClientParameter(&quot;sample1-datastore&quot;,&nbsp;&quot;sample-param-1&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;sample-value-1&quot;);&nbsp;<br>config.setClientParameter(&quot;sample1-datastore&quot;,&nbsp;&quot;sample-param-2&quot;,<br>&nbsp;&nbsp;&nbsp;&quot;sample-value-2&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;map&nbsp;where&nbsp;to&nbsp;store&nbsp;the&nbsp;configuration&nbsp;parameters&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Setup&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Service.&nbsp;<br>parameters.put(ServiceConstants.PARAMETER_CONFIG_TARGET,&nbsp;config);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreServiceFacade&nbsp;datastoreService&nbsp;=&nbsp;(DatastoreServiceFacade)&nbsp;scope<br>&nbsp;&nbsp;&nbsp;.createService(&quot;datastore-service&quot;,&nbsp;DatastoreServiceImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * To specify a set of Views for a Data Store, you have to invoke setView method
 * as many times as you need:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;one&nbsp;Data&nbsp;Store&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;null,&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;up&nbsp;a&nbsp;View&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>config.setView(&quot;sample1-datastore&quot;,&nbsp;Sample1View.class,&nbsp;&quot;view1&quot;,&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * Remember that you can associate a default Provider for a View. It can be done
 * as follows:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;one&nbsp;Data&nbsp;Store&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;null,&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Configure&nbsp;a&nbsp;View&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>config.setView(&quot;sample1-datastore&quot;,&nbsp;Sample1View.class,&nbsp;&quot;view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;sql-provider&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * When a View requires configuration, you can specify a set of initialization
 * parameters like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>DatastoreService&nbsp;config&nbsp;=&nbsp;new&nbsp;DatastoreService();<br><br>&#47;&#47;&nbsp;Configure&nbsp;one&nbsp;Data&nbsp;Store&nbsp;for&nbsp;the&nbsp;Service.&nbsp;<br>config.setDatastore(&quot;sample1-datastore&quot;,&nbsp;Sample1Connector.class,&nbsp;null,&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;configuration&nbsp;for&nbsp;the&nbsp;View.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;View&nbsp;with&nbsp;initialization&nbsp;parameters.&nbsp;<br>parameters.put(&quot;sample-param-1&quot;,&nbsp;&quot;sample-value-1&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Configure&nbsp;a&nbsp;View&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>config.setView(&quot;sample1-datastore&quot;,&nbsp;Sample1View.class,&nbsp;&quot;view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;parameters);</code>
 * <br>
 * <br>
 * <br>
 * <u> Configuration with a generic Proxy Service XML file</u><br>
 * <br>
 * Use the following templates to load a Data Store Service with Scope and Proxy
 * Service configuration files. First, the Scope XML file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;scope-<br>&nbsp;&nbsp;&nbsp;1.0.0.xsd&quot;&gt;<br>&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;services&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;service&nbsp;name=&quot;datastore-service&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.service.datastore.DatastoreServiceImpl&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-class&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;com.warework.loader.ProxyServiceSAXLoader&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-target&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;&#47;META-INF&#47;system&#47;datastore-service.xml&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;service&gt;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;services&gt;<br><br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * And now, the datastore-service.xml:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;sample-datastore&quot;&nbsp;connector=&quot;&hellip;&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;sample-param-1&quot;&nbsp;value=&quot;sample-value-1&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;sample-param-2&quot;&nbsp;value=&quot;sample-value-2&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <br>
 * <b> Views</b><br>
 * <br>
 * In general, when you work with Views, you typically deal with the interface
 * of a View instead of the implementation class. So, we can say that Views can
 * be split in two. By one side we have the interface of the View and on the
 * other side we have the class that implements this interface. <br>
 * <br>
 * There is no need to perform any extra operation to work with the interface of
 * a View. You just have to provide the same implementation class of the View
 * when you create it, but this time, you have to cast the result of getView
 * method to the interface of the View. Check this out with the following
 * example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;Data&nbsp;Store&nbsp;in&nbsp;the&nbsp;Data&nbsp;Store&nbsp;Service.&nbsp;<br>datastoreService.createClient(&quot;sample-datastore&quot;,&nbsp;SampleConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Always&nbsp;use&nbsp;the&nbsp;implementation&nbsp;class&nbsp;of&nbsp;the&nbsp;View&nbsp;to&nbsp;create&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1Impl.class,&nbsp;&quot;view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;View&nbsp;with&nbsp;the&nbsp;interface.<br>SampleView1&nbsp;view&nbsp;=&nbsp;(SampleView1)&nbsp;datastoreService.getView(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * This is a much better idea. Now we can use the same interface of a View for
 * different type of Data Stores. Of course, creating the View directly with the
 * interface is completely forbidden:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Never&nbsp;use&nbsp;the&nbsp;interface&nbsp;to&nbsp;create&nbsp;a&nbsp;View!!!&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1.class,&nbsp;&quot;view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;null);</code>
 * <br>
 * <br>
 * Anyway, if you have to work with the implementation class because you need
 * all the functionality that it provides, you can do it without problems:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Use&nbsp;the&nbsp;implementation&nbsp;class&nbsp;of&nbsp;the&nbsp;View&nbsp;to&nbsp;create&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleView1Impl.class,&nbsp;&quot;view1&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;null,&nbsp;null);&nbsp;<br><br>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;View&nbsp;with&nbsp;the&nbsp;interface.<br>SampleView1Impl&nbsp;view&nbsp;=&nbsp;(SampleView1Impl)&nbsp;datastoreService.&nbsp;<br>&nbsp;&nbsp;&nbsp;getView(&quot;sample-datastore&quot;);</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface DatastoreServiceFacade extends ProxyServiceFacade {

	/**
	 * Executes a statement that retrieves information from the Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewName
	 *            View of the Data Store where to execute the query. Use
	 *            <code>null</code> to execute the query in the Current View of
	 *            the Data Store.<br>
	 * <br>
	 * @param statement
	 *            Specific query language accepted by the Data Store (SQL, for
	 *            example).<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	Object query(String datastoreName, String viewName, Object statement)
			throws ServiceException;

	/**
	 * Executes a statement that retrieves information from the Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewName
	 *            View of the Data Store where to execute the query. Use
	 *            <code>null</code> to execute the query in the Current View of
	 *            the Data Store.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to retrieve a query object.<br>
	 * <br>
	 * @param statementName
	 *            Name of the query to retrieve from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	Object query(String datastoreName, String viewName, String providerName,
			String statementName, Map<String, Object> values)
			throws ServiceException;

	/**
	 * Executes an statement that inserts, updates or deletes data.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewName
	 *            View of the Data Store where to execute the statement. Use
	 *            <code>null</code> to execute the statement in the Current View
	 *            of the Data Store.<br>
	 * <br>
	 * @param statement
	 *            Indicates the specific operation to perform and the data to
	 *            update in the Data Store.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	void update(String datastoreName, String viewName, Object statement)
			throws ServiceException;

	/**
	 * Executes an statement that inserts, updates or deletes data.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewName
	 *            View of the Data Store where to execute the statement. Use
	 *            <code>null</code> to execute the statement in the Current View
	 *            of the Data Store.<br>
	 * <br>
	 * @param providerName
	 *            Name of the Provider where to retrieve a statement.<br>
	 * <br>
	 * @param statementName
	 *            Name of the statement to retrieve from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	void update(String datastoreName, String viewName, String providerName,
			String statementName, Map<String, Object> values)
			throws ServiceException;

	/**
	 * Makes in a Data Store all changes made since the previous commit
	 * permanent.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	void commit(String datastoreName) throws ServiceException;

	/**
	 * Makes in every Data Store all changes made since the previous commit
	 * permanent.
	 * 
	 * @throws ServiceException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	void commitAll() throws ServiceException;

	/**
	 * Adds a view on top of the stack of Views of a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewType
	 *            Implementation of the view. Must extends the
	 *            <code>com.warework.service.datastore.AbstractDatastoreView</code>
	 *            class.<br>
	 * <br>
	 * @param viewName
	 *            Name for the View to include in the Data Store.<br>
	 * <br>
	 * @param providerName
	 *            Name of the default Provider where to retrieve statements for
	 *            the View. This parameter can be <code>null</code>.<br>
	 * <br>
	 * @param parameters
	 *            Initialization parameters (as string-object pairs) for the
	 *            View.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to add the View in the Data
	 *             Store.<br>
	 * <br>
	 */
	void addView(String datastoreName, Class<? extends DatastoreView> viewType,
			String viewName, String providerName, Map<String, Object> parameters)
			throws ServiceException;

	/**
	 * Removes the Current View, that is, the View from the top of the stack of
	 * Views of a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 */
	void removeView(String datastoreName);

	/**
	 * Removes every view associated to a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 */
	void removeAllViews(String datastoreName);

	/**
	 * Validates if the Data Store only has the default View associated to it.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @return <code>true</code> if there are no Views associated with the Data
	 *         Store and <code>false</code> if not or no Data Store is bound
	 *         with the specified name.<br>
	 * <br>
	 */
	boolean isDefaultView(String datastoreName);

	/**
	 * Gets the current View of Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @return View of the Data Store.<br>
	 * <br>
	 */
	AbstractDatastoreView getView(String datastoreName);

	/**
	 * Searchs in the stack of Views the first View that matches a given type.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewType
	 *            Type of the View that determines up to where to search. Use
	 *            <code>null</code> to get the Current View of a Data Store.<br>
	 * <br>
	 * @return View of the Data Store.<br>
	 * <br>
	 */
	AbstractDatastoreView getView(String datastoreName,
			Class<? extends DatastoreView> viewType);

	/**
	 * Searchs in the stack of Views the View that matches a given name.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @param viewName
	 *            Name of the View.<br>
	 * <br>
	 * @return View of the Data Store.<br>
	 * <br>
	 */
	AbstractDatastoreView getView(String datastoreName, String viewName);

	/**
	 * Gets the names of every View that exist in a Data Store.
	 * 
	 * @param datastoreName
	 *            The name to which the Data Store is bound in the Service.<br>
	 * <br>
	 * @return Names of the Views of the Data Store.<br>
	 * <br>
	 */
	Enumeration<String> getViewNames(String datastoreName);

}
