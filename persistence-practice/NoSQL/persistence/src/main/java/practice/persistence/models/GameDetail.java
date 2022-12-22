package practice.persistence.models;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDetail extends Game {

    private List<Comment> listOfComments;

    public GameDetail(Document game, List<Document> comments) {
        super(game); // return Game class
        List<Comment> newList = new ArrayList<>();
        comments.stream().forEach(d -> newList.add(new Comment(d)));
        this.listOfComments = newList;
    }
}
