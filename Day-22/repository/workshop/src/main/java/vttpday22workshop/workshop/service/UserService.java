package vttpday22workshop.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttpday22workshop.workshop.models.User;
import vttpday22workshop.workshop.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Boolean createUser(final User user) {
        int count = userRepo.createUser(user);
        System.out.printf("Insert count: %d", count);

        return count > 0;
    }
}
