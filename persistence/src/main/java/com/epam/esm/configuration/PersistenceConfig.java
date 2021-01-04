package com.epam.esm.configuration;

import com.epam.esm.model.enums.ColumnName;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database/path.properties")
public class PersistenceConfig {
    @Value("${path.database}")
    private String propertiesPath;

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig(propertiesPath);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(jdbcTemplate());
    }

    @Bean
    public SimpleJdbcInsert insertCertificate() {
        return new SimpleJdbcInsert(jdbcTemplate())
                .withTableName(ColumnName.GIFT_CERTIFICATE.getValue())
                .usingGeneratedKeyColumns(ColumnName.GIFT_CERTIFICATE_ID.getValue());
    }

    @Bean
    public SimpleJdbcInsert insertTag() {
        return new SimpleJdbcInsert(jdbcTemplate())
                .withTableName(ColumnName.TAG.getValue())
                .usingGeneratedKeyColumns(ColumnName.TAG_ID.getValue());
    }
}
