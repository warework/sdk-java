package com.warework.core.scope;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.model.Scope;
import com.warework.service.mail.MailServiceConstants;
import com.warework.service.mail.MailServiceImpl;
import com.warework.service.mail.client.connector.JavaMailSenderConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractSerMailJavaMailTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-ser-mail-javamail";

	//

	protected static final String CLIENT_NAME_1 = "javamail-client-1";

	protected static final String CLIENT_NAME_2 = "javamail-client-2";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade create(final String name) throws ScopeException {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		//
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, AbstractSerMailJavaMailTestCase.class);
		
		//
		config.setService(MailServiceConstants.DEFAULT_SERVICE_NAME, MailServiceImpl.class, null, null);

		//
		final Map<String, Object> params1 = new HashMap<String, Object>();
		{
			params1.put("mail.host", "hostname");
			params1.put("mail.port", "25");
			params1.put("mail.transport.protocol", "smtp");
			params1.put("mail.user", "username");
			params1.put("mail.password", "password");
			params1.put("mail.smtp.auth", "true");
		}

		//
		config.setClient(MailServiceConstants.DEFAULT_SERVICE_NAME, CLIENT_NAME_1, JavaMailSenderConnector.class,
				params1);

		//
		final Map<String, Object> params2 = new HashMap<String, Object>();
		{
			params1.put("mail.host", "hostname");
			params2.put("mail.port", "25");
			params2.put("mail.transport.protocol", "smtp");
			params1.put("mail.user", "username");
			params1.put("mail.password", "password");
			params2.put("mail.smtp.auth", "true");
			params2.put("mail.message.charset", "utf-8");
			params2.put("mail.message.subtype", "html");
		}

		//
		config.setClient(MailServiceConstants.DEFAULT_SERVICE_NAME, CLIENT_NAME_2, JavaMailSenderConnector.class,
				params2);

		// Create new Scope.
		return create(config);

	}

}
