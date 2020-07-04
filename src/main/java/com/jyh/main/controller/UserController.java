
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.PrivilegeDao;
import com.jyh.main.dao.RoleDao;
import com.jyh.main.dao.UserDao;
import com.jyh.main.modle.Privilege;
import com.jyh.main.modle.Role;
import com.jyh.main.modle.User;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class UserController {
 
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private PrivilegeDao privilegeDao;
    
    @GetMapping(value="/user/addUser")
    public void saveUser(User user) throws Exception {
    	userDao.saveUser(user);
    }
    
    @GetMapping(value = "/success")
    public String success() throws Exception {
    	System.out.println("success");
    	return "success";
    }
    
    @GetMapping(value = "/error")
    public void error() throws Exception {
    	System.out.println("error");
    	//return "success";
    }
    
    @GetMapping(value = "/unauthorized")
    public String unauthorized() throws Exception {
    	System.out.println("unauthorized");
    	 return "error";
    }
    
    @GetMapping(value = "/user/getUser")
    public String findUserByName(String username,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("username = " + username );
    	User user = userDao.findUserByName(username);
    	Role role= new Role();
    	Privilege privilege= new Privilege();
    	if(!(user==null)) {
    		role = roleDao.findRoleById(user.getRole_id());
    		privilege = privilegeDao.findPrivilegeById(user.getP_id());
    	}
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (role==null) {
        	map.put("status", 202);
        	map.put("role", new Role());
		}else {
			map.put("status", 200);
			 map.put("role", role);
		}
        if (user==null) {
        	map.put("status", 202);
        	map.put("user", new User());
		}else {
			map.put("status", 200);
			 map.put("user", user);
		}
        if (privilege==null) {
        	map.put("status", 202);
        	map.put("privilege", new Privilege());
		}else {
			map.put("status", 200);
			 map.put("privilege", privilege);
		}
    	RedisUtil.StringOps.set("user", new Gson().toJson(map));
        return new Gson().toJson(map);
    }
    
    @GetMapping(value="/login")
    public String findUserByName(String username,String passWord,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("username = " + username + " passWord = "+passWord);
    	User user = userDao.findUserByName(username, passWord);
    	Role role= new Role();
    	Privilege privilege= new Privilege();
    	if(!(user==null)) {
    		role = roleDao.findRoleById(user.getRole_id());
    		privilege = privilegeDao.findPrivilegeById(user.getP_id());
    	}
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (role==null) {
        	map.put("status", 202);
        	map.put("role", new Role());
		}else {
			map.put("status", 200);
			 map.put("role", role);
		}
        if (user==null) {
        	map.put("status", 202);
        	map.put("user", new User());
		}else {
			map.put("status", 200);
			 map.put("user", user);
		}
        if (privilege==null) {
        	map.put("status", 202);
        	map.put("privilege", new Privilege());
		}else {
			map.put("status", 200);
			 map.put("privilege", privilege);
		}
    	RedisUtil.StringOps.set("user", new Gson().toJson(map));
        return new Gson().toJson(map);
    }
 
    
    @GetMapping(value="/user/updateUser")
    public String updateUser(User user){
    	User users= userDao.updateUser(user);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("status", 200);
         map.put("responseParam", "更新结束");
         map.put("user", users);
    	RedisUtil.StringOps.set("user", new Gson().toJson(map));
    	return new Gson().toJson(map);
    }
 
    @GetMapping(value="/user/deleteUser")
    public void deleteUserById(String username){
        userDao.deleteUserById(username);
        RedisUtil.StringOps.set("user", new String());
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
    }
}