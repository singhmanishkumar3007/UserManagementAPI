package com.easybusiness.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.easybusiness.usermanagement", "com.easybusiness.modelmanagement"})
@EnableAutoConfiguration
public class UserManagementApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementApplication.class);
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UserManagementApplication.class);
	    }

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
		LOGGER.info("started UserManagementApplication");
	}

	@Override
	public void run(String... args) throws Exception {
	    LOGGER.info("in run method");
	    
	}

}
