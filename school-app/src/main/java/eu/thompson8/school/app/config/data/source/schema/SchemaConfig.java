package eu.thompson8.school.app.config.data.source.schema;

import java.io.IOException;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SchemaConfig {

	private static final String DDL_LOCATION = "db/schema.sql";

	private static final Logger logger = LoggerFactory.getLogger(SchemaConfig.class);

	private final JdbcTemplate template;

	@PostConstruct
	public void initDataBase() throws IOException {
		logger.info("Database init started");
		long start = System.currentTimeMillis();

		executeDDLs();

		logger.info("Database init finished: {}ms", System.currentTimeMillis() - start);
	}

	private void executeDDLs() throws IOException {
		logger.debug("Started DDL execution");
		long start = System.currentTimeMillis();

		ClassPathResource schema = new ClassPathResource(DDL_LOCATION);
		StringBuilder builder = new StringBuilder();
		try (Scanner scanner = new Scanner(schema.getInputStream())) {
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
			}
		}

		String ddls = builder.toString();
		logger.trace("DDL statements to execute: {}", ddls);
		template.execute(ddls);

		logger.debug("Finished DDL execution: {}ms", System.currentTimeMillis() - start);
	}

}
