package com.rdas.crossword.bdd;

import com.rdas.crossword.config.HazelcastConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

@Configuration
@Import({HazelcastConfiguration.class})
//@ComponentScan(basePackages = {"com.rdas.crossword.unit.service","com.rdas.crossword.config", "com.rdas.crossword.resource"})
@ComponentScan(basePackages = {"com.rdas.crossword"})
public class IntegrationTestConfig {
}
