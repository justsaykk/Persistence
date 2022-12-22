package practice.persistence.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Comment {
    private ObjectId _id;
    private String c_id;
    private String user;
    private int rating;
    private String c_text;
    private int gid;

    public Comment(Document d) {
        this._id = d.getObjectId("_id");
        this.c_id = d.getString("c_id");
        this.user = d.getString("user");
        this.rating = d.getInteger("rating");
        this.c_text = d.getString("c_text");
        this.gid = d.getInteger("gid");
    }
}
