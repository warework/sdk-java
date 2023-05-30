package com.warework.core.util.helper.beans;

import java.util.List;
import java.util.Map;

public class Sample2Bean {

	private String title;

	private Sample1Bean[] sampleArray;

	private List<Sample1Bean> sampleList;

	private Map<String, Sample1Bean> sampleMap;

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

	/**
	 * @return the sampleArray
	 */
	public Sample1Bean[] getSampleArray() {
		return sampleArray;
	}

	/**
	 * @param sampleArray
	 *            the sampleArray to set
	 */
	public void setSampleArray(Sample1Bean[] sampleArray) {
		this.sampleArray = sampleArray;
	}

	/**
	 * @return the sampleList
	 */
	public List<Sample1Bean> getSampleList() {
		return sampleList;
	}

	/**
	 * @param sampleList
	 *            the sampleList to set
	 */
	public void setSampleList(List<Sample1Bean> sampleList) {
		this.sampleList = sampleList;
	}

	/**
	 * @return the sampleMap
	 */
	public Map<String, Sample1Bean> getSampleMap() {
		return sampleMap;
	}

	/**
	 * @param sampleMap
	 *            the sampleMap to set
	 */
	public void setSampleMap(Map<String, Sample1Bean> sampleMap) {
		this.sampleMap = sampleMap;
	}

}
