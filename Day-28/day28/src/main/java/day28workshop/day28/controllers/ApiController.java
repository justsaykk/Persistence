package day28workshop.day28.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day28workshop.day28.services.AggregateServices;
import day28workshop.day28.services.GameServices;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private AggregateServices aggregateService;

    @Autowired
    private GameServices gameServices;

    @GetMapping(path = "/game/{game_id}/reviews")
    public ResponseEntity<String> getGameReviewSummary(
            @PathVariable(name = "game_id") String gameId) {

        int gid = Integer.parseInt(gameId);

        // Check for game existance
        if (!gameServices.gameExists(gid))
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        JsonObjectBuilder jobResponse = aggregateService.getGameSummary(gid);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        jobResponse.add("timestamp", timestamp.toString());

        return new ResponseEntity<String>(jobResponse.build().toString(), HttpStatus.OK);

    }

    @GetMapping(path = "/games/{highlow}")
    public ResponseEntity<String> getHighLow(
            @PathVariable(name = "highlow") String highLow) {
        // Convert High/Low to Boolean for passing
        Boolean highest;
        switch (highLow) {
            case "highest":
                highest = true;
                break;
            case "lowest":
                highest = false;
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("highest >> " + highest);
        
        JsonArrayBuilder jabResponse = aggregateService.getGameByRating(highest);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        JsonObjectBuilder responseJob = Json.createObjectBuilder()
                .add("rating", highLow)
                .add("games", jabResponse)
                .add("timestamp", timestamp.toString());
        return new ResponseEntity<String>(responseJob.build().toString(), HttpStatus.OK);
    }
}
