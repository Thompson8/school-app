package eu.thompson8.school.app.config.data.source;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource dataSource(DataSourceProperties properties) {
		return createDataSource(properties);
	}

	private DataSource createDataSource(DataSourceProperties properties) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(properties.getUrl());
		config.setDriverClassName(properties.getDriverClassName());
		config.setUsername(properties.getUrl());
		config.setPassword(properties.getPassword());
		return new HikariDataSource(config);
	}

}
