package practice.persistence.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Game {
    private ObjectId _id;
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;

    public Game(Document doc) {
        this._id = doc.getObjectId("_id");
        this.gid = doc.getInteger("gid");
        this.name = doc.getString("name");
        this.year = doc.getInteger("year");
        this.ranking = doc.getInteger("ranking");
        this.users_rated = doc.getInteger("users_rated");
        this.url = doc.getString("url");
        this.image = doc.getString("image");
    }
}
