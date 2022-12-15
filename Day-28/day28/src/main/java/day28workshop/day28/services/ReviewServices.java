package day28workshop.day28.services;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import day28workshop.day28.models.GameRating;
import day28workshop.day28.repositories.ReviewRepo;
import io.vavr.collection.List;

@Service
public class ReviewServices {

    @Autowired
    private ReviewRepo reivewRepo;

    public List<String> getReviewsforGame(int gid) {
        return reivewRepo.getReviewsforGame(gid);
    }

    public List<GameRating> getGamesByRating(Boolean highest) {
        if (highest) {
            return reivewRepo.getGamesByRating(Sort.Direction.DESC);
        } else {
            return reivewRepo.getGamesByRating(Sort.Direction.ASC);
        }
    }
}
