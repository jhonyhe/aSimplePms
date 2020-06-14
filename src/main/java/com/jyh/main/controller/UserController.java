
package com.jyh.main.controller;
 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.main.dao.UserDao;
import com.jyh.main.modle.User;
import com.jyh.main.util.IpUtil;
 
@RestController
public class UserController {
 
    @Autowired
    private UserDao userDao;
    
    
    @GetMapping(value="/addUser")
    public void saveTest(User user) throws Exception {
    	userDao.saveTest(user);
    }
 
    @GetMapping(value="/login")
    @PostMapping(value="/login")
    public String findTestByName(String id,String passWord,HttpServletRequest request){
    	System.out.println("id = " + id + " passWord = "+passWord);
    	String str= userDao.findTestByName(id, passWord);
    	String ipAddress =IpUtil.getIpAddr(request);
    	System.out.println(ipAddress);
        System.out.println("login is "+str);
        return str;
    }
 
    
    @GetMapping(value="/updateUser")
    public String updateTest(User user){
    	return userDao.updateTest(user);
    }
 
    @GetMapping(value="/deleteUser")
    public void deleteTestById(){
        userDao.deleteTestById("11");
    }
}