package com.pranjal.PagePulse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "PagePulse APIs",
				version = "1.0",
				description = "This is the API documentation for the PagePulse Library Management System"
		)
)
@SpringBootApplication
public class PagePulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagePulseApplication.class, args);
	}

}
