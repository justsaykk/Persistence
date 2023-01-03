package com.sqlpractice.pract.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqlpractice.pract.models.Comment;
import com.sqlpractice.pract.models.Game;
import com.sqlpractice.pract.repositories.CommentTable;
import com.sqlpractice.pract.repositories.GameTable;

@Service
public class GameService {

    @Autowired
    GameTable gameTable;

    @Autowired
    CommentTable commentTable;

    public Optional<List<Game>> getAllGames(Integer offset) {
        return gameTable.getAllGames(offset);
    }

    // public List<Comment> getCommentForGame(int gid) {
    //     return commentTable.getCommentForGame(gid);
    // }
}
