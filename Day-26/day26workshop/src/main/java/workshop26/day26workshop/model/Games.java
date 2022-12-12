package workshop26.day26workshop.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Data;

@Data
public class Games {

    private ObjectId _id;
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;

    public Games(Document gamesDocument) {
        this._id = gamesDocument.getObjectId("_id");
        this.gid = gamesDocument.getInteger("gid");
        this.name = gamesDocument.getString("name");
        this.year = gamesDocument.getInteger("year");
        this.ranking = gamesDocument.getInteger("ranking");
        this.users_rated = gamesDocument.getInteger("users_rated");
        this.url = gamesDocument.getString("url");
        this.image = gamesDocument.getString("image");
    }

    public JsonObject toMiniJson() {
        return Json.createObjectBuilder()
                .add("game_id", this._id.toString())
                .add("name", this.name)
                .build();
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("_id", this._id.toString())
                .add("gid", Integer.toString(this.gid))
                .add("name", this.name)
                .add("year", Integer.toString(this.year))
                .add("ranking", Integer.toString(this.ranking))
                .add("users_rated", Integer.toString(this.users_rated))
                .add("url", this.url)
                .add("image", this.image)
                .build();
    }
}
