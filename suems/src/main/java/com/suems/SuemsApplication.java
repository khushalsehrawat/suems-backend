package com.suems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SuemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuemsApplication.class, args);
	}

}
