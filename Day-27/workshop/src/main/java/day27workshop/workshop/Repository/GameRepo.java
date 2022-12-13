package day27workshop.workshop.Repository;

import java.util.Optional;

// import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import day27workshop.workshop.Models.GameDetail;

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
        Optional<GameDetail> game = Optional.ofNullable(
                template.findOne(query, GameDetail.class, "Games"));

        if (game.isEmpty()) {
            return "null";
        }

        return game.get().getName();
    }
}
