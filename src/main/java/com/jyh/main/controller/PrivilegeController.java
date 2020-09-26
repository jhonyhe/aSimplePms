
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
import com.jyh.main.dao.PrivilegeDao;
import com.jyh.main.modle.Privilege;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class PrivilegeController {
 
    @Autowired
    private PrivilegeDao privilegeDao;
    
    
    @GetMapping(value="/privilege/addPrivilege")
    public String savePrivilege(Privilege privilege,Request request) throws Exception {
    	privilegeDao.savePrivilege(privilege);
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "保存权限成功");
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/privilege/findPrivilege")
    public String findPrivilegeById(String p_id,HttpServletRequest request){
    	System.out.println("p_id = " + p_id);
    	Privilege privilege = privilegeDao.findPrivilegeById(p_id);
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "权限查询结束");
        if (privilege==null) {
        	map.put("status", 202);
        	map.put("privilege", new Privilege());
		}else {
			map.put("status", 200);
			 map.put("privilege", privilege);
		}
    	RedisUtil.StringOps.set("privilege",new Gson().toJson(map));
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    @GetMapping(value="/privilege/findPrivilegeList")
    public String findPrivilegeList(HttpServletRequest request){
    	List<Privilege> list = privilegeDao.findPrivilegeList();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "权限列表查询结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("privilegeList", new LinkedList<Privilege>());
		}else {
			map.put("status", 200);
			 map.put("privilegeList", list);
		}
    	RedisUtil.StringOps.set("privilegeList",new Gson().toJson(map));
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    
    @GetMapping(value="/privilege/updatePrivilege")
    public String updatePrivilege(Privilege privilege,Request request){
    	Privilege newprivilege= privilegeDao.updatePrivilege(privilege);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("responseParam", "权限更新结束");
         if (newprivilege==null) {
         	map.put("status", 202);
         	map.put("role", new Privilege());
 		}else {
 			map.put("status", 200);
 			 map.put("role", newprivilege);
 		}
    	RedisUtil.StringOps.set("privilege", new Gson().toJson(map));
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/privilege/deletePrivilege")
    public String deletePrivilegeById(String p_id,Request request){
    	privilegeDao.deletePrivilegeById(p_id);
        RedisUtil.StringOps.set("privilege", new String());
        System.out.println("In redis privilege is " + RedisUtil.StringOps.get("privilege"));
        LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "权限删除结束");
        map.put("status", 202);
        String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
}