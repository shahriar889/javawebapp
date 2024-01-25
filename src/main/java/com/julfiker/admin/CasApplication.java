package com.julfiker.admin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "Ecommerce API", version = "1.0", description = "Ecommerce Application With Spring boot"))
public class CasApplication {
	public static void main(String[] args) {
		SpringApplication.run(CasApplication.class, args);
	}

}
