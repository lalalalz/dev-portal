package kr.co.kwt.devportal.secret.v2.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "devDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dev")
    public DataSource devDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "prodDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "devJdbcTemplate")
    public JdbcTemplate devJdbcTemplate(@Qualifier("devDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "prodJdbcTemplate")
    public JdbcTemplate prodJdbcTemplate(@Qualifier("prodDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}