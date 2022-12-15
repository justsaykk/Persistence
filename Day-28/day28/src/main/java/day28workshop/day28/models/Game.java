package day28workshop.day28.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.Data;

@Data
public class Game {
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;

    public Game(Document gameDoc) {
        this.gid = gameDoc.getInteger("gid");
        this.name = gameDoc.getString("name");
        this.year = gameDoc.getInteger("year");
        this.ranking = gameDoc.getInteger("ranking");
        this.users_rated = gameDoc.getInteger("users_rated");
        this.url = gameDoc.getString("url");
        this.image = gameDoc.getString("image");
    }

    public JsonObjectBuilder toJob() {
        return Json.createObjectBuilder()
                .add("game_id", this.gid)
                .add("name", this.name)
                .add("year", Integer.toString(this.year))
                .add("rank", Integer.toString(this.ranking))
                .add("users_rated", Integer.toString(this.users_rated))
                .add("url", this.url)
                .add("thumbnail", this.image);
    }
}
