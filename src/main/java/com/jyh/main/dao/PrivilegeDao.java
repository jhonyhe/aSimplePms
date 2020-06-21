package com.jyh.main.dao;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.jyh.main.modle.Privilege;

 
@Component
public class PrivilegeDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void savePrivilege(Privilege privilege) {
        mongoTemplate.save(privilege);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public Privilege findPrivilegeById(String p_id) {
        Query query=new Query(Criteria.where("p_id").is(p_id));
        Privilege privilege =  mongoTemplate.findOne(query , Privilege.class);
        return privilege!=null?privilege:new Privilege();
    }
 
    /**
     * 更新对象
     */
    public Privilege updatePrivilege(Privilege privilege) {
        Query query=new Query(Criteria.where("p_id").is(privilege.getP_id()));
        //更新数据库
        Update update= new Update().set("role_name", privilege.getP_name());
        mongoTemplate.updateFirst(query,update,Privilege.class);
        query=new Query(Criteria.where("role_id").is(privilege.getP_id()));
        Privilege newpPrivilege=mongoTemplate.findOne(query,Privilege.class);
        return newpPrivilege;
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deletePrivilegeById(String p_id) {
        Query query=new Query(Criteria.where("p_id").is(p_id));
        mongoTemplate.remove(query,Privilege.class);
    }
}
