package com.thermostate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ThermostateApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThermostateApplication.class, args);
	}

}
