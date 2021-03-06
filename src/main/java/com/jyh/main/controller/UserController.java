
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.PositionDao;
import com.jyh.main.dao.PrivilegeDao;
import com.jyh.main.dao.RoleDao;
import com.jyh.main.dao.SalaryDao;
import com.jyh.main.dao.UserDao;
import com.jyh.main.modle.Position;
import com.jyh.main.modle.Privilege;
import com.jyh.main.modle.Role;
import com.jyh.main.modle.Salary;
import com.jyh.main.modle.User;
import com.jyh.main.util.CloseUtil;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class UserController {
 
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private PrivilegeDao privilegeDao;
    
    @Autowired
    private PositionDao positionDao;
    
    @Autowired
    private SalaryDao salaryDao;
    
    @GetMapping(value="/user/addUser")
    public String saveUser(User user,Request request) throws Exception {
    	try {
    		userDao.saveUser(user);
		} catch (Exception e) {
			System.out.println(e.toString());
			return "error";
		}
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "保存用户成功");
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
    
    @GetMapping(value = "/success")
    public String success() throws Exception {
    	System.out.println("success");
    	return "success";
    }
    
    @GetMapping(value = "/error")
    public String error() throws Exception {
    	System.out.println("error");
    	return "error";
    }
    
    @GetMapping(value = "/unauthorized")
    public String unauthorized() throws Exception {
    	System.out.println("unauthorized");
    	 return "unauthorized";
    }
    
    @GetMapping(value = "/user/findUserList")
    public String findUserList(Request request) {
    	
    	
    	List<User> list = new LinkedList<User>();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	list = userDao.findUserList();
        map.put("responseParam", "查询用户列表结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("userList", new LinkedList<User>());
		}else {
			map.put("status", 200);
			 map.put("userList", list);
		}
        	RedisUtil.StringOps.set("userList",new Gson().toJson(map));
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
    
    @GetMapping(value = "/user/findUserByName")
    public String findUserByName(String username,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("username = " + username );
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	User user = userDao.findUserByName(username);
    	Role role= new Role();
    	Privilege privilege= new Privilege();
    	Salary salary = new Salary();
    	Position position = new Position();
    	if(!(user==null)) {
    		role = roleDao.findRoleById(user.getR_id());
    		privilege = privilegeDao.findPrivilegeById(user.getP_id());
    		salary = salaryDao.findSalaryByName(user.getS_id());
    		position = positionDao.findPositionById(user.getPos_id());
    	}
        map.put("responseParam", "查询用户结束");
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
        if (salary==null) {
        	map.put("status", 202);
        	map.put("salary", new Salary());
		}else {
			map.put("status", 200);
			 map.put("salary", salary);
		}
        if (position==null) {
        	map.put("status", 202);
        	map.put("position", new Position());
		}else {
			map.put("status", 200);
			 map.put("position", position);
		}
        
        	RedisUtil.StringOps.set("user", new Gson().toJson(map));
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
    
    @RequestMapping(value="/login")
    public String findUserByName(String username,String passWord,HttpServletRequest request,HttpSession httpSession){
    	System.out.println("username = " + username + " passWord = "+passWord);
    	request.getSession().setMaxInactiveInterval(120*60);
    	User user = new User();
    	user.setUsername(username);
    	user.setPassWord(passWord);
    	Role role = new Role();
    	Privilege privilege = new Privilege();
    	Salary salary = new Salary();
    	Position position = new Position();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>(); 
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassWord());
         // 获取 subject 认证主体
         Subject subject = SecurityUtils.getSubject();
         try{
             // 开始认证，这一步会跳到我们自定义的 Realm 中
             subject.login(token);
             request.getSession().setAttribute("user", user);
             user = userDao.findUserByName(username, passWord);
         	 role= roleDao.findRoleById(user.getR_id()) ;
         	 privilege= privilegeDao.findPrivilegeById(user.getP_id());
         	 salary = salaryDao.findSalaryById(user.getUsername(), user.getS_id());
         	 position = positionDao.findPositionById(user.getPos_id());
         	 map.put("responseParam", "登录成功");
         	 map.put("status", 200);
         	 map.put("user", user);
         	 map.put("privilege", privilege);
         	 map.put("role", role);
         	 map.put("salary", salary);
         	 map.put("position", position);
             RedisUtil.StringOps.set("user", new Gson().toJson(map));
             String callback = request.getParameter("callback");    
             String retStr = callback + "("+new Gson().toJson(map)+")";
             return retStr;
         }catch(Exception e){
        	 //验证未通过
        	 System.out.println(e.toString());
        	 if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
        		 CloseUtil.close();
        		 return "error";
			}else {
	             user = new User();
	             request.getSession().setAttribute("user", user);
	             request.setAttribute("error", "用户名或密码错误！");
	             map.put("responseParam", "登录失败");
	         	 map.put("status", 202);
	         	 map.put("user", user);
	         	 map.put("privilege", privilege);
	         	 map.put("role", privilege);
	         	 map.put("salary", salary);
	         	 map.put("position", position);
	             RedisUtil.StringOps.set("user", new Gson().toJson(map));
	             String callback = request.getParameter("callback");    
	             String retStr = (null==callback||callback.equals(""))?"fail":callback + "("+new Gson().toJson(map)+")";
	             return retStr;
			}
         }
    }
 
    
    @GetMapping(value="/user/updateUser")
    public String updateUser(User user,Request request){
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	User users= userDao.updateUser(user);
         map.put("status", 200);
         map.put("responseParam", "更新结束");
         map.put("user", users);
    		RedisUtil.StringOps.set("user", new Gson().toJson(map));
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
 
    @GetMapping(value="/user/deleteUser")
    public String deleteUserById(String username,Request request){
    	try {
    		userDao.deleteUserById(username);
        	RedisUtil.StringOps.set("user", new String());
		} catch (Exception e) {
			System.out.println(e.toString());
			if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
			}
		}
        System.out.println("In redis user is " + RedisUtil.StringOps.get("user"));
        LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "删除成功");
        String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
}