package tasklist.tasklist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import tasklist.tasklist.model.User;

import static tasklist.tasklist.repository.queries.*;

@Repository
public class UserRepo {

    @Autowired
    private JdbcTemplate template;

    public SqlRowSet getUser(User user) {
        return template.queryForRowSet(SQL_FIND_USER, user.getUsername(), user.getPassword());
    }
}
