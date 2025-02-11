package com.coders.software.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaApplication.class, args);
		System.out.println("To view via Swagger enter the following URL,");
		System.out.println("http://localhost:8989/swagger-ui/index.html");
	}
}
