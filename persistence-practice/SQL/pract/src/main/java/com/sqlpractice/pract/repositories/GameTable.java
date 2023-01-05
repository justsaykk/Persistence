package com.sqlpractice.pract.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.sqlpractice.pract.models.Game;

import static com.sqlpractice.pract.repositories.Queries.*;

@Repository
public class GameTable {

    @Autowired
    JdbcTemplate gameTable;

    public Optional<List<Game>> getAllGames(int offset) {
        int limit = 50;
        int skip = limit * (offset - 1);
        SqlRowSet rs = gameTable.queryForRowSet(SQL_GET_ALL_GAMES, limit, skip);

        if (!rs.first())
            return Optional.empty();

        rs.previous();
        List<Game> listOfGames = new ArrayList<>();
        while (rs.next()) {
            listOfGames.add(new Game(rs));
        }

        return Optional.of(listOfGames);
    }

    public SqlRowSet getGameByGid(int gid) {
        SqlRowSet rs = gameTable.queryForRowSet(SQL_GET_BY_GAMEID, gid);
        rs.first();
        return rs;
    }

}
