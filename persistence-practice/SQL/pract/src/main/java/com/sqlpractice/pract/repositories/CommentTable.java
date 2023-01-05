package com.sqlpractice.pract.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.sqlpractice.pract.models.Comment;
import static com.sqlpractice.pract.repositories.Queries.*;

@Repository
public class CommentTable {

    @Autowired
    JdbcTemplate commentTable;

    public List<Comment> getCommentForGame(int gid) {
        SqlRowSet rs = commentTable.queryForRowSet(SQL_GET_COMMENTS, gid);
        List<Comment> listOfComments = new LinkedList<>();
        while (rs.next()) {
            listOfComments.add(new Comment(rs));
        }
        return listOfComments;
    }
}
