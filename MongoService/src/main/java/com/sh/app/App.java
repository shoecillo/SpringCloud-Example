package com.sh.app;

import org.springframework.boot.SpringApplication;

import com.sh.config.Config;

public class App 
{
	
	public static void main(String[] args)
	{
		SpringApplication.run(Config.class, args);
	}
	
}
