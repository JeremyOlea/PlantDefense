package handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Game;
import logic.Player;

public class GardenButtonHandler implements EventHandler<ActionEvent> {
    private Player player;
    private Game game;

    public GardenButtonHandler(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("clicked plot");
    }
}
