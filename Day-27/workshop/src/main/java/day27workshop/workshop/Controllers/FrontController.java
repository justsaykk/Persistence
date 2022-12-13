package day27workshop.workshop.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class FrontController {

    @GetMapping(path = "/newreview")
    public String postReview() {
        return "postreview";
    }
}
