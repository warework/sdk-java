package com.warework.core.util.helper.beans;

public class Sample4Bean {

	private String title;

	private static String notBeanAttribute = "over the...";

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotBeanAttribute() {
		return notBeanAttribute;
	}

}
