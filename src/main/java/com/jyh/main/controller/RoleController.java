
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.RoleDao;
import com.jyh.main.modle.Role;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class RoleController {
 
    @Autowired
    private RoleDao roleDao;
    
    
    @GetMapping(value="/role/addRole")
    public void saveRole(Role role) throws Exception {
    	roleDao.saveRole(role);
    }
 
    @GetMapping(value="/role/findRole")
    public String findRoleById(String role_id,HttpServletRequest request){
    	System.out.println("role_id = " + role_id);
    	Role role = roleDao.findRoleById(role_id);
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (role==null) {
        	map.put("status", 202);
        	map.put("role", new Role());
		}else {
			map.put("status", 200);
			 map.put("role", role);
		}
    	RedisUtil.StringOps.set("role",new Gson().toJson(map));
        return new Gson().toJson(map);
    }
    @GetMapping(value="/role/findRoleList")
    public String findRoleList(HttpServletRequest request){
    	List<Role> list = new LinkedList<Role>();
    	list = roleDao.findRoleList();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("roleList", new LinkedList<Role>());
		}else {
			map.put("status", 200);
			 map.put("roleList", list);
		}
    	RedisUtil.StringOps.set("roleList",new Gson().toJson(map));
        return new Gson().toJson(map);
    }
    
    @GetMapping(value="/role/updateRole")
    public String updateRole(Role role){
    	Role newrole= roleDao.updateRole(role);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("responseParam", "查询结束");
         if (newrole==null) {
         	map.put("status", 202);
         	map.put("role", new Role());
 		}else {
 			map.put("status", 200);
 			 map.put("role", newrole);
 		}
    	RedisUtil.StringOps.set("role", new Gson().toJson(map));
    	return new Gson().toJson(map);
    }
 
    @GetMapping(value="/role/deleteRole")
    public void deleteRoleById(String role_id){
    	roleDao.deleteRoleById(role_id);
        RedisUtil.StringOps.set("role", new String());
        System.out.println("In redis role is " + RedisUtil.StringOps.get("role"));
    }
}