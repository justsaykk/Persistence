package practice.persistence.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import practice.persistence.models.Game;
import practice.persistence.models.GameDetail;
import practice.persistence.repository.CommentRepo;
import practice.persistence.repository.GameRepo;

@Service
public class GameService {

    @Autowired
    GameRepo gameRepo;

    @Autowired
    CommentRepo commentRepo;

    public List<Game> getAllGames(Integer offset) {
        List<Document> dbResult = gameRepo.getAllGames(offset);
        List<Game> listOfGames = new ArrayList<>();
        dbResult.stream().forEach(d -> listOfGames.add(new Game(d)));
        return listOfGames;
    }

    public GameDetail getGameDetail(Integer gameId) {
        Document game = gameRepo.getGame(gameId);
        List<Document> comments = commentRepo.getGameComment(gameId);
        return new GameDetail(game, comments);
    }
}
