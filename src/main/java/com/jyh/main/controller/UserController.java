
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.RoleDao;
import com.jyh.main.dao.UserDao;
import com.jyh.main.modle.Role;
import com.jyh.main.modle.User;
import com.jyh.main.util.IpUtil;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class UserController {
 
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    @GetMapping(value="/addUser")
    public void saveUser(User user) throws Exception {
    	userDao.saveUser(user);
    }
 
    @GetMapping(value="/login")
    public String findUserByName(String id,String passWord,HttpServletRequest request){
    	System.out.println("id = " + id + " passWord = "+passWord);
    	User user = userDao.findUserByName(id, passWord);
    	Role role= new Role();
    	if(!(user==null)) {
    		role = roleDao.findRoleById(user.getRole_id());
    	}
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "查询结束");
        if (role==null) {
        	map.put("status", 202);
            map.put("responseParam", "查询结束");
        	map.put("role", role==null?new Role():role);
		}else {
			map.put("status", 200);
	        map.put("responseParam", "查询结束");
			 map.put("role", role);
		}
        if (!(user==null)) {
        	map.put("status", 202);
            map.put("responseParam", "查询结束");
        	map.put("user", user==null?new User():user);
		}else {
			map.put("status", 200);
	        map.put("responseParam", "查询结束");
			 map.put("user", user);
		}
    	RedisUtil.StringOps.set("user", new Gson().toJson(map));
        System.out.println("In redis user is "+ RedisUtil.StringOps.get("user"));
    	String ipAddress =IpUtil.getIpAddr(request);
    	System.out.println(ipAddress);
        System.out.println("In java user is "+user);
        return new Gson().toJson(map);
    }
 
    
    @GetMapping(value="/updateUser")
    public String updateUser(User user){
    	User users= userDao.updateUser(user);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("status", 200);
         map.put("responseParam", "更新结束");
         map.put("user", users);
    	RedisUtil.StringOps.set("user", new Gson().toJson(map));
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
    	return new Gson().toJson(map);
    }
 
    @GetMapping(value="/deleteUser")
    public void deleteUserById(String id){
        userDao.deleteUserById(id);
        RedisUtil.StringOps.set("user", new String());
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
    }
}