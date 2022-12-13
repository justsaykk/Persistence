package day27workshop.workshop.Models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private String comment;
    private int rating;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("comment", this.comment)
                .add("rating", Integer.toString(rating))
                .build();
    }
}
