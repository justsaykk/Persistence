package day22workshop.rsvp.repository;

public class Queries {
    public static final String SQL_GET_ALL = "SELECT * FROM rsvp.RSVP";
    public static final String SQL_GET_NAME = "SELECT * FROM rsvp.RSVP WHERE name LIKE ?";
    public static final String SQL_GET_EMAIL = "SELECT * FROM rsvp.RSVP WHERE email = ?";
    public static final String SQL_ADD_RSVP = "INSERT into rsvp.RSVP (name, email, phone, date, comments) values (?,?,?,?,?)";
    public static final String SQL_COUNT_RSVP = "SELECT COUNT(*) as count FROM rsvp.RSVP ";
    public static final String SQL_UPDATE_RSVP = "UPDATE rsvp.RSVP SET name = ?, phone = ?, date = ?, comments = ? WHERE email = ?";
}
