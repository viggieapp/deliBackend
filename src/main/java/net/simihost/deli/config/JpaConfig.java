package net.simihost.deli.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 * Created by Rashed on  18/03/2019
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"net.simihost.deli"})
@ComponentScan
public class JpaConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(environment.getRequiredProperty("dataSource.driverClassName"));
        config.setJdbcUrl(environment.getRequiredProperty("dataSource.url"));
        config.setUsername(environment.getRequiredProperty("dataSource.username"));
        config.setPassword(environment.getRequiredProperty("dataSource.password"));
        config.setPoolName(environment.getRequiredProperty("hikari.poolname"));
        config.setMaximumPoolSize(environment.getRequiredProperty("hikari.max.pool", Integer.class));

        config.addDataSourceProperty("useSSL", environment.getRequiredProperty("hikari.useSSL"));
        config.addDataSourceProperty("cachePrepStmts", environment.getRequiredProperty("hikari.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", environment.getRequiredProperty("hikari.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getRequiredProperty("hikari.prepStmtCacheSqlLimit"));
        config.addDataSourceProperty("useServerPrepStmts", environment.getRequiredProperty("hikari.useServerPrepStmts"));

        return new HikariDataSource(config);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, environment.getRequiredProperty("hibernate.dialect"));
        jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, environment.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.put(org.hibernate.cfg.Environment.FORMAT_SQL, environment.getRequiredProperty("hibernate.format_sql"));
        jpaProperties.put(org.hibernate.cfg.Environment.GENERATE_STATISTICS, environment.getRequiredProperty("hibernate.generate_statistics"));

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(configureDataSource());
        entityManagerFactoryBean.setPackagesToScan("net.simihost.deli");
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        HibernateJpaSessionFactoryBean sessionFactory = new HibernateJpaSessionFactoryBean();
        sessionFactory.setEntityManagerFactory(entityManagerFactory());
        return sessionFactory;
    }
}
