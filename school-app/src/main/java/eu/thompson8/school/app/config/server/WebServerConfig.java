package eu.thompson8.school.app.config.server;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConfig {

	@Bean
	public ServletWebServerFactory servletWebServerFactory(WebServerProperties properties) {
		return new UndertowServletWebServerFactory(properties.getContextPath(), properties.getPort());
	}

}
