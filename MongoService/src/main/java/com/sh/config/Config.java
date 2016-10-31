package com.sh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan("com.sh")
@EnableMongoRepositories("com.sh.repository")
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class Config 
{
	@Value("${mongo.db}")
	private String db;
	
	@Value("${mongo.host}")
	private String host;
	
	@Value("${mongo.port}")
	private String port;
	
	
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		
		return new SimpleMongoDbFactory(new MongoClient(host, Integer.parseInt(port)), db);
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}

	
}
