package com.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RedisApplication {

	public static void main(String[] args) {
//		SpringApplication.run(LiveApplication.class, args);
		System.out.print("hello my live LOK!!");
		 SpringApplication.run(RedisApplication.class,args);
	}
	
	}
