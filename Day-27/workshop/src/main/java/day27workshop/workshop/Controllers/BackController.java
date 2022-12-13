package day27workshop.workshop.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day27workshop.workshop.Models.EditedReview;
import day27workshop.workshop.Models.NewReview;
import day27workshop.workshop.Services.ReviewServices;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackController {

    @Autowired
    private ReviewServices reviewServices;

    @PostMapping
    public ResponseEntity<String> newReview(
            @RequestBody MultiValueMap<String, String> form) {
        NewReview newReview = new NewReview(form);

        Optional<NewReview> isPosted = reviewServices.postNewReview(newReview);
        if (isPosted.isEmpty()) {
            return new ResponseEntity<String>(
                    Json.createObjectBuilder()
                            .add("Error", "Game does not exists")
                            .build().toString(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(isPosted.get().toJSON().toString(), HttpStatus.OK);
    }

    @PutMapping(path = "/{reviewid}")
    public ResponseEntity<String> editReview(
            @PathVariable(name = "reviewid") String reviewId,
            @RequestBody EditedReview editedReview) {

        Integer updateReview = reviewServices.updateReview(reviewId, editedReview);
        return new ResponseEntity<String>(Integer.toString(updateReview), HttpStatus.OK);
    }

    @GetMapping(path = "/{reviewid}")
    public ResponseEntity<String> getOneReview(
            @PathVariable(name = "reviewid") String reviewId) {
        JsonObjectBuilder dbResponse = reviewServices.getOneReview(reviewId);
        // TO-DO
        // Try to add timestamp to this result

        return new ResponseEntity<String>(dbResponse.build().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/{reviewid}/history")
    public ResponseEntity<String> getOneHistory(
            @PathVariable(name = "reviewid") String c_id) {

        String dbResponse = reviewServices.getOneRHistory(c_id);
        // TO-DO
        // Try to add timestamp to this result
        return new ResponseEntity<String>(dbResponse, HttpStatus.OK);
    }
}
