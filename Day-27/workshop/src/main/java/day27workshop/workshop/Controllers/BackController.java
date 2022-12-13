package day27workshop.workshop.Controllers;

import java.util.Optional;

// import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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

@RestController
@RequestMapping(path = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackController {

    @Autowired
    private ReviewServices reviewServices;

    @PostMapping
    public ResponseEntity<String> newReview(
            @RequestBody MultiValueMap<String, String> form) {
        NewReview newReview = new NewReview(form);
        // Instant instant = new Instant();
        // newReview.setPosted(instant.toDateTime());
        // System.out.println(instant.toDateTime().toString());

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
}
