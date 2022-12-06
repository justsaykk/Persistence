package vttpday22workshop.workshop.repository;

public class queries {

    public static final String SQL_INSERT_USER = 
            "insert into user(username, password, email, phone, dob) values(?, sha(?), ?, ?, ?)";
}
