
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.*;
import com.jyh.main.modle.*;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class SalaryController {
 
    @Autowired
    private SalaryDao salaryDaoDao;
    
    @GetMapping(value="/salary/addSalary")
    public String saveSalary(Salary salary,Request request) throws Exception {
    	salaryDaoDao.saveSalary(salary);
    	String callback = request.getParameter("callback");    
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "保存成功");
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    
    @GetMapping(value = "/salary/findSalary")
    public String findSalaryByName(String s_id,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("s_id = " + s_id );
    	Salary salary = salaryDaoDao.findSalaryByName(s_id);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询工资结束");
        if (salary==null) {
        	map.put("status", 202);
        	map.put("salary", new Salary());
		}else {
			map.put("status", 200);
			 map.put("salary", salary);
		}
        String callback = request.getParameter("callback"); 
    	RedisUtil.StringOps.set("salary", new Gson().toJson(map));
    	String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    @GetMapping(value="/salary/findSalaryList")
    public String findSalaryList(HttpServletRequest request){
    	List<Salary> list = new LinkedList<Salary>();
    	list = salaryDaoDao.findSalaryList();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询工资列表结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("salaryList", new LinkedList<Salary>());
		}else {
			map.put("status", 200);
			 map.put("salaryList", list);
		}
    	RedisUtil.StringOps.set("salaryList",new Gson().toJson(map));
    	String callback = request.getParameter("callback"); 
    	String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    @GetMapping(value="/salary/updateSalary")
    public String updateUser(Salary salary,Request request){
    	Salary salary2= salaryDaoDao.updateSalary(salary);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        if(salary2.equals(salary)) {
    	 map.put("status", 200);
        }else {
        	map.put("status", 202);
		}
         map.put("responseParam", "更新工资结束");
         map.put("salary", salary2);
    	RedisUtil.StringOps.set("salary", new Gson().toJson(map));
    	String callback = request.getParameter("callback"); 
    	String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/salary/deleteSalary")
    public String deleteSalaryById(String s_id,Request request){
        salaryDaoDao.deleteSalaryById(s_id);
        RedisUtil.StringOps.set("salary", new String());
        LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        System.out.println("In redis salary is " + RedisUtil.StringOps.get("salary"));
        map.put("status", 200);
        map.put("responseParam", "删除工资结束");
        RedisUtil.StringOps.set("salary", new Gson().toJson(map));
        String callback = request.getParameter("callback"); 
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
       return retStr;
    }
}