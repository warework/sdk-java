package com.warework.service.datastore.client.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Person {

	private BigDecimal id;

	private String name;

	private String color;

	private Date dateOfBirth;

	private byte[] picture;

	private Integer password;

	private Long speed;

	private String unknown;

	public String getUnknown() {
		return unknown;
	}

	public void setUnknown(String unknown) {
		this.unknown = unknown;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

}
