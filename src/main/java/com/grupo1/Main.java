package com.grupo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		System.out.println("test");
		ApplicationContext spring = SpringApplication.run(Main.class, args);


	}
}

