package com.warework.service.datastore.view;

/**
 * <b> Object Database Management System (ODBMS) View</b><br>
 * <br>
 * This View is a facade for a database management system in which information
 * is represented in the form of objects. It allows object-oriented programmers
 * to develop a data model with Java objects, store them and replicate or modify
 * existing objects to make new objects within the OODBMS. Six basic operations
 * are allowed here:<br>
 * <br>
 * <ul class="t0">
 * <li><I>Save</I>: Stores an object in the database.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><I>Update</I>: Updates an object in the database.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><I>Delete</I>: Removes an object in the database.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><I>Find</I>: Retrieves a specific object from the database.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><I>List</I>: Retrieves a list of objects from the database.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><I>Count</I>: Counts objects in the database.<br>
 * </li>
 * </ul>
 * <br>
 * Connect with the database<br>
 * <br>
 * Even before you perform any of these operations, what you have to do first is
 * to connect to the database management system:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;an&nbsp;ODBMS&nbsp;View&nbsp;interface.&nbsp;<br>ODBMSView&nbsp;view&nbsp;=&nbsp;(ODBMSView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();</code>
 * <br>
 * <br>
 * Begin a transaction in the database<br>
 * <br>
 * In the following examples we are going to save some information in the
 * database but before that, it is recommended to begin a transaction with the
 * database.<br>
 * <br>
 * A transaction comprises a unit of work performed within a database management
 * system, and treated in a coherent and reliable way independent of other
 * transactions. Transactions in a database environment have two main
 * purposes:<br>
 * <br>
 * <ul class="t0">
 * <li>To provide reliable units of work that allow correct recovery from
 * failures and keep a database consistent even in cases of system failure, when
 * execution stops (completely or partially) and many operations upon a database
 * remain uncompleted, with unclear status.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
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
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;an&nbsp;ODBMS&nbsp;View&nbsp;interface.&nbsp;<br>ODBMSView&nbsp;view&nbsp;=&nbsp;(ODBMSView)&nbsp;datastoreService.getView(...);&nbsp;<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();</code>
 * <br>
 * <br>
 * Save an object<br>
 * <br>
 * Now it is the right time to perform some <a target="blank" HREF=
 * "http://en.wikipedia.org/wiki/Create,_read,_update_and_delete">CRUD</a>
 * (Create, Read, Update, Delete) operations. First, we are going to save one
 * object in the database:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;an&nbsp;ORM&nbsp;View&nbsp;interface.&nbsp;<br>ODBMSView&nbsp;view&nbsp;=&nbsp;(ODBMSView)&nbsp;datastoreService.getView(...);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;Java&nbsp;Bean.&nbsp;<br>User&nbsp;user&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;some&nbsp;data&nbsp;in&nbsp;the&nbsp;Java&nbsp;Bean.<br>user.setName(&quot;James&quot;);<br>user.setDateOfBirth(new&nbsp;Date());<br>user.setPassword(new&nbsp;Integer(8713));<br><br>&#47;&#47;&nbsp;Save&nbsp;the&nbsp;Java&nbsp;Bean&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.save(user);</code>
 * <br>
 * <br>
 * You can also invoke this method with a callback object. This object will be
 * invoked when the operation is done or fails. Check out the following
 * example<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Begin&nbsp;a&nbsp;transaction&nbsp;in&nbsp;the&nbsp;database&nbsp;management&nbsp;system.&nbsp;<br>view.beginTransaction();<br><br>&#47;&#47;&nbsp;Create&nbsp;a&nbsp;Java&nbsp;Bean.&nbsp;<br>User&nbsp;user&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;some&nbsp;data&nbsp;in&nbsp;the&nbsp;Java&nbsp;Bean.<br>user.setName(&quot;James&quot;);<br>user.setDateOfBirth(new&nbsp;Date());<br>user.setPassword(new&nbsp;Integer(8713));<br><br>&#47;&#47;&nbsp;Save&nbsp;the&nbsp;Java&nbsp;Bean&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.save(user,&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;successful&nbsp;operation&nbsp;here.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;'result'&nbsp;is&nbsp;the&nbsp;object&nbsp;just&nbsp;saved.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;(User)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;user&nbsp;name.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;User&nbsp;name:&nbsp;&quot;&nbsp;+&nbsp;user.getName());<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * It is also possible to pass objects / attributes to the callback so you can
 * use them at onSuccess or onFailure. For this purpose, we have to use a Map
 * when the callback is created. Check out this example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Attributes&nbsp;for&nbsp;the&nbsp;callback.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;attributes&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;attributes.&nbsp;<br>attributes.put(&quot;color&quot;,&nbsp;&quot;red&quot;);<br>attributes.put(&quot;password&quot;,&nbsp;new&nbsp;Integer(123));<br><br>&#47;&#47;&nbsp;Save&nbsp;the&nbsp;Java&nbsp;Bean&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.save(user,&nbsp;new&nbsp;AbstractCallback(getScopeFacade(),&nbsp;attributes){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;color&nbsp;=&nbsp;(String)&nbsp;getAttribute(&quot;color&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Retrieve&nbsp;every&nbsp;attribute&nbsp;name&nbsp;with&nbsp;'getAttributeNames()'.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integer&nbsp;password&nbsp;=&nbsp;(Integer)&nbsp;getAttribute(&quot;password&quot;);<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Now we are going to save multiple objects with just one line of code. You can
 * save an array or a collection of objects like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Save&nbsp;three&nbsp;Java&nbsp;Beans&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.save(new&nbsp;User[]{user1,&nbsp;user2,&nbsp;user3});</code>
 * <br>
 * <br>
 * If the underlying Data Store can save all those objects at once, just one
 * operation will be performed. If not, the Framework will start a batch
 * operation automatically. Batch operations are very useful because they allow
 * us to track each operation executed. The following example stores a
 * collection of Java Beans and displays data about the batch operation: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;We&nbsp;can&nbsp;also&nbsp;save&nbsp;collections.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;new&nbsp;ArrayList&lt;User&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;some&nbsp;users&nbsp;in&nbsp;the&nbsp;collection.&nbsp;<br>users.add(user1);<br>users.add(user2);<br>users.add(user3);<br><br>&#47;&#47;&nbsp;Save&nbsp;the&nbsp;collection&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.save(users,&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;This&nbsp;callback&nbsp;method&nbsp;is&nbsp;invoked&nbsp;three&nbsp;times,&nbsp;one&nbsp;for.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;each&nbsp;object&nbsp;to&nbsp;save.<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;current&nbsp;object&nbsp;saved.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;(User)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;user&nbsp;name.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Current&nbsp;user&nbsp;name:&nbsp;&quot;&nbsp;+&nbsp;user.getName());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;items&nbsp;saved.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Total&nbsp;saved:&nbsp;&quot;&nbsp;+&nbsp;getBatch().count());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;percentage&nbsp;of&nbsp;completion.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;%&nbsp;saved:&nbsp;&quot;&nbsp;+&nbsp;getBatch().progress());<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * When Data Stores save arrays or collections at once but we want to use
 * Warework batch operations instead (to track each object individually), in
 * this case we have to configure the Data Store to work as we need. This is
 * done with PARAMETER_SKIP_NATIVE_BATCH_SUPPORT. Use this constant (or value
 * &quot;skip-native-batch-support&quot;) in the connector of the Data Store to
 * force the Framework use batch operations.<br>
 * <br>
 * Method getBatch provides the following useful information about the batch
 * operation in execution:<br>
 * <br>
 * <ul class="t0">
 * <li>getBatch().count(): counts the amount of callbacks executed in the batch
 * operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>getBatch().duration(): gets how long (in milliseconds) is taking the
 * current batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>getBatch().id(): gets the ID of the batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>getBatch().progress(): gets the percentage of completion of the current
 * batch operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>getBatch().size(): gets the total of callbacks to perform in the batch
 * operation.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>getBatch().startTime(): gets the time (in milliseconds ) when the batch
 * operation started.<br>
 * </li>
 * </ul>
 * <br>
 * Update objects<br>
 * <br>
 * Previously stored objects in the database can be updated later on with new
 * values:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Update&nbsp;some&nbsp;data&nbsp;in&nbsp;the&nbsp;Java&nbsp;Bean.<br>user.setName(&quot;James&nbsp;Jr.&quot;);<br><br>&#47;&#47;&nbsp;Update&nbsp;the&nbsp;object&nbsp;in&nbsp;the&nbsp;data&nbsp;store.<br>view.update(user);</code>
 * <br>
 * <br>
 * You can also use callbacks and update multiple objects (arrays or
 * collections) with batch operations: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Update&nbsp;three&nbsp;Java&nbsp;Beans&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.update(new&nbsp;User[]{user1,&nbsp;user2,&nbsp;user3},&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;current&nbsp;object&nbsp;updated.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;(User)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;user&nbsp;name.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Current&nbsp;user&nbsp;name:&nbsp;&quot;&nbsp;+&nbsp;user.getName());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;items&nbsp;updated.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Total&nbsp;updated:&nbsp;&quot;&nbsp;+&nbsp;getBatch().count());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;percentage&nbsp;of&nbsp;completion.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;%&nbsp;updated:&nbsp;&quot;&nbsp;+&nbsp;getBatch().progress());<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Delete objects<br>
 * <br>
 * The following example shows how to delete one object:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Delete&nbsp;the&nbsp;object&nbsp;in&nbsp;the&nbsp;data&nbsp;store.<br>view.delete(user);</code>
 * <br>
 * <br>
 * You can also use callbacks and delete arrays or collections with batch
 * operations: <br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Delete&nbsp;three&nbsp;Java&nbsp;Beans&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.delete(new&nbsp;User[]{user1,&nbsp;user2,&nbsp;user3},&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;current&nbsp;object&nbsp;deleted.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User&nbsp;user&nbsp;=&nbsp;(User)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;user&nbsp;name.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Current&nbsp;user&nbsp;name:&nbsp;&quot;&nbsp;+&nbsp;user.getName());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;items&nbsp;deleted.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;Total&nbsp;deleted:&nbsp;&quot;&nbsp;+&nbsp;getBatch().count());<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Display&nbsp;percentage&nbsp;of&nbsp;completion.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;%&nbsp;deleted:&nbsp;&quot;&nbsp;+&nbsp;getBatch().progress());<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * To remove every object of a specific type, we have to provide the class:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Delete&nbsp;all&nbsp;users&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>view.delete(User.class);</code>
 * <br>
 * <br>
 * This code can be executed in two different ways by the Framework. It is very
 * important to understand that delete method creates a query when a class is
 * provided. If the Data Store that implements this View can delete the objects
 * from a given query then you should not worry about running operations like
 * this because the Data Store directly handles the operation and takes care
 * about everything. When Data Stores do not support deleting objects with a
 * given query, you need to know that, in this case, the Framework divides the
 * operation in two different parts. First, it runs a query to retrieve a list
 * of objects and after that deletes each item of the list. The problem arises
 * when the list to delete is so big that it kills the virtual machine memory.
 * So please, know your Data Store first and handle this operation with
 * care.<br>
 * <br>
 * If the underlying Data Store supports queries to delete multiple objects then
 * batches will perform just one operation. When the Framework directly handles
 * the operation because the Data Store does not support it, then you will be
 * able to track each object deleted.<br>
 * <br>
 * The following example shows how to remove a set of objects specified in a
 * query:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Set&nbsp;an&nbsp;expression&nbsp;like&nbsp;this:&nbsp;(name&nbsp;=&nbsp;'Carl').<br>where.setExpression(where.createEqualToValue(&quot;name&quot;,&nbsp;&quot;Carl&quot;));<br><br>&#47;&#47;&nbsp;Delete&nbsp;every&nbsp;user&nbsp;which&nbsp;name&nbsp;is&nbsp;'Carl'.&nbsp;<br>view.delete(query);</code>
 * <br>
 * <br>
 * We will show soon how to configure Query objects. By now just bear in mind
 * that we can delete objects with queries too.<br>
 * <br>
 * If the query is in the form of an XML file, you can load the query and delete
 * the objects returned like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Delete&nbsp;every&nbsp;user&nbsp;specified&nbsp;by&nbsp;&quot;list-users&quot;&nbsp;query.&nbsp;<br>view.executeDeleteByName(&quot;list-users&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * This method uses a Provider to load the query from an XML file. Review later
 * on how to list objects with XML files. We explain in detail there how this
 * mechanism works and the same rules apply for executeDeleteByName. Also, like
 * we have seen before, you can use callback here:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Delete&nbsp;every&nbsp;user&nbsp;specified&nbsp;by&nbsp;&quot;list-users&quot;&nbsp;query.<br>view.executeDeleteByName(&quot;list-users&quot;,&nbsp;null,&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;each&nbsp;object&nbsp;deleted&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * If the query accepts parameters, you can update the query with a Map:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Attributes&nbsp;for&nbsp;the&nbsp;query.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;filter&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();&nbsp;<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;attributes.&nbsp;<br>filter.put(&quot;name&quot;,&nbsp;&quot;John&quot;);<br><br>&#47;&#47;&nbsp;Delete&nbsp;every&nbsp;user&nbsp;named&nbsp;&quot;John&quot;.<br>view.executeDeleteByName(&quot;list-users&quot;,&nbsp;filter,&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;each&nbsp;object&nbsp;deleted&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Commit or rollback a transaction<br>
 * <br>
 * Every update operation that we performed in the previous examples is related
 * to the transaction that we created before. Once the work is done, you should
 * either commit or rollback the transaction. If the operations were executed
 * without problems, then you should perform commit to register the changes in
 * the database:<br>
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
 * Find an object in the database<br>
 * <br>
 * Now we are going to see how to retrieve objects from the database. Depending
 * on how many objects we want to get, we can perform two different types of
 * operations. <br>
 * <br>
 * To search for a specific object from the database you have to invoke the find
 * method. This method requires you to provide an object of the same type as the
 * one you want to retrieve, with some data in it so it will be used as a filter
 * to find the object in the database. To better understand how it works, check
 * out the API description of this method:<br>
 * <br>
 * <br>
 * <code>&#47;**<br>&nbsp;*&nbsp;Finds&nbsp;an&nbsp;object&nbsp;by&nbsp;its&nbsp;values.&nbsp;This&nbsp;method&nbsp;returns&nbsp;the&nbsp;object&nbsp;of&nbsp;the&nbsp;data<br>&nbsp;*&nbsp;store&nbsp;which&nbsp;matches&nbsp;the&nbsp;type&nbsp;and&nbsp;all&nbsp;non-null&nbsp;field&nbsp;values&nbsp;from&nbsp;a&nbsp;given<br>&nbsp;*&nbsp;object.&nbsp;This&nbsp;is&nbsp;done&nbsp;via&nbsp;reflecting&nbsp;the&nbsp;type&nbsp;and&nbsp;all&nbsp;of&nbsp;the&nbsp;fields&nbsp;from<br>&nbsp;*&nbsp;the&nbsp;given&nbsp;object,&nbsp;and&nbsp;building&nbsp;a&nbsp;query&nbsp;expression&nbsp;where&nbsp;all<br>&nbsp;*&nbsp;non-null-value&nbsp;fields&nbsp;are&nbsp;combined&nbsp;with&nbsp;AND&nbsp;expressions.&nbsp;So,&nbsp;the&nbsp;object<br>&nbsp;*&nbsp;you&nbsp;will&nbsp;get&nbsp;will&nbsp;be&nbsp;the&nbsp;same&nbsp;type&nbsp;as&nbsp;the&nbsp;object&nbsp;that&nbsp;you&nbsp;will&nbsp;provide.&nbsp;<br>&nbsp;*&nbsp;The&nbsp;result&nbsp;of&nbsp;the&nbsp;query&nbsp;must&nbsp;return&nbsp;one&nbsp;single&nbsp;object&nbsp;in&nbsp;order&nbsp;to&nbsp;avoid<br>&nbsp;*&nbsp;an&nbsp;exception.&nbsp;In&nbsp;many&nbsp;cases,&nbsp;you&nbsp;may&nbsp;only&nbsp;need&nbsp;to&nbsp;provide&nbsp;the&nbsp;ID&nbsp;fields<br>&nbsp;*&nbsp;of&nbsp;the&nbsp;object&nbsp;to&nbsp;retrieve&nbsp;one&nbsp;single&nbsp;object.&nbsp;In&nbsp;relational&nbsp;databases&nbsp;like<br>&nbsp;*&nbsp;Oracle&nbsp;or&nbsp;MySQL,&nbsp;these&nbsp;ID&nbsp;fields&nbsp;are&nbsp;the&nbsp;primary&nbsp;keys.&nbsp;<br>&nbsp;*&nbsp;<br>&nbsp;*&nbsp;@param&nbsp;filter<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Filter&nbsp;used&nbsp;to&nbsp;find&nbsp;an&nbsp;object&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;This&nbsp;object<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;specifies&nbsp;two&nbsp;things:&nbsp;first,&nbsp;the&nbsp;type&nbsp;of&nbsp;the&nbsp;object&nbsp;to&nbsp;search<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for&nbsp;in&nbsp;the&nbsp;data&nbsp;store,&nbsp;and&nbsp;second,&nbsp;the&nbsp;values&nbsp;that&nbsp;identify<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the&nbsp;object&nbsp;in&nbsp;the&nbsp;data&nbsp;store.&nbsp;<br>&nbsp;*&nbsp;@return&nbsp;Object&nbsp;from&nbsp;the&nbsp;data&nbsp;store&nbsp;that&nbsp;matches&nbsp;the&nbsp;type&nbsp;and&nbsp;the&nbsp;values<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of&nbsp;the&nbsp;given&nbsp;object.&nbsp;If&nbsp;more&nbsp;than&nbsp;one&nbsp;object&nbsp;is&nbsp;found&nbsp;in&nbsp;the&nbsp;data<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;store,&nbsp;then&nbsp;an&nbsp;exception&nbsp;is&nbsp;thrown.&nbsp;If&nbsp;no&nbsp;objects&nbsp;are&nbsp;found,&nbsp;then<br>&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this&nbsp;method&nbsp;returns&nbsp;&lt;code&gt;null&lt;&#47;code&gt;.<br>&nbsp;*&nbsp;@throws&nbsp;ClientException<br>&nbsp;*&#47;<br>Object&nbsp;find(Object&nbsp;filter)&nbsp;throws&nbsp;ClientException;</code>
 * <br>
 * <br>
 * Let's see some sample code. Suppose that there is a user named Steve in the
 * database, we could retrieve an object instance that represents this user like
 * this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;an&nbsp;ODBMS&nbsp;View&nbsp;interface.&nbsp;<br>ODBMSView&nbsp;view&nbsp;=&nbsp;(ODBMSView)&nbsp;datastoreService.getView(...);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;the&nbsp;object.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;locate&nbsp;the&nbsp;object&nbsp;in&nbsp;the&nbsp;database.<br>filter.setName(&quot;Steve&quot;);<br><br>&#47;&#47;&nbsp;Find&nbsp;the&nbsp;object&nbsp;in&nbsp;the&nbsp;database&nbsp;which&nbsp;type&nbsp;is&nbsp;&quot;User&quot;&nbsp;and&nbsp;name&nbsp;is&nbsp;&quot;Steve&quot;.&nbsp;<br>User&nbsp;user&nbsp;=&nbsp;(User)&nbsp;view.find(filter);</code>
 * <br>
 * <br>
 * The object from the database is returned with all its data associated to it.
 * The idea is that developers can search for an object just by providing some
 * of its attributes and retrieve the same object type with the last information
 * it had when it was stored or updated in the database. Bear in mind that an
 * exception is thrown if more than one object is found in the database. This
 * method just searches for a single object in the database. In order to
 * retrieve a collection of objects, you should invoke different methods.<br>
 * <br>
 * List objects from the database<br>
 * <br>
 * Warework provides three different ways to retrieve a list of objects from a
 * database. The first one works similar as the find method, but this time you
 * will get a collection of objects (instead of one single object). Check it out
 * with an example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;an&nbsp;instance&nbsp;of&nbsp;an&nbsp;ODBMS&nbsp;View&nbsp;interface.&nbsp;<br>ODBMSView&nbsp;view&nbsp;=&nbsp;(ODBMSView)&nbsp;datastoreService.getView(...);<br><br>&#47;&#47;&nbsp;Connect&nbsp;the&nbsp;Data&nbsp;Store.&nbsp;<br>view.connect();<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;the&nbsp;objects.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;the&nbsp;objects&nbsp;in&nbsp;the&nbsp;database.<br>filter.setPassword(new&nbsp;Integer(8713));<br><br>&#47;&#47;&nbsp;Find&nbsp;the&nbsp;objects&nbsp;which&nbsp;type&nbsp;is&nbsp;&quot;User&quot;&nbsp;and&nbsp;password&nbsp;is&nbsp;&quot;8713&quot;.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(filter,&nbsp;null,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * The main characteristic of this method is that it creates a query where every
 * non-null attribute of the given object is combined with AND expressions, for
 * example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;the&nbsp;objects.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;the&nbsp;objects&nbsp;in&nbsp;the&nbsp;database.<br>filter.setName(&quot;Steve&quot;);<br>filter.setPassword(new&nbsp;Integer(8713));<br><br>&#47;&#47;&nbsp;Find&nbsp;&quot;User&quot;&nbsp;objects&nbsp;which&nbsp;name&nbsp;is&nbsp;&quot;Steve&quot;&nbsp;and&nbsp;password&nbsp;is&nbsp;&quot;8713&quot;.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(filter,&nbsp;null,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * When this operation is executed, Warework creates a query like this (it is
 * just a representation of the query, it is not used by the underlying
 * database):<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;=&nbsp;'Steve')&nbsp;AND&nbsp;(password&nbsp;=&nbsp;8713))</code>
 * <br>
 * <br>
 * Also, when objects of the data model are related each other, every non-null
 * attribute of the related objects is used to build the query. For example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;an&nbsp;user&nbsp;by&nbsp;its&nbsp;contact.&nbsp;<br>Contact&nbsp;contact&nbsp;=&nbsp;new&nbsp;Contact();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;User&nbsp;objects&nbsp;by&nbsp;its&nbsp;email.<br>contact.setEmail(&quot;steve@mail.com&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;User&nbsp;objects.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;User&nbsp;objects&nbsp;in&nbsp;the&nbsp;database.<br>filter.setName(&quot;Steve&quot;);<br>filter.setContact(contact);<br><br>&#47;&#47;&nbsp;Find&nbsp;&quot;User&quot;&nbsp;objects&nbsp;which&nbsp;name&nbsp;is&nbsp;&quot;Steve&quot;&nbsp;and&nbsp;email&nbsp;is&nbsp;&quot;steve@mail.com&quot;.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(filter,&nbsp;null,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * This operation creates a query like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;=&nbsp;'Steve')&nbsp;AND&nbsp;(contact.email&nbsp;=&nbsp;'steve@mail.com'))</code>
 * <br>
 * <br>
 * While the AND expression is always used to create the query (there is no
 * chance to modify this type of expression in this method), you can indicate
 * which specific operation must be performed in the attributes of the object.
 * By default, the equal operator is used when a non-null attribute is found but
 * it is possible to use different ones like: not equals to, greater than,
 * greater than or equals to, less than, less than or equals to, like, not like,
 * is null or is not null. The following example shows how to list User objects
 * which name is Steve and password is greater than 1000:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;the&nbsp;objects.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;the&nbsp;objects&nbsp;in&nbsp;the&nbsp;database.<br>filter.setName(&quot;Steve&quot;);<br>filter.setPassword(new&nbsp;Integer(1000));<br><br>&#47;&#47;&nbsp;Define&nbsp;which&nbsp;operators&nbsp;to&nbsp;use.&nbsp;<br>Map&lt;String,&nbsp;Operator&gt;&nbsp;operator&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Operator&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;which&nbsp;operation&nbsp;to&nbsp;perform&nbsp;for&nbsp;each&nbsp;attribute&nbsp;of&nbsp;the&nbsp;filter.<br>operator.put(&quot;password&quot;,&nbsp;Operator.GREATER_THAN);<br><br>&#47;&#47;&nbsp;Find&nbsp;&quot;User&quot;s&nbsp;which&nbsp;name&nbsp;is&nbsp;&quot;Steve&quot;&nbsp;and&nbsp;password&nbsp;is&nbsp;greater&nbsp;than&nbsp;&quot;1000&quot;.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(filter,&nbsp;operator,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * This operation creates a query like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;=&nbsp;'Steve')&nbsp;AND&nbsp;(password&nbsp;&gt;&nbsp;1000))</code>
 * <br>
 * <br>
 * It is also possible to change the operator in referenced objects:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;an&nbsp;user&nbsp;by&nbsp;its&nbsp;contact.&nbsp;<br>Contact&nbsp;contact&nbsp;=&nbsp;new&nbsp;Contact();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;User&nbsp;objects&nbsp;by&nbsp;its&nbsp;email.<br>contact.setEmail(&quot;steve@mail.com&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;filter&nbsp;to&nbsp;find&nbsp;User&nbsp;objects.&nbsp;<br>User&nbsp;filter&nbsp;=&nbsp;new&nbsp;User();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;data&nbsp;required&nbsp;to&nbsp;find&nbsp;User&nbsp;objects&nbsp;in&nbsp;the&nbsp;database.<br>filter.setName(&quot;Steve&quot;);<br>filter.setContact(contact);<br><br>&#47;&#47;&nbsp;Define&nbsp;which&nbsp;operators&nbsp;to&nbsp;use.&nbsp;<br>Map&lt;String,&nbsp;Operator&gt;&nbsp;operator&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Operator&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;which&nbsp;operation&nbsp;to&nbsp;perform&nbsp;for&nbsp;each&nbsp;attribute&nbsp;of&nbsp;the&nbsp;filter.<br>operator.put(&quot;contact.email&quot;,&nbsp;Operator.LIKE);<br><br>&#47;&#47;&nbsp;Find&nbsp;&quot;User&quot;&nbsp;objects&nbsp;which&nbsp;name&nbsp;is&nbsp;Steve&nbsp;and&nbsp;email&nbsp;is&nbsp;like&nbsp;&quot;steve@mail.com&quot;.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(filter,&nbsp;operator,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * This is the generated query:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;=&nbsp;'Steve')&nbsp;AND&nbsp;(contact.email&nbsp;LIKE&nbsp;'steve@mail.com'))</code>
 * <br>
 * <br>
 * What if you just want to list all the objects of the same type? In this case
 * you can provide just the class instead of an object instance:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;List&nbsp;all&nbsp;&quot;User&quot;&nbsp;objects.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;null,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * When you provide a class you can only specify two operators: IS_NULL and
 * IS_NOT_NULL. This is because you cannot provide the values of the fields in a
 * class. For example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;which&nbsp;operators&nbsp;to&nbsp;use.&nbsp;<br>Map&lt;String,&nbsp;Operator&gt;&nbsp;operator&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Operator&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;only&nbsp;IS_NULL&nbsp;or&nbsp;IS_NOT_NULL&nbsp;operators.<br>operator.put(&quot;name&quot;,&nbsp;Operator.IS_NOT_NULL);<br><br>&#47;&#47;&nbsp;Find&nbsp;every&nbsp;&quot;User&quot;&nbsp;which&nbsp;name&nbsp;is&nbsp;not&nbsp;null.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;operator,&nbsp;null,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * This operation creates a query like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(name&nbsp;IS_NOT_NULL)</code><br>
 * <br>
 * The same way we did before we can use a callback object in this method. Just
 * bear in mind that now the result of the operation is provided by the callback
 * onSuccess method:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;which&nbsp;operators&nbsp;to&nbsp;use.&nbsp;<br>Map&lt;String,&nbsp;Operator&gt;&nbsp;operator&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Operator&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;only&nbsp;IS_NULL&nbsp;or&nbsp;IS_NOT_NULL&nbsp;operators.<br>operator.put(&quot;name&quot;,&nbsp;Operator.IS_NOT_NULL);<br><br>&#47;&#47;&nbsp;Find&nbsp;every&nbsp;&quot;User&quot;&nbsp;which&nbsp;name&nbsp;is&nbsp;not&nbsp;null.&nbsp;<br>view.list(User.class,&nbsp;null,&nbsp;null,&nbsp;-1,&nbsp;-1,&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;database&nbsp;query&nbsp;result.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;collection&nbsp;here...<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * <br>
 * Sort results<br>
 * <br>
 * The third argument of the list method allows us to define how to sort the
 * results. It is fairly easy:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;order&nbsp;of&nbsp;the&nbsp;results.&nbsp;<br>OrderBy&nbsp;order&nbsp;=&nbsp;new&nbsp;OrderBy();<br><br>&#47;&#47;&nbsp;Set&nbsp;ascending&nbsp;order&nbsp;for&nbsp;field&nbsp;&quot;name&quot;.<br>order.addAscending(&quot;name&quot;);<br><br>&#47;&#47;&nbsp;List&nbsp;every&nbsp;&quot;User&quot;&nbsp;sorted&nbsp;by&nbsp;name.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;null,&nbsp;order,&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * The result of this operation will be a list of all users sorted in ascending
 * order. The query generated looks like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;ORDER&nbsp;BY&nbsp;name&nbsp;ASC</code><br>
 * <br>
 * Pagination<br>
 * <br>
 * Sometimes you may need to limit the number of objects returned by the
 * database when a query operation is performed. Let us say that there are 26
 * objects of USER type and that we just expect to retrieve the first 10
 * objects. We can write something like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;the&nbsp;first&nbsp;10&nbsp;objects.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;null,&nbsp;null,&nbsp;1,&nbsp;10);</code>
 * <br>
 * <br>
 * What is going on right now? When you specify the number of objects that you
 * want in the result of a database, Warework automatically calculates the
 * number of pages that hold this number of objects. In the previous example we
 * specified 10 objects per result and with this information Warework estimates
 * that the size of each page is 10 objects and that there are three pages: page
 * 1 with 10 objects, page 2 with 10 objects and page 3 with 6 objects. If now
 * we need to retrieve the next ten objects, we have to indicate that we want
 * the second page:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;objects&nbsp;from&nbsp;11&nbsp;to&nbsp;20.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;null,&nbsp;null,&nbsp;2,&nbsp;10);</code>
 * <br>
 * <br>
 * If we request page number three, we get the last 6 objects from the database.
 * The important fact to keep in mind here is that the number of objects remains
 * as 10:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Get&nbsp;objects&nbsp;from&nbsp;21&nbsp;to&nbsp;26.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(User.class,&nbsp;null,&nbsp;null,&nbsp;3,&nbsp;10);</code>
 * <br>
 * <br>
 * Creating custom queries with Query object<br>
 * <br>
 * <a NAME="ODBMS_run_custom_query_object"></a> Warework also gives the
 * possibility to create Query objects where to specify all the characteristics
 * we had seen before. These Query objects allows to define AND, OR and NOT
 * expressions as well, so they are more useful in certain cases. The following
 * example shows how to run a simple query with this object:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;type&nbsp;of&nbsp;objects&nbsp;to&nbsp;look&nbsp;for.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;List&nbsp;every&nbsp;&quot;User&quot;&nbsp;object.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * This example retrieves every User object from the database. To filter the
 * results, we have to create a Where clause for the query. Let's see how to
 * filter the query by specifying a value for the name attribute of the User
 * object:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Set&nbsp;an&nbsp;expression&nbsp;like:&nbsp;(name&nbsp;=&nbsp;'Steve').<br>where.setExpression(where.createEqualToValue(&quot;name&quot;,&nbsp;&quot;Steve&quot;));<br><br>&#47;&#47;&nbsp;List&nbsp;&quot;User&quot;&nbsp;objects&nbsp;which&nbsp;name&nbsp;is&nbsp;'Steve'.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * This operation creates a query like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(name&nbsp;=&nbsp;'Steve')</code><br>
 * <br>
 * One great feature about Query objects is that you can assign values from
 * different sources to the attributes of the object that define the search
 * criteria. This is handled with <a target="blank" HREF=
 * "http://warework.com/download/list.page?com=5&amp;lan=1">Warework
 * Providers</a>, that is, you have to create an expression for the Where clause
 * which gets the value from a Provider and assigns this value to the attribute
 * of the object. For example, suppose that we have a Provider named
 * &quot;password-provider&quot; which returns the password for a given user
 * name. To search for a user which matches the password given by the Provider,
 * we can create a query like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;password;&nbsp;assign&nbsp;to&nbsp;password&nbsp;the&nbsp;value&nbsp;of&nbsp;the&nbsp;Provider.<br>where.setExpression(where.createEqualToProviderValue(&quot;password&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;password-provider&quot;,&nbsp;&quot;steve&quot;));<br><br>&#47;&#47;&nbsp;List&nbsp;&quot;User&quot;&nbsp;objects&nbsp;which&nbsp;name&nbsp;is&nbsp;'Steve'.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * This time, the value for the attribute is not directly assigned by the
 * developer; instead, it is assigned with the value returned by the Provider.
 * So, if &quot;password-provider&quot; returns 8713 for &quot;steve&quot;, then
 * the query created looks like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(password&nbsp;=&nbsp;8713)</code><br>
 * <br>
 * Now we are going to create one AND expression to filter the query with two
 * attributes. Check it out with the following example:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Create&nbsp;one&nbsp;AND&nbsp;expression.&nbsp;<br>And&nbsp;and&nbsp;=&nbsp;where.createAnd();<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;name&nbsp;and&nbsp;password.<br>and.add(where.createLikeValue(&quot;name&quot;,&nbsp;&quot;Steve&quot;));<br>and.add(where.createGreaterThanValue(&quot;password&quot;,&nbsp;new&nbsp;Integer(1000)));<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;AND&nbsp;expression&nbsp;in&nbsp;the&nbsp;WHERE&nbsp;clause.<br>where.setExpression(and);<br><br>&#47;&#47;&nbsp;List&nbsp;&quot;User&quot;&nbsp;which&nbsp;name&nbsp;is&nbsp;like&nbsp;'Steve'&nbsp;and&nbsp;password&nbsp;is&nbsp;greater&nbsp;than&nbsp;1000.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * This operation creates a query like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;LIKE&nbsp;'Steve')&nbsp;AND&nbsp;(password&nbsp;&gt;&nbsp;1000))</code>
 * <br>
 * <br>
 * OR expressions with Query objects<br>
 * <br>
 * With Query objects you can also specify OR expressions:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Create&nbsp;one&nbsp;OR&nbsp;expression.&nbsp;<br>Or&nbsp;or&nbsp;=&nbsp;where.createOr();<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;name&nbsp;and&nbsp;password.<br>or.add(where.createLikeValue(&quot;name&quot;,&nbsp;&quot;Steve&quot;));<br>or.add(where.createGreaterThanValue(&quot;password&quot;,&nbsp;new&nbsp;Integer(1000)));<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;OR&nbsp;expression&nbsp;in&nbsp;the&nbsp;WHERE&nbsp;clause.<br>where.setExpression(or);<br><br>&#47;&#47;&nbsp;List&nbsp;&quot;User&quot;&nbsp;which&nbsp;name&nbsp;is&nbsp;like&nbsp;'Steve'&nbsp;or&nbsp;password&nbsp;is&nbsp;greater&nbsp;than&nbsp;1000.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * This operation creates this query:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;LIKE&nbsp;'Steve')&nbsp;OR&nbsp;(password&nbsp;&gt;&nbsp;1000))</code>
 * <br>
 * <br>
 * Of course, you can create more complex queries. The following example shows
 * you how to mix multiple AND, OR and NOT expressions:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Create&nbsp;OR&nbsp;expression.&nbsp;<br>Or&nbsp;or1&nbsp;=&nbsp;where.createOr();<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;name.<br>or1.add(where.createLikeValue(&quot;name&quot;,&nbsp;&quot;Arnold&quot;));<br>or1.add(where.createLikeValue(&quot;name&quot;,&nbsp;&quot;David&quot;));<br><br>&#47;&#47;&nbsp;Create&nbsp;NOT&nbsp;expression.&nbsp;<br>Not&nbsp;not&nbsp;=&nbsp;where.createNot(or1);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;another&nbsp;OR&nbsp;expression.&nbsp;<br>Or&nbsp;or2&nbsp;=&nbsp;where.createOr();<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;password.<br>or2.add(where.createGreaterThanValue(&quot;password&quot;,&nbsp;new&nbsp;Integer(1000)));<br>or2.add(where.createLessThanValue(&quot;password&quot;,&nbsp;new&nbsp;Integer(5000)));<br><br>&#47;&#47;&nbsp;Create&nbsp;one&nbsp;AND&nbsp;expression.&nbsp;<br>And&nbsp;and&nbsp;=&nbsp;where.createAnd();<br><br>&#47;&#47;&nbsp;Filter&nbsp;by&nbsp;name&nbsp;and&nbsp;password.<br>and.add(not);<br>and.add(or2);<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;AND&nbsp;expression&nbsp;in&nbsp;the&nbsp;WHERE&nbsp;clause.<br>where.setExpression(and);<br><br>&#47;&#47;&nbsp;List&nbsp;&quot;User&quot;&nbsp;objects.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * And this is the query representation:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(NOT&nbsp;((name&nbsp;LIKE&nbsp;'Arnold')&nbsp;OR&nbsp;(name&nbsp;LIKE&nbsp;'David'))&nbsp;AND&nbsp;((password&nbsp;&gt;&nbsp;1000)&nbsp;OR&nbsp;(password&nbsp;&lt;&nbsp;5000)))</code>
 * <br>
 * <br>
 * Order and pagination with Query objects<br>
 * <br>
 * Query objects also allow you to define the order of the results and which
 * page to retrieve from the database. The following example shows you how to
 * list the first ten users sorted by name in ascending order:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;type&nbsp;of&nbsp;objects&nbsp;to&nbsp;look&nbsp;for.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;order&nbsp;of&nbsp;the&nbsp;results.&nbsp;<br>OrderBy&nbsp;order&nbsp;=&nbsp;new&nbsp;OrderBy();<br><br>&#47;&#47;&nbsp;Set&nbsp;ascending&nbsp;order&nbsp;for&nbsp;field&nbsp;&quot;name&quot;.<br>order.addAscending(&quot;name&quot;);<br><br>&#47;&#47;&nbsp;Set&nbsp;order&nbsp;by.<br>query.setOrderBy(order);<br><br>&#47;&#47;&nbsp;Set&nbsp;which&nbsp;page&nbsp;to&nbsp;get&nbsp;from&nbsp;the&nbsp;result.<br>query.setPage(1);<br><br>&#47;&#47;&nbsp;Set&nbsp;maximum&nbsp;number&nbsp;of&nbsp;objects&nbsp;to&nbsp;get&nbsp;in&nbsp;the&nbsp;result.<br>query.setPageSize(10);<br><br>&#47;&#47;&nbsp;List&nbsp;the&nbsp;first&nbsp;ten&nbsp;&quot;User&quot;&nbsp;objects&nbsp;sorted&nbsp;by&nbsp;name&nbsp;in&nbsp;ascending&nbsp;order.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.list(query);</code>
 * <br>
 * <br>
 * <a NAME="ODBMS_list_objects_xml"></a> List objects with XML queries<br>
 * <br>
 * Making queries like this is very useful when you have to dynamically create
 * the query (you are free to create the query the way you need it) but, in
 * certain cases, it is better for the programmer to create queries in separate
 * XML files because they can be managed outside the Java code and they are
 * easier to understand.<br>
 * <br>
 * <a NAME="ODBMS_define_default_provider_xml"></a> A very convenient way to
 * keep object queries in separate files consist of keeping each statement in an
 * independent XML file, for example: find-user.xml, list-users.xml, etc. Later
 * on we can read these XML files with a Provider (Warework recommends you to
 * use the Object Query Provider for this task) and use this Provider to read
 * the statements for the ODBMS View. Remember that you can define a default
 * Provider for a View when you associate a View to a Data Store:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Add&nbsp;a&nbsp;View&nbsp;and&nbsp;link&nbsp;a&nbsp;Provider&nbsp;to&nbsp;it.&nbsp;<br>datastoreService.addView(&quot;sample-datastore&quot;,&nbsp;SampleViewImpl.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;view-name&quot;,&nbsp;&quot;object-query-provider&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * The object-query-provider now can be used in the View as the default Provider
 * to read XML queries from a specific directory. Let us say we have the
 * following content for find-steve.xml:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQUAL_TO&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * If object-query-provider is the default Provider in an ODBMS View, we can
 * read the content of this file with the following code:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;the&nbsp;content&nbsp;of&nbsp;'find-steve.xml'&nbsp;and&nbsp;execute&nbsp;it.&nbsp;<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.executeQueryByName(&quot;find-steve&quot;,&nbsp;null,&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * When executeQueryByName is invoked, these actions are performed:<br>
 * <br>
 * <ul class="t0">
 * <li>The ODBMS View requests the find-steve object to object-query-provider.
 * <br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li>object-query-provider reads the content of find-steve.xml and returns it
 * (as a Query object).<br>
 * </ul>
 * <br>
 * <ul class="t0" >
 * <li>The ODBMS View executes the statement included at find-steve.xml in the
 * Data Store. <br>
 * </ul>
 * <br>
 * The ODBMS View and the Object Query Provider are perfect mates. Both, in
 * combination, will simplify a lot the process of executing query statements in
 * your object database. Just write simple XML files and let Warework execute
 * them for you. It is recommended that you check out the documentation
 * associated to the Object Query Provider to fully take advantage of this
 * feature.<br>
 * <br>
 * Operators<br>
 * <br>
 * In XML queries you can specify operators like this: <br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQUAL_TO&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * And you can also use short keywords to identify them:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQ&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * Previous examples perform the same operation. Check out the following table
 * to review which operators are supported and which short names you can
 * use:<br>
 * <br>
 * <table summary='table'>
 * <tr>
 * <td><B>OPERATOR</B><br>
 * </td>
 * <td><B>FULL NAME</B><br>
 * </td>
 * <td><B>SHORT NAME</B><br>
 * </td>
 * </tr>
 * <tr>
 * <td>=<br>
 * </td>
 * <td>EQUAL_TO<br>
 * </td>
 * <td>EQ<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>!=</B><br>
 * </td>
 * <td>NOT_EQUAL_TO<br>
 * </td>
 * <td>NE<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>&lt;</B><br>
 * </td>
 * <td>LESS_THAN<br>
 * </td>
 * <td>LT<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>&lt;=</B><br>
 * </td>
 * <td>LESS_THAN_OR_EQUAL_TO<br>
 * </td>
 * <td>LE<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>&gt;</B><br>
 * </td>
 * <td>GREATER_THAN<br>
 * </td>
 * <td>GT<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>&gt;=</B><br>
 * </td>
 * <td>GREATER_THAN_OR_EQUAL_TO<br>
 * </td>
 * <td>GE<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>IS NULL</B><br>
 * </td>
 * <td>IS_NULL<br>
 * </td>
 * <td>IN<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>IS NOT NULL</B><br>
 * </td>
 * <td>IS_NOT_NULL<br>
 * </td>
 * <td>NN<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>LIKE</B><br>
 * </td>
 * <td>LIKE<br>
 * </td>
 * <td>LK<br>
 * </td>
 * </tr>
 * <tr>
 * <td><B>NOT LIKE</B><br>
 * </td>
 * <td>NOT_LIKE<br>
 * </td>
 * <td>NL<br>
 * </td>
 * </tr>
 * </table>
 * <br>
 * Value types<br>
 * <br>
 * In XML queries you can specify different value types and some of them with
 * short names. For example, the previous query can be written as:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQ&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;string&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * The following table summarizes which data types can be used with short
 * names:<br>
 * <br>
 * <table summary='table'>
 * <tr>
 * <td><B>OBJECT</B><B> TYPE</B><br>
 * </td>
 * <td><B>SHORT NAME</B><br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Boolean<br>
 * </td>
 * <td>boolean<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Byte<br>
 * </td>
 * <td>byte<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Short<br>
 * </td>
 * <td>short<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Integer<br>
 * </td>
 * <td>int or integer<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Long<br>
 * </td>
 * <td>long<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Float<br>
 * </td>
 * <td>float<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Double<br>
 * </td>
 * <td>double<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.Character<br>
 * </td>
 * <td>char or character<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.lang.String<br>
 * </td>
 * <td>string<br>
 * </td>
 * </tr>
 * <tr>
 * <td>java.util.Date<br>
 * </td>
 * <td>date<br>
 * </td>
 * </tr>
 * </table>
 * <br>
 * AND, OR and NOT expressions in XML queries<br>
 * <br>
 * With XML Object Queries you can write the same queries as the one you will
 * code with Query objects. These XML queries also allow defining AND, OR and
 * NOT expressions as well, for example:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;and&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;password&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;GREATER_THAN&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.Integer&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;1000&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;and&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * This XML query looks like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;LIKE&nbsp;'Steve')&nbsp;AND&nbsp;(password&nbsp;&gt;&nbsp;1000))</code>
 * <br>
 * <br>
 * Check it out now with an OR expression:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;or&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;password&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;GREATER_THAN&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.Integer&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;1000&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;or&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * Now, this XML query looks like this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;((name&nbsp;LIKE&nbsp;'Steve')&nbsp;OR&nbsp;(password&nbsp;&gt;&nbsp;1000))</code>
 * <br>
 * <br>
 * The following example shows a more complex query with AND, OR and NOT
 * expressions:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;not&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;and&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;NOT_LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;James&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;or&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;NO_LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Arnold&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;IS_NOT_NULL&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;or&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;and&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;not&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * This is the output for the query:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;NOT&nbsp;((name&nbsp;NOT_LIKE&nbsp;'James')&nbsp;AND&nbsp;((name&nbsp;NOT_LIKE&nbsp;'Arnold')&nbsp;OR&nbsp;(name&nbsp;IS_NOT_NULL)))</code>
 * <br>
 * <br>
 * Setting date values in XML queries<br>
 * <br>
 * Before we proceed with more different examples, keep in mind that it is also
 * possible to assign date values to the attributes:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQUAL_TO&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.util.Date&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;1967&#47;08&#47;12&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;format&gt;yyyy&#47;MM&#47;dd&lt;&#47;format&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;locale&gt;en_US&lt;&#47;locale&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;time-zone&gt;europe&#47;london&lt;&#47;time-zone&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * With this information, Warework will try to parse the date and assign its
 * value to the attribute. While locale and time-zone are not mandatory, you
 * should always provide the format to properly parse the date. Check out some
 * available locale codes here:<br>
 * <br>
 * <a target="blank" HREF=
 * "http://docs.oracle.com/javase/1.4.2/docs/guide/intl/locale.doc.html" ><span
 * >http://docs.oracle.com/javase/1.4.2/docs/guide/intl/locale.doc.html</span
 * ></a><br>
 * <br>
 * Review how to retrieve time-zone codes in the following location:<br>
 * <br>
 * <a target="blank" HREF=
 * "http://docs.oracle.com/javase/1.4.2/docs/api/java/util/TimeZone.html" ><span
 * >http://docs.oracle.com/javase/1.4.2/docs/api/java/util/TimeZone.html</span
 * ></a><br>
 * <br>
 * In XML queries it is also possible to assign values to attributes which come
 * from defined variables or Providers. <br>
 * <br>
 * Creating variables in XML queries<br>
 * <br>
 * First we are going to review how to define a variable for an attribute. The
 * idea about defining variables is simple: by one side, you have to create an
 * expression in the XML query and define in there a name for a variable. This
 * variable will be replaced later on at runtime with the values specified by
 * the developer. The following find-user.xml query file defines a variable for
 * the name attribute of the User object:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;variable-operand&nbsp;name=&quot;USER_NAME&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * Now that we have defined a variable for the name attribute, we can replace
 * this variable with a specific value as follows:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Values&nbsp;for&nbsp;variables.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;values&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;variables&nbsp;for&nbsp;the&nbsp;query.<br>values.put(&quot;USER_NAME&quot;,&nbsp;&quot;Steve&quot;);<br><br>&#47;&#47;&nbsp;Read&nbsp;'find-user.xml',&nbsp;replace&nbsp;variables&nbsp;and&nbsp;execute&nbsp;it.<br>List&lt;User&gt;&nbsp;users&nbsp;=&nbsp;(List&lt;User&gt;)&nbsp;view.executeQueryByName(&quot;find-user&quot;,&nbsp;values,&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;-1,&nbsp;-1);</code>
 * <br>
 * <br>
 * Once the value is assigned to the variable, the output query looks like
 * this:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(name&nbsp;LIKE&nbsp;'Steve')</code><br>
 * <br>
 * We can perform a similar action with a Provider. With Providers, instead of
 * defining a variable in the query, we specify an object from a Provider that
 * will be used as the value for the attribute. For example, suppose that we
 * have a Provider named password-provider which returns user's passwords, that
 * is, if we request object &quot;steve&quot;, the Provider will return
 * something like &quot;3425&quot;. The following query shows how to assign the
 * object named &quot;steve&quot; in Provider &quot;password-provider&quot; to
 * the &quot;password&quot; attribute.<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;...<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...object-query-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;password&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;EQUALS_TO&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider-name&gt;password-provider&lt;&#47;provider-name&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider-object&gt;steve&lt;&#47;provider-object&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;provider-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * After the value &quot;3425&quot; is retrieved from the Provider and it is
 * assigned to &quot;password&quot;, this query is shown as follows:<br>
 * <br>
 * <br>
 * <code>User&nbsp;WHERE&nbsp;(password&nbsp;=&nbsp;3425)</code><br>
 * <br>
 * Count database objects<br>
 * <br>
 * To count every object of a specific type we have to invoke the following
 * operation:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;all&nbsp;users.<br>int&nbsp;count&nbsp;=&nbsp;view.count(User.class);</code>
 * <br>
 * <br>
 * We can also use callbacks in count operations:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;users.<br>view.count(User.class,&nbsp;new&nbsp;AbstractCallback(getScopeFacade()){<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onFailure(Throwable&nbsp;t)&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Handle&nbsp;error&nbsp;here.<br>&nbsp;&nbsp;&nbsp;}<br><br>&nbsp;&nbsp;&nbsp;protected&nbsp;void&nbsp;onSuccess(Object&nbsp;result)&nbsp;{<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#47;&#47;&nbsp;Get&nbsp;count.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Integer&nbsp;count&nbsp;=&nbsp;(Integer)&nbsp;result;<br><br>&nbsp;&nbsp;&nbsp;}<br><br>});</code>
 * <br>
 * It is also possible to count the results of a query:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;query.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;new&nbsp;Query(getScopeFacade());<br><br>&#47;&#47;&nbsp;Search&nbsp;for&nbsp;User&nbsp;objects.<br>query.setObject(User.class);<br><br>&#47;&#47;&nbsp;Define&nbsp;the&nbsp;WHERE&nbsp;clause.&nbsp;<br>Where&nbsp;where&nbsp;=&nbsp;query.getWhere(true);<br><br>&#47;&#47;&nbsp;Set&nbsp;an&nbsp;expression&nbsp;like&nbsp;this:&nbsp;(name&nbsp;=&nbsp;'Carl').<br>where.setExpression(where.createEqualToValue(&quot;name&quot;,&nbsp;&quot;Carl&quot;));<br><br>&#47;&#47;&nbsp;Count&nbsp;users&nbsp;which&nbsp;name&nbsp;is&nbsp;'Carl'.&nbsp;<br>int&nbsp;count&nbsp;=&nbsp;view.count(query);</code>
 * <br>
 * <br>
 * With executeCountByName we can count the objects that an XML query
 * returns:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Count&nbsp;result&nbsp;of&nbsp;&quot;list-users&quot;&nbsp;query.<br>int&nbsp;count&nbsp;=&nbsp;view.executeCountByName(&quot;list-users&quot;,&nbsp;null);</code>
 * <br>
 * <br>
 * Close the connection with the database<br>
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
public interface OdbmsView extends DbmsView, ObjectDatastoreView {

}
