package practice.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo {

    @Autowired
    MongoTemplate template;

    public List<Document> getAllGames(Integer offset) {
        int limit = 50;
        int skip = limit * (offset - 1);
        Query query = Query.query(new Criteria()).limit(limit).skip(skip);
        return template.find(query, Document.class, "Games");
    }

    public Document getGame(Integer gameId) {
        Criteria criteria = Criteria.where("gid").is(gameId);
        Query query = Query.query(criteria);
        return template.findOne(query, Document.class, "Games");
    }

}
