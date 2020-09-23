package com.jyh.main.dao;
 
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.jyh.main.modle.*;

 
@Component
public class PositionDao {
 
 
    @Autowired
    private MongoTemplate mongoTemplate;
 
    /**
     * 创建对象
     */
    public void savePosition(Position position) {
        mongoTemplate.save(position);
    }
 
    /**
     * 根据用户名和密码查询对象
     * @return
     */
    public Position findPositionById(String pos_id) {
        Query query=new Query(Criteria.where("pos_id").is(pos_id));
        Position position =  mongoTemplate.findOne(query , Position.class);
        return position;
    }
    
    public List<Position> findPositionList(){
    	List<Position> list = new LinkedList<Position>();
    	Query query = new Query(Criteria.byExample(new Position()));
    	list = mongoTemplate.find(query,Position.class);
    	return list;
    }
    /**
     * 更新对象
     */
    public Position updatePosition(Position position) {
    	   Query query=new Query(Criteria.where("pos_id").is(position.getPos_id()));
        //更新数据库
        Update update= new Update().set("pos_id", position.getPos_id())
        		.set("pos_name", position.getPos_name());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,Position.class);
        return position;
    }
 
    /**
     * 删除对象
     * @param id
     */
    public void deletePositionById(String pos_id) {
        Query query=new Query(Criteria.where("pos_id").is(pos_id));
        mongoTemplate.remove(query,Position.class);
    }
}
