package com.livebarn.sushishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableScheduling
public class SushishopApplication {
	public static void main(String[] args) {
		SpringApplication.run(SushishopApplication.class, args);
	}
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.groupName("order-api")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.livebarn.sushishop.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Order API")
				.description("Spring Rest API reference")
				.licenseUrl("joeng.hy97@gmail.com")
				.version("1.0.0")
				.build();
	}
}