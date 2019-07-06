package com.groupX.appX.config;

import static com.groupX.appX.security.SecurityConstants.HEADER_STRING;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	private final Logger log = LoggerFactory.getLogger(SpringFoxConfig.class);

	@Bean
	public Docket apiDocket() {
		log.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();

		Contact contact = new Contact("Matyas Albert-Nagy", "https://justrocket.de", "matyas@justrocket.de");

		List<VendorExtension> vext = new ArrayList<>();
		ApiInfo apiInfo = new ApiInfo("Backend API", "This is the best stuff since sliced bread - API", "6.6.6",
				"https://justrocket.de", contact, "MIT", "https://justrocket.de", vext);

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).pathMapping("/")
				.apiInfo(ApiInfo.DEFAULT).forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false);

		docket = docket.select().paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();

		watch.stop();
		log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", HEADER_STRING, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
	}

	ArrayList<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
}