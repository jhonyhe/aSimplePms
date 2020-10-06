
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
import com.jyh.main.dao.PositionDao;
import com.jyh.main.modle.Position;
import com.jyh.main.util.CloseUtil;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class PositionController {
 
    @Autowired
    private PositionDao positionDao;
    
    
    @GetMapping(value="/position/addPosition")
    public String savePosition(Position position,Request request) throws Exception {
    	try {
    		positionDao.savePosition(position);
		} catch (Exception e) {
			System.out.println(e.toString());
			return "error";
		}
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "保存职位成功");
    	String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
 
    @GetMapping(value="/position/findPosition")
    public String findPositionById(String pos_id,HttpServletRequest request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	Position position = positionDao.findPositionById(pos_id);
        map.put("responseParam", "职位查询结束");
        if (position==null) {
        	map.put("status", 202);
        	map.put("position", new Position());
		}else {
			map.put("status", 200);
			 map.put("position", position);
		}
        	RedisUtil.StringOps.set("position",new Gson().toJson(map));
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
 
    @GetMapping(value="/position/findPositionList")
    public String findPositionList(HttpServletRequest request){
    	List<Position> list = new LinkedList<Position>();
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	list = positionDao.findPositionList();
        map.put("responseParam", "职位列表查询结束");
        if (list==null) {
        	map.put("status", 202);
        	map.put("positionList", new LinkedList<Position>());
		}else {
			map.put("status", 200);
			 map.put("positionList", list);
		}
        	RedisUtil.StringOps.set("positionList",new Gson().toJson(map));
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
    
    @GetMapping(value="/position/updatePosition")
    public String updateRole(Position position,Request request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	Position newposition= positionDao.updatePosition(position);
         map.put("responseParam", "职位更新结束");
         if (newposition==null) {
         	map.put("status", 202);
         	map.put("position", new Position());
 		}else {
 			map.put("status", 200);
 			 map.put("position", newposition);
 		}
        	RedisUtil.StringOps.set("position", new Gson().toJson(map));
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
 
    @GetMapping(value="/position/deletePosition")
    public String deletePositionById(String pos_id,Request request){
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
    	try {
    	positionDao.deletePositionById(pos_id);
        map.put("responseParam", "职位删除结束");
        map.put("status", 200);
        	RedisUtil.StringOps.set("position", new String());
        }catch (Exception e) {
        	System.out.println(e.toString());
        	if (e instanceof org.springframework.data.redis.RedisConnectionFailureException) {
	       		 CloseUtil.close();
	       		 return "error";
				}
		}
        System.out.println("In redis position is " + RedisUtil.StringOps.get("position"));
        String callback = request.getParameter("callback");    
        String retStr = (callback!=null||"".equals(callback))?callback:"fail" + "("+new Gson().toJson(map)+")";
        return retStr;
    }
}