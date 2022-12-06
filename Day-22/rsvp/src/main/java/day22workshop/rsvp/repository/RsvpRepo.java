package day22workshop.rsvp.repository;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day22workshop.rsvp.models.Person;
import static day22workshop.rsvp.repository.Queries.*;

@Repository
public class RsvpRepo {
    @Autowired
    private JdbcTemplate template;

    public Optional<List<Person>> getAllRsvp() {
        List<Person> listOfPersons = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL);

        if (!rs.first()) {
            return Optional.empty();
        }

        rs.previous();

        while (rs.next()) {
            listOfPersons.add(new Person(rs));
        }

        return Optional.of(listOfPersons);
    }

    public Optional<List<Person>> getRsvpByName(String q) {
        List<Person> listOfPersons = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_GET_NAME, q);
        if (!rs.first()) {
            return Optional.empty();
        }

        rs.previous();

        while (rs.next()) {
            listOfPersons.add(new Person(rs));
        }

        return Optional.of(listOfPersons);
    }

    public Integer insertRsvp(Person person) {

        SqlRowSet rs = template.queryForRowSet(SQL_GET_EMAIL, person.getEmail());

        if (!rs.first()) {
            return template.update(SQL_ADD_RSVP,
                    person.getName(),
                    person.getEmail(),
                    person.getPhone(),
                    new Timestamp(person.getDate().toDateTime().getMillis()),
                    person.getComments());
        } else {
            return template.update(SQL_UPDATE_RSVP,
                    person.getName(),
                    person.getPhone(),
                    new Timestamp(person.getDate().toDateTime().getMillis()),
                    person.getComments(), // WHERE:
                    person.getEmail());
        }
    }

    public Integer updateRsvp(Person person) {
        return template.update(SQL_UPDATE_RSVP,
                person.getName(),
                person.getPhone(),
                new Timestamp(person.getDate().toDateTime().getMillis()),
                person.getComments(), // WHERE:
                person.getEmail());
    }

    public int getCount() {
        SqlRowSet rs = template.queryForRowSet(SQL_COUNT_RSVP);
        rs.next();
        return rs.getInt("count");
    }

}
