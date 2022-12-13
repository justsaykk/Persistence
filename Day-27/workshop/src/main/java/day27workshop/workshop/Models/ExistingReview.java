package day27workshop.workshop.Models;

import org.bson.Document;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExistingReview {
    private String c_id;
    private String user;
    private int rating;
    private String c_text;
    private int gid;
    private String name;

    public ExistingReview(Document existingReviewDoc) {
        this.c_id = existingReviewDoc.getString("c_id");
        this.user = existingReviewDoc.getString("user");
        this.rating = existingReviewDoc.getInteger("rating");
        this.c_text = existingReviewDoc.getString("c_text");
        this.gid = existingReviewDoc.getInteger("gid");
    }
}
