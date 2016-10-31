package com.sh.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.sh.ctrl")
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class ConfigApp {
	
	
	
}
