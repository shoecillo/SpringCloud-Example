package com.sh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }
}
