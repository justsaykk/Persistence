package day27workshop.workshop.Services;

import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day27workshop.workshop.Models.EditedReview;
import day27workshop.workshop.Models.ExistingReview;
import day27workshop.workshop.Models.NewReview;
import day27workshop.workshop.Repository.GameRepo;
import day27workshop.workshop.Repository.ReviewRepo;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

@Service
public class ReviewServices {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    public Optional<NewReview> postNewReview(NewReview newReview) {
        // Check if gid exists
        int gid = newReview.getGid();
        if (!gameRepo.gameExists(gid)) {
            System.out.println("GID not exists");
            return Optional.empty();
        }

        // Get & Set game name
        String gameName = gameRepo.getGameName(gid);
        newReview.setName(gameName);
        return Optional.of(reviewRepo.postReview(newReview));
    }

    public Integer updateReview(String reviewId, EditedReview editedReview) {
        // Check if review exits
        if (!reviewRepo.reviewExists(reviewId)) {
            System.out.println("ReviewID not exists");
            return 0;
        }
        return reviewRepo.updateReview(reviewId, editedReview);
    }

    public JsonObjectBuilder getOneReview(String reviewId) {
        Optional<Document> reviewDocOpt = reviewRepo.getOne(reviewId);

        if (reviewDocOpt.isEmpty()) {
            return Json.createObjectBuilder()
                    .add("message", "Game not found");
        }
        Document reviewDoc = reviewDocOpt.get();
        ExistingReview existingReview = new ExistingReview(reviewDoc);
        existingReview.setName(reviewDoc.getString("name"));
        Boolean isEdited = reviewDoc.containsKey("edited");
        return existingReview.toJOB().add("edited", isEdited);
    }

    public String getOneRHistory(String c_id) {
        Optional<Document> reviewDocOpt = reviewRepo.getOne(c_id);

        if (reviewDocOpt.isEmpty()) {
            return Json.createObjectBuilder()
                    .add("message", "Game not found")
                    .build()
                    .toString();
        }

        Document reviewDoc = reviewDocOpt.get();
        return reviewDoc.toJson();
    }
}
