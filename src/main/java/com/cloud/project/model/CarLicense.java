package com.cloud.project.model;

public class CarLicense {

	// Table Constants
	public static final String CAR_LICENSE_TABLE = "CARS";
	public static final String FLD_CAR_ID = "CAR_ID";
	public static final String FLD_LICENSE = "LICENSE";
	public static final String FLD_FK_USER_ID = "FK_USER_ID";

	private Long id;
	private String license;
	private Long ownerId;

	public CarLicense() {
		this.id = (long) 0;
		this.license = "";
		this.ownerId = (long) 0;
	}

	public CarLicense(Long id, String license, Long ownerId) {
		super();
		this.id = id;
		this.license = license;
		this.ownerId = ownerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public String toString() {
		return "CarLicense [id=" + id + ", license=" + license + ", ownerId=" + ownerId + "]";
	}

}
