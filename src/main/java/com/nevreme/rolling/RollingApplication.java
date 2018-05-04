package com.nevreme.rolling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.nevreme.rolling.utils.Constants;


@SpringBootApplication
@ComponentScan(basePackages="com.nevreme.rolling")
public class RollingApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		if (System.getProperty("APP_ROOT") == null) {
			System.setProperty("APP_ROOT", Constants.APP_ROOT);
		}
		SpringApplication.run(RollingApplication.class, args);
	}

}
