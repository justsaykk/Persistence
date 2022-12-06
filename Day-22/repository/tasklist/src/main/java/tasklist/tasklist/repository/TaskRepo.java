package tasklist.tasklist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tasklist.tasklist.model.User;

import static tasklist.tasklist.repository.queries.*;

@Repository
public class TaskRepo {

    @Autowired
    private JdbcTemplate template;

    public Integer addTask(User user) {
        return template.update(SQL_INSERT_TASK, user.getTaskName(), user.getPriority(), user.getUsername(),
                user.getDueDate());
    }
}
