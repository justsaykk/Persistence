package workshop26.day26workshop.model;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import lombok.Data;

@Data
public class GameQuery {

    private JsonArrayBuilder listOfGames;
    private String offset;
    private String limit;
    private Long collectionCount;
    private String timestamp;

    public GameQuery(
            String offset,
            String limit,
            Long count,
            String timestamp,
            JsonArrayBuilder gamesArr) {
        this.listOfGames = gamesArr;
        this.offset = offset;
        this.limit = limit;
        this.collectionCount = count;
        this.timestamp = timestamp;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("games", this.listOfGames)
                .add("offset", this.offset)
                .add("limit", this.limit)
                .add("total", Long.toString(this.collectionCount))
                .add("timestamp", this.timestamp)
                .build();
    }
}
