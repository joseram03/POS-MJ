package com.example.POS_MJ_BACK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.POS_MJ_BACK.config")
public class PosMjBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosMjBackApplication.class, args);
	}

}
