package com.cloud.project.service;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.CarLicense;

public interface CarLicenseService {

	CarLicense getById(Long id) throws SQLException;

	List<CarLicense> getAll(Long id) throws SQLException;

	void addCarLicense(CarLicense entity) throws SQLException;

	void deleteById(long id) throws SQLException;
	
	void deleteByLicenseNumberAndOwnerId(String licenseNumber, long ownerId) throws SQLException;
}
