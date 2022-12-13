package day27workshop.workshop.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import day27workshop.workshop.Models.NewReview;

@Repository
public class ReviewRepo {

    @Autowired
    private MongoTemplate template;

    public NewReview postReview(NewReview newReview) {
        
        return template.insert(newReview, "Comments");
    }

}
