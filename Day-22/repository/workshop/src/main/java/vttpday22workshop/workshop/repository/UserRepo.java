package vttpday22workshop.workshop.repository;

import static vttpday22workshop.workshop.repository.queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttpday22workshop.workshop.models.User;

@Repository
public class UserRepo {

    @Autowired
    private JdbcTemplate template;

    public Integer createUser(User user) {
        return template.update(SQL_INSERT_USER, user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(),
                user.getDob());    
    }
}
