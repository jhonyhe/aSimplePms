
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.RoleDao;
import com.jyh.main.modle.Role;
import com.jyh.main.util.CloseUtil;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class RoleController {
 
    @Autowired
    private RoleDao roleDao;
    
    
    @GetMapping(value="/role/addRole")
    public String saveRole(Role role,Request request) throws Exception {
    	try {
    		roleDao.saveRole(role);
		} catch (Exception e) {
			System.out.println(e.toString());
			return "error";
		}
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "保存角色成功");
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/role/findRole")
    public String findRoleById(String role_id,HttpServletRequest request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	Role role = roleDao.findRoleById(role_id);
        map.put("responseParam", "角色查询结束");
        if (role==null) {
        	map.put("status", 202);
        	map.put("role", new Role());
		}else {
			map.put("status", 200);
			 map.put("role", role);
		}
        	RedisUtil.StringOps.set("role",new Gson().toJson(map));
		} catch (Exception e) {
			System.out.println(e.toString());
			if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
				}
		}
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    @GetMapping(value="/role/findRoleList")
    public String findRoleList(HttpServletRequest request){
    	List<Role> list = new LinkedList<Role>();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	list = roleDao.findRoleList();
        map.put("responseParam", "角色列表查询结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("roleList", new LinkedList<Role>());
		}else {
			map.put("status", 200);
			 map.put("roleList", list);
		}
        	RedisUtil.StringOps.set("roleList",new Gson().toJson(map));
		} catch (Exception e) {
			System.out.println(e.toString());
			if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
				}
		}
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    
    @GetMapping(value="/role/updateRole")
    public String updateRole(Role role,Request request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	Role newrole= roleDao.updateRole(role);
         map.put("responseParam", "角色更新结束");
         if (newrole==null) {
         	map.put("status", 202);
         	map.put("role", new Role());
 		}else {
 			map.put("status", 200);
 			 map.put("role", newrole);
 		}
        	RedisUtil.StringOps.set("role", new Gson().toJson(map));
		} catch (Exception e) {
			System.out.println(e.toString());
			if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
				}
		}
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/role/deleteRole")
    public String deleteRoleById(String role_id,Request request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	roleDao.deleteRoleById(role_id);
        map.put("responseParam", "角色删除结束");
        map.put("status", 200);
        	RedisUtil.StringOps.set("role", new String());
		} catch (Exception e) {
			System.out.println(e.toString());
			if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
				}
		}
        System.out.println("In redis role is " + RedisUtil.StringOps.get("role"));
        String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
}