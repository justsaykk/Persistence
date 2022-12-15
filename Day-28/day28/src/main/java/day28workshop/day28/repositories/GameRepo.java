package day28workshop.day28.repositories;

import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import day28workshop.day28.models.Game;

@Repository
public class GameRepo {

    @Autowired
    private MongoTemplate repo;

    public Boolean gameExists(int gid) {
        Criteria criteria = Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);
        return repo.exists(query, "Games");
    }

    public Optional<Game> getGame(int gid) {
        // Create Critera
        Criteria criteria = Criteria.where("gid").is(gid);

        // Query DB
        Query query = Query.query(criteria);
        Document queryResult = repo.findOne(query, Document.class, "Games");

        return Optional.ofNullable(new Game(queryResult));
    }

}
