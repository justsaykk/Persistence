package day22workshop.rsvp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import day22workshop.rsvp.models.Person;
import day22workshop.rsvp.repository.RsvpRepo;
import jakarta.json.JsonObject;

@Service
public class RSVPservice {

    @Autowired
    private RsvpRepo rsvpRepo;

    public Optional<List<Person>> getAllRsvp() {
        return rsvpRepo.getAllRsvp();
    }

    public Optional<List<Person>> getRsvpByName(String q) {
        return rsvpRepo.getRsvpByName(q);
    }

    public Boolean postRsvp(MultiValueMap<String, String> form) {
        int added = rsvpRepo.insertRsvp(new Person(form));
        return added > 0 ? true : false;
    }

    public Boolean updateRsvp(JsonObject req) {
        int rowsChanged = rsvpRepo.updateRsvp(new Person(req));
        return rowsChanged > 0 ? true : false;
    }

    public int getCount() {
        return rsvpRepo.getCount();
    }
}
