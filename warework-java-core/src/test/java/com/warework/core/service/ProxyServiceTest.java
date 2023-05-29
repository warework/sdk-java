package com.warework.core.service;

import java.util.Enumeration;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.ConsoleLogger;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ProxyServiceTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to create a client.
	 */
	public void testCreateClient1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", ConsoleConnector.class, null);

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that null connector type is not allowed.
	 */
	public void testCreateClient3() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", null, null);

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	/**
	 * Tests that invalid client name is not allowed.
	 */
	public void testCreateClient4() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient(null, ConsoleConnector.class, null);

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	/**
	 * Tests that invalid client name is not allowed.
	 */
	public void testCreateClient5() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("", ConsoleConnector.class, null);

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	/**
	 * Tries to bind the same client twice.
	 */
	public void testCreateClient6() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", ConsoleConnector.class, null);
			log.createClient("logger", ConsoleConnector.class, null);

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to list client names.
	 */
	public void testGetClientNames1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);
			log.createClient("logger2", ConsoleConnector.class, null);
			log.createClient("logger3", ConsoleConnector.class, null);

			//
			final Enumeration<String> names = log.getClientNames();
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("logger1")) && (!name.equals("logger2")) && (!name.equals("logger3"))) {
					fail();
				}

			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Test that when no client exists service returns null for the clients names..
	 */
	public void testGetClientNames2() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			final Enumeration<String> names = log.getClientNames();

			//
			if (names != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to list client names.
	 */
	public void testExistsClient1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);
			log.createClient("logger2", ConsoleConnector.class, null);
			log.createClient("logger3", ConsoleConnector.class, null);

			//
			if ((!log.existsClient("logger1")) || (!log.existsClient("logger2")) || (!log.existsClient("logger3"))) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to remove clients.
	 */
	public void testRemoveClient1() {

		// Create the configuration of the system.
		Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);
			log.createClient("logger2", ConsoleConnector.class, null);
			log.createClient("logger3", ConsoleConnector.class, null);

			//
			log.removeClient("logger2");

			//
			if (log.existsClient("logger2")) {
				fail();
			}

			//
			final Enumeration<String> names = log.getClientNames();
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("logger1")) && (!name.equals("logger3"))) {
					fail();
				}

			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Test to that connection is closed when a client is removed.
	 */
	public void testRemoveClient2() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.connect("logger1");

			//
			log.removeClient("logger1");

			//
			if (log.getConnection("logger1") != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to remove clients.
	 */
	public void testRemoveAll1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);
			log.createClient("logger2", ConsoleConnector.class, null);
			log.createClient("logger3", ConsoleConnector.class, null);

			//
			log.removeAllClients();

			//
			final Enumeration<String> names = log.getClientNames();
			if (names != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests the type of a client.
	 */
	public void testGetClientType1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", ConsoleConnector.class, null);

			//
			if (log.getClientType("logger") == null) {
				fail();
			}
			if (!log.getClientType("logger").equals(ConsoleLogger.class)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that the type of a client is null when client not exists.
	 */
	public void testGetClientType2() {

		// Create the configuration of the system.
		Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", ConsoleConnector.class, null);

			//
			if (log.getClientType("x") != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests the type of a client.
	 */
	public void testIsClientType1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger", ConsoleConnector.class, null);

			//
			if (!log.isClientType("logger", ConsoleLogger.class)) {
				fail();
			}
			if (log.isClientType("x", ConsoleLogger.class)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test that clients are not connected by default.
	 */
	public void testGetConnection1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			if (log.getConnection("logger1") != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Test that service returns a connection when client is connected.
	 */
	public void testGetConnection2() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.connect("logger1");

			//
			if (log.getConnection("logger1") == null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to retrieve a connection.
	 */
	public void testConnect1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.connect("logger1");

			//
			if (log.getConnection("logger1") == null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that reconnections are not allowed.
	 */
	public void testConnect2() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.connect("logger1");
			log.connect("logger1");

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that disconnect does not remove the client.
	 */
	public void testDisconnect1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.connect("logger1");
			log.disconnect("logger1");

			//
			if (!log.existsClient("logger1")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that disconnect a disconnected client performs nothing.
	 */
	public void testDisconnect2() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			log.disconnect("logger1");

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Test to validate isConnected.
	 */
	public void testIsConnected1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		try {

			//
			final ScopeFacade system = create(config);

			//
			final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

			//
			log.createClient("logger1", ConsoleConnector.class, null);

			//
			if (log.isConnected("logger1")) {
				fail();
			}

			//
			log.connect("logger1");
			if (!log.isConnected("logger1")) {
				fail();
			}

			//
			log.disconnect("logger1");
			if (log.isConnected("logger1")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

}
