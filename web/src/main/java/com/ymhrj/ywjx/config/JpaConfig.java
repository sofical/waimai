package com.ymhrj.ywjx.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Administrator on 2017/11/21.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.ymhrj.ywjx.db.repository"}, repositoryImplementationPostfix = "Impl")
public class JpaConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        String JDBC_URL = this.environment.getProperty("druid.datasource.url");
        String USER_NAME = this.environment.getProperty("druid.datasource.username");
        String PASSWORD = this.environment.getProperty("druid.datasource.password");
        String DRIVER_CLASSNAME = this.environment.getProperty("druid.datasource.driver-class-name");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(DRIVER_CLASSNAME);
        druidDataSource.setUrl(JDBC_URL);
        druidDataSource.setUsername(USER_NAME);
        druidDataSource.setPassword(PASSWORD);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(5);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(50 * 1000L);
        druidDataSource.setMinEvictableIdleTimeMillis(60 * 1000L);
        return druidDataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.ymhrj.ywjx.db.entity");
        factoryBean.setJpaProperties(properties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory());
        manager.setGlobalRollbackOnParticipationFailure(false);
        return manager;
    }
}
