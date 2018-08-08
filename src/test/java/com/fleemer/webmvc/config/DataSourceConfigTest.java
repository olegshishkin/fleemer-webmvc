package com.fleemer.webmvc.config;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("test")
@PropertySource({"classpath:db.properties"})
public class DataSourceConfigTest {
    private static final String DATABASE_PROPERTIES_FILE = "db.properties";
    private static final String URL_KEY = "url";

    @Bean
    public DataSource dataSource() {
        Properties p;
        try {
            p = getProperties();
            return new DriverManagerDataSource(p.getProperty(URL_KEY), p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Properties getProperties() throws IOException {
        Properties p = new Properties();
        p.load(ClassLoader.getSystemClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILE));
        return p;
    }
}
