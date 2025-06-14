package com.example.dataaggregator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.dataaggregator.repository.target", // Package where TargetDataRepository resides
    entityManagerFactoryRef = "targetEntityManagerFactory",        // As defined in DataSourceConfig
    transactionManagerRef = "targetTransactionManager"             // As defined in DataSourceConfig
)
public class TargetJpaConfig {
}
