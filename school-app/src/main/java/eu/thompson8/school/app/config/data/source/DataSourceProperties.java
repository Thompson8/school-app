package eu.thompson8.school.app.config.data.source;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("app.data.source")
@Getter
@Setter
public class DataSourceProperties {

	private String url;

	private String driverClassName;

	private String user;

	private String password;

}
