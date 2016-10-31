package com.sh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.sh.config.ConfigApp;

@EnableDiscoveryClient
@SpringBootApplication
public class App 
{
	
	 public static void main(String[] args) throws Exception
	 {
		 
		 SpringApplication.run(ConfigApp.class, args);
	 }
	
}
