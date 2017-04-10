package org.mov.config.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("org.mov.service")
@EnableTransactionManagement
@Import({DataSourceConfig.class, JpaConfig.class})
public class ServiceConfig {
}
