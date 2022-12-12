package workshop26.day26workshop.repository;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import workshop26.day26workshop.model.Games;

@Repository
public class RepoOperations {

    @Autowired
    private MongoTemplate repo;

    public List<Games> getGames(Integer limit, Integer offset) {

        Query query = new Query();
        List<Document> result = repo.find(query.skip(offset).limit(limit), Document.class, "Games");
        List<Games> convertedList = new LinkedList<>();

        for (Document gamesDoc : result) {
            convertedList.add(new Games(gamesDoc));
        }

        return convertedList;
    }

    public long getCount(String collection) {
        return repo.count(new Query(), collection);
    }

}
