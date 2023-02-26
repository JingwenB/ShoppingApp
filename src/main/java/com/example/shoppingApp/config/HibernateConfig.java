package com.example.shoppingApp.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    HibernateProperty hibernateProperty;

    @Bean
    protected LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                "com.example.shoppingApp.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(hibernateProperty.getDriver());
        dataSource.setUrl(hibernateProperty.getUrl());
        dataSource.setUsername(hibernateProperty.getUsername());
        dataSource.setPassword(hibernateProperty.getPassword());

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.show_sql", hibernateProperty.getShowsql());
        hibernateProperties.setProperty(
                "hibernate.dialect", hibernateProperty.getDialect());

//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hibernateProperty.getHbm2ddl());
        return hibernateProperties;
    }

    @Bean
    public  ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("emailExchange");
        rabbitTemplate.setRoutingKey("a.b.c");
        return rabbitTemplate;
    }

}
