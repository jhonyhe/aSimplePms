package com.jyh.main.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jyh.main.util.RedisUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_1{
    @Test
    public void string(){
    	RedisUtil.StringOps.set("Mykey in util", "Myval in util");
        System.out.println(RedisUtil.StringOps.get("Mykey in util"));
    }
    @Test
    public void set(){
    	RedisUtil.SetOps.sAdd("set in util", "set in util");
        System.out.println(RedisUtil.SetOps.sMembers("set in util"));
    }
}