package com.jyh.main.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jyh.main.Application;

@Component
public class RedisCheckUtil implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Check Redis's States");
    	try {
   		 RedisUtil.StringOps.set("try", "try");
   	}catch (Exception e) {
   		System.out.println(e.toString());
   		System.out.println("Redis is not running or the setting is incorrect!!!");
   		Application.context.close();
   		return ;
		}
   	RedisUtil.KeyOps.delete("try");
   	System.out.println("Redis is running!!!");
    }
    
}
