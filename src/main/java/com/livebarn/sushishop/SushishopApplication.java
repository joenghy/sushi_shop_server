package com.livebarn.sushishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SushishopApplication {
	public static void main(String[] args) {
		SpringApplication.run(SushishopApplication.class, args);
	}
}