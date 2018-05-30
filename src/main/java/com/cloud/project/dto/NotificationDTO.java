package com.cloud.project.dto;

import com.cloud.project.model.CarLicense;
import com.cloud.project.model.Notification;

public class NotificationDTO {

	private Notification notification;
	private CarLicense carLicense;

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public CarLicense getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(CarLicense carLicense) {
		this.carLicense = carLicense;
	}

}
