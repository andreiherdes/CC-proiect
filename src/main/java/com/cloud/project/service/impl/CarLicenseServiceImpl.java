package com.cloud.project.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.project.dao.CarLicenseDao;
import com.cloud.project.model.CarLicense;
import com.cloud.project.service.CarLicenseService;

@Service(value = "carLicenseService")
public class CarLicenseServiceImpl implements CarLicenseService {

	@Autowired
	private CarLicenseDao carDao;

	@Override
	public CarLicense getById(Long id) throws SQLException {
		return carDao.getById(id);
	}

	@Override
	public List<CarLicense> getAll(Long id) throws SQLException {
		return carDao.getAll(id);
	}

	@Override
	public void addCarLicense(CarLicense entity) throws SQLException {
		carDao.persist(entity);
	}

	@Override
	public void deleteById(long id) throws SQLException {
		carDao.deleteById(id);

	}

	@Override
	public void deleteByLicenseNumberAndOwnerId(String licenseNumber, long ownerId) throws SQLException {
		carDao.deleteByLicenseNumberAndOwnerId(licenseNumber, ownerId);
	}

}
