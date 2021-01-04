package com.epam.esm.configuration;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.enums.ColumnName;
import com.epam.esm.utils.MapSqlParameterSourceCreator;
import com.epam.esm.utils.rowmapper.GiftCertificateRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Configuration
public class PersistenceTestConfig {
    private static final String INIT_DB_PATH = "classpath:database/initDB_H2.sql";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(StandardCharsets.UTF_8.name())
                .addScript(INIT_DB_PATH)
                .build();
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
    public MapSqlParameterSourceCreator parameterCreator() {
        return new MapSqlParameterSourceCreator();
    }

    @Bean
    public GiftCertificateRowMapper rowMapper() {
        return new GiftCertificateRowMapper();
    }

    @Bean
    public SimpleJdbcInsert insertCertificate() {
        return new SimpleJdbcInsert(jdbcTemplate())
                .withTableName(ColumnName.GIFT_CERTIFICATE.getValue())
                .usingGeneratedKeyColumns(ColumnName.GIFT_CERTIFICATE_ID.getValue());
    }

    @Bean
    public GiftCertificateDAO giftCertificateDAO() {
        return new GiftCertificateDAOImpl(
                parameterCreator(), rowMapper(), jdbcTemplate(), namedParameterJdbcTemplate(), insertCertificate()
        );
    }
}
