package com.sqlpractice.pract.repositories;

public class Queries {
    public static final String SQL_GET_ALL_GAMES = "SELECT * FROM bgg.game LIMIT ? OFFSET ?";
    public static final String SQL_GET_COMMENTS = "SELECT * FROM bgg.comment WHERE gid LIKE ?";
    public static final String SQL_GET_BY_GAMEID = "SELECT * FROM bgg.game WHERE gid LIKE ?";
}
