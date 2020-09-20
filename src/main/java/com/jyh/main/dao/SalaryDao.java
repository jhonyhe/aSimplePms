package com.jyh.main.dao;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.jyh.main.modle.Salary;

 
@Component
public class SalaryDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void saveSalary(Salary salary) {
        mongoTemplate.save(salary);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public Salary findSalaryById(String u_id,String s_id) {
        Query query=new Query(Criteria.where("u_id").is(u_id).and("s_id").is(s_id));
        Salary salary =  mongoTemplate.findOne(query , Salary.class);
        return salary;
    }
 
    /**
     * 更新对象
     */
    public Salary updateSalary(Salary salary) {
        Query query=new Query(Criteria.where("s_id").is(salary.getS_id()));
        //更新数据库
        Update update= new Update().set("u_id", salary.getU_id())
        		.set("p_id", salary.getP_id())
        		.set("salary", salary.getSalary())
        		.set("type", salary.getType())
        		.set("s_id", salary.getS_id())
        		;
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,Salary.class);
        return salary;
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deleteSalaryById(String s_id) {
        Query query=new Query(Criteria.where("s_id").is(s_id));
        mongoTemplate.remove(query,Salary.class);
    }

	public Salary findSalaryByName(String s_id) {
		// TODO Auto-generated method stub
		Query query=new Query(Criteria.where("s_id").is(s_id));
		Salary salary =  mongoTemplate.findOne(query , Salary.class);
        return salary;
	}
}
