package com.coders.software.media;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	 GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("Media Types")
				.pathsToMatch("/v3/api-docs/**",
						"/v1/media/service/**",
						"/swagger-ui/**")
				.build();
	}
	
	@Bean
	OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Media Types, Blu-Ray, DVD, Games and Books").description("Save and dsplay Media data")
						.version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}
}
