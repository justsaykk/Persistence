package day28workshop.day28.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day28workshop.day28.models.Game;
import day28workshop.day28.repositories.GameRepo;

@Service
public class GameServices {

    @Autowired
    private GameRepo gameRepo;

    public Boolean gameExists(int gid) {
        return gameRepo.gameExists(gid);
    }

    public Optional<Game> getGame(int gid) {
        return gameRepo.getGame(gid);
    }

}
