package com.tsykul.joggingtracker.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @author KonstantinTsykulenko
* @since 7/13/2014.
*/
@ConfigurationProperties("ssl")
public class SSLServerProperties {
    private String keystoreFile;
    private String keystorePass;
    private String keystoreType;
    private int port;

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePass() {
        return keystorePass;
    }

    public void setKeystorePass(String keystorePass) {
        this.keystorePass = keystorePass;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public void setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
