package day28workshop.day28.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameRating {
    private int gid;
    private String name;
    private int rating;
    private String user;
    private String comment;
    private String cid;

    public GameRating(Document resultDoc) {
        this.gid = resultDoc.getInteger("gid");
        this.name = resultDoc.getString("name");
        this.rating = resultDoc.getInteger("rating");
        this.user = resultDoc.getString("user");
        this.comment = resultDoc.getString("c_text");
        this.cid = resultDoc.getString("c_id");
    }

    public JsonObjectBuilder toJob() {
        return Json.createObjectBuilder()
                .add("_id", Integer.toString(this.gid))
                .add("name", this.name)
                .add("rating", Integer.toString(this.rating))
                .add("user", this.user)
                .add("comment", this.comment)
                .add("review_id", this.cid);
    }
}
