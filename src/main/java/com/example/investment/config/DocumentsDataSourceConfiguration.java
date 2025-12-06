package com.example.investment.config;

import com.example.investment.database.oracle.PoolDataSourceProperties;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.investment.repository",
        entityManagerFactoryRef = "documentsEntityManagerFactory",
        transactionManagerRef = "documentsTransactionManager"
)
@EntityScan(basePackages = "com.example.investment.entity")
public class DocumentsDataSourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DocumentsDataSourceConfiguration.class);

    private static final String DATASOURCE_ID = "documents";

    // 1) Bind spring.datasource.* into a simple properties holder (similar idea to their PoolDataSourceProperties)

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public PoolDataSourceProperties documentsDataSourceProperties() {
        return new PoolDataSourceProperties();
    }

    // 2) Build the actual Oracle UCP DataSource

    @Bean
    @Primary
    public DataSource documentsDataSource(PoolDataSourceProperties props) throws SQLException {
        log.info("Creating Oracle UCP DataSource for '{}'", DATASOURCE_ID);

        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

        pds.setURL(props.getUrl());
        pds.setUser(props.getUsername());
        pds.setPassword(props.getPassword());
        pds.setConnectionFactoryClassName(props.getConnectionFactoryClassName());
        pds.setConnectionPoolName(props.getConnectionPoolName());

        pds.setInitialPoolSize(props.getInitialPoolSize());
        pds.setMinPoolSize(props.getMinPoolSize());
        pds.setMaxPoolSize(props.getMaxPoolSize());
        pds.setAbandonedConnectionTimeout(props.getAbandonedConnectionTimeout());
        pds.setConnectionWaitTimeout(props.getConnectionWaitTimeout());
        pds.setInactiveConnectionTimeout(props.getInactiveConnectionTimeout());
        pds.setMaxConnectionReuseCount(props.getMaxConnectionReuseCount());
        pds.setMaxConnectionReuseTime(props.getMaxConnectionReuseTime());
        pds.setTimeoutCheckInterval(props.getTimeoutCheckInterval());
        pds.setValidateConnectionOnBorrow(props.isValidateConnectionOnBorrow());

        return pds;
    }

    // 3) JPA EntityManagerFactory using this UCP DataSource

    @Bean
    @Primary
    @PersistenceContext(unitName = DATASOURCE_ID)
    public LocalContainerEntityManagerFactoryBean documentsEntityManagerFactory(
            DataSource documentsDataSource,
            JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(documentsDataSource);
        emf.setPackagesToScan("com.example.investment.entity");
        emf.setPersistenceUnitName(DATASOURCE_ID);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> props = new HashMap<>(jpaProperties.getProperties());
        emf.setJpaPropertyMap(props);

        return emf;
    }

    // 4) Transaction manager backed by that EntityManagerFactory

    @Bean
    @Primary
    public PlatformTransactionManager documentsTransactionManager(
            @Qualifier("documentsEntityManagerFactory") EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}

