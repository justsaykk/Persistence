package workshop26.day26workshop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
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

    public Optional<JsonObject> getGameById(String gameId) {
        return null;
    }

    public JsonArray getGamesByRank(Integer limit, Integer offset) {
        return null;
    }

    public Long getCount(String collection) {
        return repo.getCount(collection);
    }

}
