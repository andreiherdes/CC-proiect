package com.cloud.project;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.project.dao.UserDao;
import com.cloud.project.dao.impl.UserDaoImpl;
import com.cloud.project.database.CloudSqlConnection;

@SpringBootApplication
@RestController
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	
	@GetMapping("/")
	public String hello() throws SQLException {
		UserDao userDao = new UserDaoImpl();
		return userDao.getAll().toString();
	}
}
