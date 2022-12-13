package day27workshop.workshop.Models;

// import org.joda.time.DateTime;
import org.springframework.util.MultiValueMap;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Data;

@Data
public class NewReview {
    private String user;
    private int rating;
    private String c_text;
    private int gid;
    // private DateTime posted;
    private String name;

    public NewReview(MultiValueMap<String, String> form) {
        this.user = form.getFirst("user");
        this.rating = Integer.parseInt(form.getFirst("rating"));
        this.c_text = form.getFirst("c_text");
        this.gid = Integer.parseInt(form.getFirst("gid"));
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("user", this.user)
                .add("rating", Integer.toString(this.rating))
                .add("c_text", this.c_text)
                .add("gid", Integer.toString(this.gid))
                // .add("posted", this.posted.toString())
                .add("name", this.name)
                .build();
    }
}
