package com.tsykul.joggingtracker.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author KonstantinTsykulenko
 * @since7 /21/2014.
 */
@ConfigurationProperties("datasource")
public class DataSourceConfig {
    private int initialPoolSize;
    private int maxActivePoolSize;
    private String driverClassName;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMaxActivePoolSize() {
        return maxActivePoolSize;
    }

    public void setMaxActivePoolSize(int maxActivePoolSize) {
        this.maxActivePoolSize = maxActivePoolSize;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
