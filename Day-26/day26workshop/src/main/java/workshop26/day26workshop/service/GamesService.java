package workshop26.day26workshop.service;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import workshop26.day26workshop.model.Games;
import workshop26.day26workshop.repository.RepoOperations;

@Service
public class GamesService {

    @Autowired
    private RepoOperations repo;

    public JsonArrayBuilder getGames(Integer limit, Integer offset) {
        List<Games> queryResult = repo.getGames(limit, offset);
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Games games : queryResult) {
            jab.add(games.toMiniJson());
        }
        return jab;
    }

    public JsonArrayBuilder getGamesByRank(Integer limit, Integer offset) {
        List<Games> queryResult = repo.getGamesAsc(limit, offset);
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Games games : queryResult) {
            jab.add(games.toMiniJson());
        }
        return jab;
    }

    public Long getCount(String collection) {
        return repo.getCount(collection);
    }

    public Optional<Document> getGameById(String gameId) {
        ObjectId docId = new ObjectId(gameId);
        return repo.findById(docId);
    }

    public Optional<Games> getGameByRank(String rank) {
        return repo.findByRank(rank);
    }
}
