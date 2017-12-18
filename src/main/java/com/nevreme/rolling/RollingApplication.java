package com.nevreme.rolling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages="com.nevreme.rolling")
public class RollingApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(RollingApplication.class, args);
	}
}
