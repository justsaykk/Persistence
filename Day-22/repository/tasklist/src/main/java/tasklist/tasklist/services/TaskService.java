package tasklist.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.tasklist.model.User;
import tasklist.tasklist.repository.TaskRepo;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    public Boolean addTask(User user) {
        int count = taskRepo.addTask(user);
        return count > 0;
    }

}
