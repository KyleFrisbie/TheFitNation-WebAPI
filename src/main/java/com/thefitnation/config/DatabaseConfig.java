package com.thefitnation.config;

import javax.sql.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

/**
 * Created by michael on 2/27/17.
 */
@Configuration
@Profile("dev")
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
