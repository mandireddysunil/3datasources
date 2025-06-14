package com.example.dataaggregator.config; // Or a more specific package like .runner

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class SourceDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SourceDataInitializer.class);

    @Bean(name = "sourceJdbcTemplate")
    public JdbcTemplate sourceJdbcTemplate(@Qualifier("sourceDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CommandLineRunner initializeSourceData(
            @Qualifier("sourceJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("sourceDataSource") DataSource dataSource // Also inject sourceDataSource for populator
    ) {
        return args -> {
            logger.info("Attempting to initialize source PostgreSQL database...");

            try {
                // Option 1: Use a schema.sql and data.sql if JPA ddl-auto is not 'create' or 'create-drop'
                // For JPA ddl-auto=update, Hibernate handles table creation for SourceData entity.
                // We just need to check if data exists and add if not.
                // The table name for SourceData entity is 'source_data' by default.

                String tableName = "source_data"; // Matches SourceData entity's default table name

                // Check if table exists (simple check, might need refinement for specific DBs if not using JPA for schema)
                // However, with ddl-auto=update, Hibernate should create it.
                // So, we primarily check for emptiness.
                Integer rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);

                if (rowCount != null && rowCount == 0) {
                    logger.info("Table '{}' is empty. Inserting sample data.", tableName);
                    jdbcTemplate.update("INSERT INTO " + tableName + " (name, value) VALUES (?, ?)", "Sample Item Alpha", "Value A123");
                    jdbcTemplate.update("INSERT INTO " + tableName + " (name, value) VALUES (?, ?)", "Sample Item Beta", "Value B456");
                    jdbcTemplate.update("INSERT INTO " + tableName + " (name, value) VALUES (?, ?)", "Sample Item Gamma", "Value C789");
                    logger.info("Inserted 3 sample records into '{}'.", tableName);
                } else {
                    logger.info("Table '{}' already contains data or rowCount is null. No initial data inserted by runner.", tableName);
                }

            } catch (Exception e) {
                // This might fail if the table doesn't exist yet and ddl-auto hasn't run.
                // Spring Boot typically runs CommandLineRunners after entity manager factory setup.
                logger.error("Error during source data initialization for table 'source_data'. " +
                                "This might be okay if Hibernate creates/updates the table. Error: {}", e.getMessage());
                // Fallback: if above fails (e.g. table not found), try to use ResourceDatabasePopulator
                // This is more for schema + data, but can be adapted.
                // Ensure schema.sql is only run if table truly doesn't exist.
                // For this example, we'll rely on Hibernate ddl-auto=update for table creation
                // and the above logic for data insertion.
                // If more complex schema setup is needed, consider Flyway or Liquibase.
            }
        };
    }
}
