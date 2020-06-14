
package com.jyh.main.controller;
 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.main.dao.UserDao;
import com.jyh.main.modle.User;
import com.jyh.main.util.IpUtil;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class UserController {
 
    @Autowired
    private UserDao userDao;
    
    
    @GetMapping(value="/addUser")
    public void saveTest(User user) throws Exception {
    	userDao.saveTest(user);
    }
 
    @GetMapping(value="/login")
    public String findTestByName(String id,String passWord,HttpServletRequest request){
    	System.out.println("id = " + id + " passWord = "+passWord);
    	String str= userDao.findTestByName(id, passWord);
    	RedisUtil.StringOps.set("user", str);
        System.out.println("In redis user is "+ RedisUtil.StringOps.get("user"));
    	String ipAddress =IpUtil.getIpAddr(request);
    	System.out.println(ipAddress);
        System.out.println("In java user is "+str);
        return str;
    }
 
    
    @GetMapping(value="/updateUser")
    public String updateTest(User user){
    	String str= userDao.updateTest(user);
    	RedisUtil.StringOps.set("user", str);
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
    	return str;
    }
 
    @GetMapping(value="/deleteUser")
    public void deleteTestById(String id){
        userDao.deleteTestById(id);
        RedisUtil.StringOps.set("user", new String());
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
    }
}