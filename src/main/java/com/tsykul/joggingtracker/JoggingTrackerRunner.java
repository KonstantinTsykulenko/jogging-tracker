package com.tsykul.joggingtracker;

import com.tsykul.joggingtracker.conf.DataSourceConfig;
import com.tsykul.joggingtracker.conf.SSLServerProperties;
import com.tsykul.joggingtracker.conf.WebSecurityConfig;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Configuration
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.tsykul.joggingtracker")
@EnableConfigurationProperties({SSLServerProperties.class, DataSourceConfig.class})
@Import(WebSecurityConfig.class)
public class JoggingTrackerRunner {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{JoggingTrackerRunner.class}, args);
    }

    @Bean
    @Autowired
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(
            SSLServerProperties sslServerProperties) throws Exception {
        final String keystorePath =
                Thread.currentThread().getContextClassLoader().getResource(
                        sslServerProperties.getKeystoreFile()).toExternalForm();

        return factory -> {
            Assert.state(factory instanceof JettyEmbeddedServletContainerFactory, "Use Jetty for this server");
            JettyEmbeddedServletContainerFactory jettyFactory = (JettyEmbeddedServletContainerFactory) factory;
            jettyFactory.addServerCustomizers(server -> {
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setKeyStorePath(keystorePath);
                sslContextFactory.setKeyStorePassword(sslServerProperties.getKeystorePass());
                sslContextFactory.setKeyStoreType(sslServerProperties.getKeystoreType());

                Connector sslConnector = new SslSelectChannelConnector(sslContextFactory);
                sslConnector.setPort(sslServerProperties.getPort());
                server.addConnector(sslConnector);
            });
        };
    }

    @Bean
    public DataSource getDataSource(DataSourceConfig dataSourceConfig) {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setInitialSize(dataSourceConfig.getInitialPoolSize());
        poolProperties.setMaxActive(dataSourceConfig.getMaxActivePoolSize());
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        dataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
        dataSource.setUrl(dataSourceConfig.getDbUrl());
        dataSource.setDefaultAutoCommit(false);
        dataSource.setUsername(dataSourceConfig.getDbUsername());
        dataSource.setPassword(dataSourceConfig.getDbPassword());
        return dataSource;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}