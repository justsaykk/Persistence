package tasklist.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import tasklist.tasklist.model.User;
import tasklist.tasklist.repository.UserRepo;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepo userRepo;

    public Boolean authenticate(User user) {
        // Get Password from DB
        SqlRowSet rs = userRepo.getUser(user);

        // Move cursor to first row of data. If it exists, the record exists.
        return rs.next();
    }
}
