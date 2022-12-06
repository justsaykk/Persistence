package tasklist.tasklist.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tasklist.tasklist.model.User;
import tasklist.tasklist.services.AuthenticationService;
import tasklist.tasklist.services.TaskService;

@Controller
@RequestMapping(path = { "/", "/index.html", "/task" })
public class TaskController {

    @Autowired
    private AuthenticationService authSvc;

    @Autowired
    private TaskService taskSvc;

    @GetMapping
    public String getTaskPage() {
        return "task.html";
    }

    @PostMapping
    public String postTask(
            @RequestBody MultiValueMap<String, String> form,
            Model model) throws ParseException, NoSuchAlgorithmException {

        // Create data object
        User postedUser = new User();
        postedUser.setUsername(form.getFirst("username"));
        postedUser.setPassword(form.getFirst("password"));
        postedUser.setTaskName(form.getFirst("taskname"));
        postedUser.setPriority(form.getFirst("priority"));
        postedUser.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(form.getFirst("duedate")));

        // Pass object to service for business process
        Boolean isAuthenticated = authSvc.authenticate(postedUser);

        if (!isAuthenticated) {
            model.addAttribute("message", "user not found/wrong credentials");
            return "message.html";
        }

        Boolean addTask = taskSvc.addTask(postedUser);

        if (!addTask) {
            model.addAttribute("message", "Unable to add task");
        } else {
            model.addAttribute("message", "Successfully added task");
        }

        // Handle the result
        return "message.html";
    }
}
