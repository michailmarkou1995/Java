package com.userfrontend;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.userfrontend.domain.Patient;
import com.userfrontend.service.PatientService;

@SpringBootApplication
public class UserFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserFrontendApplication.class, args);
	}
}
