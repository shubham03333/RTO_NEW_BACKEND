package com.sunbeam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sunbeam.daos.UserDao;

//@SpringBootApplication(exclude =SecurityAutoConfiguration.class)
@SpringBootApplication
public class RtoServerApplication {

	@Autowired
	private UserDao userDao;
	
	
	
	public static void main(String[] args) {
		
		
	
		SpringApplication.run(RtoServerApplication.class, args);
		
	}

}
