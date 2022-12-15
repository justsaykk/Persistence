package day28workshop.day28.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day28workshop.day28.models.Game;
import day28workshop.day28.models.GameRating;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@Service
public class AggregateServices {

    @Autowired
    private GameServices gameServices;

    @Autowired
    private ReviewServices reviewServices;

    public JsonObjectBuilder getGameSummary(int gid) {

        // Get game details
        Optional<Game> gameDetailsOpt = gameServices.getGame(gid);
        if (gameDetailsOpt.isEmpty())
            return Json.createObjectBuilder().add("error", "Game not found");
        Game gameDetails = gameDetailsOpt.get();

        // Get list of reviews for game
        List<String> listOfcid = reviewServices.getReviewsforGame(gid).toJavaList();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        listOfcid.stream().forEach(cid -> jab.add(String.format("/review/%s", cid)));

        return gameDetails.toJob().add("reviews", jab);
    }

    public JsonArrayBuilder getGameByRating(Boolean highest) {
        List<GameRating> listOfGameRating = reviewServices.getGamesByRating(highest).toJavaList();
        JsonArrayBuilder jabResponse = Json.createArrayBuilder();
        listOfGameRating.stream().forEach(g -> jabResponse.add(g.toJob()));
        return jabResponse;
    }
}
