package com.fleemer.webmvc.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
@Profile("dev")
@PropertySource({"classpath:jndi.properties"})
public class DataSourceConfigDev {
    private static final String DATA_SOURCE_JNDI_NAME = "jndi.url";

    private final Environment environment;

    @Autowired
    public DataSourceConfigDev(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource(environment.getProperty(DATA_SOURCE_JNDI_NAME));
    }
}
