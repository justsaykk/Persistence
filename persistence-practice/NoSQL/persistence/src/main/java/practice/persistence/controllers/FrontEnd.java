package practice.persistence.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import practice.persistence.models.Game;
import practice.persistence.models.GameDetail;
import practice.persistence.services.GameService;

@Controller
@RequestMapping
public class FrontEnd {

    @Autowired
    private GameService gameSvc;

    @GetMapping(path = "/{page}")
    public String getLandingPage(
            @PathVariable(name = "page", required = false) String page,
            Model model) {
        System.out.println(page);

        if (page.equals("favicon.ico")) {
            page = "1";
        }

        List<Game> listOfGames = gameSvc.getAllGames(Integer.parseInt(page));
        model.addAttribute("listOfGames", listOfGames);
        return "landingpage";
    }

    @GetMapping(path = "/game/{gameid}")
    public String getGameDetailsPage(
            @PathVariable(name = "gameid") Integer gameId,
            Model model) {
        GameDetail gameDetail = gameSvc.getGameDetail(gameId);
        model.addAttribute("game", gameDetail);
        return "gamedetails";
    }

}
