package com.optus.counterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.optus.counterapi"})
public class CounterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CounterApiApplication.class, args);
	}

}
