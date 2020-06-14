package com.jyh.main.dao;
 
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jyh.main.modle.User;

 
@Component
public class UserDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void saveTest(User user) {
        mongoTemplate.save(user);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public String findTestByName(String id,String passWord) {
        Query query=new Query(Criteria.where("id").is(id).and("passWord").is(passWord));
        User user =  mongoTemplate.findOne(query , User.class);
        LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "查询结束");
        if (user==null) {
        	map.put("status", 202);
            map.put("responseParam", "查询结束");
        	map.put("user", user==null?new User():user);
		}else {
			map.put("status", 200);
	        map.put("responseParam", "查询结束");
			 map.put("user", user);
		}
       
        return new Gson().toJson(map);
    }
 
    /**
     * 更新对象
     */
    public String updateTest(User user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        //更新数据库
        Update update= new Update().set("passWord", user.getPassWord())
        		.set("name", user.getName())
        		.set("nikeName", user.getNikeName())
        		.set("ranking", user.getRanking())
        		.set("reMark", user.getReMark())
        		.set("icoPath", user.getIcoPath())
        		.set("summarise", user.getSummarise())
        		.set("salary", user.getSalary())
        		.set("age",user.getAge())
        		;
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,User.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
        LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "更新结束");
        return new Gson().toJson(map);
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deleteTestById(String id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,User.class);
    }
}
