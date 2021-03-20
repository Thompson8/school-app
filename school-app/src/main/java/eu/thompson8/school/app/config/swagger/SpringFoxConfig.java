package eu.thompson8.school.app.config.swagger;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	private static final String BASIC_AUTH = "basicAuth";

	private static final String GLOBAL = "global";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).securitySchemes(createSecuritySchemes())
				.securityContexts(createSecurityContext()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private List<SecurityContext> createSecurityContext() {
		return Collections
				.singletonList(SecurityContext.builder().securityReferences(createSecurityReferences()).build());
	}

	private List<SecurityReference> createSecurityReferences() {
		return Collections.singletonList(
				new SecurityReference(BASIC_AUTH, new AuthorizationScope[] { new AuthorizationScope(GLOBAL, "") }));
	}

	private List<SecurityScheme> createSecuritySchemes() {
		return Collections.singletonList(new BasicAuth(BASIC_AUTH));
	}

}