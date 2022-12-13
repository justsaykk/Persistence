package day27workshop.workshop.Repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo {

    @Autowired
    private MongoTemplate template;

    public Boolean gameExists(int gid) {
        Criteria criteria = Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);
        return template.exists(query, "Games");
    }

    public String getGameName(int gid) {
        Criteria criteria = Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);
        Document game = template.findOne(query, Document.class, "Games");
        return game.getString("name");
    }
}
