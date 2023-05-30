package com.warework.core.scope;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warework.core.model.Scope;
import com.warework.core.service.ServiceException;
import com.warework.core.service.UnsupportedOperationServiceException;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.file.FileRef;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.FileServiceFacade;
import com.warework.service.file.FileServiceImpl;
import com.warework.service.file.client.AbstractFileClient;
import com.warework.service.log.LogServiceConstants;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractSerFileTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	private static final String PROJECT_NAME = "warework-java-ser-file";

	//
	protected static final String CLIENT_NAME = FileServiceConstants.DEFAULT_CLIENT_NAME;

	//

	protected static final String FILE_BINARY_1 = "sample-binary-1.png";

	protected static final String FILE_TEXT_1 = "sample-text-1.txt";

	//

	protected static final String DIR_BASE = "/test-resources";

	protected static final String DIR_EMPTY = "/empty";

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected abstract Class<? extends ConnectorFacade> getClientConnectorType();

	/**
	 * 
	 * @return
	 */
	protected abstract Map<String, Object> getClientParameters();

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Cleans up file client.
	 */
	public void tearDown() {
		if (cleanUp()) {
			try {

				// Create Scope.
				final ScopeFacade system = get(SCOPE_NAME);

				// Clean up resources only when Scope was created.
				if (system != null) {

					// Log test message.
					system.info("····· T E S T: Cleaning up resources...");

					// Get the File Service.
					final FileServiceFacade service = (FileServiceFacade) system
							.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

					// Connect with File Client.
					if (!service.isConnected(CLIENT_NAME)) {
						service.connect(CLIENT_NAME);
					}

					// Configure list operation.
					final Map<String, Object> options = new HashMap<String, Object>();
					{
						options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
						options.put(AbstractFileClient.OPERATION_OPTION_REVERSE_TREE, Boolean.TRUE);
					}

					// Get resources to remove.
					final List<FileRef> resources = service.list(CLIENT_NAME, ResourceL1Helper.DIRECTORY_SEPARATOR,
							options, null);

					// Delete all files and directories.
					service.delete(CLIENT_NAME, resources, options);

					// Disconnect from File Client
					service.disconnect(CLIENT_NAME);

					// Log test message.
					system.info("····· T E S T: Resources removed successfully");

				}

			} catch (final Exception e) {
				fail();
			} finally {
				super.tearDown();
			}
		} else {
			super.tearDown();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnectDisconnect1() {
		try {

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testConnectDisconnect1");
			system.info("·······················································");

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Disconnect from File Client
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testReadTextFile1() {
		try {

			// Text file.
			final String file = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1;

			// Create Scope.
			final ScopeFacade system = create("test");

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testReadTextFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with FTP server.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */

			// Stream to store text file loaded form server.
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Read text file from server.
			final boolean success = service.read(CLIENT_NAME, updatePath(file), null, os);

			// Close output stream.
			os.close();

			/*
			 * VALIDATE
			 */
			if ((!success) || (!getResourceText1String().equals(new String(os.toByteArray())))) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testReadTextFile2() {
		try {

			// Text file.
			final String file = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1;

			// Create Scope.
			final ScopeFacade system = create("test");

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testReadTextFile2");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with FTP server.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */
			final String text = service.readAsText(CLIENT_NAME, updatePath(file), null);

			/*
			 * VALIDATE
			 */
			if ((text == null) || (!getResourceText1String().equals(text))) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testReadBinaryFile1() {
		try {

			// Text file.
			final String file = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1;

			// Create Scope.
			final ScopeFacade system = create("test");

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testReadBinaryFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with FTP server.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */

			// Stream to store text file loaded form server.
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Read text file from server.
			final boolean success = service.read(CLIENT_NAME, updatePath(file), null, os);

			// Close output stream.
			os.close();

			/*
			 * VALIDATE
			 */
			if (success) {

				// Get data loaded from File Client.
				final byte[] data = os.toByteArray();

				// Get source data.
				final byte[] image = getResourceBinary1Bytes();

				// Compare that both binary files have the same content.
				if (data.length == image.length) {
					for (int i = 0; i < image.length; i++) {
						if (data[i] != image[i]) {
							fail();
						}
					}
				} else {
					fail();
				}

			} else {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testWriteTextFile1() {
		try {

			// Text file.
			final String file = "/DELETE-ME.txt";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testWriteTextFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			/*
			 * EXECUTE TEST
			 */

			// Text file to write in the File Client.
			final String text = StringL1Helper.createSimpleID();

			// Create input stream.
			final InputStream is = new ByteArrayInputStream(text.getBytes());

			// Write file in target File Client.
			boolean success = service.write(CLIENT_NAME, is, text.getBytes().length, null, updatePath(file));

			// Close input stream.
			is.close();

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testWriteTextFile2() {
		try {

			// Text file.
			final String file = "/DELETE-ME.txt";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testWriteTextFile2");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			/*
			 * EXECUTE TEST
			 */

			// Text file to write in the File Client.
			final String text = StringL1Helper.createSimpleID();

			// Write file in target File Client.
			boolean success = service.write(CLIENT_NAME, text, null, updatePath(file));

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testWriteTextFile3() {
		try {

			// Text file.
			final String file = ResourceL1Helper.DIRECTORY_SEPARATOR + System.currentTimeMillis() + "/DELETE-ME.txt";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testWriteTextFile3");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			/*
			 * EXECUTE TEST
			 */

			// Text file to write in the File Client.
			final String text = StringL1Helper.createSimpleID();

			// Configure operation to create all directories specified in the path.
			final Map<String, Object> options = new HashMap<String, Object>();
			{
				options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
			}

			// Write file in target File Client.
			boolean success = service.write(CLIENT_NAME, text, options, updatePath(file));

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testWriteBinaryFile1() {
		try {

			// Text file.
			final String file = "/DELETE-ME.png";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testWriteBinaryFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			/*
			 * SETUP TEST
			 */

			// Get binary file.
			final byte[] image = getResourceBinary1Bytes();

			// Create input stream.
			final InputStream is = new ByteArrayInputStream(image);

			// Write file in target File Client.
			boolean success = service.write(CLIENT_NAME, is, image.length, null, updatePath(file));

			// Close input stream.
			is.close();

			// Validate operation.
			if (!success) {
				fail();
			}

			/*
			 * EXECUTE TEST
			 */

			// Stream to store binary file loaded form server.
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Read binary file from server.
			success = service.read(CLIENT_NAME, updatePath(file), null, os);

			// Close output stream.
			os.close();

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testWriteBinaryFile2() {
		try {

			// Text file.
			final String file = ResourceL1Helper.DIRECTORY_SEPARATOR + System.currentTimeMillis() + "/DELETE-ME.png";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testWriteBinaryFile2");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			/*
			 * SETUP TEST
			 */

			// Get binary file.
			final byte[] image = getResourceBinary1Bytes();

			// Create input stream.
			final InputStream is = new ByteArrayInputStream(image);

			// Configure operation to create all directories specified in the path.
			final Map<String, Object> options = new HashMap<String, Object>();
			{
				options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
			}

			// Write file in target File Client.
			boolean success = service.write(CLIENT_NAME, is, image.length, options, updatePath(file));

			// Close input stream.
			is.close();

			// Validate operation.
			if (!success) {
				fail();
			}

			/*
			 * EXECUTE TEST
			 */

			// Stream to store binary file loaded form server.
			final ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Read binary file from server.
			success = service.read(CLIENT_NAME, updatePath(file), null, os);

			// Close output stream.
			os.close();

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testMakeDir1() {
		try {

			// Directory to create and remove.
			final String dir = ResourceL1Helper.DIRECTORY_SEPARATOR + System.currentTimeMillis();

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testMakeDir1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Execute only if operation is supported.
			if (service.allowsDirectories(CLIENT_NAME)) {

				/*
				 * EXECUTE TEST
				 */
				if (!service.makeDir(CLIENT_NAME, updatePath(dir), null)) {
					fail();
				}

				/*
				 * VALIDATE
				 */

				// Validate directory exists.
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir), null);

				// Validate and clean up.
				if ((remoteDir == null) || (!remoteDir.isDirectory())) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testMakeDir2() {
		try {

			// Directory to create and remove.
			final String dir = ResourceL1Helper.DIRECTORY_SEPARATOR + System.currentTimeMillis()
					+ ResourceL1Helper.DIRECTORY_SEPARATOR + System.currentTimeMillis();

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testMakeDir2");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Execute only if operation is supported.
			if (service.allowsDirectories(CLIENT_NAME)) {

				// Configure operation to create all directories specified in the path.
				final Map<String, Object> options = new HashMap<String, Object>();
				{
					options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
				}

				/*
				 * EXECUTE TEST
				 */
				if (!service.makeDir(CLIENT_NAME, updatePath(dir), options)) {
					fail();
				}

				/*
				 * VALIDATE
				 */

				// Validate directory exists.
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir), null);

				// Validate and clean up.
				if ((remoteDir == null) || (!remoteDir.isDirectory())) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testDeleteFile1() {
		try {

			// File to delete.
			final String file = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1;

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testDeleteFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */
			if (!service.delete(CLIENT_NAME, updatePath(file), null)) {
				fail();
			}

			/*
			 * VALIDATE
			 */
			final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(file), null);
			if (remoteDir != null) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testDeleteDirectory1() {
		try {

			// Directory to remove.
			final String dir = DIR_BASE + DIR_EMPTY;

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testDeleteDirectory1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			// Execute only if operation is supported.
			if (service.allowsDirectories(CLIENT_NAME)) {

				/*
				 * EXECUTE TEST
				 */
				if (!service.delete(CLIENT_NAME, updatePath(dir), null)) {
					fail();
				}

				/*
				 * VALIDATE
				 */
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir), null);
				if (remoteDir != null) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testDeleteRecursively1() {
		try {

			// Directory to delete.
			final String dir = DIR_BASE;

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testDeleteRecursively1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */

			// Configure delete operation.
			final Map<String, Object> options = new HashMap<String, Object>();
			{
				options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
			}

			// Delete all files.
			if (!service.delete(CLIENT_NAME, updatePath(dir), options)) {
				fail();
			}

			/*
			 * VALIDATE
			 */
			final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir), null);
			if (remoteDir != null) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testRenameDir1() {
		try {

			// Directory name 1.
			final String dir1 = DIR_BASE + DIR_EMPTY;

			// Directory name 2.
			final String dir2 = DIR_BASE + "/updated";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testRenameDir1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			// Execute only if operation is supported.
			if (service.allowsDirectories(CLIENT_NAME)) {

				/*
				 * EXECUTE TEST
				 */
				if (!service.rename(CLIENT_NAME, updatePath(dir1), updatePath(dir2))) {
					fail();
				}

				/*
				 * VALIDATE
				 */
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir2), null);
				if ((remoteDir == null) || (!remoteDir.isDirectory())) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testRenameFile1() {
		try {

			// File name 1.
			final String file1 = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1;

			// File name 2.
			final String file2 = DIR_BASE + "/renamed-file.png";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testRenameFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */
			if (!service.rename(CLIENT_NAME, updatePath(file1), updatePath(file2))) {
				fail();
			}

			/*
			 * VALIDATE
			 */
			final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(file2), null);
			if ((remoteDir == null) || (remoteDir.isDirectory())) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testCopyFile1() {
		try {

			// File name 1.
			final String file1 = DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1;

			// File name 2.
			final String file2 = DIR_BASE + "/copied-file.png";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testCopyFile1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */
			final int[] response = service.copy(CLIENT_NAME, updatePath(file1), null, CLIENT_NAME, updatePath(file2),
					null);

			/*
			 * VALIDATE
			 */

			// Validate resources copied.
			if (response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED]) {
				fail();
			} else if (response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED]) {

				// Find copied file.
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(file2), null);

				// Validate copied file exists.
				if ((remoteDir == null) || (remoteDir.isDirectory())) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testCopyDirectory1() {
		try {

			// Directory name 1.
			final String dir1 = DIR_BASE + DIR_EMPTY;

			// Directory name 2.
			final String dir2 = DIR_BASE + "/copied";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testCopyDirectory1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */
			final int[] response = service.copy(CLIENT_NAME, updatePath(dir1), null, CLIENT_NAME, updatePath(dir2),
					null);

			/*
			 * VALIDATE
			 */

			// Validate resources copied.
			if (response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED]) {
				fail();
			} else if (response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED] > 0) {

				// Find copied directory.
				final FileRef remoteDir = service.find(CLIENT_NAME, updatePath(dir2), null);

				// Validate copied directory exists.
				if ((remoteDir == null) || (!remoteDir.isDirectory())) {
					fail();
				}

			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

	/**
	 * 
	 */
	public void testCopyRecursively1() {
		try {

			// Source directory.
			final String dir1 = DIR_BASE;

			// Target directory.
			final String dir2 = "/DELETE-ME";

			// Create Scope.
			final ScopeFacade system = create(SCOPE_NAME);

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testCopyRecursively1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with File Client.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */

			// Configure delete operation.
			final Map<String, Object> options = new HashMap<String, Object>();
			{
				options.put(AbstractFileClient.OPERATION_OPTION_PROCESS_RECURSIVELY, Boolean.TRUE);
			}

			// Copy directory recursively in File Client.
			final int[] response = service.copy(CLIENT_NAME, updatePath(dir1), options, CLIENT_NAME, updatePath(dir2),
					null);

			/*
			 * VALIDATE
			 */
			if (service.allowsDirectories(CLIENT_NAME)) {
				if ((response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED])
						|| (response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_DIRECTORIES_COPIED])) {
					fail();
				}
			} else if (response[FileServiceConstants.RESPONSE_INDEX_FILES_FOUND] != response[FileServiceConstants.RESPONSE_INDEX_FILES_COPIED]) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}
	
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExistsDir1() {
		try {

			// Text file.
			final String dir = DIR_BASE + "/_r";

			// Create Scope.
			final ScopeFacade system = create("test");

			// Log test unit.
			system.info("·······················································");
			system.info("T E S T: testExistsDir1");
			system.info("·······················································");

			/*
			 * SETUP TEST
			 */

			// Get the File Service.
			final FileServiceFacade service = (FileServiceFacade) system
					.getService(FileServiceConstants.DEFAULT_SERVICE_NAME);

			// Connect with FTP server.
			service.connect(CLIENT_NAME);

			// Initialize workspace.
			setup(service);

			/*
			 * EXECUTE TEST
			 */

			// Read text file from server.
			final boolean success = service.existsDir(CLIENT_NAME, updatePath(dir), null);

			/*
			 * VALIDATE
			 */
			if (!success) {
				fail();
			}

			/*
			 * FINALIZE
			 */

			// Shut down service.
			service.disconnect(CLIENT_NAME);

			// Log test message.
			system.info("····· T E S T: SUCCESSFULLY FINALIZED");

		} catch (final UnsupportedOperationServiceException e) {
			get(SCOPE_NAME).info("····· T E S T: UNSUPPORTED OPERATION");
		} catch (final Exception e) {

			// Log error message.
			if (get(SCOPE_NAME) != null) {
				get(SCOPE_NAME).log("····· T E S T: CANNOT EXECUTE TEST AS EXCEPTION '" + e.getClass().getName()
						+ "' WAS THROWN (" + e.getMessage() + ")", LogServiceConstants.LOG_LEVEL_FATAL);
			}

			// Stop test execution.
			fail();

		}
	}

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
	 */
	protected ScopeFacade create(final String name) {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		// Enable default log.
		config.enableDefaultLog();

		// Setup the parameters.
		config.setService(FileServiceConstants.DEFAULT_SERVICE_NAME, FileServiceImpl.class, null, null);

		// Setup File Client.
		config.setClient(FileServiceConstants.DEFAULT_SERVICE_NAME, CLIENT_NAME, getClientConnectorType(),
				getClientParameters());

		// Create new Scope.
		try {
			return create(config);
		} catch (final ScopeException e) {

			// Log error message.
			System.out.println("##### CANNOT CREATE TEST SCOPE !!!");

			// Stop test execution.
			fail();

			// Nothing to return.
			return null;

		}

	}

	/**
	 * Sets up the test before it's executed.
	 * 
	 * @param service File Service.<br>
	 *                <br>
	 * @throws ServiceException If there is an error when trying to setup the
	 *                          test.<br>
	 *                          <br>
	 * @throws IOException      If there is an error with an I/O operation.<br>
	 *                          <br>
	 */
	protected void setup(final FileServiceFacade service) throws ServiceException, IOException {

		// Log test message.
		service.getScopeFacade().info("····· T E S T: Setting up test resources");

		// Get binary file.
		final byte[] imageBytes = getResourceBinary1Bytes();
		service.getScopeFacade().debug("Binary file bytes loaded");

		// Get text file.
		final byte[] textBytes = getResourceText1Bytes();
		service.getScopeFacade().debug("Text file bytes loaded");

		// Directory tree to create.
		final String[] directories = new String[] { "/_r", "/a", "/b", "/c", "/b/a", "/b/b", "/b/c" };

		// Flag to validate if directories are allowed.
		final boolean allowDirectories = service.allowsDirectories(CLIENT_NAME);

		// Create root directory.
		if (allowDirectories) {
			if (service.makeDir(CLIENT_NAME, updatePath(DIR_BASE), null)) {
				service.getScopeFacade().debug("Root directory created");
			} else {

				// Log message.
				service.getScopeFacade().log("····· E R R O R: CANNOT CREATE TEST ROOT DIRECTORY",
						LogServiceConstants.LOG_LEVEL_FATAL);

				// Stop test execution.
				fail();

			}
		}

		// Create directory tree.
		if (allowDirectories) {

			// Create each directory.
			for (int i = 0; i < directories.length; i++) {
				if (!service.makeDir(CLIENT_NAME, updatePath(DIR_BASE + directories[i]), null)) {

					// Log message.
					service.getScopeFacade().log("····· E R R O R: CANNOT CREATE TEST TREE DIRECTORY",
							LogServiceConstants.LOG_LEVEL_FATAL);

					// Stop test execution.
					fail();

				}
			}

			// Log operation.
			service.getScopeFacade().debug("Directory tree successfully created");

		}

		// Add empty directory to tree.
		if (allowDirectories) {
			if (service.makeDir(CLIENT_NAME, updatePath(DIR_BASE + DIR_EMPTY), null)) {
				service.getScopeFacade().debug("Directory '" + DIR_EMPTY + "' created");
			} else {

				// Log message.
				service.getScopeFacade().log("····· E R R O R: CANNOT CREATE DIRECTORY '" + DIR_EMPTY + "'",
						LogServiceConstants.LOG_LEVEL_FATAL);

				// Stop test execution.
				fail();

			}
		}

		// Create files.
		boolean success = false;
		for (int i = 0; i < directories.length; i++) {

			// Write binary file in target File Client.
			final InputStream imageStream = new ByteArrayInputStream(imageBytes);
			success = service.write(CLIENT_NAME, imageStream, imageBytes.length, null,
					updatePath(DIR_BASE + directories[i] + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1));
			imageStream.close();
			if (!success) {

				// Log message.
				service.getScopeFacade()
						.log("····· E R R O R: CANNOT CREATE TEST FILE '" + updatePath(
								DIR_BASE + directories[i] + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1) + "'",
								LogServiceConstants.LOG_LEVEL_FATAL);

				// Stop test execution.
				fail();

			}

			// Write text file in target File Client.
			final InputStream textStream = new ByteArrayInputStream(textBytes);
			success = service.write(CLIENT_NAME, textStream, textBytes.length, null,
					updatePath(DIR_BASE + directories[i] + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1));
			textStream.close();
			if (!success) {

				// Log message.
				service.getScopeFacade()
						.log("····· E R R O R: CANNOT CREATE TEST FILE '"
								+ updatePath(
										DIR_BASE + directories[i] + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1)
								+ "'", LogServiceConstants.LOG_LEVEL_FATAL);

				// Stop test execution.
				fail();

			}

		}

		// Write binary file in target File Client.
		final InputStream imageStream = new ByteArrayInputStream(imageBytes);
		success = service.write(CLIENT_NAME, imageStream, imageBytes.length, null,
				updatePath(DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1));
		imageStream.close();
		if (!success) {

			// Log message.
			service.getScopeFacade()
					.log("····· E R R O R: CANNOT CREATE TEST FILE '"
							+ updatePath(DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_BINARY_1) + "'",
							LogServiceConstants.LOG_LEVEL_FATAL);

			// Stop test execution.
			fail();

		}

		// Write text file in target File Client.
		final InputStream textStream = new ByteArrayInputStream(textBytes);
		success = service.write(CLIENT_NAME, textStream, textBytes.length, null,
				updatePath(DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1));
		textStream.close();
		if (!success) {

			// Log message.
			service.getScopeFacade()
					.log("····· E R R O R: CANNOT CREATE TEST FILE '"
							+ updatePath(DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR + FILE_TEXT_1) + "'",
							LogServiceConstants.LOG_LEVEL_FATAL);

			// Stop test execution.
			fail();

		}

	}

	/**
	 * Remove files and directories created in the test.
	 * 
	 * @return Always <code>true</code>.<br>
	 *         <br>
	 */
	protected boolean cleanUp() {
		return true;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected final byte[] getResourceText1Bytes() {
		return getResourceText1String().getBytes();
	}

	/**
	 * 
	 * @return
	 */
	protected final String getResourceText1String() {
		try {
			return StringL1Helper.toString(getResourceStream(FILE_TEXT_1));
		} catch (final IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	protected final byte[] getResourceBinary1Bytes() {
		try {
			return toBytes(getResourceStream(FILE_BINARY_1));
		} catch (final IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	protected final String getResourcePath(final String resource) {
		return ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
				+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + DIR_BASE + ResourceL1Helper.DIRECTORY_SEPARATOR
				+ resource;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param path
	 * @return
	 */
	private String updatePath(final String path) {
		return path;
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	private InputStream getResourceStream(final String resource) {

		// Get resource path.
		final String path = getResourcePath(resource);

		// Get resource as a stream.
		final InputStream is = AbstractSerFileTestCase.class.getResourceAsStream(path);

		// Validate resource.
		if (is == null) {
			get(SCOPE_NAME).debug("Cannot find resource '" + path + "'");
		}

		// Return resource stream.
		return is;

	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private byte[] toBytes(final InputStream input) throws IOException {

		//
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();

		//
		final byte[] buffer = new byte[16384];

		int read;
		while ((read = input.read(buffer, 0, buffer.length)) != -1) {
			stream.write(buffer, 0, read);
		}

		//
		return stream.toByteArray();

	}

}
