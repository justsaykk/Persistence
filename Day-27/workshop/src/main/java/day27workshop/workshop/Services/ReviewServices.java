package day27workshop.workshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day27workshop.workshop.Models.NewReview;
import day27workshop.workshop.Repository.GameRepo;
import day27workshop.workshop.Repository.ReviewRepo;

@Service
public class ReviewServices {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    public Optional<NewReview> postNewReview(NewReview newReview) {
        // Check if gid exists
        int gid = newReview.getGid();
        if (!gameRepo.gameExists(gid)) {
            System.out.println("GID not exists");
            return Optional.empty();
        }

        // Get & Set game name
        String gameName = gameRepo.getGameName(gid);
        newReview.setName(gameName);
        return Optional.of(reviewRepo.postReview(newReview));
    }

}
