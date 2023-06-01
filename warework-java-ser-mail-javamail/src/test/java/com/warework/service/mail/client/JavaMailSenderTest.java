package com.warework.service.mail.client;

import com.warework.core.scope.AbstractSerMailJavaMailTestCase;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class JavaMailSenderTest extends AbstractSerMailJavaMailTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSendDELETEME() {

	}

	/*
	public void testSend1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final MailServiceFacade service = (MailServiceFacade) system
					.getService(MailServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_NAME_1);

			//
			service.send(CLIENT_NAME_1, "Hello without multipart: plain text message without charset specified",
					"sample1@warework.com", "sample2@warework.com;dev-test@warework.com", null, null, null,
					"this is the body of the message");

			//
			service.disconnect(CLIENT_NAME_1);

		} catch (final Exception e) {
			fail();
		}
	}

	public void testSend2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final MailServiceFacade service = (MailServiceFacade) system
					.getService(MailServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_NAME_2);

			//
			service.send(CLIENT_NAME_2, "Hello without multipart plus utf-8 and html message", "sample1@warework.com",
					"sample2@warework.com;dev-test@warework.com", null, null, null,
					"this is the <b>body of the message</b>");

			//
			service.disconnect(CLIENT_NAME_2);

		} catch (final Exception e) {
			fail();
		}
	}

	public void testSend3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final MailServiceFacade service = (MailServiceFacade) system
					.getService(MailServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_NAME_2);

			// Create a list for the attachments.
			List<BodyPart> attachments = new ArrayList<BodyPart>();

			//
			{

				// Create an attachment.
				BodyPart bodyPart = new MimeBodyPart();

				// Set the file to include in the email.
				bodyPart.setDataHandler(new DataHandler(JavaMailSender.class
						.getResource(StringL1Helper.CHARACTER_FORWARD_SLASH + ResourceL1Helper.DIRECTORY_META_INF
								+ StringL1Helper.CHARACTER_FORWARD_SLASH + PROJECT_NAME + "/image.png")));

				// Set the name for the file.
				bodyPart.setFileName("logo-warework.png");

				// Add the attachment.
				attachments.add(bodyPart);

			}

			//
			{

				// Create an attachment.
				BodyPart bodyPart = new MimeBodyPart();

				// Set the file to include in the email.
				bodyPart.setDataHandler(new DataHandler(JavaMailSender.class
						.getResource(StringL1Helper.CHARACTER_FORWARD_SLASH + ResourceL1Helper.DIRECTORY_META_INF
								+ StringL1Helper.CHARACTER_FORWARD_SLASH + PROJECT_NAME + "/text-file.txt")));

				// Set the name for the file.
				bodyPart.setFileName("new-name.txt");

				// Add the attachment.
				attachments.add(bodyPart);

			}

			//
			service.send(CLIENT_NAME_2, "Hello with multipart", "sample1@warework.com", "sample1@warework.com", null,
					null, attachments, "this message should have an <b>attachment</b>");

			//
			service.disconnect(CLIENT_NAME_2);

		} catch (final Exception e) {
			fail();
		}
	}

	public void testSend4() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final MailServiceFacade service = (MailServiceFacade) system
					.getService(MailServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_NAME_1);

			//
			service.send(CLIENT_NAME_1, "Hello without multipart: with CC", "sample1@warework.com",
					"sample1@warework.com", "dev-test@warework.com", null, null, "this is the body of the message");

			//
			service.disconnect(CLIENT_NAME_1);

		} catch (final Exception e) {
			fail();
		}
	}

	public void testSend5() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final MailServiceFacade service = (MailServiceFacade) system
					.getService(MailServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_NAME_1);

			//
			service.send(CLIENT_NAME_1, "Hello without multipart: with BCC", "sample1@warework.com",
					"sample1@warework.com", null, "dev-test@warework.com", null, "this is the body of the message");

			//
			service.disconnect(CLIENT_NAME_1);

		} catch (final Exception e) {
			fail();
		}
	}
	*/

}
