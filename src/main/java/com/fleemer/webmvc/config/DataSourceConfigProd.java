package com.fleemer.webmvc.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Profile("prod")
@PropertySource({"classpath:jdbc.properties"})
public class DataSourceConfigProd {
    private static final String DRIVER_KEY = "JDBC_DRIVER";
    private static final String PASSWORD_KEY = "JDBC_DATABASE_PASSWORD";
    private static final String URL_KEY = "JDBC_DATABASE_URL";
    private static final String USERNAME_KEY = "JDBC_DATABASE_USERNAME";

    private final Environment environment;

    @Autowired
    public DataSourceConfigProd(Environment environment) {
        this.environment = environment;
    }

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        BasicDataSource s = new BasicDataSource();
        s.setUrl(environment.getProperty(URL_KEY));
        s.setUsername(environment.getProperty(USERNAME_KEY));
        s.setPassword(environment.getProperty(PASSWORD_KEY));
        s.setDriverClassName(environment.getProperty(DRIVER_KEY));
        return s;
    }
}
