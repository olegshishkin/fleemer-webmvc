package com.fleemer.webmvc.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.fleemer.webmvc.repository")
@PropertySource({"classpath:jndi.properties", "classpath:hibernate.properties"})
public class DataConfig {
    private static final String DATA_SOURCE_JNDI_NAME = "jndi.url";
    private static final String PACKAGES_TO_SCAN = "com.fleemer.webmvc.model";
    private static final String HIBERNATE_DIALECT_KEY = "hibernate.dialect";
    private static final String HIBERNATE_HBM_2_DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_EJB_NAMING_STRATEGY_KEY = "hibernate.ejb.naming_strategy";
    private static final String HIBERNATE_SHOW_SQL_KEY = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL_KEY = "hibernate.format_sql";
    private static final String HIBERNATE_HBM_2_DDL_IMPORT_FILES_KEY = "hibernate.hbm2ddl.import_files";

    private final Environment environment;

    @Autowired
    public DataConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(PACKAGES_TO_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(getJpaProperties());
        return emf;
    }

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource(environment.getProperty(DATA_SOURCE_JNDI_NAME));
    }

    private Properties getJpaProperties() {
        Properties p = new Properties();
        p.setProperty(HIBERNATE_DIALECT_KEY, environment.getProperty(HIBERNATE_DIALECT_KEY));
        p.setProperty(HIBERNATE_HBM_2_DDL_AUTO_KEY, environment.getProperty(HIBERNATE_HBM_2_DDL_AUTO_KEY));
        p.setProperty(HIBERNATE_EJB_NAMING_STRATEGY_KEY, environment.getProperty(HIBERNATE_EJB_NAMING_STRATEGY_KEY));
        p.setProperty(HIBERNATE_SHOW_SQL_KEY, environment.getProperty(HIBERNATE_SHOW_SQL_KEY));
        p.setProperty(HIBERNATE_FORMAT_SQL_KEY, environment.getProperty(HIBERNATE_FORMAT_SQL_KEY));
        p.setProperty(HIBERNATE_HBM_2_DDL_IMPORT_FILES_KEY, environment.getProperty(HIBERNATE_HBM_2_DDL_IMPORT_FILES_KEY));
        return p;
    }

    @Bean
    public JpaTransactionManager transactionManager(@Autowired EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
