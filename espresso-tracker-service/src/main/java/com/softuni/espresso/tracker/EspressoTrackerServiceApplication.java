package com.softuni.espresso.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EspressoTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EspressoTrackerServiceApplication.class, args);
	}

}
