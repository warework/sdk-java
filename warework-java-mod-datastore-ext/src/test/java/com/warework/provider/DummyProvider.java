package com.warework.provider;

import java.util.Enumeration;

import com.warework.core.provider.AbstractProvider;

public class DummyProvider extends AbstractProvider {

	public static final String DEFAULT_PROVIDER_NAME = "dummy-provider";

	protected void initialize() {

	}

	protected void connect() {

	}

	protected void disconnect() {

	}

	protected boolean isClosed() {
		return false;
	}

	protected Object getObject(String name) {
		if (name.equals("user.name")) {
			return "Matthew";
		} else {
			return null;
		}
	}

	protected Enumeration<String> getObjectNames() {
		return null;
	}

}
