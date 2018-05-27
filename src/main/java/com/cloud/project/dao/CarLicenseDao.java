package com.cloud.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.CarLicense;

public interface CarLicenseDao {

    CarLicense getById(Long id) throws SQLException;
    
    List<CarLicense> getAll(Long id) throws SQLException;

	void persist(CarLicense entity) throws SQLException;

	void deleteById(long id) throws SQLException;
	
	boolean isLicenseNumberValid(String license) throws SQLException;
}
