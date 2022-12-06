package tasklist.tasklist.repository;

public class queries {

    public static final String SQL_FIND_USER = "SELECT * FROM user WHERE username = ? AND password = sha1(?)";

    public static final String SQL_INSERT_TASK = "INSERT into tasks(task_name, priority, assign_to, completion_date) values(?, ?, ?, ?)";
}
