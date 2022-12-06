package day22workshop.rsvp.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import day22workshop.rsvp.models.Person;
import day22workshop.rsvp.services.RSVPservice;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPcontroller {

    @Autowired
    private RSVPservice rsvpSvc;

    @GetMapping(path = "/rsvps")
    public ResponseEntity<String> getAllRsvp() {

        Optional<List<Person>> dbResponse = rsvpSvc.getAllRsvp();

        if (dbResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Person person : dbResponse.get()) {
            jab.add(person.toJSON());
        }

        return new ResponseEntity<>(jab.build().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/rsvp")
    public ResponseEntity<String> getRSVPbyName(@RequestParam String q) {
        Optional<List<Person>> dbResponse = rsvpSvc.getRsvpByName(q);

        if (dbResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Person person : dbResponse.get()) {
            jab.add(person.toJSON());
        }

        return new ResponseEntity<>(jab.build().toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/rsvp")
    public ResponseEntity<String> postRsvp(@RequestBody MultiValueMap<String, String> form) {

        Boolean isPostSuccessful = rsvpSvc.postRsvp(form);

        if (isPostSuccessful) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/rsvp/{email}")
    public ResponseEntity<String> updateRsvp(
            @PathVariable String email,
            @RequestBody String strReq) {

        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(strReq.getBytes()));

        Boolean isPostSuccessful = rsvpSvc.updateRsvp(reader.readObject());
        if (isPostSuccessful) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/rsvps/count")
    public ResponseEntity<String> getCount() {
        int count = rsvpSvc.getCount();

        JsonObject jo = Json.createObjectBuilder()
                .add("count", Integer.toString(count))
                .build();
        return new ResponseEntity<String>(jo.toString(), HttpStatus.OK);
    }
}
