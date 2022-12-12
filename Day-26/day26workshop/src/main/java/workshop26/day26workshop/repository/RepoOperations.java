package workshop26.day26workshop.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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
        return result.stream().map(
                gamesDoc -> new Games(gamesDoc))
                .toList();
    }

    public long getCount(String collection) {
        return repo.count(new Query(), collection);
    }

    public List<Games> getGamesAsc(Integer limit, Integer offset) {
        Query query = new Query();
        List<Document> result = repo.find(
                query.with(
                        Sort.by(Sort.Direction.ASC, "ranking"))
                        .skip(offset)
                        .limit(limit),
                Document.class, "Games");

        return result.stream().map(
                gamesDoc -> new Games(gamesDoc))
                .toList();
    }

    public Optional<Document> findById(ObjectId docId) {
        return Optional.ofNullable(repo.findById(docId, Document.class, "Games"));
    }

    public Optional<Games> findByRank(String rank) {
        Query query = Query.query(
            Criteria.where("ranking").is(Integer.parseInt(rank))
        );
        return Optional.ofNullable(
            repo.findOne(query, Games.class, "Games"));
            // For this to work, the entityClass needs to be the model class, the keys need to match the class variables exactly.
            // additionally, there should be a noArgsConstructor for said model.
    }
}
