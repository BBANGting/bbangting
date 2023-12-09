package com.khu.bbangting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BbangtingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbangtingApplication.class, args);
	}

}
