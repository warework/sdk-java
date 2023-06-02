package com.warework.service.converter.client.connector;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector creates a Converter to compress HTML code.<br>
 * <br>
 * <span>To add a</span><span>n HTML compressor into the Converter Service you
 * have to invoke method </span><span>createClient() </span><span>that exists in
 * its Facade with a name, the HTML compressor Connector class and optionally, a
 * configuration for the compressor. If you plan to use the HTML compressor with
 * the default configuration, then you can use
 * </span><span>HTMLCompressorConnector</span><span> as follows:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;HTML&nbsp;compressor.<br>converterService.createClient(&quot;html-compressor&quot;,<br>&nbsp;&nbsp;&nbsp;HTMLCompressorConnector.class,&nbsp;null);</code>
 * <br>
 * <br>
 * It is also possible to adjust the configuration of the compressor with the
 * following parameters:<br>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_PRESERVE_LINE_BREAKS</span>: Specifies if the Converter
 * has to preserve original line breaks. Accepted values for this parameter are
 * <span>true</span> or <span>false</span> (as <span>java.lang.String</span> or
 * <span>java.lang.Boolean</span> objects). This parameter is optional. Default
 * value is <span>true</span>.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_COMMENTS</span><span>: Specifies if the Converter
 * has to remove the HTML comments. Accepted values for this parameter are
 * </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_MULTI_SPACES</span><span>: Specifies if the
 * Converter has to remove multiple whitespace characters. Accepted values for
 * this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_INTERTAG_SPACES</span><span>: Specifies if the
 * Converter has to remove iter-tag whitespace characters. Accepted values for
 * this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_QUOTES_SPACES</span><span>: Specifies if the
 * Converter has to remove unnecessary tag attribute quotes. Accepted values for
 * this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_SCRIPT_ATTRIBUTES</span><span>: Specifies if the
 * Converter has to remove optional attributes from script tags. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_STYLE_ATTRIBUTES</span><span>: Specifies if the
 * Converter has to remove optional attributes from style tags. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_LINK_ATTRIBUTES</span><span>: Specifies if the
 * Converter has to remove optional attributes from link tags. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_FORM_ATTRIBUTES</span><span>: Specifies if the
 * Converter has to remove optional attributes from form tags. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_INPUT_ATTRIBUTES</span><span>: Specifies if the
 * Converter has to remove optional attributes from input tags. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES</span><span>: Specifies
 * if the Converter has to remove values from boolean tag attributes. Accepted
 * values for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL</span><span>: Specifies if the
 * Converter has to remove &quot;</span><span>javascript:</span><span>&quot;
 * from inline event handlers. Accepted values for this parameter are
 * </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_HTTP_PROTOCOL</span><span>: Specifies if the
 * Converter has to replace &quot;</span><span>http://</span><span>&quot; with
 * &quot;</span><span>//</span><span>&quot; inside tag attributes. Accepted
 * values for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_HTTPS_PROTOCOL</span><span>: Specifies if the
 * Converter has to replace
 * &quot;</span><span>https:&#47;&#47;</span><span>&quot; with
 * &quot;</span><span>//</span><span>&quot; inside tag attributes. Accepted
 * values for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_SURROUNDING_SPACES</span><span>: Specifies if the
 * Converter has to remove spaces around provided tags. Specify with this
 * parameter the tags separated commas, for example:
 * &quot;</span><span>br,p</span><span>&quot;. This parameter is
 * optional.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_SIMPLE_DOCTYPE</span><span>: Specifies if the Converter
 * has to simplify existing </span><span>doctype</span><span>. Accepted values
 * for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_CSS_COMPRESS</span><span>: Specifies if the Converter has
 * to compress inline CSS. Accepted values for this parameter are
 * </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_CSS_LINE_BREAK</span><span>: Specifies the column to
 * split the lines in CSS code. Some source control tools don't like files
 * containing lines longer than, say 8000 characters. The line break option is
 * used in that case to split long lines after a specific column. It can also be
 * used to make the code more readable, easier to debug (especially with the MS
 * Script Debugger). Specify 0 to get a line break after each rule in CSS.
 * Specify -1 to return just one line. This parameter is optional. Default value
 * is -1.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_JAVASCRIPT_COMPRESS</span><span>: Specifies if the
 * Converter has to compress inline JavaScript. Accepted values for this
 * parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_JAVASCRIPT_LINE_BREAK</span><span>: Specifies the column
 * to split the lines in JavaScript code. Some source control tools don't like
 * files containing lines longer than, say 8000 characters. The line break
 * option is used in that case to split long lines after a specific column. It
 * can also be used to make the code more readable, easier to debug (especially
 * with the MS Script Debugger). Specify 0 to get a line break after each
 * semi-colon in JavaScript. Specify -1 to return just one line. This parameter
 * is optional. Default value is -1.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS</span><span>: Specifies
 * if the Converter has to disable all the built-in micro optimizations for
 * JavaScript code. Accepted values for this parameter are
 * </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>false</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_JAVASCRIPT_OBFUSCATE</span><span>: Specifies if the
 * Converter has to obfuscate the inline JavaScript local symbols. Accepted
 * values for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). Use
 * </span><span>false</span><span> just to minify the JavaScript code. This
 * parameter is optional. Default value is
 * </span><span>false</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS</span><span>: Specifies if
 * the Converter has to preserve unnecessary semicolons (such as right before a
 * '}') in inline JavaScript code. Accepted values for this parameter are
 * </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is false.</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>To configure this compressor with parameters, use a
 * </span><span>java.util.Map</span><span> like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;compressor.<br>Map&lt;String, Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;compressor.<br>config.put(HTMLCompressorConnector.PARAMETER_CSS_LINE_BREAK,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;Integer(80));<br>config.put(HTMLCompressorConnector.PARAMETER_REMOVE_COMMENTS,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.FALSE);<br>config.put(HTMLCompressorConnector.PARAMETER_SIMPLE_DOCTYPE,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.FALSE);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;HTML&nbsp;compressor.<br>converterService.createClient(&quot;html-compressor&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;HTMLCompressorConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Proxy Service XML
 * configuration file:</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;html-compressor&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.converter.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;HTMLCompressorConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;preserve-line-breaks&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-comments&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-multi-spaces&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-intertag-spaces&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-quotes-spaces&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-script-attributes&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-style-attributes&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-link-attributes&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-form-attributes&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-input-attributes&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-simple-boolean-attributes&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-javascript-protocol&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-http-protocol&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-https-protocol&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-surrounding-spaces&quot;&nbsp;value=&quot;br,p&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;simple-doctype&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;css-compress&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;css-line-break&quot;&nbsp;value=&quot;80&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;js-compress&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;js-line-break&quot;&nbsp;value=&quot;80&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;js-disable-optimizations&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;js-obfuscate&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;js-preserve-semicolons&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class HtmlCompressorConnector extends AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies if the Converter has to preserve
	 * original line breaks. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_PRESERVE_LINE_BREAKS = "preserve-line-breaks";

	/**
	 * Initialization parameter that specifies if the Converter has to remove the
	 * HTML comments. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_COMMENTS = "remove-comments";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * multiple whitespace characters. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_MULTI_SPACES = "remove-multi-spaces";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * iter-tag whitespace characters. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_INTERTAG_SPACES = "remove-intertag-spaces";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * unnecessary tag attribute quotes. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_QUOTES_SPACES = "remove-quotes-spaces";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * optional attributes from script tags. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_SCRIPT_ATTRIBUTES = "remove-script-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * optional attributes from style tags. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_STYLE_ATTRIBUTES = "remove-style-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * optional attributes from link tags. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_LINK_ATTRIBUTES = "remove-link-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * optional attributes from form tags. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_FORM_ATTRIBUTES = "remove-form-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * optional attributes from input tags. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_INPUT_ATTRIBUTES = "remove-input-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove values
	 * from boolean tag attributes. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES = "remove-simple-boolean-attributes";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * "javascript:" from inline event handlers. Accepted values for this parameter
	 * are <code>true</code> or <code>false</code> (as <code>java.lang.String</code>
	 * or <code>java.lang.Boolean</code> objects). This parameter is optional.
	 * Default value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL = "remove-javascript-protocol";

	/**
	 * Initialization parameter that specifies if the Converter has to replace
	 * "http://" with "//" inside tag attributes. Accepted values for this parameter
	 * are <code>true</code> or <code>false</code> (as <code>java.lang.String</code>
	 * or <code>java.lang.Boolean</code> objects). This parameter is optional.
	 * Default value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_HTTP_PROTOCOL = "remove-http-protocol";

	/**
	 * Initialization parameter that specifies if the Converter has to replace
	 * "https://" with "//" inside tag attributes. Accepted values for this
	 * parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects).
	 * This parameter is optional. Default value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_HTTPS_PROTOCOL = "remove-https-protocol";

	/**
	 * Initialization parameter that specifies if the Converter has to remove spaces
	 * around provided tags. Specify with this parameter the tags separated commas,
	 * for example: "<code>br,p</code>". This parameter is optional.
	 */
	public final static String PARAMETER_REMOVE_SURROUNDING_SPACES = "remove-surrounding-spaces";

	/**
	 * Initialization parameter that specifies if the Converter has to simplify
	 * existing doctype. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_SIMPLE_DOCTYPE = "simple-doctype";

	/**
	 * Initialization parameter that specifies if the Converter has to compress
	 * inline CSS. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_CSS_COMPRESS = "css-compress";

	/**
	 * Initialization parameter that specifies the column to split the lines in CSS
	 * code. Some source control tools don't like files containing lines longer
	 * than, say 8000 characters. The linebreak option is used in that case to split
	 * long lines after a specific column. It can also be used to make the code more
	 * readable, easier to debug (especially with the MS Script Debugger). Specify 0
	 * to get a line break after each rule in CSS. Specify -1 to return just one
	 * line. This parameter is optional. Default value is -1.
	 */
	public final static String PARAMETER_CSS_LINE_BREAK = "css-line-break";

	/**
	 * Initialization parameter that specifies if the Converter has to compress
	 * inline JavaScript. Accepted values for this parameter are <code>true</code>
	 * or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_JAVASCRIPT_COMPRESS = "js-compress";

	/**
	 * Initialization parameter that specifies the column to split the lines in
	 * JavaScript code. Some source control tools don't like files containing lines
	 * longer than, say 8000 characters. The linebreak option is used in that case
	 * to split long lines after a specific column. It can also be used to make the
	 * code more readable, easier to debug (especially with the MS Script Debugger).
	 * Specify 0 to get a line break after each semi-colon in JavaScript. Specify -1
	 * to return just one line. This parameter is optional. Default value is -1.
	 */
	public final static String PARAMETER_JAVASCRIPT_LINE_BREAK = "js-line-break";

	/**
	 * Initialization parameter that specifies if the Converter has to disable all
	 * the built-in micro optimizations for JavaScript code. Accepted values for
	 * this parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects).
	 * This parameter is optional. Default value is <code>false</code>.
	 */
	public final static String PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS = "js-disable-optimizations";

	/**
	 * Initialization parameter that specifies if the Converter has to obfuscate the
	 * inline JavaScript local symbols. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). Use <code>false</code> just to
	 * minify the JavaScript code. This parameter is optional. Default value is
	 * <code>false</code>.
	 */
	public final static String PARAMETER_JAVASCRIPT_OBFUSCATE = "js-obfuscate";

	/**
	 * Initialization parameter that specifies if the Converter has to preserve
	 * unnecessary semicolons (such as right before a '}') in inline JavaScript
	 * code. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>false</code>.
	 */
	public final static String PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS = "js-preserve-semicolons";

	// DEFAULT VALUES

	// Compress code in one line.
	private final static int DEFAULT_LINE_BREAK_POSITION = -1;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A <code>com.warework.service.converter.client.HTMLCompressor</code>
	 *         Client.<br>
	 *         <br>
	 */
	public Class<com.warework.service.converter.client.HtmlCompressor> getClientType() {
		return com.warework.service.converter.client.HtmlCompressor.class;
	}

	/**
	 * Gets the object that performs the HTML conversion.
	 * 
	 * @return A
	 *         <code>com.googlecode.htmlcompressor.compressor.HtmlCompressor</code>
	 *         instance.<br>
	 *         <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Create the object that performs the HTML conversion.
		final HtmlCompressor connection = new HtmlCompressor();

		// Configure PARAMETER_REMOVE_COMMENTS.
		final Object removeComments = getInitParameter(PARAMETER_REMOVE_COMMENTS);
		if (removeComments == null) {
			connection.setRemoveComments(true);
		} else if (removeComments instanceof Boolean) {
			connection.setRemoveComments((Boolean) removeComments);
		} else {
			connection.setRemoveComments(Boolean.valueOf((String) removeComments));
		}

		// Configure PARAMETER_REMOVE_MULTI_SPACES.
		final Object removeMultiSpaces = getInitParameter(PARAMETER_REMOVE_MULTI_SPACES);
		if (removeMultiSpaces == null) {
			connection.setRemoveMultiSpaces(true);
		} else if (removeMultiSpaces instanceof Boolean) {
			connection.setRemoveMultiSpaces((Boolean) removeMultiSpaces);
		} else {
			connection.setRemoveMultiSpaces(Boolean.valueOf((String) removeMultiSpaces));
		}

		// Configure PARAMETER_REMOVE_INTERTAG_SPACES.
		final Object removeIntertagSpaces = getInitParameter(PARAMETER_REMOVE_INTERTAG_SPACES);
		if (removeIntertagSpaces == null) {
			connection.setRemoveIntertagSpaces(true);
		} else if (removeIntertagSpaces instanceof Boolean) {
			connection.setRemoveIntertagSpaces((Boolean) removeIntertagSpaces);
		} else {
			connection.setRemoveIntertagSpaces(Boolean.valueOf((String) removeIntertagSpaces));
		}

		// Configure PARAMETER_REMOVE_QUOTES_SPACES.
		final Object removeQuoteSpaces = getInitParameter(PARAMETER_REMOVE_QUOTES_SPACES);
		if (removeQuoteSpaces == null) {
			connection.setRemoveQuotes(true);
		} else if (removeQuoteSpaces instanceof Boolean) {
			connection.setRemoveQuotes((Boolean) removeQuoteSpaces);
		} else {
			connection.setRemoveQuotes(Boolean.valueOf((String) removeQuoteSpaces));
		}

		// Configure PARAMETER_REMOVE_SCRIPT_ATTRIBUTES.
		final Object removeScriptAttributes = getInitParameter(PARAMETER_REMOVE_SCRIPT_ATTRIBUTES);
		if (removeScriptAttributes == null) {
			connection.setRemoveScriptAttributes(true);
		} else if (removeScriptAttributes instanceof Boolean) {
			connection.setRemoveScriptAttributes((Boolean) removeScriptAttributes);
		} else {
			connection.setRemoveScriptAttributes(Boolean.valueOf((String) removeScriptAttributes));
		}

		// Configure PARAMETER_REMOVE_STYLE_ATTRIBUTES.
		final Object removeStyleAttributes = getInitParameter(PARAMETER_REMOVE_STYLE_ATTRIBUTES);
		if (removeStyleAttributes == null) {
			connection.setRemoveStyleAttributes(true);
		} else if (removeStyleAttributes instanceof Boolean) {
			connection.setRemoveStyleAttributes((Boolean) removeStyleAttributes);
		} else {
			connection.setRemoveStyleAttributes(Boolean.valueOf((String) removeStyleAttributes));
		}

		// Configure PARAMETER_REMOVE_LINK_ATTRIBUTES.
		final Object removeLinkAttributes = getInitParameter(PARAMETER_REMOVE_LINK_ATTRIBUTES);
		if (removeLinkAttributes == null) {
			connection.setRemoveLinkAttributes(true);
		} else if (removeLinkAttributes instanceof Boolean) {
			connection.setRemoveLinkAttributes((Boolean) removeLinkAttributes);
		} else {
			connection.setRemoveLinkAttributes(Boolean.valueOf((String) removeLinkAttributes));
		}

		// Configure PARAMETER_REMOVE_FORM_ATTRIBUTES.
		final Object removeFormAttributes = getInitParameter(PARAMETER_REMOVE_FORM_ATTRIBUTES);
		if (removeFormAttributes == null) {
			connection.setRemoveFormAttributes(true);
		} else if (removeFormAttributes instanceof Boolean) {
			connection.setRemoveFormAttributes((Boolean) removeFormAttributes);
		} else {
			connection.setRemoveFormAttributes(Boolean.valueOf((String) removeFormAttributes));
		}

		// Configure PARAMETER_REMOVE_INPUT_ATTRIBUTES.
		final Object removeInputAttributes = getInitParameter(PARAMETER_REMOVE_INPUT_ATTRIBUTES);
		if (removeInputAttributes == null) {
			connection.setRemoveInputAttributes(true);
		} else if (removeInputAttributes instanceof Boolean) {
			connection.setRemoveInputAttributes((Boolean) removeInputAttributes);
		} else {
			connection.setRemoveInputAttributes(Boolean.valueOf((String) removeInputAttributes));
		}

		// Configure PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES.
		final Object removeSimpleBooleanAttributes = getInitParameter(PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES);
		if (removeSimpleBooleanAttributes == null) {
			connection.setSimpleBooleanAttributes(true);
		} else if (removeSimpleBooleanAttributes instanceof Boolean) {
			connection.setSimpleBooleanAttributes((Boolean) removeSimpleBooleanAttributes);
		} else {
			connection.setSimpleBooleanAttributes(Boolean.valueOf((String) removeSimpleBooleanAttributes));
		}

		// Configure PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL.
		final Object removeJavaScriptProtocol = getInitParameter(PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL);
		if (removeJavaScriptProtocol == null) {
			connection.setRemoveJavaScriptProtocol(true);
		} else if (removeJavaScriptProtocol instanceof Boolean) {
			connection.setRemoveJavaScriptProtocol((Boolean) removeJavaScriptProtocol);
		} else {
			connection.setRemoveJavaScriptProtocol(Boolean.valueOf((String) removeJavaScriptProtocol));
		}

		// Configure PARAMETER_REMOVE_HTTP_PROTOCOL.
		final Object removeHttpProtocol = getInitParameter(PARAMETER_REMOVE_HTTP_PROTOCOL);
		if (removeHttpProtocol == null) {
			connection.setRemoveHttpProtocol(true);
		} else if (removeHttpProtocol instanceof Boolean) {
			connection.setRemoveHttpProtocol((Boolean) removeHttpProtocol);
		} else {
			connection.setRemoveHttpProtocol(Boolean.valueOf((String) removeHttpProtocol));
		}

		// Configure PARAMETER_REMOVE_HTTPS_PROTOCOL.
		final Object removeHttpsProtocol = getInitParameter(PARAMETER_REMOVE_HTTPS_PROTOCOL);
		if (removeHttpsProtocol == null) {
			connection.setRemoveHttpsProtocol(true);
		} else if (removeHttpsProtocol instanceof Boolean) {
			connection.setRemoveHttpsProtocol((Boolean) removeHttpsProtocol);
		} else {
			connection.setRemoveHttpsProtocol(Boolean.valueOf((String) removeHttpsProtocol));
		}

		// Configure PARAMETER_PRESERVE_LINE_BREAKS.
		final Object preserveLineBreaks = getInitParameter(PARAMETER_PRESERVE_LINE_BREAKS);
		if (preserveLineBreaks == null) {
			connection.setPreserveLineBreaks(true);
		} else if (preserveLineBreaks instanceof Boolean) {
			connection.setPreserveLineBreaks((Boolean) preserveLineBreaks);
		} else {
			connection.setPreserveLineBreaks(Boolean.valueOf((String) preserveLineBreaks));
		}

		// Configure PARAMETER_SIMPLE_DOCTYPE.
		final Object simpleDoctype = getInitParameter(PARAMETER_SIMPLE_DOCTYPE);
		if (simpleDoctype == null) {
			connection.setSimpleDoctype(true);
		} else if (simpleDoctype instanceof Boolean) {
			connection.setSimpleDoctype((Boolean) simpleDoctype);
		} else {
			connection.setSimpleDoctype(Boolean.valueOf((String) simpleDoctype));
		}

		// Configure PARAMETER_CSS_COMPRESS.
		final Object compressCss = getInitParameter(PARAMETER_CSS_COMPRESS);
		if (compressCss == null) {
			connection.setCompressCss(true);
		} else if (compressCss instanceof Boolean) {
			connection.setCompressCss((Boolean) compressCss);
		} else {
			connection.setCompressCss(Boolean.valueOf((String) compressCss));
		}

		// Configure PARAMETER_JAVASCRIPT_COMPRESS.
		final Object compressJavaScript = getInitParameter(PARAMETER_JAVASCRIPT_COMPRESS);
		if (compressJavaScript == null) {
			connection.setCompressJavaScript(true);
		} else if (compressJavaScript instanceof Boolean) {
			connection.setCompressJavaScript((Boolean) compressJavaScript);
		} else {
			connection.setCompressJavaScript(Boolean.valueOf((String) compressJavaScript));
		}

		// Configure PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS.
		final Object jsDisableOptimizations = getInitParameter(PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS);
		if (jsDisableOptimizations == null) {
			connection.setYuiJsDisableOptimizations(false);
		} else if (jsDisableOptimizations instanceof Boolean) {
			connection.setYuiJsDisableOptimizations((Boolean) jsDisableOptimizations);
		} else {
			connection.setYuiJsDisableOptimizations(Boolean.valueOf((String) jsDisableOptimizations));
		}

		// Configure PARAMETER_JAVASCRIPT_OBFUSCATE.
		final Object jsObfuscate = getInitParameter(PARAMETER_JAVASCRIPT_OBFUSCATE);
		if (jsObfuscate == null) {
			connection.setYuiJsNoMunge(true);
		} else if (jsObfuscate instanceof Boolean) {
			connection.setYuiJsNoMunge(!((Boolean) jsObfuscate).booleanValue());
		} else {
			connection.setYuiJsNoMunge(!(Boolean.valueOf((String) jsObfuscate)).booleanValue());
		}

		// Configure PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS.
		final Object jsPreserveSemicolons = getInitParameter(PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS);
		if (jsPreserveSemicolons == null) {
			connection.setYuiJsPreserveAllSemiColons(false);
		} else if (jsPreserveSemicolons instanceof Boolean) {
			connection.setYuiJsPreserveAllSemiColons((Boolean) jsPreserveSemicolons);
		} else {
			connection.setYuiJsPreserveAllSemiColons(Boolean.valueOf((String) jsPreserveSemicolons));
		}

		// Configure PARAMETER_JAVASCRIPT_LINE_BREAK.
		final Object jsLineBreak = getInitParameter(PARAMETER_JAVASCRIPT_LINE_BREAK);
		if (jsLineBreak == null) {
			connection.setYuiJsLineBreak(DEFAULT_LINE_BREAK_POSITION);
		} else if (jsLineBreak instanceof Integer) {
			connection.setYuiJsLineBreak((Integer) jsLineBreak);
		} else {
			connection.setYuiJsLineBreak(Integer.valueOf((String) jsLineBreak));
		}

		// Configure PARAMETER_CSS_LINE_BREAK.
		final Object cssLineBreak = getInitParameter(PARAMETER_CSS_LINE_BREAK);
		if (cssLineBreak == null) {
			connection.setYuiCssLineBreak(DEFAULT_LINE_BREAK_POSITION);
		} else if (cssLineBreak instanceof Integer) {
			connection.setYuiCssLineBreak((Integer) cssLineBreak);
		} else {
			connection.setYuiCssLineBreak(Integer.valueOf((String) cssLineBreak));
		}

		// Configure PARAMETER_REMOVE_SURROUNDING_SPACES.
		final Object removeSurroundingSpaces = getInitParameter(PARAMETER_REMOVE_SURROUNDING_SPACES);
		if (removeSurroundingSpaces != null) {
			connection.setRemoveSurroundingSpaces((String) removeSurroundingSpaces);
		}

		// Return connection.
		return connection;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Validate the value of the PARAMETER_REMOVE_COMMENTS.
		final Object removeComments = getInitParameter(PARAMETER_REMOVE_COMMENTS);
		if (removeComments != null) {
			if (removeComments instanceof String) {
				try {
					Boolean.valueOf((String) removeComments);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_COMMENTS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeComments instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_COMMENTS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_MULTI_SPACES.
		final Object removeMultiSpaces = getInitParameter(PARAMETER_REMOVE_MULTI_SPACES);
		if (removeMultiSpaces != null) {
			if (removeMultiSpaces instanceof String) {
				try {
					Boolean.valueOf((String) removeMultiSpaces);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_MULTI_SPACES
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeMultiSpaces instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_MULTI_SPACES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_INTERTAG_SPACES.
		final Object removeIntertagSpaces = getInitParameter(PARAMETER_REMOVE_INTERTAG_SPACES);
		if (removeIntertagSpaces != null) {
			if (removeIntertagSpaces instanceof String) {
				try {
					Boolean.valueOf((String) removeIntertagSpaces);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_INTERTAG_SPACES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeIntertagSpaces instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_INTERTAG_SPACES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_QUOTES_SPACES.
		final Object removeQuoteSpaces = getInitParameter(PARAMETER_REMOVE_QUOTES_SPACES);
		if (removeQuoteSpaces != null) {
			if (removeQuoteSpaces instanceof String) {
				try {
					Boolean.valueOf((String) removeQuoteSpaces);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_QUOTES_SPACES
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeQuoteSpaces instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_QUOTES_SPACES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_SCRIPT_ATTRIBUTES.
		final Object removeScriptAttributes = getInitParameter(PARAMETER_REMOVE_SCRIPT_ATTRIBUTES);
		if (removeScriptAttributes != null) {
			if (removeScriptAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeScriptAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_SCRIPT_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeScriptAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_SCRIPT_ATTRIBUTES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_STYLE_ATTRIBUTES.
		final Object removeStyleAttributes = getInitParameter(PARAMETER_REMOVE_STYLE_ATTRIBUTES);
		if (removeStyleAttributes != null) {
			if (removeStyleAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeStyleAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_STYLE_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeStyleAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_STYLE_ATTRIBUTES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_LINK_ATTRIBUTES.
		final Object removeLinkAttributes = getInitParameter(PARAMETER_REMOVE_LINK_ATTRIBUTES);
		if (removeLinkAttributes != null) {
			if (removeLinkAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeLinkAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_LINK_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeLinkAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_LINK_ATTRIBUTES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_FORM_ATTRIBUTES.
		final Object removeFormAttributes = getInitParameter(PARAMETER_REMOVE_FORM_ATTRIBUTES);
		if (removeFormAttributes != null) {
			if (removeFormAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeFormAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_FORM_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeFormAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_FORM_ATTRIBUTES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_INPUT_ATTRIBUTES.
		final Object removeInputAttributes = getInitParameter(PARAMETER_REMOVE_INPUT_ATTRIBUTES);
		if (removeInputAttributes != null) {
			if (removeInputAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeInputAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_INPUT_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeInputAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_INPUT_ATTRIBUTES
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES.
		final Object removeSimpleBooleanAttributes = getInitParameter(PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES);
		if (removeSimpleBooleanAttributes != null) {
			if (removeSimpleBooleanAttributes instanceof String) {
				try {
					Boolean.valueOf((String) removeSimpleBooleanAttributes);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeSimpleBooleanAttributes instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '"
								+ PARAMETER_REMOVE_SIMPLE_BOOLEAN_ATTRIBUTES + "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL.
		final Object removeJavaScriptProtocol = getInitParameter(PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL);
		if (removeJavaScriptProtocol != null) {
			if (removeJavaScriptProtocol instanceof String) {
				try {
					Boolean.valueOf((String) removeJavaScriptProtocol);
				} catch (Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeJavaScriptProtocol instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '"
								+ PARAMETER_REMOVE_JAVASCRIPT_PROTOCOL + "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_HTTP_PROTOCOL.
		final Object removeHttpProtocol = getInitParameter(PARAMETER_REMOVE_HTTP_PROTOCOL);
		if (removeHttpProtocol != null) {
			if (removeHttpProtocol instanceof String) {
				try {
					Boolean.valueOf((String) removeHttpProtocol);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_HTTP_PROTOCOL
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeHttpProtocol instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_HTTP_PROTOCOL
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_HTTPS_PROTOCOL.
		final Object removeHttpsProtocol = getInitParameter(PARAMETER_REMOVE_HTTPS_PROTOCOL);
		if (removeHttpsProtocol != null) {
			if (removeHttpsProtocol instanceof String) {
				try {
					Boolean.valueOf((String) removeHttpsProtocol);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_HTTPS_PROTOCOL
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeHttpsProtocol instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_HTTPS_PROTOCOL
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_PRESERVE_LINE_BREAKS.
		final Object preserveLineBreaks = getInitParameter(PARAMETER_PRESERVE_LINE_BREAKS);
		if (preserveLineBreaks != null) {
			if (preserveLineBreaks instanceof String) {
				try {
					Boolean.valueOf((String) preserveLineBreaks);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_PRESERVE_LINE_BREAKS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(preserveLineBreaks instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_PRESERVE_LINE_BREAKS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_SIMPLE_DOCTYPE.
		final Object simpleDoctype = getInitParameter(PARAMETER_SIMPLE_DOCTYPE);
		if (simpleDoctype != null) {
			if (simpleDoctype instanceof String) {
				try {
					Boolean.valueOf((String) simpleDoctype);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_SIMPLE_DOCTYPE
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(simpleDoctype instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_SIMPLE_DOCTYPE
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_CSS_COMPRESS.
		final Object compressCss = getInitParameter(PARAMETER_CSS_COMPRESS);
		if (compressCss != null) {
			if (compressCss instanceof String) {
				try {
					Boolean.valueOf((String) compressCss);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_CSS_COMPRESS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(compressCss instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_CSS_COMPRESS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_JAVASCRIPT_COMPRESS.
		final Object compressJavaScript = getInitParameter(PARAMETER_JAVASCRIPT_COMPRESS);
		if (compressJavaScript != null) {
			if (compressJavaScript instanceof String) {
				try {
					Boolean.valueOf((String) compressJavaScript);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_COMPRESS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(compressJavaScript instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_COMPRESS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS.
		final Object jsDisableOptimizations = getInitParameter(PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS);
		if (jsDisableOptimizations != null) {
			if (jsDisableOptimizations instanceof String) {
				try {
					Boolean.valueOf((String) jsDisableOptimizations);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(jsDisableOptimizations instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '"
								+ PARAMETER_JAVASCRIPT_DISABLE_OPTIMIZATIONS + "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_JAVASCRIPT_OBFUSCATE.
		final Object jsObfuscate = getInitParameter(PARAMETER_JAVASCRIPT_OBFUSCATE);
		if (jsObfuscate != null) {
			if (jsObfuscate instanceof String) {
				try {
					Boolean.valueOf((String) jsObfuscate);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_OBFUSCATE
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(jsObfuscate instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_OBFUSCATE
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS.
		final Object jsPreserveSemicolons = getInitParameter(PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS);
		if (jsPreserveSemicolons != null) {
			if (jsPreserveSemicolons instanceof String) {
				try {
					Boolean.valueOf((String) jsPreserveSemicolons);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(jsPreserveSemicolons instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '"
								+ PARAMETER_JAVASCRIPT_PRESERVE_SEMICOLONS + "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_JAVASCRIPT_LINE_BREAK.
		final Object jsLineBreak = getInitParameter(PARAMETER_JAVASCRIPT_LINE_BREAK);
		if (jsLineBreak != null) {
			if (jsLineBreak instanceof String) {
				try {
					Integer.valueOf((String) jsLineBreak);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_LINE_BREAK
									+ "' is not a valid integer value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(jsLineBreak instanceof Integer)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_JAVASCRIPT_LINE_BREAK
								+ "' is not a string or an integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_CSS_LINE_BREAK.
		final Object cssLineBreak = getInitParameter(PARAMETER_CSS_LINE_BREAK);
		if (cssLineBreak != null) {
			if (cssLineBreak instanceof String) {
				try {
					Integer.valueOf((String) cssLineBreak);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_CSS_LINE_BREAK
									+ "' is not a valid integer value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(cssLineBreak instanceof Integer)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_CSS_LINE_BREAK
								+ "' is not a string or an integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_SURROUNDING_SPACES.
		final Object removeSurroundingSpaces = getInitParameter(PARAMETER_REMOVE_SURROUNDING_SPACES);
		if ((removeSurroundingSpaces != null) && !(removeSurroundingSpaces instanceof String)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_SURROUNDING_SPACES
							+ "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);

		}

	}

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>null</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
