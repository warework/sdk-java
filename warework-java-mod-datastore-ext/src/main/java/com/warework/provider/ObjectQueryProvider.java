package com.warework.provider;

import com.warework.core.loader.AbstractSaxLoader;
import com.warework.core.provider.AbstractSaxProvider;
import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.loader.ObjectQueryXmlLoader;

/**
 * The Object Query Provider is responsible for returning as Query objects the
 * content of XML files that represent queries for Object Data Stores. That is,
 * this Provider allows you to:<br>
 * <br>
 * <ul class="t0">
 * <li>Create queries for Data Stores with XML files like this one:<br>
 * </li>
 * </ul>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;query&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;object-query.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;object&gt;com.mycompany.beans.User&lt;&#47;object&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;where&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;and&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;name&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;LIKE&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;Steve&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;attribute&gt;password&lt;&#47;attribute&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;operator&gt;GREATER_THAN&lt;&#47;operator&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;java.lang.Integer&lt;&#47;type&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;1000&lt;&#47;value&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;value-operand&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;expression&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;and&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;where&gt;<br><br>&lt;&#47;query&gt;</code>
 * <br>
 * <br>
 * <br>
 * <ul class="t0">
 * <li>Read these XML files and transform them into Java Query objects.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li>Execute each query defined in the XML files in the target Data Store.<br>
 * </li>
 * </ul>
 * <br>
 * You can review how to create object queries with XML files in the ODBMS View
 * and ORM View documentation. <br>
 * <br>
 * This Provider is typically used with the Data Store Service. You have to
 * define in a View of a Data Store that the Object Query Provider is the one
 * who will read the statements for the View. But before that, you must
 * configure this Provider in a Scope. <br>
 * <br>
 * <br>
 * <b> Configure and create an Object Query Provider</b><br>
 * <br>
 * To configure this Provider you just need to give the base directory where to
 * read the XML files:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;of&nbsp;the&nbsp;Provider.&nbsp;<br>Map&lt;String,&nbsp;Object&gt;&nbsp;parameters&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;Provider.&nbsp;<br>parameters.put(ObjectQueryProvider.PARAMETER_CONFIG_TARGET,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;&#47;META-INF&#47;statement&#47;xoq&quot;);&nbsp;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Provider.&nbsp;<br>scope.createProvider(&quot;object-query-provider&quot;,&nbsp;ObjectQueryProvider.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;parameters);</code>
 * <br>
 * <br>
 * If you plan to configure this Provider on startup with an XML file then
 * follow this template:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;scope&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;scope.xsd&quot;&gt;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&lt;providers&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;provider&nbsp;name=&quot;object-query-provider&quot;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class=&quot;com.warework.provider.ObjectQueryProvider&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-target&quot;&nbsp;value=&quot;&#47;META-INF&#47;statement&#47;xoq&quot;&nbsp;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;provider&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;providers&gt;&nbsp;&nbsp;&nbsp;<br>&lt;&#47;scope&gt;</code>
 * <br>
 * <br>
 * <br>
 * <b> Retrieve objects from an Object Query Provider</b><br>
 * <br>
 * At this point the Object Query Provider is running and we can request Query
 * objects from it. To do so, we just need to provide the name of an XML file
 * that exists in the target path specified with PARAMETER_CONFIG_TARGET. Keep
 * in mind that you do not need to give the .xml file extension, just provide
 * the name of the file. For example, suppose that we have a file named
 * find-steve.xml, we can parse the XML and get its contents like this:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Read&nbsp;'&#47;META-INF&#47;statement&#47;xoq&#47;find-steve.xml'&nbsp;and&nbsp;get&nbsp;the&nbsp;Query&nbsp;object.&nbsp;<br>Query&nbsp;query&nbsp;=&nbsp;(Query)&nbsp;scope.getObject(&quot;object-query-provider&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;find-steve&quot;);</code>
 * <br>
 * <br>
 * Once we have a Query object, we can use it to query the Data Store.<br>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ObjectQueryProvider extends AbstractSaxProvider {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for this Provider.
	 */
	public static final String DEFAULT_PROVIDER_NAME = "object-query" + StringL2Helper.CHARACTER_HYPHEN
			+ CommonValueL2Constants.STRING_PROVIDER;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the class that represents which Loader to use to load the XML files.
	 * 
	 * @return <code>ObjectQuerySAXLoader</code> class.<br>
	 *         <br>
	 */
	protected Class<? extends AbstractSaxLoader> getLoaderType() {
		return ObjectQueryXmlLoader.class;
	}

}
