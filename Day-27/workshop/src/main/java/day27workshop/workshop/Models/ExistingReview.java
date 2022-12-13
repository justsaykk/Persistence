package day27workshop.workshop.Models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
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

    public JsonObjectBuilder toJOB() {
        return Json.createObjectBuilder()
                .add("c_id", this.c_id)
                .add("user", this.user)
                .add("rating", Integer.toString(this.rating))
                .add("c_text", this.c_text)
                .add("gid", Integer.toString(this.gid))
                .add("name", this.name);
    }
}
