package com.rohit.scheduler.app.config;

import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Value("classpath:schema/quartz-create-tables.sql")
    private Resource createScript;

    @Value("classpath:schema/quartz-drop-tables.sql")
    private Resource dropScript;

    @Value("${scheduler.initdbschema}")
    private boolean initdbSchema;

    private static final Logger logger = LoggerFactory.getLogger(QuartzConfig.class);

    @PostConstruct
    public void init() {
        logger.info("Quartz Scheduler has been initialized");
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    @DependsOn(value = {"quartzInitDb"})
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);

        factory.setQuartzProperties(quartzProperties());

        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public DataSourceInitializer quartzInitDb(DataSource dataSource) {

        logger.info("Initializing the database");

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();

        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setEnabled(true);
        dataSourceInitializer.setDatabasePopulator(getDataBasePopulator());

        return dataSourceInitializer;
    }

    private DatabasePopulator getDataBasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

        databasePopulator.setContinueOnError(false);
        databasePopulator.setIgnoreFailedDrops(true);
        databasePopulator.setSqlScriptEncoding("UTF-8");

        if(initdbSchema) {
            databasePopulator.addScript(createScript);
//        databasePopulator.addScript(dropScript);
        }

        return databasePopulator;
    }
}
