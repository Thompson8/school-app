package eu.thompson8.school.app.config.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("app.server")
@Getter
@Setter
public class WebServerProperties {

	private String contextPath;

	private Integer port;

}
