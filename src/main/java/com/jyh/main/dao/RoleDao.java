package com.jyh.main.dao;
 
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.jyh.main.modle.Privilege;
import com.jyh.main.modle.Role;

 
@Component
public class RoleDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void saveRole(Role role) {
        mongoTemplate.save(role);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public Role findRoleById(String role_id) {
        Query query=new Query(Criteria.where("role_id").is(role_id));
        Role role =  mongoTemplate.findOne(query , Role.class);
        return role!=null?role:new Role();
    }
    
    public List<Role> findRoleList(){
    	List<Role> list = new LinkedList<Role>();
    	Query query = new Query(Criteria.byExample(new Role()));
    	list = mongoTemplate.find(query,Role.class);
    	return list;
    }
    /**
     * 更新对象
     */
    public Role updateRole(Role role) {
        Query query=new Query(Criteria.where("role_id").is(role.getRole_id()));
        //更新数据库
        Update update= new Update().set("role_name", role.getRole_name())
        		;
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,Role.class);
        query=new Query(Criteria.where("role_id").is(role.getRole_id()));
        Role newrole=mongoTemplate.findOne(query,Role.class);
        return newrole;
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deleteRoleById(String role_id) {
        Query query=new Query(Criteria.where("role_id").is(role_id));
        mongoTemplate.remove(query,Role.class);
    }
}
