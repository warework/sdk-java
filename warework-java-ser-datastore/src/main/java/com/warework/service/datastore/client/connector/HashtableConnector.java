package com.warework.service.datastore.client.connector;

import java.util.Hashtable;

import com.warework.service.datastore.client.HashtableDatastore;

/**
 * Connector that holds the information required to load a Hashtable Data Store.<br>
 * <br>
 * <span>To</span><span> add a Hashtable Data Store into the Data Store Service
 * you have to invoke method </span><span>createClient</span><span>() </span><span>that exists in its Facade
 * with a name and the Hashtable Connector class. You can use just one type of
 * Connector and it does not require any configuration:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Hashtable&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;hashtable-datastore&quot;,<br>&nbsp;&nbsp;&nbsp;HashtableConnector.class,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Data Store Service XML
 * configuration file (this functionality is provided by </span><a
 * target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-mod-datastore-ext"
 * ><span>Warework Data Store Extension Module</span></a><span>):</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;datastore<br>&nbsp;&nbsp;&nbsp;-service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;hashtable-datastore&quot;&nbsp;connector=<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;com.warework.service.datastore.client.connector.HashtableConnector&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class HashtableConnector extends AbstractDatastoreConnector {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Data Store.
	 * 
	 * @return A
	 *         <code>com.warework.service.datastore.client.HashtableDatastore</code>
	 *         Client.<br>
	 * <br>
	 */
	public Class<HashtableDatastore> getClientType() {
		return HashtableDatastore.class;
	}

	/**
	 * Gets a new instance of a Hashtable.
	 * 
	 * @return A <code>java.util.Hashtable</code> instance.<br>
	 * <br>
	 */
	public Object getClientConnection() {
		return new Hashtable<Object, Object>();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Retrieves a <code>java.util.Hashtable</code> class that represents the
	 * type of collection to create.
	 * 
	 * @return A <code>java.util.Hashtable</code> class.<br>
	 * <br>
	 */
	protected Object createConnectionSource() {
		return Hashtable.class;
	}

}
