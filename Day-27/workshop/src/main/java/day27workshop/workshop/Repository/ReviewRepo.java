package day27workshop.workshop.Repository;

import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import day27workshop.workshop.Models.Comment;
import day27workshop.workshop.Models.EditedReview;
import day27workshop.workshop.Models.ExistingReview;
import day27workshop.workshop.Models.NewReview;

@Repository
public class ReviewRepo {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private GameRepo gameRepo;

    public NewReview postReview(NewReview newReview) {
        return template.insert(newReview, "Comments");
    }

    public Boolean reviewExists(String reviewId) {
        Criteria criteria = Criteria.where("c_id").is(reviewId);
        Query query = Query.query(criteria);
        return template.exists(query, "Comments");
    }

    public Integer updateReview(String reviewId, EditedReview editedReview) {
        // Get gid from review and get game name from game db
        Criteria criteria = Criteria.where("c_id").is(reviewId);
        Query query = Query.query(criteria);
        Optional<Document> existingReviewOpt = Optional.ofNullable(
                template.findOne(query, Document.class, "Comments"));

        if (existingReviewOpt.isEmpty())
            return 0;

        // Set game name
        Document existingReviewDoc = existingReviewOpt.get();
        ExistingReview existingReview = new ExistingReview(existingReviewDoc);
        existingReview.setName(gameRepo.getGameName(existingReview.getGid()));

        // Isolate old comment and rating
        Comment oldComment = new Comment(existingReview.getC_text(), existingReview.getRating());

        Update updateOps = new Update()
                .set("c_text", editedReview.getComment())
                .set("rating", editedReview.getRating())
                .set("name", existingReview.getName())
                // .push will create the array in MongoDB. No need to instantiate new arraylist
                .push("edited", oldComment);

        UpdateResult updateResult = template.updateFirst(query, updateOps, "Comments");
        int updatedCount = (int) updateResult.getModifiedCount();
        return updatedCount;
    }

    public Optional<Document> getOne(String reviewId) {
        Criteria criteria = Criteria.where("c_id").is(reviewId);
        Query query = Query.query(criteria);
        return Optional.ofNullable(
                template.findOne(query, Document.class, "Comments"));
    }
}
