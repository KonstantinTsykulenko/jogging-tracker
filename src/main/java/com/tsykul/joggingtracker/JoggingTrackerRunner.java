package com.tsykul.joggingtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.tsykul.joggingtracker")
public class JoggingTrackerRunner {

    public static void main(String[] args) {
        SpringApplication.run(JoggingTrackerRunner.class, args);
    }

}