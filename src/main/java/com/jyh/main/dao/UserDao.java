package com.jyh.main.dao;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.jyh.main.modle.User;

 
@Component
public class UserDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public User findUserByName(String username,String passWord) {
        Query query=new Query(Criteria.where("username").is(username).and("password").is(passWord));
        User user =  mongoTemplate.findOne(query , User.class);
        return user;
    }
 
    /**
     * 更新对象
     */
    public User updateUser(User user) {
        Query query=new Query(Criteria.where("username").is(user.getUsername()));
        //更新数据库
        Update update= new Update().set("password", user.getPassWord())
        		.set("name", user.getName())
        		.set("nikeName", user.getNikeName())
        		.set("ranking", user.getRanking())
        		.set("reMark", user.getReMark())
        		.set("icoPath", user.getIcoPath())
        		.set("summarise", user.getSummarise())
        		.set("salary", user.getSalary())
        		.set("age",user.getAge())
        		.set("role_id", user.getRole_id())
        		.set("p_id", user.getP_id())
        		;
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,User.class);
        return user;
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deleteUserById(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query,User.class);
    }

	public User findUserByName(String username) {
		// TODO Auto-generated method stub
		Query query=new Query(Criteria.where("username").is(username));
        User user =  mongoTemplate.findOne(query , User.class);
        return user;
	}
}
