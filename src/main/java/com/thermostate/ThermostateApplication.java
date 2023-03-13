package com.thermostate;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.thermostate.events.listeners.UserLogedInListener;
import com.thermostate.temperature.infrastructure.LCDTemperatureChangedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ThermostateApplication {

	@Bean
	public EventBus eventBus() {
		EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
		eventBus.register(new UserLogedInListener());
		eventBus.register(new LCDTemperatureChangedListener());
		return eventBus;
	}
	public static void main(String[] args) {
		SpringApplication.run(ThermostateApplication.class, args);
	}

}
