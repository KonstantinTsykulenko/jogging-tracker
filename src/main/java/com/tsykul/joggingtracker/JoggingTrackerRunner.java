package com.tsykul.joggingtracker;

import com.tsykul.joggingtracker.conf.SSLServerProperties;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.tsykul.joggingtracker")
@EnableConfigurationProperties(SSLServerProperties.class)
public class JoggingTrackerRunner {

    public static void main(String[] args) {
        SpringApplication.run(JoggingTrackerRunner.class, args);
    }

    @Bean
    @Autowired
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(
            SSLServerProperties sslServerProperties) throws Exception {
        final String absoluteKeystoreFile =
                Thread.currentThread().getContextClassLoader().getResource(
                        sslServerProperties.getKeystoreFile()).getPath();

        return factory -> {
            Assert.state(factory instanceof JettyEmbeddedServletContainerFactory, "Use Jetty for this server");
            JettyEmbeddedServletContainerFactory jettyFactory = (JettyEmbeddedServletContainerFactory) factory;
            jettyFactory.addServerCustomizers(server -> {
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setKeyStorePath(absoluteKeystoreFile);
                sslContextFactory.setKeyStorePassword(sslServerProperties.getKeystorePass());
                sslContextFactory.setKeyStoreType(sslServerProperties.getKeystoreType());

                Connector sslConnector = new SslSelectChannelConnector(sslContextFactory);
                sslConnector.setPort(sslServerProperties.getPort());
                server.addConnector(sslConnector);
            });
        };
    }

}