package com.sqlpractice.pract.models;


import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.Data;

@Data
public class Comment {
    private String c_id;
    private String user;
    private int rating;
    private String c_text;
    private int gid;

    public Comment(SqlRowSet rs) {
        this.c_id = rs.getString("c_id");
        this.user = rs.getString("user");
        this.rating = rs.getInt("rating");
        this.c_text = rs.getString("c_text");
        this.gid = rs.getInt("gid");
    }
}
