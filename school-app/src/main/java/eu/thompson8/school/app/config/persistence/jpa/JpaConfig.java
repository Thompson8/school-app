package eu.thompson8.school.app.config.persistence.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "eu.thompson8.school.app.repository", enableDefaultTransactions = false)
public class JpaConfig {

	private static final String PRESISTENCE_UNIT_NAME = "CustomerServicePersistenceUnit";

	private static final String ENTITY_PACKAGES = "eu.thompson8.school.app.model.entity";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter vendorAdapter) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPersistenceUnitName(PRESISTENCE_UNIT_NAME);
		factory.setPackagesToScan(ENTITY_PACKAGES);
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();

		return factory;
	}

}
