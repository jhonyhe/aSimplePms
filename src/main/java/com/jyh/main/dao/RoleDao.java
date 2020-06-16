package com.jyh.main.dao;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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
       /* LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "查询结束");
        if (role==null) {
        	map.put("status", 202);
            map.put("responseParam", "查询结束");
        	map.put("role", role==null?new Role():role);
		}else {
			map.put("status", 200);
	        map.put("responseParam", "查询结束");
			 map.put("role", role);
		}
       
        return new Gson().toJson(map);
        */
        return role!=null?role:new Role();
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
        
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
       /* LinkedHashMap<Object,Object> map = new LinkedHashMap<Object, Object>();
        map.put("status", 200);
        map.put("responseParam", "更新结束");
        */
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
