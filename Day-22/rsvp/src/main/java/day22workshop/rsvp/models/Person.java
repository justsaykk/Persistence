package day22workshop.rsvp.models;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.MultiValueMap;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Person {
    private String name;
    private String email;
    private String phone;
    private DateTime date;
    private String comments;

    public Person(MultiValueMap<String, String> form) {
        this.name = form.getFirst("name");
        this.email = form.getFirst("email");
        this.phone = form.getFirst("phone");
        this.date = new DateTime(Instant.parse(form.getFirst("date")));
        this.comments = form.getFirst("comments");
    }

    public Person(JsonObject jo) {
        this.name = jo.getString("name");
        this.email = jo.getString("email");
        this.phone = jo.getString("phone");
        this.date = new DateTime(Instant.parse(jo.getString("date")));
        this.comments = jo.getString("comments");
    }

    public Person(SqlRowSet rs) {
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.phone = rs.getString("phone");
        this.comments = rs.getString("comments");
        this.date = new DateTime(
                DateTimeFormat.forPattern("YYYY-MM-DD")
                        .parseDateTime(rs.getString("date")));
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", this.name)
                .add("email", this.email)
                .add("phone", this.phone)
                .add("date", this.date.toString())
                .add("comments", this.comments)
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
