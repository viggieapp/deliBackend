package net.simihost.deli.config;

import net.simihost.deli.jobs.DeliJob;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Created by Rashed on  22/05/2019
 *
 */
//@Configuration
public class QuartzConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private Environment environment;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.debug("Quartz Config initialized.");
    }

    @Bean
    public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        quartzScheduler.setDataSource(configureDataSource());
        quartzScheduler.setTransactionManager(transactionManager);
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setSchedulerName("deli-quartz-scheduler");

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

        quartzScheduler.setQuartzProperties(quartzProperties());

        Trigger[] triggers = { gatewaysKeysRefreshTrigger().getObject() };
        quartzScheduler.setTriggers(triggers);

        return quartzScheduler;
    }

    @Bean
    public JobDetailFactoryBean gatewaysKeysRefreshJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DeliJob.class);
        jobDetailFactory.setGroup("refreshing-orders-quartz");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean gatewaysKeysRefreshTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(gatewaysKeysRefreshJob().getObject());
        cronTriggerFactoryBean.setCronExpression(environment.getRequiredProperty("quartz.cron.refreshOrders"));
        cronTriggerFactoryBean.setGroup("refreshing-orders-quartz");
        return cronTriggerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(new ClassPathResource("quartz/quartz.properties"), new ClassPathResource(environment.getRequiredProperty("quartz.properties.location")));
        Properties properties = null;
        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
            logger.info("Quartz's properties loaded successfully!");
        } catch (IOException e) {
            logger.warn("Cannot load quartz.properties.");
        }
        return properties;
    }

    private DataSource configureDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getRequiredProperty("org.quartz.dataSource.myDS.driver"));
        hikariConfig.setJdbcUrl(environment.getRequiredProperty("org.quartz.dataSource.myDS.URL"));
        hikariConfig.setUsername(environment.getRequiredProperty("org.quartz.dataSource.myDS.user"));
        hikariConfig.setPassword(environment.getRequiredProperty("org.quartz.dataSource.myDS.password"));

        hikariConfig.setMaximumPoolSize(Integer.valueOf(environment.getRequiredProperty("quartz.dataSource.maximumPoolSize")));
        hikariConfig.setMaxLifetime(Long.valueOf(environment.getRequiredProperty("quartz.dataSource.maxLifetime")));
        hikariConfig.setConnectionTestQuery(environment.getRequiredProperty("quartz.dataSource.connectionTestQuery"));
        hikariConfig.setIdleTimeout(Long.valueOf(environment.getRequiredProperty("quartz.dataSource.idleTimeout")));

        hikariConfig.addDataSourceProperty("cachePrepStmts", environment.getRequiredProperty("quartz.dataSource.cachePrepStmts"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", environment.getRequiredProperty("quartz.dataSource.prepStmtCacheSize"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getRequiredProperty("quartz.dataSource.prepStmtCacheSqlLimit"));
        hikariConfig.addDataSourceProperty("useServerPrepStmts", environment.getRequiredProperty("quartz.dataSource.useServerPrepStmts"));
        hikariConfig.addDataSourceProperty("jdbc4ConnectionTest",environment.getRequiredProperty("quartz.dataSource.jdbc4ConnectionTest"));

        return new HikariDataSource(hikariConfig);
    }
}
