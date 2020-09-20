
package com.jyh.main.controller;
 

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jyh.main.dao.PositionDao;
import com.jyh.main.modle.Position;
import com.jyh.main.util.RedisUtil;
 
@RestController
public class PositionController {
 
    @Autowired
    private PositionDao positionDao;
    
    
    @GetMapping(value="/position/savePosition")
    public void savePosition(Position position) throws Exception {
    	positionDao.savePosition(position);
    }
 
    @GetMapping(value="/position/findPosition")
    public String findPositionById(String pos_id,HttpServletRequest request){
    	System.out.println("pos_id = " + pos_id);
    	Position position = positionDao.findPositionById(pos_id);
    	LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("responseParam", "查询结束");
        if (position==null) {
        	map.put("status", 202);
        	map.put("position", new Position());
		}else {
			map.put("status", 200);
			 map.put("position", position);
		}
    	RedisUtil.StringOps.set("position",new Gson().toJson(map));
        return new Gson().toJson(map);
    }
 
    
    @GetMapping(value="/position/updatePosition")
    public String updateRole(Position position){
    	Position newposition= positionDao.updatePosition(position);
    	 LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
         map.put("responseParam", "查询结束");
         if (newposition==null) {
         	map.put("status", 202);
         	map.put("position", new Position());
 		}else {
 			map.put("status", 200);
 			 map.put("position", newposition);
 		}
    	RedisUtil.StringOps.set("position", new Gson().toJson(map));
    	return new Gson().toJson(map);
    }
 
    @GetMapping(value="/position/deletePosition")
    public void deletePositionById(String pos_id){
    	positionDao.deletePositionById(pos_id);
        RedisUtil.StringOps.set("position", new String());
        System.out.println("In redis role is " + RedisUtil.StringOps.get("position"));
    }
}