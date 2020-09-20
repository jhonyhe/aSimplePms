
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void saveSalary(Salary salary) throws Exception {
    	salaryDaoDao.saveSalary(salary);
    }
    
    @GetMapping(value = "/salary/getSalary")
    public String findSalaryByName(String s_id,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("s_id = " + s_id );
    	Salary salary = salaryDaoDao.findSalaryByName(s_id);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (salary==null) {
        	map.put("status", 202);
        	map.put("salary", new Salary());
		}else {
			map.put("status", 200);
			 map.put("salary", salary);
		}
    	RedisUtil.StringOps.set("salary", new Gson().toJson(map));
        return new Gson().toJson(map);
    }
    
    @GetMapping(value="/salary/updateSalary")
    public String updateUser(Salary salary){
    	Salary salary2= salaryDaoDao.updateSalary(salary);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("status", 200);
         map.put("responseParam", "更新结束");
         map.put("salary", salary2);
    	RedisUtil.StringOps.set("salary", new Gson().toJson(map));
    	return new Gson().toJson(map);
    }
 
    @GetMapping(value="/salary/deleteSalary")
    public void deleteSalaryById(String s_id){
        salaryDaoDao.deleteSalaryById(s_id);
        RedisUtil.StringOps.set("salary", new String());
        System.out.println("In redis salary is " + RedisUtil.StringOps.get("salary"));
    }
}