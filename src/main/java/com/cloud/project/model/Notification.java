package com.cloud.project.model;

import java.sql.Date;

public class Notification {

	public final static String NOTIFICATION_TABLE = "NOTIFICATIONS";
	public final static String FLD_ID = "NOTIFICATIONS_ID";
	public final static String FLD_ALERT_TYPE = "ALERT_TYPE";
	public final static String FLD_TIMESTAMP = "TIMESTAMP";
	public final static String FLD_FK_USER_ID = "FK_NOTIFICATION__USER_ID";
	public final static String FLD_FK_CAR_ID = "FK_NOTIFICATION__CAR_ID";
	public final static String FLD_FK_ISSUER_ID = "FK_NOTIFICATION__ISSUER_ID";

	private Long id;
	private Long userId;
	private Long issuerId;
	private Long carLicenseId;
	private String alertType;
	private Date timestamp;

	public Notification() {
		this.id = (long) 0;
		this.userId = (long) 0;
		this.carLicenseId = (long) 0;
		this.alertType = null;
		this.timestamp = null;
		this.issuerId = (long) 0;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getCarLicenseId() {
		return carLicenseId;
	}

	public void setCarLicenseId(Long carLicenseId) {
		this.carLicenseId = carLicenseId;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

}
