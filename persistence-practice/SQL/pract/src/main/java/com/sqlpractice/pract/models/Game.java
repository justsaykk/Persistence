package com.sqlpractice.pract.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

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

    public Game(SqlRowSet rs) {
        this.gid = rs.getInt("gid");
        this.name = rs.getString("name");
        this.year = rs.getInt("year");
        this.ranking = rs.getInt("ranking");
        this.users_rated = rs.getInt("users_rated");
        this.url = rs.getString("url");
        this.image = rs.getString("image");
    }
}
