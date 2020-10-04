package com.jyh.main;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

 
@SpringBootApplication
public class Application {
	public static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
 
}