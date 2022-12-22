package practice.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepo {

    @Autowired
    MongoTemplate template;

    public List<Document> getGameComment(Integer gameId) {
        Criteria criteria = Criteria.where("gid").is(gameId);
        Query query = Query.query(criteria);
        return template.find(query, Document.class, "Comments");
    }

}
