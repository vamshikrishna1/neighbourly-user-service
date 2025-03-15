package com.neighbourly.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NeighbourlyUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeighbourlyUserServiceApplication.class, args);
	}

}
