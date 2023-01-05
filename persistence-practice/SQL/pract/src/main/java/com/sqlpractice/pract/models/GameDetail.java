package com.sqlpractice.pract.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDetail extends Game {

    private List<Comment> listOfComments;

    public GameDetail(SqlRowSet game, List<Comment> commentList) {
        super(game);
        this.listOfComments = commentList;
    }
}
