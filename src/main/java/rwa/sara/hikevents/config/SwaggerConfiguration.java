package rwa.sara.hikevents.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration /*implements WebMvcConfigurer*/ {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				//.apis(RequestHandlerSelectors.basePackage("rwa.sara.hikevents.controller"))
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				.securitySchemes(Lists.newArrayList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()));
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("HIKEVENTS REST API")
				.description("Spring Boot REST API for HIKEVENTS")
				.contact(new Contact("Sara", "https://www.linkedin.com/in/sara-stankovi%C4%87-b42744133/", "sara.stankovic1@pmf.edu.rs"))
				.version("1.0")
				.build();
	}
	
	@Bean
	public SecurityScheme apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
		return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}
}
