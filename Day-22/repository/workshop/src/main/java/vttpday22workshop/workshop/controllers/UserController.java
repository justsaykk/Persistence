package vttpday22workshop.workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttpday22workshop.workshop.models.User;
import vttpday22workshop.workshop.service.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userSvc;

    @PostMapping
    public String postUser(
            @RequestBody MultiValueMap<String, String> form,
            Model model) {
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String email = form.getFirst("email");
        String phone = form.getFirst("phone");
        String dob = form.getFirst("dob");

        System.out.printf(
                "username: %s\n password:%s\n email: %s\n phone:%s\n dob: %s\n",
                username, password, email, phone, dob);
        
        User newUser = new User();
        newUser.setDob(dob);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setUsername(username);

        Boolean isUserCreated = userSvc.createUser(newUser);
    
        
        model.addAttribute("username", username);
        model.addAttribute("status", isUserCreated);
        
        return "createduser";
    }
}
