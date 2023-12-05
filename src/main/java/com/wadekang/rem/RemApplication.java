package com.wadekang.rem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemApplication.class, args);
	}

}
