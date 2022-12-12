package workshop26.day26workshop.controller;

import java.util.Optional;

import org.bson.Document;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import workshop26.day26workshop.model.GameQuery;
import workshop26.day26workshop.model.Games;
import workshop26.day26workshop.service.GamesService;

@RestController
@RequestMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GamesController {

    @Autowired
    private GamesService gamesSvc;

    @GetMapping
    public ResponseEntity<String> getGames(
            @RequestParam(name = "limit", defaultValue = "25") String strLimit,
            @RequestParam(name = "offset", defaultValue = "0") String strOffset) {

        Integer limit = Integer.parseInt(strLimit);
        Integer offset = Integer.parseInt(strOffset);
        Instant instant = Instant.now();

        JsonArrayBuilder gamesArr = gamesSvc.getGames(limit, offset);
        Long collectionCount = gamesSvc.getCount("Games");
        GameQuery response = new GameQuery(
                strOffset,
                strLimit,
                collectionCount,
                instant.toString(),
                gamesArr);
        return new ResponseEntity<String>(response.toJSON().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/rank")
    public ResponseEntity<String> getGamesByRank(
            @RequestParam(name = "limit", defaultValue = "25") String strLimit,
            @RequestParam(name = "offset", defaultValue = "0") String strOffset) {

        Integer limit = Integer.parseInt(strLimit);
        Integer offset = Integer.parseInt(strOffset);
        Instant instant = Instant.now();

        JsonArrayBuilder gamesArr = gamesSvc.getGamesByRank(limit, offset);
        Long collectionCount = gamesSvc.getCount("Games");
        GameQuery response = new GameQuery(
                strOffset,
                strLimit,
                collectionCount,
                instant.toString(),
                gamesArr);
        return new ResponseEntity<String>(response.toJSON().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/{gameId}")
    public ResponseEntity<String> getGamesById(
            @PathVariable("gameId") String gameId) {
        Optional<Document> dbResponse = gamesSvc.getGameById(gameId);

        if (dbResponse.isEmpty()) {
            return new ResponseEntity<String>(Json.createObjectBuilder()
                    .add("message", "No Games Found")
                    .build().toString(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(new Games(dbResponse.get()).toJSON().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/rank/{rank}")
    public ResponseEntity<String> getGamesByRank(
            @PathVariable("rank") String rank) {
        Optional<Games> dbResponse = gamesSvc.getGameByRank(rank);

        if (dbResponse.isEmpty()) {
            return new ResponseEntity<String>(Json.createObjectBuilder()
                    .add("message", "No Games Found")
                    .build().toString(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(dbResponse.get().toJSON().toString(), HttpStatus.OK);
    }
}